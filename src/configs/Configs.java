package configs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Hareen Udayanath
 */
public class Configs {
    
    public static Properties prop = new Properties();
    
    public String getServerIP(){
        return getStringProperty("SERVER_IP");         
    }
    
    public int getServerPort(){
        return getIntegerProperty("SERVER_PORT");         
    }
    
    public String getClientIP(){
        return getStringProperty("CLIENT_IP");        
    }
    
    public int getClientPort(){
        return getIntegerProperty("CLIENT_PORT");        
    }
    
    public int getReceiverTimeout(){
        return getIntegerProperty("TIME_OUT_RECEIVE");        
    }
    
     public int getTimeoutForFirstTwo(){
        return getIntegerProperty("TIME_OUT_RECEIVE");        
    }
    
    public int getNeighbourSetterTimeout(){
        return getIntegerProperty("TIME_OUT_NEIGHBOUR_SETTER");        
    }
    
    public int getNeighbourCheckerTimeout(){
        return getIntegerProperty("TIME_OUT_NEIGHBOUR_CHECKER");        
    }
    
    public String getClientName(){
        return getStringProperty("CLIENT_NAME");        
    }
    
    public int getMaxNumberOfNeighbours(){
        return getIntegerProperty("MAX_NUMBER_OF_NEIGHBORS");        
    }
    
    private int getIntegerProperty(String propertyName){
        try{
            prop.load(new FileInputStream("configs.ds"));
            return Integer.valueOf(prop.getProperty(propertyName));
        }catch(ClassCastException ex){
            //System.out.println(ex.getMessage());
            return -1;
        }catch(IOException ex){
            //System.out.println(ex.getMessage());
            return -1;
        }
    }
    
    public String getStringProperty(String propertyName){
        try{
            prop.load(new FileInputStream("configs.ds"));
            return prop.getProperty(propertyName);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public void setProperty(String propertyName,String value){
        try{            
            prop.setProperty(propertyName,value);
            prop.store(new FileOutputStream("configs.ds"), null);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            
        }
    }
}
