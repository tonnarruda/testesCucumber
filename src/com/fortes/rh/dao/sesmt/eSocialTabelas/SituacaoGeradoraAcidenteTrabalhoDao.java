package com.fortes.rh.dao.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;

public interface SituacaoGeradoraAcidenteTrabalhoDao extends GenericDao<SituacaoGeradoraAcidenteTrabalho> 
{
	Collection<SituacaoGeradoraAcidenteTrabalho> findAllSelect();
}
