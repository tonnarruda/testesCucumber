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
	private String orderField;
	private int size;
	private boolean resize;
	
	public ReportColumn(String name, String property, String orderField, int size, boolean resize) {
		super();
		this.name = name;
		this.property = property;
		this.orderField = orderField;
		this.size = size;
		this.resize = resize;
	}

	public static Collection<ReportColumn> getColumns(boolean exibirSalarioContratual)
	{
		Collection<ReportColumn> columns = new ArrayList<ReportColumn>();
		
		columns.add(new ReportColumn("Nome", "nome", "co.nome", 150, true));
		columns.add(new ReportColumn("Nome Comercial", "nomeComercial", "co.nomeComercial", 150, true));
		columns.add(new ReportColumn("Matrícula", "matricula", "co.matricula", 40, false));
		columns.add(new ReportColumn("Status", "desligadoDescricao", "co.desligado", 40, false));
		if (exibirSalarioContratual) 
			columns.add(new ReportColumn("Salário Contratual", "salarioCalculadoString", "co.nome", 20, true));
		columns.add(new ReportColumn("Empresa", "empresaNome", "emp.nome", 80, false));
		columns.add(new ReportColumn("Estabelecimento", "estabelecimentoNome", "es.nome", 120, false));
		columns.add(new ReportColumn("Área Organizacional", "areaOrganizacional.nome", "col_3_0_", 200, false));//É col_3_0_ mesmo. blz
		columns.add(new ReportColumn("Data Admissão", "dataAdmissaoFormatada", "co.dataAdmissao", 50, false));
		columns.add(new ReportColumn("Data Encerramento do Contrato", "dataEncerramentoContratoFormatada", "co.dataEncerramentoContrato", 50, false));
		columns.add(new ReportColumn("Data Desligamento", "dataDesligamentoFormatada", "co.dataDesligamento", 50, false));
		columns.add(new ReportColumn("Cargo Atual", "faixaSalarial.cargo.nome", "cg.nome", 120, true));
		columns.add(new ReportColumn("Faixa Salarial Atual", "faixaSalarial.nome", "fs.nome", 60, false));
		columns.add(new ReportColumn("Estado Civil", "pessoal.estadoCivilDic", "co.pessoal.estadoCivil", 120, false));
		columns.add(new ReportColumn("Nome da Mãe", "pessoal.mae", "co.pessoal.mae", 150, true));
		columns.add(new ReportColumn("Nome do Pai", "pessoal.pai", "co.pessoal.pai", 150, true));
		columns.add(new ReportColumn("Escolaridade", "pessoal.escolaridadeDic", "co.pessoal.escolaridade", 170, false));
		columns.add(new ReportColumn("Vinculo", "vinculoDescricao", "co.vinculo", 60, false));
		columns.add(new ReportColumn("Cpf", "pessoal.cpfFormatado", "co.pessoal.cpf", 60, false));
		columns.add(new ReportColumn("Pis", "pessoal.pis", "co.pessoal.pis", 60, false));
		columns.add(new ReportColumn("Rg", "pessoal.rg", "co.pessoal.rg", 70, false));
		columns.add(new ReportColumn("Orgão Emissor", "pessoal.rgOrgaoEmissor", "co.pessoal.rgOrgaoEmissor", 40, false));
		columns.add(new ReportColumn("UF do Rg", "pessoal.rgUf.sigla", "co.pessoal.rgUf.sigla", 20, false));
		columns.add(new ReportColumn("Deficiência", "pessoal.deficienciaDescricao", "co.pessoal.deficiencia", 60, false));
		columns.add(new ReportColumn("Data de Expedição(RG)", "pessoal.rgDataExpedicaoFormatada", "co.pessoal.rgDataExpedicao", 50, false));
		columns.add(new ReportColumn("Sexo", "pessoal.sexoDic", "co.pessoal.sexo", 40, false));
		columns.add(new ReportColumn("Data de Nascimento", "pessoal.dataNascimentoFormatada", "co.pessoal.dataNascimento", 50, false));
		columns.add(new ReportColumn("Conjugue", "pessoal.conjuge", "co.pessoal.conjuge", 150, true));
		columns.add(new ReportColumn("Qtd de Filhos", "pessoal.qtdFilhosString", "co.pessoal.qtdFilhos", 30, false));
		columns.add(new ReportColumn("Indicado Por", "candidato.pessoal.indicadoPor", "cand.pessoal.indicadoPor", 110, false));
		columns.add(new ReportColumn("Número da Habilitação", "habilitacao.numeroHab", "co.habilitacao.numeroHab", 50, false));
		columns.add(new ReportColumn("Emissão da Habilitação", "habilitacao.emissaoFormatada", "co.habilitacao.emissao", 50, false));
		columns.add(new ReportColumn("Vencimento da Habilit.", "habilitacao.vencimentoFormatada", "co.habilitacao.vencimento", 50, false));
		columns.add(new ReportColumn("Categoria da Habilit.", "habilitacao.categoria", "co.habilitacao.categoria", 60, false));
		columns.add(new ReportColumn("UF da Habilitação", "habilitacao.ufHab.sigla", "co.habilitacao.ufHab.sigla", 20, false));
		columns.add(new ReportColumn("CTPS/Série-DV", "pessoal.ctps.numeroCompleto", "co.pessoal.ctps.numeroCompleto", 15, true));
		columns.add(new ReportColumn("Logradouro", "endereco.enderecoFormatado", "co.endereco.enderecoFormatado", 200, true));
		columns.add(new ReportColumn("Bairro", "endereco.bairro", "co.endereco.bairro", 100, false));
		columns.add(new ReportColumn("Cidade/Estado", "endereco.cidadeEstado", "co.endereco.cidadeEstado", 80, false));
		columns.add(new ReportColumn("Cep", "endereco.cepFormatado", "co.endereco.cep", 60, false));
		columns.add(new ReportColumn("Email", "contato.email", "co.contato.email", 150, false));
		columns.add(new ReportColumn("Celular", "contato.celularComDddFormato", "co.contato.foneCelular", 42, false));
		columns.add(new ReportColumn("Fone Fixo", "contato.foneFixoComDddFormato", "co.contato.foneFixo", 40, false));
		columns.add(new ReportColumn("Afastado", "afastadoString", "caf.inicio", 15, false));
		
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
	
	public static String getpropertyByOrderField(Collection<ReportColumn> colunas, String orderField ) 
	{
		for (ReportColumn coluna : colunas) 
		{
			if(coluna.getOrderField().equals(orderField))
				return coluna.getProperty();
		}
		
		return "";
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
		for (String colunaMarcada : colunasMarcadas) 
		{
			for (ReportColumn coluna : colunas) 
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

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
}
