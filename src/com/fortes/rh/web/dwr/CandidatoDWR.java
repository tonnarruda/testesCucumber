package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.NonUniqueResultException;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings("unchecked")
public class CandidatoDWR
{
	private ConhecimentoManager conhecimentoManager;
	private CandidatoManager candidatoManager;
	private ColaboradorManager colaboradorManager;
	private SolicitacaoManager solicitacaoManager;
	private EmpresaManager empresaManager;

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

	public String verificaCpfDuplicado(String cpf, Long empresaId, Long id, String edit) throws Exception
	{
		//acho que não ta sendo utilizado, pode ser em algum ftl. Acho que tem um bug se tiver 3 cpf iguais
		String cpfSemMascara = cpf.replaceAll("\\.", "").replaceAll("-", "").trim();
		
		if (!cpfSemMascara.equals("") && cpfSemMascara.length() == 11)
		{
			try {
				Colaborador colaborador = colaboradorManager.findTodosColaboradorCpf(cpfSemMascara, empresaId, id); 
				if (colaborador != null)
					return "colaborador"; //link para colaborador existente
			} catch (NonUniqueResultException notUniqueResultException) {
				return "colaborador";//link para colaborador existente	
			}
			try	{
				Candidato candidato = candidatoManager.verifyCPF(cpfSemMascara, empresaId, id, false);
				if (candidato != null)
					return "candidato"; //link para candidato existente
			} catch (NonUniqueResultException notUniqueResultException) {
				return "candidato";//link para candidato existente
			}
		}

		return "";//sem resultados
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
		
		Collection<Colaborador> colaboradores = colaboradorManager.findBycandidato(candidatoId, empresaId);
		if (!colaboradores.isEmpty()) 
		{
			retorno.append("<br /><br />O candidato está vinculado aos seguintes colaboradores:");
			
			for (Colaborador colaborador : colaboradores) 
				retorno.append("<br /> - " + colaborador.getNomeComercialDesligado());
		}
		
		if (retorno.length() > 0)
			retorno.append("<br /><br />Deseja realmente remover o candidato?");
		
		return retorno.toString();
	}
    
    public Collection<Object> findParentesByNome(String nome, Long empresaId)
    {
    	Empresa empresa = empresaManager.findById(empresaId);
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();

    	if(empresa.isNomeHomonimo())
    		colaboradores = colaboradorManager.findParentesByNome(nome, null);
    	else if(empresa.isNomeHomonimoEmpresa())
    		colaboradores = colaboradorManager.findParentesByNome(nome, empresaId);
    	else
    		return null;
    	
    	return colaboradorManager.montaParentesByNome(colaboradores);
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

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}

