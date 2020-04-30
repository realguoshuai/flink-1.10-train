package scala.com.gs.flink10.utils

import java.util.Properties

/**
  * 数据库连接文件
  */
object DBProper {


    //九江
    val perproties = new Properties
    perproties.put("driver", "com.mysql.jdbc.Driver")
    perproties.put("user", "root")
    perproties.put("password", "root1234")
    val url = "jdbc:mysql://172.100.102.200:3306/bdmanager?useUnicode=true&characterEncoding=utf8"
    val phoenixUrl = "jdbc:phoenix:172.100.103.193,172.100.103.194,172.100.103.195:24002"

    //南昌
    /* val perproties = new Properties
       perproties.put("driver", "com.mysql.jdbc.Driver")
       perproties.put("user", "root")
       perproties.put("password", "Enjoyor1234")
     val user = "root"
     val password = "Enjoyor1234"
     val url = "jdbc:mysql://192.169.101.15:3306/bdmanager?useUnicode=true&characterEncoding=utf8"
     val phoenixUrl = "jdbc:phoenix:192.169.101.1,192.169.101.2,192.169.101.3:24002"*/

    //南充
    /*val perproties = new Properties
    perproties.put("driver", "com.mysql.jdbc.Driver")
    perproties.put("user", "root")
    perproties.put("password", "mysql123")
    val url = "jdbc:mysql://51.110.233.65:3306/bdmanager?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC"
  */
}
