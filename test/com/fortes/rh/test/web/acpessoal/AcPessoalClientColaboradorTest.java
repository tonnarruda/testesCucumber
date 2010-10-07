package com.fortes.rh.test.web.acpessoal;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.web.ws.AcPessoalClientColaboradorImpl;

public class AcPessoalClientColaboradorTest extends AcPessoalClientTest
{
	private Colaborador colaborador;
	private TEmpregado tEmpregado;
	private TSituacao tSituacao;

	private AcPessoalClientColaboradorImpl acPessoalClientColaboradorImpl;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientColaboradorImpl = new AcPessoalClientColaboradorImpl();
		acPessoalClientColaboradorImpl.setAcPessoalClient(acPessoalClientImpl);

		tEmpregado = new TEmpregado();
		tEmpregado.setId(1);
		tEmpregado.setNome("Fulano da Silva");
		tEmpregado.setDataAdmissao("02/10/2009");
		tEmpregado.setDataNascimento("01/05/2000");
		tEmpregado.setEmpresaCodigoAC(empresa.getCodigoAC());
		
		tSituacao = new TSituacao();
		tSituacao.setId(2);
		tSituacao.setData("02/10/2009");
		tSituacao.setValor(5.0);
		tSituacao.setValorAnterior(4.0);
		tSituacao.setIndiceQtd(0.0);
	}

	public void testContratarColaboradorAC() throws Exception
	{
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));
		
		try
		{
			TEmpregado empregadoAC = acPessoalClientColaboradorImpl.getEmpregadoACAConfirmar(tEmpregado.getId(), empresa);
			assertEquals(tEmpregado.getNome(), empregadoAC.getNome());
			assertEquals(tEmpregado.getDataAdmissao(), empregadoAC.getDataAdmissao());
			assertEquals(tEmpregado.getDataNascimento(), empregadoAC.getDataNascimento());
			assertEquals(tEmpregado.getEmpresaCodigoAC(), empregadoAC.getEmpresaCodigoAC());
			assertEquals(tEmpregado.getId(), empregadoAC.getId());
		}
		finally
		{
//			colaborador = ColaboradorFactory.getEntity(1L);
//			colaborador.setCodigoAC(null);
//			assertEquals(true, acPessoalClientColaboradorImpl.remove(colaborador, empresa));
		}
	}

	public void testEditarColaboradorAC() throws Exception
	{
		tEmpregado.setEmpresaCodigoAC(empresa.getCodigoAC());
		acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa);

		try
		{
			tEmpregado.setNome("Fulano da Silva Sauro");
			tEmpregado.setCodigoAC("000029");
			tEmpregado.setCidadeCodigoAC("04400");
			tEmpregado.setUfSigla("CE");
			acPessoalClientColaboradorImpl.atualizar(tEmpregado, empresa);
			
			TEmpregado empregadoAC = acPessoalClientColaboradorImpl.getEmpregadoACAConfirmar(tEmpregado.getId(), empresa);
			assertEquals(tEmpregado.getNome(), empregadoAC.getNome());
			assertEquals(tEmpregado.getCodigoAC(), empregadoAC.getCodigoAC());			
			assertEquals(tEmpregado.getUfSigla(), empregadoAC.getUfSigla());
		}
		finally
		{
			colaborador = ColaboradorFactory.getEntity(1L);
			colaborador.setCodigoAC(null);
			assertEquals(true, acPessoalClientColaboradorImpl.remove(colaborador, empresa));
		}
	}
	
	public void testRemoverColaboradorAC() throws Exception
	{
		colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC(null);
		
		tEmpregado.setId(1);
		
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));
		assertEquals(true, acPessoalClientColaboradorImpl.remove(colaborador, empresa));
	}

	public void testVerificaHistoricoNaFolhaAC() throws Exception
	{
		assertEquals(false, acPessoalClientColaboradorImpl.verificaHistoricoNaFolhaAC(1L, "99554", empresa));
		//AMANDA LEITÃO, ta no banco com uma folha, precisei setar a data da situação na tabela EFO
		assertEquals(true, acPessoalClientColaboradorImpl.verificaHistoricoNaFolhaAC(27L, "000029", empresa));
	}
}
