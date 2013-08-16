/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA0026
 */

package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.exception.AreaColaboradorException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Usuario;
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
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.ws.AcPessoalClientLotacao;
import com.fortes.web.tags.CheckBox;

@SuppressWarnings("unchecked")
public class AreaOrganizacionalManagerImpl extends GenericManagerImpl<AreaOrganizacional, AreaOrganizacionalDao> implements AreaOrganizacionalManager
{
	private AcPessoalClientLotacao acPessoalClientLotacao;
	private PlatformTransactionManager transactionManager;

	public Collection<AreaOrganizacional> findAreasPossiveis(Collection<AreaOrganizacional> areas, Long id)
	{
		Collection<AreaOrganizacional> areasFilhas = new ArrayList<AreaOrganizacional>();

		areasFilhas = getDescendentes(areas, id, areasFilhas);

		//adicionar o proprio elemento
		areasFilhas.add(getDao().findByIdProjection(id));

		return areasFilhas;
	}

	public Collection<AreaOrganizacional> getDescendentes(Collection<AreaOrganizacional> areas, Long id, Collection<AreaOrganizacional> descendentes)
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
		
		if(areaOrganizacional.getCoResponsavel() == null || areaOrganizacional.getCoResponsavel().getId() == null)
			areaOrganizacional.setCoResponsavel(null);

		areaOrganizacional.setEmpresa(empresa);

		if(empresa.isAcIntegra())
		{
			try
			{
				if(areaOrganizacional.getAreaMae() != null && areaOrganizacional.getAreaMae().getId() != -1)
					areaOrganizacional.setAreaMae(getDao().findAreaOrganizacionalCodigoAc(areaOrganizacional.getAreaMae().getId()));
				
				try
				{
					boolean maeSemColaboradores = true;
					if (areaOrganizacional.getAreaMae() != null && areaOrganizacional.getAreaMae().getId() != null)
						maeSemColaboradores = verificarColaboradoresAreaMae(areaOrganizacional.getAreaMae());

					if (!maeSemColaboradores)
						throw new AreaColaboradorException("Não é possível cadastrar área filha para áreas com colaboradores cadastrados.");

					String codigoAc = acPessoalClientLotacao.criarLotacao(areaOrganizacional, empresa);
					if (codigoAc == null)
						throw new IntegraACException("Metodo: AcPessoalClientLotacao.criarLotacao, codigoAc retornou null");
					
					areaOrganizacional.setCodigoAC(codigoAc);
					getDao().save(areaOrganizacional);
					// Isso garante que qualquer erro relacionado ao banco do RH levantará uma Exception antes de alterar o outro banco.
					getDao().getHibernateTemplateByGenericDao().flush();
				}
				catch (AreaColaboradorException e)
				{
					throw e;
				}
				catch (IntegraACException e)
				{
					throw e;
				}
				catch (Exception e)
				{
					acPessoalClientLotacao.deleteLotacao(areaOrganizacional, empresa);
					throw e;
				}
				
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
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBeanOld("colaboradorManager");

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

		if(areaOrganizacional.getCoResponsavel() == null || areaOrganizacional.getCoResponsavel().getId() == null)
			areaOrganizacional.setCoResponsavel(null);

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

	public Collection<AreaOrganizacional> findAllListAndInativas(Long empresaId, Boolean ativo, Collection<Long> areaInativaIds)
	{
		return getDao().findAllList(0, 0, null, null, empresaId, ativo, areaInativaIds);
	}

	public Collection<AreaOrganizacional> findAllList(Long idColaborador, Long empresaId, Boolean ativo, Long areaInativaId)
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
			Collection<AreaOrganizacional> areas = findAllListAndInativas(empresaId, AreaOrganizacional.TODAS, null);
			areas = montaFamilia(areas);
			CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
			areas = cu1.sortCollectionStringIgnoreCase(areas, "descricaoStatusAtivo");

			checks = CheckListBoxUtil.populaCheckListBox(areas, "getId", "getDescricaoStatusAtivo");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}
	
	public Collection<CheckBox> populaCheckByAreasOrderDescricao(Long[] areaIds)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<AreaOrganizacional> areas = getDao().findAreas(areaIds);
			areas = montaFamilia(areas);
			CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
			areas = cu1.sortCollectionStringIgnoreCase(areas, "descricaoStatusAtivo");
			
			checks = CheckListBoxUtil.populaCheckListBox(areas, "getId", "getDescricaoStatusAtivo");
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
			areas = cu1.sortCollectionStringIgnoreCase(areas, "descricaoComEmpresaStatusAtivo");
			
			checks = CheckListBoxUtil.populaCheckListBox(areas, "getId", "getDescricaoComEmpresaStatusAtivo");
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
		Collection<AreaOrganizacional> areas = findAllListAndInativas(empresaId, AreaOrganizacional.TODAS, null);

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
		AreaOrganizacional areaOrganizacionalTmp = getDao().findAreaOrganizacionalByCodigoAc(areaCodigoAC, empresaCodigoAC, grupoAC); 

		correcaoTransientObjectException(areaOrganizacionalTmp);

		return areaOrganizacionalTmp;
	}

