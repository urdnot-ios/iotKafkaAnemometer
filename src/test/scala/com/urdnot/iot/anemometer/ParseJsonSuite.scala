package com.urdnot.iot.anemometer

import com.urdnot.iot.anemometer.DataProcessor.{log, parseRecord}
import org.scalatest.flatspec.AsyncFlatSpec

class ParseJsonSuite  extends AsyncFlatSpec with DataStructures {
  val validJsonWindSpeed: Array[Byte] = """{"wind_speed_mph": 1.9290334661891368, "timestamp": 1600660233982, "wind_speed_kph": 3.103814847098321}""".getBytes
  val validJsonReply: windSpeed = windSpeed(timestamp = Some(1600660233982L), windSpeedMph = Some(1.9290334661891368), windSpeedKph = Some(3.103814847098321))
  val validInfluxReply: String = """windSpeed,sensor=windSpeed,host=pi-weather windSpeedMph=1.9290334661891368,windSpeedKph=3.103814847098321 1600660233982"""

  behavior of "DataParser"
  it should "Correctly extract an object from the JSON " in {
    parseRecord(validJsonWindSpeed, log).map { x =>
      assert(x == Right(validJsonReply))
    }
  }
  behavior of "DataFormatter"
  it should "prepare the influxdb update body " in {
    DataFormatter.prepareInflux(validJsonReply.asInstanceOf[DataProcessor.windSpeed]).map { x =>
      assert(x.get == validInfluxReply)
    }
  }
}
