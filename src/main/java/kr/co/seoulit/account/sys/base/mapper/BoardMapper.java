package kr.co.seoulit.account.sys.base.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.account.sys.base.to.BoardBean;
@Mapper
public interface BoardMapper {

	ArrayList<BoardBean> selectParentBoardList();

	ArrayList<BoardBean> selectDetailBoardList(String id);

	void deleteBoardList(String id);

	void updateLookup(String id);

	void insertBoard(BoardBean boardbean);

	void boardModify(BoardBean boardbean);

	ArrayList<BoardBean> selectreplyList(String id);

	void insertReBoard(BoardBean boardbean);

	void deleteReBoard(String rid);

	void modifyReBoard(BoardBean boardbean);

}
