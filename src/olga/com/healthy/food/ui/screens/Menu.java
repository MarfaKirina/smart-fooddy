package olga.com.healthy.food.ui.screens;

import olga.com.healthy.food.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		View changeDate = findViewById(R.id.change_date);
		
		View addMealView = findViewById(R.id.add_meal);
		addMealView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu.this, olga.com.healthy.food.ui.screens.AddMeal.class);
				Menu.this.startActivity(intent);
			}
		});
	}
}
