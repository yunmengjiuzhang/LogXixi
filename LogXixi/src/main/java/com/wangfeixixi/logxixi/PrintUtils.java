package com.wangfeixixi.logxixi;

import android.support.annotation.NonNull;

import static com.wangfeixixi.logxixi.Utils.checkNotNull;

class PrintUtils {
    public static String getStackTraceInfo() {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        sb.append(Thread.currentThread().getName() + ":");
        int methodCount = 1;
        int stackOffset = getStackOffset(trace);
        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }

//            sb.append(trace[stackIndex].getFileName() + ":");
            sb.append(trace[stackIndex].getClassName() + ":");
            sb.append(trace[stackIndex].getMethodName() + ":");
            sb.append(trace[stackIndex].getLineNumber());
        }
        return sb.toString();
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private static int getStackOffset(@NonNull StackTraceElement[] trace) {
        checkNotNull(trace);
        //The minimum stack trace index, starts at this class after two native calls.
        int MIN_STACK_OFFSET = 5;
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LogPrinter.class.getName()) && !name.equals(LogAndroid.class.getName())) {
                return --i;
            }
        }
        return -1;
    }
}
