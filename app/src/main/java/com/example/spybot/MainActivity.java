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
import com.model.ActionID;
import com.model.Direction;
import com.model.LevelState;
import com.pawns.BodyType;
import com.pawns.Pawn;
import com.pawns.PawnSegment;
import com.spybot.app.AppSetting;
import com.utility.Utility;

public class MainActivity extends AppCompatActivity {

    public static int[][] selectedLevel = levelSingle.Error;


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
        //infoPanel.setBackgroundColor(Color.GRAY);


        LinearLayout gameLayout = new LinearLayout(this); //layout containing the game and a info box
        gameLayout.setOrientation(LinearLayout.VERTICAL);
        parentLayout.addView(gameLayout);

        LinearLayout fields = new LinearLayout(this); //fields
        fields.setOrientation(LinearLayout.VERTICAL);
        gameLayout.addView(fields);

        LinearLayout infoBox = new LinearLayout(this); //info box
        infoBox.setOrientation(LinearLayout.VERTICAL);
        gameLayout.addView(infoBox);




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
        loadDefaultView();
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

        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / 6, width / ratio));
        btnTag.setId(id);

//        btnTag.setOnClickListener((v) -> {
//            OnClick(v.getId());
//        });

        btnTag.setVisibility(View.VISIBLE);

        return btnTag;
    }


    void OnClick(int id) {

        //Field field = board.getFieldById(id);//should be in following if block
        if (id < 1000) { // if button is on board
            Field field = board.getFieldById(id);

            switch (board.getState()) {
                case Preparation:
                    loadInfoWithSpawnable();
                    // TODO Spawn something
                    break;
                case Running:
                    doHighlightingActions(field);

                    if(field.getSegment() != null && field.getSegment().getBodyType() == BodyType.Head) {
                        lastSelected = field;
                        loadInfoWithPawn();
                        setHighlightingMove(field);
                    }
            }
        } else { // ID > 1000 are not on board
            clearBoard();

            if(lastSelected == null) {
                return;
            }

            switch (id) {
                case ActionID.move:

                    setHighlightingMove(lastSelected);

                    // loadInfoWithAction(ActionID.move);
                    break;
                case ActionID.attack1:
                    setHighlightingAttack(lastSelected, (byte) 0);
                    break;
                case ActionID.attack2:
                    setHighlightingAttack(lastSelected, (byte) 1);
                    break;
                case ActionID.back:

                    loadInfoWithPawn();
                    break;
                case ActionID.nextTurn:
                    board.nextTurn();
                    loadDefaultView();
                    break;
                default:
            }


        }

        System.out.println("" + board.getState() + " "+ board.getSelectedInfo().getFieldId());



        // Button button = findViewById(id);
        // button.setBackgroundColor(0xFF00FF00);


        refreshBoard();

    }

    void SetUpInfoPanel(LinearLayout panel) {
        //createButton(panel, 1234567, View.VISIBLE, 10);

        Button btn = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btn.setLayoutParams(new LinearLayout.LayoutParams(width / 6, width / 6));
        btn.setId((int) 1100);

        btn.setBackgroundResource(R.drawable.button_icon_bug);
        btn.setVisibility(View.VISIBLE);
        btn.setClickable(false);
        panel.addView(btn);


        CreateTextViews(panel, "Name:", Color.BLACK,90001);
        CreateTextViews(panel, "HP:", Color.BLACK, 90002);
        CreateTextViews(panel, "Steps:", Color.BLACK, 90003);
        CreateTextViews(panel, "Class:", Color.BLACK, 90004);

        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.VERTICAL);
        btnLayout.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));




        btn = createButton(btnLayout, ActionID.move, 20);
        btn.setText("Move");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionID.attack1, 20);
        btn.setText("Attack 1");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionID.attack2, 20);
        btn.setText("Attack 2");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionID.back, 20);
        btn.setText("Back");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionID.nextTurn, 20);
        btn.setText("Next Turn");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        panel.addView(btnLayout);


    }

    private void CreateTextViews(LinearLayout panel, String description, int color, int id) {
        TextView text = new TextView(this);
        text.setText(description);
        text.setTextColor(color);
        text.setId(id);
        panel.addView(text);
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
                    layerView[2] = this.getDrawable(R.drawable.highlighting_spawnable_p0);
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
                        layerView[1] = this.getDrawable(segment.getPawn().pictureHead);
                        break;
                    case Tail:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTail);
                        break;
                    case TailUp:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTailUp);
                        break;
                    case TailDown:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTailDown);
                        break;
                    case TailLeft:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTailLeft);
                        break;
                    case TailRight:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTailRight);
                        break;
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
            Pawn actor = lastSelected.getSegment().getPawn();
            // Actions when clicking a highlighted field
            switch (field.getHighlighting()) {
                case Empty:
                    clearBoard();
                    break;
                case Reachable:
                    break;
                case MovableUp:
                    actor.move(lastSelected, field, Direction.UP);
                    break;
                case MovableDown:
                    actor.move(lastSelected, field, Direction.DOWN);
                    break;
                case MovableLeft:
                    actor.move(lastSelected, field, Direction.LEFT);
                    break;
                case MovableRight:
                    actor.move(lastSelected, field, Direction.RIGHT);
                    break;
                case Movable:
                    actor.move(lastSelected, field, Direction.NONE);
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

    private void setHighlightingMove(Field field) {
        clearBoard();


        Pawn pawn = field.getSegment().getPawn();
        Button buttonNeighbor;

        if (pawn.getLeftSteps() > 0) {
            for (Field neighborField : Utility.getFieldsInRange(board, field.getId(), pawn.getLeftSteps())) {
                if(neighborField.getSegment() != null) {
                    continue;
                }
                neighborField.setHighlighting(Highlighting.Reachable);
                buttonNeighbor = findViewById(neighborField.getId());

            }

            if (board.getField((short)(field.x + 1), field.y) != null && board.getField((short)(field.x + 1), field.y).getSegment() == null) {
                board.getField((short)(field.x + 1), field.y).setHighlighting(Highlighting.MovableRight);
            }

            if (board.getField((short)(field.x - 1), field.y) != null && board.getField((short)(field.x - 1), field.y).getSegment() == null) {
                board.getField((short)(field.x - 1), field.y).setHighlighting(Highlighting.MovableLeft);
            }
            if (board.getField((short)(field.x), (short)(field.y+1)) != null && board.getField((short)(field.x), (short)(field.y+1)).getSegment() == null) {
                board.getField((short)(field.x), (short)(field.y+1)).setHighlighting(Highlighting.MovableDown);
            }
            if (board.getField((short)(field.x), (short)(field.y-1)) != null && board.getField((short)(field.x), (short)(field.y-1)).getSegment() == null) {
                board.getField((short)(field.x), (short)(field.y-1)).setHighlighting(Highlighting.MovableUp);
            }
        }
    }


    private void setHighlightingAttack(Field field, byte attackNum) {
        clearBoard();
        //Button buttonNeighbor;
        for (Field neighborField : Utility.getFieldsInRange(board, field.getId(), 1)) {
            if(neighborField.getSegment() == null || field.getSegment().getPawn() != neighborField.getSegment().getPawn()) {
                neighborField.setHighlighting(Highlighting.Attackable);
            }
                //buttonNeighbor = findViewById(neighborField.getId());
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
        TextView showName = (TextView) findViewById((int) 90001); //Name
        TextView showHealth = (TextView) findViewById((int) 90002); //HP
        TextView showSteps = (TextView) findViewById((int) 90003); //Steps
        TextView showClass = (TextView) findViewById((int) 90004); //Class

        showName.setVisibility(View.INVISIBLE);
        showHealth.setVisibility(View.INVISIBLE);
        showSteps.setVisibility(View.INVISIBLE);
        showClass.setVisibility(View.INVISIBLE);

        Button btn = findViewById(ActionID.move);
        btn.setVisibility(View.INVISIBLE);
        btn = findViewById(ActionID.attack1);
        btn.setVisibility(View.INVISIBLE);
        btn = findViewById(ActionID.attack2);
        btn.setVisibility(View.INVISIBLE);
    }

    private void loadInfoWithSpawnable() {

    }


    private void loadInfoWithPawn() {
        if (lastSelected.getSegment().getPawn().getTeam() == board.currentPlayer) {
            TextView showName = (TextView) findViewById((int) 90001); //Name
            TextView showHealth = (TextView) findViewById((int) 90002); //HP
            TextView showSteps = (TextView) findViewById((int) 90003); //Steps
            TextView showClass = (TextView) findViewById((int) 90004); //Class

            showName.setVisibility(View.VISIBLE);
            showHealth.setVisibility(View.VISIBLE);
            showSteps.setVisibility(View.VISIBLE);
            showClass.setVisibility(View.VISIBLE);

            showName.setText("Name: " + lastSelected.getSegment().getPawn().getName());
            showHealth.setText("HP: " + lastSelected.getSegment().getPawn().getCurrentSize() + " / " + lastSelected.getSegment().getPawn().getMaxSize());
            showSteps.setText("Steps: " + lastSelected.getSegment().getPawn().getLeftSteps());

            Button btn = findViewById(ActionID.move);
            btn.setVisibility(View.VISIBLE);
            btn = findViewById(ActionID.attack1);
            btn.setVisibility(View.VISIBLE);
            btn = findViewById(ActionID.attack2);
            btn.setVisibility(View.VISIBLE);


        } else {

        }
    }

    /*
    private void loadInfoWithAction(int action) {
        switch (action) {
            case ActionID.move:
                break;
            default:
        }
    }*/

}
