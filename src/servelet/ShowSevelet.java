package servelet;

import dao.StuDao;
import pojo.Page;
import pojo.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowSevelet extends HttpServlet {
    private String jspFilePath;
    private StuDao stuDao;

    @Override
    public void init(){
        stuDao = StuDao.getInstance();
        jspFilePath = getServletContext().getInitParameter("jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumS = request.getParameter("pageNum");

        int pageNum;
        if(pageNumS == null){
            pageNum = 1;
        }
        else {
            pageNum = Integer.parseInt(pageNumS);
        }

        Page<Student> page = stuDao.getData(pageNum);
        request.setAttribute("page",page);
//        request.getRequestDispatcher(jspFilePath + "/list.jsp")
//                .forward(request,response);
        request.getRequestDispatcher(jspFilePath + "/index.jsp").forward(request,response);
    }


}
