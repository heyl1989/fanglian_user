package com.flzc.fanglian.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Color;

/**
 * 
 * @ClassName: StringUtils
 * @Description: 字符串处理
 * @author: 薛东超
 * @date: 2016年3月8日 下午6:35:28
 */
public class StringUtils {

	// 正则表达式验证手机号
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("[1][345678]\\d{9}");
		Matcher m = p.matcher(mobiles);
		// System.out.println(m.matches() + "---");
		return m.matches();
	}

	/**
	 * 将null转为空字符串
	 */


	public static String change(String s){
		return null== s?"":s;
	}
	/**
	 * 去掉小数点后面不需要的0
	 */
	public static String intMoney(String s) {
		if(null == s){
			return "";
		}
		String money = "";
		try {
			DecimalFormat df = new DecimalFormat("###0.00"); 
			df.setRoundingMode(RoundingMode.HALF_UP);
			money = df.format(Double.parseDouble(s));
		} catch (Exception e) {
			money = s;
		}
		
		if (money.indexOf(".") > 0) {
			// 正则表达
			money = money.replaceAll("0+?$", "");// 去掉后面无用的零
			money = money.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
		}
		return money;
	}

	/**
	 * 
	 * @Title: isNumber
	 * @Description: 判断是否是数字
	 * @param numberStr
	 * @return
	 * @return: boolean
	 */
	public static String isNumber(String numberStr) {

		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(numberStr);
		return m.replaceAll("").trim();

	}

	public static boolean isBlank(String s) {
		return s == null || s.trim().length() < 1;
	}

	public static boolean isNotBlank(String s) {
		return s != null && s.trim().length() > 0;
	}

	public static String showText(String s) {
		return isBlank(s) == true ? "" : s;
	}

	public static String showText(Object s, String showText) {
		if (s == null || s.equals("")) {
			return showText;
		} else {
			return s.toString();
		}
	}

	public static String showText(Long value) {
		if (value == null) {
			return "菜鸟";
		}
		return value.toString();
	}

	public static String doubleFormat(Double d) {
		if (d == null)
			return "";
		NumberFormat formater = new DecimalFormat("#.##");
		return formater.format(d);
	}

	public static String floatFormat(Float d) {
		if (d == null)
			return "";
		NumberFormat formater = new DecimalFormat("#.##");
		return formater.format(d);
	}

	public static String doubleFormat(Double d, Integer len) {
		if (d == null)
			return "";

		NumberFormat formater = null;
		if (len == null) {
			formater = new DecimalFormat("#.##");

		} else if (len == 0) {
			formater = new DecimalFormat("#");
		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("#.");
			for (int i = 0; i < len; i++) {
				buff.append("#");
			}
			formater = new DecimalFormat(buff.toString());
		}

		return formater.format(d);
	}

	public static String floatFormat(Float d, Integer len) {
		if (d == null)
			return "";

		NumberFormat formater = null;
		if (len == null) {
			formater = new DecimalFormat("#.##");

		} else if (len == 0) {
			formater = new DecimalFormat("#");
		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("#.");
			for (int i = 0; i < len; i++) {
				buff.append("#");
			}
			formater = new DecimalFormat(buff.toString());
		}

		return formater.format(d);
	}

	/**
	 * 
	 * @param a
	 *            当前值
	 * @param b
	 *            前一天的值
	 * @return
	 */
	public static String color(Double a, Double b) {
		if (a == null || b == null)
			return "#000000";
		if (b > a) {
			return "green";
		} else if (b < a) {
			return "red";
		}
		return "#000000";
	}

	/**
	 * 
	 * @param a
	 *            当前值
	 * @param b
	 *            前一天的值
	 * @return
	 */
	public static int colorAndroid(Double a, Double b) {
		if (a == null || b == null)
			return Color.BLACK;
		if (b > a) {
			return Color.GREEN;
		} else if (b < a) {
			return Color.RED;
		}
		return Color.BLACK;
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 以一种简单的方式格式化字符串 如 String s = StringHelper.format("{0} is {1}", "apple",
	 * "fruit"); System.out.println(s); //输出 apple is fruit.
	 * 
	 * @param pattern
	 * @param args
	 * @return
	 */
	public static String format(String pattern, Object... args) {
		for (int i = 0; i < args.length; i++) {
			pattern = pattern.replace("{" + i + "}", args[i].toString().trim());
		}
		return pattern;
	}

}