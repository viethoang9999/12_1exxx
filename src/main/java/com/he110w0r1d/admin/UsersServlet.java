package com.he110w0r1d.admin;

import com.he110w0r1d.business.User;
import com.he110w0r1d.data.UserDB;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

public class UsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        String url = "/index.jsp";
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "display_users";  // default action
        }
        
        // perform action and set URL to appropriate page
        if (action.equals("display_users")) {            
            // get list of users
            ArrayList<User> users = UserDB.selectUsers();            

            // set as a request attribute
            request.setAttribute("users",users);
            // forward to index.jsp
            url="/index.jsp";
        } 
        else if (action.equals("display_user")) {
            // get user for specified email
            String email = request.getParameter("email");
            if(email!=null){
                User user = UserDB.selectUser(email);
                // set as request attribute
                request.setAttribute("user",user);
                url="/user.jsp";
            }else {
                url="/index.jsp";
            }

            // forward to user.jsp
        }
        else if (action.equals("update_user")) {
            // update user in database
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            UserDB.update(new User(firstName,lastName,email));
            // get current user list and set as request attribute
            ArrayList<User> users = UserDB.selectUsers();
            request.setAttribute("users",users);
            // forward to index.jsp
            url="/index.jsp";
        }
        else if (action.equals("delete_user")) {
            // get the user for the specified email
            // delete the user            
            // get current list of users
            // set as request attribute
            // forward to index.jsp
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }    
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }    
}