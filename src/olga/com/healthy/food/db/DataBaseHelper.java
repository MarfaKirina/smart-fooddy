package olga.com.healthy.food.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import olga.com.healthy.food.R;
import olga.com.healthy.food.model.Constituent;
import olga.com.healthy.food.model.Details;
import olga.com.healthy.food.model.Food;
import olga.com.healthy.food.model.NutritionalValuePer100G;
import olga.com.healthy.food.model.SimpleItem;
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
	final private static String UNITS_TABLE = "units";
	
	final private static String ID_COLUMN = "id";
	final private static String NAME_COLUMN = "name";
	final private static String DESCRIPTION_COLUMN = "description";
	final private static String ENERGY_COLUMN = "energy";
	final private static String ENERGY_UNITS_COLUMN = "energy_units";
	final private static String CARBOHYDRATES_COLUMN = "carbohydrates";
	final private static String UNITS_CARBOHYDRATES_COLUMN = "c_units";
	final private static String FAT_COLUMN = "fat";
	final private static String FAT_UNITS_COLUMN = "f_units";
	final private static String PROTEIN_COLUMN = "protein";
	final private static String PROTEIN_UNITS_COLUMN = "p_units";
	final private static String FOOD_ID_COLUMN = "food_id";
	final private static String WEIGHT_COLUMN = "weight";
	final private static String UNITS_COLUMN = "units";
	
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
			addFoodList(result);
		}catch(Exception e){
			Logger.log(e);
		}
		return result;
	}
	
	private static void insertDetails(Cursor cursor, Vector<Details> result){
		if(cursor == null || result == null)
		{
			throw new NullPointerException("One of the Input arguments is null");
		}
		int indexId = cursor.getColumnIndex(ID_COLUMN);
		int indexName = cursor.getColumnIndex(NAME_COLUMN);
		int indexDescription = cursor.getColumnIndex(DESCRIPTION_COLUMN);
		if(!cursor.moveToFirst())
		{
			return;
		}
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
			addFoodList(result);
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
			addFoodInfo(cursor, result);
		}catch(Exception e){
			Logger.log(e);
		}
		return result;
	}
	
	private void addFoodInfo(Cursor cursor, Vector<Details> result){
		if(cursor == null || result == null)
		{
			throw new NullPointerException("One of the Input arguments is null");
		}
		int energy = cursor.getColumnIndex(ENERGY_COLUMN);
		int energyUnits = cursor.getColumnIndex(ENERGY_UNITS_COLUMN);
		int cargohydrates = cursor.getColumnIndex(CARBOHYDRATES_COLUMN);
		int cargUnits = cursor.getColumnIndex(UNITS_CARBOHYDRATES_COLUMN);
		int fat = cursor.getColumnIndex(FAT_COLUMN);
		int fatUnits = cursor.getColumnIndex(FAT_UNITS_COLUMN);
		int protein = cursor.getColumnIndex(PROTEIN_COLUMN);
		int proteinUnits = cursor.getColumnIndex(PROTEIN_UNITS_COLUMN);
		
		if(!cursor.moveToFirst()){
			return;
		}
		int i = 0;
		do{
			Food food = new Food(result.get(i));
			food.energy.value = cursor.getDouble(energy);
			food.energy.unitsId = cursor.getInt(energyUnits);
			food.carbohydrates.value = cursor.getDouble(cargohydrates);
			food.carbohydrates.unitsId = cursor.getInt(cargUnits);
			food.fat.value = cursor.getDouble(fat);
			food.fat.unitsId = cursor.getInt(fatUnits);
			food.protein.value = cursor.getDouble(protein);
			food.protein.unitsId = cursor.getInt(proteinUnits);
			food.minerals = getMineralsList(food.id);
			food.vitamines = getVitaminesList(food.id);
			result.set(i, food);
			i++;
		}while(cursor.moveToNext());
	}
	
	private void addFoodList(Vector<Details> constituentList)
	{
		for(int i = 0; i < constituentList.size(); i++)
		{
			Constituent element = new Constituent(constituentList.get(i));
			element.food = getFoodList(element.id);
			constituentList.set(i, element);
		}
	}
	
	public Vector<NutritionalValuePer100G> getFoodList(int constituentId){
		return getList(constituentId, ID_COLUMN, FOOD_VITAMINES_TABLE);
	}
	
	public Vector<NutritionalValuePer100G> getVitaminesList(int foodId)
	{
		return getList(foodId, FOOD_ID_COLUMN, FOOD_VITAMINES_TABLE);
	}
	
	public Vector<NutritionalValuePer100G> getMineralsList(int foodId)
	{
		return getList(foodId, FOOD_ID_COLUMN, FOOD_MINERALS_TABLE);
	}
	
	private Vector<NutritionalValuePer100G> getList(int id, String columnName, String tableName){
		Vector<NutritionalValuePer100G> result = new Vector<NutritionalValuePer100G>();
		try{
			Cursor cursor = db.query(tableName, new String[]{FOOD_ID_COLUMN, WEIGHT_COLUMN, UNITS_COLUMN}, 
					columnName + "=" + id, null, null, null, null);
			int foodId = cursor.getColumnIndex(FOOD_ID_COLUMN);
			int weight = cursor.getColumnIndex(WEIGHT_COLUMN);
			int units = cursor.getColumnIndex(UNITS_COLUMN);
			if(!cursor.moveToFirst())
			{
				return result;
			}
			
			do{
				NutritionalValuePer100G element = new NutritionalValuePer100G();
				element.constituentId = cursor.getInt(foodId);
				element.value = cursor.getDouble(weight);
				element.unitsId = cursor.getInt(units);
				result.add(element);
			}while(cursor.moveToNext());
			
			
		}catch(Exception e){
			Logger.log(e);
		}
		return result;
	}
	
	public Vector<SimpleItem> getUnits()
	{
		Vector<SimpleItem> result = new Vector<SimpleItem>();
		try{
			Cursor cursor = db.query(UNITS_TABLE, null, null, null, null, null, null);
			int id = cursor.getColumnIndex(ID_COLUMN);
			int name = cursor.getColumnIndex(NAME_COLUMN);
			cursor.moveToFirst();
			do{
				SimpleItem item = new SimpleItem(cursor.getInt(id), cursor.getString(name));
				result.add(item);
			}while(cursor.moveToNext());
		}catch(Exception e){
			Logger.log(e);
		}
		return result;
	}

}
