package ds_project;

import Configs.Configs;
import help.Message;
import help.MessageType;
import help.ResponseHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Hareen Udayanath
 */
public class Node implements Observer{
    private final String ip;
    private final int port;
    private final String name;
    private final Configs configs;
    private Communicator com = null;
    private final List<Neighbour> neighbours_list;
    
    private Node(){
        configs = new Configs();
        neighbours_list = new ArrayList<>();
        name = configs.getClientName();
        ip = configs.getClientIP();
        port  = configs.getClientPort();
    }
    
    private static class InstanceHolder{
        static Node instance = new Node();
    }
    
    public static Node getInstance(){
        return InstanceHolder.instance;
    }
    
    public void update(Observable obs, Object obj){  
        
        //TODO
        if(obs==com){
            System.out.println(com.getMessage());
        }
       
    }
    
    public void setCommunicator(Communicator com){
        this.com = com;
    }
    
    public boolean register(){
        String str = (new Message(MessageType.REG, ip,port, name))
                .getMessage();  
        com.sendToBS(str);
        String responce = com.receiveFromBS();
        
        Neighbour[] neighbours = ResponseHandler.getInstance()
                .decodeRegisterResponse(responce);
        System.out.println("Size: "+neighbours.length);
//        if(neighbours.length==0){
//            return true;
//        }else if(neighbours.length==1){
//            if(!neighbours[0].sendJoinAsFirstNeighbour()){
//                unregister();
//                return false;
//            }
//            return true;
//        }else if(neighbours.length==2){
//        
//        }else if(neighbours.length==3){
//        
//        }
        return false;
    }
    
    public boolean unregister(){
        String str = (new Message(MessageType.UNREG, ip,port, name))
                .getMessage();  
        com.sendToBS(str);
        String responce = com.receiveFromBS();
        return ResponseHandler.getInstance()
                .decodeUnregisterResponse(responce);
    }
    
    public boolean addNeighbours(Neighbour neb){
        if (!neighbours_list.stream().noneMatch((tem) -> (tem.equals(neb)))) {
            return false;
        }
        
        neighbours_list.add(neb);
        return true;
    }
    
    
}
