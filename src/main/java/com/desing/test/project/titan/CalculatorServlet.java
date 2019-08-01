package com.desing.test.project.titan;

import com.desing.test.project.titan.exception.ExpressionsException;
import com.desing.test.project.titan.service.CalculationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.desing.test.project.titan.model.Calculation;
import com.desing.test.project.titan.model.ExpressionResult;
import com.desing.test.project.titan.util.ObjectMapperUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@WebServlet(urlPatterns = "/calculator")
public class CalculatorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
        resp.setContentType("application/json");
        try {
            switch (action) {
                case "uploadFile": {
                    List<ExpressionResult> expressionResults = uploadFileAndCalculate(req);
                    String result = objectMapper.writeValueAsString(expressionResults);
                    resp.getWriter().write(result);
                    break;
                }
                case "save": {
                    CalculationService calculationService = new CalculationService();
                    calculationService.save(objectMapper.readValue(req.getReader(), new TypeReference<List<ExpressionResult>>() {
                    }));
                    resp.setStatus(201);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }

    private List<ExpressionResult> uploadFileAndCalculate(HttpServletRequest request) throws ExpressionsException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        StringBuilder expessions = new StringBuilder();
        try {
            List<FileItem> fields = upload.parseRequest(request);
            fields.forEach(fileItem -> expessions.append(fileItem.getString()));
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        CalculationService calculationService = new CalculationService();
        return calculationService.calculate(expessions.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
        resp.setContentType("application/json");
        CalculationService calculationService = new CalculationService();
        try {
            switch (action) {
                case "uploadFileAndCalculate":
                    break;
                case "findAllCalculation": {
                    List<Calculation> calculations = calculationService.findAll();
                    String result = objectMapper.writeValueAsString(calculations);
                    resp.getWriter().write(result);
                    break;
                }
                case "findExpressions":
                    Long id = Long.valueOf(req.getParameter("calculationId"));
                    List<ExpressionResult> expressions = calculationService.findExpressionByCalculationId(id);
                    String result = objectMapper.writeValueAsString(expressions);
                    resp.getWriter().write(result);
                    break;
                case "deleteCalculation":
                    calculationService.deleteCalculation(Long.valueOf(req.getParameter("calculationId")));
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
