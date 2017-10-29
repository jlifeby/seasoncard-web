package com.jlife.abon.service;

import com.jlife.abon.report.Report;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ExcelGeneratorService {
    Workbook generateExcel(Report report) throws IOException;
}
