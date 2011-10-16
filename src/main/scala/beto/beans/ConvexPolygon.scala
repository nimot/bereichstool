package beto.beans

import com.vividsolutions.jts.algorithm.ConvexHull
import com.vividsolutions.jts.geom.{Coordinate, Geometry, Point, GeometryFactory}

/**
 * Created by IntelliJ IDEA.
 * User: jtomin
 * Date: 04.10.11
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */

class ConvexPolygon(val dpoints: List[DPoint], val geomfact: GeometryFactory)
  extends ConvexHull(dpoints.map(_.coordinate).toArray, geomfact) {

  import DGeometry._

  lazy val convexHull = dpoints match {
    case List(a) => a.geometry
    case List(_, _) => getConvexHull // TODO bilde Rechteck
    case List(_, _*) => getConvexHull
    case Nil => throw new RuntimeException("Konvexe Hülle enthält keine Punkte!")
  }

  def contains(p: DPoint): Boolean = convexHull.contains(p.geoPoint)

  def contains(p: Coordinate): Boolean = coordinates.contains(p)

  def touches(p: DPoint): Boolean = convexHull.touches(p.geoPoint)

  def touches(g: Geometry): Boolean = convexHull.touches(g)

  def getCentroid: Point = convexHull.getCentroid

  def intersection(o: Geometry): Geometry = convexHull.intersection(o)

  def union(o: Geometry): Geometry = convexHull.union(o)

  def union(o: ConvexPolygon): Geometry = convexHull.union(o.convexHull)

  def coordinates: Array[Coordinate] = {
    if (convexHull.getCoordinates.size > 2)
      convexHull.getCoordinates.tail
    else
      convexHull.getCoordinates
  }


  def getNumGeometries: Int = convexHull.getNumGeometries

  def getGeometryN(n: Int): Geometry = convexHull.getGeometryN(n)


  def randPoints(other: ConvexPolygon): Pair[Pair[Coordinate, Coordinate], Pair[Coordinate, Coordinate]] = {

    /**
     * Lokalisiert die zugewandten Punkte zweier konvexen Polygone, um später eine
     * optimale Verknüpfungsstelle zu bestimmen.
     */
    def locate(a: ConvexPolygon, b: ConvexPolygon): Pair[Pair[Coordinate, Coordinate], Pair[Coordinate, Coordinate]] = {

      // Verschmelze die konvexen Hüllen zu einer konvexen Hülle
      val conv = new ConvexHull((a.coordinates.toList ::: b.coordinates.toList).toArray, geomfact).getConvexHull

      // Gib alle Koordinaten der Eckpunkte
      val coords = conv.getCoordinates

      // Bilde daraus Kanten
      val edges = for (i <- 0 to coords.size - 2) yield ((coords(i), coords(i + 1)))

      // Bestimme Verschmelzungspunkte der Polygone a und b.
      val erg = edges.toList.filter{
        e => (a.contains(e._1) && b.contains(e._2)) || (b.contains(e._1) && a.contains(e._2))
      }

      /*
       * Konvexe Polygone dürfe nur an max. zwei Stellen verschmolzen werden, um wieder
       * eine konvexe Form zu bilden.
       */
      if (erg.size != 2) throw new RuntimeException("Fehler bei der Ermittlung von Verbindungstangenten der Polygone")

      // Randpunkte der jeweiligen Polygone zuordnen
      val aRand: List[Coordinate] = erg flatMap (e => List(e._1, e._2)) filter (c => a.contains(c))
      val bRand: List[Coordinate] = erg flatMap (e => List(e._1, e._2)) filter (c => b.contains(c))

      ((aRand.head, aRand.last), (bRand.head, bRand.last))
    }

    /**
     *  Klappt die geordnete Liste der zugewandten Punkten um, falls diese
     * über Kreuz liegen.
     */
    def turn(a: Pair[Coordinate, Coordinate],
             b: Pair[Coordinate, Coordinate]): Pair[Pair[Coordinate, Coordinate], Pair[Coordinate, Coordinate]] = {

      val begin = line(a._1, b._1)
      val end = line(a._2, b._2)

      if (begin.intersects(end))
        (a, (b._2, b._1))
      else
        (a, b)
    }

    // gegenüberliegende Eckpunkte der Polygone a und b
    val (aRand, bRand) = locate(this, other)
    turn(aRand, bRand)
  }

  override def toString: String = {
    var str = ""
    coordinates.foreach(str += _.toString + "  ")
    str
  }

}