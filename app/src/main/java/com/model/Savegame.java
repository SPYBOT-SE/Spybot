package com.model;

import com.player.Player;
import org.json.JSONObject;

//TODO implement
public class Savegame {

    private Player p1;
    private Player p2;


    public Savegame() {
        p1 = new Player(true);
        p2 = new Player(true);
    }

    public Savegame(JSONObject jsonObject) {
        this();
        if (jsonObject != null) {
            p1 = new Player(false);
            p2 = new Player(false);
        }
    }

    public String toString() {
        return "";
    }
}
