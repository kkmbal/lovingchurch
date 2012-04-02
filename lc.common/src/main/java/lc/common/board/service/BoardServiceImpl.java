package lc.common.board.service;

import java.util.ArrayList;
import java.util.List;

import lc.common.board.domain.Board;
import lc.common.board.domain.BoardFile;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang.StringUtils;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
	/**
	 * [logging]
	 * 1.apache common logging : LogFactory 사용
	 * 2.log4j : Logger.getLogger 또는 LogManager.getLogger 사용(동일)
	 */
	private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired(required=true)
	private BoardMapper boardMapper;
	
	public List<Board> listBoard(Board board) throws Exception {
		List<Board> resultList	= new ArrayList<Board>();
		resultList = boardMapper.listBoard(board);
		int rowCount = boardMapper.getRowCount(board);
		for(Board b : resultList){
			b.setROWCOUNT(rowCount);
			BoardFile bf = new BoardFile();
			bf.setBOARD_CAT_CD(b.getBOARD_CAT_CD());
			bf.setBOARD_SEQ(b.getBOARD_SEQ());
			b.setBoardFile(boardMapper.listBoardFile(bf));
			if(b.getBoardFile() != null){
				b.setFILE_CNT(b.getBoardFile().size());
			}
		} 
		return resultList;
	}
	
	public Board getBoard(Board board) throws Exception {
		Board rtnBoard = boardMapper.getBoard(board);
		
		if(rtnBoard != null){
			if(!rtnBoard.getCRE_ID().equals(board.getUPD_ID())){
				boardMapper.updateHitCnt(board);
			}
		}
		return rtnBoard;
	}
	
	public Board getBoardPrev(Board board) throws Exception {
		return boardMapper.getBoardPrev(board);
	}
	
	public Board getBoardNext(Board board) throws Exception {
		return boardMapper.getBoardNext(board);
	}

	public void insertBoard(Board board) throws Exception {
		//채번
		String seq = boardMapper.getMaxSeq(board);
		board.setBOARD_SEQ(seq);
		board.setREF(seq);
		board.setRESTEP(0);
		board.setRELEVEL(0);
		//신규저장
		boardMapper.insertBoard(board);
		
		if(board.getBoardFile() != null){
			for(BoardFile f : board.getBoardFile()){
				if(f != null){
					f.setBOARD_CAT_CD(board.getBOARD_CAT_CD());
					f.setBOARD_SEQ(seq);
					String fileSeq = boardMapper.getMaxFileSeq(f);
					f.setFILE_SEQ(fileSeq);
					f.setCRE_ID(board.getUPD_ID());
					f.setUPD_ID(board.getUPD_ID());
					//File저장
					boardMapper.insertBoardFile(f);
				}
			}
		}
		
	}
	
	public void updateBoard(Board board) throws Exception {
		boardMapper.updateBoard(board);
		
		if(board.getBoardFile() != null){
			//해당게시물 모든 파일 삭제
			BoardFile bf = new BoardFile();
			bf.setBOARD_CAT_CD(board.getBOARD_CAT_CD());
			bf.setBOARD_SEQ(board.getBOARD_SEQ());
			List<BoardFile> listBoardFile = boardMapper.listBoardFile(bf);
			for(BoardFile f2 : listBoardFile){
				boardMapper.deleteBoardFile(f2);
			}
		
			for(BoardFile f : board.getBoardFile()){
				if(f != null){
					f.setBOARD_CAT_CD(board.getBOARD_CAT_CD());
					f.setBOARD_SEQ(board.getBOARD_SEQ());
					
					String fileSeq = boardMapper.getMaxFileSeq(f);
					f.setFILE_SEQ(fileSeq);
					logger.debug("filesize:"+f.getFILE_CONTENT().length);
					//File저장
					boardMapper.insertBoardFile(f);
				}
			}
		}
	}

	public void replyBoard(Board board) throws Exception {
		//부모 게시물 조회
		Board resultBoard = boardMapper.getBoard(board);
		
		//기존 글 RESTEP UPDATE
		board.setREF(resultBoard.getREF());
		board.setRESTEP(resultBoard.getRESTEP());
		boardMapper.updateForReplyBoard(board);
		
		//채번
		String seq = boardMapper.getMaxSeq(board);
		
		//답글저장
		board.setBOARD_SEQ(seq);
		board.setRESTEP(resultBoard.getRESTEP() + 1);
		board.setRELEVEL(resultBoard.getRELEVEL() + 1);
		boardMapper.insertBoard(board);
	}

	public void deleteBoard(Board board) throws Exception {
		boardMapper.deleteBoard(board);
		
		//해당게시물 모든 파일 삭제
		BoardFile bf = new BoardFile();
		bf.setBOARD_CAT_CD(board.getBOARD_CAT_CD());
		bf.setBOARD_SEQ(board.getBOARD_SEQ());
		List<BoardFile> listBoardFile = boardMapper.listBoardFile(bf);
		for(BoardFile f2 : listBoardFile){
			boardMapper.deleteBoardFile(f2);
		}
	}

	public List<BoardFile> listBoardFile(BoardFile boardFile) throws Exception {
		List<BoardFile> resultList	= new ArrayList<BoardFile>();
		resultList = boardMapper.listBoardFile(boardFile);
		return resultList;
	}

	public BoardFile getBoardFile(BoardFile boardFile) throws Exception {
		return boardMapper.getBoardFile(boardFile);
	}

	public List<Board> listMainBoard(Board board) throws Exception {
		return boardMapper.listMainBoard(board);
	}

	public void deleteMulti(Board board) throws Exception {
		if(!StringUtils.isEmpty(board.getBOARD_SEQ_DEL())){
			String[] BOARD_SEQ = board.getBOARD_SEQ_DEL().split(",");
			for(String seq : BOARD_SEQ){
				if(seq != null && seq.length() > 0){
					board.setBOARD_SEQ(seq);
					deleteBoard(board);
				}
			}
		}
	}

}
