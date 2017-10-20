package com.fortes.rh.dao.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalho;

public interface CodificacaoAcidenteTrabalhoDao extends GenericDao<CodificacaoAcidenteTrabalho> 
{
	Collection<CodificacaoAcidenteTrabalho> findAllSelect();
}
