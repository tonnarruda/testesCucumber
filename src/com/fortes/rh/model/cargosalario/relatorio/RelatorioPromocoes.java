package com.fortes.rh.model.cargosalario.relatorio;

import java.util.Date;

import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;

// Utilizado para gerar Relatorio de Promoções
public class RelatorioPromocoes implements Comparable<RelatorioPromocoes>
{
	private Estabelecimento estabelecimento;
	private AreaOrganizacional area;
	private Date mesAno; //EX: 01/02/2011, 01/03/2012
	private int qtdHorizontal;
	private int qtdVertical;

	public void incrementa(String tipoPromocao) 
	{
		if(tipoPromocao.equals(MotivoHistoricoColaborador.PROMOCAO))//vertical
			qtdVertical++;
		else if(tipoPromocao.equals(MotivoHistoricoColaborador.PROMOCAO_HORIZONTAL))
			qtdHorizontal++;
	}

	public RelatorioPromocoes(Long estabelecimentoId, String estabelecimentoNome, Long areaId, String areaNome, int qtdVertical , int qtdHorizontal) 
	{
		if (this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(estabelecimentoId);
		this.estabelecimento.setNome(estabelecimentoNome);
		
		if (this.area == null)
			this.area = new AreaOrganizacional();
		this.area.setId(areaId);
		this.area.setNome(areaNome);
		
		this.qtdHorizontal = qtdHorizontal;
		this.qtdVertical = qtdVertical;
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

	public RelatorioPromocoes(Date data, String tipoPromocao) 
	{
		super();
		this.mesAno = data;
		incrementa(tipoPromocao);
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

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

}
