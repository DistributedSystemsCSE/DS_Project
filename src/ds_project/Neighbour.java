package ds_project;

import communication.Communicator;
import configs.Configs;
import helper.BsRegisterException;
import helper.Message;
import helper.MessageType;
import helper.MessageHandler;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author Buddhi
 */
public class Neighbour extends Host{
   
    private boolean isAlive = false;
    private int checked_alive_count = 0;
    private int MAX_CKECKED_ALIVE_COUNT;
    private Communicator com = null;

    public Neighbour(String ip, int port) {
        super(ip,port); 
        com = Communicator.getInstance();        
        MAX_CKECKED_ALIVE_COUNT = (new Configs()).getMaxAliveCount();
    }
    
    public boolean sendJoinAsFirstNeighbour(String ip_sender,int port_sender) 
            throws BsRegisterException,IOException{
        String message = (new Message(MessageType.JOIN, ip_sender, port_sender))
                .getMessage();
        //com.sendToPeer(message, ip, port);
        String responce = com.sendInitalJoin(message, ip, port);
        System.out.println("A: "+responce);
        if(responce==null)
            return false;
        return MessageHandler.getInstance()
                .decodeInitialJoinResponse(responce);
    }
    
    public void sendJoin(String ip_sender,int port_sender) throws IOException{
        String message = (new Message(MessageType.JOIN, ip_sender, port_sender))
                .getMessage();
        com.sendToPeer(message, ip, port);
    }
    
    public void sendMessage(String message)throws IOException{        
        com.sendToPeer(message, ip, port);
    }
    
    public void sendSearchRequest(String query,String ip_sender,int port_sender)
            throws IOException{
        String message = (new Message(MessageType.SER, ip_sender,
                port_sender, ip_sender, port_sender, query, 0)).getMessage();
        com.sendToPeer(message, ip, port);
    }
    
    public void sendLeaveRequest(String ip_sender,int port_sender)
            throws IOException{
        
        String message = (new Message(MessageType.LEAVE, ip_sender, port_sender)).getMessage();
        com.sendToPeer(message, ip, port);
    }
    
    public void sendIsAlive(String ip_sender,int port_sender)throws IOException{
        String message= (new Message(MessageType.ISALIVE, 
                ip_sender, port_sender)).getMessage();
        com.sendToPeer(message, ip, port);
    }
    
    public void setCommunicator(Communicator com){
        this.com = com;
    }

    public void sendNeighbourRequest(int size,String ip_sernder,int port_sender)
            throws IOException{
        
        String message = (new Message(MessageType.NEREQ, 
                                    ip_sernder, port_sender, size)).getMessage();
        com.sendToPeer(message, ip, port);
        
    }
    
    /**
     * @return the isAlive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * @param isAlive the isAlive to set
     */
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * @return the checked_alive_count
     */
    public int getChecked_alive_count() {
        return checked_alive_count;
    }

    public void incrementChecked_alive_count() {
        this.checked_alive_count++;
    }
    
    public void setChecked_alive_countZero() {
        this.checked_alive_count = 0;
    }
    
    
}
