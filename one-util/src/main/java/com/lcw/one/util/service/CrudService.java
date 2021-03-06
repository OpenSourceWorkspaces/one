package com.lcw.one.util.service;

import com.lcw.one.util.persistence.BaseRepositoryImpl;
import com.lcw.one.util.persistence.entity.TreeEntity;
import com.lcw.one.util.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public abstract class CrudService<D extends BaseRepositoryImpl, T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected D dao;

    public void flush() {
        dao.flush();
    }

    @Transactional
    public T save(T entity) {
        if (entity instanceof TreeEntity) {
            entity = (T) dao.saveTreeEntity(entity);
        } else {
            entity = (T) dao.save(entity);
        }
        return entity;
    }

    @Transactional
    public List<T> save(List<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        dao.save(entityList);
        return entityList;
    }

    @Transactional
    public T update(T entity) {
        if (entity instanceof TreeEntity) {
            entity = (T) dao.saveTreeEntity(entity);
        } else {
            entity = (T) dao.save(entity);
        }
        return entity;
    }

    @Transactional
    public List<T> update(List<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        dao.save(entityList);
        return entityList;
    }

    @Transactional
    public void delete(String id) {
        dao.delete(id);
    }

    @Transactional
    public void delete(String... ids) {
        for (String id : ids) {
            dao.delete(id);
        }
    }

    public T get(String id) {
        T t = (T) dao.findOne(id);
        loadTreeParent(t);
        return t;
    }

    private void loadTreeParent(T t) {
        if (t instanceof TreeEntity) {
            TreeEntity treeEntity = (TreeEntity) t;
            treeEntity.setParent((TreeEntity) dao.findOne(treeEntity.getParentId()));
        }
    }

    public List<T> findAll() {
        return dao.findAll();
    }


}