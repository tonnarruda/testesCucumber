package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="participanteCursoLnt_sequence", allocationSize=1)
public class ParticipanteCursoLnt extends AbstractModel implements Serializable {

	@ManyToOne
	private Colaborador colaborador;
	
	@ManyToOne
	private CursoLnt cursoLnt;
	
	@ManyToOne
	private AreaOrganizacional areaOrganizacional;
	
	@Transient
	private String areaNomeFolha;
	
	@Transient
	private String estabelecimentoNome;
	
	@Transient
	private Empresa empresa;

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public CursoLnt getCursoLnt()
	{
		return cursoLnt;
	}

	public void setCursoLnt(CursoLnt cursoLnt)
	{
		this.cursoLnt = cursoLnt;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}
	
	public void setLntId(Long lntId)
	{
		inicializaEntidades();
		this.cursoLnt.getLnt().setId(lntId);
	}
	
	public void setLntDescricao(String lntDescricao)
	{
		inicializaEntidades();
		this.cursoLnt.getLnt().setDescricao(lntDescricao);
	}
	
	public void setLntDataInicio(Date lntDataInicio)
	{
		inicializaEntidades();
		this.cursoLnt.getLnt().setDataInicio(lntDataInicio);
	}
	
	public void setLntDataFim(Date lntDataFim)
	{
		inicializaEntidades();
		this.cursoLnt.getLnt().setDataFim(lntDataFim);
	}
	
	public void setAreaNome(String areaNome)
	{
		inicializaEntidades();
		this.areaOrganizacional.setNome(areaNome);
	}
	
	public void setCursoNome(String cursoNome)
	{
		inicializaEntidades();
		this.cursoLnt.getCurso().setNome(cursoNome);
	}
	
	public void setColaboradorNome(String colaboradorNome)
	{
		inicializaEntidades();
		this.colaborador.setNome(colaboradorNome);
	}
	
	public Long getColaboradorId(){
		if (colaborador!=null)
			return colaborador.getId();
		else
			return null;
	}
	
	public String getColaboradorNome(){
		if (colaborador!=null)
			return colaborador.getNome();
		else
			return null;
	}
	
	public void setColaboradorNomeComercial(String colaboradorNomeComercial)
	{
		inicializaEntidades();
		this.colaborador.setNomeComercial(colaboradorNomeComercial);
	}
	
	public void setColaboradorId(Long colaboradorId)
	{
		inicializaEntidades();
		this.colaborador.setId(colaboradorId);
	}
	
	private void inicializaEntidades()
	{
		if(this.cursoLnt == null)
			this.cursoLnt = new CursoLnt();
		if(this.cursoLnt.getLnt() == null)
			this.cursoLnt.setLnt(new Lnt());
		if(this.cursoLnt.getCurso() == null)
			this.cursoLnt.setCurso(new Curso());
		if(this.cursoLnt.getCurso() == null)
			this.cursoLnt.setCurso(new Curso());
		
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
		
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
	}
	
	public void setCursoLntNome(String cursoLntNome)
	{
		if(this.cursoLnt == null)
			this.cursoLnt = new CursoLnt();
		
		this.cursoLnt.setNomeNovoCurso(cursoLntNome);
	}
	
	public void setCursoLntId(Long cursoLntId)
	{
		if(this.cursoLnt == null)
			this.cursoLnt = new CursoLnt();
		
		this.cursoLnt.setId(cursoLntId);
	}
	
	public void setColaboradorMatricula(String colaboradorMatricula)
	{
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setMatricula(colaboradorMatricula);
	}
	
	public void setAreaId(Long areaId)
	{
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		
		this.areaOrganizacional.setId(areaId);
	}

	public String getAreaNomeFolha() {
		return areaNomeFolha;
	}
	
	public String getAreaNomeFolhaTruncado() {
		if(areaNomeFolha.length() > 42)
			return areaNomeFolha.substring(0,43) + "...";
		
		return areaNomeFolha;
	}

	public void setAreaNomeFolha(String areaNomeFolha) {
		this.areaNomeFolha = areaNomeFolha;
	}

	public String getEstabelecimentoNome() {
		return estabelecimentoNome;
	}
	
	public void setEstabelecimentoNome(String estabelecimentoNome) {
		this.estabelecimentoNome = estabelecimentoNome;
	}

	public String getEmpresaNome() {
		if(empresa == null || empresa.getNome() == null)
			return "";
		
		return empresa.getNome();
	}
	
	public Long getEmpresaId() {
		if(empresa == null)
			return null;
		
		return empresa.getId();
	}

	public void setEmpresaNome(String empresaNome) {
		iniciaEmpresa();
		empresa.setNome(empresaNome);
	}
	
	public void setEmpresaId(Long empresaId) {
		iniciaEmpresa();
		empresa.setId(empresaId);
	}

	private void iniciaEmpresa() {
		if(empresa == null)
			empresa = new Empresa();
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}