package com.fortes.rh.test.factory.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.model.type.File;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.MedicoCoordenador;

public class MedicoCoordenadorFactory
{
	public static MedicoCoordenador getEntity() {
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();

		return medicoCoordenador;
	}

	public static MedicoCoordenador getEntity(Long id) {
		MedicoCoordenador medicoCoordenador = getEntity();
		medicoCoordenador.setId(id);

		return medicoCoordenador;
	}

	public static MedicoCoordenador getEntity(Empresa empresa, Date dataInicio, String estabelecimentoResponsavel, Collection<Estabelecimento> estabelecimentos) {
		MedicoCoordenador medicoCoordenador = getEntity();
		medicoCoordenador.setEmpresa(empresa);
		medicoCoordenador.setInicio(dataInicio);
		medicoCoordenador.setEstabelecimentoResponsavel(estabelecimentoResponsavel);
		medicoCoordenador.setEstabelecimentos(estabelecimentos);

		return medicoCoordenador;
	}
	
	public static MedicoCoordenador getEntity(Long id, String estabelecimentoResponsavel, File assinaturaDigital) {
		MedicoCoordenador medicoCoordenador = getEntity(id);
		medicoCoordenador.setEstabelecimentoResponsavel(estabelecimentoResponsavel);
		medicoCoordenador.setAssinaturaDigital(assinaturaDigital);
		
		return medicoCoordenador;
	}
}