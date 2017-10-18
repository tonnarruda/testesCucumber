package com.fortes.rh.test.factory.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;

public class EngenheiroResponsavelFactory
{
	public static EngenheiroResponsavel getEntity() {
		EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();

		return engenheiroResponsavel;
	}

	public static EngenheiroResponsavel getEntity(Long id) {
		EngenheiroResponsavel engenheiroResponsavel = getEntity();
		engenheiroResponsavel.setId(id);

		return engenheiroResponsavel;
	}

	public static EngenheiroResponsavel getEntity(Empresa empresa, Date dataInicio, String estabelecimentoResponsavel, Collection<Estabelecimento> estabelecimentos) {

		EngenheiroResponsavel engenheiroResponsavel = getEntity();
		engenheiroResponsavel.setEmpresa(empresa);
		engenheiroResponsavel.setInicio(dataInicio);
		engenheiroResponsavel.setEstabelecimentoResponsavel(estabelecimentoResponsavel);
		engenheiroResponsavel.setEstabelecimentos(estabelecimentos);

		return engenheiroResponsavel;
	}
}