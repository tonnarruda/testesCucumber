package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.Mail;

public class ColaboradorPeriodoExperienciaAvaliacaoManagerImpl extends GenericManagerImpl<ColaboradorPeriodoExperienciaAvaliacao, ColaboradorPeriodoExperienciaAvaliacaoDao> implements ColaboradorPeriodoExperienciaAvaliacaoManager 
{
	private Mail mail;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	public void saveConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes) 
	{
		if(colaboradorAvaliacoes != null)
		{
			for (ColaboradorPeriodoExperienciaAvaliacao colabPerExpAvaliacao : colaboradorAvaliacoes) 
			{
				if (colabPerExpAvaliacao.getAvaliacao() != null && colabPerExpAvaliacao.getAvaliacao().getId() != null)
				{
					colabPerExpAvaliacao.setColaborador(colaborador);
					getDao().save(colabPerExpAvaliacao);
				}
			}
		}
	}

	public void removeConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador) 
	{
		getDao().removeByColaborador(colaborador.getId());
	}

	public Collection<ColaboradorPeriodoExperienciaAvaliacao> findByColaborador(Long colaboradorId) 
	{
		return getDao().findByColaborador(colaboradorId);
	}

	public void enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo() 
	{
		Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradores = getDao().getColaboradoresComAvaliacaoVencidaHoje();
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		String appUrl = parametrosDoSistema.getAppUrl();
		String subject = "[Fortes RH] Avaliação do Período de Experiência";
		StringBuilder body;
		
		for (ColaboradorPeriodoExperienciaAvaliacao colaboradorAvaliacao : colaboradores) 
		{
			body = new StringBuilder();
			body.append("Sr(a) " + colaboradorAvaliacao.getColaborador().getNome() + ", <br><br>");
			body.append("Por gentileza, preencha sua avaliação para o período de experiência de " + colaboradorAvaliacao.getPeriodoExperiencia().getDias() + " dias <br>");
			body.append("disponível em ");
			body.append("<a href='" + appUrl + "/avaliacao/avaliacaoExperiencia/prepareInsertAvaliacaoExperiencia.action?colaboradorQuestionario.colaborador.id=" + colaboradorAvaliacao.getColaborador().getId() + "&respostaColaborador=true&colaboradorQuestionario.avaliacao.id=" + colaboradorAvaliacao.getAvaliacao().getId() +  
						"'>" + colaboradorAvaliacao.getAvaliacao().getTitulo() + "</a>");			
			
			try
			{
				mail.send(colaboradorAvaliacao.getColaborador().getEmpresa(), subject, body.toString(), null, colaboradorAvaliacao.getColaborador().getContato().getEmail());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}