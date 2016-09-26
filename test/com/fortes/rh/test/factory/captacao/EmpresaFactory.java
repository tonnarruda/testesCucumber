package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.geral.Empresa;

public class EmpresaFactory
{
	public static Empresa getEmpresa()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa.setEmailRespRH("responsavel@email.com.br");

		return empresa;
	}

	public static Empresa getEmpresa(Long id)
	{
		Empresa empresa = getEmpresa();
		empresa.setId(id);

		return empresa;
	}
	
	public static Empresa getEmpresa(Long id, String nome, String codigoAC, String grupoAC)
	{
		Empresa empresa = getEmpresa(id);
		empresa.setCodigoAC(codigoAC);
		empresa.setGrupoAC(grupoAC);

		return empresa;
	}

	public static Empresa getEmpresa(String nome, String cnpj, String razaoSocial) {
		Empresa empresa = new Empresa();
		empresa.setNome(nome);
		empresa.setCnpj(cnpj);
		empresa.setRazaoSocial(razaoSocial);

		return empresa;
	}
}
