package com.fortes.rh.business.geral;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;

public class GerenciadorComunicacaoManagerImpl extends GenericManagerImpl<GerenciadorComunicacao, GerenciadorComunicacaoDao> implements GerenciadorComunicacaoManager
{
	ParametrosDoSistemaManager parametrosDoSistemaManager;
	CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	EmpresaManager empresaManager;
	private PerfilManager perfilManager;
	Mail mail;
	
	public void enviaEmailCandidatosNaoAptos(Empresa empresa, Long solicitacaoId) throws Exception {
		
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
	
	public void enviaEmailSolicitanteSolicitacao(Solicitacao solicitacao, Empresa empresa, Usuario usuario)
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
	
	public void enviarEmailLiberadorSolicitacao(Solicitacao solicitacao, Empresa empresa, String[] emailsAvulsos) throws Exception
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
	
	public void enviarLembreteAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Empresa empresa) {
		
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
	
    public void enviaEmailQuestionarioLiberado(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios)
    {
        enviaEmailQuestionario(empresa, questionario, colaboradorQuestionarios, Operacao.LIBERAR_QUESTIONARIO);
    }
    
    public void enviaEmailQuestionarioNaoRespondido(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios) 
    {
    	enviaEmailQuestionario(empresa, questionario, colaboradorQuestionarios, Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO);
    }

	private void enviaEmailQuestionario(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios, Operacao operacao) 
	{
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
    	
        if (colaboradorQuestionarios != null && !colaboradorQuestionarios.isEmpty())
        {
            String label = TipoQuestionario.getDescricao(questionario.getTipo());

    		StringBuilder corpo = new StringBuilder();
    		corpo.append("Existe uma nova " + label + " para ser respondida por você.<br><br>");
    		corpo.append("Titulo: " + questionario.getTitulo() + "<br>");
    		corpo.append("Período: "+ DateUtil.formataDiaMesAno(questionario.getDataInicio()) + " a " + DateUtil.formataDiaMesAno(questionario.getDataFim()) + "<br><br>");
    		corpo.append("Acesse o Fortes RH em: <br>");
    		corpo.append("<a href=\"" + parametros.getAppUrl() + "\">Fortes RH</a><br><br>");
    		corpo.append("Copyright© by Fortes Informática LTDA<br>");
    		corpo.append("http://www.fortesinformatica.com.br");

            for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionarios)
            {
                try
                {
                	Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(operacao.getId(), empresa.getId());
            		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
    					if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId())){
    						mail.send(empresa, parametros, "[Fortes RH] Nova " + label, corpo.toString(), colaboradorQuestionario.getColaborador().getContato().getEmail());
    					} 		
    				}
                	
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public void enviaLembreteDeQuestionarioNaoLiberado() 
	{
    	Collection<Integer> diasLembretePesquisa = parametrosDoSistemaManager.getDiasLembretePesquisa();
		QuestionarioManager questionarioManager = (QuestionarioManager) SpringUtil.getBeanOld("questionarioManager");

        for (Integer diaLembretePesquisa : diasLembretePesquisa)
        {
        	Calendar data = Calendar.getInstance();
        	data.setTime(new Date());
        	data.add(Calendar.DAY_OF_MONTH, +diaLembretePesquisa);

	        Collection<Questionario> questionarios = questionarioManager.findQuestionarioNaoLiberados(data.getTime());

	        for (Questionario questionario : questionarios)
	        {
	        	try
	        	{
	        		String label = TipoQuestionario.getDescricao(questionario.getTipo());

	        		StringBuilder corpo = new StringBuilder();
	        		corpo.append("ATENÇÃO:<br>");
	        		corpo.append("a " + label + questionario.getTitulo() + " está prevista para iniciar no dia " + DateUtil.formataDiaMesAno(questionario.getDataInicio())+".<br>") ;
	        		corpo.append("Você ainda precisa liberá-la para que os colaboradores possam respondê-la.") ;

	        		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId(), questionario.getEmpresa().getId());
	        		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
	        			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
	        				mail.send(questionario.getEmpresa(), "[Fortes RH] Lembrete de " + label + " não Liberada", corpo.toString(), null, questionario.getEmpresa().getEmailRespRH());
	        			} 		
	        		}

	        	}
	        	catch (Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        }
        }
		
	}

	public boolean verifyExists(GerenciadorComunicacao gerenciadorComunicacao) 
	{
		return getDao().verifyExists(gerenciadorComunicacao);
	}
	
	public void enviaEmailResponsavelRh(String nomeCandidato, Long empresaId) 
	{
		String subject = "Novo candidato (" + nomeCandidato +")";

		Empresa empresa = empresaManager.findById(empresaId);
		StringBuilder body = new StringBuilder();
		body.append("O candidato " + nomeCandidato + ", <br>");
		body.append("se cadastrou na empresa " + empresa.getNome() );

		try
		{
    		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO.getId(), empresaId);
    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
    				mail.send(empresa, subject, body.toString(), null, empresa.getEmailRespRH());
    			} 		
    		}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void enviaEmailQtdCurriculosCadastrados(Collection<Empresa> empresas, Date inicioMes, Date fimMes, Collection<Candidato> candidatos) 
	{
		String subject = "Candidatos cadastros no período: " + DateUtil.formataDiaMesAno(inicioMes) + " a " + DateUtil.formataDiaMesAno(fimMes);
		
		StringBuilder body = new StringBuilder("Candidatos cadastrados no período: " + DateUtil.formataDiaMesAno(inicioMes) + " a " + DateUtil.formataDiaMesAno(fimMes) + "<br>");
		body.append( "<table>" );
		body.append("<thead><tr>");
		body.append("<th>Empresa</th>");
		body.append("<th>|</th>");
		body.append("<th>Origem</th>");
		body.append("<th>|</th>");
		body.append("<th>Qtd. cadastros</th>");
		body.append("</tr></thead><tbody>");
		
		OrigemCandidato origemCandidato = new OrigemCandidato();
		
		for (Candidato candidato : candidatos) 
		{
			body.append("<tr>");
			body.append("<td>" + candidato.getEmpresa().getNome() + "</td>");
			body.append("<td>|</td>");
			body.append("<td>" + origemCandidato.get(candidato.getOrigem())+ "</td>");
			body.append("<td>|</td>");
			body.append("<td align='center'>" + candidato.getQtdCurriculosCadastrados() + "</td>");
			body.append("</tr>");
		}
		
		body.append("</tbody></table>");
		
		try
		{
			for (Empresa empresa : empresas) 
			{
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.QTD_CURRICULOS_CADASTRADOS.getId(), empresa.getId());
	    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
	    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
	    				mail.send(empresa, subject, body.toString(), null, empresa.getEmailRespRH());
	    			} 		
	    		}
			}
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradores) 
	{
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
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId(), colaboradorAvaliacao.getColaborador().getEmpresa().getId());
	    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
	    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
	    				mail.send(colaboradorAvaliacao.getColaborador().getEmpresa(), subject, body.toString(), null, colaboradorAvaliacao.getColaborador().getContato().getEmail());
	    			} 		
	    		}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void enviaLembreteExamesPrevistos(Collection<Empresa> empresas) {
		try	
		{
			ExameManager exameManager = (ExameManager) SpringUtil.getBeanOld("exameManager");
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
			
			Map<String,Object> parametros = new HashMap<String, Object>();
			
			for (Empresa empresa : empresas) 
			{
				Collection<String> emailsCollection = colaboradorManager.findEmailsByPapel(empresa.getId(), "ROLE_RECEBE_EXAMES_PREVISTOS");
				if (!emailsCollection.isEmpty())
				{
					Collection<ExamesPrevistosRelatorio> colecaoExamesPrevistos;
					Date ultimoDiaDoMesPosterior = DateUtil.getUltimoDiaMes(DateUtil.incrementaMes(new Date(), 1));
					String dataString = DateUtil.formataDiaMesAno(ultimoDiaDoMesPosterior);
					String subject = "(" + empresa.getNome()+ ")" + "Exames previstos até " + dataString;
					String body = "<B>" + empresa.getNome() + "<B><br><br>" + "Segue em anexo Relatório de Exames Previstos até " + dataString;
					
					try 
					{
						char barra = File.separatorChar;
						String path = ArquivoUtil.getSystemConf().getProperty("sys.path");
						path = path + barra + "WEB-INF" + barra +"report" + barra; 
						ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
						String msgRegistro = Autenticador.getMsgAutenticado("");
						String logo = ArquivoUtil.getPathLogoEmpresa() + empresa.getLogoUrl();
				    	Cabecalho cabecalho = new Cabecalho("Exames Previstos até " + DateUtil.formataDiaMesAno(ultimoDiaDoMesPosterior), empresa.getNome(), "", "[Envio Automático]", parametrosDoSistema.getAppVersao(), logo, msgRegistro);
				    	cabecalho.setLicenciadoPara(empresa.getNome());
				    	parametros.put("CABECALHO", cabecalho);
				    	parametros.put("SUBREPORT_DIR", path);
				    	
				    	colecaoExamesPrevistos = exameManager.findRelatorioExamesPrevistos(empresa.getId(), ultimoDiaDoMesPosterior, null, null, null, null, 'N', true, false);

						if (!colecaoExamesPrevistos.isEmpty()){
							DataSource[] files = ArquivoUtil.montaRelatorio(parametros, colecaoExamesPrevistos, "exames_previstos.jasper");
							
							String[] emails = new String[emailsCollection.size()];
							emails = emailsCollection.toArray(emails);
							
							Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.EXAMES_PREVISTOS.getId(), empresa.getId());
				    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
				    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getId())){
				    				mail.send(empresa, subject, files, body, emails);		
				    			} 		
				    		}


						}
						
					} 
					catch (ColecaoVaziaException e) 
					{
						throw new Exception(e.getMessage(), e);
					}
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		
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

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}
