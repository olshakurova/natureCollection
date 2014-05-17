/*
 * Source of the code:
 * http://hmkcode.com/android-simple-sqlite-database-tutorial/
 * 
 * Some edits by JV 2014-03-02
 */

package com.example.sqlite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.example.businesslayer.Item;

public class MyOwnSQLiteHelper extends SQLiteOpenHelper
{

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ItemDB";

    public MyOwnSQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*constructor: public Item(String name, String category, Date photo_date, String details,
	ArrayList<String> image_paths, ArrayList<String> thumb_paths, )*/
    // Books table name
    private static final String TABLE_ITEMS = "items";
    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_PHOTODATE = "photo_date";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_IMAGES = "images";
    private static final String KEY_THUMBS = "thumbs";
    
    private static final String[] COLUMNS = {
    	KEY_ID,
    	KEY_NAME,
    	KEY_CATEGORY,
    	KEY_PHOTODATE, 
    	KEY_DETAILS,
    	KEY_IMAGES,
    	KEY_THUMBS };

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // SQL statement to create table
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEMS + "( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
                + " TEXT, " +  KEY_CATEGORY + " TEXT, " + KEY_PHOTODATE + " TEXT, " 
                + KEY_DETAILS + " TEXT, " + KEY_IMAGES + " TEXT, " + KEY_THUMBS + " TEXT )";

        // create table
        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        // create table again
        this.onCreate(db);
    }
    
    public void destroy()
    {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
    }

    // ---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete)+ get all
     * + delete all
     * KEY_ID,
    	KEY_NAME,
    	KEY_CATEGORY,
    	KEY_PHOTODATE, 
    	KEY_DETAILS,
    	KEY_IMAGES,
    	KEY_THUMBS
     */
    public void addItem(Item item)
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_CATEGORY, item.getCategory());
        values.put(KEY_PHOTODATE, item.getPhoto_date());
        values.put(KEY_DETAILS, item.getInfo());
        values.put(KEY_IMAGES, TextUtils.join(",", item.getImage_paths()));
        //http://stackoverflow.com/questions/599161/best-way-to-convert-an-arraylist-to-a-string
        values.put(KEY_THUMBS, TextUtils.join(",", item.getThumb_paths()));

        // 3. insert
        long rowId = db.insert(TABLE_ITEMS, // table
                null, // nullColumnHack
                values); // key/value -> keys = column names/ values = column
                         // values
        
        // 4. close
        db.close();
             
        // for logging
        if (rowId != -1) // If no error occured...
        {
            item.setId((int)rowId);
            Log.d("D", item.toString());
        }
    }

    public Item getItem(long id)
    {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = // Reader, ResultSet,
                        // "One row at a time pipe to database"
        db.query(TABLE_ITEMS, // a. table
                COLUMNS, // b. column names
                KEY_ID + " = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit = How many items to fetch, if many available

        // 3. if we got results get the first one
        if (cursor != null && cursor.moveToFirst())
        {
             // 4. build object
        /* 	KEY_ID,
        	KEY_NAME,
        	KEY_CATEGORY,
        	KEY_PHOTODATE, 
        	KEY_DETAILS,
        	KEY_IMAGES,
        	KEY_THUMBS
         */
            Item item = new Item();
            // Taking the values from the columns, indexes in CREATE TABLE order
            item.setId(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setCategory(cursor.getString(2));
            /*SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date date;
			try {
				date = (Date) df.parse(String.valueOf(cursor.getInt(3)));
				item.setPhoto_date(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
            item.setPhoto_date(cursor.getString(3));
            item.setInfo(cursor.getString(4));
            ArrayList<String> img_paths = (ArrayList<String>) Arrays.asList(cursor.getString(5).split(","));
            //http://stackoverflow.com/questions/7488643/java-how-to-convert-comma-separated-string-to-arraylist
            item.setImage_paths(img_paths);
            ArrayList<String> th_paths = (ArrayList<String>) Arrays.asList(cursor.getString(6).split(","));
            item.setThumb_paths(th_paths);

            // Close database connection, release external file system resources
            db.close();
            
            // log
            Log.d("getItem(" + id + ")", item.toString());

            // 5. return book
            return item;
        }
        else
        {
            // Close database connection, release external file system resources
            db.close();
            return null;
        }
    }

    public List<Item> getAllItems()
    {
        List<Item> items = new ArrayList<Item>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_ITEMS;

        // 2. get reference to writable DB // Or Readable???
        SQLiteDatabase db = this.getReadableDatabase(); // Readable???
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Item item = null;
        if (cursor.moveToFirst())   // Would just while(cursor.moveToNext()) work ???
        {
            do
            {
                item = new Item();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setCategory(cursor.getString(2));
                item.setPhoto_date(cursor.getString(3));
                item.setInfo(cursor.getString(4));
                ArrayList<String> img_paths =  new ArrayList<String>(Arrays.asList(cursor.getString(5).split(",")));
                //http://stackoverflow.com/questions/18104278/how-to-convert-a-comma-seperated-string-to-arraylist-in-java
                item.setImage_paths(img_paths);
                ArrayList<String> th_paths = new ArrayList<String>(Arrays.asList(cursor.getString(6).split(",")));
                item.setThumb_paths(th_paths);

                // Add 
                items.add(item);
            }
            while (cursor.moveToNext());
        }
        // Close database connection, release external file system resources
        db.close();
        
        Log.d("getAllItems", items.toString());

        // return books
        return items;
    }

   /* public int updateBook(Book book)
    {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle()); // get title
        values.put("author", book.getAuthor()); // get author

        // 3. updating row
        int rowsAffected = db.update(TABLE_BOOKS, // table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[] { String.valueOf(book.getId()) }); // selection
                                                                // args

        // 4. close
        db.close();

        // log
        Log.d("updateBook", rowsAffected + " row(s) updated: " + book.toString());

        return rowsAffected;

    }

    public void deleteBook(Book book)
    {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        int rowsAffected = db.delete(TABLE_BOOKS, // table name
                KEY_ID + " = ?", // selections
                new String[] { String.valueOf(book.getId()) }); // selections
                                                                // args

        // 3. close
        db.close();

        // log
        Log.d("deleteBook", rowsAffected + " row(s) deleted: " + book.toString());
        // log
    }
*/
}
