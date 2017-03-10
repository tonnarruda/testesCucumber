package com.fortes.rh.web.action.pesquisa;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class PerguntaListAction extends MyActionSupportList
{
	@Autowired private PerguntaManager perguntaManager;
	@Autowired private AspectoManager aspectoManager;
	@Autowired private QuestionarioManager questionarioManager;

	private Pergunta pergunta;
	private Aspecto aspecto;
	private Questionario questionario;

	private Collection<Pergunta> perguntas;
	private Collection<Aspecto> aspectos;

	private String msg = "";
	private char sinal = '+';
	private int ultimaOrdenacao;
	private int posicaoSugerida;
	
	private String urlVoltar;
	
	private TipoPergunta tipoPergunta = new TipoPergunta();
	private TipoQuestionario tipoQuestionario = new TipoQuestionario();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		questionario = questionarioManager.findByIdProjection(questionario.getId());
		aspectos = aspectoManager.findByQuestionario(questionario.getId());

		if(aspecto == null || aspecto.getId() == null)
			perguntas = perguntaManager.getPerguntasRespostaByQuestionario(questionario.getId());
		else
		{
			aspecto = aspectoManager.findByIdProjection(aspecto.getId());
			perguntas = perguntaManager.getPerguntasRespostaByQuestionarioAspecto(questionario.getId(),new Long[]{aspecto.getId()});
		}

		this.ultimaOrdenacao = perguntaManager.getUltimaOrdenacao(questionario.getId());

		if(getActionErr() != null && !getActionErr().equals(""))
			addActionError(getActionErr());

		//usei uma outra variavel e não setactionmsg pq estava ficando com a msg duplicada
		if(!msg.equals(""))
			addActionMessage(msg);
		
		//monta a url do botão voltar pelo questionario
		if(questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA))
			urlVoltar = "../avaliacaoTurma/list.action";
		else
			urlVoltar = questionarioManager.montaUrlVoltar(questionario.getId());

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			perguntaManager.removerPergunta(pergunta);
			setActionMsg("Pergunta excluída com sucesso.");
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
		}
		
		return list();
	}

	public String reordenar() throws Exception
	{
		try
		{
			perguntaManager.reordenarPergunta(pergunta, sinal);
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
		}
		
		return list();
	}

	public String alterarPosicao() throws Exception
	{
		try
		{
			perguntaManager.alterarPosicao(pergunta, posicaoSugerida);
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
		}
		
		return list();
	}

	public String embaralharPerguntas() throws Exception
	{
		try
		{
			perguntaManager.embaralharPerguntas(questionario.getId());
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
		}
		
		return list();
	}

	public Collection<Pergunta> getPerguntas()
	{
		return perguntas;
	}

	public Pergunta getPergunta()
	{
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta)
	{
		this.pergunta = pergunta;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public Aspecto getAspecto()
	{
		return aspecto;
	}

	public void setAspecto(Aspecto aspecto)
	{
		this.aspecto = aspecto;
	}

	public Collection<Aspecto> getAspectos()
	{
		return aspectos;
	}

	public char getSinal()
	{
		return sinal;
	}

	public void setSinal(char sinal)
	{
		this.sinal = sinal;
	}

	public int getUltimaOrdenacao()
	{
		return ultimaOrdenacao;
	}

	public void setPosicaoSugerida(int posicaoSugerida)
	{
		this.posicaoSugerida = posicaoSugerida;
	}

	public TipoPergunta getTipoPergunta()
	{
		return tipoPergunta;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public String getUrlVoltar()
	{
		return urlVoltar;
	}

	public TipoQuestionario getTipoQuestionario()
	{
		return tipoQuestionario;
	}
}