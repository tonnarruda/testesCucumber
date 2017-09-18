package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.LntManager;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.TipoCartao;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cartao;
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
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.ModelUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings("deprecation")
public class GerenciadorComunicacaoManagerImpl extends GenericManagerImpl<GerenciadorComunicacao, GerenciadorComunicacaoDao> implements GerenciadorComunicacaoManager
{
	private static Logger logger = Logger.getLogger(GerenciadorComunicacaoManagerImpl.class);
	
	ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	ParticipanteCursoLntManager participanteCursoLntManager;
	ParametrosDoSistemaManager parametrosDoSistemaManager;
	AreaOrganizacionalManager areaOrganizacionalManager;
	PeriodoExperienciaManager periodoExperienciaManager;
	MotivoSolicitacaoManager motivoSolicitacaoManager;
	UsuarioMensagemManager usuarioMensagemManager;
	ComissaoMembroManager comissaoMembroManager;
	UsuarioEmpresaManager usuarioEmpresaManager;
	ProvidenciaManager providenciaManager;
	ColaboradorManager colaboradorManager;
	MensagemManager mensagemManager;
	EmpresaManager empresaManager;
	PerfilManager perfilManager;
	CartaoManager cartaoManager;
	CargoManager cargoManager;
	CidManager cidManager;
	LntManager lntManager;
	Mail mail;
	
	
	public void insereGerenciadorComunicacaoDefault(Empresa empresa) 
	{
		save(new GerenciadorComunicacao(Operacao.CADASTRAR_CANDIDATO_MODULO_EXTERNO, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa));
		save(new GerenciadorComunicacao(Operacao.QTD_CURRICULOS_CADASTRADOS, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa));
		save(new GerenciadorComunicacao(Operacao.CURRICULO_AGUARDANDO_APROVACAO_MODULO_EXTERNO, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.ENCERRAR_SOLICITACAO, MeioComunicacao.EMAIL, EnviarPara.CANDIDATO_NAO_APTO, empresa));
		save(new GerenciadorComunicacao(Operacao.ALTERAR_STATUS_SOLICITACAO, MeioComunicacao.EMAIL, EnviarPara.SOLICITANTE_SOLICITACAO, empresa));
		save(new GerenciadorComunicacao(Operacao.AVALIACAO_DESEMPENHO_A_RESPONDER, MeioComunicacao.EMAIL, EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO, empresa));
		save(new GerenciadorComunicacao(Operacao.LIBERAR_PESQUISA, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR, empresa));
		save(new GerenciadorComunicacao(Operacao.PESQUISA_NAO_LIBERADA, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa, "2"));
		save(new GerenciadorComunicacao(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa, "2"));
		save(new GerenciadorComunicacao(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR_AVALIADO, empresa, "2"));
		save(new GerenciadorComunicacao(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA, empresa, "2"));
		save(new GerenciadorComunicacao(Operacao.LIBERAR_AVALIACAO_TURMA, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR, empresa));
		save(new GerenciadorComunicacao(Operacao.CONTRATAR_COLABORADOR_AC, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_SETOR_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.CANCELAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.CANCELAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.DESLIGAR_COLABORADOR_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.DESLIGAR_COLABORADOR_AC, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa));
		save(new GerenciadorComunicacao(Operacao.DESLIGAR_COLABORADOR_AC, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA, empresa));
		save(new GerenciadorComunicacao(Operacao.DESLIGAR_COLABORADOR_AC, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA, empresa));
		save(new GerenciadorComunicacao(Operacao.CADASTRAR_LIMITE_COLABORADOR_CARGO, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_LIMITE_CONTRATO, empresa));
		save(new GerenciadorComunicacao(Operacao.GERAR_BACKUP, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_TECNICO, empresa));
		save(new GerenciadorComunicacao(Operacao.CANCELAR_CONTRATACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL, empresa));
		save(new GerenciadorComunicacao(Operacao.TERMINO_CONTRATO_COLABORADOR, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa, "2"));
		save(new GerenciadorComunicacao(Operacao.CADASTRAR_SOLICITACAO, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH, empresa));
		// Criar novo gerenciador default no importador(EmpresaJDBC.java : insereGerenciadorComunicacaoDefault) quando for criado um novo gerenciador default aqui (gambi)
	}
	
