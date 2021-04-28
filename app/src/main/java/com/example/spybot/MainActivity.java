package com.example.spybot;

import android.content.Context;
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
import com.model.ActionID;
import com.model.LevelState;
import com.pawns.Pawn;
import com.spybot.app.AppSetting;
import com.utility.Utility;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static byte[][] selectedLevel = levelSingle.Error;


    private Board board = null;


    private int height = 0;
    private int width = 0;

    private Field lastSelected = null;

    private Resources r = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        r = getResources();

        board = new Board(selectedLevel);

        height = board.getSizeY();
        width = board.getSizeX();



        setContentView(R.layout.activity_main);
        AppSetting.hideSystemUI(this);

        LinearLayout parentLayout = new LinearLayout(this); //main layout of the level screen
        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLayout.setBackgroundResource(R.drawable.background);

        LinearLayout infoPanel = new LinearLayout(this); //layout containing the information
        infoPanel.setOrientation(LinearLayout.VERTICAL);
        parentLayout.addView(infoPanel); //add info box to parent

        SetUpInfoPanel(infoPanel);
        infoPanel.setBackgroundColor(Color.rgb(211,211, 211));


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


        for (short y = 0; y < height; y++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            for (short x = 0; x < width; x++) {
                int id = y * width + x;

                if (board.getField(x,y).getStatus()) {
                    createButton(row, id, View.VISIBLE, 20);
                } else {
                    createButton(row, id, View.INVISIBLE, 20);
                }


            }
            fields.addView(row);
        }



        setContentView(parentLayout);
        //resetButtons();
        refreshBoard();
    }

    void createButton(LinearLayout layout, int id, int viewVisibility, int ratio) {
        Button btnTag = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / ratio, width / ratio));
        btnTag.setId(id);

        btnTag.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btnTag.setVisibility(viewVisibility);
        layout.addView(btnTag);
    }

    private Button createButton(LinearLayout layout, int id, int ratio) {
        Button btnTag = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / 4, width / ratio));
        btnTag.setId(id);

        btnTag.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btnTag.setVisibility(View.VISIBLE);

        return btnTag;
    }


    void OnClick(int id) {
        //Field field = board.getFieldById(id);//should be in following if block
        if (id < 1000) { // if button is on board
            Field field = board.getFieldById(id);

            switch (board.getState()) {
                case Preparation:
                    if (field.getSpawner() > 0) {
                        loadInfoWithSpawnable();
                    }
                    break;
                case ChoosePawn:
                    clearBoard();

                    if (field.getSegment() != null) {
                        Pawn pawn = field.getSegment().getPawn();
                        board.setSelectedInfo(pawn, field.getId());
                        loadInfoWithPawn();
                    } else {
                        board.setSelectedInfo(null, -1);
                    }
                    break;
                case MoveChooseField:
                    if (field.getSegment() == null) {
                        //doHighlightingActions(field); //doMove()
                        if (Utility.getFieldsInRange(board, board.getSelectedInfo().getFieldId(), 1).contains(field)) {
                            doMovable(board.getFieldById(id));
                            if (board.getSelectedInfo().getPawn().getLeftSteps() <= 0) {
                                board.setState(LevelState.ChoosePawn);
                                board.moveSelecetedInfoPawn(board.getFieldById(id));
                                doHighlightSetting(board.getFieldById(board.getSelectedInfo().getFieldId()));
                                loadInfoWithPawn();

                            } else {
                                board.moveSelecetedInfoPawn(board.getFieldById(id));
                                doHighlightSetting(board.getFieldById(board.getSelectedInfo().getFieldId()));
                            }
                        }
                    }
                    break;
                default:
            }
        } else { // ID > 1000 are not on board
            switch (id) {
                case ActionID.move:
                    doHighlightSetting(board.getFieldById(board.getSelectedInfo().getFieldId()));
                    board.setState(LevelState.MoveChooseField);
                    loadInfoWithAction(ActionID.move);
                    break;
                case ActionID.attack1:
                case ActionID.attack2:
                case ActionID.back:
                    board.setState(LevelState.ChoosePawn);
                    loadInfoWithPawn();
                    break;
                case ActionID.nexTurn:
                    board.nextTurn();
                    loadDefaultView();
                    break;
                default:
            }


        }

        System.out.println("" + board.getState() + " "+ board.getSelectedInfo().getFieldId());



        // Button button = findViewById(id);
        // button.setBackgroundColor(0xFF00FF00);

        //doHighlightingActions(field);
        //doHighlightSetting(field);
        refreshBoard();

    }

    void SetUpInfoPanel(LinearLayout panel) {
        //createButton(panel, 1234567, View.VISIBLE, 10);

        Button btn = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btn.setLayoutParams(new LinearLayout.LayoutParams(width / 4, width / 4));
        btn.setId(1100);

        btn.setBackgroundResource(R.drawable.button_icon_bug);
        btn.setVisibility(View.VISIBLE);
        btn.setClickable(false);
        panel.addView(btn);


        TextView text = new TextView(this);
        text.setText("Bug");
        text.setTextColor(Color.GRAY);
        panel.addView(text);

        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.VERTICAL);
        btnLayout.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        btnLayout.setBackgroundColor(Color.CYAN);



        btn = createButton(btnLayout, ActionID.move, 20);
        btn.setText("Move");
        btnLayout.addView(btn);

        btn = createButton(btnLayout, ActionID.attack1, 20);
        btn.setText("Attack 1");
        btnLayout.addView(btn);

        btn = createButton(btnLayout, ActionID.attack2, 20);
        btn.setText("Attack 2");
        btnLayout.addView(btn);

        btn = createButton(btnLayout, ActionID.back, 20);
        btn.setText("Back");
        btnLayout.addView(btn);

        btn = createButton(btnLayout, ActionID.nexTurn, 20);
        btn.setText("Next Turn");
        btnLayout.addView(btn);

        panel.addView(btnLayout);
    }


    /**
     * Function iterates over field and refreshes every button representation
     */
    void refreshBoard() {
        for (short y = 0; y < height; y++) {
            for (short x = 0; x < width; x++) {

                mapFieldToView(board.getField(x,y));
            }
        }
    }

    private void doMovable(Field field) {
        field.setSegment(lastSelected.getSegment());
        lastSelected.setSegment(null);
        byte steps = field.getSegment().getPawn().getLeftSteps();
        field.getSegment().getPawn().setLeftSteps((byte) (steps - 1));
    }


    /**
     * Function maps the status of a field to the correct picture representation
     *
     * @param field current field to refresh picture
     */
    private void mapFieldToView(Field field) {

        Resources r = getResources();

        Button currBut = findViewById(field.getId());


        Drawable[] layerView = new Drawable[3];

        layerView[0] = this.getDrawable(R.drawable.field_classroom);
        layerView[1] = this.getDrawable(R.drawable.field_transparent);
        layerView[2] = this.getDrawable(R.drawable.field_transparent);

        if (field.getStatus()) {

            currBut.setVisibility(View.VISIBLE);

            switch (field.getHighlighting()) {
                case Empty:
                    layerView[2] = this.getDrawable(R.drawable.field_transparent);
                    break;

                case Reachable:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_reachable);
                    break;
                case MovableUp:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable_up);
                    break;
                case MovableDown:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable_down);
                    break;
                case MovableLeft:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable_left);
                    break;
                case MovableRight:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable_right);
                    break;
                case Movable:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable);
                    break;
                case Healable:
                    break;
                case Attackable:
                    break;
                case Buildable:
                    break;
                case SpawnableP1:
                case SpawnableP2:
                default:
            }
            PawnSegment segment = field.getSegment();
            if (segment != null) {

                switch (segment.getBodyType()) {
                    case Head:
                        layerView[1] = segment.getPawn().move(field);
                    case Tail:

                    case TailUp:
                    case TailDown:
                    case TailLeft:
                    case TailRight:
                    default:
                }

            }
        } else {
            currBut.setVisibility(View.INVISIBLE);
        }

        LayerDrawable layerDrawable = new LayerDrawable(layerView);
        currBut.setBackground(layerDrawable);

    }

    /**
     * By clicking on a highlighted field the associated action will be performed here
     *
     * @param field cklicked field
     */
    private void doHighlightingActions(Field field) {
        if (field.getHighlighting() != Highlighting.Empty) {

            // Actions when clicking a highlighted field
            switch (field.getHighlighting()) {
                case Empty:
                    clearBoard();
                    break;
                case Reachable:
                    break;
                case MovableUp:
                case MovableDown:
                case MovableLeft:
                case MovableRight:
                case Movable:
                    doMovable(field);
                    break;
                case Healable:
                    //TODO
                    break;
                case Attackable:
                    //TODO
                    break;
                case Buildable:
                    //TODO
                    break;
                case SpawnableP1:
                case SpawnableP2:
                default:

            }

            clearBoard();
        }
    }

    /**
     * by clicking on a field with a segment, the new highlighting for that segment will be set here
     *
     * @param field clicked field
     */
    private void doHighlightSetting(Field field) {
        if (field.getSegment() != null) {

            clearBoard();
            lastSelected = field;

            Pawn pawn = field.getSegment().getPawn();

            Button buttonNeighbor;


            if (pawn.getLeftSteps() > 0) {
                for (Field neighborField : Utility.getFieldsInRange(board, field.getId(), pawn.getLeftSteps())) {
                    neighborField.setHighlighting(Highlighting.Reachable);
                    buttonNeighbor = findViewById(neighborField.getId());

                }

                if (board.getField((short)(field.x + 1), field.y) != null) {
                    board.getField((short)(field.x + 1), field.y).setHighlighting(Highlighting.MovableRight);
                }

                if (board.getField((short)(field.x - 1), field.y) != null) {
                    board.getField((short)(field.x - 1), field.y).setHighlighting(Highlighting.MovableLeft);
                }
                if (board.getField((short)(field.x), (short)(field.y+1)) != null) {
                    board.getField((short)(field.x), (short)(field.y+1)).setHighlighting(Highlighting.MovableDown);
                }
                if (board.getField((short)(field.x), (short)(field.y-1)) != null) {
                    board.getField((short)(field.x), (short)(field.y-1)).setHighlighting(Highlighting.MovableUp);
                }

                /*
                for (Field neighborField : Utility.getFieldsInRange(board, id, 1)) {
                    neighborField.setHighlighting(Highlighting.Movable);
                    buttonNeighbor = findViewById(neighborField.getId());

                }*/
            }

            buttonNeighbor = findViewById(field.getId());
            board.getFieldById(field.getId()).setHighlighting(Highlighting.Empty);

            switch (board.getFieldById(field.getId()).getSegment().getPawn().getName()) {

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
            clearBoard();
        }
    }

    private void clearBoard() {
        for (short y = 0; y < height; y++) {
            for (short x = 0; x < width; x++) {
                Field currentF = board.getField(x,y);

                currentF.setHighlighting(Highlighting.Empty);
            }
        }
    }

    private void loadDefaultView() {
        clearBoard();
        clearInfoPanel();
    }

    private void clearInfoPanel() {

    }

    private void loadInfoWithSpawnable() {

    }

    private void loadInfoWithPawn() {
        if (board.getSelectedInfo().getPawn().getTeam() == board.currentPlayer) {

        } else {

        }
    }

    private void loadInfoWithAction(int action) {
        switch (action) {
            case ActionID.move:
                break;
            default:
        }
    }

}
