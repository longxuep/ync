package com.alec.ync;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import com.alec.ync.volley.HttpBase;
import com.alec.yzc.R;

public class WodeRegisterActivity extends Activity implements OnClickListener{
	private TextView _back;
	protected Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_woderegister);
		_back=(TextView)findViewById(R.id.xiangcundingwei_back);
		_back.setOnClickListener(this);
		findViewById(R.id.bt_reg).setOnClickListener(this);
	}
	private String getList(){
		String result = null;
		Map<String, String> params=new HashMap<String, String>();
		params.put("phone", "13647541833");
		params.put("passwd", "1833");
		String url="http://yzc.lzbxj.com/api/apiRegist.php?phone=13647541833&passwd=1833";
		HttpBase httpBase = new HttpBase(url);
		httpBase.setCharset("utf-8");
		httpBase.get();
		int errCount = 0;
    	while(httpBase.getResponseCode()==-1 && errCount<3){
    		httpBase.get();
    		errCount ++;
    	}    	
    	if(httpBase.getResponseCode()==200){
    		result = httpBase.getResponseText();
    	}
    	return result;
	}
	/**
	 * µÇÂ¼
	 * */
   private void getData(){
      new Thread(){
			@Override
			public void run() {
				final String result = getList();
				System.out.println(""+result);
		    	if(result != null && result.startsWith("{") && result.endsWith("}")){
			    	handler.post(new Runnable() {
						@Override
						public void run() {
							System.out.println("json="+result);
							}
						
					});
		       }else{
		    	   System.out.println("´íÎójson");  
		       }
			}
	   		
	   	}.start();
	}
	@Override
	public void onClick(View v) {
		if(v!=null){
			switch (v.getId()) {
				case R.id.xiangcundingwei_back:
					finish();
					break;
				case R.id.bt_reg://µÇÂ¼
					getData();
					break;
				default:
					break;
			}
		}
	}		
	
}
