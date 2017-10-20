package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissionalDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissional;

public class SituacaoGeradoraDoencaProfissionalManagerImpl extends GenericManagerImpl<SituacaoGeradoraDoencaProfissional, SituacaoGeradoraDoencaProfissionalDao> implements SituacaoGeradoraDoencaProfissionalManager
{
	public Collection<SituacaoGeradoraDoencaProfissional> findAllSelect() {
		return getDao().findAllSelect();
	}
}
