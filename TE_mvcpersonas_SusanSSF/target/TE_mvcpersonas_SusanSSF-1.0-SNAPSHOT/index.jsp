<%@page import="java.util.ArrayList"%>
<%@page import="com.emergentes.modelo.Persona_SSSF"%>
<%
    ArrayList<Persona_SSSF> lista = (ArrayList<Persona_SSSF>) session.getAttribute("listaper");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de Personas</h1>
        <a href="MainController?op=nuevo">Nuevo Registro</a>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>Nombres</th>
                <th>Apellidos</th>
                <th>Edad</th>
                <th></th>
                <th></th>
            </tr>
            <%
                if (lista != null){
                    for (Persona_SSSF item : lista){
            %>
            <tr>
                <th><%= item.getId() %></th>
                <th><%= item.getNombres() %></th>
                <th><%= item.getApellidos() %></th>
                <th><%= item.getEdad() %></th>
                <th><a href="MainController?op=editar&id=<%= item.getId() %>">Modificar</a></th>
                <th><a href="MainControl?op=eliminar&id=<%= item.getId() %>"
                       onclick="return confirm('Esta seguro de eliminar el registro ?')">Eliminar</a></th>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </body>
</html>
