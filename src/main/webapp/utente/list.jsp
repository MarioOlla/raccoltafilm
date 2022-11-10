<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Pagina dei Risultati</title>
	 </head>
	 
	<body class="d-flex flex-column h-100">
	 
		<!-- Fixed navbar -->
		<jsp:include page="../navbar.jsp"></jsp:include>
	 
	
		<!-- Begin page content -->
		<main class="flex-shrink-0">
		  <div class="container">
		  
		  		<div class="alert alert-success alert-dismissible fade show  ${successMessage==null?'d-none':'' }" role="alert">
				  ${successMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
				<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
				  ${errorMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
		  
		  
		  
		  		<div class='card'>
				    <div class='card-header'>
				        <h5>Lista dei risultati</h5> 
				    </div>
				    <div class='card-body'>
				    					    
				        <div class='table-responsive'>
				            <table class='table table-striped ' >
				                <thead>
				                    <tr>
			                         	<th>Username</th>
				                        <th>Nome</th>
				                        <th>Cognome</th>
				                        <th>Data di Creazione</th>
				                        <th>Azioni</th>
  
				                    </tr>
				                </thead>
				                <tbody>
				                	<c:forEach items="${utente_list_attr }" var="utenteItem">
										<tr>
											<td>${utenteItem.username }</td>
											<td>${utenteItem.nome }</td>
											<td>${utenteItem.cognome }</td>
											<td><fmt:formatDate type = "date" value = "${utenteItem.dateCreated }" /></td>
											
											 
											<td>
												<a class="btn  btn-sm btn-outline-secondary" href="user/ExecuteShowUtenteServlet?idUtente=${utenteItem.id }">Visualizza</a>
												<a class="btn  btn-sm btn-outline-primary ml-2 mr-2" href="PrepareUpdateFilmilm=${filmItem.id }">Edit</a>
												<a class="btn btn-outline-danger btn-sm" href="user/PrepareDeleteFilmSlm=${Item.id }">Delete</a>
											</td>
											
										</tr>
									</c:forEach>
				                </tbody>
				            </table>
				        </div>
				   		<a class="btn btn-primary " href="../home">To homepage</a>
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