package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;

@WebServlet("/ExecuteVisualizzaRegistaServlet")
public class ExecuteVisualizzaRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RegistaService registaService;
	
    public ExecuteVisualizzaRegistaServlet() {
        super();
        this.registaService = MyServiceFactory.getRegistaServiceInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idRegistaParam = request.getParameter("idRegista");
		
		if (!NumberUtils.isCreatable(idRegistaParam)) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}
		
		try {
			Regista registaInstance = registaService.caricaSingoloElemento(Long.parseLong(idRegistaParam));
		
			if(registaInstance == null) {
				request.setAttribute("errorMessage", "Attenzione, elemento mancante.");
				request.getRequestDispatcher("ExecuteListRegistaServlet?operationResult=NOT_FOUND").forward(request,
						response);
				return;
			}
			
			request.setAttribute("show_regista_attr", registaInstance);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}
		request.getRequestDispatcher("regista/show.jsp").forward(request, response);
	}

}
