package com.example.spybot;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.level.Board;
import com.level.Field;
import com.spybot.app.AppSetting;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int width = 16;
    int height = 8;
    Board board = new Board(width,height);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSetting.hideSystemUI(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.background);




        for (int y = 0; y < height; y++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            for (int x = 0; x < width; x++) {
               if (board.getBoard()[x][y].getStatus()){
                   createButton(row, y * width + x, View.VISIBLE);
               } else{
                   createButton(row, y * width + x, View.INVISIBLE);
               }



            }
            layout.addView(row);
        }

        setContentView(layout);
        //resetButtons();
    }

    void createButton(LinearLayout layout, int id, int viewVisibility) {
        Button btnTag = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / 20, width / 20));

        btnTag.setText(String.format(Integer.toString(id), Locale.ENGLISH));
        btnTag.setId(id);

        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = findViewById(v.getId());
                clickedButton.setBackgroundColor(0xFF00FF00);
                OnClick(v.getId());
            }
        });

        btnTag.setVisibility(viewVisibility);
        layout.addView(btnTag);
    }


    void OnClick(int id) {
        Random random = new Random();
        int x,y;

        x = id%width;
        y = id/width;

        Field field = board.getBoard()[x][y];

        Button button = findViewById(id);
        button.setBackgroundColor(0xFF00FF00);

        for (Field neighborField:board.getGraph().getNeighbours(field)) {
            Button buttonNeighbor = findViewById(neighborField.getId());
            buttonNeighbor.setBackgroundColor(Color.BLUE);
        }


    }


}
