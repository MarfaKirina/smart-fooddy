package olga.com.healthy.food.ui.screens;

import java.util.Vector;

import olga.com.healthy.food.R;
import olga.com.healthy.food.ui.helpers.ListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Details extends Activity{

	public static final String DETAILS = "details";
	
	protected olga.com.healthy.food.model.Details details;
	
	protected ImageView image;
	protected TextView name;
	protected TextView description;
	protected TextView columnName1;
	protected ListView column1;
	protected ListAdapter columnAdapter1;
	protected TextView columnName2;
	protected ListView column2;
	protected ListAdapter columnAdapter2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		Intent intent = getIntent();
		details = (olga.com.healthy.food.model.Details)intent.getSerializableExtra(DETAILS);
		
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
		
		columnName1 = (TextView)findViewById(R.id.columnName1);
		column1 = (ListView)findViewById(R.id.column1);
		columnAdapter1 = new ListAdapter(this);
		column1.setAdapter(columnAdapter1);
		insertList(columnAdapter1, details.list1);
		
		columnName2 = (TextView)findViewById(R.id.columnName2);
		column2 = (ListView)findViewById(R.id.column2);
		columnAdapter2 = new ListAdapter(this);
		column2.setAdapter(columnAdapter2);
		insertList(columnAdapter2, details.list2);
		
		if(details.hasProducts){
			columnName1.setVisibility(View.GONE);
			columnName2.setVisibility(View.GONE);
		}
	}
	
	private void insertList(ListAdapter adapter, Vector<olga.com.healthy.food.model.Details> list){
		if(adapter == null || list == null){
			return;
		}
		adapter.setList(list);
	}
}
