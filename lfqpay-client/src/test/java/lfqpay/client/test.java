package lfqpay.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class test {
	// 外网测试地址
	public static final String ADDRESS_TEST = "https://tt.lfqpay.com:343";
	// 本地开发地址
	public static final String ADDRESS_DEVELOP = "http://localhost:8080";
	//public static final String ADDRESS_DEVELOP = "http://192.168.1.242:8080";
	
	
	/**
	 * map转为queryString
	 */
	private String map2url(Map<String, String> map) throws UnsupportedEncodingException {
		Set<String> keys = map.keySet();
		StringBuffer url = new StringBuffer();
		for (String key : keys) {
			url.append(key).append("=").append(map.get(key) == null ? map.get(key) : URLEncoder.encode(map.get(key), "utf-8")).append("&");
		}
		url.setLength(url.length() - 1);
		return url.toString();
	}
	
	@Test
	public void testSecure() throws UnsupportedEncodingException, IOException{
		Map<String, String> map = new HashMap<String, String>();
		String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String pwd="12345678";
		String username="asdasdasd";
		map.put("sign",DigestUtils.md5Hex(pwd+time)); 
		map.put("time", time);
		map.put("username", username);
		System.out.print(ADDRESS_DEVELOP + "/lfq2-manager/sys_interface_mobileLogin.do?" + map2url(map));
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ADDRESS_DEVELOP + "/lfq2-manager/sys_interface_mobileLogin.do?" + map2url(map));
	}
}
