package com.im.common.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @description:清除输出文件内容
 * @author: Only
 * @date: 2016-07-13 05:53:54
 */
@Component
public class SessionTask {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	public void sessionClear() {
	}

	/**
	 * 清理linux下面输出文件越来越大
	 */
	public void outClear() {
		File file = new File("logs/catalina.out");
		logger.debug("outClear......" + file.getAbsolutePath());
		logger.debug("fileExists：" + file.exists());
		logger.debug("fileAbsolutePath：" + file.getAbsolutePath());
	
		if (file.exists()) {
			// file.delete();
			// String filePath = file.getAbsolutePath();
			//
			// //String command1 = "rm -f " + filePath;
			// String command2 = "cat /dev/null > " + filePath;
			//
			//// System.out.println("command1:" + command1);
			// System.out.println("command2:" + command2);
			//
			// Process process = null;
			// try {
			//// process = Runtime.getRuntime().exec(command1);
			//// process.waitFor();
			//
			// process = Runtime.getRuntime().exec(command2);
			// process.waitFor();
			// } catch (IOException e) {
			// e.printStackTrace();
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }

//			String sh = "/bin/clear.sh"; // 程序路径
//			Process process = null;
//			String command1 = "chmod 777 " + sh;
//
//			String command2 = "sh " + sh;
//			try {
//				process = Runtime.getRuntime().exec(command1);
//				process.waitFor();
//
//				process = Runtime.getRuntime().exec(command2);
//				process.waitFor();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

			try {
				FileWriter fw = new FileWriter(file);
				fw.write("");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
