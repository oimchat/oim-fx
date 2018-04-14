package com.oim.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;

public class FileLockUtil {
	static Map<String, FileLockData> map = new HashMap<String, FileLockData>();

	public static boolean isLock(String filePath) {
		boolean lock = true;
		FileLockData fld = map.get(filePath);
		if (null == fld) {
			fld = new FileLockData(filePath);
			map.put(filePath, fld);
		}
		lock = fld.isLock();
		return lock;
	}

	public static void releaseLock(String filePath) {
		FileLockData fld = map.remove(filePath);
		if (null != fld) {
			fld.release();
		}
	}
}

class FileLockData {

	File file;
	RandomAccessFile randomAccessFile;
	FileLock fileLock;
	FileChannel fileChannel;

	public FileLockData(String filePath) {
		file = new File(filePath);
		try {
			randomAccessFile = new RandomAccessFile(file, "rw");
			fileChannel = randomAccessFile.getChannel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public RandomAccessFile getRandomAccessFile() {
		return randomAccessFile;
	}

	public void setRandomAccessFile(RandomAccessFile randomAccessFile) {
		this.randomAccessFile = randomAccessFile;
	}

	public FileLock getFileLock() {
		return fileLock;
	}

	public void setFileLock(FileLock fileLock) {
		this.fileLock = fileLock;
	}

	public FileChannel getFileChannel() {
		return fileChannel;
	}

	public void setFileChannel(FileChannel fileChannel) {
		this.fileChannel = fileChannel;
	}

	public boolean isLock() {
		boolean lock = true;

		if (null == fileLock) {
			try {
				fileLock = fileChannel.tryLock();
				if (null != fileLock) {
					lock = !fileLock.isValid();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			lock = !fileLock.isValid();
		}
		return lock;
	}

	public void release() {
		if (null != fileLock) {
			try {
				fileLock.release();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (null != fileChannel) {
			try {
				fileChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (null != randomAccessFile) {
			try {
				randomAccessFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}