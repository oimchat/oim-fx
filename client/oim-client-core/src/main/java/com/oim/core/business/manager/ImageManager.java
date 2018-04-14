package com.oim.core.business.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.oim.common.chat.util.WebImagePathUtil;
import com.oim.common.component.file.FileHttpDownload;
import com.oim.common.component.file.action.FileAction;
import com.oim.common.util.FileNameUtil;
import com.oim.core.business.box.PathBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.sender.FileSender;
import com.oim.core.common.AppConstant;
import com.oim.core.common.action.BackAction;
import com.oim.core.common.action.BackInfo;
import com.oim.core.common.action.CallBackAction;
import com.oim.core.common.component.FileHttpUpload;
import com.oim.core.common.component.file.FileHandleInfo;
import com.oim.core.common.component.file.FileInfo;
import com.only.common.lib.util.OnlyJsonUtil;
import com.only.common.result.Info;
import com.only.common.util.OnlyFileUtil;
import com.only.common.util.OnlyMD5Util;
import com.only.common.util.OnlyStringUtil;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.app.base.task.ExecuteTask;
import com.onlyxiahui.im.bean.GroupHead;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;
import com.onlyxiahui.im.message.data.FileData;
import com.onlyxiahui.im.message.data.chat.ImageValue;
import com.onlyxiahui.im.message.data.chat.Item;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月15日 下午7:52:44
 * @version 0.0.1
 */
public class ImageManager extends AbstractManager {

	private String fileServerAddress;
	FileHttpUpload fhu = new FileHttpUpload();
	FileHttpDownload fhd = new FileHttpDownload();

	public ImageManager(AppContext appContext) {
		super(appContext);
	}

	public void downloadUserHead(final List<UserHead> userHeadList,final BackAction<List<FileHandleInfo>> backAction) {
		final	BackInfo backInfo = new BackInfo();
		final List<FileHandleInfo> list = new ArrayList<FileHandleInfo>();
		ExecuteTask et = new ExecuteTask() {
			@Override
			public void execute() {
				PathBox pb = appContext.getBox(PathBox.class);
				if (null != userHeadList) {
					for (UserHead userHead : userHeadList) {
						if (null != userHead) {
							String id = userHead.getHeadId();
							String name = userHead.getFileName();
							String savePath = pb.getUserHeadPath();
							String fileName = savePath + name;
							File file = new File(fileName);
							FileHandleInfo info = new FileHandleInfo();
							FileInfo fi = new FileInfo();
							fi.setId(id);
							if ((file.exists() && !file.isDirectory())) {
								fi.setFile(file);
								info.setFileInfo(fi);
								info.setSuccess(true);
							} else {
								String http = fileServerAddress + "/head/user/download.do?id=" + id;
								downloadImage(http, savePath, id, info, fi);
							}
							list.add(info);
						}
					}
				}
				backAction.back(backInfo, list);
			}
		};
		handle(et, backAction);
	}

	public void downloadUserHead(final UserHead userHead, final  BackAction<FileHandleInfo> backAction) {
		final BackInfo backInfo = new BackInfo();
		final FileHandleInfo info = new FileHandleInfo();
		ExecuteTask et = new ExecuteTask() {
			@Override
			public void execute() {
				PathBox pb = appContext.getBox(PathBox.class);
				if (null != userHead) {
					String id = userHead.getHeadId();
					String name = userHead.getFileName();
					String savePath = pb.getUserHeadPath();
					String fileName = savePath + name;
					File file = new File(fileName);
					FileInfo fi = new FileInfo();
					fi.setId(id);
					if ((file.exists() && !file.isDirectory())) {
						fi.setFile(file);
						info.setFileInfo(fi);
						info.setSuccess(true);
					} else {
						String http = fileServerAddress + "/head/user/download.do?id=" + id;
						downloadImage(http, savePath, id, info, fi);
					}
				}
				backAction.back(backInfo, info);
			}
		};
		handle(et, backAction);
	}

