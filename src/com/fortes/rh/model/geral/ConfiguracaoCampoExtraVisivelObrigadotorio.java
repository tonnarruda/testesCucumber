package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="ConfiguracaoCampoExtraVisivelObrigadotorio_sequence", allocationSize=1)
public class ConfiguracaoCampoExtraVisivelObrigadotorio extends AbstractModel implements Serializable
{
	private String camposExtrasVisiveis;
	
    private String camposExtrasObrigatorios;
    
    private String tipoConfiguracaoCampoExtra;
    
    @ManyToOne
    private Empresa empresa;

	public ConfiguracaoCampoExtraVisivelObrigadotorio() {
		super();
	}
    
	public ConfiguracaoCampoExtraVisivelObrigadotorio(Long empresaId, String camposVisivesis, String tipo) {
		setEmpresaId(empresaId);
		setCamposExtrasVisiveis(camposVisivesis);
		setTipoConfiguracaoCampoExtra(tipo);
	}

	public String getCamposExtrasVisiveis() {
		return camposExtrasVisiveis;
	}

	public void setCamposExtrasVisiveis(String camposExtrasVisiveis) {
		this.camposExtrasVisiveis = camposExtrasVisiveis;
	}

	public String getCamposExtrasObrigatorios() {
		return camposExtrasObrigatorios;
	}

	public void setCamposExtrasObrigatorios(String camposExtrasObrigatorios) {
		this.camposExtrasObrigatorios = camposExtrasObrigatorios;
	}

	public String getTipoConfiguracaoCampoExtra() {
		return tipoConfiguracaoCampoExtra;
	}

	public void setTipoConfiguracaoCampoExtra(String tipoConfiguracao) {
		this.tipoConfiguracaoCampoExtra = tipoConfiguracao;
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
	
	private void inicializaEmpresa() {
		if(this.empresa == null)
			this.empresa = new Empresa();
	}
}