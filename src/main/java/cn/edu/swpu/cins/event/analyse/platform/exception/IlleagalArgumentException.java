package cn.edu.swpu.cins.event.analyse.platform.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by lp-deepin on 17-5-11.
 */
public class IlleagalArgumentException extends BaseException {

    public IlleagalArgumentException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public IlleagalArgumentException() {
        super("参数不合法", HttpStatus.BAD_REQUEST);
    }

}
