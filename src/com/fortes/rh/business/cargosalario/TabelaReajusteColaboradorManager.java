package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.Empresa;

public interface TabelaReajusteColaboradorManager extends GenericManager<TabelaReajusteColaborador>
{
	public void remove(TabelaReajusteColaborador tabelaReajusteColaborador);

	public void marcaUltima(Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors);

	public void aplicar(TabelaReajusteColaborador tabelaReajusteColaborador, Empresa empresa, Collection<ReajusteColaborador> reajustes) throws IntegraACException, Exception, ColecaoVaziaException;

	public void cancelar(Long tabelaReajusteId, Empresa empresa) throws Exception;

	public Collection<TabelaReajusteColaborador> findAllSelect(Long empresaId);

	public Collection<TabelaReajusteColaborador> findAllSelectByNaoAprovada(Long empresaId);

	public Integer getCount(Long empresaId);

	public Collection<TabelaReajusteColaborador> findAllList(int page, int PagingSize, Long empresaId);

	public TabelaReajusteColaborador findByIdProjection(Long tabelaReajusteColaboradorId);

	public void verificaDataHistoricoColaborador(Long tabelaReajusteColaboradorId, Date data) throws Exception;
	
	public void update(TabelaReajusteColaborador tabelaReajusteColaborador);
}