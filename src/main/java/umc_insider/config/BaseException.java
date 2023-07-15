package umc_insider.config;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends Exception {
    private BaseResponseStatus status;

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
