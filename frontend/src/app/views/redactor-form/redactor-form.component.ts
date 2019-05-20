import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../model/entities';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-redactor-form',
  templateUrl: './redactor-form.component.html',
  styleUrls: ['./redactor-form.component.css']
})
export class RedactorFormComponent implements OnInit {
  private id: number;
  editRedactorForm: FormGroup;
  private redactorToEdit: User;
  languages: string[];

  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private router: Router,
              private toastr: ToastrService) {
    this.languages = ['PL', 'EN'];
  }

  ngOnInit() {
    this.id = this.route.snapshot.params.id;
    console.log(this.id);

    this.userService.getOne(this.id).then((item) => {
      this.redactorToEdit = item;
      console.log(this.redactorToEdit);

      this.editRedactorForm = new FormGroup({
        email: new FormControl(this.redactorToEdit.email, [Validators.required, Validators.email]),
        language: new FormControl(this.redactorToEdit.language, Validators.required),
        login: new FormControl(this.redactorToEdit.login, Validators.required),
        id: new FormControl((this.redactorToEdit.id)),
        role: new FormControl((this.redactorToEdit.role)),
      });
    });
  }

  get language() {
    return this.editRedactorForm.get('language');
  }

  cancelEditRedactorAccount() {
    this.router.navigateByUrl('redactors');
    this.toastr.info('Edycja konta redaktora została anulowana', 'Informacja', {
      timeOut: 3000,
      closeButton: true,
    });
  }

  editRedactorAccount() {
    this.userService.update(this.editRedactorForm.value).then(() => {
      this.router.navigateByUrl('redactors');
      this.toastr.success('Konto redaktora zostało zedytowane', 'Sukces', {
        timeOut: 3000,
        closeButton: true,
      });
    });
  }
}
