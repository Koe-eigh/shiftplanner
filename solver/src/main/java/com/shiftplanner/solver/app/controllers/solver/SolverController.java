package com.shiftplanner.solver.app.controllers.solver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shiftplanner.solver.app.adapters.scheduling.actions.solver.SolverAction;
import com.shiftplanner.solver.app.adapters.scheduling.actions.solver.SolverActionInput;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.NotOwnerTimetableException;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.TimetableNotFoundException;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.UserNotFoundException;
import com.shiftplanner.solver.app.utils.controllers.responses.HttpErrorResponse;

@RestController
@RequestMapping("/solver")
public class SolverController {
  private static final Logger logger = LoggerFactory.getLogger(SolverController.class);
  @Autowired
  private SolverAction solverAction;

  @PostMapping("/solve/{problemId}")
  public ResponseEntity<?> solve(@CookieValue String userId, @PathVariable Long problemId) {
    try {
      SolverActionInput input = new SolverActionInput(problemId, userId);
      solverAction.execute(input);
      return ResponseEntity.accepted().build();
    } catch (UserNotFoundException e) {
      logger.error(e.getMessage(), e);
      int statusCode = HttpStatus.NOT_FOUND.value();
      String message = "ユーザーが見つかりませんでした。";
      return ResponseEntity.status(statusCode).body(new HttpErrorResponse(statusCode, message));
    } catch (TimetableNotFoundException e) {
      logger.error(e.getMessage(), e);
      int statusCode = HttpStatus.NOT_FOUND.value();
      String message = "timetableが見つかりませんでした。";
      return ResponseEntity.status(statusCode).body(new HttpErrorResponse(statusCode, message));
    } catch (NotOwnerTimetableException e) {
      logger.error(e.getMessage(), e);
      int statusCode = HttpStatus.FORBIDDEN.value();
      String message = "timetableへのアクセス権限がありません。";
      return ResponseEntity.status(statusCode).body(new HttpErrorResponse(statusCode, message));
    } catch (Throwable e) {
      logger.error(e.getMessage(), e);
      int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
      String message = "サーバーエラーが発生しました。";
      return ResponseEntity.status(statusCode).body(new HttpErrorResponse(statusCode, message));
    }
  }
}
