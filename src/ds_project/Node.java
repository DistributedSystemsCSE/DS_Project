package ds_project;

import Configs.Configs;
import help.Message;
import help.MessageType;
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
    
    private Node(){
        configs = new Configs();
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
    
    public void register(){
        String str = (new Message(MessageType.REG, ip,port, name))
                .getMessage();  
        com.sendForBS(str);
    }
    
    public void unregister(){
        String str = (new Message(MessageType.UNREG, ip,port, name))
                .getMessage();  
        com.sendForBS(str);
    }
}
