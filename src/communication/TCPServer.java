package communication;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author Hareen Udayanath
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TCPServer {
    @WebMethod void handleRequest(String msg);
    @WebMethod String handleInitialJoinRequest(String msg);
}
