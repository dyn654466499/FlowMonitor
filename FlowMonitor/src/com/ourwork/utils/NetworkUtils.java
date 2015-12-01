package com.ourwork.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * 
 * @author daemon The module was passed by my testing.
 */
@SuppressLint("NewApi")
public class NetworkUtils {
	public static String proxyHost = "10.16.240.155";
	public static String proxyPort = "80";
	private static int timeout = 5;
	public static String PHPSESSID = "";

	private final static String ping_passTag = "5 packets transmitted, 5 received, 0% packet loss";

	/**
	 * 
	 * @param url
	 * @return the content which you post or null.
	 */
	public static String HttpPost(final String url,
			final HashMap<String, String> params_map) {
		// Set the proxy to connect my resource server.������ҪProxy�Ŀ���ȥ����
		//System.setProperty("http.proxySet", "true");
		//System.setProperty("http.proxyHost", proxyHost);
		//System.setProperty("http.proxyPort", proxyPort);
		// ����FutureTask��ʹ�÷�����ѯ�ʶ���
		FutureTask<String> task = new FutureTask<String>(
		// ʹ��Callable�ӿڵ�call�������лص�
				new Callable<String>() {

					@Override
					public String call() throws IOException,
							ClientProtocolException, Exception {
						HttpClient httpClient = new DefaultHttpClient();
						HttpPost post = new HttpPost(url);
						post.addHeader("Content-type",
								"application/x-www-form-urlencoded");
						post.addHeader("Connection", "close");

						if (params_map != null && params_map.size() > 0) {
							/**
							 * ����Post�������������
							 */

							// ʹ��Apache�ṩ��NameValuePair�����ֵ��
							List<NameValuePair> params = new ArrayList<NameValuePair>();
                            //写法上比KeySet复杂一点，但是效率却高一倍
							Iterator<Entry<String, String>> iterator = params_map
									.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<String, String> entry = iterator.next();
								params.add(new BasicNameValuePair(entry
										.getKey(), entry.getValue()));
							}
							post.setEntity(new UrlEncodedFormEntity(params,
									HTTP.UTF_8));
						}

						if ("" != PHPSESSID) {
							post.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
						}

						HttpResponse response = httpClient.execute(post);

						if (response.getStatusLine().getStatusCode() == 200) {
							List<Cookie> cookies = ((AbstractHttpClient) httpClient)
									.getCookieStore().getCookies();
							setPHPSESSID(cookies);
							HttpEntity entity = response.getEntity();
							String content = EntityUtils.toString(entity,
									"utf-8");
							return content;
						}
						for(Header head:response.getAllHeaders()){
							Log.e("response header", head.getName()+":"+head.getValue()+"\n");
						}
						Log.e("response code", ""+response.getStatusLine().getStatusCode());
						return "Http connect time out";
					}

				});
		new Thread(task).start();

