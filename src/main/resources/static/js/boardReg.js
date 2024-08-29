(function($, window) {
//----------------------------
    /** BoardReg 객체 */
//----------------------------
    const BoardReg = {
        name: 'BoardReg',
        init: function() {
            this.bind();
        },
        bind: function() {
            $('#boardReg_write')
                .on('click',this.fn.insertArticle)
        },
        fn : {
            insertArticle: function() {
                const subject = $('#boardReg_subject').val(),
                      content = $('#boardReg_content').val();
                const options = {
                    url: '/board/insertAtcl',
                    data: {subject: subject, content: content},
                    type: 'post',
                    success: function (res, statusText) {
                        if(res == 'ok') location.href='/board/boardList';
                    }
                };
                $.ajax(options);
            }
        },
        folderTree : {
        }
    };
    window.boardReg = BoardReg;
//-------------------------
})(jQuery, window);