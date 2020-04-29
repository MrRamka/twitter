<#include "macros/base.ftlh">

<#macro page_title>
    <title>${user.username} page</title>
</#macro>

<#macro page_body>
    <div class="shadow m-2 p-3 rounded border border-primary">
        <div class="row">
            <div class="col-8">
                <h1>${user.username}</h1>
                <h3 class="font-weight-light pb-3">${user.name} ${user.surname}</h3>

                <h4>
                    Joined ${user.createdAt.format(formatter)}</h4>

                <p>Tweet amount: ${userTweets?size}</p>
                <p>Followers: ${user.following?size}</p>
            </div>
            <div class="col">
                <@security.authorize access="isAuthenticated()">
                    <#if currUsername??>
                        <#if currUsername != user.username>
                            <form action="/users/${user.username}/follow" method="post">
                                <input class="form-control" type="hidden" name="${_csrf.parameterName}"
                                       value="${_csrf.token}">
                                <input type="submit" class="btn btn-outline-success" value="Follow">
                            </form>
                        </#if>
                    </#if>
                </@security.authorize>
            </div>

        </div>

    </div>
<#-- Tweet block-->
    <#list userTweets?reverse as tweet>
        <div class="col-8 offset-2">
            <div class="shadow m-3 p-3 border border-warning rounded">
                <a class="text-dark" href="/tweet/${tweet.id}">
                    <h3>${tweet.tweetText}</h3>
                </a>
                <h4 class="font-weight-light">Posted on: ${tweet.createdAt.format(formatter)}</h4>
                <h4 class="font-weight-light">Likes: ${tweet.likes?size}</h4>

            </div>
        </div>
    </#list>
</#macro>

<@display_page/>