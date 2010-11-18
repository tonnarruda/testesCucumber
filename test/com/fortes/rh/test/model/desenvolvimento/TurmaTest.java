package com.fortes.rh.test.model.desenvolvimento;

import junit.framework.TestCase;

import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.util.DateUtil;


public class TurmaTest extends TestCase
{
    public void testGetPeriodoFormatado()
    {
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setDataPrevIni(DateUtil.criarDataMesAno(01, 02, 2004));
    	turma.setDataPrevFim(DateUtil.criarDataMesAno(01, 02, 2005));
    	
    	assertEquals("01/02/2004 a 01/02/2005", turma.getPeriodoFormatado());
    }
    
    public void testGetData()
    {
    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setDataPrevIni(DateUtil.criarDataMesAno(01, 02, 2004));
    	turma.setDataPrevFim(DateUtil.criarDataMesAno(01, 02, 2004));
    	
    	assertEquals("01/02/2004", turma.getData());

    	turma.setDataPrevFim(DateUtil.criarDataMesAno(01, 02, 2005));
    	assertEquals("01/02/2004 a 01/02/2005", turma.getData());
    }
    
    public void testGetDescricaoCurso()
    {
    	Curso curso = CursoFactory.getEntity();
    	curso.setNome("Curso B");
    	
    	Turma turma = TurmaFactory.getEntity();
    	turma.setDescricao("turma A");
    	turma.setCurso(curso);
    	
    	assertEquals("Curso B / turma A", turma.getDescricaoCurso());
    }
    
    public void testClone()
    {
    	Curso java = CursoFactory.getEntity();
    	java.setNome("Curso B");
    	
    	Turma turma_noite = TurmaFactory.getEntity();
    	turma_noite.setDescricao("turma A");
    	turma_noite.setCurso(java);
    	
    	Turma turmaClonada = (Turma) turma_noite.clone();
    	
    	assertEquals(turma_noite.getDescricao(), turmaClonada.getDescricao());
    }
}
