package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;

public interface EngenheiroResponsavelManager extends GenericManager<EngenheiroResponsavel>
{
	EngenheiroResponsavel findByIdProjection(Long engenheiroResponsavelId);
	Collection<EngenheiroResponsavel> findResponsaveisPorEstabelecimento(Colaborador colaborador, Date data);
}