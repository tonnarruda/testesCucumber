package com.fortes.rh.web.action;

public class MyActionSupportList extends MyActionSupport
{
	private int page = 1;
	private int pagingSize = 15;
	private int totalSize;
	private boolean showFilter = false;

	public boolean getShowFilter() 
	{
		return showFilter;
	}

	public void setShowFilter(boolean filterState) 
	{
		this.showFilter = filterState;
	}

	public int getPage()
	{
		return page;
	}

	public int getPagingSize()
	{
		return pagingSize;
	}

	public int getTotalSize()
	{
		return totalSize;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public void setTotalSize(Integer totalSize)
	{
		this.totalSize = totalSize;
	}

}