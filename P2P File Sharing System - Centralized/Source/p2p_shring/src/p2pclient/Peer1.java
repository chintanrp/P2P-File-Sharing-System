package p2pclient;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



public class Peer1 implements Runnable{

	static String dirname = null;
	static long avgResponseTime = 0;
	static long aggregateResponseTime = 0;
	static int numLookups = 0;
	//static Integer Port_No = 8888;
	static Integer peer_port = null;
	static ArrayList<String> fileNames = new ArrayList<String>();
	public Peer1(Integer port_number) throws NumberFormatException, Exception{
		Thread t1=new Thread(this);
		t1.setName("server");
		Thread t2=new Thread(this);
		t2.setName("peer");
		t1.start();
		//t2.start();
		
	}
	
	public static void main(String[] args) throws Exception{
		String s;
		Scanner scan = new Scanner(System.in);
		InetAddress ip = InetAddress.getLocalHost();
		boolean loop = true;
		String filename;
		System.out.println(ip.getHostAddress());
		System.out.println("Please enter Port No for Peer to Peer connection : ");
		Scanner sc = new Scanner(System.in);
		peer_port = sc.nextInt();
		//sc1.close();
		System.out.println("Please enter Shared Directory Name : ");
		Scanner sc1 = new Scanner(System.in);
		String i = sc1.nextLine();
		dirname = i;
		File dir = new File(dirname);
		if (!dir.exists()) {
			System.out.println(" Shared Directory not Exists! .....Creating new shared directory "+dir);
			dir.mkdir();
		}
		new Peer1(peer_port);
		Socket client_socket = null;
		System.out.println("Please enter Ip Addess For socket connection : ");
		Scanner sc2 = new Scanner(System.in);
		String server_ip = sc2.nextLine();
		System.out.println("Please enter Port No for Scoket connection : ");
		//Scanner sc1 = new Scanner(System.in);
		Integer server_port = sc.nextInt();
		client_socket = new Socket(server_ip,server_port);
		
		
		int choice=0;
		String peerServer_ip= null;
		int peerServer_port = 0;
		getPeerFiles(dir);
		final long registerStartTie = System.nanoTime();
		final long registerEndTime;
		ObjectOutputStream objectOutput = new ObjectOutputStream(client_socket.getOutputStream());
        objectOutput.writeObject(fileNames);
        registerEndTime = System.nanoTime();
        final long registerDuration = registerEndTime
				- registerStartTie;
		System.out.println("Register Response time: "
				+ registerDuration + "ns");
        objectOutput.writeObject(ip.getHostAddress()+":"+peer_port);
        
       // ObjectOutputStream objectOutputsw = new ObjectOutputStream(client_socket.getOutputStream());
		while (loop) {
			System.out.println("\n\nPeerID: " + peer_port );
			System.out.println("Options:");
			System.out.println("1 - Search for filename");
			System.out.println("2 - Obtain filename from peer");
			System.out.println("3 - List files in shared directory");
			System.out.println("4 - Exit");	
			//System.out.println("5 - Run experiment with 1000 random requests");
			System.out.print("\n\n:");

			s = scan.nextLine();
			try { choice = Integer.parseInt(s.trim()); }
			catch(NumberFormatException e) {
				//System.out.println("\nPlease enter an integer\n");
			}
			
			//objectOutputsw.writeObject(choice);
			PrintWriter printWriter;
			 printWriter = new PrintWriter(client_socket.getOutputStream(),true);
	         printWriter.println(choice);
			switch (choice) {

			case 1:
				numLookups++;
				// Get response time also
				
				
				Scanner sc_c1 = new Scanner(System.in);
				System.out.print("Enter filename: ");
				filename = sc_c1.nextLine();
				System.out.println(filename);
				System.out.print("\n");
				final long lookupstartTime = System.nanoTime();
				final long lookupendTime;
				try {
				ObjectOutputStream objectOutputch = new ObjectOutputStream(client_socket.getOutputStream());
				//ObjectOutputStream out = new ObjectOutputStream(client_socket.getOutputStream());
				objectOutputch.writeObject(filename);                                                 /*Write/Send to Client*/

				ObjectInputStream objectInput = new ObjectInputStream(client_socket.getInputStream());
				Object object = objectInput.readObject();
				List<String> peer_list = new ArrayList<String>();
				try {
					peer_list =  (List<String>) object;
					if(peer_list.isEmpty())
					{
						System.out.println("Note : No Peer have "+filename+" in their shared directory..........");
					}
					else 
					{
						System.out.println("Below Peer have "+filename+" in their shared directory : ");
						for(String s1:peer_list){
							System.out.println(s1);	
						}
					}
				} catch(NullPointerException e) {
					System.out.println("Error : No Peer have "+filename+" in their shared directory..........");
				}
				} finally {
					lookupendTime = System.nanoTime();
				}
				final long lookupduration = lookupendTime - lookupstartTime;
				System.out.println("Lookup Response time: " + lookupduration
						+ " ns");
				aggregateResponseTime+=lookupduration;
				//objectOutput.close();
		        //objectInput.close();  
				//System.out.println(portList);
				
				break;

			case 2:
				Scanner sc_c2 = new Scanner(System.in);
				System.out.print("Enter filename: ");
				filename = sc_c2.nextLine();
				System.out.print("\nEnter Peer Server IP Address: ");
				peerServer_ip = sc_c2.nextLine();
				System.out.print("\nEnter Peer Server Port No: ");
				peerServer_port = sc_c2.nextInt();
				//peerServer = Integer.parseInt(s.trim());
				
				//---------------------------
				
				Socket client_socket_1= null;
				int FILE_SIZE = 6022386;
				client_socket_1 = new Socket(peerServer_ip,peerServer_port);
				System.out.println("Connecting...");
				ObjectOutputStream out1 = new ObjectOutputStream(client_socket_1.getOutputStream());
				out1.writeObject(filename); 
				int bytesRead;
			    int current = 0;
			    FileOutputStream fos = null;
			    BufferedOutputStream bos = null;
			    final long searchStartTIme = System.nanoTime();
				final long searchEndTime;
			    try {
			      // receive file
			      byte [] mybytearray  = new byte [FILE_SIZE];
			      InputStream is = client_socket_1.getInputStream();
			      fos = new FileOutputStream(dirname+"/"+filename);
			      bos = new BufferedOutputStream(fos);
			      bytesRead = is.read(mybytearray,0,mybytearray.length);
			      current = bytesRead;

			      do {
			         bytesRead =
			            is.read(mybytearray, current, (mybytearray.length-current));
			         if(bytesRead >= 0) current += bytesRead;
			      } while(bytesRead > -1);

			      bos.write(mybytearray, 0 , current);
			      bos.flush();
			      System.out.println("File " + filename
			          + " downloaded (" + current + " bytes read)");
			    }
			    catch(Exception e){
			    	System.out.println("File already Exists in shared directory.......");
			    }
			    finally {
			      if (fos != null) fos.close();
			      if (bos != null) bos.close();
			      if (client_socket_1 != null) client_socket_1.close();
			      searchEndTime = System.nanoTime();
			    }
				
				final long searchDuration = searchEndTime
						- searchStartTIme;
				System.out.println("Download Response time: "
						+ searchDuration + "ns");
				break;

			case 3:
				ObjectInputStream objectInput1 = new ObjectInputStream(client_socket.getInputStream());
				Object object3 = objectInput1.readObject();
				ArrayList<String> shared_FileList = new ArrayList<String>();
				shared_FileList =  (ArrayList<String>) object3;
				System.out.println("Files in Shared Directory : ");
				for(String s1:shared_FileList){
					System.out.println(s1);
                }
				//objectInput1.close(); 
				break;

			case 4:
				if(numLookups>0)
				{
					avgResponseTime=aggregateResponseTime/numLookups;
					System.out.println("\nAverage Lookup Response time for this session: "
								+ avgResponseTime + "\n");
				}
				else
				{
					System.out.println("Average Lookup Response time for this session: 0");
				}
				loop = false;
				break;
			
			
			default:
				System.out.println("\nPlease enter a number between 1 and 4\n");
				break;
			}
			//printWriter.close();

		}
		client_socket.close();
	}

	
	
	public static void getPeerFiles(File dir) throws Exception{
		
		File[] files = dir.listFiles();
		System.out.println("# of files registered: " + files.length);
		
			for (File file : files) {
				fileNames.add(file.getName());
				System.out.println("file : "+file.getName());
			}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread thread=Thread.currentThread();
		if(thread.getName().equals("server")){
			
			ServerSocket server_sock = null;
		    Socket connectionSock = null;
		    try {
		    	
				server_sock = new ServerSocket(peer_port);
				while(true) {
				System.out.println("Waiting...");
				connectionSock = server_sock.accept();
				ObjectInputStream objectInput1 = new ObjectInputStream(connectionSock.getInputStream());
				Object object = objectInput1.readObject();
				//List<Integer> portList = new ArrayList<Integer>();
				String fileName =  (String) object;
				System.out.println("Below Peer have "+fileName +" in their shared directory : ");
				FileInputStream fis = null;
			    BufferedInputStream bis = null;
			    OutputStream os = null;
			    try {
			    	  System.out.println("Accepted connection : " + connectionSock);
			          // send file
			          File myFile = new File (dirname+"/"+fileName);
			          byte [] mybytearray  = new byte [(int)myFile.length()];
			          fis = new FileInputStream(myFile);
			          bis = new BufferedInputStream(fis);
			          bis.read(mybytearray,0,mybytearray.length);
			          os = connectionSock.getOutputStream();
			          System.out.println("Sending " + dirname+"/"+fileName + "(" + mybytearray.length + " bytes)");
			          os.write(mybytearray,0,mybytearray.length);
			          os.flush();
			          System.out.println("Done.");
			        }
			        finally {
			          if (bis != null) bis.close();
			          if (os != null) os.close();
			          if (connectionSock!=null) connectionSock.close();
			        }
			      } 
		    }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			
		}
		
		else{
			
			System.out.println("in client");
			
		}
		
	}

}
