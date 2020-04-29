<#include "../macros/base.ftlh">
<#import "/spring.ftl" as spring />

<#macro page_title>
    <title>${tweet.author.username}`s tweet</title>
</#macro>

<#macro page_body>
    <h1>${tweet.author.username}</h1>
    <h3 class="font-weight-light pb-3">${tweet.author.name} ${tweet.author.surname}</h3>

    <div class="shadow m-2 p-3">
        <h3>${tweet.tweetText}</h3>
        <h4 class="font-weight-light">Posted on: ${tweet.createdAt.format(formatter)}</h4>
        <h4 class="font-weight-light">Likes: ${tweet.likes?size}</h4>


        <@security.authorize access="isAuthenticated()">
            <#if currentUser.username == tweet.author.username >
                <a href="/tweet/${tweet.id}/update" class="btn btn-outline-warning">Update</a>

                <form class="my-2" method="post" action="/tweet/${tweet.id}/delete">
                    <div class="row">
                        <input class="form-control" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div class="col-md-2">
                            <button class="btn btn-outline-danger">Delete</button>
                        </div>
                    </div>
                </form>
            </#if>
        </@security.authorize>


    </div>
    <@security.authorize access="isAuthenticated()">
        <div class="shadow m-2 p-3">
            <@spring.bind path= "form" />
            <form class="my-2" method="post" action="/tweet/${tweet.id}/reply">
                <div class="row">
                    <div class="col">
                        <@spring.formInput "form.tweetText" "id='${tweet.id}' name='add_comment_input' type='text'
                               class='form-control feed_comment_block'
                               placeholder='Add a comment'"/>

                    </div>
                    <input class="form-control" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <div class="text-danger">
                        <@spring.showErrors "<br>"/>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-outline-dark reply" data-pk="" data-type="Image">Reply</button>
                    </div>
                </div>
            </form>
        </div>
    </@security.authorize>
    <#if replies??>
        <div>
            <#list replies as reply>
                <div class="col-9 offset-md-3 shadow m-2 p-3 border border-warning rounded">
                    <h4><a href="/users/${reply.author.username}">${reply.author.username}</a></h4>
                    <a class="text-dark" href="/tweet/${reply.id}">
                        <h3>${reply.tweetText}</h3>
                    </a>
                    <h4 class="font-weight-light">Posted on: ${reply.createdAt.format(formatter)}</h4>
                    <h4 class="font-weight-light">Likes: ${reply.likes?size}</h4>
                </div>


            </#list>
        </div>
    </#if>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

    <script>
        $(document).ready(function () {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            $('.f').click(function () {
                $.ajax({
                    url: "/tweet/reply",
                    type: "POST",
                    dataType: 'json',
                    data: {
                        "tweet_id": 1
                    },
                    success: function (data) {
                        alert(data);
                    }

                });

            });


        });
    </script>
</#macro>

<@display_page/>