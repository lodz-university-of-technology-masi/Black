import {ErrorHandler, Injectable} from "@angular/core";
import {UsabilityDataService} from "./services/usability.data.service";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GlobalErrorHandler implements ErrorHandler {
  constructor(private usabilityService: UsabilityDataService) {
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
      this.usabilityService.onErrorOccurred(error)
    }
  }
}
