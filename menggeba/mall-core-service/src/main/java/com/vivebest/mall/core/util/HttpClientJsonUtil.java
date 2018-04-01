package com.vivebest.mall.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class HttpClientJsonUtil {
	/**
	 * Post请求连接Https服务
	 * 
	 * @param httpUrl
	 *            请求地址
	 * @param jsonStr
	 *            请求报文
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public static String doHttpsPost(String httpUrl,String jsonStr) throws ConnectException ,SocketTimeoutException{
		// 参数
		HttpParams httpParameters = new BasicHttpParams();
		// 设置连接超时
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		// 设置socket超时
		HttpConnectionParams.setSoTimeout(httpParameters, 120000);
		// 获取HttpClient对象 （认证）
		HttpClient hc;
		try {
			hc = initHttpClient(httpParameters,getPort(new URI(httpUrl)));
		} catch (URISyntaxException e) {
			throw new RuntimeException("URL地址异常",e);
		}
		HttpPost post = new HttpPost(httpUrl);
		// 发送数据类型
		post.addHeader("Content-Type", "application/json;charset=utf-8");
		// 接受数据类型
		post.addHeader("Accept", "application/json");
		// 请求报文
		HttpResponse response = null;
		try {
			StringEntity entity = new StringEntity(jsonStr, "utf-8");
			post.setEntity(entity);
			post.setParams(httpParameters);
			response = hc.execute(post);
		} catch (UnknownHostException e) {
			throw new RuntimeException("网络异常!");
		} catch(ConnectException e){
			throw e;
		} catch (SocketTimeoutException e) {
			throw e;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int sCode = response.getStatusLine().getStatusCode();
		if (sCode == HttpStatus.SC_OK) {
			try {
				String responseStr = EntityUtils.toString(response.getEntity());
				return responseStr;
			} catch (ParseException e) {
				throw new RuntimeException("系统异常");
			} catch (IOException e) {
				throw new RuntimeException("系统异常");
			}
		} else{
			throw new RuntimeException("系统异常");
		}
	}

	
	 //获取SSL端口,如没有端口则为443端口
	 private static int getPort(URI uri){
	      int port = uri.getPort();
	      if(port == -1){
	          port = 443;
	      }
	      return port;
	 }
	 
	/**
	 * 初始化HttpClient对象
	 * 
	 * @param params
	 * @return
	 */
	public static HttpClient initHttpClient(HttpParams params,int httpsPort) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new SSLSocketFactoryImp(trustStore);
			// 允许所有主机的验证
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			//设置http和https支持
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, httpsPort));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient(params);
		}
	}

	public static class SSLSocketFactoryImp extends SSLSocketFactory {
		
		final SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryImp(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}
				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
}
