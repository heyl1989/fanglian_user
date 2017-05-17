package com.flzc.fanglian.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JudgeAcctivityStateUtil {

	/**
	 * 
	 * @Title: getState
	 * @Description: 判断活动状态的工具类
	 * @param startTime
	 * @param endTime
	 * @return
	 * @return: 0 即将开始, 1进行中，2已结束
	 */

	public static int getState(long startTime, long endTime) {
		int state = 0;

		long currentTime = DateUtils.currentTime();
		if (currentTime > startTime && currentTime < endTime) {
			// 进行中
			state = 1;
		} else if (currentTime > endTime) {
			// 已结束
			state = 2;
		} else if (currentTime < startTime) {
			// 即将开始
			state = 0;
		}
		return state;
	}

	/**
	 * 
	 * @Title: downTimeFormat
	 * @Description: 倒计时样式的 返回 日 时 分 秒 (用于活动进行中)
	 * @return
	 * @return: int[]
	 */
	public static int[] downTimeFormat(long time) {
		int[] times = new int[4];
		time = time / 1000;
		int second = (int) (time % 60);
		int minute = (int) (time / 60 % 60);
		int hour = (int) (time / 3600 % 24);
		int day = (int) (time / 3600 / 24);
		times[0] = day;
		times[1] = hour;
		times[2] = minute;
		times[3] = second;
		return times;
	}

	/**
	 * 
	 * @Title: startTimeFormat
	 * @Description: 开始时间的样式 返回 月 日 时 分 (用于活动未开始)
	 * @param startTime
	 * @return
	 * @return: int[]
	 */
	
	public static int[] startTimeFormat(long startTime) {
		int[] times = new int[4];
		
		int month = Integer.parseInt(getTime("MM",startTime));
		int day = Integer.parseInt(getTime("dd",startTime));
		int hour = Integer.parseInt(getTime("HH",startTime));
		int minute = Integer.parseInt(getTime("mm",startTime));
		times[0] = month;
		times[1] = day;
		times[2] = hour;
		times[3] = minute;
		return times;
	}

	/**
	 * 
	 * @Title: overTime
	 * @Description: 用于活动已结束
	 * @return
	 * @return: int[]
	 */
	public static int[] overTime() {
		int[] times = new int[4];
		times[0] = 0;
		times[1] = 0;
		times[2] = 0;
		times[3] = 0;
		return times;
	}

	// 根据毫秒值格式化时间
	// yyyy-MM-dd HH:mm:ss
	public static String getTime(String pattern, long time) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date d1 = new Date(time);
		return format.format(d1);
	}
}
