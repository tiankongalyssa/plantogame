package entity;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Result {


    private boolean flag;
    private Integer code;
    @JsonInclude(JsonInclude.Include.NON_NULL)  //为空不返回
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)  //为空不返回
    private Object data;


    public Result(boolean flag, Integer code, String message, Object data) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
