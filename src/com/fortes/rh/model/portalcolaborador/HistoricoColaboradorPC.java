package com.fortes.rh.model.portalcolaborador;

import java.util.Date;

import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HistoricoColaboradorPC extends AbstractAdapterPC
{
	private Date data;
	private String motivo;
	private String estabelecimentoNome;
	private String cargoNome;
	private String areaNome;
	private String tipo;
	private Double salario;
	
	public HistoricoColaboradorPC() 
	{

	}

	public HistoricoColaboradorPC(HistoricoColaborador historicoColaborador) 
	{
		this.data = historicoColaborador.getData();
		this.motivo = historicoColaborador.getMotivo();
		this.tipo = historicoColaborador.getTipoSalarioDescricao();
		this.salario = historicoColaborador.getSalario();
		
		if (historicoColaborador.getEstabelecimento() != null)
			this.estabelecimentoNome = historicoColaborador.getEstabelecimento().getNome();
		
		if (historicoColaborador.getFaixaSalarial() != null  && historicoColaborador.getFaixaSalarial().getCargo() != null)
			this.cargoNome = historicoColaborador.getFaixaSalarial().getCargo().getNome();
		
		if (historicoColaborador.getAreaOrganizacional() != null)
			this.areaNome = historicoColaborador.getAreaOrganizacional().getNome();
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getEstabelecimentoNome() {
		return estabelecimentoNome;
	}

	public void setEstabelecimentoNome(String estabelecimentoNome) {
		this.estabelecimentoNome = estabelecimentoNome;
	}

	public String getCargoNome() {
		return cargoNome;
	}

	public void setCargoNome(String cargoNome) {
		this.cargoNome = cargoNome;
	}

	public String getAreaNome() {
		return areaNome;
	}

	public void setAreaNome(String areaNome) {
		this.areaNome = areaNome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}
	
	public String toJson()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("historicocolaborador", gson.toJsonTree(this));
		
		return jsonObject.toString();
	}
}
