import {Component, HostListener} from '@angular/core';
import {UsabilityDataService} from "./services/usability.data.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  {

  constructor(private usabilityService: UsabilityDataService) {
  }

  @HostListener('document:keyup', ['$event'])
  handleDeleteKeyboardEvent(evt: KeyboardEvent) {
    this.usabilityService.onKeyUp(evt)
  }

  @HostListener('mouseup', ['$event'])
  onLeftMiddleMouseClick(evt) {
    this.usabilityService.onMouseClick(evt)
  }

  @HostListener('contextmenu', ['$event'])
  onRightMouseClick(evt) {
    this.usabilityService.onMouseClick(evt)
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize(evt) {
    this.usabilityService.onWindowResize(evt)
  }
}
