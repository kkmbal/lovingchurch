package lc.common.util;

import java.io.File;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lc.common.LcConstants;
import lc.common.excel.domain.JExcelExportInfo;
import lc.common.excel.domain.JExcelInfo;
import lc.common.excel.domain.JExcelListInfo;

import org.springframework.util.FileCopyUtils;

public class JExcelUtil {

	public static byte[] excelDown(String fileName, JExcelExportInfo listData) throws Exception{
		String rootPath = LcConstants.EXCEL_FILE_PATH;
		File xfile = new File(rootPath + File.separator + fileName + ".xls");
		
		if(xfile.exists()){
			File outFile = new File(rootPath + File.separator + fileName + "_" + DateUtil.getCurrentDateTime() + ".xls");
			
		    Workbook workbook = Workbook.getWorkbook(xfile);
		    //WritableWorkbook copy = Workbook.createWorkbook(res.getOutputStream(), workbook);
		    WritableWorkbook copy = Workbook.createWorkbook(outFile, workbook);
		    WritableSheet sheet = copy.getSheet(0);
		    WritableCell cell = null;
		    Label l = null;
	
/*			for(int i=0;i<sheet.getRows();i++){
				Cell[] cells = sheet.getRow(i);
				if (cells.length>0){
					for(int j=0;j<cells.length;j++){
						if("#LIST".equals(cells[j].getContents())){
							//list형 데이터
							
						}else if("#DATA".equals(cells[j].getContents())){
							//단건 데이터
							cell = sheet.getWritableCell(j, i); // (column, row) 

							if (cell.getType() == CellType.LABEL){ 
							  l = (Label) cell; 
							  l.setString("modified cell"); 
							} 

							
						}
					}
				}
			}*/
			
//			if(sheet.getRows() > 0){
//			    for(JExcelInfo info : listData){
//			    	//Label label = new Label(info.getColIdx(), info.getRowIdx(), info.getContent(), new WritableCellFormat(new WritableFont(WritableFont.ARIAL, sheet.getRow(info.getRowIdx())[info.getColIdx()].getCellFormat().getFont().getPointSize(), WritableFont.NO_BOLD)));
//			    	Label label = new Label(info.getColIdx(), info.getRowIdx(), info.getContent(), new WritableCellFormat());
//			    	sheet.addCell(label);
//			    }
//			}
		    
			if(sheet.getRows() > 0){
				List<JExcelInfo> data = listData.getData();
				for(JExcelInfo info : data){
					cell = sheet.getWritableCell(info.getColIdx(), info.getRowIdx()); // (column, row)
					if("#DATA".equals(cell.getContents())){
			    		if (cell.getType() == CellType.LABEL){ 
			    			l = (Label) cell; 
			    			l.setString(info.getContent()); 
			    		} 
			    	}
				}
				
				//남아있는 #DATA 삭제
				for(int i=0;i<sheet.getRows();i++){
					Cell[] cells = sheet.getRow(i);
					for(int j=0;j<cells.length;j++){
						if("#DATA".equals(cells[j].getContents())){
							cell = sheet.getWritableCell(j, i);
							if (cell.getType() == CellType.LABEL){ 
				    			l = (Label) cell; 
				    			l.setString(""); 
				    		}
						}
					}
				}
				
				
				
				List<JExcelListInfo> list = listData.getList();
				int colIdx = 0;
				int rowIdx = 0;
				for(JExcelListInfo info : list){
					cell = sheet.getWritableCell(info.getColIdx(), info.getRowIdx()); // (column, row)
					colIdx = info.getColIdx();
					rowIdx = info.getRowIdx();
					if("#LIST".equals(cell.getContents())){
			    		List<List<JExcelInfo>> listJExcelInfo = info.getListJExcelInfo();
			    		for(List<JExcelInfo> listInfo : listJExcelInfo){
			    			for(JExcelInfo i : listInfo){
			    				Label label = new Label(colIdx, rowIdx, i.getContent(), new WritableCellFormat());
			    				sheet.addCell(label);
			    				colIdx++;
			    			}
			    			rowIdx++;
			    			colIdx = info.getColIdx();
			    		}
			    	}
				}
				
				
			}		    
			
		    copy.write();
		    copy.close();
		    workbook.close();
		    
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