	public Collection<AreaOrganizacional> findAllSelectOrderDescricao(Long empresaId, Boolean ativo, Long areaInativaId) throws Exception
	{
		Collection<Long> areasInativas = null;
		if(areaInativaId != null)
			areasInativas = Arrays.asList(areaInativaId);
		
		Collection<AreaOrganizacional> areaOrganizacionals = findAllListAndInativas(empresaId, ativo, areasInativas);
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

	public void sincronizar(Long empresaOrigemId, Empresa empresaDestino, Map<Long, Long> areaIds,  List<String> mensagens)
	{
		Long areaOrigemId;
		Long areaMaeId;
		Long areaMaeIdAntiga;
		Collection<AreaOrganizacional> areasOrigem = getDao().findSincronizarAreas(empresaOrigemId);
		Collection<AreaOrganizacional> areasDestino = new ArrayList<AreaOrganizacional>(areasOrigem.size());
		Map<Long, Long> areaIdsAreaMaeIds = new  HashMap<Long, Long>();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);

		try{
			for (AreaOrganizacional areaOrganizacional : areasOrigem)
			{
				areaOrigemId = areaOrganizacional.getId();
				areaMaeId = areaOrganizacional.getAreaMaeId();

				clonar(areaOrganizacional, empresaDestino.getId());

				areaIds.put(areaOrigemId, areaOrganizacional.getId());
				areaIdsAreaMaeIds.put(areaOrganizacional.getId(), areaMaeId);

				areasDestino.add(areaOrganizacional);
			}

			for (AreaOrganizacional areaOrganizacional : areasDestino) 
			{
				areaMaeIdAntiga =  areaIdsAreaMaeIds.get(areaOrganizacional.getId());
				areaMaeId = areaIds.get(areaMaeIdAntiga);

				if (areaMaeId != null)
				{
					areaOrganizacional.setAreaMaeId(areaMaeId);
					getDao().update(areaOrganizacional);
				}
			}

			if(empresaDestino.isAcIntegra())
				for (AreaOrganizacional areaOrganizacional : areasDestino)
					insereNoAcPessoal(empresaDestino, areaOrganizacional, areasDestino);

			transactionManager.commit(status);
		}catch (IntegraACException e)
		{
			mensagens.add("Ocorreu um erro ao importar a área organizacional para o AC Pessoal.");
			transactionManager.rollback(status);
		}catch (Exception e)
		{
			mensagens.add("Ocorreu um erro ao importar a área organizacional.");
			transactionManager.rollback(status);
			e.printStackTrace();
		}
	}

	private AreaOrganizacional insereNoAcPessoal(Empresa empresaDestino,AreaOrganizacional areaOrganizacional, Collection<AreaOrganizacional> areaOrganizacionais) throws Exception,IntegraACException 
	{
		areaOrganizacional = populaAreaMae(areaOrganizacional, areaOrganizacionais);
		if(areaOrganizacional.getAreaMaeId() != null)
		{
			AreaOrganizacional areaMae = insereNoAcPessoal(empresaDestino,areaOrganizacional.getAreaMae(), areaOrganizacionais);
			areaOrganizacional.setAreaMae(areaMae);
		}

		String codigoAc = areaOrganizacional.getCodigoAC();
		if(codigoAc == null || codigoAc.equals(""))
		{
			correcaoTransientObjectException(areaOrganizacional);
			codigoAc = salvaNoAcPessoal(empresaDestino,areaOrganizacional);
			areaOrganizacional.setCodigoAC(codigoAc);
			getDao().update(areaOrganizacional);
		}
		
		correcaoTransientObjectException(areaOrganizacional);
		return areaOrganizacional;
	}

	private AreaOrganizacional populaAreaMae(AreaOrganizacional areaOrganizacional, Collection<AreaOrganizacional> areaOrganizacionais) 
	{
		for (AreaOrganizacional area : areaOrganizacionais) 
			if(area.getId().equals(areaOrganizacional.getId()))
				return area;
		
		return areaOrganizacional;
	}

