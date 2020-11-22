package util;

import pojo.RowBounds;

public class PageUtil {
    //对SQL语句进行COUNT查询处理
    public static String count(String sql){
        int fromIndex = sql.toUpperCase().indexOf("FROM");

        String replaceStr = sql.substring(7,fromIndex - 1);
        return sql.replace(replaceStr,"COUNT(0)");
    }

    //对SQL语句进行分页查询处理
    public static String page(String sql, RowBounds rowBounds){
        int pageNum = rowBounds.getPageNum();
        int pageSize = rowBounds.getPageSize();

        int offset = (pageNum - 1) * pageSize;
        return sql + " LIMIT " + offset + "," + pageSize;
    }

    //通过总数和页面大小获取总页数
    public static int getPages(long total,int pageSize){
        return (int) Math.ceil((double) total / pageSize);
    }

    public static RowBounds getRowBounds(int pageNum, int pageSize){
        RowBounds rowBounds = new RowBounds();

        if(pageNum < 0){
            pageNum = 1;
        }
        else if(pageNum > pageSize){
            pageNum = pageSize;
        }

        rowBounds.setPageNum(pageNum);
        rowBounds.setPageSize(pageSize);

        return rowBounds;
    }
}
