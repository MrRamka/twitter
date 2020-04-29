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
            <div class="shadow m-2 p-3 mb-4 border border-warning rounded">
                <h2><span class="font-weight-light">Random number fact: </span></h2>
                <hr/>
                <h3 id="randomFact">${randomFact}</h3>

                <button id="addFact" class="btn btn-outline-primary">Click to post</button>
            </div>

            <form method="post" action="">
                <div class="form-group">
                    <label>Tweet text:</label>
                    <@spring.formInput "form.tweetText" "class='form-control'"/>
                    <div class="text-danger">
                        <@spring.showErrors "<br>"/>
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="submit" class="btn btn-success" value="Create">
            </form>
        </#if>
    </div>
    <script>
        $(document).ready(function () {

            $('#addFact').click(function () {


                let fact = $('#randomFact').text();
                $('#tweetText').val(fact);

            });

        });

    </script>
</#macro>

<@display_page/>