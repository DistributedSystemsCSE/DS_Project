/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_project;

import Configs.Configs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Hareen Udayanath
 */
public class Communicator {
    private String serverIP;
    private int serverPort;
    private Configs configs;
    
    private Communicator(){
        configs = new Configs();
        serverIP = configs.getServerIP();
        serverPort = configs.getServerPort();        
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
    
    public void sendForBS(String message){
        try{
            
            DatagramSocket ds = new DatagramSocket();  
            String str = "0029 REG 10.10.0.215 57000 vwb";  
            InetAddress ip = InetAddress.getByName("10.10.0.215");  
            //InetAddress ip = InetAddress.getByName("127.0.0.1");  
            DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, 55555);  
            ds.send(dp);  
            ds.close();  
        
            
        }catch(IOException ioe){
            ioe.printStackTrace();
	}
        
    }
    
     
    
}
