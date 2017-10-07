package cn.edu.swpu.cins.event.analyse.platform.exception;

import org.springframework.http.HttpStatus;

public class OperationFailureException extends BaseException {
    public OperationFailureException() {
        super("操作失败", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public OperationFailureException(String msg) {
        super(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
