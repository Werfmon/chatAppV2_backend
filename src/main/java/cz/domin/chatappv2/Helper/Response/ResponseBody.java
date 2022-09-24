package cz.domin.chatappv2.Helper.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseBody<T> {
    private T data;
    private String message;
}
