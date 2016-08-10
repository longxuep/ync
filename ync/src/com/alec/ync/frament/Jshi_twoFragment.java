package com.alec.ync.frament;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.alec.ync.adpter.Jshi_TwoAdapter;
import com.alec.ync.model.Message;
import com.alec.yzc.R;

public class Jshi_twoFragment extends BaseFragment {
	private View view;
	private GridView two_item_gview;
	private Jshi_TwoAdapter twoAdapter;
	private List<Message> list;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_jshi_two, null);
			initView(view);
		}
		return view;
	}
	private void initView(View view){
		two_item_gview=(GridView)view.findViewById(R.id.two_item_gview);
		list = new ArrayList<Message>();
		for (int i = 0; i < 11; i++) {
			Message ms = new Message();
			ms.setTitle("Ïç´å " + i);
			ms.setImag("http://www.baidu.com/img/bd_logo1.png");
			list.add(ms);
		}
//		twoAdapter=new Jshi_TwoAdapter(getActivity(),list);
//		two_item_gview.setAdapter(twoAdapter);
//		twoAdapter.notifyDataSetChanged();
	}
	@Override
	public void onDestroyView() {
		((ViewGroup)view.getParent()).removeView(view);
		super.onDestroyView();
	}
	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return getActivity();
	}
}
