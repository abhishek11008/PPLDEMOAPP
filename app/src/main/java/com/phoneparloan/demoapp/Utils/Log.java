package com.phoneparloan.demoapp.Utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * Created by abhisheksingh on 24/01/18.
 */

public class Log {
    //TODO log false on release
    private static final boolean LOG = true;
    private static boolean CHROME_STHETHO_LOG_ENABLED = false;

    public static boolean isChromeSthethoLogEnabled() {
        return CHROME_STHETHO_LOG_ENABLED;
    }

    public static boolean isLogEnabled() {
        return LOG;
    }

    public static void i(String tag, String string) {
        if (LOG) {
            if (string != null) android.util.Log.i(tag, string);
            else android.util.Log.i(tag, "null");
        }
    }

    public static void e(String tag, String string) {
        if (LOG) {
            if (string != null) android.util.Log.e(tag, string);
            else android.util.Log.e(tag, "null");
        }
    }

    public static void d(String tag, String string) {
        if (LOG) {
            if (string != null) android.util.Log.d(tag, string);
            else android.util.Log.d(tag, "null");
        }
    }

    public static void v(String tag, String string) {
        if (LOG) {
            if (string != null) android.util.Log.v(tag, string);
            else android.util.Log.v(tag, "null");
        }
    }

    public static void w(String tag, String string) {
        if (LOG) {
            if (string != null) android.util.Log.w(tag, string);
            else android.util.Log.w(tag, "null");
        }
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     *
     * @param tr An exception to log
     */
    public static String getStackTraceString(Throwable tr) {
        if (LOG) {
            if (tr == null) {
                return "";
            }

            // This is to reduce the amount of log spew that apps do in the non-error
            // condition of the network being unavailable.
            Throwable t = tr;
            while (t != null) {
                if (t instanceof UnknownHostException) {
                    return "";
                }
                t = t.getCause();
            }

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            tr.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }
        return "Logs-false";
    }
}
