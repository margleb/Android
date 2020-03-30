package com.example.myproject.conrtroller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    // возращает имя класса
    private static final String TAG = AppController.class.getSimpleName();
    // singleton экземпляр класса
    private static AppController mInstance;
    private RequestQueue mRequestQueue;

    public AppController getInstance() {
        // if(mInstance == null ) {
            // mInstance = new AppController();
        // }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null) {
            // getApplicationContext - возраещает контекст одного глобального обьекта текущего процесса
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addRequestQueue(Request<T> req, String tag) {
        // проверяет пустой ли дефолтный тег
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addRequestQueue(Request<T> req) {
        // проверяет пустой ли дефолтный тег
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPreperingRequest(Object tag) {
        if(mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
