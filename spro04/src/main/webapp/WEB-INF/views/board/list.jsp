<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>

<!-- 

댓글 처리를 위한 테이블 설계
CREATE TABLE reply (
	rno number(10, 0),
	bno number(10, 0) NOT NULL,
	reply varchar2(1000) NOT NULL,
	replyer varchar2(50) NOT NULL,
	replyDate DATE DEFAULT sysdate,
	updateDate DATE DEFAULT sysdate
);

CREATE SEQUENCE seq_reply;

-- 식별키(pk) 지정
ALTER TABLE reply ADD CONSTRAINT pk_reply PRIMARY KEY(rno);

-- 왜래키(pk) 지정
ALTER TABLE reply ADD CONSTRAINT fk_reply_board FOREIGN KEY(bno) REFERENCES board(bno);
 -->
 
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">게시판</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				스프링 게시판
				<button id='regBtn' type="button" class="btn btn-xs pull-right">게시글
					등록</button>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-12">
						<form class="form-inline" id="searchForm" action="/board/list" method="GET">
							<select name="type" class="form-control form-select-lg mb-3" aria-label="Default select example">
								<option value="">검색 종류</option>
								<option value="T"
								<c:out value="${pageMaker.cri.type eq 'T'  ? 'selected' : '' }"/>
								>제목</option>
								<option value="C"
								<c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : '' }"/>
								>내용</option>
								<option value="W"
								<c:out value="${pageMaker.cri.type eq 'W' ? 'selected' : ''  }"/>
								>글쓴이</option>
								<option value="TC"
								<c:out value="${pageMaker.cri.type eq 'TC' ? 'selected' : ''}"/>
								>제목 OR 내용</option>
								<option value="TW"
								<c:out value="${pageMaker.cri.type eq 'TW' ? 'selected' : ''}"/>
								>제목 OR 글쓴이</option>
								<option value="CW"
								<c:out value="${pageMaker.cri.type eq 'CW' ? 'selected' : ''} "/>
								>내용 OR 글쓴이</option>
								<option value="TCW"
								<c:out value="${pageMaker.cri.type eq 'TCW' ? 'selected' : ''}"/>
								>제목 OR 내용 OR 글쓴이</option>
							</select>
							<input name="keyword" class="form-control mr-sm-2" type="search"
								placeholder="검색조건"
								value="${pageMaker.cri.keyword }"
								>
							
							<input name="pageNum" value="${pageMaker.cri.pageNum}">
							<input name="amount" value="${pageMaker.cri.amount}">
							<button class="btn btn-outline-success my-2 my-sm-0"
								>검색조건</button>
						</form>
					</div>
				</div>
				<table width="100%"
					class="table table-striped table-bordered table-hover">
					<!-- id="dataTables-example"> -->
					<thead>
						<tr>
							<th>글번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>수정일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list }" var="board">
							<tr>
								<td><c:out value="${board.bno }"></c:out></td>
								<td><a href="/board/get?bno=${board.bno }"> <c:out
											value="${board.title }"/>
									<b>[${board.replyCnt}]</b></a></td>
								<td><c:out value="${board.writer }"></c:out></td>
								<td class="center"><fmt:formatDate
										value="${board.regDate }" pattern="yyyy-MM-dd" /></td>
								<td class="center"><fmt:formatDate
										value="${board.updateDate }" pattern="yyyy-MM-dd" /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
				<!-- /.table-responsive -->


				<div class="row">
					<div class="pull-right">
						<ul class="pagination">
							<c:if test="${pageMaker.prev}">
								<li class="paginate_button previous"><a
									href="${pageMaker.startPage - 1}">Previous</a></li>
							</c:if>
							<c:forEach var="num" begin="${pageMaker.startPage}"
								end="${pageMaker.endPage}">
								<li
									class='paginate_button ${pageMaker.cri.pageNum == num ? "active" : ""}'>
									<a href="${num}">${num}</a>
							</c:forEach>
							<c:if test="${pageMaker.next}">
								<li class="paginate_button next"><a
									href="${pageMaker.endPage + 1}">Next</a></li>
							</c:if>
						</ul>
					</div>
				</div>
				
				<form id="actionForm" action="/board/list" method="GET">
					<input type="hidden" name="pageNum"
						value="${pageMaker.cri.pageNum }"> <input type="hidden"
						name="amount" value="${pageMaker.cri.amount }"> <input
						type="hidden" name="keyword" value="${pageMaker.cri.keyword }"> <input
						type="hidden" name="type" value="${pageMaker.cri.type }">
				</form>

				<!-- Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">???</h4>
							</div>
							<div class="modal-body">처리가 완료되었습니다.</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary">Save
									changes</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>
				<!-- /.modal -->

			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				const result = '<c:out value="${result}" />';

				checkModal(result);

				console.log(history);
				history.replaceState({}, null, null);

				function checkModal(result) {
					console.log("result =" + result);
					if (result === '' || history.state)
						return;
					if (parseInt(result) > 0) {
						$(".modal-body").html(
								"게시글 " + parseInt(result) + "번이 등록되었습니다.");
					}
					$("#myModal").modal("show");
				}
			});

	$("#regBtn").on("click", function() {
		self.location = "/board/register";
	});

	const actionForm = $("#actionForm"); // 해당컴포넌트 가져오기
	$(".paginate_button a").on("click", function(e) {
		e.preventDefault();
		console.log("---------click-----------");
		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		actionForm.submit();
	});
	
	const searchForm = $("#searchForm");
	$("#searchForm button").on("click", function(e) {
		console.log("searchForm button click!!!");
		if(!searchForm.find("option:selected").val()) {
			alert("검색 키워드를 선택하세요.");
			return false;
		}
		
		if(!searchForm.find("input[name='keyword']").val()) {
			alert("검색 키워드를 입력하세요.");
			return false;
		}
		
		searchForm.find("input[name='pageNum']").val("1")
		searchForm.submit();
	})
</script>

<%@include file="../includes/footer.jsp"%>
