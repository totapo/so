package model.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Boia;

public class BoiaDAO extends DAO<Boia>{
	
	protected BoiaDAO(){
		super();
	}

	@Override
	protected void prepareStatements() {
		PreparedStatement insert,delete,update,selectId,selectLike,selectAll;
		
		insert = null;
		delete = null;
		update = null;
		selectId = null;
		selectLike = null;
		selectAll =null;
		
		try {
			selectId = c.prepareStatement(
					"select * from Boia where id=?");
			selectAll = c.prepareStatement(
					"select * from Boia");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.stmts.put(Codigo.INSERT, insert);
		this.stmts.put(Codigo.DELETE, delete);
		this.stmts.put(Codigo.UPDATE, update);
		this.stmts.put(Codigo.SELECT_ID, selectId);
		this.stmts.put(Codigo.SELECT_LIKE, selectLike);
		this.stmts.put(Codigo.SELECT_ALL, selectAll);
	}

	@Override
	public void executeStatement(Codigo cod, Boia valor) {
	}

	@Override
	public List<Boia> selectStatement(Codigo cod, Boia valor) {
		List<Boia> resp = new ArrayList<Boia>();
		PreparedStatement prep;
		Boia b;
		ResultSet result=null;
		switch(cod){
		case SELECT_ID:
			prep = stmts.get(cod);
			try {
				prep.setString(1, valor.getMac());
				result = prep.executeQuery();
				
				while(result.next()){
					
					b = new Boia(
							result.getString("id")
							);
					
					resp.add(b);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case SELECT_ALL:
			prep = stmts.get(cod);
			try {
				result = prep.executeQuery();
				
				while(result.next()){
					
					b = new Boia(
							result.getString("id")
							);
					
					resp.add(b);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case SELECT_LIKE:
			break;
		default:break;
		}
		return resp;
	}

	
}
