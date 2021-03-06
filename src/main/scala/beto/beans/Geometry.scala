package beto.beans

import com.vividsolutions.jts.geom._
import com.vividsolutions.jts.algorithm.ConvexHull

/**
 * Created by IntelliJ IDEA.
 * User: jtomin
 * Date: 15.10.11
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */

object DGeometry {

  val geomfact = new GeometryFactory

  /**
   *
   */
  def tetragon(a: Coordinate, b: Coordinate, c: Coordinate, d: Coordinate): Geometry = {
    geomfact.createPolygon(geomfact.createLinearRing(Array(a, b, c, d, a)), null)
  }

  /**
   *
   */
  def polygon(list: List[Coordinate]): Geometry = {
    geomfact.createPolygon(geomfact.createLinearRing((list.last :: list).toArray), null)
  }

  /**
   *
   */
  def point(c: Coordinate): Point = geomfact.createPoint(c)

  /**
   *
   */
  def line(a: Coordinate, b: Coordinate): LineString = {
    geomfact.createLineString(Array(a, b))
  }

  /**
   *
   */
  def line(a: Array[Coordinate]): LineString = {
    geomfact.createLineString(a)
  }

  /**
   *
   */
  def line(a: List[Coordinate]): LineString = {
    line(a.toArray)
  }

  /**...
   *
   */
  def circle(x: Int, y: Int, radius: Double): Polygon = {

    val sides = 32

    var coords = (0 to sides - 1).map{
      i =>
        val angle = (i.toDouble / sides.toDouble) * math.Pi * 2.0
        val dx = math.cos(angle) * radius
        val dy = math.sin(angle) * radius
        new Coordinate(x + dx, y + dy)
    }

    coords = coords :+ coords.head

    geomfact.createPolygon(geomfact.createLinearRing(coords.toArray), null)
  }

  def length(l: LineString): Double = {
    val coos = l.getCoordinates
    val dist = for (i <- 0 to coos.size - 2) yield (coos(i).distance(coos(i + 1)))
    dist.sum
  }

  /**
   *
   */
  def emptyGeometry = {
    geomfact.createPolygon(geomfact.createLinearRing(Array[Coordinate]()), null)
  }

  /*def convexHull(es: List[DElement]): Geometry = {
    new ConvexHull(es flatMap (p => p.coordinates) toArray, new GeometryFactory).getConvexHull.convexHull
  }*/

  /*def convexHull(es: List[DElement]): Geometry = {
    new ConvexHull(es.flatMap(p => p.puffer.getCoordinates).toArray, new GeometryFactory).getConvexHull.convexHull
  }*/
}