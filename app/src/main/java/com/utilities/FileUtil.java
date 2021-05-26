package com.utilities;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.util.stream.Collectors;

public class FileUtil {

    public static void writeToFile(String filename, String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Log.d("Spybot", "Written to file: " + filename);
            Log.d("Spybot", "Data: " + data + "\n");
        }
        catch (IOException e) {
            Log.e("Spybot/Exception", "File write failed: " + e);
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


    public static String getFileContent(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
    }
}
