package com.alec.ync.adpter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alec.ync.model.Jishi;
import com.alec.ync.model.Message;
import com.alec.ync.util.ImageLoaders;
import com.alec.yzc.R;

public class Jshi_TwoAdapter extends BaseAdapter {
	private Context context;
	private List<Jishi> list;
	public Jshi_TwoAdapter(Context context,List<Jishi> list) {
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_jshi_two_item, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.one_item_title);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.one_item_image);
			viewHolder.price = (TextView) convertView.findViewById(R.id.one_item_price);
			viewHolder.purchase_price = (TextView) convertView.findViewById(R.id.one_item_purchase_price);
			viewHolder.sell = (TextView) convertView.findViewById(R.id.one_item_sell);
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title.setText(list.get(position).getGoods_name()!=null?list.get(position).getGoods_name():"Ã»ÓÐÃüÃû");
		viewHolder.price.setText(list.get(position).getPrice()!=null?list.get(position).getPrice():"0.0");
		viewHolder.purchase_price.setText(list.get(position).getPurchase_price()!=null?list.get(position).getPurchase_price():"0.0");
		viewHolder.sell.setText(list.get(position).getSell()!=null?list.get(position).getSell():"0");
		ImageLoaders.loadImage(list.get(position).getGoods_img(),viewHolder.img, R.drawable.default_image,R.drawable.error_image, null);
		return convertView;
	}

	public class ViewHolder {
		private TextView title;
		private ImageView img;
		private TextView price;
		private TextView purchase_price;
		private TextView sell;
	}
}
