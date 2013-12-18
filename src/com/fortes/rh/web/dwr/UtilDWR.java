package com.fortes.rh.web.dwr;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.AuthenticationFailedException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.Noticia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.IntegerUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.ws.AcPessoalClient;


public class UtilDWR
{
	private Mail mail;
	private AcPessoalClient acPessoalClient;
	private GrupoACManager grupoACManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

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
			mail.testEnvio("Teste do envio de email do RH", "Este email foi enviado de forma automática, não responda.", email, autenticacao, tls);
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
				
				Pattern pattern = Pattern.compile("1.1.[0-9]{1,3}.[0-9]{1,3}");
				Matcher matcher = pattern.matcher(pagina);
			    
			    matcher.find();
			    String versaoPortal = matcher.group(0);
		    	
			    ParametrosDoSistema parametrosDoSistema =  parametrosDoSistemaManager.findByIdProjection(1L);
			    String versaoCliente = parametrosDoSistema.getAppVersao();
			    
			    if (Long.parseLong(versaoPortal.replace(".", "")) > Long.parseLong(versaoCliente.replace(".", "")))
			    	return "{\"sucesso\":\"1\", \"versao\":\"" + versaoPortal + "\"}";
			}

			return "{\"sucesso\":\"2\"}";
		
		} catch (Exception e) 
		{
			e.printStackTrace();
			return "{\"sucesso\":\"0\"}";
		}
	}
	
	@SuppressWarnings("unchecked")
	public String findUltimasNoticias()
	{
		final String URL_INFO  = "http://www.fortesinformatica.com.br/cgi-bin/resources/resourcesvc?cmd=info&name=PublicidadeParaProdutos";
		final String URL_LISTA = "http://www.fortesinformatica.com.br/cgi-bin/resources/resourcesvc?cmd=get&id=";
		
		SAXBuilder builder = new SAXBuilder();
		String noticiasJSON = "";
		
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URL_LISTA).openConnection();
			con.setRequestMethod("HEAD");
			con.setConnectTimeout(5000);

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				// Recupera o id do servico
				String info = StringUtil.getHTML(URL_INFO);
				String[] infos = info.split("\r\n");
				
				if (!infos[0].equals("+OK"))
					throw new FortesException("Não foi possível recuperar as informações do serviço.");
				
				String id = infos[1].replace("[", "").replace("]", "").trim();
				
				// Recupera a lista de noticias
				Document document = builder.build(URL_LISTA + id);
				
				Element publicidadeElement = document.getRootElement();
				List<Element> noticiasElements = publicidadeElement.getChildren();
				Iterator<Element> noticiasElementsIterator = noticiasElements.iterator();
				
				Collection<Noticia> noticias = new ArrayList<Noticia>();
				Element noticiaElement;
				
				while(noticiasElementsIterator.hasNext())
				{
					noticiaElement = (Element) noticiasElementsIterator.next();
					Integer[] produtos = IntegerUtil.arrayStringToArrayInteger(noticiaElement.getChild("produtos").getValue().split(","));
					
					
					
					noticias.add(new Noticia(	noticiaElement.getChild("texto").getValue(), 
												noticiaElement.getChild("link").getValue(), 
												Integer.parseInt(noticiaElement.getChild("criticidade").getValue()), 
												produtos ));
				}
				
				if (!noticias.isEmpty())
					noticiasJSON = StringUtil.toJSON(noticias, null);
				
		    	return "{\"sucesso\":\"1\", \"noticias\":" + noticiasJSON + "}";
			}

			return "{\"sucesso\":\"2\"}";
		
		} catch (Exception e) 
		{
			e.printStackTrace();
			return "{\"sucesso\":\"0\"}";
		}
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	public void setGrupoACManager(GrupoACManager grupoACManager) 
	{
		this.grupoACManager = grupoACManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) 
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}
