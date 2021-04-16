 package com.ahmadfma.gamehangman.activity;

//TODO : Sambungkan database ke permainan

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmadfma.gamehangman.R;
import com.ahmadfma.gamehangman.dataModel.DataHelper;
import com.ahmadfma.gamehangman.fragment.FilmFragment;
import com.ahmadfma.gamehangman.fragment.SejarahFragment;
import com.ahmadfma.gamehangman.adapter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GamebeginActivity extends AppCompatActivity {

    private Button check;
    private EditText inputTebakan;
    private TextView timetv, pertanyaantv, tebakantv, kesempatantv, scoretv;
    private ImageView hangman;

    private int time, kesempatan = 6, amountOfCorrectInput = 0, maxScore = 5000, addScore = 0, currentScore = 0, minusAddScore = 0;
    private int SUCCESS = 2, FAILED = 1, counter = 0;
    private boolean timeRunning = false, isPopUp = false;
    private CountDownTimer countDownTimer;
    private long waktu = 60000; //1 menit
    private String jawaban, pertanyaan, currentTebakan;
    private ArrayList<String> currentTebakanProgress;
    private ArrayList<String> listInputan;
    private DataHelper dataHelper;
    private Cursor cursor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamebegin);
        initVariable();
        initListener();

        getPertanyaan();
        underlinedList();
        updateTebakanTv();
        startTimer();
    }

    @Override
    public void onBackPressed() {
        if(!isPopUp) {
            counter++;
            if(counter == 1) {
                Toast.makeText(this, "Permainan Sedang DiMulai, Tekan Sekali Lagi Untuk Keluar", Toast.LENGTH_LONG).show();
            }
            if(counter == 2) {
                Intent intent = new Intent(GamebeginActivity.this, KategoriActivity.class);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(GamebeginActivity.this, KategoriActivity.class);
            startActivity(intent);
        }
    }

    private void initVariable() {
        check = findViewById(R.id.checkTebakan);
        inputTebakan = findViewById(R.id.inputanTebakan);
        timetv = findViewById(R.id.sisaWaktu);
        pertanyaantv = findViewById(R.id.pertanyaan);
        tebakantv = findViewById(R.id.tebakan);
        kesempatantv = findViewById(R.id.kesempatan);
        hangman = findViewById(R.id.hangmanImg);
        scoretv = findViewById(R.id.currentScore);
        currentTebakanProgress = new ArrayList<>();
        listInputan = new ArrayList<>();
        dataHelper = new DataHelper(this);
    }

    private void initListener() {
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInputan(view);
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(waktu,1000) {
            @Override
            public void onTick(long l) {
                waktu = l;
                updateUI();
            }

            @Override
            public void onFinish() {
                kesempatan = 0;
                kesempatantv.setText("Kesempatan : " + kesempatan);
                updateHangman();
                check.setEnabled(false);
                inputTebakan.setEnabled(false);
                createPopUp(FAILED);
            }
        }.start();
        timeRunning = true;
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timeRunning = false;
    }

    private void updateUI() {
        long waktuTersisa = waktu/1000;
        timetv.setText("Sisa Waktu : ".concat(String.valueOf(waktuTersisa)));
    }

    private void getPertanyaan() {
        String data = "";
        switch (KategoriActivity.categorySelected) {
            case "FILM":
                data = FilmFragment.getData(KategoriActivity.levelSelected);
                break;
            case "SEJARAH":
                data = SejarahFragment.getData(KategoriActivity.levelSelected);
                break;
            case "BENDA":
                break;
            case "AKTOR":
                break;
            default:
                break;
        }
        Scanner scan = new Scanner(data);
        scan.useDelimiter("#");
        jawaban = scan.next();
        pertanyaan = scan.next();
        pertanyaantv.setText("Pertanyaan : ".concat(pertanyaan));
    }

    private void updateTebakanTv() {
        String data = "";
        for(int i = 0; i < currentTebakanProgress.size(); i++) {
            data += currentTebakanProgress.get(i) + " ";
        }
        tebakantv.setText(data);
    }

    private void underlinedList() {
        for(int i = 0; i < jawaban.length(); i++) {
            if(jawaban.charAt(i) != ' ') {
                currentTebakanProgress.add("_");
                amountOfCorrectInput++;
            } else {
                currentTebakanProgress.add(" ");
            }
        }
        addScore = maxScore/amountOfCorrectInput;
        minusAddScore = addScore/kesempatan;
    }

    private void checkInputan(View view) {
        boolean isExist = false;
        String inputan = inputTebakan.getText().toString();
        if(inputan.length() != 0) {
            for(int i = 0; i < inputan.length(); i++) {
                char currentAlphabet = inputan.toLowerCase().charAt(i);
                for(int j = 0; j < jawaban.length(); j++) {
                    if(jawaban.toLowerCase().charAt(j) == currentAlphabet) {
                        String cek = currentTebakanProgress.get(j);
                        if(cek.equals("_")) {
                            if(amountOfCorrectInput != 0 ) {
                                amountOfCorrectInput--;
                            }
                            isExist = true;
                            currentTebakanProgress.set(j, String.valueOf(currentAlphabet).toUpperCase());
                            currentScore += addScore;
                        }
                        updateTebakanTv();
                    }
                }
                if(!isExist) {
                    if(kesempatan != 0) {
                        listInputan.add(String.valueOf(currentAlphabet));
                        kesempatan--;
                        addScore -= minusAddScore;
                    }
                }
            }
            if(listInputan.size() > 0) {
                String temp = "";
                for(int j = 0; j < listInputan.size(); j++) {
                    temp += listInputan.get(j);
                    if(j != (listInputan.size()-1)) {
                        temp += ", ";
                    }
                }
                Toast.makeText(this, "Huruf ".concat(temp.toUpperCase().concat(" Salah")), Toast.LENGTH_SHORT).show();
                listInputan.clear();
            }
        }

        if(kesempatan == 0) {
            check.setEnabled(false);
            inputTebakan.setEnabled(false);
            createPopUp(FAILED);
            stopTimer();
        } else if(amountOfCorrectInput == 0) {
            if (kesempatan == 6) {
                currentScore = maxScore;
            }
            check.setEnabled(false);
            inputTebakan.setEnabled(false);
            createPopUp(SUCCESS);
            stopTimer();

//            model.updateScore(KategoriActivity.categorySelected, KategoriActivity.levelSelected, String.valueOf(currentScore));
            updateCheckPoint_Level(KategoriActivity.categorySelected);

            saveScore(KategoriActivity.categorySelected);
            setCheckPointLevel(KategoriActivity.categorySelected);
        }

        hideKeyboard(view);
        updateHangman();
        kesempatantv.setText("Kesempatan : " + kesempatan);
        scoretv.setText("Score : " + currentScore);
        inputTebakan.setText(null);

    }

    private void updateHangman() {
        switch (kesempatan) {
            case 5:
                hangman.setImageResource(R.drawable.hangman1);
                break;
            case 4:
                hangman.setImageResource(R.drawable.hangman2);
                break;
            case 3:
                hangman.setImageResource(R.drawable.hangman3);
                break;
            case 2:
                hangman.setImageResource(R.drawable.hangman4);
                break;
            case 1:
                hangman.setImageResource(R.drawable.hangman5);
                break;
            case 0:
                hangman.setImageResource(R.drawable.hangman6);
                break;
            default:
                break;
        }
    }

    private void updateCheckPoint_Level(String category) {
        switch (category) {
            case "FILM":
                if(KategoriActivity.levelSelected == FilmFragment.checkPoint_level) {
                    FilmFragment.checkPoint_level++;
                }
                break;
            case "SEJARAH":
                if(KategoriActivity.levelSelected == SejarahFragment.checkPoint_level) {
                    SejarahFragment.checkPoint_level++;
                }
                break;
            default:
                break;
        }
    }

    private void saveScore(String category) {
        //TODO : tambahkan update seperti checkpoint

        String temp = "";
        SQLiteDatabase sqLiteDatabase = dataHelper.getReadableDatabase();

        switch (category) {
            case "FILM" :
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM score_FILM WHERE id = '" + KategoriActivity.levelSelected + "'", null);
                break;
            case "SEJARAH" :
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM score_SEJARAH WHERE id = '" + KategoriActivity.levelSelected + "'", null);
                break;
            default:
                break;
        }

        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            temp = cursor.getString(1);
        }

        //cek apakah skor didatabase lebih kecil daripada skor yang didapatkan saat main
        //jika lebih kecil, maka diupdate, jika tidak =  ignore, jika null = tambahkan
        sqLiteDatabase = dataHelper.getWritableDatabase();

        if(!temp.equals("")) {

            switch (category) {
                case "FILM" :
                    if(Integer.parseInt(temp) < currentScore) {
                        sqLiteDatabase.execSQL("update score_FILM set skor = '" + currentScore + "' where id = '" + KategoriActivity.levelSelected + "'");
                    }
                    break;
                case "SEJARAH" :
                    if(Integer.parseInt(temp) < currentScore) {
                        sqLiteDatabase.execSQL("update score_SEJARAH set skor = '" + currentScore + "' where id = '" + KategoriActivity.levelSelected + "'");
                    }
                    break;
                default:
                    break;
            }

        } else {
            switch (category) {
                case "FILM" :
                    sqLiteDatabase.execSQL("insert into score_FILM(id, skor) values ('" +
                            KategoriActivity.levelSelected + "','" + currentScore + "')");
                    break;
                case "SEJARAH" :
                    sqLiteDatabase.execSQL("insert into score_SEJARAH(id, skor) values ('" +
                            KategoriActivity.levelSelected + "','" + currentScore + "')");
                    break;
            }


        }
        System.out.println("SAVE SCORE BERHASIL !");
    }

    private void setCheckPointLevel(String category) {
        int id = 0;
        int point = 0;
        switch (category) {
            case "FILM" :
                id = 1;
                point = FilmFragment.checkPoint_level;
                break;
            case "SEJARAH" :
                point = SejarahFragment.checkPoint_level;
                id = 2;
                break;
            default:
                break;
        }

        SQLiteDatabase sqLiteDatabase = dataHelper.getWritableDatabase();
        try {
            //jika checpointnya masih kosong, maka buat baru
            sqLiteDatabase.execSQL("insert into checkPoint_Level(id_category, skor) values ('" +
                    id +"','" +  point +"')");
        }catch (Exception e) {
            //jika sudah ada, maka diubah
            sqLiteDatabase.execSQL("update checkPoint_Level set skor = '" + point + "' where id_category = '" + id + "'");
        }

        System.out.println("SAVE CheckPoint BERHASIL !");
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private void createPopUp(int kode) {

        ImageView img;
        TextView msg, skorFinal;
        Button nextLevel, restart,menu;
        builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.popup_layout, null);
        img = view.findViewById(R.id.hangmanLast);
        msg = view.findViewById(R.id.messageFinal);
        nextLevel = view.findViewById(R.id.nextLevelBtn);
        restart = view.findViewById(R.id.restartBtnn);
        menu = view.findViewById(R.id.menuBtn);
        skorFinal = view.findViewById(R.id.scoreFinal);

        if(kode == 1) {
            img.setImageResource(R.drawable.hangmandead);
            msg.setText("Anda Gagal !");
            nextLevel.setEnabled(false);
        } else if(kode == 2) {
            nextLevel.setEnabled(true);
            img.setImageResource(R.drawable.hangmanthanks);
            msg.setText("Selamat Anda Berhasil");
        }
        skorFinal.setText("Score Anda : " + currentScore);

        if(KategoriActivity.levelSelected == 9) {
            nextLevel.setEnabled(false);
            Toast.makeText(this, "Anda Telah Menyelesaikan Semua Level DiKategori ini", Toast.LENGTH_LONG).show();
        }

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        isPopUp = true;

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamebeginActivity.this, GamebeginActivity.class);
                startActivity(intent);
            }
        });

        nextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(KategoriActivity.levelSelected != 9) {
                    KategoriActivity.levelSelected++;
                }
                Intent intent = new Intent(GamebeginActivity.this, GamebeginActivity.class);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamebeginActivity.this, KategoriActivity.class);
                startActivity(intent);
            }
        });

    }

}
