import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Credentials} from "../model/Credentials";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private loginForm: FormGroup;
  private isLoggedIn = false;
  private userService: UserService;

  constructor(restService: UserService) {
    this.userService = restService;
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', [Validators.required, Validators.minLength(4)])
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    let credentials = new Credentials(this.loginForm.get('username').value, this.loginForm.get('password').value)
    this.userService.login(credentials).subscribe(
      user => console.log("success!", user),
      err => {
        console.error('login error', err)
      })

  }

}
