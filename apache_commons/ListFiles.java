package apache_commons;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class ListFiles {
 
	public static void main(String[] args) {
		 String di = "c:/temp";
		 File tmp = new File(di);
		 File[] list = tmp.listFiles();
		 for (File str : list) {
			 if (str.isDirectory()) System.out.println(str);
		 }
		 System.out.println("\n\n" );
		 String[] ext = {"jar"};
		 //Collection<File> files = FileUtils.listFiles(tmp, ext, true);
		 Collection<File> files = FileUtils.listFiles(tmp, null, false);
		 for (File ff : files) {
			 System.out.println(ff );
		 }
	}

}
