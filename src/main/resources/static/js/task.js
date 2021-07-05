/**
 * 
 */
/*
function checkTask() {
            var comment = document.getElementById("comment").value
            var limitday = document.getElementById("limitday").value
            if (postNumber == '' || postNumber.length != 7) {
                alert('郵便番号が正しくありません')
            }
        }
*/
function deleteTask(){
	if(window.confirm('完了したタスクを削除します。よろしいですか？')){
		return true;
	}else{
		return false;
	}
}