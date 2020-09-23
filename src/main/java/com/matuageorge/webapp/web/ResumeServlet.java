package com.matuageorge.webapp.web;

import com.matuageorge.webapp.Config;
import com.matuageorge.webapp.model.Resume;
import com.matuageorge.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage resumeStorage;

    @Override
    public void init() throws ServletException {
        super.init();
        resumeStorage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");


        List<Resume> resumes = resumeStorage.getAllSorted();

        String head = "<head>\n" +
//                "<link rel=\"stylesheet\" href=\"../css/tableFormat.css\"/>" +
                "<style>\ntable.paleBlueRows {\n" +
                "    font-family: Verdana, Geneva, sans-serif;\n" +
                "    border: 1px solid #FFFFFF;\n" +
                "    height: 200px;\n" +
                "    text-align: left;\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "table.paleBlueRows td, table.paleBlueRows th {\n" +
                "    border: 1px solid #FFFFFF;\n" +
                "    padding: 3px 2px;\n" +
                "}\n" +
                "\n" +
                "table.paleBlueRows tbody td {\n" +
                "    font-size: 13px;\n" +
                "}\n" +
                "\n" +
                "table.paleBlueRows tr:nth-child(even) {\n" +
                "    background: #D0E4F5;\n" +
                "}\n" +
                "\n" +
                "table.paleBlueRows thead {\n" +
                "    background: #0B6FA4;\n" +
                "    border-bottom: 5px solid #FFFFFF;\n" +
                "}\n" +
                "\n" +
                "table.paleBlueRows thead th {\n" +
                "    font-size: 17px;\n" +
                "    font-weight: bold;\n" +
                "    color: #FFFFFF;\n" +
                "    text-align: center;\n" +
                "    border-left: 2px solid #FFFFFF;\n" +
                "}\n" +
                "\n" +
                "table.paleBlueRows thead th:first-child {\n" +
                "    border-left: none;\n" +
                "}\n" +
                "\n" +
                "table.paleBlueRows tfoot {\n" +
                "    font-size: 14px;\n" +
                "    font-weight: bold;\n" +
                "    color: #333333;\n" +
                "    background: #D0E4F5;\n" +
                "    border-top: 3px solid #444444;\n" +
                "}\n" +
                "\n" +
                "table.paleBlueRows tfoot td {\n" +
                "    font-size: 14px;\n" +
                "}"
                +
                "</style>\n</head>";

        String tableHeader = "<body><table class=\"paleBlueRows\">\n" +
                "<tr>\n" +
                "<th>Resume UUID</th>\n" +
                "<th>Full Name</th>\n" +
                "  </tr>\n";
        String tableFooter = "</table><body>";

        StringBuilder cells = new StringBuilder();

        resumes.forEach(resume -> cells.append("<tr>" +
                "<td>")
                .append(resume.getUuid())
                .append("</td><td>")
                .append(resume.getFullName())
                .append("</td></tr>"));
        response.getWriter().write(head + tableHeader + cells + tableFooter);
    }
}
