package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;

public interface ColaboradorOcorrenciaDao extends GenericDao<ColaboradorOcorrencia>
{
	Collection<ColaboradorOcorrencia> findByColaborador(Long colaboradorId);
	Collection<ColaboradorOcorrencia> filtrar(Long[] ocorrenciaCheckLong, Long[] colaboradorCheckLong, Long[] estabelecimentoCheckLong, Map parametros);
	Collection<ColaboradorOcorrencia> findProjection(int page, int pagingSize, Long colaboradorId);
	ColaboradorOcorrencia findByIdProjection(Long colaboradorOcorrenciaId);
	ColaboradorOcorrencia findByDadosAC(Date dataIni, String ocorrenciaCodigoAC, String colaboradorCodigoAC, String empresaCodigoAC, String grupoAC);
	boolean verifyExistsMesmaData(Long colaboradorOcorrenciaId, Long colaboradorId, Long ocorrenciaId, Long empresaId, Date dataIni);
}