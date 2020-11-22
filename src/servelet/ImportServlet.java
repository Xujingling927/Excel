package servelet;

import dao.StuDao;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pojo.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public class ImportServlet extends HttpServlet {
    private String excelFilePath;
    StuDao stuDao = StuDao.getInstance();
    @Override
    public void init(){

        String jspFilePath = getServletContext().getInitParameter("jsp");
        excelFilePath = getServletContext().getInitParameter("files");
        System.out.println(jspFilePath);
    }

    private static String getFileName(String header){
        String[] tempArr1 = header.split(";");
        String[] tempArr2 = tempArr1[2].split("=");
        return tempArr2[1].substring(tempArr2[1].lastIndexOf("\\")+1).replaceAll("\"", "");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {


        final String savePath = req.getServletContext().getRealPath(excelFilePath);

        List<Student> students = new ArrayList<>();

        String filePath = "";
//
//        //步骤一    构造工厂
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//
//        //步骤二    获得解析器
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        //步骤三    对请求体内容进行解析
//        try {
//
//            fileName = req.getParameter("excel");
//            //解决老版本浏览器文件路径问题
//            String filepath = savePath+File.separator+fileName;
//            //步骤四    遍历集合
//
//            FileInputStream in = new FileInputStream(filepath);//得到的是文件内容
//            System.out.println("文件上传项：" + fileName);
//            System.out.println(filepath);
//            //将内容写入文件
//            File file = new File(savePath + File.separator + fileName);
//            FileOutputStream out = new FileOutputStream(file);
//            int len;
//            while((len = in.read()) != -1){
//                out.write(len);
//            }
//            in.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        List<Part> parts = (List<Part>) req.getParts();
        for (Part file :parts){
            if(file.getName().equals("excel")){
                String header = file.getHeader("content-disposition");
                String fileName =  System.currentTimeMillis() +  "_" + getFileName(header);
                filePath = savePath + File.separator +fileName;
                file.write(filePath);
            }
        }

        Workbook workbook = null;
        try {
            InputStream input = new FileInputStream(filePath);
            //读取不同格式excel文件
            if ("xls".equals(getPostFix(filePath))){
                System.out.println("it is xls book");
                workbook = new HSSFWorkbook(input);
            }else if("xlsx".equals(getPostFix(filePath))){
                System.out.println("it is xlsx book");
                workbook = new XSSFWorkbook(input);
            }
            else System.out.println("it is empty book");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < workbook.getNumberOfSheets(); i++){
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet == null){
                continue;
            }
            for (int j = 1; j <= sheet.getLastRowNum();j++){
                Row row = sheet.getRow(j);
                if (row != null){
                    Student stu = new Student();
                    stu.setStuId(Integer.parseInt(getValue(row.getCell(0))));
                    stu.setStuName(getValue(row.getCell(1)));
                    stu.setStuBirthday(getValue(row.getCell(2)));
                    stu.setStuClass(getValue(row.getCell(3)));
                    stu.setStuPhone(getValue(row.getCell(4)));

                    students.add(stu);
                    stuDao.insert(stu);
                }
            }

        }

        resp.sendRedirect(req.getContextPath() + "/show");


    }

    //转换Cell单元格内的值
    private String  getValue(Cell cell) {
       if (null == cell) 
           return null;               
       if (cell.getCellType() == BOOLEAN){
           return String.valueOf(cell.getBooleanCellValue());
       }
       else if (cell.getCellType() == NUMERIC) {
           DecimalFormat format = new DecimalFormat("#.##");           
           format.format(cell.getNumericCellValue());            
           return format.format(cell.getNumericCellValue());       
       }       
       else       
           return cell.getStringCellValue();
    }

    //获取文件后缀
    private String getPostFix(String path){
        if (path == null || "".equals(path.trim())){
            return "";
        }

        if (path.contains(".") && path.lastIndexOf(".") != path.length()-1){
            return path.substring(path.lastIndexOf(".") + 1,path.length());
        }

        return "";
    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
}

