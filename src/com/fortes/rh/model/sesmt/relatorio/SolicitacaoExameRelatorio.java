package com.fortes.rh.model.sesmt.relatorio;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.type.File;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.TipoClinica;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoExameRelatorio
{
	private String medicoNome;
	private String medicoCrm;
	private String clinicaNome;
	private String clinicaEndereco;
	private String clinicaTelefone;
	private String clinicaHorario;
	private String clinicaTipo;
	private String clinicaTipoDescricao;
	private String exameNome;
	private String colaboradorNome;
	private String colaboradorMatricula;
	private String colaboradorFuncao;
	private String colaboradorAmbiente;
	private String candidatoFuncao;
	private String candidatoNome;
	private Date colaboradorDataNascimento;
	private Date candidatoDataNascimento;
	private String exameMotivo = "";
	private String exames = "";
	private File medicoAssinatura;
	private int contExames = 0;
	private String solicitacaoExameObs;

	public int getContExames()
	{
		return contExames;
	}

	public void setContExames(int contExames)
	{
		this.contExames = contExames;
	}

	public void addExame(String exameNome)
	{
		this.exames += exameNome + "\n";
	}

	public java.io.File getMedicoAssinaturaArquivo()
	{
		if (this.medicoAssinatura != null)
		{
			return this.medicoAssinatura.getFileArchive();
		}
		return null;
	}

	public SolicitacaoExameRelatorio()
	{
	}

	public SolicitacaoExameRelatorio(String medicoNome, String medicoCrm, File medicoAssinatura, String clinicaNome, String clinicaTipo, String clinicaOutro, String clinicaTelefone, String clinicaHorario, String clinicaEndereco, String exameNome, String colaboradorNome, String candidatoNome, Date colaboradorDataNascimento, Date candidatoDataNascimento, String exameMotivo, String colaboradorMatricula, String colaboradorFuncao, String colaboradorAmbiente, String candidatoFuncao, String solicitacaoExameObs)
	{
		this.medicoNome = medicoNome;
		this.medicoCrm = medicoCrm;
		this.clinicaNome = clinicaNome != null ? clinicaNome : "";
		this.clinicaTipo = clinicaTipo;

		if(clinicaTipo!= null)
			this.clinicaTipoDescricao = clinicaTipo.equals("03") ? clinicaOutro : TipoClinica.getDescricao(clinicaTipo);
		else
			this.clinicaTipoDescricao = "";
		
		this.clinicaEndereco = clinicaEndereco != null ? clinicaEndereco : "";
		this.clinicaTelefone = clinicaTelefone != null ? clinicaTelefone : "";
		this.clinicaHorario = clinicaHorario != null ? clinicaHorario : "";
		this.exameNome = exameNome;
		this.colaboradorNome = colaboradorNome;
		this.candidatoNome = candidatoNome;
		this.colaboradorDataNascimento = colaboradorDataNascimento;
		this.candidatoDataNascimento = candidatoDataNascimento;
		this.exameMotivo = exameMotivo;
		this.medicoAssinatura = medicoAssinatura;
		this.colaboradorMatricula = colaboradorMatricula;
		this.colaboradorFuncao = colaboradorFuncao;
		this.colaboradorAmbiente = colaboradorAmbiente;
		this.candidatoFuncao = candidatoFuncao;
		this.solicitacaoExameObs = solicitacaoExameObs;
	}

	public String getPessoaNome()
	{
		if (StringUtils.isNotBlank(candidatoNome))
			return candidatoNome;
		else if (StringUtils.isNotBlank(colaboradorNome))
			return colaboradorNome;

		return "";
	}

	public Integer getPessoaIdade()
	{
		Date hoje = new Date();
		if (candidatoDataNascimento != null)
			return DateUtil.calcularIdade(candidatoDataNascimento, hoje);
		else
			return DateUtil.calcularIdade(colaboradorDataNascimento, hoje);
	}

	public String getMotivoDic()
	{
		MotivoSolicitacaoExame motivoSolicitacaoExame = MotivoSolicitacaoExame.getInstance();
		return (String)motivoSolicitacaoExame.get(exameMotivo);
	}

	public String getCandidatoNome()
	{
		return candidatoNome;
	}

	public String getClinicaNome()
	{
		return clinicaNome;
	}

	public String getColaboradorNome()
	{
		return colaboradorNome;
	}

	public String getExameNome()
	{
		return exameNome;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof SolicitacaoExameRelatorio))
			return false;

		SolicitacaoExameRelatorio solicitacaoExameRelatorio = (SolicitacaoExameRelatorio)obj;

		return solicitacaoExameRelatorio.getClinicaNome().equals(this.getClinicaNome());
	}

	@Override
	public int hashCode()
	{
		if (StringUtils.isEmpty(clinicaNome))
			return 0;

		return clinicaNome.hashCode();
	}

	public String getMedicoCrm()
	{
		return medicoCrm;
	}

	public String getMedicoNome()
	{
		return medicoNome;
	}

	public String getExames()
	{
		return exames;
	}
	
	public String getClinicaTelefone()
	{
		return clinicaTelefone;
	}

	public String getClinicaHorario()
	{
		return clinicaHorario;
	}
	
	public File getMedicoAssinatura()
	{
		return medicoAssinatura;
	}

	public String getClinicaEndereco()
	{
		return clinicaEndereco;
	}

	public void contaExames()
	{
		this.contExames++;
	}

	public String getClinicaTipo() {
		return clinicaTipo;
	}

	public void setClinicaTipo(String clinicaTipo) {
		this.clinicaTipo = clinicaTipo;
	}

	public String getClinicaTipoDescricao() {
		return clinicaTipoDescricao;
	}

	public void setClinicaTipoDescricao(String clinicaTipoDescricao) {
		this.clinicaTipoDescricao = clinicaTipoDescricao;
	}

	public String getColaboradorMatricula() {
		return colaboradorMatricula;
	}

	public String getColaboradorFuncao() {
		return colaboradorFuncao;
	}

	public String getCandidatoFuncao() {
		return candidatoFuncao;
	}

	public String getFuncao() {
		if (StringUtils.isNotBlank(candidatoFuncao))
			return candidatoFuncao;
		else if (StringUtils.isNotBlank(colaboradorFuncao))
			return colaboradorFuncao;

		return "";
	}

	public String getSolicitacaoExameObs() {
		return solicitacaoExameObs;
	}

	public void setSolicitacaoExameObs(String solicitacaoExameObs) {
		this.solicitacaoExameObs = solicitacaoExameObs;
	}

	public String getColaboradorAmbiente() {
		return colaboradorAmbiente;
	}
	
	public String getAmbiente() {
		if (StringUtils.isNotBlank(colaboradorAmbiente))
			return colaboradorAmbiente;

		return "";
	}
}
