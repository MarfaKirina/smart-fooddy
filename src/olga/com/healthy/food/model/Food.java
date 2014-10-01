package olga.com.healthy.food.model;

import java.util.Vector;

public class Food extends Details {
	private static final long serialVersionUID = -3292682687294137464L;
	
	private static final int ENERGY_ID = -1;
	private static final int CARBOHYDRATES_ID = -2;
	private static final int PROTEIN_ID = -3;
	private static final int FAT_ID = -4;
	
	public NutritionalValuePer100G energy;
	public NutritionalValuePer100G carbohydrates;
	public NutritionalValuePer100G protein;
	public NutritionalValuePer100G fat;
	public Vector<NutritionalValuePer100G> minerals;
	public Vector<NutritionalValuePer100G> vitamines;
	
	public Food(){
		super();
		initContainers();
	}
	
	public Food(Details details)
	{
		super(details);
		initContainers();
	}
	
	private void initContainers(){
		energy = new NutritionalValuePer100G(ENERGY_ID, "energy");
		carbohydrates = new NutritionalValuePer100G(CARBOHYDRATES_ID, "carbohydtates");
		protein = new NutritionalValuePer100G(PROTEIN_ID, "protein");
		fat = new NutritionalValuePer100G(FAT_ID, "fat");
		minerals = new Vector<NutritionalValuePer100G>();
		vitamines = new Vector<NutritionalValuePer100G>();
	}
}
