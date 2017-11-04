package ds_project;

import communication.Communicator;
import configs.Configs;
import helper.BsRegisterException;
import helper.FileHandler;
import helper.Message;
import helper.MessageType;
import helper.MessageHandler;
import helper.SearchResultTable;
import java.io.IOException;
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
    
    private String CLIENT_NAME;
    private final Configs configs;
    private int TIME_OUT_FOR_FIRST_TWO;
    private int MAX_NUMBER_OF_NEIGHBORS;
    private final Random randomGenerator;
    private Communicator com = null;
    private MessageHandler mh = null;
    private final List<Neighbour> neighbours_list;
    private final FileHandler fileHandler;
    private final SearchResultTable searchResultTable;
    
    private Node(){
        
        configs = new Configs();
        neighbours_list = new ArrayList<>();       
        randomGenerator = new Random();       
        fileHandler = new FileHandler();
        
        searchResultTable = new SearchResultTable();
        mh = MessageHandler.getInstance();
        mh.setFileHandler(fileHandler);
        mh.setNode(this);
        com = Communicator.getInstance();
        com.setMessageHandler(mh);
        mh.setCommunicator(com);
        
        this.configureVariables();
    }
    
    public void configureVariables(){
        super.setIp(configs.getClientIP());
        super.setPort(configs.getClientPort());
        CLIENT_NAME = configs.getClientName();        
        TIME_OUT_FOR_FIRST_TWO = configs.getTimeoutForFirstTwo();
        MAX_NUMBER_OF_NEIGHBORS = configs.getMaxNumberOfNeighbours();
        com.configureVariables();
    }

    public SearchResultTable getSearchResultTable(){
        return this.searchResultTable;
    }
    
    public Communicator getCommunicator(){
        return com;
    }
    
    public List<String> getFileNames(){
        return fileHandler.getFileNames();
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.CLIENT_NAME = name;
    }
    
    private static class InstanceHolder{
        static Node instance = new Node();
    }
    
    public static Node getInstance(){
        return InstanceHolder.instance;
    }
     
   
       
    public boolean register() throws IOException{
        Neighbour[] neighbours;
        String str = (new Message(MessageType.REG, ip,port, CLIENT_NAME))
                .getMessage();  
        com.sendToBS(str);
        
        String responce = com.receiveFromBS();
       
        if(responce==null){
            unregister();
            return false;
        }
        try{
            neighbours = MessageHandler.getInstance()
                .decodeRegisterResponse(responce);
        }catch(BsRegisterException ex){            
            System.err.println(ex.getMessage());
            return false;
        }
        //System.out.println("Size: "+neighbours.length);
        
                
        if(neighbours.length==0){
            //TODO
            // add neighbour search
            Thread neighbourSetter = new Thread(new NeighbourSetter());
            neighbourSetter.start();
            Thread neighbourChecker = new Thread(new NeighbourChecker());
            neighbourChecker.start();
            startReceiving();
            return true;
        }else if(neighbours.length==1){
            //TODO
            // add neighbour search 
            Thread neighbourSetter = new Thread(new NeighbourSetter());
            neighbourSetter.start();
            Thread neighbourChecker = new Thread(new NeighbourChecker());
            neighbourChecker.start();
            boolean connect = false;
            try{
                connect = neighbours[0].sendJoinAsFirstNeighbour(ip,port);
            }catch(BsRegisterException ex){
                ex.printStackTrace();
                connect = false;
            }
            if(!connect){
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
            System.out.println("3333");
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
        com.stopReceiving();
        mh.setShouldKill(true);
    }
    
    private boolean connectToTheNetwork(Neighbour nb1,Neighbour nb2){
        boolean connected = false;
        try{
        nb1.sendJoin(ip,port);
        nb2.sendJoin(ip,port);
        }catch(IOException ex){
            return false;
        }
        long startTime = System.currentTimeMillis(); 
        while(false||(System.currentTimeMillis()-startTime)<TIME_OUT_FOR_FIRST_TWO){
            if(neighbours_list.size()>0){
                connected = true;
                break;
            }
        }
        if(!connected)
            return false;
        
        Thread neighbourSetter = new Thread(new NeighbourSetter());
        neighbourSetter.start();
        Thread neighbourChecker = new Thread(new NeighbourChecker());
        neighbourChecker.start();
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
        Neighbour[] neighbours;
        if(size<list_size){
            neighbours = new Neighbour[size];
            ArrayList<Integer> list = new ArrayList<>();
            for (int i=1; i<list_size; i++) {
                list.add(i);
            }
            Collections.shuffle(list);
            for (int i=0; i<size; i++) {
                neighbours[i] = neighbours_list.get(list.get(i));
            }            
        }else{
            neighbours = neighbours_list.toArray(new Neighbour[0]);
            System.out.println("Aray_size: "+neighbours.length);
            System.out.println("List_size: "+neighbours_list.size());
        }
        return neighbours;
    }
    
    public String getNeighbourString(){
        String nb = "";
        System.out.println(neighbours_list.size());
        
        nb = neighbours_list.stream().map((neighbour) -> 
                neighbour.toString()+"\n").reduce(nb, String::concat);
        return nb;
    }
    
    public void setNeighbourAlive(String ip,int port){
        neighbours_list.stream().forEach((nb_)->{
            if(nb_.ip.equals(ip)&&nb_.port==port)
                nb_.setAlive(true);
        });
    }
    
    public void incrementNeighbourAlive(String ip,int port){
        neighbours_list.stream().forEach((nb_)->{
            if(nb_.ip.equals(ip)&&nb_.port==port)
                nb_.incrementChecked_alive_count();
        });
    }
    public void showNeighbours(){
        System.out.println(neighbours_list.size());
        neighbours_list.stream()
                .forEach(neighbours_->System.out.println(neighbours_));
    }
    
    public void sendToNeighboursExcept(String message,Neighbour neighbour){
        
        //TODO
        //test the method....
        
        List<Neighbour> neighbours_except = neighbours_list.stream()               
                .filter(neighbour_ -> !neighbour.equals(neighbour_))    
                .collect(Collectors.toList()); 
        
        neighbours_except.stream().forEach((neighbour_) -> {
            try{
                neighbour_.sendMessage(message);      
            }catch(IOException ex){}
        });
        
    }
    
    public boolean unregister() throws IOException{
        stopReceiving();
        String str = (new Message(MessageType.UNREG, ip,port, CLIENT_NAME))
                .getMessage();  
        com.sendToBS(str);
        String responce = com.receiveFromBS();
        
        if(responce==null){
            return false;
        }
        
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
        if(neb.ip.equals(ip)&&neb.port==port)
            return false;
        if(neighbours_list.size()>=MAX_NUMBER_OF_NEIGHBORS)
            return false;
        if (!neighbours_list.stream().noneMatch((tem) -> (tem.equals(neb)))) {
            return false;
        }
        
        neighbours_list.add(neb);
        return true;
    }
    
    public boolean removeNeighbour(Neighbour neb){
        return neighbours_list.remove(neb);
    }
    
    /*
     * Searching......................
     */
    
    public void seachFile(String query){
        
        synchronized(neighbours_list){            
            neighbours_list.stream().forEach((neighbour_) -> {
                try{
                    neighbour_.sendSearchRequest(query,ip,port); 
                }catch(IOException ex){}
           });
        }
    }
    
    public void leave(){        
        synchronized(neighbours_list){            
            neighbours_list.stream().forEach((neighbour_) -> {
                try{
                    neighbour_.sendLeaveRequest(ip,port); 
                }catch(IOException ex){}
           });
        }
    }
    
    private class NeighbourSetter implements Runnable{

        private final int TIME_OUT_NEIGHBOUR_SETTER;
        
        public NeighbourSetter(){
            TIME_OUT_NEIGHBOUR_SETTER = configs.getNeighbourSetterTimeout();
        }
        
        @Override
        public void run() {
            while(true){
                int size;
                int neighbours_size;
                synchronized(neighbours_list){
                    neighbours_size = neighbours_list.size();
                    size = MAX_NUMBER_OF_NEIGHBORS - neighbours_size;
                }
                if(size<=0||neighbours_size==0){
                    try {
                        Thread.sleep(TIME_OUT_NEIGHBOUR_SETTER);
                        continue;
                    } catch (InterruptedException ex) {
                        
                    }
                }
                synchronized(neighbours_list){
                    neighbours_list.stream().forEach((neighbour) -> {
                        try{
                            neighbour.sendNeighbourRequest(size,ip,port);
                        }catch(IOException ex){}
                    });
                }
                try {
                    Thread.sleep(TIME_OUT_NEIGHBOUR_SETTER);                    
                } catch (InterruptedException ex) {
                    
                }
            }
        }
        
    }
    
    private class NeighbourChecker implements Runnable{

        private final int TIME_OUT_NEIGHBOUR_CHECKER;
        private final int MAX_CKECKED_ALIVE_COUNT;
        
        public NeighbourChecker(){
            TIME_OUT_NEIGHBOUR_CHECKER = configs.getNeighbourSetterTimeout();
            MAX_CKECKED_ALIVE_COUNT = configs.getMaxAliveCount();
        } 
        
        @Override
        public void run() {
            synchronized(neighbours_list){
                neighbours_list.stream().forEach((neighbour) -> {
                    neighbour.incrementChecked_alive_count();
                    try{
                        neighbour.sendIsAlive(ip,port);
                    }catch(IOException ex){
                    }
                });
            }
            
            try {
                Thread.sleep(TIME_OUT_NEIGHBOUR_CHECKER);
            } catch (InterruptedException ex) {

            }
            
            synchronized(neighbours_list){
                neighbours_list.stream().forEach((neighbour) -> {
                    if(neighbour.getChecked_alive_count()
                            >MAX_CKECKED_ALIVE_COUNT){
                        neighbours_list.remove(neighbour);
                    }
                });
            }        
        }
    
    }
}
