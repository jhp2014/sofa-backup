package com.sof8.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Member;
import com.sof8.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService service;

	String dir = "member/";

	// 127.0.0.1/member/join
	@RequestMapping("/join")
	public String join(Model model) {
		// 회원가입폼 출력
		model.addAttribute("content", dir + "join");
		System.out.println("[SUCCESS] : MemberController/join - 회원가입폼");
		return "index";
	}

	// 127.0.0.1/member/joinok
	@RequestMapping("/joinok")
	public String joinok(Model model, Member member) {
		try {
			// 회원가입 기능 실행
			service.register(member);
			// 회원가입 성공화면 출력
			model.addAttribute("content", dir + "joinok");
			// 성공화면에 출력할 이름
			model.addAttribute("name", member.getName());
			System.out.println("[SUCCESS] : MemberController/joinok - 회원가입 성공");
			//throw new Exception(); 

		} catch (Exception e) {
			// 회원가입 실패화면 출력
			model.addAttribute("content", dir + "joinfail");

			System.out.println("[ERROR] : MemberController/joinok - 회원가입 실패");
			e.printStackTrace();
		}
		return "index";
	}

	// 127.0.0.1/member/login
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("content", dir + "login");
		System.out.println("[SUCCESS] : MemberController/login - 로그인 화면출력");
		return "index";
	}

	// 127.0.0.1/member/loginok
	@RequestMapping("/loginok")
	public String loginok(HttpSession session, Model model, String user_id, String pwd) {
		String error = null;
		try {
			// 가입된 아이디 조회
			Member user = service.get(user_id);
			// 가입된 아이디라면
			if (user != null && user.getEnable() == true) {
				// 가입된 아이디의 비밀번호가 일치한다면
				if (user.getPwd().equals(pwd)) {
					// 세션에 로그인 유저정보 저장
					session.setAttribute("login_user", user);
					// 홈 화면으로 이동
					System.out.println("[SUCCESS] : MemberController/loginok - 로그인 성공");
					return "redirect:/";                   
				}else {
					error = "아이디 또는 비밀번호가 일치하지 않습니다.";
					System.out.println("[ERROR] : MemberController/joinok - 비밀번호 로그인 실패");				
				}
			} else if(user.getEnable() == false) {
				error = "탈퇴 등의 사유로 활동이 정지된 계정입니다.";
			}else {
				error = "아이디 또는 비밀번호가 일치하지 않습니다.";
				System.out.println("[ERROR] : MemberController/joinok - 아이디 로그인 실패");
			}
			model.addAttribute("error_login", error);
			model.addAttribute("content", dir + "login");
		} catch (Exception e) {
			model.addAttribute("content", dir + "loginfail");
			System.out.println("[ERROR] : MemberController/joinok - 로그인 실패");
			e.printStackTrace();
		}

		return "index";
	}
	
	// 127.0.0.1/member/logout
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		// 세션이 존재할 시 세션을 삭제하여 로그아웃
		if(session != null)	session.invalidate();
		
		model.addAttribute("content", null);
		System.out.println("[SUCCESS] : MemberController/logout - 로그아웃 성공");
		
		return "redirect:/";                   
	}

	// 127.0.0.1/member/find_id
	@RequestMapping("/find_id")
	public String find_id(Model model) {
		model.addAttribute("content", dir + "find_id");
		System.out.println("[SUCCESS] : MemberController/find_id - 아이디찾기 화면출력");
		return "index";
	}
	
	// 127.0.0.1/member/find_password
	@RequestMapping("/find_password")
	public String find_password(Model model) {
		model.addAttribute("content", dir + "find_password");
		System.out.println("[SUCCESS] : MemberController/find_password - 비밀번호찾기 화면출력");
		return "index";
	}
	
	// 127.0.0.1/member/user_certification
	@RequestMapping("/user_certification")
	public String user_certification(Model model) {
		model.addAttribute("content", dir + "user_certification");
		System.out.println("[SUCCESS] : MemberController/user_certification - 비밀번호찾기 이메일확인 화면출력");
		return "index";
	}
	
	// 127.0.0.1/member/user_certification_confirm
	@RequestMapping("/user_certification_confirm")
	public String user_certification_confirm(Model model) {
		model.addAttribute("content", dir + "user_certification_confirm");
		System.out.println("[SUCCESS] : MemberController/user_certification_confirm - 비밀번호찾기 이메일 인증번호 화면출력");
		return "index";
	}
	
	// 127.0.0.1/member/reset_password
	@RequestMapping("/reset_password")
	public String reset_password(Model model) {
		model.addAttribute("content", dir + "reset_password");
		System.out.println("[SUCCESS] : MemberController/reset_password - 비밀번호찾기 새로운 비밀번호 화면출력");
		return "index";
	}
	
	// 127.0.0.1/member/mypage
	@RequestMapping("/mypage")
	public String mypage(HttpSession session, Model model) {
		
		// 세션 아이디를 다운캐스팅하여 Member 변수에 초기화
		Member user = (Member) session.getAttribute("login_user");
		try {
			// 회원 정보 로딩
			user = service.get(user.getUser_id());
			System.out.println("user: " + user);
			model.addAttribute("user", user);
			model.addAttribute("content", dir + "mypage");
			System.out.println("[SUCCESS] : MemberController/mypage - 마이페이지 화면출력");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
}