package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.security.spring.aop.callback.LntAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface LntManager extends GenericManager<Lnt>
{
	@Audita(operacao="Inserção", auditor=LntAuditorCallbackImpl.class)
	public Lnt save(Lnt lnt);
	@Audita(operacao="Atualização", auditor=LntAuditorCallbackImpl.class)
	public void update(Lnt lnt);
	public void remove(Long id);
	public Collection<Lnt> findAllLnt(String descricao, char status, Long empresaId, int page, int pagingSize);
	public Integer getCountFindAllLnt(String descricao, char status, Long empresaId);
	public Collection<Lnt> findLntsNaoFinalizadas(Date dataInicial);
	@Audita(operacao="Finalizar", auditor=LntAuditorCallbackImpl.class)
	public void finalizar(Long lntId, Long empresaId) throws Exception;
	@Audita(operacao="Reabrir", auditor=LntAuditorCallbackImpl.class)
	public void reabrir(Long lntId);
	public Collection<Lnt> findPossiveisLntsColaborador(Long cursoId, Long colaboradorId);
	public Long findLntColaboradorParticpa(Long cursoId, Long colaboradorId);
	public AreaOrganizacionalManager getAreaOrganizacionalManager();
	public EmpresaManager getEmpresaManager();
	@Audita(operacao="Remoção", auditor=LntAuditorCallbackImpl.class)
	public void removeComDependencias(Long lntId, ColaboradorTurmaManager colaboradorTurmaManager, CursoLntManager cursoLntManager) throws Exception;
	
}
