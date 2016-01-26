package com.fortes.rh.dao.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;

public interface TabelaReajusteColaboradorDao extends GenericDao<TabelaReajusteColaborador>
{
	public Collection<TabelaReajusteColaborador> findAllSelect(Long empresaId, Character tipoReajuste, Boolean aprovada, Date dataIni, Date dataFim);

	Integer getCount(Long empresaId);

	Collection<TabelaReajusteColaborador> findAllList(int page, int pagingSize, Long empresaId);

	TabelaReajusteColaborador findByIdProjection(Long tabelaReajusteColaboradorId);

	void updateSetAprovada(Long tabelaReajusteColaboradorId, boolean aprovada);
	
	public void update(TabelaReajusteColaborador tabelaReajusteColaborador);
}