package com.fortes.rh.test.web.acpessoal;

import java.sql.ResultSet;

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
	
	@Override
	protected void tearDown() throws Exception
	{
		delete("delete from ctt where empcodigoac='" + getEmpresa().getCodigoAC() + "'");
		delete("update epg set nome='AMANDA LEITAO DOS SANTOS' where codigo >= '000029' and emp_codigo='" + getEmpresa().getCodigoAC() + "'");
		
		String sqlAmanda = "INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME,DTNASCIMENTO,NACIONALIDADE,ANOCHEGADA,TIPOVISTO,DTVALIDADERG,DTVALIDADECTPS,MUN_UFD_SIGLA_NATURALIDADE,MUN_CODIGO_NATURALIDADE,GRAUINSTRUCAO,RACACOR,SEXO,MAENOME,PAINOME,ESTADOCIVIL,CONJUGENOME,CONJUGECPF,CONJUGEDTNASCIMENTO,CONTACORRENTE,AGE_BAN_CODIGO,AGE_CODIGO,CONTACORRENTENUMERO,CONTASALARIO,DDD,FONE,CELULAR,EMAIL,ENDLOGRADOURO,ENDNUMERO,ENDCOMPLEMENTO,BAIRRO,CEP,MUN_UFD_SIGLA,MUN_CODIGO,CTPSSERIE,CTPSDV,UFD_SIGLA_CTPS,CTPSDTEXPEDICAO,PIS,CPF,TITULO,ZONA,SECAO,IDENTIDADENUMERO,IDENTIDADEORGAOEXPEDIDOR,IDENTIDADEDTEXPEDICAO,REGISTROLIVRO,REGISTRONUMERO,ADMISSAODATA,ADMISSAOTIPO,ADMISSAONATUREZA,ADMISSAOVINCULO,FGTS,FGTSDTOPCAO,FGTSCODCEF,EXAMEMEDICODTULTIMO,EXAMEMEDICOVALIDADE,FERIASDTPREVISAO,DTRESCISAO,PRAZODETERMINADO,QTDDIASPRAZO,QTDDIASPRAZOPRORROGACAO,DTTERMINOPRAZO,ANOCTRSINDICAL,DEFICIENTEFISICO,CATEGORIA,CIPA,DTAPOSENTADORIA,HABILITACAONUMERO,HABILITACAOEMISSAO,HABILITACAOVENCIMENTO,HABILITACAOCATEGORIA,PARTICIPAPAT,ALVARA,DTTRANSFERENCIA,CMTIPO,CMNUMERO,CMSERIE,CMCATEGORIA,CMCSM_OAM,CMRM_DN_COMAR,EXPRADIACAO,TIPOSANGUINEO,FATORRH,CTPSNUMERO,SENHAWEB,MUN_UFD_SIGLA_IDENTORGAOEXPED,DTFALECIMENTO,DTEXONERACAO,SALARIOCONTRATUAL,ANO1EMPREGO,NOMECOMERCIAL,FOTO,HORARIOPONTO) VALUES ('0006','000029','AMANDA LEITAO DOS SANTOS','1972-03-12 00:00:00','10',NULL,NULL,NULL,NULL,'CE','12908','09','2','M','FRANCISCA EVANILDA ALVES MEDEIROS','JOSE CLEITON CAVALCANTE','03',NULL,NULL,NULL,'N',NULL,NULL,NULL,0,'085','2354862',NULL,NULL,'RUA NICARAGUA','59','A','ANTONIO BEZERRA','60510000','CE','04400','00029',NULL,'CE',NULL,'12504617994','43067310306','327718307/52','089','024','180791-88','SSP',NULL,NULL,'00002','1997-11-01 00:00:00','20','9','10','N','1997-11-01 00:00:00','00000080060',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2004','0','01','N',NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'N',NULL,NULL,'0072399',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL)";
		super.tearDown();
	}
	
	public void testStatusAC() throws Exception
	{
		ResultSet result = execute("select count(*) as total from car where emp_codigo = '" + getEmpresa().getCodigoAC() + "'");
		if (result.next())
			assertEquals(172, result.getInt("total"));
		else
			fail("Consulta não retornou nada...");
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
