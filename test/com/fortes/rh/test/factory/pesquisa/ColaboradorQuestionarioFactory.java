package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

public class ColaboradorQuestionarioFactory
{
	public static ColaboradorQuestionario getEntity()
	{
		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();

		colaboradorQuestionario.setId(null);

		return colaboradorQuestionario;
	}

	public static ColaboradorQuestionario getEntity(Long id)
	{
		ColaboradorQuestionario colaboradorQuestionario = getEntity();
		colaboradorQuestionario.setId(id);

		return colaboradorQuestionario;
	}
	
	public static ColaboradorQuestionario getEntity(Long id, Colaborador colaborador, Avaliacao avaliacao )
	{
		ColaboradorQuestionario colaboradorQuestionario = getEntity(id);
		colaboradorQuestionario.setAvaliacao(avaliacao);
    	colaboradorQuestionario.setColaborador(colaborador);
		return colaboradorQuestionario;
	}
	
	public static ColaboradorQuestionario getEntity(Colaborador colaborador, Colaborador avaliador, Avaliacao avaliacao, AvaliacaoDesempenho avaliacaoDesempenho, Boolean respondida)
	{
		ColaboradorQuestionario colaboradorQuestionario = getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setAvaliacao(avaliacao);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionario.setRespondida(respondida);
		
		return colaboradorQuestionario;
	}

	public static Collection<ColaboradorQuestionario> getCollection()
	{
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(getEntity(1L));

		return colaboradorQuestionarios;
	}
	
	public static ColaboradorQuestionario getEntity(Colaborador colaborador, Turma turma, AvaliacaoCurso avaliacaoCurso, Double performance)
	{
		ColaboradorQuestionario colaboradorQuestionario = getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliacaoCurso(avaliacaoCurso);
		colaboradorQuestionario.setTurma(turma);
		colaboradorQuestionario.setPerformance(performance);
		colaboradorQuestionario.setRespondida(true);
		
		return colaboradorQuestionario;
	}
}
