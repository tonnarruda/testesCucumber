package com.fortes.rh.model.geral;

import java.io.Serializable;

import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="comoFicouSabendoVaga_sequence", allocationSize=1)
public class ComoFicouSabendoVaga extends AbstractModel implements Serializable
{
	
}
