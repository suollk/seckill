package org.seckill.exception;

/**
 * 重复秒杀异常(运行期  编译器   运行期   )
 * Created by suollk on 2016/7/19.
 */
public class RepeatKillException extends SeckillExpection{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
