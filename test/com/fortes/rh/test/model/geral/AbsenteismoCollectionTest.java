package com.fortes.rh.test.model.geral;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.model.geral.relatorio.AbsenteismoCollection;

public class AbsenteismoCollectionTest extends TestCase
{
    public void testGetMedia()
    {
    	Absenteismo absenteismo1 = new Absenteismo();
    	absenteismo1.setAbsenteismo(20.0);
    	Absenteismo absenteismo2 = new Absenteismo();
    	absenteismo2.setAbsenteismo(10.0);
    	
    	Collection<Absenteismo> absenteismos = new ArrayList<Absenteismo>();
    	absenteismos.add(absenteismo1);
    	absenteismos.add(absenteismo2);
    	
    	AbsenteismoCollection absenteismoCollection = new AbsenteismoCollection();
    	absenteismoCollection.setAbsenteismos(absenteismos);
    	assertEquals(15.0, absenteismoCollection.getMedia());
    	
    }

   

}
