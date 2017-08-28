package com.fortes.rh.web.action.exportacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;

public class ExportacaoActionTest extends MockObjectTestCase
{
	private ExportacaoAction action;
	private Mock colaboradorTurmaManager;
	private Mock empresaManager;
	private Mock cursoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new ExportacaoAction();

        colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
        action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());
        
        empresaManager = mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        
        cursoManager = mock(CursoManager.class);
        action.setCursoManager((CursoManager) cursoManager.proxy());
    }
    
    public void testGerarArquivoExportacaoCursoCodigoTRUnull() throws Exception
    {
    	Curso curso = CursoFactory.getEntity();
    	curso.setNome("curso");
    	
    	cursoManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(Arrays.asList(curso)));
    	assertEquals("input",action.gerarArquivoExportacao());
    	assertEquals("Impossível exportar.<br>Existem cursos sem código TRU:<br> -curso<br>",action.getActionMessages().toArray()[0]);
    }
    
    public void testGerarArquivoExportacaoColaboradorSemCodigoAC() throws Exception
    {
    	Curso curso = CursoFactory.getEntity();
    	curso.setCodigoTru("123");
    	curso.setNome("curso");
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setNome("colaborador");
    	
    	cursoManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(Arrays.asList(curso)));
    	colaboradorTurmaManager.expects(once()).method("findColaboradorByCurso").with(ANYTHING, ANYTHING).will(returnValue(Arrays.asList(colaborador)));
    	assertEquals("input",action.gerarArquivoExportacao());
    	assertEquals("Impossível exportar.<br>Existem Colaboradores sem código AC (Matrícula):<br> -colaborador (nome comercial colaborador)<br>",action.getActionMessages().toArray()[0]);
    }
    
    public void testGerarArquivoExportacao() throws Exception
    {
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	Curso curso = CursoFactory.getEntity();
    	curso.setId(999999999L);
    	curso.setCodigoTru("123");
    	curso.setNome("curso");
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setCodigoAC("123456");
    	colaborador.setNome("colaborador");
    	
    	action.setEstabelecimentosCheck(new String[]{"1"});
    	action.setAreasCheck(new String[]{"1"});
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
    	colaboradorTurma.setColaborador(colaborador);
    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	colaboradorTurmas.add(colaboradorTurma);
    	
    	empresaManager.expects(once()).method("findEmpresasIntegradas").will(returnValue(null));
    	cursoManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(Arrays.asList(curso)));
    	colaboradorTurmaManager.expects(once()).method("findColaboradorByCurso").with(ANYTHING, ANYTHING).will(returnValue(Arrays.asList(colaborador)));
    	colaboradorTurmaManager.expects(once()).method("findColabTreinamentos").will(returnValue(colaboradorTurmas));
    	
    	assertEquals("success",action.gerarArquivoExportacao());
    	assertEquals(msgRetorno(curso),action.getTextoTru());
    }
    
    private String msgRetorno(Curso curso){
		StringBuffer texto = new StringBuffer();

		//Cabeçalho
		texto.append("H1");
		texto.append(StringUtils.rightPad("TRAFEGO", 10, " "));
		texto.append(StringUtils.rightPad("RH", 10, " "));
		texto.append(StringUtils.rightPad("Importação do RH para o TRU", 40, " "));
		texto.append("\n");
		texto.append(StringUtils.rightPad("1123456123", 280, " "));
		texto.append("\n");

		texto.append("T");
		return texto.toString();
    }
}