package com.fortes.rh.web.action.exportacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
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
	private Collection<Curso> cursos = new ArrayList<Curso>();
	
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> turmasCheckList = new ArrayList<CheckBox>();
	
	private String[] estabelecimentosCheck;
	private String[] areasCheck;
	private String[] cursosCheck;
	private String[] turmasCheck;
	
	private String textoTru;
	private boolean considerarSomenteDiasPresente;
	
	public String prepareExportacaoTreinamentos() throws Exception
	{
		try
		{
			empresaId = getEmpresaSistema().getId();
			empresas = empresaManager.findEmpresasIntegradas();
			
			if(empresas.isEmpty())
			{ 
				empresas = new ArrayList<Empresa>();
				throw new Exception ("Não existe(m) empresa(s) Integrada(s).");
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
				Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findColabTreinamentos(empresaId, estabelecimentoIds, areaIds, new Long[]{curso.getId()}, LongUtil.arrayStringToArrayLong(turmasCheck), considerarSomenteDiasPresente);
				
				if(colaboradorTurmas.isEmpty()){
					addActionMessage("Não existem colaboradores inseridos no curso '"+curso.getNome()+"' a serem exportados como ocorrência.");
					prepareExportacaoTreinamentos();
					return Action.INPUT;
				}
				
				Map<String, Collection<ColaboradorTurma>> mapColaboradoresTurmas = new HashMap<String, Collection<ColaboradorTurma>>(); 
				
				for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
				{
					if(!mapColaboradoresTurmas.containsKey(colaboradorTurma.getColaborador().getCodigoAC()))
						mapColaboradoresTurmas.put(colaboradorTurma.getColaborador().getCodigoAC(), new ArrayList<ColaboradorTurma>());

					mapColaboradoresTurmas.get(colaboradorTurma.getColaborador().getCodigoAC()).add(colaboradorTurma);
				}
				
				Date dataIni = null;
				Date dataFim = null;
				Iterator iterator = mapColaboradoresTurmas.entrySet().iterator();
				while (iterator.hasNext()) 
				{
					@SuppressWarnings("rawtypes")
					Map.Entry mapColaboradorTurma = (Map.Entry) iterator.next();
					
					dataFim = null;
					colaboradorTurmas = (Collection<ColaboradorTurma>) mapColaboradorTurma.getValue();
					for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
					{
						if(dataFim == null)
						{
							dataIni = colaboradorTurma.getDiaPresente();
							dataFim = colaboradorTurma.getDiaPresente();
						}
						else
						{
							if(DateUtil.incrementaDias(dataFim, 1).equals(colaboradorTurma.getDiaPresente()))
								dataFim = colaboradorTurma.getDiaPresente();
							else {
								texto.append("1"); 
								texto.append(mapColaboradorTurma.getKey()); 
								texto.append(curso.getCodigoTru());
								texto.append(DateUtil.formataDiaMesAno(dataIni).replace("/", ""));
								texto.append(DateUtil.formataDiaMesAno(dataFim).replace("/", ""));
								texto.append(StringUtils.rightPad(" ", 270));
								texto.append("\n");
								
								dataIni = colaboradorTurma.getDiaPresente();
								dataFim = colaboradorTurma.getDiaPresente();
							}
						}
					}
					
					texto.append("1"); 
					texto.append(mapColaboradorTurma.getKey()); 
					texto.append(curso.getCodigoTru());
					texto.append(DateUtil.formataDiaMesAno(dataIni).replace("/", ""));
					texto.append(DateUtil.formataDiaMesAno(dataFim).replace("/", ""));
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
		Collection<Colaborador> colaboradors = colaboradorTurmaManager.findColaboradorByCurso(StringUtil.stringToLong(cursosCheck), StringUtil.stringToLong(turmasCheck));
		
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

	public String[] getTurmasCheck() {
		return turmasCheck;
	}

	public void setTurmasCheck(String[] turmasCheck) {
		this.turmasCheck = turmasCheck;
	}

	public Collection<CheckBox> getTurmasCheckList() {
		return turmasCheckList;
	}

	public boolean isConsiderarSomenteDiasPresente() {
		return considerarSomenteDiasPresente;
	}

	public void setConsiderarSomenteDiasPresente(boolean considerarSomenteDiasPresente) {
		this.considerarSomenteDiasPresente = considerarSomenteDiasPresente;
	}
}