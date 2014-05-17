package com.example.nature;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.businesslayer.Item;
import com.example.sqlite.MyOwnSQLiteHelper;

public class CategoryActivity extends Activity
{
	private String category_text;
	//private ItemCollection item_collection;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		

		Intent intent = getIntent();
		category_text = (String)intent.getSerializableExtra("Category");
		this.setTitle(category_text);

		/*String stringCollection = (String)intent.getSerializableExtra("ItemCollection");
		try {
			item_collection = (ItemCollection) ObjectStringSerializer.fromString(stringCollection);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		MyOwnSQLiteHelper mh = new MyOwnSQLiteHelper(this);
		 // get all 
        List<Item> list = mh.getAllItems();
        
		//TextView tv = new TextView(this);
		//Button new_button = new Button(this);
		//new_button.setText("New");
		//tv.setText(category_text);
		/*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				1.0f);*/
		//tv.setLayoutParams(params);
		//new_button.setLayoutParams(params);
		
		/* OnClickListener on_click_new_button = new OnClickListener() {
		       @Override
		       public void onClick(View v) {
		         // TODO Auto-generated method stub
		    	 Intent intent = new Intent(CategoryActivity.this, NewItemActivity.class);
		    	 intent.putExtra("Category", category_text);
		    	 startActivity(intent);

		    	   
		       }
		     };
		new_button.setOnClickListener(on_click_new_button);*/
		//textView.setHeight(TableLayout.LayoutParams.WRAP_CONTENT);
		//tv.setBackgroundColor(Color.RED);
		//layout.addView(tv);
		System.out.println("Categorytext " + category_text);
		ArrayList <ImageView> imageViewList = new ArrayList <ImageView>();
		
		for(Item i : list){
			System.out.println("Category " + i.getCategory());
        	if(category_text.equalsIgnoreCase(i.getCategory()))
        	{
	        	ImageView image_view  = new ImageView(this);
	        	Uri image_uri =Uri.parse(i.getThumb_paths().get(0));
	        	image_view.setImageURI(image_uri);
	        	LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	    				LayoutParams.WRAP_CONTENT, 1.0f);
	        	image_view.setLayoutParams(image_params);
	        	imageViewList.add(image_view);
		        //System.out.println(i.toString());
		        System.out.println("Uri : " + image_uri.toString());
        	}
        	else
        	{
        		System.out.println("Failure i.getCategory() "+ i.getCategory()+ "category_text " + category_text);
        	}
		 }
		
		for(ImageView i : imageViewList){
			layout.addView(i);
		}
		
		//layout.addView(new_button);
		setContentView(layout);
		
		
		// Show the Up button in the action bar.
		setupActionBar();
	}
	
	

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_new:
            openSearch();
            return true;
        default:
            return super.onOptionsItemSelected(item);

			}

	}
	private void openSearch()
	{
		Intent intent = new Intent(CategoryActivity.this, NewItemActivity.class);
   	 	intent.putExtra("Category", category_text);
   	 	startActivity(intent);
	}
}