package com.bosch.validation.fw.utils;

import java.sql.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class JodaUtils {

	public static String getDateTimeToString() {
		DateTime dateTime = new DateTime();
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd_HH-mm-ss");
		String result = dateTimeFormat.print(dateTime);
		return result;
	}
	
	public static String getDateTimeToString(DateTime dateTime, DateTimeFormatter dateTimeFormat) {
		String result = dateTimeFormat.print(dateTime);
		return result;
	}

	public static String getDateTimeToString(DateTime dateTime) {
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String result = dateTimeFormat.print(dateTime);
		return result;
	}

	public static DateTime getDateTime() {
		DateTime dateTime = new DateTime();
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String result = dateTimeFormat.print(dateTime);
		return dateTimeFormat.parseDateTime(result);
	}

	public static DateTime getDateTime(String dateTime) {
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		return dateTimeFormat.parseDateTime(dateTime);
	}

	public static DateTime getDateTime(Date date) {
		DateTime dateTime = new DateTime(date);
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String result = dateTimeFormat.print(dateTime);
		return dateTimeFormat.parseDateTime(result);
	}

	public static DateTime getDateTime(java.util.Date date) {
		DateTime dateTime = new DateTime(date);
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String result = dateTimeFormat.print(dateTime);
		return dateTimeFormat.parseDateTime(result);
	}

	/*public static String getDateTimeGMT7() {
		DateTime dateTime = new DateTime();
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
				.withZone(DateTimeZone.forID("Asia/Ho_Chi_Minh"));
		String result = dateTimeFormat.print(dateTime);
		return result;
	}

	public static String getDateTimeGMT7(DateTime dateTime) {
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
				.withZone(DateTimeZone.forID("Asia/Ho_Chi_Minh"));
		String result = dateTimeFormat.print(dateTime);
		return result;
	}*/

	public static String getDuration(DateTime startTime) {
		try {
			DateTime endTime = getDateTime();
			Duration duration = new Interval(startTime, endTime).toDuration();
			Period period = duration.toPeriod();
			PeriodFormatter minutesAndSeconds = new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2)
					.appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").appendSeconds().toFormatter();
			String result = minutesAndSeconds.print(period);
			return result;
		} catch (Exception e) {
			return "00:00:00";
		}
	}

	public static int getDurationBySecs(DateTime startTime) {
		int count = 0;
		try {
			DateTime endTime = getDateTime();
			Duration duration = new Duration(startTime, endTime);
			count = Integer.valueOf(duration.toStandardSeconds().getSeconds());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public static int getDurationBySecs(DateTime startTime, DateTime endTime) {
		int count = 0;
		try {
			Duration duration = new Duration(startTime, endTime);
			count = Integer.valueOf(duration.toStandardSeconds().getSeconds());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
