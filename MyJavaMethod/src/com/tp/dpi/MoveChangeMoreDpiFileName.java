package com.tp.dpi;

import java.io.File;
import java.lang.reflect.Field;

/**
 * 
 * 工具类 当你与到很坑爹的 多个dpi 需要改名字的时候咋办
 * @author tp
 *
 */
public class MoveChangeMoreDpiFileName {
	
	
	// 文件夹路径
	static String filePath ="C:\\Users\\Administrator\\Desktop\\Android_newnew\\Android";
	
	//改名称后移动到
	static String newFilePath ="D:/bbb";
	
	//原来的名称
	static String oldName ="bg_screen_05.png";
	
	// 新名称
	static String newName ="dinuan_bg_screen_05.png";
	
	public static void main(String[] args) {
		
		File root = new File(filePath);
		
		if (!root.exists() || !root.isDirectory() ) {
			System.out.println("目录不对");
			return ;
		}
	
		// 多个文件夹
		File[] files = root.listFiles(); 
	
		System.out.println("开始了");
			
		for (int i = 0; i < files.length; i++) {
			File curFile = files[i];
			
			String curFileName = curFile.getName();
			
			File newFileD = new File(newFilePath,curFileName);
			newFileD.mkdirs();
			
			File oldFile =  getFileByFileName(curFile, oldName);
			System.out.println("文件处理开始："+ oldName);
			if (oldFile != null) { //存在
				System.out.println("处理：" + oldFile.getPath());

				File newFile = new File(newFileD.getPath(),newName);
				oldFile.renameTo(newFile);
				
			}
			System.out.println("文件处理结束："+ oldName);
		}
		
		System.out.println("搞定了");
		
	}
	
	/**
	 * 查找文件
	 * @param root
	 * @param fileName
	 * @return
	 */
	public static File getFileByFileName(File root ,String fileName ){
		
		// 多个文件夹
		File[] files = root.listFiles(); 
		
		for (int i = 0; i < files.length; i++) {
			File curFile = files[i]; 
			if (curFile.getName().equals(fileName)) {
				return curFile;
			}
		}
		
		return null;
	}
	 
}
