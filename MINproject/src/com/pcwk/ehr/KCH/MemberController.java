
package com.pcwk.ehr.KCH;

import java.util.Scanner;

import com.pcwk.ehr.cmn.PLog;

public class MemberController implements PLog {

	private MemberDao dao;
	
	public MemberController() {
		dao = new MemberDao();
	}
	
	public int doUpdate() {
		int flag = 0;
		MemberVO param=new MemberVO();
		
		//1.Scanner
		//2.등록사용자 정보
		//3.dao.doSave(param);
		
		Scanner scanner=new Scanner(System.in);
		System.out.printf("수정할 회원 정보를 입력 하세요.>"
				+ "\n(pcwk01,이상무01,4321,jamesol@paran.com,1,0,2024/10/17 14:33:00,일반)");
		
		String inputData = scanner.nextLine().trim();
		log.debug("1. inputData:{}",inputData);
		//회원정보를 받아, MemberVO변환
		param = dao.stringToMember(inputData);
		log.debug("2. param:{}",param);
		
		//dao호출
		flag = dao.doUpdate(param);
		log.debug("3. flag:{}",flag);
		return flag;
	}
	/**
	 * 회원삭제
	 * @return 1(성공)/0(실패)
	 */
	public int doDelete() {
		int flag = 0;
		MemberVO param =new MemberVO();//조회 파라메터:memberId
		//1.Scanner
		//2.조회 사용자ID 입력
		//3.dao.doDelete(param);	
		
		Scanner scanner=new Scanner(System.in);
		System.out.print("조회할 회원Id를 입력 하세요.>(pcwk01)");
		String inputData = scanner.nextLine().trim();
		log.debug("1. inputData:{}",inputData);
		param.setMemberId(inputData);
		log.debug("2. param:{}",param);
		
		flag = dao.doDelete(param);
		log.debug("3. flag:{}",flag
				);
		return flag;
	}
	
	/**
	 * 회원 단건 조회
	 * @return MemberVO
	 */
	public MemberVO doSelectOne() {
		MemberVO outVO = null;//조회 결과
		MemberVO param =new MemberVO();//조회 파라메터:memberId
		
		//1.Scanner
		//2.조회 사용자ID 입력
		//3.dao.doSave(param);		
		
		Scanner scanner=new Scanner(System.in);
		System.out.print("조회할 회원Id를 입력 하세요.>(pcwk01)");
		String inputData = scanner.nextLine().trim();
		log.debug("1. inputData:{}",inputData);
		
		param.setMemberId(inputData);
		log.debug("2. param:{}",param);
		
		outVO = dao.doSelectOne(param);
		log.debug("3. outVO:{}",outVO);
		
		
		return outVO;
	}
	/**
	 * 회원 가입
	 * @return 1(성공)/0(실패)
	 */
	public int doSave() {
		int flag = 0;
		MemberVO param=new MemberVO();
		
		//1.Scanner
		//2.등록사용자 정보
		//3.dao.doSave(param);
		
		Scanner scanner=new Scanner(System.in);
		System.out.printf("가입할 회원 정보를 입력 하세요.>"
				+ "\n(pcwk01,이상무01,4321,jamesol@paran.com,1,0,2024/10/17 14:33:00,일반)");
		
		String inputData = scanner.nextLine().trim();
		log.debug("1. inputData:{}",inputData);
		//회원정보를 받아, MemberVO변환
		param = dao.stringToMember(inputData);
		log.debug("2. param:{}",param);
		
		//dao호출
		flag = dao.doSave(param);
		log.debug("3. flag:{}",flag);
		return flag;
	}
	
	public int readFile(String path) {
		return dao.readFile(path);
	}

	public void doSelectList() {
		// TODO Auto-generated method stub
		
	}
}
