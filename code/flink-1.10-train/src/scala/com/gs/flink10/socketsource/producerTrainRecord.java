package scala.com.gs.flink10.socketsource;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description 使用socket模拟kafka发送的
 * 提供csv && json 格式数据
 * Created with guoshuai
 * date 2019/11/12 17:55
 */
public class producerTrainRecord {
    public static String[] WORDS = new String[5];
    public static String s1 = "";
    public static String s2 = "";
    public static String s3 = "";
    public static String s4 = "";
    public static String s5 = "";

    public static void main(String[] args) {
        Integer port = 8001;
        SocketServer server = new SocketServer(port);
        while (true) {
            try {
                String line = null;
                for (int i = 0; i <= WORDS.length; i++) {//4
                    line = WORDS[i];
                    //System.out.println(i);
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
                //文本格式
                s1 ="xx"+"|"+"CN"+"|"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"|"+getIps()+"|"+getTraffic()+"|"+getDomains();
                s2 ="xx"+"|"+"CN"+"|"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"|"+getIps()+"|"+getTraffic()+"|"+getDomains();
                s3 ="xx"+"|"+"CN"+"|"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"|"+getIps()+"|"+getTraffic()+"|"+getDomains();
                s4 ="xx"+"|"+"CN"+"|"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"|"+getIps()+"|"+getTraffic()+"|"+getDomains();
                s5 ="xx"+"|"+"CN"+"|"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"|"+getIps()+"|"+getTraffic()+"|"+getDomains();

                WORDS = new String[]{s1, s2, s3, s4, s5};

            }
        }, 500);
    }

    /**
     * 流量
     *
     * @return
     */
    private static int getTraffic() {
        return new Random().nextInt(10000);
    }

    /**
     * 获取域名
     *
     * @return
     */
    private static String getDomains() {
        String[] domains = new String[]{
                "www.baidu.com",
                "www.google.com",
                "www.github.com",
                "www.jianshu.com",
                "www.aliyun.com"
        };

        return domains[new Random().nextInt(domains.length)];
    }

    /**
     * 随机获取一个ip
     *
     * @return
     */
    private static String getIps() {
        String[] ips = new String[]{
                "223.104.18.110",
                "113.101.75.194",
                "27.17.127.135",
                "183.225.139.16",
                "112.1.66.34",
                "175.148.211.190",
                "183.227.58.21"};
        return ips[new Random().nextInt(ips.length)];
    }

    /**
     * 生成level数据
     *
     * @return
     */
    public static String getLevels() {
        String[] levels = new String[]{"M", "E"};
        return levels[new Random().nextInt(levels.length)];
    }



}