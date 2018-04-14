package com.onlyxiahui.im.common.box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.onlyxiahui.im.bean.Category;
import com.onlyxiahui.im.bean.CategoryMember;
import com.onlyxiahui.im.bean.DataBean;

/**
 * 通用存放分组和成员信息的盒子<br>
 * 比如用户分组和分组中的成员
 * 
 * @author XiaHui
 * @date 2017-11-06 16:10:31
 */
public class DataBeanBox<A extends DataBean, B extends Category, C extends CategoryMember> {

	/** 所有实体数据 <dataBeanId,DataBean> */
	private Map<String, A> allDataBeanMap = new ConcurrentHashMap<String, A>();
	/** 所有分组 <categoryId,DataBean> */
	private Map<String, B> allCategoryMap = new ConcurrentHashMap<String, B>();

	/** 分组中的成员列表 <categoryId,Map<dataBeanId, CategoryMember>> */
	private Map<String, Map<String, C>> categoryHasMemberListMap = new ConcurrentHashMap<String, Map<String, C>>();
	/** 实体所在的分组 <dataBeanId,Map<categoryId, CategoryMember>> */
	private Map<String, Map<String, C>> dataBeanInCategoryListMap = new ConcurrentHashMap<String, Map<String, C>>();
	/** 实体所在的分组 <categoryId,List<CategoryMember>> */
	private Map<String, List<C>> categoryMemberListMap = new ConcurrentHashMap<String, List<C>>();

	/**
	 * 存放实体数据
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:13:34
	 * @param dataBean
	 */
	public void putDataBean(A dataBean) {
		String id = dataBean.getId();
		id = id == null ? "" : id;
		allDataBeanMap.put(id, dataBean);
	}

	/**
	 * 存放分组数据
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:13:45
	 * @param category
	 */
	public void putCategory(B category) {
		String categoryId = category.getId();
		categoryId = categoryId == null ? "" : categoryId;
		allCategoryMap.put(categoryId, category);
	}

	/**
	 * 存放分组和成员关系数据
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:13:54
	 * @param categoryMember
	 */
	public void putCategoryMember(C categoryMember) {

		String memberId = categoryMember.getMemberId();
		String categoryId = categoryMember.getCategoryId();

		Map<String, C> categoryMemberMap = getCategoryMemberMap(categoryId);
		categoryMemberMap.put(memberId, categoryMember);

		Map<String, C> dataBeanInCategoryMemberMap = getDataBeanInCategoryMemberMapByDataBeanId(memberId);
		dataBeanInCategoryMemberMap.put(categoryId, categoryMember);

		List<C> list = getCategoryMemberList(categoryId);

		if (!list.contains(categoryMember)) {
			list.add(categoryMember);
		}
	}

	/**
	 * 清除所有实体数据
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:19:12
	 */
	public void clearDataBean() {
		allDataBeanMap.clear();
	}

	/**
	 * 清除所有分组
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:18:17
	 */
	public void clearCategory() {
		allCategoryMap.clear();
	}

	/**
	 * 清除所有关系信息
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:18:35
	 */
	public void clearCategoryMember() {
		categoryHasMemberListMap.clear();
		dataBeanInCategoryListMap.clear();
		categoryMemberListMap.clear();
	}

	/**
	 * 批量存入分组信息
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:33:57
	 * @param categoryList
	 */
	public void setCategoryList(List<B> categoryList) {
		if (null != categoryList) {
			for (B userCategory : categoryList) {
				this.putCategory(userCategory);
			}
		}
	}

	/**
	 * 批量存入实体信息
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:34:15
	 * @param list
	 */
	public void setDataBeanList(List<A> list) {
		if (null != list) {
			for (A dataBean : list) {
				this.putDataBean(dataBean);
			}
		}
	}

	/**
	 * 批量存入关系数据
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:34:29
	 * @param categoryMemberList
	 */
	public void setCategoryMemberList(List<C> categoryMemberList) {
		if (null != categoryMemberList) {
			for (C CategoryMember : categoryMemberList) {
				this.putCategoryMember(CategoryMember);
			}
		}
	}

