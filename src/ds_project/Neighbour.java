package ds_project;

import helper.BsRegisterException;
import helper.Message;
import helper.MessageType;
import helper.MessageHandler;
import java.util.Objects;

/**
 *
 * @author Buddhi
 */
public class Neighbour extends Host{
   
    private boolean isAlive = false;
    private Communicator com = null;

    public Neighbour(String ip, int port) {
        super(ip,port); 
        com = Communicator.getInstance();
    }
    
    public boolean sendJoinAsFirstNeighbour(String ip_sender,int port_sender) throws BsRegisterException{
        String message = (new Message(MessageType.JOIN, ip_sender, port_sender))
                .getMessage();
        com.send(message, ip, port,-1);
        String responce = com.receiveWithTimeout();
        System.out.println("A: "+responce);
        if(responce==null)
            return false;
        return MessageHandler.getInstance()
                .decodeInitialJoinResponse(responce);
    }
    
    public void sendJoin(String ip_sender,int port_sender){
        String message = (new Message(MessageType.JOIN, ip_sender, port_sender))
                .getMessage();
        com.send(message, ip, port,-1);
    }
    
    public void sendMessage(String message){        
        com.send(message, ip, port,-1);
    }
    
    public void sendSearchRequest(String query,String ip_sender,int port_sender){
        String message = (new Message(MessageType.SER, ip_sender,
                port_sender, ip_sender, port_sender, query, 0)).getMessage();
        com.sendToPeer(message, message, port);
    }
    
    public void sendIsAlive(){
        //TODO        
    }
    
    public void setCommunicator(Communicator com){
        this.com = com;
    }

    public void sendNeighbourRequest(int size,String ip_sernder,int port_sender){
        
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
    
    
}
