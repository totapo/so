package model.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.TipoSensor;

public class TipoSensorDAO extends DAO<TipoSensor> {
	
	protected TipoSensorDAO(){
		super();
	}
	
	@Override
	public void executeStatement(Codigo cod, TipoSensor valor) {
	}

	@Override
	public List<TipoSensor> selectStatement(Codigo cod, TipoSensor valor) {
		List<TipoSensor> resp = new ArrayList<TipoSensor>();
		PreparedStatement prep;
		TipoSensor b;
		ResultSet result=null;
		switch(cod){
		case SELECT_ID:
			prep = stmts.get(cod);
			try {
				prep.setInt(1, valor.getId());
				result = prep.executeQuery();
				
				while(result.next()){
					
					b = new TipoSensor(
							result.getInt("id"),
							result.getString("descricao")
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
					
					b = new TipoSensor(
							result.getInt("id"),
							result.getString("descricao")
							);
					
					resp.add(b);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:break;
		}
		return resp;
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
					"select * from TipoSensor where id= ?");
			selectAll = c.prepareStatement(
					"select * from TipoSensor");
			
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

}
