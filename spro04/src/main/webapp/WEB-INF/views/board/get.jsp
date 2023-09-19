<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">게시글 상세보기</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">게시글 상세보기</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-8">
						<div class="form-group">
							<label>글번호</label> <input class="form-control"
								placeholder="Enter text" name="bno" value="${board.bno }"
								readonly="readonly">
						</div>
						<div class="form-group">
							<label>제목</label> <input class="form-control"
								placeholder="Enter text" name="title" value="${board.title }"
								readonly="readonly">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="5" name="content"
								readonly="readonly">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>글쓴이</label> <input class="form-control"
								placeholder="Enter text" name="writer" value="${board.writer }"
								readonly="readonly">
						</div>

						<button data-oper="modify" class="btn btn-default"
							onclick="location.href='/board/modify?bno=${board.bno}'">수정</button>
						<button data-oper="list" class="btn btn-default"
							onclick="location.href='/board/list'">목록</button>

					</div>
				</div>
				<!-- /.row (nested) -->
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title" id="myModalLabel">댓글 등록</h4>
            </div>
			<div class="modal-body">
				<div class="form-group">
					<label>댓글내용</label>
					<input class="form-control" name="reply" value="New Reply!!"/>
				</div>
				<div class="form-group">
					<label>작성자</label>
					<input class="form-control" name="replyer" value="replyer"/>
				</div>
				<div class="form-group">
					<label>작성일자</label>
					<input class="form-control" name="replyDate" value="replyDate"/>
				</div>
			</div>
			<div class="modal-footer">
				<button id="modalModBtn" type="button" class="btn btn-warning"
					data-dismiss="modal">수정</button>
				<button id="modalRemoveBtn" type="button" class="btn btn-danger"
					data-dismiss="modal">삭제</button>
				<button id="modalRegisterBtn" type="button" class="btn btn-primary"
					data-dismiss="modal">등록</button>
				<button id="modalCloseBtn" type="reset" class="btn btn-default">
				닫기</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 덧글 실제 표시 부분 -->
<div class="row">
	<div class="col-lg-12 pt-5">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-commencts fa-fw"></i>댓글
				<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">댓글추가</button>
			</div>
			<div class="panel-body">
				<ul class="chat">
					<div>
						<div class="header">
							<strong class="primary-font">user00</strong>
							<small class="pull-right text-muted"></small>
						</div>
						<p>댓글 내용 들어갈예정.</p>
					</div>
				</ul>
				
			</div>
			<div class="panel-footer" style="height: 180px">
					
				</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="/resources/js/reply.js"></script>

