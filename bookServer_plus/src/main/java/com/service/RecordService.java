package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.R;
import com.domain.Record;

public interface RecordService extends IService<Record> {
    R page(int currentPage, int pageSize, Record record);

    R indexShowData();
}
