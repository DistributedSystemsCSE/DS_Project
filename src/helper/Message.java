package helper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hareen Udayanath
 */
public class Message {
   private String message;
    
    //NEW
    public Message(MessageType type, int value, String ip, int port){
        switch(type){
            case JOINOK: message=appendLength("JOINOK"+" "+value+" "+ip+" "+port);
                break;
        }
    }
    
    //NEW
    public Message(MessageType type, String ip, int port){
        switch (type){
            case LEAVE:
                message = appendLength("LEAVE"+" "+ip+" "+port);
                break;
            case LEAVEOK:
                message = appendLength("LEAVEOK"+" "+ip+" "+port);
                break;
            case JOIN:
                message = appendLength("JOIN"+" "+ip+" "+port);
                break;
            case JOINOK:
                message = appendLength("JOINOK"+" "+ip+" "+port);
                break;
            case ALIVE:
                message = appendLength("ALIVE"+" "+ip+" "+port);
                break;
            case ISALIVE:
                message = appendLength("ISALIVE"+" "+ip+" "+port);
                break;
        }
    }
    
    //NEW
    public Message(MessageType type, String ip, int port, int count){
        switch (type){
            case NEREQ:
                message = appendLength("NEREQ"+" "+ip+" "+port+" "+count);
                break;
        }
    }
    
    //NEW
    public Message(MessageType type, String responding_ip, int responding_port, int value, String[] ip, int[] port){
        switch (type){
            case NERRES:
                String neighbourList="";
                for(int i=0;i<value;i++){
                    neighbourList = neighbourList+" "+ip[i]+" "+String.valueOf(port[i]);
                }
                message = appendLength("NERRES"+" "+responding_ip+" "+responding_port+" "+value+neighbourList);
                break;
        }
    }
    
    //NEW
    public Message(MessageType type, String ip, int port, String ip_forwarding, 
            int port_forwarding, String fileNanme, int hops, String timeStamp){
        switch(type){
            case SER:
                fileNanme=fileNanme.replace(" ", "_");
                message=appendLengthWithOutTime("SER"+" "+ip+" "+port+" "+ip_forwarding+" "
                        +port_forwarding+" "+fileNanme+" "+hops+" "+timeStamp);
                break;
        }
    }
    
    //NEW
    public Message(MessageType type, String ip, int port, String ip_forwarding, 
            int port_forwarding, String fileNanme, int hops){
        switch(type){
            case SER:
                fileNanme=fileNanme.replace(" ", "_");
                message=appendLength("SER"+" "+ip+" "+port+" "+ip_forwarding+" "
                        +port_forwarding+" "+fileNanme+" "+hops);
                break;
        }
    }
    
    //NEW
    public Message(MessageType type, int noOfFiles, String fileDestinationIp,
            int fileDestinationPort, String forwarding_IP, int forwarding_port,
            int hops, String searchedFile, List<String> files){

        switch(type){
        
            case SEROK: 
            {
                String filesString=searchedFile;
                for (String file : files) {
                    filesString = filesString +" "+ file;
                }
                message=appendLength("SEROK"+" "+noOfFiles+" "+fileDestinationIp+" "+fileDestinationPort+" "+forwarding_IP+" "+forwarding_port+" "+hops+" "+filesString);
                break;
        
            }
        }
    }

    //OLD------------------------------------------
    
    public Message(MessageType type, String ip, int port, String name){

        switch(type){
            case REG:message = appendLength("REG"+" "+ip+" "+port+" "+name);
                break;
            case UNREG:message=appendLength("UNREG"+" "+ip+" "+port+" "+name);
                break;
            case JOIN:message=appendLength("JOIN"+" "+ip+" "+port);
                break;
            case JOINOK: message=appendLength("JOINOK"+" "+ip+" "+port+" "+0);
                break;
            case LEAVE: message=appendLength("JOINOK"+" "+ip+" "+port+" "+0);
                break;
            case LEAVEOK: message=appendLength("JOINOK"+" "+ip+" "+port+" "+0);
                break;
            case SER: message=appendLength("JOINOK"+" "+ip+" "+port+" "+0);
                break;
            case SEROK: message=appendLength("JOINOK"+" "+ip+" "+port+" "+0);
                break;
            case NEREQ: message=appendLength("JOINOK"+" "+ip+" "+port+" "+0);
                break;
            case NERRES: message=appendLength("JOINOK"+" "+ip+" "+port+" "+0);
                break;
//            case FILES:
//                message = appendLength("FILES"+" "+ip+" "+port+" "+name);
//                break;
//            case SER:
//            {
//                String fileName = name;
//                message=appendLength("SER"+" "+ip+" "+port+" "+fileName);
//                break;
//            }
//            case INQUIRE: message= appendLength("INQUIRE"+" "+ip+" "+port);
//                break; 
//            case INQUIREOK: message= appendLength("INQUIREOK"+" "+ip+" "+port);
//                break;
//            case LEAVE:
//                String peerIpPort = name;
//                if(peerIpPort!=null){
//                    message=appendLength("LEAVE"+" "+ip+" "+port+" "+name);
//                }else{
//                    message=appendLength("LEAVE"+" "+ip+" "+port+" "+"CHILD-LEAVING");
//                }
//                break;
        }
    }
    
