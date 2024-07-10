package com.shiftplanner.solver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftplanner.solver.domain.Timetable;
import com.shiftplanner.solver.service.SolverService;

import ai.timefold.solver.core.api.solver.SolverManager;

@RestController
@RequestMapping("/solver")
public class SolverController {

    @Autowired
    private SolverService service;
    @Autowired
    private SolverManager<Timetable, String> solverManager;

    @PostMapping("/solve")
    public void solve(@RequestBody Timetable timetable) {
        solverManager.solveAndListen(timetable.getId(), timetable, service::save);
    }


}
