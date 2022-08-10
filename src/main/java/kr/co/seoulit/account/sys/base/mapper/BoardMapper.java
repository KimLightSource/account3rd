package kr.co.seoulit.account.sys.base.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.account.sys.base.to.BoardBean;
@Mapper
public interface BoardMapper {

	ArrayList<BoardBean> selectParentBoardList();

	ArrayList<BoardBean> selectDetailBoardList(String id);

	void deleteBoardList(String id);

	void updateLookup(String id);

}