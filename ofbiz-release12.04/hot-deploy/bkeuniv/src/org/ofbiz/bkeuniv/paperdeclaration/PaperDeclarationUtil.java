package org.ofbiz.bkeuniv.paperdeclaration;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.Units;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.department.DepartmentService;
import org.ofbiz.bkeuniv.projectproposalsubmission.ProjectProposalSubmissionServiceUtil;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.utils.BKEunivUtils;

import java.util.ArrayList;

import jimm.datavision.source.Column;

public class PaperDeclarationUtil extends java.lang.Object {
	public static final String STATUS_ENABLED = "ENABLED";
	public static final String STATUS_DISABLED = "DISABLED";
	public static final String STATUS_CANCELLED = "CANCELLED";

	public static final String module = PaperDeclarationUtil.class.getName();
	public static final String[] sSTT = new String[] { "I", "II", "III", "IV",
			"V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV",
			"XV" };
	static final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7,
			I = 8, J = 9, K = 10, L = 11, M = 12, N = 13, O = 14, P = 15,
			Q = 16, R = 17, S = 18, T = 19, U = 20, V = 21, W = 22, X = 23,
			Y = 24, Z = 25;

	public static void removePaperCV(Delegator delegator, String staffPaperDeclarationId){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffPaperDeclarationId", EntityOperator.EQUALS,staffPaperDeclarationId));
			List<GenericValue> lst = delegator.findList("CVPaper", 
					EntityCondition.makeCondition(conds), null,null,null, false);
			for(GenericValue p: lst){
				delegator.removeValue(p);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void addUpdatePaperCV(Delegator delegator, String staffPaperDeclarationId, long sequenceInCVPaper){
		try{
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffPaperDeclarationId", EntityOperator.EQUALS,staffPaperDeclarationId));
			List<GenericValue> lst = delegator.findList("CVPaper", 
					EntityCondition.makeCondition(conds), null,null,null, false);
			if(lst == null || lst.size() == 0){
				String cvPaperId = delegator.getNextSeqId("CVPaper");
				GenericValue cvPaper = delegator.makeValue("CVPaper");
				cvPaper.put("cvPaperId", cvPaperId);
				cvPaper.put("staffPaperDeclarationId", staffPaperDeclarationId);
				cvPaper.put("sequenceInCVPaper", sequenceInCVPaper);
				delegator.create(cvPaper);
			}else{
				for(GenericValue cvPaper: lst){
					cvPaper.put("sequenceInCVPaper", sequenceInCVPaper);
					delegator.store(cvPaper);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
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
			// for (GenericValue gv : papers) {
			// Debug.log(module + "::getPapersOfStaff, paper "
			// + gv.get("paperName"));
			// }
			// Debug.log(module + "::getPapersOfStaff, papers.sz = "
			// + papers.size());
			return papers;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static List<GenericValue> getPapersOfStaffAcademicYear(
			Delegator delegator, String staffId, String academicYearId) {
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
			/*
			 * EntityCondition c1 =
			 * EntityCondition.makeCondition("approveStatusId",
			 * EntityOperator.NOT_EQUAL, PaperDeclarationUtil.STATUS_CANCELLED);
			 * EntityCondition c2 =
			 * EntityCondition.makeCondition("approveStatusId",
			 * EntityOperator.EQUALS,null); List<EntityCondition> L1 =
			 * FastList.newInstance(); L1.add(c1); L1.add(c2);
			 * conds.add(EntityCondition
			 * .makeCondition(L1,EntityJoinOperator.OR));
			 */

			// conds.add(EntityCondition.makeCondition("approveStatusId",
			// EntityOperator.NOT_EQUAL,
			// PaperDeclarationUtil.STATUS_CANCELLED));

			List<GenericValue> papers = delegator.findList("PapersStaffView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			List<GenericValue> ret_papers = FastList.newInstance();
			for (GenericValue p : papers) {
				// System.out.println(module +
				// "::getPapersOfStaffAcademicYear, " +
				// p.get("approveStatusId"));

				if (p.get("approveStatusId") == null
						|| !p.getString("approveStatusId").equals(
								PaperDeclarationUtil.STATUS_CANCELLED)) {
					ret_papers.add(p);
				}
			}
			// for (GenericValue gv : papers) {
			// Debug.log(module + "::getPapersOfStaff, paper "
			// + gv.get("paperName"));
			// }
			// Debug.log(module + "::getPapersOfStaff, papers.sz = "
			// + papers.size());
			return ret_papers;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static List<Map<String, Object>> getResearchProjectOfStaffAcademicYear(
			Delegator delegator, String staffId, String academicYearId) {
		try {
			List<Map<String, Object>> ResearchProjectAcademicYear = new ArrayList<Map<String, Object>>();

			List<EntityCondition> condsResearchProject = FastList.newInstance();
			condsResearchProject.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			condsResearchProject.add(EntityCondition.makeCondition(
					"academicYearId", EntityOperator.EQUALS, academicYearId));
			// condsResearchProject.add(EntityCondition.makeCondition("statusId",
			// EntityOperator.EQUALS,
			// ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_RUNNING ));

			List<GenericValue> researchProject = delegator.findList(
					"ResearchProjectView",
					EntityCondition.makeCondition(condsResearchProject), null,
					null, null, false);

			Debug.log(module
					+ "::getResearchProjectOfStaffAcademicYear, researchProject.sz = "
					+ researchProject.size());
			for (int i = 0; i < researchProject.size(); ++i) {
				Map<String, Object> rpg = new HashMap<String, Object>();

				GenericValue rp = researchProject.get(i);

				List<GenericValue> memberResearchProject = delegator.findList(
						"MemberResearchProjectView",
						EntityCondition.makeCondition(
								"researchProjectProposalId",
								EntityOperator.EQUALS,
								rp.getString("researchProjectProposalId")),
						null, null, null, false);
				Debug.log(module
						+ "::getResearchProjectOfStaffAcademicYear, project "
						+ rp.getString("researchProjectProposalName") + " has "
						+ memberResearchProject.size() + " member");

				rpg.put("information", rp);
				rpg.put("member", memberResearchProject);
				ResearchProjectAcademicYear.add(rpg);

			}

			return ResearchProjectAcademicYear;

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

	public static GenericValue getDepartment(Delegator delegator,
			String departmentId) {
		try {

			GenericValue d = delegator.findOne("Department",
					UtilMisc.toMap("departmentId", departmentId), false);
			return d;
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
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return staffs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<String> getStaffIDOfPaper(Delegator delegator,
			String paperId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();

			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));

			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, STATUS_ENABLED));

			List<GenericValue> papers = delegator.findList(
					"StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			List<String> staffID = new ArrayList<String>();
			if (papers != null)
				for (GenericValue p : papers) {
					String staffId = (String) p.get("staffId");
					staffID.add(staffId);
				}

			return staffID;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static List<GenericValue> getListDepartmentsOfFaculty(
			Delegator delegator, String facultyId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("facultyId",
					EntityOperator.EQUALS, facultyId));

			List<GenericValue> depts = delegator.findList("Department",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return depts;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getListStaffsOfDepartment(
			Delegator delegator, String departmentId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("departmentId",
					EntityOperator.EQUALS, departmentId));

			List<GenericValue> staffs = delegator.findList("Staff",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return staffs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getListStaffs(Delegator delegator) {
		try {

			List<GenericValue> staffs = delegator.findList("Staff", null, null,
					null, null, false);
			return staffs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getListStaffsOfFaculty(
			Delegator delegator, String facultyId) {
		try {
			List<GenericValue> staffs = new ArrayList<GenericValue>();
			List<GenericValue> depts = getListDepartmentsOfFaculty(delegator,
					facultyId);
			for (GenericValue d : depts) {
				String deptID = (String) d.get("departmentId");
				List<GenericValue> staffDept = getListStaffsOfDepartment(
						delegator, deptID);
				for (GenericValue st : staffDept)
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
			// conds.add(EntityCondition.makeCondition("facultyId",
			// EntityOperator.EQUALS, facultyId));

			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, STATUS_ENABLED));
			// conds.add(EntityCondition.makeCondition("statusStaffPaper",
			// EntityOperator.EQUALS, STATUS_ENABLED));

			List<GenericValue> staffs = getListStaffsOfFaculty(delegator,
					facultyId);

			HashSet<String> setStaffID = new HashSet<String>();
			for (GenericValue st : staffs) {
				String stId = (String) st.get("staffId");
				setStaffID.add(stId);
			}

			List<GenericValue> papers = delegator.findList("PaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			List<GenericValue> isiPapers = new ArrayList<GenericValue>();
			for (GenericValue p : papers) {
				String paperCategoryId = (String) p.get("paperCategoryId");
				String paperId = (String) p.get("paperId");
				List<String> staffID = getStaffIDOfPaper(delegator, paperId);
				boolean ok = false;
				for (String stId : staffID) {
					if (setStaffID.contains(stId)) {
						ok = true;
					}
				}
				if (ok
						&& (paperCategoryId.equals("JINT_SCI") || paperCategoryId
								.equals("JINT_SCIE")))
					isiPapers.add(p);
			}
			Debug.log(module + "::getListPaperISI, isi papers = "
					+ isiPapers.size());

			return isiPapers;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getListPaperScopus(Delegator delegator,
			String yearId, String facultyId) {
		try {

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, yearId));
			// conds.add(EntityCondition.makeCondition("facultyId",
			// EntityOperator.EQUALS, facultyId));

			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, STATUS_ENABLED));
			// conds.add(EntityCondition.makeCondition("statusStaffPaper",
			// EntityOperator.EQUALS, STATUS_ENABLED));

			List<GenericValue> staffs = getListStaffsOfFaculty(delegator,
					facultyId);

			HashSet<String> setStaffID = new HashSet<String>();
			for (GenericValue st : staffs) {
				String stId = (String) st.get("staffId");
				setStaffID.add(stId);
			}

			List<GenericValue> papers = delegator.findList("PaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			List<GenericValue> isiPapers = new ArrayList<GenericValue>();
			for (GenericValue p : papers) {
				//String paperCategoryId = (String) p.get("paperCategoryId");
				String paperCategoryKNCId = (String) p.get("paperCategoryKNCId");
				
				String paperId = (String) p.get("paperId");
				List<String> staffID = getStaffIDOfPaper(delegator, paperId);
				boolean ok = false;
				for (String stId : staffID) {
					if (setStaffID.contains(stId)) {
						ok = true;
					}
				}
				//if (ok && (paperCategoryId.equals("JINT_SCOPUS")))
				if (ok && (paperCategoryKNCId.equals("SCOPUS")))
					isiPapers.add(p);
				
			}
			Debug.log(module + "::getListPaperScopus, scopus papers = "
					+ isiPapers.size());

			return isiPapers;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<GenericValue> getPaperCategoryHourBudget(
			Delegator delegator, String yearId) {
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

	public static Map<String, Object> getProjectHourOfStaffs(
			Delegator delegator, String academicYearId) {
		Map<String, Object> retSucc = FastMap.newInstance();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, academicYearId));

			List<GenericValue> lst = delegator.findList(
					"ResearchProjectDeclarationYear",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			Debug.log(module + "::getProjectHourOfStaffs, list = " + lst.size());

			for (GenericValue g : lst) {
				String staffId = g.getString("staffId");
				Debug.log(module + "::getProjectHourOfStaffs, staffId = "
						+ staffId);
				if (staffId == null)
					continue;

				long hours = 0;
				if (g.getLong("workinghours") != null)
					hours = g.getLong("workinghours");
				if (retSucc.get(staffId) == null)
					retSucc.put(staffId, hours);
				else
					retSucc.put(staffId, (long) retSucc.get(staffId) + hours);

				Debug.log(module + "::getProjectHourOfStaffs, hours = " + hours
						+ ", staff " + staffId + " has hour = "
						+ retSucc.get(staffId));

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static void createPaperRow(int i_row, Sheet sh, GenericValue p,
			HSSFCellStyle cellStyle,
			HashMap<GenericValue, Integer> mPaper2NbAuthors) {
		Row r = sh.createRow(i_row);

		int i_col = 1;
		i_col++;
		Cell c = r.createCell(i_col);
		c.setCellStyle(cellStyle);
		c.setCellValue(p.getString("paperName"));

		i_col++;
		c = r.createCell(i_col);
		c.setCellStyle(cellStyle);
		c.setCellValue(p.getString("journalConferenceName"));

		i_col++;
		c = r.createCell(i_col);
		c.setCellStyle(cellStyle);
		c.setCellValue(p.getString("volumn"));

		i_col++;
		c = r.createCell(i_col);
		c.setCellStyle(cellStyle);
		c.setCellValue(p.getString("ISSN"));

		i_col++;
		c = r.createCell(i_col);
		c.setCellStyle(cellStyle);
		int nbAuthors = 0;
		if (p.getString("authors") != null) {
			String[] s = p.getString("authors").split(",");
			nbAuthors = s.length;
		}
		c.setCellValue(nbAuthors);

		i_col++;
		c = r.createCell(i_col);
		c.setCellStyle(cellStyle);
		int nbIntAuthors = mPaper2NbAuthors.get(p);
		c.setCellValue(nbIntAuthors);
	}

	public static int createContentTable(Sheet sh, List<List<String>> data,
			HSSFCellStyle cellStyle, int firstRow, int firstColumn) {
		int currRow = firstRow;
		int currColumn = firstColumn;
		for (int i = 0; i < data.size(); ++i) {
			List<String> d = data.get(i);
			Row rh = sh.createRow(currRow);
			for (int j = 0; j < d.size(); ++j) {
				createCell(rh, cellStyle, d.get(j), currRow, currColumn);

				currColumn++;
			}
			currRow++;
			currColumn = firstColumn;
		}

		return currRow - 1;
	}

	public static void createSheetPaper02CN(HSSFWorkbook wb,
			List<Map<String, Object>> researchProject, List<GenericValue> user) {

		int numColumns = 11; // A -> K
		int[] widthColumns = { 100, 1600, 5000, 3000, 3000, 3000, 3000, 3000,
				3000, 3000, 3000 };
		int currRow = 0;
		int currColumn = A; // A

		GenericValue u;
		if (user == null) {
			return;
		} else {
			u = user.get(0);
		}

		// font Sheet
		String fontName = "Times New Roman";

		Sheet sh = wb.createSheet("Đề tài 01-CN");
		Row rh;
		Cell ch;

		for (int i = 0; i < widthColumns.length; ++i) {
			sh.setColumnWidth(i, widthColumns[i]);
		}

		// Title
		HSSFCellStyle cellStyleTitle = createCellStyle(wb, (short) 12,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_NONE);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		String title = "BẢNG KÊ KHAI KHỐI LƯỢNG NGHIÊN CỨU KHOA HỌC NĂM HỌC 2017 - 2018";
		mergedRegion(sh, currRow, currRow, currColumn, H);
		createCell(rh, cellStyleTitle, title, currRow, currColumn);

		// Note
		HSSFCellStyle cellStyleNote = createCellStyle(wb, (short) 11,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_CENTER);

		currColumn = J;
		createCell(rh, cellStyleNote, "Mẫu 02- CN\nDùng cho cá nhân", currRow,
				currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = A;

		// Subtitle
		HSSFCellStyle cellStyleSubTitle = createCellStyle(wb, (short) 9,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT);

		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, H);
		createCell(
				rh,
				cellStyleSubTitle,
				"ĐỀ TÀI NGHIÊN CỨU KHOA HỌC CÁC CẤP – BẰNG ĐỘC QUYỀN SÁNG CHẾ/GIẢI PHÁP HỮU ÍCH",
				currRow, currColumn);

		// Merge Note
		mergedRegion(wb, sh, currRow - 1, currRow, J, K, CellStyle.BORDER_THIN);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);

		// Information
		HSSFCellStyle cellStyleInformation = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT);

		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, E);
		createCell(rh, cellStyleInformation,
				"Họ và tên cán bộ: " + u.getString("staffName"), currRow,
				currColumn);

		String telephone = "Tel: (CQ) - (NR) - (DĐ)";
		if (u.getString("departmentName") != null) {
			telephone = "Tel: (CQ) - (NR) - (DĐ) " + u.getString("staffPhone");
		}
		currColumn = G;
		mergedRegion(sh, currRow, currRow, currColumn, K);
		createCell(rh, cellStyleInformation, telephone, currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);

		String departmentName = "Bộ môn: ";
		if (u.getString("departmentName") != null) {
			departmentName = "Bộ môn: " + u.getString("departmentName");
		}

		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, E);
		createCell(rh, cellStyleInformation, departmentName, currRow,
				currColumn);

		currColumn = G;
		mergedRegion(sh, currRow, currRow, currColumn, K);
		createCell(rh, cellStyleInformation,
				"Khoa (Viện, Trung tâm): " + u.getString("facultyName"),
				currRow, currColumn);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);

		HSSFCellStyle cellStyleSection = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT);
		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, K);
		createCell(rh, cellStyleSection,
				"I. ĐỀ TÀI NGHIÊN CỨU KHOA HỌC CÁC CẤP:", currRow, currColumn);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);

		// Table content
		HSSFCellStyle cellStyleHeaderTable = createCellStyle(wb, (short) 9,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				CellStyle.BORDER_THIN);
		HSSFCellStyle cellStyleSum = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_THIN);

		currColumn = B;
		createCell(rh, cellStyleHeaderTable, "TT", currRow, currColumn);

		currColumn = C;
		createCell(
				rh,
				cellStyleHeaderTable,
				"Tên đề tài (dự án), thời gian thực hiện.\nNhững người cùng thực hiện, đơn vị, vai trò.",
				currRow, currColumn);

		currColumn = D;
		createCell(rh, cellStyleHeaderTable, "Kinh phí được cấp\n(Triệu đồng)",
				currRow, currColumn);

		currColumn = E;
		createCell(rh, cellStyleHeaderTable,
				"Đề tài KHCN, dự án, cấp Nhà nước", currRow, currColumn);

		currColumn = F;
		createCell(rh, cellStyleHeaderTable,
				"Đề tài, dự án cấp Bộ, thành phố và tương đương", currRow,
				currColumn);

		currColumn = G;
		createCell(rh, cellStyleHeaderTable, "Đề tài thuộc quỹ Nafosted",
				currRow, currColumn);

		currColumn = H;
		createCell(rh, cellStyleHeaderTable, "Đề tài, dự án, HTQT", currRow,
				currColumn);

		currColumn = I;
		createCell(rh, cellStyleHeaderTable, "Đề tài cấp trường", currRow,
				currColumn);

		currColumn = J;
		createCell(rh, cellStyleHeaderTable,
				"Số giờ quy đổi của  đề tài, dự án", currRow, currColumn);

		currColumn = K;
		createCell(rh, cellStyleHeaderTable,
				"Số giờ quy đổi cho người kê khai\n(I)", currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);

		// index columns
		currColumn = B;
		createCell(rh, cellStyleHeaderTable, "1", currRow, currColumn);
		currColumn = C;
		createCell(rh, cellStyleHeaderTable, "2", currRow, currColumn);
		currColumn = D;
		createCell(rh, cellStyleHeaderTable, "3", currRow, currColumn);
		currColumn = E;
		createCell(rh, cellStyleHeaderTable, "4", currRow, currColumn);
		currColumn = F;
		createCell(rh, cellStyleHeaderTable, "5", currRow, currColumn);
		currColumn = G;
		createCell(rh, cellStyleHeaderTable, "6", currRow, currColumn);
		currColumn = H;
		createCell(rh, cellStyleHeaderTable, "7", currRow, currColumn);
		currColumn = I;
		createCell(rh, cellStyleHeaderTable, "8", currRow, currColumn);
		currColumn = J;
		createCell(rh, cellStyleHeaderTable, "9", currRow, currColumn);
		currColumn = K;
		createCell(rh, cellStyleHeaderTable, "10", currRow, currColumn);

		// content
		HSSFCellStyle cellStyleContent = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_THIN);
		HSSFCellStyle cellStyleCheckBox = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				CellStyle.BORDER_THIN);
		if (researchProject == null || researchProject.size() == 0) {
			currRow++;
			rh = sh.createRow(currRow);

			for (int i = B; i <= K; ++i) {
				createCell(rh, cellStyleSum, "", currRow, i);
			}
		} else {
			for (int i = 0; i < researchProject.size(); ++i) {
				Map<String, Object> rp = researchProject.get(i);
				GenericValue information = (GenericValue) rp.get("information");
				List<GenericValue> member = (List<GenericValue>) rp
						.get("member");
				String[] memberSA = new String[member.size()];

				int index = 0;
				int totalWorkinghours = 0;
				for (int j = 0; j < member.size(); ++j) {
					GenericValue m = member.get(j);
					totalWorkinghours += m.getLong("workinghours");
					if (m.getString("projectParticipationRoleId").equals(
							"DIRECTOR")) {
						memberSA[index] = m.getString("staffName") + " ("
								+ m.getString("projectParticipationRoleName")
								+ ")";
						index++;
					}
				}

				for (int j = 0; j < member.size(); ++j) {
					GenericValue m = member.get(j);
					if (m.getString("projectParticipationRoleId").equals(
							"MEMBER")) {
						memberSA[index] = m.getString("staffName") + " ("
								+ m.getString("projectParticipationRoleName")
								+ ")";
						index++;
					}
				}

				String memberString = StringUtils.join(memberSA, ", ");
				currRow++;
				rh = sh.createRow(currRow);

				createCell(rh, cellStyleContent, String.valueOf(i + 1) + ".",
						currRow, B);
				createCell(rh, cellStyleContent,
						information.getString("researchProjectProposalName")
								+ ". " + memberString, currRow, C);
				createCell(rh, cellStyleContent,
						information.getString("totalBudget"), currRow, D);

				int checkBox = 0;

				switch (information.getString("projectCategoryId")) {
				case "INST_MIX_PROJ":
					checkBox = I;
					break;
				case "INST_PROJ":
					checkBox = I;
					break;
				case "REGION_PROJ":
					checkBox = F;
					break;
				case "NAFOS_PROJ":
					checkBox = G;
					break;
				case "MINI_PROJ":
					checkBox = F;
					break;
				case "NATI_PROJ":
					checkBox = E;
					break;

				case "INTCOOP_PROJ":
					checkBox = H;
					break;
				}

				for (int j = E; j <= I; ++j) {
					if (j == checkBox) {
						createCell(rh, cellStyleCheckBox, "X", currRow,
								checkBox);
					} else {
						createCell(rh, cellStyleCheckBox, "", currRow, j);
					}
				}

				createCell(rh, cellStyleContent,
						String.valueOf(totalWorkinghours), currRow, J);

				createCell(rh, cellStyleContent,
						information.getString("workinghours"), currRow, K);

			}
		}

		// Sum

		currRow++;
		rh = sh.createRow(currRow);

		for (int i = B; i <= K; ++i) {
			if (i == C) {
				createCell(rh, cellStyleSum, "Tổng cộng", currRow, i);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);

		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, K);
		createCell(rh, cellStyleSection,
				"II. BẰNG ĐỘC QUYỀN SÁNG CHẾ/GIẢI PHÁP HỮU ÍCH:", currRow,
				currColumn);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);

		// Table content

		currColumn = B;
		createCell(rh, cellStyleHeaderTable, "TT", currRow, currColumn);

		currColumn = C;
		createCell(rh, cellStyleHeaderTable, "Tên tác giả/các tác giả",
				currRow, currColumn);

		currColumn = D;
		createCell(rh, cellStyleHeaderTable, "Loại văn bằng", currRow,
				currColumn);

		currColumn = E;
		createCell(rh, cellStyleHeaderTable, "Số bằng", currRow, currColumn);

		currColumn = F;
		createCell(rh, cellStyleHeaderTable, "Tên Sáng chế/Giải pháp hữu ích",
				currRow, currColumn);

		currColumn = G;
		createCell(rh, cellStyleHeaderTable, "Ngày tháng năm được cấp",
				currRow, currColumn);

		currColumn = H;
		createCell(rh, cellStyleHeaderTable, "Số giờ quy đổi của văn bằng",
				currRow, currColumn);

		currColumn = I;
		createCell(rh, cellStyleHeaderTable,
				"Số giờ quy đổi  cho người kê khai\n(II)", currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);

		// index columns
		currColumn = B;
		createCell(rh, cellStyleHeaderTable, "1", currRow, currColumn);
		currColumn = C;
		createCell(rh, cellStyleHeaderTable, "2", currRow, currColumn);
		currColumn = D;
		createCell(rh, cellStyleHeaderTable, "3", currRow, currColumn);
		currColumn = E;
		createCell(rh, cellStyleHeaderTable, "4", currRow, currColumn);
		currColumn = F;
		createCell(rh, cellStyleHeaderTable, "5", currRow, currColumn);
		currColumn = G;
		createCell(rh, cellStyleHeaderTable, "6", currRow, currColumn);
		currColumn = H;
		createCell(rh, cellStyleHeaderTable, "7", currRow, currColumn);
		currColumn = I;
		createCell(rh, cellStyleHeaderTable, "8", currRow, currColumn);

		// fake data
		currRow++;
		rh = sh.createRow(currRow);

		for (int i = B; i <= I; ++i) {
			createCell(rh, cellStyleSum, "", currRow, i);
		}

		// Sum

		currRow++;
		rh = sh.createRow(currRow);

		for (int i = B; i <= I; ++i) {
			if (i == C) {
				createCell(rh, cellStyleSum, "Tổng cộng", currRow, i);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		HSSFCellStyle cellStyleSign = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, false);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);
		currColumn = C;
		mergedRegion(sh, currRow, currRow, currColumn, K);
		createCell(
				rh,
				cellStyleSign,
				"Tổng số giờ qui đổi của người kê khai (I) + (II) = .....................................................",
				currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = H;
		mergedRegion(sh, currRow, currRow, currColumn, K);
		createCell(rh, cellStyleSign,
				" Hà Nội, ngày ….... tháng ….... năm …......", currRow,
				currColumn);

		currRow++;
		rh = sh.createRow(currRow);

		currColumn = C;
		createCell(rh, cellStyleSign, "Xác nhận của Khoa (Viện , TT)", currRow,
				currColumn);

		currColumn = F;
		createCell(rh, cellStyleSign, "Xác nhận của Bộ môn", currRow,
				currColumn);

		currColumn = J;
		createCell(rh, cellStyleSign, "Người kê khai", currRow, currColumn);
	}

	public static int createContentTablePaperKV03(Sheet sh,
			List<GenericValue> data,
			HashMap<GenericValue, Integer> mPaper2NbIntAuthors,
			HSSFCellStyle cellStyle, int firstRow, int firstColumn,
			Map<String, Object> mPaper2AuthorInfo) {
		int currRow = firstRow;
		List<List<String>> papers = new ArrayList<List<String>>();

		// add row null
		if (data.size() == 0) {
			List<String> paper = new ArrayList<String>();
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			papers.add(paper);
		}

		for (int i = 0; i < data.size(); i++) {
			GenericValue p = data.get(i);
			List<String> paper = new ArrayList<String>();

			int nbAuthors = 0;
			if (p.getString("authors") != null) {
				String[] s = p.getString("authors").split(",");
				nbAuthors = s.length;
			}
			int nbIntAuthors = mPaper2NbIntAuthors.get(p);

			paper.add((i + 1) + ".");
			paper.add(p.getString("authors"));
			paper.add(p.getString("paperName"));
			paper.add(p.getString("journalConferenceName"));
			paper.add(p.getString("volumn"));
			paper.add(p.getString("ISSN"));
			paper.add(String.valueOf(nbAuthors));
			paper.add(String.valueOf(nbIntAuthors));

			String paperId = p.getString("paperId");
			Map<String, Object> info = (Map<String, Object>) mPaper2AuthorInfo
					.get(paperId);

			String firstAuthorIsCorresponding = "";
			String firstAuthorIsNotCorresponding = "";
			String correspondingAuthor = "";
			String nonCorrespondingAuthor = "";
			if (info != null) {
				firstAuthorIsCorresponding = (String) info
						.get("firstAuthorIsCorresponding");
				firstAuthorIsNotCorresponding = (String) info
						.get("firstAuthorIsNotCorresponding");
				correspondingAuthor = (String) info.get("correspondingAuthor");
				nonCorrespondingAuthor = (String) info
						.get("nonCorrespondingAuthor");
			}
			if (firstAuthorIsCorresponding.equals("T"))
				firstAuthorIsCorresponding = "X";
			else
				firstAuthorIsCorresponding = "";
			if (firstAuthorIsNotCorresponding.equals("T"))
				firstAuthorIsNotCorresponding = "X";
			else
				firstAuthorIsNotCorresponding = "";

			paper.add(firstAuthorIsCorresponding);
			paper.add(firstAuthorIsNotCorresponding);
			paper.add(correspondingAuthor);
			paper.add(nonCorrespondingAuthor);

			papers.add(paper);
		}
		currRow = createContentTable(sh, papers, cellStyle, currRow,
				firstColumn);

		return currRow;
	}

	public static int createContentTablePaper01CN(Sheet sh,
			List<GenericValue> data,
			HashMap<GenericValue, Integer> mPaper2NbIntAuthors,
			HSSFCellStyle cellStyle, int firstRow, int firstColumn) {
		int currRow = firstRow;
		List<List<String>> papers = new ArrayList<List<String>>();

		// add row null
		if (data.size() == 0) {
			List<String> paper = new ArrayList<String>();
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			papers.add(paper);
		}

		for (int i = 0; i < data.size(); i++) {
			GenericValue p = data.get(i);
			List<String> paper = new ArrayList<String>();

			int nbAuthors = 0;
			if (p.getString("authors") != null) {
				String[] s = p.getString("authors").split(",");
				nbAuthors = s.length;
			}
			int nbIntAuthors = mPaper2NbIntAuthors.get(p);
			paper.add((i + 1) + ".");
			paper.add(p.getString("authors"));
			paper.add(p.getString("paperName"));
			paper.add(p.getString("journalConferenceName"));
			paper.add(p.getString("volumn"));
			paper.add(p.getString("ISSN"));
			paper.add(String.valueOf(nbAuthors));
			paper.add(String.valueOf(nbIntAuthors));

			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			paper.add("");
			papers.add(paper);
		}
		currRow = createContentTable(sh, papers, cellStyle, currRow,
				firstColumn);

		return currRow;
	}

	public static void createSheetPaper01CN(HSSFWorkbook wb,
			List<GenericValue> lst_isi_papers1,
			List<GenericValue> lst_isi_papers2,
			List<GenericValue> lst_scopus_papers1,
			List<GenericValue> lst_scopus_papers2,
			List<GenericValue> lst_other_international_journal_papers1,
			List<GenericValue> lst_other_international_journal_papers2,
			List<GenericValue> lst_domestic_journal_papers1,
			List<GenericValue> lst_domestic_journal_papers2,
			List<GenericValue> lst_international_conference_papers1,
			List<GenericValue> lst_international_conference_papers2,
			List<GenericValue> lst_domestic_conference_papers1,
			List<GenericValue> lst_domestic_conference_papers2,
			String academicYearId,
			HashMap<GenericValue, Integer> mPaper2NbIntAuthors,
			List<GenericValue> user) {
		String[] year = academicYearId.split("-");
		GenericValue u;
		if (user == null) {
			return;
		} else {
			u = user.get(0);
		}
		int numColumns = 15; // A -> O
		int[] widthColumns = { 400, 1600, 5000, 6000, 4000, 4000, 3000, 2000,
				2000, 4000, 4000, 4000, 2000, 2000, 2000 };
		int currRow = 0;
		int currColumn = A; // A

		// font Sheet
		String fontName = "Times New Roman";

		Sheet sh = wb.createSheet("Bài báo 01-CN");
		Row rh;
		Cell ch;

		for (int i = 0; i < widthColumns.length; ++i) {
			sh.setColumnWidth(i, widthColumns[i]);
		}

		// Title
		HSSFCellStyle cellStyleTitle = createCellStyle(wb, (short) 12,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_NONE);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		String title = "BẢNG KÊ KHAI KHỐI LƯỢNG NGHIÊN CỨU KHOA HỌC  NĂM HỌC "
				+ year[0] + " - " + year[1];
		mergedRegion(sh, currRow, currRow, currColumn, G);
		createCell(rh, cellStyleTitle, title, currRow, currColumn);

		// Note
		HSSFCellStyle cellStyleNote = createCellStyle(wb, (short) 11,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_CENTER);

		currColumn = M;
		createCell(rh, cellStyleNote, "Mẫu 01- CN\nDùng cho cá nhân", currRow,
				currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = A;

		// Subtitle
		HSSFCellStyle cellStyleSubTitle = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT);

		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, G);
		createCell(rh, cellStyleSubTitle, "CÁC BÀI BÁO KHOA HỌC", currRow,
				currColumn);

		// Merge Note
		mergedRegion(wb, sh, currRow - 1, currRow, M, O, CellStyle.BORDER_THIN);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);

		// Information
		HSSFCellStyle cellStyleInformation = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT);

		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, E);
		createCell(rh, cellStyleInformation,
				"Họ và tên cán bộ: " + u.getString("staffName"), currRow,
				currColumn);

		String telephone = "Tel: (CQ) - (NR) - (DĐ)";
		if (u.getString("departmentName") != null) {
			telephone = "Tel: (CQ) - (NR) - (DĐ) " + u.getString("staffPhone");
		}
		currColumn = F;
		mergedRegion(sh, currRow, currRow, currColumn, O);
		createCell(rh, cellStyleInformation, telephone, currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);

		String departmentName = "Bộ môn: ";
		if (u.getString("departmentName") != null) {
			departmentName = "Bộ môn: " + u.getString("departmentName");
		}
		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, E);
		createCell(rh, cellStyleInformation, departmentName, currRow,
				currColumn);

		currColumn = F;
		mergedRegion(sh, currRow, currRow, currColumn, O);
		createCell(rh, cellStyleInformation,
				"Khoa (Viện, Trung tâm): " + u.getString("facultyName"),
				currRow, currColumn);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);

		// Table content
		HSSFCellStyle cellStyleHeaderTable = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				CellStyle.BORDER_THIN);

