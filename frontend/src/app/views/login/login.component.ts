import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Credentials} from '../../model/Credentials';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {Role} from '../../model/entities';

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
  private isLoginFailed = false;
  errorMessage: string;

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
    const credentials = new Credentials(this.loginForm.get('username').value, this.loginForm.get('password').value)

    await this.userService.login(credentials).then((user) => {
      switch (user.role) {
        case Role.MODERATOR:
          this.router.navigateByUrl('/positions')
          break;
        case Role.REDACTOR:
          this.router.navigateByUrl('/tests')
          break;
        case Role.CANDIDATE:
          this.router.navigateByUrl('/tests')
          break;
      }
    })
      .catch((error) => {
        this.isLoginFailed = true;
        this.errorMessage = error;
      });
  }

}
