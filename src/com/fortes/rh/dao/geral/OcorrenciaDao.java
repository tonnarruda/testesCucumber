package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Ocorrencia;

public interface OcorrenciaDao extends GenericDao<Ocorrencia>
{
	boolean removeByCodigoAC(String codigo, Long id);
	Ocorrencia findByCodigoAC(String codigo, String codigoEmpresa, String grupoAC);
	public Collection<Ocorrencia> findSincronizarOcorrenciaInteresse(Long empresaOrigemId);
	Collection<Ocorrencia> findAllSelect(Long[] empresaIds);
	Collection<Ocorrencia> findOcorrenciasComAbseteismo(Long empresaId);
	Collection<Ocorrencia> find(int page, int pagingSize, Ocorrencia ocorrencia, Long empresaId);
	Integer getCount(Ocorrencia ocorrencia, Long empresaId);
	Collection<Ocorrencia> findSemCodigoAC(Long empresaId);
	public Collection<Ocorrencia> findComCodigoAC(Long empresaId);
}