package com.example.internshipproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.Viewholder> {
    ArrayList<Pojo> li;
    Context ct;
    public Myadapter(ArrayList<Pojo> list, FragmentActivity activity) {
        li=list;
        ct=activity;
    }

    @NonNull
    @Override
    public Myadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=  LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_example,parent,false);

        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.Viewholder holder, int position) {

        Glide.with(ct).load(li.get(position).getMyuri()).into(holder.myim);

        holder.myna.setText(li.get(position).getMyname());
        holder.myph.setText(li.get(position).getMyphone());
        Log.i("qwe","vfvg");

    }

    @Override
    public int getItemCount() {
        return li.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView myph,myna;
        ImageView myim;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            myph=itemView.findViewById(R.id.exnumber);
            myna=itemView.findViewById(R.id.exname);
            myim=itemView.findViewById(R.id.eximg);
        }
    }
}
