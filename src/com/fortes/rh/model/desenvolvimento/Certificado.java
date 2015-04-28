package com.fortes.rh.model.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.desenvolvimento.relatorio.CertificacaoTreinamentosRelatorio;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;
import java.math.BigDecimal;


public class Certificado implements Cloneable
{
	private String titulo;
	private String data;
	private String conteudo;
	private String ass1;
	private String ass2;
	private String tamanho;
	private boolean imprimirMoldura;
	private boolean imprimirLogo;
	private boolean imprimirLogoCertificado;
	private BigDecimal nota;
	
	public Certificado()
	{
	}

	public Certificado(Turma turma, String localidade, boolean imprimirMoldura, boolean imprimirLogo)
	{
		titulo = turma.getCurso().getNome();
		data = localidade + ", " + DateUtil.formataDataExtenso(new Date());
		// expressão #NOMECOLABORADOR# utilizado tambem no setNomeColaborador
		conteudo = "Certificamos que #NOMECOLABORADOR# participou do curso " + turma.getCurso().getNome().toUpperCase() + " com carga horária de " + turma.getCurso().getCargaHorariaMinutos() + " horas-aula.";
		ass1 = "Diretor";
		ass2 = "Instrutor \n(" + turma.getInstrutor() + ")";
		
		this.imprimirMoldura =imprimirMoldura;
		this.imprimirLogo = imprimirLogo;
	}

	public Certificado(Collection<Curso> cursos, Certificacao certificacao, String localidade, boolean imprimirMoldura, boolean imprimirLogo)
	{
		Integer cargaHorariaTotal = 0;
		for (Curso curso : cursos)
		{
			if(curso.getCargaHoraria() != null)
				cargaHorariaTotal += curso.getCargaHoraria(); 
		}
		
		Curso cursoTemp = new Curso();
		cursoTemp.setCargaHoraria(cargaHorariaTotal);
		
		titulo = certificacao.getNome();
		data = localidade + ", " + DateUtil.formataDataExtenso(new Date());
		// expressão #NOMECOLABORADOR# utilizado tambem no setNomeColaborador
		conteudo = "Certificamos que o(a) colaborador(a) #NOMECOLABORADOR# cumpriu todo o programa de treinamentos " + certificacao.getNome().toUpperCase() + " com carga horária total de " + cursoTemp.getCargaHorariaMinutos() + " horas-aula.";
		ass1 = "Diretor";

		this.imprimirMoldura =imprimirMoldura;
		this.imprimirLogo = imprimirLogo;
	}

	public void setNomeColaborador(String colaboradorNome)
	{
		this.setConteudo(this.conteudo.replaceAll("#NOMECOLABORADOR#", colaboradorNome));
	}

	public Object clone()
	{
		try
		{
			Certificado clone = (Certificado) super.clone();

			return clone;
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error("Não é possível gerar clone.");
		}
	}

	public static Collection<Certificado> gerarColecaoVerso(Collection<Colaborador> colaboradores, Certificado certificado)
	{
		Collection<Certificado> certificados = new ArrayList<Certificado>(colaboradores.size());
		for (Colaborador colaborador: colaboradores)
		{
			Certificado certificadoTmp = new Certificado();
			certificadoTmp.setImprimirMoldura(certificado.isImprimirMoldura());		
			certificadoTmp.setNota(colaborador.getNota());		
			certificados.add(certificadoTmp);
		}
		
		return certificados;
	}

	public static Collection<CertificacaoTreinamentosRelatorio> montaCertificacao(String[] colaboradoresCheck, Certificado certificado, Collection<Curso> cursos)
	{
		Collection<CertificacaoTreinamentosRelatorio> certificacaoTreinamentos = new ArrayList<CertificacaoTreinamentosRelatorio>(colaboradoresCheck.length);
		for (String id: colaboradoresCheck)
		{
			Certificado certificadoTmp = new Certificado();
			certificadoTmp.setImprimirMoldura(certificado.isImprimirMoldura());
			
			CertificacaoTreinamentosRelatorio certificacaoTreinamentosRelatorio = new CertificacaoTreinamentosRelatorio();
			certificacaoTreinamentosRelatorio.setCertificado(certificado);
			certificacaoTreinamentosRelatorio.setCursos(cursos);
			certificacaoTreinamentos.add(certificacaoTreinamentosRelatorio);
		}

		return certificacaoTreinamentos;
	}

	public String getAss1()
	{
		return ass1;
	}

	public void setAss1(String ass1)
	{
		this.ass1 = ass1;
	}

	public String getAss2()
	{
		return ass2;
	}

	public void setAss2(String ass2)
	{
		this.ass2 = ass2;
	}

	public String getConteudo()
	{
		return conteudo;
	}

	public void setConteudo(String conteudo)
	{
		this.conteudo = conteudo;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public boolean isImprimirLogo()
	{
		return imprimirLogo;
	}

	public void setImprimirLogo(boolean imprimirLogo)
	{
		this.imprimirLogo = imprimirLogo;
	}

	public boolean isImprimirMoldura()
	{
		return imprimirMoldura;
	}

	public void setImprimirMoldura(boolean imprimirMoldura)
	{
		this.imprimirMoldura = imprimirMoldura;
	}

	public String getTamanho()
	{
		return tamanho;
	}

	public void setTamanho(String tamanho)
	{
		this.tamanho = tamanho;
	}

	public String getTitulo()
	{
		return titulo;
	}

	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}

	public boolean isImprimirLogoCertificado()
	{
		return imprimirLogoCertificado;
	}

	public void setImprimirLogoCertificado(boolean imprimirLogoCertificado)
	{
		this.imprimirLogoCertificado = imprimirLogoCertificado;
	}

	public BigDecimal getNota()
	{
		return nota;
	}

	public void setNota(java.math.BigDecimal nota)
	{
		this.nota = nota;
	}

}