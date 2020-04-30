package scala.com.gs.flink10.table.flink.stream

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat.JDBCInputFormatBuilder
import org.apache.flink.api.java.typeutils.RowTypeInfo
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.scala.{BatchTableEnvironment, StreamTableEnvironment}
import org.apache.flink.table.descriptors.BatchTableDescriptor
import org.apache.flink.types.Row

/**
  * Description  Jdbc连接mysql 使用flink sql 处理数据
  * Created with guoshuai 
  * date 2020/4/29 15:39
  **/
object CalMysqlSQL {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val streamTableEnv = StreamTableEnvironment.create(env)

        val types =  Array[TypeInformation[_]](Types.STRING, Types.STRING, Types.STRING,  Types.STRING)
        val fields = Array[String]("pass_time", "license_type","license_num", "point_id")
        val typeInfo = new RowTypeInfo(types, fields)


        val jdbc = new JDBCInputFormatBuilder()
          .setDBUrl("jdbc:mysql://172.100.102.200:3306/bdmanager?user=root&password=root1234&characterEncoding=gbk&useSSL=false")
          .setDrivername("com.mysql.jdbc.Driver")
          .setUsername("root")
          .setPassword("root1234")
          .setQuery("select * from flink_to_mysql")
          .setRowTypeInfo(typeInfo)
          .finish()
        val mysqlSource: DataStream[Row] =  env.createInput(jdbc)

        mysqlSource.print()

        val table: Table = streamTableEnv.fromDataStream(mysqlSource)

        streamTableEnv.createTemporaryView("flink_to_mysql", table)

        val table_q: Table = streamTableEnv.sqlQuery("select * from flink_to_mysql")
        table_q.printSchema()

        streamTableEnv.execute("CalMysqlSQL")
    }
}
