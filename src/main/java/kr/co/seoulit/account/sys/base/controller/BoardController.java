package kr.co.seoulit.account.sys.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.co.seoulit.account.sys.base.service.BaseService;
import kr.co.seoulit.account.sys.base.to.BoardBean;

@RestController
@RequestMapping("/base")
public class BoardController {
	@Autowired
	private BaseService baseService;

	@GetMapping("/boardlist")
	public ArrayList<BoardBean> findParentboardList() {
		System.out.println("컨트롤러 호출@@@@@@@@@@@@@@");
		return baseService.findParentboardList();
	}

	@GetMapping("/boardDetailList")
	public ArrayList<BoardBean> findDetailBoardList(@RequestParam String id) {
		baseService.updateLookup(id);


		return baseService.findDetailboardList(id);
	}
	@GetMapping("/boardreplyList")
	public ArrayList<BoardBean> boardRelpyList(@RequestParam String id) {

		ArrayList<BoardBean> replybean = baseService.showreply(id);
		System.out.println(replybean+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		return   replybean;
	}
	@GetMapping("/boardDelete")
	public void deleteBoard(@RequestParam String id) {
		System.out.println("컨트롤러는왓다2222222222222222222222222222");
		baseService.deleteBoard(id);
	}

	@PostMapping("/boardreg")
	public ModelAndView insertData(
			@RequestParam("title") String title
			,@RequestParam("writtenBy") String writtenBy
			,@RequestParam("contents")String contents,
			MultipartHttpServletRequest multipartHttpServletRequest)throws Exception {

		ModelAndView mav=new ModelAndView("redirect:/base/board");
		BoardBean boardbean = new BoardBean();
		boardbean.setTitle(title);
		boardbean.setWrittenBy(writtenBy);
		boardbean.setContents(contents);
		System.out.println("작성자:"+writtenBy+"@@@제목:"+title+"@@@내용 :"+contents+"@@@@@@@@@@@@@");
		baseService.insertBoard(boardbean);
		return mav;
	}

	@PostMapping("/boardModify")
	public ModelAndView boardModify(
			@RequestParam("id")String id
			,@RequestParam("title") String title
			,@RequestParam("contents")String contents) {

		ModelAndView mav=new ModelAndView("redirect:/base/board");
		BoardBean boardbean = new BoardBean();
		boardbean.setTitle(title);
		boardbean.setId(id);
		boardbean.setContents(contents);
		System.out.println("@@글번호"+id+"@@@제목:"+title+"@@@내용 :"+contents+"@@@@@@@@@@@@@");
		baseService.boardModify(boardbean);
		return mav;
	}

	@PostMapping("/board_re_insert")
	public Map<String, Object> insertreply(
			@RequestParam("reply") String reply
			,@RequestParam("id") String id
			,@RequestParam("writer")String writer) {
		HashMap<String, Object> map = new HashMap<>();
		BoardBean boardbean = new BoardBean();
		boardbean.setBid(id);
		boardbean.setReContents(reply);
		boardbean.setReWritter(writer);
		map.put("Msg","성공ㅎㅎ");
//		   SYSTEM.OUT.PRINTLN("작성자:"+WRITTENBY+"@@@제목:"+TITLE+"@@@내용 :"+CONTENTS+"@@@@@@@@@@@@@");
		baseService.insertReBoard(boardbean);
		return map;
	}

	@GetMapping("replyDelete")
	public void redeleteBoard(@RequestParam String rid) {
		baseService.deletereBoard(rid);

	}
	@PostMapping("/board_re_modify")
	public void boardModify(
			@RequestParam("rid")String id
			,@RequestParam("recontents")String contents) {

		BoardBean boardbean = new BoardBean();
		boardbean.setRid(id);
		boardbean.setReContents(contents);
		System.out.println(id+contents+"@@@@@@@@@");
		baseService.boardReModify(boardbean);

	}




}
