package com.flzc.fanglian.util;

import java.util.Map;

import org.apache.http.protocol.HTTP;

import com.android.volley.toolbox.HttpHeaderParser;
/**
 * 
 * @ClassName: CustomerHttpHeaderPaarser 
 * @Description: 重写解码方法
 * @author: LU
 * @date: 2016-3-8 下午4:29:38
 */
public class CustomerHttpHeaderPaarser extends HttpHeaderParser {

	 public static String parseCharset(Map<String, String> headers) {
	        String contentType = headers.get(HTTP.CONTENT_TYPE);
	        if (contentType != null) {
	            String[] params = contentType.split(";");
	            for (int i = 1; i < params.length; i++) {
	                String[] pair = params[i].trim().split("=");
	                if (pair.length == 2) {
	                    if (pair[0].equals("charset")) {
	                        return pair[1];
	                    }
	                }
	            }
	        }

	        return HTTP.UTF_8;
	    }
	
}
