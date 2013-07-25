package hsqldb;


import org.apache.log4j.Logger;
import org.hsqldb.persist.HsqlProperties;

public class StartServer {

	private Logger log = Logger.getLogger(this.getClass());
	
	public static void main(String[] args) {
		StartServer str = new StartServer();
		
		// String[] fileprop =  {"C:/temp/hsqldb-2.2.8/hsqldb/mrcdata/mrc/as400_1/mrcdb"
		//		 , "C:/temp/hsqldb-2.2.8/hsqldb/mrcdata/mrc/mysql_1/mrcdb"	 };
		 String[] fileprop =  {"C:/temp/userdata/mrc/as400_1/mrcdb"
				 , "C:/temp/userdata/mrc/mysql_1/mrcdb"	 };
		 
		 String[] dbname = {"mpowerdb",  "mpowerdb2"};
		 str.startServer(fileprop, dbname, 9001);
	}
 
	
	/************************************************************************
	 * Start server
	 ************************************************************************/
	void startServer(String[] file, String dbname[], int port) {

		org.hsqldb.Server server = new org.hsqldb.Server();
		HsqlProperties props = new HsqlProperties();
		log.info("    HSQLDB port: " + port);

		try {
			for (int i = 0; i < file.length; i++) {
				String fileprop = "file:" + file[i] + ";ifexists=true";
				log.info("HSQLDB at: " + file[i]);
				log.info("HSQLDB alias: " + dbname[i]);
				server.setDatabasePath(i, fileprop);
				server.setDatabaseName(i, dbname[i]);
			}
			server.setPort(port);
			server.start();
		} catch (Exception e) {
			log.error("", e);
		}

		System.out.println("Start DB Complete.");
	}

}
