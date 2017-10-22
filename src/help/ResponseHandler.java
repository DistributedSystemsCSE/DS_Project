package help;

import ds_project.Neighbour;
import ds_project.Node;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Buddhi
 */
public class ResponseHandler {

    private final Node node;
    public static List<String> message_list;

    public ResponseHandler(Node node) {
        this.node = node;
    }

    /**
     * @param message
     *
     *
     * length JOINOK value length LEAVE IP_address port_no length LEAVEOK value
     * length SER IP port file_name hops length SEROK no_files IP port hops
     * filename1 filename2 length ERROR
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
                        if (addNeighbours(new Neighbour(mes[2], mes[3]))) {

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

    public synchronized boolean addMessage(String message) {
        if (message_list.stream().anyMatch((str) -> (str.equals(message)))) {
            return true;
        } else {
            message_list.add(message);
            return false;
        }
    }

    /**
     * @param message
     * @return
     *
     * Decode length REGOK no_nodes IP_1 port_1 IP_2 port_2
     */
    public Neighbour[] decodeRegisterResponse(String message) {
        try {
            String[] mes = message.split(" ");
            int no_nodes = Integer.parseInt(mes[2]);
            Neighbour[] neighbour = new Neighbour[no_nodes];
            for (int n = 0; n < no_nodes; n++) {
                neighbour[n] = new Neighbour(mes[(n * 2) + 3], mes[(n * 2) + 4]);
            }
            return neighbour;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
}
