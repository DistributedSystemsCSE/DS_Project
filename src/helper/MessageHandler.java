package helper;

import ds_project.Neighbour;
import ds_project.Node;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Buddhi
 */
public class MessageHandler {

    private Node node;
    public static List<String> message_list;

    private MessageHandler() {
    }

    public void setNode(Node node) {
        this.node = node;
    }
    
    private static class InstanceHolder{
        static MessageHandler instance = new MessageHandler();
    }
    
    public static MessageHandler getInstance(){
        return InstanceHolder.instance;
    }

    /**
     * @param message
     *
     *
     * length JOINOK value 
     * length LEAVE IP_address port_no 
     * length LEAVEOK value
     * length SER IP port file_name hops 
     * length SEROK no_files IP port hops filename1 filename2 length ERROR
     */
    public void handle(String message) {

        try {
            String[] mes = message.split(" ");

            switch (mes[1]) {
                case "SER":
                    String[] newMes = null;
                    Arrays.copyOfRange(newMes, 0, (mes.length - 2));
                    String uniqMes = String.join("", newMes);
                    if(!addMessage(uniqMes)){
                        //update routing table
                        
                        
                        //check for files and send responce
                        
                        
                        //broadcast mesagge
                        
                        
                    }
                    break;
                case "JOIN":
                    if (!addMessage(message)) {
//                        if (addNeighbours(new Neighbour(mes[2], mes[3]))) {
//
//                        }
                    }
                    break;
                case "JOINOK":
                    addMessage(message);
                    break;
                case "LEAVE":
                    break;
                case "LEAVEOK":
                    break;

                case "SEROK":
                    break;
                case "ERROR":
                    break;

                default:
                    System.err.println("Error: " + message);
                    break;
            }

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public synchronized boolean addMessage(String message) {
        if (message_list.stream().anyMatch((str) -> (str.equals(message)))) {
            return true;
        } else {
            message_list.add(message);
            return false;
        }
    }

    /**
     * @param message
     * @throws helper.BsRegisterException
     * 
     * Decode length REGOK no_nodes IP_1 port_1 IP_2 port_2
     */
    public Neighbour[] decodeRegisterResponse(String message) throws BsRegisterException {
        
        String[] mes = message.split(" ");
        int no_nodes = Integer.parseInt(mes[2]);
        if(no_nodes<9996){
            Neighbour[] neighbour = new Neighbour[no_nodes];
            for (int n = 0; n < no_nodes; n++) {
                neighbour[n] = new Neighbour(mes[(n * 2) + 3], Integer.parseInt(mes[(n * 2) + 4]));
            }
            return neighbour;
        }
        else{
            switch(no_nodes){
                case 9996:
                    throw new BsRegisterException("failed, can’t register. BS full");
                case 9997:
                    throw new BsRegisterException("failed, registered to another user, try a different IP and port");
                case 9998:
                    throw new BsRegisterException("failed, already registered to you, unregister first");
                case 9999:
                    throw new BsRegisterException("failed, there is some error in the command");
            }
        }
        return null;
    }    
    
    /**
     * @param message
     * @throws BsRegisterException
     * 
     * length JOINOK value
     */
    public boolean decodeInitialJoinResponse(String message) throws BsRegisterException {
        try {
            String[] mes = message.split(" ");
            return mes[1].equals("JOINOK") && mes[1].equals("0");
        } catch (Exception e) {
            throw new BsRegisterException("Error while adding new node to routing table");
        }
    }
    
    /**
     * @param message
     * @throws BsRegisterException
     * 
     * length UNROK value
     */
    public boolean decodeUnregisterResponse(String message) throws BsRegisterException {
        try {
            String[] mes = message.split(" ");
            return mes[1].equals("UNROK") && mes[1].equals("0");
        } catch (Exception e) {
            throw new BsRegisterException("Error while unregistering. IP and port may not be in the registry or command is incorrect");
        }
    }
}
