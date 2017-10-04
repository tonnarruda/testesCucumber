package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientCargo;
import com.fortes.web.tags.CheckBox;

public class CargoManagerImpl extends GenericManagerImpl<Cargo, CargoDao> implements CargoManager
{
	private AcPessoalClientCargo acPessoalClientCargo;
	private EmpresaManager empresaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private AreaFormacaoManager areaFormacaoManager;
	private ConhecimentoManager conhecimentoManager;
	private HabilidadeManager habilidadeManager;
	private AtitudeManager atitudeManager;
	private FaixaSalarialManager faixaSalarialManager;
	private EtapaSeletivaManager etapaSeletivaManager;
	private ExperienciaManager experienciaManager;
	private PlatformTransactionManager transactionManager;

	public Integer getCount(Long empresaId, Long areaId, String cargoNome, Boolean ativo)
	{
		return getDao().getCount(empresaId, areaId, cargoNome, ativo);
	}

	public Collection<Cargo> findCargos(int page, int pagingSize, Long empresaId, Long areaId, String cargoNome, Boolean ativo, boolean verificarSePossuiFaixaSalarial)
	{
		return getDao().findCargos(page, pagingSize, empresaId, areaId, cargoNome, ativo, verificarSePossuiFaixaSalarial);
	}

	public Collection<Cargo> findByGrupoOcupacionalIdsProjection(Long[] idsLong, Long empresaId, Boolean cargoAtivo)
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();

		if(idsLong != null && idsLong.length > 0)
			cargos = getDao().findByGrupoOcupacionalIdsProjection(idsLong, empresaId, cargoAtivo);
		else
			cargos = getDao().findByGrupoOcupacionalIdsProjection(null, empresaId, cargoAtivo);

