package com.sudhanshu.employeeleavemanagementsystem.service;

import com.sudhanshu.employeeleavemanagementsystem.entity.LeaveRequest;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {

    public String buildApprovalTemplate(LeaveRequest leave) {

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<style>");
        html.append("body{font-family:Arial,sans-serif;background:#f4f4f4;padding:20px;}");
        html.append(".container{max-width:650px;margin:auto;background:white;border-radius:10px;overflow:hidden;box-shadow:0 0 10px rgba(0,0,0,.2);}");
        html.append(".header{background:#28a745;color:white;padding:20px;text-align:center;font-size:24px;font-weight:bold;}");
        html.append(".content{padding:25px;}");
        html.append("table{width:100%;border-collapse:collapse;margin-top:20px;}");
        html.append("td{border:1px solid #ddd;padding:10px;}");
        html.append(".status{color:green;font-weight:bold;}");
        html.append(".footer{background:#f5f5f5;padding:15px;text-align:center;color:#666;font-size:13px;}");
        html.append("</style>");
        html.append("</head>");

        html.append("<body>");

        html.append("<div class='container'>");

        html.append("<div class='header'>");
        html.append("Employee Leave Management System");
        html.append("</div>");

        html.append("<div class='content'>");

        html.append("<h2>✅ Leave Request Approved</h2>");

        html.append("<p>Hello <b>")
                .append(leave.getEmployee().getFullName())
                .append("</b>,</p>");

        html.append("<p>Your leave request has been approved successfully.</p>");

        html.append("<table>");

        html.append("<tr><td><b>Leave Type</b></td><td>")
                .append(leave.getLeaveType())
                .append("</td></tr>");

        html.append("<tr><td><b>Start Date</b></td><td>")
                .append(leave.getStartDate())
                .append("</td></tr>");

        html.append("<tr><td><b>End Date</b></td><td>")
                .append(leave.getEndDate())
                .append("</td></tr>");

        html.append("<tr><td><b>Reason</b></td><td>")
                .append(leave.getReason())
                .append("</td></tr>");

        html.append("<tr><td><b>Status</b></td><td class='status'>APPROVED</td></tr>");

        html.append("</table>");

        html.append("<br>");

        html.append("<p>Thank you for using our Leave Management System.</p>");

        html.append("</div>");

        html.append("<div class='footer'>");
        html.append("HR Department<br>");
        html.append("Employee Leave Management System");
        html.append("</div>");

        html.append("</div>");

        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }
}