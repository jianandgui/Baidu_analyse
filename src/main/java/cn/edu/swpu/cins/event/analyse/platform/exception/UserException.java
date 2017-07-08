package cn.edu.swpu.cins.event.analyse.platform.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by muyi on 17-7-8.
 */
public class UserException extends BaseException {

    public UserException(String message, HttpStatus status) {
        super(message, status);
    }


}
