package ds_project;

import configs.Configs;
import helper.MessageHandler;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Hareen Udayanath
 */
public class Communicator implements Runnable{
    private String serverIP;
    private String clientIP;
    private int serverPort;
    private int clientPort;
    private boolean shouldKill = false;
    private DatagramSocket socket_re = null;
    private final Configs configs;
    private int timeout;    
    private MessageHandler messageHandler;
    
    private Communicator(){
        configs = new Configs();        
    }
    
    public void configureVariables(){
        timeout = configs.getReceiverTimeout();
        serverIP = configs.getServerIP();
        clientIP = configs.getClientIP();
        clientPort = configs.getClientPort();        
        serverPort = configs.getServerPort();     
    }
    
    public void setMessageHandler(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    /**
     * @return the shouldKill
     */
    public boolean isShouldKill() {
        return shouldKill;
    }

    /**
     * @param shouldKill the shouldKill to set
     */
    public void setShouldKill(boolean shouldKill) {
        this.shouldKill = shouldKill;
    }

    /**
     * @param serverIP the serverIP to set
     */
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    /**
     * @param clientIP the clientIP to set
     */
    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    /**
     * @param serverPort the serverPort to set
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @param clientPort the clientPort to set
     */
    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }
    
    private static class InstanceHolder{
        static Communicator instance = new Communicator();
    }
    
    public static Communicator getInstance(){
        return InstanceHolder.instance;
    }

    @Override
    public void run() {
        
        while(!shouldKill){ 
            try{
                String responce = receive(clientPort, false);
                System.out.println("receiver: "+responce);
                if(responce != null){                               
                    messageHandler.putMessage(responce);
                } 
            }catch(IOException ex){}
                       
        }
    }   
    
    public void stopReceiving(){
        if(socket_re!=null)
            socket_re.close();
        this.shouldKill = true;
    }
    
    public void sendToPeer(String message, String peerIp, int peerPort)
            throws IOException{
        send(message,peerIp,peerPort,-1);
    }
    
    public String receiveWithTimeout()throws IOException{
        return receive(clientPort, true);        
    }
    
    public String receiveFromBeighbour()throws IOException{
        return receive(clientPort, false);        
    }
    
    public void sendToBS(String str)throws IOException{        
        send(str,serverIP,serverPort,clientPort);        
    }
    
      
    public String receiveFromBS()throws IOException{
       return receive(clientPort, true);
    }
    
    
    /* 
     * To send available port, use port = -1
     * otherwise port has to be specifed
     */
    public String receive(int port,boolean isTimeout) throws IOException{
        
        socket_re = null;        
        if(port==-1)
            socket_re = new DatagramSocket();
        else
            socket_re = new DatagramSocket(port); 

        if(isTimeout)
            socket_re.setSoTimeout(timeout);

        byte[] buf = new byte[65536];  
        DatagramPacket incoming = new DatagramPacket(buf, buf.length);  
        socket_re.receive(incoming);  
        String str = new String(incoming.getData(), 0, 
                incoming.getLength()); 
        socket_re.close();
        return str;

    }
    
    public void send(String message,String ip,int port,int send_port)
            throws IOException{
        
        DatagramSocket socket = null;

        if(send_port==-1)
            socket = new DatagramSocket();
        else
            socket = new DatagramSocket(send_port);

        InetAddress IPAddress = InetAddress.getByName(ip);            
        byte[] toSend  = message.getBytes(); 		  
        DatagramPacket packet =new DatagramPacket(toSend, toSend.length, 
                IPAddress, port); 

        socket.send(packet);
        socket.close();                       
             
    }
    
    
}
