<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/index.ftl"/>
<#include "component://bkeuniv/webapp/bkeuniv/lib/meterial-ui/util.ftl"/>
<style>
    label#title-modal-input {
        padding: 5px;
        line-height: 1.5;
        flex: 1 1 auto;
        display: block;
        width: 30%;
    }

    .container-fluid .inline-box {
        position: relative;
        display: -webkit-box;
        padding: 10px 5px 10px 5px;
    }

    .inline-box .form-control {
        width: 70%;
        display: block;
    }

    input.form-control:disabled {
        background: none;
        border: none;
        outline: none;
        color: #000;
        cursor: inherit;
        box-shadow: none;
    }

    /* When the pattern is matched */
    input[type="text"]:valid {
        color: green;
    }

    input[type="text"]:valid ~ .input-validation::before {
        content: "\2713";
        color: green;
    }

    input[type="text"]:valid ~ .input-validation {
        padding: 5px 0px;
    }

    input[type="text"]:invalid ~ .input-validation {
        padding: 5px 0px;
    }

    /* Unmatched */
    input[type="text"]:invalid {
        color: red;
    }

    input[type="text"]:invalid ~ .input-validation::before {
        content: "\2717";
        color: red;
    }
        
</style>
<#assign roleTypeList=[]/>
<#list resultRole.roles as rt>
    <#if rt?has_content>
        <#assign op = { "name": rt.roleName ,"value": rt.roleId } />
        <#assign roleTypeList = roleTypeList + [op] />
    </#if>
</#list>

<#assign yesnoList=[]/>
<#list resultYesNo.yn as yn>
    <#if yn?has_content>
        <#assign op = { "name": yn.name ,"value": yn.value } />
        <#assign yesnoList = yesnoList + [op] />
    </#if>
