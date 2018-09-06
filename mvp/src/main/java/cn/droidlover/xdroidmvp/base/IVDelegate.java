package cn.droidlover.xdroidmvp.base;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface IVDelegate {

    void resume();

    void pause();

    void destroy();

    void toastShort(String msg);

    void toastLong(String msg);
}
