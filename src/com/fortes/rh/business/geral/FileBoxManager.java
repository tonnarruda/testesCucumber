package com.fortes.rh.business.geral;

import java.io.File;

public interface FileBoxManager
{
	void enviar(String fileName, String description, File file) throws Exception;
}