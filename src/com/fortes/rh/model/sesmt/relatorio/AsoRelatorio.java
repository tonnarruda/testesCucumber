package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;
import java.util.Date;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.GrupoRisco;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.util.DateUtil;

public class AsoRelatorio
{
	private MedicoCoordenador medico;
	private String cidadeNome;
	private String motivo;

	private Colaborador colaborador;
	private Candidato candidato;
	private Collection<ExameSolicitacaoExame> exames;
	
	private String grpRiscoFisico = "";
	private String grpRiscoQuimico = "";
	private String grpRiscoBiologico = "";
	private String grpRiscoErgonomico = "";
	private String grpRiscoAcidente = "";
	private String grpRiscoOcupacional = "";

	public AsoRelatorio(SolicitacaoExame solicitacaoExame, Empresa empresa)
	{
		this.colaborador = solicitacaoExame.getColaborador();
		this.candidato = solicitacaoExame.getCandidato();
		this.medico = solicitacaoExame.getMedicoCoordenador();
		this.exames = solicitacaoExame.getExameSolicitacaoExames();
		
		if(exames.size() < 15)
		{
			int count = exames.size();
			for(int i=0 ; i < (15 - count); i++)
				exames.add(new ExameSolicitacaoExame());
		}
		
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
	
	public String getGrpRiscoFisico() {
		return grpRiscoFisico;
	}

	public String getGrpRiscoQuimico() {
		return grpRiscoQuimico;
	}

	public String getGrpRiscoBiologico() {
		return grpRiscoBiologico;
	}


	public String getGrpRiscoErgonomico() {
		return grpRiscoErgonomico;
	}

	public String getGrpRiscoAcidente() {
		return grpRiscoAcidente;
	}

	public String getGrpRiscoOcupacional() {
		return grpRiscoOcupacional;
	}
	
	public void setGrpRiscoFisico(String grpRiscoFisico) {
		this.grpRiscoFisico = grpRiscoFisico;
	}
	
	public void setGrpRiscoQuimico(String grpRiscoQuimico) {
		this.grpRiscoQuimico = grpRiscoQuimico;
	}
	
	public void setGrpRiscoBiologico(String grpRiscoBiologico) {
		this.grpRiscoBiologico = grpRiscoBiologico;
	}
	
	public void setGrpRiscoErgonomico(String grpRiscoErgonomico) {
		this.grpRiscoErgonomico = grpRiscoErgonomico;
	}
	
	public void setGrpRiscoAcidente(String grpRiscoAcidente) {
		this.grpRiscoAcidente = grpRiscoAcidente;
	}

	public void formataRiscos(Collection<Risco> riscos) 
	{
		for (Risco risco : riscos) 
		{
			if(risco.getGrupoRisco().equals(GrupoRisco.FISICO))
			{
				grpRiscoFisico += grpRiscoFisico.equals("")?"Físicos: ":", ";
				grpRiscoFisico += risco.getDescricao();
			}
			else if(risco.getGrupoRisco().equals(GrupoRisco.QUIMICO))
			{
				grpRiscoQuimico += grpRiscoQuimico.equals("")?"Químicos: ":", ";
				grpRiscoQuimico += risco.getDescricao();
			}
			else if(risco.getGrupoRisco().equals(GrupoRisco.BIOLOGICO))
			{
				grpRiscoBiologico += grpRiscoBiologico.equals("")?"Biológicos: ":", ";
				grpRiscoBiologico += risco.getDescricao();
			}
			else if(risco.getGrupoRisco().equals(GrupoRisco.ERGONOMICO))
			{
				grpRiscoErgonomico += grpRiscoErgonomico.equals("")?"Ergonômicos: ":", ";
				grpRiscoErgonomico += risco.getDescricao();
			}
			else if(risco.getGrupoRisco().equals(GrupoRisco.ACIDENTE))
			{
				grpRiscoAcidente += grpRiscoAcidente.equals("")?"Acidentes: ":", ";
				grpRiscoAcidente += risco.getDescricao();
			}
			else if(risco.getGrupoRisco().equals(GrupoRisco.OCUPACIONAL))
			{
				grpRiscoOcupacional += grpRiscoAcidente.equals("")?"Ocupacionais: ":", ";
				grpRiscoOcupacional += risco.getDescricao();
			}
		}
	}
	public void setExames(Collection<ExameSolicitacaoExame> exames) {
		this.exames = exames;
	}

	public Collection<ExameSolicitacaoExame> getExames() {
		return exames;
	}
}
