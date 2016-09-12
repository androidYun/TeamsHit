package teams.xianlin.com.teamshit.Utils;

import java.lang.reflect.Type;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonUtil {
	private static Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation().create();

	public static String toJson(Object src) {

		return gson.toJson(src);
	}

	public static <T> T fromJson(String json, Class<T> classOfT)
			throws Exception {
		return gson.fromJson(json, classOfT);
	}

	public static <T> T fromJson(String json, Type typeOfT) throws Exception {
		return gson.fromJson(json, typeOfT);
	}
}
