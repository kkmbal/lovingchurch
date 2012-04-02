package lc.common.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class ReciInfoObjectAdapter implements JsonDeserializer<Object>{

	public Object deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		if (json.isJsonNull()) {
			return null;
		} else if (json.isJsonPrimitive()) {
			JsonPrimitive primitive = json.getAsJsonPrimitive();
			if (primitive.isString()) {
				return primitive.getAsString();
			} else if (primitive.isNumber()) {
				return primitive.getAsNumber();
			} else if (primitive.isBoolean()) {
				return primitive.getAsBoolean();
			}
		} else if (json.isJsonArray()) {
			JsonArray array = json.getAsJsonArray();
			//Object[] result = new Object[array.size()];
			List result = new ArrayList();
			int i = 0;
			for (JsonElement element : array) {
				//result[i] = deserialize(element, null, context);
				result.add(deserialize(element, null, context));
				++i;
			}
			return result;
		} else if (json.isJsonObject()) {
			JsonObject object = json.getAsJsonObject();
			Map<String, Object> result = new HashMap<String, Object>();
			for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
				Object value = deserialize(entry.getValue(), null, context);
				result.put(entry.getKey(), value);
			}
			return result;
		} else {
			throw new JsonParseException("Unknown JSON type for JsonElement " + json.toString());
		}
		return null;
	}

}
