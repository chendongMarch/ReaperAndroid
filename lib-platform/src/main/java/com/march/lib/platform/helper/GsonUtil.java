package com.march.lib.platform.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.helper
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */

public class GsonUtil {
    public static <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String getObject2Json(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getTime());
            }
        }).setDateFormat(DateFormat.LONG);
        return gsonBuilder.create().toJson(object);
    }
}
