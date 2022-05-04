package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.BaseContext;
import com.common.Code;
import com.common.R;
import com.dao.BookDao;
import com.domain.Book;
import com.domain.IndexShowData;
import com.domain.Record;
import com.domain.User;
import com.service.BookService;
import com.service.RecordService;
import com.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;


@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements BookService {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RecordService recordService;

    @Override
    public R page(int currentPage, int pageSize,Boolean type, Book book) {
        // 1.分页构造器
        Page<Book> pageInfo = new Page<Book>(currentPage, pageSize);
        // 2.查询条件
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(book.getBookName() != null, Book::getBookName, book.getBookName());
        queryWrapper.like(book.getBookAuthor() != null, Book::getBookAuthor, book.getBookAuthor());
        queryWrapper.like(book.getBookPress() != null, Book::getBookPress, book.getBookPress());
        //已借阅
        queryWrapper.ne(type,Book::getBookStatus,"0");
        queryWrapper.ne(type,Book::getBookStatus,"3");
        //排序条件
        queryWrapper.orderByDesc(Book::getBookUploadTime);
        queryWrapper.orderByAsc(Book::getBookId);
        //判断是否是管理员,如果是管理员则不限制
        String redisUserKey = BaseContext.getCurrentId();
        User user = redisUtils.getObject(redisUserKey, User.class);
        String userRole = user.getUserRole();
        String userName = user.getUserName();
        queryWrapper.eq((!userRole.equals("ADMIN") && type),Book::getBookBorrower,userName);
        // 3.查询
        bookDao.selectPage(pageInfo, queryWrapper);
        // 4.返回
        return new R(Code.SUCCESS,pageInfo);
    }

    @Override
    public R addBook(Book book) {
        // 1. 设置图书上架时间
        book.setBookUploadTime(LocalDate.now());
        // 2. 添加图书
        if(bookDao.insert(book)>0){
            return new R(Code.SUCCESS,"添加成功");
        }
        return new R(Code.ERROR,"添加失败,请稍后重试");
    }

    @Override
    public R updateBook(Book book) {
        if (bookDao.updateById(book)>0){
            return new R(Code.SUCCESS,"修改成功");
        }
        return new R(Code.ERROR,"修改失败,请稍后重试");
    }

    @Override
    public R borrowBook(Book book) {
        // 1. 设置图书借阅时间
        book.setBookBorrowTime(LocalDate.now());
        //2. 设置图书借阅人
        //2.1 获取redis userKey
        String redisUserKey = BaseContext.getCurrentId();
        //2.2 获取redis中的用户名
        String userName = redisUtils.getObject(redisUserKey, User.class).getUserName();
        book.setBookBorrower(userName);
        //3. 设置图书状态
        book.setBookStatus("1");
        //4. 保存
        if (bookDao.updateById(book)>0){
            return new R(Code.SUCCESS,"借阅成功，请在规定时间内归还");
        }
        return new R(Code.ERROR,"借阅失败,请稍后重试");
    }

    @Override
    public R returnBook(int bookId) {
        if (bookDao.updateStatusById(bookId)>0){
            return new R(Code.SUCCESS,"归还申请提交成功，请交还图书并确认");
        }
        return new R(Code.ERROR,"归还失败,请稍后重试");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = false) //事务管理
    public R confirmReturn(Integer id) {
        //1.查询图书信息
        Book book = bookDao.selectById(id);

        //2. 删除借阅信息，更改借阅状态
        bookDao.updateBookBorrowInfo(id);

        //3. 插入借阅记录
        Record record = new Record();
        record.setBookName(book.getBookName());
        record.setBookIsbn(book.getBookIsbn());
        record.setBorrower(book.getBookBorrower());
        record.setBorrowTime(book.getBookBorrowTime());
        record.setRemandTime(LocalDate.now());
        recordService.save(record);

        return new R(Code.SUCCESS,"归还成功");
    }


}
