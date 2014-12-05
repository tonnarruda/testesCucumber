package com.fortes.rh.model.ws;

import java.io.Serializable;

public class TAula implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String empresaCodigo;
	private String empresaGrupo;
	private String empregadoCodigo;
	private String data;
	private String horaIni;
	private String horaFim;
	private String cursoNome;
	private String turmaNome;
	private Integer status = 0;

	public TAula()
	{
	}

	public String getEmpresaCodigo() {
		return empresaCodigo;
	}

	public void setEmpresaCodigo(String empresaCodigo) {
		this.empresaCodigo = empresaCodigo;
	}

	public String getEmpresaGrupo() {
		return empresaGrupo;
	}

	public void setEmpresaGrupo(String empresaGrupo) {
		this.empresaGrupo = empresaGrupo;
	}

	public String getEmpregadoCodigo() {
		return empregadoCodigo;
	}

	public void setEmpregadoCodigo(String empregadoCodigo) {
		this.empregadoCodigo = empregadoCodigo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHoraIni() {
		return horaIni;
	}

	public void setHoraIni(String horaIni) {
		this.horaIni = horaIni;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCursoNome() {
		return cursoNome;
	}

	public void setCursoNome(String cursoNome) {
		this.cursoNome = cursoNome;
	}

	public String getTurmaNome() {
		return turmaNome;
	}

	public void setTurmaNome(String turmaNome) {
		this.turmaNome = turmaNome;
	}
	
}