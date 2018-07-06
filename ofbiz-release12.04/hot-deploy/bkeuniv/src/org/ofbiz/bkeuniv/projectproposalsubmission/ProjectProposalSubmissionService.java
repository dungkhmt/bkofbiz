package org.ofbiz.bkeuniv.projectproposalsubmission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.bkeuniv.paperdeclaration.PaperDeclarationService;
import org.ofbiz.bkeuniv.paperdeclaration.PaperDeclarationUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.utils.BKEunivUtils;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;

public class ProjectProposalSubmissionService {
	// public static String STATUS_CREATED = "CREATED";
	// public static String STATUS_CANCELLED = "CANCELLED";

	public static String module = ProjectProposalSubmissionService.class
			.getName();

	// public static String dataFolder = "." + File.separator + "euniv-deploy";

	/*
	 * public static String establishFullFilename(String staffId, String name) {
	 * String path = dataFolder + File.separator + staffId + File.separator +
	 * "projects"; Debug.log(module + "::establishFullFilename, path = " +
	 * path); String fullname = path + File.separator + name;
	 * 
	 * File file = new File(path);
	 * 
	 * if (!file.exists()) {
	 * 
	 * file.mkdirs(); Debug.log(module +
	 * "::establishFullFilename, folder NOT exist --> Create folder\n\t"); // If
	 * you require it to make the entire directory path including // parents, //
	 * use directory.mkdirs(); here instead. }
	 * 
	 * return fullname; }
	 */

