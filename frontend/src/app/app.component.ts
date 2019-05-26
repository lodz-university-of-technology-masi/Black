import {Component, HostListener} from '@angular/core';
import {UsabilityDataService} from './services/usability.data.service';
import {UserService} from './services/user.service';
import {ToastrService} from 'ngx-toastr';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  {

  constructor(private usabilityService: UsabilityDataService,
              private userService: UserService,
              private toastr: ToastrService) {
  }

  @HostListener('document:keyup', ['$event'])
  handleDeleteKeyboardEvent(evt: KeyboardEvent) {
    this.usabilityService.onKeyUp(evt)
    if (this.userService.isCandidate()) {
      if (evt.key === 'PrintScreen') {
        this.copyToClipboard();
        event.preventDefault();
      }
    }
  }

  @HostListener('mouseup', ['$event'])
  onLeftMiddleMouseClick(evt) {
    this.usabilityService.onMouseClick(evt)
    if (this.userService.isCandidate()) {
      if (evt.which === 3) {
        this.toastr.error('Nie wolno klikać prawym przyciskiem myszy!', 'Drogi kandydacie', {
          timeOut: 3000,
          closeButton: true,
        });
        evt.preventDefault();
      }
    }
  }

  @HostListener('contextmenu', ['$event'])
  onRightMouseClick(evt) {
    this.usabilityService.onMouseClick(evt)
    if (this.userService.isCandidate()) {
      event.preventDefault();
    }
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize(evt) {
    this.usabilityService.onWindowResize(evt)
  }

  copyToClipboard() {
    const aux = document.createElement('input');
    aux.setAttribute('value', 'print screen disabled!');
    document.body.appendChild(aux);
    aux.select();
    document.execCommand('copy');
    document.body.removeChild(aux);
    this.toastr.error('Nie wolno robić print screen!', 'Drogi kandydacie', {
      timeOut: 3000,
      closeButton: true,
    });
  }
}
