package lfqpay.platform;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.lfq.pay.client.MpiUtil;

public class Demo {
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化
	
	/**
	 */
	@Test
	public void testChannelApply() throws UnsupportedEncodingException, IOException {
		String requestUrl = "http://tt.lfqpay.com:8090/lfq-manager/background/channelsCenter/noFilterChannelInterfaceApplySubmit.do";
		Map<String, String> data = initChannelApplyData("channelApply");
		execute(requestUrl, data);
	}
	
	/**
	 * 组装参数
	 */
	protected Map<String, String> initChannelApplyData(String type) throws UnsupportedEncodingException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		if("channelApply".equals(type)){
			map.put("channelName", "测试渠道商申请123");
			
			map.put("parentNum", "QT-XLSM-037-00002");
			map.put("email", "905302814@qq.com");
			map.put("mobile", "15021327865");
			map.put("telephone", "020-52565875");
			map.put("fax", "020-52565875");
			map.put("bankNum", "123456789128");
			map.put("cardNum", "123456123456123456123456123458");
			
			String time = SDF.format(new Date());
			map.put("time", time);
			System.out.println(time);
			map.put("sign", generateSign("b4bea07c3aad8d191d8bc6c9131b3fba", time));
		}
		return map;
	}
	
	/**
	 * 发送请求
	 * @param requestUrl 请求地址
	 * @param data 请求参数
	 */
	public void execute(String requestUrl, Map<String, String> data) {
		try {
			String resp = MpiUtil.send(requestUrl, data, "utf-8", 60 * 1000, 60 * 1000);
			System.out.println("发送信息：" + data);
			System.out.println("返回信息：" + resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static final String generateSign(String pswd, String time) {
		if(pswd == null || "".equals(pswd))
			return "";
		return DigestUtils.md5Hex(pswd + time);
	}
	
}
