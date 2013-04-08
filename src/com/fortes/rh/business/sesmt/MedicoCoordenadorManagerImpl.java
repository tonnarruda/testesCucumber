package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.model.sesmt.MedicoCoordenador;

public class MedicoCoordenadorManagerImpl extends GenericManagerImpl<MedicoCoordenador, MedicoCoordenadorDao> implements MedicoCoordenadorManager
{
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
		return getDao().findByEmpresa(empresaId, "desc");
	}

	public File getAssinaturaDigital(Long id) throws Exception
	{
		return getDao().getFile("assinaturaDigital", id);
	}
	
	public Collection<MedicoCoordenador> getMedicosAteData(Long empresaId, Date data, Date dataDesligamento)
	{
		Collection<MedicoCoordenador> medicoCoordenadors = getDao().findByEmpresa(empresaId, "asc");
		Collection<MedicoCoordenador> resultado = new ArrayList<MedicoCoordenador>();
		
		for (MedicoCoordenador medicoCoordenador : medicoCoordenadors)
		{
			if (medicoCoordenador.getInicio().compareTo(data) <= 0)
			{
				if (medicoCoordenador.getFim() != null && medicoCoordenador.getFim().compareTo(data) > 0)
					medicoCoordenador.setFim(null);
				
				medicoCoordenador.setDataDesligamento(dataDesligamento);
				resultado.add(medicoCoordenador);
			}
			else
				break;
		}
		
		return resultado;
	}

}