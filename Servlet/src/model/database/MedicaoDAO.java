package model.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Medicao;

public class MedicaoDAO extends DAO<Medicao> {
	
	protected MedicaoDAO(){
		super();
	}
	
	@Override
	public void executeStatement(Codigo cod, Medicao valor) {
		PreparedStatement prep;
		switch(cod){
		case INSERT:
			prep = stmts.get(cod);
			try {
				prep.setString(1, valor.getIdBoia());
				prep.setInt(2, valor.getIdSensor());
				prep.setDouble(3, valor.getValor());
				prep.setLong(4, valor.getHorario());
				prep.setInt(5, valor.getNivel());
				
				prep.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public List<Medicao> selectStatement(Codigo cod, Medicao valor) {
		List<Medicao> resp = new ArrayList<Medicao>();
		PreparedStatement prep;
		Medicao b;
		ResultSet result=null;
		switch(cod){
		case SELECT_MED:
			prep = stmts.get(cod);
			try {
				prep.setString(1, valor.getIdBoia());
				prep.setInt(2, valor.getIdSensor());
				result = prep.executeQuery();
				
				while(result.next()){
					
					b = new Medicao(
							result.getInt("id_sensor"),
							result.getString("id_boia"),
							result.getDouble("valor"),
							result.getLong("horario"),
							result.getInt("nivel_alarme")
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
		PreparedStatement insert,delete,update,selectId,selectLike,selectAll,selectTop;
		
		insert = null;
		delete = null;
		update = null;
		selectId = null;
		selectLike = null;
		selectAll =null;
		selectTop=null;
		
		try {
			insert = c.prepareStatement(
					"insert into Medicao (id_boia, id_sensor, valor, horario,nivel_alarme) values (?, ?, ?, ?,?)");
			delete = c.prepareStatement(
					"delete from Medicao where id_boia = ? and id_sensor = ?");
			update = c.prepareStatement(
					"update Medicao set valor = ? and horario=? where id_boia = ? and id_sensor = ?");
			selectId = c.prepareStatement(
					"select * from Medicao where id_boia = ? and id_sensor = ?");
			selectLike = c.prepareStatement(
					"select * from Medicao where id_boia like ? and id_sensor like ? and valor like ? and horario like ?");
			selectAll = c.prepareStatement(
					"select * from Medicao");
			selectTop = c.prepareStatement(
					"select * from Medicao where id_boia=? and id_sensor = ? order by horario desc limit 100");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.stmts.put(Codigo.INSERT, insert);
		this.stmts.put(Codigo.DELETE, delete);
		this.stmts.put(Codigo.UPDATE, update);
		this.stmts.put(Codigo.SELECT_ID, selectId);
		this.stmts.put(Codigo.SELECT_LIKE, selectLike);
		this.stmts.put(Codigo.SELECT_ALL, selectAll);
		this.stmts.put(Codigo.SELECT_MED, selectTop);
	}

}
/*
--insert into Medicao (id_boia,id_sensor,valor,horario,nivel_alarme) values (1,1,100,200,0);
--insert into Medicao (id_boia,id_sensor,valor,horario,nivel_alarme) values (1,1,120,250,0);
--insert into Medicao (id_boia,id_sensor,valor,horario,nivel_alarme) values (1,1,130,300,0);
--insert into Medicao (id_boia,id_sensor,valor,horario,nivel_alarme) values (1,1,140,350,0);
*/