/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA0026
 */

package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.exception.AreaColaboradorException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.ws.TAreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientLotacao;
import com.fortes.web.tags.CheckBox;

@SuppressWarnings("unchecked")
public class AreaOrganizacionalManagerImpl extends GenericManagerImpl<AreaOrganizacional, AreaOrganizacionalDao> implements AreaOrganizacionalManager
{
	private AcPessoalClientLotacao acPessoalClientLotacao;
	private ColaboradorManager colaboradorManager;

	public Collection<AreaOrganizacional> findAreasPossiveis(Collection<AreaOrganizacional> areas, Long id)
	{
		Collection<AreaOrganizacional> areasFilhas = new ArrayList<AreaOrganizacional>();

		areasFilhas = getDescendentes(areas, id, areasFilhas);

		//adicionar o proprio elemento
		areasFilhas.add(getDao().findByIdProjection(id));

		return areasFilhas;
	}

	private Collection<AreaOrganizacional> getDescendentes(Collection<AreaOrganizacional> areas, Long id, Collection<AreaOrganizacional> descendentes)
	{
		// Busca filhos da area pelo id e coloca eles nos descendentes
		Collection<AreaOrganizacional> filhos = getFilhos(areas, id);
		descendentes.addAll(filhos);
		// Busca filho dos descendentes
		for(AreaOrganizacional areaFilha : filhos){
			getDescendentes(areas, areaFilha.getId(), descendentes);
		}

		return descendentes;
	}

	private Collection<AreaOrganizacional> getFilhos(Collection<AreaOrganizacional> areas, Long id)
	{
		Collection<AreaOrganizacional> filhos = new ArrayList<AreaOrganizacional>();

		for(AreaOrganizacional areaTmp : areas)
		{
			// Caso area tenha mãe, pega area filha
			if(areaTmp.getAreaMae() != null && areaTmp.getAreaMae().getId() != null && areaTmp.getAreaMae().getId().equals(id)){
				filhos.add(areaTmp);
			}
		}

		return filhos;
	}

	public static Collection getDistinctAreas(Collection<AreaOrganizacional> areas)
	{
		Hashtable hashAreas = new Hashtable();
		Vector<AreaOrganizacional> areasFiltroVecDistinct = new Vector<AreaOrganizacional>();

		for(AreaOrganizacional area : areas){
			hashAreas.put(area.getId(), area);
		}

		Enumeration<AreaOrganizacional> enumAreas = hashAreas.elements();
		while(enumAreas.hasMoreElements()){
			areasFiltroVecDistinct.add(enumAreas.nextElement());
		}

		return areasFiltroVecDistinct;
	}

	public Collection getNaoFamilia(Collection areas, Long id)
	{
		Collection<AreaOrganizacional> familia = findAreasPossiveis(areas, id);
		areas.removeAll(familia);

		return areas;
	}

