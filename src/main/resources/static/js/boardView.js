(function($, window) {
//----------------------------
    /** BoardView 객체 */
//----------------------------
    const BoardView = {
        name: 'BoardView',
        init: function() {
            this.bind();
            // this.fn.selectComment();
        },
        bind: function() {
          $('#boardView_commentRegBtn')
              .on('click',this.fn.insertComment);
        },
        fn : {
            selectCommentJson: function() {

            },
            selectCommentForm: function() {

            },
            selectComment: function() {
                const boardId = $('#boardView_info').data('id');
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
                promiseGet(`/board/selectComment?boardId=${boardId}`)
                    .then(res => {

                        const cmtList = JSON.parse(res);
                        $('#boardView_commentList').html(Mustache.render($('#boardView_cmtTemplate').html(),{comments:cmtList}))
                    })
                    .catch();

            },
            insertComment: function() {
                const content = $('#boardView_comment').val();
                const boardId = $('#boardView_info').data('id');

                const promiseGet = url => {
                     return new Promise((resolve,reject) => {
                         const xhr = new XMLHttpRequest();

                         xhr.open('POST',url);
                         xhr.setRequestHeader('content-type', 'application/json');
                         xhr.send(JSON.stringify({content: content, boardId: boardId}));

                         xhr.onload = () => {
                             if (xhr.status === 200) {
                                 resolve();
                             } else {
                                 reject(new Error(xhr.status));
                             }
                         }
                     })
                };

                 promiseGet('/board/insertComment')
                     .then(res => {
                         $('#boardView_comment').val('');
                         BoardView.fn.selectComment()});
            }
        },
        folderTree : {
        }
    };
    window.boardView = BoardView;
//-------------------------
})(jQuery, window);