package com.qna;

import java.sql.Connection;
import java.util.List;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertQna(QnaDTO dto, String mode) {
		return 0;
	}
	
	public int updateOrderNo(int groupNum, int orderNo) {
		return orderNo;
	}
	
	public int dataCount() {
		return 0;
	}
	
	public int dataCount(String condition, String keyword) {
		return 0;
	}
	
	public List<QnaDTO> listQna(int offset, int rows) {
		return null;
	}
	
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword) {
		return null;
	}
	
	public QnaDTO readQna(int qnaNum) {
		return null;
	}
	
	// 이전글
	public QnaDTO preReadQna(int groupNum, int orderNo, String condition, String keyword) {
		return null;
	}
	
	// 다음글
	public QnaDTO nextReadQna(int groupNum, int orderNo, String Condition, String keyword) {
		return null;
	}
	
	public int updateHitCount(int qnaNum) {
		return qnaNum;
	}
	
	public int updateqna(QnaDTO dto, String userId) {
		return 0;
	}
	
	public int delete(int qnaNum) {
		return 0;
	}
}
