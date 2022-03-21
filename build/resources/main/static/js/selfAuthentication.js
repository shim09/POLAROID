// 인증코드 발송
function submitMail() {
    const checkResult=document.getElementById("output");
    $.ajax({
        type: 'post',
        url: "submitCode",
        success: function (result) {
            checkResult.style.color = 'green';
            checkResult.innerText='인증코드 발송 메일을 확인해주세요';
        },
        error: function () {
            checkResult.style.color = 'red';
            checkResult.innerText='인증코드 발송 실패';
        }
    });
}

// 인증코드 체크
function codeCheck(){
    const code = $("#code").val();
    const checkResult=document.getElementById("output");
    if (code.length==0){
        checkResult.style.color = 'red';
        checkResult.innerText='인증코드를 입력하세요';
    }
    else {
        $.ajax({
            type: 'post',
            url: "codeCheck",
            data: {"code": code},
            success: function (result) {
                if (result == "ok") {
                    checkResult.style.color = 'green';
                    $("#memberCheckmail").attr("readonly",true);
                    checkResult.innerText = 'GOOD';
                } else {
                    checkResult.style.color = 'red';
                    checkResult.innerText = '인증코드 불일치';
                }
            },
            error: function () {
                console.log("코드확인 오류발생")
            }
        });
    }
}

function joinSubmit(){

    codeCheck();

    const status=document.getElementById("output").innerText

    if(status=='GOOD')
        authenticationForm.submit();
    else {

    }
}