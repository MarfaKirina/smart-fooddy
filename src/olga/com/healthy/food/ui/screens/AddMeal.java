package olga.com.healthy.food.ui.screens;

import olga.com.healthy.food.R;
import olga.com.healthy.food.db.DataBaseHelper;
import olga.com.healthy.food.ui.helpers.ListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AddMeal extends Activity {

	private ListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_meal_screen);
		ListView list = (ListView)findViewById(R.id.list);
		adapter = new ListAdapter(this);
		list.setAdapter(adapter);
		DataBaseHelper db = new DataBaseHelper(this);
		db.openDataBase();
	}
}
