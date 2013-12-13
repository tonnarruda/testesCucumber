package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Obra;

public interface ObraManager extends GenericManager<Obra>
{
	Collection<Obra> findAllSelect(String nome, Long empresaId);
	Obra findByIdProjecion(Long id);
}
