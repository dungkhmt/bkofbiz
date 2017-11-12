package src.org.ofbiz.bkeuniv.paperdeclaration;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.ArrayList;


public class PaperDeclarationUtil {
	public static final String STATUS_ENABLED = "ENABLED";
	public static final String STATUS_DISABLED = "DISABLED";
	public static final String module = PaperDeclarationUtil.class.getName();

	public static List<GenericValue> getPapersOfStaff(Delegator delegator,
			String staffId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("authorStaffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));
			conds.add(EntityCondition.makeCondition("statusStaffPaper",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

			List<GenericValue> papers = delegator.findList("PapersStaffView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			//for (GenericValue gv : papers) {
			//	Debug.log(module + "::getPapersOfStaff, paper "
			//			+ gv.get("paperName"));
			//}
			//Debug.log(module + "::getPapersOfStaff, papers.sz = "
			//		+ papers.size());
			return papers;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	public static List<GenericValue> getPapersOfStaffAcademicYear(Delegator delegator,
			String staffId, String academicYearId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("authorStaffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, academicYearId));
			
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));
			conds.add(EntityCondition.makeCondition("statusStaffPaper",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

			List<GenericValue> papers = delegator.findList("PapersStaffView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			//for (GenericValue gv : papers) {
			//	Debug.log(module + "::getPapersOfStaff, paper "
			//			+ gv.get("paperName"));
			//}
			//Debug.log(module + "::getPapersOfStaff, papers.sz = "
			//		+ papers.size());
			return papers;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}

	public static List<GenericValue> getDepartments(Delegator delegator,
			String facultyId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("facultyId",
					EntityOperator.EQUALS, facultyId));
			List<GenericValue> departments = delegator.findList("Department",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return departments;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getStaffsOfADeparment(Delegator delegator,
			String departmentId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("departmentId",
					EntityOperator.EQUALS, departmentId));
			// get list of staffs
			List<GenericValue> staffs = delegator.findList("Staff",
					EntityCondition.makeCondition(conds), null, null,
					null, false);
			return staffs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static List<String> getStaffIDOfPaper(Delegator delegator, String paperId){
		try {
			List<EntityCondition> conds = FastList.newInstance();
			
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, STATUS_ENABLED));
			
			List<GenericValue> papers = delegator.findList("StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null,
					null, false);
			
			List<String> staffID = new ArrayList<String>();
			if(papers != null)for(GenericValue p: papers){
				String staffId = (String)p.get("staffId");
				staffID.add(staffId);
			}
			
			return staffID;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public static List<GenericValue> getListDepartmentsOfFaculty(Delegator delegator,
			String facultyId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("facultyId",EntityOperator.EQUALS,facultyId));
			
			List<GenericValue> depts = delegator.findList("Department", 
					EntityCondition.makeCondition(conds),
					null, null, null, false);
			return depts;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getListStaffsOfDepartment(Delegator delegator,
			String departmentId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("departmentId",EntityOperator.EQUALS,departmentId));
			
			List<GenericValue> staffs = delegator.findList("Staff", 
					EntityCondition.makeCondition(conds),
					null, null, null, false);
			return staffs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static List<GenericValue> getListStaffsOfFaculty(Delegator delegator,
			String facultyId) {
		try {
			List<GenericValue> staffs = new ArrayList<GenericValue>();
			List<GenericValue> depts = getListDepartmentsOfFaculty(delegator,facultyId);
			for(GenericValue d: depts){
				String deptID = (String)d.get("departmentId");
				List<GenericValue> staffDept = getListStaffsOfDepartment(delegator, deptID);
				for(GenericValue st: staffDept)
					staffs.add(st);
			}
			return staffs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getListPaperISI(Delegator delegator,
			String yearId, String facultyId) {
		try {
			
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, yearId));
			//conds.add(EntityCondition.makeCondition("facultyId",
			//		EntityOperator.EQUALS, facultyId));
			
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, STATUS_ENABLED));
			//conds.add(EntityCondition.makeCondition("statusStaffPaper",
			//		EntityOperator.EQUALS, STATUS_ENABLED));
			
			List<GenericValue> staffs = getListStaffsOfFaculty(delegator, facultyId);
			
			HashSet<String> setStaffID = new HashSet<String>();
			for(GenericValue st: staffs){
				String stId = (String)st.get("staffId");
				setStaffID.add(stId);
			}
			
			List<GenericValue> papers = delegator.findList("PaperDeclaration",
					EntityCondition.makeCondition(conds), null, null,
					null, false);
			
			List<GenericValue> isiPapers = new ArrayList<GenericValue>();
			for(GenericValue p: papers){
				String paperCategoryId = (String)p.get("paperCategoryId");
				String paperId = (String)p.get("paperId");
				List<String> staffID = getStaffIDOfPaper(delegator, paperId);
				boolean ok = false;
				for(String stId: staffID){
					if(setStaffID.contains(stId)){
						ok = true;
					}
				}
				if(ok && (paperCategoryId.equals("JINT_SCI") || paperCategoryId.equals("JINT_SCIE")))
					isiPapers.add(p);
			}
			Debug.log(module + "::getListPaperISI, isi papers = " + isiPapers.size());
			
			return isiPapers;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getPaperCategoryHourBudget(Delegator delegator,
			String yearId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, yearId));

			List<GenericValue> lst = delegator.findList(
					"PaperCategoryHourBudget",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return lst;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static HSSFWorkbook createExcelFormKV01(Delegator delegator,
			String academicYearId, String facultyId) {

		// get list of paper category hour-budget
		List<GenericValue> paperHourBudget = getPaperCategoryHourBudget(
				delegator, academicYearId);
		Map<String, Long> mPaperCategory2Hour = FastMap.newInstance();
		if (paperHourBudget != null){
			for (GenericValue gv : paperHourBudget) {
				String cat = (String) gv.get("paperCategoryId");
				long h = (long) gv.get("hour");
				mPaperCategory2Hour.put(cat, h);
			}
		}else{
			Debug.log(module + "::createExcelFormKV01, hour-budget of year " + academicYearId + " NOT EXISTS");;
			
		}
		
		Map<GenericValue, Long> mStaff2Hour = FastMap.newInstance();
		Map<GenericValue, List<GenericValue>> mDepartment2Staffs = FastMap.newInstance();
		
		// get list of departments of the given faculty
		List<GenericValue> departments = getDepartments(delegator, facultyId);
		for (GenericValue d : departments) {
			String deptId = (String) d.get("departmentId");
			List<GenericValue> staffs = getStaffsOfADeparment(delegator, deptId);
			Debug.log(module + "::createExcelFormKV01, staffs of department " + deptId + " = " + staffs.size());
			
			mDepartment2Staffs.put(d, staffs);
			for (GenericValue st : staffs) {
				String staffId = (String) st.get("staffId");
				List<GenericValue> papers = getPapersOfStaffAcademicYear(delegator, staffId, academicYearId);
				
				long hour = 0;
				for (GenericValue p : papers) {
					String paperCategoryId = (String) p.get("paperCategoryId");
					long h = 0;
					Debug.log(module + "::createExcelFormKV01, paper " + (String)p.get("paperName") + ", category = " + paperCategoryId);
					if(mPaperCategory2Hour.get(paperCategoryId) != null){
						h = (long)mPaperCategory2Hour.get(paperCategoryId);
						int sz = 1;
						String authors = (String)p.get("authors");
						if(authors != null){
							String[] s = authors.split(",");
							if(s != null) sz = s.length;
						}
						h = h / sz;
					}
					hour += h;
				}
				mStaff2Hour.put(st, hour);
			}
		}

		// start renderExcel

		HSSFWorkbook wb = new HSSFWorkbook();

		// sheet 01
		Sheet sh = wb.createSheet("KV01");

		int i_row = 0;
		
		for(GenericValue d: departments){
			String deptName = (String)d.get("departmentName");
			i_row += 1;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellValue(deptName);
			
			List<GenericValue> staffs = mDepartment2Staffs.get(d);
			for(GenericValue st: staffs){
				String staffName = (String)st.get("staffName");
				i_row += 1;
				r = sh.createRow(i_row);
				c = r.createCell(1);
				c.setCellValue(staffName);
				c = r.createCell(2);
				c.setCellValue(mStaff2Hour.get(st) + "");
			}
		}
		
		
		return wb;
	}

	public static HSSFWorkbook createExcelFormISI(Delegator delegator,
			String academicYearId, String facultyId) {

		List<GenericValue> papers = getListPaperISI(delegator, academicYearId, facultyId);
		
		List<GenericValue> papers1 = new ArrayList<GenericValue>();
		List<GenericValue> papers2 = new ArrayList<GenericValue>();
		
		for(GenericValue p: papers){
			long month = (long)p.get("month");
			if(month >= 7) papers1.add(p);
			else papers2.add(p);
		}
		
		// start renderExcel

		HSSFWorkbook wb = new HSSFWorkbook();

		Sheet sh = wb.createSheet("ISI");

		int i_row = 0;
		i_row += 10;
		for(GenericValue p: papers1){
			String authors = (String)p.get("authors");
			String name = (String)p.get("paperName");
			String journal = (String)p.get("journalConferenceName");
			String vol = (String)p.get("volumn");
			String issn = (String)p.get("ISSN");
			
			i_row += 1;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellValue(authors);
			
			c = r.createCell(2);
			c.setCellValue(name);
			
			c = r.createCell(3);
			c.setCellValue(journal);
			
			c = r.createCell(4);
			c.setCellValue(vol);
			
			c = r.createCell(5);
			c.setCellValue(issn);			
		}

		i_row += 1;
		//Row row = sh.createRow(i_row);
		
		for(GenericValue p: papers2){
			String authors = (String)p.get("authors");
			String name = (String)p.get("paperName");
			String journal = (String)p.get("journalConferenceName");
			String vol = (String)p.get("volumn");
			String issn = (String)p.get("ISSN");
			
			i_row += 1;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellValue(authors);
			
			c = r.createCell(2);
			c.setCellValue(name);
			
			c = r.createCell(3);
			c.setCellValue(journal);
			
			c = r.createCell(4);
			c.setCellValue(vol);
			
			c = r.createCell(5);
			c.setCellValue(issn);			
		}

		return wb;
	}

	public static HSSFWorkbook createExcelFormBM010203(Delegator delegator,
			String academicYearId, String facultyId, String departmentId) {

		List<GenericValue> staffs = getListStaffsOfDepartment(delegator, departmentId);
		List<GenericValue> categoryHours = getPaperCategoryHourBudget(delegator, academicYearId);
		Map<String, Long> mCategory2Hour = FastMap.newInstance();
		if(categoryHours != null)for(GenericValue gv: categoryHours){
			mCategory2Hour.put((String)gv.get("paperCategoryId"), (Long)gv.get("hour"));
		}
		
		Map<GenericValue, Integer> mStaff2NbrPaperJournal = FastMap.newInstance();
		Map<GenericValue, Integer> mStaff2NbrPaperConference = FastMap.newInstance();
		Map<GenericValue, Long> mStaff2NbrHours = FastMap.newInstance();
		Map<GenericValue,List<GenericValue>> mStaff2Papers = FastMap.newInstance();
		
		for(GenericValue st: staffs){
			String staffId = (String)st.get("staffId");
			List<GenericValue> papers = getPapersOfStaffAcademicYear(delegator, staffId, academicYearId);
			
			mStaff2Papers.put(st, papers);
			
			int nbJournals = 0;
			int nbConferences = 0;
			long hours = 0;
			for(GenericValue p: papers){
				String paperCategoryId = (String)p.get("paperCategoryId");
				if(paperCategoryId.equals("JINT_SCI")
					|| 	paperCategoryId.equals("JDOM_Other")
					|| 	paperCategoryId.equals("JINT_SCIE")
					|| 	paperCategoryId.equals("JINT_Other")
					|| 	paperCategoryId.equals("JINT_SCOPUS")
					){
					nbJournals++;
				}else{
					nbConferences++;
				}
				
				String authors = (String)p.get("authors");
				long paperHour = 0;
				int sz = 1;
				if(authors != null){
					String[] s = authors.split(",");
					sz = s.length;
				}
				if(mCategory2Hour.get(paperCategoryId) != null){
					paperHour = mCategory2Hour.get(paperCategoryId);
				}
				hours += paperHour/sz;
			}
			mStaff2NbrPaperJournal.put(st, nbJournals);
			mStaff2NbrPaperConference.put(st, nbConferences);
			mStaff2NbrHours.put(st, hours);
		}
		
		// start renderExcel

		HSSFWorkbook wb = new HSSFWorkbook();

		CellStyle styleTitle = wb.createCellStyle();
		Font fontTitle = wb.createFont();
		fontTitle.setFontHeightInPoints((short) 12);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTitle.setColor(HSSFColor.BLACK.index);
		styleTitle.setFont(fontTitle);
		styleTitle.setAlignment(styleTitle.ALIGN_CENTER);
		styleTitle.setVerticalAlignment(styleTitle.ALIGN_CENTER);
		styleTitle.setWrapText(true);
		styleTitle.setBorderBottom(CellStyle.BORDER_THIN);
		styleTitle.setBorderTop(CellStyle.BORDER_THIN);
		styleTitle.setBorderLeft(CellStyle.BORDER_THIN);
		styleTitle.setBorderRight(CellStyle.BORDER_THIN);
		
		
		CellStyle styleNormal = wb.createCellStyle();
		Font fontNormal = wb.createFont();
		fontNormal.setFontHeightInPoints((short) 12);
		fontNormal.setFontName("Times New Roman");
		//fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontNormal.setColor(HSSFColor.BLACK.index);
		styleNormal.setFont(fontNormal);
		styleNormal.setWrapText(true);
		styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
		styleNormal.setBorderTop(CellStyle.BORDER_THIN);
		styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
		styleNormal.setBorderRight(CellStyle.BORDER_THIN);
		
		// render sheet 01
		Sheet sh = wb.createSheet("01");
		
		sh.setColumnWidth(0, 400);
		
		int i_row = 10;
		for(GenericValue st: staffs){
			String name = (String)st.get("staffName");
			i_row += 1;
			Row row = sh.createRow(i_row);
			Cell c = row.createCell(1);
			c.setCellValue(name);
			
			c = row.createCell(2);
			c.setCellValue(mStaff2NbrPaperJournal.get(st));
			
			c = row.createCell(3);
			c.setCellValue(mStaff2NbrPaperConference.get(st));
			
			c = row.createCell(4);
			c.setCellValue(mStaff2NbrHours.get(st));
			
		}

		sh = wb.createSheet("03");

		sh.setColumnWidth(0, 1500);
		sh.setColumnWidth(1, 10000);
		sh.setColumnWidth(2, 10000);
		sh.setColumnWidth(3, 10000);
		
		i_row = 1;
		Row row_title = sh.createRow(i_row);
		Cell cell_title = row_title.createCell(1);
		//cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("BẢNG TỔNG HỢP CHI TIẾT KHỐI LƯỢNG NGHIÊN CỨU KHOA HỌC ĐƯỢC QUY ĐỔI TỪ BÀI BÁO");
		sh.addMergedRegion(new CellRangeAddress(
				i_row,
				i_row,
				1,
				7
				));
		
		i_row = 10;
		row_title = sh.createRow(i_row);
		
		cell_title = row_title.createCell(0);
		cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("STT");
		
		cell_title = row_title.createCell(1);
		cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("Họ tên tác giả và đồng tác giả");
		
		cell_title = row_title.createCell(2);
		cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("Tên bài báo");
		
		cell_title = row_title.createCell(3);
		cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("Tạp chí, proceedings");

		cell_title = row_title.createCell(4);
		cell_title.setCellStyle(styleTitle);
		
		cell_title = row_title.createCell(5);
		cell_title.setCellStyle(styleTitle);
		
		cell_title = row_title.createCell(6);
		cell_title.setCellStyle(styleTitle);
		
		cell_title = row_title.createCell(7);
		cell_title.setCellStyle(styleTitle);
		
		
		sh.addMergedRegion(new CellRangeAddress(
				i_row, // first row
				i_row, // last row
				3, // first column
				6  // last column
				));
		
		i_row += 1;
		row_title = sh.createRow(i_row);
		
		cell_title = row_title.createCell(0);
		cell_title.setCellStyle(styleTitle);
		
		cell_title = row_title.createCell(1);
		cell_title.setCellStyle(styleTitle);
		
		cell_title = row_title.createCell(2);
		cell_title.setCellStyle(styleTitle);
		
		cell_title = row_title.createCell(3);
		cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("Tên tạp chí, proceedings");
		
		cell_title = row_title.createCell(4);
		cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("Thời gian xuất bản");
		
		cell_title = row_title.createCell(5);
		cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("Chỉ số ISSN");
		
		cell_title = row_title.createCell(6);
		cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("Số giờ quy đổi của bài báo");
		
		cell_title = row_title.createCell(7);
		cell_title.setCellStyle(styleTitle);
		cell_title.setCellValue("Số giờ quy đổi của người kê khai");
		
		
		sh.addMergedRegion(new CellRangeAddress(
				i_row-1, // first row
				i_row, // last row
				0, // first column
				0  // last column
				));
		
		sh.addMergedRegion(new CellRangeAddress(
				i_row-1, // first row
				i_row, // last row
				1, // first column
				1  // last column
				));
		
		sh.addMergedRegion(new CellRangeAddress(
				i_row-1, // first row
				i_row, // last row
				2, // first column
				2  // last column
				));
		
		
		for(GenericValue st: staffs){
			String name = (String)st.get("staffName");
			List<GenericValue> papers = mStaff2Papers.get(st);
			
			if(papers == null || papers.size() == 0 ) continue;
			
			i_row += 1;
			Row row = sh.createRow(i_row);
			Cell c = row.createCell(1);
			c.setCellValue(name);
			c.setCellStyle(styleTitle);
			
			for(int i = 2; i <= 7; i++){
				c = row.createCell(i);
				c.setCellStyle(styleTitle);
			}
			
			int idx = 0;
			for(GenericValue p: papers){
				idx++;
				
				String authors = (String)p.get("authors");
				String paperName = (String)p.get("paperName");
				String journalConference = (String)p.get("journalConferenceName");
				String pub_year = (Long)p.get("year") + "";
				String issn = (String)p.get("ISSN");
				String paperCategoryId = (String)p.get("paperCategoryId");
				long hour = 0;
				if(mCategory2Hour.get(paperCategoryId) != null)
					hour = mCategory2Hour.get(paperCategoryId);
				
				i_row += 1;
				row = sh.createRow(i_row);
				
				c = row.createCell(0);
				c.setCellValue(idx + "");
				c.setCellStyle(styleNormal);

				c = row.createCell(1);
				c.setCellValue(authors);
				c.setCellStyle(styleNormal);
				
				c = row.createCell(2);
				c.setCellValue(paperName);
				c.setCellStyle(styleNormal);
				
				c = row.createCell(3);
				c.setCellValue(journalConference);
				c.setCellStyle(styleNormal);
				
				c = row.createCell(4);
				c.setCellValue(pub_year);
				c.setCellStyle(styleNormal);
				
				c = row.createCell(5);
				c.setCellValue(issn);
				c.setCellStyle(styleNormal);
				
				c = row.createCell(6);
				c.setCellValue(hour + "");
				c.setCellStyle(styleNormal);
				
				int sz = 1;
				if(authors != null){
					String[] s = authors.split(",");
					sz = s.length;
				}
				
				c = row.createCell(7);
				c.setCellValue(hour/sz + "");
				c.setCellStyle(styleNormal);
			}
		}
		
		
		
		return wb;
	}

	
	public static Map<String, Object> deleteStaffPaperDeclaration(
			String staffId, String paperId, Delegator delegator) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));

			List<GenericValue> lgv = delegator.findList(
					"StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			if (lgv == null || lgv.size() == 0) {
				return ServiceUtil.returnError("paperId, staffId " + paperId
						+ "," + staffId + " not exists");
			}
			for (GenericValue gv : lgv) {
				gv.put("statusId", PaperDeclarationUtil.STATUS_DISABLED);
				delegator.store(gv);
				Debug.log(module + "::deleteStaffPaperDeclaration, paperId = "
						+ paperId + ", staffId = " + staffId);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}

		return retSucc;
	}

	public static Map<String, Object> createStaffPaperDeclarationc(
			String paperId, String staffId, Delegator delegator) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		try {
			List<GenericValue> lst = PaperDeclarationUtil.getStaffsOfPaper(
					paperId, staffId, delegator);
			if (lst != null && lst.size() > 0) {
				retSucc.put("message", "StaffPaperDeclaration(" + staffId + ","
						+ paperId + ") exists");
				Debug.log("message", "StaffPaperDeclaration(" + staffId + ","
						+ paperId + ") exists");
				return retSucc;
			}

			String id = staffId + paperId;
			GenericValue gv = delegator.makeValue("StaffPaperDeclaration");
			gv.put("staffPaperDeclarationId", id);
			gv.put("staffId", staffId);
			gv.put("paperId", paperId);
			gv.put("statusId", PaperDeclarationUtil.STATUS_ENABLED);

			Debug.log(module + "::createStaffPaperDeclaration, staffId = "
					+ staffId + ", paperId = " + paperId + ", ID = " + id);

			delegator.create(gv);

			// retSucc.put("staffPaperDeclaration", gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		retSucc.put("message", "Successfully");

		return retSucc;
	}

	public static List<GenericValue> getStaffsOfPaper(String paperId,
			Delegator delegator) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

			List<GenericValue> staffsOfPaper = delegator.findList(
					"StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return staffsOfPaper;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static List<GenericValue> getStaffsOfPaper(String paperId,
			String staffId, Delegator delegator) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));

			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, STATUS_ENABLED));

			List<GenericValue> staffsOfPaper = delegator.findList(
					"StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return staffsOfPaper;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
