package olga.com.healthy.food.model;

import java.util.Vector;

import olga.com.healthy.food.R;

public class Food extends Details {
	private static final long serialVersionUID = -3292682687294137464L;
	
	private static final int ENERGY_ID = -1;
	private static final int CARBOHYDRATES_ID = -2;
	private static final int PROTEIN_ID = -3;
	private static final int FAT_ID = -4;

	public int imageId = -1;
	public String imagePath = "";
	
	public NutritionalValuePer100G energy;
	public NutritionalValuePer100G carbohydrates;
	public NutritionalValuePer100G protein;
	public NutritionalValuePer100G fat;
	public Vector<NutritionalValuePer100G> minerals;
	public Vector<NutritionalValuePer100G> vitamines;
	
	public Food(){
		super();
		imageId = R.drawable.meal_icon;
		initContainers();
	}
	
	public Food(Details details){
		super(details);
		if(details instanceof Food){
			this.imageId = ((Food)details).imageId;
			this.imagePath = ((Food)details).imagePath;
			if(imageId < 0 && imagePath == null){
				imageId = R.drawable.meal_icon;
			}
			initContainers();
		}
	}
	
	public Food(Food details)
	{
		this((Details)details);
	}
	
	private void initContainers(){
		energy = new NutritionalValuePer100G(ENERGY_ID);
		carbohydrates = new NutritionalValuePer100G(CARBOHYDRATES_ID);
		protein = new NutritionalValuePer100G(PROTEIN_ID);
		fat = new NutritionalValuePer100G(FAT_ID);
		minerals = new Vector<NutritionalValuePer100G>();
		vitamines = new Vector<NutritionalValuePer100G>();
	}
}
