package edu.whu.springbootdemo.controller;

import edu.whu.springbootdemo.common.ApiMonitorAspect;
import edu.whu.springbootdemo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiInfoController {

    @Autowired
    ApiMonitorAspect apiMonitorAspect;

    @GetMapping("/info")
    public R getApiStatus() {
        return R.success(apiMonitorAspect.getApiInfoMap());
    }
}
