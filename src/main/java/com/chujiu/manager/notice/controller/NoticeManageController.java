package com.chujiu.manager.notice.controller;

import com.chujiu.core.page.PageParameter;
import com.chujiu.core.util.SqlTool;
import com.chujiu.dto.NoticeDto;
import com.chujiu.manager.notice.managerservice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created on   2015年8月10日
 * Title:       麟龙大平台_[投顾]_[公告管理]
 * Description: [公告管理controller]
 * Copyright:   Copyright (c) 2015
 * Company:     初九科技（上海）有限公司
 * Department:  研发部
 * @author:     chujiu
 * @version:    1.0
*/
@Controller
@RequestMapping("/noticeManage")
public class NoticeManageController {

    @Autowired
    private NoticeService noticeService;

    /**
     * Created on   2015年8月12日
     * Discription: [显示首页]
     * @author:     chujiu
     * @update:     2015年8月12日 上午11:04:11
     * @return      ModelAndView .
     */
    @RequestMapping(value = "method_index")
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/index");
        return mav;
    }

    /**
     * Created on   2015年8月11日
     * Discription: [新增公告信息]
     * @author:     chujiu
     * @update:     2015年8月11日 下午3:04:49
     * @return      int .
     */
    @RequestMapping("method_addNotice")
    @ResponseBody
    public int addNotice(NoticeDto dto) {
        return noticeService.addNotice(dto);
    }

    /**
     * Created on   2015年8月11日
     * Discription: [修改公告信息]
     * @author:     chujiu
     * @update:     2015年8月11日 下午3:04:30
     * @return      int .
     */
    @RequestMapping("method_updateNotice")
    @ResponseBody
    public int updateNotice(NoticeDto dto) {
        return noticeService.updateNotice(dto);
    }

    /**
     * Created on   2015年8月11日
     * Discription: [删除公告信息]
     * @author:     chujiu
     * @update:     2015年8月11日 下午2:29:00
     * @return      int .
     */
    @RequestMapping("method_deleteNotice")
    @ResponseBody
    public int deleteNotice(long id) {
        return noticeService.deleteNotice(id);
    }

    /**
     * Created on   2015年8月11日
     * Discription: [按照条件查询公告信息]
     * @author:     chujiu
     * @update:     2015年8月11日 下午2:22:43
     * @return      Map<String,Object> .
     */
    @RequestMapping("method_selectNotice")
    @ResponseBody
    public Map<String, Object> selectNotice(PageParameter page,
            @RequestParam(value = "modifyDateStart", required = false) String modifyDateStart,
            @RequestParam(value = "modifyDateEnd", required = false) String modifyDateEnd,
            @RequestParam(value = "noticeText", required = false) String noticeText) {
        noticeText = SqlTool.transfer(noticeText);
        List<NoticeDto> list = noticeService.selectNotice(page, modifyDateStart, modifyDateEnd,noticeText);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageNo", page.getPageNo());
        map.put("pageSize", page.getPageSize());
        map.put("totalCount", page.getTotalCount());
        map.put("totalPage", page.getTotalPage());
        map.put("resultList", list);
        return map;
    }

    /**
     * Created on   2015年8月12日
     * Discription: [根据id查询公告信息]
     * @author:     chujiu
     * @update:     2015年8月12日 上午11:05:43
     * @return      List<NoticeDto> .
     */
    @RequestMapping("method_selectNoticeById")
    @ResponseBody
    public List<NoticeDto> selectNoticeById(long id) {
        return noticeService.selectNoticeById(id);
    }
}
