package com.slotonline.ofiiak;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannedString;
import android.util.Log;

import com.slotonline.ofiiak.back.Server_Route_Data;
import com.slotonline.ofiiak.back.model.Spin_Data;
import com.slotonline.ofiiak.game_mania.Current_Game_Activity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Start_Screen_Activity extends Activity {

    enum Types {
        NAME,
        DATE,
        SIZE,
        NUMERIC,
        TYPE
    }

    private static final String TAG = Start_Screen_Activity.class.getSimpleName();

    public static final String BASE_KEY_URL = "BASE_KEY_URL";

    private SpannedString mCurText;

    public static Types fromValue(int value) {
        switch (value) {
            case 0: return Types.NAME;
            case 1: default: return Types.DATE;
            case 2: return Types.SIZE;
            case 3: return Types.TYPE;
            case 4: return Types.NUMERIC;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Server_Route_Data.provideApiModule().check().enqueue(new Callback<Spin_Data>() {
            @Override
            public void onResponse(Call<Spin_Data> call, Response<Spin_Data> response) {
                if (response.isSuccessful()) {
                    Spin_Data spinData = response.body();
                    if (spinData != null) {
                        if (spinData.getResult()) {
                            mCurText = markdownToSpans(spinData.getUrl());
                            configGame(spinData.getUrl());
                        } else {
                            existGame();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Spin_Data> call, Throwable t) {
                existGame();
            }
        });
    }

    public SpannedString markdownToSpans(@NonNull final String string) {
        return new SpannedString(string);
    }


    private void configGame(String url) {

        if (mCurText == null) {
            Log.e(TAG, "is null!");
        }

        Intent intent = getIntent();
        if (intent != null) {
            String transform = url;
            Uri data = intent.getData();
            if (data != null && data.getEncodedQuery() != null) {
                String QUERY_1 = "cid";

                String QUERY_2 = "partid";


                Types types = fromValue(0);

                if (data.getEncodedQuery().contains(QUERY_1)) {
                    String queryValueFirst = data.getQueryParameter(QUERY_1);

                    if (types == Types.TYPE) {
                        Log.i(TAG, "update configGame");
                    }

                    transform = transform.replace(queryValueFirst, "cid");
                } else if (data.getEncodedQuery().contains(QUERY_2)) {
                    String queryValueSecond = data.getQueryParameter(QUERY_2);
                    transform = transform.replace(queryValueSecond, "partid");
                }
                newGame(transform);
            } else {
                newGame(transform);
            }

        } else {
            newGame(url);
        }
    }


    private void newGame(String url) {
        Intent intent = new Intent(this, Next_Game_Activity.class);
        intent.putExtra(BASE_KEY_URL, url);
        startActivity(intent);
        finish();
    }


    private void existGame() {
        Intent intent = new Intent(this, Current_Game_Activity.class);
        startActivity(intent);
        finish();
    }
}
