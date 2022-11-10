<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>

	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	
	   <title>Visualizza Elemento</title>
	   
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Visualizza Utente</h5>
					    </div>
					    
					
					    <div class='card-body'>
					    						    	
					    	<!-- info Regista -->
							  <div class="card card-body">
							  	<dl class="row">
								  <dt class="col-sm-3 text-right">Username:</dt>
								  <dd class="col-sm-9">${show_utente_attr.username}</dd>
							   	</dl>
							   	<dl class="row">
								  <dt class="col-sm-3 text-right">Nome:</dt>
								  <dd class="col-sm-9">${show_utente_attr.nome}</dd>
							   	</dl>
							   	<dl class="row">
								  <dt class="col-sm-3 text-right">Cognome:</dt>
								  <dd class="col-sm-9">${show_utente_attr.cognome}</dd>
							   	</dl>
							   	<dl class="row">
								  <dt class="col-sm-3 text-right">Data Creazione:</dt>
								  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_utente_attr.dateCreated }" /></dd>
							   	</dl>
							    <dl class="row">
								  <dt class="col-sm-3 text-right">Stato:</dt>
								  <dd class="col-sm-9">${show_utente_attr.stato}</dd>
							   	</dl>
							<!-- end info Regista -->
							</div>
					    	
					    <!-- end card body -->
					    </div>
					    
					    <div class='card-footer'>
					         <a href="PrepareSearchUtenteServlet" class='btn btn-outline-secondary' style='width:80px'>
					            <i class='fa fa-chevron-left'></i> Back
					        </a>
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