/*
 * 
 */
package com.mut.feeapi.utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class Helper.
 * @author TanagornS
 * @version 1.00, 07 August 2018
 */
/**
 * @author supportbkk
 *
 */
public class Helper {
	
	/**
	 * SQL date now.
	 *
	 * @return the string
	 */
	public static String SQLDateNow() {
		java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
//		return sqlDate.toString();
		return "2018-07-14";
	}

	/**
	 * Convert SQL date from another format date
	 *
	 * @param formatDate the format date such dd/MM/yyyy
	 * @param date the date for String parameter
	 * @return the string of date support SQL date format
	 * @throws ParseException the parse exception
	 */
	public static String convertSQLDate(String formatDate, String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(formatDate);
		Date parsed = format.parse(date);
		java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
		return sqlDate.toString();
	}

	/**
	 * To date display for Human date such dd/MM/yyyy
	 *
	 * @param dateString the date string of another format
	 * @return the string date dd/MM/yyyy
	 */
	public static String toDateDisplayFormat(String dateString) {
		if (!isNullOrEmpty(dateString) && dateString.indexOf("-") > 0) {
			if (dateString.length() > 10) {
				String[] arrDteTme = dateString.split(" ");
				dateString = arrDteTme[0];
				String[] arr = dateString.split("-");
				String time = arrDteTme[1].substring(0,
						arrDteTme[1].indexOf(".") > 0 ? arrDteTme[1].indexOf(".") : arrDteTme[1].length());
				return arr[2] + "/" + arr[1] + "/" + arr[0] + " " + time;
			} else {
				String[] arr = dateString.split("-");
				return arr[2] + "/" + arr[1] + "/" + arr[0];
			}
		}
		return dateString;
	}

	/**
	 * To date for sql format.
	 *
	 * @param dateString the date string
	 * @return the string
	 */
	public static String toDateSqlFormat(String dateString) {
		if (isNullOrEmpty(dateString))
			return null;
		if (!isNullOrEmpty(dateString) && dateString.indexOf("/") > 0) {
			if (dateString.length() > 10) {
				String[] arrDteTme = dateString.split(" ");
				dateString = arrDteTme[0];
				String[] arr = dateString.split("/");
				return arr[2] + "-" + arr[1] + "-" + arr[0] + " " + arrDteTme[1];
			} else {
				String[] arr = dateString.split("/");
				return arr[2] + "-" + arr[1] + "-" + arr[0];
			}
		}
		return dateString;
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param text the text
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(Object text) {
		if (text != null) {
			text = text.toString().trim();
			if (!text.toString().isEmpty() && !text.toString().equals("undefined") && !text.toString().equals("null")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Move raw file.
	 *
	 * @param sourceFile the source file
	 * @param destinationFile the destination file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void moveRawFile(String sourceFile, String destinationFile) throws IOException {

		File destFile = new File(destinationFile);
		File srcFile = new File(sourceFile);
		if (destFile.exists() && srcFile.exists()) {
			destFile.delete();
			FileUtils.moveFile(FileUtils.getFile(sourceFile), FileUtils.getFile(destinationFile));
		}else if (!destFile.exists() && srcFile.exists()) {
			FileUtils.moveFile(FileUtils.getFile(sourceFile), FileUtils.getFile(destinationFile));
		}

	}

	/**
	 * Gets the file name and type.
	 *
	 * @param filename the filename
	 * @return the file name and type
	 */
	public static String[] getFileNameAndType(String filename) {
		String[] Arr = filename.split("\\.");
		return Arr;
	}

	/**
	 * Gets the current datetime.
	 *
	 * @return the current datetime
	 */
	public static String getCurrentDatetime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}
	
	public static String messageFormat(String text,Object... arguments) {
		
		String result = "";
		MessageFormat messageFormat = new MessageFormat(text);
		result = messageFormat.format(arguments);
		
		return result;
	}
	public static String getStringStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
		return exceptionAsString;
	}
	public static Float getPercent(int num,int total) {
		Float percent_normal;
		percent_normal = (float)(num*100)/total;
		return percent_normal;
	}
}
