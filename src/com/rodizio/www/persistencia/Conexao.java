package com.rodizio.www.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	private static Connection conn=null;
	private static final java.io.File DATABASE = new java.io.File(
			System.getProperty("user.home") + 
			System.getProperty("file.separator") + 
			"SistemaRodizioCCB" + 
			System.getProperty("file.separator")	+ 
			"BancoDados" + 
			System.getProperty("file.separator")	+ 
			"rodizio.db");
			//"rodizio");
	
	public static Connection getConnection() throws Exception {
		if(conn != null){
		conn.close();	
		}
		
		try {
			Class.forName("org.sqlite.JDBC");
			//Class.forName("org.hsqldb.jdbcDriver" );
			
		  } catch (Exception e) {
		      System.err.println("ERRO: falha ao carregar o driver JDBC!");
		      e.printStackTrace();
		      return null;
		  }
		try{
			conn =  DriverManager.getConnection("jdbc:sqlite:" + DATABASE.getPath());
			/*conn =  DriverManager.getConnection("jdbc:hsqldb:C:/Users/Wesley/SistemaRodizioCCB/rodizio", "SA", "");*/
		  } catch (Exception e) {
		      System.err.println("ERRO: falha ao carregar o Banco de Dados!");
		      e.printStackTrace();
		      return null;
		  }
		
		
		return conn;
		}
	
	public static boolean checkDatabase() throws Exception {
		if (!DATABASE.exists()) {
			if(!createNewDatabase()){
				return false;
			}
		}
		return true;
	}

	private static boolean createNewDatabase() throws Exception {
		try {

			DATABASE.getParentFile().mkdirs(); //Cria os diret�rios pai do arquivo (caso n�o existam)
			DATABASE.createNewFile();// Cria o arquivo do banco
			if (!DATABASE.exists()) { //Caso o arquivo ainda n�o exista, ap�s os comandos acima, dispara exce��o
				throw new Exception("Erro ao gravar o arquivo de banco de dados.");//
			}
			return true;

		} catch (Exception ex) {
			throw new Exception("Erro na cria��o do banco de dados\n" + ex.getMessage());
		}
	}
}


