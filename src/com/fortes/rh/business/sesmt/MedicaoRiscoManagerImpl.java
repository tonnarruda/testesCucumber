package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.MedicaoRiscoDao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;

@Component
public class MedicaoRiscoManagerImpl extends GenericManagerImpl<MedicaoRisco, MedicaoRiscoDao> implements MedicaoRiscoManager
{
	private PlatformTransactionManager transactionManager;
	private RiscoMedicaoRiscoManager riscoMedicaoRiscoManager;
	
	@Autowired
	MedicaoRiscoManagerImpl(MedicaoRiscoDao medicaoRiscoDao) {
			setDao(medicaoRiscoDao);
	}
	
	@Override
	public MedicaoRisco findById(Long id) 
	{
		return getDao().findByIdProjection(id);
	}	
	
	public Collection<MedicaoRisco> findAllSelectByAmbiente(Long empresaId, Long ambienteId) 
	{
		if (ambienteId != null && ambienteId.equals(-1L))
			ambienteId = null;
		
		return getDao().findAllSelectByAmbiente(empresaId, ambienteId);
	}
	
	public Collection<MedicaoRisco> findAllSelectByFuncao(Long empresaId, Long funcaoId) 
	{
		if (funcaoId != null && funcaoId.equals(-1L))
			funcaoId = null;
		
		return getDao().findAllSelectByFuncao(empresaId, funcaoId);
	}


	public void save(MedicaoRisco medicaoRisco, String[] riscoIds, String[] ltcatValues, String[] ppraValues, String[] tecnicaValues, String[] intensidadeValues) throws Exception 
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try 
		{
			Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
			
			riscoMedicaoRiscoManager.removeByMedicaoRisco(medicaoRisco.getId());
			
			for (int i=0; i < riscoIds.length; i++)
			{
				Risco risco = new Risco();
				risco.setId(Long.parseLong(riscoIds[i]));
				
				RiscoMedicaoRisco riscoMedicaoRisco = new RiscoMedicaoRisco();
				riscoMedicaoRisco.setRisco(risco);
				riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
				
				riscoMedicaoRisco.setIntensidadeMedida(intensidadeValues[i]);
				riscoMedicaoRisco.setDescricaoLtcat(ltcatValues[i]);
				riscoMedicaoRisco.setDescricaoPpra(ppraValues[i]);
				riscoMedicaoRisco.setTecnicaUtilizada(tecnicaValues[i]);
				
				riscoMedicaoRiscos.add(riscoMedicaoRisco);
			}
			
			medicaoRisco.setRiscoMedicaoRiscos(riscoMedicaoRiscos);
			
			if (medicaoRisco.getId() == null)
				getDao().save(medicaoRisco);
			else
				getDao().update(medicaoRisco);
			
			transactionManager.commit(status);
		} 
		catch (Exception e) 
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw e;
		}
	}
	
	public String getTecnicasUtilizadas(Long empresaId) 
	{
		Collection<String> tecnicasUtilizadas = getDao().findTecnicasUtilizadasDistinct(empresaId);
		
		if(tecnicasUtilizadas == null || tecnicasUtilizadas.isEmpty())
			return "";
		else
			return StringUtil.converteCollectionToStringComAspas(tecnicasUtilizadas);
	}

	public Collection<RiscoMedicaoRisco> preparaRiscosDaMedicao(MedicaoRisco medicaoRisco, Collection<Risco> riscos) 
	{
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		
		if (medicaoRisco.getRiscoMedicaoRiscos() != null && riscos != null)
		{
			// Popula apenas com as medições de riscos que estão atualmente no Ambiente 
			for (RiscoMedicaoRisco riscoMedicao : medicaoRisco.getRiscoMedicaoRiscos())
			{
				if (riscos.contains(riscoMedicao.getRisco()))
					riscoMedicaoRiscos.add(riscoMedicao);
			}
		}
		
		for (Risco risco : riscos)
		{
			boolean riscoExiste = false;
			
			for (RiscoMedicaoRisco riscoMedicaoRisco : riscoMedicaoRiscos)
			{
				if (riscoMedicaoRisco.getRisco().equals(risco))
				{
					riscoExiste = true;
					break;
				}
			}
			
			if (!riscoExiste)
				riscoMedicaoRiscos.add(new RiscoMedicaoRisco(medicaoRisco, risco));
		}
		
		riscoMedicaoRiscos = new CollectionUtil<RiscoMedicaoRisco>().sortCollectionStringIgnoreCase(riscoMedicaoRiscos, "risco.descricao");
			
		return riscoMedicaoRiscos;
	}
	
	public void removeCascade(Long id) 
	{
		MedicaoRisco medicaoRisco = findById(id);
		getDao().remove(medicaoRisco);
	}

	public MedicaoRisco getFuncaoByMedicaoRisco(Long medicaoRiscoId) 
	{
		return getDao().getFuncaoByMedicaoRisco(medicaoRiscoId);
	}
	
	public Collection<RiscoMedicaoRisco> findRiscoMedicaoRiscos(Long medicaoRiscoId) 
	{
		return getDao().findRiscoMedicaoRiscos(medicaoRiscoId);
	}
	
	public void setRiscoMedicaoRiscoManager(RiscoMedicaoRiscoManager riscoMedicaoRiscoManager) 
	{
		this.riscoMedicaoRiscoManager = riscoMedicaoRiscoManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) 
	{
		this.transactionManager = transactionManager;
	}
}
