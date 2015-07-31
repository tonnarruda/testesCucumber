package com.fortes.rh.business.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.security.Auditoria;

public interface AuditoriaManager extends GenericManager<Auditoria>
{
	public Map findEntidade(Long empresaId);
	public Auditoria projectionFindById(Long id, Long empresaId);
	public Integer getCount(Map parametros, Long empresaId);
	public Collection<Auditoria> list(int page, int pagingSize, Map parametros, Long empresaId);
	/**
	 * Audita qualquer operação de um determinado recurso (em sua maioria, uma
	 * entidade).
	 * 
	 * @param recurso
	 *            Nome do recurso a ser auditado. Normalmente será o nome da
	 *            entidade.
	 * @param operacao
	 *            Nome da operação a ser auditada. Normalmente será o nome do
	 *            método.
	 * @param chave
	 *            Chave utilizada como "tag" durante as pesquisas.
	 * @param dados
	 *            Os dados e informações que devem ser auditados.
	 */
	public void audita(String recurso, String operacao, String chave, String dados);
	
	/**
	 * Busca todas as operações existente de um módulo.
	 */
	public List<String> findOperacoesPeloModulo(String modulo);
	public String getDetalhes(String dados);
	public void auditaCancelarContratacaoNoAC(Colaborador colaborador,String mensagem);
	public void auditaCancelamentoSolicitacoNoAC(Colaborador colaborador,String mensagem);
	public void auditaConfirmacaoDesligamentoNoAC(Collection<Colaborador> colaboradores,Date dataDesligamento, Empresa empresa);
}