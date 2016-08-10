package com.alec.ync.frament;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alec.ync.adpter.Jshi_ThreeAdapter;
import com.alec.ync.model.Message;
import com.alec.yzc.R;

public class Jshi_threeFragment extends BaseFragment {
	private View view;
	private ListView one_listview;
	private Jshi_ThreeAdapter threeAdapter;
	private List<Message> list;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_jshi_three, null);
			initView(view);
		}
		return view;
	}
	private void initView(View view){
		 one_listview=(ListView)view.findViewById(R.id.one_listview);
		 list = new ArrayList<Message>();
			for (int i = 0; i < 10; i++) {
				Message ms = new Message();
				ms.setTitle("Ïç´å " + i);
				ms.setImag("http://www.baidu.com/img/bd_logo1.png");
				list.add(ms);
			}
			threeAdapter=new Jshi_ThreeAdapter(getActivity(),list);
		 one_listview.setAdapter(threeAdapter);
		 threeAdapter.notifyDataSetChanged();
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
