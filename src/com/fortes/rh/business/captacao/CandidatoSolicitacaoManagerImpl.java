package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.ComparatorString;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

@SuppressWarnings("unchecked")
public class CandidatoSolicitacaoManagerImpl extends GenericManagerImpl<CandidatoSolicitacao, CandidatoSolicitacaoDao> implements CandidatoSolicitacaoManager
{
    private HistoricoCandidatoManager historicoCandidatoManager;
    private Mail mail;
    private ParametrosDoSistemaManager parametrosDoSistemaManager;
    private ColaboradorManager colaboradorManager;//SpringUtil deu  PAUUUUU com o applicationContext

    public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager)
    {
        this.historicoCandidatoManager = historicoCandidatoManager;
    }

    public CandidatoSolicitacao findByCandidatoSolicitacao(CandidatoSolicitacao cand)
    {
        return getDao().findByCandidatoSolicitacao(cand);
    }

    public void insertCandidatos(String[] candidatosId, Solicitacao solicitacao)
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

                save(candidatoSol);
            }
        }
    }

    public void moverCandidatos(Long[] candidatosSolicitacaoId, Solicitacao solicitacao) throws ColecaoVaziaException
    {
        Collection lista = getDao().findCandidatosAptosMover(candidatosSolicitacaoId, solicitacao);
        if(lista.isEmpty())
        	throw new ColecaoVaziaException("Candidato selecionado ja esta na solicitacao");
        
        getDao().updateSolicitacaoCandidatos(solicitacao, lista);
    }

    public Collection<CandidatoSolicitacao> getCandidatosBySolicitacaoAberta(String[] etapaCheck, Long empresaId)
    {
        return getDao().findHistoricoAptoByEtapaSolicitacao(empresaId, LongUtil.arrayStringToArrayLong(etapaCheck));
    }

    private boolean isEtapaFiltro(HistoricoCandidato historico, String[] etapaCheck)
    {
        for (String id : etapaCheck)
        {
            if(historico.getEtapaSeletiva().getId().equals(Long.parseLong(id)))
                return true;
        }

        return false;
    }

    public Collection<CandidatoSolicitacao> getCandidatosBySolicitacao(Solicitacao solicitacao, ArrayList<Long> idCandidatosComHistoricos)
    {
        return getDao().getCandidatosBySolicitacao(solicitacao, idCandidatosComHistoricos);
    }

    public Collection<CandidatoSolicitacao> findNaoAptos(Long solicitacaoId)
    {
        return getDao().findNaoAptos(solicitacaoId);
    }

    public void enviarEmailNaoApto(Long solicitacaoId, Empresa empresa) throws Exception
    {
        ParametrosDoSistema parametrosDoSistema;
        Collection<CandidatoSolicitacao> candidatoSolicitacoes = new ArrayList<CandidatoSolicitacao>();
        candidatoSolicitacoes = getDao().findNaoAptos(solicitacaoId);

        String subject = "Solicitação de Candidatos";
        parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findAll().toArray()[0];
        String body = parametrosDoSistema.getMailNaoAptos();

        if (candidatoSolicitacoes != null  && StringUtils.isNotBlank(body))
        {
        	for (CandidatoSolicitacao candidatoSolicitacao : candidatoSolicitacoes)
	        {
    			if (StringUtils.isNotBlank(candidatoSolicitacao.getCandidato().getContato().getEmail()))
    				mail.send(empresa, subject, body, null, candidatoSolicitacao.getCandidato().getContato().getEmail());
	        }
        }
    }

    public void setMail(Mail mail)
    {
        this.mail = mail;
    }

    public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
    {
        this.parametrosDoSistemaManager = parametrosDoSistemaManager;
    }

    public Collection<CandidatoSolicitacao> verificaExisteColaborador(Collection<CandidatoSolicitacao> candidatoSolicitacaos, Long empresaId)
    {
        colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
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

    public Collection<CandidatoSolicitacao> getCandidatoSolicitacaoList(Integer page, Integer pagingSize, Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, boolean semHistorico, String observacaoRH, String nomeBusca)
	{
        return getDao().getCandidatoSolicitacaoList(page, pagingSize, solicitacaoId, etapaSeletivaId, indicadoPor, visualizar, contratado, semHistorico, observacaoRH, nomeBusca);
	}

	public Collection<CandidatoSolicitacao> findBySolicitacaoTriagem(Long solicitacaoId)
	{
		return getDao().findBySolicitacaoTriagem(solicitacaoId);
	}
	
	public Collection<CandidatoSolicitacao> findByFiltroSolicitacaoTriagem(Boolean triagen)
	{
		return getDao().findByFiltroSolicitacaoTriagem(triagen);
	}

	public void updateTriagem(Long candidatoSolicitacaoid, boolean triagem)
	{
		getDao().updateTriagem(candidatoSolicitacaoid, triagem);
	}

	public Collection<Long> getCandidatosBySolicitacao(Long solicitacaoId)
	{
		return getDao().getCandidatosBySolicitacao(solicitacaoId);
	}

	public Integer getCount(Long solicitacaoId, Long etapaSeletivaId, String indicadoPor, Boolean visualizar, boolean contratado, String observacaoRH, String nomeBusca)
	{
		return getDao().getCount(solicitacaoId, etapaSeletivaId, indicadoPor, visualizar, contratado, observacaoRH, nomeBusca);
	}
}