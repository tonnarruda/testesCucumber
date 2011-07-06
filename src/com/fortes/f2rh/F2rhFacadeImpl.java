package com.fortes.f2rh;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.antcontrib.logic.ForEach;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.fortes.model.type.File;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;

public class F2rhFacadeImpl implements F2rhFacade {

	public static final Logger log = Logger.getLogger(F2rhFacadeImpl.class);
	
	private final F2rhDownloadFacade downloader;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	
	/**
	 * Instanciará o F2rhDownloadFacadeImpl por padrão.
	 */
	public F2rhFacadeImpl() {
		downloader = new F2rhDownloadFacadeImpl();
	}
	
	public F2rhFacadeImpl(F2rhDownloadFacade downloader) {
		this.downloader = downloader;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Collection<Curriculo> obterCurriculos(ConfigF2RH config) {
		
		Collection<Curriculo> curriculos = new ArrayList<Curriculo>();
		ArrayList<Curriculo> cus = new ArrayList<Curriculo>();
		
		try {
			JsonConfig jsonConfig = new JsonConfig();

			JSONArray arr = JSONArray.fromObject(config.getJson(), jsonConfig);
			cus = (ArrayList<Curriculo>) JSONArray.toList(arr, Curriculo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		curriculos.addAll(cus);
		return curriculos;
	}
	

	public String find_f2rh(ConfigF2RH config) throws Exception 
	{
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod( config.getUrl() );
		get.setQueryString(prepareParams(config.getConsulta()));
		String obj = "";
		try {
			client.executeMethod( get );
			obj = get.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			get.releaseConnection();
		}
		
		return  obj;
	}
	
	public NameValuePair[] prepareParams(String[] query) {
		ArrayList<NameValuePair> lista = new ArrayList<NameValuePair>();
		for (String string : query) {
			String[] pair = string.split("=");
			if(pair.length == 2) {
				NameValuePair nvpair = new NameValuePair(pair[0], pair[1]);
				lista.add(nvpair);				
			}
		}
		NameValuePair[] params = new NameValuePair[]{};
		params = lista.toArray(params);
		return params;
	}

	public Collection<Curriculo> buscarCurriculos(String[] consulta) throws Exception 
	{
		F2rhFacade f2rhFacade = new F2rhFacadeImpl();
		
		ConfigF2RH config = new ConfigF2RH(consulta);		
		config.setJson(f2rhFacade.find_f2rh(config));
		
		return f2rhFacade.obterCurriculos(config);
	}
	
	public Collection<Curriculo> removeCandidatoInseridoSolicitacao(Long SolicitacaoId, Collection<Curriculo> curriculos) 
	{
		Collection<Integer> candidatosF2rhInseridosSolicitacao = candidatoSolicitacaoManager.getIdF2RhCandidato(SolicitacaoId);
		Curriculo remover = new Curriculo();
		
		for (Integer idf2RH : candidatosF2rhInseridosSolicitacao) 
		{
			if(idf2RH!=null)
			{
				remover.setId(idf2RH);
				curriculos.remove(remover);
			}
		}
		return curriculos;
	}
	
	/**
	 * Busca curriculos com as fotos em anexo.
	 */
	public Collection<Curriculo> buscarCurriculosComFoto(String[] consulta) throws Exception {
		Collection<Curriculo> curriculos = buscarCurriculos(consulta);
		carregaFotosDoF2rh(curriculos); // baixa as fotos dos candidatos
		return curriculos;
	}

	private void carregaFotosDoF2rh(Collection<Curriculo> curriculos) {
		for (Curriculo curriculo : curriculos) {
			File foto = buscaFotoDoCandidato(curriculo);
			curriculo.setFoto(foto);
		}
	}

	private File buscaFotoDoCandidato(Curriculo curriculo) {
		return downloader.download(curriculo);
	}

	public String[] montaIds(String[] curriculosId) 
	{
		//Ex.: new String[]{"curriculo[id][]=15",  "curriculo[id][]=1560"}
		Collection<String> idsF2RH = new ArrayList<String>(); 
		for (String id : curriculosId) 
		{
			String value = "curriculo[id][]=" + id;
			idsF2RH.add(value);
		}
		
		return StringUtil.converteCollectionToArrayString(idsF2RH);
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

}
