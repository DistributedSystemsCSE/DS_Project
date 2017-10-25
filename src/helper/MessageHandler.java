package helper;

import ds_project.Communicator;
import ds_project.Host;
import ds_project.Neighbour;
import ds_project.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Buddhi
 */
public class MessageHandler implements Runnable {

    private Node node;
    private List<String> message_list;
    private BlockingQueue<String> message_queue;
    private final int MAX_QUEUE_SIZE = 100;
    private boolean shouldKill = false;
    private RoutingTable routingTable = new RoutingTable();
    private Communicator communicator;
    private FileHandler fileHandler;
    private final int maxHopCount = 10;

    private MessageHandler() {
        message_queue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
        message_list = new ArrayList<>();
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    public boolean isShouldKill() {
        return shouldKill;
    }

    public void setShouldKill(boolean shouldKill) {
        this.shouldKill = shouldKill;
    }

    public void removeRoute(String ip, int port) {
        routingTable.removeFromTable(ip, port);
    }

    @Override
    public void run() {
        try {
            String msg;
            //consuming messages until empty
            while (!shouldKill) {
                msg = message_queue.take();
//                System.out.println("Consumed " + msg);
                this.handle(msg);
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    public void putMessage(String msg) {
        try {
            message_queue.put(msg);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }

    private static class InstanceHolder {

        static MessageHandler instance = new MessageHandler();
    }

    public static MessageHandler getInstance() {
        return InstanceHolder.instance;
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

        /*
         BS_Register
         ===========
         length REG IP_address port_no username
         length REGOK no_nodes IP_1 port_1 IP_2 port_2

         BS_Unregister
         =============
         length UNREG IP_address port_no username
         length UNROK value

         Distributed System
         ==================
         Join
         length JOIN IP_address port_no timestamp
         length JOINOK value IP_address port_no timestamp

         Leave
         length LEAVE IP_address port_no timestamp
         length LEAVEOK value timestamp	

         Search
         length SER IP port forwarding_IP forwarding_port file_name hops timestamp
         length SEROK no_files IP port forwarding_IP forwarding_port hops searchedFile filename1 filename2 ... ... timestamp
        
         Requist Neighbours
         length NEREQ IP port count timestamp
         length NERRES IP port value IP_1 port_1 IP_2 port_2 ... ... timestamp
        
         IsAlive
         length ISALIVE IP port timestamp
         length ALIVE IP port timestamp
         */
        String[] mes = message.split(" ");
        int port;
        String ip;

        System.out.println("New Message: " + message);

        switch (mes[1]) {
            case "SER":
                ip = mes[2];
                port = Integer.parseInt(mes[3]);
                String forwardingIP = mes[4];
                int forwardingPort = Integer.parseInt(mes[5]);
                int hops = Integer.parseInt(mes[7]);
                String uniqMes = mes[0] + mes[1] + mes[2] + mes[3] + mes[6] + mes[8];
                if (!addMessage(uniqMes)) {
                    //update routing table
                    //routingTable.updateTable(ip, port,
                    //        forwardingIP, forwardingPort, (hops + 1));

                    // check for files and send responce
                    String fileName = mes[6];
                    List<String> searchFiles = fileHandler.getSimilarFileNames(fileName);
                    int noOfFiles = searchFiles.size();
                    searchFiles = replaceSpace(searchFiles);

                    // sent search respons
                    String resMsg = (new Message(MessageType.SEROK,
                            noOfFiles,
                            ip,
                            port,
                            node.getIp(),
                            node.getPort(),
                            (hops + 1),
                            fileName,
                            searchFiles)).getMessage();
                    communicator.sendToPeer(resMsg, ip, port);

                    // broadcast mesagge searchFile
                    if (hops < maxHopCount) {
                        String broadcastMsg = (new Message(MessageType.SER,
                                ip,
                                port,
                                node.getIp(),
                                node.getPort(),
                                fileName,
                                (hops + 1),
                                mes[8])).getMessage();
                        node.sendToNeighboursExcept(broadcastMsg, (new Neighbour(forwardingIP, forwardingPort)));
                    }
                }
                break;
            case "SEROK":
                //port = Integer.parseInt(mes[4]);
                //ip = mes[3];
                if (!addMessage(message)) {
                    ip = mes[5];
                    port = Integer.parseInt(mes[6]);
                    String keyword=mes[8];
                    //show search results
                    int count = Integer.parseInt(mes[2]);
                    List<String> fileNames=new ArrayList<>();
                    System.out.println("Search results " + mes[8]);
                    System.out.println("From " + mes[5] + mes[6]);
                    for (int i = 0; i < count; i++) {
                        System.out.println(mes[9 + i]);
                        fileNames.add(mes[9 + i]);
                    }
                    fileNames=replaceUnderscore(fileNames);
                    
                    //create search result object
                    SearchResult searchResult=new SearchResult(
                            new Host(ip, port),
                            fileNames.toArray(new String[0]));
                    
                    node.getSearchResultTable().addToTable(
                            keyword,
                            searchResult);
                    
                }
                break;
            case "JOIN":
                if (!addMessage(message)) {
                    ip = mes[2];
                    port = Integer.parseInt(mes[3]);
                    System.out.println("New JOIN message");
//                        if (node.addNeighbours(new Neighbour(ip, port))) {
//                            routingTable.updateTable(ip, port, ip, port, 1);
//                        }
                    node.addNeighbours(new Neighbour(ip, port));

                    String resMsg = (new Message(MessageType.JOINOK,
                            0,
                            node.getIp(),
                            node.getPort())).getMessage();
                    System.out.println("res:" + resMsg);
                    communicator.sendToPeer(resMsg, ip, port);
                }
                break;
            case "JOINOK":
                if (!addMessage(message)) {
                    ip = mes[3];
                    port = Integer.parseInt(mes[4]);
                    System.out.println("New JoinOK message");
//                    if (!addMessage(message)) {
//                        if (node.addNeighbours(new Neighbour(ip, port))) {
//                            routingTable.updateTable(ip, port, ip, port, 1);
//                        }
//                    }
                    node.addNeighbours(new Neighbour(ip, port));
                }
                break;
            case "NEREQ":
                ip = mes[2];
                port = Integer.parseInt(mes[3]);
                if (!addMessage(message)) {
                    int count = Integer.parseInt(mes[4]);
                    Neighbour[] neighbours = node.getRandomNeighbours(count);
                    int neighboursCount = neighbours.length;
                    String[] ipList = new String[neighboursCount];
                    int[] portList = new int[neighboursCount];
                    int i = 0;
                    for (Neighbour nb : neighbours) {
                        ipList[i] = nb.getIp();
                        portList[i] = nb.getPort();
                        i++;
                    }

                    String resMsg = (new Message(MessageType.NERRES,
                            node.getIp(),
                            node.getPort(),
                            neighboursCount,
                            ipList,
                            portList)).getMessage();
                    communicator.sendToPeer(resMsg, ip, port);
                }
                break;
            case "NERRES":
                //ip = mes[2];
                //port = Integer.parseInt(mes[3]);
                if (!addMessage(message)) {
                    int neighbourCount = Integer.parseInt(mes[4]);
                    for (int i = 0; i < neighbourCount; i++) {
                        ip = mes[5 + (i * 2)];
                        port = Integer.parseInt(mes[6 + (i * 2)]);
                        if (node.addNeighbours(new Neighbour(
                                ip,
                                port))) {
                            String resMsg = (new Message(MessageType.JOINOK,
                                    0,
                                    node.getIp(),
                                    node.getPort())).getMessage();
                            System.out.println("Sending new neighbour joinok:" + resMsg);
                            communicator.sendToPeer(resMsg, ip, port);
                        }
                    }
                }
                break;
            case "LEAVE":
                if (!addMessage(message)) {
                    ip = mes[2];
                    port = Integer.parseInt(mes[3]);
                    node.removeNeighbour(new Neighbour(ip, port));
                }
                break;
            case "ISALIVE":
                if (!addMessage(message)) {
                    ip = mes[2];
                    port = Integer.parseInt(mes[3]);
                    String resMsg = (new Message(MessageType.ALIVE,
                            node.getIp(),
                            node.getPort())).getMessage();
                    communicator.sendToPeer(resMsg, ip, port);
                }
                break;
            case "ALIVE":
                if (!addMessage(message)) {
                    ip = mes[2];
                    port = Integer.parseInt(mes[3]);
                    node.setNeighbourAlive(ip, port);
                }
                break;

            default:
                System.err.println("Error: " + message);
                break;
        }

    }

    public boolean addMessage(String message) {
        if (message_list.stream().anyMatch((str) -> (str.equals(message)))) {
            return true;
        } else {
            message_list.add(message);
            return false;
        }
    }

    /**
     * @param message
     * @throws helper.BsRegisterException
     *
     * Decode length REGOK no_nodes IP_1 port_1 IP_2 port_2
     */
    public Neighbour[] decodeRegisterResponse(String message)
            throws BsRegisterException {

        String[] mes = message.split(" ");
        System.out.println(message);
        System.out.println("mes: " + mes[2]);
        int no_nodes = Integer.parseInt(mes[2]);

        if (no_nodes < 9996) {
            Neighbour[] neighbour = new Neighbour[no_nodes];
            for (int n = 0; n < no_nodes; n++) {
                neighbour[n] = new Neighbour(mes[(n * 2) + 3],
                        Integer.parseInt(mes[(n * 2) + 4]));
            }
            return neighbour;
        } else {
            switch (no_nodes) {
                case 9996:
                    throw new BsRegisterException("failed, can’t register."
                            + " BS full");
                case 9997:
                    throw new BsRegisterException("failed, registered"
                            + " to another user, try a different IP and port");
                case 9998:
                    throw new BsRegisterException("failed, already registered"
                            + " to you, unregister first");
                case 9999:
                    throw new BsRegisterException("failed, there is some error"
                            + " in the command");
            }
        }
        return null;
    }

    /**
     * @param message
     * @throws BsRegisterException
     *
     * length JOINOK value
     */
    public boolean decodeInitialJoinResponse(String message)
            throws BsRegisterException {
        String[] mes = message.split(" ");
        if (mes[1].equals("JOINOK")) {
            if (mes[2].equals("0")) {
                node.addNeighbours(new Neighbour(mes[3], Integer.parseInt(mes[4])));
                return true;
            } else if (mes[2].equals("9999")) {
                throw new BsRegisterException("failed, can’t register."
                        + " initial join failed");
            }
        }
        return false;
    }

    /**
     * @param message
     * @throws BsRegisterException
     *
     * length UNROK value
     */
    public boolean decodeUnregisterResponse(String message)
            throws BsRegisterException {
        String[] mes = message.split(" ");
        int no_nodes = Integer.parseInt(mes[2]);
        return mes[1].equals("UNROK") && mes[1].equals("0");

    }

    private List<String> replaceSpace(List<String> filenames) {
        List<String> replacedFilenames = new ArrayList<>();
        filenames.forEach((filename) -> {
            replacedFilenames.add(filename.replace(" ", "_"));
        });
        return replacedFilenames;
    }

    private List<String> replaceUnderscore(List<String> filenames) {
        List<String> replacedFilenames = new ArrayList<>();
        filenames.forEach((filename) -> {
            replacedFilenames.add(filename.replace("_", " "));
        });
        return replacedFilenames;
    }
}
