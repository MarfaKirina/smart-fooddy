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

	public static final String DETAILS = "details";
	
	protected olga.com.healthy.food.model.Details details;
	protected Vector<olga.com.healthy.food.model.Details> column1info;
	protected Vector<olga.com.healthy.food.model.Details> column2info;
	protected Vector<SimpleItem> units;
	
	protected ImageView image;
	protected TextView name;
	protected TextView description;
	protected ListView column1;
	protected ListAdapter columnAdapter1;
	protected ListView column2;
	protected ListAdapter column2Adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		Intent intent = getIntent();
		details = (olga.com.healthy.food.model.Details)intent.getSerializableExtra(DETAILS);
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
			columnAdapter1 = new ListAdapter(this);
			column1.setAdapter(columnAdapter1);
			insertList(columnAdapter1, column1info);
			column1.setVisibility(View.VISIBLE);
		}else{
			column1.setVisibility(View.GONE);
		}
		
		if(column2info != null && column2info.size() > 0){
			column2Adapter = new ListAdapter(this);
			column2.setAdapter(column2Adapter);
			insertList(column2Adapter, column2info);
			column2.setVisibility(View.VISIBLE);
		}else{
			column2.setVisibility(View.GONE);
		}
		
	}
	
	private void initColumnsInfo(){
		DataBaseHelper dbHelper = new DataBaseHelper(this);
		
		try{
			dbHelper.openDataBase();
		}catch(Exception e){
			Logger.log(e);
		}
		
		Vector<SimpleItem> units = dbHelper.getUnits();
		
		if(details instanceof Food){
			Vector<olga.com.healthy.food.model.Details> vitamines = dbHelper.getVitamines();
			Vector<olga.com.healthy.food.model.Details> minerals = dbHelper.getMinerals();
			column1info = DataBaseHelper.mergeInfo(vitamines, ((Food) details).vitamines, units);
			column2info = DataBaseHelper.mergeInfo(minerals, ((Food) details).minerals, units);
		}else if(details instanceof Constituent){
			Vector<olga.com.healthy.food.model.Details> food = dbHelper.getFood();
			column1info = DataBaseHelper.mergeInfo(food, ((Constituent) details).food, units);
			column2info = null;
		}else{
			column1info = null;
			column2info = null;
		}
	}
	
	private void insertList(ListAdapter adapter, Vector<olga.com.healthy.food.model.Details> list){
		if(adapter == null || list == null){
			return;
		}
		adapter.setList(list);
	}
}
