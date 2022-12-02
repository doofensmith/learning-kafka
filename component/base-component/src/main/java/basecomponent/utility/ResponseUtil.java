package basecomponent.utility;

import basecomponent.common.ApiResponse;
import basecomponent.constant.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ResponseUtil {

    private ResponseUtil() {
        //
    }

    public static ResponseEntity<Object> build(HttpStatus httpStatus, String message, Object data) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTimestamp(LocalDateTime.now(ZoneId.of(AppConstant.APP_TIMEZONE)));
        apiResponse.setStatusCode(httpStatus.value());
        apiResponse.setMessage(message);
        apiResponse.setData(data);

        return new ResponseEntity<>(apiResponse, httpStatus);
    }

}
