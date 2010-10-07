package com.fortes.rh.model.desenvolvimento.relatorio;

import java.io.Serializable;
import java.util.Collection;

import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.Curso;

// Utilizado para gerar curriculo do candidato mais historico.
public class CertificacaoTreinamentosRelatorio implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Certificado certificado;
	private Collection<Curso> cursos;
	
	public Collection<Curso> getCursos()
	{
		return cursos;
	}
	public void setCursos(Collection<Curso> cursos)
	{
		this.cursos = cursos;
	}
	public Certificado getCertificado()
	{
		return certificado;
	}
	public void setCertificado(Certificado certificado)
	{
		this.certificado = certificado;
	}

}
