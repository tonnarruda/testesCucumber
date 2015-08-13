package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.DocumentoAnexo;

@SuppressWarnings("serial")
@Entity
@Table(name="turma_documentoanexo")
@SequenceGenerator(name="sequence", sequenceName="turma_documentoanexo_sequence", allocationSize=1)
public class TurmaDocumentoAnexo extends AbstractModel implements Serializable, Cloneable
{
	@ManyToOne
	private Turma turma; 
	
	@ManyToOne
	private DocumentoAnexo documentoAnexos;
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	public DocumentoAnexo getDocumentoAnexos() {
		return documentoAnexos;
	}

	public void setDocumentoAnexo(DocumentoAnexo documentoAnexos) {
		this.documentoAnexos = documentoAnexos;
	}

	public void setProjectionTurmaId(Long turmaId) {
		if (turma == null)
			turma = new Turma();
		
		turma.setId(turmaId);
	}
	
	public void setProjectionTurmaDescricao(String turmaDescricao) {
		if (turma == null)
			turma = new Turma();
		
		turma.setDescricao(turmaDescricao);
	}
	
	public void setProjectionDocumentoAnexosId(Long documentoAnexoId) {
		if (documentoAnexos == null)
			documentoAnexos = new DocumentoAnexo();
		
		documentoAnexos.setId(documentoAnexoId);
	}
}