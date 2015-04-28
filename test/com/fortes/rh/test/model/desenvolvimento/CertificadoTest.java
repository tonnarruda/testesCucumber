package com.fortes.rh.test.model.desenvolvimento;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
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
    	Colaborador colaborador1 = ColaboradorFactory.getEntity();
    	colaborador1.setNota(new BigDecimal(8));
    	
    	Colaborador colaborador2 = ColaboradorFactory.getEntity();
    	colaborador2.setNota(new BigDecimal(10));
    	
    	Certificado certificado = new Certificado();
    	Collection<Certificado> certificados = Certificado.gerarColecaoVerso(Arrays.asList(colaborador1, colaborador2), certificado);
    	assertEquals(2, certificados.size());
    }

}
