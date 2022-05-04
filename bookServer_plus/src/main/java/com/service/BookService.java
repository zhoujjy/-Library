package com.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.common.R;
import com.domain.Book;

public interface BookService extends IService<Book> {

    R page(int currentPage, int pageSize,Boolean type, Book book);

    R addBook(Book book);

    R updateBook(Book book);

    R borrowBook(Book book);

    R returnBook(int bookId);

    R confirmReturn(Integer id);

}
