import {Component, OnInit} from '@angular/core';
import {User} from '../../model/entities';
import {UserService} from '../../services/user.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-redactors',
  templateUrl: './redactors.component.html',
  styleUrls: ['./redactors.component.css']
})
export class RedactorsComponent implements OnInit {

  redactors: User[];
  private addRedactorFormGroup: FormGroup;
  languages: string[];
  private redactor: User;

  constructor(private userService: UserService,
              private router: Router,
              private toastr: ToastrService) {
    this.languages = ['PL', 'EN'];

    this.addRedactorFormGroup = new FormGroup({
      login: new FormControl('', Validators.required),
      mail: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(4)]),
      language: new FormControl('', Validators.required),
    });
  }

  ngOnInit() {
    this.userService.getRedactors().then((item) => {
      this.redactors = item;
    });
  }

  async addRedactorAccount() {
    const account = {
      login: this.addRedactorFormGroup.get('login').value,
      password: this.addRedactorFormGroup.get('password').value,
      email: this.addRedactorFormGroup.get('mail').value,
      language: this.addRedactorFormGroup.get('language').value
    };

    await this.userService.createNewRedactorAccount(account)
      .then(() => {
        this.userService.getRedactors().then((item) => {
          this.redactors = item;
        });
        this.router.navigateByUrl('redactors');
        this.toastr.success('Konto redaktora zostało utworzone', 'Sukces', {
          timeOut: 3000,
          closeButton: true,
        });
      });
  }

  async getRedactorToRemove(id: number) {
    await this.userService.getOne(id)
      .then((item) => {
        this.redactor = item;
      });
  }

  async removeRedactorAccount() {
    await this.userService.delete(this.redactor)
      .then(() => {
        this.userService.getRedactors().then((item) => {
          this.redactors = item;
        });
        this.toastr.success('Konto redaktora zostało usunięte', 'Sukces', {
          timeOut: 3000,
          closeButton: true,
        });
      });
  }

  get language() {
    return this.addRedactorFormGroup.get('language');
  }

}
