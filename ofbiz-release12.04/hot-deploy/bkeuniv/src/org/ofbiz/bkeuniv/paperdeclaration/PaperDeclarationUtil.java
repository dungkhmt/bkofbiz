package org.ofbiz.bkeuniv.paperdeclaration;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.department.DepartmentService;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.ArrayList;

public class PaperDeclarationUtil extends java.lang.Object {
	public static final String STATUS_ENABLED = "ENABLED";
	public static final String STATUS_DISABLED = "DISABLED";
	public static final String module = PaperDeclarationUtil.class.getName();
	public static final String[] sSTT = new String[] { "I", "II", "III", "IV",
			"V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV",
			"XV" };

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
				String paperCategoryId = (String) p.get("paperCategoryId");
				String paperId = (String) p.get("paperId");
				List<String> staffID = getStaffIDOfPaper(delegator, paperId);
				boolean ok = false;
				for (String stId : staffID) {
					if (setStaffID.contains(stId)) {
						ok = true;
					}
				}
				if (ok && (paperCategoryId.equals("JINT_SCOPUS")))
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

				long hours = g.getLong("workinghours");
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

	public static void createSheetPaper01CN02CN(HSSFWorkbook wb,
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
			HashMap<GenericValue, Integer> mPaper2NbIntAuthors) {

		int nbColumns = 14;

		Sheet sh = wb.createSheet("Bài báo 01-CN");
		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 3000);
		sh.setColumnWidth(2, 12000);
		sh.setColumnWidth(3, 6000);
		sh.setColumnWidth(4, 6000);
		sh.setColumnWidth(5, 6000);
		sh.setColumnWidth(6, 6000);
		sh.setColumnWidth(7, 6000);
		sh.setColumnWidth(8, 6000);
		sh.setColumnWidth(9, 6000);
		sh.setColumnWidth(10, 6000);
		sh.setColumnWidth(11, 6000);
		sh.setColumnWidth(12, 6000);
		sh.setColumnWidth(13, 6000);
		sh.setColumnWidth(14, 6000);

		HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
		HSSFCellStyle styleTitle = wb.createCellStyle();
		styleTitle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
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
		styleTitle.setWrapText(true);

		cellStyle.setWrapText(true);

		int i_row = 0;

		String[] y = academicYearId.split("-");

