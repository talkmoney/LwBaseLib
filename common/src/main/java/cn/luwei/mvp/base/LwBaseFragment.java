package cn.luwei.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.Unbinder;
import cn.luwei.mvp.XDroidConf;
import cn.luwei.mvp.utils.KnifeUtils;

/**
 *
 * @author wanglei
 * @date 2017/1/26
 */

public abstract class LwBaseFragment<P extends IPresent>
        extends RxFragment implements IView<P> {
    protected AppCompatActivity hostActivity;
    private IVDelegate IVDelegate;
    private P p;

    private RxPermissions rxPermissions;
    private Unbinder unbinder;

    private View mRootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(this.getLayoutId(), container, false);
        bindUI(mRootView);
//        if (useEventBus()) {
//            RxBusImpl.get().register(this);
//        }
        bindEvent();
        initView(savedInstanceState);
        initData();
        initEvent();
        return mRootView;
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = KnifeUtils.bind(this, rootView);
    }

    @Override
    public void bindEvent() {

    }

    public IVDelegate getIVDelegate() {
        if (IVDelegate == null) {
            IVDelegate = VDelegateBase.create(hostActivity);
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
    public void onDestroy() {
        super.onDestroy();
//        if (useEventBus()) {
//            RxBusImpl.get().unregister(this);
//        }
        if (getP() != null) {
            getP().detachV();
        }
        getIVDelegate().destroy();

        p = null;
        IVDelegate = null;
    }

    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(getActivity());
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }

//    @Override
//    public boolean useEventBus() {
//        return false;
//    }

    @Override
    public LifecycleTransformer getLifecycleDestroy() {
        return this.bindUntilEvent(FragmentEvent.DESTROY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = (AppCompatActivity) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.hostActivity = (AppCompatActivity) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        hostActivity = null;
    }

}
