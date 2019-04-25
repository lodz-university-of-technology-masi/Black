import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  private registerForm: FormGroup;
  private languages: string[];

  constructor() {
    this.registerForm = new FormGroup({
      username: new FormControl('', Validators.required),
      mail: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(4)]),
      language: new FormControl('', Validators.required),
    });
    this.languages = ['język polski', 'język angielski', 'język niemiecki'];
  }

  ngOnInit() {
  }

  get language() {
    return this.registerForm.get('language');
  }
}
