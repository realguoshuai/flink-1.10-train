package scala.com.gs.flink10.table.blink.stream

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.api.scala.StreamTableEnvironment


/**
  * Description  基于socket的实时SQL
  * Created with guoshuai
  * date 2020/4/29 15:25
  **/
object StreamQuery {
  case class WD(word:String, count:Int)

  def main(args: Array[String]): Unit = {
    val blinkStreamSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val streamTableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env, blinkStreamSettings)

    val wcStream: DataStream[(String, Int)] = env.socketTextStream("localhost",8001)
      .flatMap(_.split("\\W+"))
      .filter(_.nonEmpty)
      .map((_, 1))
      .keyBy(0)
      .sum(1)


    val table: Table = streamTableEnv.fromDataStream(wcStream)

    streamTableEnv.createTemporaryView("wd", table)

    var sql =
      """
        |select * from wd
      """.stripMargin

    streamTableEnv.sqlQuery(sql).printSchema()

    val dataStream: DataStream[WD] =  streamTableEnv.toAppendStream[WD](table)

    dataStream.print()

    streamTableEnv.execute("StreamQuery")
  }

}
