<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>계정과목</title>

	<script
			src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
	<link rel="stylesheet"
		  href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
	<link rel="stylesheet"
		  href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">

	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<style>
		.ag-header-cell-label {
			justify-content: center;
		} /*글자 밑에 있는거 중앙으로  */
		.ag-row .ag-cell {
			display: flex;
			justify-content: center !important; /* align horizontal */
			align-items: center !important;
		}

		.ag-theme-balham .ag-cell, .ag-icon .ag-icon-tree-closed::before {
			line-height: 15px !important;
		}

		.ag-group-contracted {
			height: 15px !important;
		}

		.ag-theme-balham .ag-icon-previous:before {
			content: "\f125" !important;
		}

		.ag-theme-balham .ag-icon-next:before {
			content: "\f11f" !important;
		}

		.ag-theme-balham .ag-icon-first:before {
			content: "\f115" !important;
		}

		.ag-theme-balham .ag-icon-last:before {
			content: "\f118" !important;
		}
	</style>
	<script>
		$(document).ready(function() {

			createAccount();
			showAccount();

		});
		var selectedRow;

		/* 게시글 ag-grid에 뿌리는 로직임다 */
		function createAccount() {
			rowData = [];
			var columnDefs = [ {
				headerName : "글 번호",
				field : "id",
				sort : "asc",
				width : 100
			}, {
				headerName : "제목",
				field : "title",
				width : 500,
				onCellClicked : function open() {
					$("#codeModal").modal('show');
				}
			},

				{
					headerName : "작성자",
					field : "writtenBy",
					width : 250
				}, {
					headerName : "작성 날짜",
					field : "writeDate",
					width : 250
				}, {
					headerName : "조회수",
					field : "lookup",
					width : 75
				} ];
			gridOptions = {
				columnDefs : columnDefs,
				rowSelection : 'single', //row는 하나만 선택 가능
				defaultColDef : {
					editable : false
				}, // 정의하지 않은 컬럼은 자동으로 설정
				onGridReady : function(event) {// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
					event.api.sizeColumnsToFit();
				},
				onGridSizeChanged : function(event) { // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
					event.api.sizeColumnsToFit();
				},
				onRowClicked : function(event) {
					console.log("Row선택");
					console.log(event.data);
					selectedRow = event.data;
					console.log(event.data.id);
					const id=event.data.id;
					showDetailBoard(id);

				}
			}
			accountGrid = document.querySelector('#accountGrid');
			new agGrid.Grid(accountGrid, gridOptions);
		}
		/* 게시판 리스트불러오는 함수임다 */
		function showAccount() {
			$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/base/boardlist",
				dataType : "json",
				data : [],
				success : function(jsonObj) {
					console.log(jsonObj);
					gridOptions.api.setRowData(jsonObj);
				}
			});
		}


		/* 게시물 상세보기함수임다~ */
		function showDetailBoard(id){
			$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/base/boardDetailList",
				dataType : "json",
				data : {"id" : id},
				success : function(jsonObj) {
					console.log(jsonObj);
					$("#title").attr("value",jsonObj[0].title);
					$("#id").attr("value",jsonObj[0].id);
					$("#lookup").attr("value",jsonObj[0].lookup);
					$("#writer").attr("value",jsonObj[0].writtenBy);
					$("#writtenday").text(jsonObj[0].writeDate);
					const textarea = document.querySelector("#textarea");
					textarea.value=jsonObj[0].contents;
				}
			});
		}
		/* 게시물 삭제하는 함수임다~ */
		function deleteBoard(id){
			var ans= confirm("삭제하시겠습니까?");
			var id = $('#id').val();
			console.log(" 삭제할 id 값@@@@@ :"+id  );
			if(ans==true){
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/base/boardDelete",
					dataType : "text",
					data : {"id" : id},
					success :function(data){
						alert("삭제가 완료되었습니다!");
						$("#codeModal").modal("hide");
						location.href="${pageContext.request.contextPath}/base/board";



					}
				});
			}
		}





	</script>
	<style>
		#header_board2 {
			display: inline;
		}

		#header_board3 #header_board3 {
			display: inline;
			margin-left: 500px;
		}
	</style>
</head>
<body class="bg-gradient-primary">
<!-- 게시판 위 버튼 -->
<h4 id="header_board2">게시판</h4>
<a href='${pageContext.request.contextPath}/base/boardwriteform'
   style="margin-left: 1084px;" class="btn btn-primary">글쓰기</a>
<a href='#' class="btn btn-primary">수정</a>
<a href='#' class="btn btn-primary">삭제</a>
<hr>
<div style="float: left; width: 100%; padding: 10px;">
	<div align="center">
		<div id="accountGrid" class=
				"ag-theme-balham"
			 style="height: 500px; width: 100%;"></div>
	</div>
</div>

<!--  게시판 상세보기 모달창이여요-->
<div align="center" class="modal fade" id="codeModal" tabindex="-1" role="dialog"
	 aria-labelledby="customerCodeModalGrid">
	<div class="modal-dialog" role="document">
		<div class="modal-content" style="width:700px;">
			<div class="modal-header">
				<h5 class="modal-title" id="customerCodeModalLabel">게시판</h5>
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">

				<table>

					<tr>
						<th>제목</th>
						<td colspan="2"><input style="width: 500px" type="text" id="title" readonly/></td>
					</tr>
					<tr>
						<th>글 번호</th>
						<td>
							<input style="width: 100px" type="text" id="id" name="id"
								   readonly/>
						</td>
						<td> 조회수:
							<input style="width: 100px" type="text" id="lookup"
								   readonly/>
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td><input style="width: 300x" type="text" id="writer"
								   readonly/></td>
						<td><div id="writtenday"></div></td>


					</tr>
					<tr>
						<th>내용</th>
						<td colspan="2">
								<textarea style="width: 500px" rows="20" cols="20"
										  id="textarea"  readonly>
								</textarea>
						</td>

					</tr>


				</table>
				<div>

					<a href='#' class="btn btn-primary" onClick='fn_addtoBoard()'>수정</a>
					<a href='#' class="btn btn-primary" onClick='deleteBoard()'>삭제</a>
				</div>



			</div>
		</div>
	</div>
</div>



</body>
</html>