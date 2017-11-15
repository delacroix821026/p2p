package lfqpay.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import junit.framework.TestCase;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.lfq.pay.client.MpiUtil;

public class CMCCDemo {

	// 外网测试地址
	public static final String ADDRESS_TEST = "https://tt.lfqpay.com:343";
	// 本地开发地址
	public static final String ADDRESS_DEVELOP = "http://localhost:8080";
	//public static final String ADDRESS_DEVELOP = "http://192.168.1.242:8080";
	//public static final String ADDRESS_DEVELOP = "https://tt.lfqpay.com:343";

	@Test
	public void verificationCMCCQueryResquest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/verificationCMCCQueryResquest.do";
		Map<String, String> map = initCommonData();

		map.put("txnType", "68"); // 订单核查：68
		map.put("orderId", UUID.randomUUID().toString().replace("-", "")); // 订单号
		//map.put("orderId", "b7572ec2a6664559b46a14ea01e83c42"); // 订单号
		map.put("orderAmount", "60000"); // 订单金额（分期金额+抵押金额）
		map.put("phoneNum", "18688666688"); // 手机号
		map.put("commodityCode", "CMCC2"); // 商品编号
		map.put("minCharge", "100"); // 最低消费
		map.put("period", "24"); // 周期（产品名称 = 商品编号+最低消费+周期）
		
		MpiUtil.sign(map, "utf-8"); // 签名
		

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
		try {
			Map<String, String> map = mapper.readValue(resp, Map.class);
			System.out.println("验签结果：" + MpiUtil.validate(map, "utf-8"));
			System.out.println("返回状态码：" + map.get("respCode"));
			System.out.println("返回信息：" + map.get("respMsg"));
			System.out.println("订单号:" + map.get("orderId"));
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
		map.put("version", "1.0.0"); // 固定值：1.0.0
		map.put("encoding", "utf-8"); // 编码
		map.put("merId", "CMCC"); // 商户编码
		map.put("merName", "中国移动"); // 商户名称
		map.put("merPwd", "999"); // 商户密码
		map.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())); // 交易时间：yyyyMMddHHmmss

		return map;
	}
	
	/**
	 * 订单状态配对
	 */
	@Test
	public void inspectCMCCQueryResquest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/inspectCMCCQueryResquest.do";
		
		Map<String, String> map = initCommonData();

		map.put("txnType", "69"); // 订单核查：69
		//map.put("orderId", UUID.randomUUID().toString().replace("-", "")); // 订单号
		map.put("orderId","b7572ec2a6664559b46a14ea01e83c42"); // 订单号
		map.put("deliveryState", "1"); // 订单金额
		map.put("phoneNum", "13602555023"); // 手机号
		
		MpiUtil.sign(map, "utf-8"); // 签名
		

		execute(requestUrl, map);
	}

}

