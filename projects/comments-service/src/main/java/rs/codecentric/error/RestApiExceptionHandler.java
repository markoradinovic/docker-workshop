package rs.codecentric.error;

import com.google.common.collect.Maps;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorMessage> apiExceptionHandler(ApiException e) throws Exception {
        return new ResponseEntity(new ErrorMessage(e.getMessage()), getHttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorMessage> securityExceptionHandler(SecurityException e) throws Exception {
        return new ResponseEntity(new ErrorMessage(e.getMessage()), getHttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<ErrorMessage>>> validationErrorHandler(org.springframework.web.bind
            .MethodArgumentNotValidException e) {
        Map<String, List<ErrorMessage>> result = Maps.newHashMap();
        result.put("errors", e.getBindingResult().getAllErrors().stream().map(error -> new ErrorMessage(error
                .getDefaultMessage())).collect(Collectors.toList()));
        return new ResponseEntity(result, getHttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(Exception e) throws Exception {
        return new ResponseEntity(new ErrorMessage(e.getMessage()), getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

}
