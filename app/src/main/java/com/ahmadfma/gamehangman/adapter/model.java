package com.ahmadfma.gamehangman.adapter;

import android.database.sqlite.SQLiteDatabase;

import com.ahmadfma.gamehangman.dataModel.DataHelper;

import java.util.ArrayList;

public class model {

    private static ArrayList<String> listScore_Film;
    private static ArrayList<String> listPertanyaan_Film;

    private static ArrayList<String> listScore_Sejarah;
    private static ArrayList<String> listPertanyaan_Sejarah;



    public model() {
        listPertanyaan_Film = new ArrayList<>();
        listPertanyaan_Film.add("Cars#Film animasi dari pixar yang dirilis tahun 2006, film ini menceritakan tentang seorang pembalap bernama lightning mcqueen yang tersesat ke radiator springs");
        listPertanyaan_Film.add("TOY STORY#Film animasi bergenre komedi yang dibuat oleh pixar, rilis pertama kali pada tahun 1995");
        listPertanyaan_Film.add("JOHN WICK#Film bergenre thriller aksi dengan permain utamanya Keanu Reeves, rilis pertama kali pada tahun 2014");
        listPertanyaan_Film.add("Finding Nemo#Film bergenre animasi rilis pada tahun 2003, Film ini berfokus pada 2 ikan yang bernama marlin dan dory yang sedang mencari anak dari marlin");
        listPertanyaan_Film.add("Ratatouille#Film bergenre animasi rilis pada tahun 2007, Film ini menceritakan tentang seekor tikus bernama remy yang ingin menjadi seorang koki yang hebat di perancis");
        listPertanyaan_Film.add("Monster University#Film bergenre komedi animasi rilis tahun 2013, Film ini menceritakan tentang sekelompok monster yang mengambil jurusan menakut-nakuti di sebuah universitas");
        listPertanyaan_Film.add("The Incredibles#Film bergenre animasi yang dirilis tahun 2004, film ini menceritakan tentang keluarga super hero yang melawan syndrome");
        listPertanyaan_Film.add("Tom and Jerry#Serial animasi dari amerika serikat, film ini menceriakan tentang seekor kucing dan tikus yang selalu bertengkar");
        listPertanyaan_Film.add("FAST AND FURIOUS#Film bergenre action, serial pertamanya rilis pada tahun 2001 dengan pemeran utama bernama Paul walker");
        listPertanyaan_Film.add("The Conjuring#Film bergenre horror yang dirilis pada tahun 2013, film ini dibintangi oleh  Vera Farmiga dan Patrick Wilson yang diangkat dari kisah nyata yang dialami oleh Warren. Film ini mengisahkan tentang investigasi paranormal");

        listScore_Film = new ArrayList<>();
        for(int i = 0; i < listPertanyaan_Film.size(); i++) {
            listScore_Film.add("0");
        }

        listPertanyaan_Sejarah = new ArrayList<>();
        listPertanyaan_Sejarah.add("AHMAD FATHANAH#Pembuat Game Ini");
        listPertanyaan_Sejarah.add("INDONESIA#Negara Yang Dijajah Oleh Belanda Dan Jepang");
        listPertanyaan_Sejarah.add("SOEKARNO#Presiden Pertama negara Indonesia");
        listPertanyaan_Sejarah.add("17 AGUSTUS 1945#Tanggal Kemerdekaan Negara Republik Indoenesia");
        listPertanyaan_Sejarah.add("fatmawati#Orang Yang Menjahit Bendera Indonesia Pertama Kali");
        listPertanyaan_Sejarah.add("nagasaki#Kota Yang Dibom Amerika Pada Tanggal 6 Agustus 1945");
        listPertanyaan_Sejarah.add("Rengasdengklok#Peristiwa Penculikan Terhadap Soekarno Dan Hatta");
        listPertanyaan_Sejarah.add("Sayuti Melik#Penulis Naskah Proklamsi Kemerdekaan Indonesia");
        listPertanyaan_Sejarah.add("Sukarni Kartodiwirjo#Orang Yang Memaksa Soekarno Untuk Memproklamasikan Kemerdekaan");
        listPertanyaan_Sejarah.add("Radjiman Wedyodiningrat#Mantan Ketua BPUPKI");

        listScore_Sejarah = new ArrayList<>();
        for(int i = 0; i < listPertanyaan_Sejarah.size(); i++) {
            listScore_Sejarah.add("0");
        }
    }

    public static ArrayList<String> getListScore(String category) {
        switch (category) {
            case "FILM" :
                return listScore_Film;
            case "SEJARAH" :
                return listScore_Sejarah;
        }
        return null;
    }

    public static ArrayList<String> getListPertanyaan(String category) {
        switch (category) {
            case "FILM" :
                return listPertanyaan_Film;
            case "SEJARAH" :
                return listPertanyaan_Sejarah;
        }
        return null;
    }

    public static void updateScore(String category, int index, String value) {
        switch (category) {
            case "FILM" :
                listScore_Film.set(index, value);
                break;
            case "SEJARAH" :
                listScore_Sejarah.set(index,value);
                break;
        }
    }

}
