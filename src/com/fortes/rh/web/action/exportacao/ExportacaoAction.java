package com.fortes.rh.web.action.exportacao;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
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
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	private Long empresaId;
	private Collection<Empresa> empresas;
	private char escala; 
	
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
			Long[] estabelecimentoIds = StringUtil.stringToLong(estabelecimentosCheck);
			Long[] areaIds = StringUtil.stringToLong(areasCheck);
			Long[] cursosIds = StringUtil.stringToLong(cursosCheck);
			
			ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findById(1L);
			//Espaços importantes, favor não alterar quantidadde de espaços, ver documento do TRU em FortesRH\extras\importacaoTRU.txt
			StringBuffer erro = new StringBuffer();
			StringBuffer texto = new StringBuffer();
			texto.append("H1TRAFEGO   RH        Importação do RH para o TRU            \n");
				Collection<Curso> cursos = cursoManager.findByIdProjection(cursosIds);
				
				for (Curso curso : cursos)
				{
					if(curso.getCodigoTru() == null)
					{
						erro.append(curso.getNome());
						erro.append("<br>");
					} 
				}
						
				if(erro.length() > 0){
					throw new Exception ("Existem cursos sem código TRU:<br><br>" + erro.toString() );
				} else {
					for (Curso curso : cursos)
					{
						texto.append("0" + curso.getCodigoTru() + StringUtils.rightPad(curso.getNome(), 30, " ") + "1" + escala +"\n");
						Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findColabTreinamentos(empresaId, estabelecimentoIds, areaIds, new Long[]{curso.getId()});
						for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
							texto.append("1" + colaboradorTurma.getColaborador().getCodigoAC() + curso.getCodigoTru() + 
									DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevIni()).replace("/", "") +
									DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevFim()).replace("/", "") +
									StringUtils.rightPad(" ", 270)+ "\n");
						}
						texto.append("T");
						textoTru = texto.toString();
					}
				}
					
			
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

	public char getEscala() {
		return escala;
	}

	public void setEscala(char escala) {
		this.escala = escala;
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}

	public void setParametrosDoSistemaManager(
			ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}