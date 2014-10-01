package olga.com.healthy.food.ui.screens;

import java.util.Vector;

import olga.com.healthy.food.R;
import olga.com.healthy.food.db.DataBaseHelper;
import olga.com.healthy.food.model.Constituent;
import olga.com.healthy.food.model.Food;
import olga.com.healthy.food.model.SimpleItem;
import olga.com.healthy.food.ui.helpers.ListAdapter;
import olga.com.healthy.food.utils.Logger;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Details extends Activity{

	public static final String ID = "id";
	public static final String TABLE_NAME = "table_name";
	
	protected olga.com.healthy.food.model.Details details;
	protected Vector<olga.com.healthy.food.model.Details> column1info;
	protected Vector<olga.com.healthy.food.model.Details> column2info;
	protected Vector<SimpleItem> units;
	private DataBaseHelper dbHelper;
	
	protected ImageView image;
	protected TextView name;
	protected TextView description;
	protected ListView column1;
	protected ListAdapter column1Adapter;
	protected String column1tableName;
	protected ListView column2;
	protected ListAdapter column2Adapter;
	protected String column2tableName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		Intent intent = getIntent();
		int id = intent.getIntExtra(ID, 0);
		if(id <= 0){
			return;
		}
		String tableName = intent.getStringExtra(TABLE_NAME);
		if(tableName == null || tableName.trim().length() == 0){
			return;
		}
		
		dbHelper = new DataBaseHelper(this);
		
		try{
			dbHelper.openDataBase();
		}catch(Exception e){
			Logger.log(e);
		}
		
		details = dbHelper.getDetails(id, tableName);
		
		initColumnsInfo();
		
		image = (ImageView)findViewById(R.id.image);
		try{
			image.setImageBitmap(BitmapFactory.decodeResource(getResources(), details.imageId));
		}catch(Exception e){
			//do nothing
		}
		name = (TextView)findViewById(R.id.name);
		name.setText(details.name);
		
		description = (TextView)findViewById(R.id.description);
		description.setText(details.description);
		
		column1 = (ListView)findViewById(R.id.column1);
		column2 = (ListView)findViewById(R.id.column2);
			
		if(column1info != null && column1info.size() > 0){
			column1Adapter = new ListAdapter(this);
			column1.setAdapter(column1Adapter);
			insertList(column1Adapter, column1info, column1tableName);
			column1.setVisibility(View.VISIBLE);
		}else{
			column1.setVisibility(View.GONE);
		}
		
		if(column2info != null && column2info.size() > 0){
			column2Adapter = new ListAdapter(this);
			column2.setAdapter(column2Adapter);
			insertList(column2Adapter, column2info, column2tableName);
			column2.setVisibility(View.VISIBLE);
		}else{
			column2.setVisibility(View.GONE);
		}
		
	}
	
	private void initColumnsInfo(){
		
		Vector<SimpleItem> units = dbHelper.getUnits();
		
		if(details instanceof Food){
			Vector<olga.com.healthy.food.model.Details> vitamines = dbHelper.getVitamines();
			Vector<olga.com.healthy.food.model.Details> minerals = dbHelper.getMinerals();
			column1tableName = DataBaseHelper.VITAMINES_TABLE;
			column1info = DataBaseHelper.mergeInfo(vitamines, ((Food) details).vitamines, units);
			column2tableName = DataBaseHelper.MINERALS_TABLE;
			column2info = DataBaseHelper.mergeInfo(minerals, ((Food) details).minerals, units);
		}else if(details instanceof Constituent){
			Vector<olga.com.healthy.food.model.Details> food = dbHelper.getFood();
			column1tableName = DataBaseHelper.FOOD_TABLE;
			column1info = DataBaseHelper.mergeInfo(food, ((Constituent) details).food, units);
			column2tableName = null;
			column2info = null;
		}else{
			column1tableName = null;
			column1info = null;
			column2tableName = null;
			column2info = null;
		}
	}
	
	private void insertList(ListAdapter adapter, Vector<olga.com.healthy.food.model.Details> list, String tableName){
		if(adapter == null || list == null){
			return;
		}
		adapter.setList(list, tableName);
	}
}
