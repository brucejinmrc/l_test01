package spring_context;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
/**
 * @author bruce
 *
 */
public class TestXMLConfiguration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    String spring = "C:\\m-power_distribution\\m-power\\mrcwebgui\\WEB-INF\\classes\\mrc-spring-context.xml";
        try {
            XMLConfiguration model = new XMLConfiguration(spring);
            System.out.println("Spring loaded");
        } catch (ConfigurationException cex) {
            cex.printStackTrace();
            
        } catch (Exception cex) {
            cex.printStackTrace();
            
        }       

	}

}
