package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fortes.model.type.File;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManagerImpl;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.exception.ValidacaoAssinaturaException;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.MedicoCoordenadorFactory;

public class MedicoCoordenadorManagerTest_JUnit4
{
	private MedicoCoordenadorManagerImpl medicoCoordenadorManager;
	private EstabelecimentoManager estabelecimentoManager;
	private MedicoCoordenadorDao medicoCoordenadorDao;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception
    {
        medicoCoordenadorManager = new MedicoCoordenadorManagerImpl();

        estabelecimentoManager = mock(EstabelecimentoManager.class);
        medicoCoordenadorDao = mock(MedicoCoordenadorDao.class);

        medicoCoordenadorManager.setEstabelecimentoManager(estabelecimentoManager);
        medicoCoordenadorManager.setDao(medicoCoordenadorDao);
    }

	@Test
	public void testInsereAssinaturaNulaSemEstabelecimentos() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{};
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		File assinaturaDigital = null;
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L, TipoEstabelecimentoResponsavel.ALGUNS, assinaturaDigital);
		
		when(estabelecimentoManager.findById(estabelecimentosCheck)).thenReturn(estabelecimentos);
		
		medicoCoordenadorManager.insere(medicoCoordenador, estabelecimentosCheck);
		
		verify(medicoCoordenadorDao, times(1)).save(medicoCoordenador);
	}
	
	@Test
	public void testInsereAssinaturaNulaComAlgunsEstabelecimentos() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		File assinaturaDigital = getAssinatura("image", 524287L);;
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L, TipoEstabelecimentoResponsavel.ALGUNS, assinaturaDigital);
		
		when(estabelecimentoManager.findById(estabelecimentosCheck)).thenReturn(estabelecimentos);
		
		medicoCoordenadorManager.insere(medicoCoordenador, estabelecimentosCheck);
		
		verify(medicoCoordenadorDao, times(1)).save(medicoCoordenador);
	}
	
	@Test
	public void testInsereAssinaturaValidaComTodossEstabelecimentos() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		File assinaturaDigital = getAssinatura("html", 524287L);;
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L, TipoEstabelecimentoResponsavel.TODOS, assinaturaDigital);
		
		when(estabelecimentoManager.findById(estabelecimentosCheck)).thenReturn(estabelecimentos);
		
		medicoCoordenadorManager.insere(medicoCoordenador, estabelecimentosCheck);
		
		verify(medicoCoordenadorDao, times(1)).save(medicoCoordenador);
	}
	
	@Test
	public void testInsereAssinaturaInvalidaTipoArquivo() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		File assinaturaDigital = getAssinatura("text/html", 524287L);;
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L, TipoEstabelecimentoResponsavel.TODOS, assinaturaDigital);

		thrown.expect(ValidacaoAssinaturaException.class);
		thrown.expectMessage("Tipo de arquivo não suportado.");
		
	    medicoCoordenadorManager.insere(medicoCoordenador, estabelecimentosCheck);
		
		fail("ValidacaoAssinaturaException não foi lançada.");
	}
	
	@Test
	public void testInsereAssinaturaInvalidaTamanhoArquivo() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		File assinaturaDigital = getAssinatura("image", 524289L);
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L, TipoEstabelecimentoResponsavel.TODOS, assinaturaDigital);
		
		thrown.expect(ValidacaoAssinaturaException.class);
		thrown.expectMessage("Tamanho do arquivo maior que o suportado.");
		
		medicoCoordenadorManager.insere(medicoCoordenador, estabelecimentosCheck);
		
		fail("ValidacaoAssinaturaException não foi lançada.");
	}
	
	@Test
	public void testAtualizaMantendoAssinatura() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		boolean manterAssinatura = Boolean.TRUE;
		File assinaturaDigital = getAssinatura("image", 524289L);
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L, TipoEstabelecimentoResponsavel.TODOS, assinaturaDigital);

		when(medicoCoordenadorDao.getFile("assinaturaDigital", medicoCoordenador.getId())).thenReturn(assinaturaDigital);
		
		medicoCoordenadorManager.atualiza(medicoCoordenador, estabelecimentosCheck, manterAssinatura);
		
		verify(medicoCoordenadorDao, times(1)).update(medicoCoordenador);
	}
	
	@Test
	public void testAtualizaNaoMantendoAssinaturaComAssinaturaNula() throws Exception
	{
		Long[] estabelecimentosCheck = null;
		boolean manterAssinatura = Boolean.FALSE;
		File assinaturaDigital = null;
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L, TipoEstabelecimentoResponsavel.TODOS, assinaturaDigital);
		
		medicoCoordenadorManager.atualiza(medicoCoordenador, estabelecimentosCheck, manterAssinatura);
		
		verify(medicoCoordenadorDao, times(1)).update(medicoCoordenador);
	}

	@Test
	public void testAtualizaComTipoArquivoInvalida() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		boolean manterAssinatura = Boolean.FALSE;
		File assinaturaDigital = getAssinatura("text/html", 524287L);
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L, TipoEstabelecimentoResponsavel.ALGUNS, assinaturaDigital);
		
		when(estabelecimentoManager.findById(estabelecimentosCheck)).thenReturn(estabelecimentos);

		thrown.expect(ValidacaoAssinaturaException.class);
		thrown.expectMessage("Tipo de arquivo não suportado.");
		
		medicoCoordenadorManager.atualiza(medicoCoordenador, estabelecimentosCheck, manterAssinatura);
		
		fail("ValidacaoAssinaturaException não foi lançada.");
	}
	
	@Test
	public void testAtualizaComTamamnhoArssinaturaInvalida() throws Exception
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Long[] estabelecimentosCheck = new Long[]{estabelecimento.getId()};
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		boolean manterAssinatura = Boolean.FALSE;
		File assinaturaDigital = getAssinatura("image", 524289L);
		
		MedicoCoordenador medicoCoordenador = MedicoCoordenadorFactory.getEntity(1L, TipoEstabelecimentoResponsavel.ALGUNS, assinaturaDigital);
		
		when(estabelecimentoManager.findById(estabelecimentosCheck)).thenReturn(estabelecimentos);
		
		thrown.expect(ValidacaoAssinaturaException.class);
		thrown.expectMessage("Tamanho do arquivo maior que o suportado.");
		
		medicoCoordenadorManager.atualiza(medicoCoordenador, estabelecimentosCheck, manterAssinatura);
		
		fail("ValidacaoAssinaturaException não foi lançada.");
	}
	
	private File getAssinatura(String tipoArquivo, Long tamanhoArquivo) {
		File assinatura = new File();
		assinatura.setContentType(tipoArquivo);
		assinatura.setSize(tamanhoArquivo);
		return assinatura;
	}
}