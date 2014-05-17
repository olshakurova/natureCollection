package com.example.nature;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.businesslayer.Item;
import com.example.sqlite.MyOwnSQLiteHelper;

//http://www.coderzheaven.com/2012/04/20/select-an-image-from-gallery-in-android-and-show-it-in-an-imageview/

public class NewItemActivity extends Activity {
	
	 private static final int SELECT_PICTURE = 1;
	 private ImageView image;
	 String thumb_path;
	 private String item_name;
	 private String category;
	 private String details;
	 private String image_path;
	 TextView observation_date;
	 
	 
	 Calendar c = Calendar.getInstance();
	 int year = c.get(Calendar.YEAR);
	 int month = c.get(Calendar.MONTH);
	 int day = c.get(Calendar.DAY_OF_MONTH);
     int DIALOG_DATE_ID = 1;
     
     TextView name_textview;
     EditText name_edittext;
     TextView details_textview;
     EditText details_edittext;
     

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent1 = getIntent();
		category = (String)intent1.getSerializableExtra("Category");
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1.0f);
		
		name_textview = new TextView(this);
		name_textview.setLayoutParams(params);
		name_textview.setText("Name");
		name_edittext = new EditText(this);
		name_edittext.setLayoutParams(params);
		name_edittext.setHint("Unknown");
		

		details_textview = new TextView(this);
		details_textview.setLayoutParams(params);
		details_textview.setText("Details");
		details_edittext = new EditText(this);
		details_edittext.setLayoutParams(params);
		details_edittext.setHint("Observation details");
		
		//http://www.mkyong.com/android/android-date-picker-example
		/*DatePicker observation_date = new DatePicker(this);
		observation_date.setLayoutParams(params);
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// set current date into datepicker
		observation_date.init(year, month, day, null);*/
		observation_date = new TextView(this);
		observation_date.setLayoutParams(params);
		observation_date.setText(day + "." + month + "." + year);

		OnClickListener observation_date_listener = new OnClickListener(){
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showDialog(DIALOG_DATE_ID);
			}
				 
		};
		
		observation_date.setOnClickListener(observation_date_listener);
		
		Button browse_button = new Button(this);
		
		LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1.0f);
		browse_button.setLayoutParams(params);
		browse_button.setText(R.string.browse_button_text);
		browse_button.setOnClickListener(new OnClickListener(){
				public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
                
            }
		});
		
		image  = new ImageView(this);
		image.setLayoutParams(image_params);
		
		
		
		Button save_button = new Button(this);
		save_button.setLayoutParams(params);
		save_button.setText("Save");
		save_button.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
            Intent intent = new Intent();
            String item_name = name_edittext.getText().toString();
            String item_details = details_edittext.getText().toString();
            /*Item(String name, String category, String photo_date, String info,
			ArrayList<String> image_paths, ArrayList<String> thumb_paths) */
            ArrayList<String> image_paths = new ArrayList<String>();
            image_paths.add(image_path);
            ArrayList<String> thumb_paths = new ArrayList<String>();
            thumb_paths.add(thumb_path);
            Item item  = new Item (item_name, category, observation_date.getText().toString(), item_details,image_paths, thumb_paths);
            
            MyOwnSQLiteHelper mh =  new MyOwnSQLiteHelper(NewItemActivity.this);
            mh.addItem(item);
           
			}
			
		});
		layout.addView(name_textview);
		layout.addView(name_edittext);
		layout.addView(details_textview);
		layout.addView(details_edittext);
		layout.addView(browse_button);
		layout.addView(image);
		layout.addView(observation_date);
		layout.addView(save_button);
		setContentView(layout);
		
		
	}
	
	protected Dialog onCreateDialog(int id) {
	      if (id == DIALOG_DATE_ID) {
	    	  //DatePickerDialog(Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
	        DatePickerDialog tpd = new DatePickerDialog(NewItemActivity.this, myCallBack, year, month, day);
	        return tpd;
	      }
	      return this.onCreateDialog(id);
	    }
	
	OnDateSetListener myCallBack = new OnDateSetListener() {
		
	    public void onDateSet(DatePicker view, int new_year, int new_month,
	        int new_day)
	    {
	      year = new_year;
	      month = new_month;
	      day = new_day;
	      observation_date.setText(day + "." + month + "." + year);
	    }
	 };
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
	        if (resultCode == RESULT_OK) {
	            if (requestCode == SELECT_PICTURE) {
	               Uri image_uri = data.getData();
	                //selectedImagePath = getPath(selected_image_uri);
	                System.out.println("Image Path : " + image_uri.toString());//selectedImagePath was null?
	                image_path = image_uri.toString();
	                // content://com.android.providers.media.documents/document/image%3A
	                thumb_path = getThumbPath(image_uri);
	                Uri thumb_uri =Uri.parse(thumb_path);
	                image.setImageURI(thumb_uri);
	                System.out.println("Thumb Path : " + thumb_path);
	                
	            }
	        }
	        else {
	            Toast.makeText(this, "Very wrong result", Toast.LENGTH_SHORT).show();
	          }
	    }
	 
	 	public String getThumbPath(Uri uri) {
              String[] projection = {MediaStore.Images.Media._ID};
              String result = null;

              Cursor cursor = getContentResolver().query(uri, projection, 
                                                           null, null, null);
              cursor.moveToFirst();
                long image_id = cursor.getLong(cursor  
                         .getColumnIndex(MediaStore.Images.Media._ID)); 
                System.out.println("id is " + image_id);
             cursor.close();

	 	    cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(
	 	            getContentResolver(), image_id,
	 	            MediaStore.Images.Thumbnails.MINI_KIND,
	 	            null);
	 	    System.out.println("Cursor rows number: " + cursor.getCount());
	 	    if (cursor != null && cursor.getCount() > 0) {
	 	        cursor.moveToFirst();
	 	        result = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
	 	        cursor.close();
	 	        System.out.println("Result" + result);
	 	    }
	 	    return result;
	 	}
	    public String getPath(Uri uri) {
	        String[] projection = { MediaStore.Images.Media.DATA };
			Cursor  cursor = getContentResolver().query(
					uri, //  Get data for specific image URI
					projection, //  Which columns to return
					null, //  WHERE clause; which rows to return (all rows)
					null, //  WHERE clause selection arguments (none)
					null//  Order-by clause (ascending by name)
					);
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
	    }
	    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_item, menu);
		return true;
	}

}
