package helper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author yohan
 */
public class RoutingTable {

    Hashtable<String, RoutingDestination> hashTable = new Hashtable<>();

    public void addToTable(String destination_ip,
            int destination_port,
            String gateway_ip,
            int gateway_port,
            int hopCount) {
        hashTable.put((destination_ip + ":" + destination_port),
                new RoutingDestination(gateway_ip, gateway_port, hopCount));
    }

    public void updateTable(String destination_ip,
            int destination_port,
            String gateway_ip,
            int gateway_port,
            int hopCount) {
        RoutingDestination rd = hashTable.get(destination_ip + ":"
                + destination_port);
        if (rd == null) {
            hashTable.put((destination_ip + ":" + destination_port),
                    new RoutingDestination(gateway_ip, gateway_port, hopCount));
        } else {
            int hopCount_ = hopCount;
            if (hopCount_ < rd.getHopCount()) {
                hashTable.put((destination_ip + ":" + destination_port),
                        new RoutingDestination(gateway_ip,
                                gateway_port,
                                hopCount_));
            }
        }
    }

    public void removeFromTable(String ip, int port) {
        hashTable.remove(ip + ":" + port);
        //Remove if it contain as an gateway
        ArrayList<String> al = new ArrayList<>();
        for (Map.Entry<String, RoutingDestination> entry
                : hashTable.entrySet()) {
            String key = entry.getKey();
            RoutingDestination value = entry.getValue();
            if (ip.equals(value.getIp())
                    && value.getPort() == Integer.parseInt(port)) {
                al.add(ip + ":" + port);
            }
        }
        al.stream().forEach((obj) -> {
            hashTable.remove(obj);
        });
    }
    
    public boolean isInTable(String ip,int port){
        return hashTable.containsKey(ip+":"+port);
    }
}
