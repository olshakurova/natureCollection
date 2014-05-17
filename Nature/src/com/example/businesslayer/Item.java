package com.example.businesslayer;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.util.ArrayList;


public class Item  implements Serializable {
	

	private static final long serialVersionUID = 1L; 
	//fields category images name date (may be taken from the photo date),   info
	private long id;
	private String item_name;
	private String category;
	private String observation_date;
	private String details;


	private ArrayList <String> image_paths;
	private ArrayList<String> thumb_paths;

	//constructor
	public Item() {
		super();
	}
	public Item(String name, String category, String photo_date, String info,
			ArrayList<String> image_paths, ArrayList<String> thumb_paths) {
		super();
		this.id=-999;//\correct later?
		this.item_name = name;
		this.category = category;
		this.observation_date = photo_date;
		this.details = info;
		this.image_paths = image_paths;
		this.thumb_paths = thumb_paths;
	}
	
	//getters and setters
	   public long getId()
	    {
	        return id;
	    }
	    public void setId(long id)
	    {
	        this.id = id;
	    }
	public String getName(){
		return item_name;
	}
	public void setName(String name){
		this.item_name = name;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getPhoto_date() {
		return observation_date;
	}
	public void setPhoto_date(String photo_date) {
		this.observation_date = photo_date;
	}
	public String getInfo() {
		return details;
	}
	public void setInfo(String info) {
		this.details = info;
	}
	public ArrayList<String> getImage_paths() {
		return image_paths;
	}
	public void setImage_paths(ArrayList<String> image_paths) {
		this.image_paths = image_paths;
	}
	public ArrayList<String> getThumb_paths() {
		return thumb_paths;
	}
	public void setThumb_paths(ArrayList<String> thumb_paths) {
		this.thumb_paths = thumb_paths;
	}
	
	@SuppressLint("SimpleDateFormat") //?
	@Override
	public String toString(){
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		StringBuilder textBuilder = new StringBuilder();
		textBuilder.append(item_name);
		textBuilder.append(", category ");
		textBuilder.append(category);
		textBuilder.append(", date ");
		textBuilder.append(observation_date);
		//textBuilder.append(df.format(observation_date));
		textBuilder.append(".");
		return textBuilder.toString();
		
		
	}
}
