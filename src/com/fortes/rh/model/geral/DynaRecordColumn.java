/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class DynaRecordColumn implements Serializable, Cloneable
{
	private String name;
	private String property;
	private int size;
	
	public DynaRecordColumn(String name, String property, int size) {
		super();
		this.name = name;
		this.property = property;
		this.size = size;
	}

	public static Collection<DynaRecordColumn> getColumns()
	{
		Collection<DynaRecordColumn> columns = new ArrayList<DynaRecordColumn>();
		
		columns.add(new DynaRecordColumn("Nome", "nome", 150));
		columns.add(new DynaRecordColumn("Nome Comercial", "nomeComercial", 150));
		columns.add(new DynaRecordColumn("Matrícula", "matricula", 40));
		columns.add(new DynaRecordColumn("Data Admissão", "dataAdmissaoFormatada", 50));
		columns.add(new DynaRecordColumn("Cargo Atual", "faixaSalarial.cargo.nome", 120));
		columns.add(new DynaRecordColumn("Estado Civil", "pessoal.estadoCivilDic", 120));
		columns.add(new DynaRecordColumn("Nome da Mãe", "pessoal.mae", 150));
		columns.add(new DynaRecordColumn("Nome do Pai", "pessoal.pai", 150));
		columns.add(new DynaRecordColumn("Data de Desligamento", "dataDesligamentoFormatada", 50));
		columns.add(new DynaRecordColumn("Vinculo", "vinculoDescricao", 60));
		columns.add(new DynaRecordColumn("Cpf", "pessoal.cpfFormatado", 60));
		columns.add(new DynaRecordColumn("Pis", "pessoal.pis", 60));
		columns.add(new DynaRecordColumn("Rg", "pessoal.rg", 70));
		columns.add(new DynaRecordColumn("Orgão Emissor", "pessoal.rgOrgaoEmissor", 40));
		columns.add(new DynaRecordColumn("Deficiência", "pessoal.deficienciaDescricao", 60));
		columns.add(new DynaRecordColumn("Data de Expedição(RG)", "pessoal.rgDataExpedicaoFormatada", 50));
		columns.add(new DynaRecordColumn("Sexo", "pessoal.sexoDic", 40));
		columns.add(new DynaRecordColumn("Data de Nascimento", "pessoal.dataNascimentoFormatada", 50));
		columns.add(new DynaRecordColumn("Conjugue", "pessoal.conjuge", 150));
		columns.add(new DynaRecordColumn("Qtd de Filhos", "pessoal.qtdFilhosString", 30));
		columns.add(new DynaRecordColumn("Número da Habilitação", "habilitacao.numeroHab", 50));
		columns.add(new DynaRecordColumn("Emissão da Habilitação", "habilitacao.emissaoFormatada", 50));
		columns.add(new DynaRecordColumn("Vencimento da Habilit.", "habilitacao.vencimentoFormatada", 50));
		columns.add(new DynaRecordColumn("Categoria da Habilit.", "habilitacao.categoria", 60));
		columns.add(new DynaRecordColumn("Logradouro", "endereco.logradouro", 120));
		columns.add(new DynaRecordColumn("Comp. do Logradouro", "endereco.complemento", 120));
		columns.add(new DynaRecordColumn("Número do Logradouro", "endereco.numero", 60));
		columns.add(new DynaRecordColumn("Bairro", "endereco.bairro", 100));
		columns.add(new DynaRecordColumn("Cep", "endereco.cepFormatado", 60));
		columns.add(new DynaRecordColumn("Email", "contato.email", 150));
		columns.add(new DynaRecordColumn("Celular", "contato.foneCelularFormatado", 40));
		columns.add(new DynaRecordColumn("Fone Fixo", "contato.foneFixoFormatado", 40));
		
		
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
}
