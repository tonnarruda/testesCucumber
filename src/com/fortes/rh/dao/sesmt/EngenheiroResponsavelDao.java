package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;


public interface EngenheiroResponsavelDao extends GenericDao<EngenheiroResponsavel>
{
	EngenheiroResponsavel findByIdProjection(Long engenheiroResponsavelId);
	Collection<EngenheiroResponsavel> findResponsaveisPorEstabelecimento(Long empresaId, Long estabelecimentoId);
}