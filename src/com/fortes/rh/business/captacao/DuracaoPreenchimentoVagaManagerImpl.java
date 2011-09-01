package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.CollectionUtil;

@SuppressWarnings("unchecked")
public class DuracaoPreenchimentoVagaManagerImpl implements DuracaoPreenchimentoVagaManager
{
	
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private CargoManager cargoManager;
	private SolicitacaoManager solicitacaoManager;
	
	private Map<Long, String> getAreas(Long empresaId) throws Exception {
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllSelectOrderDescricao(empresaId, null);
		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areas, "getId", "getDescricao");
	}
	
	private Map<Long, String> getEstabelecimentos(Long empresaId) {
		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(empresaId);
		return new CollectionUtil<Estabelecimento>().convertCollectionToMap(estabelecimentos, "getId", "getNome");
	}

	public Collection<IndicadorDuracaoPreenchimentoVaga> gerarIndicadorDuracaoPreenchimentoVagas(Date dataDe, Date dataAte, Collection<Long> areasIds, Collection<Long> estabelecimentosIds, Long empresaId) throws Exception
	{
		Map<Long, String> mapNomesAreas = getAreas(empresaId);
		Map<Long, String> mapNomesEstabelecimentos = getEstabelecimentos(empresaId);
		
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresDuracaoPreenchimentoVagas = solicitacaoManager.getIndicadorMediaDiasPreenchimentoVagas(dataDe, dataAte, areasIds, estabelecimentosIds);
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresQtdCandidatos = solicitacaoManager.getIndicadorQtdCandidatos(dataDe, dataAte, areasIds, estabelecimentosIds);
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresVagas = solicitacaoManager.getIndicadorQtdVagas(dataDe, dataAte, areasIds, estabelecimentosIds);
		
		if(indicadoresDuracaoPreenchimentoVagas == null || indicadoresDuracaoPreenchimentoVagas.isEmpty())
			throw new ColecaoVaziaException();
		
		for (IndicadorDuracaoPreenchimentoVaga indicador : indicadoresDuracaoPreenchimentoVagas)
		{
			String nomeDoEstabelecimento = mapNomesEstabelecimentos.get(indicador.getEstabelecimento().getId());
			String nomeDaAreaOrganizacional = mapNomesAreas.get(indicador.getAreaOrganizacional().getId());
			
			indicador.getAreaOrganizacional().setNome(nomeDaAreaOrganizacional);
			indicador.getEstabelecimento().setNome(nomeDoEstabelecimento);
			
			Cargo cargo = cargoManager.findByIdProjection(indicador.getCargo().getId());
			indicador.setCargo(cargo);
			
			for (IndicadorDuracaoPreenchimentoVaga indicadorQtdCandidatos : indicadoresQtdCandidatos) {
				if (indicadorQtdCandidatos.getAreaOrganizacional().equals(indicador.getAreaOrganizacional())
					&& indicadorQtdCandidatos.getCargo().equals(indicador.getCargo())
					&& indicadorQtdCandidatos.getEstabelecimento().equals(indicador.getEstabelecimento()) )
				{
					indicador.setQtdCandidatos(indicadorQtdCandidatos.getQtdCandidatos());
					break;
				}
			}
			
			for (IndicadorDuracaoPreenchimentoVaga indicadorQtdVagas : indicadoresVagas) {
				if (indicadorQtdVagas.getAreaOrganizacional().equals(indicador.getAreaOrganizacional())
					&& indicadorQtdVagas.getCargo().equals(indicador.getCargo())
					&& indicadorQtdVagas.getEstabelecimento().equals(indicador.getEstabelecimento()) )
				{
					indicador.setQtdVagas(indicadorQtdVagas.getQtdVagas());
					break;
				}
			}
		}
		
		return indicadoresDuracaoPreenchimentoVagas;
	}

	public Collection<IndicadorDuracaoPreenchimentoVaga> gerarIndicadorMotivoPreenchimentoVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao) throws Exception
	{
		Map<Long, String> mapNomesAreasOrganizacionais = getAreas(empresaId);
		Map<Long, String> mapNomesEstabelecimentos = getEstabelecimentos(empresaId);
		
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresMotivos = solicitacaoManager.getIndicadorMotivosSolicitacao(dataDe, dataAte, areasOrganizacionais, estabelecimentos, empresaId, statusSolicitacao);
		
		if(indicadoresMotivos == null || indicadoresMotivos.isEmpty())
			throw new ColecaoVaziaException();
		
		for (IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVaga : indicadoresMotivos)
		{
			Long areaId = indicadorDuracaoPreenchimentoVaga.getAreaOrganizacional().getId();
			Long estabelecimentoId = indicadorDuracaoPreenchimentoVaga.getEstabelecimento().getId();
			
			indicadorDuracaoPreenchimentoVaga.getAreaOrganizacional().setNome(mapNomesAreasOrganizacionais.get(areaId));
			indicadorDuracaoPreenchimentoVaga.getEstabelecimento().setNome(mapNomesEstabelecimentos.get(estabelecimentoId));
			
			Cargo cargo = cargoManager.findByIdProjection(indicadorDuracaoPreenchimentoVaga.getCargo().getId());
			indicadorDuracaoPreenchimentoVaga.setCargo(cargo);
		}
		
		return indicadoresMotivos;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}
}