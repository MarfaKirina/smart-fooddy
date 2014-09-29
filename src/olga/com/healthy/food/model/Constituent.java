package olga.com.healthy.food.model;

import java.util.Vector;

public class Constituent extends Details {

	private static final long serialVersionUID = 94060696868331969L;
	
	public Vector<NutritionalValuePer100G> food;

	public Constituent(Details details)
	{
		super(details);
		food = new Vector<NutritionalValuePer100G>();
	}
	
}
