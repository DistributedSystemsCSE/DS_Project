package help;

import ds_project.Neighbour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Buddhi
 */
public class ResponseHandler {

    public static List<String> message_list;
    public static List<Neighbour> neighbours_list;

    public ResponseHandler() {
        message_list = new ArrayList<>();
    }

    /**
     * @param message
     *
     *
     * length JOINOK value
     * length LEAVE IP_address port_no
     * length LEAVEOK value
     * length SER IP port file_name hops
     * length SEROK no_files IP port hops filename1 filename2
     * length ERROR
     */
    public void handle(String message) {

        try {
            String[] mes = message.split(" ");

            switch (mes[1]) {
                case "SER":
                    String[] newMes = null;
                    Arrays.copyOfRange(newMes, 0, (mes.length - 2));
                    String uniqMes = String.join("", newMes);
                    addMessage(uniqMes);
                    break;
                case "JOIN":
                    if (!addMessage(message)) {
                        if(addNeighbours(new Neighbour(mes[2], mes[3]))){
                            
                        }
                    }
                    break;
                case "JOINOK":
                    addMessage(message);
                    break;
                case "LEAVE":
                    break;
                case "LEAVEOK":
                    break;

                case "SEROK":
                    break;
                case "ERROR":
                    break;

                default:
                    System.err.println("Error: " + message);
                    break;
            }

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public boolean addNeighbours(Neighbour neb) {
        if (!neighbours_list.stream().noneMatch((tem) -> (tem == neb))) {
            return false;
        }
        
        neighbours_list.add(neb);
        return true;
    }

    public synchronized boolean addMessage(String message) {
        if (message_list.stream().anyMatch((str) -> (str.equals(message)))) {
            return true;
        } else {
            message_list.add(message);
            return false;
        }
    }

}
