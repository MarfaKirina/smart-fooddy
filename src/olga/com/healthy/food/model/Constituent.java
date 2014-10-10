package olga.com.healthy.food.model;

import java.util.Vector;

public class Constituent extends Details {

	private static final long serialVersionUID = 94060696868331969L;
	
	public Vector<NutritionalValuePer100G> food;

	public Constituent(){
		super();
		food = new Vector<NutritionalValuePer100G>();
	}
	
	public Constituent(Details details)
	{
		super(details);
		if(details instanceof Constituent){
			food = ((Constituent)details).food;
		}else{
			food = new Vector<NutritionalValuePer100G>();
		}
	}
	
}
