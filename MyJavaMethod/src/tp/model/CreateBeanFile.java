package tp.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * 创建 bean
 * @author tp
 *
 */
public class CreateBeanFile implements Serializable{

	
	
	public static   String  filePath ="D:/createfile";// java
	
	
	
	private File mFile ;
	private BufferedWriter bw;
	
	private String newFileName ;
	
	/**
	 * 创建file
	 * @return
	 */
	public File createFile(String fileName){
		
		newFileName = fileName;
	
		File file = new File(filePath);
		file.mkdirs();
		
		file = new File(filePath, newFileName);
		mFile = file;
		
		return file;
	}
	
	
	/**
	 * 写一行
	 * @param line
	 * @throws IOException
	 */
	public void writeLine(String line) throws IOException{
		
		if (bw == null) {
			bw = new BufferedWriter(new FileWriter(mFile));
			
		}
		
		bw.write(line);
		bw.newLine();
		bw.flush();
	}
	
	
	/**
	 * 关闭流
	 * @throws IOException
	 */
	public void  close() throws IOException{
		if (bw!=null) {
			bw.flush();
			bw.close();
		}
	}
}
