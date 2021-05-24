package com.spybot.app;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.spybot.R;
import com.model.Savegame;
import com.model.shortcuts.Json;
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
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(String filename, Context context) {

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
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    public static void loadSavegame(Context ctx) {
        String savegame = readFromFile("savegame.json", ctx);
        Savegame sg;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(savegame);
            if (!jsonObject.getBoolean(Json.INITIALIZED)) {
                jsonObject = new JSONObject(getFileContent(ctx.getResources().openRawResource(R.raw.savegame)));
            }

            sg = new Savegame(jsonObject);

        } catch (Exception e) {
            sg = new Savegame();
        }

        //TODO set savegame somewhere, accessible for all activities
        sg.toString();

    }

    public static void resetSavegame(Context ctx) {
        String originalSavegame = "{\n" +
                "  \"initialized\": false,\n" +
                "  \"Players\":\n" +
                "  {\n" +
                "    \"p1\":\n" +
                "    {\n" +
                "      \"id\":1,\n" +
                "      \"inv\":[\n" +
                "        \"Bug\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"p2\":\n" +
                "    {\n" +
                "      \"id\":2,\n" +
                "      \"inv\":[\n" +
                "        \"Bug\"\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        try {
            originalSavegame = getFileContent(ctx.getResources().openRawResource(R.raw.savegame));
        } catch (Exception e) {

        }

        writeToFile("savegame.json", originalSavegame, ctx);
    }

    public static String getFileContent(InputStream inputStream) throws IOException
    {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
    }

}
