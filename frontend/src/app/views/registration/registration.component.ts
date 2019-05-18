import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  private registerForm: FormGroup;
  private languages: string[];

  constructor(private userService: UserService,
              private toastr: ToastrService,
              private router: Router) {
    this.registerForm = new FormGroup({
      login: new FormControl('', Validators.required),
      mail: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(4)]),
      language: new FormControl('', Validators.required),
    });
    this.languages = ['PL', 'EN'];
  }

  ngOnInit() {
  }

  async onSubmit() {
    const account = {
      login: this.registerForm.get('login').value,
      password: this.registerForm.get('password').value,
      email:  this.registerForm.get('mail').value,
      language: this.registerForm.get('language').value
    };

    await this.userService.createNewAccount(account)
      .catch(() => {
        this.router.navigateByUrl('login');
        this.toastr.success('Konto zostało utworzone', 'Sukces', {
          timeOut: 3000,
          closeButton: true,
        });
      });
  }

  get language() {
    return this.registerForm.get('language');
  }
}
