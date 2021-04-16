package com.ahmadfma.gamehangman.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmadfma.gamehangman.R;
import com.ahmadfma.gamehangman.dataModel.DataHelper;
import com.ahmadfma.gamehangman.fragment.FilmFragment;
import com.ahmadfma.gamehangman.adapter.*;

public class MainActivity extends AppCompatActivity{

    private Button play;
    private DataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new model();
        initVariable();
        initListener();

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }

    private void initVariable() {
        play = findViewById(R.id.button);
    }

    private void initListener() {
        //Play Button At Landing Page
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
                startActivity(intent);
            }
        });
    }

}