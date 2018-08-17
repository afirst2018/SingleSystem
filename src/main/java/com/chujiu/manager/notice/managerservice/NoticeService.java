package com.chujiu.manager.notice.managerservice;

import com.chujiu.core.page.PageParameter;
import com.chujiu.core.util.SecurityUtil;
import com.chujiu.dto.NoticeDto;
import com.chujiu.manager.notice.managerdao.NoticeManageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on   2015年8月12日
 * Title:       麟龙大平台_[投顾]_[公告管理]
 * Description: [公告管理service]
 * Copyright:   Copyright (c) 2015
 * Company:     初九科技（上海）有限公司
 * Department:  研发部
 * @author:     chujiu
 * @version:    1.0
*/
@Service
public class NoticeService {

    @Autowired
    private NoticeManageDAO noticeManageDAO;

    /**
     * Created on   2015年8月12日
     * Discription: [新增公告]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:58:14
     * @return      int .
     */
    public int addNotice(NoticeDto dto) {
        String userName = SecurityUtil.getCurrentUserName();
        dto.setBuilder(userName);
        dto.setModifier(userName);
        return noticeManageDAO.addNotice(dto);
    }

    /**
     * Created on   2015年8月12日
     * Discription: [修改公告]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:58:23
     * @return      int .
     */
    public int updateNotice(NoticeDto dto) {
        String userName = SecurityUtil.getCurrentUserName();
        dto.setModifier(userName);
        return noticeManageDAO.updateNotice(dto);
    }

    /**
     * Created on   2015年8月12日
     * Discription: [删除公告]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:58:34
     * @return      int .
     */
    public int deleteNotice(long id) {
        return noticeManageDAO.deleteNotice(id);
    }

    /**
     * Created on   2015年8月12日
     * Discription: [按条件分页查询公告信息]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:58:44
     * @return      List<NoticeDto> .
     */
    public List<NoticeDto> selectNotice(PageParameter page, String modifyDateStart,
                                        String modifyDateEnd , String noticeText) {
        return noticeManageDAO.selectNoticePage(page, modifyDateStart, modifyDateEnd, noticeText);
    }

    /**
     * Created on   2015年8月12日
     * Discription: [根据id查询公告信息]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:59:17
     * @return      List<NoticeDto> .
     */
    public List<NoticeDto> selectNoticeById(long id) {
        return noticeManageDAO.selectNoticeById(id);
    }
}
