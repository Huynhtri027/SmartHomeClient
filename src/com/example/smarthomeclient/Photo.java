package com.example.smarthomeclient;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.example.smarthomeclient.Air.ThreadClientOpen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
public class Photo extends Activity {
	private Button choosephoto;
	private MediaPlayer mediaPlayer;
	private ImageButton controlphoto;
	AlertDialog.Builder builder;
	private SharedPreferences preferences;
	//
	/*private AudioManager audioMgr = null; // Audio管理器，用了控制音量
	private int maxVolume = 50; // 最大音量值   
    private int curVolume = 20; // 当前音量值   
*/   
	/**单项选择列表框 **/
    final String[] mItems = {"Canon","Nikon","Pentax","Sony","Olympus"};
    //int mSingleChoiceID = -1;   
    ArrayList <Integer>MultiChoiceID = new ArrayList <Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		/*//
		 audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);  
	        // 获取最大音乐音量   
	     maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
	        // 初始化音量大概为最大音量的1/2   
	     curVolume = maxVolume / 2;  */
	     
		builder = new AlertDialog.Builder(Photo.this);
		choosephoto = (Button)findViewById(R.id.choosephoto);
		preferences = getSharedPreferences("initview", Context.MODE_WORLD_READABLE);// 创造实例化对象
		choosephoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CreatDialog();
			}
		});
		controlphoto = (ImageButton)findViewById(R.id.controlphoto);
		controlphoto.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(M_arg.mSingleChoiceID >= 0){
					switch (M_arg.mSingleChoiceID) {
					case 0:
						//audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, AudioManager.FLAG_PLAY_SOUND);  
						 
						mediaPlayer=MediaPlayer.create(Photo.this, R.raw.canon_shot);
						//mediaPlayer=MediaPlayer.create(Photo.this, R.raw.houlai);
						mediaPlayer.start(); //启动或者恢复播放
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"佳能相机已拍照", Toast.LENGTH_SHORT).show();
					      }
						}); 
					break;
					case 1:
						Thread threadClientSocketOpen = new Thread(new ThreadClientOpen());//电源开启时就会出现相应socket通信。要在子线程中访问网络在主线程中访问网络是不行的
		    			threadClientSocketOpen.start(); 
						mediaPlayer=MediaPlayer.create(Photo.this, R.raw.nikon_shot);
						//mediaPlayer=MediaPlayer.create(Photo.this, R.raw.onlympic);
						mediaPlayer.start(); //启动或者恢复播放
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"尼康相机已拍照", Toast.LENGTH_SHORT).show();
					      }
						});  
					break;
					case 2:
						mediaPlayer=MediaPlayer.create(Photo.this, R.raw.pentax_shot);
						mediaPlayer.start(); //启动或者恢复播放
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"pentax已拍照", Toast.LENGTH_SHORT).show();
					      }
						});  
					break;
					case 3:
						mediaPlayer=MediaPlayer.create(Photo.this, R.raw.sony_shot);
						mediaPlayer.start(); //启动或者恢复播放
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"索尼相机已拍照", Toast.LENGTH_SHORT).show();
					      }
						});  
					break;
					case 4:
						mediaPlayer=MediaPlayer.create(Photo.this, R.raw.olymp_shot);
						mediaPlayer.start(); //启动或者恢复播放
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"olymp已拍照", Toast.LENGTH_SHORT).show();
					      }
						});  
					break;
					default:
						break;
					}
				}
			}
		});
	}
	//以下两个函数是关于socket通信的两个函数
		public void connectServerWithUDPSocketCanonOpen() {  
		    try {  
		        //创建DatagramSocket对象并指定一个端口号，注意，如果客户端需要接收服务器的返回数据,   
		        //还需要使用这个端口号来receive，所以一定要记住   
		    	//System.out.println("相应按钮");
		    	int port = Integer.parseInt(Value.port);
		    	DatagramSocket socket = new DatagramSocket(port);    
		        //使用InetAddress(Inet4Address).getByName把IP地址转换为网络地址     
		        InetAddress serverAddress = InetAddress.getByName(Value.ip);  
		        String str ="nikonphoto";//设置要发送的报文     
		        byte data[] = str.getBytes();//把字符串str字符串转换为字节数组     
		        System.out.println("发送数据"+ str);
		        System.out.println("ip"+ Value.ip);
		        System.out.println("端口" + Value.port);
		        //创建一个DatagramPacket对象，用于发送数据。     
		        //参数一：要发送的数据  参数二：数据的长度  参数三：服务端的网络地址  参数四：服务器端端口号    
		        DatagramPacket packet = new DatagramPacket(data, data.length ,serverAddress ,port);    
		        socket.send(packet);//把数据发送到服务端。 
		        socket.close();//要加这个close()如果不加的话只能收到一次数据
		        //Log.e("msg", Value.ip);
		    } catch (SocketException e) {  
		        e.printStackTrace();  
		    } catch (UnknownHostException e) {  
		        e.printStackTrace();  
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    }    
		} 
		public class ThreadClientOpen implements Runnable{
	    	public void run(){
	    		try{
	    	   connectServerWithUDPSocketCanonOpen();//第一步去连接
	           System.out.println("已经运行到线程这个一步了");
	           Thread.sleep(50);	 
	    	}
	    	catch(Throwable t){
			}
	    }
	    }
	public void CreatDialog(){
		//int dialogcount = 0;
		//AlertDialog.Builder builder = new AlertDialog.Builder(Photo.this);//只创建一次
		/*if(dialogcount==0){
			
		}*/
		 //mSingleChoiceID = -1;
	     builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("单反品牌选择");
            //http://blog.csdn.net/flyfight88/article/details/8602162 
            M_arg.mSingleChoiceID = preferences.getInt("mSingleChoiceID", 0);
            builder.setSingleChoiceItems(mItems, M_arg.mSingleChoiceID, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                        M_arg.mSingleChoiceID = whichButton;
                        Editor editor = preferences.edit();
        				editor.putInt("mSingleChoiceID", M_arg.mSingleChoiceID);
        				editor.commit();
                        //showDialog("你选择的id为" + whichButton + " , " + mItems[whichButton]);
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(M_arg.mSingleChoiceID > 0) {
               	    //showDialog("你选择的是" + mSingleChoiceID);
                    }
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
          builder.create().show();//显示
	}
	private void showDialog(String str) {
			 new AlertDialog.Builder(Photo.this)
		         .setMessage(str)
		         .show();
		    }
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	Intent intent=new Intent();
        	intent.setClass(Photo.this,indexcontent.class);
        	startActivity(intent);
        	finish();
        }
		return true;
    }
}
