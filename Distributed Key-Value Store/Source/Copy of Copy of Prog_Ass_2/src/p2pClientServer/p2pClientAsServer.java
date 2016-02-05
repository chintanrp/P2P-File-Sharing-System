package p2pClientServer;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;

public class p2pClientAsServer implements Runnable {
	
	Socket client;
	ObjectOutputStream oout;
	ObjectInputStream oin;
	static Hashtable<String,String> keyValueStore = new Hashtable<String,String>();
	BufferedReader in;
	
	public p2pClientAsServer(Socket client) throws IOException{
		this.client = client;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()) );
			oin = new ObjectInputStream(client.getInputStream());
			new Thread(this).start();
		}
		catch(Exception e) {
			System.out.println("One of the peer Disconnected!..");
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
				System.out.println("One of the peer going to Shutdown......");
				//e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Class Not Found");
				//e.printStackTrace();
			} catch (Exception e2){
				System.out.println("Peer Disconnected!....");
			}
			
			
			try 
			{
				if(operation.equals("put"))
				{
					Object obj2 = oin.readObject();
					String info = (String) obj2;
					storeArray = info.split(":");
					oout = new ObjectOutputStream(client.getOutputStream());
					try 
					{
						keyValueStore.put(storeArray[0], storeArray[1]);
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
					String valueinfo =  keyValueStore.get(name);
					ObjectOutputStream oout1 = new ObjectOutputStream(client.getOutputStream());
					if(valueinfo != null){
						oout1.writeObject(valueinfo);
					}
					else{
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
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("One of the peer going to Shutdown......");
				//e.printStackTrace();
			}
		}	
	}
}
