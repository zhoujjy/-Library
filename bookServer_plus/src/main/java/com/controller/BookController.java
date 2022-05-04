package com.controller;

import com.common.R;
import com.domain.Book;
import com.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 书籍分页查询
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @param type  模块类型，true为当前借阅
     * @param book
     * @return
     */
    @PostMapping("/page/{currentPage}/{pageSize}/{type}")
    public R page(@PathVariable int currentPage,@PathVariable int pageSize,@PathVariable Boolean type,@RequestBody Book book){
        return bookService.page(currentPage, pageSize,type, book);
    }

    /**
     * 添加书籍
     * @param book
     * @return
     */
    @PostMapping
    public R addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    /**
     * 修改书籍
     * @param book
     * @return
     */
    @PutMapping
    public R updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    /**
     * 借阅书籍
     * @param book
     * @return
     */
    @PutMapping("/borrow")
    public R borrowBook(@RequestBody Book book){
        return bookService.borrowBook(book);
    }

    /**
     * 归还书籍
     * @param bookId
     * @return
     */
    @PutMapping("/{bookId}")
    public R returnBook(@PathVariable int bookId){
        return bookService.returnBook(bookId);
    }

    /**
     * 确认归还书籍
     * @param bookId
     * @return
     */
    @PutMapping("/confirmReturn/{bookId}")
    public R confirmReturn(@PathVariable Integer bookId){
        return bookService.confirmReturn(bookId);
    }
}
