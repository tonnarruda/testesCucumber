package com.fortes.rh.model.sesmt.relatorio;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

public class FichaEpiRelatorio
{
	private Colaborador colaborador;
	private Empresa empresa;
	private boolean imprimirVerso;

	public FichaEpiRelatorio()
	{
	}

	public FichaEpiRelatorio(Colaborador colaborador, Empresa empresa)
	{
		this.colaborador = colaborador;
		this.empresa = empresa;
	}

	public String getEmpresaRazaoSocialOuNome()
	{
		if (empresa != null)
		{
			String razaoSocialOuNome = empresa.getRazaoSocial();
			return  (StringUtils.isNotBlank(razaoSocialOuNome) ? razaoSocialOuNome : empresa.getNome());
		}
		return "";
	}

	public String getColaboradorDataAdmissaoFormatada()
	{
		String dataAdmissaoFmt = "";
		if (colaborador.getDataAdmissao() != null)
			dataAdmissaoFmt = DateUtil.formataDiaMesAno(colaborador.getDataAdmissao());

		return dataAdmissaoFmt;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public boolean isImprimirVerso() {
		return imprimirVerso;
	}

	public void setImprimirVerso(boolean imprimirVerso) {
		this.imprimirVerso = imprimirVerso;
	}
}
