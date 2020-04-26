<#include "../macros/base.ftlh">

<#macro page_title>
    <title>Feed</title>
</#macro>

<#macro page_body>
<#--    <div class="shadow m-2 p-3 rounded border border-primary">-->
<#--        <h1>${user.username}</h1>-->
<#--        <h3 class="font-weight-light pb-3">${user.name} ${user.surname}</h3>-->

<#--        <h4>Joined ${user.dateCreated.format(formatter)}</h4>-->

<#--        <p>Tweet amount: ${userTweets?size}</p>-->
<#--        -->
<#--    </div>-->
<#-- Tweet block-->
    <#list tweets as tweet>
        <div class="col-8 offset-2">
            <div class="shadow m-3 p-3 border border-warning rounded">
                <a class="text-dark font-weight-light" href="/users/${tweet.author.username}">
                    <h3>${tweet.author.name} ${tweet.author.surname}</h3>
                </a>
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