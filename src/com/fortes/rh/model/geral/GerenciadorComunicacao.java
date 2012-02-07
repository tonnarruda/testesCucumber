package com.fortes.rh.model.geral;

import java.io.Serializable;

import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.geral.GerenciadorComunicacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="gerenciadorComunicacao_sequence", allocationSize=1)
public class GerenciadorComunicacao extends AbstractModel implements Serializable
{
	@ManyToOne(fetch=FetchType.LAZY)
	private Empresa empresa;
	private Integer operacao;
	private Integer meioComunicacao;
	private Integer enviarPara;
	@Column(length=200)
    private String destinatario;

    public Integer getOperacao() {
		return operacao;
	}
    public String getOperacaoDescricao() {
    	return Operacao.getDescricao(operacao);
    }
	public void setOperacao(Integer operacao) {
		this.operacao = operacao;
	}
	public Integer getMeioComunicacao() {
		return meioComunicacao;
	}
	public void setMeioComunicacao(Integer meioComunicacao) {
		this.meioComunicacao = meioComunicacao;
	}
	public String getMeioComunicacaoDescricao() {
    	return MeioComunicacao.getDescricao(meioComunicacao);
    }
	public Integer getEnviarPara() {
		return enviarPara;
	}
	public String getEnviarParaDescricao(){
		return EnviarPara.getDescricao(enviarPara);
	}
	public void setEnviarPara(Integer enviarPara) {
		this.enviarPara = enviarPara;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Empresa getEmpresa() {
		return empresa;
	}	
    
    
	
}