	private String salvaNoAcPessoal(Empresa empresaDestino,AreaOrganizacional areaOrganizacional) throws Exception, IntegraACException 
	{
		String codigoAc = acPessoalClientLotacao.criarLotacao(areaOrganizacional, empresaDestino);
		if (codigoAc == null || codigoAc.equals(""))
			throw new IntegraACException();
		return codigoAc;
	}

	private Long clonar(AreaOrganizacional areaOrganizacional, Long empresaDestinoId) {
		
		areaOrganizacional.setId(null);
		areaOrganizacional.setAreaMae(null);
		areaOrganizacional.setEmpresaId(empresaDestinoId);
		
		getDao().save(areaOrganizacional);
		
		return areaOrganizacional.getId();
	}
	
	public Collection<ExamesPrevistosRelatorio> setFamiliaAreas(Collection<ExamesPrevistosRelatorio> examesPrevistosRelatorios, Long empresaId) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = findAllListAndInativas(empresaId, AreaOrganizacional.TODAS, null);
		areaOrganizacionals = montaFamilia(areaOrganizacionals);

		for (ExamesPrevistosRelatorio examesPrevistosRelatorio: examesPrevistosRelatorios)
		{
			if(examesPrevistosRelatorio.getAreaOrganizacional() != null && examesPrevistosRelatorio.getAreaOrganizacional().getId() != null)
				examesPrevistosRelatorio.setAreaOrganizacional(this.getAreaOrganizacional(areaOrganizacionals, examesPrevistosRelatorio.getAreaOrganizacional().getId()));
		}

