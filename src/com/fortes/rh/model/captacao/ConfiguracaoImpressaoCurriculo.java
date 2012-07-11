package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "configuracaoImpressaoCurriculo_sequence", allocationSize = 1)
public class ConfiguracaoImpressaoCurriculo extends AbstractModel implements Serializable
{
	private boolean exibirConhecimento;
	private boolean exibirCurso;
	private boolean exibirExperiencia;
	private boolean exibirInformacao;
	private boolean exibirObservacao;
	private boolean exibirHistorico;
	private boolean exibirIdioma;
	private boolean exibirFormacao;
	private boolean exibirInformacaoSocioEconomica;
	private boolean exibirExamePalografico;
	private boolean exibirTextoOCR;
	private boolean exibirSolicitacaoPessoal;
	
	private boolean exibirAssinatura1;
	@Column(length = 50)
	private String assinatura1;
	
	private boolean exibirAssinatura2;
	@Column(length = 50)
	private String assinatura2;

	private boolean exibirAssinatura3;
	@Column(length = 50)
	private String assinatura3;
	
	@ManyToOne
	private Empresa empresa;
	@ManyToOne
	private Usuario usuario;
	
	public boolean isExibirConhecimento()
	{
		return exibirConhecimento;
	}

	public void setExibirConhecimento(boolean exibirConhecimento)
	{
		this.exibirConhecimento = exibirConhecimento;
	}

	public boolean isExibirCurso()
	{
		return exibirCurso;
	}

	public void setExibirCurso(boolean exibirCurso)
	{
		this.exibirCurso = exibirCurso;
	}

	public boolean isExibirExperiencia()
	{
		return exibirExperiencia;
	}

	public void setExibirExperiencia(boolean exibirExperiencia)
	{
		this.exibirExperiencia = exibirExperiencia;
	}

	public boolean isExibirInformacao()
	{
		return exibirInformacao;
	}

	public void setExibirInformacao(boolean exibirInformacao)
	{
		this.exibirInformacao = exibirInformacao;
	}

	public boolean isExibirObservacao()
	{
		return exibirObservacao;
	}

	public void setExibirObservacao(boolean exibirObservacao)
	{
		this.exibirObservacao = exibirObservacao;
	}

	public boolean isExibirHistorico()
	{
		return exibirHistorico;
	}

	public void setExibirHistorico(boolean exibirHistorico)
	{
		this.exibirHistorico = exibirHistorico;
	}

	public boolean isExibirAssinatura1()
	{
		return exibirAssinatura1;
	}

	public void setExibirAssinatura1(boolean exibirAssinatura1)
	{
		this.exibirAssinatura1 = exibirAssinatura1;
	}

	public String getAssinatura1()
	{
		return assinatura1;
	}

	public void setAssinatura1(String assinatura1)
	{
		this.assinatura1 = assinatura1;
	}

	public boolean isExibirAssinatura2()
	{
		return exibirAssinatura2;
	}

	public void setExibirAssinatura2(boolean exibirAssinatura2)
	{
		this.exibirAssinatura2 = exibirAssinatura2;
	}

	public String getAssinatura2()
	{
		return assinatura2;
	}

	public void setAssinatura2(String assinatura2)
	{
		this.assinatura2 = assinatura2;
	}

	public boolean isExibirAssinatura3()
	{
		return exibirAssinatura3;
	}

	public void setExibirAssinatura3(boolean exibirAssinatura3)
	{
		this.exibirAssinatura3 = exibirAssinatura3;
	}

	public String getAssinatura3()
	{
		return assinatura3;
	}

