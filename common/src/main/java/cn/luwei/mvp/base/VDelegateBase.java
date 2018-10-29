package cn.luwei.mvp.base;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wanglei on 2016/12/29.
 */

public class VDelegateBase implements IVDelegate {

    private Context context;

    private VDelegateBase(Context context) {
        this.context = context;
    }

    public static IVDelegate create(Context context) {
        return new VDelegateBase(context);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }


    private static Toast mToast = null;

    @Override
    public void toastShort(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mToast.show();
    }

    @Override
    public void toastLong(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