	public static void enableReviewerProposalAssignment(Delegator delegator,
			String juryId, String staffId, String researchProjectProposalId) {
		try {
			// GenericValue gv = delegator.findOne("ReviewerResearchProposal",
			// UtilMisc.toMap("juryId", juryId, "staffId", staffId,
			// "researchProjectProposalId", researchProjectProposalId),
			// false);
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("juryId",
					EntityOperator.EQUALS, juryId));
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.EQUALS,
					researchProjectProposalId));
			List<GenericValue> list = delegator.findList(
					"ReviewerResearchProposal",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			if (list != null && list.size() > 0) {
				GenericValue gv = list.get(0);
				gv.put("statusId", "ASSIGNED_REVIEWER");
				delegator.store(gv);
			} else {
				GenericValue gv = delegator
						.makeValue("ReviewerResearchProposal");
				String reviewerResearchProposalId = delegator
						.getNextSeqId("ReviewerResearchProposal");
				gv.put("reviewerResearchProposalId", reviewerResearchProposalId);
				gv.put("juryId", juryId);
				gv.put("staffId", staffId);
				gv.put("researchProjectProposalId", researchProjectProposalId);
				gv.put("statusId", "ASSIGNED_REVIEWER");
				delegator.create(gv);
				Debug.log(module
						+ "::enableReviewerProposalAssignment, reviewerResearchProposalId = "
						+ reviewerResearchProposalId);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void disableReviewerProposalAssignment(Delegator delegator,
			String juryId, String staffId, String researchProjectProposalId) {
		try {
			// GenericValue gv = delegator.findOne("ReviewerResearchProposal",
			// UtilMisc.toMap("juryId", juryId, "staffId", staffId,
			// "researchProjectProposalId", researchProjectProposalId),
			// false);
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("juryId",
					EntityOperator.EQUALS, juryId));
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.EQUALS,
					researchProjectProposalId));
			List<GenericValue> list = delegator.findList(
					"ReviewerResearchProposal",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			if (list != null && list.size() > 0) {
				for (GenericValue gv : list) {
					gv.put("statusId", "CANCELLED");
					delegator.store(gv);
				}
			} else {
				/*
				 * gv = delegator.makeValue("ReviewerResearchProposal");
				 * gv.put("juryId", juryId); gv.put("staffId", staffId);
				 * gv.put("researchProjectProposalId",
				 * researchProjectProposalId); gv.put("statusId",
				 * "ASSIGNED_REVIEWER"); delegator.create(gv);
				 */
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void exportExcelProjectProposals(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		// GenericDelegator delegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		// LocalDispatcher dispatcher =
		// org.ofbiz.service.ServiceDispatcher.getLocalDispatcher("default",
		// delegator);

		String facultyId = request.getParameter("facultyId");
		String projectCallId = request.getParameter("projectCallId");
		String projectProposalStatusId = request
				.getParameter("projectProposalStatusId");

		String filename = "Danh sach thuyet minh de tai";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			List<GenericValue> listPrj = ProjectProposalSubmissionServiceUtil
					.getListFilteredProjectProposals(delegator, projectCallId,
							facultyId, projectProposalStatusId);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sh = wb.createSheet(filename);

			CellStyle styleNormal = wb.createCellStyle();
			Font fontNormal = wb.createFont();
			fontNormal.setFontHeightInPoints((short) 12);
			fontNormal.setFontName("Times New Roman");
			// fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			fontNormal.setColor(HSSFColor.BLACK.index);
			styleNormal.setFont(fontNormal);
			styleNormal.setWrapText(true);
			// styleNormal.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleNormal.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

			styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
			styleNormal.setBorderTop(CellStyle.BORDER_THIN);
			styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
			styleNormal.setBorderRight(CellStyle.BORDER_THIN);

			sh.setColumnWidth(0, 1000);
			sh.setColumnWidth(1, 10000);
			sh.setColumnWidth(2, 8000);
			sh.setColumnWidth(3, 8000);
			sh.setColumnWidth(4, 8000);
			sh.setColumnWidth(5, 8000);
			sh.setColumnWidth(6, 8000);
			sh.setColumnWidth(7, 5000);

			int i_row = 0;
			int index = 0;

			i_row++;
			HSSFRow rh = sh.createRow(i_row);
			HSSFCell ct = rh.createCell(3);
			ct.setCellValue("DANH SÁCH ĐỀ TÀI");

			i_row += 4;
			rh = sh.createRow(i_row);
			HSSFCell ch = rh.createCell(0);
			ch.setCellValue("STT");
			ch.setCellStyle(styleNormal);

			ch = rh.createCell(1);
			ch.setCellValue("Tên đề tài");
			ch.setCellStyle(styleNormal);

			ch = rh.createCell(2);
			ch.setCellValue("Chủ nhiệm");
			ch.setCellStyle(styleNormal);

			ch = rh.createCell(3);
			ch.setCellValue("Đợt gọi đề tài");
			ch.setCellStyle(styleNormal);

			ch = rh.createCell(4);
			ch.setCellValue("Khoa/viện");
			ch.setCellStyle(styleNormal);

			ch = rh.createCell(5);
			ch.setCellValue("Nội dung nghiên cứu");
			ch.setCellStyle(styleNormal);

			ch = rh.createCell(6);
			ch.setCellValue("Sản phẩm dự kiến");
			ch.setCellStyle(styleNormal);

			ch = rh.createCell(7);
			ch.setCellValue("Kinh phí");
			ch.setCellStyle(styleNormal);

			for (GenericValue p : listPrj) {
				String pId = p.getString("researchProjectProposalId");
				List<GenericValue> contents = ProjectProposalSubmissionServiceUtil
						.getProjectProposalContents(delegator, pId);
				List<GenericValue> products = ProjectProposalSubmissionServiceUtil
						.getProjectProposalRegisteredProducts(delegator, pId);
				String str_contents = "";
				BigDecimal totalBudget = BigDecimal.ZERO;
				for (GenericValue c : contents) {
					str_contents += "-" + c.getString("content") + "\n";
					if (c.getBigDecimal("budget") != null)
						totalBudget = totalBudget
								.add(c.getBigDecimal("budget"));
				}
				if (p.getBigDecimal("materialBudget") != null)
					totalBudget = totalBudget.add(p
							.getBigDecimal("materialBudget"));

				String str_products = "";
				for (GenericValue pr : products) {
					str_products += "- " + pr.getLong("quantity") + " "
							+ pr.getString("researchProductTypeName") + ": "
							+ pr.getString("researchProductName") + "\n";
				}

				i_row++;
				index++;
				HSSFRow r = sh.createRow(i_row);
				HSSFCell c = r.createCell(0);
				c.setCellValue(index);
				c.setCellStyle(styleNormal);

				c = r.createCell(1);
				c.setCellValue(p.getString("researchProjectProposalName"));
				c.setCellStyle(styleNormal);

				c = r.createCell(2);
				c.setCellValue(p.getString("createStaffName"));
				c.setCellStyle(styleNormal);

				c = r.createCell(3);
				c.setCellValue(p.getString("projectCallName"));
				c.setCellStyle(styleNormal);

				c = r.createCell(4);
				c.setCellValue(p.getString("facultyName"));
				c.setCellStyle(styleNormal);

				c = r.createCell(5);
				c.setCellValue(str_contents);
				c.setCellStyle(styleNormal);

				c = r.createCell(6);
				c.setCellValue(str_products);
				c.setCellStyle(styleNormal);

				c = r.createCell(7);
				c.setCellValue(totalBudget.toString());
				c.setCellStyle(styleNormal);

			}
			wb.write(baos);
			byte[] bytes = baos.toByteArray();
			response.setHeader("content-disposition", "attachment;filename="
					+ filename + ".xls");
			response.setContentType("application/vnd.xls");
			response.getOutputStream().write(bytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void storeReviewerProjectProposalsAssignment(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String juryId = request.getParameter("juryId");
		String staffId = request.getParameter("staffId");
		String sel_proposalIds = request.getParameter("sel_proposalIds");
		String unsel_proposalIds = request.getParameter("unsel_proposalIds");

		Debug.log(module
				+ "::storeReviewerProjectProposalsAssignment, staffId = "
				+ staffId + ", sel_proposalIds = " + sel_proposalIds
				+ ", unsel_proposalIds = " + unsel_proposalIds + ", juryId = "
				+ juryId);
		try {
			// ENABLE selected porposal
			String[] researchProjectProposalId = sel_proposalIds.split(",");
			for (int i = 0; i < researchProjectProposalId.length; i++) {
				enableReviewerProposalAssignment(delegator, juryId, staffId,
						researchProjectProposalId[i]);
			}
			// DISABLE un_selected proposal
			researchProjectProposalId = unsel_proposalIds.split(",");
			for (int i = 0; i < researchProjectProposalId.length; i++) {
				disableReviewerProposalAssignment(delegator, juryId, staffId,
						researchProjectProposalId[i]);
			}

			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String addProjectProposal(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String facultyId = (String) request.getParameter("facultyId");
		String projectProposalName = (String) request
				.getParameter("projectProposalName");

		// String s_budget = (String)request.getParameter("budget");
		String s_material_budget = (String) request
				.getParameter("materialbudget");
		String note = (String) request.getParameter("note");
		String projectCallId = (String) request.getParameter("projectCallId");
		GenericValue userLogin = (GenericValue) request.getSession()
				.getAttribute("userLogin");
		String staffId = (String) userLogin.getString("userLoginId");

		try {
			/*
			 * GenericValue gv = delegator.makeValue("ResearchProjectProposal");
			 * String researchProjectProposalId =
			 * delegator.getNextSeqId("ResearchProjectProposal");
			 * 
			 * gv.put("researchProjectProposalId", researchProjectProposalId);
			 * 
			 * gv.put("facultyId", facultyId); gv.put("projectCallId",
			 * projectCallId); gv.put("projectProposalName",
			 * projectProposalName); gv.put("createStaffId", staffId);
			 * 
			 * delegator.create(gv);
			 */
			GenericValue pps = ProjectProposalSubmissionServiceUtil
					.createAProjectProposalSubmission(delegator,
							projectProposalName, s_material_budget, note,
							facultyId, projectCallId, staffId);

			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			return "successs";
		} catch (Exception ex) {
			ex.printStackTrace();

			return "error";
		}
	}

	public static void updateGeneralInfosProjectProposal(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String researchProjectProposalId = (String) request
				.getParameter("researchProjectProposalId");
		// String facultyId = (String) request.getParameter("facultyId");
		String projectProposalName = (String) request
				.getParameter("projectProposalName");
		String note = (String) request.getParameter("note");

		// String s_budget = (String)request.getParameter("budget");
		String s_material_budget = (String) request
				.getParameter("materialbudget");

		// String projectCallId = (String)
		// request.getParameter("projectCallId");
		GenericValue userLogin = (GenericValue) request.getSession()
				.getAttribute("userLogin");
		String staffId = (String) userLogin.getString("userLoginId");

		try {
			GenericValue gv = delegator.findOne("ResearchProjectProposal",
					UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId), false);
			gv.put("researchProjectProposalName", projectProposalName);
			gv.put("materialBudget", new BigDecimal(s_material_budget));
			gv.put("note", note);
			delegator.store(gv);

			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			// return "successs";
		} catch (Exception ex) {
			ex.printStackTrace();

			// return "error";
		}
	}

	public static void addProductProject(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String researchProjectProposalId = request
				.getParameter("researchProjectProposalId");
		String researchProductTypeId = request
				.getParameter("researchProductTypeId");
		String squantity = request.getParameter("quantity");
		Long quantity = Long.valueOf(squantity);

		Debug.log(module
				+ "::addWorkpackageProject, researchProjectProposalId = "
				+ researchProjectProposalId + ", researchProductTypeId = "
				+ researchProductTypeId + ", quantity = " + quantity);
		try {
			GenericValue gv = delegator.makeValue("ResearchProposalProduct");
			String researchProductId = delegator
					.getNextSeqId("ResearchProposalProduct");

			gv.put("researchProjectProposalId", researchProjectProposalId);
			gv.put("researchProductId", researchProductId);
			gv.put("researchProductTypeId", researchProductTypeId);
			gv.put("quantity", quantity);

			delegator.create(gv);

			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String publishEvaluationResult(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String projectCallId = (String) request.getParameter("projectCallId");
		Debug.log(module + "::publishEvaluationResult, projectCall "
				+ projectCallId);
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("projectCallId",
					EntityOperator.EQUALS, projectCallId));

			List<GenericValue> list = delegator.findList(
					"ResearchProjectProposal",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			for (GenericValue p : list) {
				p.put("evaluationOpenFlag", "Y");
				delegator.store(p);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "success";
	}

	public static String unpublishEvaluationResult(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String projectCallId = (String) request.getParameter("projectCallId");
		Debug.log(module + "::unpublishEvaluationResult, projectCall "
				+ projectCallId);
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("projectCallId",
					EntityOperator.EQUALS, projectCallId));

			List<GenericValue> list = delegator.findList(
					"ResearchProjectProposal",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			for (GenericValue p : list) {
				p.put("evaluationOpenFlag", "N");
				delegator.store(p);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "success";
	}

	public static String generateCodeProjectProposals(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String projectCallId = (String) request.getParameter("projectCallId");
		String prefix = "T-PC";

		Debug.log(module + "::generateCodeProjectProposals, projectCall "
				+ projectCallId);
		try {
			GenericValue pc = delegator.findOne("ProjectCall",
					UtilMisc.toMap("projectCallId", projectCallId), false);
			String year = (String) pc.get("year");
			prefix = "T" + year + "-PC";

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("projectCallId",
					EntityOperator.EQUALS, projectCallId));
			conds.add(EntityCondition
					.makeCondition(
							"statusId",
							EntityOperator.EQUALS,
							ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_APPROVED_UNIVERSITY));

			List<String> orderBy = FastList.newInstance();
			orderBy.add("facultyId");

			List<GenericValue> list = delegator.findList(
					"ResearchProjectProposal",
					EntityCondition.makeCondition(conds), null, orderBy, null,
					false);
			int idx = 0;
			for (GenericValue p : list) {
				idx++;
				String code = prefix
						+ "-"
						+ ProjectProposalSubmissionServiceUtil.addPading(idx
								+ "", 3);
				p.put("researchProjectProposalCode", code);
				delegator.store(p);

				Debug.log(module + "::generateCodeProjectProposals, prjec "
						+ p.getString("researchProjectProposalName")
						+ " with code = " + code);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "success";
	}

	public static String closeProjectCall(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String projectCallId = (String) request.getParameter("projectCallId");
		Debug.log(module + "::closeProjectCall, CLOSED projectCall "
				+ projectCallId);
		try {
			GenericValue pc = delegator.findOne("ProjectCall",
					UtilMisc.toMap("projectCallId", projectCallId), false);
			if (pc != null) {
				pc.put("statusId",
						ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_CLOSED);
				delegator.store(pc);
				Debug.log(module
						+ "::closeProjectCall, CLOSED successfully projectCall "
						+ projectCallId);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public static String approveProjectProposal(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String researchProjectProposalId = (String) request
				.getParameter("researchProjectProposalId");
		Debug.log(module
				+ "::approveProjectProposal, researchProjectProposalId "
				+ researchProjectProposalId);
		try {
			GenericValue pc = delegator.findOne("ResearchProjectProposal",
					UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId), false);
			if (pc != null) {
				pc.put("statusId",
						ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_APPROVED);
				delegator.store(pc);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public static String notApproveProjectProposal(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String researchProjectProposalId = (String) request
				.getParameter("researchProjectProposalId");
		Debug.log(module
				+ "::notApproveProjectProposal, researchProjectProposalId "
				+ researchProjectProposalId);
		try {
			GenericValue pc = delegator.findOne("ResearchProjectProposal",
					UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId), false);
			if (pc != null) {
				pc.put("statusId",
						ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_REJECTED);
				delegator.store(pc);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public static String acceptReviseProjectProposal(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		// GenericDelegator gdelegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		// LocalDispatcher dispatcher =
		// org.ofbiz.service.ServiceDispatcher.getLocalDispatcher("default",
		// gdelegator);

		String researchProjectProposalId = (String) request
				.getParameter("researchProjectProposalId");
		String newStatusId = ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_ACCEPT_REVISE;
		Debug.log(module
				+ "::acceptReviseProjectProposal, researchProjectProposalId "
				+ researchProjectProposalId);

		try {
			/*
			 * GenericValue pc = delegator.findOne("ResearchProjectProposal",
			 * UtilMisc.toMap("researchProjectProposalId",
			 * researchProjectProposalId), false); if (pc != null) {
			 * pc.put("statusId",
			 * ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_ACCEPT_REVISE
			 * ); delegator.store(pc);
			 * 
			 * }
			 */
			// Map<String, Object> in = FastMap.newInstance();
			// in.put("researchProjectProposalId", researchProjectProposalId);
			// Map<String, Object> rs =
			ProjectProposalSubmissionServiceUtil
					.cloneProjectProposalWithNewStatus(delegator,
							researchProjectProposalId, newStatusId);

		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public static String openProjectCallForSubmission(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String projectCallId = (String) request.getParameter("projectCallId");
		Debug.log(module + "::openProjectCallForSubmission,  projectCall "
				+ projectCallId);
		try {
			GenericValue pc = delegator.findOne("ProjectCall",
					UtilMisc.toMap("projectCallId", projectCallId), false);
			if (pc != null) {
				pc.put("statusId",
						ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_OPEN);
				delegator.store(pc);
				Debug.log(module
						+ "::openProjectCallForSubmission, OPEN successfully projectCall "
						+ projectCallId);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public static String openProjectCallForRevision(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String projectCallId = (String) request.getParameter("projectCallId");
		Debug.log(module + "::openProjectCallForRevision,  projectCall "
				+ projectCallId);
		try {
			GenericValue pc = delegator.findOne("ProjectCall",
					UtilMisc.toMap("projectCallId", projectCallId), false);
			if (pc != null) {
				pc.put("statusId",
						ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_OPEN_REVISED);
				delegator.store(pc);
				Debug.log(module
						+ "::openProjectCallForRevision, OPEN successfully projectCall "
						+ projectCallId);

				/*
				 * // update status of all project proposal of this project call
				 * 
				 * List<EntityCondition> conds = FastList.newInstance();
				 * conds.add
				 * (EntityCondition.makeCondition("projectCallId",EntityOperator
				 * .EQUALS,projectCallId));
				 * conds.add(EntityCondition.makeCondition
				 * ("statusId",EntityOperator.EQUALS,
				 * ProjectProposalSubmissionServiceUtil
				 * .STATUS_PROJECT_UNDER_REVIEW)); List<GenericValue> lstPrj =
				 * delegator.findList("ResearchProjectProposal",
				 * EntityCondition.makeCondition(conds), null,null,null, false);
				 * for(GenericValue p: lstPrj){ p.put("statusId",
				 * ProjectProposalSubmissionServiceUtil
				 * .STATUS_PROJECT_ACCEPT_REVISE); delegator.store(p); }
				 */

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public static String closeProjectCallForRevision(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String projectCallId = (String) request.getParameter("projectCallId");
		Debug.log(module + "::closeProjectCallForRevision,  projectCall "
				+ projectCallId);
		try {
			GenericValue pc = delegator.findOne("ProjectCall",
					UtilMisc.toMap("projectCallId", projectCallId), false);
			if (pc != null) {
				pc.put("statusId",
						ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_CLOSED_REVISED);
				delegator.store(pc);
				Debug.log(module
						+ "::openProjectCallForRevision, OPEN successfully projectCall "
						+ projectCallId);

				/*
				 * // update status of all project proposal of this project call
				 * 
				 * List<EntityCondition> conds = FastList.newInstance();
				 * conds.add
				 * (EntityCondition.makeCondition("projectCallId",EntityOperator
				 * .EQUALS,projectCallId));
				 * conds.add(EntityCondition.makeCondition
				 * ("statusId",EntityOperator.EQUALS,
				 * ProjectProposalSubmissionServiceUtil
				 * .STATUS_PROJECT_UNDER_REVIEW)); List<GenericValue> lstPrj =
				 * delegator.findList("ResearchProjectProposal",
				 * EntityCondition.makeCondition(conds), null,null,null, false);
				 * for(GenericValue p: lstPrj){ p.put("statusId",
				 * ProjectProposalSubmissionServiceUtil
				 * .STATUS_PROJECT_ACCEPT_REVISE); delegator.store(p); }
				 */

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public static void addProjectProposalJury(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String facultyId = request.getParameter("facultyId");
		String juryName = request.getParameter("juryName");
		String projectCallId = request.getParameter("projectCallId");

		Debug.log(module + "::addProjectProposalJury, facultyId = " + facultyId
				+ ", juryName = " + juryName + ", projectCallId = "
				+ projectCallId);
		try {
			GenericValue gv = delegator.makeValue("Jury");
			String juryId = delegator.getNextSeqId("Jury");

			gv.put("facultyId", facultyId);
			gv.put("juryName", juryName);
			gv.put("projectCallId", projectCallId);
			gv.put("juryId", juryId);

			delegator.create(gv);

			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Map<String, Object> getReviewProjectProposal(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		try {
			String reviewerResearchProposalId = (String) context
					.get("reviewerResearchProposalId");
			GenericValue gv = delegator.findOne("ReviewerResearchProposal",
					UtilMisc.toMap("reviewerResearchProposalId",
							reviewerResearchProposalId), false);
			retSucc.put("reviewprojectproposal", gv);

			return retSucc;
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
	}

	public static Map<String, Object> getListReviewsOfProjectProposal(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		try {
			String researchProjectProposalId = (String) context
					.get("researchProjectProposalId");

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.EQUALS,
					researchProjectProposalId));
			conds.add(EntityCondition
					.makeCondition(
							"statusId",
							EntityOperator.EQUALS,
							ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_EVALUATION_CONFIRM));

			List<GenericValue> list = delegator.findList(
					"ReviewerResearchProposalView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			retSucc.put("reviewprojectproposals", list);

			return retSucc;
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
	}

	public static void updateReviewProjectProposal(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		try {
			String reviewerResearchProposalId = request
					.getParameter("reviewerResearchProposalId");
			Long evaluationInnovation = Long.valueOf(request
					.getParameter("evaluationInnovation"));
			Long evaluationMotivation = Long.valueOf(request
					.getParameter("evaluationMotivation"));
			Long evaluationApplicability = Long.valueOf(request
					.getParameter("evaluationApplicability"));
			Long evaluationResearchMethod = Long.valueOf(request
					.getParameter("evaluationResearchMethod"));

			Long evaluationResearchContent = Long.valueOf(request
					.getParameter("evaluationResearchContent"));
			Long evaluationPaper = Long.valueOf(request
					.getParameter("evaluationPaper"));
			Long evaluationProduct = Long.valueOf(request
					.getParameter("evaluationProduct"));
			Long evaluationPatent = Long.valueOf(request
					.getParameter("evaluationPatent"));
			Long evaluationGraduateStudent = Long.valueOf(request
					.getParameter("evaluationGraduateStudent"));
			Long evaluationYoungResearcher = Long.valueOf(request
					.getParameter("evaluationYoungResearcher"));
			Long evaluationEducation = Long.valueOf(request
					.getParameter("evaluationEducation"));
			Long evaluationReasonableBudget = Long.valueOf(request
					.getParameter("evaluationReasonableBudget"));

			String comments = (String) request.getParameter("comments");

			Long totalEvaluation = evaluationInnovation + evaluationMotivation
					+ evaluationApplicability + evaluationResearchMethod
					+ evaluationResearchContent + evaluationPaper
					+ evaluationProduct + evaluationPatent
					+ evaluationGraduateStudent + evaluationYoungResearcher
					+ evaluationEducation + evaluationReasonableBudget;

			Debug.log(module
					+ "::updateReviewProjectProposal, reviewerResearchProposalId = "
					+ reviewerResearchProposalId + ", evaluationInnovation = "
					+ evaluationInnovation + ", evaluationMotivation = "
					+ evaluationMotivation + ", evaluationApplicability = "
					+ evaluationApplicability + ", comments=" + comments);

			GenericValue gv = delegator.findOne("ReviewerResearchProposal",
					UtilMisc.toMap("reviewerResearchProposalId",
							reviewerResearchProposalId), false);
			gv.put("evaluationInnovation", evaluationInnovation);
			gv.put("evaluationMotivation", evaluationMotivation);
			gv.put("evaluationApplicability", evaluationApplicability);
			gv.put("evaluationResearchMethod", evaluationResearchMethod);

			gv.put("evaluationResearchContent", evaluationResearchContent);
			gv.put("evaluationPaper", evaluationPaper);
			gv.put("evaluationProduct", evaluationProduct);
			gv.put("evaluationPatent", evaluationPatent);
			gv.put("evaluationGraduateStudent", evaluationGraduateStudent);
			gv.put("evaluationYoungResearcher", evaluationYoungResearcher);
			gv.put("evaluationEducation", evaluationEducation);
			gv.put("evaluationReasonableBudget", evaluationReasonableBudget);

			gv.put("totalEvaluation", totalEvaluation);

			gv.put("comments", comments);

			delegator.store(gv);

			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void confirmReviewProjectProposal(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		try {
			String reviewerResearchProposalId = request
					.getParameter("reviewerResearchProposalId");
			Debug.log(module
					+ "::confirmReviewProjectProposal, reviewerResearchProposalId = "
					+ reviewerResearchProposalId);

			GenericValue gv = delegator.findOne("ReviewerResearchProposal",
					UtilMisc.toMap("reviewerResearchProposalId",
							reviewerResearchProposalId), false);

			gv.put("statusId",
					ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_EVALUATION_CONFIRM);

			delegator.store(gv);

			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void addProjectCall(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String projectCategoryId = request.getParameter("projectCategoryId");
		String projectCallName = request.getParameter("projectCallName");
		String year = request.getParameter("year");

		Debug.log(module + "::addProjectCall, projectCategoryId = "
				+ projectCategoryId + ", projectCallName = " + projectCallName
				+ ", year  = " + year);
		try {
			GenericValue gv = delegator.makeValue("ProjectCall");
			String projectCallId = delegator.getNextSeqId("ProjectCall");

			gv.put("projectCallId", projectCallId);
			gv.put("projectCallName", projectCallName);
			gv.put("projectCategoryId", projectCategoryId);
			gv.put("statusId",
					ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_CREATED);
			gv.put("year", year);

			delegator.create(gv);

			String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void addProjectProposalJuryMember(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String staffId = request.getParameter("staffId");
		String juryRoleTypeId = request.getParameter("juryRoleTypeId");
		String juryId = request.getParameter("juryId");

		Debug.log(module + "::addProjectProposalJuryMember, staffId = "
				+ staffId + ", juryRoleTypeId = " + juryRoleTypeId
				+ ", juryId  = " + juryId);
		try {
			GenericValue gv = delegator.makeValue("JuryMember");
			String juryMemberId = delegator.getNextSeqId("JuryMember");

			gv.put("staffId", staffId);
			gv.put("juryRoleTypeId", juryRoleTypeId);
			gv.put("juryId", juryId);
			gv.put("juryMemberId", juryMemberId);

			delegator.create(gv);

			GenericValue st = delegator.findOne("Staff",
					UtilMisc.toMap("staffId", staffId), false);
			GenericValue rl = delegator.findOne("JuryRoleType",
					UtilMisc.toMap("juryRoleTypeId", juryRoleTypeId), false);
			String rs = "{\"result\":\"OK\"" + ",\"staffName\":" + "\""
					+ st.getString("staffName") + "\""
					+ ",\"juryRoleTypeName\":" + "\""
					+ rl.getString("juryRoleTypeName") + "\"" + "}";
			Debug.log(module + "::addProjectProposalJuryMember, return JSON = "
					+ rs);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void addProjectProposalType(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String researchProjectProposalId = request
				.getParameter("researchProjectProposalId");
		String researchProductTypeId = request
				.getParameter("researchProductTypeId[]");
		String researchProductName = request
				.getParameter("researchProductName");

		String sQuantity = request.getParameter("quantity");
		Long quantity = Long.valueOf(sQuantity);
		Debug.log(module
				+ "::addProjectProposalType, researchProjectProposalId= "
				+ researchProjectProposalId + ", researchProductTypeId = "
				+ researchProductTypeId + ", quantity = " + quantity
				+ ", researchProductName = " + researchProductName);
		try {
			GenericValue gv = delegator.makeValue("ResearchProposalProduct");
			String researchProductId = delegator
					.getNextSeqId("ResearchProposalProduct");

			gv.put("researchProductId", researchProductId);
			gv.put("researchProductTypeId", researchProductTypeId);
			gv.put("researchProjectProposalId", researchProjectProposalId);
			gv.put("quantity", quantity);
			gv.put("researchProductName", researchProductName);
			gv.put("sourcePathUpload", "-");

			delegator.create(gv);

			GenericValue pdt = delegator.findOne("ResearchProductType",
					UtilMisc.toMap("researchProductTypeId",
							researchProductTypeId), false);

			Map<String, Object> retObj = FastMap.newInstance();
			retObj.put("researchProductId", gv.get("researchProductId"));
			retObj.put("researchProductTypeId", gv.get("researchProductTypeId"));
			retObj.put("researchProductTypeName",
					pdt.get("researchProductTypeName"));
			retObj.put("researchProjectProposalId",
					gv.get("researchProjectProposalId"));
			retObj.put("quantity", gv.get("quantity"));
			retObj.put("researchProductId", gv.get("researchProductId"));
			retObj.put("researchProductName", gv.get("researchProductName"));
			retObj.put("sourcePathUpload", gv.get("sourcePathUpload"));

			Map<String, Object> context = FastMap.newInstance();
			context.put("projectProposalProducts", retObj);
			context.put("message", "Create new row");
			BKEunivUtils.writeJSONtoResponse(
					BKEunivUtils.parseJSONObject(context), response, 200);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void updateProjectProposalType(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String researchProductId = request.getParameter("researchProductId");

		String researchProjectProposalId = request
				.getParameter("researchProjectProposalId");
		String researchProductTypeId = request
				.getParameter("researchProductTypeId[]");
		String researchProductName = request
				.getParameter("researchProductName");

		String sQuantity = request.getParameter("quantity");
		Long quantity = Long.valueOf(sQuantity);
		Debug.log(module + "::updateProjectProposalType, researchProductId = "
				+ researchProductId + ", researchProjectProposalId= "
				+ researchProjectProposalId + ", researchProductTypeId = "
				+ researchProductTypeId + ", quantity = " + quantity
				+ ", researchProductName = " + researchProductName);
		try {
			GenericValue gv = delegator.findOne("ResearchProposalProduct",
					UtilMisc.toMap("researchProductId", researchProductId),
					false);
			if (gv != null) {

				gv.put("researchProductTypeId", researchProductTypeId);
				gv.put("researchProjectProposalId", researchProjectProposalId);
				gv.put("quantity", quantity);
				gv.put("researchProductName", researchProductName);
				gv.put("sourcePathUpload", "-");

				delegator.store(gv);

				Map<String, Object> context = FastMap.newInstance();
				context.put("projectProposalProducts", gv);
				context.put("message", "Update successfully");
				BKEunivUtils.writeJSONtoResponse(
						BKEunivUtils.parseJSONObject(context), response, 200);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void removeProjectProposalType(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String researchProductId = request.getParameter("researchProductId");

		Debug.log(module + "::removeProjectProposalType, researchProductId = "
				+ researchProductId);
		try {
			GenericValue gv = delegator.findOne("ResearchProposalProduct",
					UtilMisc.toMap("researchProductId", researchProductId),
					false);
			if (gv != null) {
				delegator.removeValue(gv);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void addWorkpackageProject(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String researchProjectProposalId = request
				.getParameter("researchProjectProposalId");
		String staffId = request.getParameter("staffId[]");
		String content = request.getParameter("content");
		String sworkingdays = request.getParameter("workingDays");
		long workingdays = 0;
		if (sworkingdays != null && !sworkingdays.equals(""))
			workingdays = Long.valueOf(sworkingdays);
		String sbudget = request.getParameter("budget");
		BigDecimal budget = null;
		if (sbudget != null && !sbudget.equals(""))
			budget = new BigDecimal(sbudget);
		else
			budget = BigDecimal.ZERO;

		Debug.log(module
				+ "::addWorkpackageProject, researchProjectProposalId = "
				+ researchProjectProposalId + ", staffId = " + staffId
				+ ", content = " + content + ", workingdays = " + workingdays
				+ ", sbudget = " + sbudget + ", budget = " + budget);
		try {
			GenericValue gv = delegator
					.makeValue("ResearchProposalContentItem");
			String contentItemSeq = delegator
					.getNextSeqId("ResearchProposalContentItem");

			gv.put("researchProjectProposalId", researchProjectProposalId);
			gv.put("contentItemSeq", contentItemSeq);
			gv.put("staffId", staffId);
			gv.put("content", content);
			gv.put("workingDays", workingdays);
			gv.put("budget", budget);

			delegator.create(gv);
			Map<String, Object> context = FastMap.newInstance();
			context.put("projectProposalContentItems", gv);
			context.put("message", "Create new row");

			BKEunivUtils.writeJSONtoResponse(
					BKEunivUtils.parseJSONObject(context), response, 200);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void updateWorkpackageProject(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String researchProjectProposalId = request
				.getParameter("researchProjectProposalId");
		String contentItemSeq = request.getParameter("contentItemSeq");

		String staffId = request.getParameter("staffId[]");
		String content = request.getParameter("content");
		String sworkingdays = request.getParameter("workingDays");

		String sbudget = request.getParameter("budget");

		Debug.log(module
				+ "::updateWorkpackageProject, researchProjectProposalId = "
				+ researchProjectProposalId + ", contentItemSeq = "
				+ contentItemSeq + ", staffId = " + staffId + ", content = "
				+ content + ", workingdays = " + sworkingdays + ", sbudget = "
				+ sbudget + ", budget = " + sbudget);
		Long workingdays = Long.valueOf(sworkingdays);
		BigDecimal budget = new BigDecimal(sbudget);

		try {
			GenericValue gv = delegator.findOne("ResearchProposalContentItem",
					UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId, "contentItemSeq",
							contentItemSeq), false);

			if (gv != null) {
				// gv.put("researchProjectProposalId",
				// researchProjectProposalId);
				// gv.put("contentItemSeq", contentItemSeq);
				gv.put("staffId", staffId);
				gv.put("content", content);
				gv.put("workingDays", workingdays);
				gv.put("budget", budget);

				delegator.store(gv);
				Map<String, Object> context = FastMap.newInstance();
				context.put("projectProposalContentItems", gv);
				context.put("message", "row updated");

				BKEunivUtils.writeJSONtoResponse(
						BKEunivUtils.parseJSONObject(context), response, 200);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void deleteWorkpackageProject(HttpServletRequest request,
			HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String researchProjectProposalId = request
				.getParameter("researchProjectProposalId");
		String contentItemSeq = request.getParameter("contentItemSeq");

		Debug.log(module
				+ "::deleteWorkpackageProject, researchProjectProposalId = "
				+ researchProjectProposalId + ", contentItemSeq = "
				+ contentItemSeq);

		try {
			GenericValue gv = delegator.findOne("ResearchProposalContentItem",
					UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId, "contentItemSeq",
							contentItemSeq), false);

			if (gv != null) {
				delegator.removeValue(gv);
				Map<String, Object> context = FastMap.newInstance();
				context.put("projectProposalContentItems", gv);
				context.put("message", "row deleted");

				BKEunivUtils.writeJSONtoResponse(
						BKEunivUtils.parseJSONObject(context), response, 200);
				Debug.log(module
						+ "::deleteWorkpackageProject, researchProjectProposalId = "
						+ researchProjectProposalId + ", contentItemSeq = "
						+ contentItemSeq + " DELETED");
			} else {
				Debug.log(module
						+ "::deleteWorkpackageProject, researchProjectProposalId = "
						+ researchProjectProposalId + ", contentItemSeq = "
						+ contentItemSeq + " NOT FOUND");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Map<String, Object> getMembersOfResearchProjectProposalJury(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		String juryId = (String) context.get("juryId");
		Delegator delegator = ctx.getDelegator();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("juryId",
					EntityOperator.EQUALS, juryId));
			List<GenericValue> list = delegator.findList("JuryMemberView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			Debug.log(module
					+ "::getMembersOfResearchProjectProposalJury, list.sz = "
					+ list.size());

			List<Map<String, Object>> retList = FastList.newInstance();
			Map<String, Object> in = FastMap.newInstance();
			for (GenericValue g : list) {

				String staffId = g.getString("staffId");
				in.clear();
				in.put("juryId", juryId);
				in.put("staffId", staffId);
				Map<String, Object> rs = dispatcher.runSync(
						"getListProjectProposalsAssignedForReview", in);

				Map<String, Object> item = FastMap.newInstance();
				item.put("staffId", g.getString("staffId"));
				item.put("staffName", g.getString("staffName"));
				item.put("juryName", g.getString("juryName"));
				item.put("juryRoleTypeName", g.getString("juryRoleTypeName"));
				item.put("projectproposals", rs.get("projectproposals"));
				retList.add(item);
			}
			retSucc.put("members", retList);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListJuryRoleTypes(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			List<GenericValue> list = delegator.findList("JuryRoleType",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			Debug.log(module + "::getListJuryRoleTypes, list.sz = "
					+ list.size());
			retSucc.put("juryRoleTypes", list);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getProjectProposalContentItem(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = context
				.get("researchProjectProposalId") + "";
		Delegator delegator = ctx.getDelegator();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.EQUALS,
					researchProjectProposalId));
			List<GenericValue> list = delegator.findList(
					"ResearchProposalContentItemView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			Debug.log(module + "::getProjectProposalContentItem, list.sz = "
					+ list.size());
			retSucc.put("projectProposalContentItems", list);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getProjectProposalProducts(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = context
				.get("researchProjectProposalId") + "";
		Debug.log(module
				+ "::getProjectProposalProducts, researchProjectProposalId = "
				+ researchProjectProposalId);
		Delegator delegator = ctx.getDelegator();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.EQUALS,
					researchProjectProposalId));
			List<GenericValue> list = delegator.findList(
					"ResearchProposalProductView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			Debug.log(module + "::getProjectProposalProducts, list.sz = "
					+ list.size());
			retSucc.put("projectProposalProducts", list);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getMembersOfProjectProposal(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = context
				.get("researchProjectProposalId") + "";
		Delegator delegator = ctx.getDelegator();
		try {
			Debug.log(module
					+ "::getMembersOfProjectProposal, researchProjectProposalId = "
					+ researchProjectProposalId);

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.EQUALS,
					researchProjectProposalId));
			List<GenericValue> list = delegator.findList(
					"ProjectProposalMemberView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			Debug.log(module + "::getMembersOfProjectProposal, list.sz = "
					+ list.size());
			retSucc.put("members", list);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectProposalRoleTypes(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			List<GenericValue> list = delegator.findList(
					"ProjectProposalRoleType",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			retSucc.put("projectProposalRoleTypes", list);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectProposalProductTypes(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		try {
			List<EntityCondition> conds = FastList.newInstance();
			List<GenericValue> list = delegator.findList("ResearchProductType",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			retSucc.put("projectProposalProductTypes", list);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectCalls(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String projectCallId = (String) context.get("projectCallId");
		try {
			Delegator delegator = ctx.getDelegator();

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition
					.makeCondition(
							"statusId",
							EntityOperator.NOT_EQUAL,
							ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_CANCELLED));

			if (projectCallId != null)
				conds.add(EntityCondition.makeCondition("projectCallId",
						EntityOperator.EQUALS, projectCallId));

			List<GenericValue> projectCalls = delegator.findList(
					"ProjectCallView", EntityCondition.makeCondition(conds),
					null, null, null, false);
			retSucc.put("projectCalls", projectCalls);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectCallsAndProposalJuriesSchool(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = (String) userLogin.getString("userLoginId");

		try {
			Delegator delegator = ctx.getDelegator();
			LocalDispatcher dispatcher = ctx.getDispatcher();

			Map<String, Object> in = FastMap.newInstance();
			in.put("universityId", "HUST");
			in.put("userLogin", userLogin);

			Map<String, Object> rs = dispatcher
					.runSync("getFacultyOfStaff", in);
			List<GenericValue> fal = (List<GenericValue>) rs.get("faculties");
			String facultyId = null;
			if (fal != null && fal.size() > 0)
				facultyId = (String) (fal.get(0).getString("facultyId"));
			Debug.log(module
					+ "::getListProjectCallsAndProposalJuriesSchool, staffId = "
					+ staffId + ", facultyId = " + facultyId);

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition
					.makeCondition(
							"statusId",
							EntityOperator.NOT_EQUAL,
							ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_CANCELLED));

			List<GenericValue> projectCalls = delegator.findList(
					"ProjectCallView", EntityCondition.makeCondition(conds),
					null, null, null, false);

			List<Map<String, Object>> resultList = FastList.newInstance();
			for (GenericValue pc : projectCalls) {
				Map<String, Object> pcj = FastMap.newInstance();

				String projectCallId = (String) pc.getString("projectCallId");
				String projectCallName = (String) pc
						.getString("projectCallName");
				String year = (String) pc.getString("year");
				String projectCategoryName = (String) pc
						.getString("projectCategoryName");
				String statusName = (String) pc.getString("statusName");

				pcj.put("projectCallId", projectCallId);
				pcj.put("projectCallName", projectCallName);
				pcj.put("year", year);
				pcj.put("projectCategoryName", projectCategoryName);
				pcj.put("statusName", statusName);

				conds = FastList.newInstance();
				conds.add(EntityCondition.makeCondition("projectCallId",
						projectCallId));
				conds.add(EntityCondition.makeCondition("facultyId", facultyId));
				List<GenericValue> juries = delegator.findList("Jury",
						EntityCondition.makeCondition(conds), null, null, null,
						false);
				if (juries != null && juries.size() > 0) {
					GenericValue jury = juries.get(0);
					String juryId = jury.getString("juryId");
					String juryName = jury.getString("juryName");

					pcj.put("juryId", juryId);
					pcj.put("juryName", juryName);
					Debug.log(module
							+ "::getListProjectCallsAndProposalJuriesSchool, staffId = "
							+ staffId + ", facultyId = " + facultyId
							+ ", juryId = " + juryId + ", juryName = "
							+ juryName);
				}
				resultList.add(pcj);
			}

			retSucc.put("projectCalls", resultList);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectCallsAndProposalJuriesUniversity(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String staffId = (String) userLogin.getString("userLoginId");

		try {
			Delegator delegator = ctx.getDelegator();
			LocalDispatcher dispatcher = ctx.getDispatcher();

			Map<String, Object> in = FastMap.newInstance();
			/*
			 * in.put("universityId", "HUST"); in.put("userLogin", userLogin);
			 * 
			 * Map<String, Object> rs = dispatcher .runSync("getFacultyOfStaff",
			 * in); List<GenericValue> fal = (List<GenericValue>)
			 * rs.get("faculties"); String facultyId = null; if (fal != null &&
			 * fal.size() > 0) facultyId = (String)
			 * (fal.get(0).getString("facultyId")); Debug.log(module +
			 * "::getListProjectCallsAndProposalJuriesSchool, staffId = " +
			 * staffId + ", facultyId = " + facultyId);
			 */

			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition
					.makeCondition(
							"statusId",
							EntityOperator.NOT_EQUAL,
							ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_CANCELLED));

			List<GenericValue> projectCalls = delegator.findList(
					"ProjectCallView", EntityCondition.makeCondition(conds),
					null, null, null, false);

			String facultyId = "UNIVERSITY";
			List<Map<String, Object>> resultList = FastList.newInstance();
			for (GenericValue pc : projectCalls) {
				Map<String, Object> pcj = FastMap.newInstance();

				String projectCallId = (String) pc.getString("projectCallId");
				String projectCallName = (String) pc
						.getString("projectCallName");
				String year = (String) pc.getString("year");
				String projectCategoryName = (String) pc
						.getString("projectCategoryName");
				String statusName = (String) pc.getString("statusName");

				pcj.put("projectCallId", projectCallId);
				pcj.put("projectCallName", projectCallName);
				pcj.put("year", year);
				pcj.put("projectCategoryName", projectCategoryName);
				pcj.put("statusName", statusName);

				conds = FastList.newInstance();
				conds.add(EntityCondition.makeCondition("projectCallId",
						projectCallId));
				conds.add(EntityCondition.makeCondition("facultyId", facultyId));
				List<GenericValue> juries = delegator.findList("Jury",
						EntityCondition.makeCondition(conds), null, null, null,
						false);
				if (juries != null && juries.size() > 0) {
					GenericValue jury = juries.get(0);
					String juryId = jury.getString("juryId");
					String juryName = jury.getString("juryName");

					pcj.put("juryId", juryId);
					pcj.put("juryName", juryName);
					Debug.log(module
							+ "::getListProjectCallsAndProposalJuriesSchool, staffId = "
							+ staffId + ", facultyId = " + facultyId
							+ ", juryId = " + juryId + ", juryName = "
							+ juryName);
				}
				resultList.add(pcj);
			}

			retSucc.put("projectCalls", resultList);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getAProjectCall(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String projectCallId = (String) context.get("projectCallId");
		String statusId = (String) context.get("statusId");
		Debug.log(module + "::getAProjectCall, projectCallId = "
				+ projectCallId + ", statusId = " + statusId);
		try {
			Delegator delegator = ctx.getDelegator();

			List<EntityCondition> conds = FastList.newInstance();
			// conds.add(EntityCondition.makeCondition("statusId",EntityOperator.EQUALS,ProjectProposalSubmissionServiceUtil.STATUS_CREATED));
			if (statusId != null)
				conds.add(EntityCondition.makeCondition("statusId",
						EntityOperator.EQUALS, statusId));
			conds.add(EntityCondition.makeCondition("projectCallId",
					EntityOperator.EQUALS, projectCallId));

			List<GenericValue> projectCalls = delegator.findList(
					"ProjectCallView", EntityCondition.makeCondition(conds),
					null, null, null, false);
			retSucc.put("projectCalls", projectCalls);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectCallStatus(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try {
			Delegator delegator = ctx.getDelegator();

			List<EntityCondition> conds = FastList.newInstance();

			List<GenericValue> projectCalls = delegator.findList(
					"ProjectCallStatus", EntityCondition.makeCondition(conds),
					null, null, null, false);
			retSucc.put("projectCallStatus", projectCalls);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectProposalStatus(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try {
			Delegator delegator = ctx.getDelegator();

			List<EntityCondition> conds = FastList.newInstance();

			List<GenericValue> projectCalls = delegator.findList(
					"ProjectProposalStatus",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			retSucc.put("projectProposalStatus", projectCalls);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectCategory(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		try {
			Delegator delegator = ctx.getDelegator();

			List<EntityCondition> conds = FastList.newInstance();

			List<GenericValue> projectCalls = delegator.findList(
					"ProjectCategory", EntityCondition.makeCondition(conds),
					null, null, null, false);
			retSucc.put("projectCategory", projectCalls);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> cloneProjectProposalWithNewStatus(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = context
				.get("researchProjectProposalId") + "";
		String newStatusId = context.get("newStatusId") + "";
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");
		Debug.log(module
				+ "::cloneProjectProposalWithNewStatus, researchProjectProposalId = "
				+ researchProjectProposalId + ", newStatusId = " + newStatusId);

		try {

			ProjectProposalSubmissionServiceUtil
					.cloneProjectProposalWithNewStatus(delegator,
							researchProjectProposalId, newStatusId);

			/*
			 * GenericValue p = delegator.findOne("ResearchProjectProposal",
			 * UtilMisc
			 * .toMap("researchProjectProposalId",researchProjectProposalId
			 * ),false);
			 * 
			 * // create a ResearchProjectProposal String
			 * newResearchProjectProposalId =
			 * delegator.getNextSeqId("ResearchProjectProposal"); GenericValue
			 * np = delegator.makeValue("ResearchProjectProposal");
			 * np.put("researchProjectProposalId",
			 * newResearchProjectProposalId); np.put("statusId", newStatusId);
			 * 
			 * np.put("researchProjectProposalCode",
			 * (String)p.get("researchProjectProposalCode")); np.put("partyId",
			 * (String)p.get("partyId")); np.put("createStaffId",
			 * (String)p.get("createStaffId")); np.put("projectCallId",
			 * (String)p.get("projectCallId")); np.put("projectCategoryId",
			 * (String)p.get("projectCategoryId"));
			 * np.put("researchProjectProposalName",
			 * (String)p.get("researchProjectProposalName"));
			 * np.put("totalBudget", (Long)p.get("totalBudget"));
			 * np.put("approvedByStaffId", (String)p.get("approvedByStaffId"));
			 * np.put("facultyId", (String)p.get("facultyId"));
			 * np.put("startDate", (java.sql.Date)p.get("startDate"));
			 * np.put("endDate", (java.sql.Date)p.get("endDate"));
			 * np.put("deliverable", (String)p.get("deliverable"));
			 * np.put("materialBudget", (Long)p.get("materialBudget"));
			 * np.put("evaluationOpenFlag",
			 * (String)p.get("evaluationOpenFlag"));
			 * 
			 * // clone uploaded file String staffId = (String)
			 * p.get("createStaffId"); String filenameDB = (String)
			 * p.get("sourceFileUpload"); String fullFileName =
			 * ProjectProposalSubmissionServiceUtil
			 * .establishFullFilename(staffId, filenameDB);
			 * 
			 * String ext =
			 * ProjectProposalSubmissionServiceUtil.getExtension(filenameDB);
			 * java.util.Date currentDate = new java.util.Date();
			 * SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
			 * "yyyyMMddHHmmss"); String sCurrentDate =
			 * dateformatyyyyMMdd.format(currentDate);
			 * 
			 * String newFilenameDB = sCurrentDate + "." + ext; String
			 * newFullFileName =
			 * ProjectProposalSubmissionServiceUtil.establishFullFilename
			 * (staffId, newFilenameDB);
			 * 
			 * Files.copy((new File(fullFileName)).toPath(), (new
			 * File(newFullFileName
			 * )).toPath(),StandardCopyOption.REPLACE_EXISTING);
			 * np.put("sourceFileUpload", newFilenameDB);
			 * 
			 * delegator.create(np);
			 * 
			 * 
			 * 
			 * // clone content of project proposal List<EntityCondition> conds
			 * = FastList.newInstance();
			 * conds.add(EntityCondition.makeCondition(
			 * "researchProjectProposalId"
			 * ,EntityOperator.EQUALS,researchProjectProposalId));
			 * 
			 * List<GenericValue> lstContents =
			 * delegator.findList("ResearchProposalContentItem",
			 * EntityCondition.makeCondition(conds), null,null,null,false);
			 * for(GenericValue c: lstContents){ GenericValue nc = delegator
			 * .makeValue("ResearchProposalContentItem"); String contentItemSeq
			 * = delegator .getNextSeqId("ResearchProposalContentItem");
			 * 
			 * nc.put("researchProjectProposalId",
			 * newResearchProjectProposalId); nc.put("contentItemSeq",
			 * contentItemSeq); nc.put("staffId", (String)c.get("staffId"));
			 * nc.put("content", (String)c.get("content"));
			 * nc.put("workingDays", (Long)c.getLong("workingdays"));
			 * nc.put("budget", (BigDecimal)c.getBigDecimal("budget"));
			 * 
			 * delegator.create(nc); }
			 * 
			 * // clone members of project proposal List<GenericValue>
			 * lstMembers = delegator.findList("ProjectProposalMember",
			 * EntityCondition.makeCondition(conds), null,null,null,false);
			 * for(GenericValue m: lstMembers){ GenericValue nm =
			 * delegator.makeValue("ProjectProposalMember"); String
			 * projectProposalMemberId =
			 * delegator.getNextSeqId("ProjectProposalMember");
			 * 
			 * nm.put("projectProposalMemberId", projectProposalMemberId);
			 * nm.put("researchProjectProposalId",
			 * newResearchProjectProposalId); nm.put("staffId",
			 * (String)m.get("staffId")); nm.put("roleTypeId",
			 * (String)m.get("roleTypeId")); nm.put("fromDate",
			 * (java.sql.Date)m.get("fromDate")); nm.put("thruDate",
			 * (java.sql.Date)m.get("thruDate"));
			 * 
			 * delegator.create(nm); }
			 * 
			 * // clone products of project proposal List<GenericValue>
			 * lstProducts = delegator.findList("ResearchProposalProduct",
			 * EntityCondition.makeCondition(conds),null,null,null,false);
			 * for(GenericValue pr: lstProducts){ String researchProductId =
			 * delegator.getNextSeqId("ResearchProposalProduct"); GenericValue
			 * npr = delegator.makeValue("ResearchProposalProduct");
			 * 
			 * npr.put("researchProductId", researchProductId);
			 * npr.put("researchProjectProposalId",
			 * newResearchProjectProposalId); npr.put("researchProductTypeId",
			 * (String)pr.get("researchProductTypeId"));
			 * npr.put("researchProductName",
			 * (String)pr.get("researchProductName")); npr.put("quantity",
			 * (Long)pr.get("quantity"));
			 * 
			 * delegator.create(npr);
			 * 
			 * }
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static Map<String, Object> getProjectProposal(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = context
				.get("researchProjectProposalId") + "";
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");

		Debug.log(module + "::getProjectProposal, researchProjectProposalId = "
				+ researchProjectProposalId);

		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.EQUALS,
					researchProjectProposalId));
			conds.add(EntityCondition
					.makeCondition(
							"statusId",
							EntityOperator.NOT_EQUAL,
							ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CANCELLED));

			List<GenericValue> prj = delegator.findList(
					"ResearchProjectProposalView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			if (prj != null && prj.size() > 0) {
				GenericValue pp = prj.get(0);
				retSucc.put("projectproposal", pp);

				String projectCallId = (String) pp.getString("projectCallId");
				String projectCallStatusId = "";
				String projectCallStatusName = "";
				if (projectCallId != null && !projectCallId.equals("")) {
					GenericValue pc = delegator.findOne("ProjectCallView",
							UtilMisc.toMap("projectCallId", projectCallId),
							false);
					if (pc != null) {
						projectCallStatusId = (String) pc
								.getString("statusId");
						projectCallStatusName = (String) pc
								.getString("statusName");
						
					}
				}
				retSucc.put("projectCallStatusId", projectCallStatusId);
				retSucc.put("projectCallStatusName",
						projectCallStatusName);
				
				// get evaluation
				/*
				 * conds = FastList.newInstance();
				 * conds.add(EntityCondition.makeCondition
				 * ("researchProjectProposalId",
				 * EntityOperator.EQUALS,researchProjectProposalId));
				 * List<GenericValue> lstEval =
				 * delegator.findList("ReviewerResearchProposal",
				 * EntityCondition.makeCondition(conds), null,null,null,false);
				 */
				Map<String, Object> in = FastMap.newInstance();
				in.put("researchProjectProposalId", researchProjectProposalId);
				Map<String, Object> retEvaluation = dispatcher.runSync(
						"getListReviewsOfProjectProposal", in);
				List<GenericValue> lstEval = (List<GenericValue>) retEvaluation
						.get("reviewprojectproposals");

				if (lstEval != null && lstEval.size() > 0)
					retSucc.put("evaluation", "YES");
				else
					retSucc.put("evaluation", "NO");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> getProjectProposalsOfStaff(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String staffId = (String) context.get("staffId");
		Delegator delegator = ctx.getDelegator();

		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");

		if (staffId == null)
			staffId = (String) userLogin.get("userLoginId");

		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("createStaffId",
					EntityOperator.EQUALS, staffId));
			conds.add(EntityCondition
					.makeCondition(
							"statusId",
							EntityOperator.NOT_EQUAL,
							ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CANCELLED));

			List<GenericValue> prj = delegator.findList(
					"ResearchProjectProposalView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			prj = ProjectProposalSubmissionServiceUtil
					.filterActiveProjectProposal(prj);

			List<Map<String, Object>> retList = FastList.newInstance();
			Set<String> pID = FastSet.newInstance();
			for (GenericValue p : prj) {
				Map<String, Object> I = FastMap.newInstance();
				I.put("project", p);
				I.put("role", "DIRECTOR");
				retList.add(I);
				pID.add(p.getString("researchProjectProposalId"));
			}

			conds.clear();
			conds.add(EntityCondition.makeCondition("staffId",
					EntityOperator.EQUALS, staffId));
			// conds.add(EntityCondition.makeCondition("statusId",
			// EntityOperator.NOT_EQUAL,
			// ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CANCELLED));

			List<GenericValue> lst = delegator.findList(
					"ResearchProposalContentItem",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			Set<String> epID = FastSet.newInstance();// set of research proposal
														// id such that current
														// staffId is member
			for (GenericValue p : lst) {
				String proposalId = p.getString("researchProjectProposalId");
				if (!pID.contains(proposalId)) {
					epID.add(proposalId);
				}
			}
			conds.clear();
			conds.add(EntityCondition.makeCondition(
					"researchProjectProposalId", EntityOperator.IN, epID));
			conds.add(EntityCondition
					.makeCondition(
							"statusId",
							EntityOperator.NOT_EQUAL,
							ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CANCELLED));

			lst = delegator.findList("ResearchProjectProposalView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			lst = ProjectProposalSubmissionServiceUtil
					.filterActiveProjectProposal(lst);

			for (GenericValue p : lst) {
				Map<String, Object> I = FastMap.newInstance();
				I.put("project", p);
				I.put("role", "MEMBER");
				retList.add(I);
			}
			// retSucc.put("projectproposals", prj);
			retSucc.put("projectproposals", retList);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> getListFilteredProjectProposals(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		String projectCallId = (String) context.get("projectCallId");
		String facultyId = (String) context.get("facultyId");
		String projectProposalStatusId = (String) context
				.get("projectProposalStatusId");

		Debug.log(module + "::getListFilteredProjectProposals, facultyId = "
				+ facultyId + ", projectCallId = " + projectCallId
				+ ", projectProposalStatusId = " + projectProposalStatusId);
		try {
			List<GenericValue> prj = ProjectProposalSubmissionServiceUtil
					.getListFilteredProjectProposals(delegator, projectCallId,
							facultyId, projectProposalStatusId);

			List<Map<String, Object>> retList = FastList.newInstance();
			for (GenericValue p : prj) {
				String researchProjectProposalId = p
						.getString("researchProjectProposalId");
				BigDecimal totalBudget = p.getBigDecimal("totalBudget");
				// get evaluation of current project
				Map<String, Object> in = FastMap.newInstance();
				in.put("researchProjectProposalId", researchProjectProposalId);
				Map<String, Object> retEvaluation = dispatcher.runSync(
						"getListReviewsOfProjectProposal", in);
				List<GenericValue> eval = (List<GenericValue>) retEvaluation
						.get("reviewprojectproposals");
				Debug.log(module
						+ "::getListFilteredProjectProposals, getEvaluation for project "
						+ researchProjectProposalId + ", GOT list.sz = "
						+ eval.size());
				int count = eval.size();
				long total = 0;
				for (GenericValue e : eval) {
					long point = e.getLong("evaluationMotivation")
							+ e.getLong("evaluationInnovation")
							+ e.getLong("evaluationApplicability")
							+ e.getLong("evaluationResearchMethod")
							+ e.getLong("evaluationResearchContent")
							+ e.getLong("evaluationPaper")
							+ e.getLong("evaluationProduct")
							+ e.getLong("evaluationPatent")
							+ e.getLong("evaluationGraduateStudent")
							+ e.getLong("evaluationYoungResearcher")
							+ e.getLong("evaluationEducation")
							+ e.getLong("evaluationReasonableBudget");
					total += point;
					Debug.log(module
							+ "::getListFilteredProjectProposals, getEvaluation for project "
							+ researchProjectProposalId + ", GOT list.sz = "
							+ eval.size() + ", point = " + point + ", total = "
							+ total);
				}
				if (count > 0) {
					total = total;
				}

				// get content for computing total budget
				in.clear();
				in.put("researchProjectProposalId", researchProjectProposalId);
				Map<String, Object> retContent = dispatcher.runSync(
						"getProjectProposalContentItem", in);
				List<GenericValue> contentItems = (List<GenericValue>) retContent
						.get("projectProposalContentItems");
				BigDecimal budget = BigDecimal.ZERO;
				for (GenericValue ci : contentItems) {
					BigDecimal b = (BigDecimal) ci.get("budget");
					budget = budget.add(b);
				}

				Map<String, Object> item = FastMap.newInstance();
				item.put("researchProjectProposalId",
						p.get("researchProjectProposalId"));
				item.put("researchProjectProposalName",
						p.get("researchProjectProposalName"));
				item.put("researchProjectProposalCode",
						p.get("researchProjectProposalCode"));
				item.put("budget", budget);
				item.put("totalBudget", totalBudget);

				item.put("statusName", p.getString("statusName"));

				item.put("createStaffName", p.get("createStaffName"));
				item.put("createStaffId", p.get("createStaffId"));
				item.put("projectCallName", p.get("projectCallName"));
				item.put("facultyName", p.get("facultyName"));
				item.put("totalEvaluation", total);
				item.put("numberEvaluations", count);

				retList.add(item);
			}

			retSucc.put("projectproposals", retList);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getListProjectProposals(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		String juryId = (String) context.get("juryId");

		List<GenericValue> list = ProjectProposalSubmissionServiceUtil
				.getListProjectProposals(delegator, juryId);
		if (list != null) {
			retSucc.put("projectproposals", list);
		}

		/*
		 * String juryId = null; String projectCallId = null; String facultyId =
		 * null;
		 * 
		 * 
		 * try{ // get projectCall of the current jury if any
		 * if(context.get("juryId") != null){ juryId = context.get("juryId") +
		 * ""; GenericValue J = delegator.findOne("Jury",
		 * UtilMisc.toMap("juryId", juryId), false); if(J != null){
		 * projectCallId = (String)J.get("projectCallId"); facultyId =
		 * (String)J.get("facultyId"); } }
		 * 
		 * 
		 * List<EntityCondition> conds = FastList.newInstance();
		 * if(projectCallId != null)
		 * conds.add(EntityCondition.makeCondition("projectCallId",
		 * EntityOperator.EQUALS,projectCallId)); if(facultyId != null)
		 * conds.add(EntityCondition.makeCondition("facultyId",
		 * EntityOperator.EQUALS,facultyId));
		 * 
		 * conds.add(EntityCondition.makeCondition("statusId",
		 * EntityOperator.NOT_EQUAL
		 * ,ProjectProposalSubmissionServiceUtil.STATUS_CANCELLED));
		 * 
		 * List<GenericValue> prj =
		 * delegator.findList("ResearchProjectProposalView",
		 * EntityCondition.makeCondition(conds), null, null, null, false);
		 * 
		 * 
		 * retSucc.put("projectproposals", prj);
		 * 
		 * }catch(Exception ex){ ex.printStackTrace(); return
		 * ServiceUtil.returnError(ex.getMessage()); }
		 */
		return retSucc;

	}

	public static Map<String, Object> getListProjectProposalJuries(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");

		String staffId = (String) userLogin.get("userLoginId");

		try {
			List<EntityCondition> conds = FastList.newInstance();

			List<GenericValue> list = delegator.findList("JuryView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);

			retSucc.put("projectproposaljuries", list);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> getProjectProposalJury(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");

		String staffId = (String) userLogin.get("userLoginId");
		String juryId = (String) context.get("juryId");
		try {
			List<EntityCondition> conds = FastList.newInstance();
			conds.add(EntityCondition.makeCondition("juryId",
					EntityOperator.EQUALS, juryId));
			List<GenericValue> list = delegator.findList("JuryView",
					EntityCondition.makeCondition(conds), null, null, null,
					false);
			if (list != null && list.size() > 0)
				retSucc.put("projectproposaljury", list.get(0));

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static void getListProjectProposalsAssignedForReviewJSON(
			HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String juryId = request.getParameter("juryId");
		String staffId = request.getParameter("staffId");

		try {
			List<GenericValue> list = ProjectProposalSubmissionServiceUtil
					.getListProjectProposals(delegator, juryId);
			Debug.log(module
					+ "::storeReviewerProjectProposalsAssignmentJSON, staffId = "
					+ staffId + ", juryId = " + juryId + ", list = "
					+ list.size());

			List<GenericValue> assigned_list = ProjectProposalSubmissionServiceUtil
					.getListProjectProposalsAssignedForReview(delegator,
							staffId, juryId);
			HashSet<String> S = new HashSet<String>();
			for (GenericValue gv : assigned_list) {
				S.add((String) gv.get("researchProjectProposalId"));
			}

			String rs = "";
			if (list != null) {
				rs = "{\"proposals\":[";
				for (int i = 0; i < list.size(); i++) {
					GenericValue gv = list.get(i);
					rs += "{";
					rs += "\"id\":\"" + gv.get("researchProjectProposalId")
							+ "\"";
					rs += ",";
					rs += "\"name\":\"" + gv.get("researchProjectProposalName")
							+ "\"";
					rs += ",";
					if (S.contains(gv.get("researchProjectProposalId")))
						rs += "\"checked\":1";
					else
						rs += "\"checked\":0";
					rs += "}";
					if (i < list.size() - 1)
						rs += ",";
				}
				rs += "]}";
			} else {
				rs = "{\"proposals\":[]}";
			}

			// String rs = "{\"result\":\"OK\"}";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(rs);
			out.close();
			Debug.log(module
					+ "::getListProjectProposalsAssignedForReviewJSON, rs = "
					+ rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Map<String, Object> getListProjectProposalsAssignedForReview(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();

		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");

		String staffId = (String) context.get("staffId");
		if (staffId == null)
			staffId = (String) userLogin.get("userLoginId");

		String juryId = (String) context.get("juryId");
		List<GenericValue> list = ProjectProposalSubmissionServiceUtil
				.getListProjectProposalsAssignedForReview(delegator, staffId,
						juryId);
		if (list != null) {
			retSucc.put("projectproposals", list);
		}
		/*
		 * try{ List<EntityCondition> conds = FastList.newInstance();
		 * conds.add(EntityCondition.makeCondition("juryId",
		 * EntityOperator.EQUALS,juryId));
		 * conds.add(EntityCondition.makeCondition("staffId",
		 * EntityOperator.EQUALS,staffId));
		 * 
		 * List<GenericValue> list =
		 * delegator.findList("ReviewerResearchProposal",
		 * EntityCondition.makeCondition(conds), null, null, null, false);
		 * 
		 * 
		 * retSucc.put("projectproposals", list);
		 * 
		 * }catch(Exception ex){ ex.printStackTrace(); return
		 * ServiceUtil.returnError(ex.getMessage()); }
		 */
		return retSucc;

	}

	public static Map<String, Object> createProjectProposalContentItem(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		String researchProjectProposalId = (String) context
				.get("researchProjectProposalId");
		String content = (String) context.get("content");
		BigDecimal amount = (BigDecimal) context.get("budget");
		String sDays = (String) context.get("workingDays");

		List<Object> staffIds = (List<Object>) context.get("staffId[]");
		Delegator delegator = ctx.getDelegator();

		try {
			GenericValue gv = delegator
					.makeValue("ResearchProposalContentItem");
			gv.put("researchProjectProposalId", researchProjectProposalId);
			if (staffIds != null && staffIds.size() > 0)
				gv.put("staffId", (String) staffIds.get(0));
			gv.put("content", content);
			gv.put("workingDays", Long.valueOf(sDays));
			gv.put("budget", amount);

			delegator.store(gv);

			retSucc.put("projectProposalContentItem", gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	/*
	 * public static Map<String, Object> createAMemberOfProjectProposal(
	 * DispatchContext ctx, Map<String, ? extends Object> context) { Map<String,
	 * Object> retSucc = ServiceUtil.returnSuccess();
	 * 
	 * String researchProjectProposalId = (String) context
	 * .get("researchProjectProposalId"); List<Object> staffIds = (List<Object>)
	 * context.get("staffId[]"); List<Object> roleTypeIds = (List<Object>)
	 * context.get("roleTypeId[]"); Debug.log(module +
	 * "::createAMemberOfProjectProposal, researchProjectProposalId = " +
	 * researchProjectProposalId); Delegator delegator = ctx.getDelegator();
	 * 
	 * try { String staffName = ""; String roleTypeName = "";
	 * 
	 * GenericValue gv = delegator.makeValue("ProjectProposalRole");
	 * gv.put("researchProjectProposalId", researchProjectProposalId); if
	 * (staffIds != null && staffIds.size() > 0) { gv.put("staffId", (String)
	 * staffIds.get(0)); GenericValue st = delegator.findOne("Staff",
	 * UtilMisc.toMap("staffId", gv.get("staffId")), false); staffName =
	 * (String) st.get("staffName"); } if (roleTypeIds != null &&
	 * roleTypeIds.size() > 0) { gv.put("roleTypeId", (String)
	 * roleTypeIds.get(0)); GenericValue rt =
	 * delegator.findOne("ProjectProposalRoleType", UtilMisc.toMap("roleTypeId",
	 * gv.get("roleTypeId")), false); roleTypeName = (String)
	 * rt.get("roleTypeName"); }
	 * 
	 * Timestamp now = UtilDateTime.nowTimestamp();
	 * 
	 * gv.put("fromDate", now);
	 * 
	 * delegator.create(gv);
	 * 
	 * GenericValue rg = delegator.makeValue("ProjectProposalRoleView");
	 * 
	 * rg.put("researchProjectProposalId", researchProjectProposalId);
	 * rg.put("staffId", (String) gv.get("staffId")); rg.put("roleTypeId",
	 * (String) gv.get("roleTypeId")); rg.put("staffName", staffName);
	 * rg.put("roleTypeName", roleTypeName);
	 * 
	 * retSucc.put("members", rg); retSucc.put("message", "Successfully"); }
	 * catch (Exception ex) { ex.printStackTrace(); return
	 * ServiceUtil.returnError(ex.getMessage()); } return retSucc;
	 * 
	 * }
	 */

	public static Map<String, Object> createAMemberOfProjectProposal(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String researchProjectProposalId = (String) context
				.get("researchProjectProposalId");
		List<Object> staffIds = (List<Object>) context.get("staffId[]");
		List<Object> roleTypeIds = (List<Object>) context.get("roleTypeId[]");
		Debug.log(module
				+ "::createAMemberOfProjectProposal, researchProjectProposalId = "
				+ researchProjectProposalId);
		Delegator delegator = ctx.getDelegator();

		try {
			String staffName = "";
			String roleTypeName = "";

			GenericValue gv = delegator.makeValue("ProjectProposalMember");
			String projectProposalMemberId = delegator
					.getNextSeqId("ProjectProposalMember");

			gv.put("projectProposalMemberId", projectProposalMemberId);
			gv.put("researchProjectProposalId", researchProjectProposalId);
			if (staffIds != null && staffIds.size() > 0) {
				gv.put("staffId", (String) staffIds.get(0));
				GenericValue st = delegator.findOne("Staff",
						UtilMisc.toMap("staffId", gv.get("staffId")), false);
				staffName = (String) st.get("staffName");
			}
			if (roleTypeIds != null && roleTypeIds.size() > 0) {
				gv.put("roleTypeId", (String) roleTypeIds.get(0));
				GenericValue rt = delegator.findOne("ProjectProposalRoleType",
						UtilMisc.toMap("roleTypeId", gv.get("roleTypeId")),
						false);
				roleTypeName = (String) rt.get("roleTypeName");
			}
			/*
			 * Timestamp now = UtilDateTime.nowTimestamp(); java.sql.Date
			 * fromDate = new java.sql.Date(now.getTime()); gv.put("fromDate",
			 * fromDate);
			 */

			delegator.create(gv);

			GenericValue rg = delegator.makeValue("ProjectProposalMemberView");

			rg.put("researchProjectProposalId", researchProjectProposalId);
			rg.put("staffId", (String) gv.get("staffId"));
			rg.put("roleTypeId", (String) gv.get("roleTypeId"));
			rg.put("staffName", staffName);
			rg.put("roleTypeName", roleTypeName);

			retSucc.put("members", rg);
			retSucc.put("message", "Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> deleteMemberOfProjectProposal(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String projectProposalMemberId = (String) context
				.get("projectProposalMemberId");
		Delegator delegator = ctx.getDelegator();
		Debug.log(module
				+ "::deleteMemberOfProjectProposal, projectProposalMemberId = "
				+ projectProposalMemberId);
		try {
			GenericValue gv = delegator.findOne("ProjectProposalMember",
					UtilMisc.toMap("projectProposalMemberId",
							projectProposalMemberId), false);
			if (gv != null) {
				delegator.removeValue(gv);
				Debug.log(module
						+ "::deleteMemberOfProjectProposal, DELETED projectProposalMemberId "
						+ projectProposalMemberId);
			} else {
				Debug.log(module
						+ "::deleteMemberOfProjectProposal, cannot find projectProposalMemberId "
						+ projectProposalMemberId);
			}
			retSucc.put("members", gv);
			retSucc.put("message", "delete successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retSucc;
	}

	public static Map<String, Object> updateMemberOfProjectProposal(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String projectProposalMemberId = (String) context
				.get("projectProposalMemberId");
		List<Object> staffIds = (List<Object>) context.get("staffId[]");
		List<Object> roleTypeIds = (List<Object>) context.get("roleTypeId[]");
		Debug.log(module
				+ "::updateMemberOfProjectProposal, projectProposalMemberId = "
				+ projectProposalMemberId);
		Delegator delegator = ctx.getDelegator();

		try {
			String staffName = "";
			String roleTypeName = "";

			GenericValue gv = delegator.findOne("ProjectProposalMember",
					UtilMisc.toMap("projectProposalMemberId",
							projectProposalMemberId), false);
			if (gv != null) {
				if (staffIds != null && staffIds.size() > 0) {
					gv.put("staffId", (String) staffIds.get(0));
					GenericValue st = delegator
							.findOne("Staff", UtilMisc.toMap("staffId",
									gv.get("staffId")), false);
					staffName = (String) st.get("staffName");
				}
				if (roleTypeIds != null && roleTypeIds.size() > 0) {
					gv.put("roleTypeId", (String) roleTypeIds.get(0));
					GenericValue rt = delegator.findOne(
							"ProjectProposalRoleType",
							UtilMisc.toMap("roleTypeId", gv.get("roleTypeId")),
							false);
					roleTypeName = (String) rt.get("roleTypeName");
				}
				/*
				 * Timestamp now = UtilDateTime.nowTimestamp(); java.sql.Date
				 * fromDate = new java.sql.Date(now.getTime());
				 * gv.put("fromDate", fromDate);
				 */

				delegator.store(gv);
				Debug.log(module
						+ "::updateMemberOfProjectProposal, UPDATED projectProposalMemberId "
						+ projectProposalMemberId);
			} else {
				Debug.log(module
						+ "::updateMemberOfProjectProposal, cannot find projectProposalMemberId "
						+ projectProposalMemberId);
			}
			GenericValue rg = delegator.makeValue("ProjectProposalMemberView");
			rg.put("projectProposalMemberId", projectProposalMemberId);
			rg.put("researchProjectProposalId",
					gv.getString("researchProjectProposalId"));
			rg.put("staffId", (String) gv.get("staffId"));
			rg.put("roleTypeId", (String) gv.get("roleTypeId"));
			rg.put("staffName", staffName);
			rg.put("roleTypeName", roleTypeName);

			retSucc.put("members", rg);
			retSucc.put("message", "Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;

	}

	public static Map<String, Object> createAProjectProposalSubmission(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");

		String staffId = (String) userLogin.get("userLoginId");
		Delegator delegator = ctx.getDelegator();
		String researchProjectProposalName = (String) context
				.get("researchProjectProposalName");
		List<Object> listProjectCalls = (List<Object>) context
				.get("projectCallId[]");
		List<Object> listFaculties = (List<Object>) context.get("facultyId[]");
		String projectCallId = null;
		String facultyId = null;
		if (listProjectCalls != null && listProjectCalls.size() > 0) {
			projectCallId = (String) listProjectCalls.get(0);
		}
		if (listFaculties != null && listFaculties.size() > 0) {
			facultyId = (String) listFaculties.get(0);
		}
		try {
			/*
			 * String partyId = delegator.getNextSeqId("Party");
			 * 
			 * Map<String, Object> info = UtilMisc.toMap("partyId", partyId);
			 * GenericValue pty = delegator.makeValue("Party",info);
			 * //pty.put("partyId", partyId); delegator.create(pty);
			 * 
			 * GenericValue pps =
			 * delegator.makeValue("ResearchProjectProposal"); String
			 * researchProjectProposalId =
			 * delegator.getNextSeqId("ResearchProjectProposal");
			 * pps.put("researchProjectProposalId", researchProjectProposalId);
			 * pps.put("researchProjectProposalName",
			 * researchProjectProposalName); pps.put("createStaffId", staffId);
			 * pps.put("partyId", partyId); pps.put("statusId",
			 * ProjectProposalSubmissionServiceUtil.STATUS_CREATED);
			 * 
			 * if(listProjectCalls != null && listProjectCalls.size() > 0){
			 * pps.put("projectCallId", listProjectCalls.get(0)); //GenericValue
			 * pc = delegator.findOne("ProjectCall",
			 * UtilMisc.toMap("projectCallId", listProjectCalls.get(0)), false);
			 * } if(listFaculties != null && listFaculties.size() > 0){
			 * pps.put("facultyId", listFaculties.get(0)); }
			 * 
			 * 
			 * delegator.create(pps);
			 */

			GenericValue pps = ProjectProposalSubmissionServiceUtil
					.createAProjectProposalSubmission(delegator,
							researchProjectProposalName, "0", "", facultyId,
							projectCallId, staffId);

			retSucc.put("projectproposals", pps);
			retSucc.put("message", "Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> createAProjectCall(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		String projectCallName = (String) context.get("projectCallName");
		List<Object> projectCategoryIds = (List<Object>) context
				.get("projectCategoryId[]");
		String projectCategoryId = null;
		if (projectCategoryIds != null && projectCategoryIds.size() > 0)
			projectCategoryId = (String) projectCategoryIds.get(0);
		String year = (String) context.get("year");
		try {
			GenericValue pc = delegator.makeValue("ProjectCall");
			String projectCallId = delegator.getNextSeqId("ProjectCall");
			pc.put("projectCallId", projectCallId);
			pc.put("projectCallName", projectCallName);
			pc.put("projectCategoryId", projectCategoryId);
			pc.put("year", year);
			pc.put("statusId",
					ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_CREATED);
			delegator.create(pc);

			pc = delegator.findOne("ProjectCallView",
					UtilMisc.toMap("projectCallId", projectCallId), false);
			retSucc.put("projectCalls", pc);
			retSucc.put("message", "Successful");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> updateAProjectCall(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		String projectCallName = (String) context.get("projectCallName");
		String projectCallId = (String) context.get("projectCallId");

		List<Object> projectCategoryIds = (List<Object>) context
				.get("projectCategoryId[]");
		String projectCategoryId = null;
		if (projectCategoryIds != null && projectCategoryIds.size() > 0)
			projectCategoryId = (String) projectCategoryIds.get(0);
		String year = (String) context.get("year");
		Debug.log(module + "::updateAProjectCall, projectCall " + projectCallId);

		try {
			GenericValue pc = delegator.findOne("ProjectCall",
					UtilMisc.toMap("projectCallId", projectCallId), false);
			pc.put("projectCallName", projectCallName);
			pc.put("projectCategoryId", projectCategoryId);
			pc.put("year", year);
			// pc.put("statusId",
			// ProjectProposalSubmissionServiceUtil.STATUS_CREATED);
			delegator.store(pc);
			Debug.log(module
					+ "::updateAProjectCall, successfully projectCall "
					+ projectCallId);

			pc = delegator.findOne("ProjectCallView",
					UtilMisc.toMap("projectCallId", projectCallId), false);
			retSucc.put("projectCalls", pc);
			retSucc.put("message", "Successful");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> getAProjectCallFull(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();
		String projectCallId = (String) context.get("projectCallId");
		Debug.log(module + "::getAProjectCallFull, projectCallId = "
				+ projectCallId);
		try {
			GenericValue pc = delegator.findOne("ProjectCallView",
					UtilMisc.toMap("projectCallId", projectCallId), false);

			Map<String, Object> in = FastMap.newInstance();
			in.put("projectCallId", projectCallId);
			Map<String, Object> rs = dispatcher.runSync(
					"getListFilteredProjectProposals", in);
			long numberSubmissions = 0;
			List<GenericValue> lst = (List<GenericValue>) rs
					.get("projectproposals");
			if (lst != null)
				numberSubmissions = lst.size();

			retSucc.put("projectCall", pc);
			retSucc.put("numberSubmissions", numberSubmissions);

			Debug.log(module + "::getAProjectCallFull, projectCallId = "
					+ projectCallId + ", GOT projectCallId = "
					+ pc.getString("projectCallId"));

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> removeAProjectCall(DispatchContext ctx,
			Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Delegator delegator = ctx.getDelegator();
		String projectCallId = (String) context.get("projectCallId");
		Debug.log(module + "::removeAProjectCall, projectCallId = "
				+ projectCallId);
		try {
			GenericValue pc = delegator.findOne("ProjectCall",
					UtilMisc.toMap("projectCallId", projectCallId), false);
			if (pc != null) {
				pc.put("statusId",
						ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CALL_CANCELLED);
				delegator.store(pc);
				Debug.log(module
						+ "::removeAProjectCall, remove successfully projectCall "
						+ projectCallId);
			}
			retSucc.put("result", "Successful");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> updateAProjectProposalSubmission(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");

		String staffId = (String) userLogin.get("userLoginId");
		Delegator delegator = ctx.getDelegator();
		String researchProjectProposalId = (String) context
				.get("researchProjectProposalId");

		String researchProjectProposalName = (String) context
				.get("researchProjectProposalName");
		List<Object> listProjectCalls = (List<Object>) context
				.get("projectCallId[]");
		List<Object> listFaculties = (List<Object>) context.get("facultyId[]");

		try {
			GenericValue pps = delegator.findOne("ResearchProjectProposal",
					UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId), false);

			pps.put("researchProjectProposalName", researchProjectProposalName);

			if (listProjectCalls != null && listProjectCalls.size() > 0) {
				pps.put("projectCallId", listProjectCalls.get(0));
			}
			if (listFaculties != null && listFaculties.size() > 0) {
				pps.put("facultyId", listFaculties.get(0));
			}

			delegator.store(pps);

			GenericValue ppsv = delegator.findOne(
					"ResearchProjectProposalView", UtilMisc.toMap(
							"researchProjectProposalId",
							researchProjectProposalId), false);

			retSucc.put("projects", ppsv);

			retSucc.put("message", "Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		return retSucc;
	}

	public static Map<String, Object> deleteAProjectProposalSubmission(
			DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		Map<String, Object> userLogin = (Map<String, Object>) context
				.get("userLogin");

		String staffId = (String) userLogin.get("userLoginId");
		Delegator delegator = ctx.getDelegator();
		String researchProjectProposalId = (String) context
				.get("researchProjectProposalId");

		try {
			GenericValue pps = delegator.findOne("ResearchProjectProposal",
					UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId), false);
			pps.put("statusId",
					ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CANCELLED);

			delegator.store(pps);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getLocalizedMessage());
		}
		return retSucc;
	}

	/*
	 * public static String getExtension(String fn) { Debug.log(module +
	 * "::getExtension, fn = " + fn); String[] s = fn.split("\\.");
	 * Debug.log(module + "::getExtension, fn = " + fn + ", s.length = " +
	 * s.length); return s[s.length - 1]; }
	 */
	public static void uploadFileProposal(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> m = FastMap.newInstance();

		// ServletFileUpload fu = new ServletFileUpload(new
		// DiskFileItemFactory(10240, new File(new File("runtime"), "tmp")));
		// //Creation of servletfileupload
		Debug.log(module
				+ "::uploadFileProposal \n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - start\n\t");
		ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory()); // Creation
																					// of
																					// servletfileupload
		List lst = null;
		String result = "AttachementException";
		String file_name = "";
		String researchProjectProposalId = "";

		try {
			lst = fu.parseRequest(request);
		} catch (FileUploadException fup_ex) {
			Debug.log(module
					+ "::uploadFileProposal \n\n\t****************************************\n\tException of FileUploadException \n\t");
			fup_ex.printStackTrace();
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

		if (lst.size() == 0) // There is no item in lst
		{
			Debug.log(module
					+ "::uploadFileProposal\n\n\t****************************************\n\tLst count is 0 \n\t");
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

		FileItem file_item = null;
		FileItem selected_file_item = null;

		// Checking for form fields - Start
		for (int i = 0; i < lst.size(); i++) {
			file_item = (FileItem) lst.get(i);
			String fieldName = file_item.getFieldName();

			switch (fieldName) {
			case "file":
				selected_file_item = file_item;

				file_name = file_item.getName(); // Getting the file name
				Debug.log(module
						+ "::uploadFileProposal\n\n\t****************************************\n\tThe selected file item's file name is : "
						+ file_name + "\n\t");
				break;
			case "researchProjectProposalId":
				researchProjectProposalId = file_item.getString();
				Debug.log(module
						+ "::uploadFileProposal\n\n\t****************************************\n\n researchProjectProposalId id : "
						+ researchProjectProposalId + "\n\t");
				break;
			}

		}
		// Checking for form fields - End

		// Uploading the file content - Start
		if (selected_file_item == null) // If selected file item is null
		{
			Debug.log(module
					+ "::uploadFileProposal\n\n\t****************************************\n\tThe selected file item is null\n\t");
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

		byte[] file_bytes = selected_file_item.get();
		byte[] extract_bytes = new byte[file_bytes.length];

		for (int l = 0; l < file_bytes.length; l++)
			extract_bytes[l] = file_bytes[l];
		// ByteBuffer byteWrap=ByteBuffer.wrap(file_bytes);
		// byte[] extract_bytes;
		// byteWrap.get(extract_bytes);

		// System.out.println("\n\n\t****************************************\n\tExtract succeeded :content are : \n\t");

		// Creation & writing to the file in server - End

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Debug.log(module + "::uploadFile, researchProjectProposalId = "
				+ researchProjectProposalId);
		try {
			GenericValue gv = delegator.findOne("ResearchProjectProposal",
					false, UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId));
			String staffId = (String) gv.get("createStaffId");

			Debug.log(module + "::uploadFileProposal, filename = " + file_name
					+ ", staffId = " + staffId);
			String ext = ProjectProposalSubmissionServiceUtil
					.getExtension(file_name);
			java.util.Date currentDate = new java.util.Date();
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			String sCurrentDate = dateformatyyyyMMdd.format(currentDate);

			String filenameDB = sCurrentDate + "." + ext;
			String fullFileName = ProjectProposalSubmissionServiceUtil
					.establishFullFilename(staffId, filenameDB);

			Debug.log(module + "::uploadFileProposal, filename = " + file_name
					+ ", researchProjectProposalId = "
					+ researchProjectProposalId + ", extension = " + ext
					+ ", filenameDB = " + filenameDB + ", fullFileName = "
					+ fullFileName);

			FileOutputStream fout = new FileOutputStream(fullFileName);
			Debug.log(module
					+ "::uploadFileProposal\n\n\t****************************************\n\tAfter creating outputstream");
			fout.flush();
			fout.write(extract_bytes);
			fout.flush();
			fout.close();

			gv.put("sourceFileUpload", filenameDB);
			delegator.store(gv);

			Debug.log(module
					+ "::uploadFileProposal\n\n\t****************************************\n\tuploadFile(HttpServletRequest request,HttpServletResponse response) - end\n\t");
			m.put("result", "AttachementSuccess");
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);

		} catch (Exception ioe_ex) {
			Debug.log(module
					+ "::uploadFileProposal\n\n\t****************************************\n\tIOException occured on file writing");
			ioe_ex.printStackTrace();
			result = "AttachementException";
			m.put("result", result);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(m),
					response, 200);
			return;
		}

	}

	public static void deleteAProjectProposal(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			String researchProjectProposalId = request
					.getParameter("researchProjectProposalId");
			GenericValue p = delegator.findOne("ResearchProjectProposal",
					UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId), false);
			if (p != null) {
				p.put("statusId",
						ProjectProposalSubmissionServiceUtil.STATUS_PROJECT_CANCELLED);
				delegator.store(p);
			} else {
				Debug.log(module + "::deleteAProjectProposal, proposal "
						+ researchProjectProposalId + " NOT exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void downloadFileProposal(HttpServletRequest request,
			HttpServletResponse response) {
		// GenericDelegator delegator = (GenericDelegator)
		// DelegatorFactory.getDelegator("default");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String researchProjectProposalId = request
				.getParameter("researchProjectProposalId");

		// String filename = "HDSD.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			GenericValue gv = delegator.findOne("ResearchProjectProposal",
					false, UtilMisc.toMap("researchProjectProposalId",
							researchProjectProposalId));
			String staffId = (String) gv.get("createStaffId");
			String filenameDB = (String) gv.get("sourceFileUpload");
			String fullFileName = ProjectProposalSubmissionServiceUtil
					.establishFullFilename(staffId, filenameDB);

			Debug.log(module
					+ "::downloadFileProposal, researchProjectProposalId = "
					+ researchProjectProposalId + ", staffId = " + staffId
					+ ", filenameDB = " + filenameDB + ", fullFileName = "
					+ fullFileName);

			// File f = new File("C:/DungPQ/olbius/tmp/" + filename);
			File f = new File(fullFileName);
			FileInputStream fi = new FileInputStream(f);
			byte[] bytes = new byte[(int) f.length()];
			fi.read(bytes);

			response.setHeader("content-disposition", "attachment;filename="
					+ filenameDB);
			response.setContentType("application/vnd.xls");
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
