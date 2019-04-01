package com.asiainfo.springbootrestful.mysocket;//我的github:https://github.com/ygj0930
//我的博客：http://www.cnblogs.com/ygj0930/
import java.io.*;
import java.net.*;
import java.util.*;

public class Client{
	public int port=8080;
	Socket socket=null;
	
	public static void main(String[] args)
	{
		new Client();
	}
	
	public Client() {
		try {  
		    socket=new Socket("127.0.0.1",port);
			
			new Cthread().start();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket  
                  .getInputStream()));  
            String msg1;  
            while ((msg1 = br.readLine()) != null) { 
							
                System.out.println(msg1);  
			}
		}catch (Exception e) {  
  
		}
	}
	
	class Cthread extends Thread {
		public void run() {
            try {
				System.out.print("输入内容：");
				BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				String msg2;
				while (true) {
                    msg2 = re.readLine();
                    pw.println(msg2);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
		}
	}

}