    public Message(MessageType type, int success){
        switch(type){
            case JOINOK: message=appendLength("JOINOK"+" "+success);
                break;
//            case SEROK: message=appendLength("SEROK"+" "+success);
//                break;
        }
    }
    
    //public Message(MessageType type,String searchKey){
    public Message(MessageType type,String searchKey,String intermediateIp,int intermediatePort){
        switch(type){
            //case SEROK: message=appendLength("SEROK"+" "+"0"+" "+searchKey);
            case SEROK: message=appendLength("SEROK"+" "+"0"+" "+searchKey+" "+intermediateIp+" "+intermediatePort);
                break;        
        }
    }
    
  //  public Message(MessageType type, int noOfFiles, String fileDestinationIp, int fileDestinationPort, int hops, ArrayList<String> files, String fileKey){
    public Message(MessageType type, int noOfFiles, String fileDestinationIp, int fileDestinationPort, int hops, ArrayList<String> files, String fileKey,String intermediateIp,int intermediatePort){

        switch(type){
        
            case SEROK: 
            {
                String filesString=fileKey;
                for (String file : files) {
                    filesString = filesString +" "+ file;
                }
                //message=appendLength("SEROK"+" "+noOfFiles+" "+fileDestinationIp+" "+fileDestinationPort+" "+hops+" "+filesString);
                message=appendLength("SEROK"+" "+noOfFiles+" "+fileDestinationIp+" "+fileDestinationPort+" "+hops+" "+filesString+" "+intermediateIp+" "+intermediatePort);
                break;
        
            }
        }
    }
    
    public Message(MessageType type, int noOfFiles, String fileDestinationIp, int fileDestinationPort, int hops, String fileString,String intermediateIp,int intermediatePort){
        switch(type){
        
            case SEROK: 
            {
                message=appendLength("SEROK"+" "+noOfFiles+" "+fileDestinationIp+" "+fileDestinationPort+" "+hops+" "+fileString+" "+intermediateIp+" "+intermediatePort);
                break;
        
            }
        }
    }
    public String getMessage(){
        return message;
    }
    
    private String appendLength(String message){
        long currentTime = System.currentTimeMillis();
        String currentTime_s=String.valueOf(currentTime);
        int messageLength = message.length()+4+2+currentTime_s.length();
        String messageLengthString = Integer.toString(messageLength);
        String prefix="";
        switch(messageLengthString.length()){
            case 1: prefix="000"+messageLengthString+" ";
                break;
            case 2:prefix = "00"+messageLengthString+" ";
                break;
            case 3:prefix="0"+messageLengthString+" ";
                break;
            case 4: prefix=messageLengthString+" ";
                break;
        }
        message=prefix+message+" "+currentTime_s;
        
        return message;
    } 
    
    private String appendLengthWithOutTime(String message){
//        long currentTime = System.currentTimeMillis();
//        String currentTime_s=String.valueOf(currentTime);
        int messageLength = message.length()+4+1;
        String messageLengthString = Integer.toString(messageLength);
        String prefix="";
        switch(messageLengthString.length()){
            case 1: prefix="000"+messageLengthString+" ";
                break;
            case 2:prefix = "00"+messageLengthString+" ";
                break;
            case 3:prefix="0"+messageLengthString+" ";
                break;
            case 4: prefix=messageLengthString+" ";
                break;
        }
        message=prefix+message;
        
        return message;
    } 
     
}
