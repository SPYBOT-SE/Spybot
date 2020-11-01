package com.example.spybot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.spybot.app.AppSetting;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int width = 16;
    int height = 8;

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
                createButton(row, y * width + x, View.VISIBLE);
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
                Button clickedButton = (Button) findViewById(v.getId());
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

        Button button = null;
        while (button == null) {
            x = random.nextInt(width) + 1;
            y = random.nextInt(height) + 1;
            button = (Button) findViewById(y * width + x);
            if (button != null) {
                if (button.getVisibility() == View.INVISIBLE) {
                    button = null;
                } else {
                    button.setBackgroundColor(0xFF00FF00);
                }
            }
        }
    }


}
