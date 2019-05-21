import {ErrorHandler, Injectable} from "@angular/core";
import {UsabilityDataService} from "./services/usability.data.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {ValidationErrorDto} from "./model/dto";

@Injectable({
  providedIn: 'root'
})
export class GlobalErrorHandler implements ErrorHandler {
  constructor(private usabilityService: UsabilityDataService, private toastr: ToastrService) {
  }

  handleError(error: any) {
    console.error('error', error);

    let httpErr: HttpErrorResponse;
    if (error instanceof HttpErrorResponse) {
      httpErr = error as HttpErrorResponse;
    }

    if (error.rejection instanceof HttpErrorResponse) {
      httpErr = error.rejection as HttpErrorResponse;
    }

    if (httpErr) {
      this.handleErrors(httpErr);
      this.usabilityService.onErrorOccurred(error)
    }
  }

  private handleErrors(httpErr: HttpErrorResponse) {
    let err = httpErr.error;

    let validationErrors: ValidationErrorDto;
    if (err.fieldErrors || err.globalErrors) {
      validationErrors = err as ValidationErrorDto;
    }

    if (!validationErrors) {
      this.toastr.error('Wystąpił nieoczekiwany błąd', 'Nieoczekiwany błąd', {
        closeButton: true,
        positionClass: 'toast-top-center',
        onActivateTick: true
      });
      return
    }

    let msg = this.getMessage(validationErrors);

    this.toastr.error(msg, 'Błąd', {
      closeButton: true,
      positionClass: 'toast-top-center',
      onActivateTick: true
    });
  }

  private getMessage(validationErrors: ValidationErrorDto) {
    let msg = '';

    for (const e of validationErrors.globalErrors) {
      msg += e.message + '\n'
    }

    for (const e of validationErrors.fieldErrors) {
      msg += `field ${e.path} ${e.message}\n`
    }
    return msg;
  }
}
