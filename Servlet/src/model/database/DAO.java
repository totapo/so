package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DAO<T>{
	
	protected Connection c;
	protected Map<Codigo,PreparedStatement> stmts;
	
	public DAO(){
		c = Conn.instanceOf();
		stmts = new HashMap<Codigo, PreparedStatement>();
		prepareStatements();
	}
	
	public abstract void executeStatement(Codigo cod, T valor);
	
	public abstract List<T> selectStatement(Codigo c, T valor);
	
	protected abstract void prepareStatements();
	
}
