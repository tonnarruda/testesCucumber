package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.RemoveCascadeException;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;
import com.fortes.rh.util.CollectionUtil;

@Component
public class EleicaoManagerImpl extends GenericManagerImpl<Eleicao, EleicaoDao> implements EleicaoManager
{
	@Autowired private PlatformTransactionManager transactionManager;
	@Autowired private ComissaoEleicaoManager comissaoEleicaoManager;
	@Autowired private CandidatoEleicaoManager candidatoEleicaoManager;
	@Autowired private EtapaProcessoEleitoralManager etapaProcessoEleitoralManager;
	@Autowired private ComissaoManager comissaoManager;

	@Autowired
	EleicaoManagerImpl(EleicaoDao eleicaoDao) {
		setDao(eleicaoDao);
	}
	
	public Collection<Eleicao> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}
	
	@Override
	public void update(Eleicao eleicao) {
		
		Eleicao eleicaoTmp = getDao().findByIdProjection(eleicao.getId());
		
		if (eleicaoTmp.getPosse().compareTo(eleicao.getPosse()) != 0)
		{
			// atualizando os prazos das etapas
			Collection<EtapaProcessoEleitoral> etapaProcessoEleitorals = etapaProcessoEleitoralManager.findAllSelect(null, eleicao.getId());
			etapaProcessoEleitoralManager.updatePrazos(etapaProcessoEleitorals, eleicao.getPosse());
		}
		
		super.update(eleicao);
	}

	public void updateVotos(Eleicao eleicao)
	{
		if(eleicao.getQtdVotoBranco() == null)
			eleicao.setQtdVotoBranco(0);
		if(eleicao.getQtdVotoNulo() == null)
			eleicao.setQtdVotoNulo(0);

		getDao().updateVotos(eleicao);
	}

	public Eleicao findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}
	
	public Eleicao montaAtaDaEleicao(Long eleicaoId) throws ColecaoVaziaException
	{
		Eleicao eleicao = getDao().findByIdProjection(eleicaoId);
		String textoAtaEleicao = eleicao.getTextoAtaEleicao();
		 
		eleicao = findImprimirResultado(eleicaoId);
		eleicao.setTextoAtaEleicao(textoAtaEleicao);
		
		return eleicao;
	}

	public Eleicao findImprimirResultado(Long eleicaoId) throws ColecaoVaziaException
	{
		Collection<CandidatoEleicao> candidatos = getDao().findImprimirResultado(eleicaoId);

		if (candidatos == null || candidatos.isEmpty())
		{
			throw new ColecaoVaziaException("Não existem candidatos para esta eleicao.");
		}

		//o objeto eleicao traz os votos brancos e nulos.
		Eleicao eleicao = ((CandidatoEleicao)candidatos.toArray()[0]).getEleicao();
		int somaVotos=0;
		for (CandidatoEleicao candidatoEleicao : candidatos)
		{
			somaVotos+=candidatoEleicao.getQtdVoto();
		}
		somaVotos += eleicao.getQtdVotoBranco() + eleicao.getQtdVotoNulo();
		eleicao.setSomaVotos(somaVotos);

		setPercentualVoto(candidatos, eleicao);

		CollectionUtil<CandidatoEleicao> collectionUtil = new CollectionUtil<CandidatoEleicao>();
		candidatos = collectionUtil.sortCollectionDesc(candidatos, "qtdVoto");

		eleicao.setCandidatoEleicaos(candidatos);
		return eleicao;
	}

	/**
	 * A partir do objeto eleicao, prepara a coleção de candidatos para apresentar no gráfico
	 * São setados os votos brancos e nulos, além de "outros" para os candidatos cujos percentuais somam até 10%
	 */
	public Collection<CandidatoEleicao> setCandidatosGrafico(Eleicao eleicao)
	{
		CollectionUtil<CandidatoEleicao> collectionUtil = new CollectionUtil<CandidatoEleicao>();

		Collection<CandidatoEleicao> retorno = new ArrayList<CandidatoEleicao>();
		Collection<CandidatoEleicao> colecao = new ArrayList<CandidatoEleicao>();
		colecao.addAll(eleicao.getCandidatoEleicaos()); // colecao auxiliar ordenada
		colecao = collectionUtil.sortCollection(colecao, "qtdVoto");

		Integer somaVotoOutros=0;
		double somaPercentualVoto = 0.0;
		for (CandidatoEleicao candidatoEleicao : colecao)
		{
			double somaTmp = somaPercentualVoto + candidatoEleicao.getPercentualVoto();
			if (somaTmp <= 10.0)//agrupa até 10%, dentro de OUTROS ao exibir o grafico
			{
				somaVotoOutros+=candidatoEleicao.getQtdVoto();
				somaPercentualVoto = somaTmp;
			}
			else
			{
				retorno.add(candidatoEleicao);
			}
		}

		// Se alguem somar menos que 10%
		if (retorno.size() < colecao.size())
		{
			CandidatoEleicao outros = new CandidatoEleicao();
			outros.setProjectionCandidatoNome("OUTROS");
			outros.setProjectionCandidatoNomeComercial("OUTROS");
			outros.setPercentualVoto(somaPercentualVoto);
			outros.setQtdVoto(somaVotoOutros);
			double percentualVoto = (outros.getQtdVoto().doubleValue()/eleicao.getSomaVotos().doubleValue())*100;
			outros.setPercentualVoto(percentualVoto);
			retorno.add(outros);
		}

		setBrancosNulos(retorno, eleicao);

		return retorno;
	}

	private void setPercentualVoto(Collection<CandidatoEleicao> candidatos, Eleicao eleicao)
	{
		if (eleicao.getSomaVotos() > 0)
		{
			for (CandidatoEleicao candidatoEleicao : candidatos)
			{
				double percentualVoto = 0.0;
				percentualVoto = (candidatoEleicao.getQtdVoto().doubleValue()/eleicao.getSomaVotos().doubleValue())*100;
				candidatoEleicao.setPercentualVoto(percentualVoto);
			}
		}
	}

	private void setBrancosNulos(Collection<CandidatoEleicao> candidatos, Eleicao eleicao)
	{
		CandidatoEleicao candidatoEleicaoNulos = new CandidatoEleicao();
		candidatoEleicaoNulos.setProjectionCandidatoNome("NULOS");
		candidatoEleicaoNulos.setProjectionCandidatoNomeComercial("NULOS");
		candidatoEleicaoNulos.setQtdVoto(eleicao.getQtdVotoNulo());
		CandidatoEleicao candidatoEleicaoBrancos = new CandidatoEleicao();
		candidatoEleicaoBrancos.setProjectionCandidatoNome("BRANCOS");
		candidatoEleicaoBrancos.setProjectionCandidatoNomeComercial("BRANCOS");
		candidatoEleicaoBrancos.setQtdVoto(eleicao.getQtdVotoBranco());

		//percentual
		double percentualVotoNulos = (candidatoEleicaoNulos.getQtdVoto().doubleValue()/eleicao.getSomaVotos().doubleValue())*100;
		candidatoEleicaoNulos.setPercentualVoto(percentualVotoNulos);
		double percentualVotoBrancos = (candidatoEleicaoBrancos.getQtdVoto().doubleValue()/eleicao.getSomaVotos().doubleValue())*100;
		candidatoEleicaoBrancos.setPercentualVoto(percentualVotoBrancos);

		candidatos.add(candidatoEleicaoNulos);
		candidatos.add(candidatoEleicaoBrancos);
	}

	public void removeCascade(Long id) throws Exception
	{
		// verificar se existe Comissão ou Candidatos associados à eleição
		if (comissaoManager.getCount(new String[] {"eleicao.id"}, new Object[]{id}) >= 1)
		{
			throw new RemoveCascadeException("Não é possível excluir a eleição, pois existe uma comissão associada.");
		}

		if (candidatoEleicaoManager.getCount(new String[] {"eleicao.id"}, new Object[]{id}) >= 1)
		{
			throw new RemoveCascadeException("Não é possível excluir a eleição, pois existem candidatos inscritos.");
		}

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			comissaoEleicaoManager.removeByEleicao(id);
			etapaProcessoEleitoralManager.removeByEleicao(id);
			remove(id);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw e;
		}
	}
	
	public Collection<ParticipacaoColaboradorCipa> getParticipacoesDeColaboradorEmEleicoes(Long colaboradorId)
	{
		Collection<CandidatoEleicao> candidatoEleicaos = candidatoEleicaoManager.findByColaborador(colaboradorId);
		
		Collection<ParticipacaoColaboradorCipa> participacoes = new ArrayList<ParticipacaoColaboradorCipa>();
		
		for (CandidatoEleicao candidatoEleicao : candidatoEleicaos)
		{
			participacoes.add( new ParticipacaoColaboradorCipa(candidatoEleicao) );
		}
		
		return participacoes;
	}
}