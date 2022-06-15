package io.github.palexdev.mfxcore.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Little utils class to convert a throwable stack trace to a String.
 */
public class ExceptionUtils {

	private ExceptionUtils() {
	}

	/**
	 * Converts the given exception stack trace to a String
	 * by using a {@link StringWriter} and a {@link PrintWriter}.
	 */
	public static String getStackTraceString(Throwable ex) {
		StringWriter sw = new StringWriter();
		sw.flush();
		ex.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	/**
	 * Returns a formatted string in the java style exception format.
	 */
	public static String formatException(Throwable ex) {
		StringBuilder sb = new StringBuilder();
		sb.append(ex.getMessage());
		sb.append("\n");
		StackTraceElement[] trace = ex.getStackTrace();
		for (StackTraceElement stackTraceElement : trace) {
			sb.append("\t");
			sb.append(stackTraceElement);
			sb.append("\n");
		}
		return sb.toString();
	}
}
