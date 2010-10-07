package com.fortes.rh.test.db.geral;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

public class DbUnitManager {

	public static final String XML_COM_DADOS_BASICOS = "./test/com/fortes/rh/test/selenium/dados_basicos.xml";
	/**
	 * Define se é necessário carregar os dados básicos do banco juntamente com os dados especificos do teste
	 */
	private boolean loadDefaultDataSet = true;
	
	
	public DbUnitManager() {
		this(true);
	}
	/**
	 * Instancia e define se é necessário carregar os dados básicos do banco juntamente com os dados especificos do teste
	 */
	public DbUnitManager(boolean loadDefaultDataSet) {
		this.loadDefaultDataSet = loadDefaultDataSet;
	}
	
	/**
	 * Retorna uma conexão com o banco de dados.
	 */
	private Connection getConnection() {
		// TODO: Poderia pegar a configuração do banco de um arquivo .properties
		Connection conn;
		try {
//			Class.forName("org.firebirdsql.jdbc.FBDriver");
//			conn = DriverManager.getConnection("jdbc:firebirdsql:localhost/3051:C:\\Fortes\\AC\\AC.GDB?user=SYSDBA&password=masterkey");
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fortesrh", "postgres", "123");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao tentar obter uma conexão com o banco: " + e.getMessage());
		}
		return conn;
	}
	/**
	 * Atualiza o banco com os dados do arquivo xml, porém não altera os
	 * registros anteriormente inseridos no banco e que não existem no arquivo
	 * xml.
	 */
	public void refresh(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.CLEAN_INSERT.execute(dbconn, this.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Deleta todos os dados de cada tabela e em seguida insere os registros encontrados no arquivo xml.
	 */
	public void cleanAndInsert(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.CLEAN_INSERT.execute(dbconn, this.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Insere os dados encontrados no arquivo xml.
	 */
	public void insert(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.INSERT.execute(dbconn, this.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Atualiza os registros encontrados no arquivo xml.
	 */
	public void update(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.UPDATE.execute(dbconn, this.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Deleta os registros encontrados no arquivo xml.
	 */
	public void delete(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.DELETE.execute(dbconn, this.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Deleta todos os dados de cada tabela encontrada no arquivo xml.
	 */
	public void deleteAll(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.DELETE_ALL.execute(dbconn, this.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Limpa o banco e popula apenas com os dados básicos.
	 */
	public void clear() {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.CLEAN_INSERT.execute(dbconn, new FlatXmlDataSet(new FileInputStream(XML_COM_DADOS_BASICOS)));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Retorna um DataSet baseado no arquivo xml passado como parâmetro.
	 */
	private IDataSet getDataSetFrom(String dbUnitXmlPath)
			throws IOException, DataSetException, FileNotFoundException {
		if (loadDefaultDataSet) {
			return new CompositeDataSet(
					new FlatXmlDataSet(new FileInputStream(XML_COM_DADOS_BASICOS)),
					new FlatXmlDataSet(new FileInputStream(dbUnitXmlPath)));
		} else {
			return new FlatXmlDataSet(new FileInputStream(dbUnitXmlPath));
		}
	}
	/**
	 * Retorna um wrapper do DbUnit para conexão com o banco de dados.
	 * @throws DatabaseUnitException
	 */
	private IDatabaseConnection getDbUnitConnection() throws DatabaseUnitException {
		IDatabaseConnection dbconn = new DatabaseConnection(this.getConnection());
		return dbconn;
	}
	
	public void dump(String dbUnitXmlPath)
	{
		try
		{
			IDatabaseConnection connection = new DatabaseConnection(getConnection());
		
			IDataSet iDataSet = connection.createDataSet();
			
			//se for necessário ordenar devido as constraints - demora pacas
//			iDataSet = new FilteredDataSet(new DatabaseSequenceFilter(connection), iDataSet); 
			
			FlatXmlDataSet.write(iDataSet, new FileOutputStream(dbUnitXmlPath));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}	
}
