package com.test.file.upload;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author XiaHui
 * @date 2014年10月29日 上午11:41:05
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

public class HttpUpload {
	public static void main(String args[]) {
		
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("userId", "123456");
		String filepath = "E:/Temp/00.txt";
		String http = "http://127.0.0.1:8080/api/v1/oim/file/upload.do";
		OnlyHttpClient3Upload h=new OnlyHttpClient3Upload();
		File file = new File(filepath);
		String text=h.upload(http, "file", file, dataMap);
		System.out.println(text);
	}

	public static void upload() {
		String targetURL = null;// TODO 指定URL
		File targetFile = null;// TODO 指定上传文件

		targetFile = new File("E:/Temp/397677819-戴.txt");
		targetURL = "http://127.0.0.1:8080/oim-file-server/file/uploads.do"; // servleturl
		PostMethod filePost = new PostMethod(targetURL);

		try {

			// 通过以下方法可以模拟页面参数提交
			// filePost.setParameter("name", "中文");
			// filePost.setParameter("pass", "1234");

			Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			String body = filePost.getResponseBodyAsString();
			System.out.println(body);
			if (status == HttpStatus.SC_OK) {
				System.out.println("上传成功");
				// 上传成功
			} else {
				System.out.println("上传失败");
				// 上传失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
	}

	public static void uploads() {

		String targetURL = "http://127.0.0.1:8080/oim-file-server/file/upload.do"; // 指定URLservleturl
		PostMethod filePost = new PostMethod(targetURL);

		try {
			File targetFile1 = new File("E:/Temp/397677819-戴.txt");// 指定上传文件
			File targetFile2 = new File("E:/Temp/fhjnn1.jpg");// 指定上传文件
			// 通过以下方法可以模拟页面参数提交
			// filePost.setParameter("name", "中文");
			// filePost.setParameter("pass", "1234");
			// filePost.setRequestHeader("Content-Type",
			// "application/octet-stream;charset=UTF-8");
			FilePart fp1 = new FilePart(targetFile1.getName(), targetFile1, null, "UTF-8");
			fp1.setCharSet("UTF-8");
			FilePart fp2 = new FilePart(targetFile2.getName(), targetFile2);
			fp2.setCharSet("UTF-8");
			Part[] parts = { fp1, fp2 };
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			String body = filePost.getResponseBodyAsString();
			System.out.println(body);
			if (status == HttpStatus.SC_OK) {
				System.out.println("上传成功");
				// 上传成功
			} else {
				System.out.println("上传失败");
				// 上传失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
	}

	public static void uploadss() {
		try {
			URL url = new URL("http://127.0.0.1:8080/oim-file-server/file/upload.do");
			// 换行符
			final String newLine = "\r\n";
			final String boundaryPrefix = "--";
			// 定义数据分隔线
			String BOUNDARY = "========7d4a6d158c9";
			// 服务器的域名
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置为POST情
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求头参数
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			// 上传文件
			File file = new File("E:/Temp/397677819-戴.txt");

			String fileName = file.getName();
			StringBuilder sb = new StringBuilder();
			sb.append(boundaryPrefix);
			sb.append(BOUNDARY);
			sb.append(newLine);
			// 文件参数,photo参数名可以随意修改
			sb.append("Content-Disposition: form-data;name=\"photo\";filename=\"" + fileName + "\"" + newLine);
			sb.append("Content-Type:application/octet-stream");
			// 参数头设置完以后需要两个换行，然后才是参数内容
			sb.append(newLine);
			sb.append(newLine);

			// 将参数头的数据写入到输出流中
			out.write(sb.toString().getBytes());

			// 数据输入流,用于读取文件数据
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			byte[] bufferOut = new byte[1024];
			int bytes = 0;
			// 每次读1KB数据,并且将文件数据写入到输出流中
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			// 最后添加换行
			out.write(newLine.getBytes());
			in.close();

			// 定义最后数据分隔线，即--加上BOUNDARY再加上--。
			byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();
			// 写上结尾标识
			out.write(end_data);
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			// BufferedReader reader = new BufferedReader(new InputStreamReader(
			// conn.getInputStream()));
			// String line = null;
			// while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			// }

		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
	}
	
	public static void u(){
//		File file = new File("E:/Temp/397677819-戴.txt", ContentType.DEFAULT_BINARY);
//		HttpPost post = new HttpPost("http://echo.200please.com");
//		FileBody fileBody = new FileBody(file);
//		StringBody stringBody1 = new StringBody("Message 1", ContentType.MULTIPART_FORM_DATA);
//		StringBody stringBody2 = new StringBody("Message 2", ContentType.MULTIPART_FORM_DATA);
//		// 
//		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//		builder.addPart("upfile", fileBody);
//		builder.addPart("text1", stringBody1);
//		builder.addPart("text2", stringBody2);
//		HttpEntity entity = builder.build();
//		//
//		post.setEntity(entity);
//		HttpResponse response = client.execute(post);
	}
}