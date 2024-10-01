(function($, window) {
//----------------------------
    /** PostView 객체 */
//----------------------------
    const PostView = {
        name: 'PostView',
        init: function() {
            this.bind();
            // this.fn.selectComment();
        },
        bind: function() {
          $('#postView_commentRegBtn')
              .on('click',this.fn.insertComment);
            const recommendBtn = document.getElementById('postView_recommend_btn');
            recommendBtn.addEventListener('click', this.fn.addPostLike);
        },
        fn : {
            selectComment: function() {
                const postId = $('#postView_info').data('id');
                const promiseGet = url => {
                    return new Promise((resolve,reject) => {
                        const xhr = new XMLHttpRequest();

                        xhr.open('GET',url);
                        xhr.send();
                        xhr.onload = () => {
                            if (xhr.status === 200) {
                                resolve(xhr.response);
                            } else {
                                reject(new Error(xhr.status));
                            }
                        }
                    })
                };
                promiseGet(`/post/selectComment?postId=${postId}`)
                    .then(res => {

                        const cmtList = JSON.parse(res);
                        $('#postView_commentList').html(Mustache.render($('#postView_cmtTemplate').html(),{comments:cmtList}))
                    })
                    .catch();

            },
            insertComment: function() {
                const content = $('#postView_comment').val();
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
                    debugger;
                    if(data == '1')  {
                        postInfoDiv.innerText = parseInt(postInfoDiv.innerText) + 1;
                    } else if(data == '2'){
                        postInfoDiv.innerText = parseInt(postInfoDiv.innerText) - 1;
                    }
                } catch (error) {
                    console.error('Fetch 오류:', error);
                    alert("로그인에 실패했습니다. 다시 시도해주세요.");
                }
            }
        },
        folderTree : {
        }
    };
    window.postView = PostView;
//-------------------------
})(jQuery, window);