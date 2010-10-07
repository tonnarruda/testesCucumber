package com.fortes.rh.dao.geral;

import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Beneficio;

public interface BeneficioDao extends GenericDao<Beneficio>
{
	Beneficio findBeneficioById(Long id);
	List<Long> getBeneficiosByHistoricoColaborador(Long historicoId);
}