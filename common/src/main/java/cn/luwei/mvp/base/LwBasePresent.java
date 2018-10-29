package cn.luwei.mvp.base;

import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *
 * @author wanglei
 * @date 2016/12/29
 * <p>
 * change  Pht  on  2017/9/27
 * 增加对rxjava的生命管理
 */

public class LwBasePresent<V extends IView> implements IPresent<V> {
    private V v;

    //管理RxJava流的生命周期
    private CompositeDisposable mCompositeSubscription;


    @Override
    public void attachV(V view) {
        v = view;
    }

    @Override
    public void detachV() {

        //释放rx订阅者
        onUnsubscribe();

        v = null;
    }

    protected V getV() {
        return v;
    }


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && !mCompositeSubscription.isDisposed()) {
            mCompositeSubscription.dispose();
        }
    }


    public <T> void addSubscription(Flowable<T> flowable, Subscriber<T> subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        if (getV() != null) {
            mCompositeSubscription.add((Disposable) flowable.compose(getV().getLifecycleDestroy()).subscribeWith(subscriber));
        }
    }


}
