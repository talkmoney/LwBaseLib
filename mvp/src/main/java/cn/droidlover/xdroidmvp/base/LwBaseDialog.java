package cn.droidlover.xdroidmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import cn.droidlover.xdroidmvp.R;


/**
 * @author runla
 * @date 2018/8/1
 * 文件描述：dialog 基础封装
 */

public abstract class LwBaseDialog extends DialogFragment {

    private int mGravity = Gravity.BOTTOM;
    private boolean mCanCancel;
    private boolean mTransparent;
    private int mHorizonalMargin = 0;
    private int mVerticalMargin = 0;
    private View mDismissView;


    protected static final String TAG = LwBaseDialog.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(1, R.style.dialog);

        if (savedInstanceState != null) {
            this.mHorizonalMargin = savedInstanceState.getInt("mHorizonalMargin");
            this.mVerticalMargin = savedInstanceState.getInt("mVerticalMargin");
            this.mGravity = savedInstanceState.getInt("mGravity");
            this.mTransparent = savedInstanceState.getBoolean("mTransparent");
            this.mCanCancel = savedInstanceState.getBoolean("mCanCancel");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mHorizonalMargin", mHorizonalMargin);
        outState.putInt("mVerticalMargin", mVerticalMargin);
        outState.putInt("mGravity", mGravity);
        outState.putBoolean("mCanCancel", mCanCancel);
        outState.putBoolean("mTransparent", mTransparent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //设置背景透明
        View view = inflater.inflate(getLayoutId(), container, false);
        onCreateView(ViewHolder.create(view), this);
        mDismissView = view.findViewById(setDismissView());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
        initEvent();
    }

    private void initParams() {
        Window window = this.getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = mGravity;

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // 设置 dialog 是否可以取消
        this.setCanCancel(mCanCancel);
        getDialog().setCanceledOnTouchOutside(mCanCancel);

        window.setAttributes(params);

        if (params.gravity == Gravity.CENTER && mHorizonalMargin == 0) {
            mHorizonalMargin = dp2px(45);
        }
        window.getDecorView().setPadding(mHorizonalMargin, mVerticalMargin, mHorizonalMargin, mVerticalMargin);

        if (mTransparent) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }


    }

    private void initEvent() {

        if (mDismissView != null) {
            mDismissView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LwBaseDialog.this.dismiss();
                }
            });
        }
    }



    public LwBaseDialog setTransparent(boolean mTransparent) {
        this.mTransparent = mTransparent;
        return this;
    }

    public LwBaseDialog setGravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }

    public LwBaseDialog setCanCancel(boolean mCanCancel) {
        this.mCanCancel = mCanCancel;
        return this;
    }

    /**
     * 单位为 dp
     *
     * @param margin
     * @return
     */
    public LwBaseDialog setHorizonalMargin(int margin) {
        this.mHorizonalMargin = dp2px(margin);
        return this;
    }


    /**
     * 单位为 dp
     *
     * @param margin
     * @return
     */
    public LwBaseDialog setVertical(int margin) {
        this.mVerticalMargin = dp2px(margin);
        return this;
    }

    public int dp2px(float dipValue) {
        float scale = getActivity().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }



    public void showDialog(AppCompatActivity appCompatActivity) {
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        if (!appCompatActivity.isFinishing()
                && !this.isAdded()) {

            fragmentManager.beginTransaction()
                    .add(this, TAG)
                    .commitAllowingStateLoss();

        }
    }

    public abstract int getLayoutId();

    public abstract void onCreateView(ViewHolder viewHolder, LwBaseDialog dialog);

    /**
     * 设置点击消失的 view id
     *
     * @return
     */
    public abstract int setDismissView();
}
