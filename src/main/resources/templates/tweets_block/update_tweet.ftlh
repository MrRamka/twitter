<#import "/spring.ftl" as spring />

<#include "../macros/base.ftlh">

<#macro page_title>
    <title>Create tweet page</title>
</#macro>

<#macro page_body>
    <div class="col-6 offset-3">
        <@spring.bind path= "form" />
        <#if form?? && noErrors??>
            <p>Tweet created</p>
        <#else>
            <form method="post" action="">
                <div class="form-group">
                    <label>Tweet text:</label>
                    <@spring.formInput "form.tweetText" "class='form-control'"/>
                    <div class="text-danger">
                        <@spring.showErrors "<br>"/>
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="submit" class="btn btn-success" value="Save">
            </form>
        </#if>
    </div>
</#macro>

<@display_page/>