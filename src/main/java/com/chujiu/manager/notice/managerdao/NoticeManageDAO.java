package com.chujiu.manager.notice.managerdao;

import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.NoticeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created on   2015年8月12日
 * Title:       麟龙大平台_[投顾]_[公告管理]
 * Description: [公告管理DAO]
 * Copyright:   Copyright (c) 2015
 * Company:     初九科技（上海）有限公司
 * Department:  研发部
 * @author:     chujiu
 * @version:    1.0
*/
public interface NoticeManageDAO {

    /**
     * Created on   2015年8月12日
     * Discription: [新增公告]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:55:19
     * @return      int .
     */
    int addNotice(NoticeDto dto);

    /**
     * Created on   2015年8月12日
     * Discription: [修改公告]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:55:34
     * @return      int .
     */
    int updateNotice(NoticeDto dto);

    /**
     * Created on   2015年8月12日
     * Discription: [删除公告]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:55:45
     * @return      int .
     */
    int deleteNotice(long id);

    /**
     * Created on   2015年8月12日
     * Discription: [按照条件分页查询公告]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:55:58
     * @return      List<NoticeDto> .
     */
    List<NoticeDto> selectNoticePage(@Param("page") PageParameter page,
                                     @Param("modifyDateStart") String modifyDateStart,
                                     @Param("modifyDateEnd") String modifyDateEnd,
                                     @Param("noticeText") String noticeText);

    /**
     * Created on   2015年8月12日
     * Discription: [根据id查询公告信息]
     * @author:     chujiu
     * @update:     2015年8月12日 上午10:56:14
     * @return      List<NoticeDto> .
     */
    List<NoticeDto> selectNoticeById(@Param("id") long id);
}
