package com.board;

import java.sql.Connection;
import java.util.List;

import com.util.DBConn;

public class BoardDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertBoard(BoardDTO dto, String mode) {
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
	
	public List<BoardDTO> listBoard(int offset, int rows) {
		return null;
	}
	
	public List<BoardDTO> listBoard(int offset, int rows, String condition, String keyword){
		return null;
	}
	
	public BoardDTO readBoard(int boardNum) {
		return null;
	}
	
	// 이전글
	public BoardDTO preReadBoard(int groupNum, int orderNo, String condition, String keyword) {
		return null;
	}
	
	// 다음글
	public BoardDTO nextReadBoard(int groupNum, int orderNo, String condition, String keyword) {
		return null;
	}
	
	public int updateHitCount(int boardNum) {
		return boardNum;
	}
	
	public int updateBoard(BoardDTO dto, String userId) {
		return 0;
	}
	
	public int deleteBoard(int boardNum) {
		return 0;
	}
}
