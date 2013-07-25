/**
 * 
 */
package email;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;

/**
 * Retrieve email from an account 
 * @author bruce
 *
 */
public class ReadGmail extends ReadEmailAbs {
	private Logger log = Logger.getLogger(this.getClass());	

	/**********************************************************************
     * Constructor. Connect to an email account
     **********************************************************************/
	public ReadGmail(String protocol, String emailServer, String emailAccount,
			String emailPswd) {
		
		super(protocol, emailServer, emailAccount, emailPswd);
		
	}
	
	/**********************************************************************
     * Return email store
     **********************************************************************/
	protected Store getStore() {
		Store store = null;
				
		try {
			log.info("Connecting to " + emailServer) ;
			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", protocol);
			Session session = Session.getDefaultInstance(props, null);
			store = session.getStore(protocol);
			store.connect(emailServer, emailAccount, emailPswd);
			log.info("connected to " + emailServer + " with account: " + emailAccount);
			 
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}  
		return store;
	}

	
	/**********************************************************************
     * Test main 
     **********************************************************************/
	public static void main(String[] args) {
		String protocol = "imaps";
		String emailServer = "imap.gmail.com";
		String emailAccount = "brucejinmrc@gmail.com";
		String emailPswd = "brucejinmrc007";
		ReadGmail read = new ReadGmail(protocol, emailServer, emailAccount, emailPswd);
		List<EmailMessage> emails = read.getMessages("Personal", true, "C://temp/");
		for (EmailMessage em : emails) {
			System.out.println(em);
		}
		//read.read("Inbox");
	}
	
}

