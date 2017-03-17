package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Arrays;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;

public class GerenciadorComunicacaoDaoHibernateTest extends GenericDaoHibernateTest<GerenciadorComunicacao>
{
	private GerenciadorComunicacaoDao gerenciadorComunicacaoDao;
	private EmpresaDao empresaDao;
	private UsuarioDao usuarioDao;

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
	
	public void testFindEmpresasByOperacaoId() 
	{		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Empresa empresa3 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa3);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = getEntity();
		gerenciadorComunicacao1.setOperacao(Operacao.ENVIAR_CARTAO_ANIVERSARIANTES.getId());
		gerenciadorComunicacao1.setEmpresa(empresa1);
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao1);
		
		GerenciadorComunicacao gerenciadorComunicacao2 = getEntity();
		gerenciadorComunicacao2.setOperacao(Operacao.ENVIAR_CARTAO_ANIVERSARIANTES.getId());
		gerenciadorComunicacao2.setEmpresa(empresa3);
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao2);
		
		assertTrue(gerenciadorComunicacaoDao.findEmpresasByOperacaoId(Operacao.ENVIAR_CARTAO_ANIVERSARIANTES.getId()).size() >= 2);
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
	}
	
	
	public void testRemoveByOperacao(){
		Integer[] operacoes = {Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB.getId(), Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB.getId()};
		
		Usuario usuario1 = UsuarioFactory.getEntity("user1", true);
		usuarioDao.save(usuario1);
		
		Usuario usuario2 = UsuarioFactory.getEntity("user2", true);
		usuarioDao.save(usuario2);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao1.setUsuarios(Arrays.asList(usuario1, usuario2));
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao1);
		
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao2);
		
		GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		gerenciadorComunicacao3.setUsuarios(Arrays.asList(usuario1, usuario2));
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao3);
		
		GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		gerenciadorComunicacaoDao.save(gerenciadorComunicacao4);
		
		assertEquals(2, gerenciadorComunicacaoDao.findByOperacaoId(Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB.getId(), empresa.getId()).size());
		assertEquals(2, gerenciadorComunicacaoDao.findByOperacaoId(Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB.getId(), empresa.getId()).size());
		
		gerenciadorComunicacaoDao.removeByOperacao(operacoes);
		
		assertEquals(0, gerenciadorComunicacaoDao.findByOperacaoId(Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB.getId(), empresa.getId()).size());
		assertEquals(0, gerenciadorComunicacaoDao.findByOperacaoId(Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB.getId(), empresa.getId()).size());
	}

	public void testFindByOperacaoAndEmpresaId() 
	{		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = getEntity();
		gerenciadorComunicacao1.setOperacao(Operacao.BOAS_VINDAS_COLABORADORES.getId());
		gerenciadorComunicacao1.setEnviarPara(EnviarPara.COLABORADOR.getId());
		gerenciadorComunicacao1.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
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
		
		GerenciadorComunicacao gerenciadorComunicacao = gerenciadorComunicacaoDao.findByOperacaoIdAndEmpresaId(Operacao.BOAS_VINDAS_COLABORADORES.getId(), empresa.getId());
		
		assertEquals(EnviarPara.COLABORADOR.getId() , gerenciadorComunicacao.getEnviarPara());
		assertEquals(new Integer(Operacao.BOAS_VINDAS_COLABORADORES.getId()) , gerenciadorComunicacao.getOperacao());
		assertEquals(MeioComunicacao.EMAIL.getId() , gerenciadorComunicacao.getMeioComunicacao());
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

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
}
