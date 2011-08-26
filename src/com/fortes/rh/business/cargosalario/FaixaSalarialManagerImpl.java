package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.ws.AcPessoalClientCargo;

@SuppressWarnings("unchecked")
public class FaixaSalarialManagerImpl extends GenericManagerImpl<FaixaSalarial, FaixaSalarialDao> implements FaixaSalarialManager
{
	private PlatformTransactionManager transactionManager;
	private AcPessoalClientCargo acPessoalClientCargo;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;

	@Override
	@Deprecated
	public Collection<FaixaSalarial> findAll()
	{
		throw new NoSuchMethodError("Deve considerar a empresa na listagem das Faixas Salariais.");
	}

	@Override
	@Deprecated
	public Collection<FaixaSalarial> findAll(String[] orderBy)
	{
		throw new NoSuchMethodError("Deve considerar a empresa na listagem das Faixas Salariais.");
	}

	public Collection<FaixaSalarial> findFaixaSalarialByCargo(Long cargoId)
	{
		return getDao().findFaixaSalarialByCargo(cargoId);
	}

	public void saveFaixaSalarial(FaixaSalarial faixaSalarial, FaixaSalarialHistorico faixaSalarialHistorico, Empresa empresa, String[] certificacaosCheck) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			CollectionUtil<Certificacao> util = new CollectionUtil<Certificacao>();
			faixaSalarial.setCertificacaos(util.convertArrayStringToCollection(Certificacao.class, certificacaosCheck));
			faixaSalarial = save(faixaSalarial);
			
			faixaSalarialHistorico = faixaSalarialHistoricoManager.save(faixaSalarialHistorico, faixaSalarial, empresa, false);

