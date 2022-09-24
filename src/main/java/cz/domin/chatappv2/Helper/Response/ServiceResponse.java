package cz.domin.chatappv2.Helper.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceResponse<T>{
    public static final Boolean OK = true;
    public static final Boolean ERROR = false;

    private T data = null;
    private String message = null;
    private Boolean status = OK;
}
