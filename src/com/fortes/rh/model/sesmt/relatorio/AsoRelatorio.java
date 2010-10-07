package com.fortes.rh.model.sesmt.relatorio;

import java.util.Date;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.util.DateUtil;

public class AsoRelatorio
{
	private MedicoCoordenador medico;
	private String cidadeNome;
	private String motivo;

	private Colaborador colaborador;
	private Candidato candidato;

	public AsoRelatorio(SolicitacaoExame solicitacaoExame, Empresa empresa)
	{
		this.colaborador = solicitacaoExame.getColaborador();
		this.candidato = solicitacaoExame.getCandidato();
		this.medico = solicitacaoExame.getMedicoCoordenador();
		
		if (empresa.getCidade() != null) 
			this.cidadeNome = empresa.getCidade().getNome();
		
		this.motivo = solicitacaoExame.getMotivo(); // só para regra de marcar o motivo no jasper 
	}

	public AsoRelatorio() {
	}

	public AbstractModel getPessoa()
	{
		if (candidato != null)
			return candidato;
		else if (colaborador != null)
			return colaborador;

		return null;
	}

	public String getDataNascimento()
	{
		Date dataNascimento = null;
		String dataNascimentoFmt = "____/____/____";
		AbstractModel pessoa = getPessoa();
		if (pessoa != null)
		{
			if (pessoa instanceof Candidato)
				dataNascimento = ((Candidato)pessoa).getPessoal().getDataNascimento();
			else if (pessoa instanceof Colaborador)
				dataNascimento = ((Colaborador)pessoa).getPessoal().getDataNascimento();
		}
		if (dataNascimento != null)
			dataNascimentoFmt = DateUtil.formataDiaMesAno(dataNascimento);

		return dataNascimentoFmt;
	}

	public String getCidadeNome()
	{
		return this.cidadeNome;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public MedicoCoordenador getMedico()
	{
		return medico;
	}

	public String getMotivo() {
		return motivo;
	}
}
