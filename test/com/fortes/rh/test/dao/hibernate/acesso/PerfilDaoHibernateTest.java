package com.fortes.rh.test.dao.hibernate.acesso;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class PerfilDaoHibernateTest extends GenericDaoHibernateTest<Perfil>
{
	private PerfilDao perfilDao;
	private PapelDao papelDao;

	public Perfil getEntity()
	{
		Perfil perfil = new Perfil();

		perfil.setId(null);
		perfil.setNome("nome do perfil");
		perfil.setPapeis(null);

		return perfil;
	}

	public GenericDao<Perfil> getGenericDao()
	{
		return perfilDao;
	}

	public void setPerfilDao(PerfilDao perfilDao)
	{
		this.perfilDao = perfilDao;
	}
	
	public void testFindPerfisByCodigoPapel()
	{
		
		String codigo = "TESTE_PAPEL_FINDPERFIS";
		
		Papel papel = new Papel();
		papel.setCodigo(codigo);
		papelDao.save(papel);
		
		Collection<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel);
		
		criaPerfil(papeis, 1L);
		
		assertEquals(1, perfilDao.findPerfisByCodigoPapel(codigo).size());
		
	}

	public void testFindAll()
	{
		String codigo = "TESTE_PAPEL_FINDPERFIS";
		
		Papel papel = new Papel();
		papel.setCodigo(codigo);
		papelDao.save(papel);
		
		Collection<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel);
		
		criaPerfil(papeis, 1L);
		criaPerfil(papeis, 2L);
		
		assertEquals(2, perfilDao.findAll(0, 2).size());
	}

	private void criaPerfil(Collection<Papel> papeis, Long id) {
		Perfil perfil = new Perfil();
		perfil.setId(id);
		perfil.setNome("Tst");
		perfil.setPapeis(papeis);
		perfilDao.save(perfil);
	}

	public void setPapelDao(PapelDao papelDao) {
		this.papelDao = papelDao;
	}

}