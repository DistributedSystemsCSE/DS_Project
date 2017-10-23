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
    private final String clientIP;
    private final int serverPort;
    private final int clientPort;
    
    private final Configs configs;
    private final int timeout;
    private String message;
    
    private Communicator(){
        configs = new Configs();
        timeout = configs.getTimeout();
        serverIP = configs.getServerIP();
        clientIP = configs.getClientIP();
        clientPort = configs.getClientPort();        
        serverPort = configs.getServerPort();        
    }
    
    private static class InstanceHolder{
        static Communicator instance = new Communicator();
    }
    
    public static Communicator getInstance(){
        return InstanceHolder.instance;
    }

    @Override
    public void run() {
        while(true){            
            String responce = receive(clientPort, false);
            if(responce != null){
                this.message = responce;                
                setChanged();
                notifyObservers();
            }            
        }
    }   
    
//    public void sendToNeighbour(String message, String peerIp, int peerPort){
//        send(message,peerIp,peerPort);
//    }
    
    public String receiveWithTimeout(){
        return receive(clientPort, true);        
    }
    
    public String receiveFromBeighbour(){
        return receive(clientPort, false);        
    }
    
    public void sendToBS(String str){        
        send(str,serverIP,serverPort,clientPort);        
    }
    
    public String receiveFromBS(){
       return receive(clientPort, false);
    }
    
    /* 
     * To send available port, use port = -1
     * otherwise port has to be specifed
     */
    public String receive(int port,boolean isTimeout){
        DatagramSocket socket = null;
        try{
            if(port==-1)
                socket = new DatagramSocket();
            else
                socket = new DatagramSocket(port); 
            if(isTimeout)
                socket.setSoTimeout(timeout);
            
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
    
    public void send(String message,String ip,int port,int send_port){
        DatagramSocket socket = null;
        try{
            if(send_port==-1)
                socket = new DatagramSocket();
            else
                socket = new DatagramSocket(send_port);
            
            InetAddress IPAddress = InetAddress.getByName(ip);            
            byte[] toSend  = message.getBytes(); 		  
            DatagramPacket packet =new DatagramPacket(toSend, toSend.length, 
                    IPAddress, port); 
            
            socket.send(packet);
            
        }catch(IOException ioe){
            ioe.printStackTrace();
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
