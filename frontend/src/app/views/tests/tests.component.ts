import {Component, OnInit} from '@angular/core';
import {TestService} from "../../services/test.service";
import {Test} from "../../model/entities";

@Component({
  selector: 'app-tests',
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.css']
})
export class TestsComponent implements OnInit {
  tests: Test[]

  constructor(private testService: TestService) {
  }

  ngOnInit() {
    this.testService.getAll<Test>().subscribe(
      tests => {
        this.tests = tests
        console.log(tests)
      },
      err => console.error(err)
    )
  }
}
