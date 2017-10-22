package ds_project;

import help.Message;
import help.MessageType;
import help.ResponseHandler;
import java.util.Objects;

/**
 *
 * @author Buddhi
 */
public class Neighbour {
    private final String ip;
    private final int port;
    private Communicator com = null;

    public Neighbour(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
    
    public boolean sendJoinAsFirstNeighbour(){
        String message = (new Message(MessageType.JOIN, ip, port))
                .getMessage();
        com.sendToNeighbour(message, ip, port);
        String responce = com.receiveWithTimeout();
        return ResponseHandler.getInstance()
                .decodeInitialJoinResponse(responce);
    }
    
    public void setCommunicator(Communicator com){
        this.com = com;
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
    
    
}
