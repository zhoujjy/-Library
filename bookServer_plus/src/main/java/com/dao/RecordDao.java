package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.ArrayList;

@Mapper
public interface RecordDao extends BaseMapper<Record> {

    @Select("SELECT COUNT(*) FROM record GROUP BY record_borrowtime ORDER BY record_borrowtime")
    ArrayList<Integer> selectNumByGroup();

    @Select("select count(*) from record  group by year(record_borrowtime)")
    ArrayList<Integer> selectNumInYear();

    @Select("select record_bookname from record WHERE record_borrower=#{userId}")
    ArrayList<String> selectReturnBookByName(String name);

    @Select("SELECT COUNT(*) FROM record WHERE record_borrowtime=#{date}")
    int selectBorrowCountInNow(LocalDate date);
}
