package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CandidatoSolicitacaoManagerImpl extends GenericManagerImpl<CandidatoSolicitacao, CandidatoSolicitacaoDao> implements CandidatoSolicitacaoManager
{
    public CandidatoSolicitacao findByCandidatoSolicitacao(CandidatoSolicitacao cand)
    {
        return getDao().findByCandidatoSolicitacao(cand);
    }

    public void insertCandidatos(String[] candidatosId, Solicitacao solicitacao, char status)
    {
        String[] properties = new String[]{"id","candidato.id","solicitacao.id","triagem"};
        String[] sets = new String[]{"id","candidatoId","solicitacaoId","triagem"};

        Collection<CandidatoSolicitacao> cands = findToList(properties, sets, new String[]{"solicitacao"}, new Object[]{solicitacao});

        for (String idCand : candidatosId)
        {
            Candidato candidato = new Candidato();
            candidato.setId(Long.valueOf(idCand));

            CandidatoSolicitacao candSolTmp = null;
            boolean existe = false;

            for (CandidatoSolicitacao candidatoSolicitacao : cands)
            {
                if (candidatoSolicitacao.getCandidato().getId().equals(candidato.getId()))
                {
                    candSolTmp = candidatoSolicitacao;
                    existe = true;
                    break;
                }
            }

            if (existe)
            {
                if (candSolTmp.isTriagem())
                {
                    candSolTmp.setTriagem(false);
                    update(candSolTmp);
                }
            }
            else
            {
                CandidatoSolicitacao candidatoSol = new CandidatoSolicitacao();
                candidatoSol.setCandidato(candidato);
                candidatoSol.setSolicitacao(solicitacao);
                candidatoSol.setTriagem(false);
               	candidatoSol.setStatus(status);

                save(candidatoSol);
            }
        }
    }

    public void moverCandidatos(Long[] candidatosSolicitacaoId, Solicitacao solicitacao) throws ColecaoVaziaException
    {
        Collection lista = getDao().findCandidatosAptosMover(candidatosSolicitacaoId, solicitacao);
        if(lista.isEmpty())
        	throw new ColecaoVaziaException("Candidato selecionado já está na solicitação.");
        
        getDao().updateSolicitacaoCandidatos(solicitacao, lista);
    }

    public Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(String[] etapaCheck, Long empresaId, char statusSolicitacao, char situacaoCandidato, Date dataIni, Date dataFim)
    {
        return getDao().getCandidatosBySolicitacao(LongUtil.arrayStringToArrayLong(etapaCheck), empresaId, statusSolicitacao, situacaoCandidato, dataIni, dataFim);
    }

    public Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(Solicitacao solicitacao, ArrayList<Long> idCandidatosComHistoricos)
    {
        return getDao().getCandidatosBySolicitacao(solicitacao, idCandidatosComHistoricos);
    }
    
    public String[] getEmailNaoAptos(Long solicitacaoId, Empresa empresa) throws Exception
    {
    	ArrayList<String> emailNaoAptos = new ArrayList<String>();

    	Collection<CandidatoSolicitacao> candidatoSolicitacoes = getDao().findNaoAptos(solicitacaoId);

    	if (candidatoSolicitacoes != null  && !candidatoSolicitacoes.isEmpty())
    	{
    		for (CandidatoSolicitacao candidatoSolicitacao : candidatoSolicitacoes)
    		{
    			if (StringUtils.isNotBlank(candidatoSolicitacao.getCandidato().getContato().getEmail()))
    				emailNaoAptos.add(candidatoSolicitacao.getCandidato().getContato().getEmail());
    		}

    	}
        
		return emailNaoAptos.toArray(new String[]{});
    }

    public Collection<CandidatoSolicitacao> verificaExisteColaborador(Collection<CandidatoSolicitacao> candidatoSolicitacaos, Long empresaId)
    {
    	ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
        for(CandidatoSolicitacao candidatoSolicitacao : candidatoSolicitacaos)
            candidatoSolicitacao.setExisteColaborador(colaboradorManager.candidatoEhColaborador(candidatoSolicitacao.getCandidato().getId(), empresaId));

        return candidatoSolicitacaos;
    }

    public CandidatoSolicitacao findCandidatoSolicitacaoById(Long candidatoSolicitacaoId)
    {
        return getDao().findCandidatoSolicitacaoById(candidatoSolicitacaoId);
    }

    public Collection<CandidatoSolicitacao> findCandidatoSolicitacaoById(Long[] candidatoSolicitacaoIds)
    {
        return getDao().findCandidatoSolicitacaoById(candidatoSolicitacaoIds);
    }

    public Boolean isCandidatoSolicitacaoByCandidato(Long candidatoId)
    {
    	CandidatoSolicitacao candidatoSolicitacao = getDao().getCandidatoSolicitacaoByCandidato(candidatoId);

    	if(candidatoSolicitacao != null && candidatoSolicitacao.getId() != null)
    		return true;
    	else
    		return false;

    }

    public Collection<CandidatoSolicitacao> getCandidatoSolicitacaoList(Integer page, Integer pagingSize, Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, boolean semHistorico, String observacaoRH, String nomeBusca, Character status)
	{
        return getDao().getCandidatoSolicitacaoList(page, pagingSize, solicitacaoId, etapaSeletivaId, indicadoPor, visualizar, contratado, semHistorico, observacaoRH, nomeBusca, status);
	}
    public Collection<CandidatoSolicitacao> getCandidatoSolicitacaoEtapasEmGrupo(Long solicitacaoId, Long etapaSeletivaId)
    {
    	return getDao().getCandidatoSolicitacaoEtapasEmGrupo(solicitacaoId, etapaSeletivaId);
    }

	public Collection<CandidatoSolicitacao> findBySolicitacaoTriagem(Long solicitacaoId)
	{
		return getDao().findBySolicitacaoTriagem(solicitacaoId);
	}
	
	public Collection<CandidatoSolicitacao> findByFiltroSolicitacaoTriagem(Boolean triagen)
	{
		return getDao().findByFiltroSolicitacaoTriagem(triagen);
	}

	public void updateTriagem(Long[] candidatoSolicitacaoIdsSelecionados, boolean triagem)
	{
		getDao().updateTriagem(candidatoSolicitacaoIdsSelecionados, triagem);
	}

	public Collection<Long> getCandidatosBySolicitacao(Long solicitacaoId)
	{
		return getDao().getCandidatosBySolicitacao(solicitacaoId);
	}

	public Integer getCount(Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, String observacaoRH, String nomeBusca)
	{
		return getDao().getCount(solicitacaoId, etapaSeletivaId, indicadoPor, visualizar, contratado, observacaoRH, nomeBusca);
	}

	//TODO BACALHAU, findById apenas para update no status
	public void setStatus(Long candidatoSolicitacaoId, char status)
	{
		CandidatoSolicitacao cs = getDao().findById(candidatoSolicitacaoId);
		cs.setStatus(status);
		getDao().update(cs);
	}

	public Collection<Integer> getIdF2RhCandidato(Long SolicitacaoId) 
	{
		return getDao().getIdF2RhCandidato(SolicitacaoId);
	}

	public void setStatusByColaborador(char status, Long... colaboradoresIds) 
	{
		getDao().setStatusByColaborador(status, colaboradoresIds);
	}

	public void removeCandidato(Long candidatoId) 
	{
		getDao().removeByCandidato(candidatoId);
	}

	public Collection<ColaboradorQuestionario> findAvaliacoesCandidatoSolicitacao(Long solicitacaoId, Long candidatoId) 
	{
		return getDao().findAvaliacoesCandidatoSolicitacao(solicitacaoId, candidatoId);
	}

	public void setStatusBySolicitacaoAndCandidato(char status, Long candidatoId, Long solicitacaoId) 
	{
		getDao().setStatusBySolicitacaoAndCandidato(status, candidatoId, solicitacaoId);
	}
}