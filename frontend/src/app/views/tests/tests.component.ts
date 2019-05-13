import {Component, OnInit} from '@angular/core';
import {TestService} from "../../services/test.service";
import {Test} from "../../model/entities";
import {Router} from "@angular/router";

@Component({
  selector: 'app-tests',
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.css']
})
export class TestsComponent implements OnInit {
  tests: Test[]

  constructor(private testService: TestService, private router: Router) {
  }

  ngOnInit() {
    this.loadTests();
  }

  async loadTests() {

    this.tests = await this.testService.getAll().toPromise();
    console.log(this.tests);

  }

  async onDeleteTest(test: Test) {
    await this.testService.delete(test).toPromise();
    await this.loadTests();
  }

  onEditTest(test: Test) {
    this.router.navigate(['/tests', test.id]);
  }

  onCreateTest() {
    this.router.navigate(['/tests', 'new']);
  }
}
