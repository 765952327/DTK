package cn.welsione.dtk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) throws ParseException {
        Date date = new Date(1451577600000L);
//        Date startDate = dateFormat.parse("2016-01-01 00:00:00");
        System.out.printf(dateFormat.format(date));
    }
}