		HSSFCellStyle cellStyleHeaderTableNote = createCellStyle(wb,
				(short) 10, HSSFFont.BOLDWEIGHT_NORMAL, true,
				HSSFCellStyle.ALIGN_CENTER, CellStyle.BORDER_THIN);

		currColumn = B;
		createCell(rh, cellStyleHeaderTable, "STT", currRow, currColumn);

		currColumn = C;
		createCell(rh, cellStyleHeaderTable,
				"Họ và tên các tác giả, đơn vị\n(ghi chi tiết)", currRow,
				currColumn);

		currColumn = D;
		createCell(rh, cellStyleHeaderTable, "Tên bài báo", currRow, currColumn);

		currColumn = E;
		mergedRegion(wb, sh, currRow, currRow, currColumn, G,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleHeaderTable, "Tạp chí, Proceedings", currRow,
				currColumn);

		currColumn = H;
		createCell(rh, cellStyleHeaderTable, "Tổng số tác giả", currRow,
				currColumn);

		currColumn = I;
		createCell(rh, cellStyleHeaderTable, "Số tác giả của trường", currRow,
				currColumn);

		currColumn = J;
		createCell(rh, cellStyleHeaderTable,
				"Tác giả  đầu tiên là Corresponding author", currRow,
				currColumn);
		currColumn = K;
		createCell(rh, cellStyleHeaderTable,
				"Tác giả  đầu tiên không là Corresponding author", currRow,
				currColumn);
		currColumn = L;
		createCell(rh, cellStyleHeaderTable, "Tác giả là Corresponding author",
				currRow, currColumn);
		currColumn = M;
		createCell(rh, cellStyleHeaderTable, "Các tác giả còn lại", currRow,
				currColumn);

		currColumn = N;
		createCell(rh, cellStyleHeaderTable, "Số giờ quy đổi  của bài báo",
				currRow, currColumn);

		currColumn = O;
		createCell(rh, cellStyleHeaderTable, "Số giờ  quy đổi của người",
				currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		mergedRegion(wb, sh, currRow - 1, currRow, B, B, CellStyle.BORDER_THIN);
		mergedRegion(wb, sh, currRow - 1, currRow, C, C, CellStyle.BORDER_THIN);
		mergedRegion(wb, sh, currRow - 1, currRow, D, D, CellStyle.BORDER_THIN);

		currColumn = E;
		createCell(rh, cellStyleHeaderTable, "Tên tạp chí, Proceedings",
				currRow, currColumn);
		currColumn = F;
		createCell(rh, cellStyleHeaderTable,
				"Số và thời gian xuất bản chính thức", currRow, currColumn);
		currColumn = G;
		createCell(rh, cellStyleHeaderTable, "Chỉ số ISSN", currRow, currColumn);

		mergedRegion(wb, sh, currRow - 1, currRow, H, H, CellStyle.BORDER_THIN);
		mergedRegion(wb, sh, currRow - 1, currRow, I, I, CellStyle.BORDER_THIN);

		currColumn = J;
		mergedRegion(wb, sh, currRow, currRow, currColumn, M,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleHeaderTableNote,
				"Đánh dấu \"x\" vào ô tương ứng", currRow, currColumn);

		mergedRegion(wb, sh, currRow - 1, currRow, N, N, CellStyle.BORDER_THIN);
		mergedRegion(wb, sh, currRow - 1, currRow, O, O, CellStyle.BORDER_THIN);

		currRow++;
		rh = sh.createRow(currRow);

		// index columns
		currColumn = B;
		createCell(rh, cellStyleHeaderTable, "1", currRow, currColumn);
		currColumn = C;
		createCell(rh, cellStyleHeaderTable, "2", currRow, currColumn);
		currColumn = D;
		createCell(rh, cellStyleHeaderTable, "3", currRow, currColumn);
		currColumn = E;
		createCell(rh, cellStyleHeaderTable, "4", currRow, currColumn);
		currColumn = F;
		createCell(rh, cellStyleHeaderTable, "5", currRow, currColumn);
		currColumn = G;
		createCell(rh, cellStyleHeaderTable, "6", currRow, currColumn);
		currColumn = H;
		createCell(rh, cellStyleHeaderTable, "", currRow, currColumn);
		currColumn = I;
		createCell(rh, cellStyleHeaderTable, "", currRow, currColumn);
		currColumn = J;
		createCell(rh, cellStyleHeaderTable, "", currRow, currColumn);
		currColumn = K;
		createCell(rh, cellStyleHeaderTable, "", currRow, currColumn);
		currColumn = L;
		createCell(rh, cellStyleHeaderTable, "", currRow, currColumn);
		currColumn = M;
		createCell(rh, cellStyleHeaderTable, "", currRow, currColumn);
		currColumn = N;
		createCell(rh, cellStyleHeaderTable, "8", currRow, currColumn);
		currColumn = O;
		createCell(rh, cellStyleHeaderTable, "9", currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);

		// content
		HSSFCellStyle cellStyleSection = createCellStyle(wb, (short) 9,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_THIN, HSSFColor.YELLOW.index);
		HSSFCellStyle cellStyleSectionIndex = createCellStyle(wb, (short) 9,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				CellStyle.BORDER_THIN, HSSFColor.YELLOW.index);

		HSSFCellStyle cellStyleSubSection = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_THIN);
		HSSFCellStyle cellStyleSubSectionIndex = createCellStyle(wb,
				(short) 10, HSSFFont.BOLDWEIGHT_BOLD,
				HSSFCellStyle.ALIGN_CENTER, CellStyle.BORDER_THIN);

