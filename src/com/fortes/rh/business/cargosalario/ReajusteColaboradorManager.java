package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.Empresa;

public interface ReajusteColaboradorManager extends GenericManager<ReajusteColaborador>
{
	Collection<ReajusteColaborador> findByGruposAreas(HashMap<Object, Object> parametros);
	Collection<ReajusteColaborador> findByIdEstabelecimentoAreaGrupo(Long tabelaReajusteColaboradorId, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, int filtraPor);
	void insertSolicitacaoReajuste(ReajusteColaborador reajusteColaborador) throws Exception;
	Collection<ReajusteColaborador> aplicarDissidio(Collection<HistoricoColaborador> historicoColaboradores, TabelaReajusteColaborador tabelaReajusteColaborador, char dissidioPor, Double valorDissidio) throws Exception;
	void deleteByColaboradoresTabelaReajuste(Long[] colaboradorIds, Long tabelaReajusteColaboradorId);
	ReajusteColaborador findByIdProjection(Long reajusteColaboradorId);
	void updateReajusteColaborador(ReajusteColaborador reajusteColaborador) throws Exception;
	Collection<ReajusteColaborador> ordenaPorEstabelecimentoAreaOrGrupoOcupacional(Long empresaId, Collection<ReajusteColaborador> reajusteColaboradors, String filtro) throws Exception;
	Map<String, Object> getParametrosRelatorio(String nomeRelatorio, Empresa empresa, String nomeFiltro);
	void updateFromHistoricoColaborador(HistoricoColaborador historicoColaborador);
	Double calculaSalarioProposto(ReajusteColaborador reajusteColaborador);
	void validaSolicitacaoReajuste(ReajusteColaborador reajusteColaborador) throws Exception;
}