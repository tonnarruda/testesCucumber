package com.fortes.rh.test.selenium.integracaoAC;	

import dbunit.DbUnitManager;

public class GeraBancoTeste
{
	public static void main(String[] args)
	{
		new DbUnitManager(false).cleanAndInsert("./test/com/fortes/rh/test/selenium/integracaoAC/deleteMinimoTestIntegracao.xml");
		new DbUnitManager(false).cleanAndInsert("./test/com/fortes/rh/test/selenium/integracaoAC/dumpMinimoTestIntegracao.xml");
		
//		new DbUnitManager(false).deleteAll("./test/com/fortes/rh/test/selenium/integracaoAC/colaboradorOcorrenciaOcorrenciaDelete.xml");
//		new DbUnitManager(false).cleanAndInsert("./test/com/fortes/rh/test/selenium/integracaoAC/ocorrenciaTipoOcorrenciaInsert.xml");
//		new DbUnitManager(false).cleanAndInsert("./test/com/fortes/rh/test/selenium/integracaoAC/colaboradorOcorrenciaOcorrenciaInsert.xml");
	
//		new DbUnitManager(false).dump("./test/com/fortes/rh/test/selenium/integracaoAC/teste.xml");
//		new DbUnitManager(false).cleanAndInsert("./test/com/fortes/rh/test/selenium/BDBasicao.xml");

//		new DbUnitManager(false).cleanAndInsert("./test/com/fortes/rh/test/selenium/integracaoAC/indiceInsert.xml");
//		new DbUnitManager(false).deleteAll("./test/com/fortes/rh/test/selenium/integracaoAC/indiceDelete.xml");
//		new DbUnitManager(false).clear();

//		new DbUnitManager(false).cleanAndInsert("./test/com/fortes/rh/test/selenium/integracaoAC/cargoInsert.xml");
//		new DbUnitManager(false).deleteAll("./test/com/fortes/rh/test/selenium/integracaoAC/cargoDelete.xml");
	}
}