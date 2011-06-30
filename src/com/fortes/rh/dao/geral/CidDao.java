package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Cid;

public interface CidDao extends GenericDao<Cid> 
{
	public Collection<Cid> buscaCids(String codigo, String descricao);
	public String findDescricaoByCodigo(String codigo);
}
