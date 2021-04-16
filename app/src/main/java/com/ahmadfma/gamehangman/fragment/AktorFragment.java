package com.ahmadfma.gamehangman.fragment;

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
import com.ahmadfma.gamehangman.adapter.recyclerAdapter;

import java.util.ArrayList;

public class AktorFragment extends Fragment {
    private ArrayList<String> listLvl;
    private ArrayList<String> listScr;

    private View view;
    private com.ahmadfma.gamehangman.adapter.recyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aktor, container,false);
        return view;
    }

    private void initVariable() {

    }

    private void initListener() {

    }

}
