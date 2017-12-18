package lfqpay.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.easymock.EasyMock;
import org.junit.Test;

import com.lfq.pay.client.MpiConstants;
import com.lfq.pay.client.MpiUtil;
import com.lfq.pay.client.SecureUtil;

/**
 * 乐百分支付demo 测试时请注意修改参数
 */
public class Demo {

	// 外网测试地址
	public static final String ADDRESS_TEST = "https://tt.lfqpay.com:343";
	// 本地开发地址
	public static final String ADDRESS_DEVELOP = "https://tt.lfqpay.com:343";

	/**
	 **********************************非常重要************************************
	 *   测试时务必填写自己的手机号码，接收验证码和扣款短信（不要随便填写其他未知手机号码）    *
	 *   包括前台模式填写手机号收取验证码也必须填写自己的手机号码						 *
	 **********************************非常重要************************************
	 */
	private static final String TEST_PHONE = "15021327865";
	
	/**
	 * 后台模式：创建订单， 测试时请修改上面的手机号码为自己的手机号
	 */
	@Test
	public void backTransRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backTransRequest.do";
		Map<String, String> map = initCommonData();

		map.put("txnType", "01"); // 交易：01
		map.put("txnTerms", "6"); // 期数
		map.put("txnAmt", "300000"); // 订单总金额，单位：分
		map.put("accNo", "370248192322610"); // 卡号
		map.put("orderId", UUID.randomUUID().toString().replace("-", "")); // 订单号
		map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步通知地址

		String cvn = "167"; // CVN
		String name = "王丹"; // 姓名
		String validDate = "0822"; // 有效期：MMYY
		String phone = TEST_PHONE; // 手机号码（接收交易/扣款短信）
		String idCard = "330901198808018886"; // 证件号（身份证号）
		map.put("customerInfo", generateCustomerInfo("01", idCard, name, phone, cvn, validDate, "utf-8")); // 身份信息

//		map.put("merType", "0"); // 类型：0：购买类型、1：租用类型、2：抵押类型
//		map.put("discount", "20000"); // 商户补贴手续费金额
//		map.put("smsCode", "000000"); // 短信验证码
		
//		map.put("firstPay", "10000"); // 首期扣款其他金额，租用模式专用
//		map.put("rentAmount", "200000"); // 租用总金额，租用模式专用
//		map.put("monthAmount", "20000"); // 租用每期金额，租用模式专用
		
//		map.put("pawnTerms", "15"); // 抵押期数，抵押模式专用，抵押金额冻结期数
//		map.put("pawnAmount", "200000"); // 抵押金额，抵押模式专用

		map.put("firstPay", "10000"); // 首期扣款转现金额，担保模式专用
//		map.put("receipt", "1"); // 是否收货，担保模式专用
//		map.put("assuAmount", "100000"); // 担保手续费，担保模式专用
		
//		map.put("merNote", "商品描述"); // 商品描述
//		map.put("reserved", "reserved"); // 保留域
//		map.put("reqReserved", "reqReserved"); // 请求方保留域，原样返回
		
		MpiUtil.sign(map, "utf-8"); // 签名

