package servelet;

import dao.StuDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pojo.Student;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExportServlet extends HttpServlet {
    private String jspFilePath;
    private StuDao stuDao;

    @Override
    public void init() {
        stuDao = StuDao.getInstance();
        jspFilePath = getServletContext().getInitParameter("jsp");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Student> students = stuDao.getData();
        XSSFWorkbook workbook = new XSSFWorkbook();//生成工作表
        XSSFSheet sheet = workbook.createSheet("Student Info"); //生成表
        int rowNum = 0,cellNum = 0;
        Row row = sheet.createRow(rowNum++);
        Cell cell;
        row.createCell(0).setCellValue("学号"); //写入表头信息
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("出生日期");
        row.createCell(3).setCellValue("班级");
        row.createCell(4).setCellValue("电话");

        for (Student student:students){ //依次写入学生信息
            row = sheet.createRow(rowNum++);
            int col = 0;
            row.createCell(col++).setCellValue(student.getStuId()); //col++每次写入一列，列数加1
            row.createCell(col++).setCellValue(student.getStuName());
            row.createCell(col++).setCellValue(student.getStuBirthday());
            row.createCell(col++).setCellValue(student.getStuClass());
            row.createCell(col++).setCellValue(student.getStuPhone());
        }

        String fileName = "Export";  //导出文件名
        OutputStream outputStream = null;
        try{
            outputStream = response.getOutputStream();
            response.reset();
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
        }


    }
}
