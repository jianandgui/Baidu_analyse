package cn.edu.swpu.cins.event.analyse.platform.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by lp-deepin on 17-5-10.
 */
public class NoEventException extends BaseException{

    public NoEventException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NoEventException() {
        super("无法找到资源", HttpStatus.NOT_FOUND);
    }
}
