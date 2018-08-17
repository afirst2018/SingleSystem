package com.chujiu.manager.codelist.managerdao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.CodeListDto;
import org.springframework.cache.annotation.CacheEvict;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[数据字典]
 * Description: [数据字典表DAO操作]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public interface CodeListDAO {

	/**
	 * Created on   2016年5月16日
	 * Discription: [查询全部数据字典信息]
	 * @return List<Map<String,String>>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:43:33
	 */
	List<Map<String,String>> selectAllCodeList();
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [增加一条数据字典信息]
	 * @param codeListEntity
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:43:48
	 */
	@CacheEvict(value = "codeListCache", allEntries=true)
	int addCodeList(CodeListDto codeListEntity);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [分页查询数据字典信息]
	 * @param page
	 * @param entity
	 * @return List<CodeListEntity>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:44:04
	 */
	List<CodeListDto> selectCodeListPage(@Param("page") PageParameter page, @Param("entity") CodeListDto entity);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [校验指定的数据字典信息是否存在]
	 * @param entity
	 * @return List<CodeListEntity>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:44:16
	 */
	List<CodeListDto> selectCodeListForCheck(@Param("entity") CodeListDto entity);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [按id查询数据字典信息]
	 * @param id
	 * @return CodeListEntity
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:44:28
	 */
	CodeListDto selectCodeListById(long id);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [修改数据字典信息]
	 * @param entity
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:44:41
	 */
	@CacheEvict(value = "codeListCache", allEntries=true)
	int updateCodeListById( @Param("entity") CodeListDto entity);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [删除数据字典信息]
	 * @param id
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:44:52
	 */
	@CacheEvict(value = "codeListCache", allEntries=true)
	int deleteCodeListById(long id);
}
