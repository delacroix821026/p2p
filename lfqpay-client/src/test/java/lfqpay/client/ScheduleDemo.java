package lfqpay.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.lfq.pay.client.MpiUtil;

public class ScheduleDemo {

	// 外网测试地址
	public static final String ADDRESS_TEST = "https://tt.lfqpay.com:343";
	// 本地开发地址
	public static final String ADDRESS_DEVELOP = "http://localhost:8080";
	//public static final String ADDRESS_DEVELOP = "http://192.168.1.242:8080";

	@Test
	public void ScheduleResquest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-manager/gateway/api/merchantScheduleResques.do";
		Map<String, String> map = initCommonData();
		map.put("scheduleNum", "68"); // 商户查询码
		execute(requestUrl, map);
	}

	/**
	 * 发送请求
	 */
	@SuppressWarnings("unchecked")
	private void execute(String requestUrl, Map<String, String> data) {
		String resp = MpiUtil.send(requestUrl, data, "utf-8", 60 * 1000, 60 * 1000);
		System.out.println("发送报文：" + data);
		System.out.println("返回报文：" + resp);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = null;
		try {
			map = mapper.readValue(resp, Map.class);
			System.out.println("返回状态码：" + map.get("respCode"));
			System.out.println("返回信息：" + map.get("respMsg"));
			TestCase.assertEquals(map.get("respCode"), "0000");
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 公共参数
	 */
	private Map<String, String> initCommonData() throws UnsupportedEncodingException, IOException {
		Map<String, String> map = new HashMap<String, String>();

		// 通用信息
		String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String pwd="999";
		map.put("version", "1.0.0"); // 固定值：1.0.0
		map.put("encoding", "utf-8"); // 编码
		map.put("parentChannelsCode", "XCSQDS11"); // 上级渠道商编码
		map.put("parentMerchantCode", "CMCC"); // 上级商户编码
		map.put("time",time ); // 交易时间：yyyyMMddHHmmss
		map.put("sign", DigestUtils.md5Hex(pwd+time));//签名
		return map;
	}

}


