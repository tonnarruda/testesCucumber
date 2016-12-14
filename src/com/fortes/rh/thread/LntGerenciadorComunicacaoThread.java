package com.fortes.rh.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CollectionUtil;

public class LntGerenciadorComunicacaoThread extends Thread{

	private Lnt lnt;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ParticipanteCursoLntManager participanteCursoLntManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private Map<String, Object> parametros;
	
	public LntGerenciadorComunicacaoThread(Lnt lnt, ParticipanteCursoLntManager participanteCursoLntManager, AreaOrganizacionalManager areaOrganizacionalManager, GerenciadorComunicacaoManager gerenciadorComunicacaoManager, Map<String, Object> parametros){
		this.lnt = lnt;
		this.participanteCursoLntManager = participanteCursoLntManager;
		this.areaOrganizacionalManager = areaOrganizacionalManager;
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
		this.parametros = parametros;
	}
	
	public void run() {
		try {
			enviaAvisoLntFinalizada();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private void enviaAvisoLntFinalizada()
	{
		try {
			Collection<ParticipanteCursoLnt> participantesCursosLnt = participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), null, null, new String[]{"e.nome", "areaNome","c.nome","cu.nomeNovoCurso"});
			Map<Long, Collection<ParticipanteCursoLnt>> mapEmpresaIdLntParticipantes = new HashMap<Long, Collection<ParticipanteCursoLnt>>();

			for (ParticipanteCursoLnt participanteCursoLnt : participantesCursosLnt) {
				if(!mapEmpresaIdLntParticipantes.containsKey(participanteCursoLnt.getEmpresaId()))
					mapEmpresaIdLntParticipantes.put(participanteCursoLnt.getEmpresaId(), new ArrayList<ParticipanteCursoLnt>());
					
				mapEmpresaIdLntParticipantes.get(participanteCursoLnt.getEmpresaId()).add(participanteCursoLnt);
			}
			
			String subject = "[RH] - Aprovação de Levantamento de Necessidade de Treinamento (LNT)";
			StringBuilder body = new StringBuilder(); 
			body.append("LNT: "+ lnt.getDescricao() + " <br>");
			body.append("Período: "+ lnt.getPeriodoFormatado() + " <br><br>");
			body.append("Esta LNT foi aprovada para os colaboradores citados no relatório em anexo.");

			gerenciaEnvioAvisoLnt(lnt, subject, body, mapEmpresaIdLntParticipantes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void gerenciaEnvioAvisoLnt(Lnt lnt, String subject, StringBuilder body, Map<Long, Collection<ParticipanteCursoLnt>> mapEmpresaIdLntParticipantes) throws Exception
	{
		for (Long empresaId : mapEmpresaIdLntParticipantes.keySet()) {

			Map<Long, AreaOrganizacional> mapAreasIds = areaOrganizacionalManager.findAllMapAreasIds(empresaId);
			Map<Long, Collection<ParticipanteCursoLnt>> mapPerticipantesLNTPorResponsaveis = new HashMap<Long, Collection<ParticipanteCursoLnt>>();
			montaMapParticipantesLnt(mapPerticipantesLNTPorResponsaveis, mapEmpresaIdLntParticipantes.get(empresaId), mapAreasIds);

			String link = "desenvolvimento/lnt/relatorioParticipantesByUsuarioMsg.action?lnt.id="+lnt.getId();
			Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByLntId(lnt.getId(), new Long[]{});
			Collection<Long> areasIds = areaOrganizacionalManager.getAncestraisIds(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas));
			Collection<ParticipanteCursoLnt> participanteCursoLntsEmpresa = mapEmpresaIdLntParticipantes.get(empresaId);
			
			gerenciadorComunicacaoManager.enviaAvisoLntFinalizada(subject, body, link, empresaId, areasIds, parametros, mapPerticipantesLNTPorResponsaveis, participanteCursoLntsEmpresa);
		}
	}

	public void montaMapParticipantesLnt(Map<Long, Collection<ParticipanteCursoLnt>> mapPerticipantesLNTPorResponsaveis, Collection<ParticipanteCursoLnt> participantesLnt, Map<Long, AreaOrganizacional> mapAreasIds) {
		for(ParticipanteCursoLnt participanteCursoLnt : participantesLnt){
			if(participanteCursoLnt.getAreaOrganizacional() == null || participanteCursoLnt.getAreaOrganizacional().getId() == null)
				continue;

			setParticipantesLntAreasRecursivo(mapPerticipantesLNTPorResponsaveis, participanteCursoLnt, mapAreasIds, mapAreasIds.get(participanteCursoLnt.getAreaOrganizacional().getId()));
		}
	}

	private void setParticipantesLntAreasRecursivo(Map<Long, Collection<ParticipanteCursoLnt>> mapPerticipantesLNTPorResponsaveis, ParticipanteCursoLnt participanteCursoLnt, Map<Long, AreaOrganizacional> mapAreasIds, AreaOrganizacional area) {
			Long responsavelId = null;
			Long coResponsavelId = null;
			
			if(area.getResponsavel() != null && area.getResponsavel().getId() != null)
				responsavelId = area.getResponsavel().getId();
			
			if(area.getCoResponsavel() != null && area.getCoResponsavel().getId() != null)
				coResponsavelId = area.getCoResponsavel().getId();

			if(area.getAreaMae() != null && area.getAreaMae().getId() != null)
				setParticipantesLntAreasRecursivo(mapPerticipantesLNTPorResponsaveis, participanteCursoLnt, mapAreasIds, mapAreasIds.get(area.getAreaMae().getId()));
			
			setaParticipantesLntMap(mapPerticipantesLNTPorResponsaveis, participanteCursoLnt, responsavelId, coResponsavelId);
	}
	
	private void setaParticipantesLntMap(Map<Long, Collection<ParticipanteCursoLnt>> mapPerticipantesLNTPorResponsaveis, ParticipanteCursoLnt participanteCursoLnt, Long responsavelId, Long coResponsavelId){
		if(responsavelId != null){
			if(!mapPerticipantesLNTPorResponsaveis.containsKey(responsavelId))
				mapPerticipantesLNTPorResponsaveis.put(responsavelId, new ArrayList<ParticipanteCursoLnt>());
			
			if(!mapPerticipantesLNTPorResponsaveis.get(responsavelId).contains(participanteCursoLnt))
				mapPerticipantesLNTPorResponsaveis.get(responsavelId).add(participanteCursoLnt);
		}
		
		if(coResponsavelId != null){
			if(!mapPerticipantesLNTPorResponsaveis.containsKey(coResponsavelId))
				mapPerticipantesLNTPorResponsaveis.put(coResponsavelId, new ArrayList<ParticipanteCursoLnt>());
			
			if(!mapPerticipantesLNTPorResponsaveis.get(coResponsavelId).contains(participanteCursoLnt))
				mapPerticipantesLNTPorResponsaveis.get(coResponsavelId).add(participanteCursoLnt);
		}
	}
	
}
