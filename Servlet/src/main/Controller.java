package main;

import java.util.List;

import model.Boia;
import model.Medicao;
import model.Sensor;
import model.database.BoiaDAO;
import model.database.Codigo;
import model.database.FacadeDAOs;
import model.database.MedicaoDAO;
import model.database.Modelo;
import model.database.SensorDAO;

public class Controller {
	public Controller(){
		
	}
	
	public void cadastraMedicao(Medicao m){
		MedicaoDAO d=(MedicaoDAO) FacadeDAOs.getDAO(Modelo.Medicao); 
		d.executeStatement(Codigo.INSERT, m);
	}
	
	public String getBoias(){
		BoiaDAO b = ((BoiaDAO)FacadeDAOs.getDAO(Modelo.Boia));
		List<Boia> l = b.selectStatement(Codigo.SELECT_ALL, null);
		String r="";
		for(Boia c:l){
			r =r+ c.getMac()+"|";
		}
		r+=";";
		return r;
	}
	
	public String getSensoresBoia(Sensor s){
		SensorDAO b = ((SensorDAO)FacadeDAOs.getDAO(Modelo.Sensor));
		List<Sensor> l = b.selectStatement(Codigo.SELECT_SENS_BOIA, s);
		String r="";
		for(Sensor c:l){
			r =r+ c.getId()+","+c.getSTipo()+"|";
		}
		r+=";";
		return r;
	}
	
	public String getMedicoes(Medicao s){
		MedicaoDAO m = (MedicaoDAO)FacadeDAOs.getDAO(Modelo.Medicao);
		List<Medicao> l = m.selectStatement(Codigo.SELECT_MED, s);
		String r="";
		for(Medicao c : l){
			r+=c.getIdBoia()+","+c.getIdSensor()+","+c.getValor()+","+c.getNivel()+","+c.getHorario()+"|";
		}
		r+=";";
		return r;
	}
	
}
