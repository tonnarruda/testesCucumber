package com.fortes.rh.web.action.exportacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ExportacaoAction extends MyActionSupport
{
	private EmpresaManager empresaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	
	private Date dataIni;
	private Date dataFim;

	private Collection<Ocorrencia> ocorrencias; 
	private String ocorrenciaId;

	private Long empresaId;
	private Collection<Empresa> empresas;
	
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;

	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	
	private Collection<Curso> cursos = new ArrayList<Curso>();
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private String[] cursosCheck;
	
	private String[] turmasCheck;
	private Collection<CheckBox> turmasCheckList = new ArrayList<CheckBox>();
	
	private String textoTru;
	
	public String prepareExportacaoTreinamentos() throws Exception
	{
		try
		{
			empresaId = getEmpresaSistema().getId();
			empresas = empresaManager.findEmpresasIntegradas();
			
			if(empresas.isEmpty())
				throw new Exception ("Não existe empresa Integrada." );
			
			return SUCCESS;
		
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			return Action.INPUT;
		}
	}
	
	public String gerarArquivoExportacao() throws Exception
	{
		try{
			Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findColabTreinamentos(empresaId, dataIni, dataFim, estabelecimentosCheck, areasCheck, turmasCheck);
			
			if(colaboradorTurmas.isEmpty())
				throw new Exception ("Não existem dados com os filtros selecionados" );
			
			//Espaços importantes, favor não alterar quantidadde de espaços, ver documento do TRU em FortesRH\extras\importacaoTRU.txt
			StringBuffer texto = new StringBuffer();
			texto.append("H1TRAFEGO   RH        Importação do RH para o TRU            \n");
			texto.append("0" + ocorrenciaId + "Treinamentos                 1N\n");
			
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
				texto.append("1" + colaboradorTurma.getColaborador().getCodigoAC() + ocorrenciaId + 
						DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevIni()).replace("/", "") +
						DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevFim()).replace("/", "") +
						StringUtils.rightPad(" ", 270)+ "\n");
			}
			
			texto.append("T");
			
			textoTru = texto.toString();
			
			prepareExportacaoTreinamentos();
			return  SUCCESS;
		
		}catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareExportacaoTreinamentos();
			return Action.INPUT;
		}
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Collection<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(Collection<Curso> cursos) {
		this.cursos = cursos;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public String[] getAreasCheck() {
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList) {
		this.areasCheckList = areasCheckList;
	}

	public String[] getEstabelecimentosCheck() {
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck) {
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList() {
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentosCheckList(
			Collection<CheckBox> estabelecimentosCheckList) {
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public Collection<CheckBox> getCursosCheckList() {
		return cursosCheckList;
	}

	public void setCursosCheckList(Collection<CheckBox> cursosCheckList) {
		this.cursosCheckList = cursosCheckList;
	}

	public String[] getCursosCheck() {
		return cursosCheck;
	}

	public void setCursosCheck(String[] cursosCheck) {
		this.cursosCheck = cursosCheck;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String[] getTurmasCheck() {
		return turmasCheck;
	}

	public void setTurmasCheck(String[] turmasCheck) {
		this.turmasCheck = turmasCheck;
	}

	public Collection<CheckBox> getTurmasCheckList() {
		return turmasCheckList;
	}

	public void setTurmasCheckList(Collection<CheckBox> turmasCheckList) {
		this.turmasCheckList = turmasCheckList;
	}

	public void setColaboradorTurmaManager(
			ColaboradorTurmaManager colaboradorTurmaManager) {
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public Collection<Ocorrencia> getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(Collection<Ocorrencia> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public String getTextoTru() {
		return textoTru;
	}

	public void setTextoTru(String textoTru) {
		this.textoTru = textoTru;
	}

	public String getOcorrenciaId() {
		return ocorrenciaId;
	}

	public void setOcorrenciaId(String ocorrenciaId) {
		this.ocorrenciaId = ocorrenciaId;
	}
}