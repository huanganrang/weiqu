package rml.comm;

import com.google.api.client.util.Maps;

import java.util.Map;

/**
 * Simple to Introduction
 * author:Jianghui
 * date:2016/7/14 15:33
 */
public class Global {
    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader propertiesLoader = new PropertiesLoader("config.properties","RestAPIConfig.properties");

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null){
            value = propertiesLoader.getProperty(key);
            map.put(key, value);
        }
        return value;
    }
}
