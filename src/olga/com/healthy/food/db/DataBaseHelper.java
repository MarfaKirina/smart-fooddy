package olga.com.healthy.food.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

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
	
	final public static String VITAMINES_TABLE = "vitamines";
	final public static String MINERALS_TABLE = "minerals";
	final public static String FOOD_TABLE = "food";
	final private static String FOOD_MINERALS_TABLE = "food_minerals";
	final private static String FOOD_VITAMINES_TABLE = "food_vitamines";
	final private static String UNITS_TABLE = "units";
	final private static String DISH_TABLE = "dish";
	final private static String MEAL_TABLE = "meal";
	final private static String FOOD_DISH_TABLE = "food_dish";
	final private static String MEAL_DISH_FOOD_TABLE = "meal_dish_food";
	
	final private static String ID_COLUMN = "id";
	final private static String NAME_COLUMN = "name";
	final private static String DESCRIPTION_COLUMN = "description";
	final private static String IMAGE_PATH_COLUMN = "image_path";
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
	final private static String TIME_COLUMN = "time";
	final private static String MEAL_ID_COLUMN = "meal_id";
	final private static String IS_DISH_COLUMN = "is_dish";
	
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
	
	public Details getDetails(int id, String tableName){
		Details result;
		
		Cursor cursor = db.query(tableName, null, ID_COLUMN + "=" + id, null, null, null, null);

		if(!cursor.moveToFirst())
		{
			return null;
		}
		
		if(tableName.equals(FOOD_TABLE)){
			result = new Food();
			result.id = id;
			addFoodInfo(cursor, (Food)result);
		}else if(tableName.equals(VITAMINES_TABLE) || tableName.equals(MINERALS_TABLE)){
			result = new Constituent();
			result.id = id;
			if(tableName.equals(VITAMINES_TABLE)){
				((Constituent)result).food = getFoodList(id, FOOD_VITAMINES_TABLE);
			}else{
				((Constituent)result).food = getFoodList(id, FOOD_MINERALS_TABLE);
			}
		}else{
			throw new IllegalArgumentException("Do not support this table yet:" + tableName);
		}
		
		insertDetails(cursor, result);

		return result;
	}
	
	public Vector<Details> getVitamines(){
		Vector<Details> result = new Vector<Details>();
		try{
			Cursor cursor = db.query(VITAMINES_TABLE, null, null, null, null, null, null);
			insertDetails(cursor, result);
			addFoodList(result, VITAMINES_TABLE);
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
		if(!cursor.moveToFirst())
		{
			return;
		}
		
		do{
			Details details = new Details();
			insertDetails(cursor, details);
			result.add(details);
		}while(cursor.moveToNext());
	}
	
	private static void insertDetails(Cursor cursor, Details details){
		if(cursor == null || details == null)
		{
			throw new NullPointerException("One of the Input arguments is null");
		}
		int indexId = cursor.getColumnIndex(ID_COLUMN);
		int indexName = cursor.getColumnIndex(NAME_COLUMN);
		int indexDescription = cursor.getColumnIndex(DESCRIPTION_COLUMN);
		details.id = cursor.getInt(indexId);
		details.name = cursor.getString(indexName);
		details.description = cursor.getString(indexDescription);
	}
	
	public Vector<Details> getMinerals(){
		Vector<Details> result = new Vector<Details>();
		try{
			Cursor cursor = db.query(MINERALS_TABLE, null, null, null, null, null, null);
			insertDetails(cursor, result);
			addFoodList(result, MINERALS_TABLE);
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
		
		if(!cursor.moveToFirst()){
			return;
		}
		int i = 0;
		do{
			Food food = new Food(result.get(i));
			addFoodInfo(cursor, food);
			result.set(i, food);
			i++;
		}while(cursor.moveToNext());
	}
	
	private void addFoodInfo(Cursor cursor, Food food){
		if(cursor == null || food == null)
		{
			throw new NullPointerException("One of the Input arguments is null");
		}

		int indexImagePath = cursor.getColumnIndex(IMAGE_PATH_COLUMN);
		int energy = cursor.getColumnIndex(ENERGY_COLUMN);
		int energyUnits = cursor.getColumnIndex(ENERGY_UNITS_COLUMN);
		int cargohydrates = cursor.getColumnIndex(CARBOHYDRATES_COLUMN);
		int cargUnits = cursor.getColumnIndex(UNITS_CARBOHYDRATES_COLUMN);
		int fat = cursor.getColumnIndex(FAT_COLUMN);
		int fatUnits = cursor.getColumnIndex(FAT_UNITS_COLUMN);
		int protein = cursor.getColumnIndex(PROTEIN_COLUMN);
		int proteinUnits = cursor.getColumnIndex(PROTEIN_UNITS_COLUMN);

		if(indexImagePath >= 0){
			food.imagePath = cursor.getString(indexImagePath);
		}
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
	}
	
	private void addFoodList(Vector<Details> constituentList, String tableName)
	{
		for(int i = 0; i < constituentList.size(); i++)
		{
			Constituent element = new Constituent(constituentList.get(i));
			element.food = getFoodList(element.id, tableName);
			constituentList.set(i, element);
		}
	}
	
	public Vector<NutritionalValuePer100G> getFoodList(int constituentId, String table){
		return getList(constituentId, ID_COLUMN, table);
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
			String columnId;
			if(columnName == FOOD_ID_COLUMN){
				columnId = ID_COLUMN;
			}else {
				columnId = FOOD_ID_COLUMN;
			}
			Cursor cursor = db.query(tableName, new String[]{columnId, WEIGHT_COLUMN, UNITS_COLUMN}, 
					columnName + "=" + id, null, null, null, null);
			int componentId = cursor.getColumnIndex(columnId);
			int weight = cursor.getColumnIndex(WEIGHT_COLUMN);
			int units = cursor.getColumnIndex(UNITS_COLUMN);
			if(!cursor.moveToFirst())
			{
				return result;
			}
			
			do{
				NutritionalValuePer100G element = new NutritionalValuePer100G();
				element.constituentId = cursor.getInt(componentId);
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
	
	/**
	 * Constructs Vector with information about each connection - weight of component in 100 g of the food
	 * For example: table of the food, weights of the mineral or vitamin in all the food, that has this component.
	 * table of the vitamins, weights of the vitamins in some food, the same
	 * @param table - table of connection
	 * @param weights - short information about weight
	 * @param units - table of the units will be the same for all tables
	 * @return
	 */
	public static Vector<Details> joinInfo(Vector<Details> table, Vector<NutritionalValuePer100G> weights, Vector<SimpleItem> units) {
		if(weights == null || table == null || table.size() == 0 || units == null){
			return null;
		}
		Vector<Details> result = new Vector<Details>();
		for(int i = 0; i < weights.size(); i++){
			NutritionalValuePer100G value = weights.get(i);
			
			Details element = table.get(value.constituentId - 1);
			Details details = new Details(element);
			details.description = "";
			details.description += value.value;
			details.description += " " + units.get(value.unitsId - 1).value;
			result.add(details);
		}
		return result;
	}
	
	public Vector<Details> getDish(){
		Vector<Details> result = new Vector<Details>();
		try{
			Cursor cursor = db.query(DISH_TABLE, null, null, null, null, null, null);
			insertDetails(cursor, result);
			insertComponentsForDish(result);
		}catch(Exception e){
			Logger.log(e);
		}
		return result;
	}
	
	public void insertComponentsForDish(Vector<Details> dish){
		for(int i = 0; i < dish.size(); i++){
			Constituent dishElem = new Constituent(dish.get(i));
			dishElem.food = getList(dishElem.id, ID_COLUMN, FOOD_DISH_TABLE);
			dish.set(i, dishElem);
		}
	}
	
	

}
