package beto.beans

import _root_.beto.log.Logger
import com.vividsolutions.jts.algorithm.ConvexHull
import com.vividsolutions.jts.geom.{Coordinate, Geometry, Point, GeometryFactory}

/**
 * Created by IntelliJ IDEA.
 * User: jtomin
 * Date: 04.10.11
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */

/*
class ConvexPolygon(val dpoints: List[DPoint], val geomfact: GeometryFactory)
  extends ConvexHull(dpoints.map(_.coordinate).toArray, geomfact) with Logger {

  import DGeometry._
  import DMerger._

  lazy val convexHull: Geometry = dpoints match {
    case List(a) => a.geometry
    case List(a, b) => new ConvexHull(a.geometry.getCoordinates ++ b.geometry.getCoordinates, geomfact).getConvexHull
    case List(_, _*) => getConvexHull
    case List() => throw new Exception("Polygon enthält keine Punkte!")
  }

  lazy val conkavHull: Geometry = {
    def mergePoints(points: List[Geometry]): Geometry = points match {
      case List(a) => a
      case List(a, b, _*) => mergePoints(a.union(b) :: points.drop(2))
      case List() => throw new Exception("Polygon enthält keine Punkte!")
    }
    mergePoints(dpoints.map(p => p.geometry))
  }

  def contains(p: DPoint): Boolean = convexHull.contains(p.geoPoint)

  def contains(p: Coordinate): Boolean = coordinates.contains(p)

  def touches(p: DPoint): Boolean = convexHull.touches(p.geoPoint)

  def touches(g: Geometry): Boolean = convexHull.touches(g)

  def touches(g: ConvexPolygon): Boolean = convexHull.touches(g.convexHull)

  def getCentroid: Point = convexHull.getCentroid

  def intersection(o: Geometry): Geometry = convexHull.intersection(o)

  def intersects(p: DPoint):Boolean = convexHull.intersects(p.puffer)

  def intersects(o: Geometry): Boolean = convexHull.intersects(o)

  def intersects(o: ConvexPolygon): Boolean = convexHull.intersects(o.convexHull)

  def union(o: Geometry): Geometry = convexHull.union(o)

  def union(o: ConvexPolygon): Geometry = convexHull.union(o.convexHull)

  def distance(other: ConvexPolygon): Double = {
    if (this.touches(other) || this.intersects(other))
      0
    else
      (for (i <- other.coordinates; j <- coordinates) yield (i.distance(j))).min
  }

  def coordinates: Array[Coordinate] = {
    if (convexHull.getCoordinates.size > 2)
      convexHull.getCoordinates.tail
    else
      convexHull.getCoordinates
  }

  def getNumGeometries: Int = convexHull.getNumGeometries

  def getGeometryN(n: Int): Geometry = convexHull.getGeometryN(n)



  def merge(other: ConvexPolygon): Geometry = {
    mergingGeo(this, other) union conkavHull union other.conkavHull
  }

  override def toString: String = {
    var str = ""
    dpoints.foreach(str += _.toString + "  ")
    str
  }

}*/
