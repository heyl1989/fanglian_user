package com.flzc.fanglian.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.util.CustomerHttpHeaderPaarser;
import com.flzc.fanglian.util.LogUtil;
import com.google.gson.Gson;

/**
 * 
 * @ClassName: SimpleRequest
 * @Description: 封装的volley请求网络框架
 * @author: LU
 * @date: 2016-3-8 下午4:26:55
 * @param <T>
 */
public class SimpleRequest<T> extends Request<T> {

	private Gson mGson;
	private Class<?> mClass;
	private Response.Listener<T> mListener;
	public static Map<String, String> mHeader = new HashMap<String, String>();
	private Map<String, String> paramsMap;
	private static Context context = UserApplication.getInstance();
	/**
	 * get请求
	 */
	public SimpleRequest(String url, Class<T> clazz,
			Response.Listener<T> listener, Response.ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		LogUtil.i("URL", url);
		mGson = new Gson();
		mClass = clazz;
		mListener = listener;
	}

	/**
	 * get请求,不需要传ErrorListener
	 */
	public SimpleRequest(String url, Class<T> clazz,
			Response.Listener<T> listener) {
		super(Method.GET, url, createErrorListener(context.getResources()
				.getString(R.string.net_error)));
		LogUtil.i("URL", url);
		mGson = new Gson();
		mClass = clazz;
		mListener = listener;
	}

	/**
	 * post请求
	 */
	public SimpleRequest(String url, Class<T> clazz,
			Response.Listener<T> listener,
			Response.ErrorListener errorListener, Map<String, String> map) {
		super(Method.POST, url, errorListener);
		LogUtil.i("URL", url);
		mGson = new Gson();
		mClass = clazz;
		mListener = listener;
		paramsMap = map;
	}

	/**
	 * post请求,不需要传入errorListener
	 */
	public SimpleRequest(String url, Class<T> clazz,
			Response.Listener<T> listener, Map<String, String> map) {
		super(Method.POST, url, createErrorListener(context.getResources()
				.getString(R.string.net_error)));
		LogUtil.i("URL", url);
		mGson = new Gson();
		mClass = clazz;
		mListener = listener;
		paramsMap = map;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		//LogUtil.i("Headers", mHeader.toString());
		return mHeader != null ? mHeader : super.getHeaders();
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		LogUtil.i("paramsMap", paramsMap.toString());
		return paramsMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,
					CustomerHttpHeaderPaarser.parseCharset(response.headers));
			// HttpHeaderParser.parseCharset(response.headers)
			LogUtil.i("json", json);
			if (mClass != null) {
				return (Response<T>) Response.success(
						mGson.fromJson(json, mClass),
						HttpHeaderParser.parseCacheHeaders(response));

			} else {
				return (Response<T>) Response.success(json, null);
			}
		} catch (UnsupportedEncodingException e) {
			LogUtil.e("parserError", e.toString());
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {
		LogUtil.i("error json", volleyError.toString());
		return super.parseNetworkError(volleyError);
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	public RetryPolicy getRetryPolicy() {
		 RetryPolicy retryPolicy = new DefaultRetryPolicy(10 * 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		return retryPolicy;
//		return super.getRetryPolicy();
	}
	private static Toast mToast;
	private static ErrorListener createErrorListener(final String info) {
		return new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (mToast == null) {
					mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
				}
				mToast.setText(info);
				mToast.show();
			}
		};
	}
}
