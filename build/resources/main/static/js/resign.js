    // 회원 탈퇴
    function confirmFunction(){

    let con=document.getElementById("confirm").value;
    let checkResult = document.getElementById('confirmOut');
    if(con==="POLAROID 탈퇴하기") {
    confirmForm.submit();
    }
    else{
    checkResult.innerText="다시입력하세요"
    checkResult.style.color="red"
    }

}
