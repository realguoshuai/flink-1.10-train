package scala.com.gs.flink10.table.flink.stream

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.api.scala.StreamTableEnvironment


/**
  * Description  基于socket的实时SQL
  * Created with guoshuai 
  * date 2020/4/29 15:25
  **/
object CalSocketSQL {
    case class wordcount(word:String, count:Int)
    def main(args: Array[String]): Unit = {
        //注意 这里新加了一个 EnvironmentSettings
        val flinkStreamSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val streamTableEnv = StreamTableEnvironment.create(env, flinkStreamSettings)


        val wcStream: DataStream[(String, Int)] = env.socketTextStream("localhost",8001)
          .flatMap(_.split("\\W+"))
          .filter(_.nonEmpty)
          .map((_, 1))
          .keyBy(0)
          .sum(1)

        val table: Table =  streamTableEnv.fromDataStream(wcStream)

        streamTableEnv.createTemporaryView("wd", table)



        streamTableEnv.sqlQuery("select * from wd").printSchema()

        val appendDateStream: DataStream[wordcount] =  streamTableEnv.toAppendStream[wordcount](table)

        appendDateStream.print()


        env.execute("StreamQuery")
    }
}
