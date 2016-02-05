package p2pserver;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class IndexServer implements Runnable {
	
	
    public Socket connectionSock ;
	public static HashMap<String, List<String>> Registry = new HashMap<String, List<String>>();
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ServerSocket server_sock ;
    	InetAddress ip = InetAddress.getLocalHost();
    	System.out.println("Please enter Port No : ");
		Scanner sc = new Scanner(System.in);
		Integer port = sc.nextInt();
		server_sock = new ServerSocket(port);
		System.out.print("Server running on IP : "+ip.getHostAddress()+".......... Port No : "+port);

		System.out.println("Trying to connect");
		//int i =1;
   	    while(true)
   	    {
		Socket sock = server_sock.accept();
		 //new IndexServer("8888");
		Thread t = new Thread(new IndexServer(sock));
		t.start();
   	    }
	}
	
	public void run(){
		ArrayList<String> titleList = new ArrayList<String>();
		try{
				System.out.println("The connection from"+connectionSock.getInetAddress()+":"+connectionSock.getPort());
				
				ObjectInputStream objectInput = new ObjectInputStream(connectionSock.getInputStream());
				Object object = objectInput.readObject();
                titleList =  (ArrayList<String>) object;
                Object object2 = objectInput.readObject();
                String peer_port = (String) object2;
                int choice=0;
                boolean loop = true;
                for(String s:titleList){
                	//System.out.println(titleList);
                	registry(peer_port,s);
                }
                //System.out.println(Registry);
                
                
                while (loop) {
                	//ObjectInputStream objectInputch = new ObjectInputStream(connectionSock.getInputStream());
                	//Object ch = objectInputch.readObject();
                	String ch1;
                	 InputStream inStream = connectionSock.getInputStream();
                	 InputStreamReader isr = new InputStreamReader(inStream);
                	BufferedReader bufferedReader = new BufferedReader(isr);
                    ch1 = bufferedReader.readLine();
                    choice = Integer.parseInt(ch1);
                    //System.out.println("ch1 : "+ch1);
                    //System.out.println("choice : "+choice);
                	switch (choice) {
                	case 1:
                		//System.out.println("case1: "+choice);
                		ObjectInputStream objectInput1 = new ObjectInputStream(connectionSock.getInputStream());
                		ObjectOutputStream objectOutput = new ObjectOutputStream(connectionSock.getOutputStream());
                		Object object1 = objectInput1.readObject();
                		String searchfile =  (String) object1;
		         
                		//System.out.println("File Name :"+ searchfile );
                		//System.out.println("port: "+Registry.get(searchfile));
		         
		         
                		objectOutput.writeObject(Registry.get(searchfile));
                		//System.out.println("case1 end : "+choice);
                		break;
                		
                	case 2:
                		break;
                	
                	case 3:
                		//System.out.println("case3: "+choice);
                		ObjectOutputStream hashMapFileLIst = new ObjectOutputStream(connectionSock.getOutputStream());
                		ArrayList<String> shared_fileList = new ArrayList<String>();
                		Iterator iterator = Registry.keySet().iterator();
                		while (iterator.hasNext()) {
                			shared_fileList.add(iterator.next().toString());
                		}
                		hashMapFileLIst.writeObject(shared_fileList);
                		//System.out.println("case3 end : "+choice);
                		break;
                	case 4:
                		loop = true;
                		break;
                	default:
        				System.out.println("\nPlease enter a number between 1 and 3\n");
        				break;
                	}
                	//objectInputch.close();
                	//isr.close();
                }
		         //objectOutput.close();
                connectionSock.close();
		         objectInput.close();    
			}
			catch(IOException e){
				System.out.println("The connection failed");
				e.printStackTrace();
				} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
					System.out.println("The connection failed1");
				e.printStackTrace();
			} catch (Exception e) {
					// TODO Auto-generated catch block
				System.out.println("The connection failed2");
					e.printStackTrace();
				}
		}
			
	
	public IndexServer(Socket sock){
		connectionSock=sock;
	}

		
	public synchronized boolean registry(String portNumber, String filename) throws Exception {
		// add filenames and peerid to the registry (assign by return peerids to clients)
	 
		List<String> tmp = new ArrayList<String>();
		List<String> put = new ArrayList<String>();
		
		//check if file is already in registry
		tmp = Registry.get(filename);
		
		if(tmp == null || tmp.isEmpty())  //file not in registry
		{
			
			put.add(portNumber);
			Registry.put(filename, put);
		}
		else //file exists
		{
			//test to see if peer is already listed, if not put in list and put in registry
			Iterator<String> i = tmp.iterator();
			
			while(i.hasNext())
			{
				String x = i.next();
				
				if(x == portNumber)  //filename and peer already exist return
				{
					return true;
				}
			}

			tmp.add(portNumber);
			Registry.put(filename, tmp);
			
		}
		return true;
	}
	
}
