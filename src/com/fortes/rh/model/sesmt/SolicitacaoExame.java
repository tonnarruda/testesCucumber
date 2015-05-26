package com.fortes.rh.model.sesmt;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="solicitacaoexame_sequence", allocationSize=1)
public class SolicitacaoExame extends AbstractModel implements Serializable
{
    @Temporal(TemporalType.DATE)
	private Date data;
    private Integer ordem;

    @Column(length=20)
    private String motivo;
    
    @Column(length=100)
    private String observacao;

    @ManyToOne(fetch = LAZY)
    private Colaborador colaborador;
    @ManyToOne(fetch = LAZY)
    private Candidato candidato;
    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private MedicoCoordenador medicoCoordenador;

    @OneToMany(fetch = LAZY, mappedBy="solicitacaoExame")
    private Collection<ExameSolicitacaoExame> exameSolicitacaoExames;
    
    @Transient
    private boolean semExames=true;
    @Transient
    private String cargoNome;
    
    public SolicitacaoExame()	{ }

    //usado na consulta da listagem
    public SolicitacaoExame(Long id, Date data, String motivo, Integer ordem, String medicoCoordenadorNome, String colaboradorNome, String colaboradorNomeComercial, String candidatoNome, String cargoNome, Boolean colaboradorDesligado)
    {
        setId(id);
        this.data = data;
        this.ordem = ordem;
        this.motivo = motivo;
        setColaboradorNome(colaboradorNome);
        setColaboradorNomeComercial(colaboradorNomeComercial);
        if(colaboradorDesligado != null)
        	this.colaborador.setDesligado(colaboradorDesligado);
        
        setCandidatoNome(candidatoNome);
        setMedicoCoordenadorNome(medicoCoordenadorNome);
        this.cargoNome = cargoNome;
        
    }
    
    //usado em relatorio de atendimentos
    public SolicitacaoExame(Long id, Date data, String motivo, Integer ordem, String observacao, String medicoCoordenadorNome, String colaboradorNome, String colaboradorNomeComercial, String candidatoNome, String colaboradorCargoNome, String candidatoCargoNome, String colaboradorMatricula)
    {
        setId(id);
        this.data = data;
        this.motivo = motivo;
        this.ordem = ordem;
        this.observacao = observacao;
        setColaboradorNome(colaboradorNome);
        setCandidatoNome(candidatoNome);
        setMedicoCoordenadorNome(medicoCoordenadorNome);
        setColaboradorNomeComercial(colaboradorNomeComercial);
        setColaboradorMatricula(colaboradorMatricula);
        
        if (StringUtils.isNotBlank(colaboradorCargoNome))
        	this.cargoNome = colaboradorCargoNome;
        else if (StringUtils.isNotBlank(candidatoCargoNome))
        	this.cargoNome = candidatoCargoNome;
    }
    
	//usado no relatorio
    @NaoAudita
    public AbstractModel getPessoa()
	{
		if (candidato != null)
			return candidato;
		else if (colaborador != null)
			return colaborador;

		return null;
	}
    
    /**
     * Retorna o nome comercial do colaborador ou indica se for candidato. 
     * Relatório de Atendimentos Médicos. 
     */
    @NaoAudita
    public String getPessoaNomeComercial()
    {
    	AbstractModel pessoa = getPessoa();
    	String retorno = "";
    	    	
    	if (pessoa instanceof Candidato)
    	{
    		Candidato candidato = (Candidato) pessoa;
    		retorno = candidato.getNome() + " (candidato)";
    	}
    	
    	else if (pessoa instanceof Colaborador)
    	{
    		Colaborador colaborador = (Colaborador) pessoa; 
    		retorno = colaborador.getNome();
    		
    		if(StringUtils.isNotBlank(colaborador.getNomeComercial()) && !colaborador.getNome().equals(colaborador.getNomeComercial()))
    			retorno += " (" + colaborador.getNomeComercial() + ")";
    	}
    	
    	return retorno;
    }

    @NaoAudita
    public String getPessoaMatricula()
    {
    	AbstractModel pessoa = getPessoa();
    	
    	String pessoaMatricula = "";
    	if (pessoa instanceof Colaborador)
    		pessoaMatricula = ((Colaborador) pessoa).getMatricula();
    	
    	return pessoaMatricula;
    }

    public String getMotivo()
    {
        return this.motivo;
    }
    
