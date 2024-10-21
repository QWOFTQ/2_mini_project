package com.pcwk.ehr.admin;

public interface MovieDiv<T> {
    int doSave(T param);
    int doUpdate(T param);
    int doDelete(T param);
    T doSelectByTitle(String title);
    int readFile(String path);
    int writeFile(String path);
	int doUpdate(MovieVO param, String timeInput);
	int doSelectAll(MovieVO param);
	MovieVO doSelectAll(String title);
}
