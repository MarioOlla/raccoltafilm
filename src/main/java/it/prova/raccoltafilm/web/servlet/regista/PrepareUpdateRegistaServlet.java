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
import it.prova.raccoltafilm.web.listener.LocalEntityManagerFactoryListener;

@WebServlet("/PrepareUpdateRegistaServlet")
public class PrepareUpdateRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegistaService registaService;
	
    public PrepareUpdateRegistaServlet() {
        super();
        registaService = MyServiceFactory.getRegistaServiceInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idRegistaParam = request.getParameter("idRegista");
		
		if(idRegistaParam==null || !NumberUtils.isParsable(idRegistaParam) || Long.parseLong(idRegistaParam) < 1) {
			request.setAttribute("errorMessage", "Errore durante la ricerca, id invalido.");
			request.getRequestDispatcher("regista/list.jsp");
			return;
		}
		
		try {
			Regista daAggiornare = registaService.caricaSingoloElemento(Long.parseLong(idRegistaParam));
			request.setAttribute("registadaaggiornare_attr", daAggiornare);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Qualcosa è andato storto, non è stato caricato il regista da aggiornare.");
			request.getRequestDispatcher("regista/list.jsp");
			return;
		}
		
		request.getRequestDispatcher("regista/update.jsp").forward(request, response);
	}

}