</#list>
<script>
    function setCustomValidity(id, text){
        var element = $(id)[0];
        if(!element) {
            return;
        }
        element.oninvalid = function(e) {
            e.target.setCustomValidity("");
            if (!e.target.validity.valid) {
                e.target.setCustomValidity(text);
            }
        };
        
        element.oninput = function(e) {
            e.target.setCustomValidity("");
            if (!e.target.validity.valid) {
                e.target.setCustomValidity(text);
            }
        };
    }

    function setCustomValidityRequireAndPattern(id, textRequire, textPattern){
        var element = $(id)[0];
        if(!element) {
            return;
        }
        element.oninvalid = function(e) {
            e.target.setCustomValidity("");
            if (!e.target.validity.valid) {
                if(e.target.value=="") {
                    e.target.setCustomValidity(textRequire);
                } else {
                    e.target.setCustomValidity(textPattern);
                }
            }
        };
        
        element.oninput = function(e) {
            e.target.setCustomValidity("");
            if (!e.target.validity.valid) {
                if(e.target.value=="") {
                    e.target.setCustomValidity(textRequire);
                } else {
                    e.target.setCustomValidity(textPattern);
                }
            }
        };
    }

    var test;
    $(document).ready(function(){
        
        //$('#tabs-swipe-demo').tabs();
        <#--  test = $('ul.tabs').tabs({
            swipeable: true
        });  -->
    });

    var addM = false, addEM = false;
    var members = [
        <#list members.staffPaperDeclaration as m>
            {
                staffId: "${StringUtil.wrapString(m.staffId)}",
                staffName: "${StringUtil.wrapString(m.staffName)}",
                roleId: "${m.roleId}",
                roleName: "<#if m.roleId?exists><#list roleTypeList as type><#if m.roleId==type.value>${StringUtil.wrapString(type.name)}</#if></#list></#if>",
                CAId: "${m.correspondingAuthor}",
                CAName: "<#if m.correspondingAuthor?exists><#list yesnoList as yesno><#if m.correspondingAuthor==yesno.value>${StringUtil.wrapString(yesno.name)}</#if></#list></#if>",
                sequence: "${m.sequence}"
            },
        </#list>
    ], externalMembers = [
        <#list members.externalMemberPaperDeclaration as m>
            {
                staffName: "${StringUtil.wrapString(m.staffName)}",
                roleId: "${m.roleId}",
                roleName: "<#if m.roleId?exists><#list roleTypeList as type><#if m.roleId==type.value>${StringUtil.wrapString(type.name)}</#if></#list></#if>",
                CAId: "${m.correspondingAuthor}",
                CAName: "<#if m.correspondingAuthor?exists><#list yesnoList as yesno><#if m.correspondingAuthor==yesno.value>${StringUtil.wrapString(yesno.name)}</#if></#list></#if>",
                affilliation: "<#if m.affilliation?exists>${StringUtil.wrapString(m.affilliation)}<#else></#if>",
                sequence: "${m.sequence}"
            },
        </#list>
    ];
    var paper = {};
    var me = {};

    function updateDataLocalPaper() {
        paper.paperName = $("#papername").val();
        paper.authors = $("#authors").val();
        paper.roleId = $("#roleid").val();
        paper.roleName = $("#roleid").select2('data').length>0?$("#roleid").select2('data')[0].text:"";
        paper.correspondingAuthorId = $("#corresponding-author").val();
        paper.correspondingAuthorName = $("#corresponding-author").select2('data').length>0?$("#corresponding-author").select2('data')[0].text:"";
        paper.paperCategoryId = $("#papercategoryid").val();
        paper.paperCategoryName = $("#papercategoryid").select2('data').length>0?$("#papercategoryid").select2('data')[0].text:"";
        paper.paperCategoryKNCId = $("#papercategorykncid").val();
        paper.paperCategoryKNCName = $("#papercategorykncid").select2('data').length>0?$("#papercategorykncid").select2('data')[0].text:"";
        paper.researchProjectProposalId = $("#researchprojectproposalid").val();
        paper.researchProjectProposalName = $("#researchprojectproposalid").select2('data').length>0?$("#researchprojectproposalid").select2('data')[0].text:"";
        paper.journalConferenceName = $("#journalconferencename").val();
        paper.academicYearId = $("#academicyearid").val();
        paper.academicYearName = $("#academicyearid").select2('data').length>0?$("#academicyearid").select2('data')[0].text:"";

        paper.link = $("#link").val();
        paper.volumn = $("#volumn").val();
        paper.month = $("#month").val();
        paper.year = $("#year").val();
        paper.issn = $("#issn").val();
        paper.doi = $("#doi").val();
        paper.impactfactor = $("#impactfactor").val();
        paper.files = document.getElementById("file-upload-dropify").files;

        paper.members = members;
        paper.externalMembers = externalMembers;

        $("#value-paper-name").text(paper.paperName);
        $("#value-authors").text(paper.authors);
        $("#value-role-name").text(paper.roleName);
        $("#value-corresponding-author").text(paper.correspondingAuthorName);
        $("#value-paper-category-name").text(paper.paperCategoryName);
        $("#value-paper-category-knc-name").text(paper.paperCategoryKNCName);
        $("#value-research-project-paper").text(paper.researchProjectProposalName);
        $("#value-paper-journal-conference").text(paper.journalConferenceName);
        $("#value-paper-academic-year").text(paper.academicYearName);
        

        $("#value-paper-link").text(paper.link);
        $("#value-paper-volumn").text(paper.volumn);
        $("#value-paper-month").text(paper.month);
        $("#value-paper-year").text(paper.year);
        $("#value-paper-issn").text(paper.issn);
        $("#value-paper-doi").text(paper.doi);
        $("#value-paper-impact-factor").text(paper.impactfactor);

        if(paper.files.length > 0) {
            $("#value-paper-file-name").text(paper.files[0].name);
        } else {
            <#if resultPaper.paper.sourcePath??>
                $("#value-paper-file-name").text('${resultPaper.paper.sourcePath}');
            </#if>   
        }

         //update sequence
        var cTableMemberPaper = $("#value-table-members-paper > tbody")[0].children;
        var cTableExternalMemberPaper = $("#value-table-external-members-paper > tbody")[0].children;

        for(var i = 0; i < members.length; ++i) {
            members[i].sequence = $("#table-members-paper > tbody > tr")[i].children[4].children[0].value;
        }

        for(var i = 0; i < externalMembers.length; ++i) {
            externalMembers[i].sequence = $("#table-external-members-paper > tbody > tr")[i].children[5].children[0].value;
        }

        //remove content table members paper

        for(var i = 0; i < cTableMemberPaper.length; ++i) {
            cTableMemberPaper[i].remove();
        }

        //add content table members paper
        if(paper.members.length > 0) {
            paper.members.forEach(function (m, index) {
                $('#value-table-members-paper > tbody:last-child').append('<tr> <td>'+(index+1)+'</td> <td>'+m.staffName+'</td> <td>'+m.roleName+'</td> <td>'+m.CAName+'</td> <td>'+m.sequence+'</td> </tr>');
            })
        } else {
            
            $('#value-table-members-paper > tbody:last-child').append('<tr> <td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>');
            
        }

        //remove content table members paper
        

        for(var i = 0; i < cTableExternalMemberPaper.length; ++i) {
            cTableExternalMemberPaper[i].remove();
        }

        //add content table members paper
        if(paper.externalMembers.length > 0) {
            paper.externalMembers.forEach(function (m, index) {
                $('#value-table-external-members-paper > tbody:last-child').append('<tr> <td>'+(index+1)+'</td> <td>'+m.staffName+'</td> <td>'+m.affilliation+'</td> <td>'+m.roleName+'</td> <td>'+m.CAName+'</td> <td>'+m.sequence+'</td></tr>');
            })
        } else {

            $('#value-table-external-members-paper > tbody:last-child').append('<tr> <td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>');
            
        }
        
    }

    function nextTab() {
        me = {
            staffId: "${StringUtil.wrapString(staff.staff.staffId)}",
            staffName: "${StringUtil.wrapString(staff.staff.staffName)}",
            roleId: $("#roleid").val(),
            roleName: $("#roleid").select2('data').length>0?$("#roleid").select2('data')[0].text:"",
            CAId: $("#corresponding-author").val(),
            CAName: $("#corresponding-author").select2('data').length>0?$("#corresponding-author").select2('data')[0].text:"",
            sequence: 1
        }

        var tabIndex = 0;
        var tabActive = $("ul.tabs").children().toArray().find(function(e, index) {
            
            let child = e.children;

            if(!!child&&child.length > 0) {
                tabIndex = index;
                //console.log(child[0].classList.contains('active'))
                return child[0].classList.contains('active');
            } else {
                return false
            }
        })
    
        switch(tabIndex) {
            case 0:
                $("#form-information #submit").click();
                if(!document.getElementById("form-information").checkValidity()) {
                    return;
                }
                break;
            case 1:
                $("#form-detail #submit").click();
                if(!document.getElementById("form-detail").checkValidity()) {
                    return;
                }
                break;
            case 2:
                if((members.length + externalMembers.length) === 0) {
                    alertify.error('${StringUtil.wrapString(uiLabelMap.BkEunivNotNullMembers)}');
                    return;
                }
                break;
            case 3:
                
                break;
            default:
                return;
        }

        if(tabIndex + 1 ===3) {
            if(addM||addEM) {
                alertify.error("${uiLabelMap.BkEunivEditingHasNotYetBeenSaved}")
                return;
            }
            updateDataLocalPaper();
        }


        var tabNext = tabActive.nextElementSibling;

        if(!!tabNext.children && tabNext.children.length > 0) {
            tabNext.children[0].click();
            
        }
        tabIndex++;

        if(tabIndex === 2) {

            if(members.length === 0) {
                members.push(me);
                
                var CR = $("#table-members-paper > tbody")[0].children;

                for(var i = 0; i < CR.length; ++i) {
                    CR[i].remove();
                }
                var idSequence = 'sequence-member-'+Math.floor((Math.random() * 1000000));
                $('#table-members-paper > tbody:last-child').append('<tr> <td>'+(members.length)+'</td> <td>'+me.staffName+'</td> <td>'+me.roleName+'</td> <td>'+me.CAName+'</td><td><select id="'+idSequence+'" value="'+me.sequence+'"> <#list 1..15 as s><option value="${s}">${s}</option></#list></select> <script>$(function () {$("#'+idSequence+'").select2({minimumResultsForSearch: -1});})<\/script></td> </tr>');

            } else {
                for(var i = 0; i < members.length; ++i) {
                    var m = members[i];
                    if(m.staffId === me.staffId ) {
                        members[i] = me;
                        var row = $("#table-members-paper > tbody > tr")[i].children;
                        var staffE = row[1]; 
                        var role = row[2];
                        var correspondingAuthor = row[3];

                        staffE.innerHTML=me.staffName;
                        role.innerHTML=me.roleName;
                        correspondingAuthor.innerHTML=me.CAId;
                        
                    }
                }
            }
        }

        $("nav.dot a").toArray().forEach(function(element, index) {
            //console.log(tabIndex, index)
            if(index < tabIndex) {
                element.classList.remove("next");
                element.classList.remove("curr");
                element.classList.add("prev");
            } else {
                if(index === tabIndex) {
                    element.classList.remove("next");
                    element.classList.remove("prev");
                    element.classList.add("curr");
                } else {
                    element.classList.remove("curr");
                    element.classList.remove("prev");
                    element.classList.add("next");
                }
            }
        })
        

    }

    function jumpTab(e, tabIndex) {
        me = {
            staffId: "${StringUtil.wrapString(staff.staff.staffId)}",
            staffName: "${StringUtil.wrapString(staff.staff.staffName)}",
            roleId: $("#roleid").val(),
            roleName: $("#roleid").select2('data').length>0?$("#roleid").select2('data')[0].text:"",
            CAId: $("#corresponding-author").val(),
            CAName: $("#corresponding-author").select2('data').length>0?$("#corresponding-author").select2('data')[0].text:"",
            sequence: 1
        }

        var tabIndexCurr = 0;
        $("ul.tabs").children().toArray().find(function(e, index) {
            
            let child = e.children;

            if(!!child&&child.length > 0) {
                tabIndexCurr = index;
                //console.log(child[0].classList.contains('active'))
                return child[0].classList.contains('active');
            } else {
                return false
            }
        })

        switch(tabIndexCurr) {
            case 0:
                $("#form-information #submit").click();
                if(!document.getElementById("form-information").checkValidity()) {
                    return;
                }

                break;
            case 1:
                $("#form-detail #submit").click();
                if(!document.getElementById("form-detail").checkValidity()) {
                    return;
                }
                break;
            case 2:
                if(members.length === 0) {
                    alertify.error('${StringUtil.wrapString(uiLabelMap.BkEunivNotNullMembers)}');
                    return;
                }
                
                break;
            case 3:
                //updateDataLocalPaper();
                break;
            default:
                return;
        }

        if(tabIndex === 2) {

            if(members.length === 0) {
                members.push(me);
                
                var CR = $("#table-members-paper > tbody")[0].children;

                for(var i = 0; i < CR.length; ++i) {
                    CR[i].remove();
                }

                var idSequence = 'sequence-member-'+Math.floor((Math.random() * 1000000));
                $('#table-members-paper > tbody:last-child').append('<tr> <td>'+(members.length)+'</td> <td>'+me.staffName+'</td> <td>'+me.roleName+'</td> <td>'+me.CAName+'</td><td><select id="'+idSequence+'" value="'+me.sequence+'"> <#list 1..15 as s><option value="${s}">${s}</option></#list></select> <script>$(function () {$("#'+idSequence+'").select2({minimumResultsForSearch: -1});})<\/script></td> </tr>');

            } else {
                for(var i = 0; i < members.length; ++i) {
                    var m = members[i];
                    if(m.staffId === me.staffId ) {
                        members[i] = me;
                        var row = $("#table-members-paper > tbody > tr")[i].children;
                        var staffE = row[1]; 
                        var role = row[2];
                        var correspondingAuthor = row[3];

                        staffE.innerHTML=me.staffName;
                        role.innerHTML=me.roleName;
                        correspondingAuthor.innerHTML=me.CAId;
                        
                    }
                }
            }
        }

        var tabs = $("ul.tabs").children().toArray();
        if(!e.target.classList.contains('next')) {
            if(tabIndex === 3) {
                if(addM||addEM) {
                    alertify.error("${uiLabelMap.BkEunivEditingHasNotYetBeenSaved}")
                    return;
                }
                updateDataLocalPaper();
            }

            if(tabIndex < tabs.length) {
                var tabJ = tabs[tabIndex];
                if(!!tabJ.children && tabJ.children.length > 0) {
                    tabJ.children[0].click();
                }
            }
        }
    }
  
    function formatSelection(data) {
        //console.log(data)
        return data.text.match(/.+?(?=\[[\d\D\w\W]*\])/g)[0]||"";
    }
    
    function addMemberPaper() {
        if(!addM) {
            if(members.length === 0) {
                var CR = $("#table-members-paper > tbody")[0].children;

                for(var i = 0; i < CR.length; ++i) {
                    CR[i].remove();
                }
            }

            var render = 'function(r){return {title:"[" + r.staffId +"] " + r.staffName, id: r.staffId, text: r.staffName + " ["+ r.facultyName +" - "+r.departmentName+"]"}}';
            var url = "/bkeuniv/control/jqxGeneralServicer?sname=JQGetListStaffs";
            var maxItem = 1;
            var id="staff-member-paper-selectize-" + (members.length+1);
            var idAffiliation="staff-member-paper-selectize-affiliation-" + (members.length+1);
            var idType="staff-member-paper-selectize-type-" + (members.length+1);
            var idYN="staff-member-paper-selectize-yesno-" + (members.length+1);
            var script = '<script type="text/javascript">'+
                        '$(function () {'+
                            '$("#'+id+'").select2({'+
                                'templateSelection: formatSelection,'+
                                'language: "vi",'+
                                'ajax: {' +
                                    'url: "'+url+'",'+
                                    '"method": "POST",'+
                                    '"content-type": "application/json",'+
                                    'delay: 250,'+
                                    'cache: true,'+
                                    'data: function (params) {'+
                                        'var query = {'+
                                            'q: params.term||"",'+
                                            'pagenum:params.page-1||0,'+
                                            'pagesize:10,'+
                                        '};'+
                                        'return query;'+
                                    '},'+
                                    'processResults: function (data) {'+
                                    'return {'+
                                        'results: data.results.map('+render+'),'+
                                        '"pagination": {'+
                                            '"more": !(data.results.length<10||data.results.length==data.totalRows),'+
                                        '},'+
                                    '};'+
                                    '},'+
                                '},'+
                                (maxItem>1?('maximumSelectionLength: ' + maxItem):"")+
                            '});'+
                            '$("#'+idType+'").select2({minimumResultsForSearch: -1});'+
                            '$("#'+idYN+'").select2({minimumResultsForSearch: -1});'+
                        '});'+
                    '<\/script>';
                    
            var save = '<button type="button" style="height: 22px; border-radius: 2px; outline: none; border: none;" class="glyphicon glyphicon-ok btn-success" onClick="saveMember(event)"></button>'
            var idSequence = 'sequence-member-'+Math.floor((Math.random() * 1000000));
            
            $('#table-members-paper > tbody:last-child').append('<tr><td>'+(members.length+1)+'</td><td><select id="'+id+'" style="width: 100%" ></select></td><td><select id="'+idType+'"> <#list roleTypeList as type><option value="${type.value}">${type.name}</option></#list></select></td><td><select id="'+idAffiliation+'"><option value="N">No</option><option value="Y">Yes</option></select></td><td><select id="'+idYN+'"> <#list yesnoList as yesno><option value="${yesno.value}">${yesno.name}</option></#list></select</td><td><select style="width: 50px;" id="'+idSequence+'" value="'+(members.length+1)+'"> <#list 1..15 as s><option value="${s}">${s}</option></#list></select> <script>$(function () {$("#'+idSequence+'").select2({minimumResultsForSearch: -1});})<\/script></td> <td>'+save+'</td> </tr>' + script);
            
            addM = true;
        }
    }

    function saveMember(e) {
        test = e;
        var row = e.target.parentElement.parentElement.children;

        var staffE = row[1]; 
        var role = row[2];
        var affiliationNoneUniversity = row[3]
        //var correspondingAuthor = row[3];
        var correspondingAuthor = row[4];
        //var action = row[5];
        var action = row[6];

        var remove = '<button type="button" style="height: 22px; border-radius: 2px; outline: none; border: none;" class="glyphicon btn-danger" onClick="removeMember(event)">&#xe014;</button>'
        var staffId = staffE.firstElementChild.value;

        if(!staffId) {
            alertify.error("${uiLabelMap.BkEunivNotNullMembers}")
            return;
        }

        var staffName = staffE.firstElementChild.textContent.match(/.+?(?=\[[\d\D\w\W]*\])/g)[0]||"";
        var roleName = $(role.firstElementChild).select2('data')[0].text;
        var roleId = role.firstElementChild.value;
        var affiliationNoneUniversityId = affiliationNoneUniversity.firstElementChild.value;
        var affiliationNoneUniversityName = $(affiliationNoneUniversity.firstElementChild).select2('data')[0].text; 
        var CAName = $(correspondingAuthor.firstElementChild).select2('data')[0].text;
        var CAId = correspondingAuthor.firstElementChild.value;



        var check = members.find(function(m, index) {
            return m.staffId===staffId
        })

        if(!!check) {
            alertify.error('${uiLabelMap.BkEunivMemberAlreadyExisted}')
            return
        }

        //update row
        staffE.innerHTML=staffName;
        role.innerHTML=roleName;
        affiliationNoneUniversity.innerHTML = affiliationNoneUniversityId;
        correspondingAuthor.innerHTML=CAName;
        action.innerHTML=remove;

        addM=false;
        var member = {
            staffId: staffId,
            staffName: staffName,
            roleId: roleId,
            roleName: roleName,
            affiliationNoneUniversityId: affiliationNoneUniversityId,
            affiliationNoneUniversityName: affiliationNoneUniversityName,            
            CAId: CAId,
            CAName: CAName
        }

        members.push(member)
    }

    function removeMember(e) {
        var remove = '<button type="button" style="height: 22px; border-radius: 2px; outline: none; border: none;" class="glyphicon btn-danger" onClick="removeMember(event)">&#xe014;</button>'

        var row = e.target.parentElement.parentElement.children;
        var index = row[0].innerHTML;
        if(index > 0 && index <= members.length) {
            members.splice(index-1, 1);
            e.target.parentElement.parentElement.remove()

            //update table

            var rows = $("#table-members-paper > tbody > tr");

            for(var i = 0; i < rows.length; ++i) {
                var r = rows[i];
                r.children[0].innerHTML = i + 1;
            }

            if(members.length ===0) {
                $('#table-members-paper > tbody:last-child').append('<tr><td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>');
            }
        }
        
        <#--  if(index > 0 && index <= members.length) {
            members.splice(index-1, 1);

            var rows = $("#table-members-paper > tbody > tr");

            var CR = $("#table-members-paper > tbody")[0].children;

            for(var i = 0; i < CR.length; ++i) {
                CR[i].remove();
            }

            var tbody = $("#table-members-paper > tbody")[0];

            for(var i = 0; i < members.length; ++i) {
                var member = members[i];

                tbody.append('<td>'+(i+1)+'</td> <td>'+member.staffName+'</td> <td>'+member.roleName+'</td> <td>'+member.CAId+'</td> <td> '+remove+' </td>')
            }

            if(members.length ===0) {
                tbody.innerHTML='<tr><td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>';
            }
        }  -->
    }

    function addExternalMemberPaper() {


        if(!addEM) {
            if(externalMembers.length ===0) {
                var rows = $("#table-external-members-paper > tbody")[0].children;

                for(var i = 0; i < rows.length; ++i) {
                    rows[i].remove();
                }
            }

            var idType="staff-external-member-paper-selectize-type-" + (externalMembers.length+1);
            var idYN="staff-external-member-paper-selectize-yesno-" + (externalMembers.length+1);
            var script = '<script type="text/javascript">'+
                            '$(function () {'+
                                '$("#'+idType+'").select2({minimumResultsForSearch: -1});'+
                                '$("#'+idYN+'").select2({minimumResultsForSearch: -1});'+
                            '});'+
                        '<\/script>';
                
            var save = '<button type="button" style="height: 22px; border-radius: 2px; outline: none; border: none;" class="glyphicon glyphicon-ok btn-success" onClick="saveExternalMember(event)"></button>'
            var idSequence = 'sequence-external-member-'+Math.floor((Math.random() * 1000000));
            
            $('#table-external-members-paper > tbody:last-child').append('<tr> <td>'+(externalMembers.length+1)+'</td> <td><input type="text" value="" /></td> <td><input type="text" value="" /></td> <td><select id="'+idType+'"> <#list roleTypeList as type><option value="${type.value}">${type.name}</option></#list></select></td> <td><select id="'+idYN+'"> <#list yesnoList as yesno><option value="${yesno.value}">${yesno.name}</option></#list></select</td> <td><select style="width: 50px;" id="'+idSequence+'" value="'+(externalMembers.length+1)+'"> <#list 1..15 as s><option value="${s}">${s}</option></#list></select> <script>$(function () {$("#'+idSequence+'").select2({minimumResultsForSearch: -1});})<\/script></td> <td>'+save+'</td></tr>' + script);
            addEM = true;
        }
    }

    function saveExternalMember(e) {
        test = e;
        var row = e.target.parentElement.parentElement.children;

        var staffE = row[1]; 
        var affilliationE = row[2];
        var role = row[3];
        var correspondingAuthor = row[4];
        var action = row[6];
        
        var remove = '<button type="button" style="height: 22px; border-radius: 2px; outline: none; border: none;" class="glyphicon btn-danger" onClick="removeExternalMember(event)">&#xe014;</button>'

        var staffName = staffE.firstElementChild.value;

        if(!staffName) {
            alertify.error("${uiLabelMap.BkEunivNotNullMembers}")
            return;
        }

        var affilliation = affilliationE.firstElementChild.value;
        var roleName = $(role.firstElementChild).select2('data')[0].text;
        var roleId = role.firstElementChild.value;
        var CAName = $(correspondingAuthor.firstElementChild).select2('data')[0].text;
        var CAId = correspondingAuthor.firstElementChild.value;

        //update row
        staffE.innerHTML=staffName;
        affilliationE.innerHTML=affilliation;
        role.innerHTML=roleName;
        correspondingAuthor.innerHTML=CAName;
        action.innerHTML=remove;

        addEM=false;
        var member = {
            staffName: staffName,
            affilliation: affilliation,
            roleId: roleId,
            roleName: roleName,
            CAId: CAId,
            CAName: CAName
        }

        externalMembers.push(member)
    }

    function removeExternalMember(e) {
        var remove = '<button type="button" style="height: 22px; border-radius: 2px; outline: none; border: none;" class="glyphicon btn-danger" onClick="removeExternalMember(event)">&#xe014;</button>'
        
        var row = e.target.parentElement.parentElement.children;
        var index = row[0].innerHTML;
        if(index > 0 && index <= externalMembers.length) {
            externalMembers.splice(index-1, 1);
            e.target.parentElement.parentElement.remove()

            //update table

            var rows = $("#table-external-members-paper > tbody > tr");

            for(var i = 0; i < rows.length; ++i) {
                var r = rows[i];
                r.children[0].innerHTML = i + 1;
            }

            if(externalMembers.length ===0) {
                $('#table-external-members-paper > tbody:last-child').append('<tr><td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>');
            }
        }
        
        
        <#--  var row = e.target.parentElement.parentElement.children;

        var index = row[0].innerHTML;

        if(index > 0 && index <= externalMembers.length) {
            externalMembers.splice(index-1, 1);

            var rows = $("#table-external-members-paper > tbody > tr");

            var CR = $("#table-external-members-paper > tbody")[0].children;

            for(var i = 0; i < CR.length; ++i) {
                CR[i].remove();
            }

            var tbody = $("#table-external-members-paper > tbody")[0];

            for(var i = 0; i < externalMembers.length; ++i) {
                var member = externalMembers[i];

                tbody.append('<td>'+(i+1)+'</td> <td>'+member.staffName+'</td> <td>'+member.affilliation+'</td> <td>'+member.roleName+'</td> <td>'+member.CAId+'</td> <td> '+remove+' </td>')
            }


            if(externalMembers.length ===0) {
                tbody.innerHTML = '<tr><td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>';
            }
        }  -->
    }

    function updatePaper(urlRedirect) {
        var formData = new FormData();
        formData.append('paperId', "${parameters.paperId}");
        formData.append('paperName', paper.paperName);
        formData.append('authors', paper.authors);
        formData.append('roleId', paper.roleId);
        formData.append('paperCategoryId', paper.paperCategoryId);
        formData.append('paperCategoryKNCId', paper.paperCategoryKNCId);
        formData.append('researchProjectProposalId', paper.researchProjectProposalId);
        formData.append('journalConferenceName', paper.journalConferenceName);
        formData.append('academicYearId', paper.academicYearId);
        formData.append('link', paper.link);
        formData.append('volumn', paper.volumn);
        formData.append('month', paper.month);
        formData.append('year', paper.year);
        formData.append('issn', paper.issn);
        formData.append('doi', paper.doi);
        formData.append('impactFactor', paper.impactfactor);
        formData.append('members', JSON.stringify(paper.members));
        formData.append('externalMembers', JSON.stringify(paper.externalMembers));

        if(!!paper.files && paper.files.length > 0) {
            formData.append('file', paper.files[0]);
        }

        $.ajax({
            url: "/bkeuniv/control/update-paper-declaration",
            type: 'POST',
            method: 'POST',
            contentType: false,
            processData: false,
            data: formData,
            xhr: function() {
                    var myXhr = $.ajaxSettings.xhr();
                    if(myXhr.upload){
                        loading.open();
                        myXhr.upload.addEventListener('progress',progress, false);
                    }
                    console.log(myXhr)
                    return myXhr;
            },
            success:function(rs){
                alertify.success('${uiLabelMap.BkEunivSaveSuccess}');
                setTimeout(function(){
                    window.location.href=urlRedirect;
                }, 500);
            }
        })
    }

    function progress(e){

		if(e.lengthComputable){
			var max = e.total;
			var current = e.loaded;

			var Percentage = Math.floor((current * 100)/max);

			if(Percentage >= 100)
			{
				document.getElementById("liner-upload").style.width="100%";
				document.getElementById("infor-liner-upload").innerHTML='${uiLabelMap.BkEunivLoaded}';
				setTimeout(function(){
					loading.close();
				}, 300);
			} else {
				document.getElementById("liner-upload").style.width=Percentage+"%";
				document.getElementById("infor-liner-upload").innerHTML='${uiLabelMap.BkEunivUpload} ' + Percentage+"%";
			}
		}  
	}
