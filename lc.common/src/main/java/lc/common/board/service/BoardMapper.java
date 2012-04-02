package lc.common.board.service;

import java.util.List;

import lc.common.board.domain.Board;
import lc.common.board.domain.BoardFile;

public interface BoardMapper {
	public List<Board> listBoard(Board board);
	
	public int getRowCount(Board board);
	
	public Board getBoard(Board board);

	public void insertBoard(Board board);

	public void updateBoard(Board board);
	
	public void updateForReplyBoard(Board board);
	
	public void deleteBoard(Board board);

	public String getMaxSeq(Board board);

	public void updateHitCnt(Board board);

	public String getMaxFileSeq(BoardFile boardFile);

	public void insertBoardFile(BoardFile boardFile);
	public void updateBoardFile(BoardFile boardFile);
	public void deleteBoardFile(BoardFile boardFile);

	public List<BoardFile> listBoardFile(BoardFile boardFile);

	public BoardFile getBoardFile(BoardFile boardFile);
	
	public Board getBoardPrev(Board board);
	
	public Board getBoardNext(Board board);
	
	public List<Board> listMainBoard(Board board);

}
