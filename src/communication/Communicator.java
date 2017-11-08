package communication;

import configs.Configs;
import helper.MessageHandler;
import helper.TCPException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
public class Communicator implements Runnable,TCPServer{
    private String SERVER_IP;
    private String CLIENT_IP;
    private int SERVER_PORT;
    private int CLIENT_PORT;
    private boolean shouldKill = false;
    private DatagramSocket socket_re = null;
    private Endpoint ep = null;
    private boolean IS_UDP = true;
    private final Configs configs;
    private int TIME_OUT_RECEIVE;    
    private MessageHandler messageHandler;
    
    private Communicator(){
        configs = new Configs(); 
        configureVariables();
    }
    
    public final void configureVariables(){
        TIME_OUT_RECEIVE = configs.getReceiverTimeout();
        SERVER_IP = configs.getServerIP();
        CLIENT_IP = configs.getClientIP();
        CLIENT_PORT = configs.getClientPort();        
        SERVER_PORT = configs.getServerPort(); 
        IS_UDP = configs.isUDP();
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
        this.SERVER_IP = serverIP;
    }

    /**
     * @param clientIP the clientIP to set
     */
    public void setClientIP(String clientIP) {
        this.CLIENT_IP = clientIP;
    }

    /**
     * @param serverPort the serverPort to set
     */
    public void setServerPort(int serverPort) {
        this.SERVER_PORT = serverPort;
    }

    /**
     * @param clientPort the clientPort to set
     */
    public void setClientPort(int clientPort) {
        this.CLIENT_PORT = clientPort;
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
        
        if(IS_UDP){
            while(!shouldKill){ 
                try{
                    String responce = receive(CLIENT_PORT, false);
                    System.out.println("udp_receiver: "+responce);
                    if(responce != null){                               
                        messageHandler.putMessage(responce);
                    } 
                }catch(IOException ex){}

            }
        }else{
            try{
                startTCPServer();
            }catch(IOException ex){}
        }
    }   
    
    public void stopReceiving(){
        if(IS_UDP){
            if(socket_re!=null)
                socket_re.close();
            this.shouldKill = true;
        }else{
            if(ep!=null)
                ep.stop();
        }
    }
    
    public void sendToPeer(String message, String peerIp, int peerPort)
            throws IOException, TCPException{
        if(IS_UDP)
            send(message,peerIp,peerPort,-1);
        else
            sendTCP(message, peerIp, peerPort);
    }
    
    public String sendInitalJoin(String message, String peerIp, int peerPort)
            throws IOException, TCPException{
        String responce;
        if(IS_UDP){
            send(message,peerIp,peerPort,-1);
            responce = receiveWithTimeout();
            return responce;
        }else{
             responce = sendAndReceiveTCP(message, peerIp, peerPort);
             return responce;
        }
    }
    
    public String receiveWithTimeout()throws IOException{
        return receive(CLIENT_PORT, true);        
    }
//    
//    public String receiveFromNeighbour()throws IOException{
//        return receive(clientPort, false);        
//    }
    
    public void sendToBS(String str)throws IOException{        
        send(str,SERVER_IP,SERVER_PORT,CLIENT_PORT);        
    }
    
      
    public String receiveFromBS()throws IOException{
       return receive(CLIENT_PORT, true);
    }
    
    
    /* 
     * To send available port, use port = -1
     * otherwise port has to be specifed
     */
    private String receive(int port,boolean isTimeout) throws IOException{
        String str = null;
        socket_re = null;  
        try{
        if(port==-1)
            socket_re = new DatagramSocket();
        else
            socket_re = new DatagramSocket(port); 

        if(isTimeout)
            socket_re.setSoTimeout(TIME_OUT_RECEIVE);

        byte[] buf = new byte[65536];  
        DatagramPacket incoming = new DatagramPacket(buf, buf.length);  
        socket_re.receive(incoming);  
        str = new String(incoming.getData(), 0, 
                incoming.getLength()); 
        }catch(IOException ex){
            throw ex;
        }finally{
            if(socket_re!=null)
                socket_re.close();            
        }
        return str;

    }
    
    private void send(String message,String ip,int port,int send_port)
            throws IOException{
                
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
        }catch(IOException ex){
            throw ex;
        }finally{
            if(socket!=null)
                socket.close();
        }
                               
             
    }
    
    public void sendTCP(String message,String ip,int port) 
            throws IOException,TCPException{
        
        String url_string = "http://"+ip+":"+port+"/ds";
//        URL url = new URL("http://localhost:9999/ws/hello?wsdl");
        URL url = new URL(url_string);
        System.out.println("1");
        //1st argument service URI, refer to wsdl document above
	//2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://communication/",
                "CommunicatorService");
        
        try{
            Service service = Service.create(url, qname);            
            TCPServer serverResponce = service.getPort(TCPServer.class);  
            serverResponce.handleRequest(message);
        }catch(RuntimeException ex){
            throw new TCPException(ex.getMessage());
        }
        
    }
    
    public String sendAndReceiveTCP(String message,String ip,int port) 
            throws IOException,TCPException{
        
        String url_string = "http://"+ip+":"+port+"/ds";
//        URL url = new URL("http://localhost:9999/ws/hello?wsdl");
        URL url = new URL(url_string);
        System.out.println("1");
        //1st argument service URI, refer to wsdl document above
	//2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://communication/",
                "CommunicatorService");

        try{
            Service service = Service.create(url, qname);            
            TCPServer serverResponce = service.getPort(TCPServer.class);            
            return serverResponce.handleInitialJoinRequest(message);
        }catch(RuntimeException ex){
            throw new TCPException(ex.getMessage());
        }
    }
    
    public void startTCPServer() throws IOException,RuntimeException{
        ep = Endpoint.create(this);
        String url_string = "http://"+CLIENT_IP+":"+CLIENT_PORT+"/ds";
        ep.publish(url_string);
        System.out.println("server started");
        //ep.stop();
    }
    
}
