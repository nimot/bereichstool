/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beto.log

import java.util.Properties
import org.apache.log4j.{PropertyConfigurator, Level}
import java.io.FileInputStream

trait Logger {

  private val logProperties = new Properties
  logProperties.load(new FileInputStream("lib/log4j.properties"))
  PropertyConfigurator.configure(logProperties)

  private val loggerName = this.getClass.getName
  private lazy val logger = org.apache.log4j.Logger.getLogger(loggerName)

  def debug(str: String) = logger.debug(str)

  def time[T](msg:String, x: => T) = {
    val start = System.nanoTime: Double
    val result = x
    val duration = (System.nanoTime: Double) - start
    debug(msg + " [" + duration / 1000000.0 + " msecs]")
    result
  }
}
