import {Injectable} from '@angular/core';

import {BaseEntityService} from './base-entity.service';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {UsabilityData} from '../model/entities';
import {DeviceDetectorService} from "ngx-device-detector";
import {ToastrService} from "ngx-toastr";
import {ViewportScroller} from "@angular/common";


const DEBUG = false;

function log(...args: any) {
  if(DEBUG) {
    console.log.apply(console, arguments)
  }
}

@Injectable({
  providedIn: 'root'
})
export class UsabilityDataService extends BaseEntityService<UsabilityData> {

  private measurementStarted = false;
  private windowHeight: number;
  private windowWidth: number;
  private startTime: Date;
  private clickCounter: number = 0;
  private distance: number = 0;
  private browserCode: string;
  private errorCode: number = null;
  private lastX: number = 0;
  private lastY: number = 0;

  constructor(http: HttpClient, private deviceDetector: DeviceDetectorService, private toastr: ToastrService, private scroller: ViewportScroller) {
    super(http);
    let info = this.deviceDetector.getDeviceInfo();
    this.browserCode = info.browser.charAt(0);
    this.windowHeight = window.innerHeight;
    this.windowWidth = window.innerWidth;
  }

  private async finishMeasurement(fail?: boolean) {
    let endTime = new Date();
    let data: UsabilityData = {
      id: null,
      browser: this.browserCode,
      screenWidth: this.windowWidth,
      screenHeight: this.windowHeight,
      mouseClicks: this.clickCounter,
      timeElapsed: endTime.getTime() - this.startTime.getTime(),
      distance: this.distance,
      fail: fail,
      error: fail ? this.errorCode: null
    };

    try {
      data = await this.create(data)
    } catch (err) {
      this.toastr.error('Wystąpił błąd podczas próby zapisu', 'Błąd', {
        timeOut: 3000,
        closeButton: true,
      });
      throw err
    }
    log('UsabilityData:', data)
    // TODO screenshot
  }

  private startMeasurement() {
    // TODO screenshot
  }

  private async abortMeasurement() {
    await this.finishMeasurement(true);
    this.clearCounters();
  }

  private clearCounters() {
    this.measurementStarted = false
    this.startTime = null;
    this.clickCounter = 0;
    this.distance = 0;
    this.errorCode = null;
    this.lastX = 0;
    this.lastY = 0;
  }

  private calculateDistance(x1: number, y1: number, x2: number, y2: number): number {
    return Math.ceil(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
  }

  async onKeyUp(evt: KeyboardEvent) {
    switch (evt.key) {
      case "D":
        if (this.measurementStarted) {
          // Stop measurement
          await this.finishMeasurement();
          log('measurement stopped')
          this.clearCounters();
          this.toastr.success('Zakończono zbieranie metryk', 'Pomiar zakończony', {
            timeOut: 3000,
            closeButton: true,
          });
        } else {
          // Start measurement
          log('measurement started')
          this.measurementStarted = true;
          this.startTime = new Date();
          this.startMeasurement()
          this.toastr.success('Rozpoczęto zbieranie metryk', 'Pomiar rozpoczęty', {
            timeOut: 3000,
            closeButton: true,
          });
        }
        break;
      case "W":
        // Cancel measurement
        if (this.measurementStarted) {
          log('measurement cancelled')
          this.clearCounters()
          this.toastr.warning('Anulowano zbieranie metryk', 'Pomiar anulowany', {
            timeOut: 3000,
            closeButton: true,
          });
        }
        break;
      case "R":
        // Abort measurement - mark as failed
        if (this.measurementStarted) {
          log('measurement aborted')
          await this.abortMeasurement();
          this.toastr.error('Przerwano zbieranie metryk', 'Pomiar przerwany', {
            timeOut: 3000,
            closeButton: true,
          });
        }
        break;
    }
  }

  onMouseClick(evt: any) {
    if(!this.measurementStarted) {
      return;
    }

    let [xScrollOffset, yScrollOffset]= this.scroller.getScrollPosition();

    let currentX = evt.clientX + xScrollOffset;
    let currentY = evt.clientY + yScrollOffset;

    this.clickCounter++;

    this.distance += this.calculateDistance(this.lastX, this.lastY, currentX, currentY);
    this.lastX = currentX;
    this.lastY = currentY;

    log('scroll offset:', xScrollOffset, yScrollOffset);
    log('distance: ', this.distance);
    log('clicks: ', this.clickCounter);
  }

  onWindowResize(evt) {
    this.windowWidth = evt.target.innerWidth;
    this.windowHeight = evt.target.innerHeight;
  }

  onErrorOccurred(err: HttpErrorResponse) {
    if (this.measurementStarted) {
      this.errorCode = err.status;
    }
  }

  onFocus(){
    //TODO wznowienie odliczania czasu
  }
  onBlur(){
    //TODO stop odliczania czasu
  }

  getEntityUrl(): string {
    return 'usability';
  }
}
