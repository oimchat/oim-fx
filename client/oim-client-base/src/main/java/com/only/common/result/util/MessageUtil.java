package com.only.common.result.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.only.common.result.ErrorInfo;
import com.only.common.result.Info;

/**
 * @author: XiaHui
 * @date: 2017年1月14日 下午3:56:05
 */
public class MessageUtil {

	/**
	 * 获取默认的错误信息
	 * 
	 * @author: XiaHui
	 * @param info
	 * @return
	 * @createDate: 2017年5月11日 上午11:16:36
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:16:36
	 */
	public static String getDefaultErrorText(Info info) {
		String error = null;
		if (info.getWarnings().size() > 0) {
			error = info.getWarnings().get(0).getText();
		} else if (info.getErrors().size() > 0) {
			error = info.getErrors().get(0).getText();
		}
		return error;
	}

	///////////////////////////////////////////////////
	/**
	 * 获取第一个错误信息
	 * 
	 * @author: XiaHui
	 * @param info
	 * @return
	 * @createDate: 2017年5月11日 上午11:16:25
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:16:25
	 */
	public static ErrorInfo getFirstError(Info info) {
		ErrorInfo error = null;
		if (!info.isSuccess()) {
			List<ErrorInfo> errors = info.getErrors();
			if (null != errors && !errors.isEmpty()) {
				error = errors.get(0);
			}
		}
		return error;
	}

	/**
	 * 获取指定的错误信息
	 * 
	 * @author: XiaHui
	 * @param info
	 * @param code
	 * @return
	 * @createDate: 2017年5月11日 上午11:16:52
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:16:52
	 */
	public static ErrorInfo getErrorInfo(Info info, String code) {
		ErrorInfo error = null;
		if (!info.isSuccess()) {
			List<ErrorInfo> errors = info.getErrors();
			if (null != errors) {
				for (ErrorInfo e : errors) {
					String c = (null == e.getCode()) ? "" : e.getCode().trim().replace(" ", "");
					if (c.equalsIgnoreCase(code)) {
						error = e;
						break;
					}
				}
			}
		}
		return error;
	}

	/**
	 * 获取指定的错误信息文本
	 * 
	 * @author: XiaHui
	 * @param info
	 * @param code
	 * @return
	 * @createDate: 2017年5月11日 上午11:17:09
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:17:09
	 */
	public static String getErrorText(Info info, String code) {
		String error = null;
		if (!info.isSuccess()) {
			List<ErrorInfo> errors = info.getErrors();
			if (null != errors) {
				for (ErrorInfo e : errors) {
					String c = (null == e.getCode()) ? "" : e.getCode().trim().replace(" ", "");
					if (c.equalsIgnoreCase(code)) {
						error = e.getText();
						break;
					}
				}
			}
		}
		return error;
	}

	/**
	 * 根据编码获取错误map
	 * 
	 * @author: XiaHui
	 * @param info
	 * @param codes
	 * @return
	 * @createDate: 2017年5月11日 上午11:17:34
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:17:34
	 */
	public static Map<String, String> getErrorMap(Info info, String... codes) {
		Map<String, String> errorMap = new HashMap<String, String>();
		if (!info.isSuccess()) {
			List<ErrorInfo> errors = info.getErrors();
			if (null != errors) {
				for (String code : codes) {
					for (ErrorInfo e : errors) {
						String c = (null == e.getCode()) ? "" : e.getCode().trim().replace(" ", "");
						if (c.equalsIgnoreCase(code)) {
							errorMap.put(code, e.getText());
							break;
						}
					}
				}
			}
		}
		return errorMap;
	}

