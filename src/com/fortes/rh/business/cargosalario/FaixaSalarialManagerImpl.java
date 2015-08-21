package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientCargo;

@SuppressWarnings("unchecked")
public class FaixaSalarialManagerImpl extends GenericManagerImpl<FaixaSalarial, FaixaSalarialDao> implements FaixaSalarialManager
{
	private PlatformTransactionManager transactionManager;
	private AcPessoalClientCargo acPessoalClientCargo;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;

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
			getDao().saveOrUpdate(faixaSalarial);
			
			faixaSalarialHistorico = faixaSalarialHistoricoManager.save(faixaSalarialHistorico, faixaSalarial, empresa, false);

			if(empresa.isAcIntegra())
			{
				String codigoAC = acPessoalClientCargo.criarCargo(faixaSalarial, faixaSalarialHistorico, empresa);
				if (codigoAC == null || codigoAC.equals(""))
					throw new Exception("O cargo não pôde ser cadastrado no Fortes Pessoal. <br>Possíveis Motivos: <br>&nbsp&nbsp&nbsp- Cargo existente com a mesma descrição no Fortes Pessoal. <br>&nbsp&nbsp&nbsp- Limite de cadastros de cargos excedido no Fortes Pessoal.");

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
				faixaSalarial.setNomeACPessoal(faixaTmp.getNomeACPessoal());
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
			throw new Exception("Erro ao remover Faixa Salarial no Fortes Pessoal.");
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

	public Collection<FaixaSalarial> findFaixas(Empresa empresa, Boolean ativo, Long faixaInativaId)
	{
		return getDao().findFaixas(empresa, ativo, faixaInativaId);
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

	public void removeFaixaAndHistoricos(Long[] faixaSalarialIds) throws Exception
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
	
	public Collection<FaixaSalarial> findByCargoComCompetencia(Long cargoId) throws Exception
	{
		return getDao().findByCargoComCompetencia(cargoId);
	}
	
	public void sincronizar(Long cargoOrigemId, Cargo cargoDestino, Empresa empresaDestino) throws Exception 
	{
		Collection<FaixaSalarial> faixas = getDao().findByCargo(cargoOrigemId);
		
		for (FaixaSalarial faixaSalarial : faixas)
		{
			Long faixaOrigemId = faixaSalarial.getId();
			
			faixaSalarial.setId(null);
			faixaSalarial.setCodigoAC(null);
			faixaSalarial.setProjectionCargoId( cargoDestino.getId() );

			if(empresaDestino.isAcIntegra() && (faixaSalarial.getNomeACPessoal() == null || faixaSalarial.getNomeACPessoal().equals("")))
				faixaSalarial.setNomeACPessoal(cargoDestino.getNome() + " " + faixaSalarial.getNome());
			
			getDao().save(faixaSalarial);

			FaixaSalarialHistorico faixaSalarialHistoricoAtualClonado = faixaSalarialHistoricoManager.sincronizar(faixaOrigemId, faixaSalarial.getId(), empresaDestino);

			if(empresaDestino.isAcIntegra())
			{
				faixaSalarialHistoricoAtualClonado.setFaixaSalarial(faixaSalarial);
	
				String codigoAC = acPessoalClientCargo.criarCargo(faixaSalarialHistoricoAtualClonado.getFaixaSalarial(), faixaSalarialHistoricoAtualClonado, empresaDestino);
				if (codigoAC == null || codigoAC.equals(""))
					throw new IntegraACException();
				
				getDao().updateCodigoAC(codigoAC, faixaSalarial.getId());
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

	public void deleteFaixaSalarial(Long[] faixaIds) throws Exception {
		
		if (faixaIds != null && faixaIds.length > 0) {
			CertificacaoManager certificacaoManager = (CertificacaoManager) SpringUtil.getBean("certificacaoManager");
			faixaSalarialHistoricoManager.deleteByFaixaSalarial(faixaIds);
			certificacaoManager.deleteByFaixaSalarial(faixaIds);
			configuracaoNivelCompetenciaColaboradorManager.deleteByFaixaSalarial(faixaIds);
			configuracaoNivelCompetenciaFaixaSalarialManager.deleteByFaixaSalarial(faixaIds);
			configuracaoNivelCompetenciaManager.removeByFaixas(faixaIds);
			
			getDao().remove(faixaIds);
		}
		
	}

	public void updateAC(TCargo tCargo)
	{
		getDao().updateAC(tCargo);
	}

	public TCargo[] getFaixasAC() 
	{
		return getDao().getFaixasAC();
	}
	
	public Collection<FaixaSalarial> findSemCodigoAC(Long empresaId) 
	{
		return getDao().findSemCodigoAC(empresaId);
	}
	
	public String findCodigoACDuplicado(Long empresaId) 
	{
		return getDao().findCodigoACDuplicado(empresaId);
	}

	public Collection<FaixaSalarial> findByCargos(Long[] cargosIds) 
	{
		return getDao().findByCargos(cargosIds);
	}

	public Collection<FaixaSalarial> findComHistoricoAtual(	Long[] faixasSalariaisIds) 
	{
		return getDao().findComHistoricoAtual(faixasSalariaisIds);
	}

	public Collection<FaixaSalarial> findComHistoricoAtualByEmpresa(Long empresaId, boolean semCodigoAC) 
	{
		return getDao().findComHistoricoAtualByEmpresa(empresaId, semCodigoAC);
	}

	public Collection<FaixaSalarial> relatorioColaboradoresPorCargoResumidoXLS(boolean exibirEstabelecimento, boolean exibirAreaOrganizacional, Long... empresasIds){
		return getDao().colaboradoresPorCargoFaixa(exibirEstabelecimento, exibirAreaOrganizacional, empresasIds);
	}
	
	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager)
	{
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}
}