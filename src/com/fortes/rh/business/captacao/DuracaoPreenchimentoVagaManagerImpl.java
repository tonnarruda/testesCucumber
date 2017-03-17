package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.CollectionUtil;

@Component
@SuppressWarnings("unchecked")
public class DuracaoPreenchimentoVagaManagerImpl implements DuracaoPreenchimentoVagaManager
{
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private EstabelecimentoManager estabelecimentoManager;
	@Autowired private CargoManager cargoManager;
	@Autowired private SolicitacaoManager solicitacaoManager;
	
	private Map<Long, String> getAreas(Long empresaId) throws Exception {
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllSelectOrderDescricao(empresaId, null, null, false);
		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areas, "getId", "getDescricao");
	}
	
	private Map<Long, String> getEstabelecimentos(Long empresaId) {
		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(empresaId);
		return new CollectionUtil<Estabelecimento>().convertCollectionToMap(estabelecimentos, "getId", "getNome");
	}

	public Collection<IndicadorDuracaoPreenchimentoVaga> gerarIndicadorDuracaoPreenchimentoVagas(Date dataDe, Date dataAte, Collection<Long> areasIds, Collection<Long> estabelecimentosIds, Long empresaId, Long[] solicitacaoIds, boolean considerarContratacaoFutura) throws Exception
	{
		Map<Long, String> mapNomesAreas = getAreas(empresaId);
		Map<Long, String> mapNomesEstabelecimentos = getEstabelecimentos(empresaId);
		
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresDuracaoPreenchimentoVagas = solicitacaoManager.getIndicadorMediaDiasPreenchimentoVagas(dataDe, dataAte, areasIds, estabelecimentosIds, solicitacaoIds, empresaId, considerarContratacaoFutura);
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresQtdCandidatos = solicitacaoManager.getIndicadorQtdCandidatos(dataDe, dataAte, areasIds, estabelecimentosIds, solicitacaoIds);
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresVagas = solicitacaoManager.getIndicadorQtdVagas(dataDe, dataAte, areasIds, estabelecimentosIds, solicitacaoIds);
		
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

	public Collection<IndicadorDuracaoPreenchimentoVaga> gerarIndicadorMotivoPreenchimentoVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao, char dataStatusAprovacaoSolicitacao, boolean indicadorResumido) throws Exception
	{
		Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresMotivos = solicitacaoManager.getIndicadorMotivosSolicitacao(dataDe, dataAte, areasOrganizacionais, estabelecimentos, empresaId, statusSolicitacao, dataStatusAprovacaoSolicitacao, indicadorResumido);
		
		if(indicadoresMotivos == null || indicadoresMotivos.isEmpty())
			throw new ColecaoVaziaException();
		
		if (!indicadorResumido)
			montaFamiliaArea(empresaId, indicadoresMotivos);
		
		return indicadoresMotivos;
	}

	private void montaFamiliaArea(Long empresaId, Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresMotivos)	throws Exception 
	{
		Map<Long, String> mapNomesAreasOrganizacionais = getAreas(empresaId);
	
		for (IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVaga : indicadoresMotivos)
		{
			Long areaId = indicadorDuracaoPreenchimentoVaga.getAreaOrganizacional().getId();
			indicadorDuracaoPreenchimentoVaga.getAreaOrganizacional().setNome(mapNomesAreasOrganizacionais.get(areaId));
		}
	}
}