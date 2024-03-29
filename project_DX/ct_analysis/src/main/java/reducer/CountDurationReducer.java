package reducer;

import kv.key.ComDimension;
import kv.value.CountDurationValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/3/19
 * @description:
 * @version: 1.0
 */
public class CountDurationReducer extends Reducer<ComDimension, Text,ComDimension, CountDurationValue> {
    private CountDurationValue countDurationValue = new CountDurationValue();
    @Override
    protected void reduce(ComDimension key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        super.reduce(key, values, context);
        int callSum = 0;
        int callDuration = 0;
        for (Text t : values) {
            callSum++;
            callDuration += Integer.valueOf(t.toString());
        }
        countDurationValue.setCallSum(String.valueOf(callSum));
        countDurationValue.setCallDurationSum(String.valueOf(callDuration));

        context.write(key, countDurationValue);
    }
}
