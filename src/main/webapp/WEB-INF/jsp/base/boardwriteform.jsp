<%@ page contentType="text/html; charset=EUC-KR"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
	const id='${sessionScope.empCode}';
	console.log(id);
</script>
</head>
<body>
	<div>
		<form id="writeForm" name="writeForm" method="post">
			<div>
				<h2>글쓰기</h2>
				<div>
					<table>
						<tr>
							<th>제목</th>
							<td><input style="width: 1000px" type="text" id="title"
								name="title" /></td>
						</tr>
						<tr>
							<th>작성자</th>
							<td><div id="writer">
							 ${sessionScope.empCode} </div></td>


						</tr>
						<tr>
							<th>내용</th>
							<td><textarea style="width: 1000px" rows="20" cols="20"
									id="content" name="content"></textarea></td>
						</tr>

					</table>
					<div>

						<a href='#' class="btn btn-primary" onClick='fn_addtoBoard()'>글
							등록</a>
							 <a href='#' class="btn btn-primary"
							  onClick="location.href='${pageContext.request.contextPath}/base/board'">목록</a>
					</div>
				</div>
			</div>
		</form>
		<script>
			//글쓰기
			function mm() {

				var form = document.getElementById("writeForm");

				form.action = "${pageContext.request.contextPath}/base/boardreg";
				form.submit();

			}

			//목록
			function fn_cancel() {
				form.action = "${pageContext.request.contextPath}/base/board";
				form.submit();

			}
		</script>
	</div>
</body>
</html>
