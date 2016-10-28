package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.web.tags.CheckBox;

@Component
public class TipoEPIManagerImpl extends GenericManagerImpl<TipoEPI, TipoEPIDao> implements TipoEPIManager
{
	@Autowired
	TipoEPIManagerImpl(TipoEPIDao tipoEPIDao) {
		setDao(tipoEPIDao);
	}
	
	public Collection<TipoEPI> findCollectionTipoEPI(Long empresId)
	{
		return getDao().findCollectionTipoEPI(empresId);
	}

	public TipoEPI findTipoEPI(Long epiId)
	{
		return getDao().findTipoEPI(epiId);
	}

	public Long findTipoEPIId(Long epiId)
	{
		return getDao().findTipoEPIId(epiId);
	}
	
	public void clonar(TipoEPI tipoEPIInteresse, Long empresaDestinoId)
	{
		tipoEPIInteresse.setId(null);
		tipoEPIInteresse.setEmpresaId(empresaDestinoId);
		
		getDao().save(tipoEPIInteresse);
	}

	public Collection<CheckBox> getByEmpresa(Long empresaId) 
	{
		Collection<CheckBox> tipoEPICheckList = new ArrayList<CheckBox>();
		
		try 
		{
			Collection<TipoEPI> tipoEpis = findCollectionTipoEPI(empresaId);
			tipoEPICheckList = CheckListBoxUtil.populaCheckListBox(tipoEpis, "getId", "getNome", null);
		}
		catch (Exception e) 
		{
		}
		
		return tipoEPICheckList;
	}
}