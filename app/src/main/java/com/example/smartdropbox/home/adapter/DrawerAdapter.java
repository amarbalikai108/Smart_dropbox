package com.example.smartdropbox.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartdropbox.R;
import com.example.smartdropbox.home.model.DrawerModel;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.myViewHolder> {
    Context context;
    List<DrawerModel> mData;
    private OnItemClickListener listener;

    public DrawerAdapter(Context context, List<DrawerModel> data, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.mData = data;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_drawer, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {
        holder.nav.setText(mData.get(position).getMenuName());

        if (mData.get(position).getIsSelected()) {
            holder.tvColor.setBackgroundColor(ContextCompat.getColor(context,
                    R.color.drawer_color));
        } else {
            holder.tvColor.setBackgroundColor(ContextCompat.getColor(context,
                    R.color.white));
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView nav, tvColor;
        LinearLayout mainLayout;

        public myViewHolder(View itemView) {
            super(itemView);
            nav = itemView.findViewById(R.id.tvMenu);
            tvColor = itemView.findViewById(R.id.tvColor);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}