	/**
	 * 
	 * @author: XiaHui
	 * @param map
	 * @param info
	 * @param text
	 * @return
	 * @createDate: 2017年5月11日 上午11:18:05
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:18:05
	 */
	public static String getErrorText(Map<String, String> map, Info info, String text) {
		String error = null;
		if (!info.isSuccess()) {
			List<ErrorInfo> errors = info.getErrors();
			if (null != errors) {
				for (ErrorInfo e : errors) {
					String code = (null == e.getCode()) ? "" : e.getCode().trim().replace(" ", "");
					if (map.containsKey(code)) {
						error = map.get(code);
						break;
					}
				}
			} else {
				error = text;
			}
		}
		return error;
	}

	/////////////////////////////////////

	/**
	 * 获取第一个错误信息
	 * 
	 * @author: XiaHui
	 * @param info
	 * @return
	 * @createDate: 2017年5月11日 上午11:16:25
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:16:25
	 */
	public static ErrorInfo getFirstWarning(Info info) {
		ErrorInfo warning = null;
		if (!info.isSuccess()) {
			List<ErrorInfo> warnings = info.getWarnings();
			if (null != warnings && !warnings.isEmpty()) {
				warning = warnings.get(0);
			}
		}
		return warning;
	}

	/**
	 * 获取指定的错误信息
	 * 
	 * @author: XiaHui
	 * @param info
	 * @param code
	 * @return
	 * @createDate: 2017年5月11日 上午11:16:52
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:16:52
	 */
	public static ErrorInfo getWarningInfo(Info info, String code) {
		ErrorInfo warning = null;
		if (!info.isSuccess()) {
			List<ErrorInfo> warnings = info.getWarnings();
			if (null != warnings) {
				for (ErrorInfo e : warnings) {
					String c = (null == e.getCode()) ? "" : e.getCode().trim().replace(" ", "");
					if (c.equalsIgnoreCase(code)) {
						warning = e;
						break;
					}
				}
			}
		}
		return warning;
	}

	/**
	 * 获取指定的错误信息文本
	 * 
	 * @author: XiaHui
	 * @param info
	 * @param code
	 * @return
	 * @createDate: 2017年5月11日 上午11:17:09
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:17:09
	 */
	public static String getWarningText(Info info, String code) {
		String warning = null;
		if (!info.isSuccess()) {
			List<ErrorInfo> warnings = info.getWarnings();
			if (null != warnings) {
				for (ErrorInfo e : warnings) {
					String c = (null == e.getCode()) ? "" : e.getCode().trim().replace(" ", "");
					if (c.equalsIgnoreCase(code)) {
						warning = e.getText();
						break;
					}
				}
			}
		}
		return warning;
	}

	/**
	 * 根据编码获取错误map
	 * 
	 * @author: XiaHui
	 * @param info
	 * @param codes
	 * @return
	 * @createDate: 2017年5月11日 上午11:17:34
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:17:34
	 */
	public static Map<String, String> getWarningMap(Info info, String... codes) {
		Map<String, String> warningMap = new HashMap<String, String>();
		if (!info.isSuccess()) {
			List<ErrorInfo> warnings = info.getWarnings();
			if (null != warnings) {
				for (String code : codes) {
					for (ErrorInfo e : warnings) {
						String c = (null == e.getCode()) ? "" : e.getCode().trim().replace(" ", "");
						if (c.equalsIgnoreCase(code)) {
							warningMap.put(code, e.getText());
							break;
						}
					}
				}
			}
		}
		return warningMap;
	}

	/**
	 * 
	 * @author: XiaHui
	 * @param map
	 * @param info
	 * @param text
	 * @return
	 * @createDate: 2017年5月11日 上午11:18:05
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午11:18:05
	 */
	public static String getWarningText(Map<String, String> map, Info info, String text) {
		String warning = null;
		if (!info.isSuccess()) {
			List<ErrorInfo> warnings = info.getWarnings();
			if (null != warnings) {
				for (ErrorInfo e : warnings) {
					String code = (null == e.getCode()) ? "" : e.getCode().trim().replace(" ", "");
					if (map.containsKey(code)) {
						warning = map.get(code);
						break;
					}
				}
			} else {
				warning = text;
			}
		}
		return warning;
	}
}
