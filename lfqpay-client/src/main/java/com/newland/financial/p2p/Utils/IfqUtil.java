package com.newland.financial.p2p.Utils;

import com.lfq.pay.client.MpiUtil;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 *分期业务工具类.
 * @author Gregory
 */
@Log4j
public class IfqUtil {
    /**
     * 创建连接的最长时间.
     */
    private static final Integer CONNECTION_TIMEOUT = 60 * 1000;
    /**
     * 数据传输的最长时间.
     */
    private static final Integer READ_TIMEOUT = 60 * 1000;
    /**
     * 默认编码.
     */
    private static final String ENCODING = "utf-8";

    /**
     * 发送请求到乐百分.
     * @param requestUrl    请求路径
     * @param data  请求数据
     * @param encoding  编码
     * @param connectionTimeout  创建连接的最长时间
     * @param readTimeout  数据传输的最长时间
     * @return  响应报文
     */
    public static Map<String, String> execute(String requestUrl, Map<String, String> data,String encoding,
                                        Integer connectionTimeout, Integer readTimeout) {
        if(encoding == null || "".equals(encoding)){
            encoding = ENCODING;
        }
        if(connectionTimeout == null || connectionTimeout<=0){
            connectionTimeout = CONNECTION_TIMEOUT;
        }
        if(readTimeout == null || readTimeout<=0){
            readTimeout = READ_TIMEOUT;
        }
        String resp = MpiUtil.send(requestUrl, data, encoding, connectionTimeout,readTimeout);
        log.debug("发送报文：" + data);
        log.debug("返回报文：" + resp);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;
        try {
            map = mapper.readValue(resp, Map.class);
            log.debug("验签结果：" + MpiUtil.validate(map, "utf-8"));
            log.debug("返回状态码：" + map.get("respCode"));
            log.debug("返回信息：" + map.get("respMsg"));
            log.debug("合同号：" + map.get("contractsCode"));
            TestCase.assertEquals(map.get("respCode"), "0000");
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 发送请求到乐百分.
     * @param requestUrl    请求路径
     * @param data  请求数据
     * @return  响应报文
     */
    public static Map<String, String> execute(String requestUrl, Map<String, String> data) {
        String resp = MpiUtil.send(requestUrl, data, ENCODING, CONNECTION_TIMEOUT, READ_TIMEOUT);
        log.debug("发送报文：" + data);
        log.debug("返回报文：" + resp);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;
        try {
            map = mapper.readValue(resp, Map.class);
            log.debug("验签结果：" + MpiUtil.validate(map, "utf-8"));
            log.debug("返回状态码：" + map.get("respCode"));
            log.debug("返回信息：" + map.get("respMsg"));
            log.debug("合同号：" + map.get("contractsCode"));
            TestCase.assertEquals(map.get("respCode"), "0000");
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Object convertMap(Class type, Map map) {
        Object obj = null;
        try{
            BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
            obj = type.newInstance(); // 创建 JavaBean 对象

            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();

                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);

                    Object[] args = new Object[1];
                    args[0] = value;

                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }

        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
