package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

@Component
@SuppressWarnings("unchecked")
public class SolicitacaoDWR {

	@Autowired
	private SolicitacaoManager solicitacaoManager;
	@Autowired
	private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;
	@Autowired
	private AnuncioManager anuncioManager;

	public Map<Long, String> getSolicitacoes(Long empresaId) 
	{
		Collection<Solicitacao> solicitacaos = solicitacaoManager.findSolicitacaoList(empresaId, false, StatusAprovacaoSolicitacao.APROVADO, false);
		return new CollectionUtil<Solicitacao>().convertCollectionToMap(solicitacaos, "getId", "getDescricaoFormatada");
	}

	public Map<String, String> getObsSolicitacao(Long solicitacaoId) 
	{
		Solicitacao solicitacao = solicitacaoManager.findById(solicitacaoId);
		
		HashMap<String, String> solicitacaoMap = new HashMap<String, String>();
		solicitacaoMap.put("status", String.valueOf(solicitacao.getStatus()));
		solicitacaoMap.put("obs", solicitacao.getObservacaoLiberador());
		solicitacaoMap.put("dataStatus", DateUtil.formataDiaMesAno(solicitacao.getDataStatus()));
		
		return solicitacaoMap;  
	}
	
	public Map<Long, String> getByEmpresaEstabelecimentosAreas(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds) 
	{
		Collection<Solicitacao> solicitacoes = solicitacaoManager.findByEmpresaEstabelecimentosAreas(empresaId, estabelecimentosIds, areasIds);
		
		return new CollectionUtil<Solicitacao>().convertCollectionToMap(solicitacoes, "getId", "getDescricaoFormatada");
	}
	
	public Collection<SolicitacaoAvaliacao> findAvaliacoesNaoRespondidas(Long solicitacaoId, Long candidatoId)
	{
		return solicitacaoAvaliacaoManager.findAvaliacaoesNaoRespondidas(solicitacaoId, candidatoId);
	}
	
	public String enviarAnuncioEmail(Long anuncioId, Long empresaId, String nomeFrom, String emailFrom, String nomeTo, String emailTo)
	{
		try {
			anuncioManager.enviarAnuncioEmail(anuncioId, empresaId, nomeFrom, emailFrom, nomeTo, emailTo);
		} catch (Exception e) {
			e.printStackTrace();
			return "Não foi possível enviar o email. Tente novamente mais tarde.";
		}
		
		return "Anúncio de vaga enviado com sucesso";
	}
	
	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) 
	{
		this.solicitacaoManager = solicitacaoManager;
	}

	public void setSolicitacaoAvaliacaoManager(SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager) 
	{
		this.solicitacaoAvaliacaoManager = solicitacaoAvaliacaoManager;
	}

	public void setAnuncioManager(AnuncioManager anuncioManager) 
	{
		this.anuncioManager = anuncioManager;
	}
}
