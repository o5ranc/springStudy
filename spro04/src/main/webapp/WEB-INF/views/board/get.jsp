<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                        <div class="panel-heading">
                            게시글 상세보기
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-8">
                                    

                                        <div class="form-group">
                                            <label>글번호</label>
                                            <input class="form-control" placeholder="Enter text" name="bno"
                                            value = "${board.bno }" readonly="readonly">
                                        </div>
                                        <div class="form-group">
                                            <label>제목</label>
                                            <input class="form-control" placeholder="Enter text" name="title"
                                            value = "${board.title }" readonly="readonly">
                                        </div>
                                        <div class="form-group">
                                            <label>내용</label>
                                            <textarea class="form-control" rows="5" name="content" readonly="readonly"
                                            >${board.content }</textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>글쓴이</label>
                                            <input class="form-control" placeholder="Enter text" name="writer"
                                            value = "${board.writer }"
                                            readonly="readonly">
                                        </div>
                                        
                                        <button data-oper="modify" class="btn btn-default"
                                        onclick="location.href='/board/modify?bno=${board.bno}'">수정</button>
                                        <button data-oper="list" class="btn btn-default"
                                        onclick="location.href='/board/list'">목록</button>
                                    
                                </div>
                                <!-- /.col-lg-6 (nested) -->
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

<script type="text/javascript" src="/resources/js/reply.js">

</script>     
       
<script type="text/javascript">
$(document).ready(function() {
	console.log("========================");
	console.log("======== JS TEST =======");
	
	const bnoValue = '<c:out value="${board.bno}"/>';
	
	replyService.add(
		{reply:"JS TEST", replyer: "tester", bno:bnoValue},
		function(result) {
			//alert("RESULT : " + result);
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
	
	replyService.remove(39, function(count) {
        console.log("remove : " + count);

        if(count==="success") {
            alert("removed");
        }
    }, function(err){
        alert("remove error");
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