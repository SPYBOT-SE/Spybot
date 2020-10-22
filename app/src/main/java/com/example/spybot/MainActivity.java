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
                if (check%5 == 0){
                    createButton("O", row, i, j,false);
                } else {
                    int name = j*10+i;
                    createButton(Integer.toString(name), row, i, j,true);
                }

            }
            layout.addView(row);
        }
        setContentView(layout);
        resetButtons();
}

void createButton(String name, LinearLayout layout, int y, int x, boolean visible){
    Button btnTag = new Button(this);

    DisplayMetrics dm = new DisplayMetrics();
    this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
    int width = dm.widthPixels;


    btnTag.setLayoutParams(new LinearLayout.LayoutParams(width/20,width/20));

    btnTag.setText(name);
    btnTag.setId(x*10+y);
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
    if (visible){
        btnTag.setVisibility(View.VISIBLE);
    } else{
        btnTag.setVisibility(View.INVISIBLE);
    }
    layout.addView(btnTag);

}

void OnClick(int id){
    Random random = new Random();


    Button button = null;
    while (button == null){
        int x = random.nextInt(16)+1;
        int y = random.nextInt(8) +1;
        button = (Button) findViewById(x*10+y);
        if (button != null) {
            if (button.getVisibility() == View.INVISIBLE) {
                button = null;
            } else {
                button.setBackgroundColor(0xFF00FF00);
                //dijkstra(id, x * 10 + y);
            }
        }


    }


}

void resetButtons(){
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 16; j++) {
            Button button = (Button) findViewById(j*10+i);
            button.setBackgroundColor(0xcccccc);
        }

    }
}



void dijkstra(int start, int target) {
    int weight = 0;
    int currentPos = start;

    int xCurrent;
    int yCurrent;
    int xTarget;
    int yTarget;


    while (currentPos != target) {
        Toast.makeText(this, Integer.toString(currentPos), Toast.LENGTH_LONG*2).show();
        xCurrent = currentPos / 10;
        yCurrent = currentPos - xCurrent * 10;
        xTarget = target / 10;
        yTarget = target - xTarget * 10;

        if (xCurrent != xTarget) {
            if (xCurrent - xTarget > 0) {  //target is left
                xCurrent--;
                if (!checkPermeability(xCurrent, yCurrent)) {
                    xCurrent++;
                    if (yCurrent - yTarget > 0) { //target is above
                        yCurrent--;
                        if (!checkPermeability(xCurrent, yCurrent)) {
                            yCurrent = yCurrent + 2;
                        }
                    } else {
                        yCurrent--;
                        if (!checkPermeability(xCurrent, yCurrent)) {
                            yCurrent = yCurrent - 2;
                        }
                    }
                } else {
                    xCurrent++; //target is right
                    if (!checkPermeability(xCurrent, yCurrent)) {
                        xCurrent--;
                        if (yCurrent - yTarget > 0) { //target is above
                            yCurrent--;
                            if (!checkPermeability(xCurrent, yCurrent)) {
                                yCurrent = yCurrent + 2;
                            }
                        } else {
                            yCurrent--;
                            if (!checkPermeability(xCurrent, yCurrent)) {
                                yCurrent = yCurrent - 2;
                            }
                        }
                    }

                }

            }


        }
        currentPos = xCurrent*10+yCurrent;
        Button button = (Button) findViewById(currentPos);
        button.setText("I");
        button.setBackgroundColor(0xcccccc);
    }
}

boolean checkPermeability(int x, int y){
    Button button = (Button) findViewById(x * 10 + y);
    if (button.getVisibility() == View.INVISIBLE || button == null) {
        return false;
    } else {
        return true;
    }
}

}