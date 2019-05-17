import {Component, OnInit} from '@angular/core';
import {User} from '../../model/entities';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-redactors',
  templateUrl: './redactors.component.html',
  styleUrls: ['./redactors.component.css']
})
export class RedactorsComponent implements OnInit {

  redactors: User[];

  constructor(private userService: UserService) {
  }

  ngOnInit() {

    this.userService.getRedactors().then((item) => {
      this.redactors = item;
      console.log(this.redactors);
    });
  }

  addRedactor() {

  }

}
