package olga.com.healthy.food;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Pref {
	
	public static final String KEY_IS_NOT_FIRST_TIME = "is_first_time";

	private static final String NAME = "olga.com.healthy.food";
	
	private SharedPreferences shPref;
	
	public Pref(Context context){
		shPref = context.getApplicationContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}
	
	public void setString(String key, String value){
		Editor editor = shPref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public void setInt(String key, int value){
		Editor editor = shPref.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void setBoolean(String key, boolean value){
		Editor editor = shPref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public String getString(String key){
		return shPref.getString(key, null);
	}
	
	public int getInt(String key, int defValue){
		return shPref.getInt(key, defValue);
	}
	
	public boolean getBoolean(String key){
		return shPref.getBoolean(key, false);
	}
	
}
