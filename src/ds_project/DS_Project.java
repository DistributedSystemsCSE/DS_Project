package ds_project;

import Configs.Configs;
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
        Communicator.getInstance().sendForBS("0029 REG 10.10.0.215 57000 vwb");
        
    }
    
}
