package com.care.root.student.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.care.root.common.session.MemberSessionName;
import com.care.root.member.service.MemberFileService;
import com.care.root.student.dto.GradeDTO;
import com.care.root.student.service.StudentService;

@Controller
@RequestMapping("student")
public class StudentController implements MemberSessionName{
	
	@Autowired StudentService ss;

		@RequestMapping("studentMain")
		public String studentMain() {
			return "student/studentMain";
		}
		
		@RequestMapping("totalInquiry")
		public String totalInquiry(Model model, HttpSession session) {
			String id = (String) session.getAttribute(LOGIN);
			ss.studentInfo(model, id);
			
			
			return "student/totalInquiry";
		}
		
		@RequestMapping("totalInquiry_C")
		public String totalInquiry_C(Model model, HttpSession session) {
			String id = (String) session.getAttribute(LOGIN);
			ss.subjectInfo(model, id);
			return "student/totalInquiry_C";
		}
		
		@RequestMapping("totalInquiry_G")
		public String totalInquiry_G(Model model, HttpSession session) {
			String id = (String) session.getAttribute(LOGIN);
			ss.totalInquiry_G(model, id);
			return "student/totalInquiry_G";
		}
		
		@RequestMapping("totalInquiry_T")
		public String totalInquiry_T(Model model, HttpSession session) {
			String id = (String) session.getAttribute(LOGIN);
			ss.totalInquiry_T(model, id);
			return "student/totalInquiry_T";
		}
		
		@RequestMapping("classRequest")
		public String classRequest(Model model, HttpSession session) {
			String id = (String) session.getAttribute(LOGIN);
			ss.classRequest(model, id);
			
			return "student/classRequest";
		}
		
		@PostMapping("classReqChk")
		public String classReqChk(HttpSession session) {
			String id = (String) session.getAttribute(LOGIN);
			ss.classReqChk(id);
			
			return "redirect:classRequest";
		}
		
		@RequestMapping("gradeInquiry")
		public String gradeInquiry(Model model, HttpSession session) {
			String id = (String) session.getAttribute(LOGIN);
			ss.gradeInquiry(model, id);
			
			return "student/gradeInquiry";
		}
		
		@RequestMapping("tuitionChk")
		public String tuitionChk(Model model, HttpSession session) {
			String id = (String) session.getAttribute(LOGIN);
			ss.tuitionInfo(model, id);
			
			return "student/tuitionChk";
		}
		@PostMapping("tuitionPayment")
		public String tuitionPayment(HttpServletResponse response, HttpSession session,HttpServletRequest request) throws Exception {
			/*
			 * System.out.println("?????? : "+request.getParameter("bankSelet"));
			 * System.out.println("?????? : "+request.getParameter("account"));
			 */
			String id = (String) session.getAttribute(LOGIN);
			String message = ss.tuitionPayment(id, request);
			
			//PrintWriter out = response.getWriter();
			//response.setContentType("text/html; charset=utf-8");
			//out.print(message);

			return "redirect:tuitionChk";
		}
		@ResponseBody
		@GetMapping(value="detailGrade/{semester}", produces="application/json;charset=utf-8")
		public List<GradeDTO> detailGrade(@PathVariable int semester, HttpSession session){
			//String id = "20201234";
			//int semester = 101;
			String id = (String) session.getAttribute(LOGIN);
			
			
			return ss.detailGrade(semester, id);
		}
		
		@GetMapping("download")
		public void download(@RequestParam("file") String fileName, HttpServletResponse response) throws Exception{
			//final String IMAGE_REPO = "C:\\Users\\Hyunwoo\\spring\\image_repo";
			final String IMAGE_REPO = MemberFileService.IMAGE_REPO;
			//Content-disposition(http ????????? ??????) : ?????? ???????????? ?????? http ????????? ???????????? ??????.
			//attachment : ????????? ???????????? ?????? ??????????????? ???????????? ??????
			response.addHeader("Content-disposition", "attachment; fileName="+fileName);
			//IMAGE_REPO : ????????? ?????? ????????? ?????? ???????????? ??????
			File file = new File(IMAGE_REPO+"\\"+fileName);
			//???????????? ?????? ???????????? inputStream?????? ????????? ???
			FileInputStream in = new FileInputStream(file);
			//in : ????????? ???(????????? ????????? ???)???, out : ?????????(reponse(??????).output ?????????)
			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			
		}
}
