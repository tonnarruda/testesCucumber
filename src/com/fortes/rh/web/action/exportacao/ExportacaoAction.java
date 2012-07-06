package com.fortes.rh.web.action.exportacao;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ExportacaoAction extends MyActionSupport
{
	private EmpresaManager empresaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private CursoManager cursoManager;
	
	private Long empresaId;
	private Collection<Empresa> empresas;
	
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;

	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	
	private Collection<Curso> cursos = new ArrayList<Curso>();
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private String[] cursosCheck;
	private String textoTru;
	
	public String prepareExportacaoTreinamentos() throws Exception
	{
		try
		{
			empresaId = getEmpresaSistema().getId();
			empresas = empresaManager.findEmpresasIntegradas();
			
			if(empresas.isEmpty())
			{
				empresas = new ArrayList<Empresa>();
				throw new Exception ("Não existe(m) empresa(s) Integrada(s)." );
			}
			
			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			return Action.INPUT;
		}
	}
	
	//para maiores informações ver Layout de importação do Tráfego Urbano (TRU) em FortesRH\web\WEB-INF\temp\layoutImportaçãoTrafegoUrbano.txt
	public String gerarArquivoExportacao() throws Exception
	{
		try{
			checaCursosSemCodigoTRU();
			checaColaboradoresSemCodigoAC();
			
			Long[] estabelecimentoIds = StringUtil.stringToLong(estabelecimentosCheck);
			Long[] areaIds = StringUtil.stringToLong(areasCheck);
			StringBuffer texto = new StringBuffer();

			//Cabeçalho
			texto.append("H1");
			texto.append(StringUtils.rightPad("TRAFEGO", 10, " "));
			texto.append(StringUtils.rightPad("RH", 10, " "));
			texto.append(StringUtils.rightPad("Importação do RH para o TRU", 40, " "));
			texto.append("\n");

			for (Curso curso : cursos)
			{
				//Ocorrências dos colaboradores
				Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findColabTreinamentos(empresaId, estabelecimentoIds, areaIds, new Long[]{curso.getId()});
				for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
				{
					texto.append("1"); 
					texto.append(colaboradorTurma.getColaborador().getCodigoAC()); 
					texto.append(curso.getCodigoTru()); 
					texto.append(DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevIni()).replace("/", ""));
					texto.append(DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevFim()).replace("/", ""));
					texto.append(StringUtils.rightPad(" ", 270));
					texto.append("\n");
				}
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

	private void checaCursosSemCodigoTRU() throws Exception 
	{
		Long[] cursosIds = StringUtil.stringToLong(cursosCheck);
		cursos = cursoManager.findByIdProjection(cursosIds);

		StringBuffer erro = new StringBuffer();
		for (Curso curso : cursos)
			if(curso.getCodigoTru() == null)
				erro.append(" -" + curso.getNome() + "<br>");
		
		if(erro.length() > 0)
			throw new Exception ("Impossível exportar.<br>Existem cursos sem código TRU:<br>" + erro.toString() );
	}

	private void checaColaboradoresSemCodigoAC() throws Exception 
	{
		Collection<Colaborador> colaboradors = colaboradorTurmaManager.findColaboradorByCurso(StringUtil.stringToLong(cursosCheck));
		
		StringBuffer erro = new StringBuffer();
		for (Colaborador colaborador : colaboradors)
			if(colaborador.getCodigoAC() == null || colaborador.getCodigoAC().equals(""))
				erro.append(" -" + colaborador.getNomeMaisNomeComercial() + "<br>");
		
		if(erro.length() > 0)
			throw new Exception ("Impossível exportar.<br>Existem Colaboradores sem código AC (Matrícula):<br>" + erro.toString() );
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

	public void setColaboradorTurmaManager(
			ColaboradorTurmaManager colaboradorTurmaManager) {
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public String getTextoTru() {
		return textoTru;
	}

	public void setTextoTru(String textoTru) {
		this.textoTru = textoTru;
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}
}