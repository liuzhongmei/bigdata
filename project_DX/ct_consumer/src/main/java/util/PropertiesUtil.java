package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author: lzm
 * @date: 2019/3/15
 * @description:
 * @version: 1.0
 */
public class PropertiesUtil {
    public static Properties properties = null;

    static {
        InputStream is = ClassLoader.getSystemResourceAsStream("hbase_consumer.properties");
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取参数值
     * @param key
     * @return
     */
    public static String getProperties(String key){
        return properties.getProperty(key);
    }
}
