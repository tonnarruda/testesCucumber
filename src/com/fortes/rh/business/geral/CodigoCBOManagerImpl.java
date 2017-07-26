package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.CodigoCBODao;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CodigoCBO;
import com.fortes.rh.model.ws.TCargo;

public class CodigoCBOManagerImpl extends GenericManagerImpl<CodigoCBO, CodigoCBODao> implements CodigoCBOManager
{
	public Collection<AutoCompleteVO> buscaCodigosCBO(String descricao)
	{
		return getDao().buscaCodigosCBO(descricao);
	}
	
	public String findDescricaoByCodigo(String cboCodigo)
	{
		if (cboCodigo != null && !cboCodigo.equals(""))
		{
			return getDao().findDescricaoByCodigo(cboCodigo);
		}
		
		return "";
	}

    public void updateCBO(TCargo tCargo) 
    {
        if(tCargo.getCboCodigo() != null && tCargo.getCboDescricao() != null && !"".equals(tCargo.getCboCodigo()) && !"".equals(tCargo.getCboDescricao()))
            this.saveOrUpdate(new CodigoCBO(tCargo.getCboCodigo(), tCargo.getCboDescricao()));
    }
}
