package com.urdnot.iot.anemometer

trait DataStructures {

  final case class windSpeed(
                              timestamp: Option[Long],
                              windSpeedMph: Option[Double],
                              windSpeedKph: Option[Double]
                            ) {
    def toInfluxString(host: String): Option[String] = {
      val measurement = s"""windSpeed,"""
      val tags: String = "sensor=windSpeed," +
        "host=" + host
      val fields: String = List(windSpeed.this.windSpeedMph match {
        case Some(i) => "windSpeedMph=" + i
        case None => ""
      }, windSpeed.this.windSpeedKph match {
        case Some(i) => "windSpeedKph=" + i
        case None => ""
      }
      ).mkString(",")
      val timestamp: String = windSpeed.this.timestamp.get.toString
      Some(measurement + tags + " " + fields + " " + timestamp)
    }
  }
}
//      val sensor = "windSpeed"
//      val host = "pi-weather"
//
//      val windSpeedPoint = Point(sensor, jsonRecord.timeStamp.get)
//        .addTag("sensor", sensor)
//        .addTag("host", host)
//        .addField("windSpeedMph", jsonRecord.windSpeedMph.get)
//        .addField("windSpeedKph", jsonRecord.windSpeedKph.get)
//