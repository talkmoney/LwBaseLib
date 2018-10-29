package cn.luwei.mvp.utils;

import com.blankj.utilcode.util.SPUtils;

/**
 * Created by runla on 2018/8/17.
 * 文件描述：
 */

public class LwSharePreUtils {
    public LwSharePreUtils(){
        SPUtils.getInstance().getAll();
    }
}
