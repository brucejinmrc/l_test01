package lucene;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailMessage {

	private String folder;
	
	private String subject;
	private String content;
	private String fromDesc; //MySQL <webmaster@mysql.com>
	private String fromEmail; //webmaster@mysql.com
	private String replyTo;
	private Date sentDate;
	private Date receiveDate;
	
	private List<String> attachments;
	private String xMailer;
	private String msgId;
	
	/***********************************************************
	 * Constructor
	 **********************************************************/
	public EmailMessage() {
		super();
		attachments = new ArrayList<String>();
	}

	/***********************************************************
	 * Constructor
	 **********************************************************/
	public void addAttachment(String filename) {
		attachments.add(filename);
	}
 
	/***********************************************************
	 *
	 **********************************************************/
	public String toString() {
		String str = subject + " from: " + fromDesc;
		return str;
	}
	
	public String getFolder() {
		return folder;
	}


	public void setFolder(String folder) {
		this.folder = folder;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getFromDesc() {
		return fromDesc;
	}


	public void setFromDesc(String fromDesc) {
		this.fromDesc = fromDesc;
	}


	public String getFromEmail() {
		return fromEmail;
	}


	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}


	public Date getSentDate() {
		return sentDate;
	}


	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}


	public Date getReceiveDate() {
		return receiveDate;
	}


	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

 

	public String getxMailer() {
		return xMailer;
	}


	public void setxMailer(String xMailer) {
		this.xMailer = xMailer;
	}


	public String getReplyTo() {
		return replyTo;
	}


	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}



	public List<String> getAttachments() {
		return attachments;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
