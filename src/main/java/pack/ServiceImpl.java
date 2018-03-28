package pack;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService(
serviceName = "HelloPersonService",
targetNamespace = "http://hello.org/hellopersonservice/1.0",
portName = "HelloPersonServicePort",
//
// NOTE: you can define wsdl location either here
// or you can set it via the endpoint.setMetadata method.
//
// if you don't do either, then the WS implementation will generate a wsdl
//wsdlLocation = "helloPersonService.wsdl",
endpointInterface = "pack.HelloPersonServicePortType")
@HandlerChain(file="handler-chain.xml")
// 
// "
// The default binding supported by JAX-WS 2.0 is SOAP 1.1 over HTTP. 
// With this release we have added SOAP 1.2 binding over HTTP support into JAX-WS 2.0.
// "
//
// https://javaee.github.io/metro-jax-ws/doc/user-guide/release-documentation.html#users-guide-soap-1-2
//
// So if you would like to use soap12 then you have to specify bindingtype here
// or at endpoint.create(...)
//
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class ServiceImpl implements HelloPersonServicePortType {

    @Resource
    private WebServiceContext wsContext;
    
	@Override
	public HelloPersonServiceResponseType greetPerson(HelloPersonServiceRequestType helloPersonServiceRequest) {
		HelloPersonServiceResponseType response = new HelloPersonServiceResponseType();
		if (helloPersonServiceRequest.getPerson().getFirstName().equalsIgnoreCase("abc")) {
		    throw new RuntimeException("firstname is ABC");
		}
		
		MessageContext mc = wsContext.getMessageContext();
		
		response.setGreetings(
		        "hello dear " + helloPersonServiceRequest.getPerson().getFirstName() +
		        ". Info from soap header: " + mc.get(MySoapHandler.KEY));
		return response;
	}
	
}
