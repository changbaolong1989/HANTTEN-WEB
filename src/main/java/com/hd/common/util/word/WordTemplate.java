package com.hd.common.util.word;

import com.google.gson.Gson;
import com.hd.common.util.*;
import com.hd.common.util.word.XWPFHandler.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 仅支持对docx文件的文本及表格中的内容进行替换
 * 模板仅支持 ${key} 标签
 * @author JianQiu
 *
 */
public class WordTemplate{
	
		private XWPFDocument document;
	
	/**
	 * 初始化模板内容
	 * @param inputStream 模板的读取流(docx文件)
	 * @throws IOException
	 */
	public WordTemplate(InputStream inputStream) throws IOException{
		document = new XWPFDocument(inputStream);
	}
	
	/**
	 * 替换模板中的标签为实际的内容
	 * @param map 
	 */
	public void replaceTag(Map<String,Object> map) throws IOException, InvalidFormatException {
		// 替换段落中的内容
		replaceParagraphs(map);
		// 替换表格中的内容
		replaceTables(map);

		// 合同模板类型
		String tempFormworkTypeStr = (String) map.get("formworkType");

		// 如果合同模板为造价咨询合同 需要绘制表格
		if ("0".equals(tempFormworkTypeStr)){

			// 绘制表格 合同
			drawContractWordTables(map);
		}

		// 任务成果文件模板类型
		String tempTaskFormworkTypeStr = (String) map.get("taskFormworkType");

		// 如果合同模板为造价咨询合同 需要绘制表格
		if ("4".equals(tempTaskFormworkTypeStr)){

			// 绘制表格 任务成果文件
			drawTaskWordTables(map);
		}

		// 如果合同模板为招标代理工作报告 需要绘制表格
		if ("18".equals(tempTaskFormworkTypeStr)){

			// 绘制表格 招标代理工作报告
			drawBidEvaluationScoreTables(map);
		}

		// 如果合同模板为评标书面报告 需要绘制表格
		if ("19".equals(tempTaskFormworkTypeStr)){

			// 绘制表格 评标书面报告文件
			drawCandidateTables(map);
		}

	}
	
	/**
	 * 将处理后的内容写入到输出流中
	 * @param outputStream 输出流
	 * @throws IOException
	 */
	public void write(OutputStream outputStream) throws IOException{
		document.write(outputStream);
	}
	
	/**
	 * 替换文本中的标签
	 * @param map key(待替换标签)-value(文本内容)
	 */
	private void replaceParagraphs(Map<String,Object> map){
		List<XWPFParagraph> allXWPFParagraphs = new ArrayList<XWPFParagraph>(document.getParagraphs());
		List<XWPFHeader> headerList = document.getHeaderList();
		for (XWPFHeader xwpfHeader: headerList){
			List<XWPFParagraph> paras = new ArrayList<XWPFParagraph>(xwpfHeader.getParagraphs());
			allXWPFParagraphs.addAll(paras);
		}
		List<XWPFFooter> footerList = document.getFooterList();
		for (XWPFFooter xwpfFooter: footerList){
			List<XWPFParagraph> paras = new ArrayList<XWPFParagraph>(xwpfFooter.getParagraphs());
			allXWPFParagraphs.addAll(paras);

		}
		for (XWPFParagraph XwpfParagrapg : allXWPFParagraphs) {
			XWPFParagraphHandler XwpfParagrapgUtils = new XWPFParagraphHandler(XwpfParagrapg);
			XwpfParagrapgUtils.replaceAll(map);
		}
	}
	
	/**
	 * 替换表格中的标签 
	 * @param map key(待替换标签)-value(文本内容)
	 */
	private void replaceTables(Map<String,Object> map) throws IOException, InvalidFormatException {
		List<XWPFTable> xwpfTables = document.getTables();
		for(XWPFTable xwpfTable : xwpfTables){
			XWPFTableHandler xwpfTableUtils = new XWPFTableHandler(xwpfTable);
			xwpfTableUtils.replace(map);
		}


	}

