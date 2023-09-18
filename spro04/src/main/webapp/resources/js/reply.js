/**
 * 
 */
 
 console.log("reply module...");
 
 const replyService = function() {
 	
 	function add(reply, callback, error) {
 		console.log(".......add reply");
 		
 		$.ajax({
 			type : 'POST',
 			url : '/reply/new',
 			data : JSON.stringify(reply),
 			contentType : "application/json; charset=UTF-8",
 			success : function(result, status, xhr) {
				if(callback) {
					callback(result);
				}
 			},
 			error : function(xhr, status, err) {
 				if(error) { error(err) }
 			}
 		});
 		
 		return {"add":add};
 	}
 	
 	return {name : "홍길동"};
 };