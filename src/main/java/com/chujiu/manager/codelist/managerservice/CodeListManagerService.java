package com.chujiu.manager.codelist.managerservice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chujiu.core.page.PageParameter;
import com.chujiu.core.util.SecurityUtil;
import com.chujiu.dto.CodeListDto;
import com.chujiu.manager.codelist.managerdao.CodeListDAO;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[数据字典]
 * Description: [对code_list表进行增删改查 ]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
@Service
public class CodeListManagerService {

	@Autowired
	private CodeListDAO codeListDAO;

	@Autowired
	private CodeListCacheDBImpl codeListCacheDBImpl;

	/**
	 * Created on   2016年5月16日
	 * Discription: [向code_list表里增加一条记录]
	 * @param codeListEntity
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:06:19
	 */
	public int addCodeList(CodeListDto codeListEntity) {
		Object oId = SecurityUtil.getCurrentUserMap().get("id");
		Date date = new Date();
		codeListEntity.setCreateBy(Long.parseLong(oId.toString()));
		codeListEntity.setCreateOn(date);
		codeListEntity.setModifiedBy(Long.parseLong(oId.toString()));
		codeListEntity.setModifiedOn(date);
		return codeListDAO.addCodeList(codeListEntity);
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [分页查询code_list表]
	 * @param page
	 * @param codeListEntity
	 * @return List<CodeListDto>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:06:38
	 */
	public List<CodeListDto> queryCodeListByPage(PageParameter page, CodeListDto codeListEntity) {
		return codeListDAO.selectCodeListPage(page,codeListEntity);
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [根据id查询codelist]
	 * @param id
	 * @return CodeListDto
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:06:55
	 */
	public CodeListDto queryCodeListById(long id) {
		return codeListDAO.selectCodeListById(id);
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [修改code_list表数据]
	 * @param codeListEntity
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:07:59
	 */
	public int updateCodeListById(CodeListDto codeListEntity) {
		Object oId = SecurityUtil.getCurrentUserMap().get("id");
		codeListEntity.setModifiedBy(Long.parseLong(oId.toString()));
		codeListEntity.setModifiedOn(new Date());
		return codeListDAO.updateCodeListById(codeListEntity);
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [根据id删除codelist]
	 * @param id
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:08:23
	 */
	public int deleteCodeListById(long id) {
		return codeListDAO.deleteCodeListById(id);
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [校验待插入表中的数据是否已存在]
	 * @param codeListEntity
	 * @return List<CodeListDto>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:08:39
	 */
	public List<CodeListDto> queryCodeListForCheck(CodeListDto codeListEntity) {
		return codeListDAO.selectCodeListForCheck(codeListEntity);
	}

	/**
	 * Created on   2015年7月31日
	 * Discription: [根据kindvalue查询多个codelist]
	 * @author:     nisicong
	 * @update:     2015年7月31日 下午2:28:18
	 * @return      Map<String,Map<String,String>> .
	 */
	public Map<String, List<Map<String, String>>> getCodeListMapByKindValue(String... kindValues) {
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>(kindValues.length);
		for(String kindValue : kindValues) {
			map.put(kindValue, codeListCacheDBImpl.initCodeListCache().get(kindValue));
		}
		return map;
	}

	/**
	 * Created on   2015年11月2日
	 * Discription: [根据kindvalue查询多个codelist转码专用]
	 * @param kindValues
	 * @return Map<String,List<Map<String,String>>>
	 * @author:     nisicong
	 * @update:     2015年11月2日 下午4:35:10
	 */
	public Map<String, Map<String, String>> getCodeListMapByKindValueForDecode(String... kindValues) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>> (kindValues.length);
		for(String kindValue : kindValues) {
			map.put(kindValue, codeListCacheDBImpl.getCodeListCacheForDecode().get(kindValue));
		}
		return map;
	}
}
