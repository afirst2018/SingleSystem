package com.chujiu.manager.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.chujiu.core.page.PageParameter;
import com.chujiu.core.util.JSONTool;
import com.chujiu.dto.ScheduleJobDto;
import com.chujiu.manager.codelist.managerservice.CodeListManagerService;
import com.chujiu.manager.quartz.managerservice.ScheduleJobService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianci on 2016/10/9.
 */
@Controller
@RequestMapping("QuartzController")
public class QuartzController {

    @Autowired
    @Qualifier("schedulerFactoryBean")
    private Scheduler scheduler;

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private CodeListManagerService codeListManagerService;

    @RequestMapping("method_index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("quartz/quartz");
        Map<String, Map<String, String>> codeList = codeListManagerService.getCodeListMapByKindValueForDecode("JOB_STATUS","isTrue");
        mav.addObject("codeList", JSON.toJSONString(codeList));
        return mav;
    }

    @Value("${quartz.start}")
    private String quartzStart;

    @RequestMapping("method_getScheduleJob")
    @ResponseBody
    public Map<String, Object> getScheduleJob(ScheduleJobDto entity, PageParameter page) {
        Map<String, Object> map = new HashMap<>();
        List<ScheduleJobDto> jobList = scheduleJobService.getScheduleJobByPage(entity, page);
        map.put("jobList", jobList);
        map.put("page", page);
        map.put("quartzStart", quartzStart);
        return map;
    }

    @RequestMapping(value = "method_updateScheduleJob")
    @ResponseBody
    public int updateScheduleJob(ScheduleJobDto entity) {

        return scheduleJobService.updateScheduleJob(entity);
    }

    @RequestMapping(value = "method_runJobNow")
    @ResponseBody
    public int runJobNow(@RequestParam(value = "id") Long id) {
        /*返回值 0失败，1成功，-1定时任务队列中无此任务（需先启动定时任务，并将此定时任务添加到执行队列中）*/
        if ("on" .equals(quartzStart)) {
            return scheduleJobService.runJobNow(id, scheduler);
        } else {
            return -1;
        }
    }

}
