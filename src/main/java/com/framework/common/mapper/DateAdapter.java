package com.framework.common.mapper;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		String dateStr = null;
		if(jsonElement.isJsonNull()){
			dateStr = "";
		}else{
			dateStr = jsonElement.getAsString();
		}
		if(null!=dateStr){
			String dateFormat = "";
			if(dateStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")){
				dateFormat = "yyyy-MM-dd HH:mm:ss";
			}
			if(dateStr.matches("\\d{4}-\\d{2}-\\d{2}")
					||dateStr.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z")
					||dateStr.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")){
				dateFormat = "yyyy-MM-dd";
			}
			
			if(!"".equals(dateFormat)){
				SimpleDateFormat format = new SimpleDateFormat(dateFormat);
				try {
					Date date = format.parse(dateStr);
					return date;
				} catch (ParseException e) {
					return null;
				}
			}else{
				return null;
			}
		}
		return null;
	}

	@Override
	public JsonElement serialize(Date date, Type type,
			JsonSerializationContext context) {
		if(null==date){
			return null;
		}
		String dateFormat = "yyyy-MM-dd HH:mm:ss:SSS";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat); 
		String dateStr = format.format(date);
		if(dateStr.contains(" 00:00:00:000")){
			dateStr = dateStr.split(" ")[0];
		}else{
			dateStr = dateStr.substring(0, 19);
		}
		return new JsonPrimitive(dateStr);
	}

}