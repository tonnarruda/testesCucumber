package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import com.fortes.rh.model.sesmt.OrdemDeServico;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="ordemDeServico_sequence", allocationSize=1)
public class OrdemDeServico extends AbstractModel implements Serializable
{
	
}
