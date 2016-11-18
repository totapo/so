package model.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Sensor;

public class SensorDAO extends DAO<Sensor> {

	protected SensorDAO(){
		super();
	}
	
	@Override
	public void executeStatement(Codigo cod, Sensor valor) {
	}

	@Override
	public List<Sensor> selectStatement(Codigo cod, Sensor valor) {
		List<Sensor> resp = new ArrayList<Sensor>();
		PreparedStatement prep;
		Sensor b;
		ResultSet result=null;
		switch(cod){
		case SELECT_ID:
			prep = stmts.get(cod);
			try {
				prep.setInt(1, valor.getId());
				result = prep.executeQuery();
				
				while(result.next()){
					
					b = new Sensor(
							result.getInt("id"),
							result.getString("id_boia"),
							result.getInt("id_tipo")
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
					
					b = new Sensor(
							result.getInt("id"),
							result.getString("id_boia"),
							result.getInt("id_tipo")
							);
					
					resp.add(b);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case SELECT_SENS_BOIA:
			prep = stmts.get(cod);
			try {
				prep.setString(1, valor.getIdBoia());
				result = prep.executeQuery();
				
				while(result.next()){
					
					b = new Sensor(
							result.getInt("id"),
							valor.getIdBoia(),
							valor.getTipo()
							);
					b.setTipo(result.getString("descricao"));
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
		PreparedStatement insert,delete,update,selectId,selectLike,selectAll,selectSensoresBoia;
		
		insert = null;
		delete = null;
		update = null;
		selectId = null;
		selectLike = null;
		selectAll =null;
		selectSensoresBoia=null;
		
		try {
			insert = c.prepareStatement(
					"insert into Sensor (id_boia, id_tipo) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
			delete = c.prepareStatement(
					"delete from Sensor where id = ? and id_boia = ? and id_tipo = ?");
			update = c.prepareStatement(
					"update Sensor set id_boia = ? and id_tipo = ? where id = ?");
			selectId = c.prepareStatement(
					"select * from Sensor where id= ?");
			selectAll = c.prepareStatement(
					"select * from Sensor");
			selectSensoresBoia = c.prepareStatement(
					"select Sensor.id as id, TipoSensor.descricao as descricao" +
					" from Sensor,TipoSensor "+
					"where Sensor.id_boia=? AND Sensor.id_tipo = TipoSensor.id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.stmts.put(Codigo.INSERT, insert);
		this.stmts.put(Codigo.DELETE, delete);
		this.stmts.put(Codigo.UPDATE, update);
		this.stmts.put(Codigo.SELECT_ID, selectId);
		this.stmts.put(Codigo.SELECT_LIKE, selectLike);
		this.stmts.put(Codigo.SELECT_ALL, selectAll);
		this.stmts.put(Codigo.SELECT_SENS_BOIA,selectSensoresBoia);
	}
	
}
