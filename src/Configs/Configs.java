package Configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Hareen Udayanath
 */
public class Configs {
    
    public static Properties prop = new Properties();
    
    public String getServerIP(){
        try{
            prop.load(new FileInputStream("configs.ds"));
            return prop.getProperty("SERVER_IP");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public int getServerPort(){
        try{
            prop.load(new FileInputStream("configs.ds"));
            return Integer.valueOf(prop.getProperty("SERVER_PORT"));
        }catch(ClassCastException ex){
            //System.out.println(ex.getMessage());
            return -1;
        }catch(IOException ex){
            //System.out.println(ex.getMessage());
            return -1;
        }
    }
    
    public String getClientIP(){
        try{
            prop.load(new FileInputStream("configs.ds"));
            return prop.getProperty("CLIENT_IP");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public int getClientPort(){
        try{
            prop.load(new FileInputStream("configs.ds"));
            return Integer.valueOf(prop.getProperty("CLIENT_PORT"));
        }catch(ClassCastException ex){
            //System.out.println(ex.getMessage());
            return -1;
        }catch(IOException ex){
            //System.out.println(ex.getMessage());
            return -1;
        }
    }
    
    public int getTimeout(){
        try{
            prop.load(new FileInputStream("configs.ds"));
            return Integer.valueOf(prop.getProperty("TIME_OUT"));
        }catch(ClassCastException ex){
            //System.out.println(ex.getMessage());
            return -1;
        }catch(IOException ex){
            //System.out.println(ex.getMessage());
            return -1;
        }
    }
    
     public String getClientName(){
        try{
            prop.load(new FileInputStream("configs.ds"));
            return prop.getProperty("CLIENT_NAME");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
