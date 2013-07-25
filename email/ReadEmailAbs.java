/**
 * 
 */
package email;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

import org.apache.log4j.Logger;

/**
 * Retrieve email from an account (abstract class)
 * @author bruce
 *
 */
public abstract class ReadEmailAbs {
 
	private Logger log = Logger.getLogger(this.getClass());	
	
	private boolean saveAttachments;
	private String saveFolder;
	
	protected String protocol; //pop3, imaps...
	protected String emailServer;
	protected String emailAccount;
	protected String emailPswd;
	 
	private int level;
	/**********************************************************************
     * Constructor. Connect to an email account
     **********************************************************************/
	public ReadEmailAbs(String protocol, String emailServer,
			String emailAccount, String emailPswd) {
		super();
		this.protocol = protocol;
		this.emailServer = emailServer;
		this.emailAccount = emailAccount;
		this.emailPswd = emailPswd;
	}

	/**********************************************************************
     * Get email messages.
     **********************************************************************/
	protected List<EmailMessage> getMessages(String folder, boolean saveAttachments, String saveFolder) {
		 
		this.saveAttachments = saveAttachments;
		this.saveFolder = saveFolder;
		
		List<EmailMessage> emails = new ArrayList<EmailMessage>();
		
		try {
			Store store = getStore();
			Folder inbox = store.getFolder(folder);
			inbox.open(Folder.READ_WRITE);
			log.info("opened box");
			
		//	Flags seen = new Flags(Flags.Flag.SEEN);
			Flags seen = new Flags(Flags.Flag.RECENT);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message messages[] = inbox.search(unseenFlagTerm); //get unread only
			
			// Use a suitable FetchProfile
		/*	FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			fp.add(FetchProfile.Item.FLAGS);
			fp.add("X-Mailer");
			inbox.fetch(messages, fp);*/
			
		 
			for(int i = 0; i<messages.length; i++) {
				if (i > 10 ) break;
				
				EmailMessage emsg = new EmailMessage();
				getPart(messages[i], emsg);
				emails.add(emsg); 
	//			msg.setFlag(Flags.Flag.SEEN, true);
	 
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
		
		return emails;
	}
	
	/**********************************************************************
     * Return email store
     **********************************************************************/
	abstract protected Store getStore();
	
	/**********************************************************************
     * Get email message attachement if any
     **********************************************************************/
	public void getPart(Part part, EmailMessage emsg ) throws Exception {
		if (part instanceof Message) {
			getEnvelope((Message) part, emsg);
		}
		
		String filename = part.getFileName();
		if (filename != null) {
			log.info("found file: " + filename);
		}

	   if (part.isMimeType("multipart/*")) {		 
			Multipart mp = (Multipart) part.getContent();
			level++;
			int count = mp.getCount();
			for (int i = 0; i < count; i++)
				getPart(mp.getBodyPart(i), emsg);
			level--;
		}

		if (saveAttachments && level != 0 && part instanceof MimeBodyPart
				&& !part.isMimeType("multipart/*")) {
			String disp = part.getDisposition();
			// many mailers don't include a Content-Disposition
			if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
				if (filename == null) {
					//filename = "Attachment" + attnum++;
					return;
				}
				log.info("Saving attachment to file " + filename);
				
				String file = saveFolder + filename;
				try {
					File f = new File(file);
					if (f.exists()) {
						throw new IOException("file exists");
					}
					((MimeBodyPart) part).saveFile(f);
					emsg.addAttachment(filename);
				} catch (IOException ex) {
					log.error("Failed to save attachment: " + ex);
				}
			}
		}
	}

	/**********************************************************************
     * Get email message parts.
     **********************************************************************/
	public static void getEnvelope(Message m, EmailMessage emsg ) throws Exception {
		 
		Address[] address;
		// FROM
		if ((address = m.getFrom()) != null && address.length > 0) {
			emsg.setFromDesc(address[0].toString());
		}

		// REPLY TO
		if ((address = m.getReplyTo()) != null && address.length > 0) {		 
			emsg.setReplyTo( address[0].toString());
		}

		// TO
		/*if ((address = m.getRecipients(Message.RecipientType.TO)) != null) {
			for (int j = 0; j < address.length; j++) {
				pr("TO: " + address[j].toString());
				InternetAddress ia = (InternetAddress) address[j];
				if (ia.isGroup()) {
					InternetAddress[] aa = ia.getGroup(false);
					for (int k = 0; k < aa.length; k++)
						pr("  GROUP: " + aa[k].toString());
				}
			}
		}*/

		// SUBJECT
		emsg.setSubject( m.getSubject());
		
		String content = m.getContent().toString();
		emsg.setContent(content);

		// DATE
		emsg.setSentDate( m.getSentDate()); 
		emsg.setReceiveDate(m.getReceivedDate());

		// X-MAILER
		String[] hdrs = m.getHeader("X-Mailer");
		if (hdrs != null){
			emsg.setxMailer(hdrs[0]);
		}
		
		// X-MAILER
		String[] hdrs2 = m.getHeader("Message-Id");
		if (hdrs2 != null){
			emsg.setMsgId(hdrs2[0]);
		}
				
		
	}


	public boolean isSaveAttachments() {
		return saveAttachments;
	}

	public void setSaveAttachments(boolean saveAttachments) {
		this.saveAttachments = saveAttachments;
	}

	public String getSaveFolder() {
		return saveFolder;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

}

