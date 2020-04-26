<#include "../macros/base.ftlh">

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
    </div>

</#macro>

<@display_page/>