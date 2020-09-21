package com.urdnot.iot.anemometer

import com.typesafe.scalalogging.Logger
import io.circe.{Json, ParsingFailure}
import io.circe.parser.parse
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object DataProcessor  extends DataStructures {
  val log: Logger = Logger("windspeed_Processor")
  def parseRecord(record: Array[Byte], log: Logger): Future[Either[String, windSpeed]] = Future {
    val recordString = record.map(_.toChar).mkString
    val genericParse: Either[ParsingFailure, Json] = parse(recordString)
    import io.circe.optics.JsonPath._
    genericParse match {
      case Right(x) => x match {
        case x: Json => try {
          Right(windSpeed(
            timestamp = root.timestamp.long.getOption(x),
            windSpeedMph = root.wind_speed_mph.double.getOption(x),
            windSpeedKph = root.wind_speed_kph.double.getOption(x)
          ))
        } catch {
          case e: Exception => Left("Unable to extract JSON: " + e.getMessage)
        }
        case _ => Left("I dunno what this is, but it's not a door message: " + x)
      }
      case Left(x) => Left(x.getMessage)
    }
  }
}
