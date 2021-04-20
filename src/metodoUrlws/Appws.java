package metodoUrlws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
 
 
/**
 * @author jmcneil
 * (c) copyright Software Pulse 2020
 *
 */
public class Appws {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        Appws testHelloAge = new Appws();
        testHelloAge.geHelloAge();
    }
 
     
    public void geHelloAge() {
        String wsURL = "https://10.16.0.250:9980/wsTSEservicios/wsDefuncion.asmx";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
        
        String xmlInput= "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
        		+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
        		+ "  <soap:Body>\r\n"
        		+ "    <Consulta_Defunciones_En_Linea xmlns=\"http://tempuri.org/\">\r\n"
        		+ "      <ENTIDAD>1</ENTIDAD>\r\n"
        		+ "      <USUARIO>TSE</USUARIO>\r\n"
        		+ "      <USUARIO_PASSWORD>tse2015</USUARIO_PASSWORD>\r\n"
        		+ "      <CODERR>0</CODERR>\r\n"
        		+ "      <TXTERR> </TXTERR>\r\n"
        		+ "    </Consulta_Defunciones_En_Linea>\r\n"
        		+ "  </soap:Body>\r\n"
        		+ "</soap:Envelope>";
        
        /*String xmlInput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
		        		+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
		        		+ "  <soap:Body>\r\n"
		        		+ "    <TipoCambioDia xmlns=\"http://www.banguat.gob.gt/variables/ws/\" />\r\n"
		        		+ "  </soap:Body>\r\n"
		        		+ "</soap:Envelope>";*/
         
        try
        {
        	System.setProperty(outputString, xmlInput);
        	
            url = new URL(wsURL);
            connection = url.openConnection();
            //connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            //connection.addRequestProperty("User-Agent", "Mozilla");
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            System.out.println(outputString);
            System.out.println("");
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:sayhelloResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            System.out.println("The response from the web service call is : " + webServiceResponse);
              
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
     
    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
             InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
}