(function($, window) {
//----------------------------
    /** BoardUpdate 객체 */
//----------------------------
    const BoardUpdate = {
        name: 'BoardUpdate',
        init: function() {
            this.bind();
        },
        bind: function() {
            $('#boardUpdate_write')
                .on('click',this.fn.updateArticle)
        },
        fn : {
            updateArticle: function() {
                const subject = $('#boardUpdate_subject').val(),
                      content = $('#boardUpdate_content').val();
                const id = $('#boardUpdate_write').data('id');
                const options = {
                    url: '/board/updateAtcl',
                    data: {subject: subject, content: content,id:id},
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
    window.boardUpdate = BoardUpdate;
//-------------------------
})(jQuery, window);