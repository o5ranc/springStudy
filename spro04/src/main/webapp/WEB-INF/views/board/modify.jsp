<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../includes/header.jsp"%>

            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">게시글 수정</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            게시글 수정
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-8">
                                    <form action="/board/modify" method="post" accept-charset="UTF-8">

                                        <div class="form-group">
                                            <label>글번호</label>
                                            <input class="form-control" placeholder="Enter text" 
                                            name="bno" value="${board.bno }" readonly="readonly">
                                        </div>
                                        <div class="form-group">
                                            <label>제목</label>
                                            <input class="form-control" placeholder="Enter text" 
                                            name="title" value="${board.title }">
                                        </div>
                                        <div class="form-group">
                                            <label>내용</label>
                                            <textarea class="form-control" rows="5" name="content">${board.content }</textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>글쓴이</label>
                                            <input class="form-control" placeholder="Enter text" 
                                            name="writer" value="${board.writer }" readonly="readonly">
                                        </div>
                                        <div class="form-group">
                                            <label>등록일자</label>
                                            <input class="form-control"
                                            name="regDate" value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regDate }"/>' readonly="readonly">
                                        </div>
                                        <div class="form-group">
                                            <label>수정일자</label>
                                            <input class="form-control"
                                            name="updateDate" value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate }"/>' readonly="readonly">
                                        </div>
                                        <button type="button" data-oper = "modify" class="btn btn-primary">작성</button>
                                        <button type="button" data-oper = "remove" class="btn btn-danger">삭제</button>
                                        <button type="button" data-oper = "list" class="btn btn-default">목록</button>
                                    </form>
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
            


<script type="text/javascript">
	$(document).ready(function() {
		const formObj = $("form");
		
		console.log("들어옴?");
		
		$('button').on('click', function(e) {
			e.preventDefault();
			const operation = $(this).data("oper");
			
			console.log("operation = " + operation);
			
			if(operation === "remove") {
				formObj.attr("action", "/board/remove")
					   .attr("method", "POST");
			} else if(operation === "modify") {
				formObj.attr("action", "/board/modify")
				       .attr("method", "POST");
			} else if(operation === "list") {
				formObj.attr("action", "/board/list")
				       .attr("method", "GET");
				
				const pageNumTag = $("input[name='pageNum']").clone();
				const amountTag = $("input[name='amount']").clone();
				const typeTag = $("input[name='type']").clone();
				const keywordTag = $("input[name='keyword']").clone();
				
				formObj.empty();
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				formObj.append(typeTag);
				formObj.append(keywordTag);
			}
			
			
			
			formObj.submit();
		});
	})
</script>
            
<%@include file="../includes/footer.jsp"%>