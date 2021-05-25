package com.spybot.app;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.spybot.R;
import com.model.Savegame;
import com.model.shortcuts.Json;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.stream.Collectors;

public class AppSetting {

    public static void hideSystemUI(AppCompatActivity activity) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);


        /* Ab API version 30
        WindowInsetsController insetsController = decorView.getWindowInsetsController();
        insetsController.hide(WindowInsets.Type.statusBars());*/
    }

    public static void writeToFile(String filename, String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Log.d("Spybot", "Written to file: " + filename);
            Log.d("Spybot", "Data: " + data + "\n");
        }
        catch (IOException e) {
            Log.e("Spybot/Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(String filename, Context context) throws IOException {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();

                Log.d("Spybot", "Read from file: " + filename);
                Log.d("Spybot", "Output: " + ret + "\n");
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Spybot/Exception", "File not found: " + e);
            throw e;
        } catch (IOException e) {
            Log.e("Spybot/Exception", "Can not read file: " + e);
            throw e;
        }

        return ret;
    }


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
        String json = readFromFile(Json.SAVEGAMEFILE, ctx);

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
            defaultSavegame = getFileContent(ctx.getResources().openRawResource(R.raw.savegame));
        } catch (Exception e) {
            Log.e("Spybot/Exception", "Could not read default savegame resource, hardcoded String used");
        }

        writeToFile("savegame.json", defaultSavegame, ctx);
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

    public static String getFileContent(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
    }

}
