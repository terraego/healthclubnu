package nu.healthclub.prospect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import nu.healthclub.prospect.model.Prospect;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {
	public static final String SHEET_NAME = "Healthclub nu export";

	private static final DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	enum Properties {
		entryDate {
			@Override
			String getValue(Prospect p) {
				return format.format(p.getEntryDate());
			}
		},
		birthDate {
			@Override
			String getValue(Prospect p) {
				return format.format(p.getBirthDate());
			}
		},
		advisor {
			@Override
			String getValue(Prospect p) {
				return p.getAdvisor();
			}
		},
		firstName {
			@Override
			String getValue(Prospect p) {
				return p.getFirstName();
			}
		},
		lastName {
			@Override
			String getValue(Prospect p) {
				return p.getLastName();
			}
		},
		address {
			@Override
			String getValue(Prospect p) {
				return p.getAddress();
			}
		},
		postalCode {
			@Override
			String getValue(Prospect p) {
				return p.getPostalCode();
			}
		},
		city {
			@Override
			String getValue(Prospect p) {
				return p.getCity();
			}
		},
		email {
			@Override
			String getValue(Prospect p) {
				return p.getEmail();
			}
		},
		phoneHome {
			@Override
			String getValue(Prospect p) {
				return p.getPhoneHome();
			}
		},
		phoneWork {
			@Override
			String getValue(Prospect p) {
				return p.getPhoneWork();
			}
		},
		phoneMobile {
			@Override
			String getValue(Prospect p) {
				return p.getPhoneMobile();
			}
		},
		currentSport {
			@Override
			String getValue(Prospect p) {
				return p.getCurrentSport();
			}
		},
		pastSport {
			@Override
			String getValue(Prospect p) {
				return p.getPastSport();
			}
		},
		reference {
			@Override
			String getValue(Prospect p) {
				return p.getReference();
			}
		},
		motivation {
			@Override
			String getValue(Prospect p) {
				return p.getMotivation();
			}
		},
		remark {
			@Override
			String getValue(Prospect p) {
				return p.getRemark();
			}
		},
		member {
			@Override
			String getValue(Prospect p) {
				return Boolean.toString(p.isMember());
			}
		};

		abstract String getValue(Prospect p);
	}

	public void export(File file, List<Prospect> prospects) throws IOException {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(SHEET_NAME);

		CellStyle headerStyle = createHeaderStyle(wb);
		CellStyle normalStyle = createNormalStyle(wb);
		int rowCount = 0;

		Row headerRow = sheet.createRow(rowCount++);
		headerRow.setHeightInPoints(12.75f);
		for (int i = 0; i < Properties.values().length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(Properties.values()[i].name());
			cell.setCellStyle(headerStyle);
		}

		for (Prospect prospect : prospects) {
			Properties[] values = Properties.values();
			Row row = sheet.createRow(rowCount++);
			row.setHeightInPoints(12.75f);

			for (int i = 0; i < values.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(values[i].getValue(prospect));
				cell.setCellStyle(normalStyle);
			}
		}

		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			wb.write(out);
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private CellStyle createHeaderStyle(Workbook wb) {
		CellStyle style;
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		return style;
	}

	private CellStyle createNormalStyle(Workbook wb) {
		CellStyle style;
		Font font1 = wb.createFont();
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font1);
		return style;
	}

	private static CellStyle createBorderedStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
}
