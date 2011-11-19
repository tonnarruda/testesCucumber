package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CnpjUtil;
import com.fortes.web.tags.CheckBox;

public class EstabelecimentoManagerImpl extends GenericManagerImpl<Estabelecimento, EstabelecimentoDao> implements EstabelecimentoManager
{
	public boolean remove(String codigo, Long idEmpresa)
	{
		return getDao().remove(codigo, idEmpresa);
	}

	public Estabelecimento findByCodigo(String codigo, String empCodigo, String grupoAC)
	{
		return getDao().findByCodigo(codigo, empCodigo, grupoAC);
	}

	public Collection<Estabelecimento> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public Estabelecimento findEstabelecimentoCodigoAc(Long estabelecimentoId)
	{
		return getDao().findEstabelecimentoCodigoAc(estabelecimentoId);
	}

	public boolean verificaCnpjExiste(String complemento, Long id, Long empresaId)
	{
		return getDao().verificaCnpjExiste(complemento, id, empresaId);
	}

	public String calculaDV(String cnpj)
	{
		return CnpjUtil.calculaDigitoVerificador(cnpj);
	}

	public Collection<CheckBox> populaCheckBox(Long empresaId)
	{
		try
		{
			Collection<Estabelecimento> estabelecimentosTmp = getDao().findAllSelect(empresaId);
			return CheckListBoxUtil.populaCheckListBox(estabelecimentosTmp, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	public Estabelecimento findEstabelecimentoByCodigoAc(String estabelecimentoCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		return getDao().findEstabelecimentoByCodigoAc(estabelecimentoCodigoAC, empresaCodigoAC, grupoAC);
	}

	public Collection<Estabelecimento> findAllSelect(Long[] empresaIds)
	{
		if(empresaIds == null || empresaIds.length == 0)
			return new ArrayList<Estabelecimento>();
		else
			return getDao().findAllSelect(empresaIds);
	}

	public Collection<CheckBox> populaCheckBox(Long[] empresaIds)
	{
		try
		{
			Collection<Estabelecimento> estabelecimentosTmp = getDao().findAllSelect(empresaIds);
			return CheckListBoxUtil.populaCheckListBox(estabelecimentosTmp, "getId", "getDescricaoComEmpresa");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ArrayList<CheckBox>();
	}

	public String nomeEstabelecimentos(Long[] estabelecimentoIds)
	{
		Collection<Estabelecimento> estabelecimentos = getDao().findEstabelecimentos(estabelecimentoIds);
		String resultado = "";
		
		for (Estabelecimento estabelecimento : estabelecimentos) 
			resultado += estabelecimento.getNome() + ", ";
		
		if(!resultado.equals(""))
			return resultado.substring(0, (resultado.length() - 2));
		else
			return resultado;
	}

	public Collection<Estabelecimento> findSemCodigoAC(Long empresaId) {
		return getDao().findSemCodigoAC(empresaId);
	}
}