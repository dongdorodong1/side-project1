(function() {
//----------------------------
    /** PostList 객체 */
//----------------------------
    const PostList = {
        name: 'PostList',
        paging: {
            pageNumber: 0, //조회할 페이지 넘버
            pageSize:15, // 한꺼번에 가져올 목록 갯수
            totalCount: 1 //전체 목록 갯수
        },
        init: function() {
            this.bind();
            this.fn.init();
        },
        bind: function() {
         /* $('#postList_write')
              .on('click',this.fn.toWrite);*/
        },
        fn : {
            init: function() {
                this.selectPostList();
            },
            createPage: function(paging) {
                const template = PostList.template.pageTemplate(paging);
                $('#postList_page').html(template)
                    .find('a._movePage')
                    .on('click',PostList.fn.movePage);
            },
            movePage: function() {
              const pageNumber = $(this).data('pageNumber');
              PostList.paging.pageNumber = pageNumber;
              PostList.fn.selectPostList();
            },
            selectPostList: function() {
                const me = this;
                const options = {
                    url: '/post/selectPostList',
                    data: PostList.paging,
                    type: 'post',
                    success: function (res, statusText) {
                        $('#postList_list').html(Mustache.render($('#postList_listTemplate').html(),{postList:res.postList}));
                        //페이지네이션
                        me.createPage(res.paging);
                    }
                };
                $.ajax(options);
            }
        },
        template: {
            pageTemplate: function(paging) {
                let pages = "";
                let pageNum = 1;
                for (let i = 0; i < paging.totalPages; i++) {
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
    window.postList = PostList;
//-------------------------
})();