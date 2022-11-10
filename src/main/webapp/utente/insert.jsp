<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   
	   <title>Inserisci Nuovo Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					<div class="alert alert-danger alert-dismissible fade show d-none" role="alert">
					  Esempio di operazione fallita!
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					<div class="alert alert-info alert-dismissible fade show d-none" role="alert">
					  Aggiungere d-none nelle class per non far apparire
					   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Inserisci nuovo elemento</h5> 
				    </div>
				    <div class='card-body'>
		
							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
		
							<form method="post" action="ExecuteInsertUtenteServlet" class="row g-3" novalidate="novalidate">
							
								<div class="col-md-6">
									<label for="username" class="form-label">Username <span class="text-danger">*</span></label>
									<input type="text" class="form-control" name="username" id="username" placeholder="Inserire l'username" value="${utente_attr.username }" required>
								</div>
								
								<div class="col-md-6">
									<label for="nome" class="form-label">Nome <span class="text-danger">*</span></label>
									<input type="text" name="nome" id="nome" class="form-control" placeholder="Inserire il nome" value="${utente_attr.nome }" required>
								</div>
								
								<div class="col-md-6">
									<label for="cognome" class="form-label">Cognome <span class="text-danger">*</span></label>
									<input type="text" name="cognome" id="cognome" class="form-control" placeholder="Inserire il cognome" value="${utente_attr.cognome }" required>
								</div>
								
								<div class="col-md-6">
									<label for="password" class="form-label">Password <span class="text-danger">*</span></label>
									<input type="password" name="password" id="password" class="form-control" placeholder="Inserire la password" value="" required>
								</div>
								
								<div class="col-md-6">
									<label for="confermaPassword" class="form-label">Conferma Password <span class="text-danger">*</span></label>
									<input type="password" name="confermaPassword" id="confermaPassword" class="form-control" placeholder="Conferma la password" value="" required>
								</div>
								
								<div class="col-md-3">
									
									<label for="ruoli" class="form-label">Ruoli Utente<span class="text-danger">*</span></label>
									<c:forEach items="${tutti_ruoli_attr}" var="ruoloItem">
										<div class="form-check">
  											<input class="form-check-input" type="checkbox" value="${ruoloItem.id}" id="flexCheckDefault" name="ruoli" <c:if test="${utente_attr.ruoli.contains(ruoloItem.id)}">checked="checked"</c:if>>
  											<label class="form-check-label" for="flexCheckDefault">${ruoloItem.descrizione}</label>
										</div>
									</c:forEach>
								</div>
								
								
								
								
							<div class="col-12">
								<a class="btn btn-primary " href="../home">To homepage</a>
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
							</div>
		
						</form>
  
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>