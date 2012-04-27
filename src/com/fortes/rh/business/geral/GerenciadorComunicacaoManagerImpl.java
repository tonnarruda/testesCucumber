package com.fortes.rh.business.geral;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

public class GerenciadorComunicacaoManagerImpl extends GenericManagerImpl<GerenciadorComunicacao, GerenciadorComunicacaoDao> implements GerenciadorComunicacaoManager
{
	private static Logger logger = Logger.getLogger(GerenciadorComunicacaoManagerImpl.class);
	
	Mail mail; 
	ParametrosDoSistemaManager parametrosDoSistemaManager;
	CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	EmpresaManager empresaManager;
	PerfilManager perfilManager;
	PeriodoExperienciaManager periodoExperienciaManager;
	UsuarioEmpresaManager usuarioEmpresaManager;
	UsuarioMensagemManager usuarioMensagemManager;
	MensagemManager mensagemManager;
	AreaOrganizacionalManager areaOrganizacionalManager;
	CargoManager cargoManager;
	ProvidenciaManager providenciaManager;
	
	public void insereGerenciadorComunicacaoDefault(Empresa empresa) 
	{
		save(new GerenciadorComunicacao(Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa));
		save(new GerenciadorComunicacao(Operacao.QTD_CURRICULOS_CADASTRADOS, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa));
		save(new GerenciadorComunicacao(Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.ENCERRAMENTO_SOLICITACAO, MeioComunicacao.EMAIL, EnviarPara.CANDIDATO_NAO_APTO, empresa));
		save(new GerenciadorComunicacao(Operacao.ALTERAR_STATUS_SOLICITACAO, MeioComunicacao.EMAIL, EnviarPara.SOLICITANTE_SOLICITACAO, empresa));
		save(new GerenciadorComunicacao(Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO, MeioComunicacao.EMAIL, EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO, empresa));
		save(new GerenciadorComunicacao(Operacao.LIBERAR_QUESTIONARIO, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR, empresa));
		save(new GerenciadorComunicacao(Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa));
		save(new GerenciadorComunicacao(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa));
		save(new GerenciadorComunicacao(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR_AVALIADO, empresa));
		save(new GerenciadorComunicacao(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA, empresa));
		save(new GerenciadorComunicacao(Operacao.LIBERAR_TURMA, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR, empresa));
		save(new GerenciadorComunicacao(Operacao.CONTRATAR_COLABORADOR, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_SETOR_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.CANCELAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.CANCELAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.DESLIGAR_COLABORADOR_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.CONFIGURACAO_LIMITE_COLABORADOR, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_LIMITE_CONTRATO, empresa));
		save(new GerenciadorComunicacao(Operacao.BACKUP_AUTOMATICO, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_TECNICO, empresa));
		save(new GerenciadorComunicacao(Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL, empresa));
	}
	
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
			
			SolicitacaoManager solicitacaoManager = (SolicitacaoManager) SpringUtil.getBean("solicitacaoManager");
			solicitacaoManager.montaCorpoEmailSolicitacao(solicitacao, link, nomeSolicitante, nomeLiberador, body);
			
			String emailSolicitante = null;
			if(colaboradorSolicitante != null && colaboradorSolicitante.getContato() != null)
				emailSolicitante = colaboradorSolicitante.getContato().getEmail();

