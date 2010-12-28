/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ReportColumn implements Serializable, Cloneable
{
	private final static int MAX_SIZE = 780;
	private final static int SPACE = 4;
	private String name;
	private String property;
	private int size;
	private boolean resize;
	
	public ReportColumn(String name, String property, int size, boolean resize) {
		super();
		this.name = name;
		this.property = property;
		this.size = size;
		this.resize = resize;
		
	}

	public static Collection<ReportColumn> getColumns()
	{
		Collection<ReportColumn> columns = new ArrayList<ReportColumn>();
		
		columns.add(new ReportColumn("Nome", "nome", 150, true));
		columns.add(new ReportColumn("Nome Comercial", "nomeComercial", 150, true));
		columns.add(new ReportColumn("Matrícula", "matricula", 40, false));
		columns.add(new ReportColumn("Data Admissão", "dataAdmissaoFormatada", 50, false));
		columns.add(new ReportColumn("Cargo Atual", "faixaSalarial.cargo.nome", 120, true));
		columns.add(new ReportColumn("Estado Civil", "pessoal.estadoCivilDic", 120, false));
		columns.add(new ReportColumn("Nome da Mãe", "pessoal.mae", 150, true));
		columns.add(new ReportColumn("Nome do Pai", "pessoal.pai", 150, true));
		columns.add(new ReportColumn("Data de Desligamento", "dataDesligamentoFormatada", 50, false));
		columns.add(new ReportColumn("Vinculo", "vinculoDescricao", 60, false));
		columns.add(new ReportColumn("Cpf", "pessoal.cpfFormatado", 60, false));
		columns.add(new ReportColumn("Pis", "pessoal.pis", 60, false));
		columns.add(new ReportColumn("Rg", "pessoal.rg", 70, false));
		columns.add(new ReportColumn("Orgão Emissor", "pessoal.rgOrgaoEmissor", 40, false));
		columns.add(new ReportColumn("Deficiência", "pessoal.deficienciaDescricao", 60, false));
		columns.add(new ReportColumn("Data de Expedição(RG)", "pessoal.rgDataExpedicaoFormatada", 50, false));
		columns.add(new ReportColumn("Sexo", "pessoal.sexoDic", 40, false));
		columns.add(new ReportColumn("Data de Nascimento", "pessoal.dataNascimentoFormatada", 50, false));
		columns.add(new ReportColumn("Conjugue", "pessoal.conjuge", 150, true));
		columns.add(new ReportColumn("Qtd de Filhos", "pessoal.qtdFilhosString", 30, false));
		columns.add(new ReportColumn("Número da Habilitação", "habilitacao.numeroHab", 50, false));
		columns.add(new ReportColumn("Emissão da Habilitação", "habilitacao.emissaoFormatada", 50, false));
		columns.add(new ReportColumn("Vencimento da Habilit.", "habilitacao.vencimentoFormatada", 50, false));
		columns.add(new ReportColumn("Categoria da Habilit.", "habilitacao.categoria", 60, false));
		columns.add(new ReportColumn("Logradouro", "endereco.logradouro", 120, true));
		columns.add(new ReportColumn("Comp. do Logradouro", "endereco.complemento", 120, true));
		columns.add(new ReportColumn("Número do Logradouro", "endereco.numero", 60, false));
		columns.add(new ReportColumn("Bairro", "endereco.bairro", 100, false));
		columns.add(new ReportColumn("Cep", "endereco.cepFormatado", 60, false));
		columns.add(new ReportColumn("Email", "contato.email", 150, false));
		columns.add(new ReportColumn("Celular", "contato.foneCelularFormatado", 40, false));
		columns.add(new ReportColumn("Fone Fixo", "contato.foneFixoFormatado", 40, false));
		
		
		return columns;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isMarcada(Collection<String> colunasMarcadas) 
	{
		for (String colunaMarcada : colunasMarcadas) 
		{
			if(colunaMarcada.equals(this.property))
				return true;
		}
		
		return false;
	}

	public boolean isResize() {
		return resize;
	}

	public void setResize(boolean resize) {
		this.resize = resize;
	}

	public static Collection<ReportColumn> resizeColumns(Collection<ReportColumn> colunas, Collection<String> colunasMarcadas) 
	{
		Collection<ReportColumn> colunasResize = new ArrayList<ReportColumn>();
		int rest = MAX_SIZE;
		int qtdResize = 0;
		for (ReportColumn coluna : colunas) 
		{
			for (String colunaMarcada : colunasMarcadas) 
			{
				if(colunaMarcada.equals(coluna.getProperty()))
				{
					rest -= (coluna.getSize() + SPACE);
					colunasResize.add(coluna);
					
					if(coluna.isResize())
						qtdResize++;
					
					break;
				}
			}
		}
		
		if(qtdResize != 0)
		{
			int leftover = rest/qtdResize;
			
			for (ReportColumn coluna : colunasResize)
			{
				if(coluna.isResize())
					coluna.setSize(coluna.getSize() + leftover);
			}			
		}
		
		return colunasResize;
	}

	public static int getMaxSize() {
		return MAX_SIZE;
	}

	public static int getSpace() {
		return SPACE;
	}
}
