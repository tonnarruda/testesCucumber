package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.CursoLntManager;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;


public class CursoLntAuditorCallbackImpl implements AuditorCallback {
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	@SuppressWarnings("unchecked")
	public Auditavel saveOrUpdate(MetodoInterceptado metodo) throws Throwable{
		CursoLntManager cursoLntManager = getCursoLntManager(metodo);
		ParticipanteCursoLntManager participanteCursoLntManager = getParticipanteCursoLntManager(cursoLntManager);
		
		Lnt lnt = (Lnt) metodo.getParametros()[0];
		Collection<CursoLnt> cursosLntAnterior = cursoLntManager.findByLntId(lnt.getId());
		Collection<CursoLnt> cursosLntAtual = ((Collection<CursoLnt>) metodo.getParametros()[1]);

		StringBuilder dados = new StringBuilder();
		montaDados(dados, cursosLntAnterior, participanteCursoLntManager, lnt, "[DADOS ANTERIORES]");
		
		metodo.processa();
		montaDados(dados, cursosLntAtual, participanteCursoLntManager, lnt, "\n\n[DADOS ATUALIZADOS]");
		return new AuditavelImpl("Cursos e Participantes da LNT", metodo.getOperacao(), "Cursos e Participantes da LNT", dados.toString());
	}
	
	private StringBuilder montaDados(StringBuilder dados, Collection<CursoLnt> cursosLnt, ParticipanteCursoLntManager participanteCursoLntManager, Lnt lnt, String titulo){
		montaInicio(dados, lnt, titulo);
		for (CursoLnt cursoLnt : cursosLnt) {
			dados = montaCursoLNT(dados, cursoLnt);
			dados = montaParticipantesLnt(dados, participanteCursoLntManager.findByCursoLntId(cursoLnt.getId()));
			dados.append("\n");
		}
		dados.append(" ]\n]");
		return dados;
	}
	
	private StringBuilder montaInicio(StringBuilder dados, Lnt lnt, String titulo){
		dados.append(titulo + " [");
		dados.append("\n LNT: " + lnt.getId());
		dados.append("\n\n Cursos:[");
		
		return dados;
	}

	private StringBuilder montaCursoLNT(StringBuilder dados, CursoLnt cursoLnt){
		dados.append("\n  Id: " + cursoLnt.getId());
		dados.append("\n  Nome: " + cursoLnt.getNomeNovoCurso());
		dados.append("\n  Custo: " + cursoLnt.getId());
		dados.append("\n  Carga horária: " + cursoLnt.getId());
		dados.append("\n  Conteúdo programático: " + cursoLnt.getConteudoProgramatico());
		dados.append("\n  Justificativa: " + cursoLnt.getJustificativa());
		dados.append("\n  Curso id: " + (cursoLnt.getCursoId() != 0 ? cursoLnt.getCursoId()  : "") );

		return dados;
	}

	private StringBuilder montaParticipantesLnt(StringBuilder dados, Collection<ParticipanteCursoLnt> participantesLnt){
		dados.append("\n  Participantes:[ " );
		for (ParticipanteCursoLnt participanteCursoLnt : participantesLnt) {
			dados.append("\n   Id: " + participanteCursoLnt.getId());
			dados.append("\n   Colaborador: ");
			dados.append("\n    Id: " + participanteCursoLnt.getColaboradorId());
			dados.append("\n    Nome: " + participanteCursoLnt.getColaboradorNome());
			dados.append("\n   Área Organizacional: ");
			dados.append("\n    Id: " + participanteCursoLnt.getAreaOrganizacional().getId());
			dados.append("\n    Nome: " + participanteCursoLnt.getAreaOrganizacional().getNome());
			dados.append("\n" );
		}
		dados.append("  ]," );
		return dados;
	}
	
	private CursoLntManager getCursoLntManager(MetodoInterceptado metodo) {
		CursoLntManager cursoLntManager = (CursoLntManager) metodo.getComponente();
		return cursoLntManager;
	}
	
	private ParticipanteCursoLntManager getParticipanteCursoLntManager(CursoLntManager cursoLntManager){
		return cursoLntManager.getParticipanteCursoLntManager();
	}
}