package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.web.tags.CheckBox;

public interface EstabelecimentoManager extends GenericManager<Estabelecimento>
{
	boolean remove(String codigo, Long idEmpresa);
	Estabelecimento findByCodigo(String codigo, String empCodigo, String grupoAC);
	Collection<Estabelecimento> findAllSelect(Long empresaId);
	Estabelecimento findEstabelecimentoCodigoAc(Long estabelecimentoId);
	boolean verificaCnpjExiste(String complemento, Long id, Long empresaId);
	String calculaDV(String cnpj);
	Collection<CheckBox> populaCheckBox(Long empresaId);
	Estabelecimento findEstabelecimentoByCodigoAc(String estabelecimentoCodigoAC, String empresaCodigoAC, String grupoAC);
	Collection<Estabelecimento> findAllSelect(Long[] empresaIds);
	Collection<CheckBox> populaCheckBox(Long[] empresaIds);
	public String nomeEstabelecimentos(Long[] estabelecimentoIds);
	Collection<Estabelecimento> findSemCodigoAC(Long empresaId);
	void deleteEstabelecimento(Long[] estabelecimentoIds) throws Exception;
}