package com.example.nature;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.businesslayer.*;
import com.example.sqlite.*;
import com.example.utils.*;
//http://img-fotki.yandex.ru/get/6313/121620033.73/0_7c03c_c76542_XL bug

public class MainActivity extends Activity {

	private static final int ROWS = 2;
	private static final int COLS = 3;
	Button buttons [][] = new Button[ROWS][COLS]; //?
	private ItemCollection item_collection;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 MyOwnSQLiteHelper mh = new MyOwnSQLiteHelper(this);
		 
	        /**
	         * CRUD Operations
	         * */
	        // add 
		 	item_collection = new ItemCollection(true);
		 	for(Item i : item_collection){
		        mh.addItem(i);
		        System.out.println(i.toString()+" Added ");
			 	}
	       
	 
		TableLayout table = (TableLayout)findViewById(R.id.categoriesTable);
		int cat_num=0;
		for(int rows=0; rows < ROWS; rows++)
		{
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.MATCH_PARENT,
					1.0f));
			table.addView(tableRow);
			for(int cols=0; cols < COLS; cols++){
				final Button button = new Button(this);//final for method OnClick, showed err without final
				TableRow.LayoutParams params = new TableRow.LayoutParams
				(TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.MATCH_PARENT,
				1.0f);
				params.setMargins(10, 10, 10, 10);
				button.setLayoutParams(params);
				String category =  Category.values()[cat_num].getCategory_name();
				button.setText(category);
				button.setPadding(0, 0, 0, 0);
				
				int ic_id = getResources().getIdentifier(
						"ic_"+cat_num,
						"drawable",
						getPackageName());
				//not all buttons are of the same width when we set a background image??????? 9patch?
				button.setBackgroundResource(ic_id);
				/*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ic_id);
				Bitmap scaled_bm = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
				button.setBackground(new BitmapDrawable(getResources(), scaled_bm));*/
				
				button.setOnClickListener(new  View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
						intent.putExtra("Category", button.getText().toString());
						/*try {
							intent.putExtra("ItemCollection", ObjectStringSerializer.toString(item_collection));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						startActivity(intent);
					}
				});
				tableRow.addView(button);
				cat_num++;
			}
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
