package com.tongwii.service;

import java.util.List;

/**
 * 分页信息接口
 * @author Zeral
 * @date 2016-01-08
 */
public interface IPageInfo {
	
	/**
	 * 默认每页显示最大数
	 */
	static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 获取记录总条数
	 * @author Zeral
	 * @date 2016-01-08
	 */
	long getTotalCount();

	/**
	 * 获取每页显示记录最大数
	 * @author Zeral
	 * @date 2016-01-08
	 */
	int getPageSize();

	/**
	 * 获取每页起始数据的位置
	 * @author Zeral
	 * @date 2016-01-08
	 */
	int getOffset();
	
	/**
	 * 设置记录总条数
	 * @author Zeral
	 * @date 2016-01-08
	 */
	void setTotalCount(long totalCount);

	/**
	 * 设置每页显示记录最大数
	 * @author Zeral
	 * @date 2016-01-08
	 */
	void setPageSize(int pageSize);
	
	/**
	 * 获取每页起始数据的位置
	 * @author Zeral
	 * @date 2016-01-08
	 */
	void setOffset(int offset);

	/**
	 * 给分页对象设置数据
	 * @param lsemp
	 */
	void setPageData(List<?> lsemp);

	/**
	 * 获取分页数据
	 * @author Zeral
	 * @return
	 */
	List<?> getPageData();

}