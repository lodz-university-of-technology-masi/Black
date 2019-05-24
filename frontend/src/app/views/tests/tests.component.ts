import {Component, OnInit} from '@angular/core';
import {TestService} from "../../services/test.service";
import {Test} from "../../model/entities";
import {Router} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-tests',
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.css']
})
export class TestsComponent implements OnInit {
  tests: Test[];

  //Download file
  fileUrl;

  constructor(private sanitizer: DomSanitizer,
              private testService: TestService,
              private router: Router,
              private userService: UserService) {
  }

  ngOnInit() {
    this.loadTests();
  }

  async loadTests() {

    this.tests = await this.testService.getAll();

  }

  async onDeleteTest(test: Test) {
    await this.testService.delete(test);
    await this.loadTests();
  }

  onEditTest(test: Test) {
    this.router.navigate(['/tests', test.id]);
  }

  onSolveTest(test: Test) {
    this.router.navigate(['/solve', test.id]);
  }

  onExportTest(test: Test) {
    const data = 'some text';
    const blob = new Blob([data], {type: 'application/octet-stream'});
    this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
  }

  onCreateTest() {
    this.router.navigate(['/tests', 'new']);
  }
}
