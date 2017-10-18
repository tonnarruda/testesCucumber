package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;

public class EngenheiroResponsavelManagerImpl extends GenericManagerImpl<EngenheiroResponsavel, EngenheiroResponsavelDao> implements EngenheiroResponsavelManager
{
	public EngenheiroResponsavel findByIdProjection(Long engenheiroResponsavelId)
	{
		return getDao().findByIdProjection(engenheiroResponsavelId);
	}
	
	public Collection<EngenheiroResponsavel> findResponsaveisPorEstabelecimento(Colaborador colaborador, Date data)
	{
		Collection<EngenheiroResponsavel> engenheiroResponsavels = getDao().findResponsaveisPorEstabelecimento(colaborador.getEmpresa().getId(), colaborador.getHistoricoColaborador().getEstabelecimento().getId());
		Collection<EngenheiroResponsavel> resultado = new ArrayList<EngenheiroResponsavel>();

		for (EngenheiroResponsavel engenheiroResponsavel : engenheiroResponsavels) {

			if (engenheiroResponsavel.getInicio().compareTo(data) <= 0) { 
				if (colaborador.getDataAdmissao().compareTo(data) <= 0 
						&& (colaborador.getDataDesligamento() == null || engenheiroResponsavel.getInicio().compareTo(colaborador.getDataDesligamento()) <= 0) 
						&& (engenheiroResponsavel.getFim() == null || colaborador.getDataAdmissao().compareTo(engenheiroResponsavel.getFim()) <= 0)) 
				{
					resultado.add(engenheiroResponsavel);
				} 
			}
			else
				break;
		}

		return resultado;
	}

}