		execute(requestUrl, map);
	}

	/**
	 * 前台模式：创建订单
	 */
	@Test
	public void frontTransRequest() throws UnsupportedEncodingException, IOException {
		Map<String, String> map = initCommonData();
		
		// 注意微信使用此链接跳转会失败，请修改地址为非本地地址
		map.put("txnType", "01"); // 交易：01
		map.put("txnTerms", "6"); // 分期期数
		map.put("txnAmt", "260000"); // 订单总金额，单位：分
		map.put("txnTermsList", "3,6,9,12"); // 可选期数
		map.put("orderId", UUID.randomUUID().toString().replace("-", "")); // 订单号
		map.put("frontUrl", "http://localhost:8080/lfq-pay/fronturl.do"); // 前台回调地址
		map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步通知地址

//		map.put("merType", "0"); // 类型：0：购买类型、1：租用类型、2：抵押类型
//		map.put("discount", "20000"); // 商户补贴手续费金额
		
//		map.put("rentAmount", "200000"); // 租用总金额，租用模式专用
//		map.put("monthAmount", "20000"); // 租用每期金额，租用模式专用
//		map.put("firstPay", "10000"); // 首期扣款其他金额，租用模式专用

//		map.put("pawnTerms", "15"); // 抵押期数，抵押模式专用，抵押金额冻结期数
//		map.put("pawnAmount", "20000"); // 抵押金额，抵押模式专用

		map.put("firstPay", "10000"); // 首期扣款其他金额，担保交易专用
//		map.put("receipt", "1"); // 是否收货，担保交易专用
//		map.put("assuAmount", "100000"); // 担保手续费，担保交易专用
		
//		map.put("accNo", "52015021011221885"); // 卡号
//		map.put("validTime", "20170616091534"); // 交易有效时间：YYYYMMDDHHmmss

//		map.put("merNote", "商品描述"); // 商品描述
//		map.put("reserved", "reserved"); // 保留域
//		map.put("reqReserved", "reqReserved"); // 请求方保留域，原样返回
		map.put("customerCode", "ffe22419-925b-4880-bdb5-83f050c4864f"); // 交易完成后返回的用户标识，下次同一个用户交易时传入可以不填写卡号等部分资料
		
		MpiUtil.sign(map, "utf-8"); // 签名
		
		System.out.println("跳转地址：" + ADDRESS_DEVELOP + "/lfq-pay/gateway/api/frontTransRequest.do?" + map2url(map));
		// 以下代码仅供windows测试使用，WEB环境请使用重定向：response.sendRedirect(ADDRESS_TEST + "/lfq-pay/gateway/api/frontTransRequest.do?" + map2url(map));
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ADDRESS_DEVELOP + "/lfq-pay/gateway/api/frontTransRequest.do?" + map2url(map));
	}
	
	/**
	 * 短信验证码
	 */
	@Test
	public void backSMSCodeRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backSMSCodeRequest.do";
		Map<String, String> map = initCommonData();
		
		map.put("txnType", "13"); // 短信验证码：13
		map.put("mobile", TEST_PHONE); // 手机号码
		
		MpiUtil.sign(map, "utf-8"); // 签名
		
		execute(requestUrl, map);
	}
	
	/**
	 * 交易查询
	 */
	@Test
	public void singleQueryRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/singleQueryRequest.do";
		Map<String, String> map = initCommonData();

		map.put("txnType", "73"); // 查询交易：73
		map.put("contractsCode", "HT20170707111421000114"); // 合同号（合同号和订单号必须填写一个）
//		map.put("orderId", "c7ad42d8701a4273a7245932f71d1db0"); // 订单号（合同号和订单号必须填写一个）

		MpiUtil.sign(map, "utf-8"); // 签名

		execute(requestUrl, map);
	}

	/**
	 * 订单退款
	 */
	@Test
	public void backCancelRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backCancelRequest.do";
		Map<String, String> map = initCommonData();
		/*Map<String, String> map = new HashMap<String, String>();
		map.put("version", "1.0.0");
		map.put("encoding", "UTF-8");
		map.put("certId", "1459236547464");
		map.put("txnTime", "20171212202533");
		map.put("merId", "SHFYJR001");
		map.put("merPwd", "w90WKRClqOgr+YqWJCEDig==");
		map.put("merName", "福建新大陆电脑股份有限公司");
		map.put("merAbbr", "新大陆");
		map.put("cancelAmount", "95500");
		map.put("state", "1");
		map.put("orderId", "2017120816324207862");
		map.put("contractsCode", "HT20171208163558110038");
		map.put("respCode", "0000");
		map.put("respMsg", "成功");*/





		map.put("txnType", "04"); // 订单退款：04
		map.put("contractsCode", "HT20170926121033000002"); // 合同号
		map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步推送地址
	
		MpiUtil.sign(map, "utf-8"); // 签名
	
		execute(requestUrl, map);
	}

	/**
	 * 确认收货
	 */
	@Test
	public void backConfirmRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backConfirmRequest.do";
		Map<String, String> map = initCommonData();

		map.put("txnType", "91"); // 确认收货：91
//		map.put("carriersNote", "201609275423"); // 取货码，有取货码时必须上送
		map.put("contractsCode", "HT20170322120104000004"); // 合同号
		map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步推送地址

		MpiUtil.sign(map, "utf-8"); // 签名

		execute(requestUrl, map);
	}
	
	/**
	 * 确认还机
	 */
	@Test
	public void backRefundRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backRefundRequest.do";
		Map<String, String> map = initCommonData();
		
		map.put("txnType", "33"); // 确认还机：33
		map.put("contractsCode", "HT20170912111040000087"); // 合同号
		map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步推送地址
		map.put("amount", "20000"); // 冻结余额部分转现
		
		MpiUtil.sign(map, "utf-8"); // 签名
		
		execute(requestUrl, map);
	}
	
	/**
	 * 后台模式：增值服务费
	 */
	@Test
	public void backValueAddedRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backValueAddedRequest.do";
		Map<String, String> map = initCommonData();
		
		map.put("txnType", "02"); // 增值服务费：02
		map.put("contractsCode", "HT20170418152729000006"); // 合同号
		map.put("orderId", "OD112233"); // 增值服务费订单号
		map.put("serveId", UUID.randomUUID().toString().replace("-", "")); // 增值服务费服务单号
		map.put("valueAdded", "10000"); // 增值服务费
		map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步推送地址
