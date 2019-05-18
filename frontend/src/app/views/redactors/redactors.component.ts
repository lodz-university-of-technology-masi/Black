import {Component, OnInit} from '@angular/core';
import {User} from '../../model/entities';
import {UserService} from '../../services/user.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-redactors',
  templateUrl: './redactors.component.html',
  styleUrls: ['./redactors.component.css']
})
export class RedactorsComponent implements OnInit {

  redactors: User[];
  addRedactorGroup: FormGroup;
  private languages: string[];

  constructor(private userService: UserService) {
    this.addRedactorGroup = new FormGroup({
      id: new FormControl(null),
      login: new FormControl('', Validators.required),
      language: new FormControl('', Validators.required),
      'e-mail': new FormControl('', [Validators.required, Validators.email]),
      role: new FormControl('REDACTOR', Validators.required)
    });
    this.languages = ['PL', 'EN'];
  }

  ngOnInit() {

    this.userService.getRedactors().then((item) => {
      this.redactors = item;
    });


  }

  addRedactor() {

  }

}
