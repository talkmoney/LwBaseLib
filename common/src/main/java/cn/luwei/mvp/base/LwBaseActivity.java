package cn.luwei.mvp.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.Unbinder;
import cn.luwei.mvp.XDroidConf;
import cn.luwei.mvp.utils.KnifeUtils;

/**
 * Created by wanglei on 2016/12/29.
 */

public abstract class LwBaseActivity<P extends IPresent> extends RxAppCompatActivity implements IView<P> {

    public static final String TAG = "LwBaseActivity";

    private IVDelegate IVDelegate;
    private P p;
    protected Activity context;
    private RxPermissions rxPermissions;
    private Unbinder unbinder;
    private boolean isSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.adaptScreen4VerticalSlide(this, 360);
        Log.e(TAG, "current: " + LwBaseActivity.class.getSimpleName());
        context = this;
        isSelected = setIsSetStatus();
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(null);
            bindEvent();
        }
        initView(savedInstanceState);
        initData();
        initEvent();
        if (isSelected) {
            tranDecorView();
        }
    }


    public void tranDecorView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = KnifeUtils.bind(this);
    }

    protected IVDelegate getIVDelegate() {
        if (IVDelegate == null) {
            IVDelegate = VDelegateBase.create(context);
        }
        return IVDelegate;
    }

    @Override
    public P getP() {
        if (p == null) {
            p = newP();
            if (p != null) {
                p.attachV(this);
            }
        }
        return p;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getIVDelegate().resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getIVDelegate().pause();
    }


    @Override
    protected void onDestroy() {
        if (getP() != null) {
            getP().detachV();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
        getIVDelegate().destroy();
        p = null;
        IVDelegate = null;
        super.onDestroy();
    }

    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }

    @Override
    public void bindEvent() {

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public abstract boolean setIsSetStatus();

    @Override
    public LifecycleTransformer getLifecycleDestroy() {
        return this.bindUntilEvent(ActivityEvent.DESTROY);
    }


}
