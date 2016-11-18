package model.database;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class FacadeDAOs {
	
	private static Map<Modelo, DAO> daos;
	
	static{
		daos = new HashMap<Modelo, DAO>();
		
		daos.put(Modelo.Boia, new BoiaDAO());
		daos.put(Modelo.TipoSensor, new TipoSensorDAO());
		daos.put(Modelo.Sensor, new SensorDAO());
		daos.put(Modelo.Medicao, new MedicaoDAO());
	}
	
	public static DAO getDAO(Modelo c){
		return daos.get(c);
	}
	
}
