package gr.york.mobiledev2026.data.remote;

public class ApiResponse<T> {
    private T data;
    private String error;
    private int code;

    public boolean isSuccess() {
        return error == null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
