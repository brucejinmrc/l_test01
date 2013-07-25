package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFolder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String sourceDir = "C:/jintemp/";
		String zipFile = "C:/temp/zipdemo.zip";
		zipDir(sourceDir, zipFile);
		

	}

	private static void zipDir(String sourceDir, String zipFile) {
		try {
			// create object of FileOutputStream
			FileOutputStream fout = new FileOutputStream(zipFile);
			ZipOutputStream zout = new ZipOutputStream(fout);
			File fileSource = new File(sourceDir);
			addDirectory(zout, fileSource);
		
			zout.close();

			System.out.println("Zip file has been created!");

		} catch (IOException ioe) {
			System.out.println("IOException :" + ioe);
		}
	}
	
	private static void addDirectory(ZipOutputStream zout, File fileSource) {

		// get sub-folder/files list
		File[] files = fileSource.listFiles();

		System.out.println("Adding directory " + fileSource.getName());

		for (int i = 0; i < files.length; i++) {
			// if the file is directory, call the function recursively
			if (files[i].isDirectory()) {
				addDirectory(zout, files[i]);
				continue;
			}

			/*
			 * we are here means, its file and not directory, so add it to the
			 * zip file
			 */

			try {
				System.out.println("Adding file " + files[i].getName());
				byte[] buffer = new byte[1024];
				FileInputStream fin = new FileInputStream(files[i]);

				zout.putNextEntry(new ZipEntry(files[i].getName()));

				/*
				 * After creating entry in the zip file, actually write the
				 * file.
				 */
				int length;

				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}

				/*
				 * After writing the file to ZipOutputStream, use
				 * 
				 * void closeEntry() method of ZipOutputStream class to close
				 * the current entry and position the stream to write the next
				 * entry.
				 */

				zout.closeEntry();

				// close the InputStream
				fin.close();

			} catch (IOException ioe) {
				System.out.println("IOException :" + ioe);
			}
		}

	}

}