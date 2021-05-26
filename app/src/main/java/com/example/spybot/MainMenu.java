package com.example.spybot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.model.shortcuts.Json;
import com.spybot.app.AppSetting;
import com.utilities.FileUtil;
import com.utilities.SavegameUtil;

import java.io.*;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startmenu);

        AppSetting.hideSystemUI(this);

        //create Buttons
        Button start = findViewById(R.id.btnStart);
        start.setOnClickListener(this);
        Button quit = findViewById(R.id.btnQuit);
        quit.setOnClickListener(this);
        Button settings = findViewById(R.id.btnSettings);
        settings.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnStart:
                Intent i = new Intent(MainMenu.this, LevelSelection.class);
                startActivity(i);
                break;
            case R.id.btnQuit:
                System.exit(0);
                break;
            case R.id.btnSettings:
                FileUtil.writeToFile(Json.SAVEGAMEFILE, "defaultSavegame", this);
                SavegameUtil.loadSavegame(this);
                break;
            default:
                System.exit(1);
                break;
        }
    }

}



