package lc.common.util;

import java.io.File;
import java.util.List;

import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lc.common.LcConstants;
import lc.common.excel.domain.JExcelInfo;

import org.springframework.util.FileCopyUtils;

public class JExcelUtil {

	public static byte[] excelDown(String fileName, List<JExcelInfo> listData) throws Exception{
		String rootPath = LcConstants.EXCEL_FILE_PATH;
		File xfile = new File(rootPath + File.separator + fileName + ".xls");
		
		if(xfile.exists()){
			File outFile = new File(rootPath + File.separator + fileName + "_" + DateUtil.getCurrentDateTime() + ".xls");
			
		    Workbook workbook = Workbook.getWorkbook(xfile);
		    //WritableWorkbook copy = Workbook.createWorkbook(res.getOutputStream(), workbook);
		    WritableWorkbook copy = Workbook.createWorkbook(outFile, workbook);
		    WritableSheet sheet = copy.getSheet(0);
	
			for(int i=0;i<sheet.getRows();i++){
				Cell[] cells = sheet.getRow(i);
				if (cells.length>0){
					
				}
			}
			
			if(sheet.getRows() > 0){
			    for(JExcelInfo info : listData){
			    	//Label label = new Label(info.getColIdx(), info.getRowIdx(), info.getContent(), new WritableCellFormat(new WritableFont(WritableFont.ARIAL, sheet.getRow(info.getRowIdx())[info.getColIdx()].getCellFormat().getFont().getPointSize(), WritableFont.NO_BOLD)));
			    	Label label = new Label(info.getColIdx(), info.getRowIdx(), info.getContent(), new WritableCellFormat());
			    	sheet.addCell(label);
			    }
			}
			
		    copy.write();
		    copy.close();
		    
		    byte[] fileByte = FileCopyUtils.copyToByteArray(outFile);
		    outFile.delete();
		    
		    return fileByte;
		}
		return null;
	}

	public static File excelDownFile(String fileName, List<JExcelInfo> listData) throws Exception{
		String rootPath = LcConstants.EXCEL_FILE_PATH;
		File xfile = new File(rootPath + File.separator + fileName + ".xls");
		
		if(xfile.exists()){
			File outFile = new File(rootPath + File.separator + fileName + "_" + DateUtil.getCurrentDateTime() + ".xls");
			
		    Workbook workbook = Workbook.getWorkbook(xfile);
		    //WritableWorkbook copy = Workbook.createWorkbook(res.getOutputStream(), workbook);
		    WritableWorkbook copy = Workbook.createWorkbook(outFile, workbook);
		    WritableSheet sheet = copy.getSheet(0);
	
			for(int i=0;i<sheet.getRows();i++){
				Cell[] cells = sheet.getRow(i);
				if (cells.length>0){
					
				}
			}
			
			if(sheet.getRows() > 0){
			    for(JExcelInfo info : listData){
			    	Label label = new Label(info.getColIdx(), info.getRowIdx(), info.getContent(), new WritableCellFormat(new WritableFont(WritableFont.ARIAL, sheet.getRow(info.getRowIdx())[info.getColIdx()].getCellFormat().getFont().getPointSize(), WritableFont.NO_BOLD)));
			    	sheet.addCell(label);
			    }
			}
			
		    copy.write();
		    copy.close();
		    
		    
		    return outFile;
		}
		return null;
	}	
}
