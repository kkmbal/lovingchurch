package lc.common.board.service;

import java.util.List;

import lc.common.board.domain.Board;
import lc.common.board.domain.BoardFile;

public interface BoardService {
	public List<Board> listBoard(Board board) throws Exception;
	public Board getBoard(Board board) throws Exception;
	public void insertBoard(Board board) throws Exception;
	public void updateBoard(Board board) throws Exception;
	public void replyBoard(Board board) throws Exception;
	public void deleteBoard(Board board) throws Exception;
	public List<BoardFile> listBoardFile(BoardFile boardFile) throws Exception;
	public BoardFile getBoardFile(BoardFile boardFile) throws Exception;
	public Board getBoardPrev(Board board) throws Exception;
	public Board getBoardNext(Board board) throws Exception;
	public List<Board> listMainBoard(Board board) throws Exception;
	public void deleteMulti(Board board) throws Exception;
}
