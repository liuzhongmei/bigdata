package producer;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: lzm
 * @date: 2019/3/15
 * @description:
 * @version: 1.0
 */
public class ProductLog {
    /**
     * 起始时间、结束时间
     */
    private String startTime = "2018-01-01";
    private String endTime = "2018-01-31";
    /**
     * 电话号码
     */
    private List<String> phoneList = new ArrayList<>();
    /**
     * 电话号码 + 姓名
     */
    public void initPhone() {
        phoneList.add("17078388295");
        phoneList.add("13980337439");
        phoneList.add("14575535933");
        phoneList.add("19902496992");
        phoneList.add("18549641558");
        phoneList.add("17005930322");
        phoneList.add("18468618874");
        phoneList.add("18576581848");
        phoneList.add("15978226424");
        phoneList.add("15542823911");
        phoneList.add("17526304161");
        phoneList.add("15422018558");
        phoneList.add("17269452013");
        phoneList.add("17764278604");
        phoneList.add("15711910344");
        phoneList.add("15714728273");
        phoneList.add("16061028454");
        phoneList.add("16264433631");
        phoneList.add("17601615878");
        phoneList.add("15897468949");
    }

    /**
     * 数据形式：17526304161,17764278604,2018-09-09 04:51:15,0043
     * 数据形式对应字段名：caller,callee,buildTime,duration
     *
     * @return
     */
    public String product() {
        //主叫，被叫电话号
        String caller = null;
        String callee = null;

        //取得主叫号码，姓名
        int callerIndex = (int) (Math.random() * phoneList.size());
        caller = phoneList.get(callerIndex);

        //取得被叫号码，姓名
        while (true) {
            int calleeIndex = (int) (Math.random() * phoneList.size());
            callee = phoneList.get(calleeIndex);
            if (caller != callee) {
                break;
            }
        }

        String bulidTime = randomBuildTime(startTime, endTime);
        //第四个参数
        DecimalFormat df = new DecimalFormat("0000");
        String duration = df.format((30 * 60 * Math.random()));
        StringBuilder sb = new StringBuilder();
        sb.append(caller).append(",").append(callee).append(",")
                .append(bulidTime).append(",").append(duration);
        return sb.toString();
    }

    private String randomBuildTime(String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = simpleDateFormat.parse(startTime);
            Date endDate = simpleDateFormat.parse(endTime);
            if (startDate.getTime() >= endDate.getTime()) {
                return null;
            }
            //随机通话建立的时间
            long randTS = startDate.getTime() + (long) ((endDate.getTime() - startDate.getTime()) * Math.random());
            Date date = new Date(randTS);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(date);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写数据到文件中
     * @param filePath
     */
    public void write(String filePath){
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath,true));
            BufferedWriter br = new BufferedWriter(out);
            while(true){
                Thread.sleep(500);
                String log = product();
                System.out.println(log);
                out.write(log + "\n");
                out.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        args = new String[]{"E:\\calllog.csv"};
        if(args == null || args.length <= 0){
            System.out.println("路径错误");
            return;
        }
        ProductLog productLog = new ProductLog();
        productLog.initPhone();
        productLog.write(args[0]);
    }
}
