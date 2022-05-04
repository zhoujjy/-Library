package com.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IndexShowData {
    private ArrayList<Integer> borrowNumInDay; //每天的借书数量，折线图
    private Integer borrowNumInNow; // //当天借阅数量
    private Integer allBorrowNum; //借书总数量

    private Integer confirmNum;//待确认数量
    private Integer returnNum;//待归还数量
    private List<String> returnBook;//归还图书

    private Integer borrowedBookNum;//已经借阅的书籍数量
    private Integer canBorrowBookNum; //剩余可借书籍数量

    private ArrayList<Integer> borrowNumInYear; //每年的借书数量



}
