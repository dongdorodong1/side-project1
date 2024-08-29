(function() {
//----------------------------
    /** BoardList 객체 */
//----------------------------
    const BoardList = {
        name: 'BoardList',
        paging: {
            pageNumber: 1, //조회할 페이지 넘버
            pageSize:15, // 한꺼번에 가져올 목록 갯수
            totalCount: 1 //전체 목록 갯수
        },
        init: function() {
            this.bind();
            this.fn.init();
        },
        bind: function() {
         /* $('#boardList_write')
              .on('click',this.fn.toWrite);*/
        },
        fn : {
            init: function() {
                this.selectBoardList();
            },
            createPage: function(paging) {
                const template = BoardList.template.pageTemplate(paging);
                $('#boardList_page').html(template)
                    .find('a._movePage')
                    .on('click',BoardList.fn.movePage);
            },
            movePage: function() {
              const pageNumber = $(this).data('pageNumber');
              BoardList.paging.pageNumber = pageNumber;
              BoardList.fn.selectBoardList();
            },
            selectBoardList: function() {
                const me = this;
                const options = {
                    url: '/board/selectBoardList',
                    data: BoardList.paging,
                    type: 'post',
                    success: function (res, statusText) {
                        debugger;
                        $('#boardList_list').html(Mustache.render($('#boardList_listTemplate').html(),{boardList:res.boardList}));
                        const paging = res.paging;
                        //페이지네이션
                        me.createPage(res.paging);
                    }
                };
                $.ajax(options);
            }
            /*toWrite: function() {
                var options = {
                    url: '/board/boardReg',
                    data: null,
                    type: 'get',
                    success: function (res, statusText) {
                    }
                };
                $.ajax(options);
            }*/
        },
        template: {
            pageTemplate: function(paging) {
                let pages = "";
                let pageNum = 1;
                for (let i = 0; i < paging.totalPages; i++) {
                    debugger;
                    if(paging.pageNumber == i) {
                        pages += `<a data-page-number="${i}" class="_movePage" style="color: blueviolet">${pageNum}</a> `;
                    } else {
                        pages += `<a data-page-number="${i}" class="_movePage">${pageNum}</a> `;
                    }
                    pageNum++;
                }

                return pages;
            }
        }
    };
    window.boardList = BoardList;
//-------------------------
})();