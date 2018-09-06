package cn.droidlover.xdroidmvp.net.exception;

/**
 * Created by Pht on 2016/12/22.
 */

public class NetException extends RuntimeException {
    public int code;
    public String message;

    public NetException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
