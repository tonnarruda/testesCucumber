package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.HistoricoExtintorDao;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.util.DateUtil;

public class HistoricoExtintorManagerImpl extends GenericManagerImpl<HistoricoExtintor, HistoricoExtintorDao> implements HistoricoExtintorManager
{
	public void update(HistoricoExtintor historicoExtintor) 
	{
		if(historicoExtintor.getHoraString() != null)
			historicoExtintor.setData(DateUtil.montaDataByStringComHora(DateUtil.formataDiaMesAno(historicoExtintor.getData()),historicoExtintor.getHoraString()));
		
		getDao().update(historicoExtintor);
	}
	
	public Collection<HistoricoExtintor> findByExtintor(Long extintorId) 
	{
		return getDao().findByExtintor(extintorId);
	}

	public void removeByExtintor(Long extintorId) 
	{
		getDao().removeByExtintor(extintorId);
	}

	public Collection<HistoricoExtintor> findAllHistoricosAtuais(Integer page, Integer pagingSize, Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId) 
	{
		return getDao().findAllHistoricosAtuais(page, pagingSize, data, localizacao, extintorTipo, estabelecimentoId, empresaId);
	}
	
	public Integer countAllHistoricosAtuais(Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId) 
	{
		return getDao().countAllHistoricosAtuais(data, localizacao, extintorTipo, estabelecimentoId, empresaId);
	}

	public void insertNewHistoricos(Long[] extintorsCheck, HistoricoExtintor historicoTemp) 
	{
		Extintor extintor;
		HistoricoExtintor historicoExtintor;
		for (Long extintorId : extintorsCheck) 
		{
			extintor = new Extintor();
			extintor.setId(extintorId);
			
			historicoExtintor = new HistoricoExtintor();
			historicoExtintor.setId(null);
			historicoExtintor.setExtintor(extintor);
			
			historicoExtintor.setData(DateUtil.montaDataByStringComHora(DateUtil.formataDiaMesAno(historicoTemp.getData()),historicoTemp.getHoraString()));
			historicoExtintor.setEstabelecimento(historicoTemp.getEstabelecimento());
			historicoExtintor.setLocalizacao(historicoTemp.getLocalizacao());
			
			save(historicoExtintor);
		}
	}
}