package com.xcubelabs.bhanuprasadm.materialdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcubelabs.bhanuprasadm.materialdemo.R;
import com.xcubelabs.bhanuprasadm.materialdemo.pojo.NavInfo;

import java.util.Collections;
import java.util.List;

/**
 * Created by bhanuprasadm on 28/9/15.
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.NavViewHolder> {

    LayoutInflater inflater;
    List<NavInfo> data = Collections.emptyList();

    public NavAdapter(Context context, List<NavInfo> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public NavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_row, parent, false);
        NavViewHolder holder = new NavViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NavViewHolder holder, int position) {
        NavInfo info = data.get(position);
        holder.ivNav.setImageResource(info.getImageId());
        holder.tvNav.setText(info.getText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NavViewHolder extends RecyclerView.ViewHolder{
        TextView tvNav;
        ImageView ivNav;

        public NavViewHolder(View itemView) {
            super(itemView);
            tvNav = (TextView) itemView.findViewById(R.id.tvNavItem);
            ivNav = (ImageView) itemView.findViewById(R.id.ivNavItem);
        }
    }
}
