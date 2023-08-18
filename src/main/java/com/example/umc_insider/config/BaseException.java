package com.example.umc_insider.config;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends Exception {
    private BaseResponseStatus status;

    public BaseException(String errorWhileGettingUserReferences, BaseException e) {
    }

    public BaseResponseStatus getStatus() {
        return this.status;
    }

    public void setStatus(final BaseResponseStatus status) {
        this.status = status;
    }

    public BaseException(final BaseResponseStatus status) {
        this.status = status;
    }
}
