package com.fortes.rh.web.dwr;

import com.fortes.rh.business.sesmt.FasePcmatManager;

public class FasePcmatDWR
{
	private FasePcmatManager fasePcmatManager;

	public void reordenar(Long[] fasesPcmatIds)
	{
		fasePcmatManager.reordenar(fasesPcmatIds);
	}
	
	public void setFasePcmatManager(FasePcmatManager fasePcmatManager) 
	{
		this.fasePcmatManager = fasePcmatManager;
	}
}
