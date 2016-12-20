package com.fortes.rh.web.dwr;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.AuthenticationFailedException;

import org.directwebremoting.WebContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import uk.ltd.getahead.dwr.WebContextFactory;


import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.ws.AcPessoalClient;

@Component
public class UtilDWR
{
	@Autowired private Mail mail;
	@Autowired private AcPessoalClient acPessoalClient;
	@Autowired private GrupoACManager grupoACManager;
	@Autowired private ParametrosDoSistemaManager parametrosDoSistemaManager;

	public String getToken(String grupoAC)
	{ 
		String token = "";
		GrupoAC grupo = grupoACManager.findByCodigo(grupoAC);
		
		if(grupo == null)
			return "Grupo AC não encontrado";

		try
		{
			token = acPessoalClient.getToken(grupo);
			token = "Conexão efetuada com sucesso.";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e.getMessage().equals("Usuário Não Autenticado!"))
				token = "Usuário Não Autenticado.";
			else
				token = "Serviço não disponível.";
		}

		return token;
	}

	public String enviaEmail(String email, boolean autenticacao, boolean tls) throws Exception
	{
		try {
			Empresa empresa = SecurityUtil.getEmpresaByDWR(WebContextFactory.get().getHttpServletRequest().getSession());
			mail.testEnvio(empresa, "Teste do envio de email do RH", "Este email foi enviado de forma automática, não responda.", email, autenticacao, tls);
		} catch (AuthenticationFailedException e) {
			e.printStackTrace();
			if(e.getMessage() == null)
				return "Erro ao tentar autenticar o usuário, verifique Usuário e Senha.";
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage() == null)
				return "Erro desconhecido, entre em contato com o suporte.";
			else
				throw e;
		}

		return "Email enviado com sucesso.";
	}
	
	public String findUltimaVersaoPortal()
	{
		final String URL = "http://www.fortesinformatica.com.br/v3/downloadParserVer.php?system=6396";
		
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URL).openConnection();
			con.setRequestMethod("HEAD");
			con.setConnectTimeout(5000);

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				String pagina = StringUtil.getHTML(URL);
				
				Pattern pattern = Pattern.compile("1.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}");
				Matcher matcher = pattern.matcher(pagina);
			    
			    matcher.find();
			    String versaoPortal = matcher.group(0);
		    	
			    ParametrosDoSistema parametrosDoSistema =  parametrosDoSistemaManager.findByIdProjection(1L);
			    String versaoCliente = parametrosDoSistema.getAppVersao();
			    
			    if (getValorParaComparacao(versaoPortal) > getValorParaComparacao(versaoCliente))
			    	return "{\"sucesso\":\"1\", \"versao\":\"" + versaoPortal + "\"}";
			}

			return "{\"sucesso\":\"2\"}";
		
		} catch (Exception e){
			e.printStackTrace();
			return "{\"sucesso\":\"0\"}";
		}
	}

	private Long getValorParaComparacao(String versao) throws NumberFormatException {
		Long valorParaComparacao = Long.parseLong(versao.replace(".", ""));
		
		if(versao.length() < 11){
			String[] versaoSplit = versao.split("\\.");
			valorParaComparacao = Long.parseLong(versaoSplit[0] + versaoSplit[1] + zeroAEsquerda(versaoSplit[2]) + zeroAEsquerda(versaoSplit[3])); 
		}
		
		return valorParaComparacao;
	}
	
	private String zeroAEsquerda(String valor){
		int tamanho = valor.length();
		 while(tamanho < 3){
			 valor="0"+valor;
			 tamanho++;
		 }
		 return valor;
	}
	
	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}
}
