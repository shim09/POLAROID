// 사용자 (마이페이지, 로그아웃)
$(document).ready(function(){
    $('#userToggleButton').click(function(){
        $('#userToggleMenu').toggle();
    });
});

// 좋아요 누를 때 하트 색 바꾸기
let flagLike = true;
function likeCheck() {
    document.getElementById("board-detail-like-btn").src = flagLike
        ? "/image/like.png"
        : "/image/like_default.png";
    flagLike = !flagLike;
}

// board_detail 댓글 보기
let flagComment = true;
function commentCheck() {
    document.getElementById("board-detail-comment-btn").src = flagComment
        ? "/image/comment.png"
        : "/image/comment_default.png";

    document.getElementById("board-detail-comment_box").style.display = flagComment
        ? "block"
        : "none";
    flagComment = !flagComment;
}

// board_detail 본문 전체 보기
let flagMainText = true;
function viewMore() {
    document.getElementById("board-main-text").style.display = flagMainText
        ? "block"
        : "-webkit-box";
    flagMainText = !flagMainText;
}

// 마이페이지 팔로워 보기
function followerView() {
    const boardView = document.getElementById("board-list");
    const followerView = document.getElementById("follower-list");
    const followingView = document.getElementById("following-list");
    const likeView = document.getElementById("like-list");
    const pickView = document.getElementById("pick-list");

    boardView.style.display = 'none';
    followerView.style.display = 'block';
    followingView.style.display = 'none';
    likeView.style.display = "none";
    pickView.style.display = "none";

    const likeBtn = document.getElementById("like-btn");
    const pickBtn = document.getElementById("pick-btn");

    likeBtn.style.backgroundColor = "#efefef";
    likeBtn.style.color = "#000";
    pickBtn.style.backgroundColor = "#efefef";
    pickBtn.style.color = "#000";
}
// 마이페이지 팔로잉 보기
function followingView() {
    const boardView = document.getElementById("board-list");
    const followerView = document.getElementById("follower-list");
    const followingView = document.getElementById("following-list");
    const likeView = document.getElementById("like-list");
    const pickView = document.getElementById("pick-list");

    boardView.style.display = 'none';
    followerView.style.display = 'none';
    followingView.style.display = 'block';
    likeView.style.display = "none";
    pickView.style.display = "none";

    const likeBtn = document.getElementById("like-btn");
    const pickBtn = document.getElementById("pick-btn");

    likeBtn.style.backgroundColor = "#efefef";
    likeBtn.style.color = "#000";
    pickBtn.style.backgroundColor = "#efefef";
    pickBtn.style.color = "#000";
}
// 마이페이지 게시물 보기
function boardView() {
    const boardView = document.getElementById("board-list");
    const followerView = document.getElementById("follower-list");
    const followingView = document.getElementById("following-list");
    const likeView = document.getElementById("like-list");
    const pickView = document.getElementById("pick-list");

    boardView.style.display = 'block';
    followerView.style.display = 'none';
    followingView.style.display = 'none';
    likeView.style.display = "none";
    pickView.style.display = "none";

    const likeBtn = document.getElementById("like-btn");
    const pickBtn = document.getElementById("pick-btn");

    likeBtn.style.backgroundColor = "#efefef";
    likeBtn.style.color = "#000";
    pickBtn.style.backgroundColor = "#efefef";
    pickBtn.style.color = "#000";

}
// 마이페이지 좋아요 게시물 보기
function likeView() {
    const boardView = document.getElementById("board-list");
    const followerView = document.getElementById("follower-list");
    const followingView = document.getElementById("following-list");
    const likeView = document.getElementById("like-list");
    const pickView = document.getElementById("pick-list");

    boardView.style.display = 'none';
    followerView.style.display = 'none';
    followingView.style.display = 'none';
    likeView.style.display = "block";
    pickView.style.display = "none";

    const likeBtn = document.getElementById("like-btn");
    const pickBtn = document.getElementById("pick-btn");

    likeBtn.style.backgroundColor = "#FF5C8D";
    likeBtn.style.color = "#fff";
    pickBtn.style.backgroundColor = "#efefef";
    pickBtn.style.color = "#000";
}
// 마이페이지 찜 게시물 보기
function pickView() {
    const boardView = document.getElementById("board-list");
    const followerView = document.getElementById("follower-list");
    const followingView = document.getElementById("following-list");
    const likeView = document.getElementById("like-list");
    const pickView = document.getElementById("pick-list");

    boardView.style.display = 'none';
    followerView.style.display = 'none';
    followingView.style.display = 'none';
    likeView.style.display = "none";
    pickView.style.display = "block";

    const likeBtn = document.getElementById("like-btn");
    const pickBtn = document.getElementById("pick-btn");

    likeBtn.style.backgroundColor = "#efefef";
    likeBtn.style.color = "#000";
    pickBtn.style.backgroundColor = "#FF5C8D";
    pickBtn.style.color = "#fff";
}

function show() {
    document.querySelector(".background").className = "background show";
}

function close() {
    document.querySelector(".background").className = "background";
}

document.querySelector("#show").addEventListener('click', show);
document.querySelector("#close").addEventListener('click', close);

function tagSearch() {
    let keyword = document.getElementById("keyword").value
    location.href = "/board/search/" + keyword.replace("#", "")
}
function modalLogout() {
    location.href='/logout'
}


