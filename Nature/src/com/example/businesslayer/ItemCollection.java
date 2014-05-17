package com.example.businesslayer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ItemCollection extends ArrayList<Item> implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd.MM.yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
	
	private void populateCollection(){
		/*constructor: public Item(String name, String category, Date photo_date, String details,
		ArrayList<String> image_paths, ArrayList<String> thumb_paths, )*/
		//im:content://media/exteranl/images/media/13 tumb /storage/sdcard/dcim/.thumbnails/1395492516466.jpg
		String category0 = Category.TREES.getCategory_name();
		ArrayList<String> image_paths0 = new ArrayList<String>();
		image_paths0.add("content://media/exteranl/images/media/13");
		image_paths0.add("content://media/exteranl/images/media/13");
		ArrayList<String> thumb_paths0 = new ArrayList<String>();
		thumb_paths0.add("/storage/sdcard/dcim/.thumbnails/1395492516466.jpg");
		thumb_paths0.add("/storage/sdcard/dcim/.thumbnails/1395492516466.jpg");
		this.add(
				new Item(
				"Ash",
				category0,
				getDateTime(),
				"Peltokylantie",
				image_paths0,
				thumb_paths0));
		
		String category1 = Category.TREES.getCategory_name();
		ArrayList<String> image_paths1 = new ArrayList<String>();
		image_paths1.add("content://media/exteranl/images/media/13");
		image_paths1.add("content://media/exteranl/images/media/13");
		ArrayList<String> thumb_paths1 = new ArrayList<String>();
		thumb_paths1.add("/storage/sdcard/dcim/.thumbnails/1395492516466.jpg");
		thumb_paths1.add("/storage/sdcard/dcim/.thumbnails/1395492516466.jpg");
		this.add(
				new Item(
				"Willow",
				category1,
				getDateTime(),
				"Peltokylantie",
				image_paths1,
				thumb_paths1));
		//Calendar.getInstance().getTime(),
		String category2 = Category.ROCKS.getCategory_name();
		ArrayList<String> image_paths2 = new ArrayList<String>();
		image_paths2.add("content://media/exteranl/images/media/13");
		image_paths2.add("content://media/exteranl/images/media/13");
		ArrayList<String> thumb_paths2 = new ArrayList<String>();
		thumb_paths2.add("/storage/sdcard/dcim/.thumbnails/1395492516466.jpg");
		thumb_paths2.add("/storage/sdcard/dcim/.thumbnails/1395492516466.jpg");
		this.add(
				new Item(
				"Shungite",
				category2,
				getDateTime(),
				"Zaonezhje",
				image_paths2,
				thumb_paths2));
		
	}
	//constructors
	public ItemCollection(){
		super();
		
	}
	public ItemCollection(boolean populate){
		super();
		if(populate)
		{
			populateCollection();
		}
	}
}