	/**
	 * 动态绘制
	 * @param map key(待替换标签)-value(文本内容)
	 */
	private void drawContractWordTables(Map<String,Object> map) throws IOException, InvalidFormatException {
		List<XWPFTable> xwpfTables = document.getTables();

		// 遍历word 模板中的所有表格
		for (int i=0;i<xwpfTables.size();i++){
			// 循环到的当前表格
			XWPFTable tempThisTable = xwpfTables.get(i);

			// 第6个表格 -附录C　委托人提供资料一览表
			if (i==5){


				// 取得 委托人提供资料数据集合
				List<ServiceRangeModel> tempList = (List<ServiceRangeModel>) map.get("provideDataList");

				List<ServiceRangeModel> provideDataList = null;

				// 取得 委托人提供资料数据集合
				if (!ListToolUtils.isEmpty(tempList)){
					provideDataList = InterfaceUtils
							.jsonToObjectList(new Gson().toJson(tempList),ServiceRangeModel.class);

				}

				// 如果集合不为空 则删除表头之外的其他单元格 根据集合重新绘制表格
				if (provideDataList != null && provideDataList.size() > 0){

					// 获取当前表格所有行
					List<XWPFTableRow> rows = tempThisTable.getRows();
					// 删除表格除了表头以外的表格
					for (int j = rows.size() - 1; j > 0; j--) {
						xwpfTables.get(i).removeRow(j);
					}

					// 根据集合数量创建行
					for (int k = 1; k <= provideDataList.size(); k++) {
						tempThisTable.createRow();
					}

					ServiceRangeModel tempServiceRangeModel = null;
					// 表格行集合
					List<XWPFTableRow> tempRowList = tempThisTable.getRows();
					// 遍历行 赋值
					for (int m = 1; m < tempRowList.size(); m++) {

						tempServiceRangeModel = provideDataList.get(m - 1);
						// 当前行
						XWPFTableRow newRow = tempThisTable.getRow(m);

						// 当前行所有列集合
						List<XWPFTableCell> cells = newRow.getTableCells();

						// 第一列 委托人名称
						cells.get(0).setText(tempServiceRangeModel.getTrusterName());

						// 第二列 资料份数
						cells.get(1).setText(tempServiceRangeModel.getDataCount());

						// 第三列 提供时间
						cells.get(2).setText(tempServiceRangeModel.getProvideDate());

						// 第四列 备注
						cells.get(3).setText(tempServiceRangeModel.getRemark());

					}

				}


			}
			// 第5个表格 -附录B 咨询人提交成果文件一览表
			if (i==4){

				// 取得 咨询人提交成果文件一览表 数据集合
				List<ServiceRangeModel> tempList = (List<ServiceRangeModel>) map.get("achievementFileList");

				List<ServiceRangeModel> achievementFileList = null;

				if (!ListToolUtils.isEmpty(tempList)){

					// 取得 咨询人提交成果文件一览表 数据集合
					achievementFileList = InterfaceUtils
							.jsonToObjectList(new Gson().toJson(tempList),ServiceRangeModel.class);
				}


				// 如果集合不为空 则删除表头之外的其他单元格 根据集合重新绘制表格
				if (achievementFileList != null && achievementFileList.size() > 0){
					// 获取当前表格所有行
					List<XWPFTableRow> rows = tempThisTable.getRows();
					// 删除表格除了表头以外的表格
					for (int j = rows.size() - 1; j > 0; j--) {
						xwpfTables.get(i).removeRow(j);
					}

					// 按照服务阶段排序
					Collections.sort(achievementFileList);

					// 根据集合数量创建行
					for (int k = 1; k <= achievementFileList.size(); k++) {
						tempThisTable.createRow();
					}

					ServiceRangeModel tempServiceRangeModel = null;

					// 表格行集合
					List<XWPFTableRow> tempRowList = tempThisTable.getRows();
					// 遍历行
					for (int m = 1; m < tempRowList.size(); m++) {

						tempServiceRangeModel = achievementFileList.get(m - 1);
						// 取得当前行
						XWPFTableRow newRow = tempThisTable.getRow(m);
						// 取得当前行所有列集合
						List<XWPFTableCell> cellList = newRow.getTableCells();

						// 第一列 服务阶段
						cellList.get(0).setText(tempServiceRangeModel.getServicePhase());

						// 设置第一列样式
						CTTc cttc = cellList.get(0).getCTTc();
						// 垂直居中
						cttc.addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
						// 水平居中
						//cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);

						// 第二列 成果文件名称
						cellList.get(1).setText(tempServiceRangeModel.getAchievementFileName());

						// 第三列 成果文件组成
						cellList.get(2).setText(tempServiceRangeModel.getAchievementFileCompose());

						// 第四列 提交时间
						cellList.get(3).setText(tempServiceRangeModel.getSubmitDate());

						// 第五列 份数
						cellList.get(4).setText(tempServiceRangeModel.getFileCount());

						// 第六列 质量标准
						cellList.get(5).setText(tempServiceRangeModel.getQualityStandard());

					}

					// 第一列的相同单元格合并
					// 开始行 默认从第二行查（不需要查表头）
					int fromIndexInt = 1;
					// 结束行 默认也是第二行
					int toIndexInt = 1;
					// 上一次的内容
					String tempLastText = null;

					// 遍历成果文件表格集合
					for (int m = 1; m <= achievementFileList.size(); m++) {
						// 当前表格的服务阶段id
						String tempText = achievementFileList.get(m-1).getServicePhaseId();

						// 如果是第一次循环 直接将本次的id 赋值上一次 开始下次循环
						if (m==1){
							tempLastText = tempText;

						}else {
							// 如果当前行内容跟上一次内容相同
							if (tempLastText.equals(tempText)){
								// 结束行 = 当前行
								toIndexInt = m;

								// 如果是最后一次循环 则合并单元格
								if (m==achievementFileList.size()){
									mergeCellsVertically(tempThisTable,0,fromIndexInt,toIndexInt);
								}


							}else {
								// 如果当前行内容跟上一次内容不相同
								// 则合并之前的单元格
								mergeCellsVertically(tempThisTable,0,fromIndexInt,toIndexInt);
								// 将本次的id 赋值上一次
								tempLastText = tempText;
								// 将当前行赋值给开始行
								fromIndexInt = m;
								// 将当前行赋值给结束行
								toIndexInt = m;
							}

						}

					}

				}


			}
			// 第4个表格 附录A 服务范围及工作内容、酬金一览表
			if (i == 3){

				// 取得 服务范围及工作内容 数据集合
				List<ServiceRangeModel> tempList = (List<ServiceRangeModel>) map.get("serviceAndWorkContentList");

				List<ServiceRangeModel> tempServiceRangeList = null;

				if (!ListToolUtils.isEmpty(tempList)){
					// 取得 服务范围及工作内容 数据集合
					tempServiceRangeList = InterfaceUtils
							.jsonToObjectList(new Gson().toJson(tempList),ServiceRangeModel.class);
				}

				// 如果集合不为空 则删除表头之外的其他单元格 根据集合重新绘制表格
				if (tempServiceRangeList != null && tempServiceRangeList.size() > 0){

					// 删除word模板中除了表头以外的表格
					List<XWPFTableRow> rows = tempThisTable.getRows();
					for (int j = rows.size() - 1; j > 1; j--) {
						xwpfTables.get(i).removeRow(j);
					}
					// 将集合按服务阶段排序 之后合并单元格
					Collections.sort(tempServiceRangeList);

					// 根据集合数量创建行
					for (int k = 1; k <= tempServiceRangeList.size(); k++) {
						tempThisTable.createRow();
					}

					ServiceRangeModel tempServiceRangeModel = null;
					// 表格行集合
					List<XWPFTableRow> tempRowList = tempThisTable.getRows();
					// 遍历行
					for (int m = 2; m < tempRowList.size(); m++) {

						tempServiceRangeModel = tempServiceRangeList.get(m - 2);

						// 取得当前行
						XWPFTableRow newRow = tempThisTable.getRow(m);
						// 取得当前行所有列集合
						List<XWPFTableCell> cells = newRow.getTableCells();
						// 第一列 服务阶段
						cells.get(0).setText(tempServiceRangeModel.getServicePhase());

						// 第二列 服务范围
						cells.get(1).setText(tempServiceRangeModel.getServiceRange());

						// 第三列
						// 判断显示文字还是checkBox
						String tempFlg = tempServiceRangeModel.getWorkContentFlg();

						// 如果显示checkBox  则根据段落插入checkBox图片 TODO
						if ("0".equals(tempFlg)){
							// 获取当前行第三列
							XWPFTableCell imageCell = newRow.getCell(2);
							// 取得该行段落标记集合
							List<XWPFParagraph> paragraphs = imageCell.getParagraphs();
							// 取得第一个段落
							XWPFParagraph newPara = paragraphs.get(0);
							// 创建一个文本区域
							XWPFRun imageCellRunn = newPara.createRun();

							// 遍历工作内容checkBox集合
							for (ServiceRangeModel tempSrviceRangeModel : tempServiceRangeModel.getWorkContentList()){
								// checkBox图片名称默认未选中
								String tempFileNameStr = "unChecked.png";

								// 如果是已选中 切换图片
								if ("1".equals(tempSrviceRangeModel.getIsCheckedFlg())){

									tempFileNameStr = "checked.png";
								}
								imageCellRunn.addPicture(new ClassPathResource("contract/" + tempFileNameStr).getInputStream(), XWPFDocument.PICTURE_TYPE_PNG, tempFileNameStr, Units.toEMU(10), Units.toEMU(10));
								// 工作内容类型数字
								int tempWorkContentTypeInt = NumberToolUtils.convertNullToInteger(tempSrviceRangeModel.getWorkContentType());

								// 工作内容类型转换文字
								String tempWorkContentTypeValueStr = BusinessUtils.convertWorkContentTypeToStateStr(tempWorkContentTypeInt);

								imageCellRunn.setText(tempWorkContentTypeValueStr);
							}

						}else {
							// 直接将工作内容放进单元格中
							cells.get(2).setText(tempServiceRangeModel.getWorkContent());
						}

						// 收费基数
						String tempChargeRadixStr = NumberToolUtils
								.convertNumberToFormatStringWithHalfUp(tempServiceRangeModel.getChargeRadix(),NumberToolUtils.INT_SCALE_FLOAT);

						// 收费基数是否暂定
						String tempIsChargeRadixProvisionalStr = tempServiceRangeModel.getIsChargeRadixProvisional();

						// 如果收费基数是暂定 则在前面加上“暂定”
						if ("1".equals(tempIsChargeRadixProvisionalStr)){

							tempChargeRadixStr = Constant.CONTRACT_TEMPLATE_PROVISIONAL_STR + tempChargeRadixStr;
						}
						// 第四列 收费基数
						cells.get(3).setText(tempChargeRadixStr);

						// 收费标准
						String tempChargeStandardStr = tempServiceRangeModel.getChargeStandard() + "%";

						// 收费标准是否暂定
						String tempIsChargeStandardProvisionalStr = tempServiceRangeModel.getIsChargeStandardProvisional();

						// 如果收费标准是暂定 则在前面加上“暂定”
						if ("1".equals(tempIsChargeStandardProvisionalStr)){

							tempChargeStandardStr = Constant.CONTRACT_TEMPLATE_PROVISIONAL_STR + tempChargeStandardStr;
						}

						// 第五列 收费标准
						cells.get(4).setText(tempChargeStandardStr);

						// 酬金数额
						String tempRemunerationStr = NumberToolUtils
								.convertNumberToFormatStringWithHalfUp(tempServiceRangeModel.getRemuneration(),NumberToolUtils.INT_SCALE_FLOAT);

						// 酬金数额是否暂定
						String tempIsRemunerationProvisionalStr = tempServiceRangeModel.getIsRemunerationProvisional();

						// 如果酬金数额是暂定 则在前面加上“暂定”
						if ("1".equals(tempIsRemunerationProvisionalStr)){

							tempRemunerationStr = Constant.CONTRACT_TEMPLATE_PROVISIONAL_STR + tempRemunerationStr;
						}

						// 第六列 酬金数额
						cells.get(5).setText(tempRemunerationStr);

						// 第七列 备注
						cells.get(6).setText(tempServiceRangeModel.getRemark());

						// 将单元内文字都居中
						CTTc cttc = null;
						for (int k = 0;k < 7;k++){
							// 居中
							cttc = cells.get(k).getCTTc();
							// 垂直居中
							cttc.addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
							// 水平居中
							//cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
						}

					}

					// 第一列的相同单元格合并
					// 开始行 默认从第三行查（不需要查表头）
					int fromIndexInt = 2;
					// 结束行 默认也是第三行
					int toIndexInt = 2;
					// 上一次的内容
					String tempLastText = null;

					// 遍历服务范围表格集合
					for (int m = 1; m <= tempServiceRangeList.size(); m++) {
						// 当前表格的服务阶段id
						String tempText = tempServiceRangeList.get(m-1).getServicePhaseId();

						// 如果是第一次循环 直接将本次的id 赋值上一次 开始下次循环
						if (m==1){
							tempLastText = tempText;

						}else {
							// 如果当前行内容跟上一次内容相同
							if (tempLastText.equals(tempText)){

								// 结束行 + 1
								toIndexInt = m + 1;

								// 如果是最后一次循环 则合并单元格
								if (m==tempServiceRangeList.size()){
									mergeCellsVertically(tempThisTable,0,fromIndexInt,toIndexInt);
								}

							}else {
								// 如果当前行内容跟上一次内容不相同
								// 则合并之前的单元格
								mergeCellsVertically(tempThisTable,0,fromIndexInt,toIndexInt);
								// 将本次的id 赋值上一次
								tempLastText = tempText;
								// 开始行 + 1
								fromIndexInt = m + 1;
								// 结算行 + 1
								toIndexInt = m + 1;
							}
						}
					}
				}
				// 将表头列合并
				mergeCellsHorizontal(tempThisTable,0,1,2);
				mergeCellsHorizontal(tempThisTable,0,3,5);

			}

		}

	}

