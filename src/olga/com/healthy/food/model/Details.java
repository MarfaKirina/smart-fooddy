package olga.com.healthy.food.model;

import java.io.Serializable;

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
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Details)
		{
			Details d = (Details)o;
			if(id == d.id){
				return name.equals(d.name);
			}else{
				return false;
			}
		}
		return super.equals(o);
	}
}
