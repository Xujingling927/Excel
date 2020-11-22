package servelet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddExcelServlet extends HttpServlet {
    private String jspFilePath;

    @Override
    public void init(){
        jspFilePath = getServletContext().getInitParameter("jsp");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(jspFilePath+"/import.jsp").forward(req,resp);
    }
}