			if(empresa.isAcIntegra())
			{
				String codigoAC = acPessoalClientCargo.criarCargo(faixaSalarial, faixaSalarialHistorico, empresa);
				if (codigoAC == null || codigoAC.equals(""))
					throw new Exception("O Cargo não pôde ser cadastrado no AC Pessoal.");

				getDao().updateCodigoAC(codigoAC, faixaSalarial.getId());
			}

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}
	}

	public void updateFaixaSalarial(FaixaSalarial faixaSalarial, Empresa empresa, String[] certificacaosCheck) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		CollectionUtil<Certificacao> util = new CollectionUtil<Certificacao>();
		faixaSalarial.setCertificacaos(util.convertArrayStringToCollection(Certificacao.class, certificacaosCheck));
		update(faixaSalarial);

		if (empresa.isAcIntegra())
		{
			try
			{
				String codigoAC = acPessoalClientCargo.updateCargo(faixaSalarial, empresa);
				if (codigoAC == null)
				{
					throw new Exception();
				}
			}
			catch (Exception e)
			{
				transactionManager.rollback(status);
				throw new IntegraACException(e.getMessage());
			}
		}

		transactionManager.commit(status);
	}

	public void transfereFaixasCargo(FaixaSalarial faixaSalarial, Cargo cargoDestino, Empresa empresa) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			getDao().updateNomeECargo(faixaSalarial.getId(), cargoDestino.getId(), faixaSalarial.getNome());
			faixaSalarial.setNomeCargo(cargoDestino.getNome());
			
			if (empresa.isAcIntegra())
			{
				FaixaSalarial faixaTmp = getDao().findCodigoACById(faixaSalarial.getId());
				faixaSalarial.setCodigoAC(faixaTmp.getCodigoAC());
				acPessoalClientCargo.updateCargo(faixaSalarial, empresa);
			}
			
			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			throw new Exception("Não foi possível remover historicos desta Faixa Salarial.");
		}
	}

	public void deleteFaixaSalarial(Long faixaSalarialId, Empresa empresa) throws Exception
	{
		String codigoAc = "";

		try
		{
			// PRECISA RECUPERAR O CODIGOAC ANTES DE REALIZAR O DELETE !!!
			if (empresa.isAcIntegra())
				codigoAc = pegaCodigoAcDaFaixa(faixaSalarialId, empresa);

			faixaSalarialHistoricoManager.remove(pegaOsIdsDosHistoricosDaFaixa(faixaSalarialId));
			remove(faixaSalarialId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Não foi possível remover historicos desta Faixa Salarial.");
		}

		if (empresa.isAcIntegra() && !acPessoalClientCargo.deleteCargo(new String[]{codigoAc}, empresa))
			throw new Exception("Erro ao remover Faixa Salarial no AC Pessoal.");
	}

	private Long[] pegaOsIdsDosHistoricosDaFaixa(Long faixaSalarialId)
	{
		Collection<FaixaSalarialHistorico> historicos = faixaSalarialHistoricoManager.findAllSelect(faixaSalarialId);
		CollectionUtil<FaixaSalarialHistorico> faixaSalarialHistoricoUtil = new CollectionUtil<FaixaSalarialHistorico>();

		return faixaSalarialHistoricoUtil.convertCollectionToArrayIds(historicos);
	}

	private String pegaCodigoAcDaFaixa(Long id, Empresa empresa)
	{
		String codigoAc = null;

		FaixaSalarial faixaSalarial = new FaixaSalarial();
		faixaSalarial = findCodigoACById(id);
		codigoAc = faixaSalarial.getCodigoAC();

		return codigoAc;
	}

	public FaixaSalarial findCodigoACById(Long id)
	{
		return getDao().findCodigoACById(id);
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Collection<FaixaSalarial> findAllSelectByCargo(Long empresaId)
	{
		return getDao().findAllSelectByCargo(empresaId);
	}

	public void setAcPessoalClientCargo(AcPessoalClientCargo acPessoalClientCargo)
	{
		this.acPessoalClientCargo = acPessoalClientCargo;
	}

	public FaixaSalarial findByFaixaSalarialId(Long faixaSalarialId)
	{
		return getDao().findByFaixaSalarialId(faixaSalarialId);
	}

	public void setFaixaSalarialHistoricoManager(FaixaSalarialHistoricoManager faixaSalarialHistoricoManager)
	{
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}

	public Collection<FaixaSalarial> findFaixas(Empresa empresa, Boolean ativo)
	{
		return getDao().findFaixas(empresa, ativo);
	}

	public FaixaSalarial findHistoricoAtual(Long faixaSalarialId)
	{
		return getDao().findHistoricoAtual(faixaSalarialId, new Date());
	}

	public FaixaSalarial findHistorico(Long faixaSalarialId, Date dataHistorico)
	{
		return getDao().findHistoricoAtual(faixaSalarialId, dataHistorico);
	}

	public boolean verifyExistsNomeByCargo(Long cargoId, String faixaNome)
	{
		return getDao().verifyExistsNomeByCargo(cargoId, faixaNome);
	}

	public void removeFaixaAndHistoricos(Long[] faixaSalarialIds)
	{
		configuracaoNivelCompetenciaManager.removeByFaixas(faixaSalarialIds);
		faixaSalarialHistoricoManager.removeByFaixas(faixaSalarialIds);
		remove(faixaSalarialIds);
	}

	public FaixaSalarial findFaixaSalarialByCodigoAc(String faixaCodigoAC, String empresaCodigoAC, String grupoAC) throws Exception
	{
		FaixaSalarial faixaSalarial = getDao().findFaixaSalarialByCodigoAc(faixaCodigoAC, empresaCodigoAC, grupoAC);
		if(faixaSalarial == null || faixaSalarial.getId() == null)
			throw new Exception("Faixa não encontrada, codigoAC: " + faixaCodigoAC + " empresaCodigoAC: " + empresaCodigoAC + " grupoAC: " + grupoAC);
		
		return faixaSalarial;

	}

	public Map<Object, Object> findByCargo(String cargoId)
	{
		Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();
		
		if(StringUtils.isNotBlank(cargoId))
		{
			faixaSalarials = getDao().findByCargo(Long.parseLong(cargoId));
		}

		return new CollectionUtil<FaixaSalarial>().convertCollectionToMap(faixaSalarials,"getId","getDescricao");
	}

	public Collection<FaixaSalarial> findByCargo(Long cargoId)
	{
		return getDao().findByCargo(cargoId);
	}
	
	public Collection<Long> findByCargos(Collection<Long> cargoIds)
	{
		return getDao().findByCargos(cargoIds);
	}

	public void sincronizar(Map<Long, Long> cargoIds) {
		
		Map<Long, Long> faixaSalarialIds = new HashMap<Long, Long>();
		
		clonar(cargoIds, faixaSalarialIds);
		
		faixaSalarialHistoricoManager.sincronizar(faixaSalarialIds);
	}
	
	private void clonar(Map<Long, Long> cargoIds, Map<Long, Long> faixaSalarialIds) {
		
		for (Long cargoId : cargoIds.keySet())
		{
			Collection<FaixaSalarial> faixas = getDao().findByCargo(cargoId);
			
			for (FaixaSalarial faixaSalarial : faixas)
			{
				Long faixaOrigemId = faixaSalarial.getId();
				
				faixaSalarial.setId(null);
				faixaSalarial.setCodigoAC(null);
				faixaSalarial.setProjectionCargoId( cargoIds.get(cargoId) );
				
				getDao().save(faixaSalarial);
				
				faixaSalarialIds.put(faixaOrigemId, faixaSalarial.getId());
			}
		}
	}

	public FaixaSalarial montaFaixa(TCargo tCargo)
	{
		FaixaSalarial faixaSalarial = new FaixaSalarial();
		faixaSalarial.setNome(tCargo.getDescricao());
		faixaSalarial.setNomeACPessoal(tCargo.getDescricaoACPessoal());
		faixaSalarial.setCodigoAC(tCargo.getCodigo());
		
		return faixaSalarial;
	}

	public void updateAC(TCargo tCargo)
	{
		getDao().updateAC(tCargo);
	}

	public TCargo[] getFaixasAC() 
	{
		return getDao().getFaixasAC();
	}

	public void setConfiguracaoNivelCompetenciaManager(
			ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

}