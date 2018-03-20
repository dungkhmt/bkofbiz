package org.ofbiz.bkeuniv.paperdeclaration;

import java.awt.Color;
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

public class PaperDeclarationUtil extends java.lang.Object{
	public static final String STATUS_ENABLED = "ENABLED";
	public static final String STATUS_DISABLED = "DISABLED";
	public static final String module = PaperDeclarationUtil.class.getName();
	public static final String[] sSTT = new String[]{"I","II","III","IV","V","VI","VII","VIII"
		, "IX", "X", "XI", "XII", "XIII", "XIV", "XV"};

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
		Map<GenericValue, List<GenericValue>> mDepartment2Staffs = FastMap
				.newInstance();

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
					hour += h;
				}
				mStaff2Hour.put(st, hour);
			}
		}

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
		int i_row = 0;
		
		//----style font in excel
		HSSFCellStyle cellStyleBold = getFontBold(wb);
		HSSFCellStyle cellStyleLeft = getAttributeLeftFullBoder(wb);
		HSSFCellStyle cellStyleLeftBold = getAttributeLeftBoldFullBoder(wb);
		HSSFCellStyle cellStyleRight = getAttributeRightFullBoder(wb);
		HSSFCellStyle cellStyleCenterBoldFullBorder = getAttributeCenterBoldFullBorder(wb);
		//----style font in excel
		//----start header in excel 
		i_row++; 
		Row row_header = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));//merged columns
		String[] str_header = new String[]{"Bảng tổng hợp tính giờ chuẩn NCKH năm học "+academicYearId+" của cán bộ trường ĐH Bách Khoa Hà Nội"};
		createRowInExcel(1, 1, str_header, row_header, cellStyleBold);
		//----end header in excel
		
		//----start title in excel
		i_row = i_row + 3;
		Row row_title = sh.createRow(i_row);
		String facultyName = getFacultyName(delegator, facultyId);
		String[] str_title = new String[]{"Khoa/Viện : " + facultyName};
		createRowInExcel(1, 1, str_title, row_title, cellStyleBold);
		//----end title in excel
		
		//----start header_table 
		i_row = i_row + 4;
		System.out.println(i_row);
		Row row_header_table = sh.createRow(i_row);
		String[] str_header_table = new String[]{"STT", "Họ và tên", "Tổng số giờ quy đổi từ bài báo", 
				"Tổng số giờ quy đổi từ đề tài NCKH", "Tổng cộng giờ quy đổi"};
		createRowInExcel(1, 5, str_header_table, row_header_table, cellStyleCenterBoldFullBorder);
		//----end header_table
		
		//----start index of table in excel 
		int s=0;
		for (GenericValue d : departments) {
			i_row += 1;
			Row r = sh.createRow(i_row);
			String[] str_stt = new String[]{sSTT[s]};
			createRowInExcel(1, 1, str_stt, r, cellStyleCenterBoldFullBorder);
			s++;
			String deptName = (String) d.get("departmentName");
			String[] str_departmentName = new String[]{deptName};
			createRowInExcel(2, 2, str_departmentName, r, cellStyleLeftBold);
			String[] str_1 = new String[]{"0", "0", "0"};
			createRowInExcel(3, 5, str_1, r, cellStyleCenterBoldFullBorder);

			List<GenericValue> staffs = mDepartment2Staffs.get(d);
			int count = 0;
			for (GenericValue st : staffs) {
				String staffName = (String) st.get("staffName");
				i_row += 1;
				count++;
				r = sh.createRow(i_row);
				String[] str_sttt = new String[]{""+count};
				createRowInExcel(1, 1, str_sttt, r, cellStyleRight);
				String[] str_staffName = new String[]{staffName};
				createRowInExcel(2, 2, str_staffName, r, cellStyleLeft);
				String[] str_2 = new String[]{mStaff2Hour.get(st) + "", "0", "0"};
				createRowInExcel(3, 5, str_2, r, cellStyleRight);
			}
		}
		//----end table in excel
		
		//----start footer in excel
		i_row = i_row + 3;
		Row rowFooter = sh.createRow(i_row);
		Date timeCurrent = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeCurrent);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String[] str_footer1 = new String[]{"Ngày "+day+" tháng "+month+" năm "+ year};
		createRowInExcel(5, 5, str_footer1, rowFooter, cellStyleBold);
		i_row = i_row + 2;//----
		rowFooter = sh.createRow(i_row);
		String[] str_footer2 = new String[]{"LÃNH ĐẠO KHOA/VIỆN"};
		createRowInExcel(5, 5, str_footer2, rowFooter, cellStyleBold);
		//----end footer in excel
		
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
			if(paperCategoryId != null && mPaperCategory2Money.get(paperCategoryId) != null)
				money = (long)mPaperCategory2Money.get(paperCategoryId);
			
			double moneyPerAuthor = money * 1.0 / nbAuthors;

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

			double mp = moneyPerAuthor * nbAuthors;
			c = r.createCell(10);
			c.setCellValue(mp + "");
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
			c.setCellValue(moneyPerAuthor + "");
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
				c.setCellValue(moneyPerAuthor + "");
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
			papers = delegator.findList("PaperDeclaration",
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
		ch.setCellValue("Há»� vÃ  tÃªn cÃ¡c tÃ¡c giáº£");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(3);
		ch.setCellValue("TÃªn bÃ i bÃ¡o");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(4);
		ch.setCellValue("Táº¡p chÃ­, Proceedings");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(5);
		ch.setCellValue("");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(6);
		ch.setCellValue("");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(7);
		ch.setCellValue("Má»©c há»— trá»£ bÃ i bÃ¡o");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(8);
		ch.setCellValue("Sá»‘ Ä‘á»“ng tÃ¡c giÃ¡/bÃ i bÃ¡o");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(9);
		ch.setCellValue("Sá»‘ ngÆ°á»�i thuá»™c Ä‘Æ¡n vá»‹/bÃ i bÃ¡o");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(10);
		ch.setCellValue("Kinh phÃ­ há»— trá»£ cho cÃ¡n bá»™ cá»§a Ä‘Æ¡n vá»‹/bÃ i bÃ¡o");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(11);
		ch.setCellValue("TÃªn ngÆ°á»�i Ä‘Æ°á»£c nháº­n");
		ch.setCellStyle(styleTitle);

		ch = rh.createCell(12);
		ch.setCellValue("Sá»‘ tiá»�n/ngÆ°á»�i/bÃ i bÃ¡o");
		ch.setCellStyle(styleTitle);

		i_row += 1;
		rh = sh.createRow(i_row);
		ch = rh.createCell(4);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Táº¡p chÃ­, proceedings");

		ch = rh.createCell(5);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Sá»‘ táº¡p chÃ­, thá»�i gian xuáº¥t báº£n");

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
		ch.setCellValue("Danh sÃ¡ch bÃ i bÃ¡o táº¡p chÃ­ quá»‘c táº¿ khÃ´ng trong danh má»¥c ISI, Scopus");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 12));
		i_row = createSegmentKV04(list_paper_international_journals,
				styleNormal, sh, i_row, delegator, mPaperCategory2Money,
				mId2Staff);

		i_row += 1;
		rh = sh.createRow(i_row);
		ch = rh.createCell(2);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Danh sÃ¡ch bÃ i bÃ¡o táº¡p chÃ­ trong nÆ°á»›c");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 12));
		i_row = createSegmentKV04(list_paper_national_journals, styleNormal,
				sh, i_row, delegator, mPaperCategory2Money, mId2Staff);

		i_row += 1;
		rh = sh.createRow(i_row);
		ch = rh.createCell(2);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Danh sÃ¡ch bÃ i bÃ¡o há»™i nghá»‹ quá»‘c táº¿");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 12));
		i_row = createSegmentKV04(list_paper_international_conferences,
				styleNormal, sh, i_row, delegator, mPaperCategory2Money,
				mId2Staff);

		i_row += 1;
		rh = sh.createRow(i_row);
		ch = rh.createCell(2);
		ch.setCellStyle(styleTitle);
		ch.setCellValue("Danh sÃ¡ch bÃ i bÃ¡o há»™i nghá»‹ trong nÆ°á»›c");
		sh.addMergedRegion(new CellRangeAddress(i_row, i_row, 2, 12));
		i_row = createSegmentKV04(list_paper_national_conferences, styleNormal,
				sh, i_row, delegator, mPaperCategory2Money, mId2Staff);

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

	public static HSSFWorkbook createExcelFormISI(Delegator delegator,
			String academicYearId, String facultyId) {

		List<GenericValue> papers = getListPaperISI(delegator, academicYearId,
				facultyId);

		List<GenericValue> papers1 = new ArrayList<GenericValue>();
		List<GenericValue> papers2 = new ArrayList<GenericValue>();

		for (GenericValue p : papers) {
			long month = (long) p.get("month");
			if (month >= 7)
				papers1.add(p);
			else
				papers2.add(p);
		}
		System.out.println("aaaaaa"+papers1.size());
		System.out.println("adadadada"+papers2.size());
		// start renderExcel

		HSSFWorkbook wb = new HSSFWorkbook();

		// sheet ISI
		Sheet sh = wb.createSheet("ISI");
		sh.setColumnWidth(0, 500);
		sh.setColumnWidth(1, 3000);
		sh.setColumnWidth(2, 12000);
		sh.setColumnWidth(3, 12000);
		sh.setColumnWidth(4, 6000);
		sh.setColumnWidth(5, 6000);
		sh.setColumnWidth(6, 6000);

		//----create style font bold
		HSSFCellStyle cellStyleBold = getFontBold(wb);
		HSSFCellStyle cellStyleLeft = getAttributeLeftFullBoder(wb);
		HSSFCellStyle cellStyleRight = getAttributeRightFullBoder(wb);
		HSSFCellStyle cellStyleCenterBoldFullBorder = getAttributeCenterBoldFullBorder(wb);
		int i_row = 0;
		//----start header in excel
		i_row++;
		Row row_header = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));
		String[] str_header1 = new String[]{"DANH MỤC BÀI BÁO CỦA CÁN BỘ TRƯỜNG ĐHBKHN"};
		createRowInExcel(1, 1, str_header1, row_header, cellStyleBold);
		i_row++;
		row_header = sh.createRow(i_row);
		String[] str_header2 = new String[]{"ĐĂNG TRONG TẠP CHÍ QUỐC TẾ TRONG DANH MỤC SCI VÀ SCIE NĂM HỌC "+ academicYearId};
		createRowInExcel(1, 1, str_header2, row_header, cellStyleBold);
		//----end header in excel
		//----start title in excel
		i_row = i_row + 4;
		Row row_title = sh.createRow(i_row);
		String text_title = getFacultyName(delegator, facultyId);
		String[] str_title = new String[]{"Khoa/viện: "+ text_title};
		createRowInExcel(1, 1, str_title, row_title, cellStyleBold);
		//----end title in excel
		//----start header table in excel
		i_row = i_row + 4;
		int i_row_1 = i_row + 1;
		int i_row_2 = i_row +2;
		Row row_header_table= sh.createRow(i_row);
		String[] str1 = new String[]{"STT","Họ và Tên tác giả","Tên bài báo","Tạp chí/Proceedings","",""};
		createRowInExcel(1, 6, str1, row_header_table, cellStyleCenterBoldFullBorder);
		sh.addMergedRegion(CellRangeAddress.valueOf("B"+i_row_1+":B"+i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("C"+i_row_1+":C"+i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("D"+i_row_1+":D"+i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("E"+i_row_1+":G"+i_row_1));
		i_row++;
		row_header_table= sh.createRow(i_row);
		String[] str2= new String[]{"","","","Tên tạp chí","Số và thời gian xuất bản","Chỉ số ISSN"};
		createRowInExcel(1, 6, str2, row_header_table, cellStyleCenterBoldFullBorder);
		//----end header table in excel		
		//----start table
		String[] arrYear = academicYearId.split("-");
		String year_start = arrYear[0]; 
		String year_end = arrYear[1];
		i_row++;
		int i_row_t = i_row + 1; 
		Row row_table = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B"+i_row_t+":G"+i_row_t));
		String[] str_1 = new String[]{"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/7/"+year_start+" - 31/12/"+year_start,
				"","","","",""};
		createRowInExcel(1, 6, str_1, row_table, cellStyleLeft);
		int m = 0;
		for (GenericValue p : papers1) {
			String authors = (String) p.get("authors");
			String name = (String) p.get("paperName");
			String journal = (String) p.get("journalConferenceName");
			String vol = (String) p.get("volumn");
			String issn = (String) p.get("ISSN");
			String[] str_papers1 = new String[]{authors, name, journal, vol, issn};
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
		sh.addMergedRegion(CellRangeAddress.valueOf("B"+i_row_t+":G"+i_row_t));
		String[] str_2 = new String[]{"CÁC BÀI BÁO KHOA HỌC ĐƯỢC ĐĂNG CHÍNH THỨC TRONG KHOẢNG THỜI GIAN TỪ 1/1/"+year_end+" - 30/6/2017"+year_end,
				"","","","",""};
		createRowInExcel(1, 6, str_2, row_table, cellStyleLeft);
		for (GenericValue p : papers2) {
			String authors = (String) p.get("authors");
			String name = (String) p.get("paperName");
			String journal = (String) p.get("journalConferenceName");
			String vol = (String) p.get("volumn");
			String issn = (String) p.get("ISSN");
			String[] str_papers2 = new String[]{authors, name, journal, vol, issn};
			m++;
			i_row++;
			Row r = sh.createRow(i_row);
			Cell c = r.createCell(1);
			c.setCellStyle(cellStyleRight);
			c.setCellValue(m);
			
			createRowInExcel(2, 6, str_papers2, r, cellStyleLeft);
		}
		//----end table
		
		//----start footer in excel
		i_row = i_row + 3;//----
		Row rowFooter = sh.createRow(i_row);
		Date timeCurrent = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeCurrent);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String[] str_footer1 = new String[]{"Ngày "+day+" tháng "+month+" năm "+ year};
		createRowInExcel(5, 5, str_footer1, rowFooter, cellStyleBold);
		i_row = i_row + 2;//----
		rowFooter = sh.createRow(i_row);
		String[] str_footer2 = new String[]{"LÃNH ĐẠO KHOA/VIỆN"};
		createRowInExcel(5, 5, str_footer2, rowFooter, cellStyleBold);
		//----end footer in excel

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
		
		//----style font in excel
		HSSFCellStyle cellStyleBold = getFontBold(wb);
		HSSFCellStyle cellStyleLeft = getAttributeLeftFullBoder(wb);
		HSSFCellStyle cellStyleLeftBold = getAttributeLeftBoldFullBoder(wb);
		HSSFCellStyle cellStyleRight = getAttributeRightFullBoder(wb);
		HSSFCellStyle cellStyleCenterBoldFullBorder = getAttributeCenterBoldFullBorder(wb);
		//----style font in excel
		//----start header in excel 
		i_row++; 
		Row row_header = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));//merged columns
		String[] str_header1 = new String[]{"BẢNG TỔNG HỢP KÊ KHAI KHỐI LƯỢNG NGHIÊN CỨU KHOA HỌC  NĂM HỌC "+academicYearId};
		createRowInExcel(1, 1, str_header1, row_header, cellStyleBold);
		i_row++;
		row_header = sh.createRow(i_row);
		sh.addMergedRegion(CellRangeAddress.valueOf("B3:E3"));//merged columns
		String[] str_header2 = new String[]{"(BÀI BÁO KHOA HỌC)"};
		createRowInExcel(1, 1, str_header2, row_header, cellStyleBold);
		//----end header in excel
		//----start title in excel
		i_row = i_row + 2;
		Row row_title = sh.createRow(i_row);
		String departmentName = getDepartmentName(delegator, departmentId); 
		String[] str_title1 = new String[]{"Bộ môn : " + departmentName};
		createRowInExcel(1, 1, str_title1, row_title, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("B5:C5"));//merged columns
		String facultyName = getFacultyName(delegator, facultyId);
		String[] str_title2 = new String[]{"Khoa(Viện, Trung tâm) : " + facultyName};
		createRowInExcel(5, 5, str_title2, row_title, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("F5:I5"));//merged columns
		//----end title in excel
		//----start header_table 
		i_row = i_row + 4;
		int i_row_1 = i_row + 1;
		int i_row_2 = i_row +2;
		Row row_header_table = sh.createRow(i_row);
		String[] str_header_table = new String[]{"STT", "Họ và tên", "Tổng số bài báo đăng tạp chí, Proceeding","", 
				"Tổng số giờ quy đổi của người kê khai (I)"};
		createRowInExcel(1, 5, str_header_table, row_header_table, cellStyleCenterBoldFullBorder);
		sh.addMergedRegion(CellRangeAddress.valueOf("B"+i_row_1+":B"+i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("C"+i_row_1+":C"+i_row_2));
		sh.addMergedRegion(CellRangeAddress.valueOf("D"+i_row_1+":E"+i_row_1));
		sh.addMergedRegion(CellRangeAddress.valueOf("F"+i_row_1+":F"+i_row_2));
		i_row++;
		row_header_table= sh.createRow(i_row);
		String[] str2= new String[]{"","","Tạp chí","Proceedings",""};
		createRowInExcel(1, 5, str2, row_header_table, cellStyleCenterBoldFullBorder);
		//----end header_table
		//----start index of table in excel 
		int count=0;
		for (GenericValue st : staffs) {
			String name = (String) st.get("staffName");
			count++;
			i_row += 1;
			Row r = sh.createRow(i_row);
			String[] str_stt = new String[]{""+count};
			createRowInExcel(1, 1, str_stt, r, cellStyleRight);
			String[] str_1 = new String[]{name, ""+mStaff2NbrPaperJournal.get(st), 
					""+mStaff2NbrPaperConference.get(st), ""+mStaff2NbrHours.get(st)};
			createRowInExcel(2, 5, str_1, r, cellStyleLeft);
		}
		i_row++;
		Row row_table= sh.createRow(i_row);
		String[] str_total = new String[]{"", "Total", "", "", "0"};
		createRowInExcel(1, 5, str_total, row_table, cellStyleCenterBoldFullBorder);
		//----end table in excel
		//----start footer in excel
		i_row = i_row + 3;
		Row rowFooter = sh.createRow(i_row);
		String[] str_footer1 = new String[]{"Ghi chú:  Mục (I)  lấy từ bản kê khai của từng cá nhân"};
		createRowInExcel(1, 1, str_footer1, rowFooter, cellStyleBold);
		i_row = i_row + 4;
		rowFooter = sh.createRow(i_row);
		Date timeCurrent = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeCurrent);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String[] str_footer2 = new String[]{"Hà Nội, Ngày "+day+" tháng "+month+" năm "+ year};
		createRowInExcel(4, 4, str_footer2, rowFooter, cellStyleBold);
		i_row++;
		rowFooter = sh.createRow(i_row);
		String[] str_footer3 = new String[]{"TRƯỞNG BỘ MÔN", "", "", "TRƯỞNG KHOA/VIỆN/TT"};
		createRowInExcel(1, 4, str_footer3, rowFooter, cellStyleBold);
		//----end footer in excel 01-BM
		
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
		//----start header in excel 
		i_row_02_BM++; 
		Row row_header_02_BM = sh.createRow(i_row_02_BM);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));//merged columns
		String[] str_header_02_BM = new String[]{"BẢNG TỔNG HỢP KÊ KHAI KHỐI LƯỢNG NGHIÊN CỨU KHOA HỌC  NĂM HỌC "+academicYearId};
		createRowInExcel(1, 1, str_header_02_BM, row_header_02_BM, cellStyleBold);
		i_row_02_BM++;
		row_header_02_BM = sh.createRow(i_row_02_BM);
		sh.addMergedRegion(CellRangeAddress.valueOf("B3:E3"));//merged columns
		String[] str_header2_02_BM = new String[]{"(ĐỀ TÀI NGHIÊN CỨU KHOA HỌC CÁC CẤP – BẰNG ĐỘC QUYỀN SÁNG CHẾ/GIẢI PHÁP HỮU ÍCH)"};
		createRowInExcel(1, 1, str_header2_02_BM, row_header_02_BM, cellStyleBold);
		//----end header in excel
		//----start title in excel
		i_row_02_BM = i_row_02_BM + 2;
		Row row_title_02_BM = sh.createRow(i_row_02_BM);
		String[] str_title1_02_BM = new String[]{"Bộ môn : " + departmentName};
		createRowInExcel(1, 1, str_title1_02_BM, row_title_02_BM, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("B5:C5"));//merged columns;
		String[] str_title2_02_BM = new String[]{"Khoa(Viện, Trung tâm) : " + facultyName};
		createRowInExcel(5, 5, str_title2_02_BM, row_title_02_BM, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("F5:I5"));//merged columns
		//----end title in excel
		//----start header_table 
		i_row_02_BM = i_row_02_BM + 4;
		int i_row_1_02_BM = i_row_02_BM + 1;
		int i_row_2_02_BM = i_row_02_BM +2;
		Row row_header_table_02_BM = sh.createRow(i_row_02_BM);
		String[] str_header_table_02_BM = new String[]{"STT", "Họ và tên", "Tổng số đề tài, dự án NCKH","", 
				"", "", "", "", "Tổng số giờ quy đổi của người kê khai (I)"};
		createRowInExcel(1, 9, str_header_table_02_BM, row_header_table_02_BM, cellStyleCenterBoldFullBorder);
		sh.addMergedRegion(CellRangeAddress.valueOf("B"+i_row_1_02_BM+":B"+i_row_2_02_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("C"+i_row_1_02_BM+":C"+i_row_2_02_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("J"+i_row_1_02_BM+":J"+i_row_2_02_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("D"+i_row_1_02_BM+":I"+i_row_1_02_BM));
		i_row_02_BM++;
		row_header_table_02_BM= sh.createRow(i_row_02_BM);
		String[] str2_02_BM= new String[]{"","","Đề tài KHCN, dự án cấp Nhà nước","Đề tài, dự án  cấp Bộ, thành phố và tương đương",
				"Đề tài thuộc quỹ Nafosted", "ĐT, dự án hợp tác quốc tế","TĐ cấp trường", "Bằng độc quyền Sáng chế/Giải pháp hữu ích", ""};
		createRowInExcel(1, 9, str2_02_BM, row_header_table_02_BM, cellStyleCenterBoldFullBorder);
		//----end header_table
		//----start index of table in excel 
		int count_02_BM=0;
		for (GenericValue st : staffs) {
			String name = (String) st.get("staffName");
			count_02_BM++;
			i_row_02_BM += 1;
			Row r = sh.createRow(i_row_02_BM);
			String[] str_stt = new String[]{""+count_02_BM};
			createRowInExcel(1, 1, str_stt, r, cellStyleRight);
			String[] str_1 = new String[]{name, "", "", "", "", ""
					, "", "", ""};
			createRowInExcel(2, 9, str_1, r, cellStyleLeft);
		}
		i_row_02_BM++;
		Row row_table_= sh.createRow(i_row_02_BM);
		String[] str_total_ = new String[]{"", "Total", "", "", "",
				 "", "", "", "0"};
		createRowInExcel(1, 9, str_total_, row_table_, cellStyleCenterBoldFullBorder);
		//----end table in excel
		//----start footer in excel
		i_row_02_BM = i_row_02_BM + 3;
		Row rowFooter_02_BM = sh.createRow(i_row_02_BM);
		String[] str_footer1_02_BM = new String[]{"Ghi chú:  Mục (I)  lấy từ bản kê khai của từng cá nhân"};
		createRowInExcel(1, 1, str_footer1_02_BM, rowFooter_02_BM, cellStyleBold);
		i_row_02_BM = i_row_02_BM + 4;
		rowFooter_02_BM = sh.createRow(i_row_02_BM);
		String[] str_footer2_02_BM = new String[]{"Hà Nội, Ngày "+day+" tháng "+month+" năm "+ year};
		createRowInExcel(4, 4, str_footer2_02_BM, rowFooter_02_BM, cellStyleBold);
		i_row_02_BM++;
		rowFooter_02_BM = sh.createRow(i_row_02_BM);
		String[] str_footer3_02_BM = new String[]{"TRƯỞNG BỘ MÔN", "", "", "TRƯỞNG KHOA/VIỆN/TT"};
		createRowInExcel(1, 4, str_footer3_02_BM, rowFooter_02_BM, cellStyleBold);
		//----end footer in excel 02-BM

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
		//----start header in excel 
		i_row_03_BM++; 
		Row row_header_03_BM = sh.createRow(i_row_03_BM);
		sh.addMergedRegion(CellRangeAddress.valueOf("B2:I2"));//merged columns
		String[] str_header_03_BM = new String[]{"BẢNG TỔNG HỢP CHI TIẾT KHỐI LƯỢNG NCKH ĐƯỢC QUY ĐỔI TỪ CÁC BÀI BÁO KHOA HỌC "};
		createRowInExcel(1, 1, str_header_03_BM, row_header_03_BM, cellStyleBold);
		i_row_03_BM++;
		row_header_03_BM = sh.createRow(i_row_03_BM);
		sh.addMergedRegion(CellRangeAddress.valueOf("B3:I3"));//merged columns
		String[] str_header2_03_BM = new String[]{"ĐĂNG  TRONG TẠP CHÍ VÀ KỶ YẾU HỘI NGHỊ KHOA HỌC NĂM HỌC "+academicYearId};
		createRowInExcel(1, 1, str_header2_03_BM, row_header_03_BM, cellStyleBold);
		//----end header in excel
		//----start title in excel
		i_row_03_BM = i_row_03_BM + 2;
		Row row_title_03_BM = sh.createRow(i_row_03_BM);
		String[] str_title1_03_BM = new String[]{"Bộ môn : " + departmentName};
		createRowInExcel(1, 1, str_title1_03_BM, row_title_03_BM, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("B5:C5"));//merged columns;
		String[] str_title2_03_BM = new String[]{"Khoa(Viện, Trung tâm) : " + facultyName};
		createRowInExcel(5, 5, str_title2_03_BM, row_title_03_BM, cellStyleBold);
		sh.addMergedRegion(CellRangeAddress.valueOf("F5:I5"));//merged columns
		//----end title in excel
		//----start header_table 
		i_row_03_BM = i_row_03_BM + 4;
		int i_row_1_03_BM = i_row_03_BM + 1;
		int i_row_2_03_BM = i_row_03_BM +2;
		Row row_header_table_03_BM = sh.createRow(i_row_03_BM);
		String[] str_header_table_03_BM = new String[]{"STT", "Họ và tên tác giả và các đồng tác giả", "Tên bài báo","Tạp chí, Proceedings", 
				"", "", "", "", "Số giờ quy đổi của người kê khai", "Ghi chú"};
		createRowInExcel(1, 10, str_header_table_03_BM, row_header_table_03_BM, cellStyleCenterBoldFullBorder);
		sh.addMergedRegion(CellRangeAddress.valueOf("B"+i_row_1_03_BM+":B"+i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("C"+i_row_1_03_BM+":C"+i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("D"+i_row_1_03_BM+":D"+i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("J"+i_row_1_03_BM+":J"+i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("K"+i_row_1_03_BM+":K"+i_row_2_03_BM));
		sh.addMergedRegion(CellRangeAddress.valueOf("E"+i_row_1_03_BM+":I"+i_row_1_03_BM));
		i_row_03_BM++;
		row_header_table_03_BM= sh.createRow(i_row_03_BM);
		String[] str2_03_BM= new String[]{"","","","Tên tạp chí, Proceedings", "Thời gian xuất bản",
				"Chỉ số ISSN", "Hệ số IF","Số giờ quy đổi của bài báo", "", ""};
		createRowInExcel(1, 10, str2_03_BM, row_header_table_03_BM, cellStyleCenterBoldFullBorder);
		//----end header_table
		//----start index of table in excel 
//		int count_03_BM=0;
//		for (GenericValue st : staffs) {
//			String name = (String) st.get("staffName");
//			count_03_BM++;
//			i_row_03_BM += 1;
//			Row r = sh.createRow(i_row_03_BM);
//			String[] str_stt = new String[]{""+count_03_BM};
//			createRowInExcel(1, 1, str_stt, r, cellStyleRight);
//			String[] str_1 = new String[]{name, "", "", "", "", ""
//					, "", "", ""};
//			createRowInExcel(2, 9, str_1, r, cellStyleLeft);
//		}
		for (GenericValue st : staffs) {
			String name = (String) st.get("staffName");
			List<GenericValue> papers = mStaff2Papers.get(st);
//			if (papers == null || papers.size() == 0)
//				continue;
			i_row_03_BM++;
			Row r = sh.createRow(i_row_03_BM);
			String[] str_1 = new String[]{"", name, "", "", "", ""
					, "", "0", "0",""};
			createRowInExcel(1, 10, str_1, r, cellStyleCenterBoldFullBorder);
			int count_03_BM = 0;
			for(GenericValue p : papers){
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
				String[] str_1ss = new String[]{""+count_02_BM};
				createRowInExcel(1, 1, str_1ss, r, cellStyleRight);
				String[] str_2ss = new String[]{authors, paperName, journalConference};
				createRowInExcel(2, 4, str_2ss, r, cellStyleLeft);
				String[] str_3ss = new String[]{pub_year};
				createRowInExcel(5, 5, str_3ss, r, cellStyleRight);
				String[] str_4ss = new String[]{issn, ""};
				createRowInExcel(6, 7, str_4ss, r, cellStyleLeft);
				int sz = 1;
				if (authors != null) {
					String[] s = authors.split(",");
					sz = s.length;
				}
				String[] str_5ss = new String[]{""+hour, ""+hour / sz };
				createRowInExcel(8, 9, str_5ss, r, cellStyleRight);
				String[] str_6ss = new String[]{""};
				createRowInExcel(10, 10, str_6ss, r, cellStyleLeft);
			}
		}
		i_row_03_BM++;
		Row row_table_03_BM= sh.createRow(i_row_03_BM);
		String[] str_total_03_Bm = new String[]{"", "Total", "", "", "",
				 "", "", "0", "0",""};
		createRowInExcel(1, 10, str_total_03_Bm, row_table_03_BM, cellStyleCenterBoldFullBorder);
		//----end table in excel
		//----start footer in excel
		i_row_03_BM = i_row_03_BM + 3;
		Row rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer1_03_BM = new String[]{"Ghi chú:"};
		createRowInExcel(1, 1, str_footer1_03_BM, rowFooter_03_BM, cellStyleBold);
		i_row_03_BM++;
		rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer1_03_BM1 = new String[]{"(*): Cộng tổng giờ bài báo khoa học cho từng cá nhân"};
		createRowInExcel(1, 1, str_footer1_03_BM1, rowFooter_03_BM, cellStyleBold);
		i_row_03_BM++;
		rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer1_03_BM2 = new String[]{"(**): Cộng tổng giờ bài báo khoa học của Bộ môn"};
		createRowInExcel(1, 1, str_footer1_03_BM2, rowFooter_03_BM, cellStyleBold);
		i_row_03_BM = i_row_03_BM + 4;
		rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer2_03_BM = new String[]{"Hà Nội, Ngày "+day+" tháng "+month+" năm "+ year};
		createRowInExcel(4, 4, str_footer2_03_BM, rowFooter_03_BM, cellStyleBold);
		i_row_03_BM++;
		rowFooter_03_BM = sh.createRow(i_row_03_BM);
		String[] str_footer3_03_BM = new String[]{"TRƯỞNG BỘ MÔN", "", "", "TRƯỞNG KHOA/VIỆN/TT"};
		createRowInExcel(1, 4, str_footer3_03_BM, rowFooter_03_BM, cellStyleBold);
		//----end footer in excel 03-BM

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
	
	public static String getFacultyName(Delegator delegator,
			String facultyId) {
		try {
			GenericValue faculty = delegator.findByPrimaryKey("Faculty", UtilMisc.toMap("facultyId", facultyId));
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
			GenericValue department = delegator.findByPrimaryKey("Department", UtilMisc.toMap("departmentId", departmentId));
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
	
	public static void allBorderForCell(HSSFCellStyle cellStyle){
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	}
	
	public static void setHeaderColumnsOfRow(int indexStart, int numCols, String[] str, 
			Row row, HSSFCellStyle cellStyle){
		int m = 0;
		for(int i=indexStart; i<=numCols;i++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(str[m]);
			m++;
		}
	}
	
	public static void createRowInExcel(int indexStart, int numCols, String[] str, 
			Row row, HSSFCellStyle cellStyle){
		int m = 0;
		for(int i=indexStart; i<=numCols;i++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(str[m]);
			m++;
		}
	}
	
}
