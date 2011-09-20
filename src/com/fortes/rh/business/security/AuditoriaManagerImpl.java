package com.fortes.rh.business.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.security.AuditoriaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.security.Auditoria;
import com.fortes.rh.security.SecurityUtil;
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
}