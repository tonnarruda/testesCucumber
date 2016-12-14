package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EpcDao;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.web.tags.CheckBox;

public class EpcManagerImpl extends GenericManagerImpl<Epc, EpcDao> implements EpcManager
{
	public Epc findByIdProjection(Long epcId)
	{
		return getDao().findByIdProjection(epcId);
	}

	public Collection<Epc> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}
	
	public Collection<CheckBox> populaCheckBox(Long empresaId)
	{
		try
		{
			Collection<Epc> epcs = getDao().findAllSelect(empresaId);
			return CheckListBoxUtil.populaCheckListBox(epcs, "getId", "getDescricao", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ArrayList<CheckBox>();
		}
	}

	public String[] findByAmbiente(Long id) 
	{
		Collection<Epc> epcs = getDao().findByAmbiente(id);
		
		CollectionUtil<Epc> collectionUtil = new CollectionUtil<Epc>();
		return collectionUtil.convertCollectionToArrayString(epcs, "getId");
		
		
	}

	public Collection<Epc> findEpcsDoAmbiente(Long ambienteId, Date data) {
		return getDao().findEpcsDoAmbiente(ambienteId, data);
	}
}