(function($, window) {
//----------------------------
    /** PostView 객체 */
//----------------------------
    const PostView = {
        name: 'PostView',
        init: function() {
            this.bind();
            PostView.fn.selectComment()
        },
        bind: function() {
          // $('#postView_commentRegBtn')
              // .on('click',this.fn.insertComment);
            const cmntRegBtn = document.getElementById('postView_commentRegBtn');
            if(cmntRegBtn) {
                cmntRegBtn.addEventListener('click', this.fn.insertComment);
            }
            const commentUlTag = document.getElementById('postView_commentList');
            commentUlTag.addEventListener('click', function(event) {
                if (event.target.matches('._cancelModiCmnt')) {
                    const cmntLi = event.target.closest('li.comment_item');
                    cmntLi.querySelector('._cmntView').style.display = 'block';
                    //수정모드 폼 삭제
                    cmntLi.querySelector('._editForm').remove();
                }
                if (event.target.matches('._updateCmnt')) {
                    PostView.fn.updateComment(event)
                }

            });



            const recommendBtn = document.getElementById('postView_recommend_btn');
            if(recommendBtn) {
                recommendBtn.addEventListener('click', this.fn.addPostLike);
            }
        },
        fn : {
            selectComment: async function() {
                const postId = $('#postView_info').data('id');

                try {
                    const response = await fetch(`/post/selectComment?postId=${postId}`, {
                        method: 'GET'
                    });

                    if (!response.ok) {
                        throw new Error('네트워크 응답이 올바르지 않습니다.');
                    }

                    const cmntList = await response.json();
                    cmntList.forEach(function(item,i) {
                        item.content = cmm.util.replaceContentToView(item.content);
                    });
                    $('#postView_commentList').html(Mustache.render($('#postView_cmntTemplate').html(),{comments:cmntList}));
                    PostView.fn.addCommentEvent();
                } catch (error) {
                    console.error('Fetch 오류:', error);
                }

            },
            insertComment: function() {
                let content = $('#postView_comment').val();
                // 유효성검사
                if(cmm.string.isEmpty(content)) {
                    alert('댓글을 입력하세요.');
                    return;
                }
                content = cmm.util.makeContentForm(content);
                const postId = $('#postView_info').data('id');

                const promiseGet = url => {
                     return new Promise((resolve,reject) => {
                         const xhr = new XMLHttpRequest();

                         xhr.open('POST',url);
                         xhr.setRequestHeader('content-type', 'application/json');
                         xhr.send(JSON.stringify({content: content, postId: postId}));

                         xhr.onload = () => {
                             if (xhr.status === 200) {
                                 resolve();
                             } else {
                                 reject(new Error(xhr.status));
                             }
                         }
                     })
                };

                 promiseGet('/post/insertComment')
                     .then(res => {
                         $('#postView_comment').val('');
                         PostView.fn.selectComment()});
            },
            /**
             * 게시물 추천클릭 이벤트
             * @returns {Promise<void>}
             */
            addPostLike: async function() {
                const postInfoDiv = document.getElementById('postView_info');
                const postId = postInfoDiv.dataset.id;

                try {
                    const response = await fetch(`/post/addPostLike?postId=${postId}`, {
                        method: 'GET',
                    });

                    if (!response.ok) {
                        throw new Error('네트워크 응답이 올바르지 않습니다.');
                    }

                    const data = await response.text();
                    const postInfoDiv = document.getElementById('postView_recommend');
                    if(data == '1')  {
                        postInfoDiv.innerText = parseInt(postInfoDiv.innerText) + 1;
                    } else if(data == '2'){
                        postInfoDiv.innerText = parseInt(postInfoDiv.innerText) - 1;
                    }
                } catch (error) {
                    console.error('Fetch 오류:', error);
                }
            },
            /**
             * 게시물 댓글 이벤트 핸들러 등록
             */
            addCommentEvent: function() {
                const delCommentBtn = document.querySelectorAll('._delCmntBtn');
                const modiCmntBtn = document.querySelectorAll('._modifyCmntBtn');

                delCommentBtn.forEach(function(item) {
                    item.addEventListener('click',PostView.fn.deleteComment );
                });

                modiCmntBtn.forEach(function(item) {
                    item.addEventListener('click',PostView.fn.onCmntEditMode );
                });
            },
            /**
             * 댓글 수정모드로 전환
             */
            onCmntEditMode: function() {
                debugger;
                const cmntLi = this.closest('li.comment_item');
                cmntLi.querySelector('._cmntView').style.display = 'none';
                const contents = cmntLi.querySelector('._contents').innerHTML;
                const userName = cmntLi.querySelector('._user').querySelector('strong').innerHTML;
                const template = document.getElementById('postView_cmntUpdateTemplate').innerHTML;
                const renderedHtml = Mustache.render(template, { userName: userName });
                cmntLi.insertAdjacentHTML("beforeend", renderedHtml);
                cmntLi.querySelector('._updateCmntForm').innerHTML = contents;
            },
            /**
             * 댓글 삭제
             * @param e
             * @returns {Promise<void>}
             */
            deleteComment: async function(e) {
                const commentItem = e.target.closest('.comment_item');
                const cmntId = commentItem.dataset.cmntId;
                try {
                    const response = await fetch('/post/deleteComment', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: cmntId
                    });

                    if(!response.ok) {
                        throw new Error('네트워크 응답이 올바르지 않습니다.');
                    }
                    PostView.fn.selectComment();

                } catch(error) {
                    console.error('Fetch 오류:', error);
                }
            },
            updateComment: async function(e) {
                const commentLi = e.target.closest('.comment_item');
                const cmntId = commentLi.dataset.cmntId;
                const content = commentLi.querySelector('._updateCmntForm').value;

                const updateForm = {
                    id:cmntId,
                    content:content
                }
                debugger
                try {
                    const response = await fetch('/post/updateComment', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(updateForm)
                    });

                    if(!response.ok) {
                        throw new Error('네트워크 응답이 올바르지 않습니다.');
                    }
                    PostView.fn.selectComment();

                } catch(error) {
                    console.error('Fetch 오류:', error);
                }
            }
        },
        folderTree : {

        }
    };
    window.postView = PostView;
//-------------------------
})(jQuery, window);