//		map.put("smsCode", "000000"); // 短信验证码
		
		MpiUtil.sign(map, "utf-8"); // 签名
		
		execute(requestUrl, map);
	}
	
	/**
	 * 前台模式：增值服务费
	 */
	@Test
	public void frontValueAddedRequest() throws UnsupportedEncodingException, IOException {
		Map<String, String> map = initCommonData();
		
		map.put("txnType", "02"); // 增值服务费：02
		map.put("contractsCode", "HT20170418152729000006"); // 合同号
		map.put("orderId", "OD112233"); // 增值服务费订单号
		map.put("serveId", UUID.randomUUID().toString().replace("-", "")); // 增值服务费服务单号
		map.put("valueAdded", "10000"); // 增值服务费
		map.put("frontUrl", "http://localhost:8080/lfq-pay/fronturl.do"); // 前台回调地址
		map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步推送地址
//		map.put("validTime", "20200902140534"); // 交易有效时间：YYYYMMDDHHmmss
		
		map.put("merNote", "商品描述"); // 商品描述
		
		MpiUtil.sign(map, "utf-8"); // 签名
		
		System.out.println("跳转地址：" + ADDRESS_DEVELOP + "/lfq-pay/gateway/api/frontValueAddedRequest.do?" + map2url(map));
		// 以下代码仅供windows测试使用，WEB环境请使用重定向：response.sendRedirect(ADDRESS_TEST + "/lfq-pay/gateway/api/frontValueAddedRequest.do?" + map2url(map));
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ADDRESS_DEVELOP + "/lfq-pay/gateway/api/frontValueAddedRequest.do?" + map2url(map));
	}

	/**
	 * 增值服务费查询
	 */
	@Test
	public void queryValueAddedRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/queryValueAddedRequest.do";
		Map<String, String> map = initCommonData();
		
		map.put("txnType", "74"); // 增值服务费查询：74
		map.put("serveId", "SV1122336"); // 增值服务费服务单号
//		map.put("traceNo", "201705240909467368225"); // 增值服务费跟踪号
		
		MpiUtil.sign(map, "utf-8"); // 签名
		
		execute(requestUrl, map);
	}
	
	/**
	 * 手续费查询
	 */
	@Test
	public void backPoundageRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backPoundageRequest.do";
		Map<String, String> map = initCommonData();

		map.put("txnType", "11"); // 手续费查询：11
		map.put("txnAmt", "300000"); // 金额，单位：分
		map.put("txnTerms", "6"); // 分期期数
//		map.put("merType", "0"); // 类型：0：购买类型、1：租用类型、2：抵押类型
		
//		map.put("firstPay", "10000"); // 首期支付其他金额，租用模式专用
//		map.put("rentAmount", "25000"); // 租用总金额，租用模式专用
//		map.put("monthAmount", "20000"); // 租用每期金额，租用模式专用
		
//		map.put("pawnAmount", "20000"); // 抵押金额，抵押模式专用

//		map.put("receipt", "1"); // 是否收货，担保交易专用
		
		MpiUtil.sign(map, "utf-8"); // 签名

		execute(requestUrl, map);
	}

	/**
	 * 还机利息
	 */
	@Test
	public void backLatefeeRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backLatefeeRequest.do";
		Map<String, String> map = initCommonData();
		
		map.put("txnType", "12"); // 还机利息：12
		map.put("contractsCode", "HT20170808145327000010"); // 合同号
		
		MpiUtil.sign(map, "utf-8"); // 签名
		
		execute(requestUrl, map);
	}
	
	/**
	 * 担保确认
	 */
	@Test
	public void backAffirmRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backAffirmRequest.do";
		Map<String, String> map = initCommonData();
		
		map.put("amount", "10000"); // 担保转现金额，单位：分
		map.put("txnType", "34"); // 担保完成：34
		map.put("affirmType", "1"); // 完成类型：0：取消交易、1：完成交易
		map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do");
		map.put("contractsCode", "HT20170925135828000004"); // 合同号
		
		MpiUtil.sign(map, "utf-8"); // 签名
		
		execute(requestUrl, map);
	}
	
	/**
	 * 月数转期数
	 */
	@Test
	public void backMonth2termsRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backMonth2termsRequest.do";
		Map<String, String> map = initCommonData();

		map.put("txnType", "41"); // 月数转期数：41
		map.put("month", "12"); // 月数

		MpiUtil.sign(map, "utf-8"); // 签名

		execute(requestUrl, map);
	}
	
	/**
	 * 短链接生成
	 */
	@Test
	public void backShortLinkRequest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backShortLinkRequest.do";
		Map<String, String> map = initCommonData();
		
		map.put("txnType", "42"); // 短链接生成：42
		map.put("txnTerms", "6"); // 分期期数
		map.put("txnAmt", "60000"); // 订单总金额，单位：分
