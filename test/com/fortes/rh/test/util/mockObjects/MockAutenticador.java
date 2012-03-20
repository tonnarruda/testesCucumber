package com.fortes.rh.test.util.mockObjects;

import remprot.RPClient;



public class MockAutenticador
{
	public static boolean isDemo()
	{
		return false;
	}
	public static RPClient getRemprot()
	{
		return new RPClient(33, "RH");
	}
}