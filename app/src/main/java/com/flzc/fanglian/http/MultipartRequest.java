package com.flzc.fanglian.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.flzc.fanglian.util.CustomerHttpHeaderPaarser;
import com.flzc.fanglian.util.LogUtil;
import com.google.gson.Gson;

/**
 * 
 * @ClassName: MultipartRequest 
 * @Description: Volley文件上传工具类
 * @author: LU
 * @date: 2016-3-8 下午4:34:26
 */
public class MultipartRequest extends Request<String> {
	private Response.ErrorListener errorListener = null;
	private Response.Listener<String> mListener = null;
	private MultipartRequestParams params = null;
	private HttpEntity httpEntity = null;
	private Gson mGson;

	public MultipartRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener,
			MultipartRequestParams params) {
		super(Method.POST, url, errorListener);
		this.params = params;
		this.mListener = listener;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (params != null) {
			httpEntity = params.getEntity();
			try {
				httpEntity.writeTo(baos);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String str = new String(baos.toByteArray());
			LogUtil.d("test", "bodyString is :" + str);
		}
		return baos.toByteArray();
	}

	private static Map<String, String> headers = new HashMap<String, String>();

	static {
		
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		LogUtil.i("Headers", headers.toString());
		return headers != null ? headers : super.getHeaders();
	}

	public String getBodyContentType() {
		// TODO Auto-generated method stub
		String str = httpEntity.getContentType().getValue();
		return httpEntity.getContentType().getValue();
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String parsed;
		try {
			parsed = new String(response.data, CustomerHttpHeaderPaarser.parseCharset(response.headers));
			LogUtil.i("response", parsed);
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
		return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	protected void deliverResponse(String response) {
		mListener.onResponse(response);
	}
}
