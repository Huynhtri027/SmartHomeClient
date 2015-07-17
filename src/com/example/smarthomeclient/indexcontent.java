package com.example.smarthomeclient;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class indexcontent extends Activity{
	LocationManager locManager;
	private ImageButton tv;
	private ImageButton air;
	private ImageButton fan;
	private ImageButton projector;
	private ImageButton photo;
	private ImageButton exit;
    private Intent mIntent;
    public  String ip;
    public  String port = "";  
    public  String password = "";
    public  String username = "";   
	Location location;
	private static int count;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ������
		setContentView(R.layout.activity_menu);
		//�����������ݴ�����
		/*mIntent = getIntent();
		Bundle b = mIntent.getExtras();
		ip = b.getString("ip");//��������
	    this.port= b.getString("port");
		this.username = b.getString("username");
	    this.password= b.getString("password");*/
		tv = (ImageButton)findViewById(R.id.tv);
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
		        	intent.setClass(indexcontent.this, TV.class);
		        	startActivity(intent);
		        	finish();
				
			}
		});
		air = (ImageButton)findViewById(R.id.air);
		air.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent intent = new Intent();
		        	intent.setClass(indexcontent.this, Air.class);
		        	startActivity(intent);
		        	finish();			
			}
		});
		photo = (ImageButton)findViewById(R.id.photo);
		photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(indexcontent.this, Photo.class);
				startActivity(intent);
				finish();
			}
		});
		fan = (ImageButton)findViewById(R.id.fan);
		projector=(ImageButton)findViewById(R.id.projector);
		exit = (ImageButton)findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent intent=new Intent();
	        	intent.setClass(indexcontent.this,SmartHome.class);
	        	startActivity(intent);*/
	        	finish();
			}
		});
	}
	//������γ����ܼӣ�һ������֮��ͻ�������ѡ���Զ���¼�������ؼ��󣬲��ܻص���¼���ڣ�����û�н��������⡣
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	/*Intent intent=new Intent();
        	intent.setClass(indexcontent.this,SmartHome.class);
        	startActivity(intent);*/
        	finish();
        }
		return true;
    }
}

		