	public void setAssinatura3(String assinatura3)
	{
		this.assinatura3 = assinatura3;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	
	public void populaParametros(Map<String, Object> parametros)
	{
		parametros.put("assinatura1", this.assinatura1);
		parametros.put("assinatura2", this.assinatura2);
		parametros.put("assinatura3", this.assinatura3);
		parametros.put("exibirAssinatura1", this.exibirAssinatura1);
		parametros.put("exibirAssinatura2", this.exibirAssinatura2);
		parametros.put("exibirAssinatura3", this.exibirAssinatura3);
		parametros.put("exibirConhecimento", this.exibirConhecimento);
		parametros.put("exibirCurso", this.exibirCurso);
		parametros.put("exibirExperiencia", this.exibirExperiencia);
		parametros.put("exibirHistorico", this.exibirHistorico);
		parametros.put("exibirInformacao", this.exibirInformacao);
		parametros.put("exibirObservacao", this.exibirObservacao);
		parametros.put("exibirIdioma", this.exibirIdioma);
		parametros.put("exibirFormacao", this.exibirFormacao);
		parametros.put("exibirInformacaoSocioEconomica", this.exibirInformacaoSocioEconomica);
		parametros.put("exibirTextoOCR", this.exibirTextoOCR);
		parametros.put("exibirSolicitacaoPessoal", this.exibirSolicitacaoPessoal);
	}

	public boolean isExibirIdioma()
	{
		return exibirIdioma;
	}

	public void setExibirIdioma(boolean exibirIdioma)
	{
		this.exibirIdioma = exibirIdioma;
	}

	public boolean isExibirFormacao()
	{
		return exibirFormacao;
	}

	public void setExibirFormacao(boolean exibirFormacao)
	{
		this.exibirFormacao = exibirFormacao;
	}

	public boolean isExibirInformacaoSocioEconomica()
	{
		return exibirInformacaoSocioEconomica;
	}

	public void setExibirInformacaoSocioEconomica(boolean exibirInformacaoSocioEconomica)
	{
		this.exibirInformacaoSocioEconomica = exibirInformacaoSocioEconomica;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((assinatura1 == null) ? 0 : assinatura1.hashCode());
		result = prime * result + ((assinatura2 == null) ? 0 : assinatura2.hashCode());
		result = prime * result + ((assinatura3 == null) ? 0 : assinatura3.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + (exibirAssinatura1 ? 1231 : 1237);
		result = prime * result + (exibirAssinatura2 ? 1231 : 1237);
		result = prime * result + (exibirAssinatura3 ? 1231 : 1237);
		result = prime * result + (exibirConhecimento ? 1231 : 1237);
		result = prime * result + (exibirCurso ? 1231 : 1237);
		result = prime * result + (exibirExperiencia ? 1231 : 1237);
		result = prime * result + (exibirFormacao ? 1231 : 1237);
		result = prime * result + (exibirHistorico ? 1231 : 1237);
		result = prime * result + (exibirIdioma ? 1231 : 1237);
		result = prime * result + (exibirInformacao ? 1231 : 1237);
		result = prime * result + (exibirInformacaoSocioEconomica ? 1231 : 1237);
		result = prime * result + (exibirObservacao ? 1231 : 1237);
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		ConfiguracaoImpressaoCurriculo other = (ConfiguracaoImpressaoCurriculo) obj;
		if(assinatura1 == null)
		{
			if(other.assinatura1 != null)
				return false;
		} else if(!assinatura1.equals(other.assinatura1))
			return false;
		if(assinatura2 == null)
		{
			if(other.assinatura2 != null)
				return false;
		} else if(!assinatura2.equals(other.assinatura2))
			return false;
		if(assinatura3 == null)
		{
			if(other.assinatura3 != null)
				return false;
		} else if(!assinatura3.equals(other.assinatura3))
			return false;
		if(empresa == null)
		{
			if(other.empresa != null)
				return false;
		} else if(!empresa.equals(other.empresa))
			return false;
		if(exibirAssinatura1 != other.exibirAssinatura1)
			return false;
		if(exibirAssinatura2 != other.exibirAssinatura2)
			return false;
		if(exibirAssinatura3 != other.exibirAssinatura3)
			return false;
		if(exibirConhecimento != other.exibirConhecimento)
			return false;
		if(exibirCurso != other.exibirCurso)
			return false;
		if(exibirExperiencia != other.exibirExperiencia)
			return false;
		if(exibirFormacao != other.exibirFormacao)
			return false;
		if(exibirHistorico != other.exibirHistorico)
			return false;
		if(exibirIdioma != other.exibirIdioma)
			return false;
		if(exibirInformacao != other.exibirInformacao)
			return false;
		if(exibirInformacaoSocioEconomica != other.exibirInformacaoSocioEconomica)
			return false;
		if(exibirObservacao != other.exibirObservacao)
			return false;
		if(usuario == null)
		{
			if(other.usuario != null)
				return false;
		} else if(!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public void setExibirExamePalografico(boolean exibirExamePalografico)
	{
		this.exibirExamePalografico = exibirExamePalografico;
	}

	public boolean isExibirExamePalografico()
	{
		return exibirExamePalografico;
	}

	public boolean isExibirTextoOCR()
	{
		return exibirTextoOCR;
	}

	public void setExibirTextoOCR(boolean exibirTextoOCR)
	{
		this.exibirTextoOCR = exibirTextoOCR;
	}

	public boolean isExibirSolicitacaoPessoal()
	{
		return exibirSolicitacaoPessoal;
	}

	public void setExibirSolicitacaoPessoal(boolean exibirSolicitacaoPessoal)
	{
		this.exibirSolicitacaoPessoal = exibirSolicitacaoPessoal;
	}
}
