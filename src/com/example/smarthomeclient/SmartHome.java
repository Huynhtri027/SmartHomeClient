package com.example.smarthomeclient;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
public class SmartHome extends Activity {

	private EditText username, password, ip, port;
	private CheckBox rem_pw, auto_login;
	private Button btn_login;
	private Button btn_exit;
	private ImageButton btnQuit;
	private String usernameValue, passwordValue, ipValue, portValue;
	private SharedPreferences sp;

	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		// 去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_home);

		// 获得实例对象
		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);// 创造实例化对象
		username = (EditText) findViewById(R.id.et_zh);// 用户名
		password = (EditText) findViewById(R.id.et_mima);// 密码
		ip = (EditText) findViewById(R.id.ipeditor_zh);// IP地址
		port = (EditText) findViewById(R.id.porteditor_zh);// 端口号
		rem_pw = (CheckBox) findViewById(R.id.cb_mima);// 记住密码
		auto_login = (CheckBox) findViewById(R.id.cb_auto);// 自动登录
		btn_login = (Button) findViewById(R.id.btn_login);// 登录
		// btnQuit = (ImageButton)findViewById(R.id.img_btn);//退出
		btn_exit = (Button) findViewById(R.id.btn_exit);// 退出 文字

		// 判断记住密码多选框状态
		if (sp.getBoolean("ISCHECK", false)) {
			// 设置默认是记录密码状
			rem_pw.setChecked(true);
			ip.setText(sp.getString("ip", ""));// 可能会有问题
			port.setText(sp.getString("port", ""));//
			username.setText(sp.getString("USER_NAME", ""));
			password.setText(sp.getString("password", ""));
			ipValue = ip.getText().toString();
			portValue = port.getText().toString();
			usernameValue = username.getText().toString();
			passwordValue = password.getText().toString();
			// 判断自动登录多选框状态״̬
			if (sp.getBoolean("AUTO_ISCHECK", false)) {//获得一个布尔值
				// 设置默认是自动登录状态״̬
				auto_login.setChecked(true);
				// 跳转界面跳转到logoactivity
				Intent intent = new Intent(SmartHome.this,
						indexcontent.class);
				Bundle b = new Bundle();//这个bundle对象是用来acitvity之间传递数据的
				// 调用Bundle对象的putString方法，采用Key-value的形式存放数据
				b.putString("ip", ip.getText().toString());
				b.putString("port", port.getText().toString());
				b.putString("username", username.getText().toString());
				b.putString("password", password.getText().toString());
				//当选择自动登录的时候进行赋值。这样在后面的activity中就不设计到数据传递而是直接读取内存中的Value.ip即可
				Value.ip = ipValue;
				Value.port=portValue;
				Value.username=usernameValue;
				Value.password=passwordValue;
				
				// 将数据载体Bundle对象放入Intent对象中。
				intent.putExtras(b);
				SmartHome.this.startActivity(intent);
			}
		}
		// 记录监听事件，现在默认用户名是admim 密码空
		btn_login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ipValue = ip.getText().toString();
				portValue = port.getText().toString();
				usernameValue = username.getText().toString();
				passwordValue = password.getText().toString();
				Value.ip = ipValue;//把ip.port等值放到内存里。
				Value.port=portValue;
				Value.username=usernameValue;
				Value.password=passwordValue;

				if (usernameValue.equals("admin") && passwordValue.equals("")) {
					// 登录成功和记住密码框为选中状态才保存用户信息
					if (rem_pw.isChecked()) {
						// 记住用户名和密码，采用获得edit()接口然后采用键值方式保存数据
						Editor editor = sp.edit();
						editor.putString("ip", ipValue);
						editor.putString("port", portValue);
						editor.putString("USER_NAME", usernameValue);
						editor.putString("password", passwordValue);
						editor.commit();
						// 检测当前网络是否连接。如果没有连接则弹出对话框要求连接
						if (isNetworkAvailable()) {
							Toast.makeText(SmartHome.this, "登录成功",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(SmartHome.this,
									indexcontent.class);
							// 创建Buddle对象用来存放数据，Buddle对象可以理解为数据的载体
							Bundle b = new Bundle();
							// 调用Bundle对象的putString方法，采用Key-value的形式存放数据
							b.putString("ip", ip.getText().toString());
							b.putString("port", port.getText().toString());
							b.putString("username", username.getText()
									.toString());
							b.putString("password", password.getText()
									.toString());
							// 将数据载体Bundle对象放入Intent对象中。
							intent.putExtras(b);
							SmartHome.this.startActivity(intent);
						} else {
							setNetwork();
						}
					}
				} else {

					Toast.makeText(SmartHome.this, "用户名或密码错误",
							Toast.LENGTH_LONG).show();
				}

			}

		});

		// 监听记住密码多选框状态
		rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (rem_pw.isChecked()) {

					System.out.println("记住密码已选中");
					sp.edit().putBoolean("ISCHECK", true).commit();

				} else {

					System.out.println("记住密码未选中");
					sp.edit().putBoolean("ISCHECK", false).commit();

				}

			}
		});

		// 监听自动登录多选框事件
		auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (auto_login.isChecked()) {
					System.out.println("自动登录已选中");
					sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

				} else {
					System.out.println("自动登录没有选中");
					sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
				}
			}
		});

		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.exit(0);
				ActivityManager activityMgr = (ActivityManager) SmartHome.this
						.getSystemService(ACTIVITY_SERVICE);
				activityMgr.restartPackage(getPackageName());
			}
		});

	}

	// 当调用这个函数后会出现对话框然后点击设置就会进入相应的无限网络设置界面
	// 由于Android的SDK版本不同所以里面的API和设置方式也是有少量变化的,尤其是在Android 3.0
	// 及后面的版本,UI和显示方式也发生了变化.
	public void setNetwork() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(R.string.netstate);
		builder.setMessage(R.string.setnetwork);
		builder.setPositiveButton(R.string.set,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = null;
						// 首先判断android版本，在高于android 3.0版本时
						if (android.os.Build.VERSION.SDK_INT > 10) {
							intent = new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						} else {
							intent = new Intent();
							ComponentName component = new ComponentName(
									"com.android.settings",
									"com.android.settings.WirelessSettings");
							intent.setComponent(component);
							intent.setAction("android.intent.action.VIEW");
						}
						startActivity(intent);
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		builder.create();
		builder.show();
	}

	// NETWORK
	public boolean isNetworkAvailable() {
		Context context = getApplicationContext();
		ConnectivityManager connect = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connect == null) {
			return false;
		} else// get all network info
		{
			NetworkInfo[] info = connect.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// onkeydown这个函数是检测是不是返回键按下，一旦按下就会有对话框问提示信息问是否退出程序
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("亲！您确定要退出程序吗？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							System.exit(0);
						}
					});
			builder.setNegativeButton("取消", null);
			builder.show();
		}
		return true;
	}
}