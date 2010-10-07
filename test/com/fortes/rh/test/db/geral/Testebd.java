package com.fortes.rh.test.db.geral;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

public class Testebd
{
	public static void main(String[] args) throws Exception
	{
		// Exporta dados do banco pro xml
//		Testebd.exportXmlFromDatabase("./test/com/fortes/rh/test/selenium/dados_basicos.xml");
		
		// Importa dados do xml pro banco
		// TODO: Remover constraint papel_papel_fk da tabela papel
		//Testebd.importXmlToDatabase("./test/com/fortes/rh/test/selenium/dados_basicos.xml");
		
	}
	
	/**
	 * Exporta os dados do banco de dados para um Xml DataSet do DbUnit tentando manter a ordem
	 * das tabelas para evitar problemas de constraints.
	 */
	public static void exportXmlFromDatabase(String dbUnitXmlPath) 
		throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException, IOException {
		
		Connection conn = getConnection();
		IDatabaseConnection connection = new DatabaseConnection(conn);
		
		IDataSet iDataSet = connection.createDataSet();
		iDataSet = new FilteredDataSet(new DatabaseSequenceFilter(connection), iDataSet); // se for necessário ordenar devido as constraints - demora pacas
		FlatXmlDataSet.write(iDataSet, new FileOutputStream(dbUnitXmlPath));
	}
	/**
	 * Importa os dados de um Xml DataSet do DbUnit para o banco de dados. As vezes se faz necessário acertar a ordem
	 * dos registros no arquivo xml para evitar problemas de constraints, ou mesmo desabilitar as constraints.
	 */
	public static void importXmlToDatabase(String dbUnitXmlPath) 
		throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException, IOException {
		
		Connection conn = getConnection();
		IDatabaseConnection connection = new DatabaseConnection(conn);
		
		DatabaseOperation.CLEAN_INSERT.execute(connection, new FlatXmlDataSet(new FileInputStream(dbUnitXmlPath)));
	}
	/**
	 * Retorna uma connection do banco de dados de teste.
	 */
	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		// TODO: Melhor obter os dados de uma arquivo .properties
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fortesrh", "postgres", "123");
		return conn;
	}
	
}