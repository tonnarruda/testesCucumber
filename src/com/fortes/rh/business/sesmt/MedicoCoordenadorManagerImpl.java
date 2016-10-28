package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.MedicoCoordenador;

@Component
public class MedicoCoordenadorManagerImpl extends GenericManagerImpl<MedicoCoordenador, MedicoCoordenadorDao> implements MedicoCoordenadorManager
{
	@Autowired
	MedicoCoordenadorManagerImpl(MedicoCoordenadorDao medicoCoordenadorDao) {
			setDao(medicoCoordenadorDao);
	}
	
	public MedicoCoordenador findByDataEmpresa(Long empresaId, Date data)
	{
		return getDao().findByDataEmpresa(empresaId, data);
	}

	public MedicoCoordenador findByIdProjection(Long medicoCoordenadorId)
	{
		return getDao().findByIdProjection(medicoCoordenadorId);
	}

	public Collection<MedicoCoordenador> findByEmpresa(Long empresaId)
	{
		return getDao().findByEmpresa(empresaId, false);
	}

	public File getAssinaturaDigital(Long id) throws Exception
	{
		return getDao().getFile("assinaturaDigital", id);
	}
	
	public Collection<MedicoCoordenador> getMedicosAteData(Date data, Colaborador colaborador)
	{
		Collection<MedicoCoordenador> medicoCoordenadors = getDao().findByEmpresa(colaborador.getEmpresa().getId(), true);
		Collection<MedicoCoordenador> resultado = new ArrayList<MedicoCoordenador>();
		
		for (MedicoCoordenador medicoCoordenador : medicoCoordenadors)
		{
			if (medicoCoordenador.getInicio().compareTo(data) <= 0) { 
				if (colaborador.getDataAdmissao().compareTo(data) <= 0 
						&& (colaborador.getDataDesligamento() == null || medicoCoordenador.getInicio().compareTo(colaborador.getDataDesligamento()) <= 0) 
						&& (medicoCoordenador.getFim() == null || colaborador.getDataAdmissao().compareTo(medicoCoordenador.getFim()) <= 0)) 
				{
					medicoCoordenador.setDataDesligamento(colaborador.getDataDesligamento());
					resultado.add(medicoCoordenador);
				} 
			}
			else
				break;
		}
		
		return resultado;
	}

}