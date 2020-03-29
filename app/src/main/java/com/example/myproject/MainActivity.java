package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // https://jsonplaceholder.typicode.com/todos/1
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // создает очередь (пул) из запросов
        requestQueue = Volley.newRequestQueue(this);

        // JSON запросы
        // method - HTTP метод для использования
        // url - метод получения по url
        // null - обьект JSON для публикации с запросом, null указывает на то, что ни один из параметров не будет запостен вместе с запросом
        // listener - слушаетель ответа запроса
        // errorListener - в случае ошибки запроса, указываем null, если не нужен
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/todos/1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                 Log.d("JSON:", "onResonse: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error:", "onErrorResopnse: " + error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
