package com.alec.ync;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alec.ync.activity.BaseActivity;
import com.alec.ync.util.Constant;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.yzc.R;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class WodeLoginActivity extends BaseActivity implements OnClickListener{
	private TextView _back;
	protected Handler handler = new Handler();
	private HttpJsonObjectRequest request_info;
	private EditText username,pwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wodelogin);
		initView();
	}
	private void initView() {
		_back=(TextView)findViewById(R.id.xiangcundingwei_back);
		_back.setOnClickListener(this);
		findViewById(R.id.bt_log).setOnClickListener(this);
		 username=(EditText)findViewById(R.id.username);
		 pwd=(EditText)findViewById(R.id.pwd);
	}
	//��֤��¼��Ϣ
	private void ckuser(){
		if(username.getText()==null||username.getText().length()<1){
			showToastMsgShort("�������ֻ���");
			return;
		}
		if(username.getText().length()>11||username.getText().length()<11){
			showToastMsgShort("�ֻ��Ÿ�ʽ����");
			return;
		}
		if(pwd.getText()==null||pwd.getText().length()<1){
			showToastMsgShort("����������");
			return;
		}
		getData(username.getText().toString(),pwd.getText().toString());
	}
	private void getData(String username,String pwd) {
		Map<String, String> params=new HashMap<String, String>();
		params.put("phone", username);
		params.put("passwd", pwd);
		//13647541833 1833
		request_info = new HttpJsonObjectRequest(Method.GET, Constant.Url.Login, null, successListener, errorListener,
				params, this);
		mRequestQueue.add(request_info);
	}   
	
	Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			try {
				if(response!=null){
					if(response.get("code").equals("success")){
						showToastMsgShort("��¼�ɹ�");
						finish();
					}else{
						showToastMsgShort(response.get("msg").toString());
					}
				}
			} catch (Exception e) {
				showToastMsgShort("���ݽ�������");
			}
		}
	};

	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			showToastMsgShort("���������Ӵ���");
		}
	};
		
	@Override
	public void onClick(View v) {
		if(v!=null){
			switch (v.getId()) {
				case R.id.xiangcundingwei_back:
					finish();
					break;
				case R.id.bt_log://��¼
					ckuser();
					break;
				default:
					break;
			}
		}
	}			
	
}
