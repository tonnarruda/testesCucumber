package com.fortes.rh.test.web.acpessoal;

import java.util.Date;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalClientCargo;

public class AcPessoalClientCargoTest extends AcPessoalClientTest
{
	private AcPessoalClientCargo acPessoalClientCargo;

	private FaixaSalarial faixaSalarial;
	private FaixaSalarialHistorico faixaSalarialHistorico;
	

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Date data = new Date();
		
		acPessoalClientCargo = new AcPessoalClientCargo();
		acPessoalClientCargo.setAcPessoalClient(acPessoalClientImpl);

		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setNome( "Não vai pro AC");
		
		faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setData(data);
	}

	public void testInsertCargoACPorValor() throws Exception
	{
		faixaSalarial.setNome("Motorista de Nave");
		faixaSalarial.setNomeACPessoal("Motorista de Nave AC");
		
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setValor(200.00);

		String codigoAC = acPessoalClientCargo.criarCargo(faixaSalarial, faixaSalarialHistorico, empresa);
		String[] cargoAC = super.getCargoAC(codigoAC, empresa.getCodigoAC());
		
		assertEquals(empresa.getCodigoAC(), cargoAC[0]);
		assertEquals(codigoAC, cargoAC[1]);
		assertEquals(faixaSalarial.getNomeACPessoal(), cargoAC[2]);
		assertEquals(faixaSalarial.getNome(), cargoAC[3]);
		assertTrue(cargoAC[4].equals("1"));
		
		String[] cargoSituacaoAC = super.getSituacaoCargoAC(codigoAC, empresa.getCodigoAC(), DateUtil.formataDiaMesAno(faixaSalarialHistorico.getData()), "rhsca");		//EMP_CODIGO, CAR_CODIGO, DATA, SALCONTRATUAL, IND_CODIGO_SALARIO, SALTIPO, VALOR, INDQTDE, RH_SCA_ID
		assertEquals(empresa.getCodigoAC(), cargoSituacaoAC[0]);
		assertEquals(codigoAC, cargoSituacaoAC[1]);
		assertEquals("S", cargoSituacaoAC[3]);
		assertNull(cargoSituacaoAC[4]);
		assertEquals("V", cargoSituacaoAC[5]);
		assertEquals(faixaSalarialHistorico.getValor(), Double.parseDouble(cargoSituacaoAC[6]));
		assertEquals(0.0, Double.parseDouble(cargoSituacaoAC[7]));
		assertTrue(cargoSituacaoAC[8].equals("1"));
		
		//Teste edição cargo
		faixaSalarial.setCodigoAC(codigoAC);
		faixaSalarial.setNome("Chefe do Castelo");
		faixaSalarial.setNomeACPessoal("Castelo do AC");
		acPessoalClientCargo.updateCargo(faixaSalarial, empresa);
		cargoAC = super.getCargoAC(codigoAC, empresa.getCodigoAC());
		
		assertEquals(empresa.getCodigoAC(), cargoAC[0]);
		assertEquals(codigoAC, cargoAC[1]);
		assertEquals(faixaSalarial.getNomeACPessoal(), cargoAC[2]);
		assertEquals(faixaSalarial.getNome(), cargoAC[3]);
		assertTrue(cargoAC[4].equals("1"));
		
		deleteCargoAndSituacao(codigoAC);
	}

	public void testInsertCargoACPorIndice() throws Exception
	{
		faixaSalarial.setNome("Motorista de Navio");
		faixaSalarial.setNomeACPessoal("Motorista de Navio AC");

		Indice indice = IndiceFactory.getEntity(1L);
		indice.setCodigoAC("1000");//salario minimo no AC
		
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setQuantidade(5.0);

		String codigoAC = acPessoalClientCargo.criarCargo(faixaSalarial, faixaSalarialHistorico, empresa);
		String[] cargoAC = super.getCargoAC(codigoAC, empresa.getCodigoAC());
		
		assertEquals(empresa.getCodigoAC(), cargoAC[0]);
		assertEquals(codigoAC, cargoAC[1]);
		assertEquals(faixaSalarial.getNomeACPessoal(), cargoAC[2]);
		assertEquals(faixaSalarial.getNome(), cargoAC[3]);
		assertTrue(cargoAC[4].equals("1"));
		
		String[] cargoSituacaoAC = super.getSituacaoCargoAC(codigoAC, empresa.getCodigoAC(), DateUtil.formataDiaMesAno(faixaSalarialHistorico.getData()), "rhsca");
		//EMP_CODIGO, CAR_CODIGO, DATA, SALCONTRATUAL, IND_CODIGO_SALARIO, SALTIPO, VALOR, INDQTDE, RH_SCA_ID
		assertEquals(empresa.getCodigoAC(), cargoSituacaoAC[0]);
		assertEquals(codigoAC, cargoSituacaoAC[1]);
		assertEquals("S", cargoSituacaoAC[3]);
		assertEquals("1000", cargoSituacaoAC[4]);
		assertEquals("I", cargoSituacaoAC[5]);
		assertEquals(0.0, Double.parseDouble(cargoSituacaoAC[6]));
		assertEquals(5.0, Double.parseDouble(cargoSituacaoAC[7]));
		assertTrue(cargoSituacaoAC[8].equals("1"));

		//Teste cria situação para o cargo
		faixaSalarial.setCodigoAC(codigoAC);
		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity(2L);
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico2.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico2.setValor(200.00);
		faixaSalarialHistorico2.setData(DateUtil.criarDataMesAno(01, 02, 2009));
		
		assertTrue(acPessoalClientCargo.criarFaixaSalarialHistorico(faixaSalarialHistorico2, empresa));
		
		cargoSituacaoAC = super.getSituacaoCargoAC(codigoAC, empresa.getCodigoAC(), DateUtil.formataDiaMesAno(faixaSalarialHistorico2.getData()), "rhsca");
		//EMP_CODIGO, CAR_CODIGO, DATA, SALCONTRATUAL, IND_CODIGO_SALARIO, SALTIPO, VALOR, INDQTDE, RH_SCA_ID
		assertEquals(empresa.getCodigoAC(), cargoSituacaoAC[0]);
		assertEquals(codigoAC, cargoSituacaoAC[1]);
		assertEquals("S", cargoSituacaoAC[3]);
		assertNull(cargoSituacaoAC[4]);
		assertEquals("V", cargoSituacaoAC[5]);
		assertEquals(faixaSalarialHistorico2.getValor(), Double.parseDouble(cargoSituacaoAC[6]));
		assertEquals(0.0, Double.parseDouble(cargoSituacaoAC[7]));
		assertTrue(cargoSituacaoAC[8].equals("2"));
		
		deleteCargoAndSituacao(codigoAC);
	}

	public void testEditaCargo() throws Exception
	{
		String codigoAC = "073";
		FaixaSalarial acabamento = FaixaSalarialFactory.getEntity(2L);
		acabamento.setNome("Acabamento RH 1234567891234567");
		acabamento.setNomeACPessoal("Acabamento AC 1234567891234567");
		acabamento.setCodigoAC(codigoAC);
		
		acPessoalClientCargo.updateCargo(acabamento, empresa);
		String[] cargoAC = super.getCargoAC(codigoAC, empresa.getCodigoAC());
		assertEquals(empresa.getCodigoAC(), cargoAC[0]);
		assertEquals(codigoAC, cargoAC[1]);
		assertEquals(acabamento.getNomeACPessoal(), cargoAC[2]);
		assertEquals(acabamento.getNome(), cargoAC[3]);
		assertNull(cargoAC[4]);
	}
	
	private void deleteCargoAndSituacao(String codigoAC) throws Exception
	{
		String[] cargoAC;
		String[] cargoSituacaoAC;
		//Teste delete situação
		boolean deletou = acPessoalClientCargo.deleteFaixaSalarialHistorico(faixaSalarialHistorico.getId(), empresa);
		assertTrue(deletou);
		
		cargoSituacaoAC = super.getSituacaoCargoAC(codigoAC, empresa.getCodigoAC(), DateUtil.formataDiaMesAno(faixaSalarialHistorico.getData()), "rhsca");
		assertNull(cargoSituacaoAC);
		
		//Teste delete cargo
		deletou = acPessoalClientCargo.deleteCargo(new String[]{codigoAC}, empresa);
		assertTrue(deletou);
		
		cargoAC = super.getCargoAC(codigoAC, empresa.getCodigoAC());
		assertNull(cargoAC);
	}
}
