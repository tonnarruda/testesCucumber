package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.desenvolvimento.LntDao;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.thread.LntGerenciadorComunicacaoThread;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.SpringUtil;

@Component
public class LntManagerImpl extends GenericManagerImpl<Lnt, LntDao> implements LntManager
{
	@Autowired private ParticipanteCursoLntManager participanteCursoLntManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private EmpresaManager empresaManager;

	@Autowired
	public LntManagerImpl(LntDao dao) {
		setDao(dao);
	}
	
	public void update(Lnt lnt){
		getDao().update(lnt);
	}
	
	public Collection<Lnt> findAllLnt(String descricao, char status, Long empresaId,int page, int pagingSize) {
		return getDao().findAllLnt(descricao, status, page, pagingSize);
	}

	public Integer getCountFindAllLnt(String descricao, char status, Long empresaId) {
		return getDao().findAllLnt(descricao, status, 0, 0).size();
	}

	public Collection<Lnt> findLntsNaoFinalizadas(Date dataInicial) {
		return getDao().findLntsNaoFinalizadas(null);
	}

	public void finalizar(Long lntId, Long empresaId) throws Exception
	{
		getDao().finalizar(lntId);
		
		Lnt lnt = getDao().findById(lntId);
		Map<String, Object> parametros = RelatorioUtil.getParametrosRelatorio("Participantes da LNT", empresaManager.findById(empresaId), "LNT: " + lnt.getDescricao() + "\nPeríodo: " + lnt.getPeriodoFormatado());
		new LntGerenciadorComunicacaoThread(lnt, participanteCursoLntManager, areaOrganizacionalManager, (GerenciadorComunicacaoManager) SpringUtil.getBean("gerenciadorComunicacaoManager"), parametros).start();
	}
	
	public void reabrir(Long lntId){
		getDao().reabrir(lntId);
	}

	public Collection<Lnt> findPossiveisLntsColaborador(Long cursoId, Long colaboradorId) {
		return getDao().findPossiveisLntsColaborador(cursoId, colaboradorId);
	}
	
	public Long findLntColaboradorParticpa(Long cursoId, Long colaboradorId) {
		return getDao().findLntColaboradorParticpa(cursoId, colaboradorId);
	}

	public void removeComDependencias(Long lntId, ColaboradorTurmaManager colaboradorTurmaManager, CursoLntManager cursoLntManager) throws Exception {
		colaboradorTurmaManager.removeAllCursoLntByLnt(lntId);

		Collection<ParticipanteCursoLnt> participantesCursoLnt = participanteCursoLntManager.findByLnt(lntId);
		participantesCursoLnt.removeAll(Collections.singleton(null));
		participanteCursoLntManager.remove(new CollectionUtil<ParticipanteCursoLnt>().convertCollectionToArrayIds(participantesCursoLnt));
		
		Collection<CursoLnt> cursosLnt = cursoLntManager.findByLntId(lntId);
		cursosLnt.removeAll(Collections.singleton(null));
		cursoLntManager.remove(new CollectionUtil<CursoLnt>().convertCollectionToArrayIds(cursosLnt));
		
		getDao().remove(lntId);
	}

	/**Método utilizado na auditoria**/
	public AreaOrganizacionalManager getAreaOrganizacionalManager() {
		return this.areaOrganizacionalManager;
	}

	/**Método utilizado na auditoria**/
	public EmpresaManager getEmpresaManager() {
		return this.empresaManager;
	}
}
