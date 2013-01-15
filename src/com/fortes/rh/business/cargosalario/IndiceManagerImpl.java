package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.web.tags.CheckBox;

public class IndiceManagerImpl extends GenericManagerImpl<Indice, IndiceDao> implements IndiceManager
{
	private IndiceHistoricoManager indiceHistoricoManager;

	public void save(Indice indice, IndiceHistorico indiceHistorico) throws Exception
	{
		try
		{
			indice = save(indice);

			indiceHistorico.setIndice(indice);
			indiceHistoricoManager.save(indiceHistorico);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public void removeIndice(Long indiceId) throws Exception
	{
		indiceHistoricoManager.remove(getIdsIndiceHistorico(indiceId));
		remove(indiceId);
	}

	private Long[] getIdsIndiceHistorico(Long indiceId)
	{
		Collection<IndiceHistorico> indiceHistoricos = indiceHistoricoManager.findAllSelect(indiceId);

		CollectionUtil<IndiceHistorico> indiceHistoricoUtil = new CollectionUtil<IndiceHistorico>();

		return indiceHistoricoUtil.convertCollectionToArrayIds(indiceHistoricos);
	}

	public void setIndiceHistoricoManager(IndiceHistoricoManager indiceHistoricoManager)
	{
		this.indiceHistoricoManager = indiceHistoricoManager;
	}

	public Indice findByIdProjection(Long indiceId)
	{
		return getDao().findByIdProjection(indiceId);
	}

	public Indice findByCodigo(String codigo, String grupoAC)
	{
		return getDao().findByCodigo(codigo, grupoAC);
	}

	public boolean remove(String codigo, String grupoAC)
	{
		return getDao().remove(codigo, grupoAC);
	}

	public Indice findHistoricoAtual(Long indiceId)
	{
		return getDao().findHistoricoAtual(indiceId, new Date());
	}
	
	public Collection<Indice> findComHistoricoAtual(Long[] indicesIds) 
	{
		return getDao().findComHistoricoAtual(indicesIds);
	}

	public Indice findHistorico(Long indiceId, Date dataHistorico)
	{
		return getDao().findHistoricoAtual(indiceId, dataHistorico);
	}

	public Indice getCodigoAc(Indice indice)
	{
		return getDao().findByIdProjection(indice.getId());
	}

	public Indice findIndiceByCodigoAc(String indiceCodigoAC, String grupoAC)
	{
		return getDao().findIndiceByCodigoAc(indiceCodigoAC, grupoAC);
	}

	public Collection<Indice> findAll(Empresa empresa) 
	{
		if(empresa.isAcIntegra())
			return find(new String[] {"grupoAC"}, new Object[] {empresa.getGrupoAC()}, new String[] {"nome"});
		else
			return findAll(new String[] {"nome"});
	}

	public Collection<Indice> findSemCodigoAC(Empresa empresa) 
	{
		return getDao().findSemCodigoAC(empresa);
	}

	public void deleteIndice(Long[] indiceIds) throws Exception 
	{
		if (indiceIds != null && indiceIds.length > 0) {
			indiceHistoricoManager.deleteByIndice(indiceIds);
			
			getDao().remove(indiceIds);
		}
		
	}

	public String findCodigoACDuplicado(Empresa empresa) 
	{
		return getDao().findCodigoACDuplicado(empresa);
	}

	public Collection<CheckBox> findOpcoesDissidio(Empresa empresa) 
	{
		Collection<Indice> indices = getDao().findComHistoricoAtual(empresa);
		Collection<CheckBox> checkboxes = new ArrayList<CheckBox>();
		CheckBox checkBox;
		
		for (Indice indice : indices) 
		{
			checkBox = new CheckBox();
			checkBox.setId(indice.getId());
			checkBox.setNome(indice.getNome());
			checkBox.setDesabilitado(true);
			
			if (false)
				checkBox.setTitulo("Esse índice possui um realinhamento pendente");
			else if (indice.getIndiceHistoricoAtual() == null || indice.getIndiceHistoricoAtual().getId() == null)
				checkBox.setTitulo("Esse índice não possui histórico");
			else
				checkBox.setDesabilitado(false);
			
			checkboxes.add(checkBox);
		}
		
		return checkboxes;
	}
}