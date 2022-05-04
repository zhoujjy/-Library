package com.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Record implements Serializable {
    @TableId(value = "record_id",type = IdType.AUTO)
    private Integer id;        //图书借阅id
    @TableField("record_bookname")
    private String bookName;   //借阅的图书名称
    @TableField("record_bookisbn")
    private String bookIsbn;   //借阅的图书的ISBN编号
    @TableField("record_borrower")
    private String borrower;   //图书借阅人
    @TableField("record_borrowtime")
    private LocalDate borrowTime; //图书借阅时间
    @TableField("record_remandtime")
    private LocalDate remandTime; //图书归还时间
}
