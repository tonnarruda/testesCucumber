package com.fortes.rh.model.sesmt.relatorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.relatorio.PppFatorRisco;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;

public class PppRelatorio
{
	private Collection<Cat> cats;
	private Collection<HistoricoColaborador> historicos;
	private Collection<HistoricoFuncao> historicoFuncaos;
	private Collection<PppFatorRisco> fatoresRiscos;
	private Collection<EngenheiroResponsavel> engenheirosResponsaveis;
	private Collection<MedicoCoordenador> medicosCoordenadores;
	private Colaborador colaborador;
	private Estabelecimento estabelecimento;
	private Date data;
	private String nit;
	private String responsavel;
	private String observacoes;
	
	// Respostas do item 15.9 
	String resposta1="";
	String resposta2="";
	String resposta3="";
	String resposta4="";
	String resposta5="";
	
	public PppRelatorio() {
	}
	
	public PppRelatorio(Colaborador colaborador, Estabelecimento estabelecimento, Date data) 
	{
		this.colaborador = colaborador;
		this.estabelecimento = estabelecimento;
		this.data = data;
	}
	
	public PppRelatorio(Colaborador colaborador, Estabelecimento estabelecimento, Date data, String[] respostas, Collection<Cat> cats, Collection<HistoricoColaborador> historicosDoColaboradors,
			Collection<HistoricoFuncao> historicoFuncaos, Collection<PppFatorRisco> pppFatorRiscos, String nit, String responsavel, String observacoes, 
			Collection<EngenheiroResponsavel> engenheirosResponsaveis, Collection<MedicoCoordenador> medicosCoordenadores) {
		this.colaborador = colaborador;
		this.estabelecimento = estabelecimento;
		this.data = data;
		this.setRespostas(respostas);
		this.setCats(cats);
		this.setHistoricos(historicosDoColaboradors);
		this.setHistoricoFuncaos(historicoFuncaos);
		this.setFatoresRiscosDistinct(pppFatorRiscos);
		this.setNit(nit);
		this.setResponsavel(responsavel);
		this.setObservacoes(observacoes);
		this.setEngenheirosResponsaveis(engenheirosResponsaveis);
		this.setMedicosCoordenadores(medicosCoordenadores);
	}

	public void setRespostas(String[] respostas)
	{
		if (respostas != null && respostas.length>=5)
		{
			resposta1 = respostas[0];
			resposta2 = respostas[1];
			resposta3 = respostas[2];
			resposta4 = respostas[3];
			resposta5 = respostas[4];
		}
	}
	
	public String getCnpjFormatado()
	{
		return StringUtil.formataCnpj(colaborador.getEmpresa().getCnpj(), estabelecimento.getComplementoCnpj());
	}

	@SuppressWarnings("unchecked")
	public void setFatoresRiscosDistinct(Collection<PppFatorRisco> fatoresRiscos)
	{
		Collection<PppFatorRisco> pppFatorRiscosResultado = new ArrayList<PppFatorRisco>();
		List<PppFatorRisco> pppFatorRiscos = new CollectionUtil<PppFatorRisco>().convertCollectionToList(fatoresRiscos);
		
		int cont = 0;
		for (PppFatorRisco pppFatorRisco : pppFatorRiscos)
		{
			if (cont+1 < pppFatorRiscos.size())
			{
				if (!pppFatorRisco.comparaMedicao(pppFatorRiscos.get(cont+1)))
					pppFatorRiscosResultado.add(pppFatorRisco);
			}
			else
				pppFatorRiscosResultado.add(pppFatorRisco);
			
			cont++;
		}
		
		this.fatoresRiscos = pppFatorRiscosResultado;
	}
	
	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}
	
	public String getNit()
	{
		return nit;
	}

	public void setNit(String nit)
	{
		this.nit = nit;
	}

	public String getResponsavel()
	{
		return responsavel;
	}

	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}

	public Collection<Cat> getCats()
	{
		return cats;
	}

	public void setCats(Collection<Cat> cats)
	{
		this.cats = cats;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Collection<HistoricoColaborador> getHistoricos()
	{
		return historicos;
	}

	public void setHistoricos(Collection<HistoricoColaborador> historicos)
	{
		this.historicos = historicos;
	}

	public Collection<HistoricoFuncao> getHistoricoFuncaos()
	{
		return historicoFuncaos;
	}

	public void setHistoricoFuncaos(Collection<HistoricoFuncao> historicoFuncaos)
	{
		this.historicoFuncaos = historicoFuncaos;
	}

	public Collection<PppFatorRisco> getFatoresRiscos()
	{
		return fatoresRiscos;
	}

	public String getObservacoes()
	{
		return observacoes;
	}

	public void setObservacoes(String observacoes)
	{
		this.observacoes = observacoes;
	}

	public String getResposta1() {
		return resposta1;
	}

	public String getResposta2() {
		return resposta2;
	}

	public String getResposta3() {
		return resposta3;
	}

	public String getResposta4() {
		return resposta4;
	}

	public String getResposta5() {
		return resposta5;
	}

	public Collection<EngenheiroResponsavel> getEngenheirosResponsaveis() {
		return engenheirosResponsaveis;
	}

	public void setEngenheirosResponsaveis(Collection<EngenheiroResponsavel> engenheirosResponsaveis) {
		this.engenheirosResponsaveis = engenheirosResponsaveis;
	}

	public Collection<MedicoCoordenador> getMedicosCoordenadores() {
		return medicosCoordenadores;
	}

	public void setMedicosCoordenadores(Collection<MedicoCoordenador> medicosCoordenadores) {
		this.medicosCoordenadores = medicosCoordenadores;
	}
}