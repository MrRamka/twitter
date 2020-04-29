<#include "../macros/base.ftlh">

<#macro page_title>
    <title>Login page</title>
</#macro>

<#macro page_body>
    <div class="col-6">
        <h1 class="font-weight-light">Not found</h1>

        ${exception}
        ${url}
    </div>
</#macro>

<@display_page/>