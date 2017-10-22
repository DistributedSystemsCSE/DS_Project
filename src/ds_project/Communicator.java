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
import java.net.SocketTimeoutException;
import java.util.Observable;

/**
 *
 * @author Hareen Udayanath
 */
public class Communicator extends Observable implements Runnable{
    private final String serverIP;
    private final int serverPort;
    private Configs configs;
    private int timeout;
    private String message;
    
    private Communicator(){
        configs = new Configs();
        timeout = configs.getTimeout();
        serverIP = configs.getServerIP();
        serverPort = configs.getServerPort();        
    }

    @Override
    public void run() {
        while(true){
            DatagramSocket socket = null;
            try{
                
                socket = new DatagramSocket(configs.getClientPort());  
                byte[] buf = new byte[65536];  
                DatagramPacket incoming = new DatagramPacket(buf, buf.length);  
                socket.receive(incoming);  
                String str = new String(incoming.getData(), 0, 
                        incoming.getLength());                  
                
                
                this.message = str;                
                setChanged();
                notifyObservers();
                
            }catch(IOException ex){
                
            }finally{
                socket.close();
            }
        }
    }
    
    private static class InstanceHolder{
        static Communicator instance = new Communicator();
    }
    
    public static Communicator getInstance(){
        return InstanceHolder.instance;
    }
    
    public String sendToNeighbour(String message, String peerIp, int peerPort){
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(); 
            InetAddress IPAddress = InetAddress.getByName(peerIp); 
            
            byte[] toSend  = message.getBytes(); 
		  
            DatagramPacket packet =new DatagramPacket(toSend, toSend.length, 
                    IPAddress, peerPort); 
            
            socket.send(packet);
            
        }catch(IOException ioe){
            ioe.printStackTrace();
	}finally{
            socket.close();
        }
        return null;
    }
    
    public String receiveWithTimeout(){
        DatagramSocket socket = null;
        try{

            socket = new DatagramSocket(configs.getClientPort());  
            socket.setSoTimeout(timeout);
            byte[] buf = new byte[65536];  
            DatagramPacket incoming = new DatagramPacket(buf, buf.length);  
            socket.receive(incoming);  
            String str = new String(incoming.getData(), 0, 
                    incoming.getLength());               
            return str;
        } catch (SocketTimeoutException e) {
            return null;
        }catch(IOException ex){
            return null;
        }finally{
            socket.close();
        }
    }
    
//    public String receiveFromBeighbour(){
//        DatagramSocket socket = null;
//        try{
//
//            socket = new DatagramSocket(configs.getClientPort());  
//            byte[] buf = new byte[65536];  
//            DatagramPacket incoming = new DatagramPacket(buf, buf.length);  
//            socket.receive(incoming);  
//            String str = new String(incoming.getData(), 0, 
//                    incoming.getLength());               
//            return str;
//
//        }catch(IOException ex){
//            return null;
//        }finally{
//            socket.close();
//        }
//    }
    
    public void sendForBS(String str){
        
        DatagramSocket socket = null;
        try{
            
            socket = new DatagramSocket(configs.getClientPort());           
            InetAddress ip = InetAddress.getByName(configs.getServerIP());             
            DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), 
                    ip, configs.getServerPort());  
            socket.send(dp);       
            
        }catch(IOException ex){
            ex.printStackTrace();
	}finally{
            socket.close(); 
        }
        
    }
    
    public String receiveFromBS(){
        DatagramSocket socket = null;
            try{
                
                socket = new DatagramSocket(configs.getClientPort());  
                byte[] buf = new byte[65536];  
                DatagramPacket incoming = new DatagramPacket(buf, buf.length);  
                socket.receive(incoming);  
                String str = new String(incoming.getData(), 0, 
                        incoming.getLength());               
                return str;
                
            }catch(IOException ex){
                return null;
            }finally{
                socket.close();
            }
    }
    
     
    public void addNode(Node node){    
        this.addObserver(node);
    }
    
    public String getMessage(){
        return this.message;
    }
    
}
