package com.utilities;

import android.content.Context;
import android.util.Log;
import com.example.spybot.R;
import com.model.Savegame;
import com.model.shortcuts.Json;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SavegameUtil {

    public static void loadSavegame(Context ctx) {
        Savegame sg;

        if (!isFileExisting(Json.SAVEGAMEFILE, ctx)) {
            resetSavegame(ctx);
        }

        sg = getSavegame(ctx, false);
        if (sg == null) {
            //Savegame is corrupted
            sg = getSavegame(ctx, true);
        }


        //TODO set savegame somewhere, accessible for all activities
        sg.toString();

    }

    /**
     * Get an instance of a savegame related to the savegame file.
     * If there is a problem with the savegame and the defaultSavegame param is false, null will be returned,
     * otherwise a default instance will be returned.
     *
     * @param ctx
     * @param defaultSavegame, if returned value should be a default savegame, otherwise will be null
     * @return
     */
    private static Savegame getSavegame(Context ctx, boolean defaultSavegame) {
        Savegame savegame;

        if (defaultSavegame) {
            savegame = withoutError(ctx);
        } else {
            try {
                savegame = withError(ctx);
            } catch (IOException e) {
                Log.e("Spybot/Exception", "Corrupted Savegame");
                savegame = null;
            } catch (JSONException e) {
                Log.e("Spybot/Exception", "JSON parse Exception");
                savegame = null;
            }
        }

        return savegame;
    }

    private static Savegame withError(Context ctx) throws IOException, JSONException {
        String json = FileUtil.readFromFile(Json.SAVEGAMEFILE, ctx);

        return new Savegame(new JSONObject(json));
    }

    private static Savegame withoutError(Context ctx) {
        Savegame savegame;
        try {
            savegame = withError(ctx);
        } catch (IOException |JSONException e) {
            savegame = new Savegame();
        }

        return  savegame;
    }



    public static void resetSavegame(Context ctx) {
        String defaultSavegame = Json.DEFAULTSAVEGAME;

        try {
            defaultSavegame = FileUtil.getFileContent(ctx.getResources().openRawResource(R.raw.savegame));
        } catch (Exception e) {
            Log.e("Spybot/Exception", "Could not read default savegame resource, hardcoded String used");
        }

        FileUtil.writeToFile("savegame.json", defaultSavegame, ctx);
        Log.i("Spybot/Savegame", "Savegame resetted");
    }

    public static boolean isFileExisting(String filename, Context ctx) {
        boolean ret = true;
        try {
            InputStream inputStream = ctx.openFileInput(filename);
            inputStream.close();
        } catch (FileNotFoundException e) {
            ret = false;
        } catch (IOException e) {
            Log.e("Spybot/Exception", "General IOException");
        }
        return ret;
    }
}
