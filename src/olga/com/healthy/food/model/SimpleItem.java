package olga.com.healthy.food.model;

import java.io.Serializable;

public class SimpleItem implements Serializable{

	private static final long serialVersionUID = 4829239217021985693L;
	
	public int labelId;
	public String value;
	public SimpleItem(int labelId, String value){
		this.labelId = labelId;
		this.value = value;
	}
}
