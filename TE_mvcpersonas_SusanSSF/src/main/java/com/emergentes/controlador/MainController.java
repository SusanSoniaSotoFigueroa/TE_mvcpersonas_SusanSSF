
package com.emergentes.controlador;

import com.emergentes.modelo.Persona_SSSF;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession ses = request.getSession();
        
        if (ses.getAttribute("listaper") == null){
            ArrayList<Persona_SSSF> listaux = new ArrayList<Persona_SSSF>();
            ses.setAttribute("listaper", listaux);
        }
        
        ArrayList<Persona_SSSF> lista = (ArrayList<Persona_SSSF>)ses.getAttribute("listaper");
        
        
        String op = request.getParameter("op");
        String opcion = (op != null) ? op : "view";
        
        Persona_SSSF obj1 = new Persona_SSSF();
        int id, pos;
        
        switch(opcion){
            case "nuevo":
                request.setAttribute("miPersona", obj1);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "editar":
                id = Integer.parseInt(request.getParameter("id"));
                pos = buscarIndice(request,id);
                obj1 = lista.get(pos);
                request.setAttribute("miPersona", obj1);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "eliminar":
                id = Integer.parseInt(request.getParameter("id"));
                pos = buscarIndice(request,id);
                lista.remove(pos);
                ses.setAttribute("listaper", lista);
                response.sendRedirect("index.jsp");
                break;
            case "view":
                response.sendRedirect("index.jsp");
        }
    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses = request.getSession();
        ArrayList<Persona_SSSF> lista = (ArrayList<Persona_SSSF>)ses.getAttribute("listaper");
        
        Persona_SSSF obj1 = new Persona_SSSF();
        
        obj1.setId(Integer.parseInt(request.getParameter("id")));
        obj1.setNombres(request.getParameter("nombres"));
        obj1.setApellidos(request.getParameter("apellidos"));
        obj1.setEdad(Integer.parseInt(request.getParameter("edad")));
        
        int idt = obj1.getId();
        
        if (idt == 0){
            //nuevo
            //actualizar el ultimo id
            int ultID;
            ultID = ultimoId(request);
            obj1.setId(ultID);
            lista.add(obj1);
        }
        else{
            //Edicion
            lista.set(buscarIndice(request,idt), obj1);
        }
        ses.setAttribute("listaper", lista);
        response.sendRedirect("index.jsp");
        
    }

    private int buscarIndice(HttpServletRequest request, int id){
        HttpSession ses = request.getSession();
        ArrayList<Persona_SSSF> lista = (ArrayList<Persona_SSSF>)ses.getAttribute("listaper");
        
        int i = 0;
        
        if (lista.size() > 0){
            while (i< lista.size()){
                if (lista.get(i).getId() == id){
                    break;
                }
                else{
                    i++;
                }
            }
        }
        return i;
    }
    private int ultimoId(HttpServletRequest request){
      HttpSession ses = request.getSession();
      ArrayList<Persona_SSSF> lista = (ArrayList<Persona_SSSF>)ses.getAttribute("listaper");
      
      int idaux = 0;
      for (Persona_SSSF item: lista){
          idaux = item.getId();
    }
      return idaux + 1;
    }
}
