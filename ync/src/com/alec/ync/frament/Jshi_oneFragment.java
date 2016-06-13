package com.alec.ync.frament;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alec.ync.adpter.Jshi_OneAdapter;
import com.alec.ync.model.Message;
import com.alec.yzc.R;

public class Jshi_oneFragment extends BaseFragment {
	private View view;
	private ListView one_listview;
	private Jshi_OneAdapter oneAdapter;
	private List<Message> list;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_jshi_one, null);
			initView(view);
		}
		return view;
	}
	 private void initView(View view){
		 one_listview=(ListView)view.findViewById(R.id.one_listview);
		 list = new ArrayList<Message>();
			for (int i = 0; i < 10; i++) {
				Message ms = new Message();
				ms.setTitle("��� " + i);
				ms.setImag("http://www.baidu.com/img/bd_logo1.png");
				list.add(ms);
			}
		 oneAdapter=new Jshi_OneAdapter(getActivity(),list);
		 one_listview.setAdapter(oneAdapter);
		 oneAdapter.notifyDataSetChanged();
	 }
	@Override
	public void onDestroyView() {
		((ViewGroup)view.getParent()).removeView(view);
		super.onDestroyView();
	}
}