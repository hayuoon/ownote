package com.project.ownote.attendance.controller;

import com.project.ownote.attendance.dto.Attendance;
import com.project.ownote.attendance.dto.Dto;
import com.project.ownote.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
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




    @GetMapping("/list1")
    public String listAllAttendances(Model model) {
        List<Attendance> allAttendances = attendanceService.getAllAttendances();
        model.addAttribute("allAttendances", allAttendances);
        return "attendance/list";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long attendance_id) {
        Attendance attendance =  attendanceService.getAttendanceById(attendance_id);
        System.out.println("컨트롤러");
        System.out.println(attendance);
//        List<Attendance> allAttendances = attendanceService.getAllAttendances();
       model.addAttribute("allAttendances", attendanceService.getAttendanceById(attendance_id));
        return "attendance/edit";
    }



//        @GetMapping("/update")
//    public String update(Attendance attendance) {
////        Attendance attendance = attendanceService.getAttendanceById(attendance);
////        if (attendance != null) {
//
//            attendanceService.updateAttendance(attendance);
//            System.out.println(attendance);
////        }
//        return "redirect:/attendance/list1";
//    }


    @PostMapping("/update")
    public String update1(@ModelAttribute Dto dto) {
        // 입력 문자열
        String timeString = dto.getAtt_offtime();
        String timeString2 = dto.getAtt_ontime();
        String status = dto.getAtt_status();
        System.out.println("3038403840385038503583" + timeString2);

        // 파싱할 시간 형식 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // 문자열을 LocalTime으로 변환
        LocalTime localTime = LocalTime.parse(timeString, formatter);
        LocalTime localTime2 = LocalTime.parse(timeString2, formatter);


        Attendance attendance = attendanceService.getAttendanceById(dto.getAttendance_id());
        attendance.setAtt_offtime(localTime);
        attendance.setAtt_ontime(localTime2);
        attendance.setAtt_status(status);
        System.out.println("----------------------" + attendance);

//        Attendance attendance = attendanceService.getAttendanceById(attendance);
//        if (attendance != null) {

        attendanceService.updateAttendance(attendance);

//        }
        return "redirect:/attendance/list1";
    }







//    @GetMapping("/{attendance_id}")
//    public String viewAttendance(@PathVariable("attendance_id") Long attendanceId, Model model) {
//        Attendance attendance = attendanceService.getAttendanceById(attendanceId);
//        model.addAttribute("attendance", attendance);
//        return "attendance/view";
//    }




//    @GetMapping("/{attendance_id}/delete")
//    public String deleteAttendance(@PathVariable("attendance_id") Long attendanceId) {
//        attendanceService.deleteAttendanceById(attendanceId);
//        return "redirect:/attendance/list";
//    }


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




    @GetMapping("/search")
    public  String search(Model model, @RequestParam Long emp_num){
        List<Attendance> list = attendanceService.findByEmpNum(emp_num);
        model.addAttribute("allAttendances", list);
        return "attendance/list1";
    }



}
