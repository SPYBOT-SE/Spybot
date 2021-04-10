package com.example.spybot;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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

public class MainActivity extends AppCompatActivity {

    public static byte[][] selectedLevel = levelSingle.Error;

    Board board = new Board(selectedLevel);

    int height = board.getSizeY();
    int width = board.getSizeX();

    private Field lastSelected = null;

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
        text.setText("Lorem Ipsum");
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
                    btnTag.setBackgroundResource(R.drawable.button_icon_bug);
                    break;
                case "Bulldozer":
                    btnTag.setBackgroundResource(R.drawable.button_icon_bulldozer);
                    break;
                default:

            }
        } else {
            btnTag.setBackgroundResource(R.drawable.button_icon);
        }


        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Button clickedButton = findViewById(v.getId());
//                clickedButton.setBackgroundColor(0xFF00FF00);
                OnClick(v.getId());
            }
        });

        btnTag.setVisibility(viewVisibility);
        layout.addView(btnTag);
    }


    void OnClick(int id) {
        // Utility.getShortestPath(board.getGraph(), board.getFieldById(0), board.getFieldById(3));

        Field field = board.getFieldById(id);
        // Button button = findViewById(id);
        // button.setBackgroundColor(0xFF00FF00);

        if(field.getHighlighting() != Highlighting.Empty) {
            switch(field.getHighlighting()) {
                case Movable:
                    field.setSegment(lastSelected.getSegment());
                    lastSelected.setSegment(null);
                    byte steps = field.getSegment().getPawn().getLeftSteps();
                    field.getSegment().getPawn().setLeftSteps((byte)(steps - 1));

                    findViewById(lastSelected.getId()).setBackgroundResource(R.drawable.button_icon);
            }

            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    Field currentF = board.getBoard()[x][y];
                    if(currentF.getHighlighting() != Highlighting.Empty) {
                        currentF.setHighlighting(Highlighting.Empty);
                        findViewById(currentF.getId()).setBackgroundResource(R.drawable.button_icon);
                    }



                }
            }

        }



        if(field.getSegment() != null) {

            lastSelected = field;

            Pawn pawn = field.getSegment().getPawn();
            Button buttonNeighbor;
            if(pawn.getLeftSteps() > 0) {
                for (Field neighborField : Utility.getFieldsInRange(board, id, pawn.getLeftSteps())) {
                    neighborField.setHighlighting(Highlighting.Reachable);
                    buttonNeighbor = findViewById(neighborField.getId());
                    buttonNeighbor.setBackgroundResource(R.drawable.button_spybot_reachable);
                }

                for (Field neighborField : Utility.getFieldsInRange(board, id, 1)) {
                    neighborField.setHighlighting(Highlighting.Movable);
                    buttonNeighbor = findViewById(neighborField.getId());
                    buttonNeighbor.setBackgroundResource(R.drawable.button_spybot_moveable);
                }
            }

            buttonNeighbor = findViewById(id);
            board.getFieldById(id).setHighlighting(Highlighting.Empty);

            switch(board.getFieldById(id).getSegment().getPawn().getName()) {

                case "Bug":
                    buttonNeighbor.setBackgroundResource(R.drawable.button_icon_bug);
                    break;
                case "Bulldozer":
                    buttonNeighbor.setBackgroundResource(R.drawable.button_icon_bulldozer);
                    break;
                default:

            }


        } else {
            lastSelected = null;
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    Field currentF = board.getBoard()[x][y];
                    if(currentF.getHighlighting() != Highlighting.Empty) {
                        currentF.setHighlighting(Highlighting.Empty);
                        findViewById(currentF.getId()).setBackgroundResource(R.drawable.button_icon);
                    }



                }
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
        btnTag.setBackgroundResource(R.drawable.button_icon_bug);
        btnTag.setVisibility(View.VISIBLE);
        panel.addView(btnTag);



        TextView text = new TextView(this);
        text.setText("Bug");
        text.setTextColor(Color.WHITE);
        panel.addView(text);

    }


    /**
     * Function iterates over field and refreshes every button representation
     *
     */
    void refreshBoard() {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {

                mapFieldToView(board.getBoard()[x][y]);
            }
        }
    }

    private void doMovable(Field field) {

    }

    /**
     * Function maps the status of a field to the correct picture representation
     *
     * @param field current field to refresh picture
     */
    private void mapFieldToView(Field field) {
        Button currBut = findViewById(field.getId());
        if (field.getStatus()) {
            currBut.setVisibility(View.VISIBLE);
            switch(field.getHighlighting()) {
                case Empty:
                    currBut.setBackgroundResource(R.drawable.button_icon);
                case Reachable:
                    currBut.setBackgroundResource(R.drawable.button_spybot_reachable);
                case MovableUp:
                case MovableDown:
                case MovableLeft:
                case MovableRight:
                case Movable:
                    currBut.setBackgroundResource(R.drawable.button_spybot_moveable);
                    Resources r = getResources();
                    Drawable[] layers = new Drawable[2];

                    layers[0] = r.getDrawable(R.drawable.button_icon);
                    layers[1] = r.getDrawable(R.drawable.button_icon_bulldozer);
                    LayerDrawable layerDrawable = new LayerDrawable(layers);

                    currBut.setBackground(layerDrawable);


                case Healable:
                case Attackable:
                case Buildable:
                default:
            }
            if(field.getSegment() != null) {

                switch(field.getSegment().getBodyType()) {
                    case Head:
                    case Tail:
                    default:
                }

            }
        } else {
            currBut.setVisibility(View.INVISIBLE);
        }
    }

}
