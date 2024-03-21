<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	request.setCharacterEncoding("utf-8");
	String path = request.getParameter("path");
	String f_name = request.getParameter("f_name");
	
	// 실제 위치
	String r_path = request.getServletContext().getRealPath(path);
	
	// 웹 브라우저 문서 타입을 다운로드 할 수있는 형태로 변경(가짜로 다운)
	response.setContentType("application/x-msdownload");
	
	// http 헤더 정보에 첨부파일이 존재하는 것으로 변경
	response.setHeader("Content-Disposition", 
			"attachment; filename=" + URLEncoder.encode(f_name, "utf-8"));
	
	// 실제 바이트 스트림을 통해서 다운로드 해야 된다.
	// 실제 위치 안에 파일명을 이용해서 선택
	File file = new File(r_path + "/" + new String(f_name.getBytes(), "utf-8"));
	
	int b;
	FileInputStream fis = null;
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	try{
		fis = new FileInputStream(file);
		bis = new BufferedInputStream(fis);
		
		// 브라우저에 output 처리 
		bos = new BufferedOutputStream(response.getOutputStream());
		
		while((b = bis.read()) != -1){
			bos.write(b);
			bos.flush();
		}
		out.clear();
		out = pageContext.pushBody(); // 충돌방지
	}catch(Exception e){
		System.out.println(e);
	}finally{
		try{
			bos.close();
			bis.close();
			fis.close();
		}catch(Exception e){
			
		}
	}
%>