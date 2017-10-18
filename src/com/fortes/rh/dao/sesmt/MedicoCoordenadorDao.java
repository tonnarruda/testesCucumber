package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.MedicoCoordenador;

public interface MedicoCoordenadorDao extends GenericDao<MedicoCoordenador>
{
	MedicoCoordenador findByDataEmpresa(Long empresaId, Date data);

	MedicoCoordenador findByIdProjection(Long medicoCoordenadorId);

	Collection<MedicoCoordenador> findResponsaveisPorEstabelecimento(Long empresaId, Long estabelecimentoId);
}