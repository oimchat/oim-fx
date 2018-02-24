/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.im.base.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.only.query.hibernate.dao.AbstractDAO;
import com.only.query.hibernate.QueryContext;

/**
 *
 * @author Only
 */
@Repository
public class WriteDAO extends AbstractDAO {

	@Resource(name = "writeSessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Resource(name = "queryContext")
	public void setQueryContext(QueryContext queryContext) {
		super.setQueryContext(queryContext);
	}

}
