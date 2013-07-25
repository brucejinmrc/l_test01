/*
 * TestJavac.java   Created on Jul 21, 2004, 10:54:49 AM
 */
package test;

/**
 * 
 * @author jin
 */
public class TestJavac {

	//private com.sun.tools.javac.Main tool = new com.sun.tools.javac.Main();

	public static void main(String[] args) {

		new TestJavac().compile();

	}

	void compile() {
		String filename = "c:\\mrcjava\\WEB-INF\\classes\\MRCTESTLIB01\\I00010sb.java";
		String userdir = System.getProperty("user.dir");
		System.out.println("user dir = " + userdir);
		
		String p1 = "-classpath";
		String p2 = "c:/jinjava/jar1/servlet.jar;c:/jinjava/jar1/mrcjs11.jar";
		String[] ar = new String[] {p1,p2, filename };
		
		int status = com.sun.tools.javac.Main.compile(ar);
		
		 switch (status) {
	        case 0:        
	            break;
	        case 1: showMsg(status, "Compile status: ERROR"); break;
	        case 2: showMsg(status, "Compile status: CMDERR"); break;
	        case 3: showMsg(status, "Compile status: SYSERR"); break;
	        case 4: showMsg(status, "Compile status: ABNORMAL"); break;
	        default:
	            showMsg(status, "Compile status: Unknown exit status");
	    }


	}

	void showMsg(int status, String msg) {
		System.out.println("status=" + status + ": " + msg);
		
	}
}