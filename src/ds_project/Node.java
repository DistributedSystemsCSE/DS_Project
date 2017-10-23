package ds_project;

import Configs.Configs;
import helper.BsRegisterException;
import helper.Message;
import helper.MessageType;
import helper.ResponseHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 *
 * @author Hareen Udayanath
 */
public class Node extends Host implements Observer{
    
    private final String name;
    private final Configs configs;
    private final int timeout;
    private final int max_number_of_neighbours;
    private final Random randomGenerator;
    private Communicator com = null;
    private final List<Neighbour> neighbours_list;
    
    private Node(){
        
        configs = new Configs();
        neighbours_list = new ArrayList<>();
        name = configs.getClientName();
        
        timeout = configs.getTimeout();
        max_number_of_neighbours = configs.getMaxNumberOfNeighbours();
        randomGenerator = new Random();
        
        super.setIp(configs.getClientIP());
        super.setPort(configs.getClientPort());
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
        Neighbour[] neighbours = null;
        String str = (new Message(MessageType.REG, ip,port, name))
                .getMessage();  
        com.sendToBS(str);
        String responce = com.receiveFromBS();
        try{
            neighbours = ResponseHandler.getInstance()
                .decodeRegisterResponse(responce);
        }catch(BsRegisterException ex){            
            System.out.println(ex.getMessage());
            return false;
        }
        //System.out.println("Size: "+neighbours.length);
        
        if(neighbours.length==0){
            return true;
        }else if(neighbours.length==1){
            boolean connected = false;
            try{
                connected = neighbours[0].sendJoinAsFirstNeighbour();
            }catch(BsRegisterException ex){
                System.out.println(ex.getMessage());
                unregister();
                return false;
            }
            if(!connected){
                unregister();
                return false;
            }
            return true;
        }else if(neighbours.length==2){
        
        }else if(neighbours.length==3){
        
        }
        
        return false;
    }
    
    private boolean connectToTheNetwork(Neighbour nb1,Neighbour nb2){
        boolean connected = false;
        nb1.sendJoin();
        nb2.sendJoin();
        long startTime = System.currentTimeMillis(); 
        while(false||(System.currentTimeMillis()-startTime)<timeout){
            if(neighbours_list.size()>0){
                connected = true;
                break;
            }
        }
        if(!connected)
            return false;
        
        return false;
    }
    
    private void setNeighbours(){
        int size = max_number_of_neighbours - neighbours_list.size();
        for(int i = 0 ; i < size ; i++){
            Neighbour neighbour = getRandomNeighbour();
            Neighbour[] new_neighbour =  neighbour.getNeighbours(size);
        }
    }
    
    public Neighbour getRandomNeighbour(){
        int index = randomGenerator.nextInt(neighbours_list.size());
        Neighbour item = neighbours_list.get(index);        
        return item;
    }
    
    public boolean unregister(){
        String str = (new Message(MessageType.UNREG, ip,port, name))
                .getMessage();  
        com.sendToBS(str);
        String responce = com.receiveFromBS();
        
        try{
            boolean isUnregistered = ResponseHandler.getInstance()
                    .decodeUnregisterResponse(responce);
            return isUnregistered;
        }catch(BsRegisterException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        
    }
    
    public boolean addNeighbours(Neighbour neb){
        if (!neighbours_list.stream().noneMatch((tem) -> (tem.equals(neb)))) {
            return false;
        }
        
        neighbours_list.add(neb);
        return true;
    }
    
    
}
