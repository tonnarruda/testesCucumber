package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;

@Component
@SuppressWarnings({"rawtypes"})
public class CandidatoDWR
{
	@Autowired
	private ConhecimentoManager conhecimentoManager;
	@Autowired
	private CandidatoManager candidatoManager;
	@Autowired
	private ColaboradorManager colaboradorManager;
	@Autowired
	private SolicitacaoManager solicitacaoManager;

	public Map getConhecimentos(String[] areaIntereseIds, Long empresaId)
	{
		Collection<Conhecimento> conhecimentos;

		if (areaIntereseIds != null && areaIntereseIds.length > 0)
		{
			Long [] idsLong = new Long[]{0L};

			if(areaIntereseIds != null)
			{
				idsLong = new Long[areaIntereseIds.length];
				for(int i = 0; i < areaIntereseIds.length; i++)
				{
					idsLong[i] = new Long(areaIntereseIds[i]);
				}
			}

			conhecimentos =  conhecimentoManager.findByAreaInteresse(idsLong,empresaId);
		}
		else
		{
			conhecimentos =  conhecimentoManager.findToList(new String[]{"id", "nome"}, new String[]{"id", "nome"}, new String[]{"empresa.id"}, new Object[]{empresaId}, new String[]{"nome"});
		}

		return new CollectionUtil<Conhecimento>().convertCollectionToMap(conhecimentos,"getId","getNome");
	}

	public Map getCandidatosHomonimos(String candidatoNome)
	{
		Collection<Candidato> candidatos = candidatoManager.getCandidatosByNome(candidatoNome);

		return new CollectionUtil<Candidato>().convertCollectionToMap(candidatos,"getId","getNomeCpf");
	}
	
	public Map find(String nome, String cpf, Long empresaId)
	{
		Pessoal pessoal = new Pessoal();
		pessoal.setCpf(StringUtil.removeMascara(cpf));

		Candidato candidato = new Candidato();
		candidato.setNome(nome);
		candidato.setPessoal(pessoal);
		
		Collection<Candidato> candidatos = candidatoManager.findByNomeCpf(candidato, empresaId);
		
		return new CollectionUtil<Candidato>().convertCollectionToMap(candidatos,"getId","getNomeECpf");
	}
	
	public Collection<Object> findColaboradoresMesmoCpf(String[] candidatosCpfs)
	{
		Collection<Colaborador> colaboradores = candidatoManager.findColaboradoresMesmoCpf(candidatosCpfs);
		
		Collection<Object> retorno = new ArrayList<Object>();
		Map<String, String> colaboradorMap;
		
		for (Colaborador colaborador : colaboradores) 
		{
			colaboradorMap = new HashMap<String, String>();
			colaboradorMap.put("id", colaborador.getId().toString());
			colaboradorMap.put("nome", colaborador.getNome());
			colaboradorMap.put("cpf", colaborador.getPessoal().getCpf());
			colaboradorMap.put("cpfFormatado", colaborador.getPessoal().getCpfFormatado());
			colaboradorMap.put("dataDesligamento", colaborador.getDataDesligamentoFormatada());
			retorno.add(colaboradorMap);
		}
		
		return retorno;
	}

	public String montaMensagemExclusao(Long candidatoId, Long empresaId)
	{
		StringBuilder retorno = new StringBuilder();
		
		Collection<Solicitacao> solicitacaos = solicitacaoManager.findAllByCandidato(candidatoId);
		if (!solicitacaos.isEmpty()) 
		{
			retorno.append("O candidato está participando das seguintes solicitações:");
			
			for (Solicitacao solicitacao : solicitacaos) 
				retorno.append("<br /> - " + solicitacao.getDescricao());
		}
		
		Colaborador colaborador = colaboradorManager.findByCandidato(candidatoId, empresaId);
		if (colaborador != null) 
		{
			retorno.append("<br /><br />O candidato está vinculado ao seguinte colaborador:");
			retorno.append("<br /> - " + colaborador.getNomeComercialDesligado());
		}
		
		if (retorno.length() > 0)
			retorno.append("<br /><br />Deseja realmente remover o candidato?");
		
		return retorno.toString();
	}
    
	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}
	
	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager)
	{
		this.conhecimentoManager = conhecimentoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}
}

