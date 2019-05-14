import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Credentials} from "../../model/Credentials";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {Role} from "../../model/entities";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private loginForm: FormGroup;
  private isLoggedIn = false;
  private userService: UserService;
  private router: Router;

  constructor(restService: UserService, router: Router) {
    this.userService = restService;
    this.router = router;
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', [Validators.required, Validators.minLength(4)])
    });
  }

  ngOnInit() {
  }

  async onSubmit() {
    let credentials = new Credentials(this.loginForm.get('username').value, this.loginForm.get('password').value)
    try {
      let user = await this.userService.login(credentials)

      switch (user.role) {
        case Role.MODERATOR:
          this.router.navigateByUrl('/positions')
          break;
        case Role.REDACTOR:
          this.router.navigateByUrl('/tests')
          break;
        case Role.CANDIDATE:
          // TODO
          console.warn('TODO domyślny route dla kandydata')
          break;
      }
    } catch (err) {
      // TODO obsługa błędów
      alert("Logowanie nie powiodło się:" + err)
      console.error('login error', err)
    }

  }

}
