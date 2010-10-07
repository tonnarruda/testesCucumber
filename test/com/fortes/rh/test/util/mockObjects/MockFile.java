package com.fortes.rh.test.util.mockObjects;

import java.io.File;


/**
 * Classe criada para auxiliar a Mockagem estática de métodos de File
 */
public class MockFile extends File
{

	public MockFile(String uri)
	{
		super(uri);
	}

	public boolean exists(){
		return true;
	}
}
