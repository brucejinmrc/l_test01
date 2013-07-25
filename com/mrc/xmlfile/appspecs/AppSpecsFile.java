package com.mrc.xmlfile.appspecs;

import java.io.File;
import java.util.Date;

import javax.xml.bind.*;

import org.apache.log4j.Logger;

/**
 *  App spec object loaded from app spec xml file 
 * @author bruce
 *
 */
public class AppSpecsFile {

	/** App spec object loaded from app spec xml file */
	private Application appSpecs;
	
	private Logger log = Logger.getLogger(this.getClass());
	/*****************************************************************
	 * Constructor. Set App spacs 
	 ****************************************************************/
	public AppSpecsFile(String xmlfile) {

		File diskFile = new File(xmlfile);
		if (!diskFile.exists()) {
			log.info(xmlfile + " does not exist.");
			return;
		}
		
		try {
			log.info("Create app specs of: " + xmlfile);
			JAXBContext jc = JAXBContext.newInstance("com.mrc.xmlfile.appspecs");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			String file = xmlfile;
			File xml = new File(file);
			Object obj = unmarshaller.unmarshal(xml);
			appSpecs = (Application)obj;
		} catch (JAXBException e) {
			log.error(e.getMessage(), e);
		}
	        
	}

	/*****************************************************************
	 * Return App spacs  
	 ****************************************************************/
    public Application getAppSpecs() {
    	return appSpecs;
    }

    /********************************************************
     * Test
     *******************************************************/
    public static void main(String[] args)   {
       
		String loc = "C:/m-power_distribution/m-power/mrcjava/WEB-INF/classes/MRCJAVA2/R00001_.xml";
		AppSpecsFile to = new AppSpecsFile(loc);		
		System.out.println( new Date() + ": " + to.getAppSpecs());
	     
        return;
    }
}
