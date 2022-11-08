package it.prova.raccoltafilm.web.servlet.film;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.dao.FilmDAO;
import it.prova.raccoltafilm.model.Film;
import it.prova.raccoltafilm.service.FilmService;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;

@WebServlet("/PrepareUpdateFilmServlet")
public class PrepareUpdateFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private FilmService filmService;
    private RegistaService registaService;
    
    public PrepareUpdateFilmServlet() {
    	super();
    	filmService = MyServiceFactory.getFilmServiceInstance();
    	registaService = MyServiceFactory.getRegistaServiceInstance();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idFilmParam = request.getParameter("idFilm");
		
		if(idFilmParam == null || !NumberUtils.isParsable(idFilmParam)|| Long.parseLong(idFilmParam)< 1) {
			request.setAttribute("errorMessage", "Errore durante la ricerca, id invalido.");
			request.getRequestDispatcher("film/list.jsp");
			return;
		}
		
		try {
			Film daAggiornare = filmService.caricaSingoloElementoEager(Long.parseLong(idFilmParam));
			request.setAttribute("registi_list_attribute", registaService.listAllElements() );
			request.setAttribute("filmdaaggiornare", daAggiornare);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Qualcosa è andato storto, non è stato caricato il film da aggiornare.");
		}
		request.getRequestDispatcher("film/update.jsp").forward(request, response);
	}

}
