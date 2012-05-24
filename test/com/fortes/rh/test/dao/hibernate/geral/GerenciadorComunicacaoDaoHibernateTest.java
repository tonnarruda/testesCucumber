package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;

public class GerenciadorComunicacaoDaoHibernateTest extends GenericDaoHibernateTest<GerenciadorComunicacao>
{
	private GerenciadorComunicacaoDao gerenciadorComunicacaoDao;
	private EmpresaDao empresaDao;

	public void testFindByOperacao() 
	{		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = getEntity();
		gerenciadorComunicacao1.setOperacao(Operacao.ENCERRAR_SOLICITACAO.getId());
		gerenciadorComunicacao1.setEmpresa(empresa);
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao1);
		
		GerenciadorComunicacao gerenciadorComunicacao2 = getEntity();
		gerenciadorComunicacao2.setOperacao(Operacao.ENCERRAR_SOLICITACAO.getId());
		gerenciadorComunicacao2.setEmpresa(empresa);
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao2);
		
		GerenciadorComunicacao gerenciadorComunicacao3 = getEntity();
		gerenciadorComunicacao3.setOperacao(Operacao.GERAR_BACKUP.getId());
		gerenciadorComunicacao3.setEmpresa(empresa);
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao3);
		
		assertEquals(2 , gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ENCERRAR_SOLICITACAO.getId(), empresa.getId()).size());
	}
	
	public void testVerifyExists() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = getEntity();
		gerenciadorComunicacao1.setOperacao(Operacao.ENCERRAR_SOLICITACAO.getId());
		gerenciadorComunicacao1.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		gerenciadorComunicacao1.setEnviarPara(EnviarPara.USUARIOS.getId());
		gerenciadorComunicacao1.setEmpresa(empresa);

		assertFalse(gerenciadorComunicacaoDao.verifyExists(gerenciadorComunicacao1));
		
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao1);
		
		GerenciadorComunicacao gerenciadorComunicacao2 = getEntity();
		gerenciadorComunicacao2.setOperacao(Operacao.ENCERRAR_SOLICITACAO.getId());
		gerenciadorComunicacao2.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		gerenciadorComunicacao2.setEnviarPara(EnviarPara.USUARIOS.getId());
		gerenciadorComunicacao2.setEmpresa(empresa);
				
		assertTrue(gerenciadorComunicacaoDao.verifyExists(gerenciadorComunicacao2));
		
//		GerenciadorComunicacao gerenciadorComunicacao3 = getEntity();
//		gerenciadorComunicacao3.setOperacao(Operacao.NAO_INFORMADO.getId());
//		gerenciadorComunicacao3.setEmpresa(empresa);
//		gerenciadorComunicacaoDao.save(gerenciadorComunicacao3);
//		
//		assertEquals(2 , gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ENCERRAMENTO_SOLICITACAO.getId(), empresa.getId()).size());
	}
	
	@Override
	public GerenciadorComunicacao getEntity()
	{
		return GerenciadorComunicacaoFactory.getEntity();
	}

	@Override
	public GenericDao<GerenciadorComunicacao> getGenericDao()
	{
		return gerenciadorComunicacaoDao;
	}

	public void setGerenciadorComunicacaoDao(GerenciadorComunicacaoDao gerenciadorComunicacaoDao)
	{
		this.gerenciadorComunicacaoDao = gerenciadorComunicacaoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