		try {
			// ���ó�ʱ
			return task.get(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param url
	 * @return the content which you get or null.
	 */
	public static String HttpGet(final String url,
			final HashMap<String, String> params_map) {
		// Set the proxy to connect my resource server.������ҪProxy�Ŀ���ȥ����
		//System.setProperty("http.proxySet", "true");
		//System.setProperty("http.proxyHost", proxyHost);
		//System.setProperty("http.proxyPort", proxyPort);

		FutureTask<String> task = new FutureTask<String>(
		// ʹ��Callable�ӿڵ�call�������лص�
				new Callable<String>() {

					@Override
					public String call() throws IOException,
							ClientProtocolException, Exception {

						HttpClient httpClient = new DefaultHttpClient();
						HttpGet get = null;

						if (params_map != null && params_map.size() > 0) {
							/**
							 * ����Get�������������
							 */
							String params = "?";
							Iterator<Entry<String, String>> iterator = params_map
									.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<String, String> entry = iterator.next();
								params += entry.getKey() + "=";
								params += URLEncoder.encode(entry.getValue(),
										"utf-8");
								params += "&";
							}
							params = params.substring(0, params.length() - 1);
							get = new HttpGet(url + params);
						} else {
							// û��Get�������
							get = new HttpGet(url);
						}

						// addHeader����Ҫ�Ŀ���ȥ��
						get.addHeader("Content-type",
								"application/x-www-form-urlencoded");
						get.addHeader("Connection", "close");
						if ("" != PHPSESSID) {
							get.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
						}

						HttpResponse response = httpClient.execute(get);

						if (response.getStatusLine().getStatusCode() == 200) {
							List<Cookie> cookies = ((AbstractHttpClient) httpClient)
									.getCookieStore().getCookies();
							setPHPSESSID(cookies);

							HttpEntity entity = response.getEntity();
							String content = EntityUtils.toString(entity,
									"utf-8");
							return content;
						}
						return null;
					}

				});
		new Thread(task).start();

		try {
			return task.get(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getPostUrl(String name) {
		return "http://172.19.76.100:8080/daemon/api/mobileViewer/" + name + ".php";
	}

//	public static void getVerificationCode(final Context context,
//			final View view, String url) {
//		if (view.isEnabled()) {
//			Animation animation = AnimationUtils.loadAnimation(context,
//					R.anim.back_scale);
//			view.setBackground(context.getResources().getDrawable(
//					android.R.drawable.progress_horizontal));
//			view.startAnimation(animation);
//			view.setEnabled(false);
//			view.setTag(false);
//			AsyncTask<String, Integer, Drawable> task = new AsyncTask<String, Integer, Drawable>() {
//
//				@Override
//				protected Drawable doInBackground(String... params) {
//					try {
//						
//						System.setProperty("http.proxySet", "true");
//						System.setProperty("http.proxyHost",
//								NetworkUtils.proxyHost);
//						System.setProperty("http.proxyPort",
//								NetworkUtils.proxyPort);
//						HttpClient httpClient = new DefaultHttpClient();
//						HttpGet get = new HttpGet(params[0]);
//						// Set the connection time out.
//						get.getParams().setParameter(
//								CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
//						// addHeader����Ҫ�Ŀ���ȥ��
//						get.addHeader("Connection", "keep-alive");
//						HttpResponse response = httpClient.execute(get);
//						if ("" != NetworkUtils.PHPSESSID) {
//							get.setHeader("Cookie", "PHPSESSID="
//									+ NetworkUtils.PHPSESSID);
//						}
//						if (response.getStatusLine().getStatusCode() == 200) {
//							List<Cookie> cookies = ((AbstractHttpClient) httpClient)
//									.getCookieStore().getCookies();
//							NetworkUtils.setPHPSESSID(cookies);
//
//							HttpEntity entity = response.getEntity();
//
//							return Drawable.createFromStream(
//									entity.getContent(), "");
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					return null;
//				}
//
//				@Override
//				protected void onPostExecute(Drawable result) {
//					super.onPostExecute(result);
//					view.setEnabled(true);
//					view.clearAnimation();
//					
//					if (result == null) {
//						view.setBackground(new BitmapDrawable(context
//								.getResources(), BitmapUtils.getInstance()
//								.createBitmap(context.getString(R.string.get_vCode_fail))));
//					} else {
//						view.setTag(true);
//						view.setBackground(result);
//
//					}
//
//				}
//
//			};
//			task.execute(url);
//		}
//	}

	public static boolean ping(final String ip) {

		synchronized (NetworkUtils.class) {
			FutureTask<Boolean> task = new FutureTask<Boolean>(
					new Callable<Boolean>() {

						@Override
						public Boolean call() throws IOException, Exception {
							Process p = Runtime.getRuntime().exec(
									"ping -c 5 " + ip);
							InputStream inStream = p.getInputStream();
							if (inStream != null) {
								int code = -1;
								char ch = 0;
								StringBuffer buffer = new StringBuffer();
								while ((code = inStream.read()) != -1) {
									ch = (char) code;
									buffer.append(ch);
								}
								inStream.close();
								String resultinfo = buffer.toString();
								if (resultinfo.contains(ping_passTag)) {
									return true;
								}
							}
							return false;
						}

					});
			new Thread(task).start();

			try {
				return task.get(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			return false;
		}
	}

	/**
	 * 
	 * @param timeout
	 * 
	 *            You can set the time out example for 10;The range of time out
	 *            is 5 to 30 seconds;
	 */
	public static void setTimeout(int timeout) {
		if (timeout < 5) {
			timeout = 5;
		}
		if (timeout > 30) {
			timeout = 30;
		}
		NetworkUtils.timeout = timeout;
	}

	public static void setPHPSESSID(List<Cookie> cookies) {
		for (int i = 0; i < cookies.size(); i++) {
			// �����Ƕ�ȡCookie['PHPSESSID']��ֵ���ھ�̬�����У���֤ÿ�ζ���ͬһ��ֵ
			if ("PHPSESSID".equals(cookies.get(i).getName())) {
				NetworkUtils.PHPSESSID = cookies.get(i).getValue();
				Log.e("PHPSESSID", "PHPSESSID = " + PHPSESSID);
				break;
			}
		}
	}

	
	 /**
     * 获取Android本机IP地址
     * 
     * @return
     */
	public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }

    /**
     * 获取Android本机MAC
     * 
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    } 
}
