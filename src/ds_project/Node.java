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
    private String ip;
    private int port;
    private Configs configs;
    private Communicator com = null;
    
    private Node(){
        configs = new Configs();
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
        System.out.println(((Communicator)obs).getMessage());      
    }
    
    public void setCommunicator(Communicator com){
        this.com = com;
    }
    
    public void register(){
        String str = (new Message(MessageType.REG, configs.getClientIP(), 
                configs.getClientPort(), configs.getClientName())).getMessage();  
        com.sendForBS(str);
    }
    
    public void unregister(){
        String str = (new Message(MessageType.UNREG, configs.getClientIP(), 
                configs.getClientPort(), configs.getClientName())).getMessage();  
        com.sendForBS(str);
    }
}
