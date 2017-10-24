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
    
    public void sendJoin(){
        String message = (new Message(MessageType.JOIN, ip, port))
                .getMessage();
        com.send(message, ip, port,-1);
    }
    
    public void sendMessage(String message){        
        com.send(message, ip, port,-1);
    }
    
    public void sendIsAlive(){
        //TODO        
    }
    
    public void setCommunicator(Communicator com){
        this.com = com;
    }

    public Neighbour[] getNeighbours(int size){
        //TODO
        // send message to the node and get required neighbours
        Neighbour[] neighbours = null;
        return neighbours;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Neighbour other = (Neighbour) obj;
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (!Objects.equals(this.port, other.port)) {
            return false;
        }
        return true;
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
