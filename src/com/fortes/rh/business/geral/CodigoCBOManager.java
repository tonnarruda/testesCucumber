package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CodigoCBO;
import com.fortes.rh.model.ws.TCargo;

public interface CodigoCBOManager extends GenericManager<CodigoCBO>
{
	public Collection<AutoCompleteVO> buscaCodigosCBO(String descricao);
	public String findDescricaoByCodigo(String cboCodigo);
	public void updateCBO(TCargo tCargo);
}
