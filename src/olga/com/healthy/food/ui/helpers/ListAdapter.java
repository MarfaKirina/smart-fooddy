package olga.com.healthy.food.ui.helpers;

import java.util.Vector;

import olga.com.healthy.food.R;
import olga.com.healthy.food.model.Details;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ListAdapter extends BaseAdapter{
	
	private Context context;
	private Vector<Details> list;
	private String tableName;
	
	public ListAdapter(Context context){
		super();
		this.context = context;
		list = new Vector<Details>();
	}
	
	public void addItem(Details item){
		list.add(item);
	}
	
	public void setList(Vector<Details> list, String tableName){
		this.list = list;
		this.tableName = tableName;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int index) {
		return list.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View contentView, ViewGroup arg2) {
		final Details item = (Details)getItem(index);
		if(contentView == null){
			contentView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
		}
		try{
			ImageView image = (ImageView)contentView.findViewById(R.id.image);
			image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), item.imageId));
		}catch(Exception e){
			//do nothing
		}
		TextView name = (TextView)contentView.findViewById(R.id.name);
		name.setText(item.name);
		TextView description = (TextView)contentView.findViewById(R.id.description);
		description.setText(item.description);

		contentView.setClickable(true);
		contentView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, olga.com.healthy.food.ui.screens.Details.class);
				intent.putExtra(olga.com.healthy.food.ui.screens.Details.ID, item.id);
				intent.putExtra(olga.com.healthy.food.ui.screens.Details.TABLE_NAME, tableName);
				context.startActivity(intent);
			}
		});
		return contentView;
	}
	
}
