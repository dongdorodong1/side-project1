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
        },
        fn : {
            selectCommentJson: function() {

            },
            selectCommentForm: function() {

            },
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
            }
        },
        folderTree : {
        }
    };
    window.postView = PostView;
//-------------------------
})(jQuery, window);