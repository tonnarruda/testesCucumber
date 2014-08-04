package com.fortes.rh.model.relatorio;

import java.util.Date;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.util.DateUtil;

/**
 * Representa uma participação de Colaborador na CIPA, como:
 * - candidato na Eleição
 * - membro da Comissão
 * 
 * @see comissaoManager.getParticipacoesDeColaboradorNaCipa()
 */
public class ParticipacaoColaboradorCipa {

	private Colaborador colaborador;
	private Date data;
	private String descricao;
	private String funcao;
	
	private ParticipacaoColaboradorCipa()
	{
	}
	
	public ParticipacaoColaboradorCipa(CandidatoEleicao candidatoEleicao)
	{
		this();
		
		this.descricao = "Inscrito como candidato";
		this.funcao = "Candidato";
		this.data = candidatoEleicao.getEleicao().getInscricaoCandidatoIni();
		this.colaborador = candidatoEleicao.getCandidato();
	}

	public ParticipacaoColaboradorCipa(ComissaoMembro comissaoMembro) {
		
		this.descricao = "Participação na CIPA";
		this.funcao = comissaoMembro.getFuncaoDic();
		this.data = comissaoMembro.getComissaoPeriodo().getaPartirDe();
		this.colaborador = comissaoMembro.getColaborador();
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public Date getData() {
		return data;
	}
	
	public String getDataFormatada() {
		String dataFormatada = ""; 
		dataFormatada = DateUtil.formataDiaMesAno(data);
		return dataFormatada;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getFuncao() {
		return funcao;
	}

}