			if (emailSolicitante != null) 
			{
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.ALTERAR_STATUS_SOLICITACAO.getId(), empresa.getId());
	
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
     	Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.LIBERAR_QUESTIONARIO.getId(), empresa.getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId())){
				enviaEmailQuestionario(empresa, questionario, colaboradorQuestionarios);
			} 		
		}
    }
    
	public void enviaEmailQuestionario(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios) 
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
                try {
                	mail.send(empresa, parametros, "[Fortes RH] Nova " + label, corpo.toString(), colaboradorQuestionario.getColaborador().getContato().getEmail());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
	}

	private Collection<Integer> getIntervaloAviso (String diasLembrete) {
		if (diasLembrete == null)
			return new ArrayList<Integer>();
		
		String[] dias = diasLembrete.split("&");
		Collection<Integer> result = new ArrayList<Integer>(dias.length);
		for (String diaLembrete : dias)
			result.add(Integer.valueOf(diaLembrete.trim()));
		
		return result;
	}


	@SuppressWarnings("deprecation")
	public void enviaLembreteDeQuestionarioNaoLiberado() 
	{
		QuestionarioManager questionarioManager = (QuestionarioManager) SpringUtil.getBeanOld("questionarioManager");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId(), null);
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) 
		{
			try
			{
				Collection<Integer> diasLembretePesquisa = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());

				for (Integer diaLembretePesquisa : diasLembretePesquisa)
				{
					Calendar data = Calendar.getInstance();
					data.setTime(new Date());
					data.add(Calendar.DAY_OF_MONTH, +diaLembretePesquisa);

					Collection<Questionario> questionarios = questionarioManager.findQuestionarioNaoLiberados(data.getTime());

					for (Questionario questionario : questionarios)
					{
						String label = TipoQuestionario.getDescricao(questionario.getTipo());

						StringBuilder corpo = new StringBuilder();
						corpo.append("ATENÇÃO:<br>");
						corpo.append("a " + label + questionario.getTitulo() + " está prevista para iniciar no dia " + DateUtil.formataDiaMesAno(questionario.getDataInicio())+".<br>") ;
						corpo.append("Você ainda precisa liberá-la para que os colaboradores possam respondê-la.") ;

						if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId()))
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
	    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR_AVALIADO.getId())){
	    				mail.send(colaboradorAvaliacao.getColaborador().getEmpresa(), subject, body.toString(), null, colaboradorAvaliacao.getColaborador().getContato().getEmail());
	    			} 		
	    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId()))
	    			{
	    				Empresa empresa = empresaManager.findEmailsEmpresa(gerenciadorComunicacao.getEmpresa().getId());	
	    				mail.send(colaboradorAvaliacao.getColaborador().getEmpresa(), subject, body.toString(), null, empresa.getEmailRespRH());
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

					if (!colecaoExamesPrevistos.isEmpty())
					{
						DataSource[] files = ArquivoUtil.montaRelatorio(parametros, colecaoExamesPrevistos, "exames_previstos.jasper");
						
						Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.EXAMES_PREVISTOS.getId(), empresa.getId());
			    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId()))
			    			{
			    				Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
			    				String[] emails = colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosConfigurados));
			    				
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
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void enviaMensagemLembretePeriodoExperiencia() 
	{
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		Collection<PeriodoExperiencia> periodoExperiencias = periodoExperienciaManager.findAll();
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId(), null);
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) 
		{
			Collection<Integer> diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
			for (Integer diaLembrete : diasLembrete)
			{
				for (PeriodoExperiencia periodoExperiencia : periodoExperiencias)
				{
					if (periodoExperiencia.getEmpresa().getId() != gerenciadorComunicacao.getEmpresa().getId())
						continue;
					
					Integer dias = periodoExperiencia.getDias() - diaLembrete;
					if (dias <= 0)
						continue;
					
					Calendar dataAvaliacao = Calendar.getInstance();
					dataAvaliacao.add(Calendar.DAY_OF_YEAR, +diaLembrete);
					
	 				Collection<Colaborador> colaboradores = null;
	 				
					try {
						colaboradores = colaboradorManager.findAdmitidosHaDias(dias, periodoExperiencia.getEmpresa());
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					
					Integer diasAvaliacao = periodoExperiencia.getDias();
					String data = DateUtil.formataDiaMesAno(dataAvaliacao.getTime());
					
					for (Colaborador colaborador : colaboradores)
					{
						StringBuilder mensagem = new StringBuilder();
						mensagem.append("Período de Experiência: ")
								.append(diaLembrete)
								.append(" dias para a Avaliação de ").append(diasAvaliacao).append(" dias de ")
								.append(colaborador.getNome()).append(".\n\n");
						
						mensagem.append("Lembrete de Avaliação de ")
								.append(diasAvaliacao)
								.append(" dias do Período de Experiência.\n")
								.append("\nColaborador: ").append(colaborador.getNome());
						
						if (StringUtils.isNotBlank(colaborador.getNomeComercial()))
							mensagem.append(" (").append(colaborador.getNomeComercial()).append(") ");
						
						mensagem.append("\nCargo: ").append(colaborador.getFaixaSalarial().getDescricao())
								.append("\nÁrea: ").append(colaborador.getAreaOrganizacional().getDescricao())
								.append("\nData da avaliação: ").append(data);
						
						String link = "avaliacao/avaliacaoExperiencia/periodoExperienciaQuestionarioList.action?colaborador.id=" + colaborador.getId();
						
						
		    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId()))
		    			{
		    				Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);	
		    				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", "", usuariosConfigurados, colaborador, TipoMensagem.PERIODOEXPERIENCIA);
		    			}
		    			
		    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId()))
		    				usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional(mensagem.toString(), "RH", link, colaborador.getAreaOrganizacional().getDescricaoIds());
					}
				}
			}
		}
	}
	
	public void enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(Long colaboradorAvaliadoId, Long avaliacaoId, Usuario usuario, Empresa empresa) 
	{
//		Collection<UsuarioEmpresa> usuarioEmpresaPeriodoExperiencia = usuarioEmpresaManager.findUsuariosByEmpresaRole(empresa.getId(), "ROLE_VER_AREAS");

		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
		Colaborador colaboradorAvaliado = colaboradorManager.findByIdDadosBasicos(colaboradorAvaliadoId, null);

		Colaborador avaliador = colaboradorManager.findByUsuarioProjection(usuario.getId());
		String avaliadorNome = avaliador !=null ? avaliador.getNomeMaisNomeComercial():usuario.getNome();
		
		StringBuilder mensagem = new StringBuilder();
		mensagem.append("A avaliação do período de experiência do colaborador ")
				.append(colaboradorAvaliado.getNomeMaisNomeComercial())
				.append(" foi respondida por ").append(avaliadorNome);
		
		mensagem.append("\n\nColaborador: ").append(colaboradorAvaliado.getNomeMaisNomeComercial())
				.append("\nCargo: ").append(colaboradorAvaliado.getFaixaSalarial().getDescricao())
				.append("\nÁrea: ").append(colaboradorAvaliado.getAreaOrganizacional().getDescricao());
		
		String link = "avaliacao/avaliacaoExperiencia/prepareInsertAvaliacaoExperiencia.action?colaboradorQuestionario.colaborador.id=" + colaboradorAvaliado.getId() + "&respostaColaborador=true&preview=true&colaboradorQuestionario.avaliacao.id="+avaliacaoId;
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId(), empresa.getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) 
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId()))
			{	
				Collection<UsuarioEmpresa> usuarioEmpresaPeriodoExperiencia = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), avaliadorNome, link, usuarioEmpresaPeriodoExperiencia, avaliador, TipoMensagem.PERIODOEXPERIENCIA);
			}
		}
	}
	
	public void enviaAvisoOcorrenciaCadastrada(ColaboradorOcorrencia colaboradorOcorrencia, Long empresaId) 
	{
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
		Colaborador colaborador = colaboradorManager.findColaboradorByIdProjection(colaboradorOcorrencia.getColaborador().getId());
		
		String providenciaDescricao = "";
		Providencia providencia = null;
		if (colaboradorOcorrencia.getProvidencia() != null && colaboradorOcorrencia.getProvidencia().getId() != null) {
			providencia = providenciaManager.findById(colaboradorOcorrencia.getProvidencia().getId());
			providenciaDescricao = providencia.getDescricao();
		}
		
		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Nova ocorrência cadastrada para "+ colaborador.getNomeMaisNomeComercial() +".");
		mensagem.append("\nOcorrência      : " + colaboradorOcorrencia.getOcorrencia().getDescricao());
		mensagem.append("\nData de Início  : " + DateUtil.formataDiaMesAno(colaboradorOcorrencia.getDataIni()));
		mensagem.append("\nData de Término : " + DateUtil.formataDiaMesAno(colaboradorOcorrencia.getDataIni()));
		mensagem.append("\nProvidência     : " + providenciaDescricao);
		mensagem.append("\nObservação      : ");
		mensagem.append(colaboradorOcorrencia.getObservacao());
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRO_OCORRENCIA.getId(), colaboradorOcorrencia.getOcorrencia().getEmpresa().getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) 
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId()))
			{	
				Collection<UsuarioEmpresa> usuarioEmpresaPeriodoExperiencia = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(),"RH", null, usuarioEmpresaPeriodoExperiencia, null, TipoMensagem.INDIFERENTE);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void enviaMensagemNotificacaoDeNaoEntregaSolicitacaoEpi()
	{
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		
		StringBuilder mensagem;
		Collection<UsuarioEmpresa> usuarioEmpresaPeriodoExperiencia;
		Collection<Integer> diasLembrete;
		Collection<Colaborador> colaboradors;
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.LEMBRETE_ENTREGA_SOLICITACAO_EPI.getId(), null);
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) 
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId()))
			{	
				usuarioEmpresaPeriodoExperiencia = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
				
				colaboradors = colaboradorManager.findAguardandoEntregaEpi(diasLembrete, gerenciadorComunicacao.getEmpresa().getId());
				for (Colaborador colaborador : colaboradors) {
					
					mensagem = new StringBuilder();
					mensagem.append("Existem EPI's a serem entregues ao colaborador ")
					.append(colaborador.getNomeMaisNomeComercial())
					.append(".");
					
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", null, usuarioEmpresaPeriodoExperiencia, colaborador, TipoMensagem.INDIFERENTE);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void enviaMensagemNotificacaoDeNaoAberturaSolicitacaoEpi()
	{
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.LEMBRETE_ABERTURA_SOLICITACAO_EPI.getId(), null);
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) 
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId()))
			{	
				Collection<UsuarioEmpresa> usuarioEmpresaPeriodoExperiencia = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				
				Collection<Integer> diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
				Collection<Colaborador> colaboradors = colaboradorManager.findAdmitidosHaDiasSemEpi(diasLembrete, gerenciadorComunicacao.getEmpresa().getId());
				for (Colaborador colaborador : colaboradors) {
					
					StringBuilder mensagem = new StringBuilder();
					mensagem.append("Não foi criada uma Solicitação de EPI's para o colaborador ")
					.append(colaborador.getNomeMaisNomeComercial())
					.append(".");
					
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", null, usuarioEmpresaPeriodoExperiencia, colaborador, TipoMensagem.INDIFERENTE);
				}
			}
		}
	}
	
	public void enviarEmailContratacaoColaborador(String colaboradorNome, Empresa empresa) throws Exception 
	{
		String body = "<br>O candidato <b>" + colaboradorNome + "</b> foi contratado e seus dados "
		+ "estão disponíveis no <b>AC Pessoal</b> para complemento de suas informações.<br><br>";

		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CONTRATAR_COLABORADOR.getId(), empresa.getId());
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) 
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_SETOR_PESSOAL.getId()))
				mail.send(empresa, "[RH] Contratação de candidato", body, null, empresa.getEmailRespSetorPessoal());
		}
	}
	
	public void enviaMensagemCancelamentoSituacao(TSituacao situacao, String mensagem, HistoricoColaborador historicoColaborador) 
	{
		String mensagemFinal = mensagemManager.formataMensagemCancelamentoHistoricoColaborador(mensagem, historicoColaborador);

		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());

		String link = "cargosalario/historicoColaborador/prepareUpdate.action?historicoColaborador.id="+ historicoColaborador.getId() +"&colaborador.id=" + historicoColaborador.getColaborador().getId();
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CANCELAR_SITUACAO_AC.getId(), historicoColaborador.getColaborador().getEmpresa().getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos)
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId()))
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal, "AC Pessoal", link, usuarioEmpresas, null, TipoMensagem.HISTORICOCOLABORADOR);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void enviaMensagemDesligamentoColaboradorAC(String codigo, String empCodigo, String grupoAC, Empresa empresa) 
	{
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		Colaborador colaborador = colaboradorManager.findByCodigoAC(codigo, empresa);

		String link = "pesquisa/entrevista/prepareResponderEntrevista.action?colaborador.id=" + colaborador.getId() + "&voltarPara=../../index.action";
		String mensagem = "O Colaborador " + colaborador.getNomeComercial() + " foi desligado no AC Pessoal.\n\n Para preencher a Entrevista de Desligamento, acesse a listagem de Colaboradores.";
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(empCodigo, grupoAC);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.DESLIGAR_COLABORADOR_AC.getId(), empresa.getId());
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos)
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId()))
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem, "AC Pessoal", link, usuarioEmpresas, colaborador, TipoMensagem.DESLIGAMENTO);
		}
	}
	
	public void notificaBackup(String arquivoDeBackup){
		
		String titulo = "Backup do Banco";
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		String corpo = getCorpo(arquivoDeBackup,  parametrosDoSistema.getAppUrl());
		String email = parametrosDoSistema.getEmailDoSuporteTecnico();
		
		try {
			logger.info("Enviando e-mail para responsável (" + email + ") sobre backup do banco de dados.");
			
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.BACKUP_AUTOMATICO.getId(), null);
    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_TECNICO.getId())){
    				mail.send(null, titulo, corpo, null, email);
    				break;
    			} 
    		}
		
		
		} catch (Exception e) {
			logger.error("Erro ao enviar e-mail ao responsável (" + email + ")  sobre backup do banco de dados", e);
			e.printStackTrace();
		}
		
	}
	
	private String getCorpo(String backupFile, String appUrl) 
	{
		String link = getLink(appUrl, backupFile.substring(backupFile.lastIndexOf("/") + 1)); 
		return new StringBuilder()
				.append("O RH fez um backup automático do banco de dados.")
				.append("<br /><br />")
				.append("O arquivo foi salvo no diretório ")
				.append("<b>'").append(backupFile).append("'</b>.")
				.append("<br /><br />")
				.append("Você pode baixar o backup a partir do link abaixo,<br />")
				.append("<a href='" + link + "'>" + link + "</a>")
				.toString();
	}
	
	public void enviaEmailConfiguracaoLimiteColaborador(ConfiguracaoLimiteColaborador configuracaoLimiteColaborador, Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, Empresa empresa) 
	{
		try {
			String[] emails = empresaManager.findByIdProjection(empresa.getId()).getEmailRespLimiteContrato().split(";");
			StringBuilder body = new StringBuilder();
			
			body.append("Contrato: " + configuracaoLimiteColaborador.getDescricao() + "<br>");
			body.append("Área Organizacional: " + areaOrganizacionalManager.findByIdProjection(configuracaoLimiteColaborador.getAreaOrganizacional().getId()).getNome() + "<br><br>");
			body.append( "<table style='width:450px' ><thead><tr><th>Cargo</th><th>Limite</th></tr></thead><tbody>");
			
			for (QuantidadeLimiteColaboradoresPorCargo qtdLimiteColabCargo:  quantidadeLimiteColaboradoresPorCargos) 
			{
				body.append("<tr>" );
				body.append("<td>" + cargoManager.findByIdProjection(qtdLimiteColabCargo.getCargo().getId()).getNomeMercado() + "</td>" );
				body.append("<td align='right' style='width:60px'>" + qtdLimiteColabCargo.getLimite() + "</td>" );
				body.append("</tr>");
			}
			
			body.append("</tbody></table>");
			
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CONFIGURACAO_LIMITE_COLABORADOR.getId(), null);
    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_LIMITE_CONTRATO.getId())){
    				mail.send(empresa, "Novo Contrato ("+configuracaoLimiteColaborador.getDescricao()+") com limite de Colaboradores por Cargo adicionado.", body.toString(), null, emails);
    			} 
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getLink(String appUrl, String backupFile) 
	{
		String link = appUrl + "/backup/show.action?filename=" + backupFile; 
		return link;
	}
	
	public boolean existeConfiguracaoParaCandidatosModuloExterno(Long empresaId) 
	{
		Object[] valores = new Object[] {Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO.getId(), MeioComunicacao.CAIXA_MENSAGEM.getId(), EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL.getId(), empresaId};

		return getDao().verifyExists(new String[]{"operacao", "meioComunicacao", "enviarPara", "empresa.id"}, valores);
	}

	public void enviarAvisoEmailLiberacao(Turma turma, AvaliacaoTurma avaliacaoTurma, Long empresaId) {
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.LIBERAR_TURMA.getId(), empresaId);
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId())){
				enviarAvisoAvaliacaoEmail(turma, avaliacaoTurma, empresaId);
			} 
		}
	}
	
	@SuppressWarnings("deprecation")
	public void enviarAvisoEmail(Turma turma, Long empresaId) 
	{
		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBeanOld("colaboradorTurmaManager");
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findColaboradoresComEmailByTurma(turma.getId()); 

		String subject = "[Fortes RH] Lembrete: Curso " + turma.getCurso().getNome();
		String  body =  "#COLABORADOR#, você está matriculado no seguinte curso.<br>";
				body += "Curso: " + turma.getCurso().getNome() + "<br>";
				body += "Turma: " + turma.getDescricao() + "<br>";
				body += "Período: " + DateUtil.formataDiaMesAno(turma.getDataPrevIni()) + " - " + DateUtil.formataDiaMesAno(turma.getDataPrevFim()) + "<br>";
				body += "Horário: " + turma.getHorario() + "<br>";
		
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
		{
			try {
				mail.send(empresa, subject, null, body.replace("#COLABORADOR#", colaboradorTurma.getColaboradorNome()), colaboradorTurma.getColaborador().getContato().getEmail());
			} catch (Exception e)	{
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void enviarAvisoAvaliacaoEmail(Turma turma, AvaliacaoTurma avaliacaoTurma, Long empresaId) 
	{
		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBeanOld("colaboradorTurmaManager");
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findColaboradoresComEmailByTurma(turma.getId()); 
		
		String subject = "[Fortes RH] Avaliação " + avaliacaoTurma.getQuestionario().getTitulo() + " do curso " + turma.getCurso().getNome();
		String  body =  "#COLABORADOR#, a avaliação " + avaliacaoTurma.getQuestionario().getTitulo() + " do curso " + turma.getCurso().getNome() + " está liberada para ser respondida.";
		
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
		{
			try {
				mail.send(empresa, subject, null, body.replace("#COLABORADOR#", colaboradorTurma.getColaboradorNome()), colaboradorTurma.getColaborador().getContato().getEmail());
			} catch (Exception e)	{
				e.printStackTrace();
			}
		}
	}
	
	private Collection<UsuarioEmpresa> verificaUsuariosAtivosNaEmpresa(GerenciadorComunicacao gerenciadorComunicacao) 
	{
		Collection<UsuarioEmpresa> usuariosConfigurados = new ArrayList<UsuarioEmpresa>();
		
		if(gerenciadorComunicacao.getUsuarios() != null && gerenciadorComunicacao.getUsuarios().size() > 0)
			usuariosConfigurados = usuarioEmpresaManager.findUsuariosAtivo(LongUtil.collectionToCollectionLong(gerenciadorComunicacao.getUsuarios()), gerenciadorComunicacao.getEmpresa().getId());
		
		return usuariosConfigurados;
	}
	
	public void enviaMensagemCancelamentoContratacao(Colaborador colaborador, String mensagem) 
	{
		StringBuilder mensagemFinal = new StringBuilder();
		mensagemFinal.append("Cancelamento da contratação do colaborador. ");
		mensagemFinal.append("\r\n\r\n");
		mensagemFinal.append("<b>Motivo do cancelamento:</b> ");
		mensagemFinal.append("\r\n");
		mensagemFinal.append(mensagem);
		mensagemFinal.append("\r\n\r\n");
		mensagemFinal.append("<b>Dados do colaborador cancelado:</b> ");
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Nome: "+colaborador.getNomeComercial());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Estabelecimento: "+colaborador.getHistoricoColaborador().getEstabelecimento().getNome());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Área Organizacional: "+colaborador.getHistoricoColaborador().getAreaOrganizacional().getDescricao());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Cargo: "+colaborador.getHistoricoColaborador().getFaixaSalarial().getCargo().getNomeMercado());
		mensagemFinal.append("\r\n");

		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(colaborador.getEmpresa().getCodigoAC(), colaborador.getEmpresa().getGrupoAC());

		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CANCELAR_CONTRATACAO_AC.getId(), colaborador.getEmpresa().getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos)
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId()))
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal.toString(), "AC Pessoal", null, usuarioEmpresas, null, TipoMensagem.HISTORICOCOLABORADOR);
		}
	}

	@SuppressWarnings("deprecation")
	public void enviaMensagemCancelamentoSolicitacaoDesligamentoAC(Colaborador colaborador, String mensagem, String empresaCodigoAC, String grupoAC) 
	{
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		colaborador = colaboradorManager.findByCodigoAC(colaborador.getCodigoAC(), colaborador.getEmpresa());
		
		StringBuilder mensagemFinal = new StringBuilder();
		mensagemFinal.append("Cancelamento da solicitação de desligamento do colaborador ");
		mensagemFinal.append(colaborador.getNome());
		mensagemFinal.append("\r\n\r\n");
		mensagemFinal.append("<b>Motivo do cancelamento:</b> ");
		mensagemFinal.append("\r\n");
		mensagemFinal.append(mensagem);
		mensagemFinal.append("\r\n");
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(empresaCodigoAC, grupoAC);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC.getId(), colaborador.getEmpresa().getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos)
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId()))
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal.toString(), "AC Pessoal", null, usuarioEmpresas, null, TipoMensagem.DESLIGAMENTO);
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

	public void setPeriodoExperienciaManager(PeriodoExperienciaManager periodoExperienciaManager) {
		this.periodoExperienciaManager = periodoExperienciaManager;
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setUsuarioMensagemManager(UsuarioMensagemManager usuarioMensagemManager) {
		this.usuarioMensagemManager = usuarioMensagemManager;
	}

	public void setMensagemManager(MensagemManager mensagemManager) {
		this.mensagemManager = mensagemManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	
	public void setProvidenciaManager(ProvidenciaManager providenciaManager)
	{
		this.providenciaManager = providenciaManager;
	}

}
