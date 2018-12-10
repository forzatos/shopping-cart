package com.shoppingcart.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT);

	public static LocalDateTime getLocalDateTimeFromString(String dateToFormat) {
		return LocalDateTime.parse(dateToFormat, DateUtil.DATE_TIME_FORMATTER);
	}

}
