package com.chujiu.manager.codelist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chujiu.core.page.PageParameter;
import com.chujiu.core.util.SqlTool;
import com.chujiu.dto.CodeListDto;
import com.chujiu.manager.codelist.managerservice.CodeListManagerService;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[数据字典管理]
 * Description: [数据字典管理]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
@Controller
@RequestMapping("codeList")
public class CodeListManageController {
	
	@Autowired
	private CodeListManagerService codeListManagerService;
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [跳转到codelist列表页面]
	 * @param modelMap
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:19:39
	 */
	@RequestMapping("method_index")
	public String index(ModelMap modelMap) {
		PageParameter page = new PageParameter();
		List<CodeListDto> list = codeListManagerService.queryCodeListByPage(page, null);
		modelMap.put("page", page);
		modelMap.put("resultList", list);
		return "codelist/codeListManage";
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [向code_list表新增数据 ]
	 * @param codeListEntity
	 * @return Map<String,String>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:19:53
	 */
	@ResponseBody
	@RequestMapping("method_addCodeList")
	public Map<String, String> addCodeList(CodeListDto codeListEntity) {
		Map<String, String> resultMap = new HashMap<String, String>();
		int count = codeListManagerService.addCodeList(codeListEntity);
		String message = "";
		if (count > 0) {
			message = "增加成功";
		} else {
			message = "增加失败";
		}
		resultMap.put("message", message);
		return resultMap;
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [修改code_list表数据 ]
	 * @param codeListEntity
	 * @return Map<String,String>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:20:13
	 */
	@ResponseBody
	@RequestMapping("method_updCodeList")
	public Map<String, String> updCodeList(CodeListDto codeListEntity) {
		Map<String, String> resultMap = new HashMap<String, String>();
		int count = codeListManagerService.updateCodeListById(codeListEntity);
		String message = "";
		if (count > 0) {
			message = "修改成功";
		} else {
			message = "修改失败";
		}
		resultMap.put("message", message);
		return resultMap;
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询codelist列表]
	 * @param page
	 * @param codeListEntity
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:20:35
	 */
	@ResponseBody
	@RequestMapping("method_queryCodeList")
	public Map<String, Object> queryCodeList(PageParameter page, CodeListDto codeListEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 记录查询条件,为翻页查询使用。
		// 说明：翻页时使用的查询条件是点击“查询按钮”记录的查询条件，而不使用用户临时输入的查询条件。
		map.put("knameConditionTxtHdn", codeListEntity.getKindName());
		map.put("kvalueConditionTxtHdn", codeListEntity.getKindValue());
		map.put("cnameConditionTxtHdn", codeListEntity.getCodeName());
		map.put("cvalueConditionTxtHdn", codeListEntity.getCodeValue());
		map.put("remarkConditionTxtHdn", codeListEntity.getRemark());
		//处理特殊字符
		codeListEntity.setKindName(SqlTool.transfer(codeListEntity.getKindName()));
		codeListEntity.setKindValue(SqlTool.transfer(codeListEntity.getKindValue()));
		codeListEntity.setCodeName(SqlTool.transfer(codeListEntity.getCodeName()));
		codeListEntity.setCodeValue(SqlTool.transfer(codeListEntity.getCodeValue()));
		codeListEntity.setRemark(SqlTool.transfer(codeListEntity.getRemark()));
		List<CodeListDto> list = codeListManagerService.queryCodeListByPage(page, codeListEntity);
		map.put("page", page);
		map.put("resultList", list);
		return map;
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [根据id查询codelist]
	 * @param id
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:21:01
	 */
	@ResponseBody
	@RequestMapping("method_queryCodeListById")
	public Map<String, Object> queryCodeListById(
			@RequestParam(value = "id", required = true) long id) {
		CodeListDto entity = codeListManagerService.queryCodeListById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == entity) {
			map.put("flag", "false");
		} else {
			map.put("flag", "true");
			map.put("entity", entity);
		}
		return map;
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [根据id查询codelist]
	 * @param codeListEntity
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:21:29
	 */
	@ResponseBody
	@RequestMapping("method_checkExistOne")
	public Map<String, Object> checkExistOne(CodeListDto codeListEntity) {
		List<CodeListDto> entityList = codeListManagerService.queryCodeListForCheck(codeListEntity);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == entityList || entityList.size() < 1) {
			map.put("flag", "0");
		} else {
			map.put("flag", "1");
		}
		return map;
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [根据id删除codelist]
	 * @param id
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:21:49
	 */
	@ResponseBody
	@RequestMapping("method_delCodeListById")
	//@MenuItem(name={"删除数据字典"},url={"/codelist/codeList/delCodeListById.html"},menuKey={1206},orderNum={206},parentMenuKey=1000,menuType=MenuType.FUNC_URL)
	public Map<String, Object> delCodeListById(@RequestParam(value = "id", required = true) long id) {
		int count = codeListManagerService.deleteCodeListById(id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String message = "";
		if (count > 0) {
			message = "删除成功";
		} else {
			message = "删除失败";
		}
		resultMap.put("message", message);
		return resultMap;
	}
}
