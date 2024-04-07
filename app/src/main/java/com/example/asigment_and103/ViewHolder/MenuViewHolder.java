package com.example.asigment_and103.ViewHolder;

//import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.asigment_and103.Interfece.ItemClickListener;
import com.example.asigment_and103.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txttensp,txtgiasp;
    public ImageView img_home;
    private ItemClickListener itemClickListener;
    public MenuViewHolder(View itemView) {
        super(itemView);
        txttensp = itemView.findViewById(R.id.txt_tenSP);
        txtgiasp = itemView.findViewById(R.id.txt_giaSP);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
