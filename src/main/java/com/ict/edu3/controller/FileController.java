package com.ict.edu3.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class FileController {
	
	// cos 라이브러리 사용
	@PostMapping("f_up.do")
	public ModelAndView fileUp(HttpServletRequest request, HttpServletResponse response) {
		try {
			ModelAndView mv = new ModelAndView("test03/result");
			String path = request.getSession().getServletContext().getRealPath("/resources/upload");
			MultipartRequest mr = // 하는 순간 업로드는 됨
					new MultipartRequest(request, path, 500*1024*1024, "utf-8", new DefaultFileRenamePolicy());
			String name = mr.getParameter("name");
			String f_name = mr.getFilesystemName("f_name");
			String file_type = mr.getContentType("f_name");
			
			File file = mr.getFile("f_name");
			long size = file.length()/1024; // KB표시
			SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd hh:mm:ss E");
			String lastday = sdf.format(file.lastModified());
			
			mv.addObject("name", name);
			mv.addObject("f_name", f_name);
			mv.addObject("file_type", file_type);
			mv.addObject("size", size);
			mv.addObject("lastday", lastday);
			
			return mv;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	// 다운로드
	@GetMapping("down.do")
	public void FileDown(HttpServletRequest request, HttpServletResponse response) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String f_name = request.getParameter("f_name");
			String path = request.getSession().getServletContext().getRealPath("/resources/upload/"+f_name);
			// 한글처리
			String r_path = URLEncoder.encode(path, "utf-8");
			
			// 브라우저 설정
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + r_path);
			
			File file = new File(new String (path.getBytes(), "utf-8"));
			int b;
			
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			
			while((b=bis.read()) != -1) {
				bos.write(b);
				bos.flush();
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				bos.close();
				bis.close();
				fis.close();
			} catch (Exception e2) {

			}
		}
		
	}
	
	// pom.xml 에서 파일업로드 와 다운로드에 관련된 라이브러리 등록 
	// commons-fileupload, commons-io 
	// servlet-contex.xml에 파일업로드용 클래스 등록
	// 업로드시 파일 용량 제한 설정
	
	@PostMapping("f_up2.dp")
	public ModelAndView fileUp2(// @RequestParam("f_name") 여러개 중에 찍어서 받을 수 있다.
			@RequestParam("f_name") MultipartFile fname,
			@RequestParam("name") String name,
			HttpServletRequest request
			) {  
		try {
			ModelAndView mv = new ModelAndView("test03/result");
			String path = request.getSession().getServletContext().getRealPath("/resources/upload");// 부모
			
			// Spring 파일업로드의 단점은 같은이름 처리를 하지 않습니다.
			// 저장 폴더에 같은 이름이 있으면 업로드 되지 않는다.
			String f_name = fname.getOriginalFilename(); // 자식
			String file_type = fname.getContentType();
			long size = fname.getSize()/1024;
			
			// 실제 올리는 작업을 하자.
			// 올릴 파일을 byte[]로 만든것
			byte[] in = fname.getBytes();
			
			
			// 올릴 장소와 저장이름 지정
			// 부모, 자식
			File out = new File(path, f_name);
			
			// 파일복사(upload = download)
			FileCopyUtils.copy(in, out); // 복사끝
			mv.addObject("name", name);
			mv.addObject("f_name", f_name);
			mv.addObject("file_type", file_type);
			mv.addObject("size", size);
		//  ----------------파일 업로드 끝-------
			return mv;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null; // return null; 을 주지않으면 오류
	}
	
	@GetMapping("down2.do")
	public void fileDown2(@RequestParam("f_name") String f_name,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = request.getSession().getServletContext().getRealPath("/resources/images/"+f_name);
			String r_path = URLEncoder.encode(path, "uft-8");
			
			// 브라우저 설정
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + r_path);
			
			File file = new File(new String(path.getBytes(), "utf-8"));
			FileInputStream in = new FileInputStream(file); // in 끝
			
			OutputStream out = response.getOutputStream(); // out 끝
			FileCopyUtils.copy(in, out);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
}
