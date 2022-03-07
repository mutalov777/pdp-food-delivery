package uz.pdp.pdp_food_delivery.rest.service.excelFile;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.pdp.pdp_food_delivery.rest.dto.excelFile.ExcelFileDto;
import uz.pdp.pdp_food_delivery.rest.dto.mealorder.MealOrderCreateDto;
import uz.pdp.pdp_food_delivery.rest.entity.excelFile.ExcelFile;
import uz.pdp.pdp_food_delivery.rest.mapper.excelFile.ExcelFileMapper;
import uz.pdp.pdp_food_delivery.rest.repository.exccelFile.ExcelFileRepository;
import uz.pdp.pdp_food_delivery.rest.service.base.AbstractService;
import uz.pdp.pdp_food_delivery.rest.service.mealorder.MealOrderService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExcelFileService extends AbstractService<ExcelFileMapper, ExcelFileRepository>{

    private final MealOrderService mealOrderService;

    public ExcelFileService(ExcelFileMapper mapper, ExcelFileRepository repository, MealOrderService mealOrderService) {
        super(mapper, repository);
        this.mealOrderService = mealOrderService;
    }

    public ExcelFileDto getExcelFile(LocalDateTime date) {

        List<MealOrderCreateDto> dto = mealOrderService.findOrderForExcelFile(date);
        ExcelFileDto excelFileDto;

        XSSFWorkbook workbook = new XSSFWorkbook();
        String fileName = date.toString() + UUID.randomUUID();
        File file = new File("src/main/resources/excelFileStorage/" + fileName + ".xlsx");

        try {
            boolean created = file.createNewFile();
            if (created) {

                try (FileOutputStream outputStream = new FileOutputStream(file.getPath())) {

                    XSSFSheet xssfSheet = workbook.createSheet("Posts");
                    XSSFRow xssfRow = xssfSheet.createRow(3);

                    xssfRow.createCell(0).setCellValue("Full");
                    xssfRow.createCell(1).setCellValue("Phone number");
                    xssfRow.createCell(2).setCellValue("Department");
                    xssfRow.createCell(3).setCellValue("Meal name");
//                    xssfRow.createCell(4).setCellValue("Meal price");
                    int counter = 0;
                    for (MealOrderCreateDto createDto : dto) {

                        XSSFRow row = xssfSheet.createRow(counter + 2);
                        row.createCell(0).setCellValue(createDto.getUserDto().getFullName());
                        row.createCell(1).setCellValue(createDto.getUserDto().getPhoneNumber());
                        row.createCell(2).setCellValue(createDto.getUserDto().getDepartment().toString());
                        row.createCell(3).setCellValue(createDto.getMealDto().getName());
//                        row.createCell(4).setCellValue(createDto.getMealDto().getPrice());
                    }
                    workbook.write(outputStream);

                    ExcelFile excelFile = new ExcelFile(fileName, file.getAbsolutePath(), ".xlsx");
                    repository.save(excelFile);
                    excelFileDto=mapper.toDto(excelFile);
                    return excelFileDto;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
