package ds_project;

import configs.Configs;
import helper.Message;
import helper.MessageType;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 *
 * @author Hareen Udayanath
 */
public class DS_Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Configs configs = new Configs();
//        System.out.println(configs.getServerIP());
//        System.out.println(configs.getServerPort());
//        System.out.println(configs.getClientIP());
//        System.out.println(configs.getClientPort());
        
        //Communicator.getInstance().addNode(Node.getInstance());
        
//        Thread receiver = new Thread(Communicator.getInstance());
//        receiver.start();
        Node node = Node.getInstance();
        if(args.length==5){
            node.getCommunicator().setServerIP(args[0]);
            node.getCommunicator().setServerPort(Integer.valueOf(args[1]));
            node.getCommunicator().setClientIP(args[2]);
            node.getCommunicator().setClientPort(Integer.valueOf(args[3]));
            node.setIp(args[2]);
            node.setPort(Integer.valueOf(args[3]));
            node.setName(args[4]);            
        }
        node.register();
        
        while(true){
            
            node.showNeighbours();
            try {
                Thread.sleep(1000);                    
            } catch (InterruptedException ex) {

            }
        }
        
        
        
    }
    
}
