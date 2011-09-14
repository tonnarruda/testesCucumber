package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.web.tags.CheckBox;

public interface OcorrenciaManager extends GenericManager<Ocorrencia>
{
	void saveOrUpdate(Ocorrencia ocorrencia, Empresa empresa) throws Exception;
	boolean removeByCodigoAC(String codigo, Long empresaId);
	Ocorrencia findByCodigoAC(String codigo, String codigoEmpresa, String grupoAC);
	Ocorrencia saveFromAC(Ocorrencia ocorrencia) throws Exception;
	void remove(Ocorrencia ocorrencia, Empresa empresa) throws Exception;
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId); 
	Collection<Ocorrencia> findAllSelect(Long[] empresaIds);
	Collection<Ocorrencia> findOcorrenciasComAbseteismo(Long empresaId);
}