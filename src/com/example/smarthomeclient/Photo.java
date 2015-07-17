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
	/*private AudioManager audioMgr = null; // Audio�����������˿�������
	private int maxVolume = 50; // �������ֵ   
    private int curVolume = 20; // ��ǰ����ֵ   
*/   
	/**����ѡ���б�� **/
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
	        // ��ȡ�����������   
	     maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
	        // ��ʼ���������Ϊ���������1/2   
	     curVolume = maxVolume / 2;  */
	     
		builder = new AlertDialog.Builder(Photo.this);
		choosephoto = (Button)findViewById(R.id.choosephoto);
		preferences = getSharedPreferences("initview", Context.MODE_WORLD_READABLE);// ����ʵ��������
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
						mediaPlayer.start(); //�������߻ָ�����
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"�������������", Toast.LENGTH_SHORT).show();
					      }
						}); 
					break;
					case 1:
						Thread threadClientSocketOpen = new Thread(new ThreadClientOpen());//��Դ����ʱ�ͻ������Ӧsocketͨ�š�Ҫ�����߳��з������������߳��з��������ǲ��е�
		    			threadClientSocketOpen.start(); 
						mediaPlayer=MediaPlayer.create(Photo.this, R.raw.nikon_shot);
						//mediaPlayer=MediaPlayer.create(Photo.this, R.raw.onlympic);
						mediaPlayer.start(); //�������߻ָ�����
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"�῵���������", Toast.LENGTH_SHORT).show();
					      }
						});  
					break;
					case 2:
						mediaPlayer=MediaPlayer.create(Photo.this, R.raw.pentax_shot);
						mediaPlayer.start(); //�������߻ָ�����
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"pentax������", Toast.LENGTH_SHORT).show();
					      }
						});  
					break;
					case 3:
						mediaPlayer=MediaPlayer.create(Photo.this, R.raw.sony_shot);
						mediaPlayer.start(); //�������߻ָ�����
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"�������������", Toast.LENGTH_SHORT).show();
					      }
						});  
					break;
					case 4:
						mediaPlayer=MediaPlayer.create(Photo.this, R.raw.olymp_shot);
						mediaPlayer.start(); //�������߻ָ�����
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					        Toast.makeText(Photo.this,"olymp������", Toast.LENGTH_SHORT).show();
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
	//�������������ǹ���socketͨ�ŵ���������
		public void connectServerWithUDPSocketCanonOpen() {  
		    try {  
		        //����DatagramSocket����ָ��һ���˿ںţ�ע�⣬����ͻ�����Ҫ���շ������ķ�������,   
		        //����Ҫʹ������˿ں���receive������һ��Ҫ��ס   
		    	//System.out.println("��Ӧ��ť");
		    	int port = Integer.parseInt(Value.port);
		    	DatagramSocket socket = new DatagramSocket(port);    
		        //ʹ��InetAddress(Inet4Address).getByName��IP��ַת��Ϊ�����ַ     
		        InetAddress serverAddress = InetAddress.getByName(Value.ip);  
		        String str ="nikonphoto";//����Ҫ���͵ı���     
		        byte data[] = str.getBytes();//���ַ���str�ַ���ת��Ϊ�ֽ�����     
		        System.out.println("��������"+ str);
		        System.out.println("ip"+ Value.ip);
		        System.out.println("�˿�" + Value.port);
		        //����һ��DatagramPacket�������ڷ������ݡ�     
		        //����һ��Ҫ���͵�����  �����������ݵĳ���  ������������˵������ַ  �����ģ��������˶˿ں�    
		        DatagramPacket packet = new DatagramPacket(data, data.length ,serverAddress ,port);    
		        socket.send(packet);//�����ݷ��͵�����ˡ� 
		        socket.close();//Ҫ�����close()������ӵĻ�ֻ���յ�һ������
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
	    	   connectServerWithUDPSocketCanonOpen();//��һ��ȥ����
	           System.out.println("�Ѿ����е��߳����һ����");
	           Thread.sleep(50);	 
	    	}
	    	catch(Throwable t){
			}
	    }
	    }
	public void CreatDialog(){
		//int dialogcount = 0;
		//AlertDialog.Builder builder = new AlertDialog.Builder(Photo.this);//ֻ����һ��
		/*if(dialogcount==0){
			
		}*/
		 //mSingleChoiceID = -1;
	     builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("����Ʒ��ѡ��");
            //http://blog.csdn.net/flyfight88/article/details/8602162 
            M_arg.mSingleChoiceID = preferences.getInt("mSingleChoiceID", 0);
            builder.setSingleChoiceItems(mItems, M_arg.mSingleChoiceID, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                        M_arg.mSingleChoiceID = whichButton;
                        Editor editor = preferences.edit();
        				editor.putInt("mSingleChoiceID", M_arg.mSingleChoiceID);
        				editor.commit();
                        //showDialog("��ѡ���idΪ" + whichButton + " , " + mItems[whichButton]);
                }
            });
            builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(M_arg.mSingleChoiceID > 0) {
               	    //showDialog("��ѡ�����" + mSingleChoiceID);
                    }
                }
            });
            builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
          builder.create().show();//��ʾ
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
