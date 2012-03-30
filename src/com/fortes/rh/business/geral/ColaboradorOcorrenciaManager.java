package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.Absenteismo;

public interface ColaboradorOcorrenciaManager extends GenericManager<ColaboradorOcorrencia>
{
	Collection<ColaboradorOcorrencia> findByColaborador(Long id);
	Collection<ColaboradorOcorrencia> filtrar(Long[] ocorrenciaCheckLong, Long[] colaboradorCheckLong, Long[] estabelecimentoCheckLong, Map parametros);
	Collection<ColaboradorOcorrencia> findProjection(int page, int pagingSize, Long colaboradorId);
	ColaboradorOcorrencia findByIdProjection(Long colaboradorOcorrenciaId);
	void saveOcorrenciasFromAC(Collection<ColaboradorOcorrencia> colaboradorOcorrencias) throws Exception;
	void saveColaboradorOcorrencia(ColaboradorOcorrencia colaboradorOcorrencia, Empresa empresa) throws Exception;
	void removeFromAC(Collection<ColaboradorOcorrencia> colaboradorOcorrencias) throws Exception;
	void remove(ColaboradorOcorrencia colaboradorOcorrencia, Empresa empresa) throws Exception;
	boolean verifyExistsMesmaData(Long colaboradorOcorrenciaId, Long colaboradorId, Long ocorrenciaId, Long empresaId, Date dataIni);
	Collection<Absenteismo> montaAbsenteismo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> ocorrenciasIds, Collection<Long> afastamentosIds) throws Exception;
	Collection<Object[]> montaGraficoAbsenteismo(String dataMesAnoIni, String dataMesAnoFim, Collection<Long> empresaIds, Collection<Long> areasIds);
	void deleteOcorrencias(Long[] ocorrenciaIds) throws Exception;
	Collection<ColaboradorOcorrencia> filtrarOcorrencias(Collection<Long> empresaIds, Date dataIni, Date dataFim, Collection<Long> ocorrenciaIds, Collection<Long> areaIds, Collection<Long> estabelecimentoIds, Collection<Long> colaboradorIds, boolean detalhamento);
}