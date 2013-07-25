/**
 * 
 */
package lucene;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.ParseException;
import javax.mail.search.FlagTerm;


import org.apache.log4j.Logger;

public class ReadGmail2 {
	private Logger log = Logger.getLogger(this.getClass());
	
	private String emailServer;
	private String emailAccount;
	private String emailPswd;
	
	private Store store;

	static int attnum = 1;
	static boolean saveAttachments = true;
	
	/**********************************************************************
     * Constructor 
     **********************************************************************/
	public ReadGmail2(String protocol, String emailServer, String emailAccount,
			String emailPswd) {
		super();
		this.emailServer = emailServer;
		this.emailAccount = emailAccount;
		this.emailPswd = emailPswd;
		log.info("Connecting to " + emailServer) ;
		try {
			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", protocol);
			Session session = Session.getDefaultInstance(props, null);
			store = session.getStore("imaps");
			store.connect(emailServer, emailAccount, emailPswd);
			log.info("connected to " + emailServer + " with account: " + emailAccount);
			 
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}  
	}

	/**********************************************************************
     * Test main 
     **********************************************************************/
	public static void main(String[] args) {
		String protocol = "imaps";
		String emailServer = "imap.gmail.com";
		String emailAccount = "brucejinmrc@gmail.com";
		String emailPswd = "brucejinmrc007";
		ReadGmail2 read = new ReadGmail2(protocol, emailServer, emailAccount, emailPswd);
		read.read("Personal");
		//read.read("Inbox");
	}
	
	void read(String folder) {
		 
		try {		 
			Folder inbox = store.getFolder(folder);
			inbox.open(Folder.READ_WRITE);
			log.info("opened box");
			
			//Message[] messages = inbox.getMessages();
		//	Flags seen = new Flags(Flags.Flag.SEEN);
			Flags seen = new Flags(Flags.Flag.RECENT);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message messages[] = inbox.search(unseenFlagTerm); //get unread only
			
		 
			for(int i = 0; i<messages.length; i++) {
				if (i > 10 ) break;
				dumpPart(messages[i]);
	//			msg.setFlag(Flags.Flag.SEEN, true);
			/*	
				String content = msg.getContent().toString();
				System.out.println(i + " - Subject: " + msg.getSubject() + ", cnt type=" + msg.getContentType());
				Address[] from = msg.getFrom();
				for (Address add : from) {
					String ff = add.toString();
					System.out.println(i + " from  " + ff); 
				}
				
				Date sentDate = msg.getSentDate();
				Date receiveDate = msg.getReceivedDate();
				String fileName = msg.getFileName(); 
				System.out.println("file=" + fileName + "--- " + sentDate + "-" + receiveDate);*/
			 
			}
			inbox.close(false);
			log.info("get msgs");
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("end:" + new Date());
	}
	
	public static void dumpPart(Part part) throws Exception {
		if (part instanceof Message) {
			dumpEnvelope((Message) part);
		}
		
		String filename = part.getFileName();
		if (filename != null)
			pr("FILENAME: " + filename);

	   if (part.isMimeType("multipart/*")) {
			pr("This is a Multipart");
			pr("---------------------------");
			Multipart mp = (Multipart) part.getContent();
			level++;
			int count = mp.getCount();
			for (int i = 0; i < count; i++)
				dumpPart(mp.getBodyPart(i));
			level--;
		}

		/*
		 * If we're saving attachments, write out anything that looks like an
		 * attachment into an appropriately named file. Don't overwrite existing
		 * files to prevent mistakes.
		 */
		if (saveAttachments && level != 0 && part instanceof MimeBodyPart
				&& !part.isMimeType("multipart/*")) {
			String disp = part.getDisposition();
			// many mailers don't include a Content-Disposition
			if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
				if (filename == null) {
					//filename = "Attachment" + attnum++;
					return;
				}
				pr("Saving attachment to file " + filename);
				
				filename = "c://temp/" + filename;
				try {
					File f = new File(filename);
					if (f.exists())
						throw new IOException("file exists");
					((MimeBodyPart) part).saveFile(f);
				} catch (IOException ex) {
					pr("Failed to save attachment: " + ex);
				}
				pr("---------------------------");
			}
		}
	}

	public static void dumpEnvelope(Message m) throws Exception {
		pr("This is the message envelope");
		pr("---------------------------");
		Address[] a;
		// FROM
		if ((a = m.getFrom()) != null) {
			for (int j = 0; j < a.length; j++)
				pr("FROM: " + a[j].toString());
		}

		// REPLY TO
		if ((a = m.getReplyTo()) != null) {
			for (int j = 0; j < a.length; j++)
				pr("REPLY TO: " + a[j].toString());
		}

		// TO
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
			for (int j = 0; j < a.length; j++) {
				pr("TO: " + a[j].toString());
				InternetAddress ia = (InternetAddress) a[j];
				if (ia.isGroup()) {
					InternetAddress[] aa = ia.getGroup(false);
					for (int k = 0; k < aa.length; k++)
						pr("  GROUP: " + aa[k].toString());
				}
			}
		}

		// SUBJECT
		pr("SUBJECT: " + m.getSubject());

		// DATE
		Date d = m.getSentDate();
		pr("SendDate: " + (d != null ? d.toString() : "UNKNOWN"));

		// FLAGS
		Flags flags = m.getFlags();
		StringBuffer sb = new StringBuffer();
		Flags.Flag[] sf = flags.getSystemFlags(); // get the system flags

		boolean first = true;
		for (int i = 0; i < sf.length; i++) {
			String s;
			Flags.Flag f = sf[i];
			if (f == Flags.Flag.ANSWERED)
				s = "\\Answered";
			else if (f == Flags.Flag.DELETED)
				s = "\\Deleted";
			else if (f == Flags.Flag.DRAFT)
				s = "\\Draft";
			else if (f == Flags.Flag.FLAGGED)
				s = "\\Flagged";
			else if (f == Flags.Flag.RECENT)
				s = "\\Recent";
			else if (f == Flags.Flag.SEEN)
				s = "\\Seen";
			else
				continue; // skip it
			if (first)
				first = false;
			else
				sb.append(' ');
			sb.append(s);
		}

		String[] uf = flags.getUserFlags(); // get the user flag strings
		for (int i = 0; i < uf.length; i++) {
			if (first)
				first = false;
			else
				sb.append(' ');
			sb.append(uf[i]);
		}
		pr("FLAGS: " + sb.toString());

		// X-MAILER
		String[] hdrs = m.getHeader("X-Mailer");
		if (hdrs != null)
			pr("X-Mailer: " + hdrs[0]);
		else
			pr("X-Mailer NOT available");
	}

	static String indentStr = "                                               ";
	static int level = 0;

	/**
	 * Print a, possibly indented, string.
	 */
	public static void pr(String s) {
	 
		System.out.println(s);
	}

}

