package cn.edu.swpu.cins.event.analyse.platform.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by lp-deepin on 17-5-15.
 */
public class RepeatlyCollectException extends BaseException{
    public RepeatlyCollectException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public RepeatlyCollectException() {
        super("重复归集", HttpStatus.BAD_REQUEST);
    }
}
