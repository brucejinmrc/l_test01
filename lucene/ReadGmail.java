/**
 * 
 */
package lucene;


import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

import org.apache.log4j.Logger;

public class ReadGmail {
	private Logger log = Logger.getLogger(this.getClass());


	public static void main(String[] args) {
		ReadGmail read = new ReadGmail();
		read.read();
	}
	
	void read() {
		SearchTerm term = new SearchTerm() {
			  @Override
			  public boolean match(Message mess) {
			    try {
			      return mess.getContent().toString().toLowerCase().length() > 1;
			    } catch (IOException ex) {
			      log.error(ex.getMessage(), ex);
			    } catch (MessagingException ex) {
			    	 log.error(ex.getMessage(), ex);
			    }
			    return false;
			  }
			};
			
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		Store store;
		System.out.println("start:" + new Date());
		try {
			store = session.getStore("imaps");
		//	store.connect("imap.gmail.com", "brucej@mrc-productivity.com", "jin007");
			store.connect("imap.gmail.com", "brucejinmrc@gmail.com", "brucejinmrc007");
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			
			//Message[] searchResults = inbox.search(term);
			Message[] messages = inbox.getMessages();
			
			for(int i = 0; i<messages.length; i++) {
				if (i > 10 ) break;
				Message msg = messages[i];
				
				String content = msg.getContent().toString();
				System.out.println("=== mail ===" + i);
				System.out.println(content);
			/*	System.out.println("getDescription: " + msg.getDescription());//
				System.out.println("getContent: " + msg.getContent());
				System.out.println("getSubject: " + msg.getSubject());
				System.out.println("getReceivedDate: " + msg.getReceivedDate());
				System.out.println("MATCHED: " + msg.getFrom()[0]);*/
			}
			
			inbox.close(false);
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("end:" + new Date());
	}

}

