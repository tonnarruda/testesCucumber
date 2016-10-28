package com.fortes.rh.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.pesquisa.AspectoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

@Component
public class AspectoManagerImpl extends GenericManagerImpl<Aspecto, AspectoDao> implements AspectoManager
{
	@Autowired
	AspectoManagerImpl(AspectoDao aspectoDao) {
		setDao(aspectoDao);
	}
	
	public Aspecto findByIdProjection(Long aspectoId)
	{
		return getDao().findByIdProjection(aspectoId);
	}

	public Collection<Aspecto> findByQuestionario(Long questionarioId)
	{
		return getDao().findByQuestionario(questionarioId);
	}
	
	public Collection<Aspecto> agruparPerguntasByAspecto(Collection<Aspecto> aspectos, Collection<Pergunta> perguntas, int ordemInicial)
	{
		Collection<Aspecto> retorno = new ArrayList<Aspecto>();
		int i = ordemInicial;

		for (Aspecto aspecto : aspectos)
		{
			Aspecto aspectoTmp = (Aspecto) aspecto.clone();
			Collection<Pergunta> perguntasTmp = new ArrayList<Pergunta>();

			for (Pergunta pergunta : perguntas)
			{
				if(pergunta.getAspecto() != null && pergunta.getAspecto().getId() != null && pergunta.getAspecto().getId().equals(aspectoTmp.getId()))
				{
					pergunta.setOrdem(i);
					perguntasTmp.add(pergunta);
					i++;
				}
			}

			aspectoTmp.setPerguntas(perguntasTmp);
			retorno.add(aspectoTmp);
		}
		return retorno;
	}

	public Aspecto saveOrGetAspectoByNome(String aspectoNome, Long questionarioId)
	{
		Aspecto aspectoRetorno = null;

		if(!aspectoNome.trim().equals(""))
		{
			aspectoRetorno = getDao().findByNomeQuestionario(aspectoNome, questionarioId);

			if(aspectoRetorno == null)
			{
				aspectoRetorno = new Aspecto();
				aspectoRetorno.setNome(aspectoNome);
				aspectoRetorno.setProjectionQuestionarioId(questionarioId);
				aspectoRetorno = getDao().save(aspectoRetorno);
			}
		}

		return aspectoRetorno;
	}

	public Collection<Pergunta> desagruparPerguntasByAspecto(Collection<Aspecto> aspectos)
	{
		Collection<Pergunta> perguntasRetorno = new ArrayList<Pergunta>();
		for (Aspecto aspecto : aspectos)
		{
			perguntasRetorno.addAll(aspecto.getPerguntas());
		}
		return perguntasRetorno;
	}

	public Collection<CheckBox> populaCheckOrderNome(Long questionarioId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Aspecto> aspectos = getDao().findByQuestionario(questionarioId);
			checks = CheckListBoxUtil.populaCheckListBox(aspectos, "getId", "getNome", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	public void removerAspectosDoQuestionario(Long questionarioId)
	{
		getDao().removerAspectosDoQuestionario(questionarioId);
	}

	public void removerAspectosDaAvaliacao(Long avaliacaoId)
	{
		getDao().removerAspectosDaAvaliacao(avaliacaoId);
	}

	public HashMap<Long, Aspecto> clonarAspectos(Long questionarioId, Questionario questionarioClonado, Avaliacao avaliacaoClonada)
	{
		Collection<Aspecto> aspectos = getDao().findByQuestionario(questionarioId);
		
		HashMap<Long , Aspecto> aspectosClonados = new HashMap<Long, Aspecto>();
		
		for (Aspecto aspecto : aspectos)
		{
			Aspecto aspectoClone = new Aspecto();
			aspectoClone = (Aspecto)aspecto.clone();
			aspectoClone.setId(null);
			aspectoClone.setQuestionario(questionarioClonado);
			aspectoClone.setAvaliacao(avaliacaoClonada);

			aspectoClone = getDao().save(aspectoClone);
			
			aspectosClonados.put(aspecto.getId(), aspectoClone);
		}
		
		return aspectosClonados;
	}

	public String getAspectosByAvaliacao(Long avaliacaoId)
	{
		Collection<String> aspectos = getDao().getNomesByAvaliacao(avaliacaoId);
		if(aspectos == null || aspectos.isEmpty())
			return "";
		else
			return StringUtil.converteCollectionToStringComAspas(aspectos);
	}

	public Aspecto saveByAvaliacao(Aspecto aspecto, Avaliacao avaliacao)
	{
		Aspecto aspectoRetorno = null;

		if(aspecto != null && !aspecto.getNome().trim().equals(""))
		{
			aspectoRetorno = getDao().findByNomeAvaliacao(aspecto.getNome(), avaliacao.getId());

			if(aspectoRetorno == null)
			{
				aspectoRetorno = new Aspecto();
				aspectoRetorno.setNome(aspecto.getNome());
				aspectoRetorno.setAvaliacao(avaliacao);
				aspectoRetorno = getDao().save(aspectoRetorno);
			}
		}

		return aspectoRetorno;
	}

	public String getAspectosFormatadosByAvaliacao(Long avaliacaoId) {
		
		Collection<String> aspectosNomes = getDao().getNomesByAvaliacao(avaliacaoId);
		
		if(aspectosNomes.isEmpty())
			return "Nenhum aspecto cadastrado";
		
		StringBuilder nomes = new StringBuilder();
		for (String nome : aspectosNomes) 
		{
			nomes.append("<a href=\"#\" onclick=\"setAspecto(this.innerText)\">" + nome.replaceAll("\'", "\"") + "</a><br>");
		}
		
		return nomes.toString();
	}
}