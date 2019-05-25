import {Component, OnInit} from '@angular/core';
import {TestService} from "../../services/test.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PositionService} from "../../services/position.service";
import {UtilsService} from "../../services/utils.service";
import {ToastrService} from "ngx-toastr";
import {Test, User} from "../../model/entities";
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

  constructor(private testService: TestService, //TODO MC Usunąć niepotrzebne
              private router: Router,
              private route: ActivatedRoute,
              private userService: UserService,
              private positionService: PositionService,
              private utilsService: UtilsService,
              private toastr: ToastrService) {
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
    this.userService.getRedactors().then((item) => {
      this.users = item;
    });
    // console.log(this.userService.getAllUsers())
  }

  addPermisionCandidate(user) {
    this.testService.makeAvaliableForCandidate(this.test, user);
  }

  addPermisionRedactor(user) {
    //TODO MC
  }

  removePermisionCandidate(user) {
    //TODO MC
  }

  removePermisionRedactor(user) {
    //TODO MC
  }
}
