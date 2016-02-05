package p2pClientServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class p2pClientAsServer implements Runnable {
	
	Socket client;
	ObjectOutputStream oout;
	ObjectInputStream oin;
	static Hashtable<String,String> keyValueStore = new Hashtable<String,String>();
	public static HashMap<String, List<String>> Registry = new HashMap<String, List<String>>();
	BufferedReader in;
	String dirname = "abc";
	
	public p2pClientAsServer(Socket client) throws IOException{
		this.client = client;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()) );
			oin = new ObjectInputStream(client.getInputStream());
			new Thread(this).start();
		}
		catch(Exception e) {
			System.out.println("One of the peer Disconnected!..");
		}finally{
			//oin.close();
			//client.close(); //Assignment-3 11-03
		}
	}
	public void run() {
		String operation = null;
		Object obj1;
		while(true){
			String[] storeArray = null;
			try 
			{
				//ObjectInputStream oin11 = new ObjectInputStream(client.getInputStream());
				obj1 = oin.readObject();
				operation = (String) obj1;
			} catch (IOException e1) {
				try {
					break;
					//oin.close();
					//client.close();
					//System.exit(0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("One of the peer going to Shutdown....000000000..");
				//oin.close();
				//e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Class Not Found");
				//e.printStackTrace();
			} catch (Exception e2){
				System.out.println("Peer Disconnected!....");
			}finally{
				
			}
			try 
			{
				if(operation.equals("put"))
				{
					Object obj2 = oin.readObject();
					String info = (String) obj2;
					//System.out.println("filename :"+info);
					Object obj3 = oin.readObject();
					String peer_id = (String) obj3;
					//System.out.println("peer1 :"+peer_id);
					Object obj4 = oin.readObject();
					String peer_id1 = (String) obj4;
					//System.out.println("repeer :"+peer_id1);
					registry(peer_id,info);
					registry(peer_id1,info);
					//displyRegistryList();
					//storeArray = info.split(":");
					oout = new ObjectOutputStream(client.getOutputStream());
					try 
					{
						//keyValueStore.put(storeArray[0], storeArray[1]);
						oout.writeObject("true");
					}
					catch(Exception e) {
						oout.writeObject("false");
					}
				}
				else if(operation.equals("get"))
				{
					Object getObject = oin.readObject();
					String name = (String) getObject;
					//String valueinfo =  keyValueStore.get(name);
					//System.out.println("Filename : "+name);
					ObjectOutputStream oout1 = new ObjectOutputStream(client.getOutputStream());
					try{
						oout1.writeObject(Registry.get(name));
					}
					catch(Exception e){
						oout1.writeObject("Key Not Found in Any Peer!....");
					}
				}
				else if(operation.equals("del"))
				{
					Object delObject = oin.readObject();
					String delname = (String) delObject;
					ObjectOutputStream oout3 = new ObjectOutputStream(client.getOutputStream());
					if(keyValueStore.containsKey(delname) == true)
					{
						keyValueStore.remove(delname);
						oout3.writeObject("true");
					}
					else
					{
						oout3.writeObject("false");
					}
				}
				else if(operation.equals("replication"))
				{
					Object repobj = oin.readObject();
					
					//String info = (String) obj2;
					String fileName =  (String) repobj;
					//System.out.println("filename rep :"+fileName);
					Object repobj1 = oin.readObject();
					String repdir = (String) repobj1;
					File dir = new File(repdir);
					if (!dir.exists()) {
						//System.out.println(" Shared Directory not Exists! .....Creating new shared directory for replication "+dir);
						dir.mkdir();
					}
					//ObjectInputStream oin1 = new ObjectInputStream(client.getInputStream());
			        FileOutputStream fos = null;

			        byte[] mybytearray = new byte[1024];
			        try {
			            fos = new FileOutputStream(repdir+"/"+fileName);

			            int count;
			            int count1;
			            do {
			            	count = oin.read(mybytearray,0,1024);
			            	if(count==-1)
			            	{
			            		break;
			            	}
			            	count1 = count;
			                fos.write(mybytearray, 0, count1);
			            } while (count >= 1024);
			        } finally {
			        	fos.close();
			            //System.out.println("Replication");
			        } 
				}
				else if(operation.equals("obtain"))
				{
					Object obobj = oin.readObject();
					//String info = (String) obj2;
					String fileName =  (String) obobj;
					//System.out.println("filename rep :"+fileName);
					Object obobj1 = oin.readObject();
					String repdir = (String) obobj1;
					File myFile = new File(repdir+"/"+fileName);
					//File dir = new File(repdir);
					//if (!dir.exists()) {
					//	System.out.println(" Shared Directory not Exists! .....Creating new shared directory for replication "+dir);
					//	dir.mkdir();
					//}
					ObjectOutputStream ofd = new ObjectOutputStream(client.getOutputStream());
					FileInputStream fos = new FileInputStream(myFile);

					try {
			        	byte[] mybytearray = new byte[1024];
			            //fis3 = new FileInputStream(myFile);
			            int count;
			            int count1;
			            do{
			            	count = fos.read(mybytearray, 0, 1024);
			            	ofd.writeObject(count);
			            	if(count==-1)
			            	{
			            		break;
			            	}
			            	count1 = count;
			            	ofd.write(mybytearray, 0, count1);
			                if(ofd != null) ofd.flush();
			            }while (count  >= 1024);
				        
			        }finally {
			            fos.close();
			            System.out.println("File Transfered");
			        }
			    }
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("One of the peer going to Shutdown......");
				//e.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			finally {
//				try {
//					client.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		} 
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
	public void displyRegistryList(){
		Set set = Registry.entrySet();
	    Iterator iterator = set.iterator();
	    System.out.println("Hashmap : ");
	    while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.println("key is: "+ mentry.getKey() + " & Value is: "+mentry.getValue());
	         //System.out.println(mentry.getValue());
	    }
	}
}