    @NaoAudita
    public String getMotivoDic()
    {
        MotivoSolicitacaoExame motivoSolicitacaoExame = MotivoSolicitacaoExame.getInstance();
        String motivo = motivoSolicitacaoExame.get(this.motivo);
        
        return motivo != null ? motivo : "";
    }
    public void setMotivo(String motivo)
    {
        this.motivo = motivo;
    }
    
    @NaoAudita
    public String getDataFormatada()
    {
        String dataFormatada = "-";
        if (data != null)
        {
            dataFormatada = DateUtil.formataDiaMesAno(data);
        }
        return dataFormatada;
    }

    private void setMedicoCoordenadorNome(String medicoCoordenadorNome)
    {
        if (this.medicoCoordenador == null)
            this.medicoCoordenador = new MedicoCoordenador();

        this.medicoCoordenador.setNome(medicoCoordenadorNome);
    }
    
    public void setMedicoCoordenadorId(Long medicoCoordenadorId)
    {
    	if (this.medicoCoordenador == null)
    		this.medicoCoordenador = new MedicoCoordenador();
    	
    	this.medicoCoordenador.setId(medicoCoordenadorId);
    }
    
    public void setEmpresaId(Long empresaId)
    {
    	if (this.empresa == null)
    		this.empresa = new Empresa();
    	
    	this.empresa.setId(empresaId);
    }
    
    public void setCandidatoId(Long candidatoId)
    {
		if (this.candidato == null)
			this.candidato = new Candidato();
		
		this.candidato.setId(candidatoId);
    }
    
    public void setCandidatoNome(String candidatoNome)
    {
        if (StringUtils.isNotBlank(candidatoNome))
        {
            if (this.candidato == null)
                this.candidato = new Candidato();

            this.candidato.setNome(candidatoNome);
        }
    }

    public void setColaboradorId(Long colaboradorId)
    {
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setId(colaboradorId);
    }
    
    public void setColaboradorNome(String colaboradorNome)
    {
    	// condição necessária, pois se for candidato, o colaborador não deve ser instanciado!
        if (StringUtils.isNotBlank(colaboradorNome))
        {
            if (this.colaborador == null)
                this.colaborador = new Colaborador();

            this.colaborador.setNome(colaboradorNome);
        }
    }
    
    public void setColaboradorNomeComercial(String colaboradorNomeComercial) {

    	 if (StringUtils.isNotBlank(colaboradorNomeComercial))
         {
             if (this.colaborador == null)
                 this.colaborador = new Colaborador();

             this.colaborador.setNomeComercial(colaboradorNomeComercial);
         }
	}
    
    public void setColaboradorMatricula(String colaboradorMatricula) {
    	
    	if (StringUtils.isNotBlank(colaboradorMatricula))
    	{
    		if (this.colaborador == null)
    			this.colaborador = new Colaborador();
    		
    		this.colaborador.setMatricula(colaboradorMatricula);
    	}
    }

    public Candidato getCandidato()
    {
        return candidato;
    }
    public void setCandidato(Candidato candidato)
    {
        this.candidato = candidato;
    }
    public Colaborador getColaborador()
    {
        return colaborador;
    }
    public void setColaborador(Colaborador colaborador)
    {
        this.colaborador = colaborador;
    }
    public Empresa getEmpresa()
    {
        return empresa;
    }
    public void setEmpresa(Empresa empresa)
    {
        this.empresa = empresa;
    }
    public Date getData()
    {
        return data;
    }
    public void setData(Date data)
    {
        this.data = data;
    }
    public MedicoCoordenador getMedicoCoordenador()
    {
        return medicoCoordenador;
    }

    public void setMedicoCoordenador(MedicoCoordenador medicoCoordenador)
    {
        this.medicoCoordenador = medicoCoordenador;
    }

	public Collection<ExameSolicitacaoExame> getExameSolicitacaoExames()
	{
		return exameSolicitacaoExames;
	}

	public void setExameSolicitacaoExames(Collection<ExameSolicitacaoExame> exameSolicitacaoExames)
	{
		this.exameSolicitacaoExames = exameSolicitacaoExames;
	}

	/**
	 * atributo <i>transiente</i>.
	 */
	@NaoAudita
	public boolean isSemExames() {
		return semExames;
	}
	
	/**
	 * atributo <i>transiente</i>. */
	@NaoAudita
	public String getCargoNome() {
		return cargoNome;
	}

	public void setSemExames(boolean semExames) {
		this.semExames = semExames;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
}