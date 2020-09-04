package com.miku;

import com.miku.utils.SplitePage;
import com.miku.utils.TestTool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Utopiamiku
 * @date 2020/8/12 14:25
 * @File Dmeo.py
 */
public class Dmeo {
    public static void main(String[] args) {
//        System.out.println(TestTool.INSTALL.getMD5String("admin"));
//        SplitePage sp = new SplitePage();
//        sp.setCurrentPage(Integer.valueOf("1"));
//        System.out.println(sp.getPageRows()+"-"+sp.getCurrentPage());
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String no = "jh"+sdf.format(d)+"01";
//        if(no.contains("20200817")){
//            String no1 = no.substring(10,12);
//            System.out.println(no1);
//        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //结束时间
        Date end = c.getTime();
        c.add(Calendar.MONTH, -1);
        //开始时间
        Date start = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println( new Date(start.getTime()-2*24*60*60*1000));
    }
}