	/**
	 * 动态绘制 成果文件模板
	 * @param map key(待替换标签)-value(文本内容)
	 */
	private void drawTaskWordTables(Map<String,Object> map) throws IOException, InvalidFormatException {
		List<XWPFTable> xwpfTables = document.getTables();

		// 遍历word 模板中的所有表格
		for (int i=0;i<xwpfTables.size();i++){
			// 循环到的当前表格
			XWPFTable tempThisTable = xwpfTables.get(i);

			// 第一个表格 -中标单位投标函分析
			if (i==0){

				// 取得 标单位投标函分析数据集合
				List<TenderLetterModel> tempList = (List<TenderLetterModel>) map.get("tenderList");

				List<TenderLetterModel> tenderList = null;

				// 取得 委托人提供资料数据集合
				if (!ListToolUtils.isEmpty(tempList)){
					tenderList = InterfaceUtils
							.jsonToObjectList(new Gson().toJson(tempList),TenderLetterModel.class);

				}

				// 如果集合不为空 则删除表头之外的其他单元格 根据集合重新绘制表格
				if (tenderList != null && tenderList.size() > 0){

					// 获取当前表格所有行
					List<XWPFTableRow> rows = tempThisTable.getRows();
					// 删除表格除了表头以外的表格
					for (int j = rows.size() - 1; j > 0; j--) {
						xwpfTables.get(i).removeRow(j);
					}

					// 根据集合数量创建行
					for (int k = 1; k <= tenderList.size(); k++) {
						tempThisTable.createRow();
					}

					TenderLetterModel tempTenderLetterModel = null;
					// 表格行集合
					List<XWPFTableRow> tempRowList = tempThisTable.getRows();
					// 遍历行 赋值
					for (int m = 1; m < tempRowList.size(); m++) {

						tempTenderLetterModel = tenderList.get(m - 1);
						// 当前行
						XWPFTableRow newRow = tempThisTable.getRow(m);

						// 当前行所有列集合
						List<XWPFTableCell> cells = newRow.getTableCells();

						// 第一列 序号
						cells.get(0).setText(StringToolUtils.convertNullObjectToString(m));

						// 第二列 投标单位
						cells.get(1).setText(tempTenderLetterModel.getInput10());

						// 第三列 中标价
						cells.get(2).setText(NumberToolUtils
								.convertNumberToFormatStringWithHalfUp(tempTenderLetterModel.getInput11(),NumberToolUtils.INT_SCALE_FLOAT));

						// 第四列 其中暂列金额及专业工程分包
						cells.get(3).setText(tempTenderLetterModel.getInput12());

					}

				}


			}


		}

	}

