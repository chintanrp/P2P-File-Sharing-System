
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.InterruptedByTimeoutException;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

//import p2pClientAsServer;

public class p2pClient implements Runnable {
	static String key;
	static String value;
	static int index;
	static int server_port;
	static String host_ip;
	ServerSocket serverSocket = null;
	Socket serverConnectionSocket = null;
	static Map<Integer, String> config = new LinkedHashMap<Integer, String>();
	
	public p2pClient(int Port_no){
		try {
			serverSocket = new ServerSocket(Port_no);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Host port number :"+serverSocket.getLocalPort());
		new Thread(this).start();
	}
	
	
	public static void main(String args[]) throws UnknownHostException, IOException, ClassNotFoundException{
		
		System.out.println("Enter server_port: ");
		Scanner sc1 = new Scanner(System.in);
		server_port = sc1.nextInt();
		System.out.println("Enter Server IP Address: ");
		Scanner sc11 = new Scanner(System.in);
		host_ip = sc11.nextLine();
		p2pClient client1 = new p2pClient(server_port);
		client1.getConfigValues();
		clientOpetions();
		
	}
	public static void clientOpetions() throws UnknownHostException, IOException, ClassNotFoundException{
		Socket getSocket = new Socket(host_ip,server_port);

		Iterator it = config.entrySet().iterator();
		int length = config.size();
		Socket[] socketArray= new Socket[8]; 
		int i =0;

		while (it.hasNext())
		{
			Map.Entry pair = (Map.Entry) it.next();
			int port1 = Integer.parseInt(pair.getKey().toString());
			if(port1 == server_port)
			{
				socketArray[i]= getSocket;
			}
			/*else
			{
				socketArray[i] = new Socket(pair.getValue().toString(),Integer.parseInt(pair.getKey().toString()));
			}*/
			else
			{
				boolean scanning = true;
				while(scanning)
				{
					try
					{
						//SocketChannel sc = SocketChannel.open();
						//sc.connect(new InetSocketAddress(pair.getValue().toString(),Integer.parseInt(pair.getKey().toString())));
						
						
						socketArray[i] = new Socket(pair.getValue().toString(),Integer.parseInt(pair.getKey().toString()));
						scanning = false;
					}
					catch(ConnectException e) {
						System.out.println("Connection Failed, All peer server has not been started yet.\n Wait for other socket connection...... \n Retrying in 20 Seconds..............");
						try
						{
							Thread.sleep(20000);
						}
						catch(Exception ex) {
							
							
						}
						
					}
				}	
				
			}
			i++;
		}
		System.out.println(i);
		ObjectOutputStream obj1 = new ObjectOutputStream(socketArray[0].getOutputStream());
		ObjectOutputStream obj2 = new ObjectOutputStream(socketArray[1].getOutputStream());
		ObjectOutputStream obj3 = new ObjectOutputStream(socketArray[2].getOutputStream());
		ObjectOutputStream obj4 = new ObjectOutputStream(socketArray[3].getOutputStream());
		ObjectOutputStream obj5 = new ObjectOutputStream(socketArray[4].getOutputStream());
		ObjectOutputStream obj6 = new ObjectOutputStream(socketArray[5].getOutputStream());
		ObjectOutputStream obj7 = new ObjectOutputStream(socketArray[6].getOutputStream());
		ObjectOutputStream obj8 = new ObjectOutputStream(socketArray[7].getOutputStream());
		

		try {
			Thread.sleep(10000);
		}
		catch(Exception e) {
			
		}
		while(true){
			int choice = 0;
			System.out.println("Peer : "+server_port);
			System.out.println("Options: ");
			System.out.println("1 - Put<key,value> function");
			System.out.println("2 - Get<key> function");
			System.out.println("3 - Del<key> function");
			System.out.println("4 - Evaluation");
			System.out.println("5 - Exit");
			Scanner sc = new Scanner(System.in);
			try{
				choice = sc.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("Value Must be Integer.....");
				clientOpetions();
			}
			switch(choice){
			case 1: 
				int getServerIndex = selectServer();
				String sendInfo;
				sendInfo= key+":"+value;
				if(getServerIndex>=100000 && getServerIndex<=199999){
					System.out.println("Server-1");
					obj1.writeObject("put");
					obj1.writeObject(sendInfo);
					ObjectInputStream oin11 = new ObjectInputStream(socketArray[0].getInputStream());
				    Object flag = oin11.readObject();
					String inp = (String) flag;
					System.out.println(flag);
					break;
				}
				else if(getServerIndex>=200000 && getServerIndex<=299999){
					System.out.println("Server-2");
					obj2.writeObject("put");
					obj2.writeObject(sendInfo);
					ObjectInputStream oin12 = new ObjectInputStream(socketArray[1].getInputStream());
				    Object flag = oin12.readObject();
					String inp = (String) flag;
					System.out.println(flag);
					break;
				}
				else if(getServerIndex>=300000 && getServerIndex<=399999){
					System.out.println("Server-3");
					obj3.writeObject("put");
					obj3.writeObject(sendInfo);
					ObjectInputStream oin13 = new ObjectInputStream(socketArray[2].getInputStream());
				    Object flag = oin13.readObject();
					String inp = (String) flag;
					System.out.println(flag);
					break;
				}
				else if(getServerIndex>=400000 && getServerIndex<=499999){
					System.out.println("Server-4");
					obj4.writeObject("put");
					obj4.writeObject(sendInfo);
					ObjectInputStream oin14 = new ObjectInputStream(socketArray[3].getInputStream());
				    Object flag = oin14.readObject();
					String inp = (String) flag;
					System.out.println(flag);
					break;
				}
				else if(getServerIndex>=500000 && getServerIndex<=599999){
					System.out.println("Server-5");
					obj5.writeObject("put");
					obj5.writeObject(sendInfo);
					ObjectInputStream oin15 = new ObjectInputStream(socketArray[4].getInputStream());
				    Object flag = oin15.readObject();
					String inp = (String) flag;
					System.out.println(flag);
					break;
				}
				else if(getServerIndex>=600000 && getServerIndex<=699999){
					System.out.println("Server-6");
					obj6.writeObject("put");
					obj6.writeObject(sendInfo);
					ObjectInputStream oin16 = new ObjectInputStream(socketArray[5].getInputStream());
				    Object flag = oin16.readObject();
					String inp = (String) flag;
					System.out.println(flag);
					break;
				}
				else if(getServerIndex>=700000 && getServerIndex<=799999){
					System.out.println("Server-7");
					obj7.writeObject("put");
					obj7.writeObject(sendInfo);
					ObjectInputStream oin17 = new ObjectInputStream(socketArray[6].getInputStream());
				    Object flag = oin17.readObject();
					String inp = (String) flag;
					System.out.println(flag);
					break;
				}
				else if(getServerIndex>=800000 && getServerIndex<=899999){
					System.out.println("Server-8");
					obj8.writeObject("put");
					obj8.writeObject(sendInfo);
					ObjectInputStream oin18 = new ObjectInputStream(socketArray[7].getInputStream());
				    Object flag = oin18.readObject();
					String inp = (String) flag;
					System.out.println(flag);
					break;
				}
			    else{
					System.out.println("The key entered is invalid");
					break;
				}
				
			case 2 : 	
				String searchName;
				Scanner sc2= new Scanner(System.in);
				System.out.println("Enter the key you want to get value between <100000 - 899999> :");
				searchName = sc2.nextLine();
				int getIndex = Integer.parseInt(searchName);
				if(getIndex>=100000 && getIndex<=199999){
					System.out.println("Server-1");
					obj1.writeObject("get");
					obj1.writeObject(searchName);
					ObjectInputStream oin21 = new ObjectInputStream(socketArray[0].getInputStream());
					Object objValue = oin21.readObject();
					String serverValue = (String) objValue;
					System.out.println(serverValue);
					break;
				}
				else if(getIndex>=200000 && getIndex<=299999){
					System.out.println("Server-2");
					obj2.writeObject("get");
					obj2.writeObject(searchName);
					ObjectInputStream oin22 = new ObjectInputStream(socketArray[1].getInputStream());
					Object objValue = oin22.readObject();
					String serverValue = (String) objValue;
					System.out.println(serverValue);
					break;
				}
				else if(getIndex>=300000 && getIndex<=399999){
					System.out.println("Server-3");
					obj3.writeObject("get");
					obj3.writeObject(searchName);
					ObjectInputStream oin23 = new ObjectInputStream(socketArray[2].getInputStream());
					Object objValue = oin23.readObject();
					String serverValue = (String) objValue;
					System.out.println(serverValue);
					break;
				}
				else if(getIndex>=400000 && getIndex<=499999){
					System.out.println("Server-4");
					obj4.writeObject("get");
					obj4.writeObject(searchName);
					ObjectInputStream oin24 = new ObjectInputStream(socketArray[3].getInputStream());
					Object objValue = oin24.readObject();
					String serverValue = (String) objValue;
					System.out.println(serverValue);
					break;
				}
				else if(getIndex>=500000 && getIndex<=599999){
					System.out.println("Server-5");
					obj5.writeObject("get");
					obj5.writeObject(searchName);
					ObjectInputStream oin25 = new ObjectInputStream(socketArray[4].getInputStream());
					Object objValue = oin25.readObject();
					String serverValue = (String) objValue;
					System.out.println(serverValue);
					break;
				}
				else if(getIndex>=600000 && getIndex<=699999){
					System.out.println("Server-6");
					obj6.writeObject("get");
					obj6.writeObject(searchName);
					ObjectInputStream oin26 = new ObjectInputStream(socketArray[5].getInputStream());
					Object objValue = oin26.readObject();
					String serverValue = (String) objValue;
					System.out.println(serverValue);
					break;
				}
				else if(getIndex>=700000 && getIndex<=799999){
					System.out.println("Server-7");
					obj7.writeObject("get");
					obj7.writeObject(searchName);
					ObjectInputStream oin27 = new ObjectInputStream(socketArray[6].getInputStream());
					Object objValue = oin27.readObject();
					String serverValue = (String) objValue;
					System.out.println(serverValue);
					break;
				}
				else if(getIndex>=800000 && getIndex<=899999){
					System.out.println("Server-8");
					obj8.writeObject("get");
					obj8.writeObject(searchName);
					ObjectInputStream oin28 = new ObjectInputStream(socketArray[7].getInputStream());
					Object objValue = oin28.readObject();
					String serverValue = (String) objValue;
					System.out.println(serverValue);
					break;
				}
				else{
					System.out.println("The key entered is invalid");
					break;
				}
				
			case 3:
				Scanner deleteKey = new Scanner(System.in);
				System.out.println("Enter the key you want ot delete between <100000 - 899999> : ");
				String name = deleteKey.nextLine();
				int delIndex = Integer.parseInt(name);
				if(delIndex>=100000 && delIndex <=199999){
					System.out.println("Server-1");
					obj1.writeObject("del");
					obj1.writeObject(name);
					ObjectInputStream oin31 = new ObjectInputStream(socketArray[0].getInputStream());
					Object delobj = oin31.readObject();
					String flag = (String) delobj;
					System.out.println(flag);
					break;
				}
				else if(delIndex>=200000 && delIndex <=299999){
					System.out.println("server-2");
					obj2.writeObject("del");
					obj2.writeObject(name);
					ObjectInputStream oin32 = new ObjectInputStream(socketArray[1].getInputStream());
					Object delobj = oin32.readObject();
					String flag = (String) delobj;
					System.out.println(flag);
					break;
				}
				else if(delIndex>=300000 && delIndex <=399999){
					System.out.println("Server-3");
					obj3.writeObject("del");
					obj3.writeObject(name);
					ObjectInputStream oin33 = new ObjectInputStream(socketArray[2].getInputStream());
					Object delobj = oin33.readObject();
					String flag = (String) delobj;
					System.out.println(flag);
					break;
				}
				else if(delIndex>=400000 && delIndex <=499999){
					System.out.println("Server-4");
					obj4.writeObject("del");
					obj4.writeObject(name);
					ObjectInputStream oin34 = new ObjectInputStream(socketArray[3].getInputStream());
					Object delobj = oin34.readObject();
					String flag = (String) delobj;
					System.out.println(flag);
					break;
				}
				else if(delIndex>=500000 && delIndex <=599999){
					System.out.println("Server-5");
					obj5.writeObject("del");
					obj5.writeObject(name);
					ObjectInputStream oin35 = new ObjectInputStream(socketArray[4].getInputStream());
					Object delobj = oin35.readObject();
					String flag = (String) delobj;
					System.out.println(flag);
					break;
				}
				else if(delIndex>=600000 && delIndex <=699999){
					System.out.println("Server-6");
					obj6.writeObject("del");
					obj6.writeObject(name);
					ObjectInputStream oin36 = new ObjectInputStream(socketArray[5].getInputStream());
					Object delobj = oin36.readObject();
					String flag = (String) delobj;
					System.out.println(flag);
					break;
				}
				else if(delIndex>=700000 && delIndex <=799999){
					System.out.println("Server-7");
					obj7.writeObject("del");
					obj7.writeObject(name);
					ObjectInputStream oin37 = new ObjectInputStream(socketArray[6].getInputStream());
					Object delobj = oin37.readObject();
					String flag = (String) delobj;
					System.out.println(flag);
					break;
				}
				else if(delIndex>=800000 && delIndex <=899999){
					System.out.println("Server-8");
					obj8.writeObject("del");
					obj8.writeObject(name);
					ObjectInputStream oin38 = new ObjectInputStream(socketArray[7].getInputStream());
					Object delobj = oin38.readObject();
					String flag = (String) delobj;
					System.out.println(flag);
					break;
				}
				else{
					System.out.println("The key entered is invalid");
					break;
				}
			
			case 4:
				int c1 = 0;
				
				System.out.println("Options: ");
				System.out.println("1 - Put Benchmarking");
				System.out.println("2 - Get Benchmarking");
				System.out.println("3 - Del Benchmarking");
				Scanner sc4 = new Scanner(System.in);
				try{
					c1 = sc4.nextInt();
				}
				catch(InputMismatchException e){
					System.out.println("Value Must be Integer.....");
					clientOpetions();
				}
				int c2 = 0;
				
				System.out.println("Options: ");
				System.out.println("1 - Server-1");
				System.out.println("2 - Server-2");
				System.out.println("3 - Server-3");
				System.out.println("4 - Server-4");
				System.out.println("5 - Server-5");
				System.out.println("6 - Server-6");
				System.out.println("7 - Server-7");
				System.out.println("8 - Server-8");
				Scanner sc41 = new Scanner(System.in);
				try{
					c2 = sc41.nextInt();
				}
				catch(InputMismatchException e){
					System.out.println("Value Must be Integer.....");
					clientOpetions();
				}
				//String last = randomValue();
				String last = "IITChicago";
				final long downloadstartTime = System.nanoTime();
				final long downloadendTime;
				final long downloadduration;
				switch(c1){
				case 1:
					switch(c2){
					case 1:
						for (int j = 100000; j < 199999; j++) {
							String eval;
							
							eval= j+":"+last;
							obj1.writeObject("put");
							obj1.writeObject(eval);
							ObjectInputStream oin41 = new ObjectInputStream(socketArray[0].getInputStream());
						    Object flag = oin41.readObject();
							String inp = (String) flag;
							//System.out.println(flag);
						 		
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 2:
						for (int j = 200000; j < 299999; j++) {
							String eval;
							eval= j+":"+last;
							obj2.writeObject("put");
							obj2.writeObject(eval);
							ObjectInputStream oin42 = new ObjectInputStream(socketArray[1].getInputStream());
						    Object flag = oin42.readObject();
							String inp = (String) flag;
							//System.out.println(flag);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 3:
						for (int j = 300000; j < 399999; j++) {
							String eval;
							eval= j+":"+last;
							obj3.writeObject("put");
							obj3.writeObject(eval);
							ObjectInputStream oin43 = new ObjectInputStream(socketArray[2].getInputStream());
						    Object flag = oin43.readObject();
							String inp = (String) flag;
							//System.out.println(flag);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 4:
						for (int j = 400000; j < 499999; j++) {
							String eval;
							eval= j+":"+last;
							obj4.writeObject("put");
							obj4.writeObject(eval);
							ObjectInputStream oin44 = new ObjectInputStream(socketArray[3].getInputStream());
						    Object flag = oin44.readObject();
							String inp = (String) flag;
							//System.out.println(flag);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 5:
						for (int j = 500000; j < 599999; j++) {
							String eval;
							eval= j+":"+last;
							obj5.writeObject("put");
							obj5.writeObject(eval);
							ObjectInputStream oin45 = new ObjectInputStream(socketArray[4].getInputStream());
						    Object flag = oin45.readObject();
							String inp = (String) flag;
							//System.out.println(flag);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 6:
						for (int j = 600000; j < 699999; j++) {
							String eval;
							eval= j+":"+last;
							obj6.writeObject("put");
							obj6.writeObject(eval);
							ObjectInputStream oin46 = new ObjectInputStream(socketArray[5].getInputStream());
						    Object flag = oin46.readObject();
							String inp = (String) flag;
							//System.out.println(flag);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 7:
						for (int j = 700000; j < 799999; j++) {
							String eval;
							eval= j+":"+last;
							obj7.writeObject("put");
							obj7.writeObject(eval);
							ObjectInputStream oin47 = new ObjectInputStream(socketArray[6].getInputStream());
						    Object flag = oin47.readObject();
							String inp = (String) flag;
							//System.out.println(flag);
						   
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 8:
						for (int j = 800000; j < 899999; j++) {
							String eval;
							eval= j+":"+last;
							obj8.writeObject("put");
							obj8.writeObject(eval);
							ObjectInputStream oin48 = new ObjectInputStream(socketArray[7].getInputStream());
						    Object flag = oin48.readObject();
							String inp = (String) flag;
							//System.out.println(flag);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					default:
						System.out.println("Given Server ont connected in Network");
						break;
					}
					break;
				case 2:
					switch(c2){
					case 1:
						for (int j = 100000; j < 199999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj1.writeObject("get");
							obj1.writeObject(eval);
							ObjectInputStream oin41 = new ObjectInputStream(socketArray[0].getInputStream());
						    Object flag = oin41.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 2:
						for (int j = 200000; j < 299999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj2.writeObject("get");
							obj2.writeObject(eval);
							ObjectInputStream oin42 = new ObjectInputStream(socketArray[1].getInputStream());
						    Object flag = oin42.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 3:
						for (int j = 300000; j < 399999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj3.writeObject("get");
							obj3.writeObject(eval);
							ObjectInputStream oin43 = new ObjectInputStream(socketArray[2].getInputStream());
						    Object flag = oin43.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 4:
						for (int j = 400000; j < 499999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj4.writeObject("get");
							obj4.writeObject(eval);
							ObjectInputStream oin44 = new ObjectInputStream(socketArray[3].getInputStream());
						    Object flag = oin44.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 5:
						for (int j = 500000; j < 599999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj5.writeObject("get");
							obj5.writeObject(eval);
							ObjectInputStream oin45 = new ObjectInputStream(socketArray[4].getInputStream());
						    Object flag = oin45.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 6:
						for (int j = 600000; j < 699999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj6.writeObject("get");
							obj6.writeObject(eval);
							ObjectInputStream oin46 = new ObjectInputStream(socketArray[5].getInputStream());
						    Object flag = oin46.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 7:
						for (int j = 700000; j < 799999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj7.writeObject("get");
							obj7.writeObject(eval);
							ObjectInputStream oin47 = new ObjectInputStream(socketArray[6].getInputStream());
						    Object flag = oin47.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 8:
						for (int j = 800000; j < 899999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj8.writeObject("get");
							obj8.writeObject(eval);
							ObjectInputStream oin48 = new ObjectInputStream(socketArray[7].getInputStream());
						    Object flag = oin48.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					default:
						System.out.println("Given Server ont connected in Network");
						break;
					}
					break;
				case 3:
					switch(c2){
					case 1:
						for (int j = 100000; j < 199999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj1.writeObject("del");
							obj1.writeObject(eval);
							ObjectInputStream oin41 = new ObjectInputStream(socketArray[0].getInputStream());
						    Object flag = oin41.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 2:
						for (int j = 200000; j < 299999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj2.writeObject("del");
							obj2.writeObject(eval);
							ObjectInputStream oin42 = new ObjectInputStream(socketArray[1].getInputStream());
						    Object flag = oin42.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						  	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 3:
						for (int j = 300000; j < 399999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj3.writeObject("del");
							obj3.writeObject(eval);
							ObjectInputStream oin43 = new ObjectInputStream(socketArray[2].getInputStream());
						    Object flag = oin43.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 4:
						for (int j = 400000; j < 499999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj4.writeObject("del");
							obj4.writeObject(eval);
							ObjectInputStream oin44 = new ObjectInputStream(socketArray[3].getInputStream());
						    Object flag = oin44.readObject();
							String inp = (String) flag;
							System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 5:
						for (int j = 500000; j < 599999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj5.writeObject("del");
							obj5.writeObject(eval);
							ObjectInputStream oin45 = new ObjectInputStream(socketArray[4].getInputStream());
						    Object flag = oin45.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 6:
						for (int j = 600000; j < 699999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj6.writeObject("del");
							obj6.writeObject(eval);
							ObjectInputStream oin46 = new ObjectInputStream(socketArray[5].getInputStream());
						    Object flag = oin46.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 7:
						for (int j = 700000; j < 799999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj7.writeObject("del");
							obj7.writeObject(eval);
							ObjectInputStream oin47 = new ObjectInputStream(socketArray[6].getInputStream());
						    Object flag = oin47.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					case 8:
						for (int j = 800000; j < 899999; j++) {
							String eval;
							eval= Integer.toString(j);
							obj8.writeObject("del");
							obj8.writeObject(eval);
							ObjectInputStream oin48 = new ObjectInputStream(socketArray[7].getInputStream());
						    Object flag = oin48.readObject();
							String inp = (String) flag;
							//System.out.println(inp);
						   	
						}
						 	downloadendTime = System.nanoTime();
						 	downloadduration = downloadendTime
									- downloadstartTime;
							System.out.println("Download Response time: "
									+ downloadduration + "ns");
						break;
					default:
						System.out.println("Given Server ont connected in Network");
						break;
					}
					break;
				default:
					System.out.println("\nPlease enter a number between 1 and 3\n");
					break;
				}
				break;
			case 5:
				System.exit(0);
			default:
				System.out.println("\nPlease enter a number between 1 and 5\n");
				break;
			}
		
		}	
	}
	public static int selectServer(){
		getKeyValue();
		return Integer.parseInt(key);
	}
	public static void getKeyValue(){
		System.out.println("Enter the key between (100000 - 899999) : ");
		Scanner sc = new Scanner(System.in);
		key = sc.nextLine();
		System.out.println("Enter the respective value of the key-: ");
		Scanner sc1 = new Scanner(System.in);
		value = sc1.nextLine();		
	}
	public void getConfigValues() throws IOException {
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			//System.out.println("class :"+getClass());
			//System.out.println("class :"+getClass().getClassLoader());
			inputStream = new FileInputStream(propFileName);
 
			if (inputStream == null) {
				System.out.println("Sorry, unable to find " + propFileName);
    		    return;
			}
 
			prop.load(inputStream);
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				//System.out.println("Key : " + key + ", Value : " + value);
				config.put(Integer.parseInt(key), value);
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}
	public static String randomValue()
	{
		char[] charList;
	    StringBuilder sb = new StringBuilder();
	    for (char ch = '0'; ch <= '9'; ++ch)
	        sb.append(ch);
	    for (char ch = 'a'; ch <= 'z'; ++ch)
	        sb.append(ch);
	    charList = sb.toString().toCharArray();
		StringBuilder sb1=new StringBuilder();
		Random random=new Random();
	    
		  for(int k=0;k<10000;k++) {
			char ch = charList[random.nextInt(charList.length)];
			sb1.append(ch);
		  }
	   
		String nextLine = sb1.toString();
		return nextLine;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Trying to connect......");
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
		try {
			
			serverConnectionSocket = serverSocket.accept();
			System.out.println("Connection estabralished from"+serverConnectionSocket.getInetAddress()+"and port"+serverConnectionSocket.getLocalPort());
			new p2pClientAsServer(serverConnectionSocket);
//			obj.fun();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
	}
	

}
