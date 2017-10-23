package ds_project;

import Configs.Configs;
import helper.BsRegisterException;
import helper.Message;
import helper.MessageType;
import helper.MessageHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;



/**
 *
 * @author Hareen Udayanath
 */
public class Node extends Host{
    
    private final String name;
    private final Configs configs;
    private final int timeout;
    private final int max_number_of_neighbours;
    private final Random randomGenerator;
    private Communicator com = null;
    private MessageHandler mh = null;
    private final List<Neighbour> neighbours_list;
    
    private Node(){
        
        configs = new Configs();
        neighbours_list = new ArrayList<>();
        name = configs.getClientName();
        
        timeout = configs.getReceiverTimeout();
        max_number_of_neighbours = configs.getMaxNumberOfNeighbours();
        randomGenerator = new Random();
        
        super.setIp(configs.getClientIP());
        super.setPort(configs.getClientPort());
        
        mh = MessageHandler.getInstance();
        com = Communicator.getInstance();
    }
    
    private static class InstanceHolder{
        static Node instance = new Node();
    }
    
    public static Node getInstance(){
        return InstanceHolder.instance;
    }
        
       
    public boolean register(){
        Neighbour[] neighbours = null;
        String str = (new Message(MessageType.REG, ip,port, name))
                .getMessage();  
        com.sendToBS(str);
        String responce = com.receiveFromBS();
        try{
            neighbours = MessageHandler.getInstance()
                .decodeRegisterResponse(responce);
        }catch(BsRegisterException ex){            
            System.err.println(ex.getMessage());
            return false;
        }
        //System.out.println("Size: "+neighbours.length);
        
                
        if(neighbours.length==0){
            startReceiving();
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
            startReceiving();
            return true;
        }else if(neighbours.length==2){
            startReceiving();
            if(!connectToTheNetwork(neighbours[0],neighbours[1])){
                unregister();
                stopReceiving();
                return false;
            }	
            return true;
        }else if(neighbours.length==3){
            startReceiving();
            int index_1 = randomGenerator.nextInt(neighbours.length);
            int index_2 = randomGenerator.nextInt(neighbours.length);
            //what if first two fails but third is possible		
            if(!connectToTheNetwork(neighbours[index_1],neighbours[index_2])){
                unregister();
                stopReceiving();
                return false;
            }	
            return true;
        }
        
        return false;
    }
    
    private void startReceiving(){
        Thread handler = new Thread(mh);
        Thread receiver = new Thread(com);
        com.setShouldKill(false);
        mh.setShouldKill(false);
        handler.start();
        receiver.start();
    }
    
    private void stopReceiving(){
        com.setShouldKill(true);
        mh.setShouldKill(true);
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
        
        Thread neighbourSetter = new Thread(new NeighbourSetter());
        neighbourSetter.start();
        return true;
    }
    
        
    public Neighbour getRandomNeighbour(){
        Neighbour neighbour = null;
        synchronized(neighbours_list){
            int index = randomGenerator.nextInt(neighbours_list.size());
            neighbour = neighbours_list.get(index);
        }
        return neighbour;
    }
    
    public Neighbour[] getRandomNeighbours(int size) {
        int list_size = neighbours_list.size();
        if(size<list_size){
            Neighbour[] neighbours = new Neighbour[size];
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i=1; i<list_size; i++) {
                list.add(i);
            }
            Collections.shuffle(list);
            for (int i=0; i<size; i++) {
                neighbours[i] = neighbours_list.get(list.get(i));
            }
            return neighbours;
        }else{
            return neighbours_list.toArray(new Neighbour[0]);
        }
    }
    
    public void sendToNeighboursExcept(String message,Neighbour neighbour){
        
        //TODO
        //test the method....
        
        List<Neighbour> neighbours_except = neighbours_list.stream()               
                .filter(neighbour_ -> !neighbour.equals(neighbour_))    
                .collect(Collectors.toList()); 
        
        neighbours_except.stream().forEach((neighbour_) -> {
                    neighbour_.sendMessage(message);                    
                });
        
    }
    
    public boolean unregister(){
        String str = (new Message(MessageType.UNREG, ip,port, name))
                .getMessage();  
        com.sendToBS(str);
        String responce = com.receiveFromBS();
        
        try{
            boolean isUnregistered = MessageHandler.getInstance()
                    .decodeUnregisterResponse(responce);
            return isUnregistered;
        }catch(BsRegisterException ex){
            System.err.println(ex.getMessage());
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
    
    private class NeighbourSetter implements Runnable{

        private final int timeout_neighbour;
        
        public NeighbourSetter(){
            timeout_neighbour = configs.getNeighbourSetterTimeout();
        }
        
        @Override
        public void run() {
            while(true){
                int size;

                synchronized(neighbours_list){
                    size = max_number_of_neighbours - neighbours_list.size();
                }
                if(size<=0){
                    try {
                        Thread.sleep(timeout_neighbour);
                        continue;
                    } catch (InterruptedException ex) {
                        
                    }
                }
                Neighbour[] new_neighbours =  getRandomNeighbour()
                        .getNeighbours(size);
                for(Neighbour neighbour:new_neighbours){
                    neighbour.sendJoin();
                }
                
                try {
                    Thread.sleep(timeout_neighbour);                    
                } catch (InterruptedException ex) {
                    
                }
            }
        }
        
    }
    
    private class NeighbourChecker implements Runnable{

        private final int timeout_neighbour_checker;
        
        public NeighbourChecker(){
            timeout_neighbour_checker = configs.getNeighbourSetterTimeout();
        } 
        
        @Override
        public void run() {
            synchronized(neighbours_list){
                neighbours_list.stream().forEach((neighbour) -> {
                    neighbour.setAlive(false);
                    neighbour.sendIsAlive();
                });
            }
            
            try {
                Thread.sleep(timeout_neighbour_checker);
            } catch (InterruptedException ex) {

            }
            
            synchronized(neighbours_list){
                neighbours_list.stream().forEach((neighbour) -> {
                    if(!neighbour.isAlive()){
                        neighbours_list.remove(neighbour);
                    }
                });
            }        
        }
    
    }
}
