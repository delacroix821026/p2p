package com.newland.financial.p2p.dao.impl;

import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.common.util.Constants;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DataAccessException;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @param <T> 泛型
 * @author Gregory
 */
@SuppressWarnings({"unchecked", "unused"})
public class MybatisBaseDao<T extends BaseEntity> extends SqlSessionDaoSupport {
    /**
     * 私有变量.
     */
    private Class<T> entityClass;
    /**
     * 命名.
     */
    private String mapperNamespace;

    /**
     * 构造器.
     */
    public MybatisBaseDao() {
        setEntityClass((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    /**
     * @param sqlSessionFactory SqlSessionFactory
     */
    @Resource
    public void setSqlSessionFactory(
            final SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    /**
     * @return List
     * @throws DataAccessException has a error
     */
    protected List<T> selectAll() throws DataAccessException {
        List<T> result = new ArrayList<T>();
        try {
            result = this.getSqlSession().selectList(
                    getMapperNamespace() + "." + "selectAll");
        } catch (DataAccessException e) {
            throw e;
        }
        return result;
    }

    /**
     * @param entity 相应实体类
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean insertSelective(final T entity)
            throws DataAccessException {
        return insert("insertSelective", entity);
    }

    /**
     * @param pk Long
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean deleteByPrimaryKey(final Long pk)
            throws DataAccessException {
        boolean flag = false;
        try {
            flag = this.getSqlSession().delete(
                    getMapperNamespace() + "."
                            + "deleteByPrimaryKey", pk) > 0 ? true
                    : false;
        } catch (DataAccessException e) {
            flag = false;
            throw e;
        }
        return flag;
    }

    /**
     * @param pk Long
     * @return 返回搜索结果.
     * @throws DataAccessException has a error
     */
    protected T selectByPrimaryKey(final Long pk) throws DataAccessException {
        T result = null;
        try {
            result = (T) this.getSqlSession().selectOne(
                    getMapperNamespace() + "." + "selectByPrimaryKey", pk);
        } catch (DataAccessException e) {
            throw e;
        }
        return result;
    }

    /**
     * @param pk String
     * @return 返回搜索结果.
     * @throws DataAccessException has a error
     */
    protected T selectByPrimaryKey(final String pk) throws DataAccessException {
        T result = null;
        try {
            result = (T) this.getSqlSession().selectOne(
                    getMapperNamespace() + "." + "selectByPrimaryKey", pk);
        } catch (DataAccessException e) {
            throw e;
        }
        return result;
    }

    /**
     * @param entity 相应实体类
     * @return List
     * @throws DataAccessException has a error
     */
    protected List<T> selectByEntity(final T entity)
            throws DataAccessException {

        return select("selectByEntity", entity);
    }

    /**
     * @param listId String
     * @param params Object
     * @param offset int
     * @param limit  int
     * @return AbstractPageModel
     * @throws Exception has a error
     */
    protected PageInfo selectByEntityAndPage(final String listId,
         final Object params, final int offset, final int limit)
            throws Exception {
        return selectByPage(listId, params, offset, limit);
    }

    /**
     * @param listId    String
     * @param params    Object
     * @param pageModel PageModel
     * @return PageModel
     * @throws Exception has a error
     */
    protected PageModel selectByEntityAndPage(final String listId,
                                              final Object params, final PageModel pageModel) throws Exception {
        PageInfo pageInfo = selectByPage(listId, params,
                pageModel.getCurrentPage(), pageModel.getPageSize());
        pageModel.setTotalRecord(new Long(pageInfo.getTotal()).intValue());
        if (pageModel.getTotalPage() > 0) {
            pageModel.setList(pageInfo.getList());
        }
        return pageModel;
    }

    /**
     * @param listId String
     * @param params Map
     * @return PageModel
     * @throws Exception has a error
     */
    protected PageModel selectByEntityAndPage(final String listId,
                                              final Map<String, String> params) throws Exception {
        String curPage = StringUtils.defaultIfEmpty((String) params.get(
                Constants.PAGED_CURPAGE), "1"); // 当前页数
        String numPerPage = StringUtils.defaultIfEmpty((String) params.get(
                Constants.PAGED_NUM_PERPAGE), "10"); // 每页条数
        PageModel pageModel = new PageModel(Integer.parseInt(curPage));
        pageModel.setPageSize(Integer.parseInt(numPerPage));
        return selectByEntityAndPage(listId, params, pageModel);
    }

    /**
     * @param listId String
     * @param params Object
     * @param offset int
     * @param limit  int
     * @return AbstractPageModel
     * @throws Exception has a error
     */
    protected PageInfo selectByPage(final String listId,
         final Object params, final int offset, final int limit)
            throws Exception {
        List<T> result = new ArrayList<T>();
        result = this.getSqlSession().selectList(getMapperNamespace() + "."
                + listId, params, new RowBounds(offset, limit));
        PageInfo page = new PageInfo(result);
        return page;
    }

    /**
     * @param id     String
     * @param entity 相应实体类
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean delete(final String id, final T entity)
            throws DataAccessException {
        boolean flag = false;
        try {
            flag = this.getSqlSession().delete(getMapperNamespace() + "." + id,
                    entity) > 0 ? true : false;
        } catch (DataAccessException e) {
            flag = false;
            throw e;
        }
        return flag;
    }

    /**
     * @param id     String
     * @param params Object
     * @return Long
     * @throws DataAccessException has a error
     */
    protected Long getTotalCount(final String id, final Object params)
            throws DataAccessException {
        return getSqlSession().selectOne(getMapperNamespace() + "." + id,
                params);
    }
    /*public PageModel getPage(String listId, String countId, Object params,
            PageModel pageModel) {

        // 得到数据记录总数
          Integer totalItem = (Integer) getSqlSession().selectOne(
              getMapperNamespace() + "." + countId, params);
              pageModel.setTotalRecord(totalItem);
          if (pageModel.getTotalPage() > 0) {
              pageModel.setList(this.getSqlSession().selectList(
                  getMapperNamespace() + "." + listId,params,
                   new PageBounds(pageModel.getCurrentPage(),
                  pageModel
                          .getPageSize())));
                }
             return pageModel;
    }*/

    /**
     * @param id     String
     * @param entity 相应实体类
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean update(final String id, final T entity)
            throws DataAccessException {
        boolean flag = false;
        try {
            flag = this.getSqlSession().update(getMapperNamespace() + "." + id,
                    entity) > 0 ? true : false;
        } catch (DataAccessException e) {
            flag = false;
            throw e;
        }
        return flag;
    }

    /**
     * @param id     String
     * @param params Object
     * @return List
     * @throws DataAccessException has a error
     */
    protected List<T> select(final String id, final Object params)
            throws DataAccessException {
        List<T> result = new ArrayList<T>();
        try {
            result = this.getSqlSession().selectList(
                    getMapperNamespace() + "." + id, params);
        } catch (DataAccessException e) {
            throw e;
        }
        return result;
    }

    /**
     * @param entity 相应实体类
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean updateByPrimaryKeySelective(final T entity)
            throws DataAccessException {

        return update("updateByPrimaryKeySelective", entity);
    }

    /**
     * @return Class
     */
    protected Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * @param entityCla Class
     */
    protected void setEntityClass(final Class<T> entityCla) {
        this.entityClass = entityCla;
        setMapperNamespace(entityClass.getName().substring(
                entityClass.getName().lastIndexOf(".") + 1));
    }

    /**
     * @return String
     */
    protected String getMapperNamespace() {
        return mapperNamespace;
    }

    /**
     * @param className String
     */
    protected void setMapperNamespace(final String className) {
        this.mapperNamespace = "com.newland.financial.p2p.mapping."
                + className + "Mapper";
    }

    /**
     * @param entity 相应实体类
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean saveOrUpdate(final T entity)
            throws DataAccessException {
        return saveOrUpdate("insertSelective", "updateByPrimaryKeySelective",
                entity);
    }

    /**
     * @param insertId String
     * @param updateId String
     * @param entity   相应实体类
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean saveOrUpdate(final String insertId,
            final String updateId, final T entity)
            throws DataAccessException {
        BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
        Long id = (Long) beanWrapper.getPropertyValue("id");
        if (id == null || id <= 0) { // 添加
            return insert(insertId, entity);
        } else {
            return update(updateId, entity);
        }
    }

    /**
     * @param id     String
     * @param entity 相应实体类
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean insert(final String id, final T entity)
            throws DataAccessException {
        return insertObj(id, entity);
    }

     /*@Override
       public PageModel getPage(String listId, String countId,
           Map paramsMap) {
         String curPage = StringUtils.defaultIfEmpty( // 当前页数
              (String)paramsMap.get(Constants.PAGED_CURPAGE), "1");
            String numPerPage = StringUtils.defaultIfEmpty( // 每页条数
             (String)paramsMap.get(Constants.PAGED_NUM_PERPAGE), "10");
            PageModel pageModel = new PageModel(Integer.parseInt(curPage));
            pageModel.setPageSize(Integer.parseInt(numPerPage));
             return getPage(listId, countId, paramsMap, pageModel);
          }*/

    /**
     * @param id     String
     * @param params Object
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean insertObj(final String id, final Object params)
            throws DataAccessException {
        boolean flag = false;
        try {
            flag = this.getSqlSession().insert(getMapperNamespace() + "." + id,
                    params) > 0 ? true : false;
        } catch (DataAccessException e) {
            flag = false;
            throw e;
        }
        return flag;
    }

    /**
     * @param id     String
     * @param params Object
     * @return T
     * @throws DataAccessException has a error
     */
    protected T selectEntity(final String id, final Object params)
            throws DataAccessException {
        List<T> list = select(id, params);
        if (list != null && list.size() > 0) {
            return (T) list.get(0);
        }
        return null;
    }

    /**
     * @param id     String
     * @param params Object
     * @return List
     * @throws DataAccessException has a error
     */
    protected List selects(final String id, final Object params)
            throws DataAccessException {
        List result = new ArrayList();
        try {
            result = this.getSqlSession().selectList(
                    getMapperNamespace() + "." + id, params);
        } catch (DataAccessException e) {
            throw e;
        }
        return result;
    }

    /**
     * @param id     String
     * @param object Object
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean deletes(final String id, final Object object)
            throws DataAccessException {
        boolean flag = false;
        try {
            flag = this.getSqlSession().delete(getMapperNamespace() + "." + id,
                    object) > 0 ? true : false;
        } catch (DataAccessException e) {
            flag = false;
            throw e;
        }
        return flag;
    }

    /**
     * @param id     String
     * @param object Object
     * @return Object
     */
    protected Object getObject(final String id, final Object object) {
        // TODO Auto-generated method stub
        return this.getSqlSession().selectOne(
                getMapperNamespace() + "." + id, object);
    }

    /**
     * @param curPage int
     * @param pageNum int
     * @return List
     * @throws DataAccessException has a error
     */
    public List<T> selectAll(final int curPage, final int pageNum)
            throws DataAccessException {

        List<T> result = new ArrayList<T>();
        PageInfo page = null;

        try {
            result = this.getSqlSession().selectList(
                    getMapperNamespace() + "."
                            + "selectAll", new RowBounds(curPage, pageNum));
            if (null != result || result.size() > 0) {

                page = new PageInfo(result);
            }

        } catch (DataAccessException e) {
            throw e;
        }
        return null == result ? null : page.getList();
    }

    /**
     * @param pk String
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean deleteByPrimaryKey(final String pk)
            throws DataAccessException {
        boolean flag = false;
        try {
            flag = this.getSqlSession().delete(
                    getMapperNamespace() + "."
                            + "deleteByPrimaryKey", pk) > 0 ? true
                    : false;
        } catch (DataAccessException e) {
            flag = false;
            throw e;
        }
        return flag;
    }

    /**
     * @param id String
     * @return List
     * @throws DataAccessException has a error
     */
    protected List<T> select(final String id) throws DataAccessException {
        List<T> result = new ArrayList<T>();
        try {
            result = this.getSqlSession().selectList(
                    getMapperNamespace() + "." + id);
        } catch (DataAccessException e) {
            throw e;
        }
        return result;
    }

    /**
     * 测试恢复用户数据专用.
     *
     * @param object Object
     * @param id     String
     * @return boolean
     * @throws DataAccessException has a error
     */
    protected boolean update(final String id, final Object object)
            throws DataAccessException {
        boolean flag = false;
        try {
            flag = this.getSqlSession().update(getMapperNamespace() + "." + id,
                    object) > 0 ? true : false;
        } catch (DataAccessException e) {
            flag = false;
            throw e;
        }
        return flag;
    }

}
