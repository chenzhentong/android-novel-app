package com.example.novelapplication.util;


import android.util.Log;

import com.example.novelapplication.model.BookList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Created by DavidWong at 2020/06/30
 */

public class Utility {
    public static BookList parseJsonWithGson(final String requestText){
        Log.d("requestText",requestText);
        Gson gson = new Gson();
        BookList novelList =gson.fromJson(requestText,new TypeToken<BookList>(){}.getType());
        return novelList;
    }


}
