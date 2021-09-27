package com.infor.teams;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

public class CustomKeyJsonBuilder {
	public final JsonObject json = new JsonObject();

	public String toJson() {
		return json.toString();
	}

	public CustomKeyJsonBuilder add(final String key, final String value){
		if (StringUtils.isNotBlank(value)) {
			json.addProperty(key, value);
		}
		return this;
	}

	public CustomKeyJsonBuilder add(final String key, final CustomKeyJsonBuilder value) {
		if (value != null) {
			json.add(key, value.json);
		}
		return this;
	}
}
