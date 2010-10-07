package com.fortes.rh.test.model.desenvolvimento;

import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;


public class CertificadoTest extends TestCase
{
    public void testGetNomeColaborador()
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	curso.setNome("Java");
    	curso.setCargaHoraria(20);

    	Turma turma = TurmaFactory.getEntity(1L);
    	turma.setInstrutor("João da Penha");
    	turma.setCurso(curso);

    	Certificado certificado = new Certificado(turma, "Palmacia", true, true);
    	certificado.setNomeColaborador("Francisco");

    	assertEquals(certificado.getTitulo(), "Java");
    }

    public void testGerarColecao()
    {
    	Certificado certificado = new Certificado();
    	Collection<Certificado> certificados = Certificado.gerarColecao(new String[]{"1","2"}, certificado);
    	assertEquals(2, certificados.size());
    }

}
