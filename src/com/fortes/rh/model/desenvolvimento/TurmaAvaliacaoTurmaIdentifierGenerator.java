package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TurmaAvaliacaoTurmaIdentifierGenerator implements IdentifierGenerator {

	public TurmaAvaliacaoTurmaIdentifierGenerator() {  }

	public Serializable generate(SessionImplementor session, Object object) {
		Serializable pkValue = new String("0017");  
		return pkValue;
	}
}