package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("unchecked")
public class SolicitacaoDWR {

	private SolicitacaoManager solicitacaoManager;
	private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;
	private AnuncioManager anuncioManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;

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
	
	public String verificaModeloAvaliacaoSolicitacaoDestinoExiste(Long solicitacaoOrigemId, Long solicitacaoDestinoId, Long[] candidatosSolicitacaoIds){
		String avaliacoesNome = "";
		Collection<Avaliacao> avaliacoes = colaboradorQuestionarioManager.getAvaliacoesBySolicitacaoIdAndCandidatoSolicitacaoId(solicitacaoOrigemId, candidatosSolicitacaoIds);
		
		if(avaliacoes.size() > 0){
			Collection<SolicitacaoAvaliacao> solicitacaoDestinoAvaliacaos = solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacaoDestinoId, null);
			for (Avaliacao avaliacao : avaliacoes){
				boolean solicitcaoDestinoPossuiAvaliacao = false;

				for (SolicitacaoAvaliacao solicitacaoDestinoAvaliacao : solicitacaoDestinoAvaliacaos) {
					if(avaliacao.getId().equals(solicitacaoDestinoAvaliacao.getAvaliacao().getId())){
						solicitcaoDestinoPossuiAvaliacao = true;
						break;
					}
				}
				
				if(!solicitcaoDestinoPossuiAvaliacao)
					avaliacoesNome += avaliacao.getTipoModeloAvaliacaoDescricao() + ", ";
			}
		}		

		if(avaliacoesNome.equals(""))
			return "";
		
		return avaliacoesNome.substring(0, avaliacoesNome.length()-2);
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

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager) {
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}
}
