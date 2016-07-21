package org.seckill.exception;

/**
 * 秒杀关闭异常(运行时)
 * Created by suollk on 2016/7/19.
 */
public class SeckillCloseException extends SeckillExpection {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