	public void enviaEmailCandidatosNaoAptos(Empresa empresa, Long solicitacaoId) throws Exception {
		if (StringUtils.isNotBlank(empresa.getMailNaoAptos())){
			
			String body = empresa.getMailNaoAptos();
			String subject = "[RH] - Solicitação de candidatos";

			String[] emails = candidatoSolicitacaoManager.getEmailNaoAptos(solicitacaoId, empresa);

			if (emails.length > 0) {

				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.ENCERRAR_SOLICITACAO.getId(), empresa.getId());

				for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
					if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.CANDIDATO_NAO_APTO.getId())){
						mail.send(empresa, subject, body.replace("\n", "<br />"), null, emails);
					} 		
				}
			}
		}
	}
	
	public void enviaEmailSolicitanteSolicitacao(Solicitacao solicitacao, Empresa empresa, Usuario usuario){
		try {
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
			Colaborador colaboradorSolicitante = colaboradorManager.findByUsuarioProjection(solicitacao.getSolicitante().getId(), true);
			
			if(colaboradorSolicitante != null && colaboradorSolicitante.getContato() != null && colaboradorSolicitante.getContato().getEmail() != null)
			{
				ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findById(1L);
				String link = parametrosDoSistema.getAppUrl() + "/captacao/solicitacao/prepareUpdate.action?solicitacao.id=" + solicitacao.getId();
				String nomeSolicitante = colaboradorSolicitante.getNomeMaisNomeComercial();
				
				Colaborador colaboradorLiberador = colaboradorManager.findByUsuarioProjection(usuario.getId(), null);
				String nomeLiberador = colaboradorLiberador !=null ? colaboradorLiberador.getNomeMaisNomeComercial():usuario.getNome();
				
				String subject = "[RH] - Status da solicitação de pessoal";
				StringBuilder body = new StringBuilder("<span style='font-weight:bold'>O usuário "+ nomeLiberador + " alterou o status da solicitação " + solicitacao.getDescricao() + " da empresa " + empresa.getNome() + " para " + solicitacao.getStatusFormatado().toLowerCase()  +".</span><br>");
				
				montaCorpoEmailSolicitacao(solicitacao, nomeSolicitante, nomeLiberador, body);
				body.append("<br><br>Acesse o RH para mais detalhes:<br>");
				body.append("<a href='" + link + "'>RH</a>");
				
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.ALTERAR_STATUS_SOLICITACAO.getId(), empresa.getId());
		
				for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
					if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.SOLICITANTE_SOLICITACAO.getId())){
	    				String[] emails = (gerenciadorComunicacao.getEmpresa().getEmailRespRH()+";"+colaboradorSolicitante.getContato().getEmail()).split(";");
						mail.send(empresa, parametrosDoSistema, subject, body.toString(), true, emails);
					} 		
				}
			}
		} catch (Exception e) {	e.printStackTrace();}
	}
	
	public void enviarLembreteResponderAvaliacaoDesempenho(){
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		Collection<Colaborador> avaliadores = colaboradorManager.findColaboradorDeAvaliacaoDesempenhoNaoRespondida();
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
		
		for (Colaborador avaliador : avaliadores)
		{
			try
			{
				StringBuilder corpo = new StringBuilder();
				corpo.append("ATENÇÃO:<br />");
				corpo.append("A avaliação de desempenho "+avaliador.getAvaliacaoDesempenhoTitulo()+" está disponível para ser respondida.<br />");
				corpo.append("Por favor click no link abaixo para acessar o sitema e respondê-la.<br />");
				corpo.append("<a href=\" "+ parametros.getAppUrl() + "\">Sistema RH</a>") ;
				
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.AVALIACAO_DESEMPENHO_A_RESPONDER.getId(), avaliador.getEmpresa().getId());
				
				for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
					if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId())){
						mail.send(avaliador.getEmpresa(), parametros, "[RH] - Lembrete - Responder avaliação de desempenho", corpo.toString(), true, avaliador.getContato().getEmail());
					} 		
				}
			}
			catch (Exception e)	{e.printStackTrace();}
		}
	}
	
	public void enviarLembreteResponderAvaliacaoDesempenhoAoLiberar(Long avaliacaoDesempenhoId, Empresa empresa)  {
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.AVALIACAO_DESEMPENHO_A_RESPONDER.getId(), empresa.getId());
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId())){
				enviarLembreteAvaliacaoDesempenho(avaliacaoDesempenhoId, empresa);
			} 		
		}
	}
	
	public void enviarLembreteAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Empresa empresa){
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
		Collection<Colaborador> avaliadores = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenhoId, false, false);
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
		
		for (Colaborador avaliador : avaliadores)
		{
			try
			{
				StringBuilder corpo = new StringBuilder();
				corpo.append("ATENÇÃO:<br>");
				corpo.append("Existe uma avaliação de desempenho para ser respondida.<br>Por favor acesse <a href=\" "+ parametros.getAppUrl() + "\">RH</a>") ;
				mail.send(empresa, parametros, "[RH] - Lembrete - Responder avaliação de desempenho", corpo.toString(), true, avaliador.getContato().getEmail());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
    public void enviaEmailQuestionarioLiberado(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios){
     	Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.LIBERAR_PESQUISA.getId(), empresa.getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId())){
				enviaEmailQuestionario(empresa, questionario, colaboradorQuestionarios);
			} 		
		}
    }
    
	public void enviaEmailQuestionario(Empresa empresa, Questionario questionario, Collection<ColaboradorQuestionario> colaboradorQuestionarios){
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
    	
        if (colaboradorQuestionarios != null && !colaboradorQuestionarios.isEmpty())
        {
            String label = TipoQuestionario.getDescricao(questionario.getTipo());

    		StringBuilder corpo = new StringBuilder();
    		corpo.append("Existe uma nova " + label + " para ser respondida por você.<br><br>");
    		corpo.append("Titulo: " + questionario.getTitulo() + "<br>");
    		corpo.append("Período: "+ DateUtil.formataDiaMesAno(questionario.getDataInicio()) + " a " + DateUtil.formataDiaMesAno(questionario.getDataFim()) + "<br><br>");
    		corpo.append("Acesse o RH em: <br>");
    		corpo.append("<a href=\"" + parametros.getAppUrl() + "\">RH</a><br><br>");
    		getCopyright(corpo);

            for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionarios)
            {
                try {
                	if(colaboradorQuestionario.getColaborador() != null && !colaboradorQuestionario.getColaborador().isDesligado())
                		mail.send(empresa, parametros, "[RH] - Nova " + label, corpo.toString(), false, colaboradorQuestionario.getColaborador().getContato().getEmail());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
	}

	private Collection<Integer> getIntervaloAviso (String diasLembrete) {
		if (StringUtils.isBlank(diasLembrete))
			return new ArrayList<Integer>();
		
		String[] dias = diasLembrete.split("&");
		Collection<Integer> result = new ArrayList<Integer>(dias.length);
		for (String diaLembrete : dias)
			result.add(Integer.valueOf(diaLembrete.trim()));
		
		return result;
	}

	public void enviaLembreteDeQuestionarioNaoLiberado(){
		QuestionarioManager questionarioManager = (QuestionarioManager) SpringUtil.getBeanOld("questionarioManager");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.PESQUISA_NAO_LIBERADA.getId(), null);
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			try{
				Collection<Integer> diasLembretePesquisa = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
				
				for (Integer diaLembretePesquisa : diasLembretePesquisa){
					Calendar data = Calendar.getInstance();
					data.setTime(new Date());
					data.add(Calendar.DAY_OF_MONTH, +diaLembretePesquisa);

					Collection<Questionario> questionarios = questionarioManager.findQuestionarioNaoLiberados(data.getTime());

					for (Questionario questionario : questionarios){
						String label = TipoQuestionario.getDescricao(questionario.getTipo());
						StringBuilder corpo = new StringBuilder();
						corpo.append("ATENÇÃO:<br>");
						corpo.append("a " + label + questionario.getTitulo() + " está prevista para iniciar no dia " + DateUtil.formataDiaMesAno(questionario.getDataInicio())+".<br>") ;
						corpo.append("Você ainda precisa liberá-la para que os colaboradores possam respondê-la.") ;

						if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
		    				String[] emails = gerenciadorComunicacao.getEmpresa().getEmailRespRH().split(";");
							mail.send(questionario.getEmpresa(), "[RH] - Lembrete de " + label + " não liberada", corpo.toString(), null, emails);
						}
					}
				} 		
			} catch (Exception e)	{ e.printStackTrace(); }
		}
	}

	public boolean verifyExists(GerenciadorComunicacao gerenciadorComunicacao){
		return getDao().verifyExists(gerenciadorComunicacao);
	}
	
	public void enviaAvisoDeCadastroCandidato(String nomeCandidato, Long empresaId){
		String subject = "[RH] - Novo candidato (" + nomeCandidato +")";
		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		StringBuilder body = new StringBuilder();
		body.append("O candidato " + nomeCandidato + ", <br>");
		body.append("se cadastrou na empresa " + empresa.getNome() );

		try{
    		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRAR_CANDIDATO_MODULO_EXTERNO.getId(), empresaId);
    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
    				String[] emails = gerenciadorComunicacao.getEmpresa().getEmailRespRH().split(";");
    				mail.send(empresa, subject, body.toString(), null, emails);
    			}
    			
    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
    				ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
    				Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
    				String[] emails = colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosConfigurados));
    				mail.send(empresa, subject, body.toString(), null, emails);
    			} 
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enviaEmailQtdCurriculosCadastrados(Collection<Empresa> empresas, Date inicioMes, Date fimMes, Collection<Candidato> candidatos){
		String subject = "[RH] - Candidatos cadastros no período: " + DateUtil.formataDiaMesAno(inicioMes) + " a " + DateUtil.formataDiaMesAno(fimMes);
		StringBuilder body = new StringBuilder("Candidatos cadastrados no período: " + DateUtil.formataDiaMesAno(inicioMes) + " a " + DateUtil.formataDiaMesAno(fimMes) + "<br />");
		body.append( "<table><thead><tr><th>Empresa</th><th>|</th><th>Origem</th><th>|</th><th>Qtd. cadastros</th></tr></thead><tbody>");
		OrigemCandidato origemCandidato = new OrigemCandidato();
		
		for (Candidato candidato : candidatos){
			body.append("<tr>");
			body.append("<td>" + candidato.getEmpresa().getNome() + "</td><td>|</td>");
			body.append("<td>" + origemCandidato.get(candidato.getOrigem())+ "</td><td>|</td>");
			body.append("<td align='center'>" + candidato.getQtdCurriculosCadastrados() + "</td>");
			body.append("</tr>");
		}
		body.append("</tbody></table>");
		
		try{
			for (Empresa empresa : empresas){
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.QTD_CURRICULOS_CADASTRADOS.getId(), empresa.getId());
	    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
	    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
	    				String[] emails = gerenciadorComunicacao.getEmpresa().getEmailRespRH().split(";");
	    				mail.send(empresa, subject, body.toString(), null, emails);
	    			} 		
	    		}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradores){
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		String appUrl = parametrosDoSistema.getAppUrl();
		String subject = "[RH] - Avaliação do período de experiência";
		StringBuilder mensagem;
		StringBuilder body;
		String link = "";
		
		for (ColaboradorPeriodoExperienciaAvaliacao colaboradorAvaliacao : colaboradores){
			mensagem = new StringBuilder();
			body = new StringBuilder();

			body.append("Sr(a) " + colaboradorAvaliacao.getColaborador().getNome() + ", <br>");
			body.append("Cargo: " + colaboradorAvaliacao.getColaborador().getFaixaSalarial().getDescricao() + "<br><br>");
			mensagem.append("Por gentileza, preencha sua avaliação para o período de experiência de " + colaboradorAvaliacao.getPeriodoExperiencia().getDias() + " dias");
			body.append(mensagem.toString().replace("dias", "dias <br>"));

			link = "avaliacao/avaliacaoExperiencia/prepareInsertAvaliacaoExperiencia.action?colaboradorQuestionario.colaborador.id=" + colaboradorAvaliacao.getColaborador().getId() + "&respostaColaborador=true&colaboradorQuestionario.avaliacao.id=" + colaboradorAvaliacao.getAvaliacao().getId();
			body.append("disponível em ");
			body.append("<a href='" + appUrl + "/" + link + "'>" + colaboradorAvaliacao.getAvaliacao().getTitulo() + "</a>");			
			
			decideGerenciadorEnviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(subject, mensagem, body, link, colaboradorAvaliacao);
		}
	}

	private void decideGerenciadorEnviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(String subject, StringBuilder mensagem, StringBuilder body, String link, ColaboradorPeriodoExperienciaAvaliacao colaboradorAvaliacao){
		try{
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId(), colaboradorAvaliacao.getColaborador().getEmpresa().getId());
			for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
				Collection<Integer> diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
				
				for (Integer diasLembreteGerenciadorComunicacao : diasLembrete){
					Integer diasDeEmpresaDoColaborador = DateUtil.diferencaEntreDatas(colaboradorAvaliacao.getColaborador().getDataAdmissao(), new Date(), true);

					if( (colaboradorAvaliacao.getPeriodoExperiencia().getDias() - diasLembreteGerenciadorComunicacao) == diasDeEmpresaDoColaborador){
						if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR_AVALIADO.getId())){
		    				mail.send(colaboradorAvaliacao.getColaborador().getEmpresa(), subject, body.toString(), null, colaboradorAvaliacao.getColaborador().getContato().getEmail());
		    			}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR_AVALIADO.getId())){
		    				Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findByColaboradorId(colaboradorAvaliacao.getColaborador().getId());
		    				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", link, usuarioEmpresas, colaboradorAvaliacao.getColaborador(), TipoMensagem.AVALIACAO_DESEMPENHO, colaboradorAvaliacao.getAvaliacao(), null);
		    			}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
		    				String[] emails = gerenciadorComunicacao.getEmpresa().getEmailRespRH().split(";");
		    				mail.send(colaboradorAvaliacao.getColaborador().getEmpresa(), subject, body.toString(), null, emails);
		    			}
					}
				}
			}
		}
		catch (Exception e)	{e.printStackTrace();}
	}
	
	public void enviaLembreteExamesPrevistos(Collection<Empresa> empresas) {
		try	{
			ExameManager exameManager = (ExameManager) SpringUtil.getBeanOld("exameManager");
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
			Map<String,Object> parametros = new HashMap<String, Object>();
			
			for (Empresa empresa : empresas){
				Collection<ExamesPrevistosRelatorio> colecaoExamesPrevistos;
				Date ultimoDiaDoMesPosterior = DateUtil.getUltimoDiaMes(DateUtil.incrementaMes(new Date(), 1));
				String dataString = DateUtil.formataDiaMesAno(ultimoDiaDoMesPosterior);
				String subject = "[RH] - (" + empresa.getNome()+ ")" + "Exames previstos até " + dataString;
				String body = "<B>" + empresa.getNome() + "<B><br><br>" + "Segue em anexo relatório de exames previstos até " + dataString;
				montaPrametrosEnviaLembreteExamesPrevistos(parametros, empresa, ultimoDiaDoMesPosterior);
			    	
			    try {
			    	colecaoExamesPrevistos = exameManager.findRelatorioExamesPrevistos(empresa.getId(), ultimoDiaDoMesPosterior, null, null, null, null, null, 'N', true, false, false);
					if (!colecaoExamesPrevistos.isEmpty()){
						DataSource[] files = ArquivoUtil.montaRelatorio(parametros, colecaoExamesPrevistos, "exames_previstos.jasper");
						Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.EXAMES_PREVISTOS.getId(), empresa.getId());
			    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
			    				Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
			    				String[] emails = colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosConfigurados));
			    				mail.send(empresa, subject, files, body, emails);		
			    			} 		
			    		}
					}
				} catch (ColecaoVaziaException e){throw new Exception(e.getMessage(), e);}
			}
		} catch (Throwable e){e.printStackTrace();}
	}

	private void montaPrametrosEnviaLembreteExamesPrevistos(Map<String, Object> parametros, Empresa empresa, Date ultimoDiaDoMesPosterior) throws NotConectAutenticationException {
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
		String msgRegistro = Autenticador.getMsgAutenticado("");
		String logo = ArquivoUtil.getPathLogoEmpresa() + empresa.getLogoUrl();
		
		Cabecalho cabecalho = new Cabecalho("Exames Previstos até " + DateUtil.formataDiaMesAno(ultimoDiaDoMesPosterior), empresa.getNome(), "", "[Envio Automático]", parametrosDoSistema.getAppVersao(), logo, msgRegistro, parametrosDoSistema.isVersaoAcademica());
		cabecalho.setLicenciadoPara(empresa.getNome());
		
		parametros.put("CABECALHO", cabecalho);
		parametros.put("SUBREPORT_DIR", ArquivoUtil.getPathReport());
	}
	
	public void enviaMensagemLembretePeriodoExperiencia(){
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		Collection<PeriodoExperiencia> periodoExperiencias = periodoExperienciaManager.findAllAtivos();
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId(), null);
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			Collection<Integer> diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
			for (Integer diaLembrete : diasLembrete){
				for (PeriodoExperiencia periodoExperiencia : periodoExperiencias){
					if (!periodoExperiencia.getEmpresa().getId().equals(gerenciadorComunicacao.getEmpresa().getId())) continue;
					Integer dias = periodoExperiencia.getDias() - diaLembrete;	
					if (dias <= 0) continue;
					Calendar dataAvaliacao = Calendar.getInstance();
					dataAvaliacao.add(Calendar.DAY_OF_YEAR, +diaLembrete);
					
	 				Collection<Colaborador> colaboradores = null;
					try {
						colaboradores = colaboradorManager.findAdmitidosHaDias(dias, periodoExperiencia.getEmpresa(), periodoExperiencia.getId());
					} catch (Exception e) { e.printStackTrace(); continue; }
					
					Integer diasAvaliacao = periodoExperiencia.getDias();
					String data = DateUtil.formataDiaMesAno(dataAvaliacao.getTime());
					
					for (Colaborador colaborador : colaboradores){
						StringBuilder mensagemTitulo = new StringBuilder("[RH] - Falta(m) ").append(diaLembrete).append(" dia(s) para responder a avaliação de ").append(diasAvaliacao).append(" dias do colaborador ").append(colaborador.getNome()).append(".\n\n");
						String mensagem = montaMensagemEnviaMensagemLembretePeriodoExperiencia(diasAvaliacao, data, colaborador,	mensagemTitulo);
						decideGerenciadorEnviaMensagemLembretePeriodoExperiencia(gerenciadorComunicacao, colaborador, mensagemTitulo, mensagem);
					}
				}
			}
		}
	}

	private String montaMensagemEnviaMensagemLembretePeriodoExperiencia(Integer diasAvaliacao, String data, Colaborador colaborador, StringBuilder mensagemTitulo) {
		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Período de Experiência: ");
		mensagem.append(mensagemTitulo);
		
		mensagem.append("Lembrete da avaliação de ")
				.append(diasAvaliacao)
				.append(" dias do período de experiência.\n")
				.append("\nColaborador: ").append(colaborador.getNome());
		
		if (StringUtils.isNotBlank(colaborador.getNomeComercial()))
			mensagem.append(" (").append(colaborador.getNomeComercial()).append(") ");
		
		mensagem.append("\nMatrícula: ").append(StringUtils.defaultString(colaborador.getMatricula()));
		mensagem.append("\nEmpresa: ").append(colaborador.getEmpresaNome());
		mensagem.append("\nEstabelecimento: ").append(colaborador.getEstabelecimento().getNome())
				.append("\nÁrea Organizacional: ").append(colaborador.getAreaOrganizacional().getDescricao())
				.append("\nCargo: ").append(colaborador.getFaixaSalarial().getDescricao())
				.append("\nFunção: ").append(colaborador.getFuncaoNome())
				.append("\nData da avaliação: ").append(data);
	
		return mensagem.toString();
	}
	
	private void decideGerenciadorEnviaMensagemLembretePeriodoExperiencia(GerenciadorComunicacao gerenciadorComunicacao, Colaborador colaborador, StringBuilder mensagemTitulo, String mensagem) {
		String link = "";
		try {
			Avaliacao avaliacao = null;
			if(colaborador.getAvaliacaoId() == null)
				link = "avaliacao/avaliacaoExperiencia/periodoExperienciaQuestionarioList.action?colaborador.id=" + colaborador.getId();
			else {
				link = "avaliacao/avaliacaoExperiencia/prepareInsertAvaliacaoExperiencia.action?colaboradorQuestionario.colaborador.id=" + colaborador.getId() + "&respostaColaborador=true&colaboradorQuestionario.avaliacao.id=" + colaborador.getAvaliacaoId();
				avaliacao = new Avaliacao(colaborador.getAvaliacaoId());
			}

			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
				String linkParaResposta = (gerenciadorComunicacao.isPermitirResponderAvaliacao() ? link : null); 
				Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);	
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem, "RH", linkParaResposta, usuariosConfigurados, colaborador, TipoMensagem.AVALIACAO_DESEMPENHO, null, null);
			}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())){
				usuarioMensagemManager.saveMensagemResponderAcompPeriodoExperiencia(mensagem, "RH", link, colaborador.getAreaOrganizacional().getDescricaoIds(), TipoMensagem.AVALIACAO_DESEMPENHO, avaliacao, colaborador, AreaOrganizacional.RESPONSAVEL);
			}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())){
				usuarioMensagemManager.saveMensagemResponderAcompPeriodoExperiencia(mensagem, "RH", link, colaborador.getAreaOrganizacional().getDescricaoIds(), TipoMensagem.AVALIACAO_DESEMPENHO, avaliacao, colaborador, AreaOrganizacional.CORRESPONSAVEL);
			}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())){
				String notEmail = "";
				if (colaborador.getId().equals(colaborador.getAreaOrganizacionalResponsavelId()) 
						&& !usuarioEmpresaManager.containsRole(colaborador.getUsuarioId(), colaborador.getEmpresa().getId(), "ROLE_AV_GESTOR_RECEBER_NOTIFICACAO_PROPRIA_AVALIACAO_ACOMP_DE_EXPERIENCIA"))
					notEmail = colaborador.getContato().getEmail();
				String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), colaborador.getEmpresa().getId(), AreaOrganizacional.RESPONSAVEL, notEmail);
				mail.send(gerenciadorComunicacao.getEmpresa(), mensagemTitulo.toString(), mensagem.replace("\n", "<br>"), null, emails);
			}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())){
				String notEmail = "";
				if (colaborador.getId().equals(colaborador.getAreaOrganizacionalCoResponsavelId()) 
						&& !usuarioEmpresaManager.containsRole(colaborador.getUsuarioId(), colaborador.getEmpresa().getId(), "ROLE_AV_GESTOR_RECEBER_NOTIFICACAO_PROPRIA_AVALIACAO_ACOMP_DE_EXPERIENCIA"))
					notEmail = colaborador.getContato().getEmail();
				
				String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), colaborador.getEmpresa().getId(), AreaOrganizacional.CORRESPONSAVEL, notEmail);
				mail.send(gerenciadorComunicacao.getEmpresa(), mensagemTitulo.toString(), mensagem.replace("\n", "<br>"), null, emails);
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(Long colaboradorAvaliadoId, Long avaliacaoId, Usuario usuario, Empresa empresa){
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
		Colaborador colaboradorAvaliado = colaboradorManager.findByIdDadosBasicos(colaboradorAvaliadoId, null);
		Colaborador avaliador = colaboradorManager.findByUsuarioProjection(usuario.getId(), true);
		
		if( ( colaboradorAvaliado.getDataDesligamento() == null  || (colaboradorAvaliado.getDataDesligamento().after(new Date()) || DateUtil.equals(colaboradorAvaliado.getDataDesligamento(), new Date()))	) && avaliador != null)
		{
			String avaliadorNome = avaliador !=null ? avaliador.getNomeMaisNomeComercial():usuario.getNome();
			StringBuilder mensagem = new StringBuilder();
			mensagem.append("A avaliação do período de experiência do colaborador ").append(colaboradorAvaliado.getNomeMaisNomeComercial()).append(" foi respondida por ").append(avaliadorNome);
			mensagem.append("\n\nColaborador: ").append(colaboradorAvaliado.getNomeMaisNomeComercial()).append("\nCargo: ").append(colaboradorAvaliado.getFaixaSalarial().getDescricao())
					.append("\nFunção: ").append(colaboradorAvaliado.getFuncaoNome()).append("\nÁrea: ").append(colaboradorAvaliado.getAreaOrganizacional().getDescricao());
			
			String link = "avaliacao/avaliacaoExperiencia/prepareInsertAvaliacaoExperiencia.action?colaboradorQuestionario.colaborador.id=" + colaboradorAvaliado.getId() + "&respostaColaborador=true&preview=true&colaboradorQuestionario.avaliacao.id="+avaliacaoId;
			decideGerenciadorEnviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(empresa, colaboradorManager, avaliador, avaliadorNome,	mensagem, link);
		}
	}

	private void decideGerenciadorEnviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(Empresa empresa, ColaboradorManager colaboradorManager,Colaborador avaliador, String avaliadorNome,StringBuilder mensagem, String link) {
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId(), empresa.getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			try 
			{
				Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){	
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), avaliadorNome, link, usuariosConfigurados, avaliador, TipoMensagem.AVALIACAO_DESEMPENHO, null, null);
				}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){	
					String[] emails = colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosConfigurados));
					ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
					mensagem.append("<br /><br>Acesse o RH para ver a resposta:<br />");
					mensagem.append("<a href='" + parametrosDoSistema.getAppUrl() + "/" + link + "'>RH</a>");
					mail.send(empresa, "[RH] - Resposta de avaliação do período de experiência", mensagem.toString().replace("\n", "<br />"), null, emails);
				}
			} catch (Exception e) {	e.printStackTrace();}
		}
	}

	public void enviarMensagemAoExluirRespostasAvaliacaoPeriodoDeExperiencia(ColaboradorQuestionario colaboradorQuestionario, Usuario usuario, Empresa empresa){
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId(), empresa.getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			try {
				Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){	
					StringBuilder mensagem = new StringBuilder();
					mensagem.append("As respostas da avaliação do período de experiência do colaborador ").append(colaboradorQuestionario.getColaborador().getNomeMaisNomeComercial()).append(" foram excluídas pelo usuário ").append(usuario.getNome());
					mensagem.append("\n\nAvaliação do Período de Experiência: " + colaboradorQuestionario.getAvaliacao().getTitulo());
					mensagem.append("\nColaborador Avaliado: " + colaboradorQuestionario.getColaborador().getNomeMaisNomeComercial());
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), usuario.getNome(), null, usuariosConfigurados, null, TipoMensagem.AVALIACAO_DESEMPENHO, null, null);
				}else{	
					StringBuilder mensagem = new StringBuilder();
					mensagem.append("As respostas da avaliação do período de experiência do colaborador ").append(colaboradorQuestionario.getColaborador().getNomeMaisNomeComercial()).append(" foram excluídas pelo usuário ").append(usuario.getNome());
					mensagem.append("<br/><br/>Avaliação do Período de Experiência: " + colaboradorQuestionario.getAvaliacao().getTitulo());
					mensagem.append("<br/>Colaborador Avaliado: " + colaboradorQuestionario.getColaborador().getNomeMaisNomeComercial());
					ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
					String[] emails = colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosConfigurados));
					mail.send(empresa, "[RH] - Exclusão das resposta da avaliação do período de experiência, do colaborador -" + colaboradorQuestionario.getColaborador().getNomeMaisNomeComercial(), mensagem.toString(), null, emails);
				}
			} catch (Exception e) {	e.printStackTrace();}
		}
	}

	public void enviaAvisoOcorrenciaCadastrada(ColaboradorOcorrencia colaboradorOcorrencia, Long empresaId){
		try {
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			Colaborador colaborador = colaboradorManager.findColaboradorByIdProjection(colaboradorOcorrencia.getColaborador().getId());
			ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
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
			
			if ( colaboradorOcorrencia.getProvidencia() != null && colaboradorOcorrencia.getProvidencia().getId() != null )
				mensagem.append("\nProvidência     : " + providenciaDescricao);
			
			if ( colaboradorOcorrencia.getObservacao() != null && !colaboradorOcorrencia.getObservacao().trim().isEmpty() )
				mensagem.append("\nObservação      : " + colaboradorOcorrencia.getObservacao());
						
			devideGerenciadorEnviaAvisoOcorrenciaCadastrada(colaboradorOcorrencia, parametrosDoSistema, mensagem);
		} catch (Exception e){e.printStackTrace();}
	}

	private void devideGerenciadorEnviaAvisoOcorrenciaCadastrada(ColaboradorOcorrencia colaboradorOcorrencia, ParametrosDoSistema parametrosDoSistema, StringBuilder mensagem)throws Exception, AddressException, MessagingException {
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRAR_OCORRENCIA.getId(), colaboradorOcorrencia.getOcorrencia().getEmpresa().getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) 
		{
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId()))
			{	
				Collection<UsuarioEmpresa> usuarioEmpresas = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(),"RH", null, usuarioEmpresas, null, TipoMensagem.INFO_FUNCIONAIS, null, null);
			} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
				String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaboradorOcorrencia.getColaborador().getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.RESPONSAVEL, null);
				mail.send(gerenciadorComunicacao.getEmpresa(), parametrosDoSistema, "Nova ocorrência para colaborador", mensagem.toString().replace("\n", "<br />"), true, emails);
			} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
				String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaboradorOcorrencia.getColaborador().getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.CORRESPONSAVEL, null);
				mail.send(gerenciadorComunicacao.getEmpresa(), parametrosDoSistema, "Nova ocorrência para colaborador", mensagem.toString().replace("\n", "<br />"), true, emails);
			}
		}
	}
	
	public void enviaMensagemNotificacaoDeNaoEntregaSolicitacaoEpi(){
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		StringBuilder mensagem;
		Collection<UsuarioEmpresa> usuariosEmpresa;
		Collection<Integer> diasLembrete;
		Collection<Colaborador> colaboradors;
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.NAO_ENTREGA_SOLICITACAO_EPI.getId(), null);
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){	
				usuariosEmpresa = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
				colaboradors = colaboradorManager.findAguardandoEntregaEpi(diasLembrete, gerenciadorComunicacao.getEmpresa().getId());
				for (Colaborador colaborador : colaboradors) {
					mensagem = new StringBuilder();
					mensagem.append("Existem EPI's a serem entregues ao colaborador ").append(colaborador.getNomeMaisNomeComercial()).append(".");
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", null, usuariosEmpresa, colaborador, TipoMensagem.SESMT, null, null);
				}
			}
		}
	}
	
	public void enviarEmailTerminoContratoTemporarioColaborador() throws Exception{
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		StringBuilder body;
		Collection<Integer> diasLembretes;
		Collection<Colaborador> colaboradors;
		String subject = "[RH] - Término de contrato temporário";
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.TERMINO_CONTRATO_COLABORADOR.getId(), null);
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){	
				diasLembretes = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
				colaboradors = colaboradorManager.findParaLembreteTerminoContratoTemporario(diasLembretes, gerenciadorComunicacao.getEmpresa().getId());
				if(colaboradors.isEmpty()) continue;
				body = new StringBuilder();
				body.append("Os contratos dos colaboradores abaixo estão próximos a vencer.");
				
				for (Colaborador colaborador : colaboradors) 
					body.append("\n"+colaborador.getNomeMaisNomeComercial());					
				
				String[] emails = gerenciadorComunicacao.getEmpresa().getEmailRespRH().split(";");
				mail.send(gerenciadorComunicacao.getEmpresa(), subject, body.toString(), null, emails);
			}
		}
	}
	
	public void enviaMensagemNotificacaoDeNaoAberturaSolicitacaoEpi(){
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.NAO_ABERTURA_SOLICITACAO_EPI.getId(), null);
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){	
				Collection<UsuarioEmpresa> usuariosEmpresa = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				Collection<Integer> diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
				Collection<Colaborador> colaboradors = colaboradorManager.findAdmitidosHaDiasSemEpi(diasLembrete, gerenciadorComunicacao.getEmpresa().getId());
				for (Colaborador colaborador : colaboradors) {
					StringBuilder mensagem = new StringBuilder();
					mensagem.append("Não foi criada uma Solicitação de EPI's para o colaborador ").append(colaborador.getNomeMaisNomeComercial()).append(".");
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", null, usuariosEmpresa, colaborador, TipoMensagem.SESMT, null, null);
				}
			}
		}
	}
	
	public void enviarEmailContratacaoColaborador(String colaboradorNome, Empresa empresa) throws Exception{
		String body = "<br>O candidato <b>" + colaboradorNome + "</b> foi contratado e seus dados estão disponíveis no <b>Fortes Pessoal</b> para complemento de suas informações.<br><br>";
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CONTRATAR_COLABORADOR_AC.getId(), empresa.getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_SETOR_PESSOAL.getId()))
				mail.send(empresa, "[RH] - Contratação de candidato", body, null, empresa.getEmailRespSetorPessoal());
		}
	}
	
	public void enviaMensagemCancelamentoSituacao(TSituacao situacao, String mensagem, HistoricoColaborador historicoColaborador){
		String mensagemFinal = mensagemManager.formataMensagemCancelamentoHistoricoColaborador(mensagem, historicoColaborador);
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(situacao.getEmpresaCodigoAC(), situacao.getGrupoAC(), null);
		String link = "cargosalario/historicoColaborador/prepareUpdate.action?historicoColaborador.id="+ historicoColaborador.getId() +"&colaborador.id=" + historicoColaborador.getColaborador().getId();
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CANCELAR_SITUACAO_AC.getId(), historicoColaborador.getColaborador().getEmpresa().getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId()))
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal, "Fortes Pessoal", link, usuarioEmpresas, null, TipoMensagem.INFO_FUNCIONAIS, null, null);
		}
	}
	
	public void enviaAvisoDesligamentoColaboradorAC(String codigoEmpresa, String grupoAC, Empresa empresa, String... codigosACColaboradores){
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
		Collection<Colaborador> colaboradores = colaboradorManager.findColaboradoresByCodigoAC(empresa.getId(), true, codigosACColaboradores);
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(codigoEmpresa, grupoAC, null);

		Map<Long, Date> colaboradoresComEstabilidade = null;
		if(colaboradores != null && colaboradores.size() > 0) 
			colaboradoresComEstabilidade = comissaoMembroManager.colaboradoresComEstabilidade(new CollectionUtil<Colaborador>().convertCollectionToArrayIds(colaboradores));
		
		for (Colaborador colaborador : colaboradores){
			String link = "pesquisa/entrevista/prepareResponderEntrevista.action?colaborador.id=" + colaborador.getId() + "&voltarPara=../../index.action";
			String msgGeral = "O Colaborador " + colaborador.getNomeEOuNomeComercial() + " da empresa "+empresa.getNome()+" foi desligado no Fortes Pessoal.";
			if(colaboradoresComEstabilidade != null && colaboradoresComEstabilidade.containsKey(colaborador.getId()))
				msgGeral += "\nEste colaborador faz parte da CIPA e possui estabilidade até o dia " + DateUtil.formataDiaMesAno(colaboradoresComEstabilidade.get(colaborador.getId())) + ".";
			String msgDetalhe = "\n\nPara preencher a entrevista de desligamento, acesse a tela de listagem de colaboradores.";
			String subject = "[RH] - Desligamento de colaborador";
			decideGerenciadorEnviaAvisoDesligamentoColaboradorAC(empresa, usuarioEmpresas, colaborador, link, msgGeral, msgDetalhe,	subject);
		}
	}

	private void decideGerenciadorEnviaAvisoDesligamentoColaboradorAC(Empresa empresa, Collection<UsuarioEmpresa> usuarioEmpresas,	Colaborador colaborador, String link, String msgGeral, String msgDetalhe, String subject) {
		String[] emails = null;
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.DESLIGAR_COLABORADOR_AC.getId(), empresa.getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos)
		{
			try {
				if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId()))
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(msgGeral+msgDetalhe, "Fortes Pessoal", link, usuarioEmpresas, colaborador, TipoMensagem.INFO_FUNCIONAIS, null, colaborador.getUsuario().getId());
				else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
					emails = gerenciadorComunicacao.getEmpresa().getArrayEmailRespRH();
					mail.send(gerenciadorComunicacao.getEmpresa(), subject, msgGeral, null, emails);
				}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())){
					emails = areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), colaborador.getEmpresa().getId(), AreaOrganizacional.RESPONSAVEL, colaborador.getContato().getEmail());
					mail.send(gerenciadorComunicacao.getEmpresa(), subject, msgGeral, null, emails);
				}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())){
					emails = areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), colaborador.getEmpresa().getId(), AreaOrganizacional.CORRESPONSAVEL, colaborador.getContato().getEmail());
					mail.send(gerenciadorComunicacao.getEmpresa(), subject, msgGeral, null, emails);
				}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
					UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBeanOld("usuarioManager");
					CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
					Long[] usuariosIds = collUtil.convertCollectionToArrayIds(gerenciadorComunicacao.getUsuarios());
					emails = usuarioManager.findEmailsByUsuario(usuariosIds, colaborador.getContato().getEmail());
					mail.send(empresa, subject, msgGeral, null, emails);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void notificaBackup(String arquivoDeBackup){
		String subject = "[RH] - Backup do Banco";
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		String corpo = getCorpo(arquivoDeBackup,  parametrosDoSistema.getAppUrl());
		String email = parametrosDoSistema.getEmailDoSuporteTecnico();
		
		try {
			logger.info("Enviando e-mail para responsável (" + email + ") sobre backup do banco de dados.");
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.GERAR_BACKUP.getId(), null);
    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_TECNICO.getId())){
    				mail.send(null, subject, corpo, email);
    				break;
    			} 
    		}
		} catch (Exception e) {
			logger.error("Erro ao enviar e-mail ao responsável (" + email + ")  sobre backup do banco de dados", e);
			e.printStackTrace();
		}
		
	}
	
	private String getCorpo(String backupFile, String appUrl){
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
	
	public void enviaEmailConfiguracaoLimiteColaborador(ConfiguracaoLimiteColaborador configuracaoLimiteColaborador, Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, Empresa empresa){
		try {
			String[] emails = empresaManager.findByIdProjection(empresa.getId()).getEmailRespLimiteContrato().split(";");
			StringBuilder body = new StringBuilder("Contrato: " + configuracaoLimiteColaborador.getDescricao() + "<br>");
			body.append("Área Organizacional: " + areaOrganizacionalManager.findByIdProjection(configuracaoLimiteColaborador.getAreaOrganizacional().getId()).getNome() + "<br><br>");
			body.append( "<table style='width:450px' ><thead><tr><th>Cargo</th><th>Limite</th></tr></thead><tbody>");
			
			for (QuantidadeLimiteColaboradoresPorCargo qtdLimiteColabCargo:  quantidadeLimiteColaboradoresPorCargos){
				body.append("<tr>" );
				body.append("<td>" + cargoManager.findByIdProjection(qtdLimiteColabCargo.getCargo().getId()).getNomeMercado() + "</td>" );
				body.append("<td align='right' style='width:60px'>" + qtdLimiteColabCargo.getLimite() + "</td>" );
				body.append("</tr>");
			}
			
			body.append("</tbody></table>");
			
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRAR_LIMITE_COLABORADOR_CARGO.getId(), null);
    		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
    			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_LIMITE_CONTRATO.getId())){
    				mail.send(empresa, "[RH] - Novo Contrato ("+configuracaoLimiteColaborador.getDescricao()+") com limite de colaboradores por cargo adicionado.", body.toString(), null, emails);
    			} 
    		}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	private String getLink(String appUrl, String backupFile){
		String link = appUrl + "/backup/show.action?filename=" + backupFile; 
		return link;
	}
	
	public boolean existeConfiguracaoParaCandidatosModuloExterno(Long empresaId){
		Object[] valores = new Object[] {Operacao.CURRICULO_AGUARDANDO_APROVACAO_MODULO_EXTERNO.getId(), MeioComunicacao.CAIXA_MENSAGEM.getId(), EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL.getId(), empresaId};
		return getDao().verifyExists(new String[]{"operacao", "meioComunicacao", "enviarPara", "empresa.id"}, valores);
	}

	public void enviarAvisoEmailLiberacao(Turma turma, AvaliacaoTurma avaliacaoTurma, Long empresaId) {
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.LIBERAR_AVALIACAO_TURMA.getId(), empresaId);
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId())){
				enviarAvisoAvaliacaoEmail(turma, avaliacaoTurma, empresaId);
			} 
		}
	}
	
	public void enviarAvisoEmail(Turma turma, Long empresaId){
		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBeanOld("colaboradorTurmaManager");
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findColaboradoresComEmailByTurma(turma.getId(), false); 

		String subject = "[RH] - Lembrete: Curso " + turma.getCurso().getNome();
		String  body =  "#COLABORADOR#, você está matriculado no seguinte curso.<br>";
				body += "Curso: " + turma.getCurso().getNome() + "<br>";
				body += "Turma: " + turma.getDescricao() + "<br>";
				body += "Período: " + DateUtil.formataDiaMesAno(turma.getDataPrevIni()) + " - " + DateUtil.formataDiaMesAno(turma.getDataPrevFim()) + "<br>";
				body += "Horário: " + turma.getHorario() + "<br>";
		
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas){
			try {
				mail.send(empresa, subject, body.replace("#COLABORADOR#", colaboradorTurma.getColaboradorNome()), null, colaboradorTurma.getColaborador().getContato().getEmail());
			} catch (Exception e)	{
				e.printStackTrace();
			}
		}
	}

	public void enviarAvisoAvaliacaoEmail(Turma turma, AvaliacaoTurma avaliacaoTurma, Long empresaId){
		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBeanOld("colaboradorTurmaManager");
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findColaboradoresComEmailByTurma(turma.getId(), true); 
		ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findById(1L);
		String subject = "[RH] - Avaliação " + avaliacaoTurma.getQuestionario().getTitulo() + " do curso " + turma.getCurso().getNome();
		String bodySubject =  "#COLABORADOR#, a avaliação " + avaliacaoTurma.getQuestionario().getTitulo() + " do curso " + turma.getCurso().getNome() + " está liberada para ser respondida.<br><br>"
						+ "<a href='" + parametrosDoSistema.getAppUrl() + "/pesquisa/colaboradorResposta/prepareResponderQuestionario.action?"
						+ "colaborador.id=#COLABORADOR_ID#" + "&questionario.id="+avaliacaoTurma.getQuestionario().getId()	+ "&turmaId="+turma.getId()+"&voltarPara=../../index.action"+"   '>Acesse o RH para responder a avaliação</a><br><br>";
		
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas){
			try {
				String body = bodySubject.replace("#COLABORADOR#", colaboradorTurma.getColaboradorNome());
				body = body.replace("#COLABORADOR_ID#", colaboradorTurma.getColaborador().getId().toString());
				mail.send(empresa, subject, body, null, colaboradorTurma.getColaborador().getContato().getEmail());
			} catch (Exception e)	{e.printStackTrace();}
		}
	}
	
	private Collection<UsuarioEmpresa> verificaUsuariosAtivosNaEmpresa(GerenciadorComunicacao gerenciadorComunicacao){
		Collection<UsuarioEmpresa> usuariosConfigurados = new ArrayList<UsuarioEmpresa>();
		if(gerenciadorComunicacao.getUsuarios() != null && gerenciadorComunicacao.getUsuarios().size() > 0)
			usuariosConfigurados = usuarioEmpresaManager.findUsuariosAtivo(LongUtil.collectionToCollectionLong(gerenciadorComunicacao.getUsuarios()), gerenciadorComunicacao.getEmpresa().getId());
		
		return usuariosConfigurados;
	}
	
	public void enviaMensagemCancelamentoContratacao(Colaborador colaborador, String mensagem){
		StringBuilder mensagemFinal = new StringBuilder("Cancelamento da contratação no Fortes Pessoal do colaborador " + colaborador.getNome() + StringUtils.rightPad(" ", 100));
		mensagemFinal.append("\r\n\r\n");
		mensagemFinal.append("<b>Motivo do cancelamento:</b> \r\n");
		mensagemFinal.append(mensagem);
		mensagemFinal.append("\r\n\r\n");
		mensagemFinal.append("<b>Dados do colaborador cancelado:</b> \r\n");
		mensagemFinal.append("Nome: "+colaborador.getNomeComercial());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Estabelecimento: "+colaborador.getHistoricoColaborador().getEstabelecimento().getNome());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Área Organizacional: "+colaborador.getHistoricoColaborador().getAreaOrganizacional().getDescricao());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Cargo: "+colaborador.getHistoricoColaborador().getFaixaSalarial().getCargo().getNomeMercado());
		mensagemFinal.append("\r\n");

		if(!StringUtil.isBlank(colaborador.getEmpresa().getCodigoAC()) && !StringUtil.isBlank(colaborador.getEmpresa().getGrupoAC())){
			Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(colaborador.getEmpresa().getCodigoAC(), colaborador.getEmpresa().getGrupoAC(), null);	
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CANCELAR_CONTRATACAO_AC.getId(), colaborador.getEmpresa().getId());
			for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
				if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId()) && usuarioEmpresas.size() > 0)
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal.toString(), "Fortes Pessoal", null, usuarioEmpresas, null, TipoMensagem.INFO_FUNCIONAIS, null, null);
			}
		}
	}

	public void enviaMensagemCancelamentoSolicitacaoDesligamentoAC(Colaborador colaborador, String mensagem, String empresaCodigoAC, String grupoAC){
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
		
		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(empresaCodigoAC, grupoAC, colaborador.getUsuario().getId());
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC.getId(), colaborador.getEmpresa().getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId()) && usuarioEmpresas.size() > 0)
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal.toString(), "Fortes Pessoal", null, usuarioEmpresas, null, TipoMensagem.INFO_FUNCIONAIS, null, null);
		}
	}
	
	public void enviaAvisoDeAfastamento(Long colaboradorAfastamentoId, Empresa empresa){
		ColaboradorAfastamentoManager colaboradorAfastamentoManager = (ColaboradorAfastamentoManager) SpringUtil.getBean("colaboradorAfastamentoManager");
		ColaboradorAfastamento colaboradorAfastamento = colaboradorAfastamentoManager.findByColaboradorAfastamentoId(colaboradorAfastamentoId);
		
		if(colaboradorAfastamento != null){
			enviaAvisoDeAfastamentoPorEmail(colaboradorAfastamento, empresa);
			enviaAvisoDeAfastamentoPorMensagem(colaboradorAfastamento, empresa);
		}
	}
	
	private void enviaAvisoDeAfastamentoPorEmail(ColaboradorAfastamento colaboradorAfastamento, Empresa empresa){
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRAR_AFASTAMENTO.getId(), empresa.getId());
		if(gerenciadorComunicacaos.isEmpty())
			return;
		
		String subject = "[RH] - Afastamento do colaborador " + colaboradorAfastamento.getColaborador().getNomeMaisNomeComercial();
		StringBuilder body = getMensagemDeAfastamento(colaboradorAfastamento, TipoMensagemAfastamento.EMAIL);

		enviaEmailsUsuarios(gerenciadorComunicacaos, empresa, subject, body.toString());
	}

	private StringBuilder getMensagemDeAfastamento(ColaboradorAfastamento colaboradorAfastamento, TipoMensagemAfastamento tipoMensagemAfastamento) {
		StringBuilder  body = new StringBuilder();
		body.append("Afastamento do colaborador " + colaboradorAfastamento.getColaborador().getNomeMaisNomeComercial());
		body.append(tipoMensagemAfastamento.getQuebraDeLinha() + tipoMensagemAfastamento.getQuebraDeLinha());
		body.append("Motivo: " + colaboradorAfastamento.getAfastamento().getDescricao());
		body.append(tipoMensagemAfastamento.getQuebraDeLinha());
		body.append("Período: " + colaboradorAfastamento.getPeriodoFormatado());
		body.append(tipoMensagemAfastamento.getQuebraDeLinha()); 
		body.append("CID: " + colaboradorAfastamento.getCid() + " - " + cidManager.findDescricaoByCodigo(colaboradorAfastamento.getCid()));
		body.append(tipoMensagemAfastamento.getQuebraDeLinha());
		body.append("Profissional da Saúde: " + colaboradorAfastamento.getNomeProfissionalDaSaude());
		body.append(tipoMensagemAfastamento.getQuebraDeLinha());
		body.append(colaboradorAfastamento.getTipoRegistroDeSaudeMaisRegistro());
		body.append(tipoMensagemAfastamento.getQuebraDeLinha());
		body.append("Observações: " + colaboradorAfastamento.getObservacao());
		return body;
	}
	
	private void enviaAvisoDeAfastamentoPorMensagem(ColaboradorAfastamento colaboradorAfastamento, Empresa empresa){
		StringBuilder mensagem = getMensagemDeAfastamento(colaboradorAfastamento, TipoMensagemAfastamento.MENSAGEM);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRAR_AFASTAMENTO.getId(), empresa.getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", null, verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao), null, TipoMensagem.SESMT, null, null);
			} 
		}
	}


	public void enviaAvisoContratacao(HistoricoColaborador historicoColaborador){
		HistoricoColaboradorManager historicoColaboradorManager = (HistoricoColaboradorManager) SpringUtil.getBean("historicoColaboradorManager");
		historicoColaborador = historicoColaboradorManager.findByIdProjection(historicoColaborador.getId());
		
		StringBuilder  mensagem = new StringBuilder("Contratação efetuada para o colaborador " + historicoColaborador.getColaborador().getNomeMaisNomeComercial());
		mensagem.append("\n\n");
		mensagem.append("Data da Admissão: " + historicoColaborador.getColaborador().getDataAdmissaoFormatada());
		mensagem.append("\n");
		mensagem.append("Empresa: " + historicoColaborador.getColaborador().getEmpresa().getNome());
		mensagem.append("\n");
		mensagem.append("Estabelecimento: " + historicoColaborador.getEstabelecimento().getNome());
		mensagem.append("\n"); 
		mensagem.append("Área Organizacional: " + historicoColaborador.getAreaOrganizacional().getDescricao());
		mensagem.append("\n");
		mensagem.append("Cargo: " + historicoColaborador.getFaixaSalarial().getCargo().getNomeMercado());
		mensagem.append("\n"); 
		mensagem.append("Função: " + StringUtils.trimToEmpty(historicoColaborador.getFuncao().getNome()));
		mensagem.append("\n"); 
		mensagem.append("Ambiente: " + StringUtils.trimToEmpty(historicoColaborador.getAmbiente().getNome()));

		enviaAvisoContratacaoEmail(historicoColaborador, mensagem.toString());
		enviaAvisoContratacaoMensagem(historicoColaborador, mensagem.toString());
	}

	private void enviaAvisoContratacaoEmail(HistoricoColaborador historicoColaborador, String mensagem){
		mensagem = mensagem.replace("\n", "<br />");
		String subject = "[RH] - Contração do colaborador " + historicoColaborador.getColaborador().getNomeMaisNomeComercial();
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CONTRATAR_COLABORADOR.getId(), historicoColaborador.getColaborador().getEmpresa().getId());
		enviaEmailsUsuarios(gerenciadorComunicacaos, historicoColaborador.getColaborador().getEmpresa(), subject, mensagem);
	}

	private void enviaAvisoContratacaoMensagem(HistoricoColaborador historicoColaborador, String mensagem){
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CONTRATAR_COLABORADOR.getId(), historicoColaborador.getColaborador().getEmpresa().getId());
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", null, verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao), null, TipoMensagem.INFO_FUNCIONAIS, null, null);
			} 
		}
	}
	
	private void enviaEmailsUsuarios(Collection<GerenciadorComunicacao> gerenciadorComunicacaos, Empresa empresa, String subject, String mensagem){
		try {
			UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBean("usuarioManager");
			CollectionUtil<Usuario> cul = new CollectionUtil<Usuario>();
			
			for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
				if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
					Long[] usuariosIds = cul.convertCollectionToArrayIds(gerenciadorComunicacao.getUsuarios());
					
					try {
						mail.send(empresa, subject, mensagem, null, usuarioManager.findEmailsByUsuario(usuariosIds, null));
					} catch (Exception e)	{
						e.printStackTrace();
					}
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enviaAvisoAtualizacaoInfoPessoais(Colaborador colaboradorOriginal, Colaborador colaboradorAtualizado, Long empresaId){
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.ATUALIZAR_INFO_PESSOAIS.getId(), empresaId);
		if (gerenciadorComunicacaos.isEmpty())
			return;
		
		StringBuffer mensagem = new StringBuffer("<b>" + colaboradorOriginal.getNome() + "</b> atualizou seus dados: \n\n");
		mensagem.append("Dados originais: \n");
		StringBuffer dadosAlterados = new StringBuffer("");
		montaDadosOriginaisEnviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, dadosAlterados);
		
		if(dadosAlterados.toString().length() == 0)
			return;
		else
			mensagem.append(dadosAlterados.toString());
		
		mensagem.append("\n\n");
		mensagem.append("Dados atualizados:\n");		
		montaDadosAtualizadosEnviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, mensagem);
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			try {
				if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", "geral/colaborador/prepareUpdate.action?colaborador.id=" + colaboradorOriginal.getId(), verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao), null, TipoMensagem.INFO_FUNCIONAIS, null, null);
				}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
					String[] emails = gerenciadorComunicacao.getEmpresa().getEmailRespRH().split(";");
					mail.send(gerenciadorComunicacao.getEmpresa(), "[RH] - "+colaboradorOriginal.getNome() + " atualizou seus dados pessoais", mensagem.toString().replace("\n", "<br>"), null, emails);
				}
			} catch (Exception e) {	e.printStackTrace();}
		}
	}

	private void montaDadosOriginaisEnviaAvisoAtualizacaoInfoPessoais(Colaborador colaboradorOriginal, Colaborador colaboradorAtualizado,StringBuffer mensagem){
		montaDadosEnviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, mensagem, colaboradorOriginal);
	}
	
	private void montaDadosAtualizadosEnviaAvisoAtualizacaoInfoPessoais(Colaborador colaboradorOriginal, Colaborador colaboradorAtualizado,StringBuffer mensagem){
		montaDadosEnviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, mensagem, colaboradorAtualizado);
	}
	
	private void montaDadosEnviaAvisoAtualizacaoInfoPessoais(Colaborador colaboradorOriginal, Colaborador colaboradorAtualizado,StringBuffer mensagem, Colaborador colaboradorMensagem){
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getEndereco().getCepFormatado()", mensagem, "CEP:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getEndereco().getLogradouro()", mensagem, "Logradouro:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getEndereco().getNumero()", mensagem, "Número:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getEndereco().getComplemento()", mensagem, "Complemento:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getEndereco().getUf().getSigla()", mensagem, "UF:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getEndereco().getCidade().getNome()", mensagem, "Cidade:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getEndereco().getBairro()", mensagem, "Bairro:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getContato().getEmail()", mensagem, "Email:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getContato().getDdd()", mensagem, "DDD Telefone Fixo:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getContato().getFoneFixo()", mensagem, "Telefone Fixo:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getContato().getDddCelular()", mensagem, "DDD Celular:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getContato().getFoneCelular()", mensagem, "Telefone Celular:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getEscolaridade()", mensagem, "Escolaridade:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getEstadoCivil()", mensagem, "Estado Civil:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getPai()", mensagem, "Nome do Pai:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getMae()", mensagem, "Nome da Mãe:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getConjuge()", mensagem, "Nome do Cônjuge:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getQtdFilhos()", mensagem, "Quantidade de filhos:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getRg()", mensagem, "RG - Número:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getRgOrgaoEmissor()", mensagem, "RG - Órgão Emissor:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getRgUf().getSigla()", mensagem, "RG - Estado:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getRgDataExpedicao()", mensagem, "RG - Data de Expedição:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getHabilitacao().getNumeroHab()", mensagem, "Carteira de Habilitação - Nº de Registro:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getHabilitacao().getRegistro()", mensagem, "Carteira de Habilitação - Prontuário:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getHabilitacao().getEmissao()", mensagem, "Carteira de Habilitação - Emissão:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getHabilitacao().getVencimento()", mensagem, "Carteira de Habilitação - Vencimento:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getHabilitacao().getCategoria()", mensagem, "Carteira de Habilitação - Categoria:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getTituloEleitoral().getTitEleitNumero()", mensagem, "Título Eleitoral - Número:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getTituloEleitoral().getTitEleitZona()", mensagem, "Título Eleitoral - Zona:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getTituloEleitoral().getTitEleitSecao()", mensagem, "Título Eleitoral - Seção:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getCertificadoMilitar().getCertMilNumero()", mensagem, "Certificado Militar - Número:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getCertificadoMilitar().getCertMilTipo()", mensagem, "Certificado Militar - Tipo:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getCertificadoMilitar().getCertMilSerie()", mensagem, "Certificado Militar - Série:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getCtps().getCtpsNumero()", mensagem, "CTPS - Número:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getCtps().getCtpsSerie()", mensagem, "CTPS - Série:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getCtps().getCtpsDv()", mensagem, "CTPS - DV:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getCtps().getCtpsUf().getSigla()", mensagem, "CTPS - Estado:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getCtps().getCtpsDataExpedicao()", mensagem, "CTPS - Data Expedição:", colaboradorMensagem);
		verificaDadoAtualizado(colaboradorOriginal, colaboradorAtualizado, "getPessoal().getPis()", mensagem, "PIS - Número:", colaboradorMensagem);
	}
	
	private void verificaDadoAtualizado(Colaborador colaboradorOriginal, Colaborador colaboradorAtualizado,String metodos, StringBuffer mensagem, String campo, Colaborador colaboradorMensagem){
		Object valorOriginal = ModelUtil.getValor(colaboradorOriginal, metodos, true);
		Object valorAtualizado = ModelUtil.getValor(colaboradorAtualizado, metodos, true);
		
		if((valorOriginal != null && valorAtualizado == null) ||
				(valorOriginal == null && valorAtualizado != null) ||
				(valorOriginal != null && valorAtualizado != null && !valorOriginal.equals(valorAtualizado))){
			mensagem.append(campo + " " + ObjectUtils.defaultIfNull(ModelUtil.getValor(colaboradorMensagem, metodos, true), "") + "\n");
		}
	}
	
	// TODO: SEM TESTE
	public void enviaEmailCartaoAniversariantes(){
		try{
			Collection<Empresa> empresas = getDao().findEmpresasByOperacaoId(Operacao.ENVIAR_CARTAO_ANIVERSARIANTES.getId());
			if (empresas != null && empresas.size() > 0){
				colaboradorManager.enviaEmailAniversariantes(empresas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void enviaMensagemCadastroSituacaoAC(String nomeColaborador, TSituacao situacao)
	{
		try {
			Empresa empresa = empresaManager.findByCodigoAC(situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRAR_SITUACAO_AC.getId(), empresa.getId());
			String mensagem = "Foi inserido um novo histórico para o colaborador "+nomeColaborador+" na data "+situacao.getData()+".";
			String subject = "[RH] - Cadastro de histórico de colaborador no Fortes Pessoal";
			for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
				try {
					if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && (gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId()))) {
						enviaEmailParaGestorOuCoGestorAC(situacao, empresa, mensagem, subject, AreaOrganizacional.RESPONSAVEL);
					} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
						enviaEmailParaGestorOuCoGestorAC(situacao, empresa, mensagem, subject, AreaOrganizacional.CORRESPONSAVEL);
					} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())) {
						String[] emails = gerenciadorComunicacao.getEmpresa().getEmailRespRH().split(";");
						mail.send(empresa, subject, mensagem, null, emails);
					} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
						UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBeanOld("usuarioManager");
						CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
						Long[] usuariosIds = collUtil.convertCollectionToArrayIds(gerenciadorComunicacao.getUsuarios());
						String[] emails = usuarioManager.findEmailsByUsuario(usuariosIds, null);
						mail.send(empresa, subject, mensagem, null, emails);
					} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
						AreaOrganizacional areaOrganizacional = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(situacao.getLotacaoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
						usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional(mensagem, "Fortes Pessoal", null, areaOrganizacional.getDescricaoIds(), TipoMensagem.INFO_FUNCIONAIS, null, null);
					} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
						AreaOrganizacional areaOrganizacional = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(situacao.getLotacaoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
						usuarioMensagemManager.saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(mensagem, "Fortes Pessoal", null, areaOrganizacional.getDescricaoIds(), TipoMensagem.INFO_FUNCIONAIS, null, null);
					} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
						Collection<UsuarioEmpresa> usuarioEmpresas = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
						usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem, "Fortes Pessoal", null, usuarioEmpresas, null, TipoMensagem.INFO_FUNCIONAIS, null, null);
					}
				} catch (Exception e) {	e.printStackTrace();}
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	public void enviaMensagemHabilitacaoAVencer(){
		try {
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.HABILITACAO_A_VENCER.getId(), null);
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");
			Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
			String mensagemBase = "As carteiras de habilitação dos colaboradores relacionados abaixo estão próximas do vencimento:\n";
			String subject = "[RH] - Aviso de vencimento de habilitação";
			for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
				Collection<AreaOrganizacional> todasAreas = areaOrganizacionalManager.findAllListAndInativas(true, null, gerenciadorComunicacao.getEmpresa().getId());
				try {
					Collection<Integer> diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
					colaboradors = colaboradorManager.findHabilitacaAVencer(diasLembrete, gerenciadorComunicacao.getEmpresa().getId());
					HashMap<AreaOrganizacional, Collection<String>> mapAreaColaborador = new HashMap<AreaOrganizacional, Collection<String>>();
					for (Colaborador colaborador : colaboradors){
						if(!mapAreaColaborador.containsKey(colaborador.getAreaOrganizacional()))
							mapAreaColaborador.put(colaborador.getAreaOrganizacional(), new ArrayList<String>());
						mapAreaColaborador.get(colaborador.getAreaOrganizacional()).add("\nColaborador: "+colaborador.getNomeMaisNomeComercial()+"   --   Vencimento da habilitação: "+colaborador.getHabilitacao().getVencimentoFormatada()+".");
					}
					descideGerenciadorEnviaMensagemHabilitacaoAVencer(mensagemBase, subject, gerenciadorComunicacao, todasAreas, mapAreaColaborador);
				} catch (Exception e) {e.printStackTrace();}
			}
		} 
		catch (Exception e) {e.printStackTrace();}
	}

	private void descideGerenciadorEnviaMensagemHabilitacaoAVencer(String mensagemBase, String subject,	GerenciadorComunicacao gerenciadorComunicacao, Collection<AreaOrganizacional> todasAreas, HashMap<AreaOrganizacional, Collection<String>> mapAreaColaborador) throws Exception, AddressException, MessagingException {
		if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && (gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId()))) {
			enviaEmailParaGestorECogestor(mensagemBase, subject, gerenciadorComunicacao, todasAreas, mapAreaColaborador, AreaOrganizacional.RESPONSAVEL);
		} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
			enviaEmailParaGestorECogestor(mensagemBase, subject, gerenciadorComunicacao, todasAreas, mapAreaColaborador, AreaOrganizacional.CORRESPONSAVEL);
		} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())) {
			String[] emails = gerenciadorComunicacao.getEmpresa().getEmailRespRH().split(";");
			enviaEmailParaResponsavelRhEUsuarios(mensagemBase, subject, gerenciadorComunicacao, mapAreaColaborador, emails);
		} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
			UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBeanOld("usuarioManager");
			CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
			Long[] usuariosIds = collUtil.convertCollectionToArrayIds(gerenciadorComunicacao.getUsuarios());
			String[] emails = usuarioManager.findEmailsByUsuario(usuariosIds, null);
			enviaEmailParaResponsavelRhEUsuarios(mensagemBase, subject, gerenciadorComunicacao, mapAreaColaborador, emails);
		} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
			for (AreaOrganizacional area : mapAreaColaborador.keySet()){
				AreaOrganizacional areaOrganizacional = areaOrganizacionalManager.getMatriarca(todasAreas, area, null);
				StringBuilder mensagem = formaMensagem(mensagemBase, mapAreaColaborador, area);
				usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional(mensagem.toString(), "RH", null, areaOrganizacional.getDescricaoIds(), TipoMensagem.INFO_FUNCIONAIS, null, null);
			}
		} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
			for (AreaOrganizacional area : mapAreaColaborador.keySet()){
				AreaOrganizacional areaOrganizacional = areaOrganizacionalManager.getMatriarca(todasAreas, area, null);
				StringBuilder mensagem = formaMensagem(mensagemBase, mapAreaColaborador, area);
				usuarioMensagemManager.saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(mensagem.toString(), "RH", null, areaOrganizacional.getDescricaoIds(), TipoMensagem.INFO_FUNCIONAIS, null, null);							
			}
		} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
			StringBuilder mensagem = formaMensagem(mensagemBase, mapAreaColaborador);
			Collection<UsuarioEmpresa> usuarioEmpresas = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
			usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", null, usuarioEmpresas, null, TipoMensagem.INFO_FUNCIONAIS, null, null);
		}
	}

	private void enviaEmailParaResponsavelRhEUsuarios(String mensagemBase, String subject, GerenciadorComunicacao gerenciadorComunicacao, HashMap<AreaOrganizacional, Collection<String>> mapAreaColaborador, String[] emails) throws AddressException, MessagingException{
		StringBuilder mensagem = formaMensagem(mensagemBase, mapAreaColaborador);
		mail.send(gerenciadorComunicacao.getEmpresa(), subject, mensagem.toString().replaceAll("\n", "<br>"), null, emails);
	}

	private StringBuilder formaMensagem(String mensagemBase, HashMap<AreaOrganizacional, Collection<String>> mapAreaColaborador){
		SortedSet<String> msgs = new TreeSet<String>();
		for (AreaOrganizacional area : mapAreaColaborador.keySet())
			msgs.addAll(mapAreaColaborador.get(area));
				
		StringBuilder mensagem = new StringBuilder(mensagemBase);
		
		for (String msg : msgs)
			mensagem.append(msg);
				
		return mensagem;
	}

	private void enviaEmailParaGestorECogestor(String mensagemBase, String subject, GerenciadorComunicacao gerenciadorComunicacao, Collection<AreaOrganizacional> todasAreas, HashMap<AreaOrganizacional, Collection<String>> mapAreaColaborador, int tipoEnvio) throws Exception, AddressException, MessagingException{
		for (AreaOrganizacional area : mapAreaColaborador.keySet())
		{
			String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(area.getId(), todasAreas, tipoEnvio); 
			StringBuilder mensagem = formaMensagem(mensagemBase, mapAreaColaborador, area);

			try {
				mail.send(gerenciadorComunicacao.getEmpresa(), subject, mensagem.toString().replaceAll("\n", "<br>"), null, emails);
			} catch (Exception e) {	e.printStackTrace();}
		}
	}

	private StringBuilder formaMensagem(String mensagemBase, HashMap<AreaOrganizacional, Collection<String>> mapAreaColaborador, AreaOrganizacional area){
		StringBuilder mensagem = new StringBuilder(mensagemBase);
		for (String msg : mapAreaColaborador.get(area))
			mensagem.append(msg);
				
		return mensagem;
	}
	
	private void enviaEmailParaGestorOuCoGestorAC(TSituacao situacao, Empresa empresa, String mensagem, String subject, int tipoResponsavel) throws Exception{
		AreaOrganizacional areaOrganizacional = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(situacao.getLotacaoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
		String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(areaOrganizacional.getId(), empresa.getId(),tipoResponsavel, null);
		mail.send(empresa, subject, mensagem, null, emails);
	}

	public void enviarEmailParaResponsaveisSolicitacaoPessoal(Solicitacao solicitacao, Empresa empresa, String[] emailsMarcados) throws Exception{
		try	{
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRAR_SOLICITACAO.getId(), empresa.getId());
			if (!gerenciadorComunicacaos.isEmpty()) {
				ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findById(1L);

				ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
				Colaborador solicitante = colaboradorManager.findByUsuarioProjection(solicitacao.getSolicitante().getId(), true);

				String nomeSolicitante = "";
				if(solicitante != null) nomeSolicitante = solicitante.getNomeMaisNomeComercial();

				solicitacao.setMotivoSolicitacao(motivoSolicitacaoManager.findById(solicitacao.getMotivoSolicitacao().getId()));
				EstabelecimentoManager estabelecimentoManager = (EstabelecimentoManager) SpringUtil.getBean("estabelecimentoManager");
				solicitacao.setEstabelecimento(estabelecimentoManager.findById(solicitacao.getEstabelecimento().getId()));

				String nomeLiberador = "";
				if(solicitacao.getStatus() != StatusAprovacaoSolicitacao.ANALISE) nomeLiberador = nomeSolicitante;

				String subject = "[RH] - Liberação de solicitação de pessoal";
				StringBuilder body = new StringBuilder("Existe uma solicitação de pessoal na empresa " + empresa.getNome() + " aguardando liberação.<br>");

				if (solicitacao.getDescricao() != null)
					body.append("<p style=\"font-weight:bold;\">" + solicitacao.getDescricao() + "</p>");

				montaCorpoEmailSolicitacao(solicitacao, nomeSolicitante, nomeLiberador, body);

				if(emailsMarcados !=null && emailsMarcados.length > 0){
					Collection<String> emailsMarcadosNoCadastroDeSolicitacaoPessoal = new CollectionUtil<String>().convertArrayToCollection(emailsMarcados);
					mail.send(empresa, parametrosDoSistema, subject, body.toString(), true, StringUtil.converteCollectionToArrayString(emailsMarcadosNoCadastroDeSolicitacaoPessoal));
				}
				decideGerenciadorEnviarEmailParaResponsaveisSolicitacaoPessoal(solicitacao, empresa, gerenciadorComunicacaos, parametrosDoSistema, subject, body);
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	private void decideGerenciadorEnviarEmailParaResponsaveisSolicitacaoPessoal(Solicitacao solicitacao, Empresa empresa, Collection<GerenciadorComunicacao> gerenciadorComunicacaos, ParametrosDoSistema parametrosDoSistema, String subject, StringBuilder body) throws AddressException, MessagingException, Exception {
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
				Collection<String> emailsRH = new CollectionUtil<String>().convertArrayToCollection(empresa.getEmailRespRH().split(";"));
				String bodyResponsavel = body.toString();
				bodyResponsavel += "<br><br>Acesse o RH para mais detalhes:<br>";
				bodyResponsavel += "<a href='" + parametrosDoSistema.getAppUrl() + "'>RH</a>";
				if (emailsRH != null && emailsRH.size() > 0) 
					mail.send(empresa, parametrosDoSistema, subject, bodyResponsavel, true, StringUtil.converteCollectionToArrayString(emailsRH));
			}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL.getId())){
				UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBeanOld("usuarioManager");
				String[] emailsByUsuario = usuarioManager.findEmailsByPerfil("ROLE_LIBERA_SOLICITACAO", empresa.getId()); 
				String link = parametrosDoSistema.getAppUrl() + "/captacao/solicitacao/prepareUpdate.action?solicitacao.id=" + solicitacao.getId();
				String bodyAprovarReprovar = body.toString();
				bodyAprovarReprovar += "<br><br>Acesse o RH para aprovar ou reprovar a solicitação:<br>";
				bodyAprovarReprovar += "<a href='" + link + "'>RH</a>";
				mail.send(empresa, parametrosDoSistema, subject, bodyAprovarReprovar, true, emailsByUsuario);
			}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL_AND_GESTOR.getId())){
				String notEmail = null;
				if(solicitacao.isInvisivelParaGestor())
					notEmail = areaOrganizacionalManager.getEmailResponsavel(solicitacao.getAreaOrganizacional().getId());
				UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBeanOld("usuarioManager");
				String[] emailsByUsuario = usuarioManager.findEmailByPerfilAndGestor("ROLE_LIBERA_SOLICITACAO", empresa.getId(), solicitacao.getAreaOrganizacional().getId(), false, notEmail);
				if(emailsByUsuario != null && emailsByUsuario.length > 0 ){
					String link = parametrosDoSistema.getAppUrl() + "/captacao/solicitacao/prepareUpdate.action?solicitacao.id=" + solicitacao.getId();
					String bodyAprovarReprovar = body.toString();
					bodyAprovarReprovar += "<br><br>Acesse o RH para aprovar ou reprovar a solicitação:<br>";
					bodyAprovarReprovar += "<a href='" + link + "'>RH</a>";
					mail.send(empresa, parametrosDoSistema, subject, bodyAprovarReprovar, true, emailsByUsuario);
				}
			}
		}
	}

	public void montaCorpoEmailSolicitacao(Solicitacao solicitacao, String nomeSolicitante, String nomeLiberador, StringBuilder body)
	{
		body.append("<br>Descrição: " + solicitacao.getDescricao());
		body.append("<br>Data: " + DateUtil.formataDiaMesAno(solicitacao.getData()));
		body.append("<br>Motivo: " + (solicitacao.getMotivoSolicitacao() != null ? solicitacao.getMotivoSolicitacao().getDescricao() : ""));
		body.append("<br>Estabelecimento: " + (solicitacao.getEstabelecimento()!= null ? solicitacao.getEstabelecimento().getNome() : ""));
		body.append("<br>Solicitante: " + nomeSolicitante);
		body.append("<br>Status: " + solicitacao.getStatusFormatado());
		body.append("<br>Liberador: " + nomeLiberador);
		body.append("<br>Observação do liberador: " + StringUtils.trimToEmpty(solicitacao.getObservacaoLiberador()));
	}
	
	public void enviaAvisoAoCadastrarSolicitacaoRealinhamentoColaborador(Long empresaId, Colaborador colaborador, Long tabelaReajusteColaboradorId){
		try {
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CADASTRAR_SOLICITACAO_REALINHAMENTO_COLABORADOR.getId(), empresaId);
			if (!gerenciadorComunicacaos.isEmpty()) {
				String subject = "[RH] - Cadastro de solicitação de realinhamento para colaborador";
				String body = "Foi realizado um cadastro de solicitação de realinhamento para o colaborador " + StringUtils.defaultString(colaborador.getNome()) + ".";
				Empresa empresa = empresaManager.findByIdProjection(empresaId);
				for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
					if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())) {
						String[] emails = StringUtils.split(empresa.getEmailRespRH(), ";");
						mail.send(empresa, subject, body, null, emails);
					} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())){
						String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), colaborador.getEmpresa().getId(), AreaOrganizacional.RESPONSAVEL, null);
						mail.send(empresa, subject, body, null, emails);
					} else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())){
						String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), colaborador.getEmpresa().getId(), AreaOrganizacional.CORRESPONSAVEL, null);
						mail.send(empresa, subject, body, null, emails);
					} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId()))	{
						ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
						Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
						String[] emails = colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosConfigurados));
						mail.send(empresa, subject, body, null, emails);
					} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.APLICAR_REALINHAMENTO.getId())){
						UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBean("usuarioManager");
						String[] emails = usuarioManager.findEmailsByPerfil("ROLE_MOV_APLICARREALINHAMENTO", empresa.getId());
						ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
						String link = parametrosDoSistema.getAppUrl() + "/cargosalario/tabelaReajusteColaborador/visualizar.action?tabelaReajusteColaborador.id=" + tabelaReajusteColaboradorId;
						String bodyAplicarRealinhamento = body + "<br><br>Acesse o RH para aplicar o realinhamento:<br>" + "<a href='" + link + "'>RH</a>";
						mail.send(empresa, subject, bodyAplicarRealinhamento, null, emails);
					}
				}
			}
		}catch (Exception e){e.printStackTrace();}
	}
	
	public void enviaAvisoSolicitacaoDesligamento(Long colaboradorId, Long usuarioId, Long empresaId){
		try {
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			Colaborador colaborador = colaboradorManager.findByIdComHistorico(colaboradorId);
			String email = "";
			
			if(colaborador.getContato()!= null && colaborador.getContato().getEmail() != null && !"".equals(colaborador.getContato().getEmail()))
				email = colaborador.getContato().getEmail(); 
			
			Empresa empresa = empresaManager.findByIdProjection(empresaId);
			ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
			String link = parametrosDoSistema.getAppUrl() + "/geral/colaborador/visualizarSolicitacaoDesligamento.action?colaborador.id=" + colaborador.getId();
			UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBean("usuarioManager");
			
			Usuario usuario = usuarioManager.findByIdProjection(usuarioId);
			
			String subject = "[RH] - Solicitação de desligamento de colaborador";
			StringBuilder body = montaBodyEnviaAvisoSolicitacaoDesligamento(colaborador, empresa, usuario, link);
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.SOLICITAR_DESLIGAMENTO.getId(), empresaId);

			for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
				if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.APROVAR_REPROVAR_SOLICITACAO_DESLIGAMENTO.getId())){
					try {
						String[] emailsByUsuario = usuarioManager.findEmailByPerfilAndGestor("ROLE_MOV_APROV_REPROV_SOL_DESLIGAMENTO", empresaId, colaborador.getAreaOrganizacional().getId(), true, email);
						mail.send(empresa, parametrosDoSistema, subject, body.toString(), true, emailsByUsuario);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
					CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
					String[] emails = usuarioManager.findEmailsByUsuario(collUtil.convertCollectionToArrayIds(gerenciadorComunicacao.getUsuarios()), email);
					mail.send(gerenciadorComunicacao.getEmpresa(), parametrosDoSistema, subject, body.toString(), true, emails);
				}else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
					String[] emails = gerenciadorComunicacao.getEmpresa().getArrayEmailRespRH();
					emails = (String[]) ArrayUtils.removeElement(emails, email);
					mail.send(gerenciadorComunicacao.getEmpresa(), parametrosDoSistema, subject, body.toString(), true, emails);
				}else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
					String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.RESPONSAVEL, null);
					mail.send(gerenciadorComunicacao.getEmpresa(), parametrosDoSistema, subject, body.toString(), true, emails);
				}else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
					String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.CORRESPONSAVEL, null);
					mail.send(gerenciadorComunicacao.getEmpresa(), parametrosDoSistema, subject, body.toString(), true, emails);
				}
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	private StringBuilder montaBodyEnviaAvisoSolicitacaoDesligamento(Colaborador colaborador, Empresa empresa, Usuario usuario, String link){
		StringBuilder body = new StringBuilder(); 
		body.append("Existe uma solicitação de desligamento para o colaborador <b>");
		body.append(colaborador.getNome());
		body.append("</b> da empresa <b>"+empresa.getNome()+"</b> pendente. Para aprovar ou reprovar essa solicitação, acesse o sistema <a href='"+link+"'>RH</a>.<br/><br />");
		body.append("<b>Usuário solicitante:</b><br />");
		body.append(usuario.getNome() + "<br /><br />");
		body.append("<b>Data da Solicitação:</b><br />");
		body.append(DateUtil.formataDate(colaborador.getDataSolicitacaoDesligamento(), "dd/MM/yyyy") + "<br /><br />");
		body.append("<b>Motivo:</b><br />");
		body.append(colaborador.getMotivoDemissao().getMotivoFormatado() + "<br /><br />");
		body.append("<b>Observação:</b><br />");
		body.append(colaborador.getObservacaoDemissao());
		
		return body;
	}

	public void enviaAvisoAprovacaoSolicitacaoDesligamento(Long colaboradorId, String colaboradorNome, Long solicitanteDemissaoId, Empresa empresa, boolean aprovado){
		try {
			if (solicitanteDemissaoId == null || (colaboradorId != null && colaboradorId.equals(solicitanteDemissaoId))) 
				return;
			
			ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			String status = (aprovado? "aprovada" : "reprovada");
			String subject = "[RH] - Solicitação de desligamento de colaborador " + status;
			StringBuilder body = new StringBuilder();
			body.append("<br />Sua solicitação de desligamento para o(a) colaborador(a) <b>");
			body.append(colaboradorNome);
			body.append("</b> da empresa <b>" + empresa.getNome());
			body.append("</b> foi ");
			body.append(status);
			body.append(".<br /><br />");
			
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.APROVAR_SOLICITACAO_DESLIGAMENTO.getId(), empresa.getId());
			for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
				if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.SOLICITANTE_DESLIGAMENTO.getId())){
					Colaborador solicitante = colaboradorManager.findColaboradorByIdProjection(solicitanteDemissaoId);
					mail.send(empresa, parametrosDoSistema, subject, body.toString(), true, solicitante.getContato().getEmail());
				}
			}
			
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void enviarEmailAoCriarAcessoSistema(String login, String senha, String email, Empresa empresa) {
		try {
			if(StringUtils.isNotBlank(email)){
				ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);

				StringBuilder corpo = new StringBuilder();
				corpo.append("Seu acesso ao sistema RH foi liberado<br><br>");
				corpo.append("<strong>Login:</strong> "+login+"<br> <strong>Senha:</strong> "+senha+"<br><br>");
				corpo.append("Acesse o RH em: <br>");
				corpo.append("<a href=\"" + parametros.getAppUrl() + "\">RH</a><br><br>");
				getCopyright(corpo);

				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CRIAR_ACESSO_SISTEMA.getId(), empresa.getId());
				for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
					if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId())){
						mail.send(empresa, parametros, "[RH] - Liberação de acesso ao sistema RH", corpo.toString(), true, email);
					}
				} 		
			}
		}
		catch (Exception e) {e.printStackTrace();}
	}

	public void enviarNotificacaoCursosOuCertificacoesAVencer() {
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
		Collection<Empresa> empresas = empresaManager.findTodasEmpresas();
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = new ArrayList<GerenciadorComunicacao>();

		for (Empresa empresa : empresas) 
		{
			if(empresa.isControlarVencimentoPorCertificacao()){
				gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.CERTIFICACOES_A_VENCER.getId(), null);
				notificacaoCertificacao(parametros, gerenciadorComunicacaos);
			}
			else{
				gerenciadorComunicacaos.addAll(getDao().findByOperacaoId(Operacao.CURSOS_A_VENCER.getId(), null));
				notificacaoCurso(parametros, gerenciadorComunicacaos);
			}
		}
	}

	private void notificacaoCertificacao(ParametrosDoSistema parametros, Collection<GerenciadorComunicacao> gerenciadorComunicacaos) {
		Date data;
		Collection<Integer> diasLembrete = new ArrayList<Integer>();
		Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
		StringBuilder mensagemTitulo;
		StringBuilder mensagem;
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			try {
				diasLembrete = getIntervaloAviso (gerenciadorComunicacao.getQtdDiasLembrete());
				for (Integer diaLembrete : diasLembrete){
					data = DateUtil.incrementaDias(new Date(), diaLembrete);
					colaboradorCertificacoes = colaboradorCertificacaoManager.getCertificacoesAVencer(data, gerenciadorComunicacao.getEmpresa().getId());
					for (ColaboradorCertificacao colaboradorCertificacao : colaboradorCertificacoes){
						mensagemTitulo = new StringBuilder("[RH] - Falta(m) ").append(diaLembrete)
						.append(" dia(s) para a certificação ").append(colaboradorCertificacao.getCertificacao().getNome()).append(" do colaborador ")
						.append(colaboradorCertificacao.getColaborador().getNome()).append(" vencer.");

						mensagem = new StringBuilder("Certificação a Vencer: ").append(colaboradorCertificacao.getCertificacao().getNome());
						mensagem.append("<br>Vencimento: ").append(colaboradorCertificacao.getDataVencimentoCertificacao())
						.append("<br>Colaborador: ").append(colaboradorCertificacao.getColaborador().getNome())
						.append("<br>Área Organizacional: ").append(colaboradorCertificacao.getColaborador().getAreaOrganizacional().getNome())
						.append("<br>Cargo: ").append(colaboradorCertificacao.getColaborador().getFaixaSalarial().getDescricao());

						decideGerenciadorNotificacaoCertificacao(parametros, mensagemTitulo, mensagem, gerenciadorComunicacao, colaboradorCertificacao);
					}
				}
			} catch (AddressException e) {e.printStackTrace();
			} catch (MessagingException e) {e.printStackTrace();
			} catch (Exception e) {	e.printStackTrace();}
		}
	}

	private void decideGerenciadorNotificacaoCertificacao(ParametrosDoSistema parametros, StringBuilder mensagemTitulo, StringBuilder mensagem, GerenciadorComunicacao gerenciadorComunicacao, ColaboradorCertificacao colaboradorCertificacao)	throws AddressException, MessagingException, Exception {
		if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId())){
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, colaboradorCertificacao.getColaborador().getContato().getEmail());
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
			String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaboradorCertificacao.getColaborador().getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.RESPONSAVEL, null);
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, emails);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
			String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaboradorCertificacao.getColaborador().getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.CORRESPONSAVEL, null);
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, emails);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())) {
			String[] emails = gerenciadorComunicacao.getEmpresa().getArrayEmailRespRH();
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, emails);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
			UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBeanOld("usuarioManager");
			CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
			String[] emails = usuarioManager.findEmailsByUsuario(collUtil.convertCollectionToArrayIds(gerenciadorComunicacao.getUsuarios()), null);
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, emails);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
			usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional(mensagem.toString(), "RH", null, colaboradorCertificacao.getColaborador().getAreaOrganizacional().getDescricaoIds(), TipoMensagem.TED, null, null);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
			usuarioMensagemManager.saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(mensagem.toString(), "RH", null, colaboradorCertificacao.getColaborador().getAreaOrganizacional().getDescricaoIds(), TipoMensagem.TED, null, null);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
			Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);	
			usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", null, usuariosConfigurados, colaboradorCertificacao.getColaborador(), TipoMensagem.TED, null, null);
		}
	}
	
	private void notificacaoCurso(ParametrosDoSistema parametros, Collection<GerenciadorComunicacao> gerenciadorComunicacaos){
		ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBeanOld("colaboradorTurmaManager");
		Collection<ColaboradorTurma> colaboradoresTurmas = new ArrayList<ColaboradorTurma>();
		Collection<Integer> diasLembrete = new ArrayList<Integer>();
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){
			try {
				diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
				for (Integer diaLembrete : diasLembrete){
					colaboradoresTurmas = colaboradorTurmaManager.findCursosCertificacoesAVencer(DateUtil.incrementaDias(new Date(), diaLembrete), gerenciadorComunicacao.getEmpresa().getId());
					colaboradoresTurmas = agrupaCertificacoes(colaboradoresTurmas);

					for (ColaboradorTurma colaboradorTurma : colaboradoresTurmas){
						StringBuilder mensagemTitulo = new StringBuilder("[RH] - Falta(m) ").append(diaLembrete)
						.append(" dia(s) para o curso ").append(colaboradorTurma.getCurso().getNome()).append(" do colaborador ")
						.append(colaboradorTurma.getColaboradorNome()).append(" vencer.");

						StringBuilder mensagem = new StringBuilder("Curso a Vencer: ").append(colaboradorTurma.getCurso().getNome());
						if (colaboradorTurma.getCurso().getCertificacaoNome() != null && !colaboradorTurma.getCurso().getCertificacaoNome().isEmpty()) 
							mensagem.append("<br>Certificação(ões): ").append(colaboradorTurma.getCurso().getCertificacaoNome());
						mensagem.append("<br>Vencimento: ").append(colaboradorTurma.getTurma().getVencimentoFormatado())
						.append("<br>Colaborador: ").append(colaboradorTurma.getColaboradorNome())
						.append("<br>Área Organizacional: ").append(colaboradorTurma.getColaborador().getAreaOrganizacional().getNome())
						.append("<br>Cargo: ").append(colaboradorTurma.getColaborador().getFaixaSalarial().getDescricao());

						decideGerenciadorNotificacaoCurso(parametros, mensagemTitulo, mensagem,	gerenciadorComunicacao, colaboradorTurma);
					}
				}
			} catch (AddressException e) {e.printStackTrace();
			} catch (MessagingException e) {e.printStackTrace();
			} catch (Exception e) {e.printStackTrace();}
		}
	}

	private void decideGerenciadorNotificacaoCurso(ParametrosDoSistema parametros, StringBuilder mensagemTitulo, StringBuilder mensagem, GerenciadorComunicacao gerenciadorComunicacao,	ColaboradorTurma colaboradorTurma) throws AddressException, MessagingException, Exception {
		if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId())){
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, colaboradorTurma.getColaborador().getContato().getEmail());
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
			String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaboradorTurma.getColaborador().getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.RESPONSAVEL, null);
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, emails);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
			String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(colaboradorTurma.getColaborador().getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.CORRESPONSAVEL, null);
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, emails);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())) {
			String[] emails = gerenciadorComunicacao.getEmpresa().getArrayEmailRespRH();
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, emails);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
			UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBeanOld("usuarioManager");
			CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
			String[] emails = usuarioManager.findEmailsByUsuario(collUtil.convertCollectionToArrayIds(gerenciadorComunicacao.getUsuarios()), null);
			mail.send(gerenciadorComunicacao.getEmpresa(), parametros, mensagemTitulo.toString(), mensagem.toString(), true, emails);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
			usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional(mensagem.toString(), "RH", null, colaboradorTurma.getColaborador().getAreaOrganizacional().getDescricaoIds(), TipoMensagem.TED, null, null);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
			usuarioMensagemManager.saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(mensagem.toString(), "RH", null, colaboradorTurma.getColaborador().getAreaOrganizacional().getDescricaoIds(), TipoMensagem.TED, null, null);
		} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
			Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);	
			usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagem.toString(), "RH", null, usuariosConfigurados, colaboradorTurma.getColaborador(), TipoMensagem.TED, null, null);
		}
	}
	
	public void enviaEmailQuandoColaboradorCompletaAnoDeEmpresa(){
		try{
			Collection<Empresa> empresas = empresaManager.findTodasEmpresas();
			for (Empresa empresa : empresas) {
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId(), empresa.getId());
				for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
					if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId()))
						enviaEmailParaColaboradorComAnoDeEmpresa(empresa);
					else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())) {
						enviaEmailParaResponsavelRhComAnoDeEmpresa(empresa, gerenciadorComunicacao.getQtdDiasLembrete());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void enviaEmailParaColaboradorComAnoDeEmpresa(Empresa empresa) {
		Collection<Colaborador> colaboradores = colaboradorManager.findComAnoDeEmpresa(empresa.getId(), new Date());
		Cartao cartao = cartaoManager.findByEmpresaIdAndTipo(empresa.getId(), TipoCartao.ANO_DE_EMPRESA);
		StringBuilder body = null;
		
		for (Colaborador colaborador : colaboradores) {
			try {
				DataSource[] files = null;
				if(cartao != null)
					files = cartaoManager.geraCartao(cartao, colaborador);
					
				String subject = "Parabéns " + colaborador.getNome() + " por mais um ano de empresa";
				body = new StringBuilder("Parabéns por mais um ano de sucesso na empresa.");
				if(files != null)
					body.append("<br><br>Cartão em anexo.");
				
				if(StringUtils.isNotEmpty(colaborador.getContato().getEmail()))
					mail.send(empresa, subject, files, body.toString(), colaborador.getContato().getEmail());		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void enviaEmailParaResponsavelRhComAnoDeEmpresa(Empresa empresa, String configuracaoDiasLembrete)
	{
		Collection<Colaborador> colaboradores;
		Collection<Integer> diasLembrete;
		diasLembrete = getIntervaloAviso(configuracaoDiasLembrete);
		
		for (Integer diaLembrete : diasLembrete){
			try {
				colaboradores = colaboradorManager.findComAnoDeEmpresa(empresa.getId(), DateUtil.incrementaDias(new Date(), diaLembrete));
				if ( colaboradores.size() > 0 ) {
					String subject = "[RH] - Falta(m) "+ diaLembrete + " dia(s) para colaboradores completarem ano de empresa";
					StringBuilder body = new StringBuilder("Falta(m) "+ diaLembrete + " dia(s) para colaboradores completarem ano de empresa <br><br> ");
					body.append("<b>Empresa:</b> "+ empresa.getNome() +" <br><br>");
					body.append("Colaboradores que completarão ano de empresa: <br><br>");
					body.append("<table><thead><tr><th>Colaborador</th><th></th><th>Qtd. anos</th></tr></thead><tbody>");
					for (Colaborador colaborador : colaboradores){
						body.append("<tr><td>" + colaborador.getNome() + "</td>");
						body.append("<td></td><td align='center'>" + colaborador.getQtdAnosDeEmpresa().intValue() + "</td></tr>");
					}
					body.append("</tbody></table><br>");
					body.append("<b>Total de colaboradores:</b> " + colaboradores.size());
				
					String[] emails = empresa.getEmailRespRH().split(";");
					mail.send(empresa, subject, body.toString(), null, emails);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Collection<ColaboradorTurma> agrupaCertificacoes(Collection<ColaboradorTurma> colaboradoresTurmas){
		Map<Long, ColaboradorTurma> colaboradoresTurmasMap = new HashMap<Long, ColaboradorTurma>();
		
		for (ColaboradorTurma colaboradorTurma : colaboradoresTurmas){
			if(!colaboradoresTurmasMap.containsKey(colaboradorTurma.getColaborador().getId()))
				colaboradoresTurmasMap.put(colaboradorTurma.getColaborador().getId(), colaboradorTurma);
			else
				colaboradoresTurmasMap.get(colaboradorTurma.getColaborador().getId()).setCertificacaoNome(colaboradoresTurmasMap.get(colaboradorTurma.getColaborador().getId()).getCurso().getCertificacaoNome() + ", " + colaboradorTurma.getCurso().getCertificacaoNome());
		}

		return (Collection<ColaboradorTurma>) colaboradoresTurmasMap.values();
	}
	
	@SuppressWarnings("static-access")
	private void gerenciaEnvioCaixaMensagem(Collection<GerenciadorComunicacao> gerenciadorComunicacaos, Empresa empresa, Colaborador colaborador, Collection<AreaOrganizacional> todasAreas, String titulo, String corpo, String link, char tipoMsg, Usuario usuarioSolicitante, Long usuarioIdLogado) throws Exception, AddressException, MessagingException {
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			try {
				if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
					Long[] areasAncestraisIds = new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areaOrganizacionalManager.getAncestrais(todasAreas, colaborador.getAreaOrganizacional().getId()));
					usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional(corpo, "RH", link, new LongUtil().arrayLongToCollectionLong(areasAncestraisIds), tipoMsg, null, usuarioIdLogado);
				}else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
					Long[] areasAncestraisIds = new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areaOrganizacionalManager.getAncestrais(todasAreas, colaborador.getAreaOrganizacional().getId()));
					usuarioMensagemManager.saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(corpo, "RH", link, new LongUtil().arrayLongToCollectionLong(areasAncestraisIds), tipoMsg, null, usuarioIdLogado);
				}else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
					Collection<UsuarioEmpresa> usuariosEmpresa = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);	
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(corpo, "RH", link, usuariosEmpresa, colaborador, tipoMsg, null, usuarioIdLogado);
				}else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.SOLICITANTE_SOLICITACAO.getId())) {
					if(usuarioSolicitante != null){
						Collection<UsuarioEmpresa> usuariosEmpresa = new ArrayList<UsuarioEmpresa>();	
						usuariosEmpresa.add(new UsuarioEmpresa(usuarioSolicitante, null, empresa));
						usuarioMensagemManager.saveMensagemAndUsuarioMensagem(corpo, "RH", link, usuariosEmpresa, colaborador, tipoMsg, null, usuarioIdLogado);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void gerenciaEnvioEmails(Collection<GerenciadorComunicacao> gerenciadorComunicacaos, ParametrosDoSistema parametros, Empresa empresa, Long areaId, Collection<AreaOrganizacional> todasAreas, String titulo, String corpo, String emailUsuarioSolicitante, String emailUsuarioLogado) throws Exception, AddressException, MessagingException {
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			try {
				if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())){
					String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(areaId, todasAreas, AreaOrganizacional.RESPONSAVEL);
					mail.send(empresa, parametros, titulo, corpo, true, StringUtil.remove(emails, emailUsuarioLogado));
				}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())){
					String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(areaId, todasAreas, AreaOrganizacional.CORRESPONSAVEL);
					mail.send(empresa, parametros, titulo, corpo, true, StringUtil.remove(emails, emailUsuarioLogado));
				}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
					String[] emails = empresa.getEmailRespRH().split(";");
					mail.send(empresa, titulo, corpo, null, emails);
				}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())){
					ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
					Collection<UsuarioEmpresa> usuariosEmpresas = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
					String[] emails = colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosEmpresas));
					mail.send(empresa, titulo, corpo, null, StringUtil.remove(emails, emailUsuarioLogado));
				}else if(gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.SOLICITANTE_SOLICITACAO.getId())){
					if(!emailUsuarioSolicitante.equals(emailUsuarioLogado))
						mail.send(empresa, titulo, corpo, null, emailUsuarioSolicitante);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} 
	
	public void enviarAvisoAoInserirColaboradorSolicitacaoDePessoal(Empresa empresa, Usuario usuarioLogado, Long colaboradorId, Long solicitacaoId) {
		try {
			ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
			if(parametros.isAutorizacaoGestorNaSolicitacaoPessoal()){
				SolicitacaoManager solicitacaoManager = (SolicitacaoManager) SpringUtil.getBean("solicitacaoManager");
				Solicitacao solicitacao = solicitacaoManager.findById(solicitacaoId);

				ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
				Colaborador colaborador = colaboradorManager.findByIdDadosBasicos(colaboradorId, null); 
				
				String titulo = "Autorização da participação do colaborador em uma Solicitação de Pessoal";
				String link = "captacao/candidatoSolicitacao/prepareAutorizarColabSolicitacaoPessoal.action";
				
				StringBuilder corpoEmail = new StringBuilder();
				corpoEmail.append("O usuário \"" + usuarioLogado.getNome() + "\" inseriu o colaborador \""  + colaborador.getNome() +  "\" em uma Solicitacao de Pessoal.<br><br>");
				corpoEmail.append("Solicitação de Pessoal: " + solicitacao.getId() + " - " + solicitacao.getDescricao() + " - " + solicitacao.getDataFormatada() + "<br>");
				corpoEmail.append("Cargo da solicitação: " + solicitacao.getNomeDoCargoDaFaixaSalarial() + "<br>");
				corpoEmail.append("Área Organizacional da solicitação: " + solicitacao.getDescricaoDaAreaOrganizacional() + "<br><br>");
				
				String corpoCxMsg = corpoEmail.toString().replace("<br>", "\n");
				corpoCxMsg += "Acesse em \"R&S > Movimentações > Autorizar Participação do Colaborador na Solicitação de Pessoal\", para autorizar a participação desse colaborador no processo seletivo.";
				
				corpoEmail.append("Acesse o Fortes RH para autorizar a participação desse colaborador no processo seletivo.<br>");
				corpoEmail.append("<a href='" + parametros.getAppUrl() + "/" + link + "'>Acesse aqui</a><br><br>");
				
				getCopyright(corpoEmail);

				Collection<AreaOrganizacional> todasAreas = areaOrganizacionalManager.findAllListAndInativas(true, null, empresa.getId());
				Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB.getId(), empresa.getId());
				gerenciaEnvioEmails(gerenciadorComunicacaos, parametros, empresa, colaborador.getAreaOrganizacional().getId(), todasAreas, titulo, corpoEmail.toString(), null, null);
				gerenciaEnvioCaixaMensagem(gerenciadorComunicacaos, empresa, colaborador, todasAreas, titulo, corpoCxMsg, link, TipoMensagem.RES, null, null);
			}
		}catch (Exception e) {e.printStackTrace();}
	}
	
	public void enviarAvisoAoAlterarStatusColaboradorSolPessoal(Usuario usuarioLogado, CandidatoSolicitacao candidatoSolicitacaoAnterior, CandidatoSolicitacao candidatoSolicitacao, Empresa empresa) {
		try {
			ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
			SolicitacaoManager solicitacaoManager = (SolicitacaoManager) SpringUtil.getBean("solicitacaoManager");
			Solicitacao solicitacao = solicitacaoManager.findById(candidatoSolicitacaoAnterior.getSolicitacao().getId());

			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			Colaborador colaborador = colaboradorManager.findByCandidato(candidatoSolicitacaoAnterior.getCandidato().getId(), empresa.getId());
			colaborador = colaboradorManager.findByIdDadosBasicos(colaborador.getId(), null); 
			
			UsuarioManager usuarioManager = (UsuarioManager) SpringUtil.getBean("usuarioManager");

			Usuario usuarioSolicitante = null;
			if(candidatoSolicitacaoAnterior.getUsuarioSolicitanteAutorizacaoGestor() != null){
				usuarioSolicitante = usuarioManager.findById(candidatoSolicitacaoAnterior.getUsuarioSolicitanteAutorizacaoGestor().getId());
				usuarioSolicitante.setEmailColaborador(usuarioManager.findEmailByUsuarioId(usuarioSolicitante.getId()));
			}
			usuarioLogado.setEmailColaborador(usuarioManager.findEmailByUsuarioId(usuarioLogado.getId()));
			
			String titulo = "O status de autorização do colaborador para participar da Solicitação de Pessoal, foi alterado";
			
			StringBuilder corpoEmail = montaCorpoDoEmail(colaborador, solicitacao, candidatoSolicitacao, candidatoSolicitacaoAnterior, usuarioLogado);
						
			String corpoCxMsg = corpoEmail.toString().replace("<br>", "\n");
			getCopyright(corpoEmail);

			Collection<AreaOrganizacional> todasAreas = areaOrganizacionalManager.findAllListAndInativas(true, null, empresa.getId());
			
			Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB.getId(), empresa.getId());
			gerenciaEnvioEmails(gerenciadorComunicacaos, parametros, empresa, colaborador.getAreaOrganizacional().getId(), todasAreas, titulo, corpoEmail.toString(), getEmailUsuario(usuarioSolicitante), getEmailUsuario(usuarioLogado));
			gerenciaEnvioCaixaMensagem(gerenciadorComunicacaos, empresa, colaborador, todasAreas, titulo, corpoCxMsg, null, TipoMensagem.RES, usuarioSolicitante, usuarioLogado.getId());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private StringBuilder montaCorpoDoEmail(Colaborador colaborador, Solicitacao solicitacao, CandidatoSolicitacao candidatoSolicitacaoAtual, CandidatoSolicitacao candidatoSolicitacaoAnterior, Usuario usuarioLogado) {
		StringBuilder corpoEmail = new StringBuilder();
		corpoEmail.append("O usuário \"" + usuarioLogado.getNome() + "\" alterou o status de autorização do colaborador.<br><br>");
		corpoEmail.append("Colaborador: "  + colaborador.getNome() +  "<br>");
		corpoEmail.append("Solicitação de Pessoal: " + solicitacao.getId() + " - " + solicitacao.getDescricao() + " - " + solicitacao.getDataFormatada() + "<br>");
		corpoEmail.append("Cargo da solicitação: " + solicitacao.getNomeDoCargoDaFaixaSalarial() + "<br>");
		corpoEmail.append("Área Organizacional da solicitação: " + solicitacao.getDescricaoDaAreaOrganizacional() + "<br><br>");
		
		corpoEmail.append("Status Anterior:<br>");
		montaStringStatusGestorCandidatoSolicitacao(candidatoSolicitacaoAnterior, corpoEmail);

		corpoEmail.append("Status Atual:<br>");
		montaStringStatusGestorCandidatoSolicitacao(candidatoSolicitacaoAtual, corpoEmail);
		
		return corpoEmail;
	}

	private void getCopyright(StringBuilder corpoEmail) {
		corpoEmail.append("Copyright© by Fortes Tecnologia LTDA<br>");
		corpoEmail.append("http://www.fortestecnologia.com.br");
	}

	private void montaStringStatusGestorCandidatoSolicitacao(CandidatoSolicitacao candidatoSolicitacao, StringBuilder corpoEmail) {
		corpoEmail.append("- Status: "  + candidatoSolicitacao.getStatusAutorizacaoGestorFormatado() +  "<br>");
		corpoEmail.append("- Data: "  + (candidatoSolicitacao.getDataAutorizacaoGestorFormatado() == null ? "" : candidatoSolicitacao.getDataAutorizacaoGestorFormatado()) +  "<br>");
		corpoEmail.append("- Obs: "  + (candidatoSolicitacao.getObsAutorizacaoGestor() == null ? "" : candidatoSolicitacao.getObsAutorizacaoGestor()) +  "<br><br>");
	}

	private String getEmailUsuario(Usuario usuario) {
		String emailUsuarioLogado = "";
		if(usuario != null && usuario.getColaborador() != null && usuario.getColaborador().getContato() != null && usuario.getColaborador().getContato().getEmail() != null)
			emailUsuarioLogado = usuario.getColaborador().getContato().getEmail();
		return emailUsuarioLogado;
	}
	
	public void removeByOperacao(Integer[] operacoes){
		getDao().removeByOperacao(operacoes);
	}
	
	public void enviaAvisoLntAutomatico(){
		enviaAvisoInicioLnt(lntManager.findLntsNaoFinalizadas(new Date()));
		enviaAvisoFimLnt(lntManager.findLntsNaoFinalizadas(null));
	}
	
	public void enviaAvisoInicioLnt(Collection<Lnt> lnts){
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.INICIAR_PERIODO_LNT.getId(), null);
		
		if(gerenciadorComunicacaos == null || gerenciadorComunicacaos.size() == 0)
			return;
		
		for (Lnt lnt : lnts) 
			enviaAvisoInicioLnt(lnt, gerenciadorComunicacaos);
	} 

	public void enviaAvisoFimLnt(Collection<Lnt> lnts){
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.ENCERRAR_PERIODO_LNT.getId(), null);
		
		if(gerenciadorComunicacaos == null || gerenciadorComunicacaos.size() == 0)
			return;
		
		for (Lnt lnt : lnts) 
			enviaAvisoFimLnt(lnt, gerenciadorComunicacaos);
	}
	
	private void enviaAvisoInicioLnt(Lnt lnt, Collection<GerenciadorComunicacao> gerenciadorComunicacaos)
	{
		String subject = "[RH] - Início Levantamento de Necessidade de Treinamento (LNT)";
		StringBuilder body = new StringBuilder(); 
		body.append("LNT: "+ lnt.getDescricao() + " <br>");
		body.append("Período: "+ lnt.getPeriodoFormatado() + " <br>");
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) 
			gerenciaEnvioAvisoLnt(lnt.getId(), subject, body, gerenciadorComunicacao);	
	}
	
	private void enviaAvisoFimLnt(Lnt lnt, Collection<GerenciadorComunicacao> gerenciadorComunicacaos)
	{
		String subject = "[RH] - Encerramento Levantamento de Necessidade de Treinamento (LNT)";
		StringBuilder body = new StringBuilder(); 
		body.append("LNT: "+ lnt.getDescricao() + " <br>");
		body.append("Período: "+ lnt.getPeriodoFormatado() + " <br>");
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos){ 
			Collection<Integer> diasLembrete = getIntervaloAviso(gerenciadorComunicacao.getQtdDiasLembrete());
			for (Integer diaLembrete : diasLembrete){
				if(DateUtil.formataDiaMesAno(new Date()).equals(DateUtil.formataDiaMesAno(DateUtil.incrementaData(lnt.getDataFim(), Calendar.DAY_OF_MONTH, -1*diaLembrete, true)))){
					StringBuilder bodyDias = new StringBuilder(body.toString() + "Faltam "+ diaLembrete + " dias para encerrar o planejamento da LNT <br>");
					gerenciaEnvioAvisoLnt(lnt.getId(), subject, bodyDias, gerenciadorComunicacao);
				}
			}
		}
	}
	
	private void gerenciaEnvioAvisoLnt(Long lntId, String subject, StringBuilder body, GerenciadorComunicacao gerenciadorComunicacao)
	{
		String link = "desenvolvimento/lnt/relatorioParticipantesByUsuarioMsg.action?lnt.id="+lntId;
		
		try {
			if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
				String[] emails = gerenciadorComunicacao.getEmpresa().getArrayEmailRespRH();
				mail.send(gerenciadorComunicacao.getEmpresa(), subject, null, body.toString(), emails);
			}else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
				Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
				String[] emails = colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosConfigurados));
				mail.send(gerenciadorComunicacao.getEmpresa(), subject, null, body.toString(), emails);
			} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
				Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByLntId(lntId, new Long[]{});
				String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(areas, gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.RESPONSAVEL);
				mail.send(gerenciadorComunicacao.getEmpresa(), subject, null, body.toString(), emails);
			} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
				Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByLntId(lntId, new Long[]{});
				String[] emails = areaOrganizacionalManager.getEmailsResponsaveis(areas, gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.CORRESPONSAVEL);
				mail.send(gerenciadorComunicacao.getEmpresa(), subject, null, body.toString(), emails);
			} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
				Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);	
				usuarioMensagemManager.saveMensagemAndUsuarioMensagem(formateMensagem(subject,body), "RH", link, usuariosConfigurados, null, TipoMensagem.TED, null, null);
			} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
				Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByLntId(lntId, new Long[]{});
				Collection<Long> areasIds = areaOrganizacionalManager.getAncestraisIds(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas));
				usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional(formateMensagem(subject,body), "RH", link, areasIds, TipoMensagem.TED, null, null);
			} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
				Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByLntId(lntId, new Long[]{});
				Collection<Long> areasIds = areaOrganizacionalManager.getAncestraisIds(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas));
				usuarioMensagemManager.saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(formateMensagem(subject,body), "RH", link, areasIds, TipoMensagem.TED, null, null);
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviaAvisoLntFinalizada(String subject, StringBuilder body, String link, Long empresaId, Collection<Long> areasIds, Map<String, Object> parametros, Map<Long, Collection<ParticipanteCursoLnt>> mapPerticipantesLNTPorResponsaveis, Collection<ParticipanteCursoLnt> participanteCursoLntsEmpresa) 
	{
		DataSource[] anexo = null;
		String relatorioNome = "participantesLntAgrupadoPorArea.jasper";
		Collection<ParticipanteCursoLnt> participantesCursoLntOrdenados = new ArrayList<ParticipanteCursoLnt>();

		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = getDao().findByOperacaoId(Operacao.FINALIZAR_LNT.getId(), empresaId);
		if (gerenciadorComunicacaos.isEmpty())
			return;
		
		for (GerenciadorComunicacao gerenciadorComunicacao : gerenciadorComunicacaos) {
			try {
				if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.RESPONSAVEL_RH.getId())){
					anexo = ArquivoUtil.montaRelatorio(parametros, participanteCursoLntsEmpresa, relatorioNome);
					String[] emails = gerenciadorComunicacao.getEmpresa().getArrayEmailRespRH();
					mail.send(gerenciadorComunicacao.getEmpresa(), subject, anexo, body.toString(), emails);
				}else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
					anexo = ArquivoUtil.montaRelatorio(parametros, participanteCursoLntsEmpresa, relatorioNome);
					Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);
					String[] emails = colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosConfigurados));
					mail.send(gerenciadorComunicacao.getEmpresa(), subject, anexo, body.toString(), emails);
				} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
					Map<Long, String> mapResponsaveisIdsEmails = areaOrganizacionalManager.findMapResponsaveisIdsEmails(empresaId);
					for (Long responsaveisId : mapResponsaveisIdsEmails.keySet()) {
						if(mapPerticipantesLNTPorResponsaveis.containsKey(responsaveisId)){
							participantesCursoLntOrdenados = new CollectionUtil<ParticipanteCursoLnt>().sortCollectionStringIgnoreCase(mapPerticipantesLNTPorResponsaveis.get(responsaveisId), "colaborador.nome");
							participantesCursoLntOrdenados = new CollectionUtil<ParticipanteCursoLnt>().sortCollectionStringIgnoreCase(participantesCursoLntOrdenados, "cursoLnt.nomeNovoCurso");
							participantesCursoLntOrdenados = new CollectionUtil<ParticipanteCursoLnt>().sortCollectionStringIgnoreCase(participantesCursoLntOrdenados, "areaOrganizacional.nome");
							anexo = ArquivoUtil.montaRelatorio(parametros, participantesCursoLntOrdenados, relatorioNome);
							mail.send(gerenciadorComunicacao.getEmpresa(), subject, anexo, body.toString(), mapResponsaveisIdsEmails.get(responsaveisId));
						}
					}
				} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
					Map<Long, String> mapCoResponsaveisIdsEmails = areaOrganizacionalManager.findMapCoResponsaveisIdsEmails(empresaId);
					for (Long coResponsaveisId : mapCoResponsaveisIdsEmails.keySet()) {
						if(mapPerticipantesLNTPorResponsaveis.containsKey(coResponsaveisId)){
							participantesCursoLntOrdenados = new CollectionUtil<ParticipanteCursoLnt>().sortCollectionStringIgnoreCase(mapPerticipantesLNTPorResponsaveis.get(coResponsaveisId), "colaborador.nome");
							participantesCursoLntOrdenados = new CollectionUtil<ParticipanteCursoLnt>().sortCollectionStringIgnoreCase(participantesCursoLntOrdenados, "cursoLnt.nomeNovoCurso");
							participantesCursoLntOrdenados = new CollectionUtil<ParticipanteCursoLnt>().sortCollectionStringIgnoreCase(participantesCursoLntOrdenados, "areaOrganizacional.nome");
							anexo = ArquivoUtil.montaRelatorio(parametros, participantesCursoLntOrdenados, relatorioNome);
							mail.send(gerenciadorComunicacao.getEmpresa(), subject, anexo, body.toString(), mapCoResponsaveisIdsEmails.get(coResponsaveisId));
						}
					}
				} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.USUARIOS.getId())) {
					Collection<UsuarioEmpresa> usuariosConfigurados = verificaUsuariosAtivosNaEmpresa(gerenciadorComunicacao);	
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(formateMensagem(subject,body), "RH", link, usuariosConfigurados, null, TipoMensagem.TED, null, null);
				} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.GESTOR_AREA.getId())) {
					usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional(formateMensagem(subject,body), "RH", link, areasIds, TipoMensagem.TED, null, null);
				} else if (gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.CAIXA_MENSAGEM.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COGESTOR_AREA.getId())) {
					usuarioMensagemManager.saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(formateMensagem(subject,body), "RH", link, areasIds, TipoMensagem.TED, null, null);
				}
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void enviaEmailBoasVindasColaboradorJob(){
		Collection<Colaborador> colaboradores = colaboradorManager.findByAdmitidos(new Date());
		for (Colaborador colaborador : colaboradores) {
			if(colaborador.getContato() != null && colaborador.getContato().getEmail() != null)
				enviaEmailBoasVindasColaborador(colaborador);
		}
	}

	public void enviaEmailBoasVindasColaborador(Colaborador colaborador){
		try {
			GerenciadorComunicacao gerenciadorComunicacao = getDao().findByOperacaoIdAndEmpresaId(Operacao.BOAS_VINDAS_COLABORADORES.getId(), colaborador.getEmpresa().getId());
			if (gerenciadorComunicacao != null && gerenciadorComunicacao.getMeioComunicacao().equals(MeioComunicacao.EMAIL.getId()) && gerenciadorComunicacao.getEnviarPara().equals(EnviarPara.COLABORADOR.getId())
					&& colaborador.getContato() != null && colaborador.getContato().getEmail() != null && colaborador.getDataAdmissao().getTime() <= new Date().getTime()){
				Cartao cartao = cartaoManager.findByEmpresaIdAndTipo(colaborador.getEmpresa().getId(), TipoCartao.BOAS_VINDAS);
				if(cartao != null)
					mail.sendImg(colaborador.getEmpresa(), "Seja bem vindo a empresa " + colaborador.getEmpresaNome(), cartao.getMensagem().replace("#NOMECOLABORADOR#", colaborador.getNome()), ArquivoUtil.getPathBackGroundCartao(cartao.getImgUrl()), colaborador.getContato().getEmail());		
			}
		} catch (Exception e) {
			System.out.println("Email de boas vindas não funcionou.");
			e.printStackTrace();
		}
	}
	
	public void enviaCartoes(){
		enviaEmailCartaoAniversariantes();
		enviaEmailBoasVindasColaboradorJob();
		enviaEmailQuandoColaboradorCompletaAnoDeEmpresa();
	} 
	
	private String formateMensagem(String subject, StringBuilder body)
	{
		return subject + "\n\n" + body.toString().replaceAll("<br>", "\n");
	}

	enum TipoMensagemAfastamento{
		EMAIL("<br />"),
		MENSAGEM("\n");
		
		private String quebraDeLinha;
		
		public String getQuebraDeLinha() {
			return quebraDeLinha;
		}

		private TipoMensagemAfastamento(String quebraDeLinha) {
			this.quebraDeLinha = quebraDeLinha;
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

	public void setCidManager(CidManager cidManager) {
		this.cidManager = cidManager;
	}

	public void setMotivoSolicitacaoManager(MotivoSolicitacaoManager motivoSolicitacaoManager) {
		this.motivoSolicitacaoManager = motivoSolicitacaoManager;
	}

	public void setComissaoMembroManager(ComissaoMembroManager comissaoMembroManager) {
		this.comissaoMembroManager = comissaoMembroManager;
	}

	public void setColaboradorCertificacaoManager(ColaboradorCertificacaoManager colaboradorCertificacaoManager)
	{
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setCartaoManager(CartaoManager cartaoManager) {
		this.cartaoManager = cartaoManager;
	}

	public void setLntManager(LntManager lntManager) {
		this.lntManager = lntManager;
	}

	public void setParticipanteCursoLntManager(ParticipanteCursoLntManager participanteCursoLntManager)
	{
		this.participanteCursoLntManager = participanteCursoLntManager;
	}
}