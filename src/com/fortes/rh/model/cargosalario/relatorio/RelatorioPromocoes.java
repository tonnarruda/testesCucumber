package com.fortes.rh.model.cargosalario.relatorio;

import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;

// Utilizado para gerar Relatorio de Promoções
public class RelatorioPromocoes implements Comparable<RelatorioPromocoes>
{
	private Estabelecimento estabelecimento;
	private AreaOrganizacional area;
	private int qtdHorizontal;
	private int qtdVertical;

	public void incrementa(String tipoPromocao) 
	{
		if(tipoPromocao.equals(MotivoHistoricoColaborador.PROMOCAO))//vertical
			qtdVertical++;
		else if(tipoPromocao.equals(MotivoHistoricoColaborador.PROMOCAO_HORIZONTAL))
			qtdHorizontal++;
	}

	public RelatorioPromocoes(Estabelecimento estabelecimento, AreaOrganizacional area, String tipoPromocao) 
	{
		super();
		this.estabelecimento = estabelecimento;
		this.area = area;
		incrementa(tipoPromocao);
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

	public int compareTo(RelatorioPromocoes relatorio) 
	{
		return (estabelecimento.getNome() + " " + area.getDescricao()).compareTo(relatorio.getEstabelecimento().getNome() + " " + relatorio.getArea().getDescricao());
	}

}
