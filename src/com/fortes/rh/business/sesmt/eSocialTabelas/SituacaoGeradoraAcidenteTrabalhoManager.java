package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;

public interface SituacaoGeradoraAcidenteTrabalhoManager extends GenericManager<SituacaoGeradoraAcidenteTrabalho>
{
	Collection<SituacaoGeradoraAcidenteTrabalho> findAllSelect();
}
