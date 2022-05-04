package com.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Data
public class Book implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer bookId;        //图书编号
    private String bookName;       //图书名称
    private String bookIsbn;       //图书标准ISBN编号
    private String bookPress;      //图书出版社
    private String bookAuthor;     //图书作者
    private Integer bookPagination;//图书页数
    private Double bookPrice;      //图书价格
    @TableField("book_uploadtime")
    private LocalDate bookUploadTime; //图书上架时间
    private String bookStatus;     //图书状态
    private String bookBorrower;   //图书借阅人
    @TableField("book_borrowtime")
    private LocalDate bookBorrowTime; //图书借阅时间
    @TableField("book_returntime")
    private LocalDate bookReturnTime; //图书预计归还时间


}
