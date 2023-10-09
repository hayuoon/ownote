package com.project.ownote.attendance.controller;

import com.project.ownote.attendance.dto.Attendance;
import com.project.ownote.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

//    @GetMapping("/admin")
//    public String showAdminPage() {
//
//        return "attendance/admin";
//    }



    @GetMapping("/list1")
    public String listAllAttendances(Model model) {
        List<Attendance> allAttendances = attendanceService.getAllAttendances();
        model.addAttribute("allAttendances", allAttendances);
        return "attendance/list";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        List<Attendance> allAttendances = attendanceService.getAllAttendances();
        model.addAttribute("allAttendances", allAttendances);
        return "attendance/edit";
    }


    @GetMapping("/{attendance_id}")
    public String viewAttendance(@PathVariable("attendance_id") Long attendanceId, Model model) {
        Attendance attendance = attendanceService.getAttendanceById(attendanceId);
        model.addAttribute("attendance", attendance);
        return "attendance/view";
    }







    @GetMapping("/edit/{id}")
    public String editAttendance(@PathVariable Long id) {
        attendanceService.editAttendance(id);

        return "redirect:/attendance/list";
    }



    @GetMapping("/attendance/search")
    public String searchAttendance(@RequestParam(name = "searchDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
        return "attendance/list";
    }





    @GetMapping("/{attendance_id}/delete")
    public String deleteAttendance(@PathVariable("attendance_id") Long attendanceId) {
        attendanceService.deleteAttendanceById(attendanceId);
        return "redirect:/attendance/list";
    }


    @GetMapping("/record")
    public String recordAttendancePage(Model model) {
        model.addAttribute("attendance", new Attendance());
        return "attendance/attendance";
    }






    @PostMapping("/recordAttendance")
    @ResponseBody
    public String recordAttendance(@RequestParam("attendanceTime") String attendanceTime) {
        attendanceService.recordAttendance(attendanceTime);
        return "출근 기록이 저장되었습니다.";
    }

    @PostMapping("/recordLeave")
    @ResponseBody
    public String recordLeave(@RequestParam("leaveTime") String leaveTime) {
        attendanceService.recordLeave(leaveTime);
        return "퇴근 기록이 저장되었습니다.";
    }


    @PostMapping("/record/attendance")
    public ResponseEntity<?> recordAttendance(@RequestBody Map<String, String> requestBody) {
        String time = requestBody.get("time");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/record/leave")
    public ResponseEntity<?> recordLeave(@RequestBody Map<String, String> requestBody) {
        String time = requestBody.get("time");

        return ResponseEntity.ok().build();
    }





}
