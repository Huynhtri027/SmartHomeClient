package com.example.smarthomeclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Air extends Activity {
	//private RelativeLayout layout1;  
    private ImageView wind;  
    private ImageView air; 
    private ImageView number;
    private ImageView fangzi;
    private ImageView qihou;
    private ImageView diandeng;
    
    private TextView dushu;
    private ImageButton dianyuan;
    private ImageButton up;
    private ImageButton down;
    private ImageButton moshi;
    private ImageButton fengliang;
    private static int count_moshi; 
	private SharedPreferences preferences;
  
    private int i = 0;  
    private int j=0;
    Timer timer = new Timer();  
    //ͨ��handler������ʵ�������߳��и���UI��ͨ��handleMessage��ʵ��
    private Handler handler = new Handler()  
    {  
        @Override  
        public void handleMessage(Message msg)  
        {  
        	//handle�д���wind��air��ʵʱ�ı䣬���߻��һ����12���������case�������£���ʱ����ʱʱ����Time�ж���
            //Log.e("@@@", i + "");  
            //index=msg.what;  
            if (i > 12)  
            {  
                i = 0;  
            }  
            else  
            {  
                switch (i)  
                {  
                case 1:  
                    wind.setImageResource(R.drawable.wind_amount_black_one);  
                    air.setImageResource(R.drawable.img_air_direction_up); 
                    break;  
                case 2:  
                    wind.setImageResource(R.drawable.wind_amount_black_one);  
                    air.setImageResource(R.drawable.img_air_direction_up); 
                    break; 
                case 3:  
                    wind.setImageResource(R.drawable.wind_amount_black_one);  
                    air.setImageResource(R.drawable.img_air_direction_up); 
                    break; 
                case 4:  
                    wind.setImageResource(R.drawable.wind_amount_black_two);  
                    air.setImageResource(R.drawable.img_air_direction_up); 
                    break; 
                case 5:  
                    wind.setImageResource(R.drawable.wind_amount_black_two);  
                    air.setImageResource(R.drawable.img_air_direction_mid); 
                    break; 
                case 6:  
                    wind.setImageResource(R.drawable.wind_amount_black_two);  
                    air.setImageResource(R.drawable.img_air_direction_mid); 
                    break; 
                case 7:  
                    wind.setImageResource(R.drawable.wind_amount_black_three);  
                    air.setImageResource(R.drawable.img_air_direction_mid); 
                    break; 
                case 8:  
                    wind.setImageResource(R.drawable.wind_amount_black_three);  
                    air.setImageResource(R.drawable.img_air_direction_mid); 
                    break; 
                case 9:  
                    wind.setImageResource(R.drawable.wind_amount_black_three);  
                    air.setImageResource(R.drawable.img_air_direction_down); 
                    break; 
                case 10:  
                    wind.setImageResource(R.drawable.wind_amount_black_four);  
                    air.setImageResource(R.drawable.img_air_direction_down); 
                    break; 
                case 11:  
                    wind.setImageResource(R.drawable.wind_amount_black_four);  
                    air.setImageResource(R.drawable.img_air_direction_down); 
                    break;  
                case 12:  
                    wind.setImageResource(R.drawable.wind_amount_black_four);  
                    air.setImageResource(R.drawable.img_air_direction_down); 
                    break;  
               
                default:  
                    break;  
                }  
            }           
          super.handleMessage(msg);  
        }  
    };  
  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
    	//activity��������initview������ʼ������ͼƬ
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.air); 
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
    } 
   /* class fengClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Thread threadClientSocketOpen = new Thread(new ThreadClientOpen());//��Դ����ʱ�ͻ������Ӧsocketͨ�š�Ҫ�����߳��з������������߳��з��������ǲ��е�
			threadClientSocketOpen.start(); 
		}		
	}*/
    class dianyuanClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//���һ�°�ť��ɫ��������ͼƬ��ʾ���ڵ��һ������ͼƬӰ��
			if(j==0)
			//��ʾ��Դ����������ʱj==1��
			{
				wind.setVisibility(View.VISIBLE);//�����óɿɼ�
				air.setVisibility(View.VISIBLE);
				number.setVisibility(View.VISIBLE);
				fangzi.setVisibility(View.VISIBLE);
				qihou.setVisibility(View.VISIBLE);
				diandeng.setVisibility(View.VISIBLE);
				dushu.setVisibility(View.VISIBLE);
				j++;
				
				Thread threadClientSocketOpen = new Thread(new ThreadClientOpen());//��Դ����ʱ�ͻ������Ӧsocketͨ�š�Ҫ�����߳��з������������߳��з��������ǲ��е�
    			threadClientSocketOpen.start();  
			}
			else if(j==1)
			{
				//��ʾ��Դ�رգ�����ʱj==0��
				wind.setVisibility(View.INVISIBLE);
				air.setVisibility(View.INVISIBLE);
				number.setVisibility(View.INVISIBLE);
				fangzi.setVisibility(View.INVISIBLE);
				qihou.setVisibility(View.INVISIBLE);
				diandeng.setVisibility(View.INVISIBLE);
				dushu.setVisibility(View.INVISIBLE);
				j=0;
				Log.e("msg","��Դ�ر�");
				Thread threadClientSocketClose = new Thread(new ThreadClientClose());
				threadClientSocketClose.start();
			}  
		}		
	}
    //�������������ǹ���socketͨ�ŵ���������
	public void connectServerWithUDPSocketOpen() {  
	    try {  
	        //����DatagramSocket����ָ��һ���˿ںţ�ע�⣬����ͻ�����Ҫ���շ������ķ�������,   
	        //����Ҫʹ������˿ں���receive������һ��Ҫ��ס   
	    	//System.out.println("��Ӧ��ť");
	    	int port = Integer.parseInt(Value.port);
	    	DatagramSocket socket = new DatagramSocket(port);    
	        //ʹ��InetAddress(Inet4Address).getByName��IP��ַת��Ϊ�����ַ     
	        InetAddress serverAddress = InetAddress.getByName(Value.ip);  
	        String str ="air open";//����Ҫ���͵ı���     
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
    	   connectServerWithUDPSocketOpen();//��һ��ȥ����
           System.out.println("�Ѿ����е��߳����һ����");
           Thread.sleep(50);	 
    	}
    	catch(Throwable t){
		}
    }
    }
	public class ThreadClientClose implements Runnable{
		public void run(){
			try{
				connectServerWithUDPSocketClose();
				Thread.sleep(50);
				 Log.e("msg","����closerun()");
			}
			catch(Throwable t){
				
			}
		}	
	}
	public void connectServerWithUDPSocketClose() {  
	    try {  
	        //����DatagramSocket����ָ��һ���˿ںţ�ע�⣬����ͻ�����Ҫ���շ������ķ�������,   
	        //����Ҫʹ������˿ں���receive������һ��Ҫ��ס   
	    	//System.out.println("��Ӧ��ť");
	    	int port = Integer.parseInt(Value.port);
	        DatagramSocket socket = new DatagramSocket(port);    
	        //ʹ��InetAddress(Inet4Address).getByName��IP��ַת��Ϊ�����ַ     
	        InetAddress serverAddress = InetAddress.getByName(Value.ip);  
	        String str ="air close";//����Ҫ���͵ı���     
	        byte data[] = str.getBytes();//���ַ���str�ַ���ת��Ϊ�ֽ�����     
	        System.out.println("��������"+ data);
	        //����һ��DatagramPacket�������ڷ������ݡ�     
	        //����һ��Ҫ���͵�����  �����������ݵĳ���  ������������˵������ַ  �����ģ��������˶˿ں�    
	        DatagramPacket packet = new DatagramPacket(data, data.length ,serverAddress ,port);    
	        socket.send(packet);//�����ݷ��͵�����ˡ� 
	        socket.close();//Ҫ�����close()������ӵĻ�ֻ���յ�һ������
	        Log.e("msg","����close");
	    } catch (SocketException e) {  
	        e.printStackTrace();  
	    } catch (UnknownHostException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }    
	} 
    class upClickListener implements OnClickListener{
    	//�����¶ȵ�����

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(j==1)//����Դ����
			{
    			M_arg.count++;
    			//���������¶�����
    			Editor editor = preferences.edit();
				editor.putInt("wendu", M_arg.count);
			 
				editor.commit();
    			numbershow();
    			if(M_arg.count>=31)
    				M_arg.count=31;
			}
		}		
	}
    class downClickListener implements OnClickListener{
    	//�����¶ȵ��½�

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(j==1)
			{
			M_arg.count--;
			Editor editor = preferences.edit();
			editor.putInt("wendu", M_arg.count);
			editor.commit();
			numbershow();
			if(M_arg.count<=16)
				M_arg.count=16;
			}	        
		}		
	}
    class moshiClickListener implements OnClickListener{
    	//����ģʽ�ı仯
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(j==1)
			{	
				count_moshi++;
				Editor editor = preferences.edit();
				editor.putInt("moshi", count_moshi);
				editor.commit();
				M_arg.count_moshi=count_moshi;
				moshishow();
			
				if(count_moshi==5)
					count_moshi=0;
			}
    				
           
		}		
	}
    public void initView()  
    {  
        //layout1 = (RelativeLayout) findViewById(R.id.layout1);  
        wind = (ImageView) findViewById(R.id.wind);  
        air=(ImageView)findViewById(R.id.air);
        number=(ImageView)findViewById(R.id.number);
        fangzi=(ImageView)findViewById(R.id.fangzi);
        qihou=(ImageView)findViewById(R.id.qihou);
        diandeng=(ImageView)findViewById(R.id.diandeng);
        dushu=(TextView)findViewById(R.id.dushu);
        
        
        dianyuan=(ImageButton)findViewById(R.id.dianyuan);
        dianyuan.setOnClickListener(new dianyuanClickListener() );
        
        preferences = getSharedPreferences("initview", Context.MODE_WORLD_READABLE);// ����ʵ��������
        /* fengliang=(ImageButton)findViewById(R.id.fengliang);
        fengliang.setOnClickListener(new fengClickListener());*/
        
        up=(ImageButton)findViewById(R.id.up);
        up.setOnClickListener(new upClickListener() );
        down=(ImageButton)findViewById(R.id.down);
        down.setOnClickListener(new downClickListener() );
        moshi=(ImageButton)findViewById(R.id.moshi);
        moshi.setOnClickListener(new moshiClickListener() );
        
        //��ʼ�����趨������ɫ����ͼƬ���ɼ�
        wind.setVisibility(View.INVISIBLE);
		air.setVisibility(View.INVISIBLE);
		
		fangzi.setVisibility(View.INVISIBLE);
		qihou.setVisibility(View.INVISIBLE);
		diandeng.setVisibility(View.INVISIBLE);
		
		numbershow();
		moshishow();
		dushu.setVisibility(View.INVISIBLE);
		number.setVisibility(View.INVISIBLE);
		
		//��ʱ����ִ�С�ÿ��60msִ��һ��
        timer.scheduleAtFixedRate(new TimerTask()  
        {  
        	//��ʱ��������60���Ը��Ķ�ʱʱ��
            @Override  
            public void run()  
            {  
                // TODO Auto-generated method stub  
                i++;  
                Message mesasge = new Message();  
                mesasge.what = i;  
                handler.sendMessage(mesasge);  
            }  
        }, 0, 60); //60ms ִ��һ��
    }  
  
    @Override  
    protected void onDestroy()  
    {  
        // TODO Auto-generated method stub  
        timer.cancel();  
        super.onDestroy();  
    }  
    protected void moshishow()
    {
    	//��ͬM_arg.count_moshi��Ӧ��ͬ��ģʽ��ʾ
    	M_arg.count_moshi = preferences.getInt("moshi", 0);
    	String s = Integer.toString(count_moshi);
    	/*Log.e("msgmoshi", s);
    	Log.e("msgmoshiafter", s);*/
    	switch (M_arg.count_moshi)  
        {  
        case 1: //ģʽһ����������� 
            qihou.setImageResource(R.drawable.mode_drying_white);  
            number.setVisibility(View.INVISIBLE);
            dushu.setVisibility(View.INVISIBLE);
            break;  
        case 2:  
            qihou.setImageResource(R.drawable.mode_warm_white);  
            number.setVisibility(View.VISIBLE);
            dushu.setVisibility(View.VISIBLE);
            break; 
        case 3:  
            qihou.setImageResource(R.drawable.mode_wind_black);  
            number.setVisibility(View.INVISIBLE);
            dushu.setVisibility(View.INVISIBLE);
            break; 
        case 4:  
            qihou.setImageResource(R.drawable.mode_auto_black);  
            number.setVisibility(View.INVISIBLE);
            dushu.setVisibility(View.INVISIBLE);
            break; 
        case 5:  
            qihou.setImageResource(R.drawable.mode_cold_black);  
            number.setVisibility(View.VISIBLE);
            dushu.setVisibility(View.VISIBLE);
            break; 
        default:  
            break;
        }
    }
    protected void numbershow()
    {
    	//��ͬ��M_arg.count��ʾ��ͬ������
    	M_arg.count = preferences.getInt("wendu", 0);
    	if(M_arg.count <16){
    		M_arg.count=16;
    	   number.setImageResource(R.drawable.img_temp_16);  
    	}
    	else {
			if(M_arg.count>31){
				M_arg.count=31;
				 number.setImageResource(R.drawable.img_temp_31);  
			}
		}
    	 switch (M_arg.count)  
         {  
         case 16:  
             number.setImageResource(R.drawable.img_temp_16);  
             break;  
         case 17:  
             number.setImageResource(R.drawable.img_temp_17);  
             break; 
         case 18:  
        	 number.setImageResource(R.drawable.img_temp_18); 
             break; 
         case 19:  
        	 number.setImageResource(R.drawable.img_temp_19); 
             break; 
         case 20:  
        	 number.setImageResource(R.drawable.img_temp_20);  
             break; 
         case 21:  
        	 number.setImageResource(R.drawable.img_temp_21);  
             break; 
         case 22:  
        	 number.setImageResource(R.drawable.img_temp_22);  
             break; 
         case 23:  
        	 number.setImageResource(R.drawable.img_temp_23); 
             break; 
         case 24:  
        	 number.setImageResource(R.drawable.img_temp_24);  
             break; 
         case 25:  
        	 number.setImageResource(R.drawable.img_temp_25);  
             break; 
         case 26:  
        	 number.setImageResource(R.drawable.img_temp_26); 
             break;  
         case 27:  
             number.setImageResource(R.drawable.img_temp_27);  
             break; 
         case 28:  
             number.setImageResource(R.drawable.img_temp_28);  
             break;  
         case 29:  
             number.setImageResource(R.drawable.img_temp_29);  
             break;  
         case 30:  
             number.setImageResource(R.drawable.img_temp_30);  
             break;  
         case 31:  
             number.setImageResource(R.drawable.img_temp_31);  
             break;  
         default:  
             break;  
         }  
    }
 
     	public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                	Intent intent=new Intent();
                	intent.setClass(Air.this,indexcontent.class);
                	startActivity(intent);
                	finish();
                	
                }
        		return true;
            }
       
}  
