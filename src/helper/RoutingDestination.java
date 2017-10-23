/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import ds_project.Host;

/**
 *
 * @author yohan
 */
public class RoutingDestination extends Host {
    private int hopCount;
    
    public RoutingDestination(String ip,int port,int hopCount){
        super(ip,port);
        this.hopCount=hopCount;
    }

    public int getHopCount() {
        return hopCount;
    }

    public void setHopCount(int hopCount) {
        this.hopCount = hopCount;
    }
    
    
}
