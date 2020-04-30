package scala.com.gs.flink10.table.flink.batch

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.scala.BatchTableEnvironment

/**
  * Description  Jdbc连接mysql 使用flink sql 处理数据
  * Created with guoshuai
  * date 2020/4/29 15:39
  **/
object BatchQuery {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val batchTableEnv: BatchTableEnvironment = BatchTableEnvironment.create(env)

    val words = "hello flink hello lagou"
    val WDS = words.split("\\W+").map(WD(_, 1))

    val input: DataSet[WD] = env.fromCollection(WDS)

    val table: Table = batchTableEnv.fromDataSet(input)


    batchTableEnv.createTemporaryView("wordcount", table)

    batchTableEnv.sqlQuery("select * from wordcount").printSchema()

    val datasetOfTable: DataSet[WD] =  batchTableEnv.toDataSet[WD](table)

    datasetOfTable.printToErr()

    batchTableEnv.execute("BatchQuery")

  }

  case class WD(word:String, count:Int)
}
