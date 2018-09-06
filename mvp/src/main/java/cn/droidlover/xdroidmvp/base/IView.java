package cn.droidlover.xdroidmvp.base;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by wanglei on 2016/12/29.
 * <p>
 * 修改：pht   添加网络异常缺省页
 */


public interface IView<P> {

    void bindUI(View rootView);

    void bindEvent();

    void initView(Bundle savedInstanceState);

    void initData();

    void initEvent();

    int getLayoutId();

//    boolean useEventBus();

    P newP();

    P getP();

    void showLoading();

    void hideLoading();

    LifecycleTransformer getLifecycleDestroy();


}
