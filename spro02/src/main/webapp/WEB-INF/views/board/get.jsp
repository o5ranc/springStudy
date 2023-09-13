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
            
<%@include file="../includes/footer.jsp"%>