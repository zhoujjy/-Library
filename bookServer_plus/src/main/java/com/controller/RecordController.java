package com.controller;

import com.common.R;
import com.domain.Book;
import com.domain.Record;
import com.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping("/page/{currentPage}/{pageSize}")
    public R page(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody Record record) {
        return recordService.page(currentPage, pageSize, record);
    }

    @GetMapping
    public R indexShowData(){
    	return recordService.indexShowData();
    }
}