	public void downloadGroupHeadList(final List<GroupHead> groupHeadList, final BackAction<List<FileHandleInfo>> backAction) {
		final BackInfo backInfo = new BackInfo();
		final List<FileHandleInfo> list = new ArrayList<FileHandleInfo>();
		ExecuteTask et = new ExecuteTask() {
			@Override
			public void execute() {
				PathBox pb = appContext.getBox(PathBox.class);
				if (null != groupHeadList) {
					for (GroupHead groupHead : groupHeadList) {
						if (null != groupHead) {
							String id = groupHead.getHeadId();
							String name = groupHead.getFileName();
							String savePath = pb.getGroupHeadPath();
							String fileName = savePath + name;
							File file = new File(fileName);
							FileHandleInfo info = new FileHandleInfo();
							FileInfo fi = new FileInfo();
							fi.setId(id);
							if ((file.exists() && !file.isDirectory())) {
								fi.setFile(file);
								info.setFileInfo(fi);
								info.setSuccess(true);
							} else {
								String http = fileServerAddress + "/head/group/download.do?id=" + id;
								downloadImage(http, savePath, id, info, fi);
							}
							list.add(info);
						}
					}
				}
				backAction.back(backInfo, list);
			}
		};
		handle(et, backAction);
	}

	/**
	 * 上传用户头像
	 * 
	 * @author XiaHui
	 * @date 2017年6月18日 上午8:39:23
	 * @param file
	 * @param backAction
	 */
	public void uploadUserHeadImage(File file, BackAction<FileHandleInfo> backAction) {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		String userId = (null == user) ? null : user.getId();
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("userId", userId);
		String action = "/head/user/upload.do";
		uploadHeadImage(action, file, dataMap, backAction);
	}

	/**
	 * 上传群头像
	 * 
	 * @author XiaHui
	 * @date 2017年6月18日 上午8:39:37
	 * @param groupId
	 * @param file
	 * @param backAction
	 */
	public void uploadGroupHeadImage(String groupId, File file, BackAction<FileHandleInfo> backAction) {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("groupId", groupId);
		String action = "/head/group/upload.do";
		uploadHeadImage(action, file, dataMap, backAction);
	}

	public void uploadHeadImage(final String action,final  File file,final  Map<String, String> dataMap,final  BackAction<FileHandleInfo> backAction) {
		final BackInfo backInfo = new BackInfo();
		final FileHandleInfo fileHandleInfo = new FileHandleInfo();
		ExecuteTask et = new ExecuteTask() {
			@Override
			public void execute() {
				String http = fileServerAddress + action;
				if (file.exists()) {
					FileAction<FileInfo> fileAction = new FileAction<FileInfo>() {

						@Override
						public void progress(long speed, long size, long up, double percentage) {

						}

						@Override
						public void success(FileInfo fileInfo) {
							fileHandleInfo.setFileInfo(fileInfo);
							fileHandleInfo.setSuccess(true);
						}

						@Override
						public void lost(FileInfo fileInfo) {
							fileHandleInfo.setFileInfo(fileInfo);
							fileHandleInfo.setSuccess(false);
						}

					};
					fhu.upload(http, file, dataMap, fileAction);
				} else {
					FileInfo fileInfo = new FileInfo();
					fileHandleInfo.setFileInfo(fileInfo);
					fileHandleInfo.setSuccess(false);
				}
				backAction.back(backInfo, fileHandleInfo);
			}
		};
		handle(et, backAction);
	}

	/**
	 * 上传聊天内容的图片
	 * 
	 * @author XiaHui
	 * @date 2017年6月17日 下午9:51:34
	 * @param items
	 * @param backAction
	 */
	public void uploadImage(final List<Item> items, final BackAction<List<FileHandleInfo>> backAction) {

		final List<FileHandleInfo> handleList = new ArrayList<FileHandleInfo>();
		final BackInfo backInfo = new BackInfo();

		final ExecuteTask et = new ExecuteTask() {
			@Override
			public void execute() {
				PersonalBox pb=appContext.getBox(PersonalBox.class);
				final UserData user = pb.getUserData();
				String userId = (null == user) ? null : user.getId();
				final 	Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("userId", userId);
				if (null != items) {
					for (final Item i : items) {
						if (Item.type_image.equals(i.getType())) {
							String filePath = i.getValue();

							if (StringUtils.isNotBlank(filePath)) {
								
								final String localSavePath = AppConstant.userHome + "/" + AppConstant.app_home_path + "/" + user.getNumber() + "/image/";
								
								if (filePath.startsWith("file:")) {
									String absolutePath=WebImagePathUtil.fileImageSourceToPath(filePath);
									File file = new File(absolutePath);
									if (file.exists()) {
										upload(file, localSavePath, dataMap, i, handleList);
									}
								} else {

									String http = filePath;
									
									FileAction<File> fileAction = new FileAction<File>() {

										@Override
										public void progress(long speed, long size, long finishSize, double percentage) {

										}

										@Override
										public void success(File file) {
											upload(file, localSavePath, dataMap, i, handleList);
										}

										@Override
										public void lost(File file) {
											i.setValue("");
										}
									};
									String md5=OnlyMD5Util.md5L32(http);
									fhd.download(http,"GET", localSavePath, md5, true, fileAction);
								}
							}
						}
					}
				}
				backAction.back(backInfo, handleList);
			}
		};
		handle(et, backAction);
	}

