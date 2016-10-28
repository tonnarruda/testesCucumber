package com.fortes.rh.web.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.util.CollectionUtil;

@Component
@SuppressWarnings("unchecked")
public class RiscoAmbienteDWR 
{
	@Autowired
	private RiscoAmbienteManager riscoAmbienteManager;
	
	public Map<Object, Object> getRiscosByAmbienteData(Long ambienteId, String data) throws Exception
	{
		Date dataValida = null; 
		try 
		{
			dataValida = new SimpleDateFormat().parse(data);
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
			throw new Exception("Data inválida.");
		}
		
		Collection<Risco> riscos = riscoAmbienteManager.findRiscosByAmbienteData(ambienteId, dataValida);
		
		return new CollectionUtil<Risco>().convertCollectionToMap(riscos, "getId", "getDescricao");
	}

	public void setRiscoAmbienteManager(RiscoAmbienteManager riscoAmbienteManager) {
		this.riscoAmbienteManager = riscoAmbienteManager;
	}
}
