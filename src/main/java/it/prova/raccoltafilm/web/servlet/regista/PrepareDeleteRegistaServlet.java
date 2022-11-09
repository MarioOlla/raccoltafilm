package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.exceptions.ElementNotFoundException;
import it.prova.raccoltafilm.exceptions.RegistaConFilmAssociatiException;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;

@WebServlet("/PrepareDeleteRegistaServlet")
public class PrepareDeleteRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RegistaService registaService;  
    
    public PrepareDeleteRegistaServlet() {
        super();
        registaService = MyServiceFactory.getRegistaServiceInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idRegistapParam = request.getParameter("idRegista");
		
		if(idRegistapParam==null || !NumberUtils.isParsable(idRegistapParam) ) {
			request.setAttribute("errorMessage", "Attenzione, id in input mancante o non valido.");
			request.getRequestDispatcher("/regista/list.jsp").forward(request, response);
			return;
		}
		
		try {
			request.setAttribute("delete_regista_attr", registaService.caricaSingoloElementoConFilms(Long.parseLong(idRegistapParam)));
		}catch (Exception e) {
			request.setAttribute("errorMessage", "Attenzione, errore durante il caricamento.");
			request.getRequestDispatcher("/regista/list.jsp").forward(request, response);
			return;			
		}
		request.getRequestDispatcher("/regista/delete.jsp").forward(request, response);
	}

}
