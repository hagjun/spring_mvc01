package com.ict.edu4.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public class DAO { //sqlSessionTemplate => root-context.xml 에 설정한 DB에 대한 정보를 사용할 수 있는 것
	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	// 리스트
	public List<VO> getList(){
		try {
			List<VO> list = null;
			list = sqlSessionTemplate.selectList("members.list");
			return list;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
		
	}
}
