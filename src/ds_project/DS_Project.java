package ds_project;

import Configs.Configs;
import help.Message;
import help.MessageType;
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
        Configs configs = new Configs();
//        System.out.println(configs.getServerIP());
//        System.out.println(configs.getServerPort());
//        System.out.println(configs.getClientIP());
//        System.out.println(configs.getClientPort());
        Node.getInstance().setCommunicator(Communicator.getInstance());
        Communicator.getInstance().addNode(Node.getInstance());
        
        Thread receiver = new Thread(Communicator.getInstance());
        receiver.start();
        Node.getInstance().register();
        
        
        
    }
    
}
