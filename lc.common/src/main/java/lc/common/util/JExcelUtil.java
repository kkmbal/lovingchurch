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
				// #DATA 타입
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
				

				
				// #LIST 타입
				List<JExcelListInfo> list = listData.getList();
				int colIdx = 0;
				int rowIdx = 0;
				int addRowCnt = 0; //추가된 row 수 
				for(JExcelListInfo info : list){
					cell = sheet.getWritableCell(info.getColIdx(), info.getRowIdx()); // (column, row)
					if("#LIST2".equals(cell.getContents())){
						//원래 가지고 있던 row값을 유지.
						addRowCnt = 0;
					}					
					colIdx = info.getColIdx();
					rowIdx = info.getRowIdx() + addRowCnt;
					if("#LIST,#LIST2".contains(cell.getContents())){
			    		List<List<JExcelInfo>> listJExcelInfo = info.getListJExcelInfo();
			    		for(List<JExcelInfo> listInfo : listJExcelInfo){ //세로 데이터
			    			if("#LIST".equals(cell.getContents()) && rowIdx > info.getRowIdx()) { //row 삽입
			    				sheet.insertRow(rowIdx + 1);
			    				addRowCnt++;
			    			}

			    			for(JExcelInfo i : listInfo){ //가로 데이터
			    				Label label = new Label(colIdx, rowIdx, i.getContent(), new WritableCellFormat());
			    				sheet.addCell(label);
			    				colIdx++;
			    			}
			    			rowIdx++;
			    			colIdx = info.getColIdx();
			    		}
			    	}
				}
				
				//값 없이 남아있는 #DATA, #LIST 플래그 삭제
				for(int i=0;i<sheet.getRows();i++){
					Cell[] cells = sheet.getRow(i);
					for(int j=0;j<cells.length;j++){
						if("#DATA,#LIST,#LIST2".contains(cells[j].getContents())){
							cell = sheet.getWritableCell(j, i);
							if (cell.getType() == CellType.LABEL){ 
				    			l = (Label) cell; 
				    			l.setString(""); 
				    		}
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

}
