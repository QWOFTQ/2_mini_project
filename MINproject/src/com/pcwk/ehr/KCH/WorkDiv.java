package com.pcwk.ehr.KCH;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;

public interface WorkDiv<T> {

	/**
	 * 등록
	 * @param vo
	 * @return 1(성공)/0(실패)
	 */
	int doSave(T param);

	/**
	 * 수정
	 * @param param
	 * @return 1(성공)/0(실패)
	 */
	int doUpdate(T param);

	/**
	 * 삭제
	 * @param param
	 * @return 1(성공)/0(실패)
	 */
	int doDelete(T param);

	/**
	 * 회원단건 조회
	 * @param param
	 * @return MemberVO
	 */
	T doSelectOne(T param);

	/**
	 * 회원단건 조회
	 * @param param
	 * @return MemberVO
	 */
	List<T> doRetrieve(DTO param);

	int writeFile(String path);

	int readFile(String path);


}
