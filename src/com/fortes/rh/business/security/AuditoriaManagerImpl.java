package com.fortes.rh.business.security;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.security.AuditoriaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.security.Auditoria;
import com.fortes.rh.model.ws.TAuditoria;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.rh.util.DateUtil;
import com.opensymphony.xwork.ActionContext;

public class AuditoriaManagerImpl extends GenericManagerImpl<Auditoria, AuditoriaDao> implements AuditoriaManager
{
	
	public Map findEntidade(Long empresaId)
	{
		return getDao().findEntidade(empresaId);
	}

	public Auditoria projectionFindById(Long id, Long empresaId)
	{
		return getDao().projectionFindById(id, empresaId);
	}

	public Integer getCount(Map parametros, Long empresaId)
	{
		return getDao().getCount(parametros, empresaId);
	}

	public Collection<Auditoria> list(int page, int pagingSize, Map parametros, Long empresaId)
	{
		return getDao().list(page, pagingSize, parametros, empresaId);
	}
	/* (non-Javadoc)
	 * @see com.fortes.rh.business.security.AuditoriaManager#audita(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void audita(String recurso, String operacao, String chave, String dados) {
		
		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		Empresa empresa = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession());
		
		Auditoria auditoria = new Auditoria();
		auditoria.audita(usuarioLogado, empresa, recurso, operacao, chave, dados);
		
		this.getDao().save(auditoria);
	}

	public List<String> findOperacoesPeloModulo(String modulo) {
		return getDao().findOperacoesPeloModulo(modulo);
	}

	public void auditaCancelarContratacaoNoAC(Colaborador colaborador, String mensagem) 
	{
		Map<String, Object> cancelamentoContratacaoAC = new LinkedHashMap<String, Object>();
		cancelamentoContratacaoAC.put("Colaborador", colaborador.getNome());
		cancelamentoContratacaoAC.put("Mensagem", "Contratação cancelada no Fortes Pessoal. Obs: "+mensagem);
		
		String dados = new DadosAuditados(null, cancelamentoContratacaoAC).gera();
		Empresa empresa = colaborador.getEmpresa();
		
		Auditoria auditoria = new Auditoria();
		auditoria.audita(null, empresa, "Colaborador", "Cancel. Contrat.AC", colaborador.getNome(), dados);
		
		this.getDao().save(auditoria);
	}
	
	public void auditaConfirmacaoDesligamentoNoAC(Collection<Colaborador> colaboradores,Date dataDesligamento, Empresa empresa) 
	{
		Map<String, Object> desligamentoContratacaoAC = new LinkedHashMap<String, Object>();
		desligamentoContratacaoAC.put("Data desligamento:", DateUtil.formataDiaMesAno(dataDesligamento));
		desligamentoContratacaoAC.put("Colaborador(es)", "");
		
		for (Colaborador colaborador : colaboradores) 
			desligamentoContratacaoAC.put(colaborador.getCodigoAC(), colaborador.getNome());
		
		String dados = new DadosAuditados(null, desligamentoContratacaoAC).gera();
		
		Auditoria auditoria = new Auditoria();
		auditoria.audita(null, empresa, "Colaborador", "Desligamento no AC", "", dados);
		
		this.getDao().save(auditoria);
	}
	
	public void auditaCancelamentoSolicitacoNoAC(Colaborador colaborador,String mensagem) 
	{
		Map<String, Object> cancelamentoSolicitacaoAC = new LinkedHashMap<String, Object>();
		cancelamentoSolicitacaoAC.put("Colaborador", colaborador.getNome());
		cancelamentoSolicitacaoAC.put("Mensagem", "Cancelamento da solicitação de desligamento no Fortes Pessoal. Obs: "+mensagem);
		
		String dados = new DadosAuditados(null, cancelamentoSolicitacaoAC).gera();
		Empresa empresa = colaborador.getEmpresa();
		
		Auditoria auditoria = new Auditoria();
		auditoria.audita(null, empresa, "Colaborador", "Cancel.solicitação desligamento", colaborador.getNome(), dados);
		
		this.getDao().save(auditoria);
	}
	
	public void auditaRemoverEnpregadoFortesPessoal(Empresa empresa, TAuditoria tAuditoria, Colaborador colaborador) 
	{
		Map<String, Object> cancelamentoSolicitacaoAC = new LinkedHashMap<String, Object>();
		cancelamentoSolicitacaoAC.put("Colaborador", colaborador.getNome());
		cancelamentoSolicitacaoAC.put("Usuário Fortes Pessoal", tAuditoria.getUsuario());
		cancelamentoSolicitacaoAC.put("Módulo Fortes Pessoal", tAuditoria.getModulo());
		cancelamentoSolicitacaoAC.put("Operação Fortes Pessoal", tAuditoria.getOperacao());
		cancelamentoSolicitacaoAC.put("Mensagem", "Colaborador Removido através do Fortes Pessoal.");
		
		String dados = new DadosAuditados(null, cancelamentoSolicitacaoAC).gera();

		Auditoria auditoria = new Auditoria();
		auditoria.audita(null, empresa, "Colaborador", "Remoção atraves do Fortes Pessoal.", colaborador.getNome(), dados);
		
		this.getDao().save(auditoria);
	}
}