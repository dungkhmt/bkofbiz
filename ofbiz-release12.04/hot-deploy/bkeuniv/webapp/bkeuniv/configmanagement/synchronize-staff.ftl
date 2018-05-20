<#include "component://bkeuniv/webapp/bkeuniv/uitemplate/button.ftl">

<div>
<@buttonStore text="Synchronize" action="synchronizeStaff"/>
</div>

<script>
function synchronizeStaff(){
	$.ajax({
			url: "/bkeuniv/control/perform-synchronize-staff",
			type: 'POST',
			data: {
			},
			success: function(rs){
				console.log(rs);
			}
		})
}
</script>