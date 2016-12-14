package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.security.spring.aop.callback.CursoLntAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface CursoLntManager extends GenericManager<CursoLnt>
{
	public Collection<CursoLnt> findByLntId(Long lntId);
	public Collection<CursoLnt> findByLntIdAndEmpresasIdsAndAreasParticipantesIds(Long lntId, Long[] areasParticipantesIds, Long[] empresasIds);
	@Audita(operacao="Inserção/Atualização", auditor=CursoLntAuditorCallbackImpl.class)
	public void saveOrUpdate(Lnt lnt, Collection<CursoLnt> cursosLnt, String[] participantesRemovidos, Long[] cursosRemovidos) throws Exception;
	public ParticipanteCursoLntManager getParticipanteCursoLntManager();
	public Boolean existePerticipanteASerRelacionado(Long cursoLntId);
	public void updateNomeNovoCurso(Long cursoId, String nomeNovoCurso);
	public void removeCursoId(Long cursoId);
}
