package com.fortes.rh.web.dwr;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import uk.ltd.getahead.dwr.WebContextFactory;

import com.fortes.rh.business.geral.CartaoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.dicionario.TipoCartao;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.ws.AcPessoalClient;
import com.sun.mail.smtp.SMTPAddressFailedException;

public class UtilDWR {
	private Mail mail;
	private AcPessoalClient acPessoalClient;
	private GrupoACManager grupoACManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private CartaoManager cartaoManager;
	private static Logger logger = Logger.getLogger(UtilDWR.class);

	private static String ENVIADO = "Email enviado com sucesso.";

	public String getToken(String grupoAC) {
		String token = "";
		GrupoAC grupo = grupoACManager.findByCodigo(grupoAC);

		if (grupo == null)
			return "Grupo AC não encontrado";

		try {
			token = acPessoalClient.getToken(grupo);
			token = "Conexão efetuada com sucesso.";
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().equals("Usuário Não Autenticado!"))
				token = "Usuário Não Autenticado.";
			else
				token = "Serviço não disponível.";
		}

		return token;
	}

	public String enviaEmail(String email, boolean autenticacao, boolean tls) throws Exception {
		try {
			Empresa empresa = SecurityUtil.getEmpresaByDWR(WebContextFactory.get().getHttpServletRequest().getSession());
			mail.testEnvio(empresa, "Teste do envio de email do RH", "Este email foi enviado de forma automática, não responda.", email, autenticacao, tls);
		} catch (AuthenticationFailedException e) {
			e.printStackTrace();
			if (e.getMessage() == null)
				return "Erro ao tentar autenticar o usuário, verifique Usuário e Senha.";
		} catch (MessagingException e) {

			StringBuilder stringBuilder = new StringBuilder();

			String mensagemErro = e.getMessage();
			if (e.getCause() instanceof SMTPAddressFailedException) {
				stringBuilder.append("O servidor SMTP recusou o envio do seu e-mail.\n");
				stringBuilder.append("Isto pode ter ocorrido devido algumas regras impostas ao seu servidor de email. Segue abaixo algumas dessas regras:\n\n");
				stringBuilder.append("1- O remetente do email (configurado no cadastro de empresa) não pode ser diferente do usuário configurado no cadastro do SMTP.\n\n");
				stringBuilder.append(" Por exemplo: Email cadastrado no cadastro de empresa está 'exemplo@fortes.com.br' e na configuração do SMTP 'fortes@exemplo.com.br'. \n\n");
				stringBuilder.append("2- O servidor SMTP não permite que o envio de emails pela máquina onde o sistema RH está instalado.\n\n");
				stringBuilder.append("3- O servidor SMTP requer autenticação. \n\n");
				stringBuilder.append("Obs: Caso não consiga concluir o envio do email, entre em contato com o suporte.");

				logger.info("Erro de configuração inesperado no servidor SMTP: " + mensagemErro);
			} else {
				stringBuilder.append("O servidor SMTP possui configurações divergentes do sistema, os problemas mais comuns são:\n\n");
				stringBuilder.append("1- O envio do email  do remetente (configurado no cadastro de empresa) não pode ser diferente do usuário configurado no cadastro de SMTP. \n\n");
				stringBuilder.append("2- Os campos 'Servidor SMTP' e 'Porta SMTP' respectivamente estão com informações divergentes. \n\n");
				stringBuilder.append("3- Campo 'TLS' marcado quando o servidor não o utiliza.\n\n");
				stringBuilder.append("Obs: Caso não consiga concluir o envio do email, entre em contato com o suporte.");

				logger.info("Erro de configuração do usuário: " + mensagemErro);
			}

			return stringBuilder.toString();
		}

		catch (Exception e) {
			e.printStackTrace();

			if (e.getMessage() == null)
				return "Erro desconhecido, entre em contato com o suporte.";
			else
				throw e;
		}

		return ENVIADO;
	}

	public String enviaEmailCartaoBoasVindas(String email, Long empresaId, String empresaNome, String empresaEmailRemetente) throws Exception {
		try {
			// Testa se o envio de email esta funcionando
			ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
			String msgRetorno = enviaEmail("teste@xteste.com", parametrosDoSistema.isAutenticacao(), parametrosDoSistema.isTls());
			if (!msgRetorno.equals(ENVIADO))
				return msgRetorno;
			// *

			if (empresaEmailRemetente == null)
				empresaEmailRemetente = "teste@teste.com.br";

			Empresa empresa = new Empresa();
			empresa.setId(empresaId);
			empresa.setEmailRemetente(empresaEmailRemetente);
			empresa.setNome(empresaNome);

			Cartao cartao = cartaoManager.findByEmpresaIdAndTipo(empresa.getId(), TipoCartao.BOAS_VINDAS);
			mail.sendImg(empresa, "Seja bem vindo a empresa " + empresa.getNome(), cartao.getMensagem().replace("#NOMECOLABORADOR#", "'Nome do Colaborador'"), ArquivoUtil.getPathBackGroundCartao(cartao.getImgUrl()), email);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() == null)
				return "Erro desconhecido, entre em contato com o suporte.";
			else
				throw e;
		}

		return "Email enviado com sucesso.</br> Verifique no email " + email + " se o teste de aviso de boas-vindas chegou.";
	}

	public String findUltimaVersaoPortal() {
		final String URL = "http://www.fortesinformatica.com.br/v3/downloadParserVer.php?system=6396";

		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URL).openConnection();
			con.setRequestMethod("HEAD");
			con.setConnectTimeout(5000);

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				String pagina = StringUtil.getHTML(URL);

				Pattern pattern = Pattern.compile("1.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}");
				Matcher matcher = pattern.matcher(pagina);

				matcher.find();
				String versaoPortal = matcher.group(0);

				ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
				String versaoCliente = parametrosDoSistema.getAppVersao();

				if (getValorParaComparacao(versaoPortal) > getValorParaComparacao(versaoCliente))
					return "{\"sucesso\":\"1\", \"versao\":\"" + versaoPortal + "\"}";
			}

			return "{\"sucesso\":\"2\"}";

		} catch (Exception e) {
			e.printStackTrace();
			return "{\"sucesso\":\"0\"}";
		}
	}

	private Long getValorParaComparacao(String versao) throws NumberFormatException {
		Long valorParaComparacao = Long.parseLong(versao.replace(".", ""));

		if (versao.length() < 11) {
			String[] versaoSplit = versao.split("\\.");
			valorParaComparacao = Long.parseLong(versaoSplit[0] + versaoSplit[1] + zeroAEsquerda(versaoSplit[2]) + zeroAEsquerda(versaoSplit[3]));
		}

		return valorParaComparacao;
	}

	private String zeroAEsquerda(String valor) {
		int tamanho = valor.length();
		while (tamanho < 3) {
			valor = "0" + valor;
			tamanho++;
		}
		return valor;
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient) {
		this.acPessoalClient = acPessoalClient;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void setGrupoACManager(GrupoACManager grupoACManager) {
		this.grupoACManager = grupoACManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setCartaoManager(CartaoManager cartaoManager) {
		this.cartaoManager = cartaoManager;
	}
}
