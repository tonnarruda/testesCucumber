package com.fortes.rh.test.model.sesmt;

import java.util.Calendar;

import junit.framework.TestCase;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.sesmt.relatorio.AsoRelatorio;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.util.DateUtil;

public class AsoRelatorioTest extends TestCase
{
	public void testGetPessoa()
	{
		AsoRelatorio asoRelatorio = new AsoRelatorio();
		asoRelatorio.setColaborador(new Colaborador());
		assertTrue(asoRelatorio.getPessoa() instanceof Colaborador);
		
		asoRelatorio.setColaborador(null);
		asoRelatorio.setCandidato(new Candidato());
		assertTrue(asoRelatorio.getPessoa() instanceof Candidato);
	}
	
	public void testGetDataNascimento()
	{
		AsoRelatorio asoRelatorio = new AsoRelatorio();
		
		Calendar candidatoDtNasc = Calendar.getInstance();
		Calendar colaboradorNascimento = Calendar.getInstance();
		colaboradorNascimento.add(Calendar.DAY_OF_MONTH, -1);
		
		Pessoal candidatoPessoal = new Pessoal();
		candidatoPessoal.setDataNascimento(candidatoDtNasc.getTime());
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setPessoal(candidatoPessoal);
		
		asoRelatorio.setCandidato(candidato);
		
		assertEquals(DateUtil.formataDiaMesAno(candidatoDtNasc.getTime()), asoRelatorio.getDataNascimento());
		
		Pessoal colaboradorPessoal = new Pessoal();
		colaboradorPessoal.setDataNascimento(colaboradorNascimento.getTime());
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setPessoal(colaboradorPessoal);
		
		asoRelatorio.setCandidato(null);
		asoRelatorio.setColaborador(colaborador);
		
		assertEquals(DateUtil.formataDiaMesAno(colaboradorNascimento.getTime()), asoRelatorio.getDataNascimento());
	}
	
	public void testGetDataNascimentoVazia()
	{
		AsoRelatorio asoRelatorio = new AsoRelatorio();
		assertEquals("____/____/____", asoRelatorio.getDataNascimento());
	}

}
