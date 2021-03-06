package com.fortes.rh.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fortes.rh.util.ArquivoUtil;

public class JDBCConnection {

	private static final Logger logger = Logger.getLogger(JDBCConnection.class);
	
	private String driverClass;
	private String jdbcUrl;
	private String user;
	private String password;

	public JDBCConnection(Properties properties) {
		this.driverClass = properties.getProperty("hibernate.connection.driver_class").trim();
		this.jdbcUrl = properties.getProperty("hibernate.connection.url").trim();
		this.user = properties.getProperty("hibernate.connection.username").trim();
		this.password = properties.getProperty("hibernate.connection.password").trim();
	}

	/**
	 * Retorna uma nova conexao com o banco de dados.
	 */
	private Connection getConexao() throws ClassNotFoundException, SQLException {
		Class.forName(this.driverClass);
		Connection conexao = DriverManager.getConnection(this.jdbcUrl, this.user, this.password);
		conexao.setAutoCommit(false);
		return conexao;
	}
	/**
	 * Retorna versao do sistema.
	 */
	public String getVersao() {
		return executeSql("select appversao from parametrosdosistema where id = 1");
	}

	public String executeSql(final String sql) {
		// obtem versao
		String resultado = (String) this.execute(new RunAQuery() {
			public Object run(Statement stmt) throws SQLException {
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				return rs.getString(1);
			}
		});
		return StringUtils.defaultString(resultado);
	}
	/**
	 * Executa um array de SQL queries. Se o array estiver vazia nada será feito.
	 */
	public void execute(final String[] queries) {
		
		boolean hasNotQueries = (queries.length == 0);
		if (hasNotQueries) {
			logger.info("Nenhum comando SQL foi enviado para execução.");
			return;
		}

		this.execute(new RunAQuery() {
			public Object run(Statement stmt) throws SQLException {
				// itera por todas as queries
				for (String query : queries) {
					stmt.execute(query);
				}
				return null;
			}
		});
		
	}
	/**
	 * Executa uma instrucao dentro de uma transacao.
	 */
	private Object execute(RunAQuery query) {
		
		Object result;
		
		Statement stmt = null;
		Connection conexao = null;
		
		try {
			conexao = this.getConexao();
			stmt = conexao.createStatement();
			result = query.run(stmt);
			conexao.commit();
		} catch (Exception e) {
			try {
				if (conexao != null)
					conexao.rollback();
				throw new RuntimeException("Erro na execução dos comandos: " + e.getMessage(), e);
			} catch (SQLException se) {
				throw new RuntimeException("Problemas na execução dos comandos e no rollback.", se);
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException("Problemas ao fechar Statement.", e);
			} finally {
				try {
					if (conexao != null)
						conexao.close();
				} catch (SQLException e) {
					throw new RuntimeException(
							"Problemas ao fechar Connection.", e);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Utilizado para definir uma instrução atomica.
	 */
	private interface RunAQuery {
		public Object run(Statement stmt) throws SQLException;
	}

	public Integer getQtdTabelas() throws Exception
	{
		Integer resultado = (Integer) this.execute(new RunAQuery() {
			public Object run(Statement stmt) throws SQLException {
				ResultSet rs = stmt.executeQuery(
						"select count(table_name) from information_schema.tables where table_schema='public' and table_type='BASE TABLE';");
				rs.next();
				return rs.getInt(1);
			}
		});
		
		return resultado;
	}

	public static void executeQuery(String[] sqls)
	{
		Properties configuracao = ArquivoUtil.getSystemConf();
		JDBCConnection jdbcConn = new JDBCConnection(configuracao);
		jdbcConn.execute(sqls);
	}
	
	public static String executeQuery(String sql)
	{
		Properties configuracao = ArquivoUtil.getSystemConf();
		JDBCConnection jdbcConn = new JDBCConnection(configuracao);
		return jdbcConn.executeSql(sql);
	}
	
	public static void executaTrigger(String acao)
	{
		Properties configuracao = ArquivoUtil.getSystemConf();
		JDBCConnection jdbcConn = new JDBCConnection(configuracao);
		
		if (StringUtils.isNotEmpty(configuracao.getProperty("db.name").trim()))
		{
			String sql = "select alter_trigger(table_name, '" + acao + "') FROM information_schema.constraint_column_usage " +
						" where table_schema='public' " +
						" and table_catalog='" + configuracao.getProperty("db.name").trim() + "' group by table_name;";
			
			jdbcConn.executeSql(sql);
		}
	}
}