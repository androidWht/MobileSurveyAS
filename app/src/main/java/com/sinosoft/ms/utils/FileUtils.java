package com.sinosoft.ms.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.os.Environment;

/**
 * 系统名：MobileSurvey 子系统名：文件处理类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:10:00 CST 2013
 */
public class FileUtils {
	private String SDPATH = null;

	public String getSDPATH() {
		return SDPATH;
	}

	public FileUtils() {
		if (isMounted()) {// 得到当前外部存储设备的目录
			SDPATH = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/mobileSurvey/";
		}
	}

	/**
	 * 判断SD卡是否存在
	 * */
	private Boolean isMounted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public File creatSDFile(String fileName) throws IOException {
		File file = null;
		if (isMounted()) {
			file = new File(SDPATH + fileName);
			file.createNewFile();
		}
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 *            目录名
	 * @return 创建目录
	 */
	public File creatSDDir(String dirName) {
		File dir = null;
		if (isMounted()) {
			dir = new File(SDPATH + dirName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		return dir;
	}

	public static File creatDir(String dirName) {
		File dir = null;
		dir = new File(dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 删除文件
	 * 
	 * @param filepath
	 */
	public static void deleteFile(String filepath) {
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
			} else {
				if (f.isFile()) {
					f.delete();
				}
			}
		}
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	/**
	 * SD卡上的文件
	 */
	public String getFilePath(String fileName) {
		String path = SDPATH + fileName;
		return path;
	}

	/**
	 * 将字节流写入到SD卡文件
	 * 
	 * @param path
	 *            SD卡路径
	 * @param fileName
	 *            文件名
	 * @param inputStream
	 *            字节流
	 * @return SD卡文件
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream inputStream) {
		File file = null;
		FileOutputStream fos = null;
		try {
			creatSDDir(path);
			file = creatSDFile(path + fileName);
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
	 * 将字符串写入到SD卡文件
	 * 
	 * @param path
	 *            SD卡路径
	 * @param fileName
	 *            文件名
	 * @param context
	 *            写入内容
	 * @return SD卡文件
	 */
	public File write2SDFromString(String path, String fileName, String context) {
		File file = null;
		FileOutputStream fos = null;
		BufferedWriter buf = null;
		try {
			creatSDDir(path);
			file = creatSDFile(path + fileName);
			fos = new FileOutputStream(file);
			buf = new BufferedWriter(new OutputStreamWriter(fos));
			buf.write(context);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				buf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param fileName
	 *            文件名
	 * @return fileContent 文件内容
	 */
	public static String readFile(String fileName) throws Exception {
		StringBuffer fileContent = new StringBuffer();
		File f = new File(fileName);
		FileReader fileReader = new FileReader(f);
		BufferedReader reader = new BufferedReader(fileReader);
		String line = "";
		while ((line = reader.readLine()) != null) {
			fileContent.append(line);
		}
		reader.close();
		return fileContent.toString();
	}

	/**
	 * 装一个字节数组写入到文件中
	 * 
	 * @param sdPath
	 *            SDCard目录
	 * @param fileName
	 *            文件名
	 * @param dataSource
	 *            写入数据源
	 * @return 成功为true 失败为false
	 */
	public boolean write2SDFromBytes(String sdPath, String fileName,
			byte[] dataSource) {
		boolean result = false;
		File file = null;
		FileOutputStream fos = null;
		try {
			creatSDDir(sdPath);
			file = creatSDFile(sdPath + File.separator + fileName);
			fos = new FileOutputStream(file);
			fos.write(dataSource, 0, dataSource.length);
			result = true;
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
		return result;
	}

	/**
	 * 装一个字节数组写入到文件中
	 * 
	 * @param sdPath
	 *            SDCard目录
	 * @param fileName
	 *            文件名
	 * @param dataSource
	 *            写入数据源
	 * @return 成功为true 失败为false
	 */
	public boolean write2SDFromOut(String sdPath, String fileName,
			ByteArrayOutputStream out) {
		boolean result = false;
		File file = null;
		FileOutputStream fos = null;
		try {
			creatSDDir(sdPath);
			file = creatSDFile(sdPath + File.separator + fileName);
			fos = new FileOutputStream(file);
			out.writeTo(fos);
			result = true;
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
		return result;
	}

	public File saveFile(byte date[], String fileName) {
		File file = new File(SDPATH + fileName);
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(date);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;

	}

	/**
	 * 压缩文件
	 * 
	 * @param filesPaths
	 *            文件路径数组
	 * @param zipFileName
	 *            压缩文件路径
	 * @throws Exception
	 *             异常
	 */
	public void zipFile(String[] filesPaths, String zipFileName)
			throws Exception {
		FileOutputStream out = null;
		ZipOutputStream zipOutput = null;
		try {
			out = new FileOutputStream(SDPATH + zipFileName);
			zipOutput = new ZipOutputStream(new BufferedOutputStream(out));
			for (int i = 0; i < filesPaths.length; i++) {
				String fileName = filesPaths[i];
				File filrTemp = new File(fileName);
				byte bytes[] = new byte[1024];
				ZipEntry entity = new ZipEntry(filrTemp.getName());
				zipOutput.putNextEntry(entity);
				FileInputStream temp = new FileInputStream(filrTemp);
				int length = 0;
				while ((length = temp.read(bytes)) != -1) {
					zipOutput.write(bytes, 0, length);
				}
				zipOutput.closeEntry();
			}
			zipOutput.flush();
			zipOutput.finish();
			LogFatory.d("debug", "压缩完成");
		} catch (Exception e) {
			LogFatory.e("error", "压缩失败:" + e.getLocalizedMessage());
			throw new IllegalArgumentException(e.getMessage());
		} finally {
			zipOutput.close();
		}
	}

	/**
	 * 读取输入流内容
	 * 
	 * @param in
	 *            输入流
	 * @return 输入流内容
	 * @throws Exception
	 */
	public String getString(InputStream in) throws Exception {
		byte[] bty = new byte[1024];
		StringBuffer result = new StringBuffer();
		int length = 0;
		try {
			while ((length = in.read(bty)) != -1) {
				result.append(new String(bty, 0, length));
			}
		} catch (IOException ioe) {
			throw new IOException(ioe.getMessage());
		}
		return result.toString();
	}

	/**
	 * 读取输入流内容(一次读一行)
	 * 
	 * @param in
	 *            输入流
	 * @return 输入流内容
	 * @throws Exception
	 */
	public String line2String(InputStream in) {
		StringBuffer result = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String str = null;
			while ((str = reader.readLine()) != null) {
				result.append(str);
			}
		} catch (Exception e) {

		}
		return result.toString();
	}

	/**
	 * 关闭流
	 * 
	 * @param ins
	 *            输入流
	 * @param fos
	 *            文件输出流
	 */
	public void close(InputStream ins, FileOutputStream fos) {
		try {
			if (null != ins) {
				ins.close();
			}
			if (null != fos) {
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}