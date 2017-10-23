package ds_project;

/**
 *
 * @author Hareen Udayanath
 */
public class Host {
    private String ip;
    private int port;
    
    public Host(){
    }
    public Host(String ip,int port){
        this.ip = ip;
        this.port = port;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
    
}
