package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.util.DateUtil;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.PcmatManager;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.exception.FortesException;

public class PcmatManagerImpl extends GenericManagerImpl<Pcmat, PcmatDao> implements PcmatManager
{
	private FasePcmatManager fasePcmatManager;
	
	public Collection<Pcmat> findByObra(Long obraId) 
	{
		return getDao().findByObra(obraId);
	}

	public void validaDataMaiorQueUltimoHistorico(Long pcmatId, Long obraId, Date aPartirDe) throws FortesException 
	{
		Pcmat ultimoPcmat = getDao().findUltimoHistorico(pcmatId, obraId);
		
		if (ultimoPcmat != null && !ultimoPcmat.getAPartirDe().before(aPartirDe)) 
			throw new FortesException("Somente é possível cadastrar um PCMAT após a data "+DateUtil.formataDiaMesAno(ultimoPcmat.getAPartirDe()) + ".");		
	}
	
	public Pcmat findUltimoHistorico(Long pcmatId, Long obraId)
	{
		return getDao().findUltimoHistorico(pcmatId, obraId);
	}

	public void clonar(Long id) 
	{
		Pcmat pcmatDestino = (Pcmat) getDao().findById(id).clone();
		pcmatDestino.setId(null);
		getDao().save(pcmatDestino);
		
		fasePcmatManager.clonar(id, pcmatDestino.getId());
	}

	public void setFasePcmatManager(FasePcmatManager fasePcmatManager) {
		this.fasePcmatManager = fasePcmatManager;
	}
}
