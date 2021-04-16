package com.ahmadfma.gamehangman.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadfma.gamehangman.R;
import com.ahmadfma.gamehangman.adapter.*;
import com.ahmadfma.gamehangman.dataModel.DataHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class SejarahFragment extends Fragment {
    private static ArrayList<String> listPertanyaan;
    private ArrayList<String> listScr;

    private View view;
    private com.ahmadfma.gamehangman.adapter.recyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    public static int checkPoint_level = 0;

    static String[] id;
    static String[] score;
    private DataHelper dataHelper;
    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sejarah, container,false);
        initVariable();
        initListener();

        loadScore();
        loadCheckPoint();

        recyclerAdapter = new recyclerAdapter(listPertanyaan, listScr, checkPoint_level);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    private void initVariable() {
        listPertanyaan = model.getListPertanyaan("SEJARAH");
        listScr = model.getListScore("SEJARAH");
        recyclerView = view.findViewById(R.id.recy_sejarah);
    }

    private void initListener() {

    }

    public static String getData(int position) {
        String data = listPertanyaan.get(position);
        return data;
    }

    private void loadScore() {
        System.out.println("LOAD SCORE");
        dataHelper = new DataHelper(getContext());
        SQLiteDatabase sqLiteDatabase = dataHelper.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM score_SEJARAH", null);
        id = new String[cursor.getCount()];
        score = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            model.updateScore("SEJARAH", i, cursor.getString(1));
            id[i] = cursor.getString(0);
            score[i] = cursor.getString(1);
        }
        System.out.println("LEVEL SEJARAH : "+ Arrays.toString(id));
        System.out.println("SCORE SEJARAH : "+Arrays.toString(score));
    }

    private void loadCheckPoint() {
        SQLiteDatabase sqLiteDatabase = dataHelper.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM checkPoint_Level WHERE id_category = '2'", null);
        if(cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            checkPoint_level = Integer.parseInt(cursor.getString(1));
        }

        System.out.println("CHECk POINT SEJARAH : " + checkPoint_level);
    }

}
