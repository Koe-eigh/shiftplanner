package com.shiftplanner.solver.app.controllers.timetables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftplanner.solver.app.adapters.scheduling.actions.timetables.GetTimetableByIdAction;
import com.shiftplanner.solver.app.adapters.scheduling.actions.timetables.GetTimetableByIdActionInput;
import com.shiftplanner.solver.app.adapters.scheduling.actions.timetables.GetTimetableByIdActionOutput;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.NotOwnerTimetableException;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.TimetableNotFoundException;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.UserNotFoundException;
import com.shiftplanner.solver.app.utils.controllers.responses.HttpErrorResponse;

@RestController
@RequestMapping("/timetables")
public class TimetableController {
  private static final Logger logger = LoggerFactory.getLogger(TimetableController.class);
  @Autowired
  private GetTimetableByIdAction getTimetableByIdAction;

  @GetMapping("/{id}")
  public ResponseEntity<?> getTimetableById(@CookieValue("userId") String userId,
      @PathVariable("id") Long timetableId) {
    try {
      GetTimetableByIdActionInput input = new GetTimetableByIdActionInput(userId, timetableId);
      GetTimetableByIdActionOutput output = new GetTimetableByIdActionOutput();
      getTimetableByIdAction.execute(input, output);
      return ResponseEntity.status(HttpStatus.OK.value()).body(output);
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
