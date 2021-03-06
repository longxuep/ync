package com.alec.ync.wode.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alec.ync.activity.DingweiActivity;
import com.alec.ync.base.BaseActivity;
import com.alec.ync.util.Constant;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.yzc.R;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private TextView _back,_findpwd,_register;
	protected Handler handler = new Handler();
	private HttpJsonObjectRequest request_info;
	private EditText username,pwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wode_login_activity);
		initView();
	}
	private void initView() {
		_back = (TextView)findViewById(R.id._back);
		_findpwd = (TextView) findViewById(R.id._findpwd);
		_register = (TextView) findViewById(R.id._register);
		_back.setOnClickListener(this);
		_findpwd.setOnClickListener(this);
		_register.setOnClickListener(this);
		findViewById(R.id.bt_log).setOnClickListener(this);
		 username=(EditText)findViewById(R.id.username);
		 pwd=(EditText)findViewById(R.id.pwd);
	}
	//验证登录信息
	private void ckuser(){
		if(username.getText()==null||username.getText().length()<1){
			showToastMsgShort("请输入手机号");
			return;
		}
		if(username.getText().length()>11||username.getText().length()<11){
			showToastMsgShort("手机号格式错误");
			return;
		}
		if(pwd.getText()==null||pwd.getText().length()<1){
			showToastMsgShort("请输入密码");
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
						showToastMsgShort("登录成功");
						finish();
					}else{
						showToastMsgShort(response.get("msg").toString());
					}
				}
			} catch (Exception e) {
				showToastMsgShort("数据解析错误");
			}
		}
	};

	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			showToastMsgShort("服务器链接错误");
		}
	};
		
	@Override
	public void onClick(View v) {
		if(v!=null){
			Intent intent = new Intent();
			switch (v.getId()) {
				case R.id._back:
					finish();
					break;
				case R.id._findpwd:
					intent.setClass(this, FindPwdActivity.class);
					startActivity(intent);
					break;
				case R.id._register:
					intent.setClass(this, RegisterActivity.class);
					startActivity(intent);
					break;
				case R.id.bt_log://登录
					ckuser();
					break;
				default:
					break;
			}
		}
	}
	@Override
	protected Context getContext() {
		return this;
	}			
	
}
