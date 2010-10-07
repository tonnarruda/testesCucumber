package com.fortes.rh.web.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.hibernate.NonUniqueResultException;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.xwork.Action;

@SuppressWarnings("unchecked")
public class CandidatoDWR
{
	private ConhecimentoManager conhecimentoManager;
	private CandidatoManager candidatoManager;
	private ColaboradorManager colaboradorManager;

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

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager)
	{
		this.conhecimentoManager = conhecimentoManager;
	}
	
	public Integer verificaCpfDuplicado(String cpf, Long empresaId, Long candidatoId) throws Exception
	{
		String cpfSemMascara = cpf.replaceAll("\\.", "").replaceAll("-", "").trim();
		
		if (!cpfSemMascara.equals("") && cpfSemMascara.length() == 11)
		{
			try {
				Colaborador colaborador = colaboradorManager.findTodosColaboradorCpf(cpfSemMascara, empresaId); 
				if (colaborador != null)
					return 2; //link para colaborador existente
			} catch (Exception e) {
				return 2;//link para colaborador existente	
			}
			try	{
				Candidato candidato = candidatoManager.verifyCPF(cpfSemMascara, empresaId, candidatoId);
				if (candidato != null)
					return 1; //link para candidato existente
			} catch (NonUniqueResultException notUniqueResultException) {
				return 1;//link para candidato existente
			}
		}

		return 0;//sem resultados
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}
}
