package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.rh.model.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalho;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalhoManager;
import com.fortes.rh.dao.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalhoDao;

public class CodificacaoAcidenteTrabalhoManagerImpl extends GenericManagerImpl<CodificacaoAcidenteTrabalho, CodificacaoAcidenteTrabalhoDao> implements CodificacaoAcidenteTrabalhoManager
{
	public Collection<CodificacaoAcidenteTrabalho> findAllSelect() {
		return getDao().findAllSelect();
	}
}
