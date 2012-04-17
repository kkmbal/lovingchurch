package lc.common.util;

import java.lang.reflect.Type;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateConverter implements JsonSerializer<Date>,
		JsonDeserializer<Date> {

	public static final String DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STRING);

	public JsonElement serialize(Date date, Type type,
			JsonSerializationContext jsc) {
		String dateString = sdf.format(date);
		return new JsonPrimitive(dateString);
	}

	public Date deserialize(JsonElement json, Type type,
			JsonDeserializationContext jsc) throws JsonParseException {
		String dateString = json.getAsJsonPrimitive().getAsString();
		if (StringUtils.hasText(dateString) && dateString.length() == DATE_FORMAT_STRING.length()) {
			return sdf.parse(dateString, new ParsePosition(0));
		}
		return null;
	}

}
