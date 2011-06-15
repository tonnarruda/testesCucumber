package com.fortes.rh.model.cargosalario.relatorio;

import java.io.Serializable;

import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;

// Utilizado para gerar Relatorio de Promoções
public class RelatorioPromocoes implements Serializable
{
	private Estabelecimento estabelecimento;
	private AreaOrganizacional area;
	private int qtdHorizontal;
	private int qtdVertical;

	public RelatorioPromocoes(Estabelecimento estabelecimento, AreaOrganizacional area) 
	{
		super();
		this.estabelecimento = estabelecimento;
		this.area = area;
	}
	public RelatorioPromocoes() 
	{
		super();
	}

	public AreaOrganizacional getArea()
	{
		return area;
	}
	public void setArea(AreaOrganizacional area)
	{
		this.area = area;
	}
	public int getQtdHorizontal()
	{
		return qtdHorizontal;
	}
	public void setQtdHorizontal(int qtdHorizontal)
	{
		this.qtdHorizontal = qtdHorizontal;
	}
	public int getQtdVertical()
	{
		return qtdVertical;
	}
	public void setQtdVertical(int qtdVertical)
	{
		this.qtdVertical = qtdVertical;
	}
	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}
	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

}
