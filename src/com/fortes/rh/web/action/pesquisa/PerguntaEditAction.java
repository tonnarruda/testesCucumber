package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

public class PerguntaEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	@Autowired private PerguntaManager perguntaManager;
	@Autowired private RespostaManager respostaManager;
	@Autowired private AspectoManager aspectoManager;

	private Pergunta pergunta = new Pergunta();
	private Pesquisa pesquisa;
	private Questionario questionario;

	private Collection<Aspecto> temas = new ArrayList<Aspecto>();
	private Collection<Resposta> respostas = new ArrayList<Resposta>();
	private Collection<Resposta> respostasSugeridas = new ArrayList<Resposta>();

	private Map tipoPerguntas;
	private String[] respostaObjetiva;
	private String[] multiplaResposta;
	private String[] respostaObjetivaSugerida;
	private Integer ordemSugerida = null;
	private Integer respostaSugerida = 0;

	private void prepare() throws Exception
	{
		if(pergunta != null && pergunta.getId() != null)
			pergunta = (Pergunta) perguntaManager.findPergunta(pergunta.getId());

		respostasSugeridas = respostaManager.findRespostasSugeridas(questionario.getId());
		temas = aspectoManager.findByQuestionario(questionario.getId());

		tipoPerguntas = new TipoPergunta();

		if(getActionErr() != null)
			addActionError(getActionErr());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try
		{
			if(respostaSugerida.equals(1))
				respostaObjetiva = (String[]) ArrayUtils.addAll(respostaObjetiva, respostaObjetivaSugerida);

			if(pergunta.getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA))
				respostaObjetiva = multiplaResposta;
			
			pergunta = perguntaManager.salvarPergunta(pergunta, respostaObjetiva, null, ordemSugerida);
			return Action.SUCCESS;

		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
			pergunta.setId(null);
			prepareInsert();
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try
		{
			pergunta.setPesquisa(pesquisa);

			if(respostaSugerida.equals(1))
				respostaObjetiva = (String[]) ArrayUtils.addAll(respostaObjetiva, respostaObjetivaSugerida);

			if(pergunta.getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA))
				respostaObjetiva = multiplaResposta;
			
			perguntaManager.atualizarPergunta(pergunta, respostaObjetiva, null);
			return Action.SUCCESS;

		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
			prepareUpdate();
			return Action.INPUT;
		}
	}

	public Pergunta getPergunta()
	{
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta)
	{
		this.pergunta = pergunta;
	}

	public Object getModel()
	{
		return getPergunta();
	}

	public Collection<Aspecto> getTemas()
	{
		return temas;
	}

	public Pesquisa getPesquisa()
	{
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa)
	{
		this.pesquisa = pesquisa;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public Map getTipoPerguntas()
	{
		return tipoPerguntas;
	}

	public Collection<Resposta> getRespostas()
	{
		return respostas;
	}

	public void setRespostas(Collection<Resposta> respostas)
	{
		this.respostas = respostas;
	}

	public String[] getRespostaObjetiva()
	{
		return respostaObjetiva;
	}

	public void setRespostaObjetiva(String[] respostaObjetiva)
	{
		this.respostaObjetiva = respostaObjetiva;
	}

	public Collection<Resposta> getRespostasSugeridas()
	{
		return respostasSugeridas;
	}

	public String[] getRespostaObjetivaSugerida()
	{
		return respostaObjetivaSugerida;
	}

	public void setRespostaObjetivaSugerida(String[] respostaObjetivaSugerida)
	{
		this.respostaObjetivaSugerida = respostaObjetivaSugerida;
	}

	public void setOrdemSugerida(Integer ordemSugerida)
	{
		this.ordemSugerida = ordemSugerida;
	}

	public Integer getOrdemSugerida()
	{
		return ordemSugerida;
	}

	public Integer getRespostaSugerida()
	{
		return respostaSugerida;
	}

	public void setRespostaSugerida(Integer respostaSugerida)
	{
		this.respostaSugerida = respostaSugerida;
	}
	public String[] getMultiplaResposta()
	{
		return multiplaResposta;
	}

	public void setMultiplaResposta(String[] multiplaResposta)
	{
		this.multiplaResposta = multiplaResposta;
	}
}