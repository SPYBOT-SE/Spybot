package com.example.spybot;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.level.Board;
import com.level.Field;
import com.level.Highlighting;
import com.level.levelSingle;
import com.model.shortcuts.ActionID;
import com.model.Direction;
import com.model.LevelState;
import com.pawns.*;
import com.spybot.app.AppSetting;
import com.utilities.BoardUtil;

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
                    break;
                case ActionID.ATTACK_1:
                    setHighlightingAttack(lastSelected, (byte) 1, lastSelected.getSegment().getPawn().getAttack1().getRange());
                    break;
                case ActionID.ATTACK_2:
                    setHighlightingAttack(lastSelected, (byte) 2, lastSelected.getSegment().getPawn().getAttack1().getRange());
                    break;
                default:
            }
        }

        refreshBoard();

    }

    void RefreshInteractableView(Field field){
        if (field != null){
            Button button = findViewById(field.getId());
            int visible = field.getStatus() ? 0 : 4;
            button.setVisibility(visible);

        } else{
            for (short y = 0; y < height; y++) {
                for (short x = 0; x < width; x++) {
                    int id = y * width + x;
                    if (board.getField(x,y).getStatus()) {
                        Button button = findViewById(id);
                        button.setVisibility(View.VISIBLE);
                    } else {
                        Button button = findViewById(field.getId());
                        button.setVisibility(View.INVISIBLE);
                    }
                }

            }
        }

    }



    void SetUpInfoPanel(LinearLayout panel) {
        Button btn = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btn.setLayoutParams(new LinearLayout.LayoutParams(width / 6, width / 6));
        btn.setId(1100);

        btn.setVisibility(View.VISIBLE);
        btn.setClickable(false);
        panel.addView(btn);


        CreateTextViews(panel, "Name:", Color.BLACK,ActionID.NAME);
        CreateTextViews(panel, "HP:", Color.BLACK, ActionID.HP);
        CreateTextViews(panel, "Steps:", Color.BLACK, ActionID.STEPS);
        CreateTextViews(panel, "Class:", Color.BLACK, ActionID.CLASS);

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
            SortPawnsInTeams();
            board.currentState = LevelState.Running;
        } else if(board.currentState.equals(LevelState.Running) && board.currentPlayer == 0){
            board.currentPlayer = 1;


        } else if(board.currentState.equals(LevelState.Running) && board.currentPlayer == 1){
            board.currentPlayer = 0;

        }
        int currentPlayerIndex = board.currentPlayer;
        ResetAttributes();
        clearBoard();
        refreshBoard();
        Toast.makeText(MainActivity.this, Integer.toString(currentPlayerIndex), Toast.LENGTH_SHORT).show();
    }

    private void SortPawnsInTeams() {
        for (Pawn pawn: board.pawnsOnBoard) {
            if (pawn.getTeam() == 0){
                board.pawnsInTeam1.add(pawn);
            } else if (pawn.getTeam() == 1){
                board.pawnsInTeam2.add(pawn);
            }
        }
    }

    private void ResetAttributes(){
        for (Pawn pawn: board.pawnsOnBoard) {
            pawn.setLeftSteps(pawn.getSpeed());
            pawn.getAttack1().SetAttackFlag(true);
            pawn.getAttack2().SetAttackFlag(true);
        }
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
                case Attackable:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_attack_reachable);
                    break;
                case Attackable1:
                    layerView[2] = this.getDrawable(lastSelected.getSegment().getPawn().getAttack1().getResource());
                    break;
                case Attackable2:
                    layerView[2] = this.getDrawable(lastSelected.getSegment().getPawn().getAttack2().getResource());
                    break;
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

            Pawn actor;
            Pawn target;

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
                    if(field.getSegment() != null && actor.getAttack1().canAttack()) {
                        target = field.getSegment().getPawn();
                        actor.attack1(lastSelected);
                        actor.getAttack1().SetAttackFlag(false);

                    }
                    break;
                case Attackable2:
                    actor = lastSelected.getSegment().getPawn();
                    if(field.getSegment() != null && actor.getAttack2().canAttack()) {
                        target = field.getSegment().getPawn();
                        actor.attack2(lastSelected);
                        actor.getAttack2().SetAttackFlag(false);
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
            RefreshInteractableView(field);
            if(board.currentState == LevelState.Running && (board.pawnsInTeam1.size() == 0 || board.pawnsInTeam2.size() == 0)){
                //game has ended

                if(board.pawnsInTeam1.size() == 0 ) {
                    Toast.makeText(MainActivity.this, "Spieler 2 hat gewonnen", Toast.LENGTH_SHORT).show();
                } else if (board.pawnsInTeam2.size() == 0) {
                    Toast.makeText(MainActivity.this, "Spieler 1 hat gewonnen", Toast.LENGTH_SHORT).show();
                }

                board.setState(LevelState.Finished);

                //Intent i = new Intent(this, LevelSelection.class);
                //startActivity(i);
            }


            clearBoard();
            refreshBoard();

        }
    }

    private void setHighlightingMove(Field field) {
        clearBoard();

        Pawn pawn = field.getSegment().getPawn();

        if (pawn.getLeftSteps() > 0) {
            for (Field neighborField : BoardUtil.getFieldsInRange(board, field.getId(), pawn.getLeftSteps(), ActionID.MOVE)) {
                if(neighborField.getSegment() != null) {
                    continue;
                }
                neighborField.setHighlighting(Highlighting.Reachable);
            }

            if (board.getField((short)(field.x + 1), field.y) != null && board.getField((short)(field.x + 1), field.y).getSegment() == null) {
                board.getField((short)(field.x + 1), field.y).setHighlighting(Highlighting.MovableRight);
            }

            if (board.getField((short)(field.x - 1), field.y) != null && board.getField((short)(field.x - 1), field.y).getSegment() == null) {
                board.getField((short)(field.x - 1), field.y).setHighlighting(Highlighting.MovableLeft);
            }
            if (board.getField(field.x, (short)(field.y+1)) != null && board.getField(field.x, (short)(field.y+1)).getSegment() == null) {
                board.getField(field.x, (short)(field.y+1)).setHighlighting(Highlighting.MovableDown);
            }
            if (board.getField(field.x, (short)(field.y-1)) != null && board.getField(field.x, (short)(field.y-1)).getSegment() == null) {
                board.getField(field.x, (short)(field.y-1)).setHighlighting(Highlighting.MovableUp);
            }
        }
    }


    private void setHighlightingAttack(Field field, byte attackNum, byte range) {
        clearBoard();
        for (Field neighborField : BoardUtil.getFieldsInRange(board, field.getId(), range, ActionID.ATTACK_1)) {
            if (neighborField.getSegment() != null && neighborField.getSegment().getPawn().getTeam() != board.currentPlayer) {
                if (attackNum == 1) {
                    neighborField.setHighlighting(Highlighting.Attackable1);
                } else if (attackNum == 2) {
                    neighborField.setHighlighting(Highlighting.Attackable2);
                }
            } else {
                neighborField.setHighlighting(Highlighting.Attackable);
            }

        }
    }


    private void clearBoard() {
        if(board.currentState == LevelState.Preparation) {
            return;
        }

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
        TextView showName =  findViewById(ActionID.NAME);
        TextView showHealth = findViewById(ActionID.HP);
        TextView showSteps = findViewById(ActionID.STEPS);
        TextView showClass = findViewById(ActionID.CLASS);

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
            TextView showName = findViewById(ActionID.NAME);
            TextView showHealth = findViewById(ActionID.HP);
            TextView showSteps = findViewById(ActionID.STEPS);
            TextView showClass = findViewById(ActionID.CLASS);

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

            btn = findViewById(ActionID.ATTACK_1);
            btn.setText(lastSelected.getSegment().getPawn().getAttack1().getNameOfAttack());
            btn = findViewById(ActionID.ATTACK_2);
            btn.setText(lastSelected.getSegment().getPawn().getAttack2().getNameOfAttack());

            btn = findViewById(1100);
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
        selectionList.getMenu().add(v.getId(),2,2,"T3INF2002");

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
        Pawn p;
        if(item.getTitle().equals("Bug")) {
            p = new Bug();
        } else if(item.getTitle().equals("Dumbbell")) {
            p = new Dumbbell();
        } else if(item.getTitle().equals("T3INF2002")){
            p = new Compilerbau();
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
