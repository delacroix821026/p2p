package lfqpay.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.lfq.pay.client.MpiUtil;

public class FinancialDemo {
	// 外网测试地址
	public static final String ADDRESS_TEST = "https://tt.lfqpay.com:343";
	// 本地开发地址
	public static final String ADDRESS_DEVELOP = "http://localhost:8080";
	//public static final String ADDRESS_DEVELOP = "http://192.168.1.242:8080";
	//public static final String ADDRESS_DEVELOP = "https://tt.lfqpay.com:343";
	
	@Test
	public void financialueryResquest() throws UnsupportedEncodingException, IOException {
		String requestUrl = ADDRESS_DEVELOP + "/lfq-manager/gateway/api/financialStatementQueryRequest.do";
		Map<String, String> map = initCommonData();

		map.put("txnType", "76");
		map.put("fileType", "zip");
		map.put("documentDate", "20170425");
		map.put("queryId", "20170425");
		map.put("merAbbr", "JD");
		map.put("merName", "京东"); // 商户名称
		MpiUtil.sign(map, "utf-8"); // 签名
		

		execute(requestUrl, map);
	}

	/**
	 * 发送请求
	 */
	private void execute(String requestUrl, Map<String, String> senData) {
		System.out.println("发送报文：" + senData);
		HttpClient client = new DefaultHttpClient();
		if(requestUrl != null && requestUrl.startsWith("https")) {
			convertHTTPS(client);
		}
		HttpResponse response = null;
		try {
			HttpPost post = new HttpPost(requestUrl);
			post.setEntity(new UrlEncodedFormEntity(generateParams(senData), "UTF-8"));
			response = client.execute(post);
			// 头域
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName() + ":" + header.getValue());
			}
			// 文件流
			InputStream stream = response.getEntity().getContent();
			IOUtils.copy(stream, new FileOutputStream("e://test/123.ZIP"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			HttpClientUtils.closeQuietly(client);
			HttpClientUtils.closeQuietly(response);
		}
	}

	private void convertHTTPS(HttpClient client) {
		try {
			SSLContext ctx = SSLContext.getInstance("SSL");
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = client.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
		} catch (Exception e) {
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
		map.put("merPwd", "999"); // 商户密码
		map.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())); // 交易时间：yyyyMMddHHmmss

		return map;
	}

	
	/**
	 * 生成参数集合
	 * @param senData 数据
	 * @return 参数集合
	 */
	private static final List<NameValuePair> generateParams(Map<String, String> senData) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if(senData == null)
			return list;
		Iterator<String> iterator = senData.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			list.add(new BasicNameValuePair(key, String.valueOf(senData.get(key)))); 
		}
		return list;
	}
	
}
