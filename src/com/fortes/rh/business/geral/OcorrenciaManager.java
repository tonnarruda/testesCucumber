package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.security.spring.aop.callback.OcorrenciaAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface OcorrenciaManager extends GenericManager<Ocorrencia>
{
	@Audita(operacao="Inserção/Atualização", auditor=OcorrenciaAuditorCallbackImpl.class)
	void saveOrUpdate(Ocorrencia ocorrencia, Empresa empresa) throws Exception;
	
	@Audita(operacao="Remoção", auditor=OcorrenciaAuditorCallbackImpl.class)
	void remove(Ocorrencia ocorrencia, Empresa empresa) throws Exception;
	
	boolean removeByCodigoAC(String codigo, Long empresaId);
	Ocorrencia findByCodigoAC(String codigo, String codigoEmpresa, String grupoAC);
	Ocorrencia saveFromAC(Ocorrencia ocorrencia) throws Exception;
	public void sincronizar(Long empresaOrigemId, Empresa empresaDestino, String[] tipoOcorrenciasCheck) throws Exception; 
	Collection<Ocorrencia> findAllSelect(Long[] empresaIds);
	Collection<Ocorrencia> findOcorrenciasComAbseteismo(Long empresaId);
	Collection<Ocorrencia> find(int page, int pagingSize, Ocorrencia ocorrencia, Long empresaId);
	Integer getCount(Ocorrencia ocorrencia, Long empresaId);
	Collection<Ocorrencia> findSemCodigoAC(Long empresaId, Boolean integraAC);
	Collection<Ocorrencia> findComCodigoAC(Long empresaId);
}