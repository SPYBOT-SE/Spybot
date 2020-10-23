package com.example.spybot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startmenu);

        //Resize buttons

        //create Buttons
        Button start = (Button) findViewById(R.id.btnStart);
        start.setOnClickListener(this);
        Button quit = (Button) findViewById(R.id.btnQuit);
        quit.setOnClickListener(this);
        Button settings = (Button) findViewById(R.id.btnSettings);
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
            default:
                System.exit(0);
                break;
        }

        }

    }

