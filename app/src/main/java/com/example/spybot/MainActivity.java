package com.example.spybot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.spybot.app.AppSetting;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSetting.hideSystemUI(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.background);                // 76 x:7 y:6
        for (int i = 0; i < 8; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 16; j++) {
                Random random = new Random();
                int check = random.nextInt(101);
                if (check % 5 == 0) {
                    createButton("O", row, i, j, false);
                } else {
                    int name = j * 10 + i;
                    createButton(Integer.toString(name), row, i, j, true);
                }

            }
            layout.addView(row);
        }
        setContentView(layout);
        resetButtons();
    }

    void createButton(String name, LinearLayout layout, int y, int x, boolean visible) {
        Button btnTag = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;


        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / 20, width / 20));


        btnTag.setText(name);
        btnTag.setId(x * 10 + y);
        btnTag.setWidth(40);
        btnTag.setHeight(40);
        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButtons();
                Button clickedButton = (Button) findViewById(v.getId());
                clickedButton.setBackgroundColor(0xFF00FF00);
                OnClick(v.getId());
            }
        });
        if (visible) {
            btnTag.setVisibility(View.VISIBLE);
        } else {
            btnTag.setVisibility(View.INVISIBLE);
        }

        layout.addView(btnTag);


    }



    void OnClick(int id) {
        Random random = new Random();


        Button button = null;
        while (button == null) {
            int x = random.nextInt(16) + 1;
            int y = random.nextInt(8) + 1;
            button = (Button) findViewById(x * 10 + y);
            if (button != null) {
                if (button.getVisibility() == View.INVISIBLE) {
                    button = null;
                } else {
                    button.setBackgroundColor(0xFF00FF00);
                    //GetDirection(id,x*10+y);
                }
            }


        }


    }



    void resetButtons() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                Button button = (Button) findViewById(j * 10 + i);
                button.setBackgroundColor(0xcccccc);
            }

        }
    }


}
