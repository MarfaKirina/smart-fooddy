package olga.com.healthy.food.model;

import java.util.Vector;

public class Food extends Details {
	private static final long serialVersionUID = -3292682687294137464L;
	
	public Vector<NutritionalValuePer100G> energy;
	public Vector<NutritionalValuePer100G> carbohydrates;
	public Vector<NutritionalValuePer100G> protein;
	public Vector<NutritionalValuePer100G> fat;
	public Vector<NutritionalValuePer100G> minerals;
	public Vector<NutritionalValuePer100G> vitamines;
}
