package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorocorrencia_sequence", allocationSize=1)
public class ColaboradorOcorrencia extends AbstractModel implements Serializable
{

	@Temporal(TemporalType.DATE)
    private Date dataIni;
	@Temporal(TemporalType.DATE)
    private Date dataFim;
	@Lob
    private String observacao;

    @ManyToOne(fetch=FetchType.LAZY)
    private Colaborador colaborador;
	@ManyToOne(fetch=FetchType.LAZY)
    private Ocorrencia ocorrencia;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Providencia providencia;

	public ColaboradorOcorrencia() {
	}

	//Usado por colaboradorOcorrenciaDaoHibernate no metodo findColaboradorOcorrencia
    public ColaboradorOcorrencia(Long colaboradorOcorrenciaId, Date colaboradorOcorrenciaDataIni, Date colaboradorOcorrenciaDataFim,
			String colaboradorOcorrenciaObservacao, Long colaboradorId, String colaboradorMatricula, String colaboradorNome, String colaboradorNomeComercial,
			int ocorrenciaPontuacao, String ocorrenciaDescricao, String estabelecimentoNome, String areaOrganizacionalNome,
			Long idProvidencia, String descricaoProvidencia)
	{	
    	setId(colaboradorOcorrenciaId);
		setOcorrenciaDescricao(ocorrenciaDescricao);
		setDataIni(colaboradorOcorrenciaDataIni);
		setDataFim(colaboradorOcorrenciaDataFim);

		setObservacao(colaboradorOcorrenciaObservacao);
		setOcorrenciaPontuacao(ocorrenciaPontuacao);

		setColaboradorId(colaboradorId);
		setColaboradorMatricula(colaboradorMatricula);
		setColaboradorNome(colaboradorNome);
		setColaboradorNomeComercial(colaboradorNomeComercial);

		setEstabelecimentoNome(estabelecimentoNome);
		setAreaNome(areaOrganizacionalNome);   	
		setIdProvidencia(idProvidencia);
		setDescricaoProvidencia(descricaoProvidencia);
	}
    
    //Usado por colaboradorOcorrenciaDaoHibernate no metodo findColaboradorOcorrencia
    public ColaboradorOcorrencia(Long colaboradorId, String colaboradorMatricula, String colaboradorNome, int ocorrenciaPontuacao)
    {
    	setColaboradorId(colaboradorId);
    	setColaboradorMatricula(colaboradorMatricula);
    	setColaboradorNome(colaboradorNome);
    	setOcorrenciaPontuacao(ocorrenciaPontuacao);
    }
	
    public void setProjectionDescricao(String projectionDescricao)
    {
    	if(this.ocorrencia == null)
    		this.ocorrencia = new Ocorrencia();
    	this.ocorrencia.setDescricao(projectionDescricao);
    }

    public void setColaboradorNomeComercial(String colaboradorNomeComercial)
    {
    	if(colaborador == null)
    		colaborador = new Colaborador();
    	colaborador.setNomeComercial(colaboradorNomeComercial);
    }

    public void setOcorrenciaDescricao(String ocorrenciaDescricao)
    {
    	prepareProjectionOcorrencia();
    	ocorrencia.setDescricao(ocorrenciaDescricao);
    }
    
    public void setProjectionOcorrenciaIntegraAC(boolean ocorrenciaIntegraAC)
    {
    	prepareProjectionOcorrencia();
    	ocorrencia.setIntegraAC(ocorrenciaIntegraAC);
    }

    public void setOcorrenciaPontuacao(int ocorrenciaPontuacao)
    {
    	prepareProjectionOcorrencia();
    	ocorrencia.setPontuacao(ocorrenciaPontuacao);
    }

    public void setOcorrenciaId(long ocorrenciaId)
    {
    	prepareProjectionOcorrencia();
    	ocorrencia.setId(ocorrenciaId);
    }
    
    public void setProjectionOcorrenciaCodigoAC(String ocorrenciaCodigoAC)
    {
    	prepareProjectionOcorrencia();
    	ocorrencia.setCodigoAC(ocorrenciaCodigoAC);
    }

