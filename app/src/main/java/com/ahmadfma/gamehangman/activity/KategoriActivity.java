package com.ahmadfma.gamehangman.activity;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ahmadfma.gamehangman.R;
import com.ahmadfma.gamehangman.dataModel.DataHelper;
import com.ahmadfma.gamehangman.fragment.AktorFragment;
import com.ahmadfma.gamehangman.fragment.BendaFragment;
import com.ahmadfma.gamehangman.fragment.FilmFragment;
import com.ahmadfma.gamehangman.fragment.SejarahFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class KategoriActivity extends AppCompatActivity implements View.OnClickListener {

    private Button film;
    private Button sejarah;
    private Button aktor;
    private Button benda;
    public static Button play;
    public static TextView levelPilihan;
    public static int levelSelected = 0;
    public static String categorySelected = "";
    public static final String TAG = KategoriActivity.class.getSimpleName();
    String save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategori_activity);
        initVariable();
        initListener();
        play.setEnabled(false);
        categorySelected = "FILM";
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_container, new FilmFragment())
                .commit();
    }

    @Override
    public void onClick(View view) {
        Fragment selected = null;
        switch (view.getId()) {
            case R.id.film_btn:
                categorySelected = "FILM";
                selected = new FilmFragment();
                break;
            case R.id.sejarah_btn:
                categorySelected = "SEJARAH";
                selected = new SejarahFragment();
                break;
            case  R.id.benda_btn:
                categorySelected = "BENDA";
                selected = new BendaFragment();
                break;
            case R.id.aktor_btn:
                categorySelected = "AKTOR";
                selected = new AktorFragment();
                break;
            default:
                break;
        }
        levelPilihan.setText("Level Pilihan : ");
        play.setEnabled(false);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_container, selected)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KategoriActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void initVariable() {
        film = findViewById(R.id.film_btn);
        sejarah = findViewById(R.id.sejarah_btn);
        aktor = findViewById(R.id.aktor_btn);
        benda = findViewById(R.id.benda_btn);
        levelPilihan = findViewById(R.id.levelPilihan);
        play = findViewById(R.id.playNow);
    }

    private void initListener() {
        film.setOnClickListener(this);
        sejarah.setOnClickListener(this);
        aktor.setOnClickListener(this);
        benda.setOnClickListener(this);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KategoriActivity.this, GamebeginActivity.class);
                startActivity(intent);
            }
        });
    }



}