	private void upload(final File file, final String localSavePath, final Map<String, String> dataMap,final  Item i,final  List<FileHandleInfo> handleList) {
		if (file.exists()) {
			final FileHandleInfo fileHandleInfo = new FileHandleInfo();

			final String fileName = file.getName();
			String http = fileServerAddress + "/image/upload.do";
			FileAction<FileInfo> fileAction = new FileAction<FileInfo>() {

				@Override
				public void progress(long speed, long size, long up, double percentage) {

				}

				@Override
				public void success(FileInfo fileInfo) {

					fileHandleInfo.setFileInfo(fileInfo);
					fileHandleInfo.setSuccess(true);
					handleList.add(fileHandleInfo);

					String suffixName = FileNameUtil.getSuffixName(fileName);
					String id = fileInfo.getId();
					// String value = id + "," + suffixName;

					ImageValue iv = new ImageValue();
					iv.setId(id);
					iv.setExtension(suffixName);
					iv.setType("1");
					iv.setName(fileName);

					String json = OnlyJsonUtil.objectToJson(iv);

					i.setValue(json);

					String name = id + "." + suffixName;
					// String path = AppConstant.userHome + "/" +
					// AppConstant.app_home_path + "/" + user.getNumber() +
					// "/image/" + name;
					String path = localSavePath + name;
					OnlyFileUtil.checkOrCreateFile(path);
					File newFile = new File(path);
					try {
						FileUtils.copyFile(file, newFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void lost(FileInfo fileInfo) {

					fileHandleInfo.setFileInfo(fileInfo);
					fileHandleInfo.setSuccess(false);
					handleList.add(fileHandleInfo);

					i.setValue("");
				}
			};
			fhu.upload(http, file, dataMap, fileAction);
		} else {
			i.setValue("");
		}
	}

	/**
	 * 处理本地图片
	 * 
	 * @author XiaHui
	 * @date 2017年6月17日 下午9:50:29
	 * @param items
	 * @param callBackAction
	 */
	public void handleLocalImage(final List<Item> items,final  CallBackAction<List<FileData>> callBackAction) {
		ExecuteTask et = new ExecuteTask() {
			@Override
			public void execute() {
				List<FileData> list = new ArrayList<FileData>();
				if (null != items) {
					for (Item i : items) {
						if (Item.type_image.equals(i.getType())) {
							String imageInfo = i.getValue();
							if (null != imageInfo && !"".equals(imageInfo)) {
								
								if(OnlyJsonUtil.mayBeJSON(imageInfo)){
									try {
										ImageValue iv=OnlyJsonUtil.jsonToObject(imageInfo, ImageValue.class);
									
										String id =iv.getId();
										String extension=iv.getExtension();
										FileData fd = new FileData();
										
										String name = id + "." + extension;
										fd.setName(name);
										fd.setId(id);
										list.add(fd);
										
									} catch (Exception e) {
									}
								}
							}
						}
					}
				}
				callBackAction.execute(list);
			}
		};
		appContext.add(et);
	}

	/**
	 * 下载聊天内容的图片
	 * 
	 * @author XiaHui
	 * @date 2017年6月17日 下午9:48:33
	 * @param items
	 * @param backAction
	 */
	public void downloadImage(final List<Item> items,final  BackAction<List<FileHandleInfo>> backAction) {
		final List<FileHandleInfo> handleList = new ArrayList<FileHandleInfo>();
		final BackInfo backInfo = new BackInfo();

		ExecuteTask et = new ExecuteTask() {
			@Override
			public void execute() {
				PersonalBox pb=appContext.getBox(PersonalBox.class);
				UserData user = pb.getUserData();
				if (null != items) {
					for (Item item : items) {
						if (Item.type_image.equals(item.getType())) {
							String imageInfo = item.getValue();
							if (null != imageInfo && !"".equals(imageInfo)) {
								
								if(OnlyJsonUtil.mayBeJSON(imageInfo)){
									try {
										ImageValue iv=OnlyJsonUtil.jsonToObject(imageInfo, ImageValue.class);
									
										String id =iv.getId();
										String extension=iv.getExtension();
										
										
										String name = id + "." + extension;
										String savePath = AppConstant.userHome + "/" + AppConstant.app_home_path + "/" + user.getNumber() + "/image/";
										String fileName = savePath + name;
										File file = new File(fileName);
										FileHandleInfo fhi = new FileHandleInfo();
										FileInfo fi = new FileInfo();
										fi.setId(id);
										if ((file.exists() && !file.isDirectory())) {
											fi.setFile(file);
											fhi.setFileInfo(fi);
											fhi.setSuccess(true);
										} else {
											String http = fileServerAddress + "/image/download.do?id=" + id;
											downloadImage(http, savePath, id, fhi, fi);
										}
										handleList.add(fhi);
									} catch (Exception e) {
									}
								}
							}
						}
					}
				}
				backAction.back(backInfo, handleList);
			}
		};
		handle(et, backAction);
	}

	/**
	 * 处理图片上传
	 * 
	 * @author XiaHui
	 * @date 2017年6月18日 下午6:13:55
	 * @param http
	 * @param file
	 * @param dataMap
	 * @param fileHandleInfo
	 */
	public void uploadImage(String http, File file, Map<String, String> dataMap,final  FileHandleInfo fileHandleInfo) {
		if (file.exists()) {
			FileAction<FileInfo> fileAction = new FileAction<FileInfo>() {

				@Override
				public void progress(long speed, long size, long up, double percentage) {

				}

				@Override
				public void success(FileInfo fileInfo) {
					fileHandleInfo.setFileInfo(fileInfo);
					fileHandleInfo.setSuccess(true);
				}

				@Override
				public void lost(FileInfo fileInfo) {
					fileHandleInfo.setFileInfo(fileInfo);
					fileHandleInfo.setSuccess(false);
				}
			};
			fhu.upload(http, file, dataMap, fileAction);
		} else {
			FileInfo fileInfo = new FileInfo();
			fileHandleInfo.setFileInfo(fileInfo);
			fileHandleInfo.setSuccess(false);
		}
	}

	/**
	 * 处理图片下载
	 * 
	 * @author XiaHui
	 * @date 2017年6月18日 下午6:12:32
	 * @param http
	 * @param savePath
	 * @param fileName
	 * @param fhi
	 * @param fi
	 */

	public void downloadImage(String http, String savePath, String fileName,final  FileHandleInfo fhi,final  FileInfo fi) {
		FileAction<File> fileAction = new FileAction<File>() {

			@Override
			public void progress(long speed, long size, long up, double percentage) {

			}

			@Override
			public void success(File file) {
				fi.setFile(file);
				fhi.setFileInfo(fi);
				fhi.setSuccess(true);
			}

			@Override
			public void lost(File file) {
				fi.setFile(file);
				fhi.setFileInfo(fi);
				fhi.setSuccess(false);
			}
		};
		fhd.download(http, savePath, fileName, true, fileAction);
	}

	/**
	 * 处理上传或者下载
	 * 
	 * @author XiaHui
	 * @date 2017年6月17日 下午9:45:01
	 * @param et
	 * @param backAction
	 */
	private <T> void handle(ExecuteTask et, BackAction<T> backAction) {
		if (hasFileSererAddress()) {
			appContext.add(et);
		} else {
			loadFileSererAddress(et, backAction);
		}
	}

	/**
	 * 图片文件服务地址是否已经就绪
	 * 
	 * @author XiaHui
	 * @date 2017年6月17日 下午9:43:51
	 * @return
	 */
	private boolean hasFileSererAddress() {
		return OnlyStringUtil.isNotBlank(fileServerAddress);
	}

	/**
	 * 载入图片文件服务器地址
	 * 
	 * @author XiaHui
	 * @date 2017年6月17日 下午9:43:25
	 * @param et
	 * @param backAction
	 */
	private <T> void loadFileSererAddress(final ExecuteTask et,final  BackAction<T> backAction) {
		DataBackAction dataBackAction = new DataBackAction() {

			BackInfo backInfo = new BackInfo();

			@Override
			public void lost() {
				backInfo.addError("001", "连接服务器失败！");
				backAction.back(backInfo, null);
			}

			@Override
			public void timeOut() {
				backInfo.addError("001", "连接服务器失败！");
				backAction.back(backInfo, null);
			}

			@Back
			public void back(Info info, @Define("fileServerUrl") String fileServerUrl) {
				fileServerAddress = fileServerUrl;
				if (info.isSuccess()) {
					appContext.add(et);
				} else {
					backInfo.addError("001", "连接服务器失败！");
					backAction.back(backInfo, null);
				}
			}
		};
		FileSender rs = this.appContext.getSender(FileSender.class);
		rs.getFileServer(dataBackAction);
	}
}
