package com.alec.ync.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alec.ync.model.Message;
import com.alec.yzc.R;

public class Jshi_OneAdapter extends BaseAdapter {
	private Context context;
	private List<Message> list;
	public Jshi_OneAdapter(Context context,List<Message> list) {
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_jshi_one_item, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.one_item_title);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.one_item_image);
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title.setText(list.get(position).getTitle());
		return convertView;
	}
	public class ViewHolder {
		private TextView title;
		private ImageView img;
	}
}
