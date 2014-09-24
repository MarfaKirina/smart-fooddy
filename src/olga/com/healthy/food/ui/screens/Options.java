package olga.com.healthy.food.ui.screens;

import olga.com.healthy.food.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Options extends Activity {

	private View vitamines;
	private View minerals;
	private View food;
	private View menu;
	private View personal_info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		vitamines = findViewById(R.id.vitamines);
		vitamines.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Options.this, List.class);
				intent.putExtra(List.TAB_INDEX, List.VITAMINES);
				startActivity(intent);
			}
		});
		minerals = findViewById(R.id.minerals);
		minerals.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Options.this, List.class);
				intent.putExtra(List.TAB_INDEX, List.MINERALS);
				startActivity(intent);
			}
		});
		food = findViewById(R.id.food);
		food.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Options.this, List.class);
				intent.putExtra(List.TAB_INDEX, List.FOOD);
				startActivity(intent);
			}
		});
		menu = findViewById(R.id.diet);
		menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Options.this, Menu.class));
			}
		});
		personal_info = findViewById(R.id.personal_info);
		personal_info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Options.this, Registration.class));
			}
		});
	}
	
}
