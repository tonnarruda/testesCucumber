package com.fortes.rh.util.geradorCodigo;

import java.io.IOException;


public class Principal
{
	//Refresh(F5) o projeto e Commit antes de rodar essa classe...
	public static String NOME_CLASSE = "ConfigHistoricoNivel";
	public static String NOME_CLASSE_MINUSCULO = "configHistoricoNivel";
	public static String NOME_PACOTE = "captacao";
	public static boolean GERAR_TODOS_ARQUIVOS = true;//gera todos os arquivos ou somente entidade, manager e dao 
	
	private static final char separator = java.io.File.separatorChar;
	
	public static void main(String[] args)
	{
		try
		{
			Gerador gerador = new Gerador(NOME_CLASSE, NOME_CLASSE_MINUSCULO, NOME_PACOTE);
			System.out.println(gerador.WORKSPACE);

			//com.fortes.rh...
			gerador.criarClass("model", NOME_CLASSE + ".java", "model.java", "src");
			
			gerador.criarClass("dao", NOME_CLASSE + "Dao.java", "dao.java", "src");
			gerador.criarClass("dao" + separator + "hibernate", NOME_CLASSE + "DaoHibernate.java", "daoHibernate.java", "src");
			
			gerador.criarClass("business", NOME_CLASSE + "Manager.java", "manager.java", "src");
			gerador.criarClass("business", NOME_CLASSE + "ManagerImpl.java", "managerImpl.java", "src");
			
			if(GERAR_TODOS_ARQUIVOS)
			{
				gerador.criarClass("web" + separator + "action", NOME_CLASSE + "EditAction.java", "action.java", "src");
				gerador.criarClass("web" + separator + "action", "xwork-" + NOME_CLASSE  + ".xml", "xworkAction.xml", "src");							
			}

			//classes de testes
			gerador.criarClass("test" + separator + "dao" + separator + "hibernate", NOME_CLASSE + "DaoHibernateTest.java", "daoHibernateTest.java", "test");
			gerador.criarClass("test" + separator + "business", NOME_CLASSE + "ManagerTest.java", "managerTest.java", "test");
			if(GERAR_TODOS_ARQUIVOS)
				gerador.criarClass("test" + separator + "web" + separator + "action", NOME_CLASSE + "EditActionTest.java", "actionTest.java", "test");
			
			gerador.criarClass("test" + separator + "factory", NOME_CLASSE + "Factory.java", "factory.java", "test");

			if(GERAR_TODOS_ARQUIVOS)
			{
				gerador.criarFTL(NOME_CLASSE_MINUSCULO + "Edit.ftl", "edit.txt");
				gerador.criarFTL(NOME_CLASSE_MINUSCULO + "List.ftl", "list.txt");				
			}

			System.out.println("------------------------------------------------------------------------");
			System.out.println("Arquivos gerados com sucesso...");
			if(!GERAR_TODOS_ARQUIVOS)
				System.out.println("NÃO FOI GERADO Action e ftl...(configuração GERAR_TODOS_ARQUIVOS)");
			
			gerador.editarXml("business", "applicationContext-business", "appContext-business", "</beans>");
			gerador.editarXml("dao", "applicationContext-dao", "appContext-dao", "</beans>");
			gerador.editarXml("dao" + separator + "hibernate", "applicationContext-dao-hibernate", "appContext-daoHibernate", "</beans>");
			gerador.editarXml(null, "hibernate.cfg", "hib.cfg", "</session-factory>");
			
			if(GERAR_TODOS_ARQUIVOS)
				gerador.editarXml(null, "xwork", "xw", "</xwork>");
			
			//saida no console
			System.out.println("Arquivos editados com sucesso...");
			System.out.println("------------------------------------------------------------------------");
			System.out.println("ATENÇÃO:");
			System.out.println("Refresh(F5) o projeto");
			System.out.println("Edite os atributos da classe " + NOME_CLASSE + ".java");
			System.out.println("Edite os ftls: validação e campos do formulário");
			System.out.println("Crie o link no menu");
			System.out.println("Crie a regra de segurança no applicationContext-security.xml");
			
			System.out.println("------------------------------------------------------------------------");
			System.out.println("Crie uma migration para criar a tabela da entidade e coloque o script. EXEMPLO:\n");
			System.out.println("CREATE TABLE "+ NOME_CLASSE_MINUSCULO +" (");
			System.out.println("id bigint NOT NULL,");
			System.out.println("descricao character varying(100),");
			System.out.println("numeroCilindro int,");
			System.out.println("ativo boolean,");
			System.out.println("empresa_id bigint");
			System.out.println(");--.go");
			
			System.out.println("\nALTER TABLE "+ NOME_CLASSE_MINUSCULO +" ADD CONSTRAINT "+ NOME_CLASSE_MINUSCULO +"_pkey PRIMARY KEY(id);--.go");
			System.out.println("ALTER TABLE "+ NOME_CLASSE_MINUSCULO +" ADD CONSTRAINT "+ NOME_CLASSE_MINUSCULO +"_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go");
			System.out.println("CREATE SEQUENCE "+ NOME_CLASSE_MINUSCULO +"_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go");
			
			System.out.println("------------------------------------------------------------------------");
			System.out.println("Edite a classe AllUnitTests.java:\n");
			System.out.println("suite.addTestSuite(" + NOME_CLASSE + "DaoHibernateTest.class);");
			System.out.println("suite.addTestSuite(" + NOME_CLASSE + "ManagerTest.class);");
			System.out.println("suite.addTestSuite(" + NOME_CLASSE + "EditActionTest.class);");
			System.out.println("------------------------------------------------------------------------");
			System.out.println("Edite a classe CoberturaGetSetTest.java:\n");
			System.out.println("cobreGetSet(new " + NOME_CLASSE + "());");
		}
		catch (IOException e)
		{
			System.out.println("------------------------------------------------------------------------");
			System.out.println("ERRO: Refresh(F5) o projeto e use o Override and git pull");
			e.printStackTrace();
		}
	}
}