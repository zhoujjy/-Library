package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.BaseContext;
import com.common.Code;
import com.common.R;
import com.dao.BookDao;
import com.dao.RecordDao;
import com.domain.Book;
import com.domain.IndexShowData;
import com.domain.Record;
import com.domain.User;
import com.service.RecordService;
import com.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordDao, Record> implements RecordService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private BookDao bookDao;

    @Override
    public R page(int currentPage, int pageSize, Record record) {
        // 1.分页构造器
        Page<Record> pageInfo = new Page<Record>(currentPage, pageSize);
        // 2.查询条件
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(record.getBookName() != null, Record::getBookName, record.getBookName());
        queryWrapper.like(record.getBorrower() != null, Record::getBorrower, record.getBorrower());
        //排序条件
        queryWrapper.orderByDesc(Record::getRemandTime);
        queryWrapper.orderByAsc(Record::getId);
        //判断是否是管理员,如果是管理员则不限制
        String redisUserKey = BaseContext.getCurrentId();
        User user = redisUtils.getObject(redisUserKey, User.class);
        String userRole = user.getUserRole();
        String userName = user.getUserName();
        queryWrapper.eq(!userRole.equals("ADMIN"),Record::getBorrower,userName);
        // 3.查询
        recordDao.selectPage(pageInfo, queryWrapper);
        // 4.返回
        return new R(Code.SUCCESS,pageInfo);
    }

    @Override
    public R indexShowData() {
        IndexShowData indexShowData = new IndexShowData();
        //图书总借阅数量
        int recordCount = recordDao.selectCount(null);
        indexShowData.setAllBorrowNum(recordCount); //图书借阅总量

        ArrayList<Integer> borrowNumList = recordDao.selectNumByGroup();
        indexShowData.setBorrowNumInDay(borrowNumList); //折线图数据

        indexShowData.setConfirmNum(bookDao.selectConfirmNum()); //确认数量

        String redisUserKey = BaseContext.getCurrentId();
        User user = redisUtils.getObject(redisUserKey, User.class);
        indexShowData.setReturnNum(bookDao.selectBorrowNum(user.getUserName())); //归还数量

        ArrayList<String> list= recordDao.selectReturnBookByName(user.getUserName());
        int i = list.size();
        if(i>3){
            List list2 =  list.subList(i-3,i);
            indexShowData.setReturnBook(list2); //归还图书
        }else{
            indexShowData.setReturnBook(list); //归还图书
        }




        indexShowData.setBorrowNumInNow(bookDao.selectBorrowCountInNow(LocalDate.now())+
                recordDao.selectBorrowCountInNow(LocalDate.now())); //当天借阅数量
        indexShowData.setBorrowedBookNum(bookDao.selectBorrowedBookCount()); //已借阅图书数量
        indexShowData.setCanBorrowBookNum(bookDao.selectCanBorrowBookCount()); //可借阅图书数量

        indexShowData.setBorrowNumInYear(recordDao.selectNumInYear()); //每年借阅数量
        return new R(Code.SUCCESS,indexShowData,"查询成功");
    }
}
