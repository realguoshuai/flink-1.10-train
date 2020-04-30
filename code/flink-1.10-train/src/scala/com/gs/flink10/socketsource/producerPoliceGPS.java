package scala.com.gs.flink10.socketsource;




import scala.com.gs.flink10.utils.TimeHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description 使用socket模拟kafka发送警员GPS数据
 * Created with guoshuai
 * date 2019/12/27 14:39
 */
public class producerPoliceGPS {
    public static String[] WORDS = new String[5];
    public static String s1 = "";
    public static String s2 = "";
    public static String s3 = "";
    public static String s4 = "";
    public static String s5 = "";

    public static String[] ID = {"1001","1002","1003","1004","1005","1006","1007"};

    public static void main(String[] args) {
        Integer port = 8001;
        SocketServer server = new SocketServer(port);
        while (true) {
            try {
                String line = null;
                for (int i = 0; i < WORDS.length; i++) {//4
                    line = WORDS[i];
                    if (WORDS.length == i+1) {
                        Thread.sleep(2000);
                        timer();
                        i = 0;
                    }
                    if (line != null && !"".equals(line) && !line.isEmpty()) {
                        server.sendMessage(line);
                        System.out.println(line);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置定时器 更新数组
     */
    public static void timer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                WORDS=null;
                System.out.println("-----定时器开始执行------");
                s1 ="{\"id\":\""+ ID[(int) (Math.random() * 7)]+"\",\"longitude\":\"112.0173\",\"latitude\":\"31.00956\",\"update_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+10000)+"\",\"oil\":\""+String.format("%.0f",(Math.random()*9+1)*10)+"\"}";
                s2 ="{\"id\":\""+ ID[(int) (Math.random() * 7)]+"\",\"longitude\":\"113.9232\",\"latitude\":\"22.51803\",\"update_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+30000)+"\",\"oil\":\""+String.format("%.0f",(Math.random()*9+1)*10)+"\"}";
                s3 ="{\"id\":\""+ ID[(int) (Math.random() * 7)]+"\",\"longitude\":\"103.7202\",\"latitude\":\"36.10377\",\"update_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+50000)+"\",\"oil\":\""+String.format("%.0f",(Math.random()*9+1)*10)+"\"}";
                s4 ="{\"id\":\""+ ID[(int) (Math.random() * 7)]+"\",\"longitude\":\"25.06217\",\"latitude\":\"121.6492\",\"update_time\":\""+ TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+10000)+"\",\"oil\":\""+String.format("%.0f",(Math.random()*9+1)*10)+"\"}";
                s5 ="{\"id\":\""+ ID[(int) (Math.random() * 7)]+"\",\"longitude\":\"118.4932\",\"latitude\":\"34.19212\",\"update_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+20000)+"\",\"oil\":\""+String.format("%.0f",(Math.random()*9+1)*10)+"\"}";

                WORDS = new String[]{s1, s2, s3, s4, s5};
            }
        }, 500);
    }

}