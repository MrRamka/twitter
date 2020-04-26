<#import "/spring.ftl" as spring />

<#include "../macros/base.ftlh">

<#macro page_title>
    <title>Registration page</title>
</#macro>

<#macro page_body>
    <div class="col-6 offset-3">
        <@spring.bind path= "form" />
        <#if form?? && noErrors??>
            Your submitted data<br>
            Name, Surname: ${form.name} ${form.surname}<br>
            Username: ${form.username}<br>
        <#else>
            <form method="post">
                <div class="form-group">
                    <label>Username:</label>
                    <@spring.formInput "form.username" "class='form-control'"/>
                    <div class="text-danger">
                        <@spring.showErrors "<br>"/>
                    </div>
                </div>
                <div class="form-group">
                    <label>Name:</label>
                    <@spring.formInput "form.name" "class='form-control'"/>
                    <div class="text-danger">
                        <@spring.showErrors "<br>"/>
                    </div>
                </div>
                <div class="form-group">
                    <label>Surname:</label>
                    <@spring.formInput "form.surname" "class='form-control'"/>
                    <div class="text-danger">
                        <@spring.showErrors "<br>"/>
                    </div>
                </div>
                <div class="form-group">
                    <label>Password:</label>
                    <@spring.formPasswordInput  "form.password" "class='form-control'"/>
                    <div class="text-danger">
                        <@spring.showErrors "<br>"/>
                    </div>
                </div>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="submit" class="btn btn-success" value="Registration">
            </form>
        </#if>
    </div>
</#macro>

<@display_page/>
