package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Medicao;
import model.Sensor;

/**
 * Servlet implementation class Hello
 */
@WebServlet("/Hello")
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Controller ctrl;
    
    static{
    	ctrl = new Controller();
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String aux = request.getParameter("boia");
		
		if(aux==null){
			//0 - boias 1 - sensores 2 - medicoes
			aux = request.getParameter("funcao");
			int f = Integer.parseInt(aux);
			aux = request.getParameter("idboia");
			String bId = (aux==null)?null:(aux);
			aux = request.getParameter("idsensor");
			int sId = (aux==null)?-1:Integer.parseInt(aux);
			switch(f){
			case 0:
				out.println(ctrl.getBoias());
				break;
			case 1:
				out.println(ctrl.getSensoresBoia(new Sensor(0,bId,0)));
				break;
			case 2:
				out.println(ctrl.getMedicoes(new Medicao(sId,bId,0,0,0)));
			default:
			}
		} else {
			String id = (aux);
			aux = request.getParameter("sensor");
			int idSens = Integer.parseInt(aux);
			aux = request.getParameter("medicao");
			int valor = Integer.parseInt(aux);
			aux = request.getParameter("nivelAlarme");
			int nivel = Integer.parseInt(aux);
			
			Medicao med = new Medicao(idSens,id,valor,System.currentTimeMillis(),nivel);
			ctrl.cadastraMedicao(med);
		}
		
		out.println("Hello friend! "+aux);
		out.println(request.getMethod());
		out.println(Collections.list(request.getParameterNames()));
	}
	// 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
