package com.luwei.lwbaselib.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.lwbaselib.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.luwei.mvp.ui.dialog.ConfirmListener;
import com.luwei.lwbaselib.dialog.CustomDialog;

/**
 * dialog 的使用示例
 */
public class DialogActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);

        List<String> listStr = new ArrayList<>();
        listStr.add("CustomDialog 顶部显示");
        listStr.add("CustomDialog 中部显示");
        listStr.add("CustomDialog 底部显示");

        adapter = new ArrayAdapter(this, listStr);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void onItemClick(int pos) {
        switch (pos) {
            case 0:
                showTopCustomDialog();
                break;
            case 1:
                showCenterCustomDialog();
                break;
            case 2:
                showBottomCustomDialog();
                break;
            default:
                break;
        }
    }

    private void showTopCustomDialog() {
        CustomDialog.newInstance()
                .setContent("这是没有标题的对话框")
                .setCancel("取消")
                .setConfirm("确认")
                .setConfirmColor(Color.BLUE)
                .setCanCancel(true)
                .setTransparent(true)
                .setGravity(Gravity.TOP)
                .setConfirmLitener(new ConfirmListener() {
                    @Override
                    public void onClickConfirm() {
                        Toast.makeText(DialogActivity.this, "确认", Toast.LENGTH_SHORT).show();
                    }
                })
                .showDialog(DialogActivity.this);
    }

    private void showCenterCustomDialog() {
        CustomDialog.newInstance()
                .setTitle("我是标题")
                .setContent("这是有标题的对话框")
                .setCancel("取消")
                .setConfirm("确认")
                .setConfirmColor(Color.BLUE)
                .setCanCancel(false)
                .setTransparent(false)
                .setGravity(Gravity.CENTER)
                .setConfirmLitener(new ConfirmListener() {
                    @Override
                    public void onClickConfirm() {
                        Toast.makeText(DialogActivity.this, "确认", Toast.LENGTH_SHORT).show();
                    }
                })
                .showDialog(DialogActivity.this);
    }

    private void showBottomCustomDialog() {
        CustomDialog.newInstance()
                .setTitle("我是标题")
                .setContent("这是有标题的对话框")
                .setCancel("取消")
                .setConfirm("确认")
                .setConfirmColor(Color.BLUE)
                .setCanCancel(false)
                .setTransparent(true)
                .setGravity(Gravity.BOTTOM)
                .setConfirmLitener(new ConfirmListener() {
                    @Override
                    public void onClickConfirm() {
                        Toast.makeText(DialogActivity.this, "确认", Toast.LENGTH_SHORT).show();
                    }
                })
                .showDialog(DialogActivity.this);
    }

    public class ArrayAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private Context context;
        private List<String> dataList;

        public ArrayAdapter(Context context, List<String> list) {
            this.context = context;
            this.dataList = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_array, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.text.setTag(position);
            holder.text.setText(dataList.get(position));
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick((Integer) v.getTag());
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList == null ? 0 : dataList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
