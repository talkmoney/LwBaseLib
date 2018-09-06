package cn.droidlover.xdroidmvp.net.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import cn.droidlover.xdroidmvp.base.LwBaseBean;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Pht on 2016/12/21.
 * change by Pht on 2017/9/26
 * <p>
 * 统一返回格式 只返回data节点的数据
 */

final class JsonResponseBodyConverter<T extends Object> implements Converter<ResponseBody, T> {


    private final Gson gson;
    private final TypeAdapter<T> adapter;


    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }


    @Override
    public T convert(ResponseBody value) {

        String body;

        try {
            body = value.string();
            return adapter.fromJson(body);
        } catch (Exception e) {
            LwBaseBean baseBean = new LwBaseBean();
            baseBean.setMessage("body is null");
            baseBean.setNull(true);
            return (T) baseBean;
        } finally {
            value.close();
        }


    }


}
