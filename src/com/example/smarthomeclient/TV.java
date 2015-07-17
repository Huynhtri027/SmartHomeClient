package com.example.smarthomeclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class TV extends Activity{

	private ImageButton dianyuan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tv);
		dianyuan=(ImageButton)findViewById(R.id.dianyuan);
		dianyuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread threadClientSocket = new Thread(new ThreadClient());//��ҪҪ�����߳��з������������߳��з��������ǲ��е�
    			threadClientSocket.start();   
			}
		});
		
	//	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	public void connectServerWithUDPSocket() {  
	    try {  
	        //����DatagramSocket����ָ��һ���˿ںţ�ע�⣬����ͻ�����Ҫ���շ������ķ�������,   
	        //����Ҫʹ������˿ں���receive������һ��Ҫ��ס   
	    	//System.out.println("��Ӧ��ť");
	        DatagramSocket socket = new DatagramSocket(9401);  
	        //ʹ��InetAddress(Inet4Address).getByName��IP��ַת��Ϊ�����ַ     
	        InetAddress serverAddress = InetAddress.getByName("192.168.1.102");  
	        String str = "123";//����Ҫ���͵ı���    
	        byte data[] = str.getBytes();//���ַ���str�ַ���ת��Ϊ�ֽ�����     
	        System.out.println("��������"+ data);
	        //����һ��DatagramPacket�������ڷ������ݡ�     
	        //����һ��Ҫ���͵�����  �����������ݵĳ���  ������������˵������ַ  �����ģ��������˶˿ں�    
	        DatagramPacket packet = new DatagramPacket(data, data.length ,serverAddress ,9401);    
	        socket.send(packet);//�����ݷ��͵�����ˡ� 
	        socket.close();//Ҫ�����close()������ӵĻ�ֻ���յ�һ������
	    } catch (SocketException e) {  
	        e.printStackTrace();  
	    } catch (UnknownHostException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }    
	}
	public class ThreadClient implements Runnable{
    	public void run(){
    		try{
    	   connectServerWithUDPSocket();//��һ��ȥ����
           System.out.println("�Ѿ����е��߳����һ����");
           Thread.sleep(50);	 
    	}
    	catch(Throwable t){
		}
    }
    }
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	Intent intent=new Intent();
        	intent.setClass(TV.this,indexcontent.class);
        	startActivity(intent);
        	finish();
        }
		return true;
    }
	
}