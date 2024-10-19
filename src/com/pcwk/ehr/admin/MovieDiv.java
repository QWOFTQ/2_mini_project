package com.pcwk.ehr.admin;

import java.util.List;

public interface MovieDiv<T> {

    /**
     * 파일 읽기
     * @param path
     * @return
     */
    int readFile(String path);

    /**
     * 파일 쓰기
     * @param path
     * @return
     */
    int writeFile(String path);

    /**
     * 영화 등록
     * @param param
     * @return 1(성공) / 0 (실패) 
     */
    int doSave(T param);

    /**
     * 영화 수정
     * @param param
     * @return 1(성공) / 0 (실패) 
     */
    int doUpdate(T param);

    /**
     * 영화 삭제
     * @param param
     * @return 1(성공) / 0 (실패) 
     */
    int doDelete(T param);

	/**
	 * 영화 조회
	 * 
	 * @param param
	 * @return 조회된 영화 리스트
	 */
    int doSelectAll(T param);
}