	/**
	 * 动态绘制 招标代理工作报告模板
	 * @param map key(待替换标签)-value(文本内容)
	 */
	private void drawBidEvaluationScoreTables(Map<String,Object> map) throws IOException, InvalidFormatException {
		List<XWPFTable> xwpfTables = document.getTables();

		// 遍历word 模板中的所有表格
		for (int i=0;i<xwpfTables.size();i++){
			// 循环到的当前表格
			XWPFTable tempThisTable = xwpfTables.get(i);

			// 第二个表格
			if (i==0){

				// 取得 投标人数据集合
				List<BidderModel> bidderList = (List<BidderModel>) map.get("bidderList");

				List<BidderModel> tempList = null;

				// 取得 提供资料数据集合
				if (!ListToolUtils.isEmpty(bidderList)){
					tempList = InterfaceUtils
							.jsonToObjectList(new Gson().toJson(bidderList),BidderModel.class);

				}

				// 如果集合不为空 则删除表头之外的其他单元格 根据集合重新绘制表格
				if (tempList != null && tempList.size() > 0){

					// 获取当前表格所有行
					List<XWPFTableRow> rows = tempThisTable.getRows();
					// 删除表格除了表头以外的表格
					for (int j = rows.size() - 1; j > 0; j--) {
						xwpfTables.get(i).removeRow(j);
					}

					// 根据集合数量创建行
					for (int k = 1; k <= tempList.size(); k++) {
						tempThisTable.createRow();
					}

					BidderModel tempBidderModel = null;
					// 表格行集合
					List<XWPFTableRow> tempRowList = tempThisTable.getRows();
					// 遍历行 赋值
					for (int m = 1; m < tempRowList.size(); m++) {

						tempBidderModel = tempList.get(m - 1);
						// 当前行
						XWPFTableRow newRow = tempThisTable.getRow(m);
						// 设置每行的高度
						newRow.setHeight(500);

						// 当前行所有列集合
						List<XWPFTableCell> cells = newRow.getTableCells();

						// 第一列 排名
						cells.get(0).setText(tempBidderModel.getRankingOrder());

						// 第二列 投标人名称
						cells.get(1).setText(tempBidderModel.getBidderName());

						// 第三列 评标得分
						cells.get(2).setText(NumberToolUtils
								.convertNumberToFormatStringWithHalfUp(tempBidderModel.getBidEvaluation(),NumberToolUtils.INT_SCALE_FLOAT));

					}

				}

			}

		}

	}

