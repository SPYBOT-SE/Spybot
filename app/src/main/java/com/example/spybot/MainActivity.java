package com.example.spybot;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.level.Board;
import com.level.Field;
import com.level.Highlighting;
import com.level.levelSingle;
import com.model.ActionID;
import com.model.Direction;
import com.model.LevelState;
import com.pawns.*;
import com.spybot.app.AppSetting;
import com.utility.Utility;

import java.util.NoSuchElementException;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

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

        btnTag.setVisibility(View.VISIBLE);

        return btnTag;
    }


    void OnClick(int id) {

        //Field field = board.getFieldById(id);//should be in following if block
        if (id < 1000) { // if button is on board
            Field field = board.getFieldById(id);

            switch (board.getState()) {
                case Preparation:
                    doHighlightingActions(field);
                    break;
                case Running:
                    doHighlightingActions(field);

                    if(field.getSegment() != null
                            && field.getSegment().getBodyType() == BodyType.Head
                            && field.getSegment().getPawn().getTeam() == board.currentPlayer) {
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
                case ActionID.MOVE:
                    setHighlightingMove(lastSelected);
                    // loadInfoWithAction(ActionID.move);
                    break;
                case ActionID.ATTACK_1:
                    setHighlightingAttack(lastSelected, (byte) 1);
                    break;
                case ActionID.ATTACK_2:
                    setHighlightingAttack(lastSelected, (byte) 2);
                    break;
                default:
            }
        }

        System.out.println("" + board.getState() + " "+ board.getSelectedInfo().getFieldId());
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




        btn = createButton(btnLayout, ActionID.MOVE, 20);
        btn.setText("Move");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionID.ATTACK_1, 20);
        btn.setText("Attack 1");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionID.ATTACK_2, 20);
        btn.setText("Attack 2");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionID.NEXT_TURN, 20);
        btn.setText("Next Turn");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            TurnButtonOnClick();
        });

        btn = createButton(btnLayout, ActionID.BACK, 20);
        btn.setText("Back");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            LoadMainMenu();
        });

        panel.addView(btnLayout);
    }

    private void LoadMainMenu(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Leave game");
        builder1.setIcon(android.R.drawable.ic_dialog_alert);
        builder1.setMessage("Do you really want to leave the game? Progress will not be saved");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ExitGame();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void ExitGame(){
        Intent i = new Intent(this, LevelSelection.class);
        startActivity(i);
    }
    private void TurnButtonOnClick(){
        if (board.currentState.equals(LevelState.Preparation) && board.currentPlayer == 0){
            board.currentPlayer = 1;
        } else if(board.currentState.equals(LevelState.Preparation) && board.currentPlayer == 1){
            board.currentPlayer = 0;
            board.currentState = LevelState.Running;
        } else if(board.currentState.equals(LevelState.Running) && board.currentPlayer == 0){
            board.currentPlayer = 1;
        } else if(board.currentState.equals(LevelState.Running) && board.currentPlayer == 1){
            board.currentPlayer = 0;
        }
        int test = board.currentPlayer;

        Toast.makeText(MainActivity.this, Integer.toString(test), Toast.LENGTH_SHORT).show();
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

        layerView[0] = this.getDrawable(field.background);
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
                case Attackable1:
                case Attackable2:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_attack);
                case Buildable:
                    break;
                case SpawnableP1:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_spawnable_p0);
                    break;
                case SpawnableP2:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_spawnable_p1);
                    break;
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
     * @param field clicked field
     */
    private void doHighlightingActions(Field field) {
        if (field.getHighlighting() != Highlighting.Empty) {

            Pawn actor = null;
            Pawn target = null;

            // Actions when clicking a highlighted field
            switch (field.getHighlighting()) {
                case Empty:
                    clearBoard();
                    break;
                case Reachable:
                    break;
                case MovableUp:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.UP);
                    break;
                case MovableDown:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.DOWN);
                    break;
                case MovableLeft:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.LEFT);
                    break;
                case MovableRight:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.RIGHT);
                    break;
                case Movable:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.NONE);
                    break;
                case Healable:
                    //TODO
                    break;
                case Attackable1:
                    actor = lastSelected.getSegment().getPawn();
                    if(field.getSegment() != null) {
                        target = field.getSegment().getPawn();
                        actor.attack1(target);
                    }
                    break;
                case Attackable2:
                    actor = lastSelected.getSegment().getPawn();
                    if(field.getSegment() != null) {
                        target = field.getSegment().getPawn();
                        actor.attack2(target);
                    }
                    break;
                case Buildable:
                    //TODO
                    break;
                case SpawnableP1:
                    if(board.currentPlayer != 0) break;
                    ShowSpawnableList(findViewById(field.getId()));
                case SpawnableP2:
                    if(board.currentPlayer != 1) break;
                    ShowSpawnableList(findViewById(field.getId()));
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
            for (Field neighborField : Utility.getFieldsInRange(board, field.getId(), pawn.getLeftSteps(), ActionID.MOVE)) {
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
        for (Field neighborField : Utility.getFieldsInRange(board, field.getId(), 1, ActionID.ATTACK_1)) {
                if (neighborField.getSegment() == null || field.getSegment().getPawn() != neighborField.getSegment().getPawn()) {
                    if (attackNum == 1) {
                        neighborField.setHighlighting(Highlighting.Attackable1);
                    } else if (attackNum == 2) {
                        neighborField.setHighlighting(Highlighting.Attackable2);
                    }
                }

                //buttonNeighbor = findViewById(neighborField.getId());
        }
    }


    private void clearBoard() {

        if(board.currentState == LevelState.Preparation) {
            return;
        }

        for (short y = 0; y < height; y++) {
            for (short x = 0; x < width; x++) {
                Field currentF = board.getField(x,y);
                if(currentF.getHighlighting() != Highlighting.SpawnableP1) {
                    currentF.setHighlighting(Highlighting.Empty);
                }
            }
        }
    }


    private void loadDefaultView() {
        clearBoard();
        clearInfoPanel();
    }

    private void clearInfoPanel() {
        TextView showName =  findViewById((int) 90001); //Name
        TextView showHealth = findViewById((int) 90002); //HP
        TextView showSteps = findViewById((int) 90003); //Steps
        TextView showClass = findViewById((int) 90004); //Class

        showName.setVisibility(View.INVISIBLE);
        showHealth.setVisibility(View.INVISIBLE);
        showSteps.setVisibility(View.INVISIBLE);
        showClass.setVisibility(View.INVISIBLE);

        Button btn = findViewById(ActionID.MOVE);
        btn.setVisibility(View.INVISIBLE);
        btn = findViewById(ActionID.ATTACK_1);
        btn.setVisibility(View.INVISIBLE);
        btn = findViewById(ActionID.ATTACK_2);
        btn.setVisibility(View.INVISIBLE);
    }


    private void loadInfoWithPawn() {
        if (lastSelected.getSegment().getPawn().getTeam() == board.currentPlayer) {
            TextView showName = findViewById((int) 90001); //Name
            TextView showHealth = findViewById((int) 90002); //HP
            TextView showSteps = findViewById((int) 90003); //Steps
            TextView showClass = findViewById((int) 90004); //Class

            showName.setVisibility(View.VISIBLE);
            showHealth.setVisibility(View.VISIBLE);
            showSteps.setVisibility(View.VISIBLE);
            showClass.setVisibility(View.VISIBLE);

            showName.setText("Name: " + lastSelected.getSegment().getPawn().getName());
            showHealth.setText("HP: " + lastSelected.getSegment().getPawn().getCurrentSize() + " / " + lastSelected.getSegment().getPawn().getMaxSize());
            showSteps.setText("Steps: " + lastSelected.getSegment().getPawn().getLeftSteps());

            Button btn = findViewById(ActionID.MOVE);
            btn.setVisibility(View.VISIBLE);
            btn = findViewById(ActionID.ATTACK_1);
            btn.setVisibility(View.VISIBLE);
            btn = findViewById(ActionID.ATTACK_2);
            btn.setVisibility(View.VISIBLE);

            btn = findViewById((int) 1100);
            btn.setBackgroundResource(lastSelected.getSegment().getPawn().pictureHead);
            
        }
    }

    /**
     * Show menu for spawnable pawns
     * @param v spawnable button field that has been pressed
     */
    public void ShowSpawnableList(View v) {
        PopupMenu selectionList = new PopupMenu(this, v);
        selectionList.setOnMenuItemClickListener(this);

        selectionList.getMenu().add(v.getId(), 0 ,0 , "Bug");
        selectionList.getMenu().add(v.getId(),1,1,"Dumbbell");

        selectionList.show();
    }

    /**
     * Actual spawning of pawn on the selected button field
     * @param item selected pawn to spawn on field
     * @return if spawning was successful
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        Field field = board.getFieldById(item.getGroupId());
        Pawn p = null;
        if(item.getTitle().equals("Bug")) {
            p = new Bug();
        } else if(item.getTitle().equals("Dumbbell")) {
            p = new Dumbbell();
        } else {
            throw new NoSuchElementException("Error, selected pawn not implemented");
        }

        field.setHighlighting(Highlighting.Empty);

        board.pawnsOnBoard.add(p);
        p.createSegment(field, BodyType.Head);
        p.setTeam(board.currentPlayer);

        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();

        mapFieldToView(field);

        return true;
    }

}
