package com.example.thilinab.tsprep;

import com.example.thilinab.tsprep.sqldb.SqlUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thilinab on 10/31/2015.
 */
public class Details {
    private static Details ourInstance = new Details();

    public static Details getInstance() {
        return ourInstance;
    }

    private Details() {
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getFromHour() {
        return FromHour;
    }

    public void setFromHour(int fromHour) {
        FromHour = fromHour;
    }

    public int getFromMinute() {
        return FromMinute;
    }

    public void setFromMinute(int fromMinute) {
        FromMinute = fromMinute;
    }

    public int getToHour() {
        return ToHour;
    }

    public void setToHour(int toHour) {
        ToHour = toHour;
    }

    public int getToMinute() {
        return ToMinute;
    }

    public void setToMinute(int toMinute) {
        ToMinute = toMinute;
    }

    int Year;
    int Month;
    int Day;
    int FromHour;
    int FromMinute;
    int ToHour;
    int ToMinute;
    String ClassName;
    String Subject;

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    SqlUtility sqlUtility;

    public SqlUtility getSqlUtility() {
        return sqlUtility;
    }

    public void setSqlUtility(SqlUtility sqlUtility) {
        this.sqlUtility = sqlUtility;
    }

    public String getFilterMonth() {
        return FilterMonth;
    }

    public void setFilterMonth(String filterMonth) {
        FilterMonth = filterMonth;
    }

    String FilterMonth;
}
