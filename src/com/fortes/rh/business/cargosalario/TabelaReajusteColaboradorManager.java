package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.callback.TabelaReajusteColaboradorAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.Modulo;

@Modulo("Tabela Reajuste Colaborador")
public interface TabelaReajusteColaboradorManager extends GenericManager<TabelaReajusteColaborador>
{
	@Audita(operacao="Remoção", auditor=TabelaReajusteColaboradorAuditorCallbackImpl.class)
	public void remove(TabelaReajusteColaborador tabelaReajusteColaborador);

	@Audita(operacao="Aplicar Reajuste", auditor=TabelaReajusteColaboradorAuditorCallbackImpl.class)
	public void aplicarPorColaborador(TabelaReajusteColaborador tabelaReajusteColaborador, Empresa empresa, Collection<ReajusteColaborador> reajustes) throws Exception;
	
	@Audita(operacao="Cancelar Reajuste", auditor=TabelaReajusteColaboradorAuditorCallbackImpl.class)
	public void cancelar(Character tipoReajuste, Long tabelaReajusteColaboradorId, Empresa empresa, boolean empresaIntegradaEAderiuAoESocial) throws Exception;

	@Audita(operacao="Atualização", auditor=TabelaReajusteColaboradorAuditorCallbackImpl.class)
	public void update(TabelaReajusteColaborador tabelaReajusteColaborador);
	
	//TODO: Auditar
	public void aplicarPorFaixaSalarial(Long tabelaReajusteColaboradorId, Empresa empresa) throws ColecaoVaziaException, Exception;

	public void marcaUltima(Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors, boolean empresaIntegradaEAderiuAoESocial);

	public Collection<TabelaReajusteColaborador> findAllSelect(Long empresaId, Date dataIni, Date dataFim);

	public Collection<TabelaReajusteColaborador> findAllSelectByNaoAprovada(Long empresaId, Character tipoReajuste);

	public Integer getCount(Long empresaId);

	public Collection<TabelaReajusteColaborador> findAllList(int page, int PagingSize, Long empresaId);

	public TabelaReajusteColaborador findByIdProjection(Long tabelaReajusteColaboradorId);

	public void verificaDataHistoricoColaborador(Long tabelaReajusteColaboradorId, Date data) throws Exception;

	public void aplicarPorIndice(Long tabelaReajusteColaboradorId, Empresa empresa) throws Exception, FortesException;

	public void verificaDataHistoricoFaixaSalarial(Long tabelaReajusteColaboradorId, Date data) throws Exception;
	
	public void verificaDataHistoricoIndice(Long tabelaReajusteColaboradorId, Date data) throws Exception;
}