	private void prepareProjectionOcorrencia()
	{
		if(ocorrencia == null)
    		ocorrencia = new Ocorrencia();
	}
	
	public void setProvidenciaId(Long id)
	{
		if(providencia == null)
			providencia = new Providencia();
		providencia.setId(id);
	}
	
	public void setProvidenciaDescricao(String providenciaDescricao)
	{
		if(providencia == null)
			providencia = new Providencia();
		providencia.setDescricao(providenciaDescricao);
	}

    public void setColaboradorId(Long id)
    {
    	if(colaborador == null)
    		colaborador = new Colaborador();
    	colaborador.setId(id);
    }
    
    public void setColaboradorMatricula(String matricula)
    {
    	if(colaborador == null)
    		colaborador = new Colaborador();
    	colaborador.setMatricula(matricula);
    }
    
    public void setProjectionColaboradorCodigoAC(String colaboradorCodigoAC)
    {
    	if(colaborador == null)
    		colaborador = new Colaborador();
    	colaborador.setCodigoAC(colaboradorCodigoAC);
    }

    public void setColaboradorNome(String colaboradorNome)
    {
    	if(colaborador == null)
    		colaborador = new Colaborador();
    	colaborador.setNome(colaboradorNome);
    }

    public void setEstabelecimentoNome(String estabelecimentoNome)
    {
    	if(colaborador == null)
    		colaborador = new Colaborador();
    	if(colaborador.getEstabelecimento() == null)
    		colaborador.setEstabelecimento(new Estabelecimento());

    	colaborador.getEstabelecimento().setNome(estabelecimentoNome);
    }

    public void setAreaNome(String areaNome)
    {
    	if(colaborador == null)
    		colaborador = new Colaborador();
    	if(colaborador.getAreaOrganizacional() == null)
    		colaborador.setAreaOrganizacional(new AreaOrganizacional());

    	colaborador.getAreaOrganizacional().setNome(areaNome);
    }

    public void setProjectionEmpresaId(Long projectionEmpresaId)
    {
    	prepareProjectionEmpresa();
    	this.colaborador.getEmpresa().setId(projectionEmpresaId);
    }
    
    public void setProjectionEmpresaCodigoAC(String projectionEmpresaCodigoAC)
    {
    	prepareProjectionEmpresa();
    	this.colaborador.getEmpresa().setCodigoAC(projectionEmpresaCodigoAC);
    }

	private void prepareProjectionEmpresa()
	{
		if(this.colaborador == null)
    		this.colaborador = new Colaborador();
    	if(this.colaborador.getEmpresa() == null)
    		this.colaborador.setEmpresa(new Empresa());
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public Date getDataFim()
	{
		return dataFim;
	}
	public String getDataFimString()
	{
		return DateUtil.formataDiaMesAno(dataFim);
	}
	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}
	public Date getDataIni()
	{
		return dataIni;
	}
	public String getDataIniString()
	{
		return DateUtil.formataDiaMesAno(dataIni);
	}
	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}
	
	public String getPeriodo()
	{
		String periodo = "";
		if(dataIni != null && dataFim != null)
		periodo = DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim);
		return periodo;
	}

	public Ocorrencia getOcorrencia()
	{
		return ocorrencia;
	}
	public void setOcorrencia(Ocorrencia ocorrencia)
	{
		this.ocorrencia = ocorrencia;
	}
	
	public void setIdProvidencia(Long idProvidencia)
	{
		if(getProvidencia() == null)
			setProvidencia(new Providencia());
		
		getProvidencia().setId(idProvidencia);
	}

	public void setDescricaoProvidencia(String descricaoProvidencia)
    {
    	if(getProvidencia() == null)
    		setProvidencia(new Providencia());
    	
    	getProvidencia().setDescricao(descricaoProvidencia);
    }

	public String getObservacao()
	{
		return observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public Providencia getProvidencia() {
		return providencia;
	}

	public void setProvidencia(Providencia providencia) {
		this.providencia = providencia;
	}

}