		// set header title
		i_row++;
		Row rh = sh.createRow(i_row);
		int i_col = 0;
		i_col++;
		Cell ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Họ tên các tác giả");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tên bài báo");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tên tạp chí, proceedings");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Số và thời gian xuất bản chính thức");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Chỉ số ISSN");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tổng số tác giả");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Số tác giả của trường");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tác giả đầu tiên là corresponding author");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tác giả đầu tiên không là corresponding author");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tác giả là corresponding author");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các tác giả còn lại");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Số giờ quy đổi của bài báo");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Số giờ quy đổi của người kê khai");

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/7/"
				+ y[0] + " - 31/12/" + y[0]);
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng trong tạp chí thuộc danh mục SCI và SCIE (ISI):");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_isi_papers1.size(); i++) {
			GenericValue p = lst_isi_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng trong tạp chí thuộc danh mục SCOPUS:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_scopus_papers1.size(); i++) {
			GenericValue p = lst_scopus_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng trong tạp chí trong và ngoài nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Ngoài nước (Không kê lại các bài ở mục I, II):");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_other_international_journal_papers1.size(); i++) {
			GenericValue p = lst_other_international_journal_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Trong nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_domestic_journal_papers1.size(); i++) {
			GenericValue p = lst_domestic_journal_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng trong kỷ yếu Hội nghị  khoa học trong và ngoài nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Ngoài nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_international_conference_papers1.size(); i++) {
			GenericValue p = lst_international_conference_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Trong nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));
		for (int i = 0; i < lst_domestic_conference_papers1.size(); i++) {
			GenericValue p = lst_domestic_conference_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		// second part
		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/1/"
				+ y[1] + " - 30/06/" + y[1]);
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng trong tạp chí thuộc danh mục SCI và SCIE (ISI):");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_isi_papers2.size(); i++) {
			GenericValue p = lst_isi_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng trong tạp chí thuộc danh mục SCOPUS:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_scopus_papers2.size(); i++) {
			GenericValue p = lst_scopus_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng trong tạp chí trong và ngoài nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Ngoài nước (Không kê lại các bài ở mục I, II):");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_other_international_journal_papers2.size(); i++) {
			GenericValue p = lst_other_international_journal_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Trong nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_domestic_journal_papers2.size(); i++) {
			GenericValue p = lst_domestic_journal_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng trong kỷ yếu Hội nghị  khoa học trong và ngoài nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Ngoài nước:");
		for (int i = 0; i < lst_international_conference_papers2.size(); i++) {
			GenericValue p = lst_international_conference_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Trong nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));
		for (int i = 0; i < lst_domestic_conference_papers2.size(); i++) {
			GenericValue p = lst_domestic_conference_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

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
			String academicYearId,
			HashMap<GenericValue, Integer> mPaper2NbIntAuthors) {

		int nbColumns = 14;

		Sheet sh = wb.createSheet("KV03");
		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 3000);
		sh.setColumnWidth(2, 12000);
		sh.setColumnWidth(3, 6000);
		sh.setColumnWidth(4, 6000);
		sh.setColumnWidth(5, 6000);
		sh.setColumnWidth(6, 6000);
		sh.setColumnWidth(7, 6000);
		sh.setColumnWidth(8, 6000);
		sh.setColumnWidth(9, 6000);
		sh.setColumnWidth(10, 6000);
		sh.setColumnWidth(11, 6000);
		sh.setColumnWidth(12, 6000);
		sh.setColumnWidth(13, 6000);
		sh.setColumnWidth(14, 6000);

		HSSFCellStyle cellStyle = getAttributeLeftFullBoder(wb);
		HSSFCellStyle styleTitle = wb.createCellStyle();
		styleTitle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
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
		styleTitle.setWrapText(true);

		cellStyle.setWrapText(true);

		int i_row = 0;

		String[] y = academicYearId.split("-");

		// set header title
		i_row++;
		Row rh = sh.createRow(i_row);
		int i_col = 0;
		i_col++;
		Cell ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Họ tên các tác giả");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tên bài báo");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tên tạp chí, proceedings");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Số và thời gian xuất bản chính thức");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Chỉ số ISSN");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tổng số tác giả");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Số tác giả của trường");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tác giả đầu tiên là corresponding author");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tác giả đầu tiên không là corresponding author");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Tác giả là corresponding author");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các tác giả còn lại");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Số giờ quy đổi của bài báo");

		i_col++;
		ch = rh.createCell(i_col);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Số giờ quy đổi của người kê khai");

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/7/"
				+ y[0] + " - 31/12/" + y[0]);
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));


		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Bài báo đăng tạp chí nước ngoài (không kê các bài báo trong danh mục SCI, SCIE, SCOPUS)");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_other_international_journal_papers1.size(); i++) {
			GenericValue p = lst_other_international_journal_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng tạp chí trong nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_domestic_journal_papers1.size(); i++) {
			GenericValue p = lst_domestic_journal_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Bài báo đăng kỷ yếu hội nghị quốc tế có phản biện:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_international_conference_papers1.size(); i++) {
			GenericValue p = lst_international_conference_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Bài báo đăng kỷ yếu hội nghị trong nước có phản biện:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));
		for (int i = 0; i < lst_domestic_conference_papers1.size(); i++) {
			GenericValue p = lst_domestic_conference_papers1.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		// second part
		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/1/"
				+ y[1] + " - 30/06/" + y[1]);
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));



		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Bài báo đăng tạp chí nước ngoài (không kê các bài báo trong danh mục SCI, SCIE, SCOPUS):");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_other_international_journal_papers2.size(); i++) {
			GenericValue p = lst_other_international_journal_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Các bài báo đăng tạp chí trong nước:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));

		for (int i = 0; i < lst_domestic_journal_papers2.size(); i++) {
			GenericValue p = lst_domestic_journal_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		
		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Bài báo đăng kỷ yếu hội nghị quốc tế có phản biện:");
		for (int i = 0; i < lst_international_conference_papers2.size(); i++) {
			GenericValue p = lst_international_conference_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

		i_row++;
		rh = sh.createRow(i_row);
		ch = rh.createCell(1);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Bài báo đăng kỷ yếu hội nghị trong nước có phản biện:");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 1, nbColumns));
		for (int i = 0; i < lst_domestic_conference_papers2.size(); i++) {
			GenericValue p = lst_domestic_conference_papers2.get(i);
			i_row++;
			createPaperRow(i_row, sh, p, cellStyle, mPaper2NbIntAuthors);
		}

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
			createSheetPaper01CN02CN(wb, lst_isi_papers1, lst_isi_papers2,
					lst_scopus_papers1, lst_scopus_papers2,
					lst_internaltional_journal_papers1,
					lst_internaltional_journal_papers2,
					lst_domestic_journal_papers1, lst_domestic_journal_papers2,
					lst_international_conference_papers1,
					lst_international_conference_papers2,
					lst_domestic_conference_papers1,
					lst_domestic_conference_papers2, academicYearId,
					mPaper2NbIntAuthors);

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

	public static void createExcelFormKV03(HSSFWorkbook wb, Delegator delegator, String academicYearId, String facultyId){
		try{
			List<GenericValue> all_papers = getPaperOfFacultyAcademcYearId(delegator, facultyId, academicYearId);
			//Sheet sh = wb.createSheet("KV03");
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
			createSheetPaperKV03(wb, 
					lst_internaltional_journal_papers1,
					lst_internaltional_journal_papers2,
					lst_domestic_journal_papers1, lst_domestic_journal_papers2,
					lst_international_conference_papers1,
					lst_international_conference_papers2,
					lst_domestic_conference_papers1,
					lst_domestic_conference_papers2, academicYearId,
					mPaper2NbIntAuthors);

		}catch(Exception ex){
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
		String[] FN = { facultyName, paper_hour1_faculty + "",
				paper_hour2_faculty + "", paper_hour_faculty + "",
				project_hour_faculty + "", total_hour_faculty + "" };
		row_header_table = sh.createRow(i_row);

		createRowInExcel(2, 7, FN, row_header_table, cellStyleLeftBold);

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
				String staffName = (String) (mId2Staff.get(st.get("staffId"))
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

	public static HSSFWorkbook createExcelFormKV04(Delegator delegator,
			String academicYearId, String facultyId) {

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
				"Số và thời gian xuất bản", "Chỉ số ISSN" };
		createRowInExcel(1, 6, str2, row_header_table,
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
			String[] str_papers1 = new String[] { authors, name, journal, vol,
					issn };
			m++;
			i_row++;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellStyle(cellStyleRight);
			c.setCellValue(m);

			createRowInExcel(2, 6, str_papers1, r, cellStyleLeft);
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
			String[] str_papers2 = new String[] { authors, name, journal, vol,
					issn };
			m++;
			i_row++;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellStyle(cellStyleRight);
			c.setCellValue(m);

			createRowInExcel(2, 6, str_papers2, r, cellStyleLeft);
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
		String[] str_header2 = new String[] { "ĐĂNG TRONG TẠP CHÍ QUỐC TẾ TRONG DANH MỤC SCOPUS NĂM HỌC "
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
				"Số và thời gian xuất bản", "Chỉ số ISSN" };
		createRowInExcel(1, 6, str2, row_header_table,
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
			String[] str_papers1 = new String[] { authors, name, journal, vol,
					issn };
			m++;
			i_row++;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellStyle(cellStyleRight);
			c.setCellValue(m);

			createRowInExcel(2, 6, str_papers1, r, cellStyleLeft);
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
			String[] str_papers2 = new String[] { authors, name, journal, vol,
					issn };
			m++;
			i_row++;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellStyle(cellStyleRight);
			c.setCellValue(m);

			createRowInExcel(2, 6, str_papers2, r, cellStyleLeft);
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
