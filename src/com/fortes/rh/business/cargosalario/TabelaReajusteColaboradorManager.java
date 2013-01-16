package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.exception.LimiteColaboradorExceditoException;
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
	public void aplicarPorColaborador(TabelaReajusteColaborador tabelaReajusteColaborador, Empresa empresa, Collection<ReajusteColaborador> reajustes) throws IntegraACException, ColecaoVaziaException, LimiteColaboradorExceditoException, Exception;
	
	@Audita(operacao="Cancelar Reajuste", auditor=TabelaReajusteColaboradorAuditorCallbackImpl.class)
	public void cancelar(Long tabelaReajusteId, Empresa empresa) throws Exception;

	@Audita(operacao="Atualização", auditor=TabelaReajusteColaboradorAuditorCallbackImpl.class)
	public void update(TabelaReajusteColaborador tabelaReajusteColaborador);
	
	//TODO: Auditar
	public void aplicarPorFaixaSalarial(Long tabelaReajusteColaboradorId, Empresa empresa) throws ColecaoVaziaException, Exception;
	//TODO: Auditar
	public void cancelarPorFaixaSalarial(Long tabelaReajusteColaboradorId, Empresa empresa) throws Exception;

	public void marcaUltima(Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors);

	public Collection<TabelaReajusteColaborador> findAllSelect(Long empresaId);

	public Collection<TabelaReajusteColaborador> findAllSelectByNaoAprovada(Long empresaId, Character tipoReajuste);

	public Integer getCount(Long empresaId);

	public Collection<TabelaReajusteColaborador> findAllList(int page, int PagingSize, Long empresaId);

	public TabelaReajusteColaborador findByIdProjection(Long tabelaReajusteColaboradorId);

	public void verificaDataHistoricoColaborador(Long tabelaReajusteColaboradorId, Date data) throws Exception;

	public void aplicarPorIndice(Long tabelaReajusteColaboradorId, Empresa empresa) throws Exception, FortesException;
}