	public void insertLotacaoAC(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception
	{
		if(areaOrganizacional.getAreaMae().getId() == -1)
			areaOrganizacional.setAreaMae(null);

		if(areaOrganizacional.getResponsavel() == null || areaOrganizacional.getResponsavel().getId() == null)
			areaOrganizacional.setResponsavel(null);

		areaOrganizacional.setEmpresa(empresa);

		if(empresa.isAcIntegra())
		{
			try
			{
				if(areaOrganizacional.getAreaMae() != null && areaOrganizacional.getAreaMae().getId() != -1)
					areaOrganizacional.setAreaMae(getDao().findAreaOrganizacionalCodigoAc(areaOrganizacional.getAreaMae().getId()));

				String codigoAc = acPessoalClientLotacao.criarLotacao(areaOrganizacional, empresa);

				if(codigoAc != null)
				{
					try
					{
						boolean maeSemColaboradores = true;
						if(areaOrganizacional.getAreaMae() != null && areaOrganizacional.getAreaMae().getId() != null)
							maeSemColaboradores = verificarColaboradoresAreaMae(areaOrganizacional.getAreaMae());

						if(maeSemColaboradores)
						{
							areaOrganizacional.setCodigoAC(codigoAc);
							save(areaOrganizacional);
							// Isso garante que qualquer erro relacionado ao banco do RH levantará uma Exception antes de alterar o outro banco.
							getDao().getHibernateTemplateByGenericDao().flush();
						}
						else
							throw new AreaColaboradorException("Área Mãe já possui colaboradores cadastrados.");
					}
					catch (AreaColaboradorException e)
					{
						acPessoalClientLotacao.deleteLotacao(areaOrganizacional, empresa);
						throw e;
					}
					catch (Exception e)
					{
						areaOrganizacional.setId(null);
						acPessoalClientLotacao.deleteLotacao(areaOrganizacional, empresa);
						throw e;
					}
				}
				else
					throw new IntegraACException("Metodo: AcPessoalClientLotacao.criarLotacao, codigoAc retornou null");
			}
			catch (AreaColaboradorException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new IntegraACException(e.getMessage());
			}
		}
		else
		{
			getDao().save(areaOrganizacional);
		}
	}

	private boolean verificarColaboradoresAreaMae(AreaOrganizacional areaMae)
	{
		colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");

		Collection<Colaborador> colaboradores = colaboradorManager.findByArea(areaMae);

		if(colaboradores.size() > 0)
			return false;

		return true;
	}

	public void editarLotacaoAC(AreaOrganizacional areaOrganizacional, Empresa empresa) throws Exception
	{
		if(areaOrganizacional.getAreaMae() == null || areaOrganizacional.getAreaMae().getId() == -1)
			areaOrganizacional.setAreaMae(null);

		if(areaOrganizacional.getResponsavel() == null || areaOrganizacional.getResponsavel().getId() == null)
			areaOrganizacional.setResponsavel(null);

		update(areaOrganizacional);
		// Isso garante que qualquer erro relacionado ao banco do RH levantará uma Exception antes de alterar o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush();

		if(empresa.isAcIntegra())
		{
			acPessoalClientLotacao.criarLotacao(areaOrganizacional, empresa);
		}
	}

	public void deleteLotacaoAC(AreaOrganizacional areaOrganizacional, Empresa empresa) throws IntegraACException, Exception
	{
		AreaOrganizacional areaTmp = getDao().findAreaOrganizacionalCodigoAc(areaOrganizacional.getId());
		remove(new Long[]{areaTmp.getId()});
		// Isso garante que qualquer erro relacionado ao banco do RH levantará uma Exception antes de alterarmos o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush();

		
		if(empresa.isAcIntegra())
			acPessoalClientLotacao.deleteLotacao(areaTmp, empresa);
	}

	public boolean verificaMaternidade(Long areaOrganizacionalId)
	{
		return getDao().verificaMaternidade(areaOrganizacionalId);
	}

	public Collection<AreaOrganizacional> findAllListAndInativa(Long empresaId, Boolean ativo, Long areaInativaId)
	{
		return getDao().findAllList(0, 0, null, null, empresaId, ativo, areaInativaId);
	}

	public Collection<AreaOrganizacional> findAllList(Long idColaborador, Long empresaId, Boolean ativo)
	{
		return getDao().findAllList(0, 0, idColaborador, null, empresaId, ativo, null);
	}

	public Collection<AreaOrganizacional> findAllList(int page, int pagingSize, String nome, Long empresaId, Boolean ativo)
	{
		return getDao().findAllList(page, pagingSize, null, nome, empresaId, ativo, null);
	}

	public Integer getCount(String nome, Long empresaId)
	{
		return getDao().getCount(nome, empresaId);
	}

	public Collection<CheckBox> populaCheckOrderDescricao(long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<AreaOrganizacional> areas = findAllListAndInativa(empresaId, AreaOrganizacional.TODAS, null);
			areas = montaFamilia(areas);
			CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
			areas = cu1.sortCollectionStringIgnoreCase(areas, "descricao");

			checks = CheckListBoxUtil.populaCheckListBox(areas, "getId", "getDescricao");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	public Collection<CheckBox> populaCheckOrderDescricao(Long[] empresaIds)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<AreaOrganizacional> areas = findByEmpresasIds(empresaIds, AreaOrganizacional.TODAS);
			areas = montaFamilia(areas);
			CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
			areas = cu1.sortCollectionStringIgnoreCase(areas, "descricaoComEmpresa");
			
			checks = CheckListBoxUtil.populaCheckListBox(areas, "getId", "getDescricaoComEmpresa");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return checks;
	}

	public Collection<AreaOrganizacional> populaAreas(String[] areasCheck)
	{
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();

		if(areasCheck != null && areasCheck.length > 0)
		{
			Long areasIds[] = LongUtil.arrayStringToArrayLong(areasCheck);

			AreaOrganizacional area;
			for (Long areaId: areasIds)
			{
				area = new AreaOrganizacional();
				area.setId(areaId);

				areas.add(area);
			}
		}

		return areas;
	}

	public void setAcPessoalClientLotacao(AcPessoalClientLotacao acPessoalClientLotacao)
	{
		this.acPessoalClientLotacao = acPessoalClientLotacao;
	}

	public AreaOrganizacional findAreaOrganizacionalCodigoAc(Long idAreaOrganizacional)
	{
		return getDao().findAreaOrganizacionalCodigoAc(idAreaOrganizacional);
	}

	public AreaOrganizacional getAreaMae(Collection<AreaOrganizacional> areas, AreaOrganizacional area)
	{
		for (AreaOrganizacional areaTmp : areas)
		{
			if(area.getAreaMae() != null && area.getAreaMae().equals(areaTmp))
			{
				area.setAreaMae(areaTmp);
				break;
			}
		}

		return area;
	}

	public Collection<AreaOrganizacional> montaFamilia(Collection<AreaOrganizacional> areas) throws Exception
	{
		Collection<AreaOrganizacional> areasRetorno = new ArrayList<AreaOrganizacional>();

		for(AreaOrganizacional areaTmp : areas)
		{
			insereMae(areas, areaTmp);
			areasRetorno.add(areaTmp);
		}

		return areasRetorno;
	}

	private void insereMae(Collection<AreaOrganizacional> areas, AreaOrganizacional area)
	{
		for(AreaOrganizacional areaTmp : areas)
		{
			if(areaTmp.equals(area.getAreaMae()))
			{
				area.setAreaMae(areaTmp);
				if(areaTmp.getAreaMae() != null && areaTmp.getAreaMae().getId() != null)
					insereMae(areas, areaTmp);
			}
		}
	}

	public Collection<AreaOrganizacional> getDistinctAreaMae(Collection<AreaOrganizacional> todasAreas, Collection<AreaOrganizacional> areaOrganizacionals)
	{
		Collection<AreaOrganizacional> retorno = new ArrayList<AreaOrganizacional>();

		for (AreaOrganizacional areaTmp : areaOrganizacionals)
		{
			for (AreaOrganizacional todaArea : todasAreas)
			{
				if(areaTmp.equals(todaArea))
				{
					retorno.add(todaArea);
					break;
				}
			}
		}

		return retorno;
	}

	public Collection<AreaOrganizacional> findByCargo(Long cargoId)
	{
		return getDao().findByCargo(cargoId);
	}

	public Collection<AreaOrganizacional> montaAllSelect(Long empresaId)
	{
		Collection<AreaOrganizacional> areas = findAllListAndInativa(empresaId, AreaOrganizacional.TODAS, null);

		try
		{
			areas = montaFamilia(areas);
			CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
			areas = cu1.sortCollectionStringIgnoreCase(areas, "descricao");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			areas = new ArrayList<AreaOrganizacional>();
		}

		return areas;
	}

	public AreaOrganizacional getAreaOrganizacional(Collection<AreaOrganizacional> areaOrganizacionals, Long areaOrganizacionalId)
	{
		for (AreaOrganizacional area : areaOrganizacionals)
		{
			if(area.getId().equals(areaOrganizacionalId))
				return area;
		}

		return null;
	}
	
	public Map<String, Object> getParametrosRelatorio(String nomeRelatorio, Empresa empresa, String filtro)
	{
		return RelatorioUtil.getParametrosRelatorio(nomeRelatorio, empresa, filtro);
	}

	public Collection<AreaOrganizacional> getAreasByAreaInteresse(Long areaInteresseId)
	{
		return getDao().findAreaIdsByAreaInteresse(areaInteresseId);
	}

	public AreaOrganizacional findAreaOrganizacionalByCodigoAc(String areaCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		return getDao().findAreaOrganizacionalByCodigoAc(areaCodigoAC, empresaCodigoAC, grupoAC);
	}

	public Collection<AreaOrganizacional> findAllSelectOrderDescricao(Long empresaId, Boolean ativo, Long faixaInativaId) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = findAllListAndInativa(empresaId, ativo, faixaInativaId);
		areaOrganizacionals = montaFamilia(areaOrganizacionals);

		CollectionUtil<AreaOrganizacional> cUtil = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cUtil.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricao");

		return areaOrganizacionals;
	}
	
	public Collection<AreaOrganizacional> findByConhecimento(Long conhecimentoId)
	{
		return getDao().findByConhecimento(conhecimentoId);
	}
	
	public Collection<AreaOrganizacional> findByHabilidade(Long habilidadeId)
	{
		return getDao().findByHabilidade(habilidadeId);
	}

	public Collection<AreaOrganizacional> findByAtitude(Long atitudeId)
	{
		return getDao().findByAtitude(atitudeId);
	}

	public AreaOrganizacional findByIdProjection(Long areaId)
	{
		return getDao().findByIdProjection(areaId);
	}

	public Collection<AreaOrganizacional> findQtdColaboradorPorArea(Long estabelecimentoId, Date data)
	{
		return getDao().findQtdColaboradorPorArea(estabelecimentoId, data);
	}

	public Collection<AreaOrganizacional> findByEmpresasIds(Long[] empresaIds, Boolean ativo) {
		return getDao().findByEmpresasIds(empresaIds, ativo);
	}

	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds)
	{
		Collection<AreaOrganizacional> areasOrigem = getDao().findSincronizarAreas(empresaOrigemId);
		Collection<AreaOrganizacional> areasDestino = new ArrayList<AreaOrganizacional>(areasOrigem.size());
		Map<Long, Long> areaIdsAreaMaeIds = new  HashMap<Long, Long>();
		
		for (AreaOrganizacional areaOrganizacional : areasOrigem)
		{
			Long areaOrigemId = areaOrganizacional.getId();
			Long areaMaeId = areaOrganizacional.getAreaMaeId();
			
			clonar(areaOrganizacional, empresaDestinoId);
			
			areaIds.put(areaOrigemId, areaOrganizacional.getId());
			areaIdsAreaMaeIds.put(areaOrganizacional.getId(), areaMaeId);
			
			areasDestino.add(areaOrganizacional);
		}
		
		for (AreaOrganizacional areaOrganizacional : areasDestino) {
			
			Long areaMaeIdAntiga =  areaIdsAreaMaeIds.get(areaOrganizacional.getId());
			Long areaMaeId = areaIds.get(areaMaeIdAntiga);
			
			if (areaMaeId != null)
			{
				areaOrganizacional.setAreaMaeId(areaMaeId);
				getDao().update(areaOrganizacional);
			}
		}
	}

	private Long clonar(AreaOrganizacional areaOrganizacional, Long empresaDestinoId) {
		
		areaOrganizacional.setId(null);
		areaOrganizacional.setAreaMae(null);
		areaOrganizacional.setEmpresaId(empresaDestinoId);
		
		getDao().save(areaOrganizacional);
		
		return areaOrganizacional.getId();
	}
	
	// 
	public Collection<ExamesPrevistosRelatorio> setFamiliaAreas(Collection<ExamesPrevistosRelatorio> examesPrevistosRelatorios, Long empresaId) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = findAllListAndInativa(empresaId, AreaOrganizacional.TODAS, null);
		areaOrganizacionals = montaFamilia(areaOrganizacionals);

		for (ExamesPrevistosRelatorio examesPrevistosRelatorio: examesPrevistosRelatorios)
		{
			if(examesPrevistosRelatorio.getAreaOrganizacional() != null && examesPrevistosRelatorio.getAreaOrganizacional().getId() != null)
				examesPrevistosRelatorio.setAreaOrganizacional(this.getAreaOrganizacional(areaOrganizacionals, examesPrevistosRelatorio.getAreaOrganizacional().getId()));
		}

		return examesPrevistosRelatorios;
	}

	public void bind(AreaOrganizacional areaOrganizacional, TAreaOrganizacional lotacao)
	{
		areaOrganizacional.setCodigoAC(lotacao.getCodigo());
		areaOrganizacional.setNome(lotacao.getNome());

		if(lotacao.getAreaMaeCodigo().equals(""))
			areaOrganizacional.setAreaMae(null);
		else
			areaOrganizacional.setAreaMae(getDao().findAreaOrganizacionalByCodigoAc(lotacao.getAreaMaeCodigo(), lotacao.getEmpresaCodigo(), lotacao.getGrupoAC()));
	}

	public Collection<AreaOrganizacional> getAncestrais(Collection<AreaOrganizacional> areas, Long id) 
	{
		Collection<AreaOrganizacional> familia = new ArrayList<AreaOrganizacional>();
		
		for (AreaOrganizacional area : areas) 
		{
			if(area.getId().equals(id))
			{
				procuraMae(familia, areas, area.getAreaMae());
				familia.add(area);
				break;
			}
		}
		
		return familia;
	}

	private void procuraMae(Collection<AreaOrganizacional> familia, Collection<AreaOrganizacional> areas, AreaOrganizacional areaMae) 
	{
		if(areaMae != null && areaMae.getId() != null)
		{
			for (AreaOrganizacional area : areas) 
			{
				if(area.getId().equals(areaMae.getId()))
				{
					procuraMae(familia, areas, area.getAreaMae());
					familia.add(area);
					break;
				}
			}
		}
	}

	public String nomeAreas(Long[] areaIds) 
	{
		Collection<AreaOrganizacional> areas = getDao().findAreas(areaIds);
		String resultado = "";
		
		for (AreaOrganizacional area : areas) 
			resultado += area.getNome() + ", ";
		
		if(!resultado.equals(""))
			return resultado.substring(0, (resultado.length() - 2));
		else
			return resultado;
	}

	public AreaOrganizacional getMatriarca(Collection<AreaOrganizacional> areas, AreaOrganizacional area, Long filhaDeId) 
	{
		AreaOrganizacional matriarca = area;
		if(area.getAreaMae() != null && area.getAreaMae().getId() != null)
		{
			for (AreaOrganizacional areaTmp : areas) 
			{
				if(filhaDeId != null)
				{
					if(matriarca.getAreaMae() != null && matriarca.getAreaMae().getId() != null && matriarca.getAreaMae().getId().equals(filhaDeId))
						return matriarca;
				}
				
				if(areaTmp.getId().equals(area.getAreaMae().getId()))
					matriarca = getMatriarca(areas, areaTmp, filhaDeId);
			}
		}

		return matriarca;
	}

	public Collection<AreaOrganizacional> findByEmpresa(Long empresaId) {
		return getDao().findByEmpresa(empresaId);
	}

	public Long[] findIdsAreasDoResponsavel(Long usuarioId, Long empresaId) 
	{
		return getDao().findIdsAreasDoResponsavel(usuarioId, empresaId);
	}

	public Long[] selecionaFamilia(Collection<AreaOrganizacional> areaOrganizacionais, Collection<Long> areasIdsConfiguradas) 
	{
		Collection<Long> familia = new ArrayList<Long>();
		Collection<Long> pais = new ArrayList<Long>();
		for (Long id : areasIdsConfiguradas) 
		{
			for (AreaOrganizacional area: areaOrganizacionais)
			{
				pais = area.getDescricaoIds();
				if(pais.contains(id))
					familia.addAll(pais);
			}
		}
		
		CollectionUtil<Long> cul = new CollectionUtil<Long>();
		return cul.distinctCollection(familia).toArray(new Long[]{});
	}

	public Collection<AreaOrganizacional> findSemCodigoAC(Long empresaId) {
		return getDao().findSemCodigoAC(empresaId);
	}

	public void deleteAreaOrganizacional(Long[] areaIds) throws Exception {

		if (areaIds != null && areaIds.length > 0) {
			AreaInteresseManager areaInteresseManager = (AreaInteresseManager) SpringUtil.getBean("areaInteresseManager");
			ConhecimentoManager conhecimentoManager = (ConhecimentoManager) SpringUtil.getBean("conhecimentoManager");
			HabilidadeManager habilidadeManager = (HabilidadeManager) SpringUtil.getBean("habilidadeManager");
			AtitudeManager atitudeManager = (AtitudeManager) SpringUtil.getBean("atitudeManager");
			ConfiguracaoLimiteColaboradorManager configuracaoLimiteColaboradorManager = (ConfiguracaoLimiteColaboradorManager) SpringUtil.getBean("configuracaoLimiteColaboradorManager");
			QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager = (QuantidadeLimiteColaboradoresPorCargoManager) SpringUtil.getBean("quantidadeLimiteColaboradoresPorCargoManager");
			CargoManager cargoManager = (CargoManager) SpringUtil.getBean("cargoManager");
			
			areaInteresseManager.deleteByAreaOrganizacional(areaIds);
			conhecimentoManager.deleteByAreaOrganizacional(areaIds);
			habilidadeManager.deleteByAreaOrganizacional(areaIds);
			atitudeManager.deleteByAreaOrganizacional(areaIds);
			configuracaoLimiteColaboradorManager.deleteByAreaOrganizacional(areaIds);
			quantidadeLimiteColaboradoresPorCargoManager.deleteByArea(areaIds);
			cargoManager.deleteByAreaOrganizacional(areaIds);
			
			getDao().remove(areaIds);
		}
	}

	public String findCodigoACDuplicado(Long empresaId) {
		return getDao().findCodigoACDuplicado(empresaId);
	}

}