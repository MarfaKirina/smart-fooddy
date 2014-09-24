package olga.com.healthy.food.ui.helpers;

import java.util.Vector;

import olga.com.healthy.food.R;
import olga.com.healthy.food.model.SimpleItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SimpleAdapter extends BaseAdapter{

	private Context context;
	private Vector<SimpleItem> items;
	
	public SimpleAdapter(Context context){
		this.context = context;
		items = new Vector<SimpleItem>();
	}
	
	public void add(SimpleItem item){
		if(item != null){
			items.add(item);
		}
	}
	
	public void remove(int index){
		if(items.size() > index){
			items.remove(index);
		}
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int index) {
		return items.get(index);
	}

	@Override
	public View getView(int index, View convertView, ViewGroup arg2) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
		}
		TextView text = (TextView)convertView.findViewById(R.id.text);
		text.setText(items.get(index).labelId);
		return convertView;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
}