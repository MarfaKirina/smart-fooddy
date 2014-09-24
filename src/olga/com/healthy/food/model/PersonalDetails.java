package olga.com.healthy.food.model;

import olga.com.healthy.food.Pref;
import android.content.Context;

public class PersonalDetails {
	public static final String MALE = "male";
	public static final String FEMALE = "female";
	
	public static final String LIFESTYLE_ACTIVE = "ls_active";
	public static final String LIFESTYLE_SEDENTARY = "ls_sedentary";
	public static final String LIFESTYLE_HARD_WORK = "ls_hard_work";
	public static final String LIFESTYLE_PREGNANT = "ls_pregnant";
	public static final String LIFESTYLE_BREAST_FEEDING = "ls_breast_feeding";
	
	private static final String KEY_NAME = "name";
	private static final String KEY_AGE = "age";
	private static final String KEY_HEIGHT = "height";
	private static final String KEY_SKIN_COLOR = "skin color";
	private static final String KEY_SEX = "sex";
	private static final String KEY_LIFESTYLE= "lifestyle";
	
	private String name;
	private int age;
	private int height;
	private int skinColor;
	private String sex;
	private String lifestyle;
	
	public PersonalDetails(){
		name = "";
		age = -1;
		height = -1;
		skinColor = -1;
		sex = "";
		lifestyle = "";
	}
	/**
	 * Make copy of data
	 * @param data
	 */
	public PersonalDetails(PersonalDetails data){
		name = data.name;
		age = data.age;
		height = data.height;
		skinColor = data.skinColor;
		sex = data.sex;
		lifestyle = data.lifestyle;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getSkinColor() {
		return skinColor;
	}
	public void setSkinColor(int skinColor) {
		this.skinColor = skinColor;
	}
	public String getSex() {
		if(sex == null){
			return "";
		}
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLifestyle() {
		if(lifestyle == null){
			return "";
		}
		return lifestyle;
	}
	public void setLifestyle(String lifestyle) {
		this.lifestyle = lifestyle;
	}
	
	public void save(Context context){
		Pref pref = new Pref(context);
		pref.setInt(KEY_AGE, age);
		pref.setInt(KEY_HEIGHT, height);
		pref.setString(KEY_LIFESTYLE, lifestyle);
		pref.setString(KEY_NAME, name);
		pref.setString(KEY_SEX, sex);
		pref.setInt(KEY_SKIN_COLOR, skinColor);
	}
	
	public void load(Context context){
		Pref pref = new Pref(context);
		this.age = pref.getInt(KEY_AGE, -1);
		this.height = pref.getInt(KEY_HEIGHT, -1);
		this.lifestyle = pref.getString(KEY_LIFESTYLE);
		this.name = pref.getString(KEY_NAME);
		this.sex = pref.getString(KEY_SEX);
		this.skinColor = pref.getInt(KEY_SKIN_COLOR, -1);
	}
}