	/**
	 * 移除关系数据
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:34:51
	 * @param dataBeanId
	 * @return
	 */
	public List<C> removeCategoryMemberList(String dataBeanId) {
		Map<String, C> map = dataBeanInCategoryListMap.remove(dataBeanId);
		List<C> list = new ArrayList<C>();
		if (null != map) {
			list.addAll(map.values());
			for (CategoryMember categoryMember : list) {
				if (null != categoryMember) {
					String categoryId = categoryMember.getCategoryId();
					Map<String, ? extends CategoryMember> categoryMemberMap = getCategoryMemberMap(categoryId);
					categoryMemberMap.remove(dataBeanId);
				}
			}
		}
		return list;
	}

	/**
	 * 移除关系数据
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:35:03
	 * @param categoryId
	 * @param dataBeanId
	 * @return
	 */
	public C removeCategoryMember(String categoryId, String dataBeanId) {
		Map<String, C> categoryMemberMap = getCategoryMemberMap(categoryId);
		C categoryMember = categoryMemberMap.remove(dataBeanId);
		Map<String, C> map = dataBeanInCategoryListMap.get(dataBeanId);
		if (null != map) {
			map.remove(categoryId);
			if (map.isEmpty()) {
				dataBeanInCategoryListMap.remove(dataBeanId);
			}
		}
		return categoryMember;
	}

	/**
	 * 获取分组中关系列表
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:35:27
	 * @param categoryId
	 * @return
	 */
	public List<C> getCategoryMemberList(String categoryId) {
		List<C> list = categoryMemberListMap.get(categoryId);
		if (null == list) {
			list = new ArrayList<C>();
			categoryMemberListMap.put(categoryId, list);
		}
		return list;
	}

	/**
	 * 获取分组中关系map
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:36:04
	 * @param categoryId
	 * @return
	 */
	public Map<String, C> getCategoryMemberMap(String categoryId) {
		Map<String, C> map = categoryHasMemberListMap.get(categoryId);
		if (null == map) {
			map = new HashMap<String, C>();
			categoryHasMemberListMap.put(categoryId, map);
		}
		return map;
	}

	/**
	 * 分组中成员数量
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:36:29
	 * @param categoryId
	 * @return
	 */
	public int getCategoryMemberSize(String categoryId) {
		Map<String, ? extends CategoryMember> categoryMemberMap = categoryHasMemberListMap.get(categoryId);
		return null == categoryMemberMap ? 0 : categoryMemberMap.size();
	}

	/**
	 * 获取分组信息
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:36:44
	 * @param id
	 * @return
	 */
	public B getCategory(String id) {
		return allCategoryMap.get(id);
	}

	/**
	 * 获取所有分组信息
	 * 
	 * @author XiaHui
	 * @date 2017-11-06 16:36:58
	 * @return
	 */
	public List<B> getCategoryList() {
		return new ArrayList<B>(allCategoryMap.values());
	}

	public C getCategoryMemberByDataBeanId(String categoryId, String dataBeanId) {
		Map<String, C> categoryMemberMap = getCategoryMemberMap(categoryId);
		C c = categoryMemberMap.get(dataBeanId);
		return c;
	}

	public List<C> getDataBeanInCategoryMemberListBydataBeanId(String dataBeanId) {
		Map<String, C> map = getDataBeanInCategoryMemberMapByDataBeanId(dataBeanId);
		List<C> list = new ArrayList<C>();
		list.addAll(map.values());
		return list;
	}

	public Map<String, C> getDataBeanInCategoryMemberMapByDataBeanId(String dataBeanId) {
		Map<String, C> map = dataBeanInCategoryListMap.get(dataBeanId);
		if (map == null) {
			map = new HashMap<String, C>();
			dataBeanInCategoryListMap.put(dataBeanId, map);
		}
		return map;
	}

	/**
	 *
	 * @param dataBeanId
	 * @return
	 */
	public boolean inMemberList(String dataBeanId) {
		boolean has = dataBeanInCategoryListMap.containsKey(dataBeanId);
		return has;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public A getDataBean(String id) {
		A ud = allDataBeanMap.get(id);
		return ud;
	}

	public List<A> getDataBeanList() {
		List<A> list = new ArrayList<A>(allDataBeanMap.values());
		return list;
	}
}
