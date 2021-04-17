package com.keai.flower;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.keai.flower.activity.BaseActivity;
import com.keai.flower.activity.SettingsActivity;
import com.keai.flower.api.Api;
import com.keai.flower.api.Constants;
import com.keai.flower.api.TtitCallback;
import com.keai.flower.api.flowerData.FlowerData;
import com.keai.flower.api.flowerData.UtilData;
import com.keai.flower.myAdapter.MyAdapter;
import com.keai.flower.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {
    String[] array = new String[]{"重命名", "删除"};
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<FlowerData.DataBean.MachineListBean> data = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAdapter = new MyAdapter(this);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            Log.d("baiopop", "onConfigurationChanged: 横屏");
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            Log.d("baiopop", "onConfigurationChanged: 竖屏");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        swipeRefreshLayout.setOnRefreshListener(this::initFlowerList);

        initFlowerList();
        initClick();
        recyclerView.setAdapter(myAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START|ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                myAdapter.notifyItemMoved(viewHolder.getPosition(),target.getPosition());
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                delDialog(viewHolder,viewHolder.getAdapterPosition());
            }
            Drawable icon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_delete_forever_24);
            Drawable background =new ColorDrawable(Color.BLACK);
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;
                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 10;
                int iconLeft,iconRight,iconTop,iconBottom;
                int backTop,backBottom,backLeft,backRight;
                backTop = itemView.getTop();
                backBottom = itemView.getBottom();
                iconTop = itemView.getTop()+(itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                iconBottom = (iconTop + icon.getIntrinsicHeight());
                if (dX>0){
                    backLeft = itemView.getLeft();
                    backRight = itemView.getLeft() +(int) dX;
                    background.setBounds(backLeft,backTop,backRight,backBottom);
                    iconLeft = itemView.getLeft() + iconMargin;
                    iconRight = iconLeft + icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                }else if (dX<0){
                    backRight = itemView.getRight();
                    backLeft = itemView.getRight() + (int) dX;
                    background.setBounds(backLeft,backTop,backRight,backBottom);
                    iconRight = itemView.getRight() - iconMargin;
                    iconLeft = iconRight - icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                }else {
                    background.setBounds(0,0,0,0);
                    icon.setBounds(0,0,0,0);
                }
                background.draw(c);
                icon.draw(c);
            }
        }).attachToRecyclerView(recyclerView);
    }



    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
           Log.d("baiopop", "onConfigurationChanged: 横屏");
           recyclerView.setLayoutManager(new GridLayoutManager(this,3));
       }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
           Log.d("baiopop", "onConfigurationChanged: 竖屏");
           recyclerView.setLayoutManager(new LinearLayoutManager(this));
       }
    }

    private void initClick() {
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String name) {
                Log.d("baiopop", "onItemClick: " + position + name);

                alertDialog(position, name);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                navigateTo(SettingsActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initFlowerList() {
        String api_key = getStringKey("api_key");
        if (!StringUtils.isEmpty(api_key)) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("api_key", api_key);
            Api.config(Constants.WEB_SITE, params).getRequest(new TtitCallback() {
                @Override
                public void onSuccess(String res) {
                    runOnUiThread(() -> {
                        FlowerData flowerData = new Gson().fromJson(res, FlowerData.class);
                        if (flowerData != null && flowerData.getCode() == 0) {
                            data = flowerData.getData().getMachine_list();
                            myAdapter.submitList(data);
                            if (swipeRefreshLayout.isRefreshing()) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });

                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            navigateTo(SettingsActivity.class);
        }
    }

    private void RenameFlowerList(int num, String name) {
        String api_key = getStringKey("api_key");
        HashMap<String, Object> params = new HashMap<>();
        params.put("api_key", api_key);
        params.put("mac", data.get(num).getMac());
        params.put("uname", name);
        Api.config(Constants.WEB_SITE_NAME, params).getRequest(new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                UtilData utilData = new Gson().fromJson(res, UtilData.class);
                if (utilData != null && utilData.getCode() == 0) {
                    showToastSync("修改成功！！");
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void delFlowerList(int num) {
        String api_key = getStringKey("api_key");
        HashMap<String, Object> params = new HashMap<>();
        params.put("api_key", api_key);
        params.put("mac", data.get(num).getMac());
        Api.config(Constants.WEB_SITE_DELETE, params).getRequest(new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                UtilData utilData = new Gson().fromJson(res, UtilData.class);
                if (utilData != null && utilData.getCode() == 0) {
                    showToastSync("修改成功！！");
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    public void alertDialog(int position, String name) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置标题
        builder.setTitle("设备名称："+name).
                // 设置可选择的内容，并添加点击事件
                        setItems(array, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // which代表的是选择的标签的序列号
                        switch (which) {
                            case 0:
                                uNameDialog(position);
                                break;
                            case 1:
                                delDialog(null,position);
                                break;
                        }
                    }
                }).
                create().show();

    }

    public void uNameDialog(int position) {

        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).
                setTitle("输入新名称").
                setView(et).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取输入的字符
                        String name = et.getText().toString();
                        RenameFlowerList(position, name);
                    }
                }).setNegativeButton("取消", null).setCancelable(false).create().show();

    }

    public void delDialog(RecyclerView.ViewHolder viewHolder,int position) {
        new AlertDialog.Builder(this).setTitle("提示").setMessage("您确定要删除设备吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myAdapter.notifyItemRemoved(position);
                delFlowerList(position);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (viewHolder != null){
                    myAdapter.notifyDataSetChanged();
                }
            }
        }).setCancelable(false).create().show();
    }
}