		return examesPrevistosRelatorios;
	}

	public void bind(AreaOrganizacional areaOrganizacional, TAreaOrganizacional lotacao) throws Exception
	{
		//TODO: codigoac vazio
		areaOrganizacional.setCodigoAC(lotacao.getCodigo());
		areaOrganizacional.setNome(lotacao.getNome());

		if("".equals(lotacao.getAreaMaeCodigo()))
			areaOrganizacional.setAreaMae(null);
		else
			areaOrganizacional.setAreaMae(getDao().findAreaOrganizacionalByCodigoAc(lotacao.getAreaMaeCodigo(), lotacao.getEmpresaCodigo(), lotacao.getGrupoAC()));
		
		boolean maeSemColaboradores = true;
		
		if(areaOrganizacional.getAreaMae() != null && areaOrganizacional.getAreaMae().getId() != null)
			maeSemColaboradores = verificarColaboradoresAreaMae(areaOrganizacional.getAreaMae());

		if(areaOrganizacional.getResponsavel() == null || areaOrganizacional.getResponsavel().getId() == null)
			areaOrganizacional.setResponsavel(null);

		if(areaOrganizacional.getCoResponsavel() == null || areaOrganizacional.getCoResponsavel().getId() == null)
			areaOrganizacional.setCoResponsavel(null);
		
		if(!maeSemColaboradores)
			throw new Exception("Não é possível cadastrar área filha para áreas com colaboradores cadastrados.");
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

	public Long[] findIdsAreasDoResponsavelCoResponsavel(Long usuarioId, Long empresaId) 
	{
		return getDao().findIdsAreasDoResponsavelCoResponsavel(usuarioId, empresaId);
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

	public Long[] findIdsAreasDoResponsavelCoResponsavel(Usuario usuarioLogado, Long empresaId) 
	{
		Long[] areasIds = findIdsAreasDoResponsavelCoResponsavel(usuarioLogado.getId(), empresaId);
		if(areasIds.length > 0)
		{
			CollectionUtil<Long> cUtil = new CollectionUtil<Long>();
			Collection<Long> areasIdsFilhas = findIdsAreasFilhas(cUtil.convertArrayToCollection(areasIds));
			areasIdsFilhas.addAll(cUtil.convertArrayToCollection(areasIds));
			areasIds = (Long[]) areasIdsFilhas.toArray(new Long[areasIdsFilhas.size()]);
		}
		return areasIds;
	}
	
	private Collection<Long> findIdsAreasFilhas(Collection<Long> areasIds) 
	{
		Collection<Long> areasFilhasIds = new ArrayList<Long>();
		Collection<Long> filhasIds = getDao().findIdsAreasFilhas(areasIds);
		
		while (!filhasIds.isEmpty())
		{
			areasFilhasIds.addAll(filhasIds);
			filhasIds = findIdsAreasFilhas(filhasIds);
		}
		
		return areasFilhasIds;
	}
	
	public Collection<AreaOrganizacional> findAreasByUsuarioResponsavel(Usuario usuario, Long empresaId) throws Exception
	{
		Long[] areaIds = findIdsAreasDoResponsavelCoResponsavel(usuario, empresaId);
		if(areaIds == null || areaIds.length == 0)
			return new ArrayList<AreaOrganizacional>();
		
		Collection<AreaOrganizacional> areas = getDao().findAreas(areaIds);
		areas = montaFamilia(areas);
		
		CollectionUtil<AreaOrganizacional> cUtil = new CollectionUtil<AreaOrganizacional>();
		areas = cUtil.sortCollectionStringIgnoreCase(areas, "descricao");
		
		return areas;
	}
	
	public String[] getEmailsResponsaveis(Long areaId, Long empresaId, int tipoResponsavel) throws Exception
	{
		Collection<AreaOrganizacional> areas = findAllListAndInativas(empresaId, true, null); 
		areas = getAncestrais(areas, areaId);
		
		Collection<String> emailsNotificacoes = new ArrayList<String>();
		for (AreaOrganizacional area : areas) 
		{
			if(tipoResponsavel == AreaOrganizacional.CORRESPONSAVEL){
				if(area.getCoResponsavel() != null && area.getCoResponsavel().getContato() != null && area.getCoResponsavel().getContato().getEmail() != null && !area.getCoResponsavel().getContato().getEmail().equals(""))
					emailsNotificacoes.add(area.getCoResponsavelEmail());
			} else if(tipoResponsavel == AreaOrganizacional.RESPONSAVEL){
				if(area.getResponsavel() != null && area.getResponsavel().getContato() != null && area.getResponsavel().getContato().getEmail() != null && !area.getResponsavel().getContato().getEmail().equals(""))
					emailsNotificacoes.add(area.getResponsavelEmail());
			}
			
			if(area.getEmailsNotificacoes() != null)
				for (String email : area.getEmailsNotificacoes().split(";"))
					emailsNotificacoes.add(email);
		}
		
		return StringUtil.converteCollectionToArrayString(emailsNotificacoes);
	}
	
	public String[] getEmailsResponsaveis(Long areaId, Collection<AreaOrganizacional> todasAreas, int tipoResponsavel) throws Exception
	{
		
		Collection<AreaOrganizacional> areasRetornadas = null;
		areasRetornadas = getAncestrais(todasAreas, areaId);
		
		Collection<String> emailsNotificacoes = new ArrayList<String>();
		for (AreaOrganizacional area : areasRetornadas) 
		{
			if(tipoResponsavel == AreaOrganizacional.CORRESPONSAVEL){
				if(area.getCoResponsavel() != null && area.getCoResponsavel().getContato() != null && area.getCoResponsavel().getContato().getEmail() != null && !area.getCoResponsavel().getContato().getEmail().equals(""))
					emailsNotificacoes.add(area.getCoResponsavelEmail());
			} else if(tipoResponsavel == AreaOrganizacional.RESPONSAVEL){
				if(area.getResponsavel() != null && area.getResponsavel().getContato() != null && area.getResponsavel().getContato().getEmail() != null && !area.getResponsavel().getContato().getEmail().equals(""))
					emailsNotificacoes.add(area.getResponsavelEmail());
			}
			
			if(area.getEmailsNotificacoes() != null)
				for (String email : area.getEmailsNotificacoes().split(";"))
					emailsNotificacoes.add(email);
		}
		
		return StringUtil.converteCollectionToArrayString(emailsNotificacoes);
	}

	public void desvinculaResponsaveis(Long colaboradorId)
	{
		getDao().desvinculaResponsavel(colaboradorId);		
		getDao().desvinculaCoResponsavel(colaboradorId);		
	}
	
	private void correcaoTransientObjectException(AreaOrganizacional areaOrganizacionalTmp) 
	{
		if(areaOrganizacionalTmp.getResponsavel() !=null && areaOrganizacionalTmp.getResponsavel().getId() == null)
			areaOrganizacionalTmp.setResponsavel(null);

		if(areaOrganizacionalTmp.getCoResponsavel() !=null && areaOrganizacionalTmp.getCoResponsavel().getId() == null)
			areaOrganizacionalTmp.setCoResponsavel(null);

		if(areaOrganizacionalTmp.getAreaMae() != null && areaOrganizacionalTmp.getAreaMae().getId() == null)
			areaOrganizacionalTmp.setAreaMae(null);
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
}