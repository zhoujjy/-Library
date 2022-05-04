package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;


@Mapper
public interface BookDao extends BaseMapper<Book> {

    @Update("update book set book_status = 2 where book_id = #{id}")
    int updateStatusById(Integer id);

    @Update("update book set book_status = 0 , book_borrowtime = null , book_returntime = null, book_borrower = null where book_id = #{id}")
    int updateBookBorrowInfo(Integer id);

    @Select("SELECT COUNT(*) FROM book WHERE book_borrowtime=#{date}")
    int selectBorrowCountInNow(LocalDate date);

    //已借阅书籍
    @Select("SELECT COUNT(*) FROM book WHERE book_status = 1 or book_status=2")
    int selectBorrowedBookCount();

    //可借阅书籍
    @Select("SELECT COUNT(*) FROM book WHERE book_status = 0")
    int selectCanBorrowBookCount();

    @Select("SELECT COUNT(*) FROM book WHERE book_status = 2")
    Integer selectConfirmNum();

    @Select("SELECT COUNT(*) FROM book WHERE (book_status = 1 or book_status=2) and book_borrower=#{username}")
    Integer selectBorrowNum(String username);
}