//		map.put("pawnAmount", "40000"); // 抵押金额，单位：分
		map.put("orderId", UUID.randomUUID().toString().replace("-", "")); // 订单号
		map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步通知地址
		map.put("productName", "商品信息"); // 商品信息
//		map.put("reserved", "reserved"); // 保留域
//		map.put("reqReserved", "reqReserved"); // 请求方保留域，原样返回
		
		MpiUtil.sign(map, "utf-8"); // 签名
		
		Map<String, String> result = execute(requestUrl, map);
		if(result != null) {
			String access = result.get("access");
			System.out.println("短链接地址：" + access);
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + access);
		}
	}

	/**
	 * 接收通知测试
	 */
	@Test
	public void accept() throws UnsupportedEncodingException {
		String queryString = "txnType=01&respCode=0000&txnTerms=6&merId=GZW-001&merPwd=123456&version=1.0.0&txnAmt=60000&amount=10000&contractsCode=HT20170621150222000015&poundage=0&certId=1459236547464&merName=测试商户&merAbbr=商户简称&customerCode=cd8e2d9c-8026-4884-aa9f-e61de82a9b70&merType=0&encoding=UTF-8&respMsg=成功&merNote=商品描述&respTime=20170621150234&queryId=4244007d-5f8e-4545-be40-46e31d57cc1c&orderId=123456&signature=DMnfn0i34aZtbTiocOAznbZLGZonMpr75K1/wbThnRqcGzQg4o6YiYzzxGMblOZJJQqYgJoycUKHfks9MiBkkmnm3pWfbr6HmvySaRcbebJ7UCaIueuT5sPYgDs7PG5quXjWtKOHhAiD+ggpszycN6WDHmAViYDNJDpzkzs9p2M=&txnTime=20170621150110";
		String[] queryStrings = queryString.split("&");
		int index;
		String key, value;
		//==========tomcat6==========//
//		Map<String, String> map = new HashMap<String, String>();
		//==========tomcat6==========//
		//==========tomcat7+==========//
		Map<String, String[]> map = new HashMap<String, String[]>();
		//==========tomcat7+==========//
		HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		for (String paras : queryStrings) {
			index = paras.indexOf("=");
			key = paras.substring(0, index);
			// queryString未使用URL编码，不需要解码
			value = paras.substring(index + 1);
			// queryString已使用URL编码，需要解码
//			value = URLDecoder.decode(paras.substring(index + 1), "utf-8");
			//==========tomcat6==========//
//			map.put(key, value);
			//==========tomcat6==========//
			//==========tomcat7+==========//
			map.put(key, new String[] { value });
			//==========tomcat7+==========//
			EasyMock.expect(request.getParameter(key)).andReturn(value).anyTimes();
		}
		EasyMock.expect(request.getParameterMap()).andReturn(map).anyTimes();
		EasyMock.replay(request);
		accept(request);
	}

	/**
	 * 接收通知
	 */
	public void accept(HttpServletRequest request) {
		//==========tomcat6==========//
//		Map<String, String> map = request.getParameterMap();
		//==========tomcat6==========//
		//==========tomcat7+==========//
		Map<String, String[]> names = request.getParameterMap();
		Map<String, String> map = new HashMap<String, String>();
		Entry<String, String[]> entry = null;
		Set<Entry<String, String[]>> entries = names.entrySet();
		Iterator<Entry<String, String[]>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			entry = iterator.next();
			map.put(entry.getKey(), entry.getValue()[0]);
		}
		//==========tomcat7+==========//
		if (MpiUtil.validate(map, "UTF-8")) {
			System.out.println("验签成功");
			String respMsg = map.get("respMsg");
			String respCode = map.get("respCode");
			String contractsCode = map.get("contractsCode");
			if ("0000".equals(respCode)) {
				System.out.println("交易成功，提示信息：" + respMsg);
				System.out.println("合同号：" + contractsCode);
				// TODO 交易成功业务逻辑
			} else {
				System.out.println("交易失败，错误信息：" + respMsg);
				// TODO 交易失败业务逻辑
			}
		} else {
			System.out.println("验签失败");
			// TODO 验签失败业务逻辑
		}
	}

	/**
	 * 签名
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void sign() throws JsonParseException, JsonMappingException, IOException {
		String json = "{\"txnType\": \"01\"}";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = mapper.readValue(json, Map.class);

//		Map<String, String> map = new HashMap<String, String>();
//		map.put("version", "1.0.0");
//		map.put("encoding", "utf-8");
//		map.put("merId", "GZW-001");
//		map.put("merPwd", "123456");
//		map.put("merAbbr", "商户简称");
//		map.put("merName", "某测试商户");
//		map.put("txnAmt", "100000");
//		map.put("txnTerms", "6");
//		map.put("txnTime", "20161014105755");
//		map.put("txnType", "11");
//		map.put("reserved", "");
//		map.put("reqReserved", "");
		
		System.out.println("拼接字符串：" + MpiUtil.coverMap2String(map));
		MpiUtil.sign(map, "utf-8"); // 签名
		System.out.println("验签结果：" + MpiUtil.validate(map, "utf-8"));
		System.out.println("签名字符串：" + map.get("signature"));
	}

	/**
	 * 生成身份信息
	 */
	@Test
	public void generateCustomerInfo() throws UnsupportedEncodingException, IOException {
		String cvn = "123"; // CVN
		String name = "测试"; // 姓名
		String validDate = "1222"; // 有效期：MMYY
		String phone = TEST_PHONE; // 手机号码（接收交易/扣款短信）
		String idCard = "327140198829244255"; // 证件号（身份证号）
		generateCustomerInfo("01", idCard, name, phone, cvn, validDate, "utf-8");
	}

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

	/**
	 * 发送请求
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> execute(String requestUrl, Map<String, String> data) {
		String resp = MpiUtil.send(requestUrl, data, "utf-8", 60 * 1000, 60 * 1000);
		System.out.println("发送报文：" + data);
		System.out.println("返回报文：" + resp);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = null;
		try {
			map = mapper.readValue(resp, Map.class);
			System.out.println("验签结果：" + MpiUtil.validate(map, "utf-8"));
			System.out.println("返回状态码：" + map.get("respCode"));
			System.out.println("返回信息：" + map.get("respMsg"));
			System.out.println("合同号：" + map.get("contractsCode"));
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
	 * 持卡人身份信息
	 */
	private String generateCustomerInfo(String type, String idCard, String name, String phone, String cvn, String validDate, String encoding) throws UnsupportedEncodingException, IOException {
		StringBuffer info = new StringBuffer("{").append(type).append(MpiConstants.COLON); // 证件类型
		info.append(idCard).append(MpiConstants.COLON);// 证件号码
		info.append(name).append(MpiConstants.COLON); // 姓名
		info.append(phone).append(MpiConstants.COLON); // 电话
		info.append(MpiConstants.COLON); // 校验码
		info.append(MpiConstants.COLON); // 密码
		info.append(cvn).append(MpiConstants.COLON); // CVN
		info.append(validDate).append("}"); // 有效期
		System.out.println("CustomerInfo拼接字符串：" + info);
		System.out.println("CustomerInfo Base64编码：" + new String(SecureUtil.base64Encode(info.toString().getBytes(encoding))));
		return new String(SecureUtil.base64Encode(info.toString().getBytes(encoding)));
	}

	/**
	 * 公共参数
	 */
	private Map<String, String> initCommonData() throws UnsupportedEncodingException, IOException {
		Map<String, String> map = new HashMap<String, String>();

		String merPwd = "12345678";
		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 通用信息
		map.put("version", "1.0.0"); // 固定值：1.0.0
		map.put("encoding", "utf-8"); // 编码
		map.put("merId", "GZW-001"); // 商户编号
		map.put("merName", "交易测试"); // 商户名称
		map.put("merAbbr", "交易测试"); // 商户简称
		// 直接使用密码
//		map.put("merPwd", merPwd); // 商户密码
		// 加密传送密码
		map.put("merPwd", SecureUtil.encryptWithDES(txnTime, merPwd)); // 商户密码
		map.put("txnTime", txnTime); // 交易时间：yyyyMMddHHmmss

		return map;
	}

}
