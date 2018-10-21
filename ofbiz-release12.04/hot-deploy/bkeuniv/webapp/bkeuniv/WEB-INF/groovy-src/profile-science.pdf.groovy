import java.util.ArrayList;
import java.util.List

import org.ofbiz.base.util.UtilMisc;
import java.util.Map;

import org.ofbiz.entity.Delegator
import org.ofbiz.entity.GenericValue
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction
import org.ofbiz.entity.condition.EntityJoinOperator
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityListIterator
import org.ofbiz.entity.util.EntityUtil;
import javolution.util.FastList;
import javolution.util.FastMap;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.accounting.invoice.InvoiceWorker;
import org.ofbiz.base.util.UtilValidate;

GenericValue userLogin = (GenericValue) context.get("userLogin");

String userLoginId = (String) userLogin.get("userLoginId");
if(UtilValidate.isNotEmpty(parameters.staffId)) {
	userLoginId = (String) parameters.staffId;
}


/* get user academic information */

List<GenericValue> listStaffBasicInfo = delegator.findList("StaffGenaralInformation", EntityCondition.makeCondition("staffId", userLoginId), null, null, null, false);
GenericValue staffBasicInfo = null;

if(!UtilValidate.isEmpty(listStaffBasicInfo)) {
	staffBasicInfo = listStaffBasicInfo.get(0);
}

if(staffBasicInfo != null) {
	String departmentId = staffBasicInfo.getString("departmentId");
	context.hocHamName = staffBasicInfo.getString("hocHamName");
	context.yearHocHam = staffBasicInfo.getString("yearHocHam");
	context.hocViName = staffBasicInfo.getString("hocViName");
	context.yearHocVi = staffBasicInfo.getString("yearHocVi");
	context.departmentName = staffBasicInfo.getString("departmentName");
	context.genderName = staffBasicInfo.getString("genderName");
	context.duty = staffBasicInfo.getString("chucVuHienNay");
	context.researchPosition = staffBasicInfo.getString("chucDanhNghienCuuName");
	
	List<GenericValue> listDivision = delegator.findList("DepartmentDetail", EntityCondition.makeCondition("departmentId", departmentId), null, null, null, false);
	if(!UtilValidate.isEmpty(listDivision)) {
		division = listDivision.get(0);
	}
	
	context.facultyName = division.getString("facultyName")
	context.universityName = division.getString("universityName");
}




GenericValue staffAgencyWork = null;
List<EntityCondition> staffAgencyWorkCond = FastList.newInstance();
staffAgencyWorkCond.add(EntityCondition.makeCondition("staffId", userLoginId));
staffAgencyWorkCond.add(EntityCondition.makeCondition("thruDate", EntityJoinOperator.EQUALS, null));

List<GenericValue> listStaffAgencyWork = delegator.findList("StaffAgencyWork", EntityCondition.makeCondition(staffAgencyWorkCond, EntityOperator.AND), null, null, null, false);
if(!UtilValidate.isEmpty(listStaffAgencyWork)) {
	staffAgencyWork = listStaffAgencyWork.get(0);
}

if(staffAgencyWork != null) {
	context.companyAddress = staffAgencyWork.getString("address");
	context.leaderName = staffAgencyWork.getString("leaderName");
	context.companyPhone = staffAgencyWork.getString("phone");
	context.companyFax = staffAgencyWork.getString("fax");
}



/* get list phd student supervision */

List<EntityCondition> listPhdStudentConds = FastList.newInstance();

listPhdStudentConds.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("staffId"), EntityOperator.EQUALS, EntityFunction.UPPER(userLoginId)));
listPhdStudentConds.add(EntityCondition.makeCondition("thruDate", EntityJoinOperator.EQUALS, null));

EntityCondition phdStudentCondition = EntityCondition.makeCondition(listPhdStudentConds, EntityOperator.AND);

EntityListIterator listPhdStudentSupervisionTmp = delegator.find("PhdStudentView", phdStudentCondition, null, null, null, null);

List<GenericValue> listPhdStudentSupervision = listPhdStudentSupervisionTmp.getCompleteList();

listPhdStudentSupervisionTmp.close();

context.listPhdStudentSupervision = listPhdStudentSupervision;

/* get foreign language */

List<GenericValue> listForeignLanguage = delegator.findList("ForeignLanguageStaffView", EntityCondition.makeCondition("staffId", userLoginId), null, null, null,false);

context.listForeignLanguage = listForeignLanguage;

