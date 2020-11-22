package dao;

import com.sun.xml.internal.ws.util.pipe.DumpTube;
import pojo.Page;
import pojo.RowBounds;
import pojo.Student;
import util.PageUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StuDao {
    private BaseDao baseDao;

    private final static int PAGE_SIZE = 100;

    private static StuDao instance;
    public static StuDao getInstance() {
        if (instance == null) {
            instance = new StuDao();
        }
        return instance;
    }

    private StuDao(){
        baseDao = BaseDao.getInstance();
    }

    public List<Student> getData(){
        List<Student> students = new ArrayList<>();

        final String sql = "SELECT * FROM stuInfo";
        PreparedStatement preparedStatement;
        ResultSet set;
        try {
            preparedStatement = baseDao.getConnection().prepareStatement(sql);
            set = preparedStatement.executeQuery();
            while (set.next()){
                Student student = this.getStudent(set);
                students.add(student);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return students;
    }

    public Page<Student> getData(int pageNum){
        Page<Student> page = new Page<>();

        final String sql = "SELECT * FROM stuInfo";

        RowBounds bounds = PageUtil.getRowBounds(pageNum,PAGE_SIZE);
        page.setPageNum(bounds.getPageNum());
        page.setPageSize(bounds.getPageSize());

        PreparedStatement preparedStatement;
        ResultSet set;
        try{
            String pageSql = PageUtil.page(sql,bounds);
            List<Student> students = new ArrayList<>();

            preparedStatement = baseDao.getConnection().prepareStatement(pageSql);
            set = preparedStatement.executeQuery();
            while (set.next()){
                Student student = this.getStudent(set);
                students.add(student);
            }
            page.setList(students);
            pageSql = PageUtil.count(sql);

            preparedStatement = baseDao.getConnection().prepareStatement(pageSql);
            set = preparedStatement.executeQuery();

            set.next();
            long total =  set.getLong(1);
            page.setTotal(total);

            int pages = PageUtil.getPages(total,PAGE_SIZE);
            page.setPages(pages);


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return page;
    }

    //插入学生数据
    public void insert(Student stu){
        final String sql = "INSERT INTO stuInfo (id,name,birthday,phone,class) values(?,?,?,?,?)";

        try{
            PreparedStatement preparedStatement = baseDao.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,stu.getStuId());
            preparedStatement.setString(2,stu.getStuName());
            preparedStatement.setString(3,stu.getStuBirthday());
            preparedStatement.setString(4,stu.getStuPhone());
            preparedStatement.setString(5,stu.getStuClass());

            preparedStatement.execute();


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    //获取学生实体
    private Student getStudent(ResultSet set) throws SQLException {
        Student student = new Student();

        student.setStuId(set.getInt("id"));
        student.setStuName(set.getString("name"));
        student.setStuClass(set.getString("class"));
        student.setStuBirthday(set.getString("birthday"));
        student.setStuPhone(set.getString("phone"));

        return student;

    }



}
