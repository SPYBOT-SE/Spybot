package com.example.spybot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

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

    void findEnemy(int xDifference, int yDifference, int current, int target){
        while(xDifference != 0){
            GetDirection(current,target);
            current = Moving(xDifference,yDifference, current);
        }


    }

    int Moving(int xDirection, int yDirection, int current){
        int currentX, currentY;
        currentX = current/10;
        currentY = current-(current/10);

        //?
        if (xDirection != 0){
            currentX =+ (0+(1/xDirection)*xDirection);
            if(CheckButton(currentX,currentY)){

            }
        }
        return current;
    }

    void GetDirection(int target, int start){
        int differenceX = target/10 - start/10;
        int differenceY = (target-(target/10)*10) - (start-(start/10)*10);
        findEnemy(differenceX,differenceY,start,target);
    }

    boolean CheckButton(int currentX, int currentY){
        Button button = (Button) findViewById(currentX*10+currentY);

        if (button != null){
            if (button.getVisibility()==View.VISIBLE)
            return true;
            else return false;
        }
        return true;
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
