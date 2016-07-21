package org.seckill.exception;

/**
 * Created by suollk on 2016/7/19.
 */
public class SeckillExpection extends  Exception {
    public SeckillExpection(String message) {
        super(message);
    }

    public SeckillExpection(String message, Throwable cause) {
        super(message, cause);
    }
}
