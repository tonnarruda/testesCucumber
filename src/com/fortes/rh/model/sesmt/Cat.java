package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoAcidente;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="cat_sequence", allocationSize=1)
public class Cat extends AbstractModel implements Serializable
{
    @ManyToOne
    private Colaborador colaborador;
    @ManyToOne(fetch = FetchType.LAZY)
    private Ambiente ambiente;
    @ManyToOne(fetch = FetchType.LAZY)
    private NaturezaLesao naturezaLesao;
    @ManyToMany(fetch=FetchType.LAZY)
    private Collection<Epi> epis;
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(length=5)
    private String horario;
    @Column(length=100)
    private String parteAtingida;
    @Column(length=100)
    private String fonteLesao;
    private Integer tipoAcidente;
    private boolean foiTreinadoParaFuncao;
    private boolean usavaEPI;
    private boolean emitiuCAT;
    @Column(length=20)
    private String numeroCat;
    private boolean gerouAfastamento;
    private Integer qtdDiasAfastado;
    @Lob
    private String observacao;//descricao do acidente
    @Lob
    private String conclusao;
    
    public Cat()
	{
	}

    public Cat(Long id, Date data, String numeroCat, String observacao, Boolean gerouAfastamento, Long colaboradorId, String colaboradorNome, String estabelecimentoNome, Long areaOrganizacionalId)
	{
		setId(id);
		this.data = data;
		this.numeroCat = numeroCat;
		this.observacao = observacao;
		this.gerouAfastamento = gerouAfastamento;
		
		this.colaborador = new Colaborador();
		setColaboradorId(colaboradorId);
		colaborador.setNome(colaboradorNome);
		colaborador.setEstabelecimentoNomeProjection(estabelecimentoNome);
		colaborador.setAreaOrganizacionalId(areaOrganizacionalId);
	}

	public void setColaboradorId(Long colaboradorId)
	{
		if(colaborador == null)
			colaborador = new Colaborador();

		colaborador.setId(colaboradorId);
	}

    public void setNomeComercial(String nomeComercial)
    {
    	if(colaborador == null)
    		colaborador = new Colaborador();

    	colaborador.setNomeComercial(nomeComercial);
    }

	public Colaborador getColaborador()
	{
		return colaborador;
	}
	
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	
	public Date getData()
	{
		return data;
	}
	
	public void setData(Date data)
	{
		this.data = data;
	}
	
	public String getNumeroCat()
	{
		return numeroCat;
	}
	
	public void setNumeroCat(String numeroCat)
	{
		this.numeroCat = numeroCat;
	}
	
	public String getObservacao()
	{
		return observacao;
	}
	
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public String getGerouAfastamentoFormatado() 
	{
		return gerouAfastamento ? "Sim" : "Não";
	}
	
	public boolean getGerouAfastamento() 
	{
		return gerouAfastamento;
	}

	public void setGerouAfastamento(boolean gerouAfastamento) 
	{
		this.gerouAfastamento = gerouAfastamento;
	}

	public String getDataFormatada()
	{
		String dataFmt = "";
		if (data != null)
			dataFmt += DateUtil.formataDiaMesAno(data);

		return dataFmt;
	}

	public Ambiente getAmbiente() 
	{
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) 
	{
		this.ambiente = ambiente;
	}

	public NaturezaLesao getNaturezaLesao() 
	{
		return naturezaLesao;
	}

	public void setNaturezaLesao(NaturezaLesao naturezaLesao) 
	{
		this.naturezaLesao = naturezaLesao;
	}

	public Collection<Epi> getEpis() 
	{
		return epis;
	}
	
	public String getEpisFormatado() 
	{
		StringBuffer retorno = new StringBuffer();
		for (Epi epi : epis) 
		{
			retorno.append(", " + epi.getNome());
		}		
		
		return retorno.length() >= 2 ? retorno.substring(2) : "";
	}

	public void setEpis(Collection<Epi> epis) 
	{
		this.epis = epis;
	}

	public String getHorario() 
	{
		return horario;
	}

	public void setHorario(String horario) 
	{
		this.horario = horario;
	}

	public String getParteAtingida() 
	{
		return parteAtingida;
	}

	public void setParteAtingida(String parteAtingida) 
	{
		this.parteAtingida = parteAtingida;
	}

	public Integer getTipoAcidente() 
	{
		return tipoAcidente;
	}
	
	public String getTipoAcidenteDescricao() 
	{
		return new TipoAcidente().get(tipoAcidente);
	}

	public void setTipoAcidente(Integer tipoAcidente) 
	{
		this.tipoAcidente = tipoAcidente;
	}

	public boolean isFoiTreinadoParaFuncao() 
	{
		return foiTreinadoParaFuncao;
	}

	public void setFoiTreinadoParaFuncao(boolean foiTreinadoParaFuncao) 
	{
		this.foiTreinadoParaFuncao = foiTreinadoParaFuncao;
	}

	public boolean isUsavaEPI() 
	{
		return usavaEPI;
	}

	public void setUsavaEPI(boolean usavaEPI) 
	{
		this.usavaEPI = usavaEPI;
	}

	public boolean isEmitiuCAT() 
	{
		return emitiuCAT;
	}

	public void setEmitiuCAT(boolean emitiuCAT) 
	{
		this.emitiuCAT = emitiuCAT;
	}

	public Integer getQtdDiasAfastado() 
	{
		return qtdDiasAfastado;
	}

	public void setQtdDiasAfastado(Integer qtdDiasAfastado) 
	{
		this.qtdDiasAfastado = qtdDiasAfastado;
	}

	public String getConclusao() 
	{
		return conclusao;
	}

	public void setConclusao(String conclusao) 
	{
		this.conclusao = conclusao;
	}

	public String getFonteLesao() {
		return fonteLesao;
	}

	public void setFonteLesao(String fonteLesao) {
		this.fonteLesao = fonteLesao;
	}
}