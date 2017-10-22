/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_project;

import Configs.Configs;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;

/**
 *
 * @author Hareen Udayanath
 */
public class Communicator extends Observable implements Runnable{
    private final String serverIP;
    private final int serverPort;
    private Configs configs;
    
    private String message;
    
    private Communicator(){
        configs = new Configs();
        serverIP = configs.getServerIP();
        serverPort = configs.getServerPort();        
    }

    @Override
    public void run() {
        while(true){
            try{
                DatagramSocket ds = new DatagramSocket(configs.getClientPort());  
                byte[] buf = new byte[65536];  
                DatagramPacket incoming = new DatagramPacket(buf, buf.length);  
                ds.receive(incoming);  
                String str = new String(incoming.getData(), 0, incoming.getLength());  
                //System.out.println(str);  
                ds.close();  
                
                this.message = str;                
                setChanged();
                notifyObservers();
                
            }catch(IOException ex){
                
            }
        }
    }
    
    private static class InstanceHolder{
        static Communicator instance = new Communicator();
    }
    
    public static Communicator getInstance(){
        return InstanceHolder.instance;
    }
    
    public String send(String message, String peerIp, int peerPort){
        try{
            DatagramSocket clientSocket = new DatagramSocket(); 
            InetAddress IPAddress = InetAddress.getByName(peerIp); 
            
            byte[] toSend  = message.getBytes(); 
		  
            DatagramPacket packet =new DatagramPacket(toSend, toSend.length, IPAddress, peerPort); 
            System.out.println("sending message:"+message+"\nfrom-"+configs.getClientIP()+":"+configs.getClientPort()+",to-"+peerIp+":"+peerPort);
            clientSocket.send(packet);
            
            }
        catch(IOException ioe){
            ioe.printStackTrace();
	}
        return null;
    }
    
    public void sendForBS(String str){
        try{
            
            DatagramSocket ds = new DatagramSocket(configs.getClientPort()); 
            
            InetAddress ip = InetAddress.getByName(configs.getServerIP());  
            //InetAddress ip = InetAddress.getByName("127.0.0.1");  
            DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, configs.getServerPort());  
            ds.send(dp);  
            ds.close();  
        
            
        }catch(IOException ex){
            ex.printStackTrace();
	}
        
    }
    
    
     
    public void addNode(Node node){    
        this.addObserver(node);
    }
    
    public String getMessage(){
        return this.message;
    }
    
}
