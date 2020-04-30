package scala.com.gs.flink10.table.flink.batch

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.io.jdbc.JDBCAppendTableSink
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat.JDBCInputFormatBuilder
import org.apache.flink.api.java.typeutils.RowTypeInfo
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.formats.parquet.ParquetTableSource
import org.apache.flink.table.api.scala.BatchTableEnvironment
import org.apache.flink.table.descriptors.{BatchTableDescriptor, FileSystem}
import org.apache.flink.table.sinks.CsvTableSink
import org.apache.flink.table.sources.{CsvTableSource, TableSource}
import org.apache.flink.types.Row

/**
  * Description
  * Created with guoshuai
  * date 2020/4/29 15:39
  **/
object ConnectJDBCBatch {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val batchTableEnv = BatchTableEnvironment.create(env)

    val types =  Array[TypeInformation[_]](Types.STRING, Types.STRING, Types.STRING,  Types.STRING)
    val fields = Array[String]("pass_time", "license_type","license_num", "point_id")
    val typeInfo = new RowTypeInfo(types, fields)

    val jdbc = new JDBCInputFormatBuilder()
      .setDBUrl("jdbc:mysql://xx.xx.xx.xx:3306/bdmanager?user=root&password=root1234&characterEncoding=gbk&useSSL=false")
      .setDrivername("com.mysql.jdbc.Driver")
      .setUsername("root")
      .setPassword("root1234")
      .setQuery("select * from flink_to_mysql")
      .setRowTypeInfo(typeInfo)
      .finish()
    val mysqlSource : DataSet[Row] =  env.createInput(jdbc)

    mysqlSource.print()

//    val table: ParquetTableSource = new ParquetTableSource()
//    batchTableEnv.registerTableSource("table", table)

    val file = new FileSystem() //注意这里只是 实验性质
    val fs: BatchTableDescriptor =  batchTableEnv.connect(file)

//    val jdbcTableSink = new JDBCAppendTableSink()
//    batchTableEnv.registerTableSink("jdbcTableSink", jdbcTableSink)


    val csvTableSink = new CsvTableSink("E:\\development\\flink\\flink-1.10-train\\src\\scala\\com\\gs\\flink10\\table\\flink\\stream\\test.csv")
    batchTableEnv.registerTableSink("csvTableSink", csvTableSink)



    //目前来看，只有在 有 sink的情况下，需要 加 execute
//    batchTableEnv.execute("ConnectJDBCBatch")
  }
}