		HSSFCellStyle cellStyleContent = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_THIN);

		// Section A
		currColumn = B;
		createCell(rh, cellStyleSectionIndex, "A", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleSection,
				"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/7/"
						+ year[0] + " - 31/12/" + year[0], currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSubSectionIndex, "I.", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(
				rh,
				cellStyleSubSection,
				"Các bài báo đăng trong tạp chí thuộc danh mục SCI và SCIE (ISI):",
				currRow, currColumn);

		currRow = createContentTablePaper01CN(sh, lst_isi_papers1,
				mPaper2NbIntAuthors, cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSubSectionIndex, "II.", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleSubSection,
				"Các bài báo đăng trong tạp chí thuộc danh mục SCOPUS:",
				currRow, currColumn);

		currRow = createContentTablePaper01CN(sh, lst_scopus_papers1,
				mPaper2NbIntAuthors, cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSubSectionIndex, "III.", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleSubSection,
				"Các bài báo đăng trong tạp chí trong và ngoài nước:", currRow,
				currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		for (int i = B; i <= O; ++i) {
			if (i == C) {
				HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
				Font fontNoBold = wb.createFont();
				Font fontBold = wb.createFont();
				fontBold.setFontHeightInPoints((short) 10);
				fontBold.setFontName("Times New Roman");
				fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				fontBold.setColor(HSSFColor.BLACK.index);
				fontNoBold.setFontHeightInPoints((short) 10);
				fontNoBold.setFontName("Times New Roman");
				fontNoBold.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				fontNoBold.setColor(HSSFColor.BLACK.index);
				String text = "Ngoài nước (Không kê lại các bài ở mục I, II):";
				HSSFRichTextString value = new HSSFRichTextString(text);
				value.applyFont(0, 10, fontBold);
				value.applyFont(11, text.length() - 1, fontNoBold);

				cellStyle.setWrapText(true);
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);

				Cell chx = rh.createCell(i);
				chx.setCellStyle(cellStyle);
				chx.setCellValue(value);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		currRow = createContentTablePaper01CN(sh,
				lst_other_international_journal_papers1, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		for (int i = B; i <= O; ++i) {
			if (i == C) {
				createCell(rh, cellStyleSubSection, "Trong nước", currRow, i);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		currRow = createContentTablePaper01CN(sh, lst_domestic_journal_papers1,
				mPaper2NbIntAuthors, cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSubSectionIndex, "IV.", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(
				rh,
				cellStyleSubSection,
				"Các bài báo đăng trong kỷ yếu Hội nghị  khoa học trong và ngoài nước:",
				currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		for (int i = B; i <= O; ++i) {
			if (i == C) {
				createCell(rh, cellStyleSubSection, "Ngoài nước", currRow, i);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		currRow = createContentTablePaper01CN(sh,
				lst_international_conference_papers1, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		for (int i = B; i <= O; ++i) {
			if (i == C) {
				createCell(rh, cellStyleSubSection, "Trong nước", currRow, i);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		currRow = createContentTablePaper01CN(sh,
				lst_domestic_conference_papers1, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		// Section B
		currColumn = B;
		createCell(rh, cellStyleSectionIndex, "A", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleSection,
				"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/1/"
						+ year[1] + " - 30/06/" + year[1], currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSubSectionIndex, "I.", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(
				rh,
				cellStyleSubSection,
				"Các bài báo đăng trong tạp chí thuộc danh mục SCI và SCIE (ISI):",
				currRow, currColumn);

		currRow = createContentTablePaper01CN(sh, lst_isi_papers2,
				mPaper2NbIntAuthors, cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSubSectionIndex, "II.", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleSubSection,
				"Các bài báo đăng trong tạp chí thuộc danh mục SCOPUS:",
				currRow, currColumn);

		currRow = createContentTablePaper01CN(sh, lst_scopus_papers2,
				mPaper2NbIntAuthors, cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSubSectionIndex, "III.", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleSubSection,
				"Các bài báo đăng trong tạp chí trong và ngoài nước:", currRow,
				currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		for (int i = B; i <= O; ++i) {
			if (i == C) {
				HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
				Font fontNoBold = wb.createFont();
				Font fontBold = wb.createFont();
				fontBold.setFontHeightInPoints((short) 10);
				fontBold.setFontName("Times New Roman");
				fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				fontBold.setColor(HSSFColor.BLACK.index);
				fontNoBold.setFontHeightInPoints((short) 10);
				fontNoBold.setFontName("Times New Roman");
				fontNoBold.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				fontNoBold.setColor(HSSFColor.BLACK.index);
				String text = "Ngoài nước (Không kê lại các bài ở mục I, II):";
				HSSFRichTextString value = new HSSFRichTextString(text);
				value.applyFont(0, 10, fontBold);
				value.applyFont(11, text.length() - 1, fontNoBold);

				cellStyle.setWrapText(true);
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);

				Cell chx = rh.createCell(i);
				chx.setCellStyle(cellStyle);
				chx.setCellValue(value);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		currRow = createContentTablePaper01CN(sh,
				lst_other_international_journal_papers2, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		for (int i = B; i <= O; ++i) {
			if (i == C) {
				createCell(rh, cellStyleSubSection, "Trong nước", currRow, i);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		currRow = createContentTablePaper01CN(sh, lst_domestic_journal_papers2,
				mPaper2NbIntAuthors, cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSubSectionIndex, "IV.", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, O,
				CellStyle.BORDER_THIN);
		createCell(
				rh,
				cellStyleSubSection,
				"Các bài báo đăng trong kỷ yếu Hội nghị  khoa học trong và ngoài nước:",
				currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		for (int i = B; i <= O; ++i) {
			if (i == C) {
				createCell(rh, cellStyleSubSection, "Ngoài nước", currRow, i);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		currRow = createContentTablePaper01CN(sh,
				lst_international_conference_papers2, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B);

		currRow++;
		rh = sh.createRow(currRow);
		for (int i = B; i <= O; ++i) {
			if (i == C) {
				createCell(rh, cellStyleSubSection, "Trong nước", currRow, i);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		currRow = createContentTablePaper01CN(sh,
				lst_domestic_conference_papers2, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B);

		// Sum

		currRow++;
		rh = sh.createRow(currRow);
		for (int i = B; i <= O; ++i) {
			if (i == C) {
				createCell(rh, cellStyleSubSection, "Cộng", currRow, i);
			} else {
				createCell(rh, cellStyleContent, "", currRow, i);
			}
		}

		HSSFCellStyle cellStyleSign = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, false);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, O);
		createCell(
				rh,
				cellStyleSign,
				"Tổng số giờ qui đổi của người kê khai (A+B) = .....................................................",
				currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = G;
		mergedRegion(sh, currRow, currRow, currColumn, O);
		createCell(rh, cellStyleSign,
				" Hà Nội, ngày ….... tháng ….... năm …......", currRow,
				currColumn);

		currRow++;
		rh = sh.createRow(currRow);

		currColumn = C;
		createCell(rh, cellStyleSign, "Xác nhận của Khoa (Viện , TT)", currRow,
				currColumn);

		currColumn = F;
		createCell(rh, cellStyleSign, "Xác nhận của Bộ môn", currRow,
				currColumn);

		currColumn = M;
		createCell(rh, cellStyleSign, "Người kê khai", currRow, currColumn);

		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/7/"
		// + y[0] + " - 31/12/" + y[0]);
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Các bài báo đăng trong tạp chí thuộc danh mục SCI và SCIE (ISI):");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// for (int i = 0; i < lst_isi_papers1.size(); i++) {
		// GenericValue p = lst_isi_papers1.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Các bài báo đăng trong tạp chí thuộc danh mục SCOPUS:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// for (int i = 0; i < lst_scopus_papers1.size(); i++) {
		// GenericValue p = lst_scopus_papers1.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Các bài báo đăng trong tạp chí trong và ngoài nước:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Ngoài nước (Không kê lại các bài ở mục I, II):");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// for (int i = 0; i < lst_other_international_journal_papers1.size();
		// i++) {
		// GenericValue p = lst_other_international_journal_papers1.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Trong nước:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// for (int i = 0; i < lst_domestic_journal_papers1.size(); i++) {
		// GenericValue p = lst_domestic_journal_papers1.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Các bài báo đăng trong kỷ yếu Hội nghị  khoa học trong và ngoài nước:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Ngoài nước:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// for (int i = 0; i < lst_international_conference_papers1.size(); i++)
		// {
		// GenericValue p = lst_international_conference_papers1.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Trong nước:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		// for (int i = 0; i < lst_domestic_conference_papers1.size(); i++) {
		// GenericValue p = lst_domestic_conference_papers1.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		//
		// // second part
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/1/"
		// + y[1] + " - 30/06/" + y[1]);
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Các bài báo đăng trong tạp chí thuộc danh mục SCI và SCIE (ISI):");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// for (int i = 0; i < lst_isi_papers2.size(); i++) {
		// GenericValue p = lst_isi_papers2.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Các bài báo đăng trong tạp chí thuộc danh mục SCOPUS:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// for (int i = 0; i < lst_scopus_papers2.size(); i++) {
		// GenericValue p = lst_scopus_papers2.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Các bài báo đăng trong tạp chí trong và ngoài nước:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Ngoài nước (Không kê lại các bài ở mục I, II):");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// for (int i = 0; i < lst_other_international_journal_papers2.size();
		// i++) {
		// GenericValue p = lst_other_international_journal_papers2.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Trong nước:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// for (int i = 0; i < lst_domestic_journal_papers2.size(); i++) {
		// GenericValue p = lst_domestic_journal_papers2.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Các bài báo đăng trong kỷ yếu Hội nghị  khoa học trong và ngoài nước:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Ngoài nước:");
		// for (int i = 0; i < lst_international_conference_papers2.size(); i++)
		// {
		// GenericValue p = lst_international_conference_papers2.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }
		//
		// i_row++;
		// rh = sh.createRow(i_row);
		// ch = rh.createCell(1);
		// ch.setCellStyle(styleTitle);
		// ch.setCellValue("Trong nước:");
		// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1,
		// nbColumns));
		// for (int i = 0; i < lst_domestic_conference_papers2.size(); i++) {
		// GenericValue p = lst_domestic_conference_papers2.get(i);
		// i_row++;
		// createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		// }

	}

	public static void createSheetPaperKV03(HSSFWorkbook wb,
			List<GenericValue> lst_other_international_journal_papers1,
			List<GenericValue> lst_other_international_journal_papers2,
			List<GenericValue> lst_domestic_journal_papers1,
			List<GenericValue> lst_domestic_journal_papers2,
			List<GenericValue> lst_international_conference_papers1,
			List<GenericValue> lst_international_conference_papers2,
			List<GenericValue> lst_domestic_conference_papers1,
			List<GenericValue> lst_domestic_conference_papers2,
			String academicYearId, String facultyName,
			HashMap<GenericValue, Integer> mPaper2NbIntAuthors,
			Map<String, Object> mPaper2AuthorInfo) {

		String[] year = academicYearId.split("-");

		int numColumns = 13; // A -> M
		int[] widthColumns = { 400, 1600, 5000, 6000, 4000, 4000, 3000, 2000,
				2000, 4000, 4000, 4000, 2000 };
		int currRow = 0;
		int currColumn = A; // A

		// font Sheet
		String fontName = "Times New Roman";

		Sheet sh = wb.createSheet("KV03");
		Row rh;
		Cell ch;

		for (int i = 0; i < widthColumns.length; ++i) {
			sh.setColumnWidth(i, widthColumns[i]);
		}
		currRow++;
		rh = sh.createRow(currRow);
		// Note
		HSSFCellStyle cellStyleNote = createCellStyle(wb, (short) 11,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_CENTER);

		currColumn = J;
		createCell(rh, cellStyleNote, "Mẫu 03-KV\nDùng cho Khoa/Viện/TT",
				currRow, currColumn);
		// Merge Note
		mergedRegion(wb, sh, currRow, currRow + 1, J, K, CellStyle.BORDER_THIN);

		// Title
		HSSFCellStyle cellStyleTitle = createCellStyle(wb, (short) 12,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				CellStyle.BORDER_NONE);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		String title = "DANH MỤC BÀI BÁO ĐĂNG TRONG TẠP CHÍ VÀ KỶ YẾU HỘI NGHỊ KHOA HỌC\nCỦA CÁN BỘ TRƯỜNG ĐẠI HỌC BÁCH KHOA HÀ NỘI NĂM HỌC "
				+ year[0] + " - " + year[1];
		mergedRegion(sh, currRow, currRow, currColumn, M);
		createCell(rh, cellStyleTitle, title, currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = A;

		// Subtitle
		HSSFCellStyle cellStyleSubTitle = createCellStyle(wb, (short) 12,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT);

		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, G);
		createCell(rh, cellStyleSubTitle, "Khoa/Viện: " + facultyName, currRow,
				currColumn);

		currRow++;
		rh = sh.createRow(currRow);

		// Table content
		HSSFCellStyle cellStyleHeaderTable = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				CellStyle.BORDER_THIN);

		HSSFCellStyle cellStyleHeaderTableNote = createCellStyle(wb,
				(short) 10, HSSFFont.BOLDWEIGHT_NORMAL, true,
				HSSFCellStyle.ALIGN_CENTER, CellStyle.BORDER_THIN);

		currColumn = B;
		createCell(rh, cellStyleHeaderTable, "STT", currRow, currColumn);

		currColumn = C;
		createCell(rh, cellStyleHeaderTable,
				"Họ và tên các tác giả, đơn vị\n(ghi chi tiết)", currRow,
				currColumn);

		currColumn = D;
		createCell(rh, cellStyleHeaderTable, "Tên bài báo", currRow, currColumn);

		currColumn = E;
		mergedRegion(wb, sh, currRow, currRow, currColumn, G,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleHeaderTable, "Tạp chí, Proceedings", currRow,
				currColumn);

		currColumn = H;
		createCell(rh, cellStyleHeaderTable, "Tổng số tác giả", currRow,
				currColumn);

		currColumn = I;
		createCell(rh, cellStyleHeaderTable, "Số tác giả của trường", currRow,
				currColumn);

		currColumn = J;
		createCell(rh, cellStyleHeaderTable,
				"Tác giả  đầu tiên là Corresponding author", currRow,
				currColumn);
		currColumn = K;
		createCell(rh, cellStyleHeaderTable,
				"Tác giả  đầu tiên không là Corresponding author", currRow,
				currColumn);
		currColumn = L;
		createCell(rh, cellStyleHeaderTable, "Tác giả là Corresponding author",
				currRow, currColumn);
		currColumn = M;
		createCell(rh, cellStyleHeaderTable, "Các tác giả còn lại", currRow,
				currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		mergedRegion(wb, sh, currRow - 1, currRow, B, B, CellStyle.BORDER_THIN);
		mergedRegion(wb, sh, currRow - 1, currRow, C, C, CellStyle.BORDER_THIN);
		mergedRegion(wb, sh, currRow - 1, currRow, D, D, CellStyle.BORDER_THIN);

		currColumn = E;
		createCell(rh, cellStyleHeaderTable, "Tên tạp chí, Proceedings",
				currRow, currColumn);
		currColumn = F;
		createCell(rh, cellStyleHeaderTable,
				"Số và thời gian xuất bản chính thức", currRow, currColumn);
		currColumn = G;
		createCell(rh, cellStyleHeaderTable, "Chỉ số ISSN", currRow, currColumn);

		mergedRegion(wb, sh, currRow - 1, currRow, H, H, CellStyle.BORDER_THIN);
		mergedRegion(wb, sh, currRow - 1, currRow, I, I, CellStyle.BORDER_THIN);

		currColumn = J;
		mergedRegion(wb, sh, currRow, currRow, currColumn, M,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleHeaderTableNote,
				"Đánh dấu \"x\" vào ô tương ứng", currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);

		// content
		HSSFCellStyle cellStyleSection = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_THIN);
		HSSFCellStyle cellStyleSectionIndex = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				CellStyle.BORDER_THIN);

		HSSFCellStyle cellStyleSubSection = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_THIN);
		HSSFCellStyle cellStyleSubSectionIndex = createCellStyle(wb,
				(short) 10, HSSFFont.BOLDWEIGHT_BOLD,
				HSSFCellStyle.ALIGN_CENTER, CellStyle.BORDER_THIN);

		HSSFCellStyle cellStyleContent = createCellStyle(wb, (short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				CellStyle.BORDER_THIN);

		// Section A
		currColumn = B;
		createCell(rh, cellStyleSectionIndex, "I", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, M,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleSection,
				"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/7/"
						+ year[0] + " - 31/12/" + year[0], currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCellNull(rh, cellStyleSubSectionIndex, B, C);

		currColumn = D;
		HSSFRichTextString subSectionTitle1 = createRickText(
				wb,
				cellStyleSubSection,
				"Bài báo đăng trong tạp chí nước ngoài (Không kê các bài báo trong danh mục SCI, SCIE, SCOPUS)",
				new int[] { 0, 37 });
		createCell(rh, cellStyleSubSection, subSectionTitle1, currRow,
				currColumn);
		createCellNull(rh, cellStyleSubSection, E, M);

		currRow = createContentTablePaperKV03(sh,
				lst_other_international_journal_papers1, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B, mPaper2AuthorInfo);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCellNull(rh, cellStyleSubSectionIndex, B, C);

		currColumn = D;
		String subSectionTitle2 = "Bài báo đăng trong tạp chí trong nước";
		createCell(rh, cellStyleSubSection, subSectionTitle2, currRow,
				currColumn);
		createCellNull(rh, cellStyleSubSection, E, M);

		currRow = createContentTablePaperKV03(sh, lst_domestic_journal_papers1,
				mPaper2NbIntAuthors, cellStyleContent, currRow + 1, B,
				mPaper2AuthorInfo);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCellNull(rh, cellStyleSubSectionIndex, B, C);

		currColumn = D;
		String subSectionTitle3 = "Bài báo đăng trong kỷ yếu hội nghị quốc tế có phản biện (Proceedings)";
		createCell(rh, cellStyleSubSection, subSectionTitle3, currRow,
				currColumn);
		createCellNull(rh, cellStyleSubSection, E, M);

		currRow = createContentTablePaperKV03(sh,
				lst_international_conference_papers1, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B, mPaper2AuthorInfo);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCellNull(rh, cellStyleSubSectionIndex, B, C);

		currColumn = D;
		String subSectionTitle4 = "Bài báo đăng trong kỷ yếu hội nghị trong nước có phản biện (Proceedings)";
		createCell(rh, cellStyleSubSection, subSectionTitle4, currRow,
				currColumn);
		createCellNull(rh, cellStyleSubSection, E, M);

		currRow = createContentTablePaperKV03(sh,
				lst_domestic_conference_papers1, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B, mPaper2AuthorInfo);

		// Section B
		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSectionIndex, "II", currRow, currColumn);

		currColumn = C;
		mergedRegion(wb, sh, currRow, currRow, currColumn, M,
				CellStyle.BORDER_THIN);
		createCell(rh, cellStyleSection,
				"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/1/"
						+ year[1] + " - 30/6/" + year[1], currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCellNull(rh, cellStyleSubSectionIndex, B, C);

		currColumn = D;
		createCell(rh, cellStyleSubSection, subSectionTitle1, currRow,
				currColumn);
		createCellNull(rh, cellStyleSubSection, E, M);

		currRow = createContentTablePaperKV03(sh,
				lst_other_international_journal_papers2, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B, mPaper2AuthorInfo);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCellNull(rh, cellStyleSubSectionIndex, B, C);

		currColumn = D;
		createCell(rh, cellStyleSubSection, subSectionTitle2, currRow,
				currColumn);
		createCellNull(rh, cellStyleSubSection, E, M);

		currRow = createContentTablePaperKV03(sh, lst_domestic_journal_papers2,
				mPaper2NbIntAuthors, cellStyleContent, currRow + 1, B,
				mPaper2AuthorInfo);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCellNull(rh, cellStyleSubSectionIndex, B, C);

		currColumn = D;
		createCell(rh, cellStyleSubSection, subSectionTitle3, currRow,
				currColumn);
		createCellNull(rh, cellStyleSubSection, E, M);

		currRow = createContentTablePaperKV03(sh,
				lst_international_conference_papers2, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B, mPaper2AuthorInfo);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCellNull(rh, cellStyleSubSectionIndex, B, C);

		currColumn = D;
		createCell(rh, cellStyleSubSection, subSectionTitle4, currRow,
				currColumn);
		createCellNull(rh, cellStyleSubSection, E, M);

		currRow = createContentTablePaperKV03(sh,
				lst_domestic_conference_papers2, mPaper2NbIntAuthors,
				cellStyleContent, currRow + 1, B, mPaper2AuthorInfo);

		HSSFCellStyle cellStyleSignTitle = createCellStyle(wb, (short) 11,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER, false);
		HSSFCellStyle cellStyleSignDate = createCellStyle(wb, (short) 11,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_CENTER, false);
		HSSFCellStyle cellStyleSignContent = createCellStyle(wb, (short) 11,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT, true);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = H;
		mergedRegion(sh, currRow, currRow, currColumn, M);
		createCell(rh, cellStyleSignDate, "Ngày     tháng     năm 201",
				currRow, currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		mergedRegion(sh, currRow, currRow, currColumn, D);
		createCell(rh, cellStyleSignTitle, "PHÒNG KHOA HỌC CÔNG NGHỆ", currRow,
				currColumn);

		currColumn = H;
		mergedRegion(sh, currRow, currRow, currColumn, M);
		createCell(rh, cellStyleSignTitle, "LÃNH ĐẠO KHOA/VIỆN", currRow,
				currColumn);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(
				rh,
				createCellStyle(wb, (short) 11, HSSFFont.BOLDWEIGHT_NORMAL,
						true, Font.U_SINGLE, HSSFCellStyle.ALIGN_LEFT,
						HSSFCellStyle.BORDER_NONE), "Ghi chú:", currRow,
				currColumn);
		mergedRegion(sh, currRow, currRow, currColumn, F);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(
				rh,
				cellStyleSignContent,
				"- Căn cứ vào kê khai của từng cá nhân các Khoa/Viện tổng hợp theo mẫu.",
				currRow, currColumn);
		mergedRegion(sh, currRow, currRow, currColumn, F);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(
				rh,
				cellStyleSignContent,
				"- Đối với bài báo do nhiều tác giả trong cùng Khoa/Viện viết chung thì chỉ tổng hợp 1 lần.",
				currRow, currColumn);
		mergedRegion(sh, currRow, currRow, currColumn, F);

		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(
				rh,
				cellStyleSignContent,
				"- Các đơn vị gửi minh chứng bài báo khoa học kèm theo danh mục này.\nMinh chứng đầy đủ theo hướng dẫn tại mục 2.1 công văn.",
				currRow, currColumn);
		mergedRegion(sh, currRow, currRow + 1, currColumn, F);

		currRow++;
		currRow++;
		rh = sh.createRow(currRow);
		currColumn = B;
		createCell(rh, cellStyleSignContent,
				"- Chỉ chấp nhận bài có đầy đủ thông tin tại các cột", currRow,
				currColumn);
		mergedRegion(sh, currRow, currRow, currColumn, F);

	}

	public static int getNbInternalAuthors(Delegator delegator, String paperId) {
		// return the number of co-authors which is the member of the university
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			List<GenericValue> lst = delegator.findList(
					"StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			return lst.size();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public static HSSFWorkbook createExcelForm01CN02CN(Delegator delegator,
			String academicYearId, String staffId) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			List<GenericValue> all_papers = getPapersOfStaffAcademicYear(
					delegator, staffId, academicYearId);

			List<Map<String, Object>> researchProject = getResearchProjectOfStaffAcademicYear(
					delegator, staffId, academicYearId);

			List<GenericValue> user = delegator.findList("StaffView",
					EntityCondition.makeCondition("staffId",
							EntityOperator.EQUALS, staffId), null, null, null,
					false);

			HashMap<GenericValue, Integer> mPaper2NbIntAuthors = new HashMap<GenericValue, Integer>();
			for (GenericValue p : all_papers) {
				int nbauthors = getNbInternalAuthors(delegator,
						p.getString("paperId"));
				mPaper2NbIntAuthors.put(p, nbauthors);
			}

			List<GenericValue> lst_isi_papers1 = FastList.newInstance();
			List<GenericValue> lst_isi_papers2 = FastList.newInstance();
			List<GenericValue> lst_scopus_papers1 = FastList.newInstance();
			List<GenericValue> lst_scopus_papers2 = FastList.newInstance();
			List<GenericValue> lst_internaltional_journal_papers1 = FastList
					.newInstance();
			List<GenericValue> lst_internaltional_journal_papers2 = FastList
					.newInstance();
			List<GenericValue> lst_domestic_journal_papers1 = FastList
					.newInstance();
			List<GenericValue> lst_domestic_journal_papers2 = FastList
					.newInstance();
			List<GenericValue> lst_international_conference_papers1 = FastList
					.newInstance();
			List<GenericValue> lst_international_conference_papers2 = FastList
					.newInstance();
			List<GenericValue> lst_domestic_conference_papers1 = FastList
					.newInstance();
			List<GenericValue> lst_domestic_conference_papers2 = FastList
					.newInstance();

			Debug.log(module + "::createExcelForm01CN02CN, staffId = "
					+ staffId + ", year = " + academicYearId
					+ ", all_papers = " + all_papers.size());

			for (GenericValue p : all_papers) {
				String category = p.getString("paperCategoryId");
				long month = 0;
				if (p.getLong("month") != null)
					month = p.getLong("month");

				if (month >= 7 && month <= 12) {
					if (category.equals("JINT_SCI")
							|| category.equals("JINT_SCIE")) {
						lst_isi_papers1.add(p);
					} else if (category.equals("JINT_SCOPUS")) {
						lst_scopus_papers1.add(p);
					} else if (category.equals("JINT_Other")) {
						lst_internaltional_journal_papers1.add(p);
					} else if (category.equals("JDOM_Other")) {
						lst_domestic_journal_papers1.add(p);
					} else if (category.equals("CINT_Other")) {
						lst_international_conference_papers1.add(p);
					} else if (category.equals("CDOM_Other")) {
						lst_domestic_conference_papers1.add(p);
					}

				} else if (month >= 1 && month <= 6) {
					if (category.equals("JINT_SCI")
							|| category.equals("JINT_SCIE")) {
						lst_isi_papers2.add(p);
					} else if (category.equals("JINT_SCOPUS")) {
						lst_scopus_papers2.add(p);
					} else if (category.equals("JINT_Other")) {
						lst_internaltional_journal_papers2.add(p);
					} else if (category.equals("JDOM_Other")) {
						lst_domestic_journal_papers2.add(p);
					} else if (category.equals("CINT_Other")) {
						lst_international_conference_papers2.add(p);
					} else if (category.equals("CDOM_Other")) {
						lst_domestic_conference_papers2.add(p);
					}
				}
			}

			createSheetPaper01CN(wb, lst_isi_papers1, lst_isi_papers2,
					lst_scopus_papers1, lst_scopus_papers2,
					lst_internaltional_journal_papers1,
					lst_internaltional_journal_papers2,
					lst_domestic_journal_papers1, lst_domestic_journal_papers2,
					lst_international_conference_papers1,
					lst_international_conference_papers2,
					lst_domestic_conference_papers1,
					lst_domestic_conference_papers2, academicYearId,
					mPaper2NbIntAuthors, user);
			createSheetPaper02CN(wb, researchProject, user);

			return wb;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static long getHourProject(Delegator delegator, String staffId,
			String academicYearId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, academicYearId));
			List<GenericValue> lst = delegator.findList(
					"ResearchProjectDeclarationYear",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			long hour = 0;
			for (GenericValue p : lst) {
				if (p.getLong("workinghours") != null)
					hour += p.getLong("workinghours");
			}
			return hour;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static Map<String, Object> getAuthorInfos(Delegator delegator,
			String paperId) {
		Map<String, Object> retSucc = FastMap.newInstance();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			List<GenericValue> lst_ext = delegator.findList(
					"ExternalMemberPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			List<GenericValue> lst_int = delegator.findList("PapersStaffView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			String firstAuthorIsCorresponding = "F";
			String firstAuthorIsNotCorresponding = "F";
			String correspondingAuthor = "";
			String nonCorrespondingAuthor = "";
			for (GenericValue g : lst_ext) {
				if (g.getString("correspondingAuthor") != null
						&& g.getString("correspondingAuthor").equals("Y")) {
					if (g.getLong("sequence") != null
							&& g.getLong("sequence") == 1) {
						firstAuthorIsCorresponding = "T";
					}
					// else{
					// firstAuthorIsNotCorresponding = "T";
					// }
					if (g.getString("staffName") != null)
						correspondingAuthor += g.getString("staffName") + ", ";
				} else {
					if (g.getString("staffName") != null)
						nonCorrespondingAuthor += g.getString("staffName")
								+ ", ";
				}
			}

			for (GenericValue g : lst_int) {
				if (g.getString("correspondingAuthor") != null
						&& g.getString("correspondingAuthor").equals("Y")) {
					if (g.getLong("sequence") != null
							&& g.getLong("sequence") == 1) {
						firstAuthorIsCorresponding = "T";
					}
					// else{
					// firstAuthorIsNotCorresponding = "T";
					// }
					if (g.getString("staffName") != null)
						correspondingAuthor += g.getString("staffName") + ", ";
				} else {
					if (g.getString("staffName") != null)
						nonCorrespondingAuthor += g.getString("staffName")
								+ ", ";
				}
			}
			if (firstAuthorIsCorresponding.equals("T"))
				firstAuthorIsNotCorresponding = "F";
			else
				firstAuthorIsNotCorresponding = "T";

			retSucc.put("firstAuthorIsCorresponding",
					firstAuthorIsCorresponding);
			retSucc.put("firstAuthorIsNotCorresponding",
					firstAuthorIsNotCorresponding);
			retSucc.put("correspondingAuthor", correspondingAuthor);
			retSucc.put("nonCorrespondingAuthor", nonCorrespondingAuthor);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static void createExcelFormKV03(HSSFWorkbook wb,
			Delegator delegator, String academicYearId, String facultyId) {
		try {
			List<GenericValue> all_papers = getPaperOfFacultyAcademcYearId(
					delegator, facultyId, academicYearId);

			Map<String, Object> mPaper2AuthorInfo = FastMap.newInstance();
			for (GenericValue p : all_papers) {
				String paperId = p.getString("paperId");
				Map<String, Object> infos = getAuthorInfos(delegator, paperId);
				mPaper2AuthorInfo.put(paperId, infos);
			}

			// Sheet sh = wb.createSheet("KV03");
			List<GenericValue> lst_isi_papers1 = FastList.newInstance();
			List<GenericValue> lst_isi_papers2 = FastList.newInstance();
			List<GenericValue> lst_scopus_papers1 = FastList.newInstance();
			List<GenericValue> lst_scopus_papers2 = FastList.newInstance();
			List<GenericValue> lst_internaltional_journal_papers1 = FastList
					.newInstance();
			List<GenericValue> lst_internaltional_journal_papers2 = FastList
					.newInstance();
			List<GenericValue> lst_domestic_journal_papers1 = FastList
					.newInstance();
			List<GenericValue> lst_domestic_journal_papers2 = FastList
					.newInstance();
			List<GenericValue> lst_international_conference_papers1 = FastList
					.newInstance();
			List<GenericValue> lst_international_conference_papers2 = FastList
					.newInstance();
			List<GenericValue> lst_domestic_conference_papers1 = FastList
					.newInstance();
			List<GenericValue> lst_domestic_conference_papers2 = FastList
					.newInstance();

			HashMap<GenericValue, Integer> mPaper2NbIntAuthors = new HashMap<GenericValue, Integer>();
			for (GenericValue p : all_papers) {
				int nbauthors = getNbInternalAuthors(delegator,
						p.getString("paperId"));
				mPaper2NbIntAuthors.put(p, nbauthors);
			}

			for (GenericValue p : all_papers) {
				String category = p.getString("paperCategoryId");
				long month = 0;
				if (p.getLong("month") != null)
					month = p.getLong("month");

				if (month >= 7 && month <= 12) {
					if (category.equals("JINT_SCI")
							|| category.equals("JINT_SCIE")) {
						lst_isi_papers1.add(p);
					} else if (category.equals("JINT_SCOPUS")) {
						lst_scopus_papers1.add(p);
					} else if (category.equals("JINT_Other")) {
						lst_internaltional_journal_papers1.add(p);
					} else if (category.equals("JDOM_Other")) {
						lst_domestic_journal_papers1.add(p);
					} else if (category.equals("CINT_Other")) {
						lst_international_conference_papers1.add(p);
					} else if (category.equals("CDOM_Other")) {
						lst_domestic_conference_papers1.add(p);
					}

				} else if (month >= 1 && month <= 6) {
					if (category.equals("JINT_SCI")
							|| category.equals("JINT_SCIE")) {
						lst_isi_papers2.add(p);
					} else if (category.equals("JINT_SCOPUS")) {
						lst_scopus_papers2.add(p);
					} else if (category.equals("JINT_Other")) {
						lst_internaltional_journal_papers2.add(p);
					} else if (category.equals("JDOM_Other")) {
						lst_domestic_journal_papers2.add(p);
					} else if (category.equals("CINT_Other")) {
						lst_international_conference_papers2.add(p);
					} else if (category.equals("CDOM_Other")) {
						lst_domestic_conference_papers2.add(p);
					}

				}
			}

			String facultyName = getFacultyName(delegator, facultyId);

			createSheetPaperKV03(wb, lst_internaltional_journal_papers1,
					lst_internaltional_journal_papers2,
					lst_domestic_journal_papers1, lst_domestic_journal_papers2,
					lst_international_conference_papers1,
					lst_international_conference_papers2,
					lst_domestic_conference_papers1,
					lst_domestic_conference_papers2, academicYearId,
					facultyName, mPaper2NbIntAuthors, mPaper2AuthorInfo);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static HSSFWorkbook createExcelFormKV01(Delegator delegator,
			String academicYearId, String facultyId) {

		// get list of paper category hour-budget
		List<GenericValue> paperHourBudget = getPaperCategoryHourBudget(
				delegator, academicYearId);
		Map<String, Long> mPaperCategory2Hour = FastMap.newInstance();
		if (paperHourBudget != null) {
			for (GenericValue gv : paperHourBudget) {
				String cat = (String) gv.get("paperCategoryId");
				long h = (long) gv.get("hour");
				mPaperCategory2Hour.put(cat, h);
			}
		} else {
			Debug.log(module + "::createExcelFormKV01, hour-budget of year "
					+ academicYearId + " NOT EXISTS");
			;

		}

		Map<GenericValue, Long> mStaff2Hour = FastMap.newInstance();
		Map<GenericValue, Long> mStaff2Hour1 = FastMap.newInstance();
		Map<GenericValue, Long> mStaff2Hour2 = FastMap.newInstance();
		Map<GenericValue, Long> mStaff2HourProject = FastMap.newInstance();

		Map<GenericValue, List<GenericValue>> mDepartment2Staffs = FastMap
				.newInstance();

		/*
		 * String facultyName = ""; try{ GenericValue f =
		 * delegator.findOne("Faculty", UtilMisc.toMap("facultyId",facultyId),
		 * false); facultyName = f.getString("facultyName"); }catch(Exception
		 * ex){ ex.printStackTrace(); }
		 */

		// get list of departments of the given faculty
		List<GenericValue> departments = getDepartments(delegator, facultyId);
		for (GenericValue d : departments) {
			String deptId = (String) d.get("departmentId");
			List<GenericValue> staffs = getStaffsOfADeparment(delegator, deptId);
			Debug.log(module + "::createExcelFormKV01, staffs of department "
					+ deptId + " = " + staffs.size());

			mDepartment2Staffs.put(d, staffs);
			for (GenericValue st : staffs) {
				String staffId = (String) st.get("staffId");
				List<GenericValue> papers = getPapersOfStaffAcademicYear(
						delegator, staffId, academicYearId);

				long hour = 0;
				long hour1 = 0;
				long hour2 = 0;
				for (GenericValue p : papers) {
					String paperCategoryId = (String) p.get("paperCategoryId");
					long h = 0;
					Debug.log(module + "::createExcelFormKV01, paper "
							+ (String) p.get("paperName") + ", category = "
							+ paperCategoryId);
					if (mPaperCategory2Hour.get(paperCategoryId) != null) {
						h = (long) mPaperCategory2Hour.get(paperCategoryId);
						int sz = 1;
						String authors = (String) p.get("authors");
						if (authors != null) {
							String[] s = authors.split(",");
							if (s != null)
								sz = s.length;
						}
						h = h / sz;
					}
					long month = 0;
					if (p.getLong("month") != null)
						month = p.getLong("month");
					if (month >= 7 && month <= 12) {
						hour1 += h;
					} else if (month >= 1 && month <= 6) {
						hour2 += h;
					}
					hour += h;
				}
				mStaff2Hour.put(st, hour);
				mStaff2Hour1.put(st, hour1);
				mStaff2Hour2.put(st, hour2);

				long hourProject = getHourProject(delegator, staffId,
						academicYearId);
				mStaff2HourProject.put(st, hourProject);
				Debug.log(module + "::createExcelFormKV01, hour_project of "
						+ staffId + " = " + hourProject);
			}
		}

		Map<String, Object> mStaff2ProjectHours = getProjectHourOfStaffs(
				delegator, academicYearId);

		String[] Y = academicYearId.split("-");
		// start renderExcel

		HSSFWorkbook wb = new HSSFWorkbook();

		// sheet KV01
		Sheet sh = wb.createSheet("KV01");
		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 3000);
		sh.setColumnWidth(2, 12000);
		sh.setColumnWidth(3, 6000);
		sh.setColumnWidth(4, 6000);
		sh.setColumnWidth(5, 6000);
		sh.setColumnWidth(6, 6000);
		sh.setColumnWidth(7, 6000);
		int i_row = 0;

		// ----style font in excel
		HSSFCellStyle cellStyleBold = getFontBold(wb);
		HSSFCellStyle cellStyleLeft = getAttributeLeftFullBoder(wb);
		HSSFCellStyle cellStyleLeftBold = getAttributeLeftBoldFullBoder(wb);
		HSSFCellStyle cellStyleRight = getAttributeRightFullBoder(wb);
		HSSFCellStyle cellStyleCenterBoldFullBorder = getAttributeCenterBoldFullBorder(wb);
		// ----style font in excel
		// ----start header in excel
		i_row++;
		Row row_header = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));// merged columns
		String[] str_header = new String[] { "Bảng tổng hợp tính giờ chuẩn NCKH năm học "
				+ academicYearId + " của cán bộ trường ĐH Bách Khoa Hà Nội" };
		createRowInExcel(1, 1, str_header, row_header, cellStyleBold);
		// ----end header in excel

		// ----start title in excel
		i_row = i_row + 3;
		Row row_title = sh.createRow(i_row);
		String facultyName = getFacultyName(delegator, facultyId);
		String[] str_title = new String[] { "Khoa/Viện : " + facultyName };
		createRowInExcel(1, 1, str_title, row_title, cellStyleBold);
		// ----end title in excel

		// ----start header_table
		i_row = i_row + 4;
		// System.out.println(i_row);
		Row row_header_table = sh.createRow(i_row);
		String[] str_header_table = new String[] {
				"TT",
				"Họ và tên",
				"Tổng số giờ quy đổi từ bài báo tính trong khoảng thời gian 1/7/"
						+ Y[0] + "-31/12/" + Y[0],
				"Tổng số giờ quy đổi từ bài báo tính trong khoảng thời gian 1/1/"
						+ Y[1] + "-30/6/" + Y[1], "Tổng cộng (C+D)",
				"Tổng số giờ quy đổi từ đề tài NCKH",
				"Tổng cộng giờ NCKH năm học " + academicYearId + "(E+F)" };
		createRowInExcel(1, 7, str_header_table, row_header_table,
				cellStyleCenterBoldFullBorder);

		i_row++;
		row_header_table = sh.createRow(i_row);
		String[] str_ABCD = { "A", "B", "C", "D", "E", "F", "G" };
		createRowInExcel(1, 7, str_ABCD, row_header_table,
				cellStyleCenterBoldFullBorder);

		// ----end header_table
		long paper_hour1_faculty = 0;
		long paper_hour2_faculty = 0;
		long paper_hour_faculty = 0;
		long project_hour_faculty = 0;
		long total_hour_faculty = 0;
		for (GenericValue d : departments) {
			List<GenericValue> staffs = mDepartment2Staffs.get(d);
			long dept_hour1 = 0;
			long dept_hour2 = 0;
			long dept_hour_project = 0;
			long dept_hour_paper = 0;
			long dept_hour_total = 0;
			for (GenericValue st : staffs) {
				dept_hour1 += mStaff2Hour1.get(st);
				dept_hour2 += mStaff2Hour2.get(st);
				dept_hour_project += mStaff2HourProject.get(st);
			}
			dept_hour_paper = dept_hour1 + dept_hour2;
			dept_hour_total = dept_hour_paper + dept_hour_project;

			paper_hour1_faculty += dept_hour1;
			paper_hour2_faculty += dept_hour2;

			project_hour_faculty += dept_hour_project;
		}
		paper_hour_faculty = paper_hour1_faculty + paper_hour2_faculty;
		total_hour_faculty = paper_hour_faculty + project_hour_faculty;

		i_row++;
		String[] FN = { "", facultyName, paper_hour1_faculty + "",
				paper_hour2_faculty + "", paper_hour_faculty + "",
				project_hour_faculty + "", total_hour_faculty + "" };
		row_header_table = sh.createRow(i_row);

		createRowInExcel(1, 7, FN, row_header_table, cellStyleLeftBold);

		// ----start index of table in excel
		int s = 0;
		for (GenericValue d : departments) {
			List<GenericValue> staffs = mDepartment2Staffs.get(d);
			long dept_hour1 = 0;
			long dept_hour2 = 0;
			long dept_hour_project = 0;
			long dept_hour_paper = 0;
			long dept_hour_total = 0;
			for (GenericValue st : staffs) {
				dept_hour1 += mStaff2Hour1.get(st);
				dept_hour2 += mStaff2Hour2.get(st);
				dept_hour_project += mStaff2HourProject.get(st);
			}
			dept_hour_paper = dept_hour1 + dept_hour2;
			dept_hour_total = dept_hour_paper + dept_hour_project;

			i_row += 1;
			Row r = sh.createRow(i_row);
			String[] str_stt = new String[] { sSTT[s] };
			createRowInExcel(1, 1, str_stt, r, cellStyleCenterBoldFullBorder);
			s++;
			String deptName = (String) d.get("departmentName");
			String[] str_departmentName = new String[] { deptName };
			createRowInExcel(2, 2, str_departmentName, r, cellStyleLeftBold);
			String[] str_1 = new String[] { dept_hour1 + "", dept_hour2 + "",
					dept_hour_paper + "", dept_hour_project + "",
					dept_hour_total + "" };
			createRowInExcel(3, 7, str_1, r, cellStyleCenterBoldFullBorder);

			int count = 0;
			for (GenericValue st : staffs) {
				String staffName = (String) st.get("staffName");
				String staffId = st.getString("staffId");
				i_row += 1;
				count++;
				r = sh.createRow(i_row);
				String[] str_sttt = new String[] { "" + count };
				createRowInExcel(1, 1, str_sttt, r, cellStyleRight);
				String[] str_staffName = new String[] { staffName };
				createRowInExcel(2, 2, str_staffName, r, cellStyleLeft);
				// long paperHour = mStaff2Hour.get(st);
				// long projectHour = mStaff2ProjectHours.get(staffId) != null ?
				// (long) mStaff2ProjectHours
				// .get(staffId) : 0;
				long paper_hour1 = mStaff2Hour1.get(st);
				long paper_hour2 = mStaff2Hour2.get(st);
				long paper_hour = paper_hour1 + paper_hour2;
				long project_hour = mStaff2HourProject.get(st);
				long total_hour = paper_hour + project_hour;

				// long totalHour = paperHour + projectHour;
				String[] str_2 = new String[] { paper_hour1 + "",
						paper_hour2 + "", paper_hour + "", project_hour + "",
						total_hour + "" };

				Debug.log(module + "::createExcelFormKV01, staffId " + staffId
						+ ", project_hour = " + project_hour);

				createRowInExcel(3, 7, str_2, r, cellStyleRight);
			}
		}
		// ----end table in excel

		// ----start footer in excel
		i_row = i_row + 3;
		Row rowFooter = sh.createRow(i_row);
		Date timeCurrent = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeCurrent);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String[] str_footer1 = new String[] { "Ngày " + day + " tháng " + month
				+ " năm " + year };
		createRowInExcel(5, 5, str_footer1, rowFooter, cellStyleBold);
		i_row = i_row + 2;// ----
		rowFooter = sh.createRow(i_row);
		String[] str_footer2 = new String[] { "LÃNH ĐẠO KHOA/VIỆN" };
		createRowInExcel(5, 5, str_footer2, rowFooter, cellStyleBold);
		// ----end footer in excel

		// create additional sheet for KV03
		createExcelFormKV03(wb, delegator, academicYearId, facultyId);

		return wb;
	}

	private static int createSegmentKV04(List<GenericValue> papers,
			CellStyle styleNormal, Sheet sh, int i_row, Delegator delegator,
			Map<String, Long> mPaperCategory2Money,
			Map<String, GenericValue> mId2Staff) {
		for (GenericValue p : papers) {
			i_row += 1;
			Row r = sh.createRow(i_row);

			String paperId = (String) p.get("paperId");
			List<GenericValue> staffsOfPaper = getStaffsOfPaper(paperId,
					delegator);
			String authors = (String) p.get("authors");
			String paperName = (String) p.get("paperName");
			String journalConferenceName = (String) p
					.get("journalConferenceName");
			String volumn = (String) p.get("volumn");
			String ISSN = (String) p.get("ISSN");
			int nbAuthors = 1;
			if (authors != null && !authors.equals("")) {
				String[] s = authors.split(",");
				if (s != null)
					nbAuthors = s.length;
			}
			String paperCategoryId = (String) p.get("paperCategoryId");
			long money = 0;
			if (paperCategoryId != null
					&& mPaperCategory2Money.get(paperCategoryId) != null)
				money = (long) mPaperCategory2Money.get(paperCategoryId);

			double moneyPerAuthor = money * 1.0 / nbAuthors;
			// moneyPerAuthor = Math.floor(moneyPerAuthor);
			DecimalFormat formatter = new DecimalFormat("#0");
			String sMoneyPerAuthor = formatter.format(moneyPerAuthor);
			// System.out.println(formatter.format(d));

			Cell c = r.createCell(2);
			c.setCellValue(authors);
			c.setCellStyle(styleNormal);

			c = r.createCell(3);
			c.setCellValue(paperName);
			c.setCellStyle(styleNormal);

			c = r.createCell(4);
			c.setCellValue(journalConferenceName);
			c.setCellStyle(styleNormal);

			c = r.createCell(5);
			c.setCellValue(volumn);
			c.setCellStyle(styleNormal);

			c = r.createCell(6);
			c.setCellValue(ISSN);
			c.setCellStyle(styleNormal);

			c = r.createCell(7);
			c.setCellValue(money + "");
			c.setCellStyle(styleNormal);

			c = r.createCell(8);
			c.setCellValue(nbAuthors + "");
			c.setCellStyle(styleNormal);

			c = r.createCell(9);
			c.setCellValue(staffsOfPaper.size() + "");
			c.setCellStyle(styleNormal);

			double mp = moneyPerAuthor * staffsOfPaper.size();// nbAuthors;
			String sMoneyAuthors = formatter.format(mp);

			c = r.createCell(10);
			c.setCellValue(sMoneyAuthors + "");
			c.setCellStyle(styleNormal);

			if (staffsOfPaper.size() > 0) {
				GenericValue st = staffsOfPaper.get(0);
				String staffName = (String) (mId2Staff.get(st.get("staffId"))
						.get("staffName"));
				c = r.createCell(11);
				c.setCellValue(staffName);
				c.setCellStyle(styleNormal);
			}

			c = r.createCell(12);
			c.setCellValue(sMoneyPerAuthor + "");
			c.setCellStyle(styleNormal);

			int start_r = i_row;
			for (int i = 1; i < staffsOfPaper.size(); i++) {
				GenericValue st = staffsOfPaper.get(i);
				String staffId = st.getString("staffId");
				System.out.println(name() + "::createSegmentKV04, paper "
						+ p.getString("paperName") + ", staffId = " + staffId);
				if (mId2Staff.get(staffId) == null)
					continue;

				String staffName = (String) (mId2Staff.get(staffId)
						.get("staffName"));
				i_row += 1;
				r = sh.createRow(i_row);
				c = r.createCell(11);
				c.setCellValue(staffName);
				c.setCellStyle(styleNormal);

				c = r.createCell(12);
				c.setCellValue(sMoneyPerAuthor + "");
				c.setCellStyle(styleNormal);

				for (int j = 2; j <= 10; j++) {
					c = r.createCell(j);
					c.setCellStyle(styleNormal);
				}
			}

			if (start_r < i_row)
				for (int j = 2; j <= 10; j++) {
					sh.addMergedRegion(new CellRangeAddress(start_r, i_row, j,
							j));
				}
		}
		return i_row;
	}

	public static void createSheetListPapersKV04(HSSFWorkbook wb,
			List<GenericValue> papers) {
		Sheet sh = wb.createSheet("danh sach bai bao");

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
		// fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontNormal.setColor(HSSFColor.BLACK.index);
		styleNormal.setFont(fontNormal);
		styleNormal.setWrapText(true);
		styleNormal.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
		styleNormal.setBorderTop(CellStyle.BORDER_THIN);
		styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
		styleNormal.setBorderRight(CellStyle.BORDER_THIN);

		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 1000);
		sh.setColumnWidth(2, 8000);
		sh.setColumnWidth(3, 8000);
		sh.setColumnWidth(4, 8000);
		sh.setColumnWidth(5, 8000);
		sh.setColumnWidth(6, 8000);
		sh.setColumnWidth(7, 8000);
		sh.setColumnWidth(8, 5000);
		sh.setColumnWidth(9, 5000);
		sh.setColumnWidth(10, 5000);

		int i_row = 0;

		i_row = 10;
		Row rh = sh.createRow(i_row);

		int i_col = 0;

		i_col++;
		Cell ch = rh.createCell(i_col);
		ch.setCellValue("STT");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Người kê khai");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Họ và tên các tác giả");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Tên bài báo");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Tạp chí, Proceedings");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Thuộc đề tài");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Mã số đề tài");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Tháng đăng tải");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Năm đăng tải");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Minh chứng");
		ch.setCellStyle(styleTitle);

		int count = 0;
		for (GenericValue p : papers) {
			i_row++;
			count++;
			rh = sh.createRow(i_row);
			i_col = 0;

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(count);
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(p.getString("staffName"));
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(p.getString("authors"));
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(p.getString("paperName"));
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(p.getString("journalConferenceName"));
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(p.getString("researchProjectProposalName"));
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(p.getString("researchProjectProposalCode"));
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			String month = "";
			if (p.getString("month") != null)
				month = p.getString("month");
			ch.setCellValue(month);
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			String year = "";
			if (p.getString("year") != null)
				year = p.getString("year");
			ch.setCellValue(year);
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			String file = "NO";
			if (p.getString("sourcePath") != null
					&& !p.getString("sourcePath").equals(""))
				file = "YES [" + p.getString("sourcePath") + "]";
			ch.setCellValue(file);
			ch.setCellStyle(styleNormal);

		}

	}

	public static GenericValue getStaffPaperDeclaration(Delegator delegator,
			String paperId, String staffId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));
			List<GenericValue> L = delegator.findList("StaffPaperDeclaration",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			if (L != null && L.size() > 0)
				return L.get(0);
			else
				return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static HashMap<String, Double> getRateKNCPaper(Delegator delegator,
			String academicYearId) {
		try {
			HashMap<String, Double> mCategory2Rate = new HashMap<String, Double>();
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, academicYearId));
			List<GenericValue> L = delegator.findList(
					"PaperCategoryKNCRateYear",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			for (GenericValue g : L) {
				String cat = g.getString("paperCategoryKNCId");
				double rate = g.getDouble("rateNangluc");
				mCategory2Rate.put(cat, rate);
			}
			return mCategory2Rate;
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}

	public static HashMap<String, Double> getRateKKLPaper(Delegator delegator,
			String academicYearId) {
		try {
			HashMap<String, Double> mCategory2Rate = new HashMap<String, Double>();
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, academicYearId));
			List<GenericValue> L = delegator.findList(
					"PaperCategoryKNCRateYear",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			for (GenericValue g : L) {
				String cat = g.getString("paperCategoryKNCId");
				double rate = g.getDouble("rateKhoiluong");
				mCategory2Rate.put(cat, rate);
			}
			return mCategory2Rate;
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}

	public static HashMap<String, String> getMapPaperCategoryKNCId2Name(
			Delegator delegator) {
		try {
			HashMap<String, String> m = new HashMap<String, String>();
			// List<EntityCondition> conds = FastList.newInstance();
			// conds.add(EntityCondition.makeCondition("academicYearId",EntityOperator.EQUALS,academicYearId));
			List<GenericValue> L = delegator.findList("PaperCategoryKNC", null,
					null, null, null, false);
			for (GenericValue g : L) {
				String cat = g.getString("paperCategoryKNCId");
				String name = g.getString("paperCategoryKNCName");
				m.put(cat, name);
			}
			return m;
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}

	public static String name() {
		return "paperDeclarationUtil";
	}

	public static void createSheetKNC(HSSFWorkbook wb, String facultyId,
			String academicYearId, Delegator delegator, String userLoginId) {
		Sheet sh = wb.createSheet("KNC");

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
		// fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontNormal.setColor(HSSFColor.BLACK.index);
		styleNormal.setFont(fontNormal);
		styleNormal.setWrapText(true);
		styleNormal.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
		styleNormal.setBorderTop(CellStyle.BORDER_THIN);
		styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
		styleNormal.setBorderRight(CellStyle.BORDER_THIN);

		sh.setColumnWidth(0, 1000);// blank
		sh.setColumnWidth(1, 1000);// STT
		sh.setColumnWidth(2, 6000);// Ho ten (ten bai bao)
		sh.setColumnWidth(3, 8000);// Loai hinh
		sh.setColumnWidth(4, 8000);// D/S tac gia
		sh.setColumnWidth(5, 3000);// so tac gia
		sh.setColumnWidth(6, 3000);// corresponding
		sh.setColumnWidth(7, 3000);// rate_nangluc
		sh.setColumnWidth(8, 4000);// KNC
		sh.setColumnWidth(9, 5000);// Ghi chu
		// sh.setColumnWidth(10, 10000);// GHi chu

		int i_row = 0;

		i_row = 10;
		Row rh = sh.createRow(i_row);

		int i_col = 0;

		i_col++;
		Cell ch = rh.createCell(i_col);
		ch.setCellValue("STT");
		ch.setCellStyle(styleTitle);

		// i_col++;
		// ch = rh.createCell(i_col);
		// ch.setCellValue("Họ tên");
		// ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Tên bài báo, sách");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Loại hình");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("D/S Tác giả");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Số tác giả");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Corresponding");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Điểm năng lực");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("KNC");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Ghi chú");
		ch.setCellStyle(styleTitle);

		List<String> groups = BKEunivUtils.getListSecurityGroupsOfUserLogin(
				delegator, userLoginId);
		boolean staffOnly = true;
		for (String g : groups) {
			if (g.equals("HUST_KHCN_ADMIN") || g.equals("SCHOOL_KHCN_ADMIN")
					|| g.equals("SUPER_ADMIN"))
				staffOnly = false;
		}

		List<GenericValue> staffs = getListStaffsOfFaculty(delegator, facultyId);

		HashMap<String, Double> mCategory2Rate = getRateKNCPaper(delegator,
				academicYearId);

		HashMap<String, String> mKNCIdName = getMapPaperCategoryKNCId2Name(delegator);

		int count = 0;
		for (GenericValue st : staffs) {
			String staffId = st.getString("staffId");
			if (staffOnly) {
				if (!staffId.equals(userLoginId))
					continue;
			}
			List<GenericValue> papers = getPapersOfStaffAcademicYear(delegator,
					staffId, academicYearId);

			i_row++;
			count++;
			rh = sh.createRow(i_row);
			i_col = 0;

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(count);
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(st.getString("staffName"));
			ch.setCellStyle(styleNormal);

			for (int k = 3; k <= 9; k++) {
				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellStyle(styleNormal);
			}

			sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 9));

			HashMap<GenericValue, Double> mPaper2KNC = new HashMap<GenericValue, Double>();

			double total_knc = 0;
			for (GenericValue p : papers) {
				String paperId = p.getString("paperId");
				String authors = p.getString("authors");
				String paperCategoryKNCId = p.getString("paperCategoryKNCId");

				GenericValue sp = getStaffPaperDeclaration(delegator, paperId,
						staffId);

				String s_rate = "";
				String s_knc = "";
				double knc = 0;

				int nbAuthors = 1;
				String correspondingAuthor = "N";
				String description = "";
				if (sp.get("sequence") == null) {
					description += "Không có thông tin về số thứ tự của tác giả. ";
				}
				if (paperCategoryKNCId == null) {
					description += "Không có thông tin về phân loại KNC. ";
				}
				if (authors != null && !authors.equals("")) {
					String[] s = authors.split(",");
					nbAuthors = s.length;
				}

				if (paperCategoryKNCId != null && sp != null) {
					Double x = mCategory2Rate.get(paperCategoryKNCId);

					System.out.println(name() + "::createSheetKNC paper "
							+ p.getString("paperName") + ", categoryKNC = "
							+ paperCategoryKNCId + ", sequence = "
							+ sp.getLong("sequence") + ", corresponding = "
							+ sp.getString("correspondingAuthor") + ", rate = "
							+ x + ", authors = " + authors);

					if (x != null && sp.getLong("sequence") != null
							&& sp.getString("correspondingAuthor") != null
							&& authors != null && !authors.equals("")) {
						s_rate = x + "";
						long seq = sp.getLong("sequence");

						if (seq == 1
								&& sp.getString("correspondingAuthor").equals(
										"Y")) {
							knc = 0.4 * x + (0.6 * x * 1.0 / nbAuthors);
						} else if (seq == 1) {
							knc = 0.2 * x + (0.6 * x * 1.0 / nbAuthors);
						} else if (sp.getString("correspondingAuthor").equals(
								"Y")) {
							knc = 0.2 * x + (0.6 * x * 1.0 / nbAuthors);
						} else {
							knc = (0.6 * x * 1.0 / nbAuthors);
						}
						if (sp.getString("correspondingAuthor").equals("Y"))
							correspondingAuthor = "Y";

						if (sp.getString("affiliationOutsideUniversity") != null
								&& sp.getString("affiliationOutsideUniversity")
										.equals("Y")) {
							knc = knc * 0.5;
							description += "Affiliation NGOÀI TRƯỜNG -> KNC chia đôi. ";
						}
						s_knc = knc + "";
						total_knc += knc;
					}
				}

				mPaper2KNC.put(p, knc);

				i_row++;
				rh = sh.createRow(i_row);
				i_col = 0;

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(p.getString("paperName"));
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				String catKNCName = mKNCIdName.get(p
						.getString("paperCategoryKNCId"));
				if (catKNCName != null)
					ch.setCellValue(catKNCName);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(p.getString("authors"));
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(nbAuthors + "");
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(correspondingAuthor);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(s_rate);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(s_knc);
				ch.setCellStyle(styleNormal);
				styleNormal.setDataFormat(wb.getCreationHelper()
						.createDataFormat().getFormat("#.#"));

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(description);
				ch.setCellStyle(styleNormal);

			}

			total_knc = 0;
			double knc_web_science_scopus = 0;
			double knc_remain = 0;
			for (GenericValue p : papers) {
				String paperCategoryKNCId = p.getString("paperCategoryKNCId");
				if (paperCategoryKNCId == null)
					continue;

				if (paperCategoryKNCId.equals("WEB_SCIENCE_Q1")
						|| paperCategoryKNCId.equals("WEB_SCIENCE_OTHER")
						|| paperCategoryKNCId.equals("SCOPUS")) {
					knc_web_science_scopus += mPaper2KNC.get(p);
				} else {
					knc_remain += mPaper2KNC.get(p);
				}
			}
			if (knc_remain > 0.5)
				knc_remain = 0.5;

			total_knc = knc_web_science_scopus + knc_remain;

			i_row++;
			rh = sh.createRow(i_row);
			i_col = 0;

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue("Tổng");
			ch.setCellStyle(styleNormal);

			for (int k = 1; k <= 6; k++) {
				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellStyle(styleNormal);
			}
			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(total_knc + "");
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			String description = "KNC của các bài báo Web Science and Scopus là "
					+ knc_web_science_scopus
					+ ", KNC của các bài báo còn lại là " + knc_remain;
			ch.setCellValue(description);
			ch.setCellStyle(styleNormal);

			sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, 7));
			// sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 8, 9));
		}

	}

	public static Map<String, Object> computeRateKKLOfProject(
			Delegator delegator, String researchProjectProposalId,
			String academicYearId) {
		Map<String, Object> ret = FastMap.newInstance();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.clear();
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, academicYearId));
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.EQUALS,
					researchProjectProposalId));

			List<GenericValue> list_budget = delegator.findList(
					"ResearchProjectBudgetDeclarationYear",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			BigDecimal total = BigDecimal.ZERO;
			for (GenericValue b : list_budget) {
				BigDecimal eb = b.getBigDecimal("equipmentBudget");
				BigDecimal mb = b.getBigDecimal("managementBudget");
				BigDecimal budget = eb.add(mb);
				total = total.add(budget);
			}
			BigDecimal trieu = new BigDecimal(1000000);
			total = total.divide(trieu);
			double budget = total.doubleValue();
			double rate = 0;
			if (20 <= budget && budget <= 200) {
				rate = (budget - 20) * (1 - 0.1) / (200 - 20) + 0.1;
			} else if (budget > 200) {
				rate = 1.0 + (budget - 200) * 0.1 / (100);
			}
			Debug.log(module + "::computeRateKKLOfProject, project "
					+ researchProjectProposalId + " year " + academicYearId
					+ ", budget = " + budget + ", rate = " + rate);

			ret.put("rate", rate);
			ret.put("budget", budget);

			/*
			 * BigDecimal m1 = new BigDecimal(20000000); BigDecimal m2 = new
			 * BigDecimal(200000000); BigDecimal m = new BigDecimal(100000000);
			 * BigDecimal m21 = m2.subtract(m1); BigDecimal r1 = new
			 * BigDecimal(0.1); BigDecimal r2 = new BigDecimal(1.0); BigDecimal
			 * r21 = r2.subtract(r1); double rate = 0; if (total.compareTo(m1)
			 * >= 0 && total.compareTo(m2) <= 0) { BigDecimal a =
			 * total.subtract(m1); a = a.multiply(r21); a = a.divide(m21); rate
			 * = a.doubleValue() + 0.1; } else if (total.compareTo(m2) > 0) {
			 * rate = 1.0; BigDecimal a = total.subtract(m2); a = a.divide(m);
			 * rate += a.doubleValue(); }
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
			// return 0;
		}
		return ret;
	}

	public static double computeKKLFromProjectOfStaff(Delegator delegator,
			String staffId, String academicYearId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, academicYearId));

			List<GenericValue> list = delegator.findList(
					"ResearchProjectParticipationDeclarationYear",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			double total_kkl = 0;

			for (GenericValue p : list) {
				String researchProjectProposalId = p
						.getString("researchProjectProposalId");
				long percentage = p.getLong("staffParticipationPercentage");
				/*
				 * conds.clear();
				 * conds.add(EntityCondition.makeCondition("academicYearId"
				 * ,EntityOperator.EQUALS,academicYearId));
				 * conds.add(EntityCondition
				 * .makeCondition("researchProjectProposalId"
				 * ,EntityOperator.EQUALS,researchProjectProposalId));
				 * 
				 * List<GenericValue> list_budget =
				 * delegator.findList("ResearchProjectBudgetDeclarationYear",
				 * EntityCondition.makeCondition(conds), null,null,null,false);
				 * 
				 * BigDecimal total = BigDecimal.ZERO; for(GenericValue b:
				 * list_budget){ BigDecimal eb =
				 * b.getBigDecimal("equipmentBudget"); BigDecimal mb =
				 * b.getBigDecimal("managementBudget"); BigDecimal budget =
				 * eb.add(mb); total = total.add(budget); } BigDecimal m1 = new
				 * BigDecimal(20000000); BigDecimal m2 = new
				 * BigDecimal(200000000); BigDecimal m = new
				 * BigDecimal(100000000); BigDecimal m21 = m2.subtract(m1);
				 * BigDecimal r1 = new BigDecimal(0.1); BigDecimal r2 = new
				 * BigDecimal(1.0); BigDecimal r21 = r2.subtract(r1); double
				 * rate = 0; if(total.compareTo(m1) >= 0 && total.compareTo(m2)
				 * <= 0 ){ BigDecimal a = total.subtract(m1); a =
				 * a.multiply(r21); a = a.divide(m21); rate = a.doubleValue() +
				 * 0.1; }else if(total.compareTo(m2) > 0){ rate = 1.0;
				 * BigDecimal a = total.subtract(m2); a = a.divide(m); rate +=
				 * a.doubleValue(); }
				 */
				Map<String, Object> m = computeRateKKLOfProject(delegator,
						researchProjectProposalId, academicYearId);
				double rate = (double) m.get("rate");
				total_kkl += (rate * percentage * 1.0) / 100.0;
			}
			return total_kkl;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}

	}

	public static void createSheetKKL(HSSFWorkbook wb, String facultyId,
			String academicYearId, Delegator delegator, String userLoginId) {
		Sheet sh = wb.createSheet("K-KhoiLuong");

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
		// fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontNormal.setColor(HSSFColor.BLACK.index);
		styleNormal.setFont(fontNormal);
		styleNormal.setWrapText(true);
		styleNormal.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
		styleNormal.setBorderTop(CellStyle.BORDER_THIN);
		styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
		styleNormal.setBorderRight(CellStyle.BORDER_THIN);

		sh.setColumnWidth(0, 1000);// blank
		sh.setColumnWidth(1, 1000);// STT
		sh.setColumnWidth(2, 6000);// Ho ten (ten bai bao)
		sh.setColumnWidth(3, 8000);// Loai hinh
		sh.setColumnWidth(4, 8000);// D/S tac gia
		sh.setColumnWidth(5, 3000);// so tac gia
		sh.setColumnWidth(6, 3000);// corresponding
		sh.setColumnWidth(7, 3000);// rate_nangluc
		sh.setColumnWidth(8, 4000);// KNC
		sh.setColumnWidth(9, 5000);// Ghi chu
		// sh.setColumnWidth(10, 10000);// GHi chu

		int i_row = 0;

		i_row = 10;
		Row rh = sh.createRow(i_row);

		int i_col = 0;

		i_col++;
		Cell ch = rh.createCell(i_col);
		ch.setCellValue("STT");
		ch.setCellStyle(styleTitle);

		// i_col++;
		// ch = rh.createCell(i_col);
		// ch.setCellValue("Họ tên");
		// ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Tên bài báo, sách");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Loại hình");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("D/S Tác giả");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Số tác giả");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Corresponding");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Điểm khối lượng");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("KKL");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Ghi chú");
		ch.setCellStyle(styleTitle);

		List<String> groups = BKEunivUtils.getListSecurityGroupsOfUserLogin(
				delegator, userLoginId);
		boolean staffOnly = true;
		for (String g : groups) {
			if (g.equals("HUST_KHCN_ADMIN") || g.equals("SCHOOL_KHCN_ADMIN")
					|| g.equals("SUPER_ADMIN"))
				staffOnly = false;
		}

		List<GenericValue> staffs = getListStaffsOfFaculty(delegator, facultyId);

		HashMap<String, Double> mCategory2RateKL = getRateKKLPaper(delegator,
				academicYearId);

		HashMap<String, String> mKNCIdName = getMapPaperCategoryKNCId2Name(delegator);

		int count = 0;
		for (GenericValue st : staffs) {
			String staffId = st.getString("staffId");
			if (staffOnly) {
				if (!staffId.equals(userLoginId))
					continue;
			}
			List<GenericValue> papers = getPapersOfStaffAcademicYear(delegator,
					staffId, academicYearId);

			i_row++;
			count++;
			rh = sh.createRow(i_row);
			i_col = 0;

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(count);
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(st.getString("staffName"));
			ch.setCellStyle(styleNormal);

			for (int k = 3; k <= 9; k++) {
				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellStyle(styleNormal);
			}

			sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 9));

			double total_kkl = 0;
			for (GenericValue p : papers) {
				String paperId = p.getString("paperId");
				String authors = p.getString("authors");
				String paperCategoryKNCId = p.getString("paperCategoryKNCId");

				GenericValue sp = getStaffPaperDeclaration(delegator, paperId,
						staffId);

				String s_rate = "";
				String s_kkl = "";
				double kkl = 0;
				int nbAuthors = 1;
				String correspondingAuthor = "N";
				String description = "";
				if (sp.get("sequence") == null) {
					description += "Không có thông tin về số thứ tự của tác giả. ";
				}
				if (paperCategoryKNCId == null) {
					description += "Không có thông tin về phân loại KNC. ";
				}
				if (authors != null && !authors.equals("")) {
					String[] s = authors.split(",");
					nbAuthors = s.length;
				}

				if (paperCategoryKNCId != null && sp != null) {
					Double x = mCategory2RateKL.get(paperCategoryKNCId);

					System.out.println(name() + "::createSheetKKL paper "
							+ p.getString("paperName") + ", categoryKNC = "
							+ paperCategoryKNCId + ", sequence = "
							+ sp.getLong("sequence") + ", corresponding = "
							+ sp.getString("correspondingAuthor") + ", rate = "
							+ x + ", authors = " + authors);

					if (x != null && sp.getLong("sequence") != null
							&& sp.getString("correspondingAuthor") != null
							&& authors != null && !authors.equals("")) {
						s_rate = x + "";
						long seq = sp.getLong("sequence");

						if (seq == 1
								&& sp.getString("correspondingAuthor").equals(
										"Y")) {
							kkl = 0.4 * x + (0.6 * x * 1.0 / nbAuthors);
						} else if (seq == 1) {
							kkl = 0.2 * x + (0.6 * x * 1.0 / nbAuthors);
						} else if (sp.getString("correspondingAuthor").equals(
								"Y")) {
							kkl = 0.2 * x + (0.6 * x * 1.0 / nbAuthors);
						} else {
							kkl = (0.6 * x * 1.0 / nbAuthors);
						}
						if (sp.getString("correspondingAuthor").equals("Y"))
							correspondingAuthor = "Y";
						s_kkl = kkl + "";
						total_kkl += kkl;
					}
				}

				i_row++;
				rh = sh.createRow(i_row);
				i_col = 0;

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(p.getString("paperName"));
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				String catKNCName = mKNCIdName.get(p
						.getString("paperCategoryKNCId"));
				if (catKNCName != null)
					ch.setCellValue(catKNCName);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(p.getString("authors"));
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(nbAuthors + "");
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(correspondingAuthor);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(s_rate);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(s_kkl);
				ch.setCellStyle(styleNormal);
				styleNormal.setDataFormat(wb.getCreationHelper()
						.createDataFormat().getFormat("#.#"));

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(description);
				ch.setCellStyle(styleNormal);
			}

			// tính khoi luong tu kinh phi de tai
			List<GenericValue> list = FastList.newInstance();
			try {
				List<EntityCondition> conds = FastList.newInstance();
				conds.add(EntityCondition.makeCondition("staffId",
						EntityOperator.EQUALS, staffId));
				conds.add(EntityCondition.makeCondition("academicYearId",
						EntityOperator.EQUALS, academicYearId));

				list = delegator.findList(
						"ResearchProjectParticipationDeclarationYearView",
						EntityCondition.makeCondition(conds), null, null, null,
						false);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (list.size() > 0) {
				Debug.log(module + "::createSheetKKL, staffId = " + staffId
						+ ", project.sz = " + list.size());
			}

			for (GenericValue pp : list) {
				String researchProjectProposalId = pp
						.getString("researchProjectProposalId");
				long percentage = pp.getLong("staffParticipationPercentage");
				Map<String, Object> m = computeRateKKLOfProject(delegator,
						researchProjectProposalId, academicYearId);
				double rate = (double) m.get("rate");
				double budget = (double) m.get("budget");

				double kkl_prj = (rate * percentage * 1.0) / 100.0;

				Debug.log(module + "::createSheetKKL, staffId = " + staffId
						+ ", project "
						+ pp.getString("researchProjectProposalName")
						+ ", percentage = " + percentage + ", rate = " + rate);

				String description = "Đề tài, kinh phí thiết bị và quản lý = "
						+ budget + ", mức độ đóng góp = " + percentage + "%";

				i_row++;
				rh = sh.createRow(i_row);
				i_col = 0;

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(pp.getString("researchProjectProposalName"));
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				// ch.setCellValue(p.getString("authors"));
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				// ch.setCellValue(nbAuthors + "");
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				// ch.setCellValue(correspondingAuthor);
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(rate + "");
				ch.setCellStyle(styleNormal);

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(kkl_prj + "");
				ch.setCellStyle(styleNormal);
				styleNormal.setDataFormat(wb.getCreationHelper()
						.createDataFormat().getFormat("#.#"));

				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellValue(description);
				ch.setCellStyle(styleNormal);

				total_kkl += kkl_prj;
			}

			i_row++;
			rh = sh.createRow(i_row);
			i_col = 0;

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue("Tổng");
			ch.setCellStyle(styleNormal);

			for (int k = 1; k <= 6; k++) {
				i_col++;
				ch = rh.createCell(i_col);
				ch.setCellStyle(styleNormal);
			}
			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(total_kkl + "");
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellStyle(styleNormal);

			sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, 7));
			sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 8, 9));
		}

	}

	public static double getKNCFromPapers(Delegator delegator, String staffId,
			String academicYearId, HashMap<String, Double> mCategory2Rate) {
		List<GenericValue> papers = getPapersOfStaffAcademicYear(delegator,
				staffId, academicYearId);

		double total_knc = 0;
		HashMap<GenericValue, Double> mPaper2KNC = new HashMap<GenericValue, Double>();

		for (GenericValue p : papers) {
			String paperId = p.getString("paperId");
			String authors = p.getString("authors");
			String paperCategoryKNCId = p.getString("paperCategoryKNCId");

			GenericValue sp = getStaffPaperDeclaration(delegator, paperId,
					staffId);

			String s_rate = "";
			String s_knc = "";
			double knc = 0;
			int nbAuthors = 1;
			String correspondingAuthor = "N";
			String description = "";
			if (sp.get("sequence") == null) {
				description += "Không có thông tin về số thứ tự của tác giả. ";
			}
			if (paperCategoryKNCId == null) {
				description += "Không có thông tin về phân loại KNC. ";
			}
			if (authors != null && !authors.equals("")) {
				String[] s = authors.split(",");
				nbAuthors = s.length;
			}

			if (paperCategoryKNCId != null && sp != null) {
				Double x = mCategory2Rate.get(paperCategoryKNCId);

				System.out.println(name() + "::createSheetKNC paper "
						+ p.getString("paperName") + ", categoryKNC = "
						+ paperCategoryKNCId + ", sequence = "
						+ sp.getLong("sequence") + ", corresponding = "
						+ sp.getString("correspondingAuthor") + ", rate = " + x
						+ ", authors = " + authors);

				if (x != null && sp.getLong("sequence") != null
						&& sp.getString("correspondingAuthor") != null
						&& authors != null && !authors.equals("")) {
					s_rate = x + "";
					long seq = sp.getLong("sequence");

					if (seq == 1
							&& sp.getString("correspondingAuthor").equals("Y")) {
						knc = 0.4 * x + (0.6 * x * 1.0 / nbAuthors);
					} else if (seq == 1) {
						knc = 0.2 * x + (0.6 * x * 1.0 / nbAuthors);
					} else if (sp.getString("correspondingAuthor").equals("Y")) {
						knc = 0.2 * x + (0.6 * x * 1.0 / nbAuthors);
					} else {
						knc = (0.6 * x * 1.0 / nbAuthors);
					}

					if (sp.getString("affiliationOutsideUniversity") != null
							&& sp.getString("affiliationOutsideUniversity")
									.equals("Y")) {
						knc = knc * 0.5;
					}

					if (sp.getString("correspondingAuthor").equals("Y"))
						correspondingAuthor = "Y";
					s_knc = knc + "";
					total_knc += knc;
				}
			}

			mPaper2KNC.put(p, knc);
		}

		total_knc = 0;
		double knc_web_science_scopus = 0;
		double knc_remain = 0;
		for (GenericValue p : papers) {
			String paperCategoryKNCId = p.getString("paperCategoryKNCId");
			if (paperCategoryKNCId == null)
				continue;
			if (paperCategoryKNCId.equals("WEB_SCIENCE_Q1")
					|| paperCategoryKNCId.equals("WEB_SCIENCE_OTHER")
					|| paperCategoryKNCId.equals("SCOPUS")) {
				knc_web_science_scopus += mPaper2KNC.get(p);
			} else {
				knc_remain += mPaper2KNC.get(p);
			}
		}
		if (knc_remain > 0.5)
			knc_remain = 0.5;

		total_knc = knc_web_science_scopus + knc_remain;

		return total_knc;
	}

	public static double getKKLFromPapers(Delegator delegator, String staffId,
			String academicYearId, HashMap<String, Double> mCategory2Rate) {
		List<GenericValue> papers = getPapersOfStaffAcademicYear(delegator,
				staffId, academicYearId);

		double total_kkl = 0;
		for (GenericValue p : papers) {
			String paperId = p.getString("paperId");
			String authors = p.getString("authors");
			String paperCategoryKNCId = p.getString("paperCategoryKNCId");

			GenericValue sp = getStaffPaperDeclaration(delegator, paperId,
					staffId);

			String s_rate = "";
			String s_kkl = "";
			double kkl = 0;
			int nbAuthors = 1;
			String correspondingAuthor = "N";
			String description = "";
			if (sp.get("sequence") == null) {
				description += "Không có thông tin về số thứ tự của tác giả. ";
			}
			if (paperCategoryKNCId == null) {
				description += "Không có thông tin về phân loại KNC. ";
			}
			if (authors != null && !authors.equals("")) {
				String[] s = authors.split(",");
				nbAuthors = s.length;
			}

			if (paperCategoryKNCId != null && sp != null) {
				Double x = mCategory2Rate.get(paperCategoryKNCId);

				System.out.println(name() + "::getKKLFromPapers paper "
						+ p.getString("paperName") + ", categoryKNC = "
						+ paperCategoryKNCId + ", sequence = "
						+ sp.getLong("sequence") + ", corresponding = "
						+ sp.getString("correspondingAuthor") + ", rate = " + x
						+ ", authors = " + authors);

				if (x != null && sp.getLong("sequence") != null
						&& sp.getString("correspondingAuthor") != null
						&& authors != null && !authors.equals("")) {
					s_rate = x + "";
					long seq = sp.getLong("sequence");

					if (seq == 1
							&& sp.getString("correspondingAuthor").equals("Y")) {
						kkl = 0.4 * x + (0.6 * x * 1.0 / nbAuthors);
					} else if (seq == 1) {
						kkl = 0.2 * x + (0.6 * x * 1.0 / nbAuthors);
					} else if (sp.getString("correspondingAuthor").equals("Y")) {
						kkl = 0.2 * x + (0.6 * x * 1.0 / nbAuthors);
					} else {
						kkl = (0.6 * x * 1.0 / nbAuthors);
					}
					if (sp.getString("correspondingAuthor").equals("Y"))
						correspondingAuthor = "Y";
					s_kkl = kkl + "";
					total_kkl += kkl;
				}
			}

		}

		// tinh diem khoi luong tu de tai
		List<GenericValue> list = FastList.newInstance();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition("academicYearId",
					EntityOperator.EQUALS, academicYearId));

			list = delegator.findList(
					"ResearchProjectParticipationDeclarationYearView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (list.size() > 0) {
			Debug.log(module + "::getKKLFromPapers, staffId = " + staffId
					+ ", project.sz = " + list.size());
		}

		for (GenericValue pp : list) {
			String researchProjectProposalId = pp
					.getString("researchProjectProposalId");
			long percentage = pp.getLong("staffParticipationPercentage");
			Map<String, Object> m = computeRateKKLOfProject(delegator,
					researchProjectProposalId, academicYearId);
			double rate = (double) m.get("rate");
			double budget = (double) m.get("budget");

			double kkl_prj = (rate * percentage * 1.0) / 100.0;

			Debug.log(module + "::getKKLFromPapers, staffId = " + staffId
					+ ", project "
					+ pp.getString("researchProjectProposalName")
					+ ", percentage = " + percentage + ", rate = " + rate);

			total_kkl += kkl_prj;
		}

		return total_kkl;
	}

	public static void createSheetKNCTotal(HSSFWorkbook wb, String facultyId,
			String academicYearId, Delegator delegator, String userLoginId) {
		Sheet sh = wb.createSheet("KNC-Total");

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
		// fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontNormal.setColor(HSSFColor.BLACK.index);
		styleNormal.setFont(fontNormal);
		styleNormal.setWrapText(true);
		styleNormal.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
		styleNormal.setBorderTop(CellStyle.BORDER_THIN);
		styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
		styleNormal.setBorderRight(CellStyle.BORDER_THIN);

		sh.setColumnWidth(0, 1000);// blank
		sh.setColumnWidth(1, 2000);// STT
		sh.setColumnWidth(2, 8000);// Ho ten can bo
		sh.setColumnWidth(3, 2000);// total KNC

		int i_row = 0;

		i_row = 10;
		Row rh = sh.createRow(i_row);

		int i_col = 0;

		i_col++;
		Cell ch = rh.createCell(i_col);
		ch.setCellValue("STT");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Họ tên");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("KNC");
		ch.setCellStyle(styleTitle);

		List<String> groups = BKEunivUtils.getListSecurityGroupsOfUserLogin(
				delegator, userLoginId);
		boolean staffOnly = true;
		for (String g : groups) {
			if (g.equals("HUST_KHCN_ADMIN") || g.equals("SCHOOL_KHCN_ADMIN")
					|| g.equals("SUPER_ADMIN"))
				staffOnly = false;
		}

		List<GenericValue> staffs = getListStaffsOfFaculty(delegator, facultyId);

		HashMap<String, Double> mCategory2Rate = getRateKNCPaper(delegator,
				academicYearId);

		HashMap<String, String> mKNCIdName = getMapPaperCategoryKNCId2Name(delegator);

		int count = 0;
		for (GenericValue st : staffs) {
			String staffId = st.getString("staffId");
			if (staffOnly) {
				if (!staffId.equals(userLoginId))
					continue;
			}

			List<GenericValue> papers = getPapersOfStaffAcademicYear(delegator,
					staffId, academicYearId);

			double knc = getKNCFromPapers(delegator, staffId, academicYearId,
					mCategory2Rate);

			i_row++;
			count++;
			rh = sh.createRow(i_row);
			i_col = 0;

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(count);
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(st.getString("staffName"));
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(knc);
			ch.setCellStyle(styleNormal);

			styleNormal.setDataFormat(wb.getCreationHelper().createDataFormat()
					.getFormat("#.##"));

		}

	}

	public static void createSheetKKLTotal(HSSFWorkbook wb, String facultyId,
			String academicYearId, Delegator delegator, String userLoginId) {
		Sheet sh = wb.createSheet("K-KhoiLuong-Total");

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
		// fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontNormal.setColor(HSSFColor.BLACK.index);
		styleNormal.setFont(fontNormal);
		styleNormal.setWrapText(true);
		styleNormal.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
		styleNormal.setBorderTop(CellStyle.BORDER_THIN);
		styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
		styleNormal.setBorderRight(CellStyle.BORDER_THIN);

		sh.setColumnWidth(0, 1000);// blank
		sh.setColumnWidth(1, 2000);// STT
		sh.setColumnWidth(2, 8000);// Ho ten can bo
		sh.setColumnWidth(3, 2000);// total KNC

		int i_row = 0;

		i_row = 10;
		Row rh = sh.createRow(i_row);

		int i_col = 0;

		i_col++;
		Cell ch = rh.createCell(i_col);
		ch.setCellValue("STT");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("Họ tên");
		ch.setCellStyle(styleTitle);

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellValue("KNC");
		ch.setCellStyle(styleTitle);

		List<String> groups = BKEunivUtils.getListSecurityGroupsOfUserLogin(
				delegator, userLoginId);
		boolean staffOnly = true;
		for (String g : groups) {
			if (g.equals("HUST_KHCN_ADMIN") || g.equals("SCHOOL_KHCN_ADMIN")
					|| g.equals("SUPER_ADMIN"))
				staffOnly = false;
		}

		List<GenericValue> staffs = getListStaffsOfFaculty(delegator, facultyId);

		HashMap<String, Double> mCategory2Rate = getRateKKLPaper(delegator,
				academicYearId);

		HashMap<String, String> mKNCIdName = getMapPaperCategoryKNCId2Name(delegator);

		int count = 0;
		for (GenericValue st : staffs) {
			String staffId = st.getString("staffId");
			if (staffOnly) {
				if (!staffId.equals(userLoginId))
					continue;
			}
			// List<GenericValue> papers =
			// getPapersOfStaffAcademicYear(delegator,
			// staffId, academicYearId);

			double kkl = getKKLFromPapers(delegator, staffId, academicYearId,
					mCategory2Rate);

			i_row++;
			count++;
			rh = sh.createRow(i_row);
			i_col = 0;

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(count);
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(st.getString("staffName"));
			ch.setCellStyle(styleNormal);

			i_col++;
			ch = rh.createCell(i_col);
			ch.setCellValue(kkl);
			ch.setCellStyle(styleNormal);

			styleNormal.setDataFormat(wb.getCreationHelper().createDataFormat()
					.getFormat("#.##"));

		}

	}

	public static HSSFWorkbook createExcelFormKV04(Delegator delegator,
			String academicYearId, String facultyId, String userLoginId) {

		// get list of paper category hour-budget
		List<GenericValue> paperHourBudget = getPaperCategoryHourBudget(
				delegator, academicYearId);
		Map<String, Long> mPaperCategory2Money = FastMap.newInstance();

		if (paperHourBudget != null) {
			for (GenericValue gv : paperHourBudget) {
				String cat = (String) gv.get("paperCategoryId");
				long money = (long) gv.get("budget");
				mPaperCategory2Money.put(cat, money);
			}
		} else {
			Debug.log(module + "::createExcelFormKV04, hour-budget of year "
					+ academicYearId + " NOT EXISTS");
			;

		}

		List<GenericValue> staffs = getListStaffsOfFaculty(delegator, facultyId);
		List<GenericValue> all_staffs = getListStaffs(delegator);

		HashMap<String, GenericValue> mId2Staff = new HashMap<String, GenericValue>();

		List<String> staffIDs = FastList.newInstance();
		for (GenericValue gv : staffs) {
			staffIDs.add((String) gv.get("staffId"));
		}
		for (GenericValue st : all_staffs) {
			mId2Staff.put((String) st.get("staffId"), st);
		}

		List<EntityCondition> conds = FastList.newInstance();
		conds.add(EntityCondition.makeCondition("academicYearId",
				EntityOperator.EQUALS, academicYearId));
		conds.add(EntityCondition.makeCondition("statusId",
				EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

		// conds.add(EntityCondition.makeCondition("staffId",EntityOperator.IN,staffIDs));
		List<GenericValue> papers = FastList.newInstance();
		try {
			papers = delegator.findList("PaperView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Debug.log(module + "::createExcelFormKV04, papers.sz = "
				+ papers.size());

		List<GenericValue> list_paper_international_journals = FastList
				.newInstance();
		List<GenericValue> list_paper_national_journals = FastList
				.newInstance();
		List<GenericValue> list_paper_international_conferences = FastList
				.newInstance();
		List<GenericValue> list_paper_national_conferences = FastList
				.newInstance();

		for (GenericValue p : papers) {
			String paperCategoryId = (String) p.get("paperCategoryId");
			Debug.log(module + "::createExcelFormKV04, paper "
					+ (String) p.get("paperName") + ", category = "
					+ paperCategoryId);
			if (paperCategoryId == null || paperCategoryId.equals(""))
				continue;

			if (paperCategoryId.equals("JINT_Other")) {
				list_paper_international_journals.add(p);
			} else if (paperCategoryId.equals("JDOM_Other")) {
				list_paper_national_journals.add(p);
			} else if (paperCategoryId.equals("CINT_Other")) {
				list_paper_international_conferences.add(p);
			} else if (paperCategoryId.equals("CDOM_Other")) {
				list_paper_national_conferences.add(p);
			}
		}

		// start renderExcel
		HSSFWorkbook wb = new HSSFWorkbook();

		Sheet sh = wb.createSheet("KV04");

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
		// fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontNormal.setColor(HSSFColor.BLACK.index);
		styleNormal.setFont(fontNormal);
		styleNormal.setWrapText(true);
		styleNormal.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
		styleNormal.setBorderTop(CellStyle.BORDER_THIN);
		styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
		styleNormal.setBorderRight(CellStyle.BORDER_THIN);

		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 1000);
		sh.setColumnWidth(2, 8000);
		sh.setColumnWidth(3, 8000);
		sh.setColumnWidth(4, 8000);

		int i_row = 0;

		i_row = 10;
		Row rh = sh.createRow(i_row);
		Cell ch = rh.createCell(2);
		ch.setCellValue("Họ và tên các tác giả");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(3);
		ch.setCellValue("Tên bài báo");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(4);
		ch.setCellValue("Tạp chí, Proceedings");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(5);
		ch.setCellValue("");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(6);
		ch.setCellValue("");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(7);
		ch.setCellValue("Mức hỗ trợ/bài báo");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(8);
		ch.setCellValue("Số đồng tác giả/bài báo");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(9);
		ch.setCellValue("Số người thuộc đơn vị/bài báo");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(10);
		ch.setCellValue("Kinh phí hỗ trợ/bài báo/tổng số cán bộ của đơn vị");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(11);
		ch.setCellValue("Tên người được nhận");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(12);
		ch.setCellValue("Số tiền nhận/người/bài báo");
		ch.setCellStyle(styleTitle);

		i_row += 1;
		rh = sh.createRow(i_row);
		ch = rh.createCell(4);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tạp chí, proceedings");

		ch = rh.createCell(5);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Số tạp chí, thời gian xuất bản");

		ch = rh.createCell(6);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("ISSN");

		sh.addMergedRegion(new CellRangeAddress(i_row - 1, i_row, 2, 2));
		sh.addMergedRegion(new CellRangeAddress(i_row - 1, i_row, 3, 3));
		sh.addMergedRegion(new CellRangeAddress(i_row - 1, i_row - 1, 4, 6));

		for (int j = 2; j <= 3; j++) {
			ch = rh.createCell(j);
			ch.setCellStyle(styleTitle);
		}
		for (int j = 7; j <= 12; j++) {
			ch = rh.createCell(j);
			ch.setCellStyle(styleTitle);
		}

		for (int j = 7; j <= 12; j++) {
			sh.addMergedRegion(new CellRangeAddress(i_row - 1, i_row, j, j));
		}

		i_row += 1;
		rh = sh.createRow(i_row);
		ch = rh.createCell(2);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Danh sách bài báo không nằm trong danh mục ISI, Scopus");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 12));
		i_row = createSegmentKV04(list_paper_international_journals,
				styleNormal, sh, i_row, delegator, mPaperCategory2Money,
				mId2Staff);

		i_row += 1;
		rh = sh.createRow(i_row);
		ch = rh.createCell(2);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Danh sách bài báo tạp chí trong nước");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 12));
		i_row = createSegmentKV04(list_paper_national_journals, styleNormal,
				sh, i_row, delegator, mPaperCategory2Money, mId2Staff);

		i_row += 1;
		rh = sh.createRow(i_row);
		ch = rh.createCell(2);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Danh sách bài báo hội nghị quốc tế");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 12));
		i_row = createSegmentKV04(list_paper_international_conferences,
				styleNormal, sh, i_row, delegator, mPaperCategory2Money,
				mId2Staff);

		i_row += 1;
		rh = sh.createRow(i_row);
		ch = rh.createCell(2);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Danh sách bài báo hội nghị trong nước");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 12));
		i_row = createSegmentKV04(list_paper_national_conferences, styleNormal,
				sh, i_row, delegator, mPaperCategory2Money, mId2Staff);

		createSheetListPapersKV04(wb, papers);

		createSheetKNC(wb, facultyId, academicYearId, delegator, userLoginId);

		createSheetKNCTotal(wb, facultyId, academicYearId, delegator,
				userLoginId);

		/*
		 * for(GenericValue p: list_paper_international_journals){ i_row += 1;
		 * Row r = sh.createRow(i_row);
		 * 
		 * 
		 * String paperId = (String)p.get("paperId"); List<GenericValue>
		 * staffsOfPaper = getStaffsOfPaper(paperId, delegator); String authors
		 * = (String)p.get("authors"); String paperName =
		 * (String)p.get("paperName"); String journalConferenceName =
		 * (String)p.get("journalConferenceName"); String volumn =
		 * (String)p.get("volumn"); String ISSN = (String)p.get("ISSN"); int
		 * nbAuthors = 1; String[] s = authors.split(","); if(s != null)
		 * nbAuthors = s.length; String paperCategoryId =
		 * (String)p.get("paperCategoryId"); Long money =
		 * mPaperCategory2Money.get(paperCategoryId); double moneyPerAuthor =
		 * money*1.0/nbAuthors;
		 * 
		 * Cell c = r.createCell(2); c.setCellValue(authors);
		 * c.setCellStyle(styleNormal);
		 * 
		 * c = r.createCell(3); c.setCellValue(paperName);
		 * c.setCellStyle(styleNormal);
		 * 
		 * c = r.createCell(4); c.setCellValue(journalConferenceName);
		 * c.setCellStyle(styleNormal);
		 * 
		 * c = r.createCell(5); c.setCellValue(volumn);
		 * c.setCellStyle(styleNormal);
		 * 
		 * c = r.createCell(6); c.setCellValue(ISSN);
		 * c.setCellStyle(styleNormal);
		 * 
		 * c = r.createCell(7); c.setCellValue(money + "");
		 * c.setCellStyle(styleNormal);
		 * 
		 * c = r.createCell(8); c.setCellValue(nbAuthors + "");
		 * c.setCellStyle(styleNormal);
		 * 
		 * c = r.createCell(9); c.setCellValue(staffsOfPaper.size() + "");
		 * c.setCellStyle(styleNormal);
		 * 
		 * double mp = moneyPerAuthor*nbAuthors; c = r.createCell(10);
		 * c.setCellValue(mp + ""); c.setCellStyle(styleNormal);
		 * 
		 * if(staffsOfPaper.size() > 0){ GenericValue st = staffsOfPaper.get(0);
		 * String staffName =
		 * (String)(mId2Staff.get(st.get("staffId")).get("staffName")); c =
		 * r.createCell(11); c.setCellValue(staffName);
		 * c.setCellStyle(styleNormal); }
		 * 
		 * 
		 * c = r.createCell(12); c.setCellValue(moneyPerAuthor + "");
		 * c.setCellStyle(styleNormal);
		 * 
		 * int start_r = i_row; for(int i = 1; i < staffsOfPaper.size(); i++){
		 * GenericValue st = staffsOfPaper.get(i); String staffName =
		 * (String)(mId2Staff.get(st.get("staffId")).get("staffName")); i_row +=
		 * 1; r = sh.createRow(i_row); c = r.createCell(11);
		 * c.setCellValue(staffName); c.setCellStyle(styleNormal);
		 * 
		 * c = r.createCell(12); c.setCellValue(moneyPerAuthor + "");
		 * c.setCellStyle(styleNormal);
		 * 
		 * for(int j = 2; j <= 10; j++){ c = r.createCell(j);
		 * c.setCellStyle(styleNormal); } }
		 * 
		 * if(start_r < i_row)for(int j = 2; j <= 10; j++){
		 * sh.addMergedRegion(new CellRangeAddress(start_r, i_row, j, j)); } }
		 */
		return wb;
	}

	public static HSSFWorkbook createExcelFormKNC(Delegator delegator,
			String academicYearId, String facultyId, String userLoginId) {

		// get list of paper category hour-budget
		List<GenericValue> paperHourBudget = getPaperCategoryHourBudget(
				delegator, academicYearId);
		Map<String, Long> mPaperCategory2Money = FastMap.newInstance();

		if (paperHourBudget != null) {
			for (GenericValue gv : paperHourBudget) {
				String cat = (String) gv.get("paperCategoryId");
				long money = (long) gv.get("budget");
				mPaperCategory2Money.put(cat, money);
			}
		} else {
			Debug.log(module + "::createExcelFormKNC, hour-budget of year "
					+ academicYearId + " NOT EXISTS");
			;

		}

		List<GenericValue> staffs = getListStaffsOfFaculty(delegator, facultyId);
		List<GenericValue> all_staffs = getListStaffs(delegator);

		HashMap<String, GenericValue> mId2Staff = new HashMap<String, GenericValue>();

		List<String> staffIDs = FastList.newInstance();
		for (GenericValue gv : staffs) {
			staffIDs.add((String) gv.get("staffId"));
		}
		for (GenericValue st : all_staffs) {
			mId2Staff.put((String) st.get("staffId"), st);
		}

		List<EntityCondition> conds = FastList.newInstance();
		conds.add(EntityCondition.makeCondition("academicYearId",
				EntityOperator.EQUALS, academicYearId));
		conds.add(EntityCondition.makeCondition("statusId",
				EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

		List<GenericValue> papers = FastList.newInstance();
		try {
			papers = delegator.findList("PaperView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Debug.log(module + "::createExcelFormKNC, papers.sz = " + papers.size());

		List<GenericValue> list_paper_international_journals = FastList
				.newInstance();
		List<GenericValue> list_paper_national_journals = FastList
				.newInstance();
		List<GenericValue> list_paper_international_conferences = FastList
				.newInstance();
		List<GenericValue> list_paper_national_conferences = FastList
				.newInstance();

		for (GenericValue p : papers) {
			String paperCategoryId = (String) p.get("paperCategoryId");
			Debug.log(module + "::createExcelFormKV04, paper "
					+ (String) p.get("paperName") + ", category = "
					+ paperCategoryId);
			if (paperCategoryId == null || paperCategoryId.equals(""))
				continue;

			if (paperCategoryId.equals("JINT_Other")) {
				list_paper_international_journals.add(p);
			} else if (paperCategoryId.equals("JDOM_Other")) {
				list_paper_national_journals.add(p);
			} else if (paperCategoryId.equals("CINT_Other")) {
				list_paper_international_conferences.add(p);
			} else if (paperCategoryId.equals("CDOM_Other")) {
				list_paper_national_conferences.add(p);
			}
		}

		// start renderExcel
		HSSFWorkbook wb = new HSSFWorkbook();

		List<String> groups = BKEunivUtils.getListSecurityGroupsOfUserLogin(
				delegator, userLoginId);
		boolean staffOnly = true;
		for (String g : groups) {
			if (g.equals("HUST_KHCN_ADMIN") || g.equals("SCHOOL_KHCN_ADMIN")
					|| g.equals("SUPER_ADMIN"))
				staffOnly = false;
		}
		if(!staffOnly)
			createSheetListPapersKV04(wb, papers);

		// chi so KNC
		createSheetKNC(wb, facultyId, academicYearId, delegator, userLoginId);
		createSheetKNCTotal(wb, facultyId, academicYearId, delegator,
				userLoginId);

		// chi so Khoi luong
		createSheetKKL(wb, facultyId, academicYearId, delegator, userLoginId);
		createSheetKKLTotal(wb, facultyId, academicYearId, delegator,
				userLoginId);

		return wb;
	}

	public static void createISISheet(HSSFWorkbook wb,
			List<GenericValue> papers1, List<GenericValue> papers2,
			String academicYearId, String facultyId, Delegator delegator) {
		// sheet ISI
		Sheet sh = wb.createSheet("ISI");
		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 3000);
		sh.setColumnWidth(2, 12000);
		sh.setColumnWidth(3, 12000);
		sh.setColumnWidth(4, 6000);
		sh.setColumnWidth(5, 6000);
		sh.setColumnWidth(6, 6000);
		sh.setColumnWidth(7, 6000);
		sh.setColumnWidth(8, 6000);
		sh.setColumnWidth(9, 6000);
		sh.setColumnWidth(10, 6000);
		sh.setColumnWidth(11, 6000);
		sh.setColumnWidth(12, 6000);

		// ----create style font bold
		HSSFCellStyle cellStyleBold = getFontBold(wb);
		HSSFCellStyle cellStyleLeft = getAttributeLeftFullBoder(wb);
		HSSFCellStyle cellStyleRight = getAttributeRightFullBoder(wb);
		HSSFCellStyle cellStyleCenterBoldFullBorder = getAttributeCenterBoldFullBorder(wb);
		cellStyleRight.setWrapText(true);
		cellStyleLeft.setWrapText(true);
		int i_row = 0;
		// ----start header in excel
		i_row++;
		Row row_header = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));
		String[] str_header1 = new String[] { "DANH MỤC BÀI BÁO CỦA CÁN BỘ TRƯỜNG ĐHBKHN" };
		createRowInExcel(1, 1, str_header1, row_header, cellStyleBold);
		i_row++;
		row_header = sh.createRow(i_row);
		String[] str_header2 = new String[] { "ĐĂNG TRONG TẠP CHÍ QUỐC TẾ TRONG DANH MỤC SCI VÀ SCIE NĂM HỌC "
				+ academicYearId };
		createRowInExcel(1, 1, str_header2, row_header, cellStyleBold);
		// ----end header in excel
		// ----start title in excel
		i_row = i_row + 4;
		Row row_title = sh.createRow(i_row);
		String text_title = getFacultyName(delegator, facultyId);
		String[] str_title = new String[] { "Khoa/viện: " + text_title };
		createRowInExcel(1, 1, str_title, row_title, cellStyleBold);
		// ----end title in excel
		// ----start header table in excel
		i_row = i_row + 4;
		int i_row_1 = i_row + 1;
		int i_row_2 = i_row + 2;
		Row row_header_table = sh.createRow(i_row);
		String[] str1 = new String[] { "STT", "Họ và Tên tác giả",
				"Tên bài báo", "Tạp chí/Proceedings", "", "" };
		createRowInExcel(1, 6, str1, row_header_table,
				cellStyleCenterBoldFullBorder);
		sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_1 + ":B"
				+ i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("C" + i_row_1 + ":C"
				+ i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("D" + i_row_1 + ":D"
				+ i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("E" + i_row_1 + ":G"
				+ i_row_1));
		i_row++;
		row_header_table = sh.createRow(i_row);
		String[] str2 = new String[] { "", "", "", "Tên tạp chí",
				"Số và thời gian xuất bản", "Đường link", "Chỉ số ISSN",
				"Tổng số tác giả", "Số tác giả của trường",
				"Tác giả đầu tiên là Corresponding author",
				"Tác giả đầu tiên không phải là Corresponding author",
				"Tác giả là Corresponding author", "Các tác giả còn lại" };
		createRowInExcel(1, 13, str2, row_header_table,
				cellStyleCenterBoldFullBorder);
		// ----end header table in excel
		// ----start table
		String[] arrYear = academicYearId.split("-");
		String year_start = arrYear[0];
		String year_end = arrYear[1];
		i_row++;
		int i_row_t = i_row + 1;
		Row row_table = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_t + ":G"
				+ i_row_t));
		String[] str_1 = new String[] {
				"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/7/"
						+ year_start + " - 31/12/" + year_start, "", "", "",
				"", "" };
		createRowInExcel(1, 6, str_1, row_table, cellStyleLeft);
		int m = 0;
		for (GenericValue p : papers1) {
			String authors = (String) p.get("authors");
			String name = (String) p.get("paperName");
			String journal = (String) p.get("journalConferenceName");
			String vol = (String) p.get("volumn");
			String issn = (String) p.get("ISSN");
			int nbIntAuthors = getNbInternalAuthors(delegator,
					p.getString("paperId"));
			int nbAuthors = 0;
			String[] s = authors.split(",");
			if (s != null)
				nbAuthors = s.length;

			String[] str_papers1 = new String[] { authors, name, journal, vol,
					p.getString("link"), issn, nbAuthors + "",
					nbIntAuthors + "" };
			Map<String, Object> infos = getAuthorInfos(delegator,
					p.getString("paperId"));

			m++;
			i_row++;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellStyle(cellStyleRight);
			c.setCellValue(m);

			createRowInExcel(2, 9, str_papers1, r, cellStyleLeft);

			// add author information
			int i_col = 9;
			String mark = "";
			i_col++;
			c = r.createCell(i_col);
			if (infos.get("firstAuthorIsCorresponding") != null
					&& infos.get("firstAuthorIsCorresponding").equals("T"))
				mark = "X";
			c.setCellValue(mark);
			c.setCellStyle(cellStyleLeft);

			i_col++;
			mark = "";
			c = r.createCell(i_col);
			if (infos.get("firstAuthorIsNotCorresponding") != null
					&& infos.get("firstAuthorIsNotCorresponding").equals("T"))
				mark = "X";
			c.setCellValue(mark);
			c.setCellStyle(cellStyleLeft);

			i_col++;
			c = r.createCell(i_col);
			if (infos.get("correspondingAuthor") != null)
				c.setCellValue((String) infos.get("correspondingAuthor"));
			c.setCellStyle(cellStyleLeft);

			i_col++;
			c = r.createCell(i_col);
			if (infos.get("nonCorrespondingAuthor") != null)
				c.setCellValue((String) infos.get("nonCorrespondingAuthor"));
			c.setCellStyle(cellStyleLeft);
		}
		i_row++;
		i_row_t = i_row + 1;
		row_table = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_t + ":G"
				+ i_row_t));
		String[] str_2 = new String[] {
				"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/1/"
						+ year_end + " - 30/6/" + year_end, "", "", "", "", "" };
		createRowInExcel(1, 6, str_2, row_table, cellStyleLeft);
		for (GenericValue p : papers2) {
			String authors = (String) p.get("authors");
			String name = (String) p.get("paperName");
			String journal = (String) p.get("journalConferenceName");
			String vol = (String) p.get("volumn");
			String issn = (String) p.get("ISSN");
			int nbIntAuthors = getNbInternalAuthors(delegator,
					p.getString("paperId"));
			int nbAuthors = 0;
			String[] s = authors.split(",");
			if (s != null)
				nbAuthors = s.length;

			String[] str_papers2 = new String[] { authors, name, journal, vol,
					p.getString("link"), issn, nbAuthors + "",
					nbIntAuthors + "" };

			Map<String, Object> infos = getAuthorInfos(delegator,
					p.getString("paperId"));
			m++;
			i_row++;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellStyle(cellStyleRight);
			c.setCellValue(m);

			createRowInExcel(2, 9, str_papers2, r, cellStyleLeft);
			int i_col = 9;
			// add author information
			String mark = "";
			i_col++;
			c = r.createCell(i_col);
			if (infos.get("firstAuthorIsCorresponding") != null
					&& infos.get("firstAuthorIsCorresponding").equals("T"))
				mark = "X";
			c.setCellStyle(cellStyleLeft);
			c.setCellValue(mark);

			i_col++;
			mark = "";
			c = r.createCell(i_col);
			if (infos.get("firstAuthorIsNotCorresponding") != null
					&& infos.get("firstAuthorIsNotCorresponding").equals("T"))
				mark = "X";
			c.setCellValue(mark);
			c.setCellStyle(cellStyleLeft);

			i_col++;
			c = r.createCell(i_col);
			if (infos.get("correspondingAuthor") != null)
				c.setCellValue((String) infos.get("correspondingAuthor"));
			c.setCellStyle(cellStyleLeft);

			i_col++;
			c = r.createCell(i_col);
			if (infos.get("nonCorrespondingAuthor") != null)
				c.setCellValue((String) infos.get("nonCorrespondingAuthor"));
			c.setCellStyle(cellStyleLeft);
		}
		// ----end table

		// ----start footer in excel
		i_row = i_row + 3;// ----
		Row rowFooter = sh.createRow(i_row);
		Date timeCurrent = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeCurrent);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String[] str_footer1 = new String[] { "Ngày " + day + " tháng " + month
				+ " năm " + year };
		createRowInExcel(5, 5, str_footer1, rowFooter, cellStyleBold);
		i_row = i_row + 2;// ----
		rowFooter = sh.createRow(i_row);
		String[] str_footer2 = new String[] { "LÃNH ĐẠO KHOA/VIỆN" };
		createRowInExcel(5, 5, str_footer2, rowFooter, cellStyleBold);
		// ----end footer in excel

	}

	public static void createScopusSheet(HSSFWorkbook wb,
			List<GenericValue> papers1, List<GenericValue> papers2,
			String academicYearId, String facultyId, Delegator delegator) {
		// sheet ISI
		Sheet sh = wb.createSheet("Scopus");
		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 3000);
		sh.setColumnWidth(2, 12000);
		sh.setColumnWidth(3, 12000);
		sh.setColumnWidth(4, 6000);
		sh.setColumnWidth(5, 6000);
		sh.setColumnWidth(6, 6000);
		sh.setColumnWidth(7, 6000);
		sh.setColumnWidth(8, 6000);
		sh.setColumnWidth(9, 6000);
		sh.setColumnWidth(10, 6000);
		sh.setColumnWidth(11, 6000);
		sh.setColumnWidth(12, 6000);

		// ----create style font bold
		HSSFCellStyle cellStyleBold = getFontBold(wb);
		HSSFCellStyle cellStyleLeft = getAttributeLeftFullBoder(wb);
		HSSFCellStyle cellStyleRight = getAttributeRightFullBoder(wb);
		HSSFCellStyle cellStyleCenterBoldFullBorder = getAttributeCenterBoldFullBorder(wb);
		cellStyleRight.setWrapText(true);
		cellStyleLeft.setWrapText(true);
		int i_row = 0;
		// ----start header in excel
		i_row++;
		Row row_header = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));
		String[] str_header1 = new String[] { "DANH MỤC BÀI BÁO CỦA CÁN BỘ TRƯỜNG ĐHBKHN" };
		createRowInExcel(1, 1, str_header1, row_header, cellStyleBold);
		i_row++;
		row_header = sh.createRow(i_row);
		String[] str_header2 = new String[] { "TRONG DANH MỤC SCOPUS NĂM HỌC "
				+ academicYearId };
		createRowInExcel(1, 1, str_header2, row_header, cellStyleBold);
		// ----end header in excel
		// ----start title in excel
		i_row = i_row + 4;
		Row row_title = sh.createRow(i_row);
		String text_title = getFacultyName(delegator, facultyId);
		String[] str_title = new String[] { "Khoa/viện: " + text_title };
		createRowInExcel(1, 1, str_title, row_title, cellStyleBold);
		// ----end title in excel
		// ----start header table in excel
		i_row = i_row + 4;
		int i_row_1 = i_row + 1;
		int i_row_2 = i_row + 2;
		Row row_header_table = sh.createRow(i_row);
		String[] str1 = new String[] { "STT", "Họ và Tên tác giả",
				"Tên bài báo", "Tạp chí/Proceedings", "", "" };
		createRowInExcel(1, 6, str1, row_header_table,
				cellStyleCenterBoldFullBorder);
		sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_1 + ":B"
				+ i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("C" + i_row_1 + ":C"
				+ i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("D" + i_row_1 + ":D"
				+ i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("E" + i_row_1 + ":G"
				+ i_row_1));
		i_row++;
		row_header_table = sh.createRow(i_row);
		String[] str2 = new String[] { "", "", "", "Tên tạp chí",
				"Số và thời gian xuất bản", "Đường link", "Chỉ số ISSN",
				"Tổng số tác giả", "Số tác giả của trường",
				"Tác giả đầu tiên là Corresponding author",
				"Tác giả đầu tiên không phải là Corresponding author",
				"Tác giả là Corresponding author", "Các tác giả còn lại" };
		createRowInExcel(1, 13, str2, row_header_table,
				cellStyleCenterBoldFullBorder);
		// ----end header table in excel
		// ----start table
		String[] arrYear = academicYearId.split("-");
		String year_start = arrYear[0];
		String year_end = arrYear[1];
		i_row++;
		int i_row_t = i_row + 1;
		Row row_table = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_t + ":G"
				+ i_row_t));
		String[] str_1 = new String[] {
				"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/7/"
						+ year_start + " - 31/12/" + year_start, "", "", "",
				"", "" };
		createRowInExcel(1, 6, str_1, row_table, cellStyleLeft);
		int m = 0;
		for (GenericValue p : papers1) {
			String authors = (String) p.get("authors");
			String name = (String) p.get("paperName");
			String journal = (String) p.get("journalConferenceName");
			String vol = (String) p.get("volumn");
			String issn = (String) p.get("ISSN");
			int nbIntAuthors = getNbInternalAuthors(delegator,
					p.getString("paperId"));
			int nbAuthors = 0;
			String[] s = authors.split(",");
			if (s != null)
				nbAuthors = s.length;

			String[] str_papers1 = new String[] { authors, name, journal, vol,
					p.getString("link"), issn, nbAuthors + "",
					nbIntAuthors + "" };
			Map<String, Object> infos = getAuthorInfos(delegator,
					p.getString("paperId"));

			m++;
			i_row++;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellStyle(cellStyleRight);
			c.setCellValue(m);

			createRowInExcel(2, 9, str_papers1, r, cellStyleLeft);

			// add author information
			int i_col = 9;
			String mark = "";
			i_col++;
			c = r.createCell(i_col);
			if (infos.get("firstAuthorIsCorresponding") != null
					&& infos.get("firstAuthorIsCorresponding").equals("T"))
				mark = "X";
			c.setCellValue(mark);
			c.setCellStyle(cellStyleLeft);

			i_col++;
			mark = "";
			c = r.createCell(i_col);
			if (infos.get("firstAuthorIsNotCorresponding") != null
					&& infos.get("firstAuthorIsNotCorresponding").equals("T"))
				mark = "X";
			c.setCellValue(mark);
			c.setCellStyle(cellStyleLeft);

			i_col++;
			c = r.createCell(i_col);
			if (infos.get("correspondingAuthor") != null)
				c.setCellValue((String) infos.get("correspondingAuthor"));
			c.setCellStyle(cellStyleLeft);

			i_col++;
			c = r.createCell(i_col);
			if (infos.get("nonCorrespondingAuthor") != null)
				c.setCellValue((String) infos.get("nonCorrespondingAuthor"));
			c.setCellStyle(cellStyleLeft);
		}
		i_row++;
		i_row_t = i_row + 1;
		row_table = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_t + ":G"
				+ i_row_t));
		String[] str_2 = new String[] {
				"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/1/"
						+ year_end + " - 30/6/" + year_end, "", "", "", "", "" };
		createRowInExcel(1, 6, str_2, row_table, cellStyleLeft);
		for (GenericValue p : papers2) {
			String authors = (String) p.get("authors");
			String name = (String) p.get("paperName");
			String journal = (String) p.get("journalConferenceName");
			String vol = (String) p.get("volumn");
			String issn = (String) p.get("ISSN");
			int nbIntAuthors = getNbInternalAuthors(delegator,
					p.getString("paperId"));
			int nbAuthors = 0;
			String[] s = authors.split(",");
			if (s != null)
				nbAuthors = s.length;

			String[] str_papers2 = new String[] { authors, name, journal, vol,
					p.getString("link"), issn, nbAuthors + "",
					nbIntAuthors + "" };

			Map<String, Object> infos = getAuthorInfos(delegator,
					p.getString("paperId"));
			m++;
			i_row++;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellStyle(cellStyleRight);
			c.setCellValue(m);

			createRowInExcel(2, 9, str_papers2, r, cellStyleLeft);
			int i_col = 9;
			// add author information
			String mark = "";
			i_col++;
			c = r.createCell(i_col);
			if (infos.get("firstAuthorIsCorresponding") != null
					&& infos.get("firstAuthorIsCorresponding").equals("T"))
				mark = "X";
			c.setCellStyle(cellStyleLeft);
			c.setCellValue(mark);

			i_col++;
			mark = "";
			c = r.createCell(i_col);
			if (infos.get("firstAuthorIsNotCorresponding") != null
					&& infos.get("firstAuthorIsNotCorresponding").equals("T"))
				mark = "X";
			c.setCellValue(mark);
			c.setCellStyle(cellStyleLeft);

			i_col++;
			c = r.createCell(i_col);
			if (infos.get("correspondingAuthor") != null)
				c.setCellValue((String) infos.get("correspondingAuthor"));
			c.setCellStyle(cellStyleLeft);

			i_col++;
			c = r.createCell(i_col);
			if (infos.get("nonCorrespondingAuthor") != null)
				c.setCellValue((String) infos.get("nonCorrespondingAuthor"));
			c.setCellStyle(cellStyleLeft);
		}
		// ----end table

		// ----start footer in excel
		i_row = i_row + 3;// ----
		Row rowFooter = sh.createRow(i_row);
		Date timeCurrent = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeCurrent);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String[] str_footer1 = new String[] { "Ngày " + day + " tháng " + month
				+ " năm " + year };
		createRowInExcel(5, 5, str_footer1, rowFooter, cellStyleBold);
		i_row = i_row + 2;// ----
		rowFooter = sh.createRow(i_row);
		String[] str_footer2 = new String[] { "LÃNH ĐẠO KHOA/VIỆN" };
		createRowInExcel(5, 5, str_footer2, rowFooter, cellStyleBold);
		// ----end footer in excel

	}

	public static HSSFWorkbook createExcelFormISI(Delegator delegator,
			String academicYearId, String facultyId) {

		List<GenericValue> isi_papers = getListPaperISI(delegator,
				academicYearId, facultyId);

		List<GenericValue> isi_papers1 = new ArrayList<GenericValue>();
		List<GenericValue> isi_papers2 = new ArrayList<GenericValue>();

		for (GenericValue p : isi_papers) {
			long month = 0;
			if (p.get("month") != null)
				month = (long) p.get("month");

			if (month >= 7 && month <= 12)
				isi_papers1.add(p);
			else if (month >= 1 && month <= 6)
				isi_papers2.add(p);
		}
		List<GenericValue> scopus_papers = getListPaperScopus(delegator,
				academicYearId, facultyId);

		List<GenericValue> scopus_papers1 = new ArrayList<GenericValue>();
		List<GenericValue> scopus_papers2 = new ArrayList<GenericValue>();

		for (GenericValue p : scopus_papers) {
			long month = 0;
			if (p.get("month") != null)
				month = (long) p.get("month");

			if (month >= 7 && month <= 12)
				scopus_papers1.add(p);
			else if (month >= 1 && month <= 6)
				scopus_papers2.add(p);
		}

		// System.out.println("aaaaaa" + papers1.size());
		// System.out.println("adadadada" + papers2.size());
		// start renderExcel

		HSSFWorkbook wb = new HSSFWorkbook();

		createISISheet(wb, isi_papers1, isi_papers2, academicYearId, facultyId,
				delegator);
		createScopusSheet(wb, scopus_papers1, scopus_papers2, academicYearId,
				facultyId, delegator);

		/*
		 * // sheet ISI Sheet sh = wb.createSheet("ISI"); sh.setColumnWidth(0,
		 * 500); sh.setColumnWidth(1, 3000); sh.setColumnWidth(2, 12000);
		 * sh.setColumnWidth(3, 12000); sh.setColumnWidth(4, 6000);
		 * sh.setColumnWidth(5, 6000); sh.setColumnWidth(6, 6000);
		 * 
		 * // ----create style font bold HSSFCellStyle cellStyleBold =
		 * getFontBold(wb); HSSFCellStyle cellStyleLeft =
		 * getAttributeLeftFullBoder(wb); HSSFCellStyle cellStyleRight =
		 * getAttributeRightFullBoder(wb); HSSFCellStyle
		 * cellStyleCenterBoldFullBorder = getAttributeCenterBoldFullBorder(wb);
		 * int i_row = 0; // ----start header in excel i_row++; Row row_header =
		 * sh.createRow(i_row);
		 * sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2")); String[]
		 * str_header1 = new String[] {
		 * "DANH MỤC BÀI BÁO CỦA CÁN BỘ TRƯỜNG ĐHBKHN" }; createRowInExcel(1, 1,
		 * str_header1, row_header, cellStyleBold); i_row++; row_header =
		 * sh.createRow(i_row); String[] str_header2 = new String[] {
		 * "ĐĂNG TRONG TẠP CHÍ QUỐC TẾ TRONG DANH MỤC SCI VÀ SCIE NĂM HỌC " +
		 * academicYearId }; createRowInExcel(1, 1, str_header2, row_header,
		 * cellStyleBold); // ----end header in excel // ----start title in
		 * excel i_row = i_row + 4; Row row_title = sh.createRow(i_row); String
		 * text_title = getFacultyName(delegator, facultyId); String[] str_title
		 * = new String[] { "Khoa/viện: " + text_title }; createRowInExcel(1, 1,
		 * str_title, row_title, cellStyleBold); // ----end title in excel //
		 * ----start header table in excel i_row = i_row + 4; int i_row_1 =
		 * i_row + 1; int i_row_2 = i_row + 2; Row row_header_table =
		 * sh.createRow(i_row); String[] str1 = new String[] { "STT",
		 * "Họ và Tên tác giả", "Tên bài báo", "Tạp chí/Proceedings", "", "" };
		 * createRowInExcel(1, 6, str1, row_header_table,
		 * cellStyleCenterBoldFullBorder);
		 * sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_1 + ":B" +
		 * i_row_2)); sh.addMergedRegion(CellRangeAddress.valueOf("C" + i_row_1
		 * + ":C" + i_row_2)); sh.addMergedRegion(CellRangeAddress.valueOf("D" +
		 * i_row_1 + ":D" + i_row_2));
		 * sh.addMergedRegion(CellRangeAddress.valueOf("E" + i_row_1 + ":G" +
		 * i_row_1)); i_row++; row_header_table = sh.createRow(i_row); String[]
		 * str2 = new String[] { "", "", "", "Tên tạp chí",
		 * "Số và thời gian xuất bản", "Chỉ số ISSN" }; createRowInExcel(1, 6,
		 * str2, row_header_table, cellStyleCenterBoldFullBorder); // ----end
		 * header table in excel // ----start table String[] arrYear =
		 * academicYearId.split("-"); String year_start = arrYear[0]; String
		 * year_end = arrYear[1]; i_row++; int i_row_t = i_row + 1; Row
		 * row_table = sh.createRow(i_row);
		 * sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_t + ":G" +
		 * i_row_t)); String[] str_1 = new String[] {
		 * "CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/7/"
		 * + year_start + " - 31/12/" + year_start, "", "", "", "", "" };
		 * createRowInExcel(1, 6, str_1, row_table, cellStyleLeft); int m = 0;
		 * for (GenericValue p : papers1) { String authors = (String)
		 * p.get("authors"); String name = (String) p.get("paperName"); String
		 * journal = (String) p.get("journalConferenceName"); String vol =
		 * (String) p.get("volumn"); String issn = (String) p.get("ISSN");
		 * String[] str_papers1 = new String[] { authors, name, journal, vol,
		 * issn }; m++; i_row++; Row r = sh.createRow(i_row); Cell c =
		 * r.createCell(1); c.setCellStyle(cellStyleRight); c.setCellValue(m);
		 * 
		 * createRowInExcel(2, 6, str_papers1, r, cellStyleLeft); } i_row++;
		 * i_row_t = i_row + 1; row_table = sh.createRow(i_row);
		 * sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_t + ":G" +
		 * i_row_t)); String[] str_2 = new String[] {
		 * "CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/1/"
		 * + year_end + " - 30/6/2017" + year_end, "", "", "", "", "" };
		 * createRowInExcel(1, 6, str_2, row_table, cellStyleLeft); for
		 * (GenericValue p : papers2) { String authors = (String)
		 * p.get("authors"); String name = (String) p.get("paperName"); String
		 * journal = (String) p.get("journalConferenceName"); String vol =
		 * (String) p.get("volumn"); String issn = (String) p.get("ISSN");
		 * String[] str_papers2 = new String[] { authors, name, journal, vol,
		 * issn }; m++; i_row++; Row r = sh.createRow(i_row); Cell c =
		 * r.createCell(1); c.setCellStyle(cellStyleRight); c.setCellValue(m);
		 * 
		 * createRowInExcel(2, 6, str_papers2, r, cellStyleLeft); } // ----end
		 * table
		 * 
		 * // ----start footer in excel i_row = i_row + 3;// ---- Row rowFooter
		 * = sh.createRow(i_row); Date timeCurrent = new Date(); Calendar cal =
		 * Calendar.getInstance(); cal.setTime(timeCurrent); int year =
		 * cal.get(Calendar.YEAR); int month = cal.get(Calendar.MONTH) + 1; int
		 * day = cal.get(Calendar.DAY_OF_MONTH); String[] str_footer1 = new
		 * String[] { "Ngày " + day + " tháng " + month + " năm " + year };
		 * createRowInExcel(5, 5, str_footer1, rowFooter, cellStyleBold); i_row
		 * = i_row + 2;// ---- rowFooter = sh.createRow(i_row); String[]
		 * str_footer2 = new String[] { "LÃNH ĐẠO KHOA/VIỆN" };
		 * createRowInExcel(5, 5, str_footer2, rowFooter, cellStyleBold); //
		 * ----end footer in excel
		 */

		return wb;
	}

	public static HSSFWorkbook createExcelFormBM010203(Delegator delegator,
			String academicYearId, String facultyId, String departmentId) {

		List<GenericValue> staffs = getListStaffsOfDepartment(delegator,
				departmentId);
		List<GenericValue> categoryHours = getPaperCategoryHourBudget(
				delegator, academicYearId);
		Map<String, Long> mCategory2Hour = FastMap.newInstance();
		if (categoryHours != null)
			for (GenericValue gv : categoryHours) {
				mCategory2Hour.put((String) gv.get("paperCategoryId"),
						(Long) gv.get("hour"));
			}

		Map<GenericValue, Integer> mStaff2NbrPaperJournal = FastMap
				.newInstance();
		Map<GenericValue, Integer> mStaff2NbrPaperConference = FastMap
				.newInstance();
		Map<GenericValue, Long> mStaff2NbrHours = FastMap.newInstance();
		Map<GenericValue, List<GenericValue>> mStaff2Papers = FastMap
				.newInstance();

		for (GenericValue st : staffs) {
			String staffId = (String) st.get("staffId");
			List<GenericValue> papers = getPapersOfStaffAcademicYear(delegator,
					staffId, academicYearId);

			mStaff2Papers.put(st, papers);

			int nbJournals = 0;
			int nbConferences = 0;
			long hours = 0;
			for (GenericValue p : papers) {
				String paperCategoryId = (String) p.get("paperCategoryId");
				if (paperCategoryId.equals("JINT_SCI")
						|| paperCategoryId.equals("JDOM_Other")
						|| paperCategoryId.equals("JINT_SCIE")
						|| paperCategoryId.equals("JINT_Other")
						|| paperCategoryId.equals("JINT_SCOPUS")) {
					nbJournals++;
				} else {
					nbConferences++;
				}

				String authors = (String) p.get("authors");
				long paperHour = 0;
				int sz = 1;
				if (authors != null) {
					String[] s = authors.split(",");
					sz = s.length;
				}
				if (mCategory2Hour.get(paperCategoryId) != null) {
					paperHour = mCategory2Hour.get(paperCategoryId);
				}
				hours += paperHour / sz;
			}
			mStaff2NbrPaperJournal.put(st, nbJournals);
			mStaff2NbrPaperConference.put(st, nbConferences);
			mStaff2NbrHours.put(st, hours);
		}

		// start renderExcel
		HSSFWorkbook wb = new HSSFWorkbook();

		// sheet 01-BM
		Sheet sh = wb.createSheet("01-BM");
		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 6000);
		sh.setColumnWidth(2, 6000);
		sh.setColumnWidth(3, 6000);
		sh.setColumnWidth(4, 6000);
		sh.setColumnWidth(5, 6000);
		int i_row = 0;

		// ----style font in excel
		HSSFCellStyle cellStyleBold = getFontBold(wb);
		HSSFCellStyle cellStyleLeft = getAttributeLeftFullBoder(wb);
		HSSFCellStyle cellStyleLeftBold = getAttributeLeftBoldFullBoder(wb);
		HSSFCellStyle cellStyleRight = getAttributeRightFullBoder(wb);
		HSSFCellStyle cellStyleCenterBoldFullBorder = getAttributeCenterBoldFullBorder(wb);
		// ----style font in excel
		// ----start header in excel
		i_row++;
		Row row_header = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));// merged columns
		String[] str_header1 = new String[] { "BẢNG TỔNG HỢP KÊ KHAI KHỐI LƯỢNG NGHIÊN CỨU KHOA HỌC  NĂM HỌC "
				+ academicYearId };
		createRowInExcel(1, 1, str_header1, row_header, cellStyleBold);
		i_row++;
		row_header = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B3:E3"));// merged columns
		String[] str_header2 = new String[] { "(BÀI BÁO KHOA HỌC)" };
		createRowInExcel(1, 1, str_header2, row_header, cellStyleBold);
		// ----end header in excel
		// ----start title in excel
		i_row = i_row + 2;
		Row row_title = sh.createRow(i_row);
		String departmentName = getDepartmentName(delegator, departmentId);
		String[] str_title1 = new String[] { "Bộ môn : " + departmentName };
		createRowInExcel(1, 1, str_title1, row_title, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("B5:C5"));// merged columns
		String facultyName = getFacultyName(delegator, facultyId);
		String[] str_title2 = new String[] { "Khoa(Viện, Trung tâm) : "
				+ facultyName };
		createRowInExcel(5, 5, str_title2, row_title, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("F5:I5"));// merged columns
		// ----end title in excel
		// ----start header_table
		i_row = i_row + 4;
		int i_row_1 = i_row + 1;
		int i_row_2 = i_row + 2;
		Row row_header_table = sh.createRow(i_row);
		String[] str_header_table = new String[] { "STT", "Họ và tên",
				"Tổng số bài báo đăng tạp chí, Proceeding", "",
				"Tổng số giờ quy đổi của người kê khai (I)" };
		createRowInExcel(1, 5, str_header_table, row_header_table,
				cellStyleCenterBoldFullBorder);
		sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_1 + ":B"
				+ i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("C" + i_row_1 + ":C"
				+ i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("D" + i_row_1 + ":E"
				+ i_row_1));
		sh.addMergedRegion(CellRangeAddress.valueOf("F" + i_row_1 + ":F"
				+ i_row_2));
		i_row++;
		row_header_table = sh.createRow(i_row);
		String[] str2 = new String[] { "", "", "Tạp chí", "Proceedings", "" };
		createRowInExcel(1, 5, str2, row_header_table,
				cellStyleCenterBoldFullBorder);
		// ----end header_table
		// ----start index of table in excel
		int count = 0;
		for (GenericValue st : staffs) {
			String name = (String) st.get("staffName");
			count++;
			i_row += 1;
			Row r = sh.createRow(i_row);
			String[] str_stt = new String[] { "" + count };
			createRowInExcel(1, 1, str_stt, r, cellStyleRight);
			String[] str_1 = new String[] { name,
					"" + mStaff2NbrPaperJournal.get(st),
					"" + mStaff2NbrPaperConference.get(st),
					"" + mStaff2NbrHours.get(st) };
			createRowInExcel(2, 5, str_1, r, cellStyleLeft);
		}
		i_row++;
		Row row_table = sh.createRow(i_row);
		String[] str_total = new String[] { "", "Total", "", "", "0" };
		createRowInExcel(1, 5, str_total, row_table,
				cellStyleCenterBoldFullBorder);
		// ----end table in excel
		// ----start footer in excel
		i_row = i_row + 3;
		Row rowFooter = sh.createRow(i_row);
		String[] str_footer1 = new String[] { "Ghi chú:  Mục (I)  lấy từ bản kê khai của từng cá nhân" };
		createRowInExcel(1, 1, str_footer1, rowFooter, cellStyleBold);
		i_row = i_row + 4;
		rowFooter = sh.createRow(i_row);
		Date timeCurrent = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeCurrent);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String[] str_footer2 = new String[] { "Hà Nội, Ngày " + day + " tháng "
				+ month + " năm " + year };
		createRowInExcel(4, 4, str_footer2, rowFooter, cellStyleBold);
		i_row++;
		rowFooter = sh.createRow(i_row);
		String[] str_footer3 = new String[] { "TRƯỞNG BỘ MÔN", "", "",
				"TRƯỞNG KHOA/VIỆN/TT" };
		createRowInExcel(1, 4, str_footer3, rowFooter, cellStyleBold);
		// ----end footer in excel 01-BM

		// sheet 02-BM
		sh = wb.createSheet("02-BM");
		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 3000);
		sh.setColumnWidth(2, 6000);
		sh.setColumnWidth(3, 6000);
		sh.setColumnWidth(4, 6000);
		sh.setColumnWidth(5, 6000);
		sh.setColumnWidth(6, 6000);
		sh.setColumnWidth(7, 6000);
		sh.setColumnWidth(8, 6000);
		sh.setColumnWidth(9, 6000);
		int i_row_02_BM = 0;
		// ----start header in excel
		i_row_02_BM++;
		Row row_header_02_BM = sh.createRow(i_row_02_BM);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));// merged columns
		String[] str_header_02_BM = new String[] { "BẢNG TỔNG HỢP KÊ KHAI KHỐI LƯỢNG NGHIÊN CỨU KHOA HỌC  NĂM HỌC "
				+ academicYearId };
		createRowInExcel(1, 1, str_header_02_BM, row_header_02_BM,
				cellStyleBold);
		i_row_02_BM++;
		row_header_02_BM = sh.createRow(i_row_02_BM);
		sh.addMergedRegion(CellRangeAddress.valueOf("B3:E3"));// merged columns
		String[] str_header2_02_BM = new String[] { "(ĐỀ TÀI NGHIÊN CỨU KHOA HỌC CÁC CẤP – BẰNG ĐỘC QUYỀN SÁNG CHẾ/GIẢI PHÁP HỮU ÍCH)" };
		createRowInExcel(1, 1, str_header2_02_BM, row_header_02_BM,
				cellStyleBold);
		// ----end header in excel
		// ----start title in excel
		i_row_02_BM = i_row_02_BM + 2;
		Row row_title_02_BM = sh.createRow(i_row_02_BM);
		String[] str_title1_02_BM = new String[] { "Bộ môn : " + departmentName };
		createRowInExcel(1, 1, str_title1_02_BM, row_title_02_BM, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("B5:C5"));// merged columns;
		String[] str_title2_02_BM = new String[] { "Khoa(Viện, Trung tâm) : "
				+ facultyName };
		createRowInExcel(5, 5, str_title2_02_BM, row_title_02_BM, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("F5:I5"));// merged columns
		// ----end title in excel
		// ----start header_table
		i_row_02_BM = i_row_02_BM + 4;
		int i_row_1_02_BM = i_row_02_BM + 1;
		int i_row_2_02_BM = i_row_02_BM + 2;
		Row row_header_table_02_BM = sh.createRow(i_row_02_BM);
		String[] str_header_table_02_BM = new String[] { "STT", "Họ và tên",
				"Tổng số đề tài, dự án NCKH", "", "", "", "", "",
				"Tổng số giờ quy đổi của người kê khai (I)" };
		createRowInExcel(1, 9, str_header_table_02_BM, row_header_table_02_BM,
				cellStyleCenterBoldFullBorder);
		sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_1_02_BM + ":B"
				+ i_row_2_02_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("C" + i_row_1_02_BM + ":C"
				+ i_row_2_02_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("J" + i_row_1_02_BM + ":J"
				+ i_row_2_02_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("D" + i_row_1_02_BM + ":I"
				+ i_row_1_02_BM));
		i_row_02_BM++;
		row_header_table_02_BM = sh.createRow(i_row_02_BM);
		String[] str2_02_BM = new String[] { "", "",
				"Đề tài KHCN, dự án cấp Nhà nước",
				"Đề tài, dự án  cấp Bộ, thành phố và tương đương",
				"Đề tài thuộc quỹ Nafosted", "ĐT, dự án hợp tác quốc tế",
				"TĐ cấp trường", "Bằng độc quyền Sáng chế/Giải pháp hữu ích",
				"" };
		createRowInExcel(1, 9, str2_02_BM, row_header_table_02_BM,
				cellStyleCenterBoldFullBorder);
		// ----end header_table
		// ----start index of table in excel
		int count_02_BM = 0;
		for (GenericValue st : staffs) {
			String name = (String) st.get("staffName");
			count_02_BM++;
			i_row_02_BM += 1;
			Row r = sh.createRow(i_row_02_BM);
			String[] str_stt = new String[] { "" + count_02_BM };
			createRowInExcel(1, 1, str_stt, r, cellStyleRight);
			String[] str_1 = new String[] { name, "", "", "", "", "", "", "",
					"" };
			createRowInExcel(2, 9, str_1, r, cellStyleLeft);
		}
		i_row_02_BM++;
		Row row_table_ = sh.createRow(i_row_02_BM);
		String[] str_total_ = new String[] { "", "Total", "", "", "", "", "",
				"", "0" };
		createRowInExcel(1, 9, str_total_, row_table_,
				cellStyleCenterBoldFullBorder);
		// ----end table in excel
		// ----start footer in excel
		i_row_02_BM = i_row_02_BM + 3;
		Row rowFooter_02_BM = sh.createRow(i_row_02_BM);
		String[] str_footer1_02_BM = new String[] { "Ghi chú:  Mục (I)  lấy từ bản kê khai của từng cá nhân" };
		createRowInExcel(1, 1, str_footer1_02_BM, rowFooter_02_BM,
				cellStyleBold);
		i_row_02_BM = i_row_02_BM + 4;
		rowFooter_02_BM = sh.createRow(i_row_02_BM);
		String[] str_footer2_02_BM = new String[] { "Hà Nội, Ngày " + day
				+ " tháng " + month + " năm " + year };
		createRowInExcel(4, 4, str_footer2_02_BM, rowFooter_02_BM,
				cellStyleBold);
		i_row_02_BM++;
		rowFooter_02_BM = sh.createRow(i_row_02_BM);
		String[] str_footer3_02_BM = new String[] { "TRƯỞNG BỘ MÔN", "", "",
				"TRƯỞNG KHOA/VIỆN/TT" };
		createRowInExcel(1, 4, str_footer3_02_BM, rowFooter_02_BM,
				cellStyleBold);
		// ----end footer in excel 02-BM

		// sheet 03-BM
		sh = wb.createSheet("03-BM");
		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 3000);
		sh.setColumnWidth(2, 6000);
		sh.setColumnWidth(3, 6000);
		sh.setColumnWidth(4, 6000);
		sh.setColumnWidth(5, 6000);
		sh.setColumnWidth(6, 6000);
		sh.setColumnWidth(7, 6000);
		sh.setColumnWidth(8, 6000);
		sh.setColumnWidth(9, 6000);
		int i_row_03_BM = 0;
		// ----start header in excel
		i_row_03_BM++;
		Row row_header_03_BM = sh.createRow(i_row_03_BM);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));// merged columns
		String[] str_header_03_BM = new String[] { "BẢNG TỔNG HỢP CHI TIẾT KHỐI LƯỢNG NCKH ĐƯỢC QUY ĐỔI TỪ CÁC BÀI BÁO KHOA HỌC " };
		createRowInExcel(1, 1, str_header_03_BM, row_header_03_BM,
				cellStyleBold);
		i_row_03_BM++;
		row_header_03_BM = sh.createRow(i_row_03_BM);
		sh.addMergedRegion(CellRangeAddress.valueOf("B3:I3"));// merged columns
		String[] str_header2_03_BM = new String[] { "ĐĂNG  TRONG TẠP CHÍ VÀ KỶ YẾU HỘI NGHỊ KHOA HỌC NĂM HỌC "
				+ academicYearId };
		createRowInExcel(1, 1, str_header2_03_BM, row_header_03_BM,
				cellStyleBold);
		// ----end header in excel
		// ----start title in excel
		i_row_03_BM = i_row_03_BM + 2;
		Row row_title_03_BM = sh.createRow(i_row_03_BM);
		String[] str_title1_03_BM = new String[] { "Bộ môn : " + departmentName };
		createRowInExcel(1, 1, str_title1_03_BM, row_title_03_BM, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("B5:C5"));// merged columns;
		String[] str_title2_03_BM = new String[] { "Khoa(Viện, Trung tâm) : "
				+ facultyName };
		createRowInExcel(5, 5, str_title2_03_BM, row_title_03_BM, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("F5:I5"));// merged columns
		// ----end title in excel
		// ----start header_table
		i_row_03_BM = i_row_03_BM + 4;
		int i_row_1_03_BM = i_row_03_BM + 1;
		int i_row_2_03_BM = i_row_03_BM + 2;
		Row row_header_table_03_BM = sh.createRow(i_row_03_BM);
		String[] str_header_table_03_BM = new String[] { "STT",
				"Họ và tên tác giả và các đồng tác giả", "Tên bài báo",
				"Tạp chí, Proceedings", "", "", "", "",
				"Số giờ quy đổi của người kê khai", "Ghi chú" };
		createRowInExcel(1, 10, str_header_table_03_BM, row_header_table_03_BM,
				cellStyleCenterBoldFullBorder);
		sh.addMergedRegion(CellRangeAddress.valueOf("B" + i_row_1_03_BM + ":B"
				+ i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("C" + i_row_1_03_BM + ":C"
				+ i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("D" + i_row_1_03_BM + ":D"
				+ i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("J" + i_row_1_03_BM + ":J"
				+ i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("K" + i_row_1_03_BM + ":K"
				+ i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("E" + i_row_1_03_BM + ":I"
				+ i_row_1_03_BM));
		i_row_03_BM++;
		row_header_table_03_BM = sh.createRow(i_row_03_BM);
		String[] str2_03_BM = new String[] { "", "", "",
				"Tên tạp chí, Proceedings", "Thời gian xuất bản",
				"Chỉ số ISSN", "Hệ số IF", "Số giờ quy đổi của bài báo", "", "" };
		createRowInExcel(1, 10, str2_03_BM, row_header_table_03_BM,
				cellStyleCenterBoldFullBorder);
		// ----end header_table
		// ----start index of table in excel
		// int count_03_BM=0;
		// for (GenericValue st : staffs) {
		// String name = (String) st.get("staffName");
		// count_03_BM++;
		// i_row_03_BM += 1;
		// Row r = sh.createRow(i_row_03_BM);
		// String[] str_stt = new String[]{""+count_03_BM};
		// createRowInExcel(1, 1, str_stt, r, cellStyleRight);
		// String[] str_1 = new String[]{name, "", "", "", "", ""
		// , "", "", ""};
		// createRowInExcel(2, 9, str_1, r, cellStyleLeft);
		// }
		for (GenericValue st : staffs) {
			String name = (String) st.get("staffName");
			List<GenericValue> papers = mStaff2Papers.get(st);
			// if (papers == null || papers.size() == 0)
			// continue;
			i_row_03_BM++;
			Row r = sh.createRow(i_row_03_BM);
			String[] str_1 = new String[] { "", name, "", "", "", "", "", "0",
					"0", "" };
			createRowInExcel(1, 10, str_1, r, cellStyleCenterBoldFullBorder);
			int count_03_BM = 0;
			for (GenericValue p : papers) {
				String authors = (String) p.get("authors");
				String paperName = (String) p.get("paperName");
				String journalConference = (String) p
						.get("journalConferenceName");
				String pub_year = (Long) p.get("year") + "";
				String issn = (String) p.get("ISSN");
				String paperCategoryId = (String) p.get("paperCategoryId");
				long hour = 0;
				if (mCategory2Hour.get(paperCategoryId) != null)
					hour = mCategory2Hour.get(paperCategoryId);
				i_row_03_BM++;
				count_03_BM++;
				r = sh.createRow(i_row_03_BM);
				String[] str_1ss = new String[] { "" + count_02_BM };
				createRowInExcel(1, 1, str_1ss, r, cellStyleRight);
				String[] str_2ss = new String[] { authors, paperName,
						journalConference };
				createRowInExcel(2, 4, str_2ss, r, cellStyleLeft);
				String[] str_3ss = new String[] { pub_year };
				createRowInExcel(5, 5, str_3ss, r, cellStyleRight);
				String[] str_4ss = new String[] { issn, "" };
				createRowInExcel(6, 7, str_4ss, r, cellStyleLeft);
				int sz = 1;
				if (authors != null) {
					String[] s = authors.split(",");
					sz = s.length;
				}
				String[] str_5ss = new String[] { "" + hour, "" + hour / sz };
				createRowInExcel(8, 9, str_5ss, r, cellStyleRight);
				String[] str_6ss = new String[] { "" };
				createRowInExcel(10, 10, str_6ss, r, cellStyleLeft);
			}
		}
		i_row_03_BM++;
		Row row_table_03_BM = sh.createRow(i_row_03_BM);
		String[] str_total_03_Bm = new String[] { "", "Total", "", "", "", "",
				"", "0", "0", "" };
		createRowInExcel(1, 10, str_total_03_Bm, row_table_03_BM,
				cellStyleCenterBoldFullBorder);
		// ----end table in excel
		// ----start footer in excel
		i_row_03_BM = i_row_03_BM + 3;
		Row rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer1_03_BM = new String[] { "Ghi chú:" };
		createRowInExcel(1, 1, str_footer1_03_BM, rowFooter_03_BM,
				cellStyleBold);
		i_row_03_BM++;
		rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer1_03_BM1 = new String[] { "(*): Cộng tổng giờ bài báo khoa học cho từng cá nhân" };
		createRowInExcel(1, 1, str_footer1_03_BM1, rowFooter_03_BM,
				cellStyleBold);
		i_row_03_BM++;
		rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer1_03_BM2 = new String[] { "(**): Cộng tổng giờ bài báo khoa học của Bộ môn" };
		createRowInExcel(1, 1, str_footer1_03_BM2, rowFooter_03_BM,
				cellStyleBold);
		i_row_03_BM = i_row_03_BM + 4;
		rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer2_03_BM = new String[] { "Hà Nội, Ngày " + day
				+ " tháng " + month + " năm " + year };
		createRowInExcel(4, 4, str_footer2_03_BM, rowFooter_03_BM,
				cellStyleBold);
		i_row_03_BM++;
		rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer3_03_BM = new String[] { "TRƯỞNG BỘ MÔN", "", "",
				"TRƯỞNG KHOA/VIỆN/TT" };
		createRowInExcel(1, 4, str_footer3_03_BM, rowFooter_03_BM,
				cellStyleBold);
		// ----end footer in excel 03-BM

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

	public static Map<String, Object> removeStaffPaperDeclarationc(
			String paperId, String staffId, Delegator delegator) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId", paperId));
			conds.add(EntityCondition.makeCondition("staffId", staffId));

			// GenericValue sp = delegator.findOne("StaffPaperDeclaration",
			// UtilMisc.toMap("paperId",paperId,"staffId",staffId), false);
			delegator.removeByCondition("StaffPaperDeclaration",
					EntityCondition.makeCondition(conds));

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> createStaffPaperDeclarationc(
			String paperId, String staffId, String roleId, Delegator delegator) {
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
			if (roleId != null)
				gv.put("roleId", roleId);
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

	public static void approveAPaperDeclaration(Delegator delegator,
			String paperId, String staffId) {
		try {
			GenericValue p = delegator.findOne("PaperDeclaration",
					UtilMisc.toMap("paperId", paperId), false);
			p.put("approveStatusId", "APPROVED");
			p.put("approverStaffId", staffId);
			delegator.store(p);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void rejectAPaperDeclaration(Delegator delegator,
			String paperId, String staffId) {
		try {
			GenericValue p = delegator.findOne("PaperDeclaration",
					UtilMisc.toMap("paperId", paperId), false);
			p.put("approveStatusId", "REJECTED");
			p.put("approverStaffId", staffId);
			delegator.store(p);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static List<GenericValue> getStaffsOfPaper(String paperId,
			Delegator delegator) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("paperId",
					EntityOperator.EQUALS, paperId));

			// conds.add(EntityCondition.makeCondition("statusId",
			// EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

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

			// conds.add(EntityCondition.makeCondition("statusId",
			// EntityOperator.EQUALS, STATUS_ENABLED));

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

	public static String getFacultyName(Delegator delegator, String facultyId) {
		try {
			GenericValue faculty = delegator.findByPrimaryKey("Faculty",
					UtilMisc.toMap("facultyId", facultyId));
			String facultyName = (String) faculty.get("facultyName");
			return facultyName;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String getDepartmentName(Delegator delegator,
			String departmentId) {
		try {
			GenericValue department = delegator.findByPrimaryKey("Department",
					UtilMisc.toMap("departmentId", departmentId));
			String departmentName = (String) department.get("departmentName");
			return departmentName;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void mergedRegion(HSSFWorkbook wb, Sheet sh, int fr, int lw,
			int fc, int lc, short border) {
		CellRangeAddress mergedCell = new CellRangeAddress(fr, // first row
																// (0-based)
				lw, // last row (0-based)
				fc, // first column (0-based)
				lc // last column (0-based)
		);
		sh.addMergedRegion(mergedCell);

		RegionUtil.setBorderTop(border, mergedCell, sh, wb);
		RegionUtil.setBorderBottom(border, mergedCell, sh, wb);
		RegionUtil.setBorderLeft(border, mergedCell, sh, wb);
		RegionUtil.setBorderRight(border, mergedCell, sh, wb);
	}

	public static void mergedRegion(Sheet sh, int fr, int lw, int fc, int lc) {
		CellRangeAddress mergedCell = new CellRangeAddress(fr, // first row
																// (0-based)
				lw, // last row (0-based)
				fc, // first column (0-based)
				lc // last column (0-based)
		);
		sh.addMergedRegion(mergedCell);
	}

	public static void createCell(Row rh, HSSFCellStyle style, String value,
			int row, int column) {
		Cell ch = rh.createCell(column);
		ch.setCellStyle(style);
		ch.setCellValue(value);
	}

	public static void createCell(Row rh, HSSFCellStyle style,
			HSSFRichTextString value, int row, int column) {
		Cell ch = rh.createCell(column);
		ch.setCellStyle(style);
		ch.setCellValue(value);
	}

	public static HSSFCellStyle createCellStyle(HSSFWorkbook wb,
			short fontSize, short boldWeight, short align, short border) {
		HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
		Font font = wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setFontName("Times New Roman");
		font.setBoldweight(boldWeight);
		font.setColor(HSSFColor.BLACK.index);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(align);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(border);
		cellStyle.setBorderTop(border);
		cellStyle.setBorderLeft(border);
		cellStyle.setBorderRight(border);

		return cellStyle;
	}

	public static HSSFCellStyle createCellStyle(HSSFWorkbook wb,
			short fontSize, short boldWeight, short align, short border,
			short color) {
		HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
		Font font = wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setFontName("Times New Roman");
		font.setBoldweight(boldWeight);
		font.setColor(HSSFColor.BLACK.index);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillBackgroundColor(color);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(align);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(border);
		cellStyle.setBorderTop(border);
		cellStyle.setBorderLeft(border);
		cellStyle.setBorderRight(border);

		return cellStyle;
	}

	public static HSSFCellStyle createCellStyle(HSSFWorkbook wb,
			short fontSize, short boldWeight, boolean italic, short align,
			short border) {
		HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
		Font font = wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setFontName("Times New Roman");
		font.setBoldweight(boldWeight);
		font.setItalic(italic);
		font.setColor(HSSFColor.BLACK.index);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(align);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(border);
		cellStyle.setBorderTop(border);
		cellStyle.setBorderLeft(border);
		cellStyle.setBorderRight(border);

		return cellStyle;
	}

	public static HSSFCellStyle createCellStyle(HSSFWorkbook wb,
			short fontSize, short boldWeight, boolean italic, byte underline,
			short align, short border) {
		HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
		Font font = wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setFontName("Times New Roman");
		font.setBoldweight(boldWeight);
		font.setItalic(italic);
		font.setUnderline(underline);
		;
		font.setColor(HSSFColor.BLACK.index);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(align);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(border);
		cellStyle.setBorderTop(border);
		cellStyle.setBorderLeft(border);
		cellStyle.setBorderRight(border);

		return cellStyle;
	}

	public static HSSFCellStyle createCellStyle(HSSFWorkbook wb,
			short fontSize, short boldWeight, short align) {
		HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
		Font font = wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setFontName("Times New Roman");
		font.setBoldweight(boldWeight);
		font.setColor(HSSFColor.BLACK.index);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(align);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);

		return cellStyle;
	}

	public static void createCellNull(Row rh, HSSFCellStyle styleCell,
			int firstColumn, int lastColumn) {
		for (int i = firstColumn; i <= lastColumn; ++i) {
			createCell(rh, styleCell, "", rh.getRowNum(), i);
		}
	}

	public static void createCellNull(HSSFSheet sh, HSSFCellStyle styleCell,
			int firstRow, int lastRow, int firstColumn, int lastColumn) {
		for (int i = firstRow; i <= lastRow; ++i) {
			Row rh = sh.createRow(i);
			for (int j = firstRow; j <= lastRow; ++j) {
				createCell(rh, styleCell, "", i, j);
			}
		}
	}

	public static HSSFRichTextString createRickText(HSSFWorkbook wb,
			HSSFCellStyle styleCell, String text, int[] position) {
		Font fontNoBold = wb.createFont();
		Font fontBold = wb.createFont();
		Font font = styleCell.getFont(wb);

		fontBold.setFontHeightInPoints(font.getFontHeightInPoints());
		fontBold.setFontName(font.getFontName());
		fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontBold.setColor(font.getColor());
		fontBold.setItalic(font.getItalic());

		fontNoBold.setFontHeightInPoints(font.getFontHeightInPoints());
		fontNoBold.setFontName(font.getFontName());
		fontNoBold.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		fontNoBold.setColor(font.getColor());
		fontNoBold.setItalic(font.getItalic());

		HSSFRichTextString value = new HSSFRichTextString(text);

		int k = position.length / 2;
		int currIndex = 0;
		int end = text.length();
		for (int i = 0; i < k; ++i) {
			int start = position[i * 2];
			if (i * 2 + 1 < position.length) {
				end = position[i * 2 + 1];
			}
			if (start < end) {
				if (start > currIndex) {
					value.applyFont(currIndex, start - 1, fontNoBold);
					value.applyFont(start, end, fontBold);
					currIndex = end + 1;
				} else {
					value.applyFont(start, end, fontBold);
					currIndex = end + 1;
				}
			}
		}

		if (currIndex < text.length()) {
			value.applyFont(currIndex, text.length(), fontNoBold);
		}

		return value;
	}

	public static HSSFCellStyle createCellStyle(HSSFWorkbook wb,
			short fontSize, short boldWeight, short align, boolean wrapText) {
		HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
		Font font = wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setFontName("Times New Roman");
		font.setBoldweight(boldWeight);
		font.setColor(HSSFColor.BLACK.index);
		cellStyle.setFont(font);
		cellStyle.setWrapText(wrapText);
		cellStyle.setAlignment(align);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);

		return cellStyle;
	}

	public static HSSFCellStyle getFontBold(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 13);
		font.setFontName("Times New Roman");
		cellStyle.setFont(font);
		return cellStyle;
	}

	public static HSSFCellStyle getAttributeLeftFullBoder(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		cellStyle.setFont(font);
		allBorderForCell(cellStyle);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		return cellStyle;
	}

	public static HSSFCellStyle getAttributeLeftBoldFullBoder(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		allBorderForCell(cellStyle);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		return cellStyle;
	}

	public static HSSFCellStyle getAttributeRightFullBoder(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		cellStyle.setFont(font);
		allBorderForCell(cellStyle);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		return cellStyle;
	}

	public static HSSFCellStyle getAttributeCenterBoldFullBorder(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Times New Roman");
		cellStyle.setFont(font);
		allBorderForCell(cellStyle);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		return cellStyle;
	}

	public static void allBorderForCell(HSSFCellStyle cellStyle) {
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	}

	public static void setHeaderColumnsOfRow(int indexStart, int numCols,
			String[] str, Row row, HSSFCellStyle cellStyle) {
		int m = 0;
		for (int i = indexStart; i <= numCols; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(str[m]);
			m++;
		}
	}

	public static void createRowInExcel(int indexStart, int numCols,
			String[] str, Row row, HSSFCellStyle cellStyle) {
		int m = 0;
		for (int i = indexStart; i <= numCols; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(str[m]);
			m++;
		}
	}

	public static List<GenericValue> getPaperOfFacultyAcademcYearId(
			Delegator delegator, String facultyId, String academicYearId) {
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("statusId",
					EntityOperator.EQUALS, PaperDeclarationUtil.STATUS_ENABLED));

			if (academicYearId != null && !academicYearId.equals("all"))
				conds.add(EntityCondition.makeCondition("academicYearId",
						EntityOperator.EQUALS, academicYearId));

			// List<GenericValue> papers = delegator.findList("PapersStaffView",
			List<GenericValue> papers = delegator.findList("PaperView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			HashSet<String> setStaffId = new HashSet<String>();
			if (facultyId != null && !facultyId.equals("all")) {
				List<GenericValue> staffsOfFaculty = PaperDeclarationUtil
						.getListStaffsOfFaculty(delegator, facultyId);
				for (GenericValue st : staffsOfFaculty)
					setStaffId.add((String) st.getString("staffId"));
			}
			Debug.log(module
					+ "::getPaperOfFacultyAcademcYearId, staff of selected faculty = "
					+ setStaffId.size());
			List<GenericValue> retList = FastList.newInstance();
			for (GenericValue gv : papers) {
				Debug.log(module + "::getPaperOfFacultyAcademcYearId, paper "
						+ gv.get("paperName"));

				boolean ok = true;
				if (facultyId != null && !facultyId.equals("all")) {
					String paperId = (String) gv.getString("paperId");
					List<GenericValue> ST = PaperDeclarationUtil
							.getStaffsOfPaper(paperId, delegator);
					ok = false;
					for (GenericValue st : ST) {
						String stId = (String) st.getString("staffId");
						if (setStaffId.contains(stId)) {
							ok = true;
							break;
						}
					}
				}
				if (ok)
					retList.add(gv);

			}
			return retList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return FastList.newInstance();
	}
	
	
}
