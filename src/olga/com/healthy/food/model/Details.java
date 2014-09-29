package olga.com.healthy.food.model;

import java.io.Serializable;
import java.util.Vector;

import olga.com.healthy.food.R;

public class Details implements Serializable{
	protected static final long serialVersionUID = 1L;
	public int id;
	public String name = "";
	public String description = "";
	public int imageId = R.drawable.meal_icon;
	
	public Details(){
		
	}
	
	public Details(Details details){
		this.id = details.id;
		this.name = details.name;
		this.description = details.description;
		this.imageId = details.imageId;
	}
	
	public static Vector<Details> getConnections(Vector<Details> table, Vector<NutritionalValuePer100G> connection, Vector<SimpleItem> units) {
		if(connection == null || table == null || units == null){
			return null;
		}
		Vector<Details> result = new Vector<Details>();
		for(int i = 0; i < connection.size(); i++){
			Details details = new Details();
			NutritionalValuePer100G value = connection.get(i);
			details.id = value.constituentId;
			Food food = (Food)table.get(details.id - 1);
			details.name = food.name;
			details.description = "";
			details.description += value.value;
			details.description += " " + units.get(value.unitsId - 1);
			result.add(details);
		}
		return result;
	}
}
