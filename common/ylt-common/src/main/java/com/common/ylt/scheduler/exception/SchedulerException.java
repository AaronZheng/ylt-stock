package com.common.ylt.scheduler.exception;

/**
 * Created by zhengchenglei on 2017/3/3.
 */
public class SchedulerException extends Throwable {


	private static final long serialVersionUID = 8071542570172799442L;

	public SchedulerException() {
    }

    public SchedulerException(String message) {
        super(message);
    }

    public SchedulerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchedulerException(Throwable cause) {
        super(cause);
    }

 
}
