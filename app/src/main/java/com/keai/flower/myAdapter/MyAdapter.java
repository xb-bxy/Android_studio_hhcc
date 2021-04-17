package com.keai.flower.myAdapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.keai.flower.R;
import com.keai.flower.api.flowerData.FlowerData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class MyAdapter extends ListAdapter<FlowerData.DataBean.MachineListBean,MyAdapter.MyViewHolder> {

    private final Bitmap mix;
    private final Bitmap max;
    HashMap<Integer,Integer> p = new HashMap<>();

    public MyAdapter(Context context) {
        super(new DiffUtil.ItemCallback<FlowerData.DataBean.MachineListBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull FlowerData.DataBean.MachineListBean oldItem, @NonNull FlowerData.DataBean.MachineListBean newItem) {
                return oldItem.getMac().equals(newItem.getMac()) ;
            }

            @Override
            public boolean areContentsTheSame(@NonNull FlowerData.DataBean.MachineListBean oldItem, @NonNull FlowerData.DataBean.MachineListBean newItem) {
                if (oldItem.getName()!=null){
                    if (
                            oldItem.getAh() == newItem.getAh()&&
                                    oldItem.getAt()==newItem.getAt()&&
                                    oldItem.getSm()==newItem.getSm()&&
                                    oldItem.getName().equals(newItem.getName())&&
                                    oldItem.getUp().equals(newItem.getUp())){
                        return true;
                    }else {
                        return false;
                    }
                }else {
                    if (
                            oldItem.getAh() == newItem.getAh()&&
                                    oldItem.getAt()==newItem.getAt()&&
                                    oldItem.getSm()==newItem.getSm()&&
                                    oldItem.getUp().equals(newItem.getUp())){
                        return true;
                    }else {
                        return false;
                    }
                }


            }
        });
        int blue =context.getResources().getColor(R.color.blue);
        int red =context.getResources().getColor(R.color.red);
        int pick =context.getResources().getColor(R.color.pick);
        int yellow =context.getResources().getColor(R.color.yellow);
        int write_blue =context.getResources().getColor(R.color.write_blue);
        int green =context.getResources().getColor(R.color.green);
        p.put(0,blue);p.put(1,red);p.put(2,pick);p.put(3,yellow);p.put(4,write_blue);p.put(5,green);
        this.mix = BitmapFactory.decodeResource(context.getResources(), R.mipmap.trl30);
        this.max = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tru30);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cord_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FlowerData.DataBean.MachineListBean flowerData = getItem(position);
        Random random = new Random();
        holder.cardView.setCardBackgroundColor(p.get(random.nextInt(5)));
        holder.tv_CordTitle.setText(flowerData.getName());
        holder.tv_Soil.setText(flowerData.getSm() + "%");
        holder.tv_Hum.setText(flowerData.getAt() + "%");
        holder.tv_Temp.setText(flowerData.getAh() + "%");
        if (flowerData.getUp()!=null){
            Date date = new Date(Long.parseLong(flowerData.getUp()) * 1000);
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            holder.tv_UpdateTime.setText("更新时间：" + format.format(date));
        }
        if (flowerData.getSm() <= 30) {
            holder.img_Soil.setImageBitmap(mix);
        } else {
            holder.img_Soil.setImageBitmap(max);
        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position, flowerData.getName());
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_CordTitle, tv_Soil, tv_Hum, tv_Temp, tv_UpdateTime;
        ImageView img_Soil;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_CordTitle = itemView.findViewById(R.id.tv_CordTitle);
            tv_Soil = itemView.findViewById(R.id.tv_Soil);
            tv_Hum = itemView.findViewById(R.id.tv_Hum);
            tv_Temp = itemView.findViewById(R.id.tv_Temp);
            tv_UpdateTime = itemView.findViewById(R.id.tv_UpdateTime);
            img_Soil = itemView.findViewById(R.id.img_Soil);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String name);
    }


    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
