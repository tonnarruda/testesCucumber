package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.util.SpringUtil;

public class TipoQuestionario extends LinkedHashMap<Object, Object>
{
	private static final long serialVersionUID = 7894016302373484475L;

	//No relatorio esta sendo usado 0,1,2
	public static final int AVALIACAO = 0;
	public static final int ENTREVISTA = 1;
	public static final int PESQUISA = 2;
	public static final int AVALIACAOTURMA = 3;
	public static final int FICHAMEDICA = 4;

	public TipoQuestionario()
	{
		put(AVALIACAO, "Avaliação");
		put(ENTREVISTA, "Entrevista");
		put(PESQUISA, "Pesquisa");
		put(AVALIACAOTURMA, "Questionário de Avaliação de Curso");
		put(FICHAMEDICA, "Ficha Médica");
	}

	public static String getUrlVoltarList(int tipo, Long id)
	{
		String avaliacaoTurmaVoltar = "../avaliacaoTurma/list.action";
		if(id != null)
			avaliacaoTurmaVoltar = "../../desenvolvimento/turma/list.action?curso.id=" + id;
		
		String[] urlVoltar = new String[]{"../avaliacao/list.action", "../entrevista/list.action", "../pesquisa/list.action", avaliacaoTurmaVoltar, "../../sesmt/fichaMedica/list.action"};
		return urlVoltar[tipo];
	}

	public static String getDescricao(Integer tipo)
	{
		String retorno = "";
		switch (tipo)
		{
			case AVALIACAO:
				retorno = "avaliação";
				break;
			case ENTREVISTA:
				retorno = "entrevista";
				break;
			case PESQUISA:
				retorno = "pesquisa";
				break;
			case AVALIACAOTURMA:
				retorno = "avaliação da turma";
				break;
			case FICHAMEDICA:
				retorno = "ficha médica";
				break;
		}

		return retorno;
	}

	public static String getDescricaoMaisc(Integer tipo)
	{
		String retorno = "";
		switch (tipo)
		{
			case AVALIACAO:
				retorno = "Avaliação";
				break;
			case ENTREVISTA:
				retorno = "Entrevista de Desligamento";
				break;
			case PESQUISA:
				retorno = "Pesquisa";
				break;
			case AVALIACAOTURMA:
				retorno = "Avaliação da Turma";
				break;
			case FICHAMEDICA:
				retorno = "Ficha Médica";
				break;
		}

		return retorno;
	}

	public static int getAVALIACAO()
	{
		return AVALIACAO;
	}

	public static int getENTREVISTA()
	{
		return ENTREVISTA;
	}

	public static int getPESQUISA()
	{
		return PESQUISA;
	}

	public static int getAVALIACAOTURMA()
	{
		return AVALIACAOTURMA;
	}

	public static String getFiltro(Questionario questionario, String filtro)
	{
		String retorno = "";
		switch (questionario.getTipo())
		{
			case AVALIACAO:
				if(questionario.getTitulo() != null && !questionario.getTitulo().equals(""))
					retorno = "Título: " + questionario.getTitulo() + "\n";
				break;
			case ENTREVISTA:
				if(questionario.getTitulo() != null && !questionario.getTitulo().equals(""))
					retorno = "Título: " + questionario.getTitulo() + "\n";
				break;
			case PESQUISA:
				if(questionario.getTitulo() != null && !questionario.getTitulo().equals(""))
					retorno = "Título: " + questionario.getTitulo() + "\n";
				retorno += "Período: ";
				retorno += questionario.getPeriodoFormatado();
				break;
			case AVALIACAOTURMA:
				if(filtro != null && !filtro.equals(""))
				{
					try
					{
						TurmaManager turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
						Turma turma = turmaManager.findByIdProjection(Long.parseLong(filtro));					
						retorno = "Curso: " + turma.getCurso().getNome() + "\n" + "Turma: " + turma.getDescricao() + "\n" + "Instrutor: " + turma.getInstrutor();						
					} catch (Exception e)
					{
						retorno = ""; 
					}
				}
				else
					retorno = filtro;
				break;
			case FICHAMEDICA:
				if(questionario.getTitulo() != null && !questionario.getTitulo().equals(""))
					retorno = "Título: " + questionario.getTitulo() + "\n";
				break;
		}

		return retorno;
	}

	public static int getFICHAMEDICA()
	{
		return FICHAMEDICA;
	}	

}