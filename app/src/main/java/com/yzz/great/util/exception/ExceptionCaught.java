package com.yzz.great.util.exception;

/**
 * 异常处理类
 */
public class ExceptionCaught implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public ExceptionCaught(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ShowExceptionActivity.showException(ex);
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, ex);
        }
    }
}
