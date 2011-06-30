package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Cid;

public interface CidManager extends GenericManager<Cid>
{
	public Collection<Cid> buscaCids(String codigo, String descricao);
	public String findDescricaoByCodigo(String codigo);
}
