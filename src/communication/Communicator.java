package communication;

import configs.Configs;
import helper.MessageHandler;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

/**
 *
 * @author Hareen Udayanath
 */
@WebService(endpointInterface = "communication.RPCServer")
public class Communicator implements Runnable,RPCServer{
    private String serverIP;
    private String clientIP;
    private int serverPort;
    private int clientPort;
    private boolean shouldKill = false;
    private DatagramSocket socket_re = null;
    private Endpoint ep = null;
    private boolean isUDP = true;
    private final Configs configs;
    private int timeout;    
    private MessageHandler messageHandler;
    
    private Communicator(){
        configs = new Configs(); 
        configureVariables();
    }
    
    public void configureVariables(){
        timeout = configs.getReceiverTimeout();
        serverIP = configs.getServerIP();
        clientIP = configs.getClientIP();
        clientPort = configs.getClientPort();        
        serverPort = configs.getServerPort(); 
        isUDP = configs.isUDP();
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

    /**
     *  
     * Receive the RPC message
     * @param msg 
     */
    @Override
    public void handleRequest(String msg) {
        String responce = msg;
        System.out.println("rpc_receiver: "+responce);
        if(responce != null){                               
            messageHandler.putMessage(responce);
        } 
    }

    @Override
    public String handleInitialJoinRequest(String message) {
        return messageHandler.handelInitialJoinToDS(message);
    }
    
    private static class InstanceHolder{
        static Communicator instance = new Communicator();
    }
    
    public static Communicator getInstance(){
        return InstanceHolder.instance;
    }

    /**
     * Continuously receive UDP massages or
     * start TCP server
     */
    @Override
    public void run() {
        
        if(isUDP){
            while(!shouldKill){ 
                try{
                    String responce = receive(clientPort, false);
                    System.out.println("udp_receiver: "+responce);
                    if(responce != null){                               
                        messageHandler.putMessage(responce);
                    } 
                }catch(IOException ex){}

            }
        }else{
            startRPCServer();
        }
    }   
    
    public void stopReceiving(){
        if(isUDP){
            if(socket_re!=null)
                socket_re.close();
            this.shouldKill = true;
        }else{
            if(ep!=null)
                ep.stop();
        }
    }
    
    public void sendToPeer(String message, String peerIp, int peerPort)
            throws IOException{
        if(isUDP)
            send(message,peerIp,peerPort,-1);
        else
            sendRPC(message, peerIp, peerPort);
    }
    
    public String sendInitalJoin(String message, String peerIp, int peerPort)
            throws IOException{
        String responce;
        if(isUDP){
            send(message,peerIp,peerPort,-1);
            responce = receiveWithTimeout();
            return responce;
        }else{
             responce = sendAndReceiveRPC(message, peerIp, peerPort);
             return responce;
        }
    }
    
    public String receiveWithTimeout()throws IOException{
        return receive(clientPort, true);        
    }
//    
//    public String receiveFromNeighbour()throws IOException{
//        return receive(clientPort, false);        
//    }
    
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
    private String receive(int port,boolean isTimeout) throws IOException{
        
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
    
    private void send(String message,String ip,int port,int send_port)
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
    
    public void sendRPC(String message,String ip,int port) 
            throws MalformedURLException{
        
        String url_string = "http://"+ip+":"+port+"/ds";
//        URL url = new URL("http://localhost:9999/ws/hello?wsdl");
        URL url = new URL(url_string);
        System.out.println("1");
        //1st argument service URI, refer to wsdl document above
	//2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://communication/",
                "CommunicatorService");

        Service service = Service.create(url, qname);            
        RPCServer serverResponce = service.getPort(RPCServer.class);            
        serverResponce.handleRequest(message);
    }
    
    public String sendAndReceiveRPC(String message,String ip,int port) 
            throws MalformedURLException{
        
        String url_string = "http://"+ip+":"+port+"/ds";
//        URL url = new URL("http://localhost:9999/ws/hello?wsdl");
        URL url = new URL(url_string);
        System.out.println("1");
        //1st argument service URI, refer to wsdl document above
	//2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://communication/",
                "CommunicatorService");

        Service service = Service.create(url, qname);            
        RPCServer serverResponce = service.getPort(RPCServer.class);            
        return serverResponce.handleInitialJoinRequest(message);
    }
    
    public void startRPCServer(){
        ep = Endpoint.create(this);
        String url_string = "http://"+clientIP+":"+clientPort+"/ds";
        ep.publish(url_string);
        System.out.println("sadasdas");
        //ep.stop();
    }
    
}
