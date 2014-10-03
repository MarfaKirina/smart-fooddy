package olga.com.healthy.food.ui.screens;

import java.io.IOException;
import java.util.Vector;

import olga.com.healthy.food.R;
import olga.com.healthy.food.db.DataBaseHelper;
import olga.com.healthy.food.model.Details;
import olga.com.healthy.food.model.Food;
import olga.com.healthy.food.ui.helpers.ListAdapter;
import olga.com.healthy.food.utils.Logger;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class List extends ListActivity {
	public static String TAB_INDEX = "tab_index";
	public static final int VITAMINES = 0;
	public static final int MINERALS = 1;
	public static final int FOOD = 2;
	
	private View vitamines;
	private View minerals;
	private View food;
	private View addItem;
	private int tab;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		Intent intent = getIntent();
		registerForContextMenu(getListView());
		tab = intent.getIntExtra(TAB_INDEX, VITAMINES);
		vitamines = findViewById(R.id.vitamines);
		vitamines.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activateTab(VITAMINES);
			}
		});
		minerals = findViewById(R.id.minerals);
		minerals.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activateTab(MINERALS);
			}
		});
		food = findViewById(R.id.food);
		food.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activateTab(FOOD);
			}
		});

		activateTab(tab);
	}
	
	
	private void activateTab(int tabIndex){

		View activeView = null;
		View [] inactiveViews = new View[2];
		switch(tabIndex){
		case MINERALS:
			activeView = minerals;
			inactiveViews[0] = vitamines;
			inactiveViews[1] = food;
			break;
		case FOOD:
			activeView = food;
			inactiveViews[0] = vitamines;
			inactiveViews[1] = minerals;
			break;
		case VITAMINES:
		default:
			activeView = vitamines;
			inactiveViews[0] = minerals;
			inactiveViews[1] = food;
			break;
		}
		activeView.setBackgroundResource(R.drawable.bg_tab_shape);
		activeView.setEnabled(false);
		for(int i = 0; i < inactiveViews.length; i++){
			inactiveViews[i].setBackgroundResource(R.drawable.bg_tab_sel_shape);
			inactiveViews[i].setEnabled(true);
		}
		
		DataBaseHelper dbHelper = new DataBaseHelper(List.this);
		try {
			dbHelper.createDB();
		} catch (IOException e) {
			Logger.log(e);
		}
		try{
			dbHelper.openDataBase();
		}catch(Exception e){
			Logger.log(e);
		}

		final ListAdapter adapter = new ListAdapter(List.this);
		switch(tabIndex){
		case VITAMINES:
			insertVitamines(adapter, dbHelper);
			break;
		case MINERALS:
			insertMinerals(adapter, dbHelper);
			break;
		case FOOD:
			insertFood(adapter, dbHelper);
			break;
		}
		
		dbHelper.close();
		getListView().setAdapter(adapter);
	}
	
	private void insertVitamines(ListAdapter adapter, DataBaseHelper dbHelper){
		try{
			Vector<Details> vitamines = dbHelper.getVitamines();
			//vitamines.insertElementAt(getAddItem(), 0);
			adapter.setList(vitamines, DataBaseHelper.VITAMINES_TABLE);
		}catch(Exception e){
			Logger.log(e);
		}
	}
	
	private void insertMinerals(ListAdapter adapter, DataBaseHelper dbHelper){
		Vector<Details> minerals = dbHelper.getMinerals();
		//minerals.insertElementAt(getAddItem(), 0);
		adapter.setList(minerals, DataBaseHelper.MINERALS_TABLE);
	}
	
	private void insertFood(ListAdapter adapter, DataBaseHelper dbHelper){
		Vector<Details> food = dbHelper.getFood();
		//food.insertElementAt(getAddItem(), 0);
		adapter.setList(food, DataBaseHelper.FOOD_TABLE);
	}
	
	private Details getAddItem()
	{
		Food result = new Food();
		result.imageId = R.drawable.add_icon;
		result.name = getResources().getString(R.string.add_item);
		return result;
	}
}
