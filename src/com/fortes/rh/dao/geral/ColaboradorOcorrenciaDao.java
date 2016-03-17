package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.relatorio.Absenteismo;

public interface ColaboradorOcorrenciaDao extends GenericDao<ColaboradorOcorrencia>
{
	Collection<ColaboradorOcorrencia> findByColaborador(Long colaboradorId);
	Collection<ColaboradorOcorrencia> filtrar(Long[] ocorrenciaCheckLong, Long[] colaboradorCheckLong, Long[] estabelecimentoCheckLong, Map parametros);
	Collection<ColaboradorOcorrencia> findProjection(int page, int pagingSize, Long colaboradorId);
	ColaboradorOcorrencia findByIdProjection(Long colaboradorOcorrenciaId);
	ColaboradorOcorrencia findByDadosAC(Date dataIni, String ocorrenciaCodigoAC, String colaboradorCodigoAC, String empresaCodigoAC, String grupoAC);
	Collection<Absenteismo> countFaltasByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<Long> ocorrenciasIds);
	boolean verifyExistsMesmaData(Long colaboradorOcorrenciaId, Long colaboradorId, Long ocorrenciaId, Long empresaId, Date dataIni);
	void deleteByOcorrencia(Long[] ocorrenciaIds) throws Exception;
	Collection<ColaboradorOcorrencia> findColaboradorOcorrencia(Collection<Long> ocorrenciaIds, Collection<Long> colaboradorIds, Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> areaIds, Collection<Long> estabelecimentoIds, boolean detalhamento, Character agruparPor);
	Collection<ColaboradorOcorrencia> findByFiltros(int page, int pagingSize, String colaboradorNome, String ocorrenciaDescricao, Boolean comProvidencia, Long[] colaboradoresIds, Long empresaId);
	Collection<ColaboradorOcorrencia> findByEmpresaId(Long empresaId);
}