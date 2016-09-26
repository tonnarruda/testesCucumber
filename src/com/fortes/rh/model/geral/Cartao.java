package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.model.type.File;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="cartao_sequence", allocationSize=1)
public class Cartao extends AbstractModel implements Serializable
{
	@Column(length=200)
    private String imgUrl;
    @Column(length=300)
    private String mensagem;
    private String tipoCartao;
    @ManyToOne
    private Empresa empresa;
    @Transient
    private File file;
    
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getTipoCartao() {
		return tipoCartao;
	}
	public void setTipoCartao(String tipoCartao) {
		this.tipoCartao = tipoCartao;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public void setEmpresaId(Long empresaId){
		inicializaEmpresa();
		this.empresa.setId(empresaId);
	}
	
	public void setEmpresaNome(String empresaNome){
		inicializaEmpresa();
		this.empresa.setNome(empresaNome);
	}
	
	public void setEmpresaEmailRemetente(String empresaEmailRemetente){
		inicializaEmpresa();
		this.empresa.setEmailRemetente(empresaEmailRemetente);
	}
	
	public void setEmpresaEmailRespRH(String empresaEmailRespRH){
		inicializaEmpresa();
		this.empresa.setEmailRespRH(empresaEmailRespRH);
	}
	
	public void setEmpresaLogoUrl(String empresaLogoUrl){
		inicializaEmpresa();
		this.empresa.setLogoUrl(empresaLogoUrl);
	}
	
	private void inicializaEmpresa(){
		if(this.empresa == null)
			this.empresa = new Empresa();
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}