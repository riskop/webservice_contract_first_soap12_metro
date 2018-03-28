package pack;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class TestIt {
    
    @Before
    public void before() {
        
        // This instructs the internal (JDK) Metro implementation to log the incoming requests
        // (so set this on the server)
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
        // use this to control large messages
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold","999");
        
        // use this if Metro is not internal
        //System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        
        StartServer.main(null);
    }
    
    @After
    public void after() {
        StartServer.stop();
    }

    /**
     * use this to start up the service and use external tool to use it
     */
    @Ignore
    @Test
    public void startAndWaitIndefinitely() {
        waitIndefinitely();
    }
    
//    @Ignore
    @Test
    public void testIt() throws Exception {
        OkHttpClient client = new OkHttpClient();
        
        String envelope = 
            "<soapenv:Envelope \n" +
            "xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\" \n" +
            "xmlns:hdr=\"headerDataSpecification\" \n" +
            "xmlns:dat=\"http://hello.org/hellopersonservice/1.0/datastructures\">\n" +
            "   <soapenv:Header>\n" +
            "   <hdr:HeaderData>this is some info in the soap header</hdr:HeaderData>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <dat:HelloPersonServiceRequest>\n" +
            "         <dat:Person>\n" +
            "            <dat:FirstName>abcc</dat:FirstName>\n" +
            "            <dat:LastName>?</dat:LastName>\n" +
            "         </dat:Person>\n" +
            "      </dat:HelloPersonServiceRequest>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>\n";
        
        Request request = new Request.Builder()
                .url("http://127.0.0.1:8080/helloservice")
                .addHeader("SOAPAction", "\"http://hello.org/HelloPersonService/greetPerson\"")
                .post(
                        RequestBody.create(
                                MediaType.parse("application/soap+xml;charset=UTF-8"), 
                                envelope))
                .build();
        
        Response response = client.newCall(request).execute();
        
        System.out.println("status: " + response.code() );
        System.out.println("response: " + response.body().string() );
        
    }

	private static void waitIndefinitely() {
	    System.out.println("going to wait indefinitely... You can test the service via SoapUI for example");
	    try {
	        while(true) {
	            Thread.sleep(1000);
	        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}
