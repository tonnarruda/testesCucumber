package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.CodigoCBO;

public interface CodigoCBOManager extends GenericManager<CodigoCBO>
{
	public Collection<CodigoCBO> buscaCodigosCBO(String codigo, String descricao);
	public String findDescricaoByCodigo(String cboCodigo);
}
