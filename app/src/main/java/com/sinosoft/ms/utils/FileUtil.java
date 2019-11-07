package com.sinosoft.ms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 系统名 查勘相机系统 类名 FileUtil.java 类作用 文件工具类
 * 
 * @author wuhaijie
 * @createTime 2014-1-24 下午4:50:29
 */
public class FileUtil {

	/**
	 * 如果文件夹不存在，则创建
	 * 
	 * @param path
	 * @return
	 */
	public static File createNewDirIfNotExist(String path) {
		File file = new File(path);
		if (!isFileExist(file)) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 创建新文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static File createNewDir(String path) {
		File file = new File(path);
		file.mkdirs();
		return file;
	}

	/**
	 * 如果文件不存在,则创建
	 * 
	 * @param path
	 * @return
	 */
	public static File createNewFileIfNotExist(String path) {
		File file = new File(path);
		if (!isFileExist(file)) {
			file = createNewFile(path);
		}
		return file;
	}

	/**
	 * 如果文件不存在,则创建
	 * 
	 * @param dirPath
	 * @param fileName
	 * @return
	 */
	public static File createNewFileIfNotExist(String dirPath, String fileName) {
		String path = "";
		path = setSlash(dirPath) + fileName;
		return createNewFileIfNotExist(path);
	}

	/**
	 * 无论如何创建新文件
	 * 
	 * @param path
	 * @return
	 */
	public static File createNewFile(String path) {
		String dirPath = path.substring(0, path.lastIndexOf("/") + 1);
		createNewDirIfNotExist(dirPath);
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return file;
	}

	public static File getFile(String path) {
		File file = new File(path);
		return file;
	}

	public static boolean isFileExist(File file) {
		if (null != file && file.exists()) {
			return true;
		}
		return false;
	}

	public static boolean isFileExist(String path) {
		File file = new File(path);
		return isFileExist(file);
	}

	public static boolean isDirectoryExist(String path) {
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			return true;
		}
		return false;
	}

	public static void deleteFile(String filepath){
		File f = new File(filepath);// 定义文件路径
		if (f.exists()) {
			if (f.isDirectory()) {// 判断是文件还是目录
				if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
					f.delete();
				} else {// 若有则把文件放进数组，并判断是否有下级目录
					File delFile[] = f.listFiles();
					int i = f.listFiles().length;
					for (int j = 0; j < i; j++) {
						if (delFile[j].isDirectory()) {
							deleteFile(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
						}
						delFile[j].delete(); // 删除文件
					}
				}
				f.delete();
			}else{
				if(f.isFile()){
					f.delete();
				}
			}
		}
	}

	public static void deleteFile(File file) {
		deleteFile(file.getAbsoluteFile());
	}

	public static void renameFile(String path, String newPath) {
		File file = new File(path);
		if (isFileExist(path)) {
			file.renameTo(new File(newPath));
		}
	}

	public static int getIndex(String dirPath, String fileName) {
		int index = 0;
		File[] list = createNewDirIfNotExist(dirPath).listFiles();
		for (int i = 0; i < list.length; i++) {
			if (list[i].getAbsolutePath().equals(fileName)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * 返回文件的大小
	 * 
	 * @param absolutePath
	 * @return
	 */
	public static long getFileSize(String absolutePath) {
		File file = new File(absolutePath);
		if (!isFileExist(file)) {
			return 0;
		}
		return file.length();
	}

	/**
	 * 设置反斜杠
	 * 
	 * @param path
	 * @return
	 */
	public static String setSlash(String path) {
		char a = path.charAt(path.length() - 1);
		if (a == '/') {
			return path;
		}
		return path + "/";
	}

	/**
	 * 将绝对路径拆分成路径和文件名
	 * 
	 * @param absolutePath
	 * @return
	 */
	public static String[] splitPath(String absolutePath) {
		String str[] = new String[2];
		str[0] = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		str[1] = absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
		return str;
	}

	/**
	 * 将字节流写入到指定文件
	 * 
	 * @param path
	 *            路径
	 * @param fileName
	 *            文件名
	 * @param inputStream
	 *            字节流
	 * @return SD卡文件
	 */
	public static File write2SDFromInput(String path, String fileName,
			InputStream inputStream) {
		String absolutePath = setSlash(path)+fileName;
		return write2SDFromInput(absolutePath, inputStream);
	}
	
	/**
	 * 将字节流写入到指定文件
	 * @param absolutePath		文件绝对路径
	 * @param inputStream			字节流
	 * @return							文件
	 */
	public static File write2SDFromInput(String absolutePath,
			InputStream inputStream) {
		File file = null;
		FileOutputStream fos = null;
		try {
			file = createNewFileIfNotExist(absolutePath);
			fos = new FileOutputStream(file);
			byte buf[] = new byte[128];
			do {
				int numread = inputStream.read(buf);
				if (numread <= 0) {
					break;
				}
				fos.write(buf, 0, numread);
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	/**
	 * 将字符串写入到指定的路径
	 * @param str
	 * @param absolutePath
	 */
	public static File write2FileFromString(String absolutePath,String str){
		FileOutputStream out = null;   
		File file = null;
        try {
        	file = createNewFileIfNotExist(absolutePath);
			out = new FileOutputStream(new File(absolutePath));  
			out.write(str.getBytes());   
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        return file;
	}
	
	public static File write2FileFromString(String dir,String fileName,String str){
		String absolutePath = setSlash(dir) + fileName;
		return write2FileFromString(absolutePath, str);
	}

	/**
	 * * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 * */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				// 文件存在时
				InputStream inStream = new FileInputStream(oldPath);
				// 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024 * 5];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					// 字节数 文件大小
//					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 复制文件夹
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(String sourceDir, String targetDir) {
		try {
			// 新建目标目录
			(new File(targetDir)).mkdirs();
			// 获取源文件夹当前下的文件或目录
			File[] file = (new File(sourceDir)).listFiles();
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// 源文件
					File sourceFile = file[i];
					// 目标文件
					File targetFile = new File(
							new File(targetDir).getAbsolutePath() + File.separator
									+ file[i].getName());
					copyFile(sourceFile.getAbsolutePath(),
							targetFile.getAbsolutePath());
				}
				if (file[i].isDirectory()) {
					// 准备复制的源文件夹
					String dir1 = sourceDir + "/" + file[i].getName();
					// 准备复制的目标文件夹
					String dir2 = targetDir + "/" + file[i].getName();
					copyDirectiory(dir1, dir2);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<String> readLines(String filePath) {
		List<String> list = new ArrayList<String>();
		BufferedReader br = null;
		FileReader reader = null;
		try {
			reader = new FileReader(filePath);
			br = new BufferedReader(reader);
			String s1 = null;
			while ((s1 = br.readLine()) != null) {
				list.add(s1);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != br)
					br.close();
				if (null != reader)
					reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 在路径中创建".nomedia"文件
	 * @param dirPath
	 */
	public static void createNomediaFile(String dirPath){
		createNewFileIfNotExist(dirPath, ".nomedia");
	}
}
