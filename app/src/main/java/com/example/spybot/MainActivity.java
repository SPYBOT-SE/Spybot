package com.example.spybot;

import android.graphics.drawable.Drawable;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.level.Board;
import com.level.Field;
import com.level.Highlighting;
import com.level.levelSingle;
import com.pawns.Pawn;
import com.spybot.app.AppSetting;
import com.utility.Utility;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {



    Board board = new Board(levelSingle.TestLevel1);

    int height = board.getSizeY();
    int width = board.getSizeX();

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSetting.hideSystemUI(this);

        LinearLayout parentLayout = new LinearLayout(this); //main layout of the level screen
        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLayout.setBackgroundResource(R.drawable.background);

        LinearLayout infoPanel = new LinearLayout(this); //layout containing the information
        infoPanel.setOrientation(LinearLayout.VERTICAL);
        parentLayout.addView(infoPanel); //add info box to parent

        SetUpInfoPanel(infoPanel);



        LinearLayout gameLayout = new LinearLayout(this); //layout containing the game and a info box
        gameLayout.setOrientation(LinearLayout.VERTICAL);
        parentLayout.addView(gameLayout);

        LinearLayout fields = new LinearLayout(this); //fields
        fields.setOrientation(LinearLayout.VERTICAL);
        gameLayout.addView(fields);

        LinearLayout infoBox = new LinearLayout(this); //info box
        infoBox.setOrientation(LinearLayout.VERTICAL);
        gameLayout.addView(infoBox);

        TextView text = new TextView(this);
        text.setText("Timo Steidinger");
        text.setTextColor(Color.WHITE);
        infoBox.addView(text);



        for (int y = 0; y < height; y++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            for (int x = 0; x < width; x++) {
                int id = y * width + x;

                if (board.getBoard()[x][y].getStatus()){
                   createButton(row, id, View.VISIBLE, 20);
                } else{
                   createButton(row, id, View.INVISIBLE,20);
                }



            }
            fields.addView(row);
        }

        setContentView(parentLayout);
        //resetButtons();
    }

    void createButton(LinearLayout layout, int id, int viewVisibility , int ratio) {
        Button btnTag = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / ratio, width / ratio));
        btnTag.setId(id);

        if(board.getFieldById(id).getSegment() != null) {

            switch(board.getFieldById(id).getSegment().getPawn().getName()) {

                case "Bug":
                    btnTag.setBackgroundResource(R.drawable.bug);
                    break;
                default:

            }
        } else {
            btnTag.setBackgroundResource(R.drawable.button_icon);
        }


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


        Field field = board.getFieldById(id);
        // Button button = findViewById(id);
        // button.setBackgroundColor(0xFF00FF00);



        if(field.getSegment() != null && field.getSegment().isHead()) {

            Pawn pawn = field.getSegment().getPawn();

            for (Field neighborField: Utility.getFieldsInRange(board, id, pawn.getSpeed())) {
                neighborField.setHighlighting(Highlighting.Movable);
                Button buttonNeighbor = findViewById(neighborField.getId());
                buttonNeighbor.setBackgroundColor(Color.BLUE);
            }

        }


    }

    void SetUpInfoPanel(LinearLayout panel){
        //createButton(panel, 1234567, View.VISIBLE, 10);

        Button btnTag = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / 4, width / 4));
        btnTag.setId(10001);
        btnTag.setBackgroundResource(R.drawable.button_icon_timo);
        btnTag.setVisibility(View.VISIBLE);
        panel.addView(btnTag);



        TextView text = new TextView(this);
        text.setText("Timo Steidinger");
        text.setTextColor(Color.WHITE);
        panel.addView(text);

    }
}
