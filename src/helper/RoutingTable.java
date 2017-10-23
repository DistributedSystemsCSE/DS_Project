/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author yohan
 */
public class RoutingTable {
    Hashtable<String,RoutingDestination> hashTable=new Hashtable<>(); 
     
    public void addToTable(String destination_ip,String destination_port,String gateway_ip,String gateway_port,String hopCount ){        
        hashTable.put((destination_ip+":"+destination_port), new RoutingDestination(gateway_ip, Integer.parseInt(gateway_port),Integer.parseInt(hopCount)));
    }
    
    public void updateTable(String destination_ip,String destination_port,String gateway_ip,String gateway_port,String hopCount){
        RoutingDestination rd=hashTable.get(destination_ip+":"+destination_port);
        if(rd==null){
            hashTable.put((destination_ip+":"+destination_port), new RoutingDestination(gateway_ip, Integer.parseInt(gateway_port),Integer.parseInt(hopCount)));
        }
        else{
            int hopCount_=Integer.parseInt(hopCount);
            if(hopCount_<rd.getHopCount()){
                hashTable.put((destination_ip+":"+destination_port), new RoutingDestination(gateway_ip, Integer.parseInt(gateway_port),hopCount_));
            }
        }
    }
    
    public void removeFromTable(String ip,String port){
        hashTable.remove(ip+":"+port);
        //Remove if it contain as an gateway
        ArrayList<String> al=new ArrayList<String>();
        for(Map.Entry<String,RoutingDestination> entry:hashTable.entrySet()){
            String key = entry.getKey();
            RoutingDestination value=entry.getValue(); 
            if(ip.equals(value.getIp()) && value.getPort()==Integer.parseInt(port)){
                al.add(ip+":"+port);
            }
        }
        for(String obj:al){
            hashTable.remove(obj);
        }
    }
}