<script>
$(document).ready(function() {
	const bnoValue = '<c:out value="${board.bno}"/>';
	const replyUL = $(".chat");
	
	showList(1);
	
	function showList(page) {
		replyService.getList({bno: bnoValue, page: page || 1}, function(replyCnt, list) {
			
			console.log("showList Fnc replyCnt : ", replyCnt);
			console.log("showList Fnc list : ", list);
			
			if (page == -1) {
				// 등록 완료 후 페이지 찾아서 호출 가능하게(등록되고나서 등록된 마지막 페이지로 이동해주려고)
				pageNum = Math.ceil(replyCnt/10.0);
				showList(pageNum);
				return;
			}
			
			let str = "";
			console.log("Called showList???");
			if(list == null || list.length == 0) {
				replyUL.html("");
				return;
			} 
			
			for(let i = 0, len = list.length || 0; i < len; i++) {
				str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>";
				str += "<div><div class='header'><strong class='primary-font'>" + list[i].replyer + "</strong>";
				str += "<small class='pull-right text-muted'>" + replyService.displayTime(list[i].replyDate) + "</small></div>";
				str += "<p>" + list[i].reply + "</p></div></li>";
				
				// """ 내용 """; 선언의 사용이 가능한가?
			}
			
			replyUL.html(str);
			showReplyPage(replyCnt);
		})
	}
	
	let pageNum = 1;
	let replyPageFooter = $(".panel-footer");
	//showReplyPage(132);
	
	function showReplyPage(replyCnt) {
		let endNum = Math.ceil(pageNum / 10.0) * 10;
		let startNum = endNum - 9;
		
		const prev = startNum != 1;
		let next = false;
		
		if(endNum * 10 >= replyCnt) {
			endNum = Math.ceil(replyCnt / 10.0);
		}
		
		if(endNum * 10 < replyCnt) {
			next = true;
		}
		
		let str = "<ul class='pagination pull-right'>";
		
		if(prev) {
			str += "<li class='page-item'><a class='page-link' href='" + (startNum - 1) + "'> 이전</a></li>";
		}
		
		for(let i = startNum; i < endNum; i++) {
			const active = (pageNum == i? "active" : "");
			str += "<li class='page-item " + active + "'><a class='page-link' href='" + i + "'>"
				+ i + "</a></li>";
		}
		
		if(next) {
			str += "<li class='page-item'><a class='page-link' href='" + (endNum + 1) + "'> 다음</a></li>"
		}
		
		str += "</ul>";
		console.log(str);
		replyPageFooter.html(str);
	}
 	
	// 댓글 모달 처리
	const modal = $("#myModal");
	const modalInputReply = modal.find("input[name='reply']");
	const modalInputReplyer = modal.find("input[name='replyer']");
	const modalInputReplyDate = modal.find("input[name='replyDate']");
	
	const modalModBtn = $("#modalModBtn");
	const modalRemoveBtn = $("#modalRemoveBtn");
	const modalRegisterBtn = $("#modalRegisterBtn");
	
	$("#addReplyBtn").on("click", function(e) {
		// 새로운 덧글 등록이므로 초기화 및 가려줄 부분 정리 선행
		modal.find("input").val(""); // 모달 내용 초기화
		modalInputReplyDate.closest("div").hide(); // 가장 가까운 (div인?)부모 찾기
		modal.find("button[id != 'modalCloseBtn']").hide();
		modalInputReplyer.attr("readonly", false);	
		modalRegisterBtn.show();
		// 정리후 모달 띄우기
		$(".modal").modal("show");
	});
	
	$(".chat").on("click", "li", function(e) {
		const rno = $(this).data("rno");
		console.log("chat... rno : ", rno);
		
		replyService.get(rno, function(reply) {
			modalInputReply.val(reply.reply);	
			modalInputReplyer.val(reply.replyer).attr("readonly", "readonly");	
			modalInputReplyDate.val(replyService.displayTime(reply.replyDate))
				.attr("readonly", "readonly");	
			modal.data("rno", reply.rno);
			modal.find("button[id != 'modalCloseBtn']").hide();
			modalInputReplyDate.closest("div").show();
			modalModBtn.show();
			modalRemoveBtn.show();
			
			$(".modal").modal("show");
		})
	})
	
	modalRegisterBtn.on("click", function(e) {
		const reply = {
			reply: modalInputReply.val(),
			replyer: modalInputReplyer.val(),
			bno: bnoValue
		}
		
		replyService.add(reply, function(result) {
			alert("in Modal! " + result);
			modal.find("input").val("");
			modal.modal("hide");
			
			// 위에서 등록완료 후 화면 새로고침을 위해 작성한 부분
			// await - async를 사용하면 비동기 처리 가능
			showList(1);
		})
	})
	
	modalModBtn.on("click", function(e) {
		const reply = {
			rno: modal.data("rno"), 
			reply: modalInputReply.val()
		};
		
		replyService.update(reply, function(result) {
			alert("in Modal! " + result);
			modal.modal("hide");
			showList(1);
		})
	})
	
	modalRemoveBtn.on("click", function(e) {
		const rno = madal.data("rno");
		
		replyService.remove(rno, function(result) {
			alert(result);
			modal.modal("hide");
			showList(1);
		})
	})
});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		/*
		테스트용으로 만든 코드라 주석 처리
		
		console.log("========================");
		console.log("======== JS TEST =======");

		const bnoValue = '<c:out value="${board.bno}"/>';

		replyService.get(16, function(result) {
			console.log("GET ONE RESULT1 : ");
			alert("GET ONE RESULT2 : " + result);
		}, function(err) {
			console.log("GET ONE err : ");
			alert("get error" + err);
		});

		
		replyService.add(
			{reply:"JS TEST", replyer: "tester", bno:bnoValue},
			function(result) {
				alert("RESULT : " + result);
			}
		)
		
		// http://localhost:8093/reply/pages/124/1.json 를 주소표시줄에 입력하면 XML
		// 형태로 결과를 웹화면에 보여줌. 
		replyService.getList({bno: bnoValue, page:1},
			function(list) {
				for(let i = 0, len = list.length||0; i < len; i++) {
					console.log(list[i]);
				}
			}
		);
		
		replyService.get(16, function(result) {
			console.log("GET ONE RESULT1 : ");
			alert("GET ONE RESULT2 : " + result);
		}, function(err) {
			console.log("GET ONE err : ");
			 alert("get error" + err);
		});
		
		replyService.remove(17, function(count) {
		    console.log("remove : " + count);

		    if(count==="success") {
		        alert("removed");
		    }
		}, function(err){
		    alert("remove error" + err);
		});
		
		replyService.update({
			rno: 30,
			bno: bnoValue,
			reply: "update from get.jsp"
			}, function(result) {
				alert("수정 완료!")
			}
		)
		 
		console.log(replyService);
		*/

		const operform = $("#operform");

		$("button[data-oper='modify']").on("click", function(e) {
			operform.attr("action", "/board/modify").submit();
		});

		$("button[data-oper='list']").on("click", function(e) {
			operform.find("#bno").remove();
			operform.attr("action", "/board/list");
			operform.submit();
		});
	});
</script>

<%@include file="../includes/footer.jsp"%>