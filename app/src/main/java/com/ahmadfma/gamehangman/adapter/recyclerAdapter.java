package com.ahmadfma.gamehangman.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadfma.gamehangman.R;
import com.ahmadfma.gamehangman.activity.KategoriActivity;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.holder> {
    ArrayList<String> List1;
    ArrayList<String> listScr;
    int checkPoint_level;

    public recyclerAdapter(ArrayList<String> list1, ArrayList<String> list2, int checkPoint_lvl) {
        this.List1 = list1;
        this.listScr = list2;
        this.checkPoint_level = checkPoint_lvl;
    }

    @NonNull
    @Override
    public recyclerAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.level_layout, parent, false);
        return new holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.holder holder, int position) {
        holder.lvl.setText("Level ".concat(String.valueOf(position+1)));
        holder.scr.setText("Best Score : ".concat(listScr.get(position)));
        holder.lvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KategoriActivity.levelPilihan.setText("Level Pilihan : ".concat(String.valueOf(position+1)));
                KategoriActivity.levelSelected = position;
                KategoriActivity.play.setEnabled(true);
            }
        });
        if(position > checkPoint_level) {
            holder.lvl.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return List1.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        private Button lvl;
        private TextView scr;


        holder(View view) {
            super(view);
            lvl = view.findViewById(R.id.lvl_btn);
            scr = view.findViewById(R.id.score);
        }
    }

}
