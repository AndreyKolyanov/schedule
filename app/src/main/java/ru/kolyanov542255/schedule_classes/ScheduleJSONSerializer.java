package ru.kolyanov542255.schedule_classes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Адольф on 30.01.2015.
 */
public class ScheduleJSONSerializer {

    private String filename;
    private Context context;

    ScheduleJSONSerializer(Context c, String f){

        filename = f;
        context = c;
    }

    public ArrayList<DayOfWeek> loadSchedule() throws IOException, JSONException{
        ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
                Log.d("READ", line);
            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                days.add(new DayOfWeek(array.getJSONObject(i), context
                        .getSharedPreferences(WeekPagerActivity.APP_PREFS,Context.MODE_PRIVATE)
                        .getInt(SettingsActivity.DURATION,95)));
            }

        } catch (FileNotFoundException e){
            Log.d("READ", "file not found");
        } finally {
            if (reader != null){
                reader.close();
            }
        }
        return days;
    }

    public  void saveSchedule(ArrayList<DayOfWeek> days) throws JSONException, IOException{

        JSONArray array = new JSONArray();

        for (DayOfWeek d: days){
            array.put(d.toJSON());
        }

        Writer writer = null;

        try{
            OutputStream out = context.openFileOutput(filename, context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
            Log.d("WRITE", array.toString());
        }finally {
            if (writer != null){
                writer.close();
            }
        }
    }
}
