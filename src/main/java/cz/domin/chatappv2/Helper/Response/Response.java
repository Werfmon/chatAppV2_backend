package cz.domin.chatappv2.Helper.Response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.List;


public class Response<T> extends ResponseEntity {
    public Response() {
        super(HttpStatus.OK);
    }
    public Response(HttpStatus status) {
        super(status);
    }

    public Response(T data, HttpStatus status, String message) {
        super(new ResponseBody<>(data, message), status);
    }

    public Response(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }
}
