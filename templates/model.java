package com.fortes.rh.model.#NOME_PACOTE#;

import java.io.Serializable;

import com.fortes.rh.model.#NOME_PACOTE#.#NOME_CLASSE#;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="#NOME_CLASSE_MINUSCULO#_sequence", allocationSize=1)
public class #NOME_CLASSE# extends AbstractModel implements Serializable
{
	
}