package it.prova.raccoltafilm.web.servlet.utente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.model.Utente;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.UtenteService;

@WebServlet("/user/ExecuteShowUtenteServlet")
public class ExecuteShowUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteService utenteService;

    public ExecuteShowUtenteServlet() {
        super();
        utenteService = MyServiceFactory.getUtenteServiceInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idUtenteParam = request.getParameter("idUtente");
		
		if(!NumberUtils.isCreatable(idUtenteParam)) {
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}
		
		try {
			Utente utenteInstance = utenteService.caricaSingoloElemento(Long.parseLong(idUtenteParam));
			
			if(utenteInstance == null) {
				request.setAttribute("errorMessage", "Attenzione, elemento non trovato.");
				request.getRequestDispatcher("user/ExecuteListUtenteServlet?operationResult=NOT_FOUND").forward(request,
						response);
				return;
			}
			request.setAttribute("show_utente_attr", utenteInstance);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}
		request.getRequestDispatcher("./utente/show.jsp").forward(request, response);
	}

}