</script>

<#assign me={}/>
<#list members.staffPaperDeclaration as m>
    <#if m.staffId==userLogin.userLoginId>
        <#assign me=m/>
    </#if>
</#list>

<body>
	<div class="body">
		<div id="information-paper" style="flex: 1 1 0%; padding: 2em 3em 6em 3em; width: 100%;overflow-y: auto; height: 100%;background-color: rgb(237, 236, 236);">
			<div style="color: rgba(0, 0, 0, 0.87); background-color: rgb(255, 255, 255); transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; box-sizing: border-box; font-family: Roboto, sans-serif; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 6px, rgba(0, 0, 0, 0.12) 0px 1px 4px; border-radius: 2px; z-index: 1; opacity: 1;padding: 1em;">
				<div style="display: flex; justify-content: space-between;">
					<div class="title" style="padding: 16px; position: relative;">
						<span style="font-size: 24px; color: rgba(0, 0, 0, 0.87); display: block; line-height: 36px;">
							<span id="titlePage">
							    ${titlePage}
							</span>
						</span>
					</div>
					<#--  <div style="padding: 8px; position: relative; z-index: 2; display: flex; justify-content: flex-end; flex-wrap: wrap;">
						<@FlatButton id="download" onClick="download()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
							<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z"></path>
							</svg>
							${uiLabelMap.BkEunivDownload}
						</@FlatButton>
					</div>  -->
				</div>
				<hr class="side-bar-separator">
                <style>
                    .carousel .carousel-item {
                        width: 100%;
                    }
                </style>

                <#--  <div class="breadcrumbs">
                    <nav class="dot">
                        <div class="nav-wrapper">
                        <div style="padding: 0px 2em; background: rgb(0, 188, 212);">
                            <div class="breadcrumb-materialize">ThÃ´ng tin cÆ¡ báº£n</div>
                            <div class="breadcrumb-materialize active">ThÃ nh viÃªn trong trÆ°á»�ng</div>
                            <div class="breadcrumb-materialize">ThÃ nh viÃªn ngoÃ i trÆ°á»�ng</div>
                            <div class="breadcrumb-materialize">XÃ¡c nháº­n</div>
                        </div>
                        </div>
                    </nav>
                </div>  -->

                <nav class="dot" style="margin: 30px 0px; height: auto">
                    <div class="nav-wrapper">
                        <div class="col s12">
                        <a class="breadcrumb-materialize curr" onClick="jumpTab(event, 0)" style="width: 24%; text-align: center; padding: 0 0.5rem; font-size: 16px;">${uiLabelMap.BkEunivBasicPaperInformation}</a>
                        <a class="breadcrumb-materialize next" onClick="jumpTab(event, 1)" style="width: 24%; text-align: center; padding: 0 0.5rem; font-size: 16px;">${uiLabelMap.BkEunivDetailPaperInformation}</a>
                        <a class="breadcrumb-materialize next" onClick="jumpTab(event, 2)" style="width: 24%; text-align: center; padding: 0 0.5rem; font-size: 16px;">${uiLabelMap.BkEunivMembersPaper}</a>
                        <a class="breadcrumb-materialize next" onClick="jumpTab(event, 3)" style="width: 24%; text-align: center; padding: 0 0.5rem; font-size: 16px;">${uiLabelMap.BkEunivConfirm}</a>
                        </div>
                    </div>
                </nav>
                <ul id="tabs-swipe-demo" class="tabs" style="display: none">
                    <li class="tab col s3"><a class="active" href="#swipe-1">Test 1</a></li>
                    <li class="tab col s3"><a href="#swipe-2">Test 2</a></li>
                    <li class="tab col s3"><a href="#swipe-3">Test 3</a></li>
                    <li class="tab col s3"><a href="#swipe-4">Test 4</a></li>
                </ul>
                <div id="swipe-1" class="col s12">
                
                    <form id="form-information" action="javascript:void(0);" class="container-fluid">
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperName}<span style="color: #db4437;" title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label><input
                                type="text" class="form-control" id="papername" value="<#if resultPaper.paper.paperName?exists>${resultPaper.paper.paperName}</#if>" required>
                            <script type="text/javascript">
                                $(function () {
                                    setCustomValidity("#papername", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperAuthors}<span style="color: #db4437;"
                                    title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label><input type="text" class="form-control" id="authors" value="<#if resultPaper.paper.authors?exists> ${resultPaper.paper.authors}</#if>"
                                required>
                            <script type="text/javascript">
                                $(function () {
                                    setCustomValidity("#authors", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                });
                            </script>
                        </div>
                        
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivRoleName}<span style="color: #db4437;"
                                    title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label>
                                    
                            <div style="width: 70%">
                                <select class="form-control" style="width: 100%" id="roleid" required>
                                    <#list paperRoles.roles as r>
                                        <#if r?has_content>
                                            <option value="${r.roleId}"
                                            <#if me.roleId??&&me.roleId==r.roleId>selected</#if>
                                            >${r.roleName}</option>
                                        </#if>
                                    </#list>
                                </select>
                            </div>
                            <script type="text/javascript">
                                $(function () {
                                    setCustomValidity("#roleid", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                    $("#roleid").select2({minimumResultsForSearch: -1});
                                });
                            </script>
                        </div>
                        
                        
                        
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivCorrespondingAuthor}<span style="color: #db4437;"
                                    title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label>
                                    
                            <div style="width: 70%">
                                <select class="form-control" style="width: 100%" id="corresponding-author" required>
                                    <#list yesnoList as yesno>
                                        <#if yesno?has_content>
                                            <option value="${yesno.value}"
                                            <#if me.correspondingAuthor??&&me.correspondingAuthor==yesno.value>selected</#if>
                                            >${yesno.name}</option>
                                        </#if>
                                    </#list>
                                </select>
                            </div>
                            <script type="text/javascript">
                                $(function () {
                                    setCustomValidity("#corresponding-author", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                    $("#corresponding-author").select2({minimumResultsForSearch: -1});
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperCategory}<span style="color: #db4437;"
                                    title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label>
                                    
                            <div style="width: 70%">
                                <select class="js-states form-control" style="width: 100%" id="papercategoryid">
                                    <#list paperCategory.result as paper>
                                        <#if paper?has_content>
                                            <option value="${paper.paperCategoryId?j_string}"
                                            <#if resultPaper.paper.paperCategoryId??&&resultPaper.paper.paperCategoryId==paper.paperCategoryId>
                                                selected
                                            </#if>
                                            >${paper.paperCategoryName}</option>
                                        </#if>
                                    </#list>
                                </select>
                            </div>
                            <script type="text/javascript">
                                $(function () {
                                    setCustomValidity("#papercategoryid", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                    $("#papercategoryid").select2({});
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperCategoryKNC}<span style="color: #db4437;"
                                    title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label>
                                    
                            <div style="width: 70%">
                                <select class="form-control" style="width: 100%" id="papercategorykncid">
                                    <#list paperCategoryKNC.listPaperCategoryKNC as knc>
                                        <#if knc?has_content>
                                            <option value="${knc.paperCategoryKNCId?j_string}" 
                                            <#if resultPaper.paper.paperCategoryKNCId??&&resultPaper.paper.paperCategoryKNCId==knc.paperCategoryKNCId>
                                                selected
                                            </#if>
                                            
                                            >${knc.paperCategoryKNCName?j_string}</option>
                                        </#if>
                                    </#list>
                                </select>
                            </div>
                            <script type="text/javascript">
                                $(function () {
                                    $("#papercategorykncid").select2({});
                                    setCustomValidity("#papercategorykncid", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivResearchProjectOfPaper}</label>
                                    
                            <div style="width: 70%">
                                <select class="form-control" style="width: 100%" id="researchprojectproposalid">
                                    <option value=""></option>
                                    <#list projects.researchProjectProposals as prj>
                                        <#if prj?has_content>
                                            <#if prj.researchProjectProposalName?exists>
                                            <option value="${prj.researchProjectProposalId?j_string}"
                                            <#if resultPaper.paper.researchProjectProposalId??&&resultPaper.paper.researchProjectProposalId==prj.researchProjectProposalId>
                                                selected
                                            </#if>
                                            >${prj.researchProjectProposalName?j_string}</option>
                                            </#if>
                                        </#if>
                                    </#list>
                                </select>
                            </div>
                            <script type="text/javascript">
                                $(function () {
                                    $("#researchprojectproposalid").select2({});
                                    setCustomValidity("#researchprojectproposalid", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperJournalConference}<span style="color: #db4437;"
                                    title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label>
                                    
                        <input type="text" class="form-control" id="journalconferencename" value="<#if resultPaper.paper.journalConferenceName?exists> ${resultPaper.paper.journalConferenceName} </#if>" required>
                            <script type="text/javascript">
                                $(function () {
                                    setCustomValidity("#journalconferencename", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperAcademicYear}<span style="color: #db4437;"
                                    title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label>
                                    
                            <div style="width: 70%"><select class="form-control" style="width: 100%" id="academicyearid">
                                    <#list result.academicYears as y>
                                        <#if y?has_content>
                                            <option value="${y.academicYearId?j_string}" 
                                            <#if resultPaper.paper.academicYearId??&&resultPaper.paper.academicYearId==y.academicYearId>
                                            selected
                                            </#if>
                                            >${y.academicYearName?j_string}</option>
                                        </#if>
                                    </#list>
                                </select>
                            </div>
                            <script type="text/javascript">
                                $(function () {
                                    $("#academicyearid").select2({});
                                    setCustomValidity("#academicyearid", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                });
                            </script>
                        </div>
                        <button id="submit" style="display: none" type="submit">Click Me!</button>
                        <#--  <@FlatButton id="prev-tab-1" onClick="nextTab()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
							<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"></path>
							</svg>
							${uiLabelMap.BkEunivPrevious}
						</@FlatButton>  -->
                        <@FlatButton id="next-tab-1" onClick="nextTab()" style="float: right; color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
							${uiLabelMap.BkEunivNext}
                            <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M12 4l-1.41 1.41L16.17 11H4v2h12.17l-5.58 5.59L12 20l8-8z"></path>
							</svg>
						</@FlatButton>
                    </form>
                </div>
                <div id="swipe-2" class="col s12">
                    <form id="form-detail" action="javascript:void(0);" class="container-fluid">
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperLink}<span style="color: #db4437;"
                                    title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label>
                        <input type="text" class="form-control"
                                id="link" value="<#if resultPaper.paper.link?exists>${resultPaper.paper.link}</#if>" required>
                            <script type="text/javascript">
                                //$(function () {});
                                $(function () {
                                    setCustomValidity("#link", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperVolumn}<span style="color: #db4437;"
                                    title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label>
                        
                        <input type="text"
                                class="form-control" id="volumn" value="<#if resultPaper.paper.volumn?exists>${resultPaper.paper.volumn}</#if>" required>
                            <script type="text/javascript">
                                //$(function () {});
                                 $(function () {
                                    setCustomValidity("#volumn", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}");
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperMonth}<span style="color: #db4437;" title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label><input
                                type="number" class="form-control" pattern="[1-9]([0-9]{0,2})" id="month" value="<#if resultPaper.paper.month?exists>${resultPaper.paper.month}</#if>" required="" min="1" max="12">
                            <div class="input-validation"></div>
                            <script type="text/javascript">
                                $(function () {
                                    setCustomValidityRequireAndPattern("#jqModalAdd #month", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}",
                                        "${StringUtil.wrapString(uiLabelMap.BkEunivInteger)}");
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperYear}<span style="color: #db4437;" title="${StringUtil.wrapString(uiLabelMap.BkEunivQuestionIsRequired)}"> * </span></label><input
                                type="number" class="form-control" id="year" pattern="[1-9]([0-9]{0,4})" min='1971' max='${.now?string("yyyy")?number}' value="<#if resultPaper.paper.year?exists>${resultPaper.paper.year}</#if>" required="">
                            <div class="input-validation"></div>
                            <script type="text/javascript">
                                $(function () {
                                    setCustomValidityRequireAndPattern("#jqModalAdd #year", "${StringUtil.wrapString(uiLabelMap.BkEunivNotNull)}",
                                        "${StringUtil.wrapString(uiLabelMap.BkEunivInteger)}");
                                });
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperISSN}</label>
                        <input type="text" class="form-control" id="issn"
                                value="<#if resultPaper.paper.ISSN?exists>${resultPaper.paper.ISSN}</#if>">
                            <script type="text/javascript">
                                $(function () {});
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperDOI}</label>
                        <input type="text" class="form-control" id="doi"
                                value="<#if resultPaper.paper.DOI?exists>${resultPaper.paper.DOI}</#if>">
                            <script type="text/javascript">
                                $(function () {});
                            </script>
                        </div>
                        <div class="row inline-box"><label id="title-modal-input">${uiLabelMap.BkEunivPaperIF}</label>
                        <input type="text" class="form-control"
                                id="impactfactor" pattern="([0-9]*[.])?[0-9]+" value="<#if resultPaper.paper.impactFactor?exists>${resultPaper.paper.impactFactor?c}</#if>">
                            <div class="input-validation"></div>
                            <script type="text/javascript">
                                $(function () {
                                    setCustomValidity("#impactfactor", "${StringUtil.wrapString(uiLabelMap.BkEunivFloat)}");
                                });
                            </script>
                        </div>

                        <div class="row inline-box">
                        <style>
                            #form-detail .dropify-wrapper {
                                width: 70%
                            }
                        </style>
                            <label id="title-modal-input">${uiLabelMap.BkEunivUploadFilePaper}</label>
                                <input type="file" class="form-control"
                                id="file-upload-dropify"
                                class="dropify"
                                accept=".doc, .docx, .pdf, .csv, .xls, .xlsx"
                                <#if resultPaper.paper.sourcePath??>
                                    data-default-file='${resultPaper.paper.sourcePath}'
                                </#if>
                                require
                                />
                            <div class="input-validation"></div>
                            <script type="text/javascript">
                                $(function () {
                                    $("#file-upload-dropify").dropify({
                                        maxWidth: "200",
                                        messages: {
                                            default: '${uiLabelMap.BkEunivDropifyDefault}',
                                            replace: '${uiLabelMap.BkEunivDropifyReplace}',
                                            remove:  '${uiLabelMap.BkEunivDropifyRemove}',
                                            error:   '${uiLabelMap.BkEunivDropifyError}'
                                        }
                                    }).data("dropify");
                                });
                            </script>
                        </div>
                        <button id="submit" style="display: none" type="submit">Click Me!</button>
                        <@FlatButton id="prev-tab-2" onClick="jumpTab(event, 0)" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
							<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"></path>
							</svg>
							${uiLabelMap.BkEunivPrevious}
						</@FlatButton>
                        <@FlatButton id="next-tab-2" onClick="nextTab()" style="float: right; color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
							${uiLabelMap.BkEunivNext}
                            <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M12 4l-1.41 1.41L16.17 11H4v2h12.17l-5.58 5.59L12 20l8-8z"></path>
							</svg>
						</@FlatButton>
                    </form>
                </div>
                <div id="swipe-3" class="col s12">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <div class="row">
                                    <label class="col-xs-10 col-sm-10 col-md-10" style="height: 42px; line-height: 3em;">${uiLabelMap.BkEunivPaperMembersHUST}</label>
                                    <div class="col-xs-2 col-sm-2 col-md-2">
                                        <@FlatButton id="button-add-member-paper" onClick="addMemberPaper()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 100px">
                                            <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
                                                <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"></path>
                                            </svg>
                                            ${uiLabelMap.BkEunivAdd}
                                        </@FlatButton>
                                    </div>
                                </div>
                                <table id="table-members-paper">
                                    <thead>
                                    <tr>
                                        <th>${uiLabelMap.BkEunivSTT}</th>
                                        <th style="width: 40%;">${uiLabelMap.BkEunivPaperMembers}</th>
                                        <th>${uiLabelMap.BkEunivRoleName}</th>
                                        <th>${uiLabelMap.BkEunivAffiliationOutside}</th>
                                        <th title="Corresponding author">CA</th>
                                        <th style="width: 50px;">Sequence</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    
                                    <#assign stt=1/>
                                    <tbody>
                                        <#if members.staffPaperDeclaration?size gt 0>
                                            <#assign code=random(1, 999999)?string["000000"] />
                                            
                                            <#list members.staffPaperDeclaration as m>
                                                <tr>
                                                    <td>${stt}</td>
                                                    
                                                    <td>
                                                    <#if m.staffName?exists>
                                                        ${m.staffName}
                                                    <#else>
                                                    </#if>
                                                    </td>

                                                    <td>
                                                        <#if m.roleId?exists>
                                                            <#list roleTypeList as type>
                                                                <#if m.roleId==type.value>
                                                                    ${type.name}
                                                                </#if>  
                                                            </#list>
                                                        </#if>                                                    
                                                    </td>

													
                                                    <td>
                                                        <#if m.affiliationOutsideUniversity?exists>
                                                            <#list yesnoList as yesno>
                                                                <#if m.affiliationOutsideUniversity==yesno.value>
                                                                    ${yesno.name}
                                                                </#if>  
                                                            </#list>
                                                        <#else>
                                                        	   
                                                        </#if>                                                	
                                                    </td>
													
                                                    <td>
                                                        <#if m.correspondingAuthor?exists>
                                                            <#list yesnoList as yesno>
                                                                <#if m.correspondingAuthor==yesno.value>
                                                                    ${yesno.name}
                                                                </#if>  
                                                            </#list>
                                                        </#if>                                                	
                                                    </td>




                                                    <td><select style="width: 50px;" id="sequence-member-${code}-${stt}"> <#list 1..15 as s><option value="${s}" <#if m.sequence??&&m.sequence==s>selected</#if> >${s}</option></#list></select><script>$(function () {$("#sequence-member-${code}-${stt}").select2({minimumResultsForSearch: -1});})</script></td>
                                                    <#if staff.staff.staffId!=m.staffId>
                                                        <td><button type="button" style="height: 22px; border-radius: 2px; outline: none; border: none;" class="glyphicon btn-danger" onClick="removeMember(event)">&#xe014;</button></td>
                                                    </#if>
                                                    <#assign stt=stt+1/>
                                                </tr>
                                            </#list>
                                        <#else>
                                            <tr><td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>
                                        </#if>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <div class="row">
                                    <label class="col-xs-10 col-sm-10 col-md-10" style="height: 42px; line-height: 3em;">${uiLabelMap.BkEunivPaperExternalMembersHUST}</label>
                                    <div class="col-xs-2 col-sm-2 col-md-2">
                                        <@FlatButton id="button-add-external-member-paper" onClick="addExternalMemberPaper()" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 100px">
                                            <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
                                                <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"></path>
                                            </svg>
                                            ${uiLabelMap.BkEunivAdd}
                                        </@FlatButton>
                                    </div>
                                </div>
                                <table id="table-external-members-paper">
                                    <thead>
                                    <tr>
                                        <th>${uiLabelMap.BkEunivSTT}</th>
                                        <th style="width: 20%;">${uiLabelMap.BkEunivPaperMembers}</th>
                                        <th style="width: 20%;">${uiLabelMap.Affiliation}</th>
                                        <th>${uiLabelMap.BkEunivRoleName}</th>
                                        <th title="Corresponding author">CA</th>
                                        <th style="width: 50px;">Sequence</th>
                                        <th></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                        <#assign stt=1/>
                                        
                                        <#if members.externalMemberPaperDeclaration?size gt 0>
                                            <#assign code=random(1, 999999)?string["000000"] />
                                            <#list members.externalMemberPaperDeclaration as m>
                                                <tr>
                                                    <td>${stt}</td>
                                                    <#if m.staffName?exists>
                                                        <td>${m.staffName}</td>
                                                    <#else>
                                                        <td></td>
                                                    </#if>

                                                    <td>
                                                        <#if m.affilliation?exists>
                                                            ${m.affilliation}
                                                        </#if>
                                                    </td>

                                                    <td>
                                                        <#if m.roleId?exists>
                                                            <#list roleTypeList as type>
                                                                <#if m.roleId==type.value>
                                                                    ${type.name}
                                                                </#if>  
                                                            </#list>
                                                        </#if>
                                                    </td>

                                                    <td>
                                                        <#if m.correspondingAuthor?exists>
                                                            <#list yesnoList as yesno>
                                                                <#if m.correspondingAuthor==yesno.value>
                                                                    ${yesno.name}
                                                                </#if>  
                                                            </#list>
                                                        </#if>
                                                    </td>

                                                    <td><select style="width: 50px;" id="sequence-member-${code}-${stt}"> <#list 1..15 as s><option value="${s}" <#if m.sequence??&&m.sequence==s>selected</#if> >${s}</option></#list></select><script>$(function () {$("#sequence-member-${code}-${stt}").select2({minimumResultsForSearch: -1});})</script></td>
                                                    <td><button type="button" style="height: 22px; border-radius: 2px; outline: none; border: none;" class="glyphicon btn-danger" onClick="removeExternalMember(event)">&#xe014;</button></td>
                                                    <#assign stt=stt+1/>
                                                </tr>
                                            </#list>
                                        <#else>
                                            <tr><td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>
                                        </#if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <@FlatButton id="prev-tab-2" onClick="jumpTab(event, 1)" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
							<svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"></path>
							</svg>
							${uiLabelMap.BkEunivPrevious}
						</@FlatButton>
                        <@FlatButton id="next-tab-2" onClick="nextTab()" style="float: right; color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
							${uiLabelMap.BkEunivNext}
                            <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
								<path d="M12 4l-1.41 1.41L16.17 11H4v2h12.17l-5.58 5.59L12 20l8-8z"></path>
							</svg>
						</@FlatButton>
                    </div>
                </div>
                <div id="swipe-4" class="col s12">
                    <div class="container-fluid">
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperName}</label>
                            <div id="value-paper-name" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperAuthors}</label>
                            <div id="value-authors" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivRoleName}</label>
                            <div id="value-role-name" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivCorrespondingAuthor}</label>
                            <div id="value-corresponding-author" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperCategory}</label>
                            <div id="value-paper-category-name" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperCategoryKNC}</label>
                            <div id="value-paper-category-knc-name" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivResearchProjectOfPaper}</label>
                            <div id="value-research-project-paper" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperJournalConference}</label>
                            <div id="value-paper-journal-conference" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperAcademicYear}</label>
                            <div id="value-paper-academic-year" style="width: 70%;"></div>
                        </div>
                        
                        <hr class="side-bar-separator">

                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperLink}</label>
                            <div id="value-paper-link" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperVolumn}</label>
                            <div id="value-paper-volumn" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperMonth}</label>
                            <div id="value-paper-month" style="width: 70%;"></div>
                        </div>
                        <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperYear}</label>
                            <div id="value-paper-year" style="width: 70%;"></div>
                        </div>
                         <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperISSN}</label>
                            <div id="value-paper-issn" style="width: 70%;"></div>
                        </div>
                         <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperDOI}</label>
                            <div id="value-paper-doi" style="width: 70%;"></div>
                        </div>
                         <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivPaperIF}</label>
                            <div id="value-paper-impact-factor" style="width: 70%;"></div>
                        </div>
                         <div class="row inline-box">
                            <label id="title-modal-input">${uiLabelMap.BkEunivUploadFilePaper}</label>
                            <div id="value-paper-file-name" style="width: 70%;"></div>
                        </div>
                        
                        <hr class="side-bar-separator">

                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <div class="row">
                                    <label class="col-xs-12 col-sm-12 col-md-12" style="text-align: center; font-size: 16px; height: 42px; line-height: 3em;">${uiLabelMap.BkEunivPaperMembersHUST}</label>
                                </div>
                                <table id="value-table-members-paper">
                                    <thead>
                                    <tr>
                                        <th>${uiLabelMap.BkEunivSTT}</th>
                                        <th style="width: 40%;">${uiLabelMap.BkEunivPaperMembers}</th>
                                        <th>${uiLabelMap.BkEunivRoleName}</th>
                                        <th title="Corresponding author">CA</th>
                                        <th>Sequence</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                        <tr><td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <div class="row">
                                    <label class="col-xs-12 col-sm-12 col-md-12" style="text-align: center; font-size: 16px; height: 42px; line-height: 3em;">${uiLabelMap.BkEunivPaperExternalMembersHUST}</label>
                                </div>
                                <table id="value-table-external-members-paper">
                                    <thead>
                                    <tr>
                                        <th>${uiLabelMap.BkEunivSTT}</th>
                                        <th style="width: 25%;">${uiLabelMap.BkEunivPaperMembers}</th>
                                        <th style="width: 25%;">${uiLabelMap.Affiliation}</th>
                                        <th>${uiLabelMap.BkEunivRoleName}</th>
                                        <th title="Corresponding author">CA</th>
                                        <th>Sequence</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <tr><td>${StringUtil.wrapString(uiLabelMap.BkEunivNoMembers)}</td></tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <@FlatButton id="prev-tab-3" onClick="jumpTab(event, 2)" style="color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
                        <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
                            <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"></path>
                        </svg>
                        ${uiLabelMap.BkEunivPrevious}
                    </@FlatButton>
                    <@FlatButton id="save-1" onClick='updatePaper("/bkeuniv/control/detail-paper?paperId=${parameters.paperId}")' style="left: calc(100% - 600px); color: rgb(0, 188, 212); text-transform: uppercase;width: 150px">
                        <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
                            <path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V5h10v4z"></path>
                        </svg>
                        ${uiLabelMap.BkEunivSave}
                    </@FlatButton>
                    <@FlatButton id="save-2" onClick='updatePaper("/bkeuniv/control/paper-declaration-staff")' style="left: calc(100% - 600px); color: rgb(0, 188, 212); text-transform: uppercase;width: 250px">
                        <svg viewBox="0 0 24 24" style="display: inline-block; color: rgba(0, 0, 0, 0.87); fill: rgb(0, 188, 212); height: 24px; width: 24px; user-select: none; transition: all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; vertical-align: middle; margin-left: 0px; margin-right: 0px;">
                            <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"></path>
                        </svg>
                        ${uiLabelMap.BkEunivSaveAndViewList}
                    </@FlatButton>
                </div>
                        
			</div>
		</div>
	</div>

    <@Loader handleToggle="loading" backgroundColor="rgba(0, 0, 0, 0.6)">
        <div style="margin-left: 17%; margin-right: 17%;">
            <div class="progress">
                <div class="determinate" id="liner-upload" style="width: 0%"></div>
            </div>
        </div>
        <div style="font-size: 20px; text-align: center; color: #fffffff2; font-weight: 400;" id="infor-liner-upload">
            ${uiLabelMap.BkEunivLoading} ...
        </div>
    </@Loader>
