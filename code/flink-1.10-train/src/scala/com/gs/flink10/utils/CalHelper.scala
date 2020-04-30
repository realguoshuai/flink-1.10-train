package scala.com.gs.flink10.utils

/**
  * Description  常用计算封装
  * Created with guoshuai 
  * date 2019/12/30 11:36
  **/
object CalHelper {
    /**
      * 计算经纬度距离(公里/km)
      *
      * @param lon1 经度1
      * @param lat1 纬度1
      * @param lon2 经度2
      * @param lat2 纬度2
      * @return
      */
    def getDistance(lon1: Double, lat1: Double, lon2: Double, lat2: Double): Double = {
        //pi为π，r为地球半径
        val pi = 3.1415926
        val r: Double = 6370.99681
        // a1、a2、b1、b2分别为上面数据的经纬度转换为弧度
        val a1 = lat1 * pi / 180.0
        val a2 = lon1 * pi / 180.0
        val b1 = lat2 * pi / 180.0
        val b2 = lon2 * pi / 180.0
        val t1: Double = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2)
        val t2: Double = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2)
        val t3: Double = Math.sin(a1) * Math.sin(b1)
        val distance = Math.acos(t1 + t2 + t3) * r
        //println("------"+distance) //TODO 出现NaN
        distance.formatted("%.3f").toDouble
    }



    def main(args: Array[String]): Unit = {
        println(getDistance(115.983642,29.672352,115.983926,29.672363))
        println(getDistance(115.975426,29.717477,115.976335,29.713975))
        println("36040030040203_561500466742111".split("_")(1))

        for(i <- 1 to 5){
            println(TimeHelper.getDecimalScale((new util.Random).nextDouble()+0.5,2))
        }

    }
}
