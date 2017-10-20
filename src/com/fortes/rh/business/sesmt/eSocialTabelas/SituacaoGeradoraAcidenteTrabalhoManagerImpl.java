package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalhoManager;
import com.fortes.rh.dao.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalhoDao;

public class SituacaoGeradoraAcidenteTrabalhoManagerImpl extends GenericManagerImpl<SituacaoGeradoraAcidenteTrabalho, SituacaoGeradoraAcidenteTrabalhoDao> implements SituacaoGeradoraAcidenteTrabalhoManager
{
	public Collection<SituacaoGeradoraAcidenteTrabalho> findAllSelect() {
		return getDao().findAllSelect();
	}
}
