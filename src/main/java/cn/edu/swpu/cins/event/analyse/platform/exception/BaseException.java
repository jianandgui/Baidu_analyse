package cn.edu.swpu.cins.event.analyse.platform.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception {

    protected HttpStatus status;

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    @Override
    public String toString() {
        return "BaseException{" +
                "status=" + status +
                '}';

    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
