package com.fortes.rh.test.web.action;

import junit.framework.TestCase;

import com.fortes.rh.web.action.MyActionSupportList;

public class MyActionSupportListTest extends TestCase
{
	MyActionSupportList myActionSupportList;

	protected void setUp() throws Exception
	{
		myActionSupportList = new MyActionSupportList();
	}

	public void testGetSet()
	{
		myActionSupportList.setPage(1);
		myActionSupportList.getPage();
		myActionSupportList.setTotalSize(1);
		myActionSupportList.getTotalSize();
		myActionSupportList.getPagingSize();
	}
}
