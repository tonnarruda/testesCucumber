package com.fortes.rh.business.geral;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.NoticiaDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Noticia;
import com.fortes.rh.model.geral.NoticiaComparator;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.IntegerUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.xwork.ActionContext;

public class NoticiaManagerImpl extends GenericManagerImpl<Noticia, NoticiaDao> implements NoticiaManager
{
	public Collection<Noticia> findByUsuario(Long usuarioId) 
	{
		return getDao().findByUsuario(usuarioId);
	}
	
	@SuppressWarnings("unchecked")
	public void carregarUltimasNoticias(Long usuarioId)
	{
		Collection<Noticia> noticias = getDao().findByUsuario(usuarioId);
		Collections.sort((List<Noticia>) noticias, new NoticiaComparator());
		
		ActionContext.getContext().getSession().put(Noticia.ULTIMAS_NOTICIAS, StringUtil.toJSON(noticias, null));
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void importarUltimasNoticias()
    {
		final String URL_INFO  = "http://www.fortesinformatica.com.br/cgi-bin/resources/resourcesvc?cmd=info&name=PublicidadeParaProdutos";
		final String URL_LISTA = "http://www.fortesinformatica.com.br/cgi-bin/resources/resourcesvc?cmd=get&id=";
		
		SAXBuilder builder = new SAXBuilder();
		
		NoticiaManager noticiaManager = (NoticiaManager) SpringUtil.getBeanOld("noticiaManager");
		
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URL_INFO).openConnection();
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
				Element noticiaElement;
				
				Noticia noticia, noticiaBD;
				noticiaManager.despublicarTodas();
				
				while (noticiasElementsIterator.hasNext())
				{
					noticiaElement = (Element) noticiasElementsIterator.next();
					Integer[] produtos = IntegerUtil.arrayStringToArrayInteger(noticiaElement.getChild("produtos").getValue().split(","));
					
					if (ArrayUtils.contains(produtos, Autenticador.appId))
					{
						noticia = new Noticia(	noticiaElement.getChild("texto").getValue(), 
												noticiaElement.getChild("link").getValue(), 
												Integer.parseInt(noticiaElement.getChild("criticidade").getValue()) );
						
						noticiaBD = noticiaManager.findByTexto(noticia.getTexto());
						
						if (noticiaBD == null)
						{
							noticia.setPublicada(true);
							noticiaManager.save(noticia);
						}
						else
						{
							noticiaBD.setPublicada(true);
							noticiaManager.update(noticiaBD);
						}
					} 
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public Noticia findByTexto(String texto) 
	{
		return getDao().findByTexto(texto);
	}

	public void despublicarTodas() 
	{
		getDao().despublicarTodas();
	}
}