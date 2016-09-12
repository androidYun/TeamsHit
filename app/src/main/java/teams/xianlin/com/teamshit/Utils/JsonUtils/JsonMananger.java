/*
    ShengDao Android Client, JsonMananger
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package teams.xianlin.com.teamshit.Utils.JsonUtils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


/**
 * [JSON解析管理类]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2014-3-5
 **/
public class JsonMananger {
    private static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation().create();


    private static final String tag = JsonMananger.class.getSimpleName();

    /**
     * 将json字符串转换成java对象
     *
     * @param json
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> T jsonToBean(String json, Class<T> cls) throws Exception {
        return gson.fromJson(json, cls);
    }

    /**
     * 将json字符串转换成java List对象
     *
     * @param json
     * @param typeToken  new TypeToken<ArrayList<?>>() {};
     * @return
     * @throws Exception
     */
    public static <T> List<?> jsonToList(String json, TypeToken typeToken) throws Exception {
        List<?> list = null;
        Type type = typeToken.getType();
        list = gson.fromJson(json, type);
        return list;
    }

    /**
     * 将bean对象转化成json字符串
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String beanToJson(Object obj) throws Exception {
        String result = gson.toJson(obj);
        Log.e(tag, "beanToJson: " + result);
        return result;
    }

}
