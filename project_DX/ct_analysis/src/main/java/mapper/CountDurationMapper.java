package mapper;

import kv.key.ComDimension;
import kv.key.ContactDimension;
import kv.key.DateDimension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lzm
 * @date: 2019/3/19
 * @description:
 * @version: 1.0
 */
public class CountDurationMapper extends TableMapper<ComDimension, Text> {
    private ComDimension comDimension = new ComDimension();
    private Text text = new Text();
    private Map<String,String> phoneMap;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        phoneMap = new HashMap<>();
        phoneMap.put("17078388295", "李雁");
        phoneMap.put("13980337439", "卫艺");
        phoneMap.put("14575535933", "仰莉");
        phoneMap.put("19902496992", "陶欣悦");
        phoneMap.put("18549641558", "施梅梅");
        phoneMap.put("17005930322", "金虹霖");
        phoneMap.put("18468618874", "魏明艳");
        phoneMap.put("18576581848", "华贞");
        phoneMap.put("15978226424", "华啟倩");
        phoneMap.put("15542823911", "仲采绿");
        phoneMap.put("17526304161", "卫丹");
        phoneMap.put("15422018558", "戚丽红");
        phoneMap.put("17269452013", "何翠柔");
        phoneMap.put("17764278604", "钱溶艳");
        phoneMap.put("15711910344", "钱琳");
        phoneMap.put("15714728273", "缪静欣");
        phoneMap.put("16061028454", "焦秋菊");
        phoneMap.put("16264433631", "吕访琴");
        phoneMap.put("17601615878", "沈丹");
        phoneMap.put("15897468949", "褚美丽");
    }

    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        super.map(key, value, context);
        String rowkey = Bytes.toString(key.get());
        String[] splits = rowkey.split(" ");
        //05_19902496992_20180312154840_15542823911_1_1288
        if("0".equals(splits[4])){
            return;
        }
        String caller = splits[1];
        String callee = splits[3];
        String buildTime = splits[2];
        String duration = splits[5];
        text.set(duration);

        String year = buildTime.substring(0, 4);
        String month = buildTime.substring(4, 6);
        String day = buildTime.substring(6, 8);
        DateDimension yearDimension = new DateDimension(year, "-1", "-1");
        DateDimension monthDimension = new DateDimension(year, month, "-1");
        DateDimension dayDimension = new DateDimension(year, month, day);

        //主叫callerContactDimension
        ContactDimension callerContactDimension = new ContactDimension(caller,phoneMap.get(caller));
        comDimension.setContactDimension(callerContactDimension);
        //年
        comDimension.setDateDimension(yearDimension);
        context.write(comDimension,text);
        //月
        comDimension.setDateDimension(monthDimension);
        context.write(comDimension,text);
        //日
        comDimension.setDateDimension(dayDimension);
        context.write(comDimension,text);

        //被叫calleeContactDimension
        ContactDimension calleeContactDimension = new ContactDimension(callee,phoneMap.get(callee));

        comDimension.setContactDimension(calleeContactDimension);
        //年
        comDimension.setDateDimension(yearDimension);
        context.write(comDimension, text);
        //月
        comDimension.setDateDimension(monthDimension);
        context.write(comDimension, text);
        //日
        comDimension.setDateDimension(dayDimension);
        context.write(comDimension, text);
    }
}
