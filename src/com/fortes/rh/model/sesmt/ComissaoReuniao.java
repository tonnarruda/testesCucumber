package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoComissaoReuniao;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="comissaoreuniao_sequence", allocationSize=1)
public class ComissaoReuniao extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date data;

	@Column(length=20)
	private String horario;
	@Column(length=100)
	private String localizacao;
	@Column(length=100)
	private String descricao;
	@Column(length=1)
	private String tipo;
	
	private String ata;
	
	private String obsReuniaoAnterior;

	@ManyToOne
	private Comissao comissao;
	
	//somente para habilitar faltas da cipa.
	@Transient
	private boolean frequeniciaReuniao = false;

	@OneToMany(mappedBy="comissaoReuniao")
	private Collection<ComissaoReuniaoPresenca> comissaoReuniaoPresencas;

	public String getTipoDic()
	{
		Object tipo = TipoComissaoReuniao.getInstance().get(this.tipo);
		if (tipo != null)
			return tipo.toString();
		return "";
	}

	public String getDataFormatada()
    {
        String dataFormatada = "";
        if (data != null)
            dataFormatada = DateUtil.formataDiaMesAno(data);

        return dataFormatada;
    }

	// Usado na tela de Presenças
	public String getDescricaoFmt()
	{
		return getTipoDic() + " - " + getDataFormatada();
	}

	//Projection
	public void setProjectionComissaoId(Long comissaoId)
	{
		if (comissao == null)
			comissao = new Comissao();

		comissao.setId(comissaoId);
	}

	public Comissao getComissao()
	{
		return comissao;
	}
	public void setComissao(Comissao comissao)
	{
		this.comissao = comissao;
	}

	public Date getData()
	{
		if (data.before(new Date()))
			setFrequeniciaReuniao(true);
					
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public String getHorario()
	{
		return horario;
	}

	public void setHorario(String horario)
	{
		this.horario = horario;
	}

	public String getLocalizacao()
	{
		return localizacao;
	}

	public void setLocalizacao(String localizacao)
	{
		this.localizacao = localizacao;
	}

	public String getTipo()
	{
		return tipo;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public String getAta()
	{
		return ata;
	}

	public void setAta(String ata)
	{
		this.ata = ata;
	}

	public void setDados(Long id, String descricao2, String tipo2, Date data2, String horario2, String localizacao2, String ata2)
	{
		setId(id);
		this.data = data2;
		this.horario = horario2;
		this.localizacao = localizacao2;
		this.ata = ata2;
		this.tipo = tipo2;
		this.descricao = descricao2;
	}

	public Collection<ComissaoReuniaoPresenca> getComissaoReuniaoPresencas()
	{
		return comissaoReuniaoPresencas;
	}

	public void setComissaoReuniaoPresencas(Collection<ComissaoReuniaoPresenca> comissaoReuniaoPresencas)
	{
		this.comissaoReuniaoPresencas = comissaoReuniaoPresencas;
	}

	public String getObsReuniaoAnterior()
	{
		return obsReuniaoAnterior;
	}

	public void setObsReuniaoAnterior(String obsReuniaoAnterior)
	{
		this.obsReuniaoAnterior = obsReuniaoAnterior;
	}
	
	public boolean isFrequeniciaReuniao() {
		return frequeniciaReuniao;
	}

	public void setFrequeniciaReuniao(boolean frequeniciaReuniao) {
		this.frequeniciaReuniao = frequeniciaReuniao;
	}
}