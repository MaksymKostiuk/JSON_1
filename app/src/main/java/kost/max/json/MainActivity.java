package kost.max.json;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            //Возвращает строковое значение
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset("users.json"));
            //Из обьекта получаем массив
            JSONArray jsonArray = jsonObject.getJSONArray("users");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject userData = jsonArray.getJSONObject(i);
                    name.add(userData.getString("name"));
                    email.add(userData.getString("email"));
                }
        }catch (
                JSONException e) {
            e.printStackTrace();
        }
            HelperAdapter helperAdapter = new HelperAdapter(name, email, MainActivity.this);
            recyclerView.setAdapter(helperAdapter);
    }

    //Разборка массива происходит в try catch(Который передался в файле)
    private String JsonDataFromAsset(String fileName){
        String json = null;
        try{
            //Устанавливаем размер
            InputStream inputStream = getAssets().open(fileName);
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];

            inputStream.read(bufferData);
            inputStream.close();
            //Вернет данные в json в виде UTF-8
            json = new String(bufferData, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
}