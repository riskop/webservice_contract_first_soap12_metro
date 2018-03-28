package pack;

import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

// based on this:
// https://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-server-side/
public class MySoapHandler implements SOAPHandler<SOAPMessageContext> {
	
	public static final String KEY = "key";
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
	    System.out.println("handleMessage");
		try {
			Boolean isResponse = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			if(!isResponse) {
				SOAPMessage soapMsg = context.getMessage();
				SOAPHeader soapHeader = soapMsg.getSOAPPart().getEnvelope().getHeader();
				String headerData = soapHeader.getElementsByTagNameNS("headerDataSpecification", "HeaderData").item(0).getTextContent();
				
				// communicate something to service implementations (and to others on the handler chain) 
				context.put(KEY, headerData);
				context.setScope(KEY, MessageContext.Scope.APPLICATION);
			} else { 
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		//continue other handler chain
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
	    System.out.println("handleFault...");
		return true;
	}

	@Override
	public void close(MessageContext context) {
        System.out.println("close...");
	}

	@Override
	public Set<QName> getHeaders() {
        System.out.println("getHeaders...");
		return Collections.emptySet();
	}

}
