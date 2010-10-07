package com.fortes.rh.test.model.captacao;

import junit.framework.TestCase;

import com.fortes.rh.model.captacao.Ctps;

public class CtpsTest extends TestCase
{
    public void testGetSerieMaisDv()
    {
		Ctps ctps = new Ctps();
		ctps.setCtpsSerie("11770981");
		
		assertEquals("11770981-", ctps.getSerieMaisDv());
		
		ctps.setCtpsDv('5');
		
		assertEquals("11770981-5", ctps.getSerieMaisDv());
    }
    
    public void testGetSerieMaisDvEmBranco()
    {
    	Ctps ctps = new Ctps();
    	
    	assertEquals(" ", ctps.getSerieMaisDv());
    }
}
