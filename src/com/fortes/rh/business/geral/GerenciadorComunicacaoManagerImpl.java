package com.fortes.rh.business.geral;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;

public class GerenciadorComunicacaoManagerImpl extends GenericManagerImpl<GerenciadorComunicacao, GerenciadorComunicacaoDao> implements GerenciadorComunicacaoManager
{
	ParametrosDoSistemaManager parametrosDoSistemaManager;
	CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private PerfilManager perfilManager;
	Mail mail;
	
	public void executeEncerrarSolicitacao(Empresa empresa, Long solicitacaoId) throws Exception {
		
		if (empresa.getEmailCandidatoNaoApto() && StringUtils.isNotBlank(empresa.getMailNaoAptos())){
			
			String body = empresa.getMailNaoAptos();
			String subject = "Solicitação de Candidatos";

			String[] emails = candidatoSolicitacaoManager.getEmailNaoAptos(solicitacaoId, empresa);

			if (emails.length > 0) {

				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.ENCERRAMENTO_SOLICITACAO.getId(), empresa.getId());

				for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
					if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.CANDIDATO_NAO_APTO.getId())){
						mail.send(empresa, subject, body, null, emails);
					} 		
				}
			}
		}
	}
	
	public void emailSolicitante(Solicitacao solicitacao, Empresa empresa, Usuario usuario)
	{
		try {
			ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findById(1L);
			
			String link = parametrosDoSistema.getAppUrl() + "/captacao/solicitacao/prepareUpdate.action?solicitacao.id=" + solicitacao.getId();

			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			Colaborador colaboradorSolicitante = colaboradorManager.findByUsuarioProjection(solicitacao.getSolicitante().getId());
			Colaborador colaboradorLiberador = colaboradorManager.findByUsuarioProjection(usuario.getId());
			
			String nomeSolicitante = colaboradorSolicitante !=null ? colaboradorSolicitante.getNomeMaisNomeComercial():"";
			String nomeLiberador = colaboradorLiberador !=null ? colaboradorLiberador.getNomeMaisNomeComercial():usuario.getNome();
			
			String subject = "Status da solicitação de pessoal";
			StringBuilder body = new StringBuilder("<span style='font-weight:bold'>O usuário "+ nomeLiberador + " alterou o status da solicitação " + solicitacao.getDescricao() + " da empresa " + empresa.getNome() + " para " + solicitacao.getStatusFormatado().toLowerCase()  +".</span><br>");
			
			montaCorpoEmailSolicitacao(solicitacao, link, nomeSolicitante, nomeLiberador, body);
			
			String emailSolicitante = null;
			if(colaboradorSolicitante != null && colaboradorSolicitante.getContato() != null)
				emailSolicitante = colaboradorSolicitante.getContato().getEmail();

			if (emailSolicitante != null) 
			{
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.ALTEREAR_STATUS_SOLICITACAO.getId(), empresa.getId());
	
				for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
					if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.SOLICITANTE_SOLICITACAO.getId())){
						mail.send(empresa, parametrosDoSistema, subject, body.toString(), new String[]{emailSolicitante, empresa.getEmailRespRH()});
					} 		
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void montaCorpoEmailSolicitacao(Solicitacao solicitacao, String link, String nomeSolicitante, String nomeLiberador, StringBuilder body)
	{
		body.append("<br>Descrição: " + solicitacao.getDescricao());
		body.append("<br>Data: " + DateUtil.formataDiaMesAno(solicitacao.getData()));
		body.append("<br>Motivo: " + solicitacao.getMotivoSolicitacao().getDescricao());
		body.append("<br>Estabelecimento: " + solicitacao.getEstabelecimento().getNome());
		body.append("<br>Solicitante: " + nomeSolicitante);
		body.append("<br>Status: " + solicitacao.getStatusFormatado());
		body.append("<br>Liberador: " + nomeLiberador);
		body.append("<br>Observação do Liberador: " + StringUtils.trimToEmpty(solicitacao.getObservacaoLiberador()));
		
		body.append("<br><br>Acesse o RH para mais detalhes:<br>");
		body.append("<a href='" + link + "'>RH</a>");
	}
	
	public void enviarEmailParaLiberadorSolicitacao(Solicitacao solicitacao, Empresa empresa, String[] emailsAvulsos) throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findById(1L);
		String link = parametrosDoSistema.getAppUrl();
		
		Collection<String> emails = perfilManager.getEmailsByRoleLiberaSolicitacao(empresa.getId());
		incluiEmails(emails, emailsAvulsos);
		
		emails.add(empresa.getEmailRespRH());
		
		if (emails != null && !emails.isEmpty())
		{
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			Colaborador solicitante = colaboradorManager.findByUsuarioProjection(solicitacao.getSolicitante().getId());
			
			String nomeSolicitante = "";
			if(solicitante != null)
				nomeSolicitante = solicitante.getNomeMaisNomeComercial();
		
			String nomeLiberador = "";
			if(solicitacao.getStatus() != StatusAprovacaoSolicitacao.ANALISE)
		        nomeLiberador = nomeSolicitante;
			
			String subject = "Liberação de Solicitação de Pessoal";
			StringBuilder body = new StringBuilder("Existe uma Solicitação de Pessoal na empresa " + empresa.getNome() + " aguardando liberação.<br>");
			
			if (solicitacao.getDescricao() != null)
				body.append("<p style=\"font-weight:bold;\">" + solicitacao.getDescricao() + "</p>");
			
			montaCorpoEmailSolicitacao(solicitacao, link, nomeSolicitante, nomeLiberador, body);
			
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.LIBERAR_SOLICITACAO.getId(), empresa.getId());

			for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
				if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.LIBERADOR_SOLICITACAO.getId())){
					mail.send(empresa, parametrosDoSistema, subject, body.toString(), StringUtil.converteCollectionToArrayString(emails));
				} 		
			}
		}
	}
	
	private void incluiEmails(Collection<String> emails, String[] emailsAvulsos) 
	{
		if(emailsAvulsos != null)
		{
			for (String emailAvulso : emailsAvulsos) 
			{
				emails.add(emailAvulso);
			}
		}
	}
	
	public void enviarLembrete(Long avaliacaoDesempenhoId, Empresa empresa) {
		
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
		Collection<Colaborador> avaliadores = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenhoId, false, false);
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
		
		for (Colaborador avaliador : avaliadores)
		{
			try
			{
				StringBuilder corpo = new StringBuilder();
				corpo.append("ATENÇÃO:<br>");
				corpo.append("Existe Avaliação de Desempenho para ser respondida.<br>Por favor acesse <a href=\" "+ parametros.getAppUrl() + "\">RH</a>") ;
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.getId(), empresa.getId());

				for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
					if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId())){
						mail.send(empresa, parametros, "[RH] Lembrete responder Avaliação de Desempenho", corpo.toString(), avaliador.getContato().getEmail());
					} 		
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public boolean verifyExists(GerenciadorComunicacao gerenciadorComunicacao) {
		return getDao().verifyExists(gerenciadorComunicacao);
	}
	
	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setPerfilManager(PerfilManager perfilManager) {
		this.perfilManager = perfilManager;
	}

}
