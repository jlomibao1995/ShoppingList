
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jean
 */
public class ShoppingListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String action = (String) request.getParameter("action");
        if (action != null){
            if (action.equals("logout")) {
            session.invalidate();
            session = request.getSession();
        }
        }
        
        String username = (String) session.getAttribute("username");
        
        if (username != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
        }
        else {
          
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = (String) request.getParameter("action");
        ArrayList<String> shoppingList = (ArrayList) session.getAttribute("shoppingList");
        
        if (shoppingList == null) {
            shoppingList = new ArrayList<>();
        }
        
        if (action.equals("register")) {
            String username = request.getParameter("username");
            session.setAttribute("username", username);
        }
        if (action.equals("add")) {
            String item = request.getParameter("item");
            if (!item.equals("")) {
                shoppingList.add(item);
            }
        }
        if (action.equals("delete")) {
            String deleteItem = request.getParameter("items");
            if (!deleteItem.equals("")) {
                shoppingList.remove(deleteItem);    
            }
        }
        session.setAttribute("shoppingList", shoppingList);
        getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
    }

}
