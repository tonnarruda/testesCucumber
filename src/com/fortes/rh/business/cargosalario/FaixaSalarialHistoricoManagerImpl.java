package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FaixaJaCadastradaException;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistoricoVO;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.ws.TSituacaoCargo;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientCargo;

public class FaixaSalarialHistoricoManagerImpl extends GenericManagerImpl<FaixaSalarialHistorico, FaixaSalarialHistoricoDao> implements FaixaSalarialHistoricoManager
{
	IndiceHistoricoManager indiceHistoricoManager;
	IndiceManager indiceManager;
	private AcPessoalClientCargo acPessoalClientCargo;
	private PlatformTransactionManager transactionManager;

	public void setIndiceHistoricoManager(IndiceHistoricoManager indiceHistoricoManager)
	{
		this.indiceHistoricoManager = indiceHistoricoManager;
	}

	public FaixaSalarialHistorico save(FaixaSalarialHistorico faixaSalarialHistorico, FaixaSalarial faixaSalarial, Empresa empresa, boolean salvaNoAC) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			prepareSaveUpdate(faixaSalarialHistorico, faixaSalarial, empresa);
			getDao().saveOrUpdate(faixaSalarialHistorico);

			if(empresa.isAcIntegra() && salvaNoAC)
			{
				if(faixaSalarial != null && faixaSalarial.getId() != null && faixaSalarial.getCodigoAC() == null)
				{
					FaixaSalarialManager faixaSalarialManager = (FaixaSalarialManager) SpringUtil.getBean("faixaSalarialManager");
					faixaSalarial = faixaSalarialManager.findCodigoACById(faixaSalarial.getId());
					faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
				}

				acPessoalClientCargo.criarFaixaSalarialHistorico(faixaSalarialHistorico, empresa);
			}

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}

		return faixaSalarialHistorico;
	}

	public void criarFaixaSalarialHistoricoNoAc(FaixaSalarialHistorico faixaSalarialHistorico, Empresa empresa) throws Exception
	{
		acPessoalClientCargo.criarFaixaSalarialHistorico(faixaSalarialHistorico, empresa);
	}
	
	public void update(FaixaSalarialHistorico faixaSalarialHistorico, FaixaSalarial faixaSalarial, Empresa empresa) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			if(empresa.isAcIntegra())
			{
				if(faixaSalarial != null && faixaSalarial.getId() != null && faixaSalarial.getCodigoAC() == null)
				{
					FaixaSalarialManager faixaSalarialManager = (FaixaSalarialManager) SpringUtil.getBean("faixaSalarialManager");
					faixaSalarial = faixaSalarialManager.findCodigoACById(faixaSalarial.getId());
				}
			}

			prepareSaveUpdate(faixaSalarialHistorico, faixaSalarial, empresa);
			getDao().update(faixaSalarialHistorico);

			if(empresa.isAcIntegra())
				acPessoalClientCargo.criarFaixaSalarialHistorico(faixaSalarialHistorico, empresa);

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}
	}

	private void prepareSaveUpdate(FaixaSalarialHistorico faixaSalarialHistorico, FaixaSalarial faixaSalarial, Empresa empresa)
	{
		if(empresa.isAcIntegra())
		{
			faixaSalarialHistorico.setStatus(StatusRetornoAC.AGUARDANDO);

			if(faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.INDICE)
				faixaSalarialHistorico.setIndice(indiceManager.findByIdProjection(faixaSalarialHistorico.getIndice().getId()));
		}
		else
			faixaSalarialHistorico.setStatus(StatusRetornoAC.CONFIRMADO);

		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		switch (faixaSalarialHistorico.getTipo())
		{
			case TipoAplicacaoIndice.INDICE:
				faixaSalarialHistorico.setValor(null);
				break;
			case TipoAplicacaoIndice.VALOR:
				faixaSalarialHistorico.setQuantidade(0.0);
				faixaSalarialHistorico.setIndice(null);
				break;
		}
	}

	public Collection<FaixaSalarialHistorico> findAllSelect(Long faixaSalarialId)
	{
		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = getDao().findAllSelect(faixaSalarialId);

		// Atualiza o valor do último histórico do índice para o valor na data de hoje, desconsiderando o valor da data do histórico da faixa.
		if(faixaSalarialHistoricos.size() > 0)
		{
			FaixaSalarialHistorico faixaSalarialHistorico = (FaixaSalarialHistorico)(faixaSalarialHistoricos.toArray())[faixaSalarialHistoricos.size()-1];
			faixaSalarialHistorico.getIndice().getIndiceHistoricoAtual().setValor(faixaSalarialHistorico.getIndice().getIndiceHistoricoAtual().getValorAtual());
		}

		return faixaSalarialHistoricos;
	}
	
	public Collection<FaixaSalarialHistoricoVO> findAllComHistoricoIndice(Long faixaSalarialId)
	{
		return getDao().findAllComHistoricoIndice(faixaSalarialId);
	}

	public FaixaSalarialHistorico findByIdProjection(Long faixaSalarialHistoricoId)
	{
		return getDao().findByIdProjection(faixaSalarialHistoricoId);
	}

	public boolean verifyData(Long faixaSalarialHistoricoId, Date data, Long faixaSalarialId)
	{
		return getDao().verifyData(faixaSalarialHistoricoId, data, faixaSalarialId);
	}

	public Double findUltimoHistoricoFaixaSalarial(Long faixaSalarialId)
	{
		FaixaSalarialHistorico faixaSalarialHistorico = findByHistoricoFaixaSalarial(faixaSalarialId);

		if (faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.getIndice())
		{
			Double valor = indiceHistoricoManager.findUltimoSalarioIndice(faixaSalarialHistorico.getIndice().getId());

			return valor * faixaSalarialHistorico.getQuantidade();
		}
		else if (faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.getValor())
		{
			return faixaSalarialHistorico.getValor();
		}
		else
		{
			return null;
		}
	}

	public FaixaSalarialHistorico findByHistoricoFaixaSalarial(Long faixaSalarialId)
	{
		return getDao().findByFaixaSalarialId(faixaSalarialId);
	}

	// TODO Método muito complexo, analisar a possiblidade de refatoração.
	public Collection<FaixaSalarialHistorico> findByPeriodo(Long faixaSalarialId, Date data, Date dataProxima)
	{
		List<FaixaSalarialHistorico> faixaSalarialHistoricos = (List<FaixaSalarialHistorico>) getDao().findByPeriodo(faixaSalarialId, dataProxima);
		Collection<FaixaSalarialHistorico> retorno = new ArrayList<FaixaSalarialHistorico>();

		int proximo = 1;
		for (FaixaSalarialHistorico faixaSalarialHistoricoTmp : faixaSalarialHistoricos)
		{
			switch (faixaSalarialHistoricoTmp.getTipo())
			{
				case TipoAplicacaoIndice.VALOR:
				{
					retorno.add(faixaSalarialHistoricoTmp);
					break;
				}
				case TipoAplicacaoIndice.INDICE:
				{
					Date dataProximo = null;
					if(proximo == faixaSalarialHistoricos.size())
						dataProximo = new Date();
					else
						dataProximo = faixaSalarialHistoricos.get(proximo).getData();

					if(dataProxima.before(dataProximo))
						dataProximo = dataProxima;

					Collection<IndiceHistorico> indiceHistoricos = indiceHistoricoManager.findByPeriodo(faixaSalarialHistoricoTmp.getIndice().getId(), faixaSalarialHistoricoTmp.getData(), dataProximo);

					retorno.add(faixaSalarialHistoricoTmp);
					for (IndiceHistorico indiceHistorico: indiceHistoricos)
					{
						if(indiceHistorico.getData().before(data))
							continue;

						FaixaSalarialHistorico faixaSalarialHistoricoClone = (FaixaSalarialHistorico) faixaSalarialHistoricoTmp.clone();
						faixaSalarialHistoricoClone.setData(indiceHistorico.getData());
						faixaSalarialHistoricoClone.getIndice().setIndiceHistoricoAtual(indiceHistorico);
						faixaSalarialHistoricoClone.setObsReajuste("Reajuste do Índice da Faixa");

						retorno.add(faixaSalarialHistoricoClone);
					}

					break;
				}
			}

			proximo++;
		}

		return retorno;
	}

	public Collection<FaixaSalarialHistorico> findByGrupoCargoAreaData(String[] grupoOcupacionalsCheck, String[] cargosCheck, String[] areasCheck, Date data, boolean ordemDataDescendente, Long empresaId, Boolean cargoAtivo) throws Exception
	{
		Collection<Long> grupoOcupacionalIds = LongUtil.arrayStringToCollectionLong(grupoOcupacionalsCheck);
		Collection<Long> cargoIds = LongUtil.arrayStringToCollectionLong(cargosCheck);
		Collection<Long> areaIds = LongUtil.arrayStringToCollectionLong(areasCheck);
		
		Collection<FaixaSalarialHistorico> retorno = getDao().findByGrupoCargoAreaData(grupoOcupacionalIds, cargoIds, areaIds, data, ordemDataDescendente, empresaId, cargoAtivo);
		
		if(retorno.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");

		return retorno;
	}

	public boolean verifyHistoricoIndiceNaData(Date data, Long indiceId)
	{
		return indiceHistoricoManager.existeHistoricoAnteriorOuIgualDaData(data, indiceId);
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public boolean setStatus(Long faixaSalarialHistoricoId, boolean aprovado)
	{
		return getDao().setStatus(faixaSalarialHistoricoId, aprovado);
	}

	public void setAcPessoalClientCargo(AcPessoalClientCargo acPessoalClientCargo)
	{
		this.acPessoalClientCargo = acPessoalClientCargo;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public void remove(Long faixaSalarialHistoricoId, Empresa empresa) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			if(empresa.isAcIntegra())
				acPessoalClientCargo.deleteFaixaSalarialHistorico(faixaSalarialHistoricoId, empresa);

			remove(faixaSalarialHistoricoId);

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}
	}

	public void removeByFaixas(Long[] faixaSalarialIds)
	{
		getDao().removeByFaixas(faixaSalarialIds);
	}

	public Collection<PendenciaAC> findPendenciasByFaixaSalarialHistorico(Long empresaId)
	{
		Collection<PendenciaAC> pendenciaACs = new ArrayList<PendenciaAC>();

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = getDao().findPendenciasByFaixaSalarialHistorico(empresaId);

		for (FaixaSalarialHistorico faixaSalarialHistorico : faixaSalarialHistoricos)
		{
			PendenciaAC pendenciaAC = new PendenciaAC();

			pendenciaAC.setPendencia("Novo Histórico de Faixa");
			pendenciaAC.setDetalhes("Cadastro do histórico da faixa salarial "+faixaSalarialHistorico.getFaixaSalarial().getNome()+ " do cargo "+faixaSalarialHistorico.getFaixaSalarial().getCargo().getNome());
			pendenciaAC.setStatus(faixaSalarialHistorico.getStatus());
			pendenciaAC.setRole("ROLE_CAD_CARGO");
			pendenciaAC.setLinkExcluir("newConfirm('Confirma exclusão da pendência do novo histórico da faixa salarial?', function(){window.location='removePendenciaACHistoricoFaixaSalarial.action?faixaSalarialHistoricoId="+ faixaSalarialHistorico.getId() + "'});");

			pendenciaACs.add(pendenciaAC);
		}

		return pendenciaACs;
	}

	public FaixaSalarialHistorico sincronizar(Long faixaSalarialOrigemId, Long faixaSalarialDestinoId, Empresa empresaDestino) throws FaixaJaCadastradaException
	{
		FaixaSalarialHistorico faixaSalarialHistorico = getDao().findHistoricoAtual(faixaSalarialOrigemId);

		if(faixaSalarialHistorico == null)
			throw new FaixaJaCadastradaException();
		
		faixaSalarialHistorico.setId(null);
		faixaSalarialHistorico.setIndice(null);
		faixaSalarialHistorico.setProjectionFaixaSalarialId(faixaSalarialDestinoId);

		prepareSaveUpdate(faixaSalarialHistorico, faixaSalarialHistorico.getFaixaSalarial(), empresaDestino);
		getDao().save(faixaSalarialHistorico);
		
		return faixaSalarialHistorico;
	}

	public FaixaSalarialHistorico bind(TSituacaoCargo tSituacaoCargo, FaixaSalarial faixaSalarial)
	{
		FaixaSalarialHistorico faixaSalarialHistorico = new FaixaSalarialHistorico();
		faixaSalarialHistorico.setData(tSituacaoCargo.getDataFormatada());
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistorico.setReajusteFaixaSalarial(getDao().findReajusteFaixaSalarial(tSituacaoCargo.getDataFormatada(), faixaSalarial.getId()));

		if(tSituacaoCargo.getTipo().equalsIgnoreCase("V"))
		{
			faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
			faixaSalarialHistorico.setValor(tSituacaoCargo.getValor());
			faixaSalarialHistorico.setQuantidade(0.0);
			faixaSalarialHistorico.setIndice(null);
		}
		else if(tSituacaoCargo.getTipo().equalsIgnoreCase("I"))
		{
			faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
			faixaSalarialHistorico.setQuantidade(tSituacaoCargo.getQuantidade());
			faixaSalarialHistorico.setValor(null);
			
			Indice indice = indiceManager.findByCodigo(tSituacaoCargo.getIndiceCodigo(), tSituacaoCargo.getGrupoAC());
			faixaSalarialHistorico.setIndice(indice);
		}
		
		return faixaSalarialHistorico;
	}

	public Long findIdByDataFaixa(FaixaSalarialHistorico faixaSalarialHistorico)
	{
		return getDao().findIdByDataFaixa(faixaSalarialHistorico);
	}

	public void deleteByFaixaSalarial(Long[] faixaIds) throws Exception 
	{
		getDao().deleteByFaixaSalarial(faixaIds);
	}

	public Collection<FaixaSalarialHistorico> findByTabelaReajusteId(Long tabelaReajusteColaboradorId) 
	{
		return getDao().findByTabelaReajusteId(tabelaReajusteColaboradorId);
	}

	public Collection<FaixaSalarialHistorico> findByTabelaReajusteIdData(Long tabelaReajusteColaboradorId, Date data)
	{
		return getDao().findByTabelaReajusteIdData(tabelaReajusteColaboradorId, data);
	}

	public boolean existeHistoricoPorIndice(Long empresaId) 
	{
		return getDao().existeHistoricoPorIndice(empresaId);
	}

	public boolean existeDependenciaComHistoricoIndice(Date dataHistoricoExcluir, Long indiceId)
	{
		Collection<IndiceHistorico> indiceHistoricos = indiceHistoricoManager.findToList(new String[]{"data"}, new String[]{"data"}, new String[]{"indice.id"}, new Long[]{indiceId}, 1, 2, new String[]{"data"});
		
		if(indiceHistoricos.size() == 1)
			return getDao().existeDependenciaComHistoricoIndice(dataHistoricoExcluir, null, indiceId);
		else if(indiceHistoricos.size() > 1)
			return getDao().existeDependenciaComHistoricoIndice(dataHistoricoExcluir, ((IndiceHistorico) indiceHistoricos.toArray()[1]).getData(), indiceId);
		
		return false;
	}
}