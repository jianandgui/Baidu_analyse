package cn.edu.swpu.cins.event.analyse.platform.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by muyi on 17-6-6.
 */
public class UpdateException extends BaseException{

    public UpdateException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
    public UpdateException(){
        super("服务器异常",HttpStatus.FORBIDDEN);
    }
}
