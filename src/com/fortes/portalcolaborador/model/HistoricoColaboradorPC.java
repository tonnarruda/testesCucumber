package com.fortes.portalcolaborador.model;

import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class HistoricoColaboradorPC extends AbstractAdapterPC
{
	private String data;
	private String motivo;
	@SerializedName("estabelecimento_nome")
	private String estabelecimentoNome;
	@SerializedName("cargo_nome")
	private String cargoNome;
	@SerializedName("area_nome")
	private String areaNome;
	private String tipo;
	private Double salario;
	
	public HistoricoColaboradorPC() 
	{
	}

	public HistoricoColaboradorPC(HistoricoColaborador historicoColaborador) 
	{
		this.data = DateUtil.formataDiaMesAno(historicoColaborador.getData());
		this.motivo = historicoColaborador.getMotivoDescricao();
		this.tipo = historicoColaborador.getTipoSalarioDescricao();
		this.salario = historicoColaborador.getSalarioCalculado();
		
		if (historicoColaborador.getEstabelecimento() != null)
			this.estabelecimentoNome = historicoColaborador.getEstabelecimento().getNome();
		
		if (historicoColaborador.getFaixaSalarial() != null  && historicoColaborador.getFaixaSalarial().getCargo() != null)
			this.cargoNome = historicoColaborador.getFaixaSalarial().getCargo().getNome();
		
		if (historicoColaborador.getAreaOrganizacional() != null)
			this.areaNome = historicoColaborador.getAreaOrganizacional().getNome();
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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
