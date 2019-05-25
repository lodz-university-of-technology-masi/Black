import {Component, OnInit} from '@angular/core';
import {TestService} from "../../services/test.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Role, Test, User} from "../../model/entities";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-permisions-form',
  templateUrl: './permissions-form.component.html',
  styleUrls: ['./permissions-form.component.css']
})
export class PermissionsFormComponent implements OnInit {

  testId: number;
  test: Test;
  users: User[];
  languages: string[];

  constructor(private testService: TestService,
              private router: Router,
              private route: ActivatedRoute,
              private userService: UserService) {
    this.languages = ['PL', 'EN'];
  }

  async ngOnInit() {
    this.testId = parseInt(this.route.snapshot.paramMap.get('id'));
    this.test = {
      id: this.testId,
      group: 0,
      language: "",
      name: "",
      position: undefined,
      questions: []
    };
    this.userService.getAll().then((item) => {
      this.users = item.filter(user => {
        return (user.role === Role.CANDIDATE) || (user.role === Role.REDACTOR)
      });
    });
  }

  addPermissionCandidate(user) {
    this.testService.changeAvailabilityForUser(this.test, user);
  }

  addPermissionRedactor(user) {
    this.testService.changeAvailabilityForUser(this.test, user);
  }

  removePermissionCandidate(user) {
    console.error("Unimplemented!")//TODO MC
  }

  removePermissionRedactor(user) {
    console.error("Unimplemented!")//TODO MC
  }

  showIfCandidate(user): boolean {
    return user.role === Role.CANDIDATE
  }

  showIfRedactor(user): boolean {
    return user.role === Role.REDACTOR
  }
}
