package basedomain.utility;

import basedomain.common.ApiResponse;
import basedomain.constant.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ResponseUtil {

    private ResponseUtil() {
        //
    }

    public static ResponseEntity<Object> build(HttpStatus httpStatus, String message, Object data) {
        return new ResponseEntity<>(ApiResponse.builder()
                .timestamp(LocalDateTime.now(ZoneId.of(AppConstant.APP_TIMEZONE)))
                .statusCode(httpStatus.value())
                .message(message)
                .data(data), httpStatus);
    }

}