	/**
	 * 动态绘制 评标书面报告模板
	 * @param map key(待替换标签)-value(文本内容)
	 */
	private void drawCandidateTables(Map<String,Object> map) throws IOException, InvalidFormatException {
		List<XWPFTable> xwpfTables = document.getTables();

		// 遍历word 模板中的所有表格
		for (int i=1;i<xwpfTables.size();i++){
			// 循环到的当前表格
			XWPFTable tempThisTable = xwpfTables.get(i);

			// 第二个表格
			if (i==1){

				// 取得 投标人数据集合
				List<BidderModel> bidderList = (List<BidderModel>) map.get("bidderList");

				List<BidderModel> tempList = null;

				// 取得 提供资料数据集合
				if (!ListToolUtils.isEmpty(bidderList)){
					tempList = InterfaceUtils
							.jsonToObjectList(new Gson().toJson(bidderList),BidderModel.class);

				}

				// 如果集合不为空 则删除表头之外的其他单元格 根据集合重新绘制表格
				if (tempList != null && tempList.size() > 0){

					// 获取当前表格所有行
					List<XWPFTableRow> rows = tempThisTable.getRows();
					// 删除表格除了表头以外的表格
					for (int j = rows.size() - 1; j > 0; j--) {
						xwpfTables.get(i).removeRow(j);
					}

					// 根据集合数量创建行
					for (int k = 1; k <= tempList.size(); k++) {
						tempThisTable.createRow();
					}

					BidderModel tempBidderModel = null;
					// 表格行集合
					List<XWPFTableRow> tempRowList = tempThisTable.getRows();
					// 遍历行 赋值
					for (int m = 1; m < tempRowList.size(); m++) {

						tempBidderModel = tempList.get(m - 1);
						// 当前行
						XWPFTableRow newRow = tempThisTable.getRow(m);
						// 设置每行的高度
						newRow.setHeight(500);

						// 当前行所有列集合
						List<XWPFTableCell> cells = newRow.getTableCells();

						// 第一列 投标人名称
						cells.get(0).setText(tempBidderModel.getBidderName());

//						cells.get(0).getCTTc().addNewTcPr()

						// 第二列 排名次序
						cells.get(1).setText(tempBidderModel.getRankingOrder());

						// 第三列 评标得分
						cells.get(2).setText(NumberToolUtils
								.convertNumberToFormatStringWithHalfUp(tempBidderModel.getBidEvaluation(),NumberToolUtils.INT_SCALE_FLOAT));

					}

				}

			}

		}

	}

	/**
	 *  合并行
	 * @param table 表格
	 * @param col 列数
	 * @param fromRow 开始行
	 * @param toRow 结束行
	 */
	private  void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
		for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
			XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
			if ( rowIndex == fromRow ) {
				// The first merged cell is set with RESTART merge value
				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

	/**
	 * 合并列
	 * @param table 表格
	 * @param row 行数
	 * @param fromCell 开始列
	 * @param toCell 结束列
	 */
	private  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
		for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
			XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
			if ( cellIndex == fromCell ) {
				// The first merged cell is set with RESTART merge value
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

}
