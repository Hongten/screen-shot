/**
 * 
 */
package com.b510.hongten.screenshot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hongten
 * @created 24 Feb, 2015
 */
public class ScreenShotUtil {

	public static String getDate(String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String date = simpleDateFormat.format(new Date());
		return date;
	}
}