		return cargos;
	}
	
	public Collection<Cargo> findByAreasAndGrupoOcapcinal(Long empresaId, Long[] grupoOcupacionaisIds, Boolean cargoAtivo, Long... areasOrganizacionalIds) 
	{
		return getDao().findByAreasAndGrupoOcapcinal(empresaId, grupoOcupacionaisIds, cargoAtivo, areasOrganizacionalIds);
	}
	
	public Collection<Cargo> findByAreasOrganizacionalIdsProjection(Long[] idsLong, Long empresaId)
	{
		Collection<Cargo> cargos;

		if(idsLong != null && idsLong.length > 0)
			cargos = getDao().findByAreaOrganizacionalIdsProjection(idsLong, empresaId);
		else
			cargos = getDao().findByAreaOrganizacionalIdsProjection(null, empresaId);

		return cargos;
	}

	public Collection<Cargo> getCargosByIds(Long[] cargoIds, Long empresaId) throws Exception
	{
		FaixaSalarialManager faixaSalarialManager = (FaixaSalarialManager) SpringUtil.getBean("faixaSalarialManager");
		
		Collection<Cargo> cargos = getDao().findCargosByIds(cargoIds, empresaId);
		
		for (Cargo cargo : cargos)
		{
			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findByCargo(cargo.getId());
			cargo.setAreasOrganizacionais(areaOrganizacionals);
			
			Collection<AreaFormacao> areaFormacaos = areaFormacaoManager.findByCargo(cargo.getId());
			cargo.setAreaFormacaos(areaFormacaos);

			Collection<EtapaSeletiva> EtapaSeletivas = etapaSeletivaManager.findByCargo(cargo.getId());
			cargo.setEtapaSeletivas(EtapaSeletivas);
			
			Collection<Conhecimento> conhecimentos = conhecimentoManager.findByCargo(cargo.getId());
			cargo.setConhecimentos(conhecimentos);
			
			Collection<Habilidade> habilidades = habilidadeManager.findByCargo(cargo.getId());
			cargo.setHabilidades(habilidades);
			
			Collection<Atitude> atitudes = atitudeManager.findByCargo(cargo.getId());
			cargo.setAtitudes(atitudes);
			
			Collection<FaixaSalarial> faixaSalarials = faixaSalarialManager.findByCargoComCompetencia(cargo.getId());
			cargo.setFaixaSalarials(faixaSalarials);
		}

		return cargos;
	}
	
	public Collection<Cargo> findAllSelect(String ordenarPor, Boolean exibirModuloExterno, Boolean ativo, Long... empresaIds)
	{
		return getDao().findAllSelect(empresaIds, ordenarPor, exibirModuloExterno, ativo);
	}

	public Cargo findByIdProjection(Long cargoId)
	{
		return getDao().findByIdProjection(cargoId);
	}
	
	public Collection<Cargo> findCollectionByIdProjection(Long... cargoId)
	{
		return getDao().findCollectionByIdProjection(cargoId);
	}

	public Collection<Cargo> populaCargos(String[] cargosCheck)
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();

		if (cargosCheck != null && cargosCheck.length > 0)
		{
			for (int i = 0; i < cargosCheck.length; i++)
			{
				Cargo cargo = new Cargo();
				cargo.setId(Long.valueOf(cargosCheck[i]));
				cargos.add(cargo);
			}
		}

		return cargos;
	}

	public Collection<Cargo> populaCargos(Long[] ids)
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();

		if (ids != null && ids.length > 0)
		{
			for (int i = 0; i < ids.length; i++)
			{
				Cargo cargo = new Cargo();
				cargo.setId(ids[i]);
				cargos.add(cargo);
			}
		}

		return cargos;
	}

	public Cargo findByIdAllProjection(Long cargoId)
	{
		return getDao().findByIdAllProjection(cargoId);
	}

	public Collection<CheckBox> populaCheckBox(boolean exibirNomeEmpresa, Long... empresaIds)
	{
		String getCheckList = "getNomeMercadoComStatus";
		if(exibirNomeEmpresa)
			getCheckList = "getNomeMercadoComEmpresaEStatus";
		
		try
		{
			Collection<Cargo> cargosTmp = getDao().findAllSelect(empresaIds);
			return CheckListBoxUtil.populaCheckListBox(cargosTmp, "getId", getCheckList, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ArrayList<CheckBox>();
	}

	public Collection<CheckBox> populaCheckBox(String[] gruposCheck, String[] cargosCheck, Long empresaId) throws Exception
	{
		if(gruposCheck == null || gruposCheck.length == 0)
		{
			return populaCheckBox(false, empresaId);
		}
		else
		{
			Long [] gruposIds = LongUtil.arrayStringToArrayLong(gruposCheck);
			Collection<Cargo> cargos = getDao().findByGrupoOcupacionalIdsProjection(gruposIds, empresaId, null);

			Collection<CheckBox> checks = CheckListBoxUtil.populaCheckListBox(cargos, "getId", "getNomeMercadoComStatus", null);

			return CheckListBoxUtil.marcaCheckListBox(checks, cargosCheck);
		}

	}

	public Collection<Cargo> getCargosFromFaixaSalarialHistoricos(Collection<FaixaSalarialHistorico> faixaSalarialHistoricos)
	{
		Long cargoId = 0L;
		Collection<Cargo> cargos = new ArrayList<Cargo>();

		for (FaixaSalarialHistorico historico : faixaSalarialHistoricos)
		{
			if(!historico.getFaixaSalarial().getCargo().getId().equals(cargoId))
				cargos.add(historico.getFaixaSalarial().getCargo());

			cargoId = historico.getFaixaSalarial().getCargo().getId();
		}

		return cargos;
	}

	public void remove(Long cargoId, Empresa empresa) throws Exception
	{
		Collection<FaixaSalarial> faixasSalariais = removeFaixasSalariais(cargoId);
		removeDependencias(cargoId);
		super.remove(cargoId);
		
		// Flush necessário quando houver uma operação com banco/sistema externo.
		// Isso garante que qualquer erro relacionado ao banco do RH levantará uma Exception antes de alterarmos o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush();

		if(empresa.isAcIntegra() && faixasSalariais.size() > 0){
			CollectionUtil<FaixaSalarial> faixaSalarialUtil = new CollectionUtil<FaixaSalarial>();			
			acPessoalClientCargo.deleteCargo(faixaSalarialUtil.convertCollectionToArrayString(faixasSalariais, "getCodigoAC"), empresa);
		}
	}
	
	private Collection<FaixaSalarial> removeFaixasSalariais(Long cargoId) throws Exception
	{
		FaixaSalarialManager faixaSalarialManager = (FaixaSalarialManager) SpringUtil.getBean("faixaSalarialManager");
		Collection<FaixaSalarial> faixasSalariais = faixaSalarialManager.findFaixaSalarialByCargo(cargoId);

		CollectionUtil<FaixaSalarial> faixaSalarialUtil = new CollectionUtil<FaixaSalarial>();
		if(faixasSalariais.size() > 0)
			faixaSalarialManager.removeFaixaAndHistoricos(faixaSalarialUtil.convertCollectionToArrayIds(faixasSalariais));
		
		return faixasSalariais;
	}

	private void removeDependencias(Long cargoId)
	{
		Cargo cargo = findByIdProjection(cargoId);
		experienciaManager.desvinculaCargo(cargo.getId(), cargo.getNomeMercado());
		
		QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager = (QuantidadeLimiteColaboradoresPorCargoManager) SpringUtil.getBean("quantidadeLimiteColaboradoresPorCargoManager");
		quantidadeLimiteColaboradoresPorCargoManager.deleteByCargo(cargoId);
		
		CandidatoManager candidatoManager = (CandidatoManager) SpringUtil.getBean("candidatoManager");
		candidatoManager.deleteCargosPretendidos(cargoId);
	}

	public boolean verifyExistCargoNome(String cargoNome, Long empresaId)
	{
		return getDao().verifyExistCargoNome(cargoNome, empresaId);
	}
	
	public Collection<Cargo> findByGrupoOcupacional(Long grupoOcupacionalId)
	{
		return getDao().findByGrupoOcupacional(grupoOcupacionalId);
	}

	@SuppressWarnings("rawtypes")
	public Map findByAreaDoHistoricoColaborador(String[] areaOrganizacionalIds)
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		if(areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
		{
			Long [] areaIds = LongUtil.arrayStringToArrayLong(areaOrganizacionalIds);
			cargos = getDao().findByAreaDoHistoricoColaborador(areaIds);
		}

		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId","getNomeMercadoComStatus");
	}

	public Collection<Cargo> findBySolicitacao(Long solicitacaoId)
	{
		return getDao().findBySolicitacao(solicitacaoId);
	}

	public Collection<Cargo> findAllSelectDistinctNome(Long[] empresaIds)
	{
		return getDao().findAllSelectDistinctNomeMercado(empresaIds);
	}

	public void sincronizar(Long empresaOrigemId, Empresa empresaDestino, Map<Long, Long> areaIds, Map<Long, Long> conhecimentoIds, Map<Long, Long> habilidadeIds, Map<Long, Long> atitudeIds, List<String> mensagens)
	{
		faixaSalarialManager = (FaixaSalarialManager) SpringUtil.getBean("faixaSalarialManager");
		Empresa empresaOrigem = empresaManager.findByIdProjection(empresaOrigemId);
		Map<Long, GrupoOcupacional> novosGruposOcupacionais = new HashMap<Long, GrupoOcupacional>();
		
		GrupoOcupacionalManager grupoOcupacionalManager = (GrupoOcupacionalManager) SpringUtil.getBean("grupoOcupacionalManager");
		sincronizarGruposOcupacionais(empresaOrigemId, empresaDestino.getId(), novosGruposOcupacionais, grupoOcupacionalManager);
		        
		Collection<Cargo> cargos = getDao().findSincronizarCargos(empresaOrigemId);
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = null;

		for (Cargo cargo : cargos) 
		{
			try{
                status = transactionManager.getTransaction(def);
                Long cargoOrigemId = cargo.getId();
                GrupoOcupacional grupoOcupacionalOrigem = cargo.getGrupoOcupacional();
                clonar(cargo, empresaDestino.getId());

                if (areaIds != null && areaIds.size() > 0) {
                    Collection<AreaOrganizacional> areasOrganizacionais = popularAreasOrganizacionaisComIds(areaIds, cargoOrigemId);
                    cargo.setAreasOrganizacionais(areasOrganizacionais);
                }

                if (conhecimentoIds != null && conhecimentoIds.size() > 0) {
                    Collection<Conhecimento> conhecimentos = popularConhecimentosComIds(conhecimentoIds, cargoOrigemId);
                    cargo.setConhecimentos(conhecimentos);
                }

                if (habilidadeIds != null && habilidadeIds.size() > 0) {
                    Collection<Habilidade> habilidades = popularHabilidadesComIds(habilidadeIds, cargoOrigemId);
                    cargo.setHabilidades(habilidades);
                }

                if (atitudeIds != null && atitudeIds.size() > 0) {
                    Collection<Atitude> atitudes = popularAtitudesComIds(atitudeIds, cargoOrigemId);
                    cargo.setAtitudes(atitudes);
                }

                Collection<AreaFormacao> areaFormacaos = areaFormacaoManager.findByCargo(cargoOrigemId);
                cargo.setAreaFormacaos(areaFormacaos);
                cargo.setGrupoOcupacional(grupoOcupacionalOrigem);

                if (cargo.getGrupoOcupacional() != null) {
                    cargo.setGrupoOcupacional(novosGruposOcupacionais.get(cargo.getGrupoOcupacional().getId()));
                }

                getDao().update(cargo);

                Collection<FaixaSalarial> faixas = faixaSalarialManager.findByCargo(cargoOrigemId);
				Collection<String> msgsErros = faixaSalarialManager.sincronizar(faixas, cargo, empresaDestino, empresaOrigem.getGrupoAC());
				
				if(msgsErros.size() > 0)
					mensagens.addAll(msgsErros);

				if(faixas.size() != 0 && msgsErros.size() == faixas.size())
					transactionManager.rollback(status);
				else	
					transactionManager.commit(status);
				
			}catch (IntegraACException e) {
				mensagens.add("Não foi possível importar o cargo <strong>" + cargo.getNome() + "</strong> para o Fortes Pessoal.<br>Possíveis Motivos: <br>&nbsp&nbsp&nbsp- Cargo existente com a mesma descrição no Fortes Pessoal. <br>&nbsp&nbsp&nbsp- Limite de cadastros de cargos excedido no Fortes Pessoal.");
				transactionManager.rollback(status);
			}catch (FortesException e) {
			    mensagens.add("Não é possível importar o cargo <strong>" + cargo.getNome() + "</strong><br>&nbsp&nbsp&nbsp- " + e.getMessage());
			    transactionManager.rollback(status);
                e.printStackTrace();
            }catch (Exception e) {
				mensagens.add("Ocorreu um erro ao importar o cargo <strong>" + cargo.getNome() + "</strong>");
				transactionManager.rollback(status);
				e.printStackTrace();
			}
		}
		
		if(novosGruposOcupacionais.size() > 0)
			grupoOcupacionalManager.deletarGruposInseridosENaoUtilizadosAposImportarCadastroEntreEmpresas(LongUtil.collectionToArrayLong(novosGruposOcupacionais.values()), empresaDestino.getId());
	}

    private void sincronizarGruposOcupacionais(Long empresaOrigemId, Long empresaDestinoId, Map<Long, GrupoOcupacional> novosGruposOcupacionais, GrupoOcupacionalManager grupoOcupacionalManager) {
        Collection<GrupoOcupacional> gruposOcupacionais = grupoOcupacionalManager.findGruposUsadosPorCargosByEmpresaId(empresaOrigemId);
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);

        for (GrupoOcupacional grupoOcupacional : gruposOcupacionais) 
		    novosGruposOcupacionais.put(grupoOcupacional.getId(), clonarGrupoOcupacional(grupoOcupacionalManager, grupoOcupacional, empresaDestinoId));
		
		transactionManager.commit(status);
    }

	private GrupoOcupacional clonarGrupoOcupacional(GrupoOcupacionalManager grupoOcupacionalManager, GrupoOcupacional grupoOcupacional, Long empresaDestinoId) 
	{
		GrupoOcupacional grupoOcupacionalDestino = new GrupoOcupacional();
		
		grupoOcupacionalDestino.setNome(grupoOcupacional.getNome());
		grupoOcupacionalDestino.setProjectionEmpresaId(empresaDestinoId);
		
		grupoOcupacionalManager.save(grupoOcupacionalDestino);
		getDao().getHibernateTemplateByGenericDao().flush();
		
		return grupoOcupacionalDestino;
	}

	private void clonar(Cargo cargo, Long empresaDestinoId) {
		
		cargo.setId(null);
		cargo.setGrupoOcupacional(null);
		cargo.setEmpresaIdProjection(empresaDestinoId);
		
		getDao().save(cargo);
	}
	
	private Collection<AreaOrganizacional> popularAreasOrganizacionaisComIds(Map<Long, Long> areaIds, Long cargoOrigemId) {
		
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByCargo(cargoOrigemId);
		
		for (AreaOrganizacional areaOrganizacional : areas)
		{
			Long id = areaIds.get( areaOrganizacional.getId() );
			
			if (id == null)
				continue;
			
			areaOrganizacional.setId(id);
		}
		
		return areas;
	}
	
	private Collection<Conhecimento> popularConhecimentosComIds(Map<Long, Long> conhecimentoIds, Long cargoOrigemId) {
		
		Collection<Conhecimento> conhecimentos = conhecimentoManager.findByCargo(cargoOrigemId);
		
		for (Conhecimento conhecimento : conhecimentos)
		{
			Long id = conhecimentoIds.get( conhecimento.getId() );
			
			if (id == null)
				continue;
			
			conhecimento.setId(id);
		}
		
		return conhecimentos;
	}

	private Collection<Habilidade> popularHabilidadesComIds(Map<Long, Long> habilidadeIds, Long cargoOrigemId) {
		
		Collection<Habilidade> habilidades = habilidadeManager.findByCargo(cargoOrigemId);
		
		for (Habilidade habilidade : habilidades)
		{
			Long id = habilidadeIds.get( habilidade.getId() );
			
			if (id == null)
				continue;
			
			habilidade.setId(id);
		}
		
		return habilidades;
	}

	private Collection<Atitude> popularAtitudesComIds(Map<Long, Long> atitudeIds, Long cargoOrigemId) {
		
		Collection<Atitude> atitudes = atitudeManager.findByCargo(cargoOrigemId);
		
		for (Atitude atitude : atitudes)
		{
			Long id = atitudeIds.get( atitude.getId() );
			
			if (id == null)
				continue;
			
			atitude.setId(id);
		}
		
		return atitudes;
	}

	public Collection<Cargo> findByEmpresaAC(String empCodigo, String codigo, String grupoAC)
	{
		if(StringUtils.isEmpty(codigo))
			return getDao().findByEmpresaAC(empCodigo, grupoAC);
		else
			return getDao().findByEmpresaAC(empCodigo, codigo, grupoAC);
	}
	

	public Cargo preparaCargoDoAC(TCargo tCargo)
	{
		Empresa empresa = empresaManager.findByCodigoAC(tCargo.getEmpresaCodigoAC(), tCargo.getGrupoAC());
		if(tCargo.getCargoId() == 0)//cadastra um novo cargo, foi inserido uma descrição no AC
		{
			Cargo cargo = new Cargo();
			cargo.setNome(StringUtils.isEmpty(tCargo.getCargoDescricao()) ? tCargo.getDescricaoACPessoal() : tCargo.getCargoDescricao());
			cargo.setNomeMercado(StringUtils.isEmpty(tCargo.getCargoDescricao()) ? tCargo.getDescricaoACPessoal() : tCargo.getCargoDescricao());
			cargo.setEmpresa(empresa);
			
			return save(cargo);
		}
		else
		{
			Cargo cargo = findByIdProjection(tCargo.getCargoId());
			return cargo;
		}
	}
	
	public Collection<Cargo> findByEmpresa(Long empresaId) 
	{
		return getDao().findByEmpresa(empresaId);
	}

	public Collection<Cargo> findAllSelect(Long[] empresaIds)
	{
		return getDao().findAllSelect(empresaIds);
	}

	public String nomeCargos(Long[] cargosIds) 
	{
		Collection<Cargo> cargos = getDao().findCargosByIds(cargosIds, null);
		String resultado = "";
		
		for (Cargo cargo : cargos) 
			resultado += cargo.getNome() + ", ";
		
		if(!resultado.equals(""))
			return resultado.substring(0, (resultado.length() - 2));
		else
			return resultado;
	}

	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception 
	{
		getDao().deleteByAreaOrganizacional(areaIds);
	}

	public boolean existemCargosSemAreaRelacionada(Long empresaId) 
	{
		Collection<Cargo> cargos = getDao().getCargosSemAreaRelacionada(empresaId);
		return cargos.size() != 0;
	}

	public Collection<Cargo> getCargosSemAreaRelacionada(Long empresaId) 
	{
		return getDao().getCargosSemAreaRelacionada(empresaId);
	}

	public Collection<Cargo> getCargosSemGrupoRelacionado(Long empresaId) 
	{
		return getDao().getCargosSemGrupoRelacionado(empresaId);
	}

	public Collection<Cargo> findByAreaGrupo(Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId) 
	{
		return getDao().findByAreaGrupo(areaOrganizacionalIds, grupoOcupacionalIds, empresaId);
	}

	public void insereAreaRelacionada(Long areaMaeId, Long areaId) 
	{
		getDao().insereAreaRelacionada(areaMaeId, areaId);
	}

	public void removeViculoComHabilidade(Long habilidadeId) {
		getDao().removeViculoComHabilidade(habilidadeId);
	}
	
	public Collection<Cargo> findCargoByEmpresaEArea(Long[] empresasIds, Long[] areaOrganizacionaisIds, boolean exibirSomenteCargosVinculadosAsAreasSeleciondas) {
	    return getDao().findCargoByEmpresaEArea(empresasIds, areaOrganizacionaisIds, exibirSomenteCargosVinculadosAsAreasSeleciondas);
	}
	 
	public void setHabilidadeManager(HabilidadeManager habilidadeManager) 
	{
		this.habilidadeManager = habilidadeManager;
	}
	
	public void setAtitudeManager(AtitudeManager atitudeManager) 
	{
		this.atitudeManager = atitudeManager;
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) 
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
	
	public void setAreaFormacaoManager(AreaFormacaoManager areaFormacaoManager) 
	{
		this.areaFormacaoManager = areaFormacaoManager;
	}
	
	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager) 
	{
		this.conhecimentoManager = conhecimentoManager;
	}
	
	public void setEtapaSeletivaManager(EtapaSeletivaManager etapaSeletivaManager)
	{
		this.etapaSeletivaManager = etapaSeletivaManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public void setAcPessoalClientCargo(AcPessoalClientCargo acPessoalClientCargo)
	{
		this.acPessoalClientCargo = acPessoalClientCargo;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public void setExperienciaManager(ExperienciaManager experienciaManager)
	{
		this.experienciaManager = experienciaManager;
	}
}