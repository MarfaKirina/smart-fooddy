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

	final private static String DB_NAME = "smart_fooddy_database";
	final private static int version = 1;
	
	final private static String VITAMINES_TABLE = "vitamines";
	final private static String MINERALS_TABLE = "minerals";
	final private static String FOOD_TABLE = "food";
	final private static String FOOD_MINERALS_TABLE = "food_minerals";
	final private static String FOOD_VITAMINES_TABLE = "food_vitamines";
	
	final private static String COLUMN_ID = "id";
	final private static String COLUMN_NAME = "name";
	final private static String COLUMN_DESCRIPTION = "description";
	final private static String COLUMN_ENERGY = "energy";
	final private static String COLUMN_CARBOHYDRATES = "carbohydrates";
	final private static String COLUMN_C_UNITS = "c_units";
	final private static String COLUMN_FAT = "fat";
	final private static String COLUMN_F_UNITS = "f_units";
	final private static String COLUMN_PROTEIN = "protein";
	final private static String COLUMNE_P_UNITS = "p_units";
	final private static String COLUMN_FOOD_ID = "food_id";
	final private static String COLUMN_WEIGHT = "weight";
	final private static String COLUMN_UNITS = "units";
	
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
		boolean isExists = false;//checkDataBase();
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
			String path = context.getFilesDir().getPath() + DB_NAME;
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
		OutputStream out = new FileOutputStream(context.getFilesDir().getPath() + DB_NAME);
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
		String path = context.getFilesDir().getPath() + DB_NAME;
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
			insertDetails(cursor, result);
		}catch(Exception e){
			Logger.log(e);
		}
		return result;
	}
	
	private static void insertDetails(Cursor cursor, Vector<Details> result)
	{
		if(cursor == null || result == null)
		{
			throw new NullPointerException("One of the Iiput arguments is null");
		}
		int indexId = cursor.getColumnIndex(COLUMN_ID);
		int indexName = cursor.getColumnIndex(COLUMN_NAME);
		int indexDescription = cursor.getColumnIndex(COLUMN_DESCRIPTION);
		cursor.moveToFirst();
		do{
			Details details = new Details();
			details.imageId = R.drawable.vitamin_icon;
			details.id = cursor.getInt(indexId);
			details.name = cursor.getString(indexName);
			details.description = cursor.getString(indexDescription);
			result.add(details);
		}while(cursor.moveToNext());
	}
	
	public Vector<Details> getMinerals(){
		Vector<Details> result = new Vector<Details>();
		try{
			Cursor cursor = db.query(MINERALS_TABLE, null, null, null, null, null, null);
			insertDetails(cursor, result);
		}catch(Exception e){
			Logger.log(e);
		}
		return result;
	}
	
	public Vector<Details> getFood(){
		Vector<Details> result = new Vector<Details>();
		try{
			Cursor cursor = db.query(FOOD_TABLE, null, null, null, null, null, null);
			insertDetails(cursor, result);
		}catch(Exception e){
			Logger.log(e);
		}
		return result;
	}

}
