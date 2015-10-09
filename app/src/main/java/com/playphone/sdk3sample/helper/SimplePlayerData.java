/**
 * Used in saving data to cloud demo
 * 
 */
package com.playphone.sdk3sample.helper;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class SimplePlayerData {
	
	private String name = "";
	private String level = "";
	private String life = "";

	public SimplePlayerData() {
	}
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getLevel() {
		return level;
	}



	public void setLevel(String level) {
		this.level = level;
	}



	public String getLife() {
		return life;
	}



	public void setLife(String life) {
		this.life = life;
	}



	public Map getMapFromObject(){
		Map<String, String> simplePayerDataMap = new HashMap<String,String>();
		simplePayerDataMap.put("name", this.getName());
		simplePayerDataMap.put("level", this.getLevel());
		simplePayerDataMap.put("life", this.getLife());
		return simplePayerDataMap;
	}
	
	public SimplePlayerData updateFromJson(JSONObject json) throws JSONException
	{
		if(json.has("name")) this.setName(json.getString("name"));
		if(json.has("level")) this.setLevel(json.getString("level"));
		if(json.has("life")) this.setLife(json.getString("life"));
		return this;
	}
	

}
