package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.exceptions.ElementNotFoundException;
import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;
import it.prova.raccoltafilm.utility.UtilityForm;

@WebServlet("/ExecuteUpdateRegistaServlet")
public class ExecuteUpdateRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegistaService registaService;
	
    public ExecuteUpdateRegistaServlet() {
        super();
        registaService = MyServiceFactory.getRegistaServiceInstance();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idParam = request.getParameter("idRegista");
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String nickNameParam = request.getParameter("nickName");
		String dataDiNascitaParam = request.getParameter("dataDiNascita");
		String sessoParam = request.getParameter("sesso");
		
		if(idParam == null || !NumberUtils.isParsable(idParam) || Long.parseLong(idParam) < 1) {
			request.setAttribute("errorMessage", "Errore durante la ricerca...id inesistente o non valido.");
			response.sendRedirect("ExecuteListRegistaServlet?operationResult=NOT_FOUND");
			return;
		}
		
		Regista registaInstance = UtilityForm.createRegistaFromParams(nomeParam, cognomeParam, nickNameParam,
				dataDiNascitaParam, sessoParam);
		registaInstance.setId(Long.parseLong(idParam));

		if (!UtilityForm.validateRegistaBean(registaInstance)) {
			request.setAttribute("registadaaggiornare_attr", registaInstance);
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.getRequestDispatcher("/regista/update.jsp").forward(request, response);
			return;
		}

		try {
			registaService.aggiorna(registaInstance);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/regista/update.jsp").forward(request, response);
			return;
		}
		
		response.sendRedirect("ExecuteListRegistaServlet?operationResult=SUCCESS");
	}

}
