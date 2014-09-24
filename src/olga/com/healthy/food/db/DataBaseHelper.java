package olga.com.healthy.food.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import olga.com.healthy.food.R;
import olga.com.healthy.food.model.Details;
import olga.com.healthy.food.utils.Logger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	final private static String DB_PATH = "/data/data/olga.com.healthy.food/databases/";
	final private static String DB_NAME = "smart_fooddy_database";
	final private static int version = 1;
	
	final private static String VITAMINES_TABLE = "vitamines";
	
	final private static String COLUMN_ID = "_id";
	final private static String COLUMN_NAME = "name";
	final private static String COLUMN_DESCRIPTION = "description";
	
	private Context context;
	private SQLiteDatabase db;
	
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
	public void createDB() throws IOException{
		boolean isExists = checkDataBase();
		if(!isExists){
			this.getReadableDatabase();
			try{
				copyDataBase();
			}catch(Exception e){
				throw new IOException("Error copying database" + e);
			}
		}
	}
	
	private boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		try{
			String path = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openOrCreateDatabase(path, null);
		}catch(Exception e){
			return false;
		}
		if(checkDB != null){
			checkDB.close();
		}
		return checkDB != null;
	}
	
	private void copyDataBase() throws IOException{
		InputStream is = context.getAssets().open("smart_fooddy_database");
		OutputStream out = new FileOutputStream(DB_PATH + DB_NAME);
		byte[] buffer = new byte[1024];
		int length = is.read(buffer);
		while(length > 0){
			out.write(buffer, 0, length);
			length = is.read(buffer);
		}
		
		out.flush();
		out.close();
		is.close();
	}
	
	public void openDataBase(){
		String path = DB_PATH + DB_NAME;
		db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
	}
	
	@Override
	public synchronized void close() {
		if(db != null){
			db.close();
		}
		super.close();
	}
	
	public Vector<Details> getVitamines(){
		Vector<Details> result = new Vector<Details>();
		try{
			Cursor cursor = db.query(VITAMINES_TABLE, null, null, null, null, null, null);
			int indexId = cursor.getColumnIndex("_id");
			int indexName = cursor.getColumnIndex("name");
			int indexDescription = cursor.getColumnIndex("description");
			while(cursor.moveToNext()){
				Details vitamin = new Details();
				vitamin.hasProducts = true;
				vitamin.imageId = R.drawable.vitamin_icon;
				vitamin.id = cursor.getInt(indexId);
				vitamin.name = cursor.getString(indexName);
				vitamin.description = cursor.getString(indexDescription);
				result.add(vitamin);
			}
		}catch(Exception e){
			Logger.log(e);
		}
		return result;
	}
	
	public Vector<Details> getMinerals(){
		return new Vector<Details>();
	}
	
	public Vector<Details> getFood(){
		return new Vector<Details>();
	}

}
