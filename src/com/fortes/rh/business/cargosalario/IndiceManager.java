package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.callback.IndiceAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.web.tags.CheckBox;

public interface IndiceManager extends GenericManager<Indice>
{
	@Audita(operacao="Inserção", auditor=IndiceAuditorCallbackImpl.class)
	void save(Indice indice, IndiceHistorico indiceHistorico)throws Exception;
	@Audita(operacao="Remoção", auditor=IndiceAuditorCallbackImpl.class)
	void removeIndice(Long indiceId) throws Exception;

	Indice findByIdProjection(Long indiceId);
	Indice findByCodigo(String codigo, String grupoAC);
	boolean remove(String codigo, String grupoAC);
	Indice findHistoricoAtual(Long indiceId);
	Indice getCodigoAc(Indice indice);
	Indice findIndiceByCodigoAc(String indiceCodigoAC, String grupoAC);
	Indice findHistorico(Long indiceId, Date dataHistorico);
	Collection<Indice> findAll(Empresa empresa);
	Collection<Indice> findSemCodigoAC(Empresa empresa);
	void deleteIndice(Long[] indiceIds) throws Exception;
	String findCodigoACDuplicado(Empresa empresa);
	Collection<Indice> findComHistoricoAtual(Long[] indicesIds);
	Collection<Indice> findComHistoricoAtual(Empresa empresa);
}