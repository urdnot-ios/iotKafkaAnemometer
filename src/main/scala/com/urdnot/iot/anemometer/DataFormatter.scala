package com.urdnot.iot.anemometer

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object DataFormatter extends DataStructures {
  def prepareInflux(structuredData: DataProcessor.windSpeed): Future[Option[String]] = Future {
    structuredData.toInfluxString("pi-weather")
  }
}
