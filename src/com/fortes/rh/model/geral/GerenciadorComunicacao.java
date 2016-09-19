package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

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
	
	private String qtdDiasLembrete;
	
	private boolean permitirResponderAvaliacao;
	
	@OneToMany(fetch=FetchType.LAZY, targetEntity=Usuario.class, cascade = CascadeType.REMOVE)
	private Collection<Usuario> usuarios;

	public GerenciadorComunicacao (){
	}
	
	public GerenciadorComunicacao (Operacao operacao, MeioComunicacao meioComunicacao, EnviarPara enviarPara, Empresa empresa)
	{
		this.operacao = operacao.getId();
		this.meioComunicacao = meioComunicacao.getId();
		this.enviarPara = enviarPara.getId();
		this.empresa = empresa;
	}

	public GerenciadorComunicacao (Operacao operacao, MeioComunicacao meioComunicacao, EnviarPara enviarPara, Empresa empresa, String qtdDiasLembrete)
	{
		this.operacao = operacao.getId();
		this.meioComunicacao = meioComunicacao.getId();
		this.enviarPara = enviarPara.getId();
		this.empresa = empresa;
		this.qtdDiasLembrete = qtdDiasLembrete;
	}
	
	public Integer getMeioComunicacao() {
		return meioComunicacao;
	}
	public void setMeioComunicacao(Integer meioComunicacao) {
		this.meioComunicacao = meioComunicacao;
	}
	public String getMeioComunicacaoDescricao() {
    	return MeioComunicacao.getDescricaoById(meioComunicacao);
    }
	public Integer getEnviarPara() {
		return enviarPara;
	}
	public String getEnviarParaDescricao(){
		return EnviarPara.getDescricaoById(enviarPara);
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
	public Integer getOperacao() {
		return operacao;
	}
	public void setOperacao(Integer operacao) {
		this.operacao = operacao;
	}
	public String getOperacaoDescricao() {
		return Operacao.getDescricaoById(operacao);
	}
	public String getOperacaoGrupo() {
		return Operacao.getGrupoById(operacao);
	}
	public String getDestinatarioFormatado() {
		if(this.destinatario != null && this.destinatario.length() > 60)
			return this.destinatario.substring(0, 57) + "...";
		
		return this.destinatario;
	}
	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getQtdDiasLembrete() {
		return qtdDiasLembrete;
	}

	public void setQtdDiasLembrete(String qtdDiasLembrete) {
		this.qtdDiasLembrete = qtdDiasLembrete;
	}

	public boolean isPermitirResponderAvaliacao()
	{
		return permitirResponderAvaliacao;
	}
	
	public void setPermitirResponderAvaliacao(boolean permitirResponderAvaliacao)
	{
		this.permitirResponderAvaliacao = permitirResponderAvaliacao;
	}
}
