import com.matuageorge.webapp.Config;
import com.matuageorge.webapp.model.Resume;
import com.matuageorge.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage resumeStorage = Config.get().getSqlStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");


        List<Resume> resumes = resumeStorage.getAllSorted();

        String head = "<head>\n" +
                "<link rel=\"stylesheet\" href=\"css/tableFormat.css\"/>" +
